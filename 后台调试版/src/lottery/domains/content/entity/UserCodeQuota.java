package lottery.domains.content.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="user_code_quota", catalog="ecai", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"user_id", "code"})})
public class UserCodeQuota
  implements Serializable
{
  private int id;
  private int userId;
  private int code;
  private int sysAllocateQuantity;
  private int upAllocateQuantity;
  
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
  
  @Column(name="code", nullable=false)
  public int getCode()
  {
    return this.code;
  }
  
  public void setCode(int code)
  {
    this.code = code;
  }
  
  @Column(name="sys_allocate_quantity")
  public int getSysAllocateQuantity()
  {
    return this.sysAllocateQuantity;
  }
  
  public void setSysAllocateQuantity(int sysAllocateQuantity)
  {
    this.sysAllocateQuantity = sysAllocateQuantity;
  }
  
  @Column(name="up_allocate_quantity")
  public int getUpAllocateQuantity()
  {
    return this.upAllocateQuantity;
  }
  
  public void setUpAllocateQuantity(int upAllocateQuantity)
  {
    this.upAllocateQuantity = upAllocateQuantity;
  }
}
