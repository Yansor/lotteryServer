package lottery.domains.content.vo.user;

import java.util.ArrayList;
import java.util.List;
import javautils.StringUtil;
import lottery.domains.content.entity.User;
import lottery.domains.pool.LotteryDataFactory;

public class UserOnlineVO
{
  private String username;
  private double totalMoney;
  private double lotteryMoney;
  private double baccaratMoney;
  private List<String> levelUsers = new ArrayList();
  private String loginTime;
  
  public UserOnlineVO(User bean, LotteryDataFactory lotteryDataFactory)
  {
    this.username = bean.getUsername();
    this.totalMoney = bean.getTotalMoney();
    this.lotteryMoney = bean.getLotteryMoney();
    this.baccaratMoney = bean.getBaccaratMoney();
    this.loginTime = bean.getLoginTime();
    if (StringUtil.isNotNull(bean.getUpids()))
    {
      String[] ids = bean.getUpids().replaceAll("\\[|\\]", "").split(",");
      String[] arrayOfString1;
      int j = (arrayOfString1 = ids).length;
      for (int i = 0; i < j; i++)
      {
        String id = arrayOfString1[i];
        UserVO thisUser = lotteryDataFactory.getUser(Integer.parseInt(id));
        if (thisUser != null) {
          this.levelUsers.add(thisUser.getUsername());
        }
      }
    }
  }
  
  public String getUsername()
  {
    return this.username;
  }
  
  public void setUsername(String username)
  {
    this.username = username;
  }
  
  public double getTotalMoney()
  {
    return this.totalMoney;
  }
  
  public void setTotalMoney(double totalMoney)
  {
    this.totalMoney = totalMoney;
  }
  
  public double getLotteryMoney()
  {
    return this.lotteryMoney;
  }
  
  public void setLotteryMoney(double lotteryMoney)
  {
    this.lotteryMoney = lotteryMoney;
  }
  
  public double getBaccaratMoney()
  {
    return this.baccaratMoney;
  }
  
  public void setBaccaratMoney(double baccaratMoney)
  {
    this.baccaratMoney = baccaratMoney;
  }
  
  public List<String> getLevelUsers()
  {
    return this.levelUsers;
  }
  
  public void setLevelUsers(List<String> levelUsers)
  {
    this.levelUsers = levelUsers;
  }
  
  public String getLoginTime()
  {
    return this.loginTime;
  }
  
  public void setLoginTime(String loginTime)
  {
    this.loginTime = loginTime;
  }
}
