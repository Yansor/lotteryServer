package admin.web.helper.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public final class AuthenticateAccountInterceptor
  extends HandlerInterceptorAdapter
{
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
  {
    return true;
  }
  
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {}
  
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) {}
}
