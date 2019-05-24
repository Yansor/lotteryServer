package lottery.web.websocket;

import admin.web.helper.session.SessionUser;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

@Component
public class HandshakeInterceptor
  extends HttpSessionHandshakeInterceptor
{
  public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes)
    throws Exception
  {
    if (request.getHeaders().containsKey("Sec-WebSocket-Extensions")) {
      request.getHeaders().set("Sec-WebSocket-Extensions", "permessage-deflate");
    }
    if ((request instanceof ServletServerHttpRequest))
    {
      ServletServerHttpRequest servletRequest = (ServletServerHttpRequest)request;
      HttpServletRequest httpServletRequest = servletRequest.getServletRequest();
      Map<String, String> params = getParams(httpServletRequest);
      if (params.containsKey("type"))
      {
        HttpSession session = httpServletRequest.getSession(false);
        if (session != null)
        {
          SessionUser bean = (SessionUser)session.getAttribute("SESSION_USER_PROFILE_SES");
          if (bean != null)
          {
            boolean check = checkType(params, bean);
            if (!check) {
              return false;
            }
            attributes.put("SESSION_USER_PROFILE_SES", bean);
            attributes.putAll(params);
            return super.beforeHandshake(request, response, wsHandler, attributes);
          }
        }
      }
    }
    return false;
  }
  
  private boolean checkType(Map<String, String> params, SessionUser bean)
  {
    if ((!params.containsKey("type")) || (bean == null)) {
      return false;
    }
    String type = ((String)params.get("type")).toString();
    if ("1".equals(type))
    {
      if ((bean.getRoleId() == 1) || (bean.getRoleId() == 2) || (bean.getRoleId() == 10) || (bean.getRoleId() == 11)) {
        return true;
      }
      return false;
    }
    return false;
  }
  
  private Map<String, String> getParams(HttpServletRequest httpServletRequest)
  {
    Enumeration parameterNames = httpServletRequest.getParameterNames();
    
    Map<String, String> params = new HashMap();
    while (parameterNames.hasMoreElements())
    {
      String paramName = parameterNames.nextElement().toString();
      String paramValue = httpServletRequest.getParameter(paramName);
      params.put(paramName, paramValue);
    }
    return params;
  }
  
  public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception ex)
  {
    super.afterHandshake(request, response, wsHandler, ex);
  }
}
