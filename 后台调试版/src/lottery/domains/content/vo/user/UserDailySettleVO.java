package lottery.domains.content.vo.user;

import lottery.domains.content.entity.UserDailySettle;
import lottery.domains.pool.LotteryDataFactory;

public class UserDailySettleVO
{
  private int id;
  private String username;
  private String scaleLevel;
  private String lossLevel;
  private String salesLevel;
  private String userLevel;
  private int minValidUser;
  private String createTime;
  private String agreeTime;
  private double totalAmount;
  private int status;
  private int fixed;
  private double minScale;
  private double maxScale;
  
  public UserDailySettleVO(UserDailySettle bean, LotteryDataFactory dataFactory)
  {
    this.id = bean.getId();
    UserVO user = dataFactory.getUser(bean.getUserId());
    if (user == null) {
      this.username = ("未知[" + bean.getUserId() + "]");
    } else {
      this.username = user.getUsername();
    }
    this.scaleLevel = bean.getScaleLevel();
    this.salesLevel = bean.getSalesLevel();
    this.lossLevel = bean.getLossLevel();
    this.minValidUser = bean.getMinValidUser();
    this.createTime = bean.getCreateTime();
    this.agreeTime = bean.getAgreeTime();
    this.totalAmount = bean.getTotalAmount();
    this.status = bean.getStatus();
    this.fixed = bean.getFixed();
    this.minScale = bean.getMinScale();
    this.maxScale = bean.getMaxScale();
    this.userLevel = bean.getUserLevel();
  }
  
  public int getId()
  {
    return this.id;
  }
  
  public void setId(int id)
  {
    this.id = id;
  }
  
  public String getUsername()
  {
    return this.username;
  }
  
  public void setUsername(String username)
  {
    this.username = username;
  }
  
  public String getScaleLevel()
  {
    return this.scaleLevel;
  }
  
  public void setScaleLevel(String scaleLevel)
  {
    this.scaleLevel = scaleLevel;
  }
  
  public String getLossLevel()
  {
    return this.lossLevel;
  }
  
  public void setLossLevel(String lossLevel)
  {
    this.lossLevel = lossLevel;
  }
  
  public String getSalesLevel()
  {
    return this.salesLevel;
  }
  
  public void setSalesLevel(String salesLevel)
  {
    this.salesLevel = salesLevel;
  }
  
  public int getMinValidUser()
  {
    return this.minValidUser;
  }
  
  public void setMinValidUser(int minValidUser)
  {
    this.minValidUser = minValidUser;
  }
  
  public String getCreateTime()
  {
    return this.createTime;
  }
  
  public void setCreateTime(String createTime)
  {
    this.createTime = createTime;
  }
  
  public String getAgreeTime()
  {
    return this.agreeTime;
  }
  
  public void setAgreeTime(String agreeTime)
  {
    this.agreeTime = agreeTime;
  }
  
  public double getTotalAmount()
  {
    return this.totalAmount;
  }
  
  public void setTotalAmount(double totalAmount)
  {
    this.totalAmount = totalAmount;
  }
  
  public int getStatus()
  {
    return this.status;
  }
  
  public void setStatus(int status)
  {
    this.status = status;
  }
  
  public int getFixed()
  {
    return this.fixed;
  }
  
  public void setFixed(int fixed)
  {
    this.fixed = fixed;
  }
  
  public double getMinScale()
  {
    return this.minScale;
  }
  
  public void setMinScale(double minScale)
  {
    this.minScale = minScale;
  }
  
  public double getMaxScale()
  {
    return this.maxScale;
  }
  
  public void setMaxScale(double maxScale)
  {
    this.maxScale = maxScale;
  }
  
  public String getUserLevel()
  {
    return this.userLevel;
  }
  
  public void setUserLevel(String userLevel)
  {
    this.userLevel = userLevel;
  }
}
