package lottery.domains.content.vo.config;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;

public class GameDividendConfig
{
    private List<GameDividendConfigRule> zhuGuanScaleConfigs;
    private int zhuGuanMinValidUser;
    private double zhuGuanMinScale;
    private double zhuGuanMaxScale;
    private double zhuGuanBelowMinScale;
    private double zhuGuanBelowMaxScale;
    private boolean enable;
    private double minBillingOrder;
    
    public GameDividendConfig() {
        this.zhuGuanScaleConfigs = new ArrayList<GameDividendConfigRule>();
    }
    
    public boolean isEnable() {
        return this.enable;
    }
    
    public void setEnable(final boolean enable) {
        this.enable = enable;
    }
    
    public double getMinBillingOrder() {
        return this.minBillingOrder;
    }
    
    public void setMinBillingOrder(final double minBillingOrder) {
        this.minBillingOrder = minBillingOrder;
    }
    
    public double getZhuGuanBelowMaxScale() {
        return this.zhuGuanBelowMaxScale;
    }
    
    public void setZhuGuanBelowMaxScale(final double zhuGuanBelowMaxScale) {
        this.zhuGuanBelowMaxScale = zhuGuanBelowMaxScale;
    }
    
    public double getZhuGuanBelowMinScale() {
        return this.zhuGuanBelowMinScale;
    }
    
    public void setZhuGuanBelowMinScale(final double zhuGuanBelowMinScale) {
        this.zhuGuanBelowMinScale = zhuGuanBelowMinScale;
    }
    
    public double getZhuGuanMaxScale() {
        return this.zhuGuanMaxScale;
    }
    
    public void setZhuGuanMaxScale(final double zhuGuanMaxScale) {
        this.zhuGuanMaxScale = zhuGuanMaxScale;
    }
    
    public double getZhuGuanMinScale() {
        return this.zhuGuanMinScale;
    }
    
    public void setZhuGuanMinScale(final double zhuGuanMinScale) {
        this.zhuGuanMinScale = zhuGuanMinScale;
    }
    
    public int getZhuGuanMinValidUser() {
        return this.zhuGuanMinValidUser;
    }
    
    public void setZhuGuanMinValidUser(final int zhuGuanMinValidUser) {
        this.zhuGuanMinValidUser = zhuGuanMinValidUser;
    }
    
    public List<GameDividendConfigRule> getZhuGuanScaleConfigs() {
        return this.zhuGuanScaleConfigs;
    }
    
    public void setZhuGuanScaleConfigs(final List<GameDividendConfigRule> zhuGuanScaleConfigs) {
        this.zhuGuanScaleConfigs = zhuGuanScaleConfigs;
    }
    
    public void addZhuGuanScaleConfig(final double fromLoss, final double toLoss, final double scale) {
        final GameDividendConfigRule rule = new GameDividendConfigRule(fromLoss, toLoss, scale);
        this.zhuGuanScaleConfigs.add(rule);
        this.setZhuGuanMinMax();
    }
    
    private void setZhuGuanMinMax() {
        for (final GameDividendConfigRule configRule : this.zhuGuanScaleConfigs) {
            if (this.zhuGuanMinScale == 0.0) {
                this.zhuGuanMinScale = configRule.getScale();
            }
            else if (this.zhuGuanMinScale > configRule.getScale()) {
                this.zhuGuanMinScale = configRule.getScale();
            }
            if (this.zhuGuanMaxScale == 0.0) {
                this.zhuGuanMaxScale = configRule.getScale();
            }
            else {
                if (this.zhuGuanMaxScale >= configRule.getScale()) {
                    continue;
                }
                this.zhuGuanMaxScale = configRule.getScale();
            }
        }
    }
    
    public GameDividendConfigRule determineZhuGuanRule(final double loss) {
        return this.determineRule(loss, this.zhuGuanScaleConfigs);
    }
    
    private GameDividendConfigRule determineRule(final double billingOrder, final List<GameDividendConfigRule> configsRules) {
        GameDividendConfigRule billingRule = null;
        for (final GameDividendConfigRule rule : configsRules) {
            if (rule.getToLoss() < 0.0) {
                if (billingOrder >= rule.getFromLoss()) {
                    billingRule = rule;
                    break;
                }
                continue;
            }
            else {
                if (billingOrder >= rule.getFromLoss() && billingOrder <= rule.getToLoss()) {
                    billingRule = rule;
                    break;
                }
                continue;
            }
        }
        return billingRule;
    }
}
