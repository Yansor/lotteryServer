package admin.web.helper.cookie;

import javautils.encrypt.DESUtil;
import javautils.http.CookieUtil;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

@Component
public class CookieManager
{
  public static void cleanCookie(HttpServletRequest request, HttpServletResponse response, String[] cookieKeys)
  {
    String[] arrayOfString;
    int j = (arrayOfString = cookieKeys).length;
    for (int i = 0; i < j; i++)
    {
      String cookieKey = arrayOfString[i];
      CookieUtil.getInstance().cleanCookie(request, response, cookieKey);
    }
  }
  
  public static void cleanCookie(HttpServletRequest request, HttpServletResponse response, String cookieKey)
  {
    CookieUtil.getInstance().cleanCookie(request, response, cookieKey);
  }
  
  public static void cleanUserCookie(HttpServletRequest request, HttpServletResponse response)
  {
    cleanCookie(request, response, "STOKEN");
    cleanCookie(request, response, "SAVEUSER");
    cleanCookie(request, response, "PTOKEN");
  }
  
  public static void setCurrentUser(HttpServletResponse response, CookieUser cookieUser)
  {
    String stoken = ObjectId.get().toString();
    String desEmial = DESUtil.getInstance().encryptStr(cookieUser.getUsername(), stoken);
    String desPassword = DESUtil.getInstance().encryptStr(cookieUser.getPassword(), stoken);
    CookieUtil.getInstance().addCookie(response, "STOKEN", stoken, 2592000);
    CookieUtil.getInstance().addCookie(response, "SAVEUSER", desEmial, 2592000);
    CookieUtil.getInstance().addCookie(response, "PTOKEN", desPassword, 2592000);
  }
  
  public static CookieUser getCurrentUser(HttpServletRequest request)
  {
    Cookie cookie_stoken = CookieUtil.getInstance().getCookieByName(request, "STOKEN");
    Cookie cookie_saveuser = CookieUtil.getInstance().getCookieByName(request, "SAVEUSER");
    Cookie cookie_ptoken = CookieUtil.getInstance().getCookieByName(request, "PTOKEN");
    if ((cookie_stoken != null) && (cookie_saveuser != null) && (cookie_ptoken != null))
    {
      String stoken = cookie_stoken.getValue();
      String desUsername = cookie_saveuser.getValue();
      String desPassword = cookie_ptoken.getValue();
      String username = DESUtil.getInstance().decryptStr(desUsername, stoken);
      String password = DESUtil.getInstance().decryptStr(desPassword, stoken);
      
      CookieUser cookieUser = new CookieUser();
      cookieUser.setUsername(username);
      cookieUser.setPassword(password);
      return cookieUser;
    }
    return null;
  }
}
