package lottery.domains.content.entity;

import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.UniqueConstraint;
import javax.persistence.Table;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@Table(name = "user_bets", catalog = "ecaibackup", uniqueConstraints = { @UniqueConstraint(columnNames = { "billno", "user_id" }) })
public class HistoryUserBets implements Serializable, Cloneable
{
    private static final long serialVersionUID = 1L;
    private int id;
    private String billno;
    private int userId;
    private int type;
    private int lotteryId;
    private String expect;
    private int ruleId;
    private String codes;
    private int nums;
    private String model;
    private int multiple;
    private int code;
    private double point;
    private double money;
    private String time;
    private String stopTime;
    private String openTime;
    private int status;
    private String openCode;
    private Double prizeMoney;
    private String prizeTime;
    private String chaseBillno;
    private Integer chaseStop;
    private String planBillno;
    private Double rewardMoney;
    private int isAll;
    private int compressed;
    private int locked;
    
    @Column(name = "is_all", nullable = false)
    public int getIsAll() {
        return this.isAll;
    }
    
    public void setIsAll(final int isAll) {
        this.isAll = isAll;
    }
    
    public HistoryUserBets clone() {
        try {
            return (HistoryUserBets)super.clone();
        }
        catch (Exception ex) {
            return null;
        }
    }
    
    public HistoryUserBets() {
    }
    
    public HistoryUserBets(final String billno, final int userId, final int type, final int lotteryId, final String expect, final int ruleId, final String codes, final int nums, final String model, final int multiple, final int code, final double point, final double money, final String time, final String stopTime, final String openTime, final int status, final int locked) {
        this.billno = billno;
        this.userId = userId;
        this.type = type;
        this.lotteryId = lotteryId;
        this.expect = expect;
        this.ruleId = ruleId;
        this.codes = codes;
        this.nums = nums;
        this.model = model;
        this.multiple = multiple;
        this.code = code;
        this.point = point;
        this.money = money;
        this.time = time;
        this.stopTime = stopTime;
        this.openTime = openTime;
        this.status = status;
        this.locked = locked;
    }
    
    public HistoryUserBets(final String billno, final int userId, final int type, final int lotteryId, final String expect, final int ruleId, final String codes, final int nums, final String model, final int multiple, final int code, final double point, final double money, final String time, final String stopTime, final String openTime, final int status, final int isAll, final int locked) {
        this.billno = billno;
        this.userId = userId;
        this.type = type;
        this.lotteryId = lotteryId;
        this.expect = expect;
        this.ruleId = ruleId;
        this.codes = codes;
        this.nums = nums;
        this.model = model;
        this.multiple = multiple;
        this.code = code;
        this.point = point;
        this.money = money;
        this.time = time;
        this.stopTime = stopTime;
        this.openTime = openTime;
        this.status = status;
        this.isAll = isAll;
        this.locked = locked;
    }
    
