package lottery.domains.content.payment.xinbei;

import org.apache.commons.lang.StringUtils;
import javautils.http.HttpClientUtil;
import lottery.domains.content.payment.mkt.URLUtils;
import com.alibaba.fastjson.JSON;
import java.util.LinkedHashMap;
import org.apache.commons.codec.digest.DigestUtils;
import lottery.domains.content.payment.utils.MoneyFormat;
import lottery.domains.content.entity.PaymentChannel;
import admin.web.WebJSONObject;
import java.util.HashMap;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import java.util.Map;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.InitializingBean;

@Component
public class XinBeiPayment implements InitializingBean
{
    private static final Logger log;
    private static final String ENCRYPT_KEY = "1";
    private static final String ENCRYPT = "1";
    private static final String SETTLE_TYPE = "1";
    private static final String URGENT_TYPE = "1";
    private static Map<Integer, String> BRANCH_NAMES;
    @Value("${xinbei.daifu.url}")
    private String daifuUrl;
    @Value("${xinbei.daifu.query.url}")
    private String daifuQueryUrl;
    @Value("${xinbei.daifu.wangyin.paypasswd}")
    private String daifuWangyingPasswd;
    @Value("${xinbei.daifu.wangyin.tokenkey}")
    private String daifuWangyingTokenKey;
    @Value("${xinbei.daifu.wechat.paypasswd}")
    private String daifuWeChatPasswd;
    @Value("${xinbei.daifu.wechat.tokenkey}")
    private String daifuWeChatTokenKey;
    @Value("${xinbei.daifu.alipay.paypasswd}")
    private String daifuAlipayPasswd;
    @Value("${xinbei.daifu.alipay.tokenkey}")
    private String daifuAlipayTokenKey;
    
    static {
        log = LoggerFactory.getLogger((Class)XinBeiPayment.class);
        XinBeiPayment.BRANCH_NAMES = new HashMap<Integer, String>();
    }
    
    public void afterPropertiesSet() throws Exception {
        XinBeiPayment.BRANCH_NAMES.put(1, "中国工商银行股份有限公司上海市龙江路支行");
        XinBeiPayment.BRANCH_NAMES.put(2, "中国建设银行北京市分行营业部");
        XinBeiPayment.BRANCH_NAMES.put(3, "中国农业银行股份有限公司忻州和平分理处");
        XinBeiPayment.BRANCH_NAMES.put(4, "招商银行股份有限公司厦门金湖支行");
        XinBeiPayment.BRANCH_NAMES.put(5, "中国银行股份有限公司赣州市客家大道支行");
        XinBeiPayment.BRANCH_NAMES.put(6, "交通银行北京安翔里支行");
        XinBeiPayment.BRANCH_NAMES.put(7, "上海浦东发展银行安亭支行");
        XinBeiPayment.BRANCH_NAMES.put(8, "兴业银行北京安华支行");
        XinBeiPayment.BRANCH_NAMES.put(9, "中信银行北京安贞支行");
        XinBeiPayment.BRANCH_NAMES.put(10, "宁波银行股份有限公司北京东城支行");
        XinBeiPayment.BRANCH_NAMES.put(11, "上海银行股份有限公司北京安贞支行");
        XinBeiPayment.BRANCH_NAMES.put(12, "杭州银行股份有限公司上海北新泾支行");
        XinBeiPayment.BRANCH_NAMES.put(13, "渤海银行股份有限公司北京朝阳门支行");
        XinBeiPayment.BRANCH_NAMES.put(14, "浙商银行股份有限公司杭州滨江支行");
        XinBeiPayment.BRANCH_NAMES.put(15, "广发银行股份有限公司北京朝阳北路支行");
        XinBeiPayment.BRANCH_NAMES.put(16, "中国邮政储蓄银行股份有限公司北京昌平区北七家支行");
        XinBeiPayment.BRANCH_NAMES.put(17, "深圳发展银行");
        XinBeiPayment.BRANCH_NAMES.put(18, "中国民生银行股份有限公北京西大望路支行");
        XinBeiPayment.BRANCH_NAMES.put(19, "中国光大银行股份有限公司北京安定门支行");
        XinBeiPayment.BRANCH_NAMES.put(20, "华夏银行北京德外支行");
        XinBeiPayment.BRANCH_NAMES.put(21, "北京银行安定门支行");
        XinBeiPayment.BRANCH_NAMES.put(22, "南京银行股份有限公司北京车公庄支行");
        XinBeiPayment.BRANCH_NAMES.put(23, "平安银行股份有限公司北京北苑支行");
        XinBeiPayment.BRANCH_NAMES.put(24, "北京农村商业银行股份有限公司漷县支行");
    }
    
