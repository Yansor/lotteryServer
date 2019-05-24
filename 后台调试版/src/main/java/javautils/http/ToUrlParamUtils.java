package javautils.http;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.apache.commons.lang.StringUtils;

public class ToUrlParamUtils
{
  private static final String EMPTY = "";
  private static final String EQUALS = "=";
  private static final String DEFAULT_SEPARATOR = "&";
  
  public static String toUrlParam(Map<String, String> params)
  {
    return toUrlParam(params, "&", true);
  }
  
  public static String toUrlParam(Map<String, String> params, String separator)
  {
    return toUrlParam(params, separator, true);
  }
  
  public static String toUrlParam(Map<String, String> params, String separator, boolean ignoreEmpty)
  {
    if ((params == null) || (params.isEmpty())) {
      return "";
    }
    StringBuffer url = new StringBuffer();
    
    Iterator<Map.Entry<String, String>> it = params.entrySet().iterator();
    while (it.hasNext())
    {
      Map.Entry<String, String> entry = (Map.Entry)it.next();
      String value = (String)entry.getValue();
      boolean valueIsEmpty = isEmpty(value);
      if ((!ignoreEmpty) || (!valueIsEmpty))
      {
        url.append((String)entry.getKey()).append("=").append(value);
        if (it.hasNext()) {
          url.append(separator);
        }
      }
    }
    return url.toString();
  }
  
  public static String toUrlParamWithoutEmpty(Map<String, String> params, String separator)
  {
    if ((params == null) || (params.isEmpty())) {
      return "";
    }
    StringBuffer url = new StringBuffer();
    
    Iterator<Map.Entry<String, String>> it = params.entrySet().iterator();
    while (it.hasNext())
    {
      Map.Entry<String, String> entry = (Map.Entry)it.next();
      String value = (String)entry.getValue();
      if (!StringUtils.isEmpty(value))
      {
        url.append((String)entry.getKey()).append("=").append(value);
        if (it.hasNext()) {
          url.append(separator);
        }
      }
    }
    String urlStr = url.toString();
    if (urlStr.endsWith(separator)) {
      urlStr = urlStr.substring(0, urlStr.length() - separator.length());
    }
    return urlStr;
  }
  
  private static boolean isEmpty(String value)
  {
    return (value == null) || ("".equals(value));
  }
}
