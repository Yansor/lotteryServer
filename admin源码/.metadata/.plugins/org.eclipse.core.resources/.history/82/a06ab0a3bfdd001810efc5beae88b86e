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
@Table(name = "user_recharge", catalog = "ecaibackup", uniqueConstraints = { @UniqueConstraint(columnNames = { "billno" }) })
public class HistoryUserRecharge implements Serializable
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
    private int type;
    private int subtype;
    private Integer channelId;
    private String postscript;
    private String infos;
    private String payBillno;
    private String payTime;
    private String remarks;
    private Integer cardId;
    private String realName;
    private double receiveFeeMoney;
    private Integer requestBankId;
    private Integer payBankId;
    
    public HistoryUserRecharge() {
    }
    
    public HistoryUserRecharge(final String billno, final int userId, final double money, final double beforeMoney, final double afterMoney, final double recMoney, final double feeMoney, final String time, final int status, final int type, final int subtype) {
        this.billno = billno;
        this.userId = userId;
        this.money = money;
        this.beforeMoney = beforeMoney;
        this.afterMoney = afterMoney;
        this.recMoney = recMoney;
        this.feeMoney = feeMoney;
        this.time = time;
        this.status = status;
        this.type = type;
        this.subtype = subtype;
    }
    
    public HistoryUserRecharge(final String billno, final int userId, final double money, final double beforeMoney, final double afterMoney, final double recMoney, final double feeMoney, final String time, final int status, final int type, final int subtype, final String postscript, final String infos, final String payBillno, final String payTime, final String remarks) {
        this.billno = billno;
        this.userId = userId;
        this.money = money;
        this.beforeMoney = beforeMoney;
        this.afterMoney = afterMoney;
        this.recMoney = recMoney;
        this.feeMoney = feeMoney;
        this.time = time;
        this.status = status;
        this.type = type;
        this.subtype = subtype;
        this.postscript = postscript;
        this.infos = infos;
        this.payBillno = payBillno;
        this.payTime = payTime;
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
    
    @Column(name = "type", nullable = false)
    public int getType() {
        return this.type;
    }
    
    public void setType(final int type) {
        this.type = type;
    }
    
    @Column(name = "subtype", nullable = false)
    public int getSubtype() {
        return this.subtype;
    }
    
    public void setSubtype(final int subtype) {
        this.subtype = subtype;
    }
    
    @Column(name = "channel_id", nullable = false)
    public Integer getChannelId() {
        return this.channelId;
    }
    
    public void setChannelId(final Integer channelId) {
        this.channelId = channelId;
    }
    
    @Column(name = "postscript", length = 128)
    public String getPostscript() {
        return this.postscript;
    }
    
    public void setPostscript(final String postscript) {
        this.postscript = postscript;
    }
    
    @Column(name = "infos")
    public String getInfos() {
        return this.infos;
    }
    
    public void setInfos(final String infos) {
        this.infos = infos;
    }
    
    @Column(name = "pay_billno")
    public String getPayBillno() {
        return this.payBillno;
    }
    
    public void setPayBillno(final String payBillno) {
        this.payBillno = payBillno;
    }
    
    @Column(name = "pay_time", length = 19)
    public String getPayTime() {
        return this.payTime;
    }
    
    public void setPayTime(final String payTime) {
        this.payTime = payTime;
    }
    
    @Column(name = "remarks")
    public String getRemarks() {
        return this.remarks;
    }
    
    public void setRemarks(final String remarks) {
        this.remarks = remarks;
    }
    
    @Column(name = "card_id")
    public Integer getCardId() {
        return this.cardId;
    }
    
    public void setCardId(final Integer cardId) {
        this.cardId = cardId;
    }
    
    @Column(name = "real_name")
    public String getRealName() {
        return this.realName;
    }
    
    public void setRealName(final String realName) {
        this.realName = realName;
    }
    
    @Column(name = "receive_fee_money", nullable = false, precision = 16, scale = 5)
    public double getReceiveFeeMoney() {
        return this.receiveFeeMoney;
    }
    
    public void setReceiveFeeMoney(final double receiveFeeMoney) {
        this.receiveFeeMoney = receiveFeeMoney;
    }
    
    @Column(name = "pay_bank_id")
    public Integer getPayBankId() {
        return this.payBankId;
    }
    
    public void setPayBankId(final Integer payBankId) {
        this.payBankId = payBankId;
    }
    
    @Column(name = "request_bank_id")
    public Integer getRequestBankId() {
        return this.requestBankId;
    }
    
    public void setRequestBankId(final Integer requestBankId) {
        this.requestBankId = requestBankId;
    }
}
