package lottery.domains.content.vo.config;

public class DailySettleConfig
{
  private double neibuZhaoShangScale;
  private int neibuZhaoShangMinValidUser;
  private double zhaoShangScale;
  private double cjZhaoShangScale;
  private int zhaoShangMinValidUser;
  private boolean enable;
  private double minBillingOrder;
  private int maxSignLevel;
  private int minValidUserl;
  private boolean checkLoss;
  private double[] levelsLoss;
  private double[] levelsSales;
  private double[] levelsScale;
  
  public double getNeibuZhaoShangScale()
  {
    return this.neibuZhaoShangScale;
  }
  
  public void setNeibuZhaoShangScale(double neibuZhaoShangScale)
  {
    this.neibuZhaoShangScale = neibuZhaoShangScale;
  }
  
  public int getNeibuZhaoShangMinValidUser()
  {
    return this.neibuZhaoShangMinValidUser;
  }
  
  public void setNeibuZhaoShangMinValidUser(int neibuZhaoShangMinValidUser)
  {
    this.neibuZhaoShangMinValidUser = neibuZhaoShangMinValidUser;
  }
  
  public boolean isEnable()
  {
    return this.enable;
  }
  
  public void setEnable(boolean enable)
  {
    this.enable = enable;
  }
  
  public double getMinBillingOrder()
  {
    return this.minBillingOrder;
  }
  
  public double getZhaoShangScale()
  {
    return this.zhaoShangScale;
  }
  
  public void setZhaoShangScale(double zhaoShangScale)
  {
    this.zhaoShangScale = zhaoShangScale;
  }
  
  public double getCjZhaoShangScale()
  {
    return this.cjZhaoShangScale;
  }
  
  public void setCjZhaoShangScale(double cjZhaoShangScale)
  {
    this.cjZhaoShangScale = cjZhaoShangScale;
  }
  
  public int getZhaoShangMinValidUser()
  {
    return this.zhaoShangMinValidUser;
  }
  
  public void setZhaoShangMinValidUser(int zhaoShangMinValidUser)
  {
    this.zhaoShangMinValidUser = zhaoShangMinValidUser;
  }
  
  public void setMinBillingOrder(double minBillingOrder)
  {
    this.minBillingOrder = minBillingOrder;
  }
  
  public int getMaxSignLevel()
  {
    return this.maxSignLevel;
  }
  
  public void setMaxSignLevel(int maxSignLevel)
  {
    this.maxSignLevel = maxSignLevel;
  }
  
  public int getMinValidUserl()
  {
    return this.minValidUserl;
  }
  
  public void setMinValidUserl(int minValidUserl)
  {
    this.minValidUserl = minValidUserl;
  }
  
  public boolean isCheckLoss()
  {
    return this.checkLoss;
  }
  
  public void setCheckLoss(boolean checkLoss)
  {
    this.checkLoss = checkLoss;
  }
  
  public double[] getLevelsLoss()
  {
    return this.levelsLoss;
  }
  
  public void setLevelsLoss(double[] levelsLoss)
  {
    this.levelsLoss = levelsLoss;
  }
  
  public double[] getLevelsSales()
  {
    return this.levelsSales;
  }
  
  public void setLevelsSales(double[] levelsSales)
  {
    this.levelsSales = levelsSales;
  }
  
  public double[] getLevelsScale()
  {
    return this.levelsScale;
  }
  
  public void setLevelsScale(double[] levelsScale)
  {
    this.levelsScale = levelsScale;
  }
}