    public String daifu(final WebJSONObject json, final PaymentChannel channel, final double money, final String billno, final String name, final int bankId, final String card, final String branchName, final String returnUrl) {
        try {
            XinBeiPayment.log.debug("开始新贝网银代付,注单ID:{},姓名:{},卡号:{},分行:{},商户号：{}", new Object[] { billno, name, card, branchName, channel.getMerCode() });
            return this.daifuInternel(json, channel.getMerCode(), this.daifuWangyingPasswd, this.daifuWangyingTokenKey, money, billno, name, bankId, card, branchName, returnUrl);
        }
        catch (Exception e) {
            XinBeiPayment.log.error("新贝网银代付发生异常", (Throwable)e);
            json.set(2, "2-4000");
            return null;
        }
    }
    
    public String daifuWeChat(final WebJSONObject json, final PaymentChannel channel, final double money, final String billno, final String name, final int bankId, final String card, final String branchName, final String returnUrl) {
        try {
            XinBeiPayment.log.debug("开始新贝微信代付,注单ID:{},姓名:{},卡号:{},分行:{},商户号：{}", new Object[] { billno, name, card, branchName, channel.getMerCode() });
            return this.daifuInternel(json, channel.getMerCode(), this.daifuWeChatPasswd, this.daifuWeChatTokenKey, money, billno, name, bankId, card, branchName, returnUrl);
        }
        catch (Exception e) {
            XinBeiPayment.log.error("新贝微信代付发生异常", (Throwable)e);
            json.set(2, "2-4000");
            return null;
        }
    }
    
    public String daifuAlipay(final WebJSONObject json, final PaymentChannel channel, final double money, final String billno, final String name, final int bankId, final String card, final String branchName, final String returnUrl) {
        try {
            XinBeiPayment.log.debug("开始新贝支付宝代付,注单ID:{},姓名:{},卡号:{},分行:{},商户号：{}", new Object[] { billno, name, card, branchName, channel.getMerCode() });
            return this.daifuInternel(json, channel.getMerCode(), this.daifuAlipayPasswd, this.daifuAlipayTokenKey, money, billno, name, bankId, card, branchName, returnUrl);
        }
        catch (Exception e) {
            XinBeiPayment.log.error("新贝支付宝代付发生异常", (Throwable)e);
            json.set(2, "2-4000");
            return null;
        }
    }
    
