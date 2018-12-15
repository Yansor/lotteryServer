package lottery.domains.content.entity;

public class My18UserBankTransfer
{
  private int id;
  private String billno;
  private String realName;
  private int postscript;
  private int money;
  private String time;
  private int status;
  private String payBillno;
  private String payTime;
  private String secret;
  
  public int getId()
  {
    return this.id;
  }
  
  public void setId(int id)
  {
    this.id = id;
  }
  
  public String getBillno()
  {
    return this.billno;
  }
  
  public void setBillno(String billno)
  {
    this.billno = billno;
  }
  
  public String getRealName()
  {
    return this.realName;
  }
  
  public void setRealName(String realName)
  {
    this.realName = realName;
  }
  
  public int getPostscript()
  {
    return this.postscript;
  }
  
  public void setPostscript(int postscript)
  {
    this.postscript = postscript;
  }
  
  public int getMoney()
  {
    return this.money;
  }
  
  public void setMoney(int money)
  {
    this.money = money;
  }
  
  public String getTime()
  {
    return this.time;
  }
  
  public void setTime(String time)
  {
    this.time = time;
  }
  
  public int getStatus()
  {
    return this.status;
  }
  
  public void setStatus(int status)
  {
    this.status = status;
  }
  
  public String getPayBillno()
  {
    return this.payBillno;
  }
  
  public void setPayBillno(String payBillno)
  {
    this.payBillno = payBillno;
  }
  
  public String getPayTime()
  {
    return this.payTime;
  }
  
  public void setPayTime(String payTime)
  {
    this.payTime = payTime;
  }
  
  public String getSecret()
  {
    return this.secret;
  }
  
  public void setSecret(String secret)
  {
    this.secret = secret;
  }
}
