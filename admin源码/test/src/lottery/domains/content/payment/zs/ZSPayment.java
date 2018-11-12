package lottery.domains.content.payment.zs;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import javautils.http.HttpClientUtil;
import lottery.domains.content.payment.zs.utils.MD5Encrypt;
import lottery.domains.content.payment.zs.utils.StringUtil;
import lottery.domains.content.entity.PaymentChannel;
import lottery.domains.content.entity.PaymentChannelBank;
import lottery.domains.content.entity.UserCard;
import lottery.domains.content.entity.UserWithdraw;
import admin.web.WebJSONObject;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Value;
import java.util.Map;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.InitializingBean;
import lottery.domains.content.AbstractPayment;

@Component
public class ZSPayment extends AbstractPayment implements InitializingBean
{
    private static Map<Integer, String> BRANCH_NAMES;
    @Value("${zs.daifu.url}")
    private String daifuUrl;
    @Value("${zs.daifu.queryurl}")
    private String daifuQueryUrl;
    
    static {
        ZSPayment.BRANCH_NAMES = new HashMap<Integer, String>();
    }
    
    public void afterPropertiesSet() throws Exception {
        ZSPayment.BRANCH_NAMES.put(1, "中国工商银行股份有限公司上海市龙江路支行");
        ZSPayment.BRANCH_NAMES.put(2, "中国建设银行北京市分行营业部");
        ZSPayment.BRANCH_NAMES.put(3, "中国农业银行股份有限公司忻州和平分理处");
        ZSPayment.BRANCH_NAMES.put(4, "招商银行股份有限公司厦门金湖支行");
        ZSPayment.BRANCH_NAMES.put(5, "中国银行股份有限公司赣州市客家大道支行");
        ZSPayment.BRANCH_NAMES.put(6, "交通银行北京安翔里支行");
        ZSPayment.BRANCH_NAMES.put(7, "上海浦东发展银行安亭支行");
        ZSPayment.BRANCH_NAMES.put(8, "兴业银行北京安华支行");
        ZSPayment.BRANCH_NAMES.put(9, "中信银行北京安贞支行");
        ZSPayment.BRANCH_NAMES.put(10, "宁波银行股份有限公司北京东城支行");
        ZSPayment.BRANCH_NAMES.put(11, "上海银行股份有限公司北京安贞支行");
        ZSPayment.BRANCH_NAMES.put(12, "杭州银行股份有限公司上海北新泾支行");
        ZSPayment.BRANCH_NAMES.put(13, "渤海银行股份有限公司北京朝阳门支行");
        ZSPayment.BRANCH_NAMES.put(14, "浙商银行股份有限公司杭州滨江支行");
        ZSPayment.BRANCH_NAMES.put(15, "广发银行股份有限公司北京朝阳北路支行");
        ZSPayment.BRANCH_NAMES.put(16, "中国邮政储蓄银行股份有限公司北京昌平区北七家支行");
        ZSPayment.BRANCH_NAMES.put(17, "深圳发展银行");
        ZSPayment.BRANCH_NAMES.put(18, "中国民生银行股份有限公北京西大望路支行");
        ZSPayment.BRANCH_NAMES.put(19, "中国光大银行股份有限公司北京安定门支行");
        ZSPayment.BRANCH_NAMES.put(20, "华夏银行北京德外支行");
        ZSPayment.BRANCH_NAMES.put(21, "北京银行安定门支行");
        ZSPayment.BRANCH_NAMES.put(22, "南京银行股份有限公司北京车公庄支行");
        ZSPayment.BRANCH_NAMES.put(23, "平安银行股份有限公司北京北苑支行");
        ZSPayment.BRANCH_NAMES.put(24, "北京农村商业银行股份有限公司漷县支行");
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
    
    private String daifuInternal(final WebJSONObject json, final UserWithdraw order, final UserCard card, final PaymentChannelBank bank, final PaymentChannel channel) {
        try {
            final String nonceStr = StringUtil.getRandomNum(32);
            final String outOrderId = order.getBillno();
            final Long totalAmount = StringUtil.changeY2F(new StringBuilder(String.valueOf(order.getRecMoney())).toString());
            final String merchantCode = channel.getMerCode();
            final String intoCardNo = order.getCardId();
            final String intoCardName = order.getCardName();
            final String intoCardType = "2";
            final String type = "04";
            final String bankName = "";
            final String remark = "";
            final String bankCode = "";
            final String signStr = String.format("bankCode=%s&bankName=%s&intoCardName=%s&intoCardNo=%s&intoCardType=%s&merchantCode=%s&nonceStr=%s&outOrderId=%s&totalAmount=%s&type=%s&KEY=%s", bankCode, bankName, intoCardName, intoCardNo, intoCardType, merchantCode, nonceStr, outOrderId, totalAmount, type, channel.getMd5Key());
            final String sign = MD5Encrypt.getMessageDigest(signStr);
            final Map<String, String> paramsMap = new HashMap<String, String>();
            paramsMap.put("bankCode", bankCode);
            paramsMap.put("bankName", bankName);
            paramsMap.put("intoCardName", intoCardName);
            paramsMap.put("intoCardNo", intoCardNo);
            paramsMap.put("intoCardType", intoCardType);
            paramsMap.put("merchantCode", merchantCode);
            paramsMap.put("nonceStr", nonceStr);
            paramsMap.put("outOrderId", outOrderId);
            paramsMap.put("totalAmount", new StringBuilder().append(totalAmount).toString());
            paramsMap.put("type", type);
            paramsMap.put("remark", remark);
            paramsMap.put("sign", sign);
            final String retStr = HttpClientUtil.post(this.daifuUrl, paramsMap, null, 10000);
            if (StringUtils.isEmpty(retStr)) {
                this.logError(order, bank, channel, "接口返回空，可能是请求超时");
                json.set(-1, "-1");
                return null;
            }
            final JSONObject jsonObject = JSON.parseObject(retStr);
            final String code = jsonObject.getString("code");
            final String msg = jsonObject.getString("msg");
            if ("00".equals(code)) {
                final String data = jsonObject.getString("data");
                final ZSDaifuResult result = (ZSDaifuResult)JSON.parseObject(data, (Class)ZSDaifuResult.class);
                if (result == null) {
                    this.logError(order, bank, channel, "请求失败，解析返回数据失败，返回数据为：" + retStr);
                    json.setWithParams(2, "2-4007", StringUtils.abbreviate(retStr, 20));
                    return null;
                }
                if (StringUtils.isEmpty(result.getOrderId())) {
                    this.logError(order, bank, channel, "请求失败，返回第三方注单号为空，返回数据为：" + retStr);
                    json.setWithParams(2, "2-4007", StringUtils.abbreviate(retStr, 20));
                    return null;
                }
                this.logSuccess(order, result.getOrderId(), channel);
                return result.getOrderId();
            }
            else {
                this.logError(order, bank, channel, "请求返回错误消息，返回数据：" + retStr + "，开始查询订单状态");
                final ZSDaifuQueryResult queryResult = this.query(order, channel);
                if (this.isSuccessRequested(queryResult)) {
                    this.logSuccess(order, queryResult.getOrderId(), channel);
                    return queryResult.getOrderId();
                }
                this.logError(order, bank, channel, "请求失败，返回数据为：" + retStr);
                json.setWithParams(2, "2-4002", msg);
                return null;
            }
        }
        catch (Exception e) {
            this.logException(order, bank, channel, "代付请求失败", e);
            json.set(-1, "-1");
            return null;
        }
    }
    
    public ZSDaifuQueryResult query(final UserWithdraw order, final PaymentChannel channel) {
        try {
            final String nonceStr = StringUtil.getRandomNum(32);
            final String outOrderId = order.getBillno();
            final String md5Key = channel.getMd5Key();
            final String merchantCode = channel.getMerCode();
            final String signStr = String.format("merchantCode=%s&nonceStr=%s&outOrderId=%s&KEY=%s", merchantCode, nonceStr, outOrderId, md5Key);
            final String sign = MD5Encrypt.getMessageDigest(signStr);
            final Map<String, String> paramsMap = new HashMap<String, String>();
            paramsMap.put("merchantCode", merchantCode);
            paramsMap.put("nonceStr", nonceStr);
            paramsMap.put("outOrderId", outOrderId);
            paramsMap.put("sign", sign);
            final String retStr = HttpClientUtil.post(this.daifuQueryUrl, paramsMap, null, 10000);
            if (StringUtils.isEmpty(retStr)) {
                this.logError(order, null, channel, "查询请求失败，发送请求后返回空数据");
                return null;
            }
            this.logInfo(order, null, channel, "查询返回数据：" + retStr);
            final JSONObject jsonObject = JSON.parseObject(retStr);
            final String code = jsonObject.getString("code");
            String data = null;
            if ("00".equals(code)) {
                data = jsonObject.getString("data");
            }
            if (StringUtils.isEmpty(data)) {
                this.logError(order, null, channel, "查询请求失败，解析返回数据失败");
                return null;
            }
            final ZSDaifuQueryResult result = (ZSDaifuQueryResult)JSON.parseObject(data, (Class)ZSDaifuQueryResult.class);
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
    
    public boolean isSuccessRequested(final ZSDaifuQueryResult queryResult) {
        return queryResult != null && !StringUtils.isEmpty(queryResult.getOrderId()) && ("00".equalsIgnoreCase(queryResult.getState()) || "90".equalsIgnoreCase(queryResult.getState()));
    }
    
    public int transferBankStatus(final String bankStatus) {
        int remitStatus = -4;
        switch (bankStatus) {
            case "00": {
                remitStatus = 2;
                break;
            }
            case "02": {
                remitStatus = -2;
                break;
            }
            case "90": {
                remitStatus = 1;
                break;
            }
            default:
                break;
        }
        return remitStatus;
    }
}
