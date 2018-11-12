package lottery.domains.content.payment.cf;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import javautils.http.HttpClientUtil;
import javautils.http.ToUrlParamUtils;
import java.util.TreeMap;
import javautils.date.Moment;
import lottery.domains.content.payment.utils.MoneyFormat;
import lottery.domains.content.payment.lepay.utils.StringUtil;
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
public class CFPayment extends AbstractPayment implements InitializingBean
{
    private static final String BATCH_BIZ_TYPE = "00000";
    private static final String BATCH_VERSION = "00";
    private static final String CHARSET = "UTF-8";
    private static final String SIGN_TYPE = "SHA";
    private static Map<Integer, String> BRANCH_NAMES;
    private static Map<Integer, String> BANK_NAMES;
    @Value("${cf.daifu.url}")
    private String daifuUrl;
    private static final String SIGN_ALGORITHMS = "SHA-1";
    
    static {
        CFPayment.BRANCH_NAMES = new HashMap<Integer, String>();
        CFPayment.BANK_NAMES = new HashMap<Integer, String>();
    }
    
    public void afterPropertiesSet() throws Exception {
        CFPayment.BRANCH_NAMES.put(1, "中国工商银行股份有限公司上海市龙江路支行");
        CFPayment.BRANCH_NAMES.put(2, "中国建设银行北京市分行营业部");
        CFPayment.BRANCH_NAMES.put(3, "中国农业银行股份有限公司忻州和平分理处");
        CFPayment.BRANCH_NAMES.put(4, "招商银行股份有限公司厦门金湖支行");
        CFPayment.BRANCH_NAMES.put(5, "中国银行股份有限公司赣州市客家大道支行");
        CFPayment.BRANCH_NAMES.put(6, "交通银行北京安翔里支行");
        CFPayment.BRANCH_NAMES.put(7, "上海浦东发展银行安亭支行");
        CFPayment.BRANCH_NAMES.put(8, "兴业银行北京安华支行");
        CFPayment.BRANCH_NAMES.put(9, "中信银行北京安贞支行");
        CFPayment.BRANCH_NAMES.put(10, "宁波银行股份有限公司北京东城支行");
        CFPayment.BRANCH_NAMES.put(11, "上海银行股份有限公司北京安贞支行");
        CFPayment.BRANCH_NAMES.put(12, "杭州银行股份有限公司上海北新泾支行");
        CFPayment.BRANCH_NAMES.put(13, "渤海银行股份有限公司北京朝阳门支行");
        CFPayment.BRANCH_NAMES.put(14, "浙商银行股份有限公司杭州滨江支行");
        CFPayment.BRANCH_NAMES.put(15, "广发银行股份有限公司北京朝阳北路支行");
        CFPayment.BRANCH_NAMES.put(16, "中国邮政储蓄银行股份有限公司北京昌平区北七家支行");
        CFPayment.BRANCH_NAMES.put(17, "深圳发展银行");
        CFPayment.BRANCH_NAMES.put(18, "中国民生银行股份有限公北京西大望路支行");
        CFPayment.BRANCH_NAMES.put(19, "中国光大银行股份有限公司北京安定门支行");
        CFPayment.BRANCH_NAMES.put(20, "华夏银行北京德外支行");
        CFPayment.BRANCH_NAMES.put(21, "北京银行安定门支行");
        CFPayment.BRANCH_NAMES.put(22, "南京银行股份有限公司北京车公庄支行");
        CFPayment.BRANCH_NAMES.put(23, "平安银行股份有限公司北京北苑支行");
        CFPayment.BRANCH_NAMES.put(24, "北京农村商业银行股份有限公司漷县支行");
        CFPayment.BANK_NAMES.put(1, "中国工商银行");
        CFPayment.BANK_NAMES.put(2, "中国建设银行");
        CFPayment.BANK_NAMES.put(3, "中国农业银行");
        CFPayment.BANK_NAMES.put(4, "招商银行");
        CFPayment.BANK_NAMES.put(5, "中国银行");
        CFPayment.BANK_NAMES.put(6, "交通银行");
        CFPayment.BANK_NAMES.put(7, "上海浦东发展银行");
        CFPayment.BANK_NAMES.put(8, "兴业银行");
        CFPayment.BANK_NAMES.put(9, "中信银行");
        CFPayment.BANK_NAMES.put(10, "宁波银行");
        CFPayment.BANK_NAMES.put(11, "上海银行");
        CFPayment.BANK_NAMES.put(12, "杭州银行");
        CFPayment.BANK_NAMES.put(13, "渤海银行");
        CFPayment.BANK_NAMES.put(14, "浙商银行");
        CFPayment.BANK_NAMES.put(15, "广发银行");
        CFPayment.BANK_NAMES.put(16, "中国邮政储蓄银行");
        CFPayment.BANK_NAMES.put(18, "中国民生银行");
        CFPayment.BANK_NAMES.put(19, "中国光大银行");
        CFPayment.BANK_NAMES.put(20, "华夏银行");
        CFPayment.BANK_NAMES.put(21, "北京银行");
        CFPayment.BANK_NAMES.put(22, "南京银行");
        CFPayment.BANK_NAMES.put(23, "平安银行");
        CFPayment.BANK_NAMES.put(24, "北京农村商业银行");
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
            final String bankName = CFPayment.BANK_NAMES.get(card.getBankId());
            if (StringUtil.isEmpty(bankName)) {
                json.set(2, "2-4012");
                return null;
            }
            final String amount = MoneyFormat.pasMoney(order.getRecMoney());
            final StringBuffer batchContent = new StringBuffer();
            batchContent.append("1").append(",");
            batchContent.append(card.getCardId()).append(",");
            batchContent.append(card.getCardName()).append(",");
            batchContent.append(bankName).append(",");
            final String branchName = CFPayment.BRANCH_NAMES.get(card.getBankId());
            batchContent.append(branchName).append(",");
            batchContent.append(branchName).append(",");
            batchContent.append("私").append(",");
            batchContent.append(amount).append(",");
            batchContent.append("CNY").append(",");
            batchContent.append("北京").append(",");
            batchContent.append("北京").append(",");
            batchContent.append("").append(",");
            batchContent.append("").append(",");
            batchContent.append("").append(",");
            batchContent.append("").append(",");
            batchContent.append(order.getBillno()).append(",");
            batchContent.append("APIPAY");
            final String date = new Moment().format("yyyyMMdd");
            final Map<String, String> paramsMap = new TreeMap<String, String>();
            paramsMap.put("batchAmount", amount);
            paramsMap.put("batchBiztype", "00000");
            paramsMap.put("batchContent", batchContent.toString());
            paramsMap.put("batchCount", "1");
            paramsMap.put("batchDate", date);
            paramsMap.put("batchNo", order.getBillno());
            paramsMap.put("batchVersion", "00");
            paramsMap.put("charset", "UTF-8");
            paramsMap.put("merchantId", channel.getMerCode());
            final String signStr = String.valueOf(ToUrlParamUtils.toUrlParam(paramsMap, "&", true)) + channel.getMd5Key();
            final String sign = sign(signStr, "UTF-8");
            paramsMap.put("signType", "SHA");
            paramsMap.put("sign", sign);
            final String url = String.valueOf(this.daifuUrl) + "/agentPay/v1/batch/" + channel.getMerCode() + "-" + order.getBillno();
            final String retStr = HttpClientUtil.post(url, paramsMap, null, 10000);
            if (StringUtils.isEmpty(retStr)) {
                this.logError(order, bank, channel, "接口返回空，可能是请求超时");
                json.set(-1, "-1");
                return null;
            }
            final CFPayResult result = (CFPayResult)JSON.parseObject(retStr, (Class)CFPayResult.class);
            if (result == null) {
                this.logError(order, bank, channel, "请求失败，解析返回数据失败，返回数据为：" + retStr);
                json.setWithParams(2, "2-4007", StringUtils.abbreviate(retStr, 20));
                return null;
            }
            if ("S0001".equalsIgnoreCase(result.getRespCode())) {
                this.logSuccess(order, order.getBillno(), channel);
                return order.getBillno();
            }
            if (!StringUtils.isEmpty(result.getRespMessage())) {
                this.logError(order, bank, channel, "请求失败，返回数据为：" + retStr);
                json.setWithParams(2, "2-4002", result.getRespMessage());
                return null;
            }
            final String msg = "未知错误";
            this.logError(order, bank, channel, "请求返回空的错误消息，返回数据：" + retStr + "，开始查询订单状态");
            final CFPayQueryResult queryResult = this.query(order, channel, date);
            if (this.isAccepted(queryResult)) {
                this.logSuccess(order, queryResult.getBatchNo(), channel);
                return queryResult.getBatchNo();
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
    
    public CFPayQueryResult query(final UserWithdraw order, final PaymentChannel channel) {
        final String date = new Moment().fromTime(order.getOperatorTime()).format("yyyyMMdd");
        return this.query(order, channel, date);
    }
    
    private CFPayQueryResult query(final UserWithdraw order, final PaymentChannel channel, final String date) {
        try {
            final Map<String, String> paramsMap = new TreeMap<String, String>();
            paramsMap.put("batchDate", date);
            paramsMap.put("batchNo", order.getBillno());
            paramsMap.put("batchVersion", "00");
            paramsMap.put("charset", "UTF-8");
            paramsMap.put("merchantId", channel.getMerCode());
            final String signStr = String.valueOf(ToUrlParamUtils.toUrlParam(paramsMap, "&", true)) + channel.getMd5Key();
            final String sign = sign(signStr, "UTF-8");
            paramsMap.put("signType", "SHA");
            paramsMap.put("sign", sign);
            final String paramsStr = ToUrlParamUtils.toUrlParam(paramsMap, "&", true);
            final String url = String.valueOf(this.daifuUrl) + "/agentPay/v1/batch/" + channel.getMerCode() + "-" + order.getBillno() + "?" + paramsStr;
            final String retStr = HttpClientUtil.get(url, null, 10000);
            if (StringUtils.isEmpty(retStr)) {
                this.logError(order, null, channel, "查询请求失败，发送请求后返回空数据");
                return null;
            }
            this.logInfo(order, null, channel, "查询返回数据：" + retStr);
            final CFPayQueryResult result = (CFPayQueryResult)JSON.parseObject(retStr, (Class)CFPayQueryResult.class);
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
    
    private static String sign(final String content, final String inputCharset) {
        try {
            final MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.update(content.getBytes(inputCharset));
            final byte[] messageDigest = digest.digest();
            final StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; ++i) {
                final String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString().toUpperCase();
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e2) {
            e2.printStackTrace();
        }
        return null;
    }
    
    public boolean isAccepted(final CFPayQueryResult result) {
        return "S0001".equalsIgnoreCase(result.getRespCode()) && StringUtils.isNotEmpty(result.getBatchNo());
    }
    
    public int transferBankStatus(final String batchContent) {
        final String bankStatus = this.getBankStatusFromBatchContent(batchContent);
        if (bankStatus == null) {
            return 1;
        }
        if ("null".equalsIgnoreCase(bankStatus)) {
            return 1;
        }
        if ("成功".equalsIgnoreCase(bankStatus)) {
            return 2;
        }
        if ("success".equalsIgnoreCase(bankStatus)) {
            return 2;
        }
        if ("failure".equalsIgnoreCase(bankStatus)) {
            return -5;
        }
        if ("失败".equalsIgnoreCase(bankStatus)) {
            return -5;
        }
        return -4;
    }
    
    private String getBankStatusFromBatchContent(final String batchContent) {
        final String[] batchContents = batchContent.split(",");
        final String bankStatus = batchContents[12];
        return bankStatus;
    }
    
    public static void main(final String[] args) {
        final String content = "1,171206111554nkjWivyJ,6217000010074430920,弋会超,中国建设银行北京市分行营业部,中国建设银行北京市分行营业部,中国建设银行,0,1100.00,CNY,APIPAY,,成功,";
        final String[] batchContents = content.split(",");
        final String bankStatus = batchContents[12];
        System.out.println(bankStatus);
    }
}
