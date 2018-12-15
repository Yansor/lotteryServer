package javautils.array;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javautils.StringUtil;
import org.apache.commons.lang.StringUtils;

public class ArrayUtils
{
  public static String transInIds(int[] ids)
  {
    StringBuffer sb = new StringBuffer();
    int i = 0;
    for (int j = ids.length; i < j; i++)
    {
      sb.append(ids[i]);
      if (i < j - 1) {
        sb.append(",");
      }
    }
    return sb.toString();
  }
  
  public static String transInIds(Integer[] ids)
  {
    StringBuffer sb = new StringBuffer();
    int i = 0;
    for (int j = ids.length; i < j; i++)
    {
      sb.append(ids[i].intValue());
      if (i < j - 1) {
        sb.append(",");
      }
    }
    return sb.toString();
  }
  
  public static String transInIds(List<Integer> ids)
  {
    StringBuffer sb = new StringBuffer();
    int i = 0;
    for (int j = ids.size(); i < j; i++)
    {
      sb.append(((Integer)ids.get(i)).intValue());
      if (i < j - 1) {
        sb.append(",");
      }
    }
    return sb.toString();
  }
  
  public static String transInsertIds(int[] ids)
  {
    StringBuffer sb = new StringBuffer();
    int i = 0;
    for (int j = ids.length; i < j; i++)
    {
      sb.append("[" + ids[i] + "]");
      if (i < j - 1) {
        sb.append(",");
      }
    }
    return sb.toString();
  }
  
  public static int[] transGetIds(String ids)
  {
    String[] tmp = ids.replaceAll("\\[|\\]", "").split(",");
    int[] arr = new int[tmp.length];
    for (int i = 0; i < arr.length; i++) {
      arr[i] = Integer.parseInt(tmp[i]);
    }
    return arr;
  }
  
  public static String deleteInsertIds(String ids, int id, boolean isAll)
  {
    if ((StringUtil.isNotNull(ids)) && 
      (ids.indexOf("[" + id + "]") != -1))
    {
      String[] tmp = ids.replaceAll("\\[|\\]", "").split(",");
      List<Integer> list = new ArrayList();
      for (int i = 0; i < tmp.length; i++) {
        if (id != Integer.parseInt(tmp[i])) {
          list.add(Integer.valueOf(Integer.parseInt(tmp[i])));
        } else {
          if (isAll) {
            break;
          }
        }
      }
      int[] arr = new int[list.size()];
      for (int i = 0; i < list.size(); i++) {
        arr[i] = ((Integer)list.get(i)).intValue();
      }
      return transInsertIds(arr);
    }
    return ids;
  }
  
  public static String addId(String ids, int id)
  {
    if (StringUtils.isEmpty(ids)) {
      return "[" + id + "]";
    }
    if (ids.indexOf("[" + id + "]") <= -1) {
      ids = ids + ",[" + id + "]";
    }
    return ids;
  }
  
  public static String toString(List<Integer> list)
  {
    StringBuffer sb = new StringBuffer();
    int i = 0;
    for (int j = list.size(); i < j; i++)
    {
      sb.append(list.get(i));
      if (i < j - 1) {
        sb.append(",");
      }
    }
    return sb.toString();
  }
  
  public static String toStringWithQuote(Collection<String> sets)
  {
    StringBuffer sb = new StringBuffer();
    
    Iterator<String> iterator = sets.iterator();
    while (iterator.hasNext())
    {
      String next = (String)iterator.next();
      sb.append("'").append(next).append("'");
      if (iterator.hasNext()) {
        sb.append(",");
      }
    }
    return sb.toString();
  }
  
  public static boolean hasRepeat(String[] arr)
  {
    Set<String> set = new HashSet(Arrays.asList(arr));
    return arr.length != set.size();
  }
  
  public static void main(String[] args)
  {
    String ids = "[2],[209],[72],[1]";
    
    System.out.println(deleteInsertIds(ids, 72, false));
  }
}
