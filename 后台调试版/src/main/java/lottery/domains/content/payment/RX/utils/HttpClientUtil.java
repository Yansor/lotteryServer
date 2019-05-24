package lottery.domains.content.payment.RX.utils;

import com.alibaba.fastjson.JSONObject;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpClientUtil
{
  public static String post(String url, Map<String, String> params)
  {
    CloseableHttpClient httpclient = null;
    try
    {
      HttpPost httpRequest = new HttpPost(url);
      
      List<NameValuePair> nameValuePairs = new ArrayList();
      for (String key : params.keySet()) {
        nameValuePairs.add(new BasicNameValuePair(key, (String)params.get(key)));
      }
      HttpEntity httpentity = null;
      httpentity = new UrlEncodedFormEntity(nameValuePairs, "UTF-8");
      
      RequestConfig config = RequestConfig.custom().setSocketTimeout(50000).setConnectTimeout(50000).build();
      httpRequest.setConfig(config);
      
      httpRequest.setEntity(httpentity);
      
      httpclient = HttpClients.createDefault();
      
      HttpResponse httpResponse = httpclient.execute(httpRequest);
      if (httpResponse.getStatusLine().getStatusCode() == 200) {
        return EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
      }
    }
    catch (Exception e)
    {
      return null;
    }
    finally
    {
      try
      {
        httpclient.close();
      }
      catch (IOException e)
      {
        e.printStackTrace();
      }
    }
    try
    {
      httpclient.close();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    return null;
  }
  
  public static String httpsPostJSON(String url, JSONObject json)
    throws Exception
  {
    HttpPost httpRequest = new HttpPost(url);
    httpRequest.addHeader("X-tenantId", "single");
    StringEntity se = new StringEntity(json.toString(), "UTF-8");
    se.setContentType("application/json");
    httpRequest.setEntity(se);
    
    CloseableHttpClient httpclient = HttpClients.createDefault();
    try
    {
      HttpResponse httpResponse = httpclient.execute(httpRequest);
      if (httpResponse.getStatusLine().getStatusCode() == 200) {
        return EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
      }
    }
    catch (Exception e)
    {
      return null;
    }
    finally
    {
      try
      {
        httpclient.close();
      }
      catch (IOException e)
      {
        e.printStackTrace();
      }
    }
    try
    {
      HttpResponse httpResponse;
      httpclient.close();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    return null;
  }
  
  public static String postJSON(String url, JSONObject json)
    throws Exception
  {
    HttpPost httpRequest = new HttpPost(url);
    httpRequest.addHeader("X-tenantId", "single");
    StringEntity se = new StringEntity(json.toString(), "UTF-8");
    se.setContentType("application/json");
    httpRequest.setEntity(se);
    
    CloseableHttpClient httpclient = HttpClients.createDefault();
    try
    {
      HttpResponse httpResponse = httpclient.execute(httpRequest);
      if (httpResponse.getStatusLine().getStatusCode() == 200) {
        return EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
      return null;
    }
    finally
    {
      try
      {
        httpclient.close();
      }
      catch (IOException e)
      {
        e.printStackTrace();
      }
    }
    try
    {
      HttpResponse httpResponse;
      httpclient.close();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    return null;
  }
  
  public static String get(String url, Map<String, String> params)
    throws Exception
  {
    List<NameValuePair> nameValuePairs = new ArrayList();
    for (String key : params.keySet()) {
      nameValuePairs.add(new BasicNameValuePair(key, (String)params.get(key)));
    }
    String param = URLEncodedUtils.format(nameValuePairs, "UTF-8");
    
    HttpGet httpRequest = new HttpGet(url + "?" + param);
    
    System.out.println(url + "?" + param);
    
    CloseableHttpClient httpclient = HttpClients.createDefault();
    try
    {
      HttpResponse httpResponse = httpclient.execute(httpRequest);
      if (httpResponse.getStatusLine().getStatusCode() == 200) {
        return EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
      }
    }
    catch (Exception e)
    {
      return null;
    }
    finally
    {
      try
      {
        httpclient.close();
      }
      catch (IOException e)
      {
        e.printStackTrace();
      }
    }
    try
    {
      HttpResponse httpResponse;
      httpclient.close();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    return null;
  }
  
  public static String omsPostJSON(String url, JSONObject json)
    throws Exception
  {
    HttpPost httpRequest = new HttpPost(url);
    
    StringEntity se = new StringEntity(json.toString(), "UTF-8");
    se.setContentType("application/json");
    httpRequest.setEntity(se);
    
    httpRequest.setHeader("Content-Type", "application/json");
    httpRequest.setHeader("Accept", "application/json");
    httpRequest.setHeader("X-tenantId", "single");
    
    CloseableHttpClient httpclient = HttpClients.createDefault();
    try
    {
      HttpResponse httpResponse = httpclient.execute(httpRequest);
      if (httpResponse.getStatusLine().getStatusCode() == 200) {
        return EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
      }
    }
    catch (Exception e)
    {
      return null;
    }
    finally
    {
      try
      {
        httpclient.close();
      }
      catch (IOException e)
      {
        e.printStackTrace();
      }
    }
    try
    {
      HttpResponse httpResponse;
      httpclient.close();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    return null;
  }
  
  public static String postForm(String url, JSONObject json)
    throws Exception
  {
    HttpPost httpRequest = new HttpPost(url);
    
    StringEntity se = new StringEntity(json.toString(), "UTF-8");
    se.setContentType("application/x-www-form-urlencoded");
    httpRequest.setEntity(se);
    
    CloseableHttpClient httpclient = HttpClients.createDefault();
    try
    {
      HttpResponse httpResponse = httpclient.execute(httpRequest);
      if (httpResponse.getStatusLine().getStatusCode() == 200) {
        return EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
      }
    }
    catch (Exception e)
    {
      return null;
    }
    finally
    {
      try
      {
        httpclient.close();
      }
      catch (IOException e)
      {
        e.printStackTrace();
      }
    }
    try
    {
      HttpResponse httpResponse;
      httpclient.close();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    return null;
  }
  
  /* Error */
  public static String sendHttpPostRequest(String reqURL, String data)
  {
    return null;
    // Byte code:
    //   0: invokestatic 100	org/apache/http/impl/client/HttpClients:createDefault	()Lorg/apache/http/impl/client/CloseableHttpClient;
    //   3: astore_2
    //   4: ldc -6
    //   6: astore_3
    //   7: new 18	org/apache/http/client/methods/HttpPost
    //   10: dup
    //   11: aload_0
    //   12: invokespecial 20	org/apache/http/client/methods/HttpPost:<init>	(Ljava/lang/String;)V
    //   15: astore 4
    //   17: new 182	org/apache/http/entity/StringEntity
    //   20: dup
    //   21: aload_1
    //   22: ldc 67
    //   24: invokespecial 189	org/apache/http/entity/StringEntity:<init>	(Ljava/lang/String;Ljava/lang/String;)V
    //   27: astore 5
    //   29: aload 5
    //   31: ldc -10
    //   33: invokevirtual 192	org/apache/http/entity/StringEntity:setContentType	(Ljava/lang/String;)V
    //   36: aload 4
    //   38: aload 5
    //   40: invokevirtual 96	org/apache/http/client/methods/HttpPost:setEntity	(Lorg/apache/http/HttpEntity;)V
    //   43: invokestatic 72	org/apache/http/client/config/RequestConfig:custom	()Lorg/apache/http/client/config/RequestConfig$Builder;
    //   46: sipush 10000
    //   49: invokevirtual 79	org/apache/http/client/config/RequestConfig$Builder:setSocketTimeout	(I)Lorg/apache/http/client/config/RequestConfig$Builder;
    //   52: sipush 10000
    //   55: invokevirtual 85	org/apache/http/client/config/RequestConfig$Builder:setConnectTimeout	(I)Lorg/apache/http/client/config/RequestConfig$Builder;
    //   58: invokevirtual 88	org/apache/http/client/config/RequestConfig$Builder:build	()Lorg/apache/http/client/config/RequestConfig;
    //   61: astore 6
    //   63: aload 4
    //   65: aload 6
    //   67: invokevirtual 92	org/apache/http/client/methods/HttpPost:setConfig	(Lorg/apache/http/client/config/RequestConfig;)V
    //   70: aload_2
    //   71: aload 4
    //   73: invokevirtual 106	org/apache/http/impl/client/CloseableHttpClient:execute	(Lorg/apache/http/client/methods/HttpUriRequest;)Lorg/apache/http/client/methods/CloseableHttpResponse;
    //   76: astore 7
    //   78: aload 7
    //   80: invokeinterface 112 1 0
    //   85: invokeinterface 118 1 0
    //   90: istore 8
    //   92: iload 8
    //   94: sipush 200
    //   97: if_icmpne +73 -> 170
    //   100: aload 7
    //   102: invokeinterface 124 1 0
    //   107: astore 9
    //   109: aload 9
    //   111: ifnull +59 -> 170
    //   114: aload 9
    //   116: invokestatic 252	org/apache/http/util/EntityUtils:toString	(Lorg/apache/http/HttpEntity;)Ljava/lang/String;
    //   119: astore_3
    //   120: aload 9
    //   122: invokestatic 255	org/apache/http/util/EntityUtils:consume	(Lorg/apache/http/HttpEntity;)V
    //   125: goto +45 -> 170
    //   128: astore 4
    //   130: aload 4
    //   132: invokevirtual 200	java/lang/Exception:printStackTrace	()V
    //   135: aload_2
    //   136: invokevirtual 134	org/apache/http/impl/client/CloseableHttpClient:close	()V
    //   139: goto +10 -> 149
    //   142: astore 11
    //   144: aload 11
    //   146: invokevirtual 137	java/io/IOException:printStackTrace	()V
    //   149: aconst_null
    //   150: areturn
    //   151: astore 10
    //   153: aload_2
    //   154: invokevirtual 134	org/apache/http/impl/client/CloseableHttpClient:close	()V
    //   157: goto +10 -> 167
    //   160: astore 11
    //   162: aload 11
    //   164: invokevirtual 137	java/io/IOException:printStackTrace	()V
    //   167: aload 10
    //   169: athrow
    //   170: aload_2
    //   171: invokevirtual 134	org/apache/http/impl/client/CloseableHttpClient:close	()V
    //   174: goto +10 -> 184
    //   177: astore 11
    //   179: aload 11
    //   181: invokevirtual 137	java/io/IOException:printStackTrace	()V
    //   184: aload_3
    //   185: areturn
    // Line number table:
    //   Java source line #318	-> byte code offset #0
    //   Java source line #319	-> byte code offset #4
    //   Java source line #321	-> byte code offset #7
    //   Java source line #322	-> byte code offset #17
    //   Java source line #323	-> byte code offset #29
    //   Java source line #324	-> byte code offset #36
    //   Java source line #327	-> byte code offset #43
    //   Java source line #328	-> byte code offset #63
    //   Java source line #330	-> byte code offset #70
    //   Java source line #331	-> byte code offset #78
    //   Java source line #332	-> byte code offset #92
    //   Java source line #333	-> byte code offset #100
    //   Java source line #334	-> byte code offset #109
    //   Java source line #335	-> byte code offset #114
    //   Java source line #336	-> byte code offset #120
    //   Java source line #340	-> byte code offset #125
    //   Java source line #341	-> byte code offset #130
    //   Java source line #345	-> byte code offset #135
    //   Java source line #346	-> byte code offset #139
    //   Java source line #347	-> byte code offset #144
    //   Java source line #342	-> byte code offset #149
    //   Java source line #343	-> byte code offset #151
    //   Java source line #345	-> byte code offset #153
    //   Java source line #346	-> byte code offset #157
    //   Java source line #347	-> byte code offset #162
    //   Java source line #349	-> byte code offset #167
    //   Java source line #345	-> byte code offset #170
    //   Java source line #346	-> byte code offset #174
    //   Java source line #347	-> byte code offset #179
    //   Java source line #350	-> byte code offset #184
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	186	0	reqURL	String
    //   0	186	1	data	String
    //   3	168	2	httpclient	CloseableHttpClient
    //   6	179	3	respStr	String
    //   15	57	4	httppost	HttpPost
    //   128	3	4	e	Exception
    //   27	12	5	strEntity	StringEntity
    //   61	5	6	config	RequestConfig
    //   76	25	7	response	HttpResponse
    //   90	3	8	status	int
    //   107	14	9	resEntity	HttpEntity
    //   151	17	10	localObject	Object
    //   142	3	11	e	IOException
    //   160	3	11	e	IOException
    //   177	3	11	e	IOException
    // Exception table:
    //   from	to	target	type
    //   7	125	128	java/lang/Exception
    //   135	139	142	java/io/IOException
    //   7	135	151	finally
    //   153	157	160	java/io/IOException
    //   170	174	177	java/io/IOException
  }
}
