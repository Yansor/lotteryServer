package lottery.domains.content.entity;

import javax.persistence.Column;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.UniqueConstraint;
import javax.persistence.Table;
import javax.persistence.Entity;

@Entity
@Table(name = "user_bets_limit", catalog = "ecai", uniqueConstraints = { @UniqueConstraint(columnNames = { "lottery_id", "user_id", "max_bet" }) })
public class UserBetsLimit
{
    private int id;
    private int userId;
    private int lotteryId;
    private double maxBet;
    private double maxPrize;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public int getId() {
        return this.id;
    }
    
    @Column(name = "max_prize")
    public double getMaxPrize() {
        return this.maxPrize;
    }
    
    public void setMaxPrize(final double maxPrize) {
        this.maxPrize = maxPrize;
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
    
    @Column(name = "lottery_id", nullable = false)
    public int getLotteryId() {
        return this.lotteryId;
    }
    
    public void setLotteryId(final int lotteryId) {
        this.lotteryId = lotteryId;
    }
    
    @Column(name = "max_bet", nullable = false)
    public double getMaxBet() {
        return this.maxBet;
    }
    
    public void setMaxBet(final double maxBet) {
        this.maxBet = maxBet;
    }
}
