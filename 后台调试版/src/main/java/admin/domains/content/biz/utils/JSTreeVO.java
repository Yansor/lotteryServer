package admin.domains.content.biz.utils;

import java.util.ArrayList;
import java.util.List;

public class JSTreeVO
{
  private String id;
  private String text;
  private String icon;
  private ArrayList<JSTreeVO> children = new ArrayList();
  
  public String getId()
  {
    return this.id;
  }
  
  public void setId(String id)
  {
    this.id = id;
  }
  
  public String getText()
  {
    return this.text;
  }
  
  public void setText(String text)
  {
    this.text = text;
  }
  
  public String getIcon()
  {
    return this.icon;
  }
  
  public void setIcon(String icon)
  {
    this.icon = icon;
  }
  
  public List<JSTreeVO> getChildren()
  {
    return this.children;
  }
  
  public void setChildren(ArrayList<JSTreeVO> children)
  {
    this.children = children;
  }
}
