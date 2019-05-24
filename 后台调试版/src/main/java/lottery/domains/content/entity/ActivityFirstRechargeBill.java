package lottery.domains.content.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="activity_first_recharge_bill", catalog="ecai", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"user_id", "date"})})
public class ActivityFirstRechargeBill
  implements Serializable
{
  private int id;
  private int userId;
  private String date;
  private String time;
  private double recharge;
  private double amount;
  private String ip;
  
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
  
  @Column(name="date", nullable=false, length=10)
  public String getDate()
  {
    return this.date;
  }
  
  public void setDate(String date)
  {
    this.date = date;
  }
  
  @Column(name="time", nullable=false, length=20)
  public String getTime()
  {
    return this.time;
  }
  
  public void setTime(String time)
  {
    this.time = time;
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
  
  @Column(name="amount", nullable=false, precision=16, scale=5)
  public double getAmount()
  {
    return this.amount;
  }
  
  public void setAmount(double amount)
  {
    this.amount = amount;
  }
  
  @Column(name="ip", nullable=false, length=25)
  public String getIp()
  {
    return this.ip;
  }
  
  public void setIp(String ip)
  {
    this.ip = ip;
  }
}
