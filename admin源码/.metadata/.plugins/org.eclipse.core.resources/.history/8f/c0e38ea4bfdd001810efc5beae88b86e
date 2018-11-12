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
@Table(name = "lottery_play_rules_config", catalog = "ecai", uniqueConstraints = { @UniqueConstraint(columnNames = { "rule_id", "lottery_id" }) })
public class LotteryPlayRulesConfig implements Serializable
{
    private static final long serialVersionUID = 1L;
    private int id;
    private int ruleId;
    private int lotteryId;
    private String minNum;
    private String maxNum;
    private int status;
    private String prize;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public int getId() {
        return this.id;
    }
    
    public void setId(final int id) {
        this.id = id;
    }
    
    @Column(name = "rule_id", nullable = false)
    public int getRuleId() {
        return this.ruleId;
    }
    
    public void setRuleId(final int ruleId) {
        this.ruleId = ruleId;
    }
    
    @Column(name = "lottery_id", nullable = false)
    public int getLotteryId() {
        return this.lotteryId;
    }
    
    public void setLotteryId(final int lotteryId) {
        this.lotteryId = lotteryId;
    }
    
    @Column(name = "min_num", length = 128)
    public String getMinNum() {
        return this.minNum;
    }
    
    public void setMinNum(final String minNum) {
        this.minNum = minNum;
    }
    
    @Column(name = "max_num", length = 128)
    public String getMaxNum() {
        return this.maxNum;
    }
    
    public void setMaxNum(final String maxNum) {
        this.maxNum = maxNum;
    }
    
    @Column(name = "status")
    public int getStatus() {
        return this.status;
    }
    
    public void setStatus(final int status) {
        this.status = status;
    }
    
    @Column(name = "prize", nullable = false, length = 512)
    public String getPrize() {
        return this.prize;
    }
    
    public void setPrize(final String prize) {
        this.prize = prize;
    }
}
