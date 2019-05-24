package lottery.domains.content.vo.activity;

import lottery.domains.content.entity.ActivityRedPacketRainBill;
import lottery.domains.content.vo.user.UserVO;
import lottery.domains.pool.LotteryDataFactory;

public class ActivityRedPacketRainBillVO
{
  private String username;
  private ActivityRedPacketRainBill bean;
  
  public ActivityRedPacketRainBillVO(ActivityRedPacketRainBill bean, LotteryDataFactory lotteryDataFactory)
  {
    this.bean = bean;
    UserVO tmpUser = lotteryDataFactory.getUser(bean.getUserId());
    if (tmpUser != null) {
      this.username = tmpUser.getUsername();
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
  
  public ActivityRedPacketRainBill getBean()
  {
    return this.bean;
  }
  
  public void setBean(ActivityRedPacketRainBill bean)
  {
    this.bean = bean;
  }
}
