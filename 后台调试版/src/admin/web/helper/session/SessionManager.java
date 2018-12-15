package admin.web.helper.session;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpSession;

public final class SessionManager
{
  public static Map<String, HttpSession> onlineUser = new HashMap();
  
  public static void cleanUserSession(HttpSession session)
  {
    SessionUser sessionUser = (SessionUser)session.getAttribute("SESSION_USER_PROFILE_SES");
    onlineUser.remove(sessionUser.getUsername());
    session.removeAttribute("SESSION_USER_PROFILE_SES");
  }
  
  public static void setCurrentUser(HttpSession session, SessionUser sessionUser)
  {
    session.setAttribute("SESSION_USER_PROFILE_SES", sessionUser);
    onlineUser.put(sessionUser.getUsername(), session);
  }
  
  public static SessionUser getCurrentUser(HttpSession session)
  {
    SessionUser sessionUser = (SessionUser)session.getAttribute("SESSION_USER_PROFILE_SES");
    if (sessionUser != null)
    {
      HttpSession onlineSession = (HttpSession)onlineUser.get(sessionUser.getUsername());
      if (onlineSession != null)
      {
        String onlineSId = onlineSession.getId();
        if (!onlineSId.equals(session.getId()))
        {
          cleanUserSession(session);
          return null;
        }
      }
    }
    return sessionUser;
  }
}
