package lottery.domains.content.vo.bill;

import lottery.domains.content.entity.UserMainReport;

public class UserMainReportVO
{
  private String name;
  private double recharge;
  private double withdrawals;
  private double transIn;
  private double transOut;
  private double accountIn;
  private double accountOut;
  private double activity;
  private boolean hasMore;
  
  public UserMainReportVO() {}
  
  public UserMainReportVO(String name)
  {
    this.name = name;
  }
  
  public void addBean(UserMainReport bean)
  {
    this.recharge += bean.getRecharge();
    this.withdrawals += bean.getWithdrawals();
    this.transIn += bean.getTransIn();
    this.transOut += bean.getTransOut();
    this.accountIn += bean.getAccountIn();
    this.accountOut += bean.getAccountOut();
    this.activity += bean.getActivity();
  }
  
  public void addBean(UserMainReportVO bean)
  {
    this.recharge += bean.getRecharge();
    this.withdrawals += bean.getWithdrawals();
    this.transIn += bean.getTransIn();
    this.transOut += bean.getTransOut();
    this.accountIn += bean.getAccountIn();
    this.accountOut += bean.getAccountOut();
    this.activity += bean.getActivity();
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  public double getRecharge()
  {
    return this.recharge;
  }
  
  public void setRecharge(double recharge)
  {
    this.recharge = recharge;
  }
  
  public double getWithdrawals()
  {
    return this.withdrawals;
  }
  
  public void setWithdrawals(double withdrawals)
  {
    this.withdrawals = withdrawals;
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
  
  public double getAccountIn()
  {
    return this.accountIn;
  }
  
  public void setAccountIn(double accountIn)
  {
    this.accountIn = accountIn;
  }
  
  public double getAccountOut()
  {
    return this.accountOut;
  }
  
  public void setAccountOut(double accountOut)
  {
    this.accountOut = accountOut;
  }
  
  public double getActivity()
  {
    return this.activity;
  }
  
  public void setActivity(double activity)
  {
    this.activity = activity;
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
