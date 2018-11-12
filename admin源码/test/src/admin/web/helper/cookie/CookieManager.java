package admin.web.helper.cookie;

import javax.servlet.http.Cookie;
import javautils.encrypt.DESUtil;
import org.bson.types.ObjectId;
import javautils.http.CookieUtil;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public class CookieManager
{
    public static void cleanCookie(final HttpServletRequest request, final HttpServletResponse response, final String[] cookieKeys) {
        for (final String cookieKey : cookieKeys) {
            CookieUtil.getInstance().cleanCookie(request, response, cookieKey);
        }
    }
    
    public static void cleanCookie(final HttpServletRequest request, final HttpServletResponse response, final String cookieKey) {
        CookieUtil.getInstance().cleanCookie(request, response, cookieKey);
    }
    
    public static void cleanUserCookie(final HttpServletRequest request, final HttpServletResponse response) {
        cleanCookie(request, response, "STOKEN");
        cleanCookie(request, response, "SAVEUSER");
        cleanCookie(request, response, "PTOKEN");
    }
    
    public static void setCurrentUser(final HttpServletResponse response, final CookieUser cookieUser) {
        final String stoken = ObjectId.get().toString();
        final String desEmial = DESUtil.getInstance().encryptStr(cookieUser.getUsername(), stoken);
        final String desPassword = DESUtil.getInstance().encryptStr(cookieUser.getPassword(), stoken);
        CookieUtil.getInstance().addCookie(response, "STOKEN", stoken, 2592000);
        CookieUtil.getInstance().addCookie(response, "SAVEUSER", desEmial, 2592000);
        CookieUtil.getInstance().addCookie(response, "PTOKEN", desPassword, 2592000);
    }
    
    public static CookieUser getCurrentUser(final HttpServletRequest request) {
        final Cookie cookie_stoken = CookieUtil.getInstance().getCookieByName(request, "STOKEN");
        final Cookie cookie_saveuser = CookieUtil.getInstance().getCookieByName(request, "SAVEUSER");
        final Cookie cookie_ptoken = CookieUtil.getInstance().getCookieByName(request, "PTOKEN");
        if (cookie_stoken != null && cookie_saveuser != null && cookie_ptoken != null) {
            final String stoken = cookie_stoken.getValue();
            final String desUsername = cookie_saveuser.getValue();
            final String desPassword = cookie_ptoken.getValue();
            final String username = DESUtil.getInstance().decryptStr(desUsername, stoken);
            final String password = DESUtil.getInstance().decryptStr(desPassword, stoken);
            final CookieUser cookieUser = new CookieUser();
            cookieUser.setUsername(username);
            cookieUser.setPassword(password);
            return cookieUser;
        }
        return null;
    }
}
