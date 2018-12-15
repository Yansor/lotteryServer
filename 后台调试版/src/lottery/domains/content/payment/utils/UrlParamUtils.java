package lottery.domains.content.payment.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import org.apache.commons.lang.StringUtils;

public class UrlParamUtils
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
  
  public String buildSignStr(Map<String, Object> params)
  {
    StringBuilder sb = new StringBuilder();
    
    Map<String, Object> sortParams = new TreeMap(params);
    for (Map.Entry<String, Object> entry : sortParams.entrySet())
    {
      if (sb.length() != 0) {
        sb.append("&");
      }
      sb.append((String)entry.getKey()).append("=").append(entry.getValue());
    }
    return sb.toString();
  }
  
  public static String toUrlParamWithoutEmpty(Map<String, String> params, String separator, boolean bool)
  {
    if ((params == null) || (params.isEmpty())) {
      return "";
    }
    Map<String, String> sortParams = null;
    StringBuffer url = new StringBuffer();
    if (bool) {
      sortParams = new TreeMap(params);
    } else {
      sortParams = params;
    }
    Iterator<Map.Entry<String, String>> it = sortParams.entrySet().iterator();
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
  
  public static Map<String, String> fromUrlParam(String url)
  {
    Map<String, String> paramsMap = new HashMap();
    
    String[] params = url.split("&");
    String[] arrayOfString1;
    int j = (arrayOfString1 = params).length;
    for (int i = 0; i < j; i++)
    {
      String param = arrayOfString1[i];
      String[] values = param.split("=");
      if (values != null)
      {
        String key = values.length > 0 ? values[0] : null;
        String value = values.length > 1 ? values[1] : null;
        if (key != null) {
          paramsMap.put(key, value);
        }
      }
    }
    return paramsMap;
  }
  
  private static boolean isEmpty(String value)
  {
    return (value == null) || ("".equals(value));
  }
}
