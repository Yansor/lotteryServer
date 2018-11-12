package lottery.domains.content.payment.yr;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import javautils.http.HttpClientUtil;
import org.apache.commons.codec.digest.DigestUtils;
import lottery.domains.content.payment.utils.UrlParamUtils;
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
public class YRPayment extends AbstractPayment implements InitializingBean
{
    @Autowired
    private ServletContext servletContext;
    private static Map<Integer, String> BRANCH_NAMES;
    @Value("${yr.daifu.url}")
    private String daifuUrl;
    @Value("${yr.daifu.queryurl}")
    private String daifuQueryUrl;
    
    static {
        YRPayment.BRANCH_NAMES = new HashMap<Integer, String>();
    }
    
    public void afterPropertiesSet() throws Exception {
        YRPayment.BRANCH_NAMES.put(1, "中国工商银行股份有限公司上海市龙江路支行");
        YRPayment.BRANCH_NAMES.put(2, "中国建设银行北京市分行营业部");
        YRPayment.BRANCH_NAMES.put(3, "中国农业银行股份有限公司忻州和平分理处");
        YRPayment.BRANCH_NAMES.put(4, "招商银行股份有限公司厦门金湖支行");
        YRPayment.BRANCH_NAMES.put(5, "中国银行股份有限公司赣州市客家大道支行");
        YRPayment.BRANCH_NAMES.put(6, "交通银行北京安翔里支行");
        YRPayment.BRANCH_NAMES.put(7, "上海浦东发展银行安亭支行");
        YRPayment.BRANCH_NAMES.put(8, "兴业银行北京安华支行");
        YRPayment.BRANCH_NAMES.put(9, "中信银行北京安贞支行");
        YRPayment.BRANCH_NAMES.put(10, "宁波银行股份有限公司北京东城支行");
        YRPayment.BRANCH_NAMES.put(11, "上海银行股份有限公司北京安贞支行");
        YRPayment.BRANCH_NAMES.put(12, "杭州银行股份有限公司上海北新泾支行");
        YRPayment.BRANCH_NAMES.put(13, "渤海银行股份有限公司北京朝阳门支行");
        YRPayment.BRANCH_NAMES.put(14, "浙商银行股份有限公司杭州滨江支行");
        YRPayment.BRANCH_NAMES.put(15, "广发银行股份有限公司北京朝阳北路支行");
        YRPayment.BRANCH_NAMES.put(16, "中国邮政储蓄银行股份有限公司北京昌平区北七家支行");
        YRPayment.BRANCH_NAMES.put(17, "深圳发展银行");
        YRPayment.BRANCH_NAMES.put(18, "中国民生银行股份有限公北京西大望路支行");
        YRPayment.BRANCH_NAMES.put(19, "中国光大银行股份有限公司北京安定门支行");
        YRPayment.BRANCH_NAMES.put(20, "华夏银行北京德外支行");
        YRPayment.BRANCH_NAMES.put(21, "北京银行安定门支行");
        YRPayment.BRANCH_NAMES.put(22, "南京银行股份有限公司北京车公庄支行");
        YRPayment.BRANCH_NAMES.put(23, "平安银行股份有限公司北京北苑支行");
        YRPayment.BRANCH_NAMES.put(24, "北京农村商业银行股份有限公司漷县支行");
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
            final Map<String, String> paramsMap = new HashMap<String, String>();
            paramsMap.put("payKey", channel.getMerCode());
            paramsMap.put("outTradeNo", order.getBillno());
            paramsMap.put("orderPrice", new StringBuilder(String.valueOf(order.getRecMoney())).toString());
            final String pcdnDomain = (String)this.servletContext.getAttribute("pcdnDomain");
            paramsMap.put("proxyType", "T0");
            paramsMap.put("productType", "B2CPAY");
            paramsMap.put("bankAccountType", "PRIVATE_DEBIT_ACCOUNT");
            paramsMap.put("receiverAccountNo", order.getCardId());
            paramsMap.put("receiverName", order.getCardName());
            final String branchName = YRPayment.BRANCH_NAMES.get(card.getBankId());
            paramsMap.put("bankBranchNo", branchName);
            paramsMap.put("bankName", order.getBankName());
            final String signStr = String.valueOf(UrlParamUtils.toUrlParamWithoutEmpty(paramsMap, "&", true)) + "&paySecret=" + channel.getMd5Key();
            final String sign = DigestUtils.md5Hex(signStr).toUpperCase();
            paramsMap.put("sign", sign);
            final String retStr = HttpClientUtil.post(this.daifuUrl, paramsMap, null, 10000);
            if (StringUtils.isEmpty(retStr)) {
                this.logError(order, bank, channel, "接口返回空，可能是请求超时");
                json.set(-1, "-1");
                return null;
            }
            final YRDaifuResult result = (YRDaifuResult)JSON.parseObject(retStr, (Class)YRDaifuResult.class);
            if (result == null) {
                this.logError(order, bank, channel, "请求失败，解析返回数据失败，返回数据为：" + retStr);
                json.setWithParams(2, "2-4007", StringUtils.abbreviate(retStr, 20));
                return null;
            }
            if ("0000".equals(result.getResultCode())) {
                if (StringUtils.isEmpty(result.getOutOrderId())) {
                    this.logError(order, bank, channel, "请求失败，返回第三方注单号为空，返回数据为：" + retStr);
                    json.setWithParams(2, "2-4007", StringUtils.abbreviate(retStr, 20));
                    return null;
                }
                this.logSuccess(order, result.getOutOrderId(), channel);
                return result.getOutOrderId();
            }
            else {
                this.logError(order, bank, channel, "请求返回错误消息，返回数据：" + retStr + "，开始查询订单状态");
                final YRDaifuQueryResult queryResult = this.query(order, channel);
                if (this.isSuccessRequested(queryResult)) {
                    this.logSuccess(order, queryResult.getOutTradeNo(), channel);
                    return queryResult.getOutTradeNo();
                }
                this.logError(order, bank, channel, "请求失败，返回数据为：" + retStr);
                json.setWithParams(2, "2-4002", result.getErrMsg());
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
    
    public YRDaifuQueryResult query(final UserWithdraw order, final PaymentChannel channel) {
        try {
            final String outOrderId = order.getBillno();
            final String merchantCode = channel.getMerCode();
            final Map<String, String> paramsMap = new HashMap<String, String>();
            paramsMap.put("payKey ", merchantCode);
            paramsMap.put("outTradeNo", outOrderId);
            final String signStr = String.valueOf(UrlParamUtils.toUrlParamWithoutEmpty(paramsMap, "&", true)) + "&paySecret=" + channel.getMd5Key();
            final String sign = DigestUtils.md5Hex(signStr);
            paramsMap.put("sign", sign.toUpperCase());
            final String retStr = HttpClientUtil.post(this.daifuQueryUrl, paramsMap, null, 10000);
            if (StringUtils.isEmpty(retStr)) {
                this.logError(order, null, channel, "查询请求失败，发送请求后返回空数据");
                return null;
            }
            this.logInfo(order, null, channel, "查询返回数据：" + retStr);
            YRDaifuQueryResult result = null;
            try {
                result = (YRDaifuQueryResult)JSON.parseObject(retStr, (Class)YRDaifuQueryResult.class);
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
    
    public boolean isSuccessRequested(final YRDaifuQueryResult queryResult) {
        return queryResult != null && !StringUtils.isEmpty(queryResult.getOutTradeNo()) && "0000".equalsIgnoreCase(queryResult.getResultCode());
    }
    
    public int transferBankStatus(final String bankStatus) {
        int remitStatus = -4;
        switch (bankStatus) {
            case "REMIT_SUCCESS": {
                remitStatus = 2;
                break;
            }
            case "REMITTING": {
                remitStatus = 1;
                break;
            }
            case "REMIT_FAIL": {
                remitStatus = -2;
                break;
            }
            default:
                break;
        }
        return remitStatus;
    }
}
