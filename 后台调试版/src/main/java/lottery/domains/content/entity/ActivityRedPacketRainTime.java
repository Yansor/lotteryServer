package lottery.domains.content.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="activity_red_packet_rain_time", catalog="ecai", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"date", "hour"})})
public class ActivityRedPacketRainTime
  implements Serializable
{
  private int id;
  private String date;
  private String hour;
  private String startTime;
  private String endTime;
  
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
  
  @Column(name="start_time", nullable=false, length=20)
  public String getStartTime()
  {
    return this.startTime;
  }
  
  public void setStartTime(String startTime)
  {
    this.startTime = startTime;
  }
  
  @Column(name="end_time", nullable=false, length=20)
  public String getEndTime()
  {
    return this.endTime;
  }
  
  public void setEndTime(String endTime)
  {
    this.endTime = endTime;
  }
}
