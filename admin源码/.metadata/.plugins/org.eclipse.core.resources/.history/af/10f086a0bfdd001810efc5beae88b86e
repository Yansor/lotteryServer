package lottery.domains.content.payment.tgf;

import org.apache.commons.lang.StringUtils;
import lottery.domains.content.payment.tgf.utils.QueryResponseEntity;
import lottery.domains.content.payment.tgf.utils.SignUtil;
import lottery.domains.content.payment.tgf.utils.Merchant;
import java.text.SimpleDateFormat;
import java.util.Date;
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
public class TGFPayment extends AbstractPayment implements InitializingBean
{
    @Autowired
    private ServletContext servletContext;
    private static Map<Integer, String> BRANCH_NAMES;
    public static final String NONE_NOTIFY_URL = "http://www.baidu.com";
    @Value("${af.daifu.url}")
    private String daifuUrl;
    @Value("${af.daifu.queryurl}")
    private String daifuQueryUrl;
    
    static {
        TGFPayment.BRANCH_NAMES = new HashMap<Integer, String>();
    }
    
    public void afterPropertiesSet() throws Exception {
        TGFPayment.BRANCH_NAMES.put(1, "中国工商银行股份有限公司上海市龙江路支行");
        TGFPayment.BRANCH_NAMES.put(2, "中国建设银行北京市分行营业部");
        TGFPayment.BRANCH_NAMES.put(3, "中国农业银行股份有限公司忻州和平分理处");
        TGFPayment.BRANCH_NAMES.put(4, "招商银行股份有限公司厦门金湖支行");
        TGFPayment.BRANCH_NAMES.put(5, "中国银行股份有限公司赣州市客家大道支行");
        TGFPayment.BRANCH_NAMES.put(6, "交通银行北京安翔里支行");
        TGFPayment.BRANCH_NAMES.put(7, "上海浦东发展银行安亭支行");
        TGFPayment.BRANCH_NAMES.put(8, "兴业银行北京安华支行");
        TGFPayment.BRANCH_NAMES.put(9, "中信银行北京安贞支行");
        TGFPayment.BRANCH_NAMES.put(10, "宁波银行股份有限公司北京东城支行");
        TGFPayment.BRANCH_NAMES.put(11, "上海银行股份有限公司北京安贞支行");
        TGFPayment.BRANCH_NAMES.put(12, "杭州银行股份有限公司上海北新泾支行");
        TGFPayment.BRANCH_NAMES.put(13, "渤海银行股份有限公司北京朝阳门支行");
        TGFPayment.BRANCH_NAMES.put(14, "浙商银行股份有限公司杭州滨江支行");
        TGFPayment.BRANCH_NAMES.put(15, "广发银行股份有限公司北京朝阳北路支行");
        TGFPayment.BRANCH_NAMES.put(16, "中国邮政储蓄银行股份有限公司北京昌平区北七家支行");
        TGFPayment.BRANCH_NAMES.put(17, "深圳发展银行");
        TGFPayment.BRANCH_NAMES.put(18, "中国民生银行股份有限公北京西大望路支行");
        TGFPayment.BRANCH_NAMES.put(19, "中国光大银行股份有限公司北京安定门支行");
        TGFPayment.BRANCH_NAMES.put(20, "华夏银行北京德外支行");
        TGFPayment.BRANCH_NAMES.put(21, "北京银行安定门支行");
        TGFPayment.BRANCH_NAMES.put(22, "南京银行股份有限公司北京车公庄支行");
        TGFPayment.BRANCH_NAMES.put(23, "平安银行股份有限公司北京北苑支行");
        TGFPayment.BRANCH_NAMES.put(24, "北京农村商业银行股份有限公司漷县支行");
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
            final Map<String, String> paramsMap = new HashMap<String, String>();
            paramsMap.put("service", "TRADE.SETTLE");
            paramsMap.put("version", "1.0.0.0");
            paramsMap.put("merId", channel.getMerCode());
            paramsMap.put("tradeNo", order.getBillno());
            final Date d = new Date();
            final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            paramsMap.put("tradeDate", sdf.format(d));
            paramsMap.put("amount", new StringBuilder(String.valueOf(order.getRecMoney())).toString());
            paramsMap.put("notifyUrl", "http://www.baidu.com");
            paramsMap.put("extra", order.getBillno());
            paramsMap.put("summary", order.getBillno());
            paramsMap.put("bankCardNo", order.getCardId());
            paramsMap.put("bankCardName", order.getCardName());
            paramsMap.put("bankId", bank.getCode());
            paramsMap.put("bankName", TGFPayment.BRANCH_NAMES.get(card.getBankId()));
            paramsMap.put("purpose", "");
            String paramsStr = Merchant.generateSingleSettRequest(paramsMap);
            final String signMsg = SignUtil.signByMD5(paramsStr, channel.getMd5Key());
            paramsStr = String.valueOf(paramsStr) + "&sign=" + signMsg;
            final String payGateUrl = channel.getPayUrl();
            final String responseMsg = Merchant.transact(paramsStr, payGateUrl);
            System.out.println(responseMsg);
            final QueryResponseEntity entity = new QueryResponseEntity();
            entity.parse(responseMsg, channel.getMd5Key());
            if ("00".equals(entity.getRespCode())) {
                this.logSuccess(order, order.getBillno(), channel);
                return order.getBillno();
            }
            this.logError(order, bank, channel, "请求返回错误消息，返回数据：" + responseMsg + "，开始查询订单状态");
            final QueryResponseEntity queryResult = this.query(order, channel);
            if (this.isSuccessRequested(queryResult)) {
                this.logSuccess(order, order.getBillno(), channel);
                return order.getBillno();
            }
            this.logError(order, bank, channel, "请求失败，返回数据为：" + responseMsg);
            json.setWithParams(2, "2-4002", entity.getRespDesc());
            return null;
        }
        catch (Exception e) {
            this.logException(order, bank, channel, "代付请求失败", e);
            json.set(-1, "-1");
            return null;
        }
    }
    
    public static void main(final String[] args) {
    }
    
    public QueryResponseEntity query(final UserWithdraw order, final PaymentChannel channel) {
        try {
            final Map<String, String> paramsMap = new HashMap<String, String>();
            paramsMap.put("service", "TRADE.SETTLE.QUERY");
            paramsMap.put("version", "1.0.0.0");
            paramsMap.put("merId", channel.getMerCode());
            paramsMap.put("tradeNo", order.getBillno());
            final Date d = new Date();
            final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            paramsMap.put("tradeDate", sdf.format(d));
            String paramsStr = Merchant.generateSingleSettQueryRequest(paramsMap);
            final String signMsg = SignUtil.signByMD5(paramsStr, channel.getMd5Key());
            paramsStr = String.valueOf(paramsStr) + "&sign=" + signMsg;
            final String payGateUrl = channel.getPayUrl();
            System.out.println(paramsStr);
            final String responseMsg = Merchant.transact(paramsStr, payGateUrl);
            System.out.println(responseMsg);
            final QueryResponseEntity entity = new QueryResponseEntity();
            entity.parse(responseMsg, channel.getMd5Key());
            return entity;
        }
        catch (Exception e) {
            this.logException(order, null, channel, "查询请求失败", e);
            return null;
        }
    }
    
    public boolean isSuccessRequested(final QueryResponseEntity queryResult) {
        return !StringUtils.isEmpty(queryResult.getRespCode()) && ("00".equalsIgnoreCase(queryResult.getRespCode()) || "1".equalsIgnoreCase(queryResult.getStatus()));
    }
    
    public int transferBankStatus(final String bankStatus) {
        int remitStatus = -4;
        switch (bankStatus) {
            case "1": {
                remitStatus = 2;
                break;
            }
            case "2": {
                remitStatus = -2;
                break;
            }
            case "5": {
                remitStatus = 1;
                break;
            }
            default:
                break;
        }
        return remitStatus;
    }
}
