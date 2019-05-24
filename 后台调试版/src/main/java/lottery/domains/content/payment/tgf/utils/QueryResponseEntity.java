package lottery.domains.content.payment.tgf.utils;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class QueryResponseEntity
{
  protected String respCode;
  protected String respDesc;
  protected String accDate;
  protected String accNo;
  protected String orderNo;
  protected String status;
  protected String signMsg;
  
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
  
  public String getAccDate()
  {
    return this.accDate;
  }
  
  public void setAccDate(String accDate)
  {
    this.accDate = accDate;
  }
  
  public String getAccNo()
  {
    return this.accNo;
  }
  
  public void setAccNo(String accNo)
  {
    this.accNo = accNo;
  }
  
  public String getOrderNo()
  {
    return this.orderNo;
  }
  
  public void setOrderNo(String orderNo)
  {
    this.orderNo = orderNo;
  }
  
  public String getStatus()
  {
    return this.status;
  }
  
  public void setStatus(String status)
  {
    this.status = status;
  }
  
  public String getSignMsg()
  {
    return this.signMsg;
  }
  
  public void setSignMsg(String signMsg)
  {
    this.signMsg = signMsg;
  }
  
  private static Map<String, String> ORDER_STATUS = new HashMap();
  
  static
  {
    ORDER_STATUS.put("0", "未支付");
    ORDER_STATUS.put("1", "成功");
    ORDER_STATUS.put("2", "失败");
    ORDER_STATUS.put("4", "部分退款");
    ORDER_STATUS.put("5", "全额退款");
    ORDER_STATUS.put("9", "退款处理中");
    ORDER_STATUS.put("10", "未支付");
    ORDER_STATUS.put("11", "订单过期");
  }
  
  public void parse(String respStr, String mk5key)
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
    if ("00".equalsIgnoreCase(this.respCode))
    {
      this.accDate = ((String)resultMap.get("/message/detail/opeDate"));
      if (StringUtils.isBlank(this.accDate)) {
        throw new Exception("响应信息格式错误：不存在'opeDate'节点。");
      }
      this.accNo = ((String)resultMap.get("/message/detail/opeNo"));
      if (StringUtils.isBlank(this.accNo)) {
        throw new Exception("响应信息格式错误：不存在'opeNo'节点。");
      }
      this.orderNo = ((String)resultMap.get("/message/detail/tradeNo"));
      
      this.status = ((String)resultMap.get("/message/detail/status"));
    }
    this.signMsg = ((String)resultMap.get("/message/sign"));
    if (StringUtils.isBlank(this.signMsg)) {
      throw new Exception("响应信息格式错误：不存在'sign'节点");
    }
    if (!getSignMsg().equalsIgnoreCase(SignUtil.signByMD5(srcData, mk5key))) {
      throw new Exception("响应信息格式错误：md5验证签名失败");
    }
  }
}
