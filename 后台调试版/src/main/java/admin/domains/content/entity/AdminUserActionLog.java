package admin.domains.content.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="admin_user_action_log", catalog="ecai")
public class AdminUserActionLog
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private int id;
  private int userId;
  private int actionId;
  private String data;
  private long millisecond;
  private int error;
  private String message;
  private String time;
  private String userAgent;
  
  public AdminUserActionLog() {}
  
  public AdminUserActionLog(int userId, int actionId, String data, int error, String time)
  {
    this.userId = userId;
    this.actionId = actionId;
    this.data = data;
    this.error = error;
    this.time = time;
  }
  
  public AdminUserActionLog(int userId, int actionId, String data, long millisecond, int error, String message, String time)
  {
    this.userId = userId;
    this.actionId = actionId;
    this.data = data;
    this.millisecond = millisecond;
    this.error = error;
    this.message = message;
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
  
  @Column(name="action_id", nullable=false)
  public int getActionId()
  {
    return this.actionId;
  }
  
  public void setActionId(int actionId)
  {
    this.actionId = actionId;
  }
  
  @Column(name="data", nullable=false, length=65535)
  public String getData()
  {
    return this.data;
  }
  
  public void setData(String data)
  {
    this.data = data;
  }
  
  @Column(name="millisecond")
  public long getMillisecond()
  {
    return this.millisecond;
  }
  
  public void setMillisecond(long millisecond)
  {
    this.millisecond = millisecond;
  }
  
  @Column(name="error", nullable=false)
  public int getError()
  {
    return this.error;
  }
  
  public void setError(int error)
  {
    this.error = error;
  }
  
  @Column(name="message", length=16777215)
  public String getMessage()
  {
    return this.message;
  }
  
  public void setMessage(String message)
  {
    this.message = message;
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
  
  @Column(name="user_agent", nullable=false, length=255)
  public String getUserAgent()
  {
    return this.userAgent;
  }
  
  public void setUserAgent(String userAgent)
  {
    this.userAgent = userAgent;
  }
}
