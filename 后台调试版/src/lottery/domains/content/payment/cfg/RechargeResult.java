package lottery.domains.content.payment.cfg;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import net.sf.json.JSONObject;
import org.apache.http.message.BasicNameValuePair;

public class RechargeResult
{
  private String tradeId;
  private String redirectUrl;
  private String keyword;
  private String payUserName;
  private String message;
  private String signature;
  private String payMethod;
  private String payUrl;
  private int errorCode;
  private String errorMsg;
  private Date createTime;
  private JSONObject jsonValue;
  private List<BasicNameValuePair> weChatParam;
  private HashMap<String, String> paramsMap;
  
  public Date getCreateTime()
  {
    return this.createTime;
  }
  
  public void setCreateTime(Date createTime)
  {
    this.createTime = createTime;
  }
  
  public String getPayUrl()
  {
    return this.payUrl;
  }
  
  public void setPayUrl(String payUrl)
  {
    this.payUrl = payUrl;
  }
  
  public String getMessage()
  {
    return this.message;
  }
  
  public void setMessage(String message)
  {
    this.message = message;
  }
  
  public String getSignature()
  {
    return this.signature;
  }
  
  public void setSignature(String signature)
  {
    this.signature = signature;
  }
  
  public String getPayMethod()
  {
    return this.payMethod;
  }
  
  public void setPayMethod(String payMethod)
  {
    this.payMethod = payMethod;
  }
  
  public String getKeyword()
  {
    return this.keyword;
  }
  
  public void setKeyword(String keyword)
  {
    this.keyword = keyword;
  }
  
  public String getTradeId()
  {
    return this.tradeId;
  }
  
  public void setTradeId(String tradeId)
  {
    this.tradeId = tradeId;
  }
  
  public String getRedirectUrl()
  {
    return this.redirectUrl;
  }
  
  public void setRedirectUrl(String redirectUrl)
  {
    this.redirectUrl = redirectUrl;
  }
  
  public int getErrorCode()
  {
    return this.errorCode;
  }
  
  public void setErrorCode(int errorCode)
  {
    this.errorCode = errorCode;
  }
  
  public String getErrorMsg()
  {
    return this.errorMsg;
  }
  
  public void setErrorMsg(String errorMsg)
  {
    this.errorMsg = errorMsg;
  }
  
  public String getPayUserName()
  {
    return this.payUserName;
  }
  
  public void setPayUserName(String payUserName)
  {
    this.payUserName = payUserName;
  }
  
  public JSONObject getJsonValue()
  {
    return this.jsonValue;
  }
  
  public void setJsonValue(JSONObject jsonValue)
  {
    this.jsonValue = jsonValue;
  }
  
  public List<BasicNameValuePair> getWeChatParam()
  {
    return this.weChatParam;
  }
  
  public void setWeChatParam(List<BasicNameValuePair> weChatParam)
  {
    this.weChatParam = weChatParam;
  }
  
  public HashMap<String, String> getParamsMap()
  {
    return this.paramsMap;
  }
  
  public void setParamsMap(HashMap<String, String> paramsMap)
  {
    this.paramsMap = paramsMap;
  }
}
