package lottery.domains.content.vo.user;

import lottery.domains.content.entity.UserWithdrawLimit;

public class UserWithdrawLimitVO
{
  private UserWithdrawLimit bean;
  private String username;
  private double totalBilling;
  private double remainConsumption;
  
  public UserWithdrawLimitVO(UserWithdrawLimit bean, String username, double totalBilling, double remainConsumption)
  {
    this.bean = bean;
    this.username = username;
    this.totalBilling = totalBilling;
    this.remainConsumption = remainConsumption;
  }
  
  public UserWithdrawLimit getBean()
  {
    return this.bean;
  }
  
  public void setBean(UserWithdrawLimit bean)
  {
    this.bean = bean;
  }
  
  public String getUsername()
  {
    return this.username;
  }
  
  public void setUsername(String username)
  {
    this.username = username;
  }
  
  public double getTotalBilling()
  {
    return this.totalBilling;
  }
  
  public void setTotalBilling(double totalBilling)
  {
    this.totalBilling = totalBilling;
  }
  
  public double getRemainConsumption()
  {
    return this.remainConsumption;
  }
  
  public void setRemainConsumption(double remainConsumption)
  {
    this.remainConsumption = remainConsumption;
  }
}
