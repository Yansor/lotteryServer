package lottery.domains.content.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="db_server_sync", catalog="ecai")
public class DbServerSync
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private String key;
  private String lastModTime;
  private String desc;
  
  public DbServerSync() {}
  
  public DbServerSync(String lastModTime, String desc)
  {
    this.lastModTime = lastModTime;
    this.desc = desc;
  }
  
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name="`key`", unique=true, nullable=false, length=128)
  public String getKey()
  {
    return this.key;
  }
  
  public void setKey(String key)
  {
    this.key = key;
  }
  
  @Column(name="last_mod_time", length=19)
  public String getLastModTime()
  {
    return this.lastModTime;
  }
  
  public void setLastModTime(String lastModTime)
  {
    this.lastModTime = lastModTime;
  }
  
  @Column(name="`desc`")
  public String getDesc()
  {
    return this.desc;
  }
  
  public void setDesc(String desc)
  {
    this.desc = desc;
  }
}
