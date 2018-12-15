package lottery.domains.content.vo.config;

public class RegistConfig
{
  private int defaultCode;
  private boolean enable;
  private int demoCount;
  private String demoPassword;
  private double demoLotteryMoney;
  private int fictitiousCount;
  private String fictitiousPassword;
  
  public int getFictitiousCount()
  {
    return this.fictitiousCount;
  }
  
  public void setFictitiousCount(int fictitiousCount)
  {
    this.fictitiousCount = fictitiousCount;
  }
  
  public String getFictitiousPassword()
  {
    return this.fictitiousPassword;
  }
  
  public void setFictitiousPassword(String fictitiousPassword)
  {
    this.fictitiousPassword = fictitiousPassword;
  }
  
  public int getDemoCount()
  {
    return this.demoCount;
  }
  
  public void setDemoCount(int demoCount)
  {
    this.demoCount = demoCount;
  }
  
  public String getDemoPassword()
  {
    return this.demoPassword;
  }
  
  public void setDemoPassword(String demoPassword)
  {
    this.demoPassword = demoPassword;
  }
  
  public double getDemoLotteryMoney()
  {
    return this.demoLotteryMoney;
  }
  
  public void setDemoLotteryMoney(double demoLotteryMoney)
  {
    this.demoLotteryMoney = demoLotteryMoney;
  }
  
  public int getDefaultCode()
  {
    return this.defaultCode;
  }
  
  public void setDefaultCode(int defaultCode)
  {
    this.defaultCode = defaultCode;
  }
  
  public boolean isEnable()
  {
    return this.enable;
  }
  
  public void setEnable(boolean enable)
  {
    this.enable = enable;
  }
}
