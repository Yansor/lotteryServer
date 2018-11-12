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
@Table(name = "user_lottery_report", catalog = "ecai", uniqueConstraints = { @UniqueConstraint(columnNames = { "user_id", "time" }) })
public class UserLotteryReport implements Serializable
{
    private static final long serialVersionUID = 1L;
    private int id;
    private int userId;
    private double transIn;
    private double transOut;
    private double spend;
    private double prize;
    private double spendReturn;
    private double proxyReturn;
    private double cancelOrder;
    private double activity;
    private double dividend;
    private double billingOrder;
    private String time;
    private double packet;
    private double rechargeFee;
    
    public UserLotteryReport() {
    }
    
    public UserLotteryReport(final int userId, final double transIn, final double transOut, final double spend, final double prize, final double spendReturn, final double proxyReturn, final double cancelOrder, final double activity, final double dividend, final double billingOrder, final String time) {
        this.userId = userId;
        this.transIn = transIn;
        this.transOut = transOut;
        this.spend = spend;
        this.prize = prize;
        this.spendReturn = spendReturn;
        this.proxyReturn = proxyReturn;
        this.cancelOrder = cancelOrder;
        this.activity = activity;
        this.dividend = dividend;
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
    
    @Column(name = "packet", nullable = false, precision = 16, scale = 5)
    public double getPacket() {
        return this.packet;
    }
    
    public void setPacket(final double packet) {
        this.packet = packet;
    }
    
    @Column(name = "user_id", nullable = false)
    public int getUserId() {
        return this.userId;
    }
    
    public void setUserId(final int userId) {
        this.userId = userId;
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
    
    @Column(name = "activity", nullable = false, precision = 16, scale = 5)
    public double getActivity() {
        return this.activity;
    }
    
    public void setActivity(final double activity) {
        this.activity = activity;
    }
    
    @Column(name = "dividend", nullable = false, precision = 16, scale = 5)
    public double getDividend() {
        return this.dividend;
    }
    
    public void setDividend(final double dividend) {
        this.dividend = dividend;
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
    
    @Column(name = "recharge_fee", nullable = false, precision = 16, scale = 5)
    public double getRechargeFee() {
        return this.rechargeFee;
    }
    
    public void setRechargeFee(final double rechargeFee) {
        this.rechargeFee = rechargeFee;
    }
}
