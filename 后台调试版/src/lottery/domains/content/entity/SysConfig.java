package lottery.domains.content.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="sys_config", catalog="ecai")
public class SysConfig
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private String group;
  private String key;
  private String value;
  private String description;
  
  public SysConfig() {}
  
  public SysConfig(String value)
  {
    this.value = value;
  }
  
  public SysConfig(String value, String description)
  {
    this.value = value;
    this.description = description;
  }
  
  @Id
  @Column(name="`group`", nullable=false, length=64)
  public String getGroup()
  {
    return this.group;
  }
  
  public void setGroup(String group)
  {
    this.group = group;
  }
  
  @Id
  @Column(name="`key`", nullable=false, length=64)
  public String getKey()
  {
    return this.key;
  }
  
  public void setKey(String key)
  {
    this.key = key;
  }
  
  @Column(name="`value`", nullable=false)
  public String getValue()
  {
    return this.value;
  }
  
  public void setValue(String value)
  {
    this.value = value;
  }
  
  @Column(name="description")
  public String getDescription()
  {
    return this.description;
  }
  
  public void setDescription(String description)
  {
    this.description = description;
  }
}
