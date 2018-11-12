package lottery.domains.content.payment.ht;

import java.util.Iterator;
import org.apache.commons.codec.digest.DigestUtils;
import java.util.Map.Entry;
import java.util.TreeMap;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import java.util.Map;
import javautils.http.HttpClientUtil;
import java.util.HashMap;
import javautils.date.Moment;
import lottery.domains.content.payment.utils.MoneyFormat;
import lottery.domains.content.entity.PaymentChannel;
import lottery.domains.content.entity.PaymentChannelBank;
import lottery.domains.content.entity.UserCard;
import lottery.domains.content.entity.UserWithdraw;
import admin.web.WebJSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import lottery.domains.content.AbstractPayment;

@Component
public class HTPayment extends AbstractPayment
{
    @Value("${ht.daifu.url}")
    private String daifuUrl;
    @Value("${ht.daifu.queryurl}")
    private String daifuQueryUrl;
    
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
            final String amount = MoneyFormat.pasMoney(order.getRecMoney());
            final String time = new Moment().toSimpleTime();
            final Map<String, String> paramsMap = new HashMap<String, String>();
            paramsMap.put("merchant_code", channel.getMerCode());
            paramsMap.put("order_amount", amount);
            paramsMap.put("trade_no", order.getBillno());
            paramsMap.put("order_time", time);
            paramsMap.put("bank_code", bank.getCode());
            paramsMap.put("account_name", order.getCardName());
            paramsMap.put("account_number", order.getCardId());
            final String sign = getSign(paramsMap, channel.getMd5Key());
            paramsMap.put("sign", sign);
            final String retStr = HttpClientUtil.post(this.daifuUrl, paramsMap, null, 10000);
            if (StringUtils.isEmpty(retStr)) {
                this.logError(order, bank, channel, "接口返回空，可能是请求超时");
                json.set(-1, "-1");
                return null;
            }
            final HTPayResult result = (HTPayResult)JSON.parseObject(retStr, (Class)HTPayResult.class);
            if (result == null) {
                this.logError(order, bank, channel, "请求失败，解析返回数据失败，返回数据为：" + retStr);
                json.setWithParams(2, "2-4007", StringUtils.abbreviate(retStr, 20));
                return null;
            }
            if (this.isAccepted(result)) {
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
            final HTPayResult queryResult = this.query(order, channel);
            if (queryResult != null && this.isAccepted(queryResult)) {
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
    
    public HTPayResult query(final UserWithdraw order, final PaymentChannel channel) {
        try {
            final String time = new Moment().toSimpleTime();
            final Map<String, String> paramsMap = new HashMap<String, String>();
            paramsMap.put("merchant_code", channel.getMerCode());
            paramsMap.put("now_date", time);
            paramsMap.put("trade_no", order.getBillno());
            final String sign = getSign(paramsMap, channel.getMd5Key());
            paramsMap.put("sign", sign);
            final String retStr = HttpClientUtil.post(this.daifuQueryUrl, paramsMap, null, 10000);
            if (StringUtils.isEmpty(retStr)) {
                this.logError(order, null, channel, "查询请求失败，发送请求后返回空数据");
                return null;
            }
            this.logInfo(order, null, channel, "查询返回数据：" + retStr);
            final HTPayResult result = (HTPayResult)JSON.parseObject(retStr, (Class)HTPayResult.class);
            if (result == null) {
                this.logError(order, null, channel, "查询请求失败，解析返回数据失败");
                return null;
            }
            return result;
        }
        catch (Exception e) {
            this.logException(order, null, channel, "查询请求失败", e);
            return null;
        }
    }
    
    private static String getSign(final Map<String, String> paramsMap, final String md5Key) {
        final TreeMap<String, String> signMap = new TreeMap<String, String>(paramsMap);
        final StringBuffer signStr = new StringBuffer();
        final Iterator<Entry<String, String>> it = signMap.entrySet().iterator();
        while (it.hasNext()) {
            final Entry<String, String> entry = it.next();
            final String key = entry.getKey();
            if (!"sign".equalsIgnoreCase(key)) {
                if ("reqBody".equalsIgnoreCase(key)) {
                    continue;
                }
                final String value = entry.getValue();
                if (StringUtils.isEmpty(value)) {
                    continue;
                }
                signStr.append(entry.getKey()).append("=").append(value);
                if (!it.hasNext()) {
                    continue;
                }
                signStr.append("&");
            }
        }
        signStr.append("&key=").append(md5Key);
        final String sign = DigestUtils.md5Hex(signStr.toString());
        return sign;
    }
    
    public boolean isAccepted(final HTPayResult result) {
        return "true".equalsIgnoreCase(result.getIsSuccess()) && this.isAcceptedBankStatus(result.getBankStatus()) && StringUtils.isNotEmpty(result.getOrderId());
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
    
    public boolean isUnprocess(final HTPayResult result) {
        return "true".equalsIgnoreCase(result.getIsSuccess()) && "0".equals(result.getBankStatus()) && StringUtils.isNotEmpty(result.getOrderId());
    }
    
    public boolean isBankingProcessing(final HTPayResult result) {
        return "true".equalsIgnoreCase(result.getIsSuccess()) && "1".equals(result.getBankStatus()) && StringUtils.isNotEmpty(result.getOrderId());
    }
    
    public boolean isBankingProcessSuccessed(final HTPayResult result) {
        return "true".equalsIgnoreCase(result.getIsSuccess()) && "2".equals(result.getBankStatus()) && StringUtils.isNotEmpty(result.getOrderId());
    }
    
    public boolean isBankingProcessedFaild(final HTPayResult result) {
        return "true".equalsIgnoreCase(result.getIsSuccess()) && "3".equals(result.getBankStatus()) && StringUtils.isNotEmpty(result.getOrderId());
    }
    
    private boolean isAcceptedBankStatus(final String status) {
        return "0".equalsIgnoreCase(status) || "1".equalsIgnoreCase(status) || "2".equalsIgnoreCase(status);
    }
}
