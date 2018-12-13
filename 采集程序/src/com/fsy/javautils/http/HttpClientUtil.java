/*     */ package com.fsy.javautils.http;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.URLDecoder;
/*     */ import java.security.KeyManagementException;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ import org.apache.http.StatusLine;
/*     */ import org.apache.http.client.config.RequestConfig;
/*     */ import org.apache.http.client.entity.UrlEncodedFormEntity;
/*     */ import org.apache.http.client.methods.CloseableHttpResponse;
/*     */ import org.apache.http.client.methods.HttpGet;
/*     */ import org.apache.http.client.methods.HttpPost;
/*     */ import org.apache.http.conn.ConnectionKeepAliveStrategy;
/*     */ import org.apache.http.entity.InputStreamEntity;
/*     */ import org.apache.http.impl.client.CloseableHttpClient;
/*     */ import org.apache.http.impl.client.HttpClientBuilder;
/*     */ import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
/*     */ import org.apache.http.message.BasicNameValuePair;
/*     */ import org.apache.http.util.EntityUtils;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HttpClientUtil
/*     */ {
/*  35 */   private static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);
/*     */   
/*     */   private static CloseableHttpClient getHttpClient(int timeout) {
/*  38 */     HttpClientBuilder clientBuilder = HttpClientBuilder.create();
/*     */     
/*     */ 
/*  41 */     ConnectionKeepAliveStrategy keepAliveStrategy = HttpConfigBuilder.getInstance().buildKeepAliveStrategy(60000L);
/*  42 */     clientBuilder.setKeepAliveStrategy(keepAliveStrategy);
/*     */     
/*  44 */     RequestConfig requestConfig = HttpConfigBuilder.getInstance().buildRequestConfig(timeout);
/*  45 */     clientBuilder.setDefaultRequestConfig(requestConfig);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  51 */     CloseableHttpClient httpClient = clientBuilder.build();
/*     */     
/*  53 */     return httpClient;
/*     */   }
/*     */   
/*     */   private static CloseableHttpClient getHttpsClient(int timeout, String sslVersion) throws NoSuchAlgorithmException, KeyManagementException {
/*  57 */     HttpClientBuilder clientBuilder = HttpClientBuilder.create();
/*     */     
/*     */ 
/*  60 */     ConnectionKeepAliveStrategy keepAliveStrategy = HttpConfigBuilder.getInstance().buildKeepAliveStrategy(60000L);
/*  61 */     clientBuilder.setKeepAliveStrategy(keepAliveStrategy);
/*     */     
/*  63 */     RequestConfig requestConfig = HttpConfigBuilder.getInstance().buildRequestConfig(timeout);
/*  64 */     clientBuilder.setDefaultRequestConfig(requestConfig);
/*     */     
/*     */     BasicHttpClientConnectionManager connMgr;
///*     */     BasicHttpClientConnectionManager connMgr;
/*  68 */     if (sslVersion == null) {
/*  69 */       connMgr = HttpConfigBuilder.getInstance().buildBasicHttpsClientConnectionManager();
/*     */     }
/*     */     else {
/*  72 */       connMgr = HttpConfigBuilder.getInstance().buildBasicHttpsClientConnectionManager(sslVersion);
/*     */     }
/*  74 */     clientBuilder.setConnectionManager(connMgr);
/*     */     
/*  76 */     CloseableHttpClient httpClient = clientBuilder.build();
/*     */     
/*  78 */     return httpClient;
/*     */   }
/*     */   
/*     */   public static String get(String url, Map<String, String> httpHeader, int timeout) throws Exception { CloseableHttpClient httpClient;
///*     */     CloseableHttpClient httpClient;
/*  83 */     if (url.startsWith("https://")) {
/*  84 */       httpClient = getHttpsClient(timeout, null);
/*     */     }
/*     */     else {
/*  87 */       httpClient = getHttpClient(timeout);
/*     */     }
/*     */     
/*  90 */     return get(httpClient, url, httpHeader);
/*     */   }
/*     */   
/*     */   public static String get(CloseableHttpClient httpClient, String url, Map<String, String> httpHeader) throws IOException {
/*  94 */     String strResult = null;
/*     */     try
/*     */     {
/*  97 */       HttpGet request = new HttpGet(url);
/*     */       
/*     */       Iterator<Entry<String, String>> ies;
/* 100 */       if ((httpHeader != null) && (httpHeader.size() > 0)) {
/* 101 */         for (ies = httpHeader.entrySet().iterator(); ies.hasNext();) {
/* 102 */           Entry<String, String> entry = (Entry)ies.next();
/* 103 */           String key = (String)entry.getKey();
/* 104 */           String value = (String)entry.getValue();
/* 105 */           request.addHeader(key, value);
/*     */         }
/*     */       }
/* 108 */       CloseableHttpResponse response = httpClient.execute(request);
/*     */       
/*     */ 
/* 111 */       if (response.getStatusLine().getStatusCode() == 200) {
/*     */         try
/*     */         {
/* 114 */           strResult = EntityUtils.toString(response.getEntity(), "UTF-8");
/* 115 */           url = URLDecoder.decode(url, "UTF-8");
/*     */         } catch (Exception e) {
/* 117 */           logger.error("get请求提交失败:" + url);
/*     */         } finally {
/* 119 */           response.close();
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (IOException e) {
/* 124 */       logger.error("get请求提交失败:" + url, e);
/*     */     } finally {
/* 126 */       httpClient.close();
/*     */     }
/* 128 */     return strResult;
/*     */   }
/*     */   
/*     */   public static String post(String url, Map<String, String> params, Map<String, String> headers, int timeout) throws Exception { CloseableHttpClient httpClient;
///*     */     CloseableHttpClient httpClient;
/* 133 */     if (url.startsWith("https://")) {
/* 134 */       httpClient = getHttpsClient(timeout, null);
/*     */     }
/*     */     else {
/* 137 */       httpClient = getHttpClient(timeout);
/*     */     }
/*     */     
/* 140 */     return post(httpClient, url, params, headers);
/*     */   }
/*     */   
/*     */   public static String postAsStream(String url, Map<String, String> params, Map<String, String> headers, int timeout) throws Exception { CloseableHttpClient httpClient;
///*     */     CloseableHttpClient httpClient;
/* 145 */     if (url.startsWith("https://")) {
/* 146 */       httpClient = getHttpsClient(timeout, null);
/*     */     }
/*     */     else {
/* 149 */       httpClient = getHttpClient(timeout);
/*     */     }
/*     */     
/* 152 */     return postAsStream(httpClient, url, params, headers);
/*     */   }
/*     */   
/*     */   public static String postSSL(String url, Map<String, String> params, Map<String, String> headers, int timeout, String sslVersion) throws Exception { CloseableHttpClient httpClient;
///*     */     CloseableHttpClient httpClient;
/* 157 */     if (url.startsWith("https://")) {
/* 158 */       httpClient = getHttpsClient(timeout, sslVersion);
/*     */     }
/*     */     else {
/* 161 */       httpClient = getHttpClient(timeout);
/*     */     }
/*     */     
/* 164 */     return post(httpClient, url, params, headers);
/*     */   }
/*     */   
/*     */   public static String post(CloseableHttpClient httpClient, String url, Map<String, String> params, Map<String, String> headers) throws IOException {
/* 168 */     String result = null;
/*     */     try {
/* 170 */       HttpPost request = new HttpPost(url);
/*     */       
/*     */       Iterator<Entry<String, String>> ies;
/* 173 */       if ((headers != null) && (headers.size() > 0)) {
/* 174 */         for (ies = headers.entrySet().iterator(); ies.hasNext();) {
/* 175 */           Entry<String, String> entry = (Entry)ies.next();
/*     */           
/* 177 */           String key = (String)entry.getKey();
/* 178 */           String value = (String)entry.getValue();
/*     */           
/* 180 */           request.addHeader(key, value);
/*     */         }
/*     */       }
/*     */       
/* 184 */       if ((params != null) && (params.size() > 0)) {
/* 185 */         List list = new ArrayList();
/* 186 */         Iterator iterator = params.entrySet().iterator();
/* 187 */         while (iterator.hasNext()) {
/* 188 */           Entry elem = (Entry)iterator.next();
/* 189 */           list.add(new BasicNameValuePair((String)elem.getKey(), (String)elem.getValue()));
/*     */         }
/* 191 */         if (list.size() > 0) {
/* 192 */           UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, "utf-8");
/* 193 */           request.setEntity(entity);
/*     */         }
/*     */       }
/*     */       
/* 197 */       CloseableHttpResponse response = httpClient.execute(request);
/* 198 */       url = URLDecoder.decode(url, "UTF-8");
/*     */       
/*     */ 
/* 201 */       if (response.getStatusLine().getStatusCode() == 200) {
/*     */         try
/*     */         {
/* 204 */           result = EntityUtils.toString(response.getEntity(), "UTF-8");
/*     */         } catch (Exception e) {
/* 206 */           logger.error("post请求提交失败:" + url, e);
/*     */         } finally {
/* 208 */           response.close();
/*     */         }
/*     */       } else {
/* 211 */         result = response.getStatusLine().getStatusCode() + "-" + response.getStatusLine().getReasonPhrase();
/*     */       }
/*     */     } catch (Exception e) {
/* 214 */       logger.error("post请求提交失败:" + url, e);
/*     */     } finally {
/* 216 */       httpClient.close();
/*     */     }
/* 218 */     return result;
/*     */   }
/*     */   
/*     */   public static String postAsStream(CloseableHttpClient httpClient, String url, Map<String, String> params, Map<String, String> headers) throws IOException {
/* 222 */     String result = null;
/*     */     try {
/* 224 */       HttpPost request = new HttpPost(url);
/*     */       
/*     */       Iterator<Entry<String, String>> ies;
/* 227 */       if ((headers != null) && (headers.size() > 0)) {
/* 228 */         for (ies = headers.entrySet().iterator(); ies.hasNext();) {
/* 229 */           Entry<String, String> entry = (Entry)ies.next();
/*     */           
/* 231 */           String key = (String)entry.getKey();
/* 232 */           String value = (String)entry.getValue();
/*     */           
/* 234 */           request.addHeader(key, value);
/*     */         }
/*     */       }
/*     */       
/* 238 */       if ((params != null) && (params.size() > 0)) {
/* 239 */         String paramUrl = ToUrlParamUtils.toUrlParam(params);
/* 240 */         InputStream is = new ByteArrayInputStream(paramUrl.getBytes());
/* 241 */         InputStreamEntity inputEntry = new InputStreamEntity(is);
/* 242 */         request.setEntity(inputEntry);
/*     */       }
/*     */       
/* 245 */       CloseableHttpResponse response = httpClient.execute(request);
/* 246 */       url = URLDecoder.decode(url, "UTF-8");
/*     */       
/*     */ 
/* 249 */       if (response.getStatusLine().getStatusCode() == 200) {
/*     */         try
/*     */         {
/* 252 */           result = EntityUtils.toString(response.getEntity(), "UTF-8");
/*     */         } catch (Exception e) {
/* 254 */           logger.error("post请求提交失败:" + url, e);
/*     */         } finally {
/* 256 */           response.close();
/*     */         }
/*     */       } else {
/* 259 */         result = response.getStatusLine().getStatusCode() + "-" + response.getStatusLine().getReasonPhrase();
/*     */       }
/*     */     } catch (Exception e) {
/* 262 */       logger.error("post请求提交失败:" + url, e);
/*     */     } finally {
/* 264 */       httpClient.close();
/*     */     }
/* 266 */     return result;
/*     */   }
/*     */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryCapture/lotteryCaptureRepair.jar!/javautils/http/HttpClientUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */