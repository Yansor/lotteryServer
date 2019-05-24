package lottery.domains.content.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="user_balance_snapshot", catalog="ecai")
public class UserBalanceSnapshot
  implements Serializable
{
  private int id;
  private double totalMoney;
  private double lotteryMoney;
  private String time;
  
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
  
  @Column(name="total_money", nullable=false, precision=16, scale=5)
  public double getTotalMoney()
  {
    return this.totalMoney;
  }
  
  public void setTotalMoney(double totalMoney)
  {
    this.totalMoney = totalMoney;
  }
  
  @Column(name="lottery_money", nullable=false, precision=16, scale=5)
  public double getLotteryMoney()
  {
    return this.lotteryMoney;
  }
  
  public void setLotteryMoney(double lotteryMoney)
  {
    this.lotteryMoney = lotteryMoney;
  }
  
  @Column(name="time", length=19)
  public String getTime()
  {
    return this.time;
  }
  
  public void setTime(String time)
  {
    this.time = time;
  }
}
