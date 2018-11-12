package lottery.domains.content.payment.fkt;

import org.springframework.web.client.RestTemplate;
import org.apache.commons.codec.digest.DigestUtils;
import javautils.http.ToUrlParamUtils;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import java.util.Map;
import javautils.http.HttpClientUtil;
import java.util.TreeMap;
import javautils.date.Moment;
import lottery.domains.content.payment.utils.MoneyFormat;
import lottery.domains.content.entity.PaymentChannel;
import lottery.domains.content.entity.PaymentChannelBank;
import lottery.domains.content.entity.UserCard;
import lottery.domains.content.entity.UserWithdraw;
import admin.web.WebJSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.pool.LotteryDataFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import lottery.domains.content.AbstractPayment;

@Component
public class FKTPayment extends AbstractPayment
{
    private static final String INPUT_CHARSET = "UTF-8";
    private static final String REMARK = "df";
    private static final String OUTPUT_SUCCESS = "success";
    private static final String OUTPUT_FAILED = "failed";
    @Value("${fkt.daifu.url}")
    private String daifuUrl;
    @Value("${fkt.daifu.queryurl}")
    private String queryUrl;
    @Autowired
    private LotteryDataFactory dataFactory;
    
    @Override
    public String daifu(final WebJSONObject json, final UserWithdraw order, final UserCard card, final PaymentChannelBank bank, final PaymentChannel channel) {
        try {
            this.logStart(order, bank, channel);
            return this.daifuInternal(json, order, card, bank, channel);
        }
        catch (Exception e) {
            this.logException(order, bank, channel, "代付请求失败", e);
            json.set(2, "2-4000");
            return null;
        }
    }
    
    private String daifuInternal(final WebJSONObject json, final UserWithdraw order, final UserCard card, final PaymentChannelBank bank, final PaymentChannel channel) {
        try {
            final String amount = MoneyFormat.moneyToYuanForPositive(new StringBuilder(String.valueOf(order.getRecMoney())).toString());
            final String currentDate = new Moment().toSimpleTime();
            final Map<String, String> paramsMap = new TreeMap<String, String>();
            paramsMap.put("input_charset", "UTF-8");
            paramsMap.put("merchant_code", channel.getMerCode());
            paramsMap.put("amount", amount);
            paramsMap.put("transid", order.getBillno());
            paramsMap.put("bitch_no", order.getBillno());
            paramsMap.put("currentDate", currentDate);
            paramsMap.put("bank_name", bank.getCode());
            paramsMap.put("account_name", order.getCardName());
            paramsMap.put("account_number", order.getCardId());
            paramsMap.put("remark", "df");
            final String sign = sign(paramsMap, channel);
            paramsMap.put("sign", sign);
            final String url = String.valueOf(this.daifuUrl) + "?_=" + System.currentTimeMillis();
            final String retStr = HttpClientUtil.post(url, paramsMap, null, 60000);
            if (StringUtils.isEmpty(retStr)) {
                this.logError(order, bank, channel, "接口返回空，可能是请求超时");
                json.set(-1, "-1");
                return null;
            }
            final FKTPayResult result = (FKTPayResult)JSON.parseObject(retStr, (Class)FKTPayResult.class);
            if (result == null) {
                this.logError(order, bank, channel, "请求失败，解析返回数据失败，返回数据为：" + retStr);
                json.setWithParams(2, "2-4007", StringUtils.abbreviate(retStr, 20));
                return null;
            }
            if (this.isAcceptedRequest(result.getIsSuccess()) && this.isAcceptedBankStatus(result.getBankStatus())) {
                this.logSuccess(order, result.getOrderId(), channel);
                return result.getOrderId();
            }
            if (!StringUtils.isEmpty(result.getErrrorMsg())) {
                this.logError(order, bank, channel, "请求失败，返回数据为：" + retStr);
                json.setWithParams(2, "2-4002", result.getErrrorMsg());
                return null;
            }
            final String msg = "未知错误";
            this.logError(order, bank, channel, "请求返回空的错误消息，返回数据：" + retStr + "，开始查询订单状态");
            final FKTPayResult queryResult = this.query(order, channel);
            if (this.isAcceptedRequest(queryResult.getIsSuccess()) && this.isAcceptedBankStatus(queryResult.getBankStatus())) {
                this.logSuccess(order, queryResult.getOrderId(), channel);
                return queryResult.getOrderId();
            }
            this.logError(order, bank, channel, "请求失败，返回数据为：" + retStr);
            json.setWithParams(2, "2-4002", msg);
            return null;
        }
        catch (Exception e) {
            this.logException(order, bank, channel, "代付请求失败", e);
            json.set(-1, "-1");
            return null;
        }
    }
    
