package admin.domains.content.biz.utils;

import java.util.LinkedList;
import java.util.List;

public class JSMenuVO
{
  private String name;
  private String icon;
  private String link;
  private List<JSMenuVO> items = new LinkedList();
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  public String getIcon()
  {
    return this.icon;
  }
  
  public void setIcon(String icon)
  {
    this.icon = icon;
  }
  
  public String getLink()
  {
    return this.link;
  }
  
  public void setLink(String link)
  {
    this.link = link;
  }
  
  public List<JSMenuVO> getItems()
  {
    return this.items;
  }
  
  public void setItems(List<JSMenuVO> items)
  {
    this.items = items;
  }
}
