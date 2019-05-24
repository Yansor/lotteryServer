package lottery.domains.content.vo.chart;

public class RechargeWithdrawTotal
{
  private int totalRechargeCount;
  private double totalRechargeMoney;
  private double totalReceiveFeeMoney;
  private int totalWithdrawCount;
  private double totalWithdrawMoney;
  private double totalActualReceiveMoney;
  private double totalRechargeWithdrawDiff;
  
  public RechargeWithdrawTotal(int totalRechargeCount, double totalRechargeMoney, double totalReceiveFeeMoney, int totalWithdrawCount, double totalWithdrawMoney, double totalActualReceiveMoney, double totalRechargeWithdrawDiff)
  {
    this.totalRechargeCount = totalRechargeCount;
    this.totalRechargeMoney = totalRechargeMoney;
    this.totalReceiveFeeMoney = totalReceiveFeeMoney;
    this.totalWithdrawCount = totalWithdrawCount;
    this.totalWithdrawMoney = totalWithdrawMoney;
    this.totalActualReceiveMoney = totalActualReceiveMoney;
    this.totalRechargeWithdrawDiff = totalRechargeWithdrawDiff;
  }
  
  public int getTotalRechargeCount()
  {
    return this.totalRechargeCount;
  }
  
  public void setTotalRechargeCount(int totalRechargeCount)
  {
    this.totalRechargeCount = totalRechargeCount;
  }
  
  public double getTotalRechargeMoney()
  {
    return this.totalRechargeMoney;
  }
  
  public void setTotalRechargeMoney(double totalRechargeMoney)
  {
    this.totalRechargeMoney = totalRechargeMoney;
  }
  
  public double getTotalReceiveFeeMoney()
  {
    return this.totalReceiveFeeMoney;
  }
  
  public void setTotalReceiveFeeMoney(double totalReceiveFeeMoney)
  {
    this.totalReceiveFeeMoney = totalReceiveFeeMoney;
  }
  
  public int getTotalWithdrawCount()
  {
    return this.totalWithdrawCount;
  }
  
  public void setTotalWithdrawCount(int totalWithdrawCount)
  {
    this.totalWithdrawCount = totalWithdrawCount;
  }
  
  public double getTotalWithdrawMoney()
  {
    return this.totalWithdrawMoney;
  }
  
  public void setTotalWithdrawMoney(double totalWithdrawMoney)
  {
    this.totalWithdrawMoney = totalWithdrawMoney;
  }
  
  public double getTotalActualReceiveMoney()
  {
    return this.totalActualReceiveMoney;
  }
  
  public void setTotalActualReceiveMoney(double totalActualReceiveMoney)
  {
    this.totalActualReceiveMoney = totalActualReceiveMoney;
  }
  
  public double getTotalRechargeWithdrawDiff()
  {
    return this.totalRechargeWithdrawDiff;
  }
  
  public void setTotalRechargeWithdrawDiff(double totalRechargeWithdrawDiff)
  {
    this.totalRechargeWithdrawDiff = totalRechargeWithdrawDiff;
  }
}
