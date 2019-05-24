package lottery.domains.content.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="user_game_account", catalog="ecai", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"user_id", "platform_id", "model"}), @javax.persistence.UniqueConstraint(columnNames={"username", "platform_id", "model"})})
public class UserGameAccount
  implements Serializable
{
  private int id;
  private int userId;
  private int platformId;
  private String username;
  private String password;
  private int model;
  private String ext1;
  private String ext2;
  private String ext3;
  private String time;
  
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
  
  @Column(name="platform_id", nullable=false)
  public int getPlatformId()
  {
    return this.platformId;
  }
  
  public void setPlatformId(int platformId)
  {
    this.platformId = platformId;
  }
  
  @Column(name="username", nullable=false, length=50)
  public String getUsername()
  {
    return this.username;
  }
  
  public void setUsername(String username)
  {
    this.username = username;
  }
  
  @Column(name="password", length=50)
  public String getPassword()
  {
    return this.password;
  }
  
  public void setPassword(String password)
  {
    this.password = password;
  }
  
  @Column(name="model", nullable=false)
  public int getModel()
  {
    return this.model;
  }
  
  public void setModel(int model)
  {
    this.model = model;
  }
  
  @Column(name="ext1", length=128)
  public String getExt1()
  {
    return this.ext1;
  }
  
  public void setExt1(String ext1)
  {
    this.ext1 = ext1;
  }
  
  @Column(name="ext2", length=128)
  public String getExt2()
  {
    return this.ext2;
  }
  
  public void setExt2(String ext2)
  {
    this.ext2 = ext2;
  }
  
  @Column(name="ext3", length=128)
  public String getExt3()
  {
    return this.ext3;
  }
  
  public void setExt3(String ext3)
  {
    this.ext3 = ext3;
  }
  
  @Column(name="time", nullable=false, length=50)
  public String getTime()
  {
    return this.time;
  }
  
  public void setTime(String time)
  {
    this.time = time;
  }
}
