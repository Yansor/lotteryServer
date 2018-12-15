package lottery.domains.content.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="user_action_log", catalog="ecai")
public class UserActionLog
  implements Serializable
{
  private static final long serialVersionUID = -7560535410474004055L;
  private int id;
  private int userId;
  private String ip;
  private String address;
  private String action;
  private String time;
  
  public UserActionLog() {}
  
  public UserActionLog(int userId, String ip, String action, String time)
  {
    this.userId = userId;
    this.ip = ip;
    this.action = action;
    this.time = time;
  }
  
  public UserActionLog(int userId, String ip, String address, String action, String time)
  {
    this.userId = userId;
    this.ip = ip;
    this.address = address;
    this.action = action;
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
  
  @Column(name="ip", nullable=false, length=128)
  public String getIp()
  {
    return this.ip;
  }
  
  public void setIp(String ip)
  {
    this.ip = ip;
  }
  
  @Column(name="address")
  public String getAddress()
  {
    return this.address;
  }
  
  public void setAddress(String address)
  {
    this.address = address;
  }
  
  @Column(name="action", nullable=false, length=65535)
  public String getAction()
  {
    return this.action;
  }
  
  public void setAction(String action)
  {
    this.action = action;
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
}
