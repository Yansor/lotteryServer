package lottery.web.websocket;

import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
@EnableWebSocket
public class WebSocketConfig extends WebMvcConfigurerAdapter implements WebSocketConfigurer
{
    @Autowired
    private SystemWebSocketHandler socketHandler;
    
    public void registerWebSocketHandlers(final WebSocketHandlerRegistry registry) {
        registry.addHandler((WebSocketHandler)this.socketHandler, new String[] { "/websocket" }).addInterceptors(new HandshakeInterceptor[] { (HandshakeInterceptor)new lottery.web.websocket.HandshakeInterceptor() });
        registry.addHandler((WebSocketHandler)this.socketHandler, new String[] { "/websocket/sockjs" }).addInterceptors(new HandshakeInterceptor[] { (HandshakeInterceptor)new lottery.web.websocket.HandshakeInterceptor() }).withSockJS();
    }
}
