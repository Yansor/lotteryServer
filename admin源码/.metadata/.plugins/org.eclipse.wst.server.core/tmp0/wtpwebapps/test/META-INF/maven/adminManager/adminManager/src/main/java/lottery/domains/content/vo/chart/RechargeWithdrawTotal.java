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
    
    public RechargeWithdrawTotal(final int totalRechargeCount, final double totalRechargeMoney, final double totalReceiveFeeMoney, final int totalWithdrawCount, final double totalWithdrawMoney, final double totalActualReceiveMoney, final double totalRechargeWithdrawDiff) {
        this.totalRechargeCount = totalRechargeCount;
        this.totalRechargeMoney = totalRechargeMoney;
        this.totalReceiveFeeMoney = totalReceiveFeeMoney;
        this.totalWithdrawCount = totalWithdrawCount;
        this.totalWithdrawMoney = totalWithdrawMoney;
        this.totalActualReceiveMoney = totalActualReceiveMoney;
        this.totalRechargeWithdrawDiff = totalRechargeWithdrawDiff;
    }
    
    public int getTotalRechargeCount() {
        return this.totalRechargeCount;
    }
    
    public void setTotalRechargeCount(final int totalRechargeCount) {
        this.totalRechargeCount = totalRechargeCount;
    }
    
    public double getTotalRechargeMoney() {
        return this.totalRechargeMoney;
    }
    
    public void setTotalRechargeMoney(final double totalRechargeMoney) {
        this.totalRechargeMoney = totalRechargeMoney;
    }
    
    public double getTotalReceiveFeeMoney() {
        return this.totalReceiveFeeMoney;
    }
    
    public void setTotalReceiveFeeMoney(final double totalReceiveFeeMoney) {
        this.totalReceiveFeeMoney = totalReceiveFeeMoney;
    }
    
    public int getTotalWithdrawCount() {
        return this.totalWithdrawCount;
    }
    
    public void setTotalWithdrawCount(final int totalWithdrawCount) {
        this.totalWithdrawCount = totalWithdrawCount;
    }
    
    public double getTotalWithdrawMoney() {
        return this.totalWithdrawMoney;
    }
    
    public void setTotalWithdrawMoney(final double totalWithdrawMoney) {
        this.totalWithdrawMoney = totalWithdrawMoney;
    }
    
    public double getTotalActualReceiveMoney() {
        return this.totalActualReceiveMoney;
    }
    
    public void setTotalActualReceiveMoney(final double totalActualReceiveMoney) {
        this.totalActualReceiveMoney = totalActualReceiveMoney;
    }
    
    public double getTotalRechargeWithdrawDiff() {
        return this.totalRechargeWithdrawDiff;
    }
    
    public void setTotalRechargeWithdrawDiff(final double totalRechargeWithdrawDiff) {
        this.totalRechargeWithdrawDiff = totalRechargeWithdrawDiff;
    }
}
