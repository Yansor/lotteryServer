package lottery.domains.content.vo.user;

import admin.domains.content.entity.AdminUser;
import lottery.domains.content.entity.UserWithdrawLog;

public class UserWithdrawLogVO
{
  private int id;
  private int userId;
  private String username;
  private int adminUserId;
  private String action;
  private String time;
  
  public UserWithdrawLogVO() {}
  
  public UserWithdrawLogVO(UserWithdrawLog bean, AdminUser df)
  {
    if (df != null) {
      this.username = df.getUsername();
    }
    this.id = bean.getId();
    this.userId = bean.getUserId();
    this.action = bean.getAction();
    this.time = bean.getTime();
  }
  
  public int getId()
  {
    return this.id;
  }
  
  public void setId(int id)
  {
    this.id = id;
  }
  
  public int getUserId()
  {
    return this.userId;
  }
  
  public void setUserId(int userId)
  {
    this.userId = userId;
  }
  
  public int getAdminUserId()
  {
    return this.adminUserId;
  }
  
  public void setAdminUserId(int adminUserId)
  {
    this.adminUserId = adminUserId;
  }
  
  public String getTime()
  {
    return this.time;
  }
  
  public void setTime(String time)
  {
    this.time = time;
  }
  
  public String getUsername()
  {
    return this.username;
  }
  
  public void setUsername(String username)
  {
    this.username = username;
  }
  
  public String getAction()
  {
    return this.action;
  }
  
  public void setAction(String action)
  {
    this.action = action;
  }
}
