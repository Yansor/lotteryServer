package lottery.domains.content.payment.af;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import javautils.http.HttpClientUtil;
import org.apache.commons.codec.digest.DigestUtils;
import java.io.UnsupportedEncodingException;
import sun.misc.BASE64Encoder;
import lottery.domains.content.entity.PaymentChannel;
import lottery.domains.content.entity.PaymentChannelBank;
import lottery.domains.content.entity.UserCard;
import lottery.domains.content.entity.UserWithdraw;
import admin.web.WebJSONObject;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Value;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import javax.servlet.ServletContext;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.InitializingBean;
import lottery.domains.content.AbstractPayment;

@Component
public class AFPayment extends AbstractPayment implements InitializingBean
{
    @Autowired
    private ServletContext servletContext;
    private static Map<Integer, String> BRANCH_NAMES;
    @Value("${af.daifu.url}")
    private String daifuUrl;
    @Value("${af.daifu.queryurl}")
    private String daifuQueryUrl;
    
    static {
        AFPayment.BRANCH_NAMES = new HashMap<Integer, String>();
    }
    
    public void afterPropertiesSet() throws Exception {
        AFPayment.BRANCH_NAMES.put(1, "中国工商银行股份有限公司上海市龙江路支行");
        AFPayment.BRANCH_NAMES.put(2, "中国建设银行北京市分行营业部");
        AFPayment.BRANCH_NAMES.put(3, "中国农业银行股份有限公司忻州和平分理处");
        AFPayment.BRANCH_NAMES.put(4, "招商银行股份有限公司厦门金湖支行");
        AFPayment.BRANCH_NAMES.put(5, "中国银行股份有限公司赣州市客家大道支行");
        AFPayment.BRANCH_NAMES.put(6, "交通银行北京安翔里支行");
        AFPayment.BRANCH_NAMES.put(7, "上海浦东发展银行安亭支行");
        AFPayment.BRANCH_NAMES.put(8, "兴业银行北京安华支行");
        AFPayment.BRANCH_NAMES.put(9, "中信银行北京安贞支行");
        AFPayment.BRANCH_NAMES.put(10, "宁波银行股份有限公司北京东城支行");
        AFPayment.BRANCH_NAMES.put(11, "上海银行股份有限公司北京安贞支行");
        AFPayment.BRANCH_NAMES.put(12, "杭州银行股份有限公司上海北新泾支行");
        AFPayment.BRANCH_NAMES.put(13, "渤海银行股份有限公司北京朝阳门支行");
        AFPayment.BRANCH_NAMES.put(14, "浙商银行股份有限公司杭州滨江支行");
        AFPayment.BRANCH_NAMES.put(15, "广发银行股份有限公司北京朝阳北路支行");
        AFPayment.BRANCH_NAMES.put(16, "中国邮政储蓄银行股份有限公司北京昌平区北七家支行");
        AFPayment.BRANCH_NAMES.put(17, "深圳发展银行");
        AFPayment.BRANCH_NAMES.put(18, "中国民生银行股份有限公北京西大望路支行");
        AFPayment.BRANCH_NAMES.put(19, "中国光大银行股份有限公司北京安定门支行");
        AFPayment.BRANCH_NAMES.put(20, "华夏银行北京德外支行");
        AFPayment.BRANCH_NAMES.put(21, "北京银行安定门支行");
        AFPayment.BRANCH_NAMES.put(22, "南京银行股份有限公司北京车公庄支行");
        AFPayment.BRANCH_NAMES.put(23, "平安银行股份有限公司北京北苑支行");
        AFPayment.BRANCH_NAMES.put(24, "北京农村商业银行股份有限公司漷县支行");
    }
    
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
    
