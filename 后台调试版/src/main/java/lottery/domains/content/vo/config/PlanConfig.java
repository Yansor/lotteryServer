package lottery.domains.content.vo.config;

import java.util.List;

public class PlanConfig
{
  private double minMoney;
  private List<String> title;
  private List<Integer> rate;
  private List<Integer> level;
  
  public double getMinMoney()
  {
    return this.minMoney;
  }
  
  public void setMinMoney(double minMoney)
  {
    this.minMoney = minMoney;
  }
  
  public List<String> getTitle()
  {
    return this.title;
  }
  
  public void setTitle(List<String> title)
  {
    this.title = title;
  }
  
  public List<Integer> getRate()
  {
    return this.rate;
  }
  
  public void setRate(List<Integer> rate)
  {
    this.rate = rate;
  }
  
  public List<Integer> getLevel()
  {
    return this.level;
  }
  
  public void setLevel(List<Integer> level)
  {
    this.level = level;
  }
}
