package lottery.domains.content.entity;

import javax.persistence.Column;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@Table(name = "vip_integral_exchange", catalog = "ecai")
public class VipIntegralExchange implements Serializable
{
    private static final long serialVersionUID = 1L;
    private int id;
    private int userId;
    private double integral;
    private double money;
    private String time;
    
    public VipIntegralExchange() {
    }
    
    public VipIntegralExchange(final int userId, final double integral, final double money, final String time) {
        this.userId = userId;
        this.integral = integral;
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
    
    @Column(name = "integral", nullable = false, precision = 16, scale = 5)
    public double getIntegral() {
        return this.integral;
    }
    
    public void setIntegral(final double integral) {
        this.integral = integral;
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
