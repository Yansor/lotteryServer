package lottery.domains.content.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="activity_red_packet_rain_bill", catalog="ecai", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"user_id", "date", "hour"})})
public class ActivityRedPacketRainBill
  implements Serializable
{
  private int id;
  private int userId;
  private String date;
  private String hour;
  private String time;
  private double cost;
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
  
  @Column(name="hour", nullable=false, length=2)
  public String getHour()
  {
    return this.hour;
  }
  
  public void setHour(String hour)
  {
    this.hour = hour;
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
  
  @Column(name="cost", nullable=false, precision=16, scale=5)
  public double getCost()
  {
    return this.cost;
  }
  
  public void setCost(double cost)
  {
    this.cost = cost;
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
