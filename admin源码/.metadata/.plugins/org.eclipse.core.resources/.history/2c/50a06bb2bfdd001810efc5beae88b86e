package admin.web.helper.filter;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import java.io.IOException;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.FilterChain;
import javax.servlet.ServletResponse;
import javax.servlet.ServletRequest;
import javax.servlet.Filter;

public class DisableUrlSessionFilter implements Filter
{
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {
        if (!(request instanceof HttpServletRequest)) {
            chain.doFilter(request, response);
            return;
        }
        final HttpServletRequest httpRequest = (HttpServletRequest)request;
        final HttpServletResponse httpResponse = (HttpServletResponse)response;
        if (httpRequest.isRequestedSessionIdFromURL()) {
            final HttpSession session = httpRequest.getSession();
            if (session != null) {
                session.invalidate();
            }
        }
        final HttpServletResponseWrapper wrappedResponse = new HttpServletResponseWrapper(httpResponse) {
            public String encodeRedirectUrl(final String url) {
                return url;
            }
            
            public String encodeRedirectURL(final String url) {
                return url;
            }
            
            public String encodeUrl(final String url) {
                return url;
            }
            
            public String encodeURL(final String url) {
                return url;
            }
        };
        chain.doFilter(request, (ServletResponse)wrappedResponse);
    }
    
    public void init(final FilterConfig config) throws ServletException {
    }
    
    public void destroy() {
    }
}
