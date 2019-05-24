package lottery.domains.content.payment.yr;

import com.alibaba.fastjson.annotation.JSONField;

public class YRDaifuResult
{
  @JSONField(name="resultCode")
  private String resultCode;
  @JSONField(name="outTradeNo")
  private String outOrderId;
  @JSONField(name="remitStatus")
  private String remitStatus;
  @JSONField(name="orderPrice")
  private Double orderPrice;
  @JSONField(name="sign")
  private String sign;
  @JSONField(name="errMsg")
  private String errMsg;
  
  public String getResultCode()
  {
    return this.resultCode;
  }
  
  public void setResultCode(String resultCode)
  {
    this.resultCode = resultCode;
  }
  
  public String getOutOrderId()
  {
    return this.outOrderId;
  }
  
  public void setOutOrderId(String outOrderId)
  {
    this.outOrderId = outOrderId;
  }
  
  public String getRemitStatus()
  {
    return this.remitStatus;
  }
  
  public void setRemitStatus(String remitStatus)
  {
    this.remitStatus = remitStatus;
  }
  
  public Double getOrderPrice()
  {
    return this.orderPrice;
  }
  
  public void setOrderPrice(Double orderPrice)
  {
    this.orderPrice = orderPrice;
  }
  
  public String getSign()
  {
    return this.sign;
  }
  
  public void setSign(String sign)
  {
    this.sign = sign;
  }
  
  public String getErrMsg()
  {
    return this.errMsg;
  }
  
  public void setErrMsg(String errMsg)
  {
    this.errMsg = errMsg;
  }
}
