package javautils.http;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javautils.StringUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpUtil
{
  private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);
  public static final String json = "text/json";
  public static final String html = "text/html";
  public static final String xml = "text/xml";
  
  public static void write(HttpServletResponse response, String s)
  {
    logger.debug(s);
    if (StringUtil.isNotNull(s)) {
      try
      {
        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        writer.write(s);
        writer.flush();
        writer.close();
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }
  }
  
  public static void write(HttpServletResponse response, String s, String ContentType)
  {
    logger.debug(s);
    if (StringUtil.isNotNull(s)) {
      try
      {
        response.setContentType(ContentType);
        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        writer.write(s);
        writer.flush();
        writer.close();
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }
  }
  
  public static void writeJSONP(HttpServletRequest request, HttpServletResponse response, String s, String ContentType)
  {
    String callback = request.getParameter("callback");
    logger.debug(s);
    if (StringUtil.isNotNull(s)) {
      try
      {
        String callbackStr = callback + "(" + s + ")";
        response.setContentType(ContentType);
        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        writer.write(callbackStr);
        writer.flush();
        writer.close();
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }
  }
  
  public static void writeJSONP(HttpServletRequest request, HttpServletResponse response, String s)
  {
    String callback = request.getParameter("callback");
    logger.debug(s);
    if (StringUtil.isNotNull(s)) {
      try
      {
        String callbackStr = callback + "(" + s + ")";
        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        writer.write(callbackStr);
        writer.flush();
        writer.close();
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }
  }
  
  public static String getWebDomainPath(HttpServletRequest request)
  {
    String name = request.getServerName();
    if ("localhost".equals(name)) {
      name = "127.0.0.1";
    }
    int port = request.getServerPort();
    if (port == 80) {
      return "http://" + name;
    }
    return "http://" + name + ":" + port;
  }
  
  public static String getWebPath(HttpServletRequest request)
  {
    String path = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + request.getServletPath();
    return path;
  }
  
  public static String getRequestPath(HttpServletRequest request)
  {
    String path = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + request.getServletPath();
    String queryStr = request.getQueryString();
    if (StringUtil.isNotNull(queryStr)) {
      path = path + "?" + queryStr;
    }
    return path;
  }
  
  public static Map<String, String> getRequestMap(String queryStr)
  {
    Map<String, String> map = new HashMap();
    if (StringUtil.isNotNull(queryStr))
    {
      String[] strs = queryStr.split("&");
      String[] arrayOfString1;
      int j = (arrayOfString1 = strs).length;
      for (int i = 0; i < j; i++)
      {
        String str = arrayOfString1[i];
        String[] keyValue = str.split("=");
        map.put(keyValue[0], keyValue[1]);
      }
    }
    return map;
  }
  
  public static String getClientIpAddr(HttpServletRequest request)
  {
    String ip = request.getHeader("X-Forwarded-For");
    if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
      ip = request.getHeader("Proxy-Client-IP");
    }
    if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
      ip = request.getHeader("WL-Proxy-Client-IP");
    }
    if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
      ip = request.getHeader("HTTP_CLIENT_IP");
    }
    if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
      ip = request.getHeader("HTTP_X_FORWARDED_FOR");
    }
    if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
      ip = request.getRemoteAddr();
    }
    if (ip.split(",").length > 0) {
      ip = ip.split(",")[0];
    }
    return ip;
  }
  
  public static Short getShortParameter(HttpServletRequest request, String name)
  {
    String value = request.getParameter(name);
    try
    {
      return Short.valueOf(Short.parseShort(value));
    }
    catch (Exception localException) {}
    return null;
  }
  
  public static Integer getIntParameter(HttpServletRequest request, String name)
  {
    String value = request.getParameter(name);
    try
    {
      if (StringUtil.isNotNull(value)) {
        return Integer.valueOf(Integer.parseInt(value));
      }
      return null;
    }
    catch (Exception localException) {}
    return null;
  }
  
  public static String getStringParameterTrim(HttpServletRequest request, String name)
  {
    String value = request.getParameter(name);
    try
    {
      if (value != null) {
        return value.trim();
      }
      return value;
    }
    catch (Exception localException) {}
    return null;
  }
  
  public static Boolean getBooleanParameter(HttpServletRequest request, String name)
  {
    String value = request.getParameter(name);
    try
    {
      return Boolean.valueOf(Boolean.parseBoolean(value));
    }
    catch (Exception localException) {}
    return null;
  }
  
  public static Double getDoubleParameter(HttpServletRequest request, String name)
  {
    String value = request.getParameter(name);
    try
    {
      if (StringUtil.isNotNull(value)) {
        return Double.valueOf(Double.parseDouble(value));
      }
      return null;
    }
    catch (Exception localException) {}
    return null;
  }
  
  public static Float getFloatParameter(HttpServletRequest request, String name)
  {
    String value = request.getParameter(name);
    try
    {
      return Float.valueOf(Float.parseFloat(value));
    }
    catch (Exception localException) {}
    return null;
  }
  
  public static Long getLongParameter(HttpServletRequest request, String name)
  {
    String value = request.getParameter(name);
    try
    {
      return Long.valueOf(Long.parseLong(value));
    }
    catch (Exception localException) {}
    return null;
  }
  
  public static String encodeURL(String destURL)
  {
    try
    {
      destURL = URLEncoder.encode(destURL, "utf-8");
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException) {}
    return destURL;
  }
  
  public static String decodeURL(String destURL)
  {
    try
    {
      destURL = URLDecoder.decode(destURL, "utf-8");
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException) {}
    return destURL;
  }
  
  public static String getRequestURL(HttpServletRequest request)
  {
    boolean flag = true;
    
    StringBuffer requestURL = request.getRequestURL();
    Enumeration<String> paramNames = request.getParameterNames();
    while (paramNames.hasMoreElements())
    {
      if (flag)
      {
        flag = false;
        requestURL.append("?");
      }
      else
      {
        requestURL.append("&");
      }
      String paramName = (String)paramNames.nextElement();
      try
      {
        String paramValue = URLEncoder.encode(request.getParameter(paramName), "utf-8");
        requestURL.append(paramName).append("=").append(paramValue);
      }
      catch (UnsupportedEncodingException localUnsupportedEncodingException) {}
    }
    return requestURL.toString();
  }
  
  public static void printRquestParams(HttpServletRequest request)
  {
    Map<String, String[]> paramsMap = request.getParameterMap();
    Object[] arrayOfObject;
    int j = (arrayOfObject = paramsMap.keySet().toArray()).length;
    for (int i = 0; i < j; i++)
    {
      Object key = arrayOfObject[i];
      System.out.println("key:" + key);
      System.out.print("value:");
      String[] arrayOfString;
      int m = (arrayOfString = (String[])paramsMap.get(key)).length;
      for (int k = 0; k < m; k++)
      {
        String value = arrayOfString[k];
        System.out.print(value);
      }
      System.out.println();
    }
  }
  
  public static void printHeaderFields(HttpURLConnection conn)
  {
    Map<String, List<String>> headerFields = conn.getHeaderFields();
    Object[] arrayOfObject;
    int j = (arrayOfObject = headerFields.keySet().toArray()).length;
    for (int i = 0; i < j; i++)
    {
      Object key = arrayOfObject[i];
      System.out.println(key + ":" + ((List)headerFields.get(key)).toString());
      for (String value : (List<String>)headerFields.get(key)) {
        System.out.println("==============:" + value);
      }
    }
  }
}
