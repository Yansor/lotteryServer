package admin.web.helper.filter;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import java.io.IOException;
import admin.web.helper.session.SessionUser;
import javax.servlet.http.HttpSession;
import java.util.Date;
import javautils.date.Moment;
import admin.web.helper.session.SessionManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.FilterChain;
import javax.servlet.ServletResponse;
import javax.servlet.ServletRequest;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.Filter;

public class SessionTimeoutFilter implements Filter
{
    private static final Set<String> ignorePages;
    private static final Set<String> ignoreUrls;
    
    static {
        ignorePages = new HashSet<String>();
        ignoreUrls = new HashSet<String>();
        SessionTimeoutFilter.ignorePages.add("/login");
        SessionTimeoutFilter.ignorePages.add("/logout");
        SessionTimeoutFilter.ignorePages.add("/access-denied");
        SessionTimeoutFilter.ignorePages.add("/page-not-found");
        SessionTimeoutFilter.ignorePages.add("/page-error");
        SessionTimeoutFilter.ignorePages.add("/page-not-login");
        SessionTimeoutFilter.ignoreUrls.add("/global");
        SessionTimeoutFilter.ignoreUrls.add("/high-prize-unprocess-count");
        SessionTimeoutFilter.ignoreUrls.add("/lottery-user-withdraw/list");
    }
    
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {
        if (!(request instanceof HttpServletRequest)) {
            chain.doFilter(request, response);
            return;
        }
        final HttpServletRequest httpRequest = (HttpServletRequest)request;
        final HttpSession session = httpRequest.getSession();
        final String requestURI = httpRequest.getRequestURI();
        final SessionUser sessionUser = SessionManager.getCurrentUser(session);
        if (sessionUser == null) {
            chain.doFilter(request, response);
            return;
        }
        if (SessionTimeoutFilter.ignorePages.contains(requestURI)) {
            chain.doFilter(request, response);
            return;
        }
        final Object expireDate = session.getAttribute("SESSION_EXPIRE_TIME");
        if (expireDate == null) {
            final Moment expireMoment = new Moment().add(session.getMaxInactiveInterval(), "seconds");
            session.setAttribute("SESSION_EXPIRE_TIME", (Object)expireMoment.toDate());
            chain.doFilter(request, response);
            return;
        }
        final Date expiretAt = (Date)expireDate;
        final Date now = new Date();
        if (expiretAt.before(now)) {
            session.invalidate();
            chain.doFilter(request, response);
            return;
        }
        if (!SessionTimeoutFilter.ignoreUrls.contains(requestURI)) {
            final Moment expireMoment2 = new Moment().add(session.getMaxInactiveInterval(), "seconds");
            session.setAttribute("SESSION_EXPIRE_TIME", (Object)expireMoment2.toDate());
        }
        chain.doFilter(request, response);
    }
    
    public void init(final FilterConfig config) throws ServletException {
    }
    
    public void destroy() {
    }
}
