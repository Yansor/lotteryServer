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
@Table(name = "user_lottery_details_report", catalog = "ecai", uniqueConstraints = { @UniqueConstraint(columnNames = { "user_id", "lottery_id", "rule_id", "time" }) })
public class HistoryUserLotteryDetailsReport implements Serializable
{
    private static final long serialVersionUID = 1L;
    private int id;
    private int userId;
    private int lotteryId;
    private int ruleId;
    private double spend;
    private double prize;
    private double spendReturn;
    private double proxyReturn;
    private double cancelOrder;
    private double billingOrder;
    private String time;
    
    public HistoryUserLotteryDetailsReport() {
    }
    
    public HistoryUserLotteryDetailsReport(final int userId, final int lotteryId, final int ruleId, final double spend, final double prize, final double spendReturn, final double proxyReturn, final double cancelOrder, final double billingOrder, final String time) {
        this.userId = userId;
        this.lotteryId = lotteryId;
        this.ruleId = ruleId;
        this.spend = spend;
        this.prize = prize;
        this.spendReturn = spendReturn;
        this.proxyReturn = proxyReturn;
        this.cancelOrder = cancelOrder;
        this.billingOrder = billingOrder;
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
    
    @Column(name = "lottery_id", nullable = false)
    public int getLotteryId() {
        return this.lotteryId;
    }
    
    public void setLotteryId(final int lotteryId) {
        this.lotteryId = lotteryId;
    }
    
    @Column(name = "rule_id", nullable = false)
    public int getRuleId() {
        return this.ruleId;
    }
    
    public void setRuleId(final int ruleId) {
        this.ruleId = ruleId;
    }
    
    @Column(name = "spend", nullable = false, precision = 16, scale = 5)
    public double getSpend() {
        return this.spend;
    }
    
    public void setSpend(final double spend) {
        this.spend = spend;
    }
    
    @Column(name = "prize", nullable = false, precision = 16, scale = 5)
    public double getPrize() {
        return this.prize;
    }
    
    public void setPrize(final double prize) {
        this.prize = prize;
    }
    
    @Column(name = "spend_return", nullable = false, precision = 16, scale = 5)
    public double getSpendReturn() {
        return this.spendReturn;
    }
    
    public void setSpendReturn(final double spendReturn) {
        this.spendReturn = spendReturn;
    }
    
    @Column(name = "proxy_return", nullable = false, precision = 16, scale = 5)
    public double getProxyReturn() {
        return this.proxyReturn;
    }
    
    public void setProxyReturn(final double proxyReturn) {
        this.proxyReturn = proxyReturn;
    }
    
    @Column(name = "cancel_order", nullable = false, precision = 16, scale = 5)
    public double getCancelOrder() {
        return this.cancelOrder;
    }
    
    public void setCancelOrder(final double cancelOrder) {
        this.cancelOrder = cancelOrder;
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
