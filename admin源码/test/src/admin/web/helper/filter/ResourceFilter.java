package admin.web.helper.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResourceFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
//        if(req instanceof HttpServletRequest){
//            StringBuffer url = ((HttpServletRequest) req).getRequestURL();
//            System.out.println("url : " + url );
//            if(url.toString().contains(".")){
//                String suffix = url.toString().substring(url.toString().lastIndexOf(".") ,url.toString().length() );
//                System.out.println( "suffix :" + suffix);
//
//                if(resp instanceof HttpServletResponse){
//                    req.getRequestDispatcher("/WEB-INF/staticmedia/favicon.ico").forward(req ,resp);
//                    return;
////                    ((HttpServletResponse) resp).sendRedirect("/WEB-INF/staticmedia/favicon.ico");
//                }
//            }
//
//
//
//        }
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
