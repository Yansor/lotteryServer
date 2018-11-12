package admin.web.helper.session;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionListener implements HttpSessionListener
{
    public void sessionCreated(final HttpSessionEvent event) {
    }
    
    public void sessionDestroyed(final HttpSessionEvent event) {
        final HttpSession session = event.getSession();
        if (session != null) {
            final SessionUser sessionUser = (SessionUser)session.getAttribute("SESSION_USER_PROFILE_SES");
            if (sessionUser != null) {
                final HttpSession onlineSession = SessionManager.onlineUser.get(sessionUser.getUsername());
                if (onlineSession != null) {
                    final String onlineSId = onlineSession.getId();
                    if (onlineSId.equals(session.getId())) {
                        SessionManager.onlineUser.remove(sessionUser.getUsername());
                    }
                }
            }
        }
    }
}
