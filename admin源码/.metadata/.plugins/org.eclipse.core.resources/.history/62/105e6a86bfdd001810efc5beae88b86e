package lottery.domains.content.entity;

import javax.persistence.Column;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@Table(name = "activity_recharge_bill", catalog = "ecai")
public class ActivityRechargeBill implements Serializable
{
    private static final long serialVersionUID = 1L;
    private int id;
    private int userId;
    private String ip;
    private double totalMoney;
    private double money;
    private String payTime;
    private String time;
    private int status;
    
    public ActivityRechargeBill() {
    }
    
    public ActivityRechargeBill(final int userId, final String ip, final double totalMoney, final double money, final String payTime, final String time, final int status) {
        this.userId = userId;
        this.ip = ip;
        this.totalMoney = totalMoney;
        this.money = money;
        this.payTime = payTime;
        this.time = time;
        this.status = status;
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
    
    @Column(name = "ip", nullable = false, length = 128)
    public String getIp() {
        return this.ip;
    }
    
    public void setIp(final String ip) {
        this.ip = ip;
    }
    
    @Column(name = "total_money", nullable = false, precision = 12, scale = 6)
    public double getTotalMoney() {
        return this.totalMoney;
    }
    
    public void setTotalMoney(final double totalMoney) {
        this.totalMoney = totalMoney;
    }
    
    @Column(name = "money", nullable = false, precision = 12, scale = 6)
    public double getMoney() {
        return this.money;
    }
    
    public void setMoney(final double money) {
        this.money = money;
    }
    
    @Column(name = "pay_time", nullable = false, length = 19)
    public String getPayTime() {
        return this.payTime;
    }
    
    public void setPayTime(final String payTime) {
        this.payTime = payTime;
    }
    
    @Column(name = "time", nullable = false, length = 19)
    public String getTime() {
        return this.time;
    }
    
    public void setTime(final String time) {
        this.time = time;
    }
    
    @Column(name = "status", nullable = false)
    public int getStatus() {
        return this.status;
    }
    
    public void setStatus(final int status) {
        this.status = status;
    }
}
