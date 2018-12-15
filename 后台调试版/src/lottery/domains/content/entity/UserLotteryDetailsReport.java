package lottery.domains.content.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="user_lottery_details_report", catalog="ecai", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"user_id", "lottery_id", "rule_id", "time"})})
public class UserLotteryDetailsReport
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private int id;
  private int userId;
  private int lotteryId;
  private int ruleId;
  private double spend;
  private double prize;
  private double spendReturn;
  private double proxyReturn;
  private double cancelOrder;
  private double billingOrder;
  private String time;
  
  public UserLotteryDetailsReport() {}
  
  public UserLotteryDetailsReport(int userId, int lotteryId, int ruleId, double spend, double prize, double spendReturn, double proxyReturn, double cancelOrder, double billingOrder, String time)
  {
    this.userId = userId;
    this.lotteryId = lotteryId;
    this.ruleId = ruleId;
    this.spend = spend;
    this.prize = prize;
    this.spendReturn = spendReturn;
    this.proxyReturn = proxyReturn;
    this.cancelOrder = cancelOrder;
    this.billingOrder = billingOrder;
    this.time = time;
  }
  
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name="id", unique=true, nullable=false)
  public int getId()
  {
    return this.id;
  }
  
  public void setId(int id)
  {
    this.id = id;
  }
  
  @Column(name="user_id", nullable=false)
  public int getUserId()
  {
    return this.userId;
  }
  
  public void setUserId(int userId)
  {
    this.userId = userId;
  }
  
  @Column(name="lottery_id", nullable=false)
  public int getLotteryId()
  {
    return this.lotteryId;
  }
  
  public void setLotteryId(int lotteryId)
  {
    this.lotteryId = lotteryId;
  }
  
  @Column(name="rule_id", nullable=false)
  public int getRuleId()
  {
    return this.ruleId;
  }
  
  public void setRuleId(int ruleId)
  {
    this.ruleId = ruleId;
  }
  
  @Column(name="spend", nullable=false, precision=16, scale=5)
  public double getSpend()
  {
    return this.spend;
  }
  
  public void setSpend(double spend)
  {
    this.spend = spend;
  }
  
  @Column(name="prize", nullable=false, precision=16, scale=5)
  public double getPrize()
  {
    return this.prize;
  }
  
  public void setPrize(double prize)
  {
    this.prize = prize;
  }
  
  @Column(name="spend_return", nullable=false, precision=16, scale=5)
  public double getSpendReturn()
  {
    return this.spendReturn;
  }
  
  public void setSpendReturn(double spendReturn)
  {
    this.spendReturn = spendReturn;
  }
  
  @Column(name="proxy_return", nullable=false, precision=16, scale=5)
  public double getProxyReturn()
  {
    return this.proxyReturn;
  }
  
  public void setProxyReturn(double proxyReturn)
  {
    this.proxyReturn = proxyReturn;
  }
  
  @Column(name="cancel_order", nullable=false, precision=16, scale=5)
  public double getCancelOrder()
  {
    return this.cancelOrder;
  }
  
  public void setCancelOrder(double cancelOrder)
  {
    this.cancelOrder = cancelOrder;
  }
  
  @Column(name="billing_order", nullable=false, precision=16, scale=5)
  public double getBillingOrder()
  {
    return this.billingOrder;
  }
  
  public void setBillingOrder(double billingOrder)
  {
    this.billingOrder = billingOrder;
  }
  
  @Column(name="time", nullable=false, length=10)
  public String getTime()
  {
    return this.time;
  }
  
  public void setTime(String time)
  {
    this.time = time;
  }
}
