package lottery.domains.content.vo.user;

import lottery.domains.content.entity.UserBetsSameIpLog;
import org.apache.commons.lang.StringUtils;

public class UserBetsSameIpLogVO
{
  private int id;
  private String ip;
  private String address;
  private String users;
  private int usersCount;
  private String lastTime;
  private String lastUser;
  private int lastUserBetsId;
  private int times;
  private double amount;
  
  public UserBetsSameIpLogVO(UserBetsSameIpLog userBetsSameIpLog)
  {
    setId(userBetsSameIpLog.getId());
    setIp(userBetsSameIpLog.getIp());
    setAddress(userBetsSameIpLog.getAddress());
    if (StringUtils.isNotEmpty(userBetsSameIpLog.getUsers())) {
      setUsers(userBetsSameIpLog.getUsers().replaceAll("\\[", "").replaceAll("\\]", ""));
    }
    setUsersCount(userBetsSameIpLog.getUsersCount());
    setLastTime(userBetsSameIpLog.getLastTime());
    setLastUser(userBetsSameIpLog.getLastUser());
    setLastUserBetsId(userBetsSameIpLog.getLastUserBetsId());
    setTimes(userBetsSameIpLog.getTimes());
    setAmount(userBetsSameIpLog.getAmount());
  }
  
  public int getId()
  {
    return this.id;
  }
  
  public void setId(int id)
  {
    this.id = id;
  }
  
  public String getIp()
  {
    return this.ip;
  }
  
  public void setIp(String ip)
  {
    this.ip = ip;
  }
  
  public String getAddress()
  {
    return this.address;
  }
  
  public void setAddress(String address)
  {
    this.address = address;
  }
  
  public String getUsers()
  {
    return this.users;
  }
  
  public void setUsers(String users)
  {
    this.users = users;
  }
  
  public int getUsersCount()
  {
    return this.usersCount;
  }
  
  public void setUsersCount(int usersCount)
  {
    this.usersCount = usersCount;
  }
  
  public String getLastTime()
  {
    return this.lastTime;
  }
  
  public void setLastTime(String lastTime)
  {
    this.lastTime = lastTime;
  }
  
  public String getLastUser()
  {
    return this.lastUser;
  }
  
  public void setLastUser(String lastUser)
  {
    this.lastUser = lastUser;
  }
  
  public int getLastUserBetsId()
  {
    return this.lastUserBetsId;
  }
  
  public void setLastUserBetsId(int lastUserBetsId)
  {
    this.lastUserBetsId = lastUserBetsId;
  }
  
  public int getTimes()
  {
    return this.times;
  }
  
  public void setTimes(int times)
  {
    this.times = times;
  }
  
  public double getAmount()
  {
    return this.amount;
  }
  
  public void setAmount(double amount)
  {
    this.amount = amount;
  }
}
