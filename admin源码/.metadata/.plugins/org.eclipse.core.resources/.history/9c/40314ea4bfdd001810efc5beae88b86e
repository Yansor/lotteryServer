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
@Table(name = "user_plan_info", catalog = "ecai", uniqueConstraints = { @UniqueConstraint(columnNames = { "user_id" }) })
public class UserPlanInfo implements Serializable
{
    private static final long serialVersionUID = 1L;
    private int id;
    private int userId;
    private int level;
    private int planCount;
    private int prizeCount;
    private double totalMoney;
    private double totalPrize;
    private int status;
    
    public UserPlanInfo() {
    }
    
    public UserPlanInfo(final int userId, final int level, final int planCount, final int prizeCount, final double totalMoney, final double totalPrize, final int status) {
        this.userId = userId;
        this.level = level;
        this.planCount = planCount;
        this.prizeCount = prizeCount;
        this.totalMoney = totalMoney;
        this.totalPrize = totalPrize;
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
    
    @Column(name = "user_id", unique = true, nullable = false)
    public int getUserId() {
        return this.userId;
    }
    
    public void setUserId(final int userId) {
        this.userId = userId;
    }
    
    @Column(name = "level", nullable = false)
    public int getLevel() {
        return this.level;
    }
    
    public void setLevel(final int level) {
        this.level = level;
    }
    
    @Column(name = "plan_count", nullable = false)
    public int getPlanCount() {
        return this.planCount;
    }
    
    public void setPlanCount(final int planCount) {
        this.planCount = planCount;
    }
    
    @Column(name = "prize_count", nullable = false)
    public int getPrizeCount() {
        return this.prizeCount;
    }
    
    public void setPrizeCount(final int prizeCount) {
        this.prizeCount = prizeCount;
    }
    
    @Column(name = "total_money", nullable = false, precision = 16, scale = 5)
    public double getTotalMoney() {
        return this.totalMoney;
    }
    
    public void setTotalMoney(final double totalMoney) {
        this.totalMoney = totalMoney;
    }
    
    @Column(name = "total_prize", nullable = false, precision = 16, scale = 5)
    public double getTotalPrize() {
        return this.totalPrize;
    }
    
    public void setTotalPrize(final double totalPrize) {
        this.totalPrize = totalPrize;
    }
    
    @Column(name = "status", nullable = false)
    public int getStatus() {
        return this.status;
    }
    
    public void setStatus(final int status) {
        this.status = status;
    }
}
