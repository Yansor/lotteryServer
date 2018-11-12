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
@Table(name = "user_game_report", catalog = "ecai", uniqueConstraints = { @UniqueConstraint(columnNames = { "user_id", "platform_id", "time" }) })
public class UserGameReport implements Serializable
{
    private int id;
    private int userId;
    private int platformId;
    private double transIn;
    private double transOut;
    private double waterReturn;
    private double proxyReturn;
    private double activity;
    private double billingOrder;
    private double prize;
    private String time;
    
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
    
    @Column(name = "platform_id", nullable = false)
    public int getPlatformId() {
        return this.platformId;
    }
    
    public void setPlatformId(final int platformId) {
        this.platformId = platformId;
    }
    
    @Column(name = "trans_in", nullable = false, precision = 16, scale = 5)
    public double getTransIn() {
        return this.transIn;
    }
    
    public void setTransIn(final double transIn) {
        this.transIn = transIn;
    }
    
    @Column(name = "trans_out", nullable = false, precision = 16, scale = 5)
    public double getTransOut() {
        return this.transOut;
    }
    
    public void setTransOut(final double transOut) {
        this.transOut = transOut;
    }
    
    @Column(name = "prize", nullable = false, precision = 16, scale = 5)
    public double getPrize() {
        return this.prize;
    }
    
    public void setPrize(final double prize) {
        this.prize = prize;
    }
    
    @Column(name = "water_return", nullable = false, precision = 16, scale = 5)
    public double getWaterReturn() {
        return this.waterReturn;
    }
    
    public void setWaterReturn(final double waterReturn) {
        this.waterReturn = waterReturn;
    }
    
    @Column(name = "proxy_return", nullable = false, precision = 16, scale = 5)
    public double getProxyReturn() {
        return this.proxyReturn;
    }
    
    public void setProxyReturn(final double proxyReturn) {
        this.proxyReturn = proxyReturn;
    }
    
    @Column(name = "activity", nullable = false, precision = 16, scale = 5)
    public double getActivity() {
        return this.activity;
    }
    
    public void setActivity(final double activity) {
        this.activity = activity;
    }
    
    @Column(name = "billing_order", nullable = false, precision = 16, scale = 5)
    public double getBillingOrder() {
        return this.billingOrder;
    }
    
    public void setBillingOrder(final double billingOrder) {
        this.billingOrder = billingOrder;
    }
    
    @Column(name = "time", nullable = false, length = 10)
    public String getTime() {
        return this.time;
    }
    
    public void setTime(final String time) {
        this.time = time;
    }
}
