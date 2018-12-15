package lottery.domains.content.payment.xinbei;

import com.alibaba.fastjson.annotation.JSONField;

public class XinbeiDaifuResult
{
  @JSONField(name="SettleType")
  private String SettleType;
  @JSONField(name="UrgentType")
  private String UrgentType;
  @JSONField(name="Amount")
  private String Amount;
  @JSONField(name="BankConfig")
  private String BankConfig;
  @JSONField(name="MerchantOrder")
  private String MerchantOrder;
  @JSONField(name="SerialNo")
  private String SerialNo;
  @JSONField(name="Status")
  private String Status;
  @JSONField(name="Msg")
  private String Msg;
  @JSONField(name="MsgCode")
  private String MsgCode;
  @JSONField(name="Sin")
  private String Sin;
  
  public String getSettleType()
  {
    return this.SettleType;
  }
  
  public void setSettleType(String settleType)
  {
    this.SettleType = settleType;
  }
  
  public String getUrgentType()
  {
    return this.UrgentType;
  }
  
  public void setUrgentType(String urgentType)
  {
    this.UrgentType = urgentType;
  }
  
  public String getAmount()
  {
    return this.Amount;
  }
  
  public void setAmount(String amount)
  {
    this.Amount = amount;
  }
  
  public String getBankConfig()
  {
    return this.BankConfig;
  }
  
  public void setBankConfig(String bankConfig)
  {
    this.BankConfig = bankConfig;
  }
  
  public String getMerchantOrder()
  {
    return this.MerchantOrder;
  }
  
  public void setMerchantOrder(String merchantOrder)
  {
    this.MerchantOrder = merchantOrder;
  }
  
  public String getSerialNo()
  {
    return this.SerialNo;
  }
  
  public void setSerialNo(String serialNo)
  {
    this.SerialNo = serialNo;
  }
  
  public String getStatus()
  {
    return this.Status;
  }
  
  public void setStatus(String status)
  {
    this.Status = status;
  }
  
  public String getMsg()
  {
    return this.Msg;
  }
  
  public void setMsg(String msg)
  {
    this.Msg = msg;
  }
  
  public String getMsgCode()
  {
    return this.MsgCode;
  }
  
  public void setMsgCode(String msgCode)
  {
    this.MsgCode = msgCode;
  }
  
  public String getSin()
  {
    return this.Sin;
  }
  
  public void setSin(String sin)
  {
    this.Sin = sin;
  }
}
