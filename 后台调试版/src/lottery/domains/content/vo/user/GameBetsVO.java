package lottery.domains.content.vo.user;

import lottery.domains.content.entity.GameBets;
import lottery.domains.content.entity.SysPlatform;
import lottery.domains.pool.LotteryDataFactory;

public class GameBetsVO
{
  private String username;
  private String platform;
  private GameBets bean;
  
  public GameBetsVO(GameBets bean, LotteryDataFactory lotteryDataFactory)
  {
    this.bean = bean;
    UserVO tmpUser = lotteryDataFactory.getUser(bean.getUserId());
    if (tmpUser != null) {
      this.username = tmpUser.getUsername();
    } else {
      this.username = ("未知[" + bean.getUserId() + "]");
    }
    SysPlatform platform = lotteryDataFactory.getSysPlatform(bean.getPlatformId());
    if (platform != null) {
      this.platform = platform.getName();
    } else {
      this.platform = "未知";
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
  
  public String getPlatform()
  {
    return this.platform;
  }
  
  public void setPlatform(String platform)
  {
    this.platform = platform;
  }
  
  public GameBets getBean()
  {
    return this.bean;
  }
  
  public void setBean(GameBets bean)
  {
    this.bean = bean;
  }
}
