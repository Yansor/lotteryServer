package lottery.domains.content.vo.user;

import lottery.domains.pool.LotteryDataFactory;
import lottery.domains.content.entity.UserDailySettleBill;

public class UserDailySettleBillVO
{
    private int id;
    private String username;
    private double scale;
    private int validUser;
    private int minValidUser;
    private String indicateDate;
    private double billingOrder;
    private double thisLoss;
    private double calAmount;
    private double userAmount;
    private double lowerTotalAmount;
    private double lowerPaidAmount;
    private double totalReceived;
    private String settleTime;
    private int issueType;
    private int status;
    private String remarks;
    
    public UserDailySettleBillVO(final UserDailySettleBill bean, final LotteryDataFactory dataFactory) {
        this.id = bean.getId();
        final UserVO user = dataFactory.getUser(bean.getUserId());
        if (user == null) {
            this.username = "未知[" + bean.getUserId() + "]";
        }
        else {
            this.username = user.getUsername();
        }
        this.thisLoss = bean.getThisLoss();
        this.scale = bean.getScale();
        this.validUser = bean.getValidUser();
        this.minValidUser = bean.getMinValidUser();
        this.indicateDate = bean.getIndicateDate();
        this.billingOrder = bean.getBillingOrder();
        this.calAmount = bean.getCalAmount();
        this.userAmount = bean.getUserAmount();
        this.lowerTotalAmount = bean.getLowerTotalAmount();
        this.lowerPaidAmount = bean.getLowerPaidAmount();
        this.totalReceived = bean.getTotalReceived();
        this.settleTime = bean.getSettleTime();
        this.issueType = bean.getIssueType();
        this.status = bean.getStatus();
        this.remarks = bean.getRemarks();
    }
    
    public int getId() {
        return this.id;
    }
    
    public void setId(final int id) {
        this.id = id;
    }
    
    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(final String username) {
        this.username = username;
    }
    
    public double getScale() {
        return this.scale;
    }
    
    public void setScale(final double scale) {
        this.scale = scale;
    }
    
    public int getValidUser() {
        return this.validUser;
    }
    
    public void setValidUser(final int validUser) {
        this.validUser = validUser;
    }
    
    public int getMinValidUser() {
        return this.minValidUser;
    }
    
    public void setMinValidUser(final int minValidUser) {
        this.minValidUser = minValidUser;
    }
    
    public String getIndicateDate() {
        return this.indicateDate;
    }
    
    public void setIndicateDate(final String indicateDate) {
        this.indicateDate = indicateDate;
    }
    
    public double getBillingOrder() {
        return this.billingOrder;
    }
    
    public void setBillingOrder(final double billingOrder) {
        this.billingOrder = billingOrder;
    }
    
    public double getCalAmount() {
        return this.calAmount;
    }
    
    public void setCalAmount(final double calAmount) {
        this.calAmount = calAmount;
    }
    
    public double getUserAmount() {
        return this.userAmount;
    }
    
    public void setUserAmount(final double userAmount) {
        this.userAmount = userAmount;
    }
    
    public double getLowerTotalAmount() {
        return this.lowerTotalAmount;
    }
    
    public void setLowerTotalAmount(final double lowerTotalAmount) {
        this.lowerTotalAmount = lowerTotalAmount;
    }
    
    public double getLowerPaidAmount() {
        return this.lowerPaidAmount;
    }
    
    public void setLowerPaidAmount(final double lowerPaidAmount) {
        this.lowerPaidAmount = lowerPaidAmount;
    }
    
    public double getTotalReceived() {
        return this.totalReceived;
    }
    
    public void setTotalReceived(final double totalReceived) {
        this.totalReceived = totalReceived;
    }
    
    public String getSettleTime() {
        return this.settleTime;
    }
    
    public void setSettleTime(final String settleTime) {
        this.settleTime = settleTime;
    }
    
    public int getIssueType() {
        return this.issueType;
    }
    
    public void setIssueType(final int issueType) {
        this.issueType = issueType;
    }
    
    public int getStatus() {
        return this.status;
    }
    
    public void setStatus(final int status) {
        this.status = status;
    }
    
    public String getRemarks() {
        return this.remarks;
    }
    
    public void setRemarks(final String remarks) {
        this.remarks = remarks;
    }
    
    public double getThisLoss() {
        return this.thisLoss;
    }
    
    public void setThisLoss(final double thisLoss) {
        this.thisLoss = thisLoss;
    }
}
