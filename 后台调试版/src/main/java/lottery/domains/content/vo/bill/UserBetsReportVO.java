package lottery.domains.content.vo.bill;

public class UserBetsReportVO
{
  private String field;
  private double money;
  private double returnMoney;
  private double prizeMoney;
  
  public String getField()
  {
    return this.field;
  }
  
  public void setField(String field)
  {
    this.field = field;
  }
  
  public double getMoney()
  {
    return this.money;
  }
  
  public void setMoney(double money)
  {
    this.money = money;
  }
  
  public double getReturnMoney()
  {
    return this.returnMoney;
  }
  
  public void setReturnMoney(double returnMoney)
  {
    this.returnMoney = returnMoney;
  }
  
  public double getPrizeMoney()
  {
    return this.prizeMoney;
  }
  
  public void setPrizeMoney(double prizeMoney)
  {
    this.prizeMoney = prizeMoney;
  }
}
