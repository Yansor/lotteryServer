package lottery.domains.content.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="vip_upgrade_list", catalog="ecai")
public class VipUpgradeList
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private int id;
  private int userId;
  private int beforeLevel;
  private int afterLevel;
  private double recharge;
  private double cost;
  private String month;
  private String time;
  
  public VipUpgradeList() {}
  
  public VipUpgradeList(int userId, int beforeLevel, int afterLevel, double recharge, double cost, String month, String time)
  {
    this.userId = userId;
    this.beforeLevel = beforeLevel;
    this.afterLevel = afterLevel;
    this.recharge = recharge;
    this.cost = cost;
    this.month = month;
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
  
  @Column(name="before_level", nullable=false)
  public int getBeforeLevel()
  {
    return this.beforeLevel;
  }
  
  public void setBeforeLevel(int beforeLevel)
  {
    this.beforeLevel = beforeLevel;
  }
  
  @Column(name="after_level", nullable=false)
  public int getAfterLevel()
  {
    return this.afterLevel;
  }
  
  public void setAfterLevel(int afterLevel)
  {
    this.afterLevel = afterLevel;
  }
  
  @Column(name="recharge", nullable=false, precision=16, scale=5)
  public double getRecharge()
  {
    return this.recharge;
  }
  
  public void setRecharge(double recharge)
  {
    this.recharge = recharge;
  }
  
  @Column(name="cost", nullable=false, precision=16, scale=5)
  public double getCost()
  {
    return this.cost;
  }
  
  public void setCost(double cost)
  {
    this.cost = cost;
  }
  
  @Column(name="month", nullable=false, length=7)
  public String getMonth()
  {
    return this.month;
  }
  
  public void setMonth(String month)
  {
    this.month = month;
  }
  
  @Column(name="time", nullable=false, length=19)
  public String getTime()
  {
    return this.time;
  }
  
  public void setTime(String time)
  {
    this.time = time;
  }
}
