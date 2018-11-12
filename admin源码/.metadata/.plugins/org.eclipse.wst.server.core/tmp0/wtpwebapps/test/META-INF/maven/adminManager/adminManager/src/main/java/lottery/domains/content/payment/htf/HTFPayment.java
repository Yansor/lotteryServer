package lottery.domains.content.payment.htf;

import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;
import java.io.Reader;
import org.xml.sax.InputSource;
import java.io.StringReader;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.commons.lang.StringUtils;
import javautils.http.HttpClientUtil;
import org.apache.commons.codec.digest.DigestUtils;
import java.util.TreeMap;
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
public class HTFPayment extends AbstractPayment implements InitializingBean
{
    private static final String INPUT_CHARSET = "UTF-8";
    private static final String REMARK = "df";
    private static final String VERSION = "2";
    private static final String NOTIFY_URL = "http://www.yyy.com";
    private static final String DEFAULT_PROVICE = "北京市";
    private static final String DEFAULT_CITY = "北京市";
    private static Map<Integer, String> BANK_CODES;
    private static Map<Integer, String> BRANCH_NAMES;
    @Value("${htf.daifu.url}")
    private String daifuUrl;
    @Value("${htf.daifu.queryurl}")
    private String queryUrl;
    
    static {
        HTFPayment.BANK_CODES = new HashMap<Integer, String>();
        HTFPayment.BRANCH_NAMES = new HashMap<Integer, String>();
    }
    
