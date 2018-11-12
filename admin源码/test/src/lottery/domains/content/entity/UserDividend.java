package lottery.domains.content.entity;

import javax.persistence.Column;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.UniqueConstraint;
import javax.persistence.Table;
import javax.persistence.Entity;

@Entity
@Table(name = "user_dividend", catalog = "ecai", uniqueConstraints = { @UniqueConstraint(columnNames = { "user_id" }) })
public class UserDividend
{
    private int id;
    private int userId;
    private String scaleLevel;
    private String lossLevel;
    private String salesLevel;
    private String userLevel;
    private int minValidUser;
    private String createTime;
    private String agreeTime;
    private String startDate;
    private String endDate;
    private double totalAmount;
    private double lastAmount;
    private int status;
    private int fixed;
    private double minScale;
    private double maxScale;
    private String remarks;
    
    public UserDividend() {
    }
    
    public UserDividend(final int userId, final String scaleLevel, final String lossLevel, final String salesLevel, final int minValidUser, final String createTime, final String agreeTime, final String startDate, final String endDate, final double totalAmount, final double lastAmount, final int status, final int fixed, final double minScale, final double maxScale, final String remarks, final String userLevel) {
        this.userId = userId;
        this.scaleLevel = scaleLevel;
        this.lossLevel = lossLevel;
        this.salesLevel = salesLevel;
        this.minValidUser = minValidUser;
        this.createTime = createTime;
        this.agreeTime = agreeTime;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalAmount = totalAmount;
        this.lastAmount = lastAmount;
        this.status = status;
        this.fixed = fixed;
        this.minScale = minScale;
        this.maxScale = maxScale;
        this.remarks = remarks;
        this.userLevel = userLevel;
    }
    
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
    
    @Column(name = "scale_level", length = 255)
    public String getScaleLevel() {
        return this.scaleLevel;
    }
    
    public void setScaleLevel(final String scaleLevel) {
        this.scaleLevel = scaleLevel;
    }
    
    @Column(name = "loss_level", length = 255)
    public String getLossLevel() {
        return this.lossLevel;
    }
    
    public void setLossLevel(final String lossLevel) {
        this.lossLevel = lossLevel;
    }
    
    @Column(name = "sales_level", length = 255)
    public String getSalesLevel() {
        return this.salesLevel;
    }
    
    public void setSalesLevel(final String salesLevel) {
        this.salesLevel = salesLevel;
    }
    
    @Column(name = "min_valid_user", nullable = false)
    public int getMinValidUser() {
        return this.minValidUser;
    }
    
    public void setMinValidUser(final int minValidUser) {
        this.minValidUser = minValidUser;
    }
    
    @Column(name = "create_time", length = 19)
    public String getCreateTime() {
        return this.createTime;
    }
    
    public void setCreateTime(final String createTime) {
        this.createTime = createTime;
    }
    
    @Column(name = "agree_time", length = 19)
    public String getAgreeTime() {
        return this.agreeTime;
    }
    
    public void setAgreeTime(final String agreeTime) {
        this.agreeTime = agreeTime;
    }
    
    @Column(name = "start_date", length = 10)
    public String getStartDate() {
        return this.startDate;
    }
    
    public void setStartDate(final String startDate) {
        this.startDate = startDate;
    }
    
    @Column(name = "end_date", length = 10)
    public String getEndDate() {
        return this.endDate;
    }
    
    public void setEndDate(final String endDate) {
        this.endDate = endDate;
    }
    
    @Column(name = "total_amount", nullable = false, precision = 16, scale = 4)
    public double getTotalAmount() {
        return this.totalAmount;
    }
    
    public void setTotalAmount(final double totalAmount) {
        this.totalAmount = totalAmount;
    }
    
    @Column(name = "last_amount", nullable = false, precision = 16, scale = 4)
    public double getLastAmount() {
        return this.lastAmount;
    }
    
    public void setLastAmount(final double lastAmount) {
        this.lastAmount = lastAmount;
    }
    
    @Column(name = "status", nullable = false)
    public int getStatus() {
        return this.status;
    }
    
    public void setStatus(final int status) {
        this.status = status;
    }
    
    @Column(name = "fixed", nullable = false)
    public int getFixed() {
        return this.fixed;
    }
    
    public void setFixed(final int fixed) {
        this.fixed = fixed;
    }
    
    @Column(name = "min_scale", precision = 5, scale = 2)
    public double getMinScale() {
        return this.minScale;
    }
    
    public void setMinScale(final double minScale) {
        this.minScale = minScale;
    }
    
    @Column(name = "max_scale", precision = 5, scale = 2)
    public double getMaxScale() {
        return this.maxScale;
    }
    
    public void setMaxScale(final double maxScale) {
        this.maxScale = maxScale;
    }
    
    @Column(name = "remarks", length = 255)
    public String getRemarks() {
        return this.remarks;
    }
    
    public void setRemarks(final String remarks) {
        this.remarks = remarks;
    }
    
    @Column(name = "user_level", length = 255)
    public String getUserLevel() {
        return this.userLevel;
    }
    
    public void setUserLevel(final String userLevel) {
        this.userLevel = userLevel;
    }
}
