package lottery.domains.content.entity;

import javax.persistence.Column;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@Table(name = "user_bankcard_unbind_record", catalog = "ecai")
public class UserBankcardUnbindRecord implements Serializable
{
    private static final long serialVersionUID = 1L;
    private int id;
    private String userIds;
    private String cardId;
    private int unbindNum;
    private String unbindTime;
    
    public UserBankcardUnbindRecord() {
    }
    
    public UserBankcardUnbindRecord(final String userIds, final String cardId, final int unbindNum, final String unbindTime) {
        this.userIds = userIds;
        this.cardId = cardId;
        this.unbindNum = unbindNum;
        this.unbindTime = unbindTime;
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
    
    @Column(name = "user_ids", nullable = false)
    public String getUserIds() {
        return this.userIds;
    }
    
    public void setUserIds(final String userIds) {
        this.userIds = userIds;
    }
    
    @Column(name = "card_id", unique = true, nullable = false, length = 128)
    public String getCardId() {
        return this.cardId;
    }
    
    public void setCardId(final String cardId) {
        this.cardId = cardId;
    }
    
    @Column(name = "unbind_num", nullable = false)
    public int getUnbindNum() {
        return this.unbindNum;
    }
    
    public void setUnbindNum(final int unbindNum) {
        this.unbindNum = unbindNum;
    }
    
    @Column(name = "unbind_time", nullable = false)
    public String getUnbindTime() {
        return this.unbindTime;
    }
    
    public void setUnbindTime(final String unbindTime) {
        this.unbindTime = unbindTime;
    }
}
