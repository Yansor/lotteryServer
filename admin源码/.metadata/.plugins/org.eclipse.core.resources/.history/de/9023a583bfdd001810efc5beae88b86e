package lottery.domains.content.payment.tgf.utils;

import org.dom4j.Element;
import org.dom4j.Document;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.dom4j.DocumentHelper;
import java.util.HashMap;

public class RefundResponseEntity
{
    protected String respCode;
    protected String respDesc;
    protected String respAmt;
    protected String signMsg;
    protected String qrCode;
    
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
    
    public String getSignMsg() {
        return this.signMsg;
    }
    
    public void setSignMsg(final String signMsg) {
        this.signMsg = signMsg;
    }
    
    public String getQrCode() {
        return this.qrCode;
    }
    
    public void setQrCode(final String qrCode) {
        this.qrCode = qrCode;
    }
    
    public String getRespAmt() {
        return this.respAmt;
    }
    
    public void setRespAmt(final String respAmt) {
        this.respAmt = respAmt;
    }
    
    public void parse(final String respStr, final String md5key) throws Exception {
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
        this.respAmt = resultMap.get("/message/detail/Amt");
        this.signMsg = resultMap.get("/message/sign");
        if (StringUtils.isBlank(this.signMsg)) {
            throw new Exception("响应信息格式错误：不存在'sign'节点");
        }
        if (!this.getSignMsg().equalsIgnoreCase(SignUtil.signByMD5(srcData, md5key))) {
            throw new Exception("响应信息格式错误：md5验证签名失败");
        }
        this.qrCode = resultMap.get("/message/detail/qrCode");
    }
}
