package admin.web.helper.filter;

import admin.web.helper.session.SessionManager;
import admin.web.helper.session.SessionUser;
import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javautils.date.Moment;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionTimeoutFilter
  implements Filter
{
  private static final Set<String> ignorePages = new HashSet();
  private static final Set<String> ignoreUrls = new HashSet();
  
  static
  {
    ignorePages.add("/login");
    ignorePages.add("/logout");
    ignorePages.add("/access-denied");
    ignorePages.add("/page-not-found");
    ignorePages.add("/page-error");
    ignorePages.add("/page-not-login");
    
    ignoreUrls.add("/global");
    ignoreUrls.add("/high-prize-unprocess-count");
    ignoreUrls.add("/lottery-user-withdraw/list");
  }
  
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
    throws IOException, ServletException
  {
    if (!(request instanceof HttpServletRequest))
    {
      chain.doFilter(request, response);
      return;
    }
    HttpServletRequest httpRequest = (HttpServletRequest)request;
    HttpSession session = httpRequest.getSession();
    String requestURI = httpRequest.getRequestURI();
    
    SessionUser sessionUser = SessionManager.getCurrentUser(session);
    if (sessionUser == null)
    {
      chain.doFilter(request, response);
      return;
    }
    if (ignorePages.contains(requestURI))
    {
      chain.doFilter(request, response);
      return;
    }
    Object expireDate = session.getAttribute("SESSION_EXPIRE_TIME");
    if (expireDate == null)
    {
      Moment expireMoment = new Moment().add(session.getMaxInactiveInterval(), "seconds");
      session.setAttribute("SESSION_EXPIRE_TIME", expireMoment.toDate());
      chain.doFilter(request, response);
      return;
    }
    Date expiretAt = (Date)expireDate;
    Date now = new Date();
    if (expiretAt.before(now))
    {
      session.invalidate();
      chain.doFilter(request, response);
      return;
    }
    if (!ignoreUrls.contains(requestURI))
    {
      Moment expireMoment = new Moment().add(session.getMaxInactiveInterval(), "seconds");
      session.setAttribute("SESSION_EXPIRE_TIME", expireMoment.toDate());
    }
    chain.doFilter(request, response);
  }
  
  public void init(FilterConfig config)
    throws ServletException
  {}
  
  public void destroy() {}
}
