package lottery.domains.content.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="lottery_play_rules_group", catalog="ecai")
public class LotteryPlayRulesGroup
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private int id;
  private int typeId;
  private String name;
  private String code;
  private int status;
  private int sort;
  
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
  
  @Column(name="type_id", nullable=false)
  public int getTypeId()
  {
    return this.typeId;
  }
  
  public void setTypeId(int typeId)
  {
    this.typeId = typeId;
  }
  
  @Column(name="name", nullable=false, length=128)
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  @Column(name="code", nullable=false, length=128)
  public String getCode()
  {
    return this.code;
  }
  
  public void setCode(String code)
  {
    this.code = code;
  }
  
  @Column(name="status")
  public int getStatus()
  {
    return this.status;
  }
  
  public void setStatus(int status)
  {
    this.status = status;
  }
  
  @Column(name="sort")
  public int getSort()
  {
    return this.sort;
  }
  
  public void setSort(int sort)
  {
    this.sort = sort;
  }
}
