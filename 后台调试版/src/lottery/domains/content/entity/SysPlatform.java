package lottery.domains.content.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="sys_platform", catalog="ecai", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"name"})})
public class SysPlatform
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private int id;
  private String name;
  private int upid;
  private int status;
  
  public SysPlatform() {}
  
  public SysPlatform(String name, int upid, int status)
  {
    this.name = name;
    this.upid = upid;
    this.status = status;
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
  
  @Column(name="name", unique=true, nullable=false, length=128)
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  @Column(name="upid", nullable=false)
  public int getUpid()
  {
    return this.upid;
  }
  
  public void setUpid(int upid)
  {
    this.upid = upid;
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
}
