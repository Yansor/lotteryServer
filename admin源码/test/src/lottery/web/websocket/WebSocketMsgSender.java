package lottery.web.websocket;

import java.util.Map;
import java.util.Iterator;
import java.util.List;
import java.io.IOException;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import java.util.Collection;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class WebSocketMsgSender
{
    private static final Logger log;
    @Autowired
    private WebSocketSessionHolder webSocketSessionHolder;
    
    static {
        log = LoggerFactory.getLogger((Class)WebSocketMsgSender.class);
    }
    
    public boolean sendHighPrizeNotice(final String msg) {
        final List<WebSocketSession> sessions = this.webSocketSessionHolder.listAllSessions();
        if (CollectionUtils.isEmpty((Collection)sessions)) {
            return true;
        }
        for (final WebSocketSession session : sessions) {
            final Map<String, Object> attributes = (Map<String, Object>)session.getAttributes();
            final int _type = Integer.valueOf(attributes.get("type").toString());
            if (_type == 1) {
                try {
                    if (!session.isOpen()) {
                        continue;
                    }
                    synchronized (session) {
                        session.sendMessage((WebSocketMessage)new TextMessage((CharSequence)msg));
                    }
                    // monitorexit(session)
                }
                catch (IOException e) {
                    WebSocketMsgSender.log.error("发送WebSocket消息时出错", (Throwable)e);
                    return false;
                }
            }
        }
        return true;
    }
}
