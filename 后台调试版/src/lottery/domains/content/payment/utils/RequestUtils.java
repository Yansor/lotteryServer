package lottery.domains.content.payment.utils;

import java.io.IOException;
import java.io.InputStream;
import javax.servlet.http.HttpServletRequest;

public class RequestUtils
{
  public static String inputStream2String(InputStream in)
    throws IOException
  {
    StringBuffer out = new StringBuffer();
    try
    {
      byte[] b = new byte['က'];
      int n;
      while ((n = in.read(b)) != -1)
      {
        out.append(new String(b, 0, n));
      }
      return out.toString();
    }
    catch (IOException ex)
    {
      throw ex;
    }
  }
  
  public static String getReferer(HttpServletRequest request)
  {
    return getSchema(request) + "://" + getServerName(request);
  }
  
  public static String getSchema(HttpServletRequest request)
  {
    String schema = request.getHeader("X-Forwarded-Proto");
    if ((!"http".equalsIgnoreCase(schema)) && (!"https".equalsIgnoreCase(schema))) {
      schema = request.getScheme();
    }
    return schema;
  }
  
  public static String getServerName(HttpServletRequest request)
  {
    return request.getServerName();
  }
}
