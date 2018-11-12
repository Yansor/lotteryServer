package lottery.domains.content.entity;

import javax.persistence.Column;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@Table(name = "vip_upgrade_gifts", catalog = "ecai")
public class VipUpgradeGifts implements Serializable
{
    private static final long serialVersionUID = 1L;
    private int id;
    private int userId;
    private int beforeLevel;
    private int afterLevel;
    private double money;
    private String time;
    private int status;
    private int isReceived;
    
    public VipUpgradeGifts() {
    }
    
    public VipUpgradeGifts(final int userId, final int beforeLevel, final int afterLevel, final double money, final String time, final int status, final int isReceived) {
        this.userId = userId;
        this.beforeLevel = beforeLevel;
        this.afterLevel = afterLevel;
        this.money = money;
        this.time = time;
        this.status = status;
        this.isReceived = isReceived;
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
    
    @Column(name = "before_level", nullable = false)
    public int getBeforeLevel() {
        return this.beforeLevel;
    }
    
    public void setBeforeLevel(final int beforeLevel) {
        this.beforeLevel = beforeLevel;
    }
    
    @Column(name = "after_level", nullable = false)
    public int getAfterLevel() {
        return this.afterLevel;
    }
    
    public void setAfterLevel(final int afterLevel) {
        this.afterLevel = afterLevel;
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
    
    @Column(name = "status", nullable = false)
    public int getStatus() {
        return this.status;
    }
    
    public void setStatus(final int status) {
        this.status = status;
    }
    
    @Column(name = "is_received", nullable = false)
    public int getIsReceived() {
        return this.isReceived;
    }
    
    public void setIsReceived(final int isReceived) {
        this.isReceived = isReceived;
    }
}
