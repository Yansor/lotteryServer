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
@Table(name = "user_card", catalog = "ecai", uniqueConstraints = { @UniqueConstraint(columnNames = { "card_id" }) })
public class UserCard implements Serializable
{
    private static final long serialVersionUID = 1L;
    private int id;
    private int userId;
    private int bankId;
    private String bankBranch;
    private String cardName;
    private String cardId;
    private int status;
    private String time;
    private String lockTime;
    private int isDefault;
    
    public UserCard() {
    }
    
    public UserCard(final int userId, final int bankId, final String cardName, final String cardId, final int status, final String time, final int isDefault) {
        this.userId = userId;
        this.bankId = bankId;
        this.cardName = cardName;
        this.cardId = cardId;
        this.status = status;
        this.time = time;
        this.isDefault = isDefault;
    }
    
    public UserCard(final int userId, final int bankId, final String bankBranch, final String cardName, final String cardId, final int status, final String time, final String lockTime, final int isDefault) {
        this.userId = userId;
        this.bankId = bankId;
        this.bankBranch = bankBranch;
        this.cardName = cardName;
        this.cardId = cardId;
        this.status = status;
        this.time = time;
        this.lockTime = lockTime;
        this.isDefault = isDefault;
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
    
    @Column(name = "bank_id", nullable = false)
    public int getBankId() {
        return this.bankId;
    }
    
    public void setBankId(final int bankId) {
        this.bankId = bankId;
    }
    
    @Column(name = "bank_branch", length = 128)
    public String getBankBranch() {
        return this.bankBranch;
    }
    
    public void setBankBranch(final String bankBranch) {
        this.bankBranch = bankBranch;
    }
    
    @Column(name = "card_name", nullable = false, length = 64)
    public String getCardName() {
        return this.cardName;
    }
    
    public void setCardName(final String cardName) {
        this.cardName = cardName;
    }
    
    @Column(name = "card_id", unique = true, nullable = false, length = 128)
    public String getCardId() {
        return this.cardId;
    }
    
    public void setCardId(final String cardId) {
        this.cardId = cardId;
    }
    
    @Column(name = "status", nullable = false)
    public int getStatus() {
        return this.status;
    }
    
    public void setStatus(final int status) {
        this.status = status;
    }
    
    @Column(name = "time", nullable = false, length = 19)
    public String getTime() {
        return this.time;
    }
    
    public void setTime(final String time) {
        this.time = time;
    }
    
    @Column(name = "lock_time", length = 19)
    public String getLockTime() {
        return this.lockTime;
    }
    
    public void setLockTime(final String lockTime) {
        this.lockTime = lockTime;
    }
    
    @Column(name = "is_default", nullable = false)
    public int getIsDefault() {
        return this.isDefault;
    }
    
    public void setIsDefault(final int isDefault) {
        this.isDefault = isDefault;
    }
}
