package lottery.domains.content.payment.cfg;

import java.util.Date;

public class RechargePay
{
  private String billno;
  private String tradeNo;
  private double amount;
  private String recvCardNo;
  private String recvName;
  private Integer payBankId;
  private String payBankName;
  private Date payTime;
  private int userId;
  private String notifyType;
  private String tradeStatus;
  private String requestHost;
  
  public String getBillno()
  {
    return this.billno;
  }
  
  public void setBillno(String billno)
  {
    this.billno = billno;
  }
  
  public double getAmount()
  {
    return this.amount;
  }
  
  public void setAmount(double amount)
  {
    this.amount = amount;
  }
  
  public String getRecvCardNo()
  {
    return this.recvCardNo;
  }
  
  public void setRecvCardNo(String recvCardNo)
  {
    this.recvCardNo = recvCardNo;
  }
  
  public String getRecvName()
  {
    return this.recvName;
  }
  
  public void setRecvName(String recvName)
  {
    this.recvName = recvName;
  }
  
  public Integer getPayBankId()
  {
    return this.payBankId;
  }
  
  public void setPayBankId(Integer payBankId)
  {
    this.payBankId = payBankId;
  }
  
  public String getPayBankName()
  {
    return this.payBankName;
  }
  
  public void setPayBankName(String payBankName)
  {
    this.payBankName = payBankName;
  }
  
  public Date getPayTime()
  {
    return this.payTime;
  }
  
  public void setPayTime(Date payTime)
  {
    this.payTime = payTime;
  }
  
  public int getUserId()
  {
    return this.userId;
  }
  
  public void setUserId(int userId)
  {
    this.userId = userId;
  }
  
  public String getNotifyType()
  {
    return this.notifyType;
  }
  
  public void setNotifyType(String notifyType)
  {
    this.notifyType = notifyType;
  }
  
  public String getTradeStatus()
  {
    return this.tradeStatus;
  }
  
  public void setTradeStatus(String tradeStatus)
  {
    this.tradeStatus = tradeStatus;
  }
  
  public String getRequestHost()
  {
    return this.requestHost;
  }
  
  public void setRequestHost(String requestHost)
  {
    this.requestHost = requestHost;
  }
  
  public String getTradeNo()
  {
    return this.tradeNo;
  }
  
  public void setTradeNo(String tradeNo)
  {
    this.tradeNo = tradeNo;
  }
}
