package lottery.domains.content.entity;

import javax.persistence.Column;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@Table(name = "user_blacklist", catalog = "ecai")
public class UserBlacklist implements Serializable
{
    private static final long serialVersionUID = 1L;
    private int id;
    private String username;
    private String cardName;
    private String cardId;
    private Integer bankId;
    private String ip;
    private String operatorUser;
    private String operatorTime;
    private String remarks;
    
    public UserBlacklist() {
    }
    
    public UserBlacklist(final String cardName, final String operatorUser, final String operatorTime) {
        this.cardName = cardName;
        this.operatorUser = operatorUser;
        this.operatorTime = operatorTime;
    }
    
    public UserBlacklist(final String username, final String cardName, final String cardId, final Integer bankId, final String ip, final String operatorUser, final String operatorTime, final String remarks) {
        this.username = username;
        this.cardName = cardName;
        this.cardId = cardId;
        this.bankId = bankId;
        this.ip = ip;
        this.operatorUser = operatorUser;
        this.operatorTime = operatorTime;
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
    
    @Column(name = "username", length = 20)
    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(final String username) {
        this.username = username;
    }
    
    @Column(name = "card_name", nullable = false, length = 64)
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
    
    @Column(name = "bank_id")
    public Integer getBankId() {
        return this.bankId;
    }
    
    public void setBankId(final Integer bankId) {
        this.bankId = bankId;
    }
    
    @Column(name = "ip")
    public String getIp() {
        return this.ip;
    }
    
    public void setIp(final String ip) {
        this.ip = ip;
    }
    
    @Column(name = "operator_user", nullable = false)
    public String getOperatorUser() {
        return this.operatorUser;
    }
    
    public void setOperatorUser(final String operatorUser) {
        this.operatorUser = operatorUser;
    }
    
    @Column(name = "operator_time", nullable = false, length = 19)
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
}
