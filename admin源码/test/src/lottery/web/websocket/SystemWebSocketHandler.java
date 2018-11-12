package lottery.web.websocket;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;

@Component
public class SystemWebSocketHandler implements WebSocketHandler
{
    @Autowired
    private WebSocketSessionHolder sessionHolder;
    
    public void afterConnectionEstablished(final WebSocketSession session) throws Exception {
        this.sessionHolder.addSession(session);
    }
    
    public void handleMessage(final WebSocketSession session, final WebSocketMessage<?> message) throws Exception {
    }
    
    public void handleTransportError(final WebSocketSession session, final Throwable exception) throws Exception {
        this.sessionHolder.removeBySession(session);
    }
    
    public void afterConnectionClosed(final WebSocketSession session, final CloseStatus closeStatus) throws Exception {
        this.sessionHolder.removeBySession(session);
    }
    
    public boolean supportsPartialMessages() {
        return false;
    }
}
