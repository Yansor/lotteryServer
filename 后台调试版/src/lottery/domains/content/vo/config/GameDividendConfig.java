package lottery.domains.content.vo.config;

import java.util.ArrayList;
import java.util.List;

public class GameDividendConfig
{
  private List<GameDividendConfigRule> zhuGuanScaleConfigs = new ArrayList();
  private int zhuGuanMinValidUser;
  private double zhuGuanMinScale;
  private double zhuGuanMaxScale;
  private double zhuGuanBelowMinScale;
  private double zhuGuanBelowMaxScale;
  private boolean enable;
  private double minBillingOrder;
  
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
  
  public double getZhuGuanBelowMaxScale()
  {
    return this.zhuGuanBelowMaxScale;
  }
  
  public void setZhuGuanBelowMaxScale(double zhuGuanBelowMaxScale)
  {
    this.zhuGuanBelowMaxScale = zhuGuanBelowMaxScale;
  }
  
  public double getZhuGuanBelowMinScale()
  {
    return this.zhuGuanBelowMinScale;
  }
  
  public void setZhuGuanBelowMinScale(double zhuGuanBelowMinScale)
  {
    this.zhuGuanBelowMinScale = zhuGuanBelowMinScale;
  }
  
  public double getZhuGuanMaxScale()
  {
    return this.zhuGuanMaxScale;
  }
  
  public void setZhuGuanMaxScale(double zhuGuanMaxScale)
  {
    this.zhuGuanMaxScale = zhuGuanMaxScale;
  }
  
  public double getZhuGuanMinScale()
  {
    return this.zhuGuanMinScale;
  }
  
  public void setZhuGuanMinScale(double zhuGuanMinScale)
  {
    this.zhuGuanMinScale = zhuGuanMinScale;
  }
  
  public int getZhuGuanMinValidUser()
  {
    return this.zhuGuanMinValidUser;
  }
  
  public void setZhuGuanMinValidUser(int zhuGuanMinValidUser)
  {
    this.zhuGuanMinValidUser = zhuGuanMinValidUser;
  }
  
  public List<GameDividendConfigRule> getZhuGuanScaleConfigs()
  {
    return this.zhuGuanScaleConfigs;
  }
  
  public void setZhuGuanScaleConfigs(List<GameDividendConfigRule> zhuGuanScaleConfigs)
  {
    this.zhuGuanScaleConfigs = zhuGuanScaleConfigs;
  }
  
  public void addZhuGuanScaleConfig(double fromLoss, double toLoss, double scale)
  {
    GameDividendConfigRule rule = new GameDividendConfigRule(fromLoss, toLoss, scale);
    this.zhuGuanScaleConfigs.add(rule);
    setZhuGuanMinMax();
  }
  
  private void setZhuGuanMinMax()
  {
    for (GameDividendConfigRule configRule : this.zhuGuanScaleConfigs)
    {
      if (this.zhuGuanMinScale == 0.0D) {
        this.zhuGuanMinScale = configRule.getScale();
      } else if (this.zhuGuanMinScale > configRule.getScale()) {
        this.zhuGuanMinScale = configRule.getScale();
      }
      if (this.zhuGuanMaxScale == 0.0D) {
        this.zhuGuanMaxScale = configRule.getScale();
      } else if (this.zhuGuanMaxScale < configRule.getScale()) {
        this.zhuGuanMaxScale = configRule.getScale();
      }
    }
  }
  
  public GameDividendConfigRule determineZhuGuanRule(double loss)
  {
    return determineRule(loss, this.zhuGuanScaleConfigs);
  }
  
  private GameDividendConfigRule determineRule(double billingOrder, List<GameDividendConfigRule> configsRules)
  {
    GameDividendConfigRule billingRule = null;
    for (GameDividendConfigRule rule : configsRules) {
      if (rule.getToLoss() < 0.0D)
      {
        if (billingOrder >= rule.getFromLoss())
        {
          billingRule = rule;
          break;
        }
      }
      else if ((billingOrder >= rule.getFromLoss()) && (billingOrder <= rule.getToLoss()))
      {
        billingRule = rule;
        break;
      }
    }
    return billingRule;
  }
}
