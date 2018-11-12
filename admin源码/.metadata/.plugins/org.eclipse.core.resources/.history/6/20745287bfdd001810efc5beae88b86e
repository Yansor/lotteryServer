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
@Table(name = "user_main_report", catalog = "ecai", uniqueConstraints = { @UniqueConstraint(columnNames = { "user_id", "time" }) })
public class UserMainReport implements Serializable
{
    private static final long serialVersionUID = 1L;
    private int id;
    private int userId;
    private double recharge;
    private double withdrawals;
    private double transIn;
    private double transOut;
    private double accountIn;
    private double accountOut;
    private double activity;
    private String time;
    
    public UserMainReport() {
    }
    
    public UserMainReport(final int userId, final double recharge, final double withdrawals, final double transIn, final double transOut, final double accountIn, final double accountOut, final double activity, final String time) {
        this.userId = userId;
        this.recharge = recharge;
        this.withdrawals = withdrawals;
        this.transIn = transIn;
        this.transOut = transOut;
        this.accountIn = accountIn;
        this.accountOut = accountOut;
        this.activity = activity;
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
    
    @Column(name = "recharge", nullable = false, precision = 16, scale = 5)
    public double getRecharge() {
        return this.recharge;
    }
    
    public void setRecharge(final double recharge) {
        this.recharge = recharge;
    }
    
    @Column(name = "withdrawals", nullable = false, precision = 16, scale = 5)
    public double getWithdrawals() {
        return this.withdrawals;
    }
    
    public void setWithdrawals(final double withdrawals) {
        this.withdrawals = withdrawals;
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
    
    @Column(name = "account_in", nullable = false, precision = 16, scale = 5)
    public double getAccountIn() {
        return this.accountIn;
    }
    
    public void setAccountIn(final double accountIn) {
        this.accountIn = accountIn;
    }
    
    @Column(name = "account_out", nullable = false, precision = 16, scale = 5)
    public double getAccountOut() {
        return this.accountOut;
    }
    
    public void setAccountOut(final double accountOut) {
        this.accountOut = accountOut;
    }
    
    @Column(name = "activity", nullable = false, precision = 16, scale = 5)
    public double getActivity() {
        return this.activity;
    }
    
    public void setActivity(final double activity) {
        this.activity = activity;
    }
    
    @Column(name = "time", nullable = false, length = 10)
    public String getTime() {
        return this.time;
    }
    
    public void setTime(final String time) {
        this.time = time;
    }
}
