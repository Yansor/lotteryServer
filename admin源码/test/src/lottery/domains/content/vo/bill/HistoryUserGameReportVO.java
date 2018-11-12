package lottery.domains.content.vo.bill;

import lottery.domains.content.entity.HistoryUserGameReport;

public class HistoryUserGameReportVO
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
    
    public HistoryUserGameReportVO() {
    }
    
    public HistoryUserGameReportVO(final String name) {
        this.name = name;
    }
    
    public void addBean(final HistoryUserGameReport bean) {
        this.transIn += bean.getTransIn();
        this.transOut += bean.getTransOut();
        this.waterReturn += bean.getWaterReturn();
        this.proxyReturn += bean.getProxyReturn();
        this.activity += bean.getActivity();
        this.billingOrder += bean.getBillingOrder();
        this.prize += bean.getPrize();
    }
    
    public void addBean(final HistoryUserGameReportVO bean) {
        this.transIn += bean.getTransIn();
        this.transOut += bean.getTransOut();
        this.waterReturn += bean.getWaterReturn();
        this.proxyReturn += bean.getProxyReturn();
        this.activity += bean.getActivity();
        this.billingOrder += bean.getBillingOrder();
        this.prize += bean.getPrize();
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public int getUserId() {
        return this.userId;
    }
    
    public void setUserId(final int userId) {
        this.userId = userId;
    }
    
    public int getPlatformId() {
        return this.platformId;
    }
    
    public void setPlatformId(final int platformId) {
        this.platformId = platformId;
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
    
    public double getPrize() {
        return this.prize;
    }
    
    public void setPrize(final double prize) {
        this.prize = prize;
    }
    
    public double getWaterReturn() {
        return this.waterReturn;
    }
    
    public void setWaterReturn(final double waterReturn) {
        this.waterReturn = waterReturn;
    }
    
    public double getProxyReturn() {
        return this.proxyReturn;
    }
    
    public void setProxyReturn(final double proxyReturn) {
        this.proxyReturn = proxyReturn;
    }
    
    public double getActivity() {
        return this.activity;
    }
    
    public void setActivity(final double activity) {
        this.activity = activity;
    }
    
    public double getBillingOrder() {
        return this.billingOrder;
    }
    
    public void setBillingOrder(final double billingOrder) {
        this.billingOrder = billingOrder;
    }
    
    public String getTime() {
        return this.time;
    }
    
    public void setTime(final String time) {
        this.time = time;
    }
    
    public boolean isHasMore() {
        return this.hasMore;
    }
    
    public void setHasMore(final boolean hasMore) {
        this.hasMore = hasMore;
    }
}
