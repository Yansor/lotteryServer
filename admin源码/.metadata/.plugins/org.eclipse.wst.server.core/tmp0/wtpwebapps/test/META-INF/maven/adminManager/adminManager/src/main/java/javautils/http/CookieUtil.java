package javautils.http;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class CookieUtil
{
    protected static final Logger logger;
    private static CookieUtil instance;
    
    static {
        logger = LoggerFactory.getLogger((Class)CookieUtil.class);
    }
    
    private CookieUtil() {
    }
    
    private static synchronized void synInit() {
        if (CookieUtil.instance == null) {
            CookieUtil.instance = new CookieUtil();
        }
    }
    
    public static CookieUtil getInstance() {
        if (CookieUtil.instance == null) {
            synInit();
        }
        return CookieUtil.instance;
    }
    
    public void addCookie(final HttpServletResponse response, final String name, final String value, final int maxAge) {
        final Cookie cookie = new Cookie(name, value);
        if (maxAge > 0) {
            cookie.setMaxAge(maxAge);
        }
        response.addCookie(cookie);
        CookieUtil.logger.debug(String.valueOf(name) + "：Cookie已经设置");
    }
    
    public void cleanCookie(final HttpServletRequest request, final HttpServletResponse response, final String name) {
        final Cookie cookie = this.getCookieByName(request, name);
        if (cookie != null) {
            cookie.setMaxAge(0);
            response.addCookie(cookie);
            CookieUtil.logger.debug(String.valueOf(name) + "：Cookie已经删除");
        }
        else {
            CookieUtil.logger.debug(String.valueOf(name) + "：Cookie为空");
        }
    }
    
    public Cookie getCookieByName(final HttpServletRequest request, final String name) {
        final Map<String, Cookie> cookieMap = this.ReadCookieMap(request);
        if (cookieMap.containsKey(name)) {
            final Cookie cookie = cookieMap.get(name);
            return cookie;
        }
        return null;
    }
    
    public Map<String, Cookie> ReadCookieMap(final HttpServletRequest request) {
        final Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
        final Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            Cookie[] array;
            for (int length = (array = cookies).length, i = 0; i < length; ++i) {
                final Cookie cookie = array[i];
                cookieMap.put(cookie.getName(), cookie);
            }
        }
        return cookieMap;
    }
}
