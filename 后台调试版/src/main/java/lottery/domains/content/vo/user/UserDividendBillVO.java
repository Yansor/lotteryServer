package lottery.domains.content.vo.user;

import java.util.LinkedList;
import java.util.List;
import lottery.domains.content.entity.UserDividendBill;
import lottery.domains.pool.LotteryDataFactory;

public class UserDividendBillVO
{
  private String username;
  private UserDividendBill bean;
  private List<String> userLevels = new LinkedList();
  
  public UserDividendBillVO(UserDividendBill bean, LotteryDataFactory dataFactory)
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
  
  public UserDividendBill getBean()
  {
    return this.bean;
  }
  
  public void setBean(UserDividendBill bean)
  {
    this.bean = bean;
  }
  
  public List<String> getUserLevels()
  {
    return this.userLevels;
  }
  
  public void setUserLevels(List<String> userLevels)
  {
    this.userLevels = userLevels;
  }
}
