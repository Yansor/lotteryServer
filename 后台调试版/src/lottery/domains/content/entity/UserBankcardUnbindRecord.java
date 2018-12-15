package lottery.domains.content.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="user_bankcard_unbind_record", catalog="ecai")
public class UserBankcardUnbindRecord
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private int id;
  private String userIds;
  private String cardId;
  private int unbindNum;
  private String unbindTime;
  
  public UserBankcardUnbindRecord() {}
  
  public UserBankcardUnbindRecord(String userIds, String cardId, int unbindNum, String unbindTime)
  {
    this.userIds = userIds;
    this.cardId = cardId;
    this.unbindNum = unbindNum;
    this.unbindTime = unbindTime;
  }
  
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name="id", unique=true, nullable=false)
  public int getId()
  {
    return this.id;
  }
  
  public void setId(int id)
  {
    this.id = id;
  }
  
  @Column(name="user_ids", nullable=false)
  public String getUserIds()
  {
    return this.userIds;
  }
  
  public void setUserIds(String userIds)
  {
    this.userIds = userIds;
  }
  
  @Column(name="card_id", unique=true, nullable=false, length=128)
  public String getCardId()
  {
    return this.cardId;
  }
  
  public void setCardId(String cardId)
  {
    this.cardId = cardId;
  }
  
  @Column(name="unbind_num", nullable=false)
  public int getUnbindNum()
  {
    return this.unbindNum;
  }
  
  public void setUnbindNum(int unbindNum)
  {
    this.unbindNum = unbindNum;
  }
  
  @Column(name="unbind_time", nullable=false)
  public String getUnbindTime()
  {
    return this.unbindTime;
  }
  
  public void setUnbindTime(String unbindTime)
  {
    this.unbindTime = unbindTime;
  }
}
