package lottery.web.websocket;

import admin.web.helper.session.SessionUser;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

@Component
public class WebSocketSessionHolder
{
  private Hashtable<String, WebSocketSession> sessionTable = new Hashtable();
  
  public void closeSession(WebSocketSession session)
  {
    try
    {
      if ((session != null) && (session.isOpen())) {
        session.close();
      }
    }
    catch (Exception localException) {}
  }
  
  public List<WebSocketSession> listAllSessions()
  {
    Collection<WebSocketSession> values = this.sessionTable.values();
    
    List<WebSocketSession> sessions = new ArrayList();
    for (WebSocketSession value : values) {
      if ((value != null) && (value.isOpen())) {
        sessions.add(value);
      }
    }
    return sessions;
  }
  
  public WebSocketSession getSession(String username)
  {
    return (WebSocketSession)this.sessionTable.get(username);
  }
  
  public boolean addSession(WebSocketSession session)
  {
    if (session == null) {
      return false;
    }
    SessionUser bean = (SessionUser)session.getAttributes().get("SESSION_USER_PROFILE_SES");
    if (bean == null) {
      return false;
    }
    WebSocketSession thisSession = (WebSocketSession)this.sessionTable.get(bean.getUsername());
    if (thisSession == null)
    {
      this.sessionTable.put(bean.getUsername(), session);
    }
    else if (!thisSession.getId().equals(session.getId()))
    {
      closeSession(thisSession);
      this.sessionTable.put(bean.getUsername(), session);
    }
    return true;
  }
  
  public boolean removeByUser(String username)
  {
    WebSocketSession thisSession = (WebSocketSession)this.sessionTable.get(username);
    if (thisSession != null)
    {
      closeSession(thisSession);
      this.sessionTable.remove(username);
    }
    return true;
  }
  
  public boolean removeBySession(WebSocketSession session)
  {
    if (session != null)
    {
      SessionUser bean = (SessionUser)session.getAttributes().get("SESSION_USER_PROFILE_SES");
      if (bean != null)
      {
        WebSocketSession thisSession = (WebSocketSession)this.sessionTable.get(bean.getUsername());
        if (thisSession != null)
        {
          closeSession(thisSession);
          this.sessionTable.remove(bean.getUsername());
        }
      }
    }
    return true;
  }
}
