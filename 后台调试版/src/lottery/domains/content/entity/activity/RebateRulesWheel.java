package lottery.domains.content.entity.activity;

import com.alibaba.fastjson.JSON;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class RebateRulesWheel
{
  static class WheelRules{
    private double minCost;
    private double maxCost;
    private Integer[] amounts;

    public double getMinCost() {
      return minCost;
    }

    public void setMinCost(double minCost) {
      this.minCost = minCost;
    }

    public double getMaxCost() {
      return maxCost;
    }

    public void setMaxCost(double maxCost) {
      this.maxCost = maxCost;
    }

    public Integer[] getAmounts() {
      return amounts;
    }

    public void setAmounts(Integer[] amounts) {
      this.amounts = amounts;
    }
  }
  private List<RebateRulesWheel.WheelRules> rules;
  
  public List<RebateRulesWheel.WheelRules> getRules()
  {
    return this.rules;
  }
  
  public void setRules(List<RebateRulesWheel.WheelRules> rules)
  {
    this.rules = rules;
  }
  
  public static void main(String[] args)
  {
    RebateRulesWheel.WheelRules rule1 = new RebateRulesWheel.WheelRules();
    rule1.setMinCost(10000.0D);
    rule1.setMaxCost(49999.0D);
    rule1.setAmounts(new Integer[] { Integer.valueOf(18), Integer.valueOf(28) });
    
    RebateRulesWheel.WheelRules rule2 = new RebateRulesWheel.WheelRules();
    rule2.setMinCost(50000.0D);
    rule2.setMaxCost(99999.0D);
    rule2.setAmounts(new Integer[] { Integer.valueOf(88), Integer.valueOf(128) });
    
    RebateRulesWheel.WheelRules rule3 = new RebateRulesWheel.WheelRules();
    rule3.setMinCost(100000.0D);
    rule3.setMaxCost(199999.0D);
    rule3.setAmounts(new Integer[] { Integer.valueOf(168), Integer.valueOf(288) });
    
    RebateRulesWheel.WheelRules rule4 = new RebateRulesWheel.WheelRules();
    rule4.setMinCost(200000.0D);
    rule4.setMaxCost(499999.0D);
    rule4.setAmounts(new Integer[] { Integer.valueOf(518), Integer.valueOf(888) });
    
    RebateRulesWheel.WheelRules rule5 = new RebateRulesWheel.WheelRules();
    rule5.setMinCost(500000.0D);
    rule5.setMaxCost(999999.0D);
    rule5.setAmounts(new Integer[] { Integer.valueOf(1688) });
    
    RebateRulesWheel.WheelRules rule6 = new RebateRulesWheel.WheelRules();
    rule6.setMinCost(1000000.0D);
    rule6.setMaxCost(-1.0D);
    rule6.setAmounts(new Integer[] { Integer.valueOf(2888) });
    
    List<RebateRulesWheel.WheelRules> rules = new ArrayList();
    rules.add(rule1);
    rules.add(rule2);
    rules.add(rule3);
    rules.add(rule4);
    rules.add(rule5);
    rules.add(rule6);
    
    RebateRulesWheel rulesWheel = new RebateRulesWheel();
    rulesWheel.setRules(rules);
    
    System.out.println(JSON.toJSONString(rulesWheel));
  }
}
