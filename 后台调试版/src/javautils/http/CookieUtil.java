package javautils.http;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CookieUtil
{
  protected static final Logger logger = LoggerFactory.getLogger(CookieUtil.class);
  private static CookieUtil instance;
  
  private static synchronized void synInit()
  {
    if (instance == null) {
      instance = new CookieUtil();
    }
  }
  
  public static CookieUtil getInstance()
  {
    if (instance == null) {
      synInit();
    }
    return instance;
  }
  
  public void addCookie(HttpServletResponse response, String name, String value, int maxAge)
  {
    Cookie cookie = new Cookie(name, value);
    if (maxAge > 0) {
      cookie.setMaxAge(maxAge);
    }
    response.addCookie(cookie);
    logger.debug(name + "：Cookie已经设置");
  }
  
  public void cleanCookie(HttpServletRequest request, HttpServletResponse response, String name)
  {
    Cookie cookie = getCookieByName(request, name);
    if (cookie != null)
    {
      cookie.setMaxAge(0);
      response.addCookie(cookie);
      logger.debug(name + "：Cookie已经删除");
    }
    else
    {
      logger.debug(name + "：Cookie为空");
    }
  }
  
  public Cookie getCookieByName(HttpServletRequest request, String name)
  {
    Map<String, Cookie> cookieMap = ReadCookieMap(request);
    if (cookieMap.containsKey(name))
    {
      Cookie cookie = (Cookie)cookieMap.get(name);
      return cookie;
    }
    return null;
  }
  
  public Map<String, Cookie> ReadCookieMap(HttpServletRequest request)
  {
    Map<String, Cookie> cookieMap = new HashMap();
    Cookie[] cookies = request.getCookies();
    if (cookies != null)
    {
      Cookie[] arrayOfCookie1;
      int j = (arrayOfCookie1 = cookies).length;
      for (int i = 0; i < j; i++)
      {
        Cookie cookie = arrayOfCookie1[i];
        cookieMap.put(cookie.getName(), cookie);
      }
    }
    return cookieMap;
  }
}
