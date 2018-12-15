package lottery.domains.content.payment.tgf.utils;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class QuickPayConfirmResponseEntity
{
  private String code;
  private String desc;
  private String sign;
  
  public String getCode()
  {
    return this.code;
  }
  
  public void setCode(String code)
  {
    this.code = code;
  }
  
  public String getDesc()
  {
    return this.desc;
  }
  
  public void setDesc(String desc)
  {
    this.desc = desc;
  }
  
  public String getSign()
  {
    return this.sign;
  }
  
  public void setSign(String sign)
  {
    this.sign = sign;
  }
  
  public void parse(String respStr)
    throws Exception
  {
    Map<String, String> resultMap = new HashMap();
    XMLParserUtil.parse(respStr, resultMap);
    Document doc = DocumentHelper.parseText(respStr);
    Element root = doc.getRootElement();
    Element respData = root.element("detail");
    String srcData = respData.asXML();
    this.code = ((String)resultMap.get("/message/detail/code"));
    if (StringUtils.isBlank(this.code)) {
      throw new Exception("响应信息格式错误：不存在'code'节点。");
    }
    this.desc = ((String)resultMap.get("/message/detail/desc"));
    if (StringUtils.isBlank(this.desc)) {
      throw new Exception("响应信息格式错误：不存在'desc'节点。");
    }
    this.sign = ((String)resultMap.get("/message/sign"));
    if (StringUtils.isBlank(this.sign)) {
      throw new Exception("响应信息格式错误：不存在'sign'节点");
    }
    if (!SignUtil.verifyData(getSign(), srcData)) {
      throw new Exception("签名验证不通过");
    }
  }
}