    public static String base64Encoder(final String ss, final String charset) {
        final BASE64Encoder en = new BASE64Encoder();
        String encStr = "";
        if (charset == null || "".equals(charset)) {
            encStr = en.encode(ss.getBytes());
            return encStr;
        }
        try {
            encStr = en.encode(ss.getBytes(charset));
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encStr;
    }
    
    private String daifuInternal(final WebJSONObject json, final UserWithdraw order, final UserCard card, final PaymentChannelBank bank, final PaymentChannel channel) {
        try {
            final Map<String, String> params = new HashMap<String, String>();
            final String cardName = base64Encoder(order.getCardName(), "UTF-8");
            params.put("merchant_no", channel.getMerCode());
            params.put("order_no", order.getBillno());
            params.put("card_no", order.getCardId());
            params.put("account_name", cardName);
            params.put("bank_branch", base64Encoder(AFPayment.BRANCH_NAMES.get(bank.getBankId()), "UTF-8"));
            params.put("cnaps_no", "demo");
            params.put("pay_pwd", channel.getExt1());
            params.put("bank_code", bank.getCode());
            params.put("bank_name", order.getBankName());
            params.put("amount", new StringBuilder(String.valueOf(order.getRecMoney())).toString());
            String src = "merchant_no=" + params.get("merchant_no") + "&order_no=" + params.get("order_no") + "&card_no=" + params.get("card_no") + "&account_name=" + params.get("account_name") + "&bank_branch=" + params.get("bank_branch") + "&cnaps_no=" + params.get("cnaps_no") + "&bank_code=" + params.get("bank_code") + "&bank_name=" + params.get("bank_name") + "&amount=" + params.get("amount") + "&pay_pwd=" + params.get("pay_pwd");
            src = String.valueOf(src) + "&key=" + channel.getMd5Key();
            final String sign = DigestUtils.md5Hex(src);
            params.put("sign", sign);
            final String retStr = HttpClientUtil.post(this.daifuUrl, params, null, 10000);
            if (StringUtils.isEmpty(retStr)) {
                this.logError(order, bank, channel, "接口返回空，可能是请求超时");
                json.set(-1, "-1");
                return null;
            }
            final AFDaifuResult result = (AFDaifuResult)JSON.parseObject(retStr, (Class)AFDaifuResult.class);
            if (result == null) {
                this.logError(order, bank, channel, "请求失败，解析返回数据失败，返回数据为：" + retStr);
                json.setWithParams(2, "2-4007", StringUtils.abbreviate(retStr, 20));
                return null;
            }
            if ("000000".equals(result.getResult_code())) {
                if (StringUtils.isEmpty(result.getMer_order_no())) {
                    this.logError(order, bank, channel, "请求失败，返回第三方注单号为空，返回数据为：" + retStr);
                    json.setWithParams(2, "2-4007", StringUtils.abbreviate(retStr, 20));
                    return null;
                }
                this.logSuccess(order, order.getBillno(), channel);
                return order.getBillno();
            }
            else {
                this.logError(order, bank, channel, "请求返回错误消息，返回数据：" + retStr + "，开始查询订单状态");
                final AFDaifuQueryResult queryResult = this.query(order, channel);
                if (this.isSuccessRequested(queryResult)) {
                    this.logSuccess(order, queryResult.getOrder_no(), channel);
                    return queryResult.getOrder_no();
                }
                this.logError(order, bank, channel, "请求失败，返回数据为：" + retStr);
                json.setWithParams(2, "2-4002", result.getResult_msg());
                return null;
            }
        }
        catch (Exception e) {
            this.logException(order, bank, channel, "代付请求失败", e);
            json.set(-1, "-1");
            return null;
        }
    }
    
    public static void main(final String[] args) {
    }
    
    public AFDaifuQueryResult query(final UserWithdraw order, final PaymentChannel channel) {
        try {
            final String outOrderId = order.getBillno();
            final String merchantCode = channel.getMerCode();
            final Map<String, String> params = new HashMap<String, String>();
            params.put("merchant_no", merchantCode);
            params.put("order_no", outOrderId);
            final String src = "merchant_no=" + merchantCode + "&order_no=" + outOrderId + "&key=" + channel.getMd5Key();
            final String sign = DigestUtils.md5Hex(src);
            params.put("sign", sign);
            final String retStr = HttpClientUtil.post(this.daifuQueryUrl, params, null, 10000);
            if (StringUtils.isEmpty(retStr)) {
                this.logError(order, null, channel, "查询请求失败，发送请求后返回空数据");
                return null;
            }
            this.logInfo(order, null, channel, "查询返回数据：" + retStr);
            AFDaifuQueryResult result = null;
            try {
                result = (AFDaifuQueryResult)JSON.parseObject(retStr, (Class)AFDaifuQueryResult.class);
                if (result == null) {
                    this.logError(order, null, channel, "查询请求失败，解析返回数据失败");
                    return null;
                }
            }
            catch (Exception e2) {
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
    
    public boolean isSuccessRequested(final AFDaifuQueryResult queryResult) {
        return queryResult != null && !StringUtils.isEmpty(queryResult.getOrder_no()) && ("000000".equalsIgnoreCase(queryResult.getResult_code()) || "S".equalsIgnoreCase(queryResult.getResult()));
    }
    
    public int transferBankStatus(final String bankStatus) {
        int remitStatus = -4;
        switch (bankStatus) {
            case "F": {
                remitStatus = -2;
                break;
            }
            case "H": {
                remitStatus = 1;
                break;
            }
            case "S": {
                remitStatus = 2;
                break;
            }
            default:
                break;
        }
        return remitStatus;
    }
}
