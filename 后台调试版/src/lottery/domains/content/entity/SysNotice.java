package lottery.domains.content.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="sys_notice", catalog="ecai")
public class SysNotice
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private Integer id;
  private String title;
  private String content;
  private String simpleContent;
  private Integer sort;
  private Integer status;
  private String date;
  private String time;
  
  public SysNotice() {}
  
  public SysNotice(String title, Integer sort, Integer status, String date, String time)
  {
    this.title = title;
    this.sort = sort;
    this.status = status;
    this.date = date;
    this.time = time;
  }
  
  public SysNotice(String title, String content, String simpleContent, Integer sort, Integer status, String date, String time)
  {
    this.title = title;
    this.content = content;
    this.simpleContent = simpleContent;
    this.sort = sort;
    this.status = status;
    this.date = date;
    this.time = time;
  }
  
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name="id", unique=true, nullable=false)
  public Integer getId()
  {
    return this.id;
  }
  
  public void setId(Integer id)
  {
    this.id = id;
  }
  
  @Column(name="title", nullable=false, length=128)
  public String getTitle()
  {
    return this.title;
  }
  
  public void setTitle(String title)
  {
    this.title = title;
  }
  
  @Column(name="content", length=16777215)
  public String getContent()
  {
    return this.content;
  }
  
  public void setContent(String content)
  {
    this.content = content;
  }
  
  @Column(name="simple_content", length=16777215)
  public String getSimpleContent()
  {
    return this.simpleContent;
  }
  
  public void setSimpleContent(String simpleContent)
  {
    this.simpleContent = simpleContent;
  }
  
  @Column(name="sort", nullable=false)
  public Integer getSort()
  {
    return this.sort;
  }
  
  public void setSort(Integer sort)
  {
    this.sort = sort;
  }
  
  @Column(name="status", nullable=false)
  public Integer getStatus()
  {
    return this.status;
  }
  
  public void setStatus(Integer status)
  {
    this.status = status;
  }
  
  @Column(name="date", nullable=false, length=10)
  public String getDate()
  {
    return this.date;
  }
  
  public void setDate(String date)
  {
    this.date = date;
  }
  
  @Column(name="time", nullable=false, length=19)
  public String getTime()
  {
    return this.time;
  }
  
  public void setTime(String time)
  {
    this.time = time;
  }
}
