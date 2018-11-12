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
@Table(name = "user_bill", catalog = "ecai", uniqueConstraints = { @UniqueConstraint(columnNames = { "billno" }) })
public class UserBill implements Serializable
{
    private static final long serialVersionUID = 1L;
    private int id;
    private String billno;
    private int userId;
    private int account;
    private int type;
    private double money;
    private double beforeMoney;
    private double afterMoney;
    private Integer refType;
    private String refId;
    private String time;
    private String remarks;
    
    public UserBill() {
    }
    
    public UserBill(final String billno, final int userId, final int account, final int type, final double money, final double beforeMoney, final double afterMoney, final String time, final String remarks) {
        this.billno = billno;
        this.userId = userId;
        this.account = account;
        this.type = type;
        this.money = money;
        this.beforeMoney = beforeMoney;
        this.afterMoney = afterMoney;
        this.time = time;
        this.remarks = remarks;
    }
    
    public UserBill(final String billno, final int userId, final int account, final int type, final double money, final double beforeMoney, final double afterMoney, final Integer refType, final String refId, final String time, final String remarks) {
        this.billno = billno;
        this.userId = userId;
        this.account = account;
        this.type = type;
        this.money = money;
        this.beforeMoney = beforeMoney;
        this.afterMoney = afterMoney;
        this.refType = refType;
        this.refId = refId;
        this.time = time;
        this.remarks = remarks;
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
    
    @Column(name = "billno", unique = true, nullable = false, length = 32)
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
    
    @Column(name = "account", nullable = false)
    public int getAccount() {
        return this.account;
    }
    
    public void setAccount(final int account) {
        this.account = account;
    }
    
    @Column(name = "type", nullable = false)
    public int getType() {
        return this.type;
    }
    
    public void setType(final int type) {
        this.type = type;
    }
    
    @Column(name = "money", nullable = false, precision = 16, scale = 5)
    public double getMoney() {
        return this.money;
    }
    
    public void setMoney(final double money) {
        this.money = money;
    }
    
    @Column(name = "before_money", nullable = false, precision = 16, scale = 5)
    public double getBeforeMoney() {
        return this.beforeMoney;
    }
    
    public void setBeforeMoney(final double beforeMoney) {
        this.beforeMoney = beforeMoney;
    }
    
    @Column(name = "after_money", nullable = false, precision = 16, scale = 5)
    public double getAfterMoney() {
        return this.afterMoney;
    }
    
    public void setAfterMoney(final double afterMoney) {
        this.afterMoney = afterMoney;
    }
    
    @Column(name = "ref_type")
    public Integer getRefType() {
        return this.refType;
    }
    
    public void setRefType(final Integer refType) {
        this.refType = refType;
    }
    
    @Column(name = "ref_id", length = 32)
    public String getRefId() {
        return this.refId;
    }
    
    public void setRefId(final String refId) {
        this.refId = refId;
    }
    
    @Column(name = "time", nullable = false, length = 19)
    public String getTime() {
        return this.time;
    }
    
    public void setTime(final String time) {
        this.time = time;
    }
    
    @Column(name = "remarks", nullable = false, length = 256)
    public String getRemarks() {
        return this.remarks;
    }
    
    public void setRemarks(final String remarks) {
        this.remarks = remarks;
    }
}
