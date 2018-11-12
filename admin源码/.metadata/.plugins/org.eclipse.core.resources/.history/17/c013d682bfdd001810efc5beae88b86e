package lottery.domains.content.vo.config;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;

public class DividendConfig
{
    private List<DividendConfigRule> zhaoShangScaleConfigs;
    private List<DividendConfigRule> cjZhaoShangScaleConfigs;
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
    
    public DividendConfig() {
        this.zhaoShangScaleConfigs = new ArrayList<DividendConfigRule>();
        this.cjZhaoShangScaleConfigs = new ArrayList<DividendConfigRule>();
    }
    
    public List<DividendConfigRule> getZhaoShangScaleConfigs() {
        return this.zhaoShangScaleConfigs;
    }
    
    public void setZhaoShangScaleConfigs(final List<DividendConfigRule> zhaoShangScaleConfigs) {
        this.zhaoShangScaleConfigs = zhaoShangScaleConfigs;
    }
    
    public List<DividendConfigRule> getCjZhaoShangScaleConfigs() {
        return this.cjZhaoShangScaleConfigs;
    }
    
    public void setCjZhaoShangScaleConfigs(final List<DividendConfigRule> cjZhaoShangScaleConfigs) {
        this.cjZhaoShangScaleConfigs = cjZhaoShangScaleConfigs;
    }
    
    public int getZhaoShangMinValidUser() {
        return this.zhaoShangMinValidUser;
    }
    
    public void setZhaoShangMinValidUser(final int zhaoShangMinValidUser) {
        this.zhaoShangMinValidUser = zhaoShangMinValidUser;
    }
    
    public double getCjZhaoShangMinScale() {
        return this.cjZhaoShangMinScale;
    }
    
    public void setCjZhaoShangMinScale(final double cjZhaoShangMinScale) {
        this.cjZhaoShangMinScale = cjZhaoShangMinScale;
    }
    
    public double getCjZhaoShangMaxScale() {
        return this.cjZhaoShangMaxScale;
    }
    
    public void setCjZhaoShangMaxScale(final double cjZhaoShangMaxScale) {
        this.cjZhaoShangMaxScale = cjZhaoShangMaxScale;
    }
    
    public double getZhaoShangBelowMinScale() {
        return this.zhaoShangBelowMinScale;
    }
    
    public void setZhaoShangBelowMinScale(final double zhaoShangBelowMinScale) {
        this.zhaoShangBelowMinScale = zhaoShangBelowMinScale;
    }
    
    public double getZhaoShangBelowMaxScale() {
        return this.zhaoShangBelowMaxScale;
    }
    
    public void setZhaoShangBelowMaxScale(final double zhaoShangBelowMaxScale) {
        this.zhaoShangBelowMaxScale = zhaoShangBelowMaxScale;
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
    
    public void addZhaoShangScaleConfig(final double fromDailyBilling, final double toDailyBilling, final double scale) {
        final DividendConfigRule rule = new DividendConfigRule(fromDailyBilling, toDailyBilling, scale);
        this.zhaoShangScaleConfigs.add(rule);
    }
    
    public void addCJZhaoShangScaleConfig(final double fromDailyBilling, final double toDailyBilling, final double scale) {
        final DividendConfigRule rule = new DividendConfigRule(fromDailyBilling, toDailyBilling, scale);
        this.cjZhaoShangScaleConfigs.add(rule);
        this.setCJZhaoShangMinMax();
    }
    
    public String getZhaoShangScaleLevels() {
        return this.zhaoShangScaleLevels;
    }
    
    public void setZhaoShangScaleLevels(final String zhaoShangScaleLevels) {
        this.zhaoShangScaleLevels = zhaoShangScaleLevels;
    }
    
    public String getZhaoShangSalesLevels() {
        return this.zhaoShangSalesLevels;
    }
    
    public void setZhaoShangSalesLevels(final String zhaoShangSalesLevels) {
        this.zhaoShangSalesLevels = zhaoShangSalesLevels;
    }
    
    public String getZhaoShangLossLevels() {
        return this.zhaoShangLossLevels;
    }
    
    public void setZhaoShangLossLevels(final String zhaoShangLossLevels) {
        this.zhaoShangLossLevels = zhaoShangLossLevels;
    }
    
    private void setCJZhaoShangMinMax() {
        for (final DividendConfigRule configRule : this.cjZhaoShangScaleConfigs) {
            if (this.cjZhaoShangMinScale == 0.0) {
                this.cjZhaoShangMinScale = configRule.getScale();
            }
            else if (this.cjZhaoShangMinScale > configRule.getScale()) {
                this.cjZhaoShangMinScale = configRule.getScale();
            }
            if (this.cjZhaoShangMaxScale == 0.0) {
                this.cjZhaoShangMaxScale = configRule.getScale();
            }
            else {
                if (this.cjZhaoShangMaxScale >= configRule.getScale()) {
                    continue;
                }
                this.cjZhaoShangMaxScale = configRule.getScale();
            }
        }
    }
    
    public DividendConfigRule determineZhaoShangRule(final double dailyBilling) {
        return this.determineRule(dailyBilling, this.zhaoShangScaleConfigs);
    }
    
    public DividendConfigRule determineCJZhaoShangRule(final double dailyBilling) {
        return this.determineRule(dailyBilling, this.cjZhaoShangScaleConfigs);
    }
    
    private DividendConfigRule determineRule(final double dailyBilling, final List<DividendConfigRule> configsRules) {
        DividendConfigRule billingRule = null;
        for (final DividendConfigRule rule : configsRules) {
            if (rule.getTo() < 0.0) {
                if (dailyBilling >= rule.getFrom()) {
                    billingRule = rule;
                    break;
                }
                continue;
            }
            else {
                if (dailyBilling >= rule.getFrom() && dailyBilling <= rule.getTo()) {
                    billingRule = rule;
                    break;
                }
                continue;
            }
        }
        return billingRule;
    }
    
    public int getStartLevel() {
        return this.startLevel;
    }
    
    public void setStartLevel(final int startLevel) {
        this.startLevel = startLevel;
    }
    
    public int getMaxSignLevel() {
        return this.maxSignLevel;
    }
    
    public void setMaxSignLevel(final int maxSignLevel) {
        this.maxSignLevel = maxSignLevel;
    }
    
    public int getMinValidUserl() {
        return this.minValidUserl;
    }
    
    public void setMinValidUserl(final int minValidUserl) {
        this.minValidUserl = minValidUserl;
    }
    
    public boolean isCheckLoss() {
        return this.checkLoss;
    }
    
    public void setCheckLoss(final boolean checkLoss) {
        this.checkLoss = checkLoss;
    }
    
    public double[] getLevelsLoss() {
        return this.levelsLoss;
    }
    
    public void setLevelsLoss(final double[] levelsLoss) {
        this.levelsLoss = levelsLoss;
    }
    
    public double[] getLevelsSales() {
        return this.levelsSales;
    }
    
    public void setLevelsSales(final double[] levelsSales) {
        this.levelsSales = levelsSales;
    }
    
    public double[] getLevelsScale() {
        return this.levelsScale;
    }
    
    public void setLevelsScale(final double[] levelsScale) {
        this.levelsScale = levelsScale;
    }
    
    public int getFixedType() {
        return this.fixedType;
    }
    
    public void setFixedType(final int fixedType) {
        this.fixedType = fixedType;
    }
}
