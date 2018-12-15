package lottery.domains.content.entity.activity;

public class ActivityFirstRechargeConfigRule
{
  private double minRecharge;
  private double maxRecharge;
  private double amount;
  
  public double getMinRecharge()
  {
    return this.minRecharge;
  }
  
  public void setMinRecharge(double minRecharge)
  {
    this.minRecharge = minRecharge;
  }
  
  public double getMaxRecharge()
  {
    return this.maxRecharge;
  }
  
  public void setMaxRecharge(double maxRecharge)
  {
    this.maxRecharge = maxRecharge;
  }
  
  public double getAmount()
  {
    return this.amount;
  }
  
  public void setAmount(double amount)
  {
    this.amount = amount;
  }
}
