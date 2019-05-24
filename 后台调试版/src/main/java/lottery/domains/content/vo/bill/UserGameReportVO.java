package lottery.domains.content.vo.bill;

import lottery.domains.content.entity.UserGameReport;

public class UserGameReportVO
{
  private String name;
  private int userId;
  private int platformId;
  private double transIn;
  private double transOut;
  private double prize;
  private double waterReturn;
  private double proxyReturn;
  private double activity;
  private double billingOrder;
  private String time;
  private boolean hasMore;
  
  public UserGameReportVO() {}
  
  public UserGameReportVO(String name)
  {
    this.name = name;
  }
  
  public void addBean(UserGameReport bean)
  {
    this.transIn += bean.getTransIn();
    this.transOut += bean.getTransOut();
    this.waterReturn += bean.getWaterReturn();
    this.proxyReturn += bean.getProxyReturn();
    this.activity += bean.getActivity();
    this.billingOrder += bean.getBillingOrder();
    this.prize += bean.getPrize();
  }
  
  public void addBean(UserGameReportVO bean)
  {
    this.transIn += bean.getTransIn();
    this.transOut += bean.getTransOut();
    this.waterReturn += bean.getWaterReturn();
    this.proxyReturn += bean.getProxyReturn();
    this.activity += bean.getActivity();
    this.billingOrder += bean.getBillingOrder();
    this.prize += bean.getPrize();
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  public int getUserId()
  {
    return this.userId;
  }
  
  public void setUserId(int userId)
  {
    this.userId = userId;
  }
  
  public int getPlatformId()
  {
    return this.platformId;
  }
  
  public void setPlatformId(int platformId)
  {
    this.platformId = platformId;
  }
  
  public double getTransIn()
  {
    return this.transIn;
  }
  
  public void setTransIn(double transIn)
  {
    this.transIn = transIn;
  }
  
  public double getTransOut()
  {
    return this.transOut;
  }
  
  public void setTransOut(double transOut)
  {
    this.transOut = transOut;
  }
  
  public double getPrize()
  {
    return this.prize;
  }
  
  public void setPrize(double prize)
  {
    this.prize = prize;
  }
  
  public double getWaterReturn()
  {
    return this.waterReturn;
  }
  
  public void setWaterReturn(double waterReturn)
  {
    this.waterReturn = waterReturn;
  }
  
  public double getProxyReturn()
  {
    return this.proxyReturn;
  }
  
  public void setProxyReturn(double proxyReturn)
  {
    this.proxyReturn = proxyReturn;
  }
  
  public double getActivity()
  {
    return this.activity;
  }
  
  public void setActivity(double activity)
  {
    this.activity = activity;
  }
  
  public double getBillingOrder()
  {
    return this.billingOrder;
  }
  
  public void setBillingOrder(double billingOrder)
  {
    this.billingOrder = billingOrder;
  }
  
  public String getTime()
  {
    return this.time;
  }
  
  public void setTime(String time)
  {
    this.time = time;
  }
  
  public boolean isHasMore()
  {
    return this.hasMore;
  }
  
  public void setHasMore(boolean hasMore)
  {
    this.hasMore = hasMore;
  }
}
