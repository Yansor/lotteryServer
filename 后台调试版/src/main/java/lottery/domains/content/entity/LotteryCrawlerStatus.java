package lottery.domains.content.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="lottery_crawler_status", catalog="ecai", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"show_name"}), @javax.persistence.UniqueConstraint(columnNames={"short_name"})})
public class LotteryCrawlerStatus
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private int id;
  private String showName;
  private String shortName;
  private int times;
  private String lastExpect;
  private String lastUpdate;
  
  public LotteryCrawlerStatus() {}
  
  public LotteryCrawlerStatus(String showName, String shortName, int times, String lastExpect, String lastUpdate)
  {
    this.showName = showName;
    this.shortName = shortName;
    this.times = times;
    this.lastExpect = lastExpect;
    this.lastUpdate = lastUpdate;
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
  
  @Column(name="show_name", unique=true, nullable=false, length=128)
  public String getShowName()
  {
    return this.showName;
  }
  
  public void setShowName(String showName)
  {
    this.showName = showName;
  }
  
  @Column(name="short_name", unique=true, nullable=false, length=32)
  public String getShortName()
  {
    return this.shortName;
  }
  
  public void setShortName(String shortName)
  {
    this.shortName = shortName;
  }
  
  @Column(name="times", nullable=false)
  public int getTimes()
  {
    return this.times;
  }
  
  public void setTimes(int times)
  {
    this.times = times;
  }
  
  @Column(name="last_expect", nullable=false, length=32)
  public String getLastExpect()
  {
    return this.lastExpect;
  }
  
  public void setLastExpect(String lastExpect)
  {
    this.lastExpect = lastExpect;
  }
  
  @Column(name="last_update", nullable=false, length=19)
  public String getLastUpdate()
  {
    return this.lastUpdate;
  }
  
  public void setLastUpdate(String lastUpdate)
  {
    this.lastUpdate = lastUpdate;
  }
}
