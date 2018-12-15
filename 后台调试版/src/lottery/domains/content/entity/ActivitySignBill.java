package lottery.domains.content.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="activity_sign_bill", catalog="ecai")
public class ActivitySignBill
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private int id;
  private int userId;
  private int days;
  private String startTime;
  private String endTime;
  private double totalCost;
  private double scale;
  private double money;
  private String time;
  
  public ActivitySignBill() {}
  
  public ActivitySignBill(int userId, int days, String startTime, String endTime, double totalCost, double scale, double money, String time)
  {
    this.userId = userId;
    this.days = days;
    this.startTime = startTime;
    this.endTime = endTime;
    this.totalCost = totalCost;
    this.scale = scale;
    this.money = money;
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
  
  @Column(name="days", nullable=false)
  public int getDays()
  {
    return this.days;
  }
  
  public void setDays(int days)
  {
    this.days = days;
  }
  
  @Column(name="start_time", nullable=false, length=19)
  public String getStartTime()
  {
    return this.startTime;
  }
  
  public void setStartTime(String startTime)
  {
    this.startTime = startTime;
  }
  
  @Column(name="end_time", nullable=false, length=19)
  public String getEndTime()
  {
    return this.endTime;
  }
  
  public void setEndTime(String endTime)
  {
    this.endTime = endTime;
  }
  
  @Column(name="total_cost", nullable=false, precision=16, scale=5)
  public double getTotalCost()
  {
    return this.totalCost;
  }
  
  public void setTotalCost(double totalCost)
  {
    this.totalCost = totalCost;
  }
  
  @Column(name="scale", nullable=false, precision=16, scale=5)
  public double getScale()
  {
    return this.scale;
  }
  
  public void setScale(double scale)
  {
    this.scale = scale;
  }
  
  @Column(name="money", nullable=false, precision=16, scale=5)
  public double getMoney()
  {
    return this.money;
  }
  
  public void setMoney(double money)
  {
    this.money = money;
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
