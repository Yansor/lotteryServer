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
@Table(name = "user_transfers", catalog = "ecai", uniqueConstraints = { @UniqueConstraint(columnNames = { "billno" }) })
public class UserTransfers implements Serializable
{
    private static final long serialVersionUID = 1L;
    private int id;
    private String billno;
    private int toUid;
    private int fromUid;
    private int toAccount;
    private int fromAccount;
    private double money;
    private double beforeMoney;
    private double afterMoney;
    private String time;
    private int status;
    private int type;
    private String infos;
    
    public UserTransfers() {
    }
    
    public UserTransfers(final String billno, final int toUid, final int fromUid, final int toAccount, final int fromAccount, final double money, final double beforeMoney, final double afterMoney, final String time, final int status, final int type) {
        this.billno = billno;
        this.toUid = toUid;
        this.fromUid = fromUid;
        this.toAccount = toAccount;
        this.fromAccount = fromAccount;
        this.money = money;
        this.beforeMoney = beforeMoney;
        this.afterMoney = afterMoney;
        this.time = time;
        this.status = status;
        this.type = type;
    }
    
    public UserTransfers(final String billno, final int toUid, final int fromUid, final int toAccount, final int fromAccount, final double money, final double beforeMoney, final double afterMoney, final String time, final int status, final int type, final String infos) {
        this.billno = billno;
        this.toUid = toUid;
        this.fromUid = fromUid;
        this.toAccount = toAccount;
        this.fromAccount = fromAccount;
        this.money = money;
        this.beforeMoney = beforeMoney;
        this.afterMoney = afterMoney;
        this.time = time;
        this.status = status;
        this.type = type;
        this.infos = infos;
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
    
    @Column(name = "to_uid", nullable = false)
    public int getToUid() {
        return this.toUid;
    }
    
    public void setToUid(final int toUid) {
        this.toUid = toUid;
    }
    
    @Column(name = "from_uid", nullable = false)
    public int getFromUid() {
        return this.fromUid;
    }
    
    public void setFromUid(final int fromUid) {
        this.fromUid = fromUid;
    }
    
    @Column(name = "to_account", nullable = false)
    public int getToAccount() {
        return this.toAccount;
    }
    
    public void setToAccount(final int toAccount) {
        this.toAccount = toAccount;
    }
    
    @Column(name = "from_account", nullable = false)
    public int getFromAccount() {
        return this.fromAccount;
    }
    
    public void setFromAccount(final int fromAccount) {
        this.fromAccount = fromAccount;
    }
    
    @Column(name = "money", nullable = false, precision = 11, scale = 3)
    public double getMoney() {
        return this.money;
    }
    
    public void setMoney(final double money) {
        this.money = money;
    }
    
    @Column(name = "before_money", nullable = false, precision = 11, scale = 3)
    public double getBeforeMoney() {
        return this.beforeMoney;
    }
    
    public void setBeforeMoney(final double beforeMoney) {
        this.beforeMoney = beforeMoney;
    }
    
    @Column(name = "after_money", nullable = false, precision = 11, scale = 3)
    public double getAfterMoney() {
        return this.afterMoney;
    }
    
    public void setAfterMoney(final double afterMoney) {
        this.afterMoney = afterMoney;
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
    
    @Column(name = "type", nullable = false)
    public int getType() {
        return this.type;
    }
    
    public void setType(final int type) {
        this.type = type;
    }
    
    @Column(name = "infos")
    public String getInfos() {
        return this.infos;
    }
    
    public void setInfos(final String infos) {
        this.infos = infos;
    }
}
