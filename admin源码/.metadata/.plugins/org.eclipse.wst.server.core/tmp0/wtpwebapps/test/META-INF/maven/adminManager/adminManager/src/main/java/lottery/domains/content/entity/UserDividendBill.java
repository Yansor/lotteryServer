package lottery.domains.content.entity;

import javax.persistence.Column;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;

@Entity
@Table(name = "user_dividend_bill", catalog = "ecai")
public class UserDividendBill
{
    private int id;
    private int userId;
    private String indicateStartDate;
    private String indicateEndDate;
    private int minValidUser;
    private int validUser;
    private double scale;
    private double dailyBillingOrder;
    private double billingOrder;
    private double thisLoss;
    private double lastLoss;
    private double totalLoss;
    private double calAmount;
    private double userAmount;
    private double lowerTotalAmount;
    private double lowerPaidAmount;
    private double availableAmount;
    private double totalReceived;
    private int issueType;
    private String settleTime;
    private String collectTime;
    private int status;
    private String remarks;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public int getId() {
        return this.id;
    }
    
    public void setId(final int id) {
        this.id = id;
    }
    
    @Column(name = "user_id", nullable = false, unique = true)
    public int getUserId() {
        return this.userId;
    }
    
    public void setUserId(final int userId) {
        this.userId = userId;
    }
    
    @Column(name = "indicate_start_date", length = 10)
    public String getIndicateStartDate() {
        return this.indicateStartDate;
    }
    
    public void setIndicateStartDate(final String indicateStartDate) {
        this.indicateStartDate = indicateStartDate;
    }
    
    @Column(name = "indicate_end_date", length = 10)
    public String getIndicateEndDate() {
        return this.indicateEndDate;
    }
    
    public void setIndicateEndDate(final String indicateEndDate) {
        this.indicateEndDate = indicateEndDate;
    }
    
    @Column(name = "min_valid_user", nullable = false)
    public int getMinValidUser() {
        return this.minValidUser;
    }
    
    public void setMinValidUser(final int minValidUser) {
        this.minValidUser = minValidUser;
    }
    
    @Column(name = "valid_user", nullable = false)
    public int getValidUser() {
        return this.validUser;
    }
    
    public void setValidUser(final int validUser) {
        this.validUser = validUser;
    }
    
    @Column(name = "scale", nullable = false, precision = 6, scale = 1)
    public double getScale() {
        return this.scale;
    }
    
    public void setScale(final double scale) {
        this.scale = scale;
    }
    
    @Column(name = "daily_billing_order", nullable = false, precision = 16, scale = 4)
    public double getDailyBillingOrder() {
        return this.dailyBillingOrder;
    }
    
    public void setDailyBillingOrder(final double dailyBillingOrder) {
        this.dailyBillingOrder = dailyBillingOrder;
    }
    
    @Column(name = "billing_order", nullable = false, precision = 16, scale = 4)
    public double getBillingOrder() {
        return this.billingOrder;
    }
    
    public void setBillingOrder(final double billingOrder) {
        this.billingOrder = billingOrder;
    }
    
    @Column(name = "cal_amount", nullable = false, precision = 16, scale = 4)
    public double getCalAmount() {
        return this.calAmount;
    }
    
    public void setCalAmount(final double calAmount) {
        this.calAmount = calAmount;
    }
    
    @Column(name = "lower_total_amount", nullable = false, precision = 16, scale = 4)
    public double getLowerTotalAmount() {
        return this.lowerTotalAmount;
    }
    
    public void setLowerTotalAmount(final double lowerTotalAmount) {
        this.lowerTotalAmount = lowerTotalAmount;
    }
    
    @Column(name = "lower_paid_amount", nullable = false, precision = 16, scale = 4)
    public double getLowerPaidAmount() {
        return this.lowerPaidAmount;
    }
    
    public void setLowerPaidAmount(final double lowerPaidAmount) {
        this.lowerPaidAmount = lowerPaidAmount;
    }
    
    @Column(name = "this_loss", nullable = false, precision = 16, scale = 4)
    public double getThisLoss() {
        return this.thisLoss;
    }
    
    public void setThisLoss(final double thisLoss) {
        this.thisLoss = thisLoss;
    }
    
    @Column(name = "last_loss", nullable = false, precision = 16, scale = 4)
    public double getLastLoss() {
        return this.lastLoss;
    }
    
    public void setLastLoss(final double lastLoss) {
        this.lastLoss = lastLoss;
    }
    
    @Column(name = "total_loss", nullable = false, precision = 16, scale = 4)
    public double getTotalLoss() {
        return this.totalLoss;
    }
    
    public void setTotalLoss(final double totalLoss) {
        this.totalLoss = totalLoss;
    }
    
    @Column(name = "available_amount", nullable = false, precision = 16, scale = 4)
    public double getAvailableAmount() {
        return this.availableAmount;
    }
    
    public void setAvailableAmount(final double availableAmount) {
        this.availableAmount = availableAmount;
    }
    
    @Column(name = "total_received", nullable = false, precision = 16, scale = 4)
    public double getTotalReceived() {
        return this.totalReceived;
    }
    
    public void setTotalReceived(final double totalReceived) {
        this.totalReceived = totalReceived;
    }
    
    @Column(name = "user_amount", nullable = false, precision = 16, scale = 4)
    public double getUserAmount() {
        return this.userAmount;
    }
    
    public void setUserAmount(final double userAmount) {
        this.userAmount = userAmount;
    }
    
    @Column(name = "issue_type", nullable = false)
    public int getIssueType() {
        return this.issueType;
    }
    
    public void setIssueType(final int issueType) {
        this.issueType = issueType;
    }
    
    @Column(name = "settle_time", length = 19)
    public String getSettleTime() {
        return this.settleTime;
    }
    
    public void setSettleTime(final String settleTime) {
        this.settleTime = settleTime;
    }
    
    @Column(name = "collect_time", length = 19, nullable = true)
    public String getCollectTime() {
        return this.collectTime;
    }
    
    public void setCollectTime(final String collectTime) {
        this.collectTime = collectTime;
    }
    
    @Column(name = "status", nullable = false)
    public int getStatus() {
        return this.status;
    }
    
    public void setStatus(final int status) {
        this.status = status;
    }
    
    @Column(name = "remarks", length = 255, nullable = true)
    public String getRemarks() {
        return this.remarks;
    }
    
    public void setRemarks(final String remarks) {
        this.remarks = remarks;
    }
}
