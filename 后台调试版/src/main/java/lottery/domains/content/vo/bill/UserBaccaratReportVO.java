package lottery.domains.content.vo.bill;

import lottery.domains.content.entity.UserBaccaratReport;

public class UserBaccaratReportVO
{
  private String name;
  private double transIn;
  private double transOut;
  private double spend;
  private double prize;
  private double waterReturn;
  private double proxyReturn;
  private double cancelOrder;
  private double activity;
  private double billingOrder;
  
  public UserBaccaratReportVO() {}
  
  public UserBaccaratReportVO(String name)
  {
    this.name = name;
  }
  
  public void addBean(UserBaccaratReport bean)
  {
    this.transIn += bean.getTransIn();
    this.transOut += bean.getTransOut();
    this.spend += bean.getSpend();
    this.prize += bean.getPrize();
    this.waterReturn += bean.getWaterReturn();
    this.proxyReturn += bean.getProxyReturn();
    this.cancelOrder += bean.getCancelOrder();
    this.activity += bean.getActivity();
    this.billingOrder += bean.getBillingOrder();
  }
  
  public void addBean(UserBaccaratReportVO bean)
  {
    this.transIn += bean.getTransIn();
    this.transOut += bean.getTransOut();
    this.spend += bean.getSpend();
    this.prize += bean.getPrize();
    this.waterReturn += bean.getWaterReturn();
    this.proxyReturn += bean.getProxyReturn();
    this.cancelOrder += bean.getCancelOrder();
    this.activity += bean.getActivity();
    this.billingOrder += bean.getBillingOrder();
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String name)
  {
    this.name = name;
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
  
  public double getSpend()
  {
    return this.spend;
  }
  
  public void setSpend(double spend)
  {
    this.spend = spend;
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
  
  public double getCancelOrder()
  {
    return this.cancelOrder;
  }
  
  public void setCancelOrder(double cancelOrder)
  {
    this.cancelOrder = cancelOrder;
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
}
