package lottery.web.websocket;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

@Component
public class WebSocketMsgSender
{
  private static final Logger log = LoggerFactory.getLogger(WebSocketMsgSender.class);
  @Autowired
  private WebSocketSessionHolder webSocketSessionHolder;
  
  public boolean sendHighPrizeNotice(String msg)
  {
    List<WebSocketSession> sessions = this.webSocketSessionHolder.listAllSessions();
    if (CollectionUtils.isEmpty(sessions)) {
      return true;
    }
    for (WebSocketSession session : sessions)
    {
      Map<String, Object> attributes = session.getAttributes();
      int _type = Integer.valueOf(attributes.get("type").toString()).intValue();
      if (_type == 1) {
        try
        {
          if (session.isOpen()) {
            synchronized (session)
            {
              session.sendMessage(new TextMessage(msg));
            }
          }
        }
        catch (IOException e)
        {
          log.error("发送WebSocket消息时出错", e);
          return false;
        }
      }
    }
    return true;
  }
}
