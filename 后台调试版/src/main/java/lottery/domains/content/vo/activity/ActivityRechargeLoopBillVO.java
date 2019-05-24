package lottery.domains.content.vo.activity;

import lottery.domains.content.entity.ActivityRechargeLoopBill;
import lottery.domains.content.vo.user.UserVO;
import lottery.domains.pool.LotteryDataFactory;

public class ActivityRechargeLoopBillVO
{
  private String username;
  private ActivityRechargeLoopBill bean;
  
  public ActivityRechargeLoopBillVO(ActivityRechargeLoopBill bean, LotteryDataFactory lotteryDataFactory)
  {
    this.bean = bean;
    UserVO user = lotteryDataFactory.getUser(bean.getUserId());
    if (user != null) {
      this.username = user.getUsername();
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
  
  public ActivityRechargeLoopBill getBean()
  {
    return this.bean;
  }
  
  public void setBean(ActivityRechargeLoopBill bean)
  {
    this.bean = bean;
  }
}
