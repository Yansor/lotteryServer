package lottery.domains.content.payment.utils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;

public class RequestUtils
{
    public static String inputStream2String(final InputStream in) throws IOException {
        final StringBuffer out = new StringBuffer();
        try {
            final byte[] b = new byte[4096];
            int n;
            while ((n = in.read(b)) != -1) {
                out.append(new String(b, 0, n));
            }
            return out.toString();
        }
        catch (IOException ex) {
            throw ex;
        }
    }
    
    public static String getReferer(final HttpServletRequest request) {
        return String.valueOf(getSchema(request)) + "://" + getServerName(request);
    }
    
    public static String getSchema(final HttpServletRequest request) {
        String schema = request.getHeader("X-Forwarded-Proto");
        if (!"http".equalsIgnoreCase(schema) && !"https".equalsIgnoreCase(schema)) {
            schema = request.getScheme();
        }
        return schema;
    }
    
    public static String getServerName(final HttpServletRequest request) {
        return request.getServerName();
    }
}
