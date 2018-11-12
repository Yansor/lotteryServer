package lottery.domains.content.entity;

import javax.persistence.Column;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.UniqueConstraint;
import javax.persistence.Table;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@Table(name = "activity_first_recharge_bill", catalog = "ecai", uniqueConstraints = { @UniqueConstraint(columnNames = { "user_id", "date" }) })
public class ActivityFirstRechargeBill implements Serializable
{
    private int id;
    private int userId;
    private String date;
    private String time;
    private double recharge;
    private double amount;
    private String ip;
    
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
    
    @Column(name = "date", nullable = false, length = 10)
    public String getDate() {
        return this.date;
    }
    
    public void setDate(final String date) {
        this.date = date;
    }
    
    @Column(name = "time", nullable = false, length = 20)
    public String getTime() {
        return this.time;
    }
    
    public void setTime(final String time) {
        this.time = time;
    }
    
    @Column(name = "recharge", nullable = false, precision = 16, scale = 5)
    public double getRecharge() {
        return this.recharge;
    }
    
    public void setRecharge(final double recharge) {
        this.recharge = recharge;
    }
    
    @Column(name = "amount", nullable = false, precision = 16, scale = 5)
    public double getAmount() {
        return this.amount;
    }
    
    public void setAmount(final double amount) {
        this.amount = amount;
    }
    
    @Column(name = "ip", nullable = false, length = 25)
    public String getIp() {
        return this.ip;
    }
    
    public void setIp(final String ip) {
        this.ip = ip;
    }
}
