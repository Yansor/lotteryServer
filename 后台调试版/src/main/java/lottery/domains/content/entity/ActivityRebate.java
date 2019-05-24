package lottery.domains.content.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="activity_rebate", catalog="ecai")
public class ActivityRebate
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private int id;
  private int type;
  private String rules;
  private String startTime;
  private String endTime;
  private int status;
  
  public ActivityRebate() {}
  
  public ActivityRebate(int type, String rules, int status)
  {
    this.type = type;
    this.rules = rules;
    this.status = status;
  }
  
  public ActivityRebate(int type, String rules, String startTime, String endTime, int status)
  {
    this.type = type;
    this.rules = rules;
    this.startTime = startTime;
    this.endTime = endTime;
    this.status = status;
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
  
  @Column(name="type", nullable=false)
  public int getType()
  {
    return this.type;
  }
  
  public void setType(int type)
  {
    this.type = type;
  }
  
  @Column(name="rules", nullable=false, length=65535)
  public String getRules()
  {
    return this.rules;
  }
  
  public void setRules(String rules)
  {
    this.rules = rules;
  }
  
  @Column(name="startTime")
  public String getStartTime()
  {
    return this.startTime;
  }
  
  public void setStartTime(String startTime)
  {
    this.startTime = startTime;
  }
  
  @Column(name="endTime")
  public String getEndTime()
  {
    return this.endTime;
  }
  
  public void setEndTime(String endTime)
  {
    this.endTime = endTime;
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
