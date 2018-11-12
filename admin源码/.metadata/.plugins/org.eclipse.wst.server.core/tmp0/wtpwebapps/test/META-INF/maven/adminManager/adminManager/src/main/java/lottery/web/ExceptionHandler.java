package lottery.web;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.method.HandlerMethod;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

@Component
public class ExceptionHandler extends ExceptionHandlerExceptionResolver
{
    private static final Logger log;
    
    static {
        log = LoggerFactory.getLogger((Class)ExceptionHandler.class);
    }
    
    protected ModelAndView doResolveHandlerMethodException(final HttpServletRequest request, final HttpServletResponse response, final HandlerMethod handlerMethod, final Exception exception) {
        final String exceptionName = exception.getClass().getName();
        if (exceptionName != null && !"org.apache.catalina.connector.ClientAbortException".equals(exceptionName)) {
            ExceptionHandler.log.error("发生异常", (Throwable)exception);
        }
        return super.doResolveHandlerMethodException(request, response, handlerMethod, exception);
    }
}
