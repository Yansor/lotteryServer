package lottery.domains.content.entity.activity;

import com.alibaba.fastjson.JSON;
import java.util.ArrayList;
import java.util.List;

public class RebateRulesWheel
{
    private List<WheelRules> rules;
    
    public List<WheelRules> getRules() {
        return this.rules;
    }
    
    public void setRules(final List<WheelRules> rules) {
        this.rules = rules;
    }
    
    public static void main(final String[] args) {
        final WheelRules rule1 = new WheelRules();
        rule1.setMinCost(10000.0);
        rule1.setMaxCost(49999.0);
        rule1.setAmounts(new Integer[] { 18, 28 });
        final WheelRules rule2 = new WheelRules();
        rule2.setMinCost(50000.0);
        rule2.setMaxCost(99999.0);
        rule2.setAmounts(new Integer[] { 88, 128 });
        final WheelRules rule3 = new WheelRules();
        rule3.setMinCost(100000.0);
        rule3.setMaxCost(199999.0);
        rule3.setAmounts(new Integer[] { 168, 288 });
        final WheelRules rule4 = new WheelRules();
        rule4.setMinCost(200000.0);
        rule4.setMaxCost(499999.0);
        rule4.setAmounts(new Integer[] { 518, 888 });
        final WheelRules rule5 = new WheelRules();
        rule5.setMinCost(500000.0);
        rule5.setMaxCost(999999.0);
        rule5.setAmounts(new Integer[] { 1688 });
        final WheelRules rule6 = new WheelRules();
        rule6.setMinCost(1000000.0);
        rule6.setMaxCost(-1.0);
        rule6.setAmounts(new Integer[] { 2888 });
        final List<WheelRules> rules = new ArrayList<WheelRules>();
        rules.add(rule1);
        rules.add(rule2);
        rules.add(rule3);
        rules.add(rule4);
        rules.add(rule5);
        rules.add(rule6);
        final RebateRulesWheel rulesWheel = new RebateRulesWheel();
        rulesWheel.setRules(rules);
        System.out.println(JSON.toJSONString((Object)rulesWheel));
    }
    
    public static class WheelRules
    {
        private double minCost;
        private double maxCost;
        private Integer[] amounts;
        
        public double getMinCost() {
            return this.minCost;
        }
        
        public void setMinCost(final double minCost) {
            this.minCost = minCost;
        }
        
        public double getMaxCost() {
            return this.maxCost;
        }
        
        public void setMaxCost(final double maxCost) {
            this.maxCost = maxCost;
        }
        
        public Integer[] getAmounts() {
            return this.amounts;
        }
        
        public void setAmounts(final Integer[] amounts) {
            this.amounts = amounts;
        }
    }
}
