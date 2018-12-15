package lottery.domains.content.payment.tgf.utils;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class RefundResponseEntity
{
  protected String respCode;
  protected String respDesc;
  protected String respAmt;
  protected String signMsg;
  protected String qrCode;
  
  public String getRespCode()
  {
    return this.respCode;
  }
  
  public void setRespCode(String respCode)
  {
    this.respCode = respCode;
  }
  
  public String getRespDesc()
  {
    return this.respDesc;
  }
  
  public void setRespDesc(String respDesc)
  {
    this.respDesc = respDesc;
  }
  
  public String getSignMsg()
  {
    return this.signMsg;
  }
  
  public void setSignMsg(String signMsg)
  {
    this.signMsg = signMsg;
  }
  
  public String getQrCode()
  {
    return this.qrCode;
  }
  
  public void setQrCode(String qrCode)
  {
    this.qrCode = qrCode;
  }
  
  public String getRespAmt()
  {
    return this.respAmt;
  }
  
  public void setRespAmt(String respAmt)
  {
    this.respAmt = respAmt;
  }
  
  public void parse(String respStr, String md5key)
    throws Exception
  {
    Map<String, String> resultMap = new HashMap();
    XMLParserUtil.parse(respStr, resultMap);
    Document doc = DocumentHelper.parseText(respStr);
    Element root = doc.getRootElement();
    Element respData = root.element("detail");
    String srcData = respData.asXML();
    this.respCode = ((String)resultMap.get("/message/detail/code"));
    if (StringUtils.isBlank(this.respCode)) {
      throw new Exception("响应信息格式错误：不存在'code'节点。");
    }
    this.respDesc = ((String)resultMap.get("/message/detail/desc"));
    if (StringUtils.isBlank(this.respDesc)) {
      throw new Exception("响应信息格式错误：不存在'desc'节点");
    }
    this.respAmt = ((String)resultMap.get("/message/detail/Amt"));
    
    this.signMsg = ((String)resultMap.get("/message/sign"));
    if (StringUtils.isBlank(this.signMsg)) {
      throw new Exception("响应信息格式错误：不存在'sign'节点");
    }
    if (!getSignMsg().equalsIgnoreCase(SignUtil.signByMD5(srcData, md5key))) {
      throw new Exception("响应信息格式错误：md5验证签名失败");
    }
    this.qrCode = ((String)resultMap.get("/message/detail/qrCode"));
  }
}
