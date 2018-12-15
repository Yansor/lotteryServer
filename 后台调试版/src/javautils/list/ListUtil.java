package javautils.list;

import java.util.List;
import javautils.StringUtil;

public class ListUtil
{
  public static int[] transObjectToInt(Object[] o)
  {
    int[] t = new int[o.length];
    for (int i = 0; i < o.length; i++)
    {
      if ((o[i] instanceof Integer)) {
        t[i] = ((Integer)o[i]).intValue();
      }
      if ((o[i] instanceof String))
      {
        String s = (String)o[i];
        if (StringUtil.isIntegerString(s)) {
          t[i] = Integer.parseInt(s);
        }
      }
    }
    return t;
  }
  
  public static String transListToString(List<?> list)
  {
    StringBuffer sb = new StringBuffer();
    for (Object obj : list) {
      sb.append(String.valueOf(obj) + ", ");
    }
    if (list.size() > 0) {
      return sb.substring(0, sb.length() - 2);
    }
    return sb.toString();
  }
}
