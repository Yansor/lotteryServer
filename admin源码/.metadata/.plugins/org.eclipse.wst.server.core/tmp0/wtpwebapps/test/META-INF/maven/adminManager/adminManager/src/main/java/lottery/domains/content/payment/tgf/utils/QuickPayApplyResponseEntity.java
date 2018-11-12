package lottery.domains.content.payment.tgf.utils;

import org.dom4j.Element;
import org.dom4j.Document;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.dom4j.DocumentHelper;
import java.util.HashMap;

public class QuickPayApplyResponseEntity
{
    private String code;
    private String desc;
    private String opeNo;
    private String opeDate;
    private String sessionID;
    private String sign;
    
    public String getCode() {
        return this.code;
    }
    
    public void setCode(final String code) {
        this.code = code;
    }
    
    public String getDesc() {
        return this.desc;
    }
    
    public void setDesc(final String desc) {
        this.desc = desc;
    }
    
    public String getOpeNo() {
        return this.opeNo;
    }
    
    public void setOpeNo(final String opeNo) {
        this.opeNo = opeNo;
    }
    
    public String getSessionID() {
        return this.sessionID;
    }
    
    public void setSessionID(final String sessionID) {
        this.sessionID = sessionID;
    }
    
    public String getOpeDate() {
        return this.opeDate;
    }
    
    public void setOpeDate(final String opeDate) {
        this.opeDate = opeDate;
    }
    
    public String getSign() {
        return this.sign;
    }
    
    public void setSign(final String sign) {
        this.sign = sign;
    }
    
    public void parse(final String respStr) throws Exception {
        final Map<String, String> resultMap = new HashMap<String, String>();
        XMLParserUtil.parse(respStr, resultMap);
        final Document doc = DocumentHelper.parseText(respStr);
        final Element root = doc.getRootElement();
        final Element respData = root.element("detail");
        final String srcData = respData.asXML();
        this.code = resultMap.get("/message/detail/code");
        if (StringUtils.isBlank(this.code)) {
            throw new Exception("响应信息格式错误：不存在'code'节点。");
        }
        this.desc = resultMap.get("/message/detail/desc");
        if (StringUtils.isBlank(this.desc)) {
            throw new Exception("响应信息格式错误：不存在'desc'节点。");
        }
        this.opeNo = resultMap.get("/message/detail/opeNo");
        this.opeDate = resultMap.get("/message/detail/opeDate");
        this.sessionID = resultMap.get("/message/detail/sessionID");
        this.sign = resultMap.get("/message/sign");
        if (StringUtils.isBlank(this.sign)) {
            throw new Exception("响应信息格式错误：不存在'sign'节点");
        }
        if (!SignUtil.verifyData(this.getSign(), srcData)) {
            throw new Exception("签名验证不通过");
        }
    }
}
