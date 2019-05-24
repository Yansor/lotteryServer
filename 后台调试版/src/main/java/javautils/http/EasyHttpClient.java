package javautils.http;

import java.io.InputStream;
import java.net.SocketTimeoutException;
import org.apache.commons.httpclient.ConnectTimeoutException;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EasyHttpClient
{
  private static final Logger logger = LoggerFactory.getLogger(EasyHttpClient.class);
  private HttpClient httpClient = new HttpClient(new MultiThreadedHttpConnectionManager());
  private final int TIMEOUT = 10000;
  private final int SO_TIMEOUT = 10000;
  private int REPEAT_TIMES = 3;
  
  public EasyHttpClient()
  {
    setTimeOut(10000, 10000);
  }
  
  public void setTimeOut(int TIMEOUT, int SO_TIMEOUT)
  {
    this.httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(TIMEOUT);
    this.httpClient.getHttpConnectionManager().getParams().setSoTimeout(SO_TIMEOUT);
  }
  
  public void setRepeatTimes(int times)
  {
    this.REPEAT_TIMES = times;
  }
  
  public String get(String url)
  {
    GetMethod get = new GetMethod(url);
    for (int i = 0; i < this.REPEAT_TIMES; i++)
    {
      logger.debug("正在发送请求，当前第" + (i + 1) + "次...");
      try
      {
        int state = this.httpClient.executeMethod(get);
        logger.debug("请求状态" + state);
        if (state == 200)
        {
          String data = fromInputStream(get.getResponseBodyAsStream());
          logger.debug("成功获取到数据，长度为" + data.length());
          get.releaseConnection();
          return data;
        }
      }
      catch (ConnectTimeoutException e)
      {
        logger.error("请求超时...Connect Timeout");
      }
      catch (SocketTimeoutException e)
      {
        logger.error("请求超时...Socket Timeout");
      }
      catch (Exception e)
      {
        logger.error("请求出错...", e);
      }
      finally
      {
        get.releaseConnection();
      }
      get.releaseConnection();
    }
    return null;
  }
  
  public String post(String url, NameValuePair[] params)
  {
    PostMethod post = new PostMethod(url);
    if (params != null) {
      post.setRequestBody(params);
    }
    for (int i = 0; i < this.REPEAT_TIMES; i++)
    {
      logger.debug("正在发送请求，当前第" + (i + 1) + "次...");
      try
      {
        int state = this.httpClient.executeMethod(post);
        logger.debug("请求状态" + state);
        if (state == 200)
        {
          String data = fromInputStream(post.getResponseBodyAsStream());
          logger.debug("成功获取到数据，长度为" + data.length());
          post.releaseConnection();
          return data;
        }
      }
      catch (ConnectTimeoutException e)
      {
        logger.error("请求超时...Connect Timeout");
      }
      catch (SocketTimeoutException e)
      {
        logger.error("请求超时...Socket Timeout");
      }
      catch (Exception e)
      {
        logger.error("请求出错...", e);
      }
      finally
      {
        post.releaseConnection();
      }
      post.releaseConnection();
    }
    return null;
  }
  
  public static String fromInputStream(InputStream inputStream)
  {
    try
    {
      StringBuffer sb = new StringBuffer();
      byte[] bytes = new byte['Ѐ'];
      int len;
      while ((len = inputStream.read(bytes)) != -1)
      {
        sb.append(new String(bytes, 0, len));
      }
      inputStream.close();
      return sb.toString();
    }
    catch (Exception localException) {}
    return null;
  }
}
