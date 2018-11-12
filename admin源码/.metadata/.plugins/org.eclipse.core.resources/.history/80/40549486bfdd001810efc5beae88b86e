package lottery.domains.content.entity;

import javax.persistence.Column;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.UniqueConstraint;
import javax.persistence.Table;
import javax.persistence.Entity;

@Entity
@Table(name = "user_game_water_bill", catalog = "ecai", uniqueConstraints = { @UniqueConstraint(columnNames = { "user_id", "indicate_date", "from_user" }) })
public class UserGameWaterBill
{
    private int id;
    private int userId;
    private String indicateDate;
    private int fromUser;
    private String settleTime;
    private double scale;
    private double billingOrder;
    private double userAmount;
    private int type;
    private int status;
    private String remark;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public int getId() {
        return this.id;
    }
    
    public void setId(final int id) {
        this.id = id;
    }
    
    @Column(name = "user_id", nullable = false)
    public int getUserId() {
        return this.userId;
    }
    
    public void setUserId(final int userId) {
        this.userId = userId;
    }
    
    @Column(name = "indicate_date", length = 10)
    public String getIndicateDate() {
        return this.indicateDate;
    }
    
    public void setIndicateDate(final String indicateDate) {
        this.indicateDate = indicateDate;
    }
    
    @Column(name = "from_user", nullable = false)
    public int getFromUser() {
        return this.fromUser;
    }
    
    public void setFromUser(final int fromUser) {
        this.fromUser = fromUser;
    }
    
    @Column(name = "settle_time", length = 19)
    public String getSettleTime() {
        return this.settleTime;
    }
    
    public void setSettleTime(final String settleTime) {
        this.settleTime = settleTime;
    }
    
    @Column(name = "scale", nullable = false, precision = 5, scale = 4)
    public double getScale() {
        return this.scale;
    }
    
    public void setScale(final double scale) {
        this.scale = scale;
    }
    
    @Column(name = "billing_order", nullable = false, precision = 16, scale = 4)
    public double getBillingOrder() {
        return this.billingOrder;
    }
    
    public void setBillingOrder(final double billingOrder) {
        this.billingOrder = billingOrder;
    }
    
    @Column(name = "user_amount", nullable = false, precision = 16, scale = 4)
    public double getUserAmount() {
        return this.userAmount;
    }
    
    public void setUserAmount(final double userAmount) {
        this.userAmount = userAmount;
    }
    
    @Column(name = "type", nullable = false)
    public int getType() {
        return this.type;
    }
    
    public void setType(final int type) {
        this.type = type;
    }
    
    @Column(name = "status", nullable = false)
    public int getStatus() {
        return this.status;
    }
    
    public void setStatus(final int status) {
        this.status = status;
    }
    
    @Column(name = "remark")
    public String getRemark() {
        return this.remark;
    }
    
    public void setRemark(final String remark) {
        this.remark = remark;
    }
}
