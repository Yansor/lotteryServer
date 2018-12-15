package lottery.domains.content.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="user_high_prize", catalog="ecai")
public class UserHighPrize
  implements Serializable, Cloneable
{
  private int id;
  private int userId;
  private int platform;
  private String name;
  private String nameId;
  private String subName;
  private String refId;
  private double money;
  private double prizeMoney;
  private double times;
  private String time;
  private int status;
  private String confirmUsername;
  
  public UserHighPrize clone()
  {
    try
    {
      return (UserHighPrize)super.clone();
    }
    catch (Exception localException) {}
    return null;
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
  
  @Column(name="user_id", nullable=false)
  public int getUserId()
  {
    return this.userId;
  }
  
  public void setUserId(int userId)
  {
    this.userId = userId;
  }
  
  @Column(name="platform", nullable=false)
  public int getPlatform()
  {
    return this.platform;
  }
  
  public void setPlatform(int platform)
  {
    this.platform = platform;
  }
  
  @Column(name="name", nullable=false, length=512)
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  @Column(name="name_id", nullable=false, length=256)
  public String getNameId()
  {
    return this.nameId;
  }
  
  public void setNameId(String nameId)
  {
    this.nameId = nameId;
  }
  
  @Column(name="sub_name", nullable=false, length=512)
  public String getSubName()
  {
    return this.subName;
  }
  
  public void setSubName(String subName)
  {
    this.subName = subName;
  }
  
  @Column(name="ref_id", nullable=false, length=512)
  public String getRefId()
  {
    return this.refId;
  }
  
  public void setRefId(String refId)
  {
    this.refId = refId;
  }
  
  @Column(name="money", nullable=false, precision=16, scale=5)
  public double getMoney()
  {
    return this.money;
  }
  
  public void setMoney(double money)
  {
    this.money = money;
  }
  
  @Column(name="prize_money", nullable=false, precision=16, scale=5)
  public double getPrizeMoney()
  {
    return this.prizeMoney;
  }
  
  public void setPrizeMoney(double prizeMoney)
  {
    this.prizeMoney = prizeMoney;
  }
  
  @Column(name="times", nullable=false, precision=16, scale=5)
  public double getTimes()
  {
    return this.times;
  }
  
  public void setTimes(double times)
  {
    this.times = times;
  }
  
  @Column(name="time", nullable=false, length=19)
  public String getTime()
  {
    return this.time;
  }
  
  public void setTime(String time)
  {
    this.time = time;
  }
  
  @Column(name="status", nullable=false)
  public int getStatus()
  {
    return this.status;
  }
  
  public void setStatus(int status)
  {
    this.status = status;
  }
  
  @Column(name="confirm_username", length=50)
  public String getConfirmUsername()
  {
    return this.confirmUsername;
  }
  
  public void setConfirmUsername(String confirmUsername)
  {
    this.confirmUsername = confirmUsername;
  }
}
