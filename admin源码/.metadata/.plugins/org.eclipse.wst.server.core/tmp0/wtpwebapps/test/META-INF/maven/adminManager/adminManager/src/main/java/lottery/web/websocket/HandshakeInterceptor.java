package lottery.web.websocket;

import java.util.Enumeration;
import java.util.HashMap;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import admin.web.helper.session.SessionUser;
import org.springframework.http.server.ServletServerHttpRequest;
import java.util.Map;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

@Component
public class HandshakeInterceptor extends HttpSessionHandshakeInterceptor
{
    public boolean beforeHandshake(final ServerHttpRequest request, final ServerHttpResponse response, final WebSocketHandler wsHandler, final Map<String, Object> attributes) throws Exception {
        if (request.getHeaders().containsKey((Object)"Sec-WebSocket-Extensions")) {
            request.getHeaders().set("Sec-WebSocket-Extensions", "permessage-deflate");
        }
        if (request instanceof ServletServerHttpRequest) {
            final ServletServerHttpRequest servletRequest = (ServletServerHttpRequest)request;
            final HttpServletRequest httpServletRequest = servletRequest.getServletRequest();
            final Map<String, String> params = this.getParams(httpServletRequest);
            if (params.containsKey("type")) {
                final HttpSession session = httpServletRequest.getSession(false);
                if (session != null) {
                    final SessionUser bean = (SessionUser)session.getAttribute("SESSION_USER_PROFILE_SES");
                    if (bean != null) {
                        final boolean check = this.checkType(params, bean);
                        if (!check) {
                            return false;
                        }
                        attributes.put("SESSION_USER_PROFILE_SES", bean);
                        attributes.putAll(params);
                        return super.beforeHandshake(request, response, wsHandler, (Map)attributes);
                    }
                }
            }
        }
        return false;
    }
    
    private boolean checkType(final Map<String, String> params, final SessionUser bean) {
        if (!params.containsKey("type") || bean == null) {
            return false;
        }
        final String type = params.get("type").toString();
        return "1".equals(type) && (bean.getRoleId() == 1 || bean.getRoleId() == 2 || bean.getRoleId() == 10 || bean.getRoleId() == 11);
    }
    
    private Map<String, String> getParams(final HttpServletRequest httpServletRequest) {
        final Enumeration parameterNames = httpServletRequest.getParameterNames();
        final Map<String, String> params = new HashMap<String, String>();
        while (parameterNames.hasMoreElements()) {
            final String paramName = parameterNames.nextElement().toString();
            final String paramValue = httpServletRequest.getParameter(paramName);
            params.put(paramName, paramValue);
        }
        return params;
    }
    
    public void afterHandshake(final ServerHttpRequest request, final ServerHttpResponse response, final WebSocketHandler wsHandler, final Exception ex) {
        super.afterHandshake(request, response, wsHandler, ex);
    }
}
