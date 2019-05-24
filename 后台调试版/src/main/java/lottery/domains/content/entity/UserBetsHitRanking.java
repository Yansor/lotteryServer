package lottery.domains.content.entity;

import com.alibaba.fastjson.annotation.JSONField;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="user_bets_hit_ranking", catalog="ecai")
public class UserBetsHitRanking
  implements Serializable, Comparable<UserBetsHitRanking>
{
  private static final long serialVersionUID = -2265717841611668255L;
  @JSONField(serialize=false)
  private int id;
  private String name;
  private String username;
  private int prizeMoney;
  private String time;
  private String code;
  private String type;
  private int platform;
  
  public UserBetsHitRanking() {}
  
  public UserBetsHitRanking(int id, String name, String username, int prizeMoney, String time, String code, String type, int platform)
  {
    this.id = id;
    this.name = name;
    this.username = username;
    this.prizeMoney = prizeMoney;
    this.time = time;
    this.code = code;
    this.type = type;
    this.platform = platform;
  }
  
  public UserBetsHitRanking(String name, String username, int prizeMoney, String time, String code, String type, int platform)
  {
    this.name = name;
    this.username = username;
    this.prizeMoney = prizeMoney;
    this.time = time;
    this.code = code;
    this.type = type;
    this.platform = platform;
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
  
  @Column(name="name", nullable=false, length=256)
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  @Column(name="username", nullable=false, length=256)
  public String getUsername()
  {
    return this.username;
  }
  
  public void setUsername(String username)
  {
    this.username = username;
  }
  
  @Column(name="prize_money", nullable=false)
  public int getPrizeMoney()
  {
    return this.prizeMoney;
  }
  
  public void setPrizeMoney(int prizeMoney)
  {
    this.prizeMoney = prizeMoney;
  }
  
  @Column(name="time", nullable=false, length=20)
  public String getTime()
  {
    return this.time;
  }
  
  public void setTime(String time)
  {
    this.time = time;
  }
  
  @Column(name="code")
  public String getCode()
  {
    return this.code;
  }
  
  public void setCode(String code)
  {
    this.code = code;
  }
  
  @Column(name="type")
  public String getType()
  {
    return this.type;
  }
  
  public void setType(String type)
  {
    this.type = type;
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
  
  public int compareTo(UserBetsHitRanking o)
  {
    if (getPrizeMoney() == o.getPrizeMoney()) {
      return 1;
    }
    return o.getPrizeMoney() > getPrizeMoney() ? 1 : -1;
  }
}
