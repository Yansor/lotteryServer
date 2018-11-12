package lottery.domains.content.entity;

import javax.persistence.Column;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@Table(name = "activity_cost_bill", catalog = "ecai")
public class ActivityCostBill implements Serializable
{
    private static final long serialVersionUID = 1L;
    private int id;
    private int userId;
    private double totalCost;
    private double money;
    private String time;
    private String drawIp;
    
    public ActivityCostBill() {
    }
    
    public ActivityCostBill(final int id, final int userId, final double totalCost, final double money, final String time, final String drawIp) {
        this.id = id;
        this.userId = userId;
        this.totalCost = totalCost;
        this.money = money;
        this.time = time;
        this.drawIp = drawIp;
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
    
    @Column(name = "draw_ip", nullable = false, length = 30)
    public String getDrawIp() {
        return this.drawIp;
    }
    
    public void setDrawIp(final String drawIp) {
        this.drawIp = drawIp;
    }
}
