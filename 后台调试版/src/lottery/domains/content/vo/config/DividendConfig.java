package lottery.domains.content.vo.config;

import java.util.ArrayList;
import java.util.List;

public class DividendConfig
{
  private List<DividendConfigRule> zhaoShangScaleConfigs = new ArrayList();
  private List<DividendConfigRule> cjZhaoShangScaleConfigs = new ArrayList();
  private int zhaoShangMinValidUser;
  private String zhaoShangScaleLevels;
  private String zhaoShangSalesLevels;
  private String zhaoShangLossLevels;
  private double cjZhaoShangMinScale;
  private double cjZhaoShangMaxScale;
  private double zhaoShangBelowMinScale;
  private double zhaoShangBelowMaxScale;
  private boolean enable;
  private double minBillingOrder;
  private int startLevel;
  private int maxSignLevel;
  private int minValidUserl;
  private boolean checkLoss;
  private double[] levelsLoss;
  private double[] levelsSales;
  private double[] levelsScale;
  private int fixedType;
  
  public List<DividendConfigRule> getZhaoShangScaleConfigs()
  {
    return this.zhaoShangScaleConfigs;
  }
  
  public void setZhaoShangScaleConfigs(List<DividendConfigRule> zhaoShangScaleConfigs)
  {
    this.zhaoShangScaleConfigs = zhaoShangScaleConfigs;
  }
  
  public List<DividendConfigRule> getCjZhaoShangScaleConfigs()
  {
    return this.cjZhaoShangScaleConfigs;
  }
  
  public void setCjZhaoShangScaleConfigs(List<DividendConfigRule> cjZhaoShangScaleConfigs)
  {
    this.cjZhaoShangScaleConfigs = cjZhaoShangScaleConfigs;
  }
  
  public int getZhaoShangMinValidUser()
  {
    return this.zhaoShangMinValidUser;
  }
  
  public void setZhaoShangMinValidUser(int zhaoShangMinValidUser)
  {
    this.zhaoShangMinValidUser = zhaoShangMinValidUser;
  }
  
  public double getCjZhaoShangMinScale()
  {
    return this.cjZhaoShangMinScale;
  }
  
  public void setCjZhaoShangMinScale(double cjZhaoShangMinScale)
  {
    this.cjZhaoShangMinScale = cjZhaoShangMinScale;
  }
  
  public double getCjZhaoShangMaxScale()
  {
    return this.cjZhaoShangMaxScale;
  }
  
  public void setCjZhaoShangMaxScale(double cjZhaoShangMaxScale)
  {
    this.cjZhaoShangMaxScale = cjZhaoShangMaxScale;
  }
  
  public double getZhaoShangBelowMinScale()
  {
    return this.zhaoShangBelowMinScale;
  }
  
  public void setZhaoShangBelowMinScale(double zhaoShangBelowMinScale)
  {
    this.zhaoShangBelowMinScale = zhaoShangBelowMinScale;
  }
  
  public double getZhaoShangBelowMaxScale()
  {
    return this.zhaoShangBelowMaxScale;
  }
  
  public void setZhaoShangBelowMaxScale(double zhaoShangBelowMaxScale)
  {
    this.zhaoShangBelowMaxScale = zhaoShangBelowMaxScale;
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
  
  public void setMinBillingOrder(double minBillingOrder)
  {
    this.minBillingOrder = minBillingOrder;
  }
  
  public void addZhaoShangScaleConfig(double fromDailyBilling, double toDailyBilling, double scale)
  {
    DividendConfigRule rule = new DividendConfigRule(fromDailyBilling, toDailyBilling, scale);
    this.zhaoShangScaleConfigs.add(rule);
  }
  
  public void addCJZhaoShangScaleConfig(double fromDailyBilling, double toDailyBilling, double scale)
  {
    DividendConfigRule rule = new DividendConfigRule(fromDailyBilling, toDailyBilling, scale);
    this.cjZhaoShangScaleConfigs.add(rule);
    setCJZhaoShangMinMax();
  }
  
  public String getZhaoShangScaleLevels()
  {
    return this.zhaoShangScaleLevels;
  }
  
  public void setZhaoShangScaleLevels(String zhaoShangScaleLevels)
  {
    this.zhaoShangScaleLevels = zhaoShangScaleLevels;
  }
  
  public String getZhaoShangSalesLevels()
  {
    return this.zhaoShangSalesLevels;
  }
  
  public void setZhaoShangSalesLevels(String zhaoShangSalesLevels)
  {
    this.zhaoShangSalesLevels = zhaoShangSalesLevels;
  }
  
  public String getZhaoShangLossLevels()
  {
    return this.zhaoShangLossLevels;
  }
  
  public void setZhaoShangLossLevels(String zhaoShangLossLevels)
  {
    this.zhaoShangLossLevels = zhaoShangLossLevels;
  }
  
  private void setCJZhaoShangMinMax()
  {
    for (DividendConfigRule configRule : this.cjZhaoShangScaleConfigs)
    {
      if (this.cjZhaoShangMinScale == 0.0D) {
        this.cjZhaoShangMinScale = configRule.getScale();
      } else if (this.cjZhaoShangMinScale > configRule.getScale()) {
        this.cjZhaoShangMinScale = configRule.getScale();
      }
      if (this.cjZhaoShangMaxScale == 0.0D) {
        this.cjZhaoShangMaxScale = configRule.getScale();
      } else if (this.cjZhaoShangMaxScale < configRule.getScale()) {
        this.cjZhaoShangMaxScale = configRule.getScale();
      }
    }
  }
  
  public DividendConfigRule determineZhaoShangRule(double dailyBilling)
  {
    return determineRule(dailyBilling, this.zhaoShangScaleConfigs);
  }
  
  public DividendConfigRule determineCJZhaoShangRule(double dailyBilling)
  {
    return determineRule(dailyBilling, this.cjZhaoShangScaleConfigs);
  }
  
  private DividendConfigRule determineRule(double dailyBilling, List<DividendConfigRule> configsRules)
  {
    DividendConfigRule billingRule = null;
    for (DividendConfigRule rule : configsRules) {
      if (rule.getTo() < 0.0D)
      {
        if (dailyBilling >= rule.getFrom())
        {
          billingRule = rule;
          break;
        }
      }
      else if ((dailyBilling >= rule.getFrom()) && (dailyBilling <= rule.getTo()))
      {
        billingRule = rule;
        break;
      }
    }
    return billingRule;
  }
  
  public int getStartLevel()
  {
    return this.startLevel;
  }
  
  public void setStartLevel(int startLevel)
  {
    this.startLevel = startLevel;
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
  
  public int getFixedType()
  {
    return this.fixedType;
  }
  
  public void setFixedType(int fixedType)
  {
    this.fixedType = fixedType;
  }
}
