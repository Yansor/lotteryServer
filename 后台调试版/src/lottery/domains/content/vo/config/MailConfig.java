package lottery.domains.content.vo.config;

import java.util.ArrayList;
import java.util.List;

public class MailConfig
{
  private String username;
  private String personal;
  private String password;
  private String host;
  private int bet;
  private int open;
  private int recharge;
  private List<String> receiveMails = new ArrayList();
  
  public String getUsername()
  {
    return this.username;
  }
  
  public void setUsername(String username)
  {
    this.username = username;
  }
  
  public String getPersonal()
  {
    return this.personal;
  }
  
  public void setPersonal(String personal)
  {
    this.personal = personal;
  }
  
  public String getPassword()
  {
    return this.password;
  }
  
  public void setPassword(String password)
  {
    this.password = password;
  }
  
  public String getHost()
  {
    return this.host;
  }
  
  public void setHost(String host)
  {
    this.host = host;
  }
  
  public List<String> getReceiveMails()
  {
    return this.receiveMails;
  }
  
  public void setReceiveMails(List<String> receiveMails)
  {
    this.receiveMails = receiveMails;
  }
  
  public int getBet()
  {
    return this.bet;
  }
  
  public void setBet(int bet)
  {
    this.bet = bet;
  }
  
  public int getOpen()
  {
    return this.open;
  }
  
  public void setOpen(int open)
  {
    this.open = open;
  }
  
  public int getRecharge()
  {
    return this.recharge;
  }
  
  public void setRecharge(int recharge)
  {
    this.recharge = recharge;
  }
  
  public void addReceiveMail(String mail)
  {
    this.receiveMails.add(mail);
  }
}
