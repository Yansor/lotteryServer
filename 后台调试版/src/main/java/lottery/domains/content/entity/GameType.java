package lottery.domains.content.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="game_type", catalog="ecai")
public class GameType
  implements Serializable
{
  private int id;
  private String typeName;
  private int platformId;
  private int sequence;
  private int display;
  
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
  
  @Column(name="type_name", nullable=false, length=50)
  public String getTypeName()
  {
    return this.typeName;
  }
  
  public void setTypeName(String typeName)
  {
    this.typeName = typeName;
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
  
  @Column(name="sequence", nullable=false)
  public int getSequence()
  {
    return this.sequence;
  }
  
  public void setSequence(int sequence)
  {
    this.sequence = sequence;
  }
  
  @Column(name="display", nullable=false)
  public int getDisplay()
  {
    return this.display;
  }
  
  public void setDisplay(int display)
  {
    this.display = display;
  }
}
