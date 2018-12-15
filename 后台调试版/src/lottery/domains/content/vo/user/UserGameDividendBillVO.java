package lottery.domains.content.vo.user;

import lottery.domains.content.entity.UserGameDividendBill;
import lottery.domains.pool.LotteryDataFactory;

public class UserGameDividendBillVO
{
  private String username;
  private UserGameDividendBill bean;
  
  public UserGameDividendBillVO(UserGameDividendBill bean, LotteryDataFactory dataFactory)
  {
    UserVO user = dataFactory.getUser(bean.getUserId());
    if (user == null) {
      this.username = ("未知[" + bean.getUserId() + "]");
    } else {
      this.username = user.getUsername();
    }
    this.bean = bean;
  }
  
  public String getUsername()
  {
    return this.username;
  }
  
  public void setUsername(String username)
  {
    this.username = username;
  }
  
  public UserGameDividendBill getBean()
  {
    return this.bean;
  }
  
  public void setBean(UserGameDividendBill bean)
  {
    this.bean = bean;
  }
}