    public void afterPropertiesSet() throws Exception {
        HTFPayment.BRANCH_NAMES.put(1, "中国工商银行股份有限公司上海市龙江路支行");
        HTFPayment.BRANCH_NAMES.put(2, "中国建设银行北京市分行营业部");
        HTFPayment.BRANCH_NAMES.put(3, "中国农业银行股份有限公司忻州和平分理处");
        HTFPayment.BRANCH_NAMES.put(4, "招商银行股份有限公司厦门金湖支行");
        HTFPayment.BRANCH_NAMES.put(5, "中国银行股份有限公司赣州市客家大道支行");
        HTFPayment.BRANCH_NAMES.put(6, "交通银行北京安翔里支行");
        HTFPayment.BRANCH_NAMES.put(7, "上海浦东发展银行安亭支行");
        HTFPayment.BRANCH_NAMES.put(8, "兴业银行北京安华支行");
        HTFPayment.BRANCH_NAMES.put(9, "中信银行北京安贞支行");
        HTFPayment.BRANCH_NAMES.put(10, "宁波银行股份有限公司北京东城支行");
        HTFPayment.BRANCH_NAMES.put(11, "上海银行股份有限公司北京安贞支行");
        HTFPayment.BRANCH_NAMES.put(12, "杭州银行股份有限公司上海北新泾支行");
        HTFPayment.BRANCH_NAMES.put(13, "渤海银行股份有限公司北京朝阳门支行");
        HTFPayment.BRANCH_NAMES.put(14, "浙商银行股份有限公司杭州滨江支行");
        HTFPayment.BRANCH_NAMES.put(15, "广发银行股份有限公司北京朝阳北路支行");
        HTFPayment.BRANCH_NAMES.put(16, "中国邮政储蓄银行股份有限公司北京昌平区北七家支行");
        HTFPayment.BRANCH_NAMES.put(17, "深圳发展银行");
        HTFPayment.BRANCH_NAMES.put(18, "中国民生银行股份有限公北京西大望路支行");
        HTFPayment.BRANCH_NAMES.put(19, "中国光大银行股份有限公司北京安定门支行");
        HTFPayment.BRANCH_NAMES.put(20, "华夏银行北京德外支行");
        HTFPayment.BRANCH_NAMES.put(21, "北京银行安定门支行");
        HTFPayment.BRANCH_NAMES.put(22, "南京银行股份有限公司北京车公庄支行");
        HTFPayment.BRANCH_NAMES.put(23, "平安银行股份有限公司北京北苑支行");
        HTFPayment.BRANCH_NAMES.put(24, "北京农村商业银行股份有限公司漷县支行");
        HTFPayment.BANK_CODES.put(1, "1");
        HTFPayment.BANK_CODES.put(2, "2");
        HTFPayment.BANK_CODES.put(3, "3");
        HTFPayment.BANK_CODES.put(4, "7");
        HTFPayment.BANK_CODES.put(5, "5");
        HTFPayment.BANK_CODES.put(6, "6");
        HTFPayment.BANK_CODES.put(7, "9");
        HTFPayment.BANK_CODES.put(8, "13");
        HTFPayment.BANK_CODES.put(9, "12");
        HTFPayment.BANK_CODES.put(10, "17");
        HTFPayment.BANK_CODES.put(11, "16");
        HTFPayment.BANK_CODES.put(12, "15");
        HTFPayment.BANK_CODES.put(15, "11");
        HTFPayment.BANK_CODES.put(16, "4");
        HTFPayment.BANK_CODES.put(18, "14");
        HTFPayment.BANK_CODES.put(19, "8");
        HTFPayment.BANK_CODES.put(20, "10");
        HTFPayment.BANK_CODES.put(23, "18");
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
            final String bankCode = HTFPayment.BANK_CODES.get(card.getBankId());
            if (StringUtil.isEmpty(bankCode)) {
                json.set(2, "2-4012");
                return null;
            }
            final String branchName = HTFPayment.BRANCH_NAMES.get(card.getBankId());
            if (StringUtil.isEmpty(branchName)) {
                json.set(2, "2-4012");
                return null;
            }
            final String amount = MoneyFormat.moneyToYuanForPositive(new StringBuilder(String.valueOf(order.getRecMoney())).toString());
            final String version = "2";
            final String agent_id = channel.getMerCode();
            final String batch_no = order.getBillno();
            final String batch_amt = amount;
            final String batch_num = "1";
            final String notify_url = "http://www.yyy.com";
            final String ext_param1 = order.getBillno();
            final Map<String, String> paramsMap = new TreeMap<String, String>();
            paramsMap.put("version", version);
            paramsMap.put("agent_id", agent_id);
            paramsMap.put("batch_no", batch_no);
            paramsMap.put("batch_amt", batch_amt);
            paramsMap.put("batch_num", batch_num);
            String detail_data = "%s^%s^%s^%s^%s^%s^%s^%s^%s^%s";
            detail_data = String.format(detail_data, order.getBillno(), bankCode, "0", order.getCardId(), order.getCardName(), amount, "df", "北京市", "北京市", branchName);
            paramsMap.put("detail_data", detail_data);
            paramsMap.put("notify_url", notify_url);
            paramsMap.put("ext_param1", ext_param1);
            String signStr = "agent_id=%s&batch_amt=%s&batch_no=%s&batch_num=%s&detail_data=%s&ext_param1=%s&key=%s&notify_url=%s&version=%s";
            signStr = String.format(signStr, agent_id, batch_amt, batch_no, batch_num, detail_data, ext_param1, channel.getExt1(), notify_url, version);
            signStr = signStr.toLowerCase();
            final String sign = DigestUtils.md5Hex(signStr).toLowerCase();
            paramsMap.put("sign", sign);
            final String url = String.valueOf(this.daifuUrl) + "?_=" + System.currentTimeMillis();
            final String retStr = HttpClientUtil.post(url, paramsMap, null, 60000);
            if (StringUtils.isEmpty(retStr)) {
                this.logError(order, bank, channel, "接口返回空，可能是请求超时");
                json.set(-1, "-1");
                return null;
            }
            final HTFPayResult result = this.transferPayResult(order, channel, retStr);
            if (result == null) {
                this.logError(order, bank, channel, "请求失败，解析返回数据失败，返回数据为：" + retStr);
                json.setWithParams(2, "2-4007", StringUtils.abbreviate(retStr, 20));
                return null;
            }
            if (this.isAcceptedRequest(result.getRetCode())) {
                this.logSuccess(order, order.getBillno(), channel);
                return order.getBillno();
            }
            if (!StringUtils.isEmpty(result.getRetMsg())) {
                this.logError(order, bank, channel, "请求失败，返回数据为：" + retStr);
                json.setWithParams(2, "2-4002", result.getRetMsg());
                return null;
            }
            final String msg = "未知错误";
            this.logError(order, bank, channel, "请求返回空的错误消息，返回数据：" + retStr + "，开始查询订单状态");
            final HTFPayQueryResult queryResult = this.query(order, channel);
            if (this.isAcceptedRequest(queryResult.getRetCode()) && StringUtils.isNotEmpty(queryResult.getHyBillNo())) {
                this.logSuccess(order, queryResult.getHyBillNo(), channel);
                return queryResult.getHyBillNo();
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
    
    public HTFPayQueryResult query(final UserWithdraw order, final PaymentChannel channel) {
        try {
            final Map<String, String> paramsMap = new TreeMap<String, String>();
            final String version = "2";
            final String agent_id = channel.getMerCode();
            final String batch_no = order.getBillno();
            paramsMap.put("version", version);
            paramsMap.put("agent_id", agent_id);
            paramsMap.put("batch_no", batch_no);
            String signStr = "agent_id=%s&batch_no=%s&key=%s&version=%s";
            signStr = String.format(signStr, agent_id, batch_no, channel.getExt1(), version);
            signStr = signStr.toLowerCase();
            final String sign = DigestUtils.md5Hex(signStr).toLowerCase();
            paramsMap.put("sign", sign);
            final String url = String.valueOf(this.queryUrl) + "?_=" + System.currentTimeMillis();
            final String retStr = HttpClientUtil.post(url, paramsMap, null, 60000);
            if (StringUtils.isEmpty(retStr)) {
                this.logError(order, null, channel, "查询请求失败，发送请求后返回空数据");
                return null;
            }
            this.logInfo(order, null, channel, "查询返回数据：" + retStr);
            final HTFPayQueryResult queryResult = this.transferPayQueryResult(order, channel, retStr);
            if (queryResult == null) {
                this.logError(order, null, channel, "查询请求失败，解析返回数据失败");
                return null;
            }
            return queryResult;
        }
        catch (Exception e) {
            this.logException(order, null, channel, "查询请求失败", e);
            return null;
        }
    }
    
    public boolean isAcceptedRequest(final String retCode) {
        return "0000".equalsIgnoreCase(retCode);
    }
    
    public int transferBankStatus(final String detailData) {
        final String[] datas = detailData.split("\\^");
        if (datas == null || datas.length < 5) {
            return -4;
        }
        final String bankStatus = datas[4];
        if ("S".equalsIgnoreCase(bankStatus)) {
            return 2;
        }
        if ("F".equalsIgnoreCase(bankStatus)) {
            return -5;
        }
        if ("P".equalsIgnoreCase(bankStatus)) {
            return 1;
        }
        return -4;
    }
    
    private HTFPayResult transferPayResult(final UserWithdraw order, final PaymentChannel channel, final String xml) {
        try {
            final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            final DocumentBuilder builder = factory.newDocumentBuilder();
            final Document doc = builder.parse(new InputSource(new StringReader(xml)));
            final Node rootChild = doc.getFirstChild();
            if (rootChild == null) {
                return null;
            }
            final HTFPayResult result = new HTFPayResult();
            final NodeList childNodes = rootChild.getChildNodes();
            for (int length = childNodes.getLength(), i = 0; i < length; ++i) {
                final Node item = childNodes.item(i);
                final String nodeName = item.getNodeName();
                final String textContent = item.getTextContent();
                if ("ret_code".equalsIgnoreCase(nodeName)) {
                    result.setRetCode(textContent);
                }
                else if ("ret_msg".equalsIgnoreCase(nodeName)) {
                    result.setRetMsg(textContent);
                }
            }
            return result;
        }
        catch (Exception e) {
            this.logException(order, null, channel, "转换支付结果XML异常", e);
            return null;
        }
    }
    
    private HTFPayQueryResult transferPayQueryResult(final UserWithdraw order, final PaymentChannel channel, final String xml) {
        try {
            final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            final DocumentBuilder builder = factory.newDocumentBuilder();
            final Document doc = builder.parse(new InputSource(new StringReader(xml)));
            final Node rootChild = doc.getFirstChild();
            if (rootChild == null) {
                return null;
            }
            final HTFPayQueryResult queryResult = new HTFPayQueryResult();
            final NodeList childNodes = rootChild.getChildNodes();
            for (int length = childNodes.getLength(), i = 0; i < length; ++i) {
                final Node item = childNodes.item(i);
                final String nodeName = item.getNodeName();
                final String textContent = item.getTextContent();
                if ("ret_code".equalsIgnoreCase(nodeName)) {
                    queryResult.setRetCode(textContent);
                }
                else if ("ret_msg".equalsIgnoreCase(nodeName)) {
                    queryResult.setRetMsg(textContent);
                }
                else if ("agent_id".equalsIgnoreCase(nodeName)) {
                    queryResult.setAgentId(textContent);
                }
                else if ("hy_bill_no".equalsIgnoreCase(nodeName)) {
                    queryResult.setHyBillNo(textContent);
                }
                else if ("batch_no".equalsIgnoreCase(nodeName)) {
                    queryResult.setBatchNo(textContent);
                }
                else if ("batch_amt".equalsIgnoreCase(nodeName)) {
                    queryResult.setBatchAmt(textContent);
                }
                else if ("batch_num".equalsIgnoreCase(nodeName)) {
                    queryResult.setBatchNum(textContent);
                }
                else if ("detail_data".equalsIgnoreCase(nodeName)) {
                    queryResult.setDetailData(textContent);
                }
                else if ("ext_param1".equalsIgnoreCase(nodeName)) {
                    queryResult.setExtParam1(textContent);
                }
                else if ("sign".equalsIgnoreCase(nodeName)) {
                    queryResult.setSign(textContent);
                }
            }
            return queryResult;
        }
        catch (Exception e) {
            this.logException(order, null, channel, "转换支付结果XML异常", e);
            return null;
        }
    }
    
    public static void main(final String[] args) {
    }
}
