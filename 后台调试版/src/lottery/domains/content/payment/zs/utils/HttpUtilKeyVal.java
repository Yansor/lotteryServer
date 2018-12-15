package lottery.domains.content.payment.zs.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpUtilKeyVal
{
  private static Logger log = LoggerFactory.getLogger(HttpUtilKeyVal.class);
  
  public static HttpUtilKeyVal getInstance()
  {
    return new HttpUtilKeyVal();
  }
  
  public static String doPost(String url, Map<String, String> params)
  {
    StringBuffer sb = new StringBuffer();
    if (params != null)
    {
      boolean isFirst = false;
      for (Map.Entry<String, String> e : params.entrySet())
      {
        if (isFirst) {
          sb.append("&");
        }
        sb.append((String)e.getKey());
        sb.append("=");
        sb.append((String)e.getValue());
        if (!isFirst) {
          isFirst = true;
        }
      }
    }
    String reciveStr = null;
    PostMethod postMethod = null;
    try
    {
      HttpClient httpClient = new HttpClient();
      postMethod = new PostMethod(url);
      httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(80000);
      httpClient.getHttpConnectionManager().getParams().setSoTimeout(80000);
      postMethod.setRequestHeader("Connection", "close");
      postMethod.addRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
      postMethod.getParams().setContentCharset("utf-8");
      postMethod.getParams().setParameter("http.protocol.content-charset", "UTF-8");
      
      NameValuePair[] dataList = null;
      if (params != null)
      {
        dataList = new NameValuePair[params.keySet().size()];
        int i = 0;
        for (Map.Entry<String, String> e : params.entrySet())
        {
          dataList[i] = new NameValuePair((String)e.getKey(), (String)e.getValue());
          i++;
        }
        postMethod.setRequestBody(dataList);
      }
      else
      {
        postMethod.setRequestBody("");
      }
      httpClient.executeMethod(postMethod);
      
      InputStream resStream = postMethod.getResponseBodyAsStream();
      BufferedReader br = new BufferedReader(new InputStreamReader(resStream));
      String retStr = "";
      String tempbf;
      while ((tempbf = br.readLine()) != null)
      {
        retStr = retStr + tempbf;
      }
      return retStr;
    }
    catch (HttpException e)
    {
      e.printStackTrace();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    finally
    {
      if (postMethod != null) {
        postMethod.releaseConnection();
      }
    }
    return reciveStr;
  }
}
