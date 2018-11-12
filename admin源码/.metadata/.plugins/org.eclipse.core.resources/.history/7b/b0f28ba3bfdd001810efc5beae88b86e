package lottery.domains.content.entity;

import javax.persistence.Column;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@Table(name = "activity_sign_bill", catalog = "ecai")
public class ActivitySignBill implements Serializable
{
    private static final long serialVersionUID = 1L;
    private int id;
    private int userId;
    private int days;
    private String startTime;
    private String endTime;
    private double totalCost;
    private double scale;
    private double money;
    private String time;
    
    public ActivitySignBill() {
    }
    
    public ActivitySignBill(final int userId, final int days, final String startTime, final String endTime, final double totalCost, final double scale, final double money, final String time) {
        this.userId = userId;
        this.days = days;
        this.startTime = startTime;
        this.endTime = endTime;
        this.totalCost = totalCost;
        this.scale = scale;
        this.money = money;
        this.time = time;
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
    
    @Column(name = "user_id", nullable = false)
    public int getUserId() {
        return this.userId;
    }
    
    public void setUserId(final int userId) {
        this.userId = userId;
    }
    
    @Column(name = "days", nullable = false)
    public int getDays() {
        return this.days;
    }
    
    public void setDays(final int days) {
        this.days = days;
    }
    
    @Column(name = "start_time", nullable = false, length = 19)
    public String getStartTime() {
        return this.startTime;
    }
    
    public void setStartTime(final String startTime) {
        this.startTime = startTime;
    }
    
    @Column(name = "end_time", nullable = false, length = 19)
    public String getEndTime() {
        return this.endTime;
    }
    
    public void setEndTime(final String endTime) {
        this.endTime = endTime;
    }
    
    @Column(name = "total_cost", nullable = false, precision = 16, scale = 5)
    public double getTotalCost() {
        return this.totalCost;
    }
    
    public void setTotalCost(final double totalCost) {
        this.totalCost = totalCost;
    }
    
    @Column(name = "scale", nullable = false, precision = 16, scale = 5)
    public double getScale() {
        return this.scale;
    }
    
    public void setScale(final double scale) {
        this.scale = scale;
    }
    
    @Column(name = "money", nullable = false, precision = 16, scale = 5)
    public double getMoney() {
        return this.money;
    }
    
    public void setMoney(final double money) {
        this.money = money;
    }
    
    @Column(name = "time", nullable = false, length = 19)
    public String getTime() {
        return this.time;
    }
    
    public void setTime(final String time) {
        this.time = time;
    }
}
