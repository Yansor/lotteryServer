package lottery.domains.content.entity.activity;

import java.util.List;

public class ActivityFirstRechargeConfigVO
{
  private int id;
  private String rules;
  private int status;
  private double minRecharge;
  private List<ActivityFirstRechargeConfigRule> ruleVOs;
  
  public int getId()
  {
    return this.id;
  }
  
  public void setId(int id)
  {
    this.id = id;
  }
  
  public String getRules()
  {
    return this.rules;
  }
  
  public void setRules(String rules)
  {
    this.rules = rules;
  }
  
  public int getStatus()
  {
    return this.status;
  }
  
  public void setStatus(int status)
  {
    this.status = status;
  }
  
  public double getMinRecharge()
  {
    return this.minRecharge;
  }
  
  public void setMinRecharge(double minRecharge)
  {
    this.minRecharge = minRecharge;
  }
  
  public List<ActivityFirstRechargeConfigRule> getRuleVOs()
  {
    return this.ruleVOs;
  }
  
  public void setRuleVOs(List<ActivityFirstRechargeConfigRule> ruleVOs)
  {
    this.ruleVOs = ruleVOs;
  }
}
