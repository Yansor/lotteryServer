package lottery.domains.content.entity;

import javax.persistence.Column;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@Table(name = "activity_reward_bill", catalog = "ecai")
public class ActivityRewardBill implements Serializable
{
    private static final long serialVersionUID = 1L;
    private int id;
    private int toUser;
    private int fromUser;
    private int type;
    private double totalMoney;
    private double money;
    private String date;
    private String time;
    private int status;
    
    public ActivityRewardBill() {
    }
    
    public ActivityRewardBill(final int toUser, final int fromUser, final int type, final double totalMoney, final double money, final String date, final String time, final int status) {
        this.toUser = toUser;
        this.fromUser = fromUser;
        this.type = type;
        this.totalMoney = totalMoney;
        this.money = money;
        this.date = date;
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
    
    @Column(name = "to_user", nullable = false)
    public int getToUser() {
        return this.toUser;
    }
    
    public void setToUser(final int toUser) {
        this.toUser = toUser;
    }
    
    @Column(name = "from_user", nullable = false)
    public int getFromUser() {
        return this.fromUser;
    }
    
    public void setFromUser(final int fromUser) {
        this.fromUser = fromUser;
    }
    
    @Column(name = "type", nullable = false)
    public int getType() {
        return this.type;
    }
    
    public void setType(final int type) {
        this.type = type;
    }
    
    @Column(name = "total_money", nullable = false, precision = 16, scale = 5)
    public double getTotalMoney() {
        return this.totalMoney;
    }
    
    public void setTotalMoney(final double totalMoney) {
        this.totalMoney = totalMoney;
    }
    
    @Column(name = "money", nullable = false, precision = 16, scale = 5)
    public double getMoney() {
        return this.money;
    }
    
    public void setMoney(final double money) {
        this.money = money;
    }
    
    @Column(name = "date", nullable = false, length = 10)
    public String getDate() {
        return this.date;
    }
    
    public void setDate(final String date) {
        this.date = date;
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
