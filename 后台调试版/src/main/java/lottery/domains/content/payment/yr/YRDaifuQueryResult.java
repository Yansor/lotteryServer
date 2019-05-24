package lottery.domains.content.payment.yr;

import com.alibaba.fastjson.annotation.JSONField;

public class YRDaifuQueryResult
{
  @JSONField(name="resultCode")
  private String resultCode;
  @JSONField(name="outTradeNo")
  private String outTradeNo;
  @JSONField(name="remitStatus")
  private String remitStatus;
  @JSONField(name="settAmount")
  private String settAmount;
  @JSONField(name="completeDate")
  private String completeDate;
  @JSONField(name="errMsg")
  private String errMsg;
  @JSONField(name="sign")
  private String sign;
  
  public String getResultCode()
  {
    return this.resultCode;
  }
  
  public void setResultCode(String resultCode)
  {
    this.resultCode = resultCode;
  }
  
  public String getOutTradeNo()
  {
    return this.outTradeNo;
  }
  
  public void setOutTradeNo(String outTradeNo)
  {
    this.outTradeNo = outTradeNo;
  }
  
  public String getRemitStatus()
  {
    return this.remitStatus;
  }
  
  public void setRemitStatus(String remitStatus)
  {
    this.remitStatus = remitStatus;
  }
  
  public String getSettAmount()
  {
    return this.settAmount;
  }
  
  public void setSettAmount(String settAmount)
  {
    this.settAmount = settAmount;
  }
  
  public String getCompleteDate()
  {
    return this.completeDate;
  }
  
  public void setCompleteDate(String completeDate)
  {
    this.completeDate = completeDate;
  }
  
  public String getErrMsg()
  {
    return this.errMsg;
  }
  
  public void setErrMsg(String errMsg)
  {
    this.errMsg = errMsg;
  }
  
  public String getSign()
  {
    return this.sign;
  }
  
  public void setSign(String sign)
  {
    this.sign = sign;
  }
}
