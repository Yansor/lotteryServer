package lottery.domains.content.entity.activity;

public class RebateRulesSign
{
    private int day;
    private double minCost;
    private double rewardPercent;
    private double max;
    
    public int getDay() {
        return this.day;
    }
    
    public void setDay(final int day) {
        this.day = day;
    }
    
    public double getMinCost() {
        return this.minCost;
    }
    
    public void setMinCost(final double minCost) {
        this.minCost = minCost;
    }
    
    public double getRewardPercent() {
        return this.rewardPercent;
    }
    
    public void setRewardPercent(final double rewardPercent) {
        this.rewardPercent = rewardPercent;
    }
    
    public double getMax() {
        return this.max;
    }
    
    public void setMax(final double max) {
        this.max = max;
    }
}
