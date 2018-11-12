package lottery.web.websocket;

import admin.web.helper.session.SessionUser;
import java.util.Iterator;
import java.util.Collection;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.socket.WebSocketSession;
import java.util.Hashtable;
import org.springframework.stereotype.Component;

@Component
public class WebSocketSessionHolder
{
    private Hashtable<String, WebSocketSession> sessionTable;
    
    public WebSocketSessionHolder() {
        this.sessionTable = new Hashtable<String, WebSocketSession>();
    }
    
    public void closeSession(final WebSocketSession session) {
        try {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        catch (Exception ex) {}
    }
    
    public List<WebSocketSession> listAllSessions() {
        final Collection<WebSocketSession> values = this.sessionTable.values();
        final List<WebSocketSession> sessions = new ArrayList<WebSocketSession>();
        for (final WebSocketSession value : values) {
            if (value != null && value.isOpen()) {
                sessions.add(value);
            }
        }
        return sessions;
    }
    
    public WebSocketSession getSession(final String username) {
        return this.sessionTable.get(username);
    }
    
    public boolean addSession(final WebSocketSession session) {
        if (session == null) {
            return false;
        }
        final SessionUser bean = (SessionUser) session.getAttributes().get("SESSION_USER_PROFILE_SES");
        if (bean == null) {
            return false;
        }
        final WebSocketSession thisSession = this.sessionTable.get(bean.getUsername());
        if (thisSession == null) {
            this.sessionTable.put(bean.getUsername(), session);
        }
        else if (!thisSession.getId().equals(session.getId())) {
            this.closeSession(thisSession);
            this.sessionTable.put(bean.getUsername(), session);
        }
        return true;
    }
    
    public boolean removeByUser(final String username) {
        final WebSocketSession thisSession = this.sessionTable.get(username);
        if (thisSession != null) {
            this.closeSession(thisSession);
            this.sessionTable.remove(username);
        }
        return true;
    }
    
    public boolean removeBySession(final WebSocketSession session) {
        if (session != null) {
            final SessionUser bean = (SessionUser)session.getAttributes().get("SESSION_USER_PROFILE_SES");
            if (bean != null) {
                final WebSocketSession thisSession = this.sessionTable.get(bean.getUsername());
                if (thisSession != null) {
                    this.closeSession(thisSession);
                    this.sessionTable.remove(bean.getUsername());
                }
            }
        }
        return true;
    }
}
