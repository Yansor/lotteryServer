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
@Table(name = "user_withdraw", catalog = "ecai", uniqueConstraints = { @UniqueConstraint(columnNames = { "billno" }) })
public class UserWithdraw implements Serializable
{
    private static final long serialVersionUID = 1L;
    private int id;
    private String billno;
    private int userId;
    private double money;
    private double beforeMoney;
    private double afterMoney;
    private double recMoney;
    private double feeMoney;
    private String time;
    private int status;
    private String infos;
    private String bankName;
    private String bankBranch;
    private String cardName;
    private String cardId;
    private String payBillno;
    private String operatorUser;
    private String operatorTime;
    private String remarks;
    private int lockStatus;
    private int checkStatus;
    private int remitStatus;
    private Integer payType;
    private Integer paymentChannelId;
    
    public UserWithdraw() {
    }
    
    public UserWithdraw(final String billno, final int userId, final double money, final double beforeMoney, final double afterMoney, final double recMoney, final double feeMoney, final String time, final int status, final int lockStatus, final int checkStatus, final int remitStatus) {
        this.billno = billno;
        this.userId = userId;
        this.money = money;
        this.beforeMoney = beforeMoney;
        this.afterMoney = afterMoney;
        this.recMoney = recMoney;
        this.feeMoney = feeMoney;
        this.time = time;
        this.status = status;
        this.lockStatus = lockStatus;
        this.checkStatus = checkStatus;
        this.remitStatus = remitStatus;
    }
    
    public UserWithdraw(final String billno, final int userId, final double money, final double beforeMoney, final double afterMoney, final double recMoney, final double feeMoney, final String time, final int status, final String infos, final String bankName, final String bankBranch, final String cardName, final String cardId, final String payBillno, final String operatorUser, final String operatorTime, final String remarks, final int lockStatus, final int checkStatus, final int remitStatus) {
        this.billno = billno;
        this.userId = userId;
        this.money = money;
        this.beforeMoney = beforeMoney;
        this.afterMoney = afterMoney;
        this.recMoney = recMoney;
        this.feeMoney = feeMoney;
        this.time = time;
        this.status = status;
        this.infos = infos;
        this.bankName = bankName;
        this.bankBranch = bankBranch;
        this.cardName = cardName;
        this.cardId = cardId;
        this.payBillno = payBillno;
        this.operatorUser = operatorUser;
        this.operatorTime = operatorTime;
        this.remarks = remarks;
        this.lockStatus = lockStatus;
        this.checkStatus = checkStatus;
        this.remitStatus = remitStatus;
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
    
    @Column(name = "rec_money", nullable = false, precision = 11, scale = 3)
    public double getRecMoney() {
        return this.recMoney;
    }
    
    public void setRecMoney(final double recMoney) {
        this.recMoney = recMoney;
    }
    
    @Column(name = "fee_money", nullable = false, precision = 11, scale = 3)
    public double getFeeMoney() {
        return this.feeMoney;
    }
    
    public void setFeeMoney(final double feeMoney) {
        this.feeMoney = feeMoney;
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
    
    @Column(name = "infos")
    public String getInfos() {
        return this.infos;
    }
    
    public void setInfos(final String infos) {
        this.infos = infos;
    }
    
    @Column(name = "bank_name", length = 64)
    public String getBankName() {
        return this.bankName;
    }
    
    public void setBankName(final String bankName) {
        this.bankName = bankName;
    }
    
    @Column(name = "bank_branch", length = 128)
    public String getBankBranch() {
        return this.bankBranch;
    }
    
    public void setBankBranch(final String bankBranch) {
        this.bankBranch = bankBranch;
    }
    
    @Column(name = "card_name", length = 64)
    public String getCardName() {
        return this.cardName;
    }
    
    public void setCardName(final String cardName) {
        this.cardName = cardName;
    }
    
    @Column(name = "card_id", length = 128)
    public String getCardId() {
        return this.cardId;
    }
    
    public void setCardId(final String cardId) {
        this.cardId = cardId;
    }
    
    @Column(name = "pay_billno", length = 128)
    public String getPayBillno() {
        return this.payBillno;
    }
    
    public void setPayBillno(final String payBillno) {
        this.payBillno = payBillno;
    }
    
    @Column(name = "operator_user", length = 64)
    public String getOperatorUser() {
        return this.operatorUser;
    }
    
    public void setOperatorUser(final String operatorUser) {
        this.operatorUser = operatorUser;
    }
    
    @Column(name = "operator_time", length = 19)
    public String getOperatorTime() {
        return this.operatorTime;
    }
    
    public void setOperatorTime(final String operatorTime) {
        this.operatorTime = operatorTime;
    }
    
    @Column(name = "remarks")
    public String getRemarks() {
        return this.remarks;
    }
    
    public void setRemarks(final String remarks) {
        this.remarks = remarks;
    }
    
    @Column(name = "lock_status", nullable = false)
    public int getLockStatus() {
        return this.lockStatus;
    }
    
    public void setLockStatus(final int lockStatus) {
        this.lockStatus = lockStatus;
    }
    
    @Column(name = "check_status", nullable = false)
    public int getCheckStatus() {
        return this.checkStatus;
    }
    
    public void setCheckStatus(final int checkStatus) {
        this.checkStatus = checkStatus;
    }
    
    @Column(name = "remit_status", nullable = false)
    public int getRemitStatus() {
        return this.remitStatus;
    }
    
    public void setRemitStatus(final int remitStatus) {
        this.remitStatus = remitStatus;
    }
    
    @Column(name = "pay_type")
    public Integer getPayType() {
        return this.payType;
    }
    
    public void setPayType(final Integer payType) {
        this.payType = payType;
    }
    
    @Column(name = "payment_channel_id")
    public Integer getPaymentChannelId() {
        return this.paymentChannelId;
    }
    
    public void setPaymentChannelId(final Integer paymentChannelId) {
        this.paymentChannelId = paymentChannelId;
    }
}
