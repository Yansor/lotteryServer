package lottery.domains.content.payment.tgf.utils;

import org.dom4j.Element;
import org.dom4j.Document;
import org.apache.commons.lang.StringUtils;
import org.dom4j.DocumentHelper;
import java.util.HashMap;
import java.util.Map;

public class QueryResponseEntity
{
    protected String respCode;
    protected String respDesc;
    protected String accDate;
    protected String accNo;
    protected String orderNo;
    protected String status;
    protected String signMsg;
    private static Map<String, String> ORDER_STATUS;
    
    static {
        (QueryResponseEntity.ORDER_STATUS = new HashMap<String, String>()).put("0", "未支付");
        QueryResponseEntity.ORDER_STATUS.put("1", "成功");
        QueryResponseEntity.ORDER_STATUS.put("2", "失败");
        QueryResponseEntity.ORDER_STATUS.put("4", "部分退款");
        QueryResponseEntity.ORDER_STATUS.put("5", "全额退款");
        QueryResponseEntity.ORDER_STATUS.put("9", "退款处理中");
        QueryResponseEntity.ORDER_STATUS.put("10", "未支付");
        QueryResponseEntity.ORDER_STATUS.put("11", "订单过期");
    }
    
    public String getRespCode() {
        return this.respCode;
    }
    
    public void setRespCode(final String respCode) {
        this.respCode = respCode;
    }
    
    public String getRespDesc() {
        return this.respDesc;
    }
    
    public void setRespDesc(final String respDesc) {
        this.respDesc = respDesc;
    }
    
    public String getAccDate() {
        return this.accDate;
    }
    
    public void setAccDate(final String accDate) {
        this.accDate = accDate;
    }
    
    public String getAccNo() {
        return this.accNo;
    }
    
    public void setAccNo(final String accNo) {
        this.accNo = accNo;
    }
    
    public String getOrderNo() {
        return this.orderNo;
    }
    
    public void setOrderNo(final String orderNo) {
        this.orderNo = orderNo;
    }
    
    public String getStatus() {
        return this.status;
    }
    
    public void setStatus(final String status) {
        this.status = status;
    }
    
    public String getSignMsg() {
        return this.signMsg;
    }
    
    public void setSignMsg(final String signMsg) {
        this.signMsg = signMsg;
    }
    
    public void parse(final String respStr, final String mk5key) throws Exception {
        final Map<String, String> resultMap = new HashMap<String, String>();
        XMLParserUtil.parse(respStr, resultMap);
        final Document doc = DocumentHelper.parseText(respStr);
        final Element root = doc.getRootElement();
        final Element respData = root.element("detail");
        final String srcData = respData.asXML();
        this.respCode = resultMap.get("/message/detail/code");
        if (StringUtils.isBlank(this.respCode)) {
            throw new Exception("响应信息格式错误：不存在'code'节点。");
        }
        this.respDesc = resultMap.get("/message/detail/desc");
        if (StringUtils.isBlank(this.respDesc)) {
            throw new Exception("响应信息格式错误：不存在'desc'节点");
        }
        if ("00".equalsIgnoreCase(this.respCode)) {
            this.accDate = resultMap.get("/message/detail/opeDate");
            if (StringUtils.isBlank(this.accDate)) {
                throw new Exception("响应信息格式错误：不存在'opeDate'节点。");
            }
            this.accNo = resultMap.get("/message/detail/opeNo");
            if (StringUtils.isBlank(this.accNo)) {
                throw new Exception("响应信息格式错误：不存在'opeNo'节点。");
            }
            this.orderNo = resultMap.get("/message/detail/tradeNo");
            this.status = resultMap.get("/message/detail/status");
        }
        this.signMsg = resultMap.get("/message/sign");
        if (StringUtils.isBlank(this.signMsg)) {
            throw new Exception("响应信息格式错误：不存在'sign'节点");
        }
        if (!this.getSignMsg().equalsIgnoreCase(SignUtil.signByMD5(srcData, mk5key))) {
            throw new Exception("响应信息格式错误：md5验证签名失败");
        }
    }
}
