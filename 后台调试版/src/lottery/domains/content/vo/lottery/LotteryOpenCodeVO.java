package lottery.domains.content.vo.lottery;

import lottery.domains.content.entity.Lottery;
import lottery.domains.content.entity.LotteryOpenCode;
import lottery.domains.content.vo.user.UserVO;
import lottery.domains.pool.LotteryDataFactory;

public class LotteryOpenCodeVO
{
  private String lotteryName;
  private LotteryOpenCode bean;
  private String username;
  
  public LotteryOpenCodeVO(LotteryOpenCode bean, LotteryDataFactory df)
  {
    this.bean = bean;
    Lottery lottery = df.getLottery(bean.getLottery());
    if (lottery != null) {
      this.lotteryName = lottery.getShowName();
    }
    if (bean.getUserId() != null)
    {
      UserVO user = df.getUser(bean.getUserId().intValue());
      if (user != null) {
        this.username = user.getUsername();
      }
    }
  }
  
  public String getLotteryName()
  {
    return this.lotteryName;
  }
  
  public void setLotteryName(String lotteryName)
  {
    this.lotteryName = lotteryName;
  }
  
  public LotteryOpenCode getBean()
  {
    return this.bean;
  }
  
  public void setBean(LotteryOpenCode bean)
  {
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
}