    private String daifuInternel(final WebJSONObject json, final String merCode, final String passwd, final String tokenKey, final double money, final String billno, final String name, final int bankId, final String card, final String branchName, final String returnUrl) {
        try {
            if (bankId == 23) {
                json.set(2, "2-4012");
                return null;
            }
            String realBranchName = XinBeiPayment.BRANCH_NAMES.get(bankId);
            if (realBranchName == null) {
                realBranchName = branchName;
            }
            final long amount = MoneyFormat.yuanToFenMoney(new StringBuilder(String.valueOf(money)).toString());
            final String bankConfig = String.valueOf(realBranchName) + "|" + card + "|" + name + "|" + amount + "|1";
            String sign = String.valueOf(amount) + bankConfig + "1" + returnUrl + billno + passwd + "1" + "1" + tokenKey;
            sign = DigestUtils.md5Hex(sign).toUpperCase();
            final Map<String, String> dataMap = new LinkedHashMap<String, String>();
            dataMap.put("amount", new StringBuilder(String.valueOf(amount)).toString());
            dataMap.put("bank_config", bankConfig);
            dataMap.put("encrypt", "1");
            dataMap.put("PayPassWord", passwd);
            dataMap.put("settle_type", "1");
            dataMap.put("sign", sign);
            dataMap.put("UrgentType", "1");
            dataMap.put("MerchantOrder", billno);
            dataMap.put("MerchantNotifyUrl", returnUrl);
            String dataString = JSON.toJSONString((Object)dataMap);
            dataString = URLUtils.encode(dataString, "UTF-8");
            final Map<String, String> paramsMap = new LinkedHashMap<String, String>();
            paramsMap.put("data", dataString);
            paramsMap.put("Merchantaccount", merCode);
            paramsMap.put("Encryptkey", "1");
            String retStr;
            try {
                retStr = HttpClientUtil.post(this.daifuUrl, paramsMap, null, 5000);
            }
            catch (Exception e) {
                XinBeiPayment.log.error("新贝代付发生连接异常", (Throwable)e);
                retStr = null;
            }
            if (StringUtils.isEmpty(retStr)) {
                XinBeiPayment.log.error("新贝代付发生连接异常，开始查询订单状态：" + billno);
                final String xbOrderId = this.query(merCode, tokenKey, billno);
                if (StringUtils.isNotEmpty(xbOrderId)) {
                    XinBeiPayment.log.debug("新贝代付查询返回表示成功,我方订单号：{},新贝订单号：{}", (Object)billno, (Object)xbOrderId);
                    return xbOrderId;
                }
                XinBeiPayment.log.error("新贝代付查询返回表示不成功：我方订单号：" + billno);
                json.set(2, "2-4001");
                return null;
            }
            else {
                XinbeiDaifuResult result;
                try {
                    retStr = URLUtils.decode(retStr, "UTF-8");
                    result = (XinbeiDaifuResult)JSON.parseObject(retStr, (Class)XinbeiDaifuResult.class);
                }
                catch (Exception e2) {
                    XinBeiPayment.log.error("新贝代付请求失败，解析返回数据失败，返回数据为：" + retStr, (Throwable)e2);
                    json.setWithParams(2, "2-4007", StringUtils.abbreviate(retStr, 20));
                    return null;
                }
                if (result == null) {
                    XinBeiPayment.log.error("新贝代付请求失败，解析返回数据失败，返回数据为：" + retStr);
                    json.setWithParams(2, "2-4007", StringUtils.abbreviate(retStr, 20));
                    return null;
                }
                if ("1".equals(result.getStatus())) {
                    String serverSign = String.valueOf(result.getAmount()) + result.getBankConfig() + result.getMsg() + result.getMsgCode() + result.getSerialNo() + result.getSettleType() + result.getStatus() + tokenKey;
                    serverSign = DigestUtils.md5Hex(serverSign).toUpperCase();
                    if (!StringUtils.equalsIgnoreCase(serverSign, result.getSin())) {
                        XinBeiPayment.log.error("新贝代付请求成功,但返回数据签名与服务器签名不匹配,返回数据为：" + retStr + ",服务器签名:" + serverSign);
                        json.setWithParams(2, "2-4008", new Object[0]);
                        return result.getSerialNo();
                    }
                    if (StringUtils.isNotEmpty(result.getSerialNo())) {
                        XinBeiPayment.log.debug("新贝代付请求返回订单号：{}", (Object)result.getSerialNo());
                        return result.getSerialNo();
                    }
                    XinBeiPayment.log.error("新贝代付返回订单ID为空,我方订单ID:" + billno);
                    json.setWithParams(2, "2-4014", new Object[0]);
                    return null;
                }
                else {
                    if (!"申请提现失败".equals(result.getMsg())) {
                        XinBeiPayment.log.error("新贝代付请求失败,返回数据为：" + retStr + ",我方订单ID:" + billno);
                        json.setWithParams(2, "2-4002", result.getMsg());
                        return null;
                    }
                    XinBeiPayment.log.debug("新贝代付请求返回提示：{}，开始查询订单状态：{}", (Object)result.getMsg(), (Object)billno);
                    final String xbOrderId2 = this.query(merCode, tokenKey, billno);
                    if (StringUtils.isNotEmpty(xbOrderId2)) {
                        XinBeiPayment.log.debug("新贝代付查询返回表示成功,我方订单号：{},新贝订单号：{}", (Object)billno, (Object)xbOrderId2);
                        return xbOrderId2;
                    }
                    XinBeiPayment.log.error("新贝代付请求失败,返回数据为：" + retStr + ",我方订单ID:" + billno);
                    json.setWithParams(2, "2-4002", result.getMsg());
                    return null;
                }
            }
        }
        catch (Exception e3) {
            XinBeiPayment.log.error("新贝代付失败,发生异常", (Throwable)e3);
            json.set(2, "2-4000");
            return null;
        }
    }
    
