package admin.web.helper.session;

import java.util.Map;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionListener
  implements HttpSessionListener
{
  public void sessionCreated(HttpSessionEvent event) {}
  
  public void sessionDestroyed(HttpSessionEvent event)
  {
    HttpSession session = event.getSession();
    if (session != null)
    {
      SessionUser sessionUser = (SessionUser)session.getAttribute("SESSION_USER_PROFILE_SES");
      if (sessionUser != null)
      {
        HttpSession onlineSession = (HttpSession)SessionManager.onlineUser.get(sessionUser.getUsername());
        if (onlineSession != null)
        {
          String onlineSId = onlineSession.getId();
          if (onlineSId.equals(session.getId())) {
            SessionManager.onlineUser.remove(sessionUser.getUsername());
          }
        }
      }
    }
  }
}
