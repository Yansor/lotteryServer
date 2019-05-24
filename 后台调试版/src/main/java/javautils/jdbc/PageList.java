package javautils.jdbc;

import java.util.ArrayList;
import java.util.List;

public class PageList
{
  private List<?> list;
  private int count;
  
  public PageList()
  {
    this.list = new ArrayList();
    this.count = 0;
  }
  
  public PageList(List<?> list, int count)
  {
    this.list = list;
    this.count = count;
  }
  
  public List<?> getList()
  {
    return this.list;
  }
  
  public void setList(List<?> list)
  {
    this.list = list;
  }
  
  public int getCount()
  {
    return this.count;
  }
  
  public void setCount(int count)
  {
    this.count = count;
  }
}
