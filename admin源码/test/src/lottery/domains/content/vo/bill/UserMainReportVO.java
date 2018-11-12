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
    
    public UserMainReportVO() {
    }
    
    public UserMainReportVO(final String name) {
        this.name = name;
    }
    
    public void addBean(final UserMainReport bean) {
        this.recharge += bean.getRecharge();
        this.withdrawals += bean.getWithdrawals();
        this.transIn += bean.getTransIn();
        this.transOut += bean.getTransOut();
        this.accountIn += bean.getAccountIn();
        this.accountOut += bean.getAccountOut();
        this.activity += bean.getActivity();
    }
    
    public void addBean(final UserMainReportVO bean) {
        this.recharge += bean.getRecharge();
        this.withdrawals += bean.getWithdrawals();
        this.transIn += bean.getTransIn();
        this.transOut += bean.getTransOut();
        this.accountIn += bean.getAccountIn();
        this.accountOut += bean.getAccountOut();
        this.activity += bean.getActivity();
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public double getRecharge() {
        return this.recharge;
    }
    
    public void setRecharge(final double recharge) {
        this.recharge = recharge;
    }
    
    public double getWithdrawals() {
        return this.withdrawals;
    }
    
    public void setWithdrawals(final double withdrawals) {
        this.withdrawals = withdrawals;
    }
    
    public double getTransIn() {
        return this.transIn;
    }
    
    public void setTransIn(final double transIn) {
        this.transIn = transIn;
    }
    
    public double getTransOut() {
        return this.transOut;
    }
    
    public void setTransOut(final double transOut) {
        this.transOut = transOut;
    }
    
    public double getAccountIn() {
        return this.accountIn;
    }
    
    public void setAccountIn(final double accountIn) {
        this.accountIn = accountIn;
    }
    
    public double getAccountOut() {
        return this.accountOut;
    }
    
    public void setAccountOut(final double accountOut) {
        this.accountOut = accountOut;
    }
    
    public double getActivity() {
        return this.activity;
    }
    
    public void setActivity(final double activity) {
        this.activity = activity;
    }
    
    public boolean isHasMore() {
        return this.hasMore;
    }
    
    public void setHasMore(final boolean hasMore) {
        this.hasMore = hasMore;
    }
}
