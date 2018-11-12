package lottery.domains.content.entity;

import javax.persistence.Column;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@Table(name = "user_bets_plan", catalog = "ecai")
public class UserBetsPlan implements Serializable
{
    private static final long serialVersionUID = 1L;
    private int id;
    private String billno;
    private int userId;
    private int orderId;
    private int lotteryId;
    private String expect;
    private int ruleId;
    private int nums;
    private String model;
    private int multiple;
    private double money;
    private double maxPrize;
    private String title;
    private double rate;
    private int followCount;
    private double rewardMoney;
    private double prizeMoney;
    private String time;
    private int status;
    private int part;
    
    public UserBetsPlan() {
    }
    
    public UserBetsPlan(final String billno, final int userId, final int orderId, final int lotteryId, final String expect, final int ruleId, final int nums, final String model, final int multiple, final double money, final double maxPrize, final String title, final double rate, final int followCount, final double rewardMoney, final String time, final int status, final double prizeMoney, final int part) {
        this.billno = billno;
        this.userId = userId;
        this.orderId = orderId;
        this.lotteryId = lotteryId;
        this.expect = expect;
        this.ruleId = ruleId;
        this.nums = nums;
        this.model = model;
        this.multiple = multiple;
        this.money = money;
        this.maxPrize = maxPrize;
        this.title = title;
        this.rate = rate;
        this.followCount = followCount;
        this.rewardMoney = rewardMoney;
        this.time = time;
        this.status = status;
        this.prizeMoney = prizeMoney;
        this.part = part;
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
    
    @Column(name = "billno", nullable = false, length = 32)
    public String getBillno() {
        return this.billno;
    }
    
    public void setBillno(final String billno) {
        this.billno = billno;
    }
    
    @Column(name = "user_id", nullable = false)
    public int getUserId() {
        return this.userId;
    }
    
    public void setUserId(final int userId) {
        this.userId = userId;
    }
    
    @Column(name = "order_id", nullable = false)
    public int getOrderId() {
        return this.orderId;
    }
    
    public void setOrderId(final int orderId) {
        this.orderId = orderId;
    }
    
    @Column(name = "lottery_id", nullable = false)
    public int getLotteryId() {
        return this.lotteryId;
    }
    
    public void setLotteryId(final int lotteryId) {
        this.lotteryId = lotteryId;
    }
    
    @Column(name = "expect", nullable = false, length = 32)
    public String getExpect() {
        return this.expect;
    }
    
    public void setExpect(final String expect) {
        this.expect = expect;
    }
    
    @Column(name = "rule_id", nullable = false)
    public int getRuleId() {
        return this.ruleId;
    }
    
    public void setRuleId(final int ruleId) {
        this.ruleId = ruleId;
    }
    
    @Column(name = "nums", nullable = false)
    public int getNums() {
        return this.nums;
    }
    
    public void setNums(final int nums) {
        this.nums = nums;
    }
    
    @Column(name = "model", nullable = false, length = 16)
    public String getModel() {
        return this.model;
    }
    
    public void setModel(final String model) {
        this.model = model;
    }
    
    @Column(name = "multiple", nullable = false)
    public int getMultiple() {
        return this.multiple;
    }
    
    public void setMultiple(final int multiple) {
        this.multiple = multiple;
    }
    
    @Column(name = "money", nullable = false, precision = 16, scale = 5)
    public double getMoney() {
        return this.money;
    }
    
    public void setMoney(final double money) {
        this.money = money;
    }
    
    @Column(name = "max_prize", nullable = false, precision = 16, scale = 5)
    public double getMaxPrize() {
        return this.maxPrize;
    }
    
    public void setMaxPrize(final double maxPrize) {
        this.maxPrize = maxPrize;
    }
    
    @Column(name = "title", nullable = false, length = 64)
    public String getTitle() {
        return this.title;
    }
    
    public void setTitle(final String title) {
        this.title = title;
    }
    
    @Column(name = "rate", nullable = false, precision = 11)
    public double getRate() {
        return this.rate;
    }
    
    public void setRate(final double rate) {
        this.rate = rate;
    }
    
    @Column(name = "follow_count", nullable = false)
    public int getFollowCount() {
        return this.followCount;
    }
    
    public void setFollowCount(final int followCount) {
        this.followCount = followCount;
    }
    
    @Column(name = "reward_money", nullable = false, precision = 16, scale = 5)
    public double getRewardMoney() {
        return this.rewardMoney;
    }
    
    public void setRewardMoney(final double rewardMoney) {
        this.rewardMoney = rewardMoney;
    }
    
    @Column(name = "prize_money", nullable = false, precision = 16, scale = 5)
    public double getPrizeMoney() {
        return this.prizeMoney;
    }
    
    public void setPrizeMoney(final double prizeMoney) {
        this.prizeMoney = prizeMoney;
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
    
    @Column(name = "part", nullable = false)
    public int getPart() {
        return this.part;
    }
    
    public void setPart(final int part) {
        this.part = part;
    }
}
