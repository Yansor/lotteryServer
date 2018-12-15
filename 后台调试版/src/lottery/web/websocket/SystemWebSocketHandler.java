package lottery.web.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

@Component
public class SystemWebSocketHandler
  implements WebSocketHandler
{
  @Autowired
  private WebSocketSessionHolder sessionHolder;
  
  public void afterConnectionEstablished(WebSocketSession session)
    throws Exception
  {
    this.sessionHolder.addSession(session);
  }
  
  public void handleMessage(WebSocketSession session, WebSocketMessage<?> message)
    throws Exception
  {}
  
  public void handleTransportError(WebSocketSession session, Throwable exception)
    throws Exception
  {
    this.sessionHolder.removeBySession(session);
  }
  
  public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus)
    throws Exception
  {
    this.sessionHolder.removeBySession(session);
  }
  
  public boolean supportsPartialMessages()
  {
    return false;
  }
}
