package lottery.domains.content.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="user_security", catalog="ecai")
public class UserSecurity
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private int id;
  private int userId;
  private String key;
  private String value;
  
  public UserSecurity() {}
  
  public UserSecurity(int userId, String key, String value)
  {
    this.userId = userId;
    this.key = key;
    this.value = value;
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
  
  @Column(name="`key`", nullable=false, length=128)
  public String getKey()
  {
    return this.key;
  }
  
  public void setKey(String key)
  {
    this.key = key;
  }
  
  @Column(name="`value`", nullable=false, length=128)
  public String getValue()
  {
    return this.value;
  }
  
  public void setValue(String value)
  {
    this.value = value;
  }
}
