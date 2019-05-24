package lottery.domains.content.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="activity_red_packet_rain_config", catalog="ecai")
public class ActivityRedPacketRainConfig
  implements Serializable
{
  private int id;
  private String rules;
  private String hours;
  private int durationMinutes;
  private int status;
  
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
  
  @Column(name="rules", nullable=false, length=512)
  public String getRules()
  {
    return this.rules;
  }
  
  public void setRules(String rules)
  {
    this.rules = rules;
  }
  
  @Column(name="hours", nullable=false, length=512)
  public String getHours()
  {
    return this.hours;
  }
  
  public void setHours(String hours)
  {
    this.hours = hours;
  }
  
  @Column(name="duration_minutes", nullable=false)
  public int getDurationMinutes()
  {
    return this.durationMinutes;
  }
  
  public void setDurationMinutes(int durationMinutes)
  {
    this.durationMinutes = durationMinutes;
  }
  
  @Column(name="status", nullable=false)
  public int getStatus()
  {
    return this.status;
  }
  
  public void setStatus(int status)
  {
    this.status = status;
  }
}
