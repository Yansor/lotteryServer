package lottery.domains.content.payment.fkt;

import com.alibaba.fastjson.annotation.JSONField;

public class FKTPayResult
{
  @JSONField(name="is_success")
  private String isSuccess;
  @JSONField(name="errror_msg")
  private String errrorMsg;
  @JSONField(name="transid")
  private String transid;
  @JSONField(name="order_id")
  private String orderId;
  @JSONField(name="bank_status")
  private String bankStatus;
  @JSONField(name="sign")
  private String sign;
  
  public String getIsSuccess()
  {
    return this.isSuccess;
  }
  
  public void setIsSuccess(String isSuccess)
  {
    this.isSuccess = isSuccess;
  }
  
  public String getErrrorMsg()
  {
    return this.errrorMsg;
  }
  
  public void setErrrorMsg(String errrorMsg)
  {
    this.errrorMsg = errrorMsg;
  }
  
  public String getTransid()
  {
    return this.transid;
  }
  
  public void setTransid(String transid)
  {
    this.transid = transid;
  }
  
  public String getOrderId()
  {
    return this.orderId;
  }
  
  public void setOrderId(String orderId)
  {
    this.orderId = orderId;
  }
  
  public String getBankStatus()
  {
    return this.bankStatus;
  }
  
  public void setBankStatus(String bankStatus)
  {
    this.bankStatus = bankStatus;
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
