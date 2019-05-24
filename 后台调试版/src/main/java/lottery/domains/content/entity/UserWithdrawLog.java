package lottery.domains.content.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="user_withdraw_log", catalog="ecai")
public class UserWithdrawLog
  implements Serializable
{
  private static final long serialVersionUID = -7560535410474004055L;
  private int id;
  private int userId;
  private int adminUserId;
  private String billno;
  private String action;
  private String time;
  
  public UserWithdrawLog() {}
  
  public UserWithdrawLog(String billno, int userId, int adminUserId, String action, String time)
  {
    this.userId = userId;
    this.action = action;
    this.time = time;
    this.billno = billno;
    this.adminUserId = adminUserId;
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
  
  @Column(name="admin_user_id", nullable=false)
  public int getAdminUserId()
  {
    return this.adminUserId;
  }
  
  public void setAdminUserId(int adminUserId)
  {
    this.adminUserId = adminUserId;
  }
  
  @Column(name="billno", nullable=false)
  public String getBillno()
  {
    return this.billno;
  }
  
  public void setBillno(String billno)
  {
    this.billno = billno;
  }
}