    private String query(final String merCode, final String tokenKey, final String billno) {
        try {
            String sign = "1" + merCode + billno + tokenKey;
            sign = DigestUtils.md5Hex(sign).toUpperCase();
            final Map<String, String> dataMap = new LinkedHashMap<String, String>();
            dataMap.put("Encrypt", "1");
            dataMap.put("MerchantCode", merCode);
            dataMap.put("OrderId", billno);
            dataMap.put("sign", sign);
            String dataString = JSON.toJSONString((Object)dataMap);
            dataString = URLUtils.encode(dataString, "UTF-8");
            final Map<String, String> paramsMap = new LinkedHashMap<String, String>();
            paramsMap.put("data", dataString);
            paramsMap.put("Merchantaccount", merCode);
            paramsMap.put("Encryptkey", "1");
            String retStr;
            try {
                retStr = HttpClientUtil.post(this.daifuQueryUrl, paramsMap, null, 5000);
            }
            catch (Exception e) {
                XinBeiPayment.log.error("新贝代付查询请求失败，发生连接异常", (Throwable)e);
                return null;
            }
            if (StringUtils.isEmpty(retStr)) {
                XinBeiPayment.log.error("新贝代付查询请求失败，发送请求后返回空数据");
                return null;
            }
            XinbeiDaifuQueryResult result;
            try {
                retStr = URLUtils.decode(retStr, "UTF-8");
                XinBeiPayment.log.debug("新贝查询返回数据：{}", (Object)retStr);
                retStr = retStr.substring(9, retStr.length() - 2);
                result = (XinbeiDaifuQueryResult)JSON.parseObject(retStr, (Class)XinbeiDaifuQueryResult.class);
            }
            catch (Exception e2) {
                XinBeiPayment.log.error("新贝代付查询请求失败，解析返回数据失败，返回数据为：" + retStr, (Throwable)e2);
                return null;
            }
            if (result == null) {
                XinBeiPayment.log.error("新贝代付查询请求失败，解析返回数据失败，返回数据为：" + retStr);
                return null;
            }
            if ("00".equals(result.getStatus()) || ("50".equals(result.getStatus()) && StringUtils.isNotEmpty(result.getXbeiOrderId()))) {
                XinBeiPayment.log.debug("新贝代付查询请求成功，返回状态表示成功");
                return result.getXbeiOrderId();
            }
            return null;
        }
        catch (Exception e3) {
            XinBeiPayment.log.error("新贝代付查询请求失败,发生异常", (Throwable)e3);
            return null;
        }
    }
}
