package lottery.domains.content.entity;

import javax.persistence.Column;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@Table(name = "activity_recharge_loop_bill", catalog = "ecai")
public class ActivityRechargeLoopBill implements Serializable
{
    private static final long serialVersionUID = 1L;
    private int id;
    private int userId;
    private String ip;
    private int step;
    private double totalMoney;
    private double totalCost;
    private double money;
    private String time;
    
    public ActivityRechargeLoopBill() {
    }
    
    public ActivityRechargeLoopBill(final int userId, final String ip, final int step, final double totalMoney, final double totalCost, final double money, final String time) {
        this.userId = userId;
        this.ip = ip;
        this.step = step;
        this.totalMoney = totalMoney;
        this.totalCost = totalCost;
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
    
    @Column(name = "ip", nullable = false, length = 128)
    public String getIp() {
        return this.ip;
    }
    
    public void setIp(final String ip) {
        this.ip = ip;
    }
    
    @Column(name = "step", nullable = false)
    public int getStep() {
        return this.step;
    }
    
    public void setStep(final int step) {
        this.step = step;
    }
    
    @Column(name = "total_money", nullable = false, precision = 16, scale = 5)
    public double getTotalMoney() {
        return this.totalMoney;
    }
    
    public void setTotalMoney(final double totalMoney) {
        this.totalMoney = totalMoney;
    }
    
    @Column(name = "total_cost", nullable = false, precision = 16, scale = 5)
    public double getTotalCost() {
        return this.totalCost;
    }
    
    public void setTotalCost(final double totalCost) {
        this.totalCost = totalCost;
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