    public HistoryUserBets(final String billno, final int userId, final int type, final int lotteryId, final String expect, final int ruleId, final String codes, final int nums, final String model, final int multiple, final int code, final double point, final double money, final String time, final String stopTime, final String openTime, final int status, final String openCode, final Double prizeMoney, final String prizeTime, final String chaseBillno, final Integer chaseStop, final String planBillno, final Double rewardMoney, final int isAll, final int locked) {
        this.billno = billno;
        this.userId = userId;
        this.type = type;
        this.lotteryId = lotteryId;
        this.expect = expect;
        this.ruleId = ruleId;
        this.codes = codes;
        this.nums = nums;
        this.model = model;
        this.multiple = multiple;
        this.code = code;
        this.point = point;
        this.money = money;
        this.time = time;
        this.stopTime = stopTime;
        this.openTime = openTime;
        this.status = status;
        this.openCode = openCode;
        this.prizeMoney = prizeMoney;
        this.prizeTime = prizeTime;
        this.chaseBillno = chaseBillno;
        this.chaseStop = chaseStop;
        this.planBillno = planBillno;
        this.rewardMoney = rewardMoney;
        this.isAll = isAll;
        this.locked = locked;
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
    
    @Column(name = "billno", nullable = false, length = 32, updatable = false)
    public String getBillno() {
        return this.billno;
    }
    
    public void setBillno(final String billno) {
        this.billno = billno;
    }
    
    @Column(name = "user_id", nullable = false, updatable = false)
    public int getUserId() {
        return this.userId;
    }
    
    public void setUserId(final int userId) {
        this.userId = userId;
    }
    
    @Column(name = "type", nullable = false, updatable = false)
    public int getType() {
        return this.type;
    }
    
    public void setType(final int type) {
        this.type = type;
    }
    
    @Column(name = "lottery_id", nullable = false, updatable = false)
    public int getLotteryId() {
        return this.lotteryId;
    }
    
    public void setLotteryId(final int lotteryId) {
        this.lotteryId = lotteryId;
    }
    
    @Column(name = "expect", nullable = false, length = 32, updatable = false)
    public String getExpect() {
        return this.expect;
    }
    
    public void setExpect(final String expect) {
        this.expect = expect;
    }
    
    @Column(name = "rule_id", nullable = false, updatable = false)
    public int getRuleId() {
        return this.ruleId;
    }
    
    public void setRuleId(final int ruleId) {
        this.ruleId = ruleId;
    }
    
    @Column(name = "codes", nullable = false, length = 16777215, updatable = false)
    public String getCodes() {
        return this.codes;
    }
    
    public void setCodes(final String codes) {
        this.codes = codes;
    }
    
    @Column(name = "nums", nullable = false, updatable = false)
    public int getNums() {
        return this.nums;
    }
    
    public void setNums(final int nums) {
        this.nums = nums;
    }
    
    @Column(name = "model", nullable = false, length = 16, updatable = false)
    public String getModel() {
        return this.model;
    }
    
    public void setModel(final String model) {
        this.model = model;
    }
    
    @Column(name = "multiple", nullable = false, updatable = false)
    public int getMultiple() {
        return this.multiple;
    }
    
    public void setMultiple(final int multiple) {
        this.multiple = multiple;
    }
    
    @Column(name = "code", nullable = false, updatable = false)
    public int getCode() {
        return this.code;
    }
    
    public void setCode(final int code) {
        this.code = code;
    }
    
    @Column(name = "point", nullable = false, precision = 11, scale = 1, updatable = false)
    public double getPoint() {
        return this.point;
    }
    
    public void setPoint(final double point) {
        this.point = point;
    }
    
    @Column(name = "money", nullable = false, precision = 16, scale = 5, updatable = false)
    public double getMoney() {
        return this.money;
    }
    
    public void setMoney(final double money) {
        this.money = money;
    }
    
    @Column(name = "time", nullable = false, length = 19, updatable = false)
    public String getTime() {
        return this.time;
    }
    
    public void setTime(final String time) {
        this.time = time;
    }
    
    @Column(name = "stop_time", nullable = false, length = 19)
    public String getStopTime() {
        return this.stopTime;
    }
    
    public void setStopTime(final String stopTime) {
        this.stopTime = stopTime;
    }
    
    @Column(name = "open_time", nullable = false, length = 19, updatable = false)
    public String getOpenTime() {
        return this.openTime;
    }
    
    public void setOpenTime(final String openTime) {
        this.openTime = openTime;
    }
    
    @Column(name = "status", nullable = false)
    public int getStatus() {
        return this.status;
    }
    
    public void setStatus(final int status) {
        this.status = status;
    }
    
    @Column(name = "open_code", length = 128)
    public String getOpenCode() {
        return this.openCode;
    }
    
    public void setOpenCode(final String openCode) {
        this.openCode = openCode;
    }
    
    @Column(name = "prize_money", precision = 16, scale = 5)
    public Double getPrizeMoney() {
        return this.prizeMoney;
    }
    
    public void setPrizeMoney(final Double prizeMoney) {
        this.prizeMoney = prizeMoney;
    }
    
    @Column(name = "prize_time", length = 19)
    public String getPrizeTime() {
        return this.prizeTime;
    }
    
    public void setPrizeTime(final String prizeTime) {
        this.prizeTime = prizeTime;
    }
    
    @Column(name = "chase_billno", length = 64)
    public String getChaseBillno() {
        return this.chaseBillno;
    }
    
    public void setChaseBillno(final String chaseBillno) {
        this.chaseBillno = chaseBillno;
    }
    
    @Column(name = "chase_stop")
    public Integer getChaseStop() {
        return this.chaseStop;
    }
    
    public void setChaseStop(final Integer chaseStop) {
        this.chaseStop = chaseStop;
    }
    
    @Column(name = "plan_billno", length = 64)
    public String getPlanBillno() {
        return this.planBillno;
    }
    
    public void setPlanBillno(final String planBillno) {
        this.planBillno = planBillno;
    }
    
    @Column(name = "reward_money", precision = 16, scale = 5)
    public Double getRewardMoney() {
        return this.rewardMoney;
    }
    
    public void setRewardMoney(final Double rewardMoney) {
        this.rewardMoney = rewardMoney;
    }
    
    @Column(name = "compressed")
    public int getCompressed() {
        return this.compressed;
    }
    
    public void setCompressed(final int compressed) {
        this.compressed = compressed;
    }
    
    @Column(name = "locked", nullable = false)
    public int getLocked() {
        return this.locked;
    }
    
    public void setLocked(final int locked) {
        this.locked = locked;
    }
}
