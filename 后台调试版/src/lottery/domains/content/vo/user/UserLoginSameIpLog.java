package lottery.domains.content.vo.user;

public class UserLoginSameIpLog
{
  private String ip;
  private String address;
  private String users;
  
  public UserLoginSameIpLog(String ip, String address, String users)
  {
    this.ip = ip;
    this.address = address;
    this.users = users;
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
}
