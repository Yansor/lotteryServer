package admin.web.helper.session;

import java.util.HashMap;
import javax.servlet.http.HttpSession;
import java.util.Map;

public final class SessionManager
{
    public static Map<String, HttpSession> onlineUser;
    
    static {
        SessionManager.onlineUser = new HashMap<String, HttpSession>();
    }
    
    private SessionManager() {
    }
    
    public static void cleanUserSession(final HttpSession session) {
        final SessionUser sessionUser = (SessionUser)session.getAttribute("SESSION_USER_PROFILE_SES");
        SessionManager.onlineUser.remove(sessionUser.getUsername());
        session.removeAttribute("SESSION_USER_PROFILE_SES");
    }
    
    public static void setCurrentUser(final HttpSession session, final SessionUser sessionUser) {
        session.setAttribute("SESSION_USER_PROFILE_SES", (Object)sessionUser);
        SessionManager.onlineUser.put(sessionUser.getUsername(), session);
    }
    
    public static SessionUser getCurrentUser(final HttpSession session) {
        final SessionUser sessionUser = (SessionUser)session.getAttribute("SESSION_USER_PROFILE_SES");
        if (sessionUser != null) {
            final HttpSession onlineSession = SessionManager.onlineUser.get(sessionUser.getUsername());
            if (onlineSession != null) {
                final String onlineSId = onlineSession.getId();
                if (!onlineSId.equals(session.getId())) {
                    cleanUserSession(session);
                    return null;
                }
            }
        }
        return sessionUser;
    }
}