    public FKTPayResult query(final UserWithdraw order, final PaymentChannel channel) {
        return this.query(order.getPayBillno(), channel);
    }
    
    public FKTPayResult query(final String orderId, final PaymentChannel channel) {
        try {
            final Map<String, String> paramsMap = new TreeMap<String, String>();
            paramsMap.put("input_charset", "UTF-8");
            paramsMap.put("merchant_code", channel.getMerCode());
            paramsMap.put("currentDate", new Moment().toSimpleTime());
            paramsMap.put("order_id", orderId);
            final String sign = sign(paramsMap, channel);
            paramsMap.put("sign", sign);
            final String url = String.valueOf(this.queryUrl) + "?_=" + System.currentTimeMillis();
            final String retStr = HttpClientUtil.post(url, paramsMap, null, 60000);
            if (StringUtils.isEmpty(retStr)) {
                this.logError(channel, "查询请求失败，发送请求后返回空数据");
                return null;
            }
            this.logInfo(channel, "查询返回数据：" + retStr);
            final FKTPayResult result = (FKTPayResult)JSON.parseObject(retStr, (Class)FKTPayResult.class);
            if (result == null) {
                this.logError(channel, "查询请求失败，解析返回数据失败");
                return null;
            }
            return result;
        }
        catch (Exception e) {
            this.logException(channel, "查询请求失败", e);
            return null;
        }
    }
    
    private static String sign(final Map<String, String> paramsMap, final PaymentChannel channel) {
        String sign = ToUrlParamUtils.toUrlParamWithoutEmpty(paramsMap, "&");
        sign = String.valueOf(sign) + "&key=" + channel.getMd5Key();
        sign = DigestUtils.md5Hex(sign);
        sign = DigestUtils.md5Hex(sign.toUpperCase());
        sign = DigestUtils.md5Hex(sign);
        return sign;
    }
    
    public boolean isAcceptedRequest(final String isSuccess) {
        return "true".equalsIgnoreCase(isSuccess);
    }
    
    public boolean isAcceptedBankStatus(final String bankStatus) {
        return "0".equalsIgnoreCase(bankStatus) || "1".equalsIgnoreCase(bankStatus) || "2".equalsIgnoreCase(bankStatus);
    }
    
    public int transferBankStatus(final String bankStatus) {
        int remitStatus = -4;
        switch (bankStatus) {
            case "0": {
                remitStatus = 3;
                break;
            }
            case "1": {
                remitStatus = 1;
                break;
            }
            case "2": {
                remitStatus = 2;
                break;
            }
            case "3": {
                remitStatus = -5;
                break;
            }
            default:
                break;
        }
        return remitStatus;
    }
    
    public static void main(final String[] args) {
        try {
            final Map<String, String> paramsMap = new TreeMap<String, String>();
            paramsMap.put("input_charset", "UTF-8");
            paramsMap.put("merchant_code", "84354848");
            paramsMap.put("currentDate", "2017-12-30 18:07:07");
            paramsMap.put("order_id", "171230180325dYO6Xf9X");
            String sign = ToUrlParamUtils.toUrlParamWithoutEmpty(paramsMap, "&");
            sign = String.valueOf(sign) + "&key=aa34ccc93e5202cc7142b883d0aef189";
            sign = DigestUtils.md5Hex(sign);
            sign = DigestUtils.md5Hex(sign.toUpperCase());
            sign = DigestUtils.md5Hex(sign);
            paramsMap.put("sign", sign);
            System.out.println("请求参数：" + paramsMap);
            final String url = "http://df.fktpay.vip/gateway/df_query.html";
            System.out.println("POST请求（Form表单）：" + url);
            final String paramsUrl = ToUrlParamUtils.toUrlParam(paramsMap);
            final String _url = String.valueOf(url) + "?_=" + System.currentTimeMillis() + "&" + paramsUrl;
            final String retStr = (String)new RestTemplate().postForObject(_url, (Object)null, (Class)String.class, new Object[0]);
            System.out.println("请求返回：" + retStr);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
