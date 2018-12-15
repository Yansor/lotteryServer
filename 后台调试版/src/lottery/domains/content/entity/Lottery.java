package lottery.domains.content.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="lottery", catalog="ecai")
public class Lottery
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private int id;
  private String showName;
  private String shortName;
  private int type;
  private int times;
  private int sort;
  private int status;
  private int self;
  private int dantiaoMaxPrize;
  private int expectMaxPrize;
  private int expectTrans;
  private String allowModels;
  private int display;
  
  public Lottery() {}
  
  public Lottery(String showName, int type, int times, int sort, int status, int self, int dantiaoMaxPrize, int expectMaxPrize)
  {
    this.showName = showName;
    this.type = type;
    this.times = times;
    this.sort = sort;
    this.status = status;
    this.self = self;
    this.dantiaoMaxPrize = dantiaoMaxPrize;
    this.expectMaxPrize = expectMaxPrize;
  }
  
  public Lottery(String showName, String shortName, int type, int times, int sort, int status, int self, int dantiaoMaxPrize, int expectMaxPrize)
  {
    this.showName = showName;
    this.shortName = shortName;
    this.type = type;
    this.times = times;
    this.sort = sort;
    this.status = status;
    this.self = self;
    this.dantiaoMaxPrize = dantiaoMaxPrize;
    this.expectMaxPrize = expectMaxPrize;
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
  
  @Column(name="show_name", nullable=false, length=32)
  public String getShowName()
  {
    return this.showName;
  }
  
  public void setShowName(String showName)
  {
    this.showName = showName;
  }
  
  @Column(name="short_name", length=32, nullable=false)
  public String getShortName()
  {
    return this.shortName;
  }
  
  public void setShortName(String shortName)
  {
    this.shortName = shortName;
  }
  
  @Column(name="type", nullable=false)
  public int getType()
  {
    return this.type;
  }
  
  public void setType(int type)
  {
    this.type = type;
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
  
  @Column(name="`sort`", nullable=false)
  public int getSort()
  {
    return this.sort;
  }
  
  public void setSort(int sort)
  {
    this.sort = sort;
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
  
  @Column(name="self", nullable=false)
  public int getSelf()
  {
    return this.self;
  }
  
  public void setSelf(int self)
  {
    this.self = self;
  }
  
  @Column(name="dantiao_max_prize", nullable=false)
  public int getDantiaoMaxPrize()
  {
    return this.dantiaoMaxPrize;
  }
  
  public void setDantiaoMaxPrize(int dantiaoMaxPrize)
  {
    this.dantiaoMaxPrize = dantiaoMaxPrize;
  }
  
  @Column(name="expect_max_prize", nullable=false)
  public int getExpectMaxPrize()
  {
    return this.expectMaxPrize;
  }
  
  public void setExpectMaxPrize(int expectMaxPrize)
  {
    this.expectMaxPrize = expectMaxPrize;
  }
  
  @Column(name="expect_trans", nullable=false)
  public int getExpectTrans()
  {
    return this.expectTrans;
  }
  
  public void setExpectTrans(int expectTrans)
  {
    this.expectTrans = expectTrans;
  }
  
  @Column(name="allow_models", nullable=false)
  public String getAllowModels()
  {
    return this.allowModels;
  }
  
  public void setAllowModels(String allowModels)
  {
    this.allowModels = allowModels;
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
