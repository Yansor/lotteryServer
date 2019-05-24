package lottery.domains.content.vo.activity;

import lottery.domains.content.entity.ActivityBindBill;
import lottery.domains.content.entity.PaymentBank;
import lottery.domains.content.entity.User;
import lottery.domains.content.vo.user.UserVO;
import lottery.domains.pool.LotteryDataFactory;

public class ActivityBindBillVO
{
  private String username;
  private double locatePoint;
  private String upperUser;
  private boolean isRecharge;
  private boolean isCost;
  private String bindBank;
  private ActivityBindBill bean;
  
  public ActivityBindBillVO(ActivityBindBill bean, User uBean, LotteryDataFactory lotteryDataFactory)
  {
    this.bean = bean;
    UserVO user = lotteryDataFactory.getUser(bean.getUserId());
    if (user != null) {
      this.username = user.getUsername();
    }
    if (uBean != null)
    {
      this.locatePoint = uBean.getLocatePoint();
      if (uBean.getUpid() != 0)
      {
        UserVO upper = lotteryDataFactory.getUser(uBean.getUpid());
        if (upper != null) {
          this.upperUser = upper.getUsername();
        }
      }
    }
    PaymentBank bindBank = lotteryDataFactory.getPaymentBank(bean.getBindBank());
    if (bindBank != null) {
      this.bindBank = bindBank.getName();
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
  
  public double getLocatePoint()
  {
    return this.locatePoint;
  }
  
  public void setLocatePoint(double locatePoint)
  {
    this.locatePoint = locatePoint;
  }
  
  public String getUpperUser()
  {
    return this.upperUser;
  }
  
  public void setUpperUser(String upperUser)
  {
    this.upperUser = upperUser;
  }
  
  public boolean isRecharge()
  {
    return this.isRecharge;
  }
  
  public void setRecharge(boolean isRecharge)
  {
    this.isRecharge = isRecharge;
  }
  
  public boolean isCost()
  {
    return this.isCost;
  }
  
  public void setCost(boolean isCost)
  {
    this.isCost = isCost;
  }
  
  public String getBindBank()
  {
    return this.bindBank;
  }
  
  public void setBindBank(String bindBank)
  {
    this.bindBank = bindBank;
  }
  
  public ActivityBindBill getBean()
  {
    return this.bean;
  }
  
  public void setBean(ActivityBindBill bean)
  {
    this.bean = bean;
  }
}
