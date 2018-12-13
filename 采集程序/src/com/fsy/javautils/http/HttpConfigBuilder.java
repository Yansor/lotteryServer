/*     */ package com.fsy.javautils.http;
/*     */ 
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.security.KeyManagementException;
/*     */ import java.security.KeyStore;
/*     */ import java.security.KeyStoreException;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.SecureRandom;
/*     */ import java.security.UnrecoverableKeyException;
/*     */ import java.security.cert.CertificateException;
/*     */ import java.security.cert.X509Certificate;
/*     */ import javax.net.ssl.HostnameVerifier;
/*     */ import javax.net.ssl.KeyManagerFactory;
/*     */ import javax.net.ssl.SSLContext;
/*     */ import javax.net.ssl.SSLSession;
/*     */ import javax.net.ssl.TrustManager;
/*     */ import javax.net.ssl.X509TrustManager;
/*     */ import org.apache.http.client.config.RequestConfig.Builder;
/*     */ import org.apache.http.config.Registry;
/*     */ import org.apache.http.config.RegistryBuilder;
/*     */ import org.apache.http.conn.socket.ConnectionSocketFactory;
/*     */ import org.apache.http.conn.socket.PlainConnectionSocketFactory;
/*     */ import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
/*     */ import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
/*     */ import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
/*     */ 
/*     */ public class HttpConfigBuilder
/*     */ {
/*  32 */   private static final HttpConfigBuilder INSTANCE = new HttpConfigBuilder();
/*     */   
/*     */ 
/*     */   public static HttpConfigBuilder getInstance()
/*     */   {
/*  37 */     return INSTANCE;
/*     */   }
/*     */   
/*     */   public org.apache.http.conn.ConnectionKeepAliveStrategy buildKeepAliveStrategy(final long duration) {
/*  41 */     return new org.apache.http.conn.ConnectionKeepAliveStrategy()
/*     */     {
/*     */       public long getKeepAliveDuration(org.apache.http.HttpResponse response, org.apache.http.protocol.HttpContext context) {
/*  44 */         return duration;
/*     */       }
/*     */     };
/*     */   }
/*     */   
/*     */   public org.apache.http.client.HttpRequestRetryHandler buildRetryHandler(int retryCount) {
/*  50 */     return new org.apache.http.impl.client.DefaultHttpRequestRetryHandler(retryCount, false);
/*     */   }
/*     */   
/*     */   public org.apache.http.client.config.RequestConfig buildRequestConfig(int timeout) {
/*  54 */     return org.apache.http.client.config.RequestConfig.custom().setConnectionRequestTimeout(timeout).setConnectTimeout(timeout).setSocketTimeout(timeout).build();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public BasicHttpClientConnectionManager buildBasicHttpClientConnectionManager()
/*     */   {
/*  61 */     BasicHttpClientConnectionManager connectionManager = new BasicHttpClientConnectionManager();
/*  62 */     return connectionManager;
/*     */   }
/*     */   
/*     */ 
/*     */   public BasicHttpClientConnectionManager buildBasicHttpsClientConnectionManager()
/*     */   {
/*     */     try
/*     */     {
/*  70 */       TrustManager[] trustAllCerts = { new X509TrustManager()
/*     */       {
/*     */         public X509Certificate[] getAcceptedIssuers() {
/*  73 */           return null;
/*     */         }
/*     */         
/*     */ 
/*     */ 
/*     */         public void checkClientTrusted(X509Certificate[] certs, String authType) {}
/*     */         
/*     */ 
/*     */         public void checkServerTrusted(X509Certificate[] certs, String authType) {}
/*  82 */       } };
/*  83 */       SSLContext sslContext = SSLContext.getInstance("TLS");
/*  84 */       sslContext.init(null, trustAllCerts, new SecureRandom());
/*     */       
/*  86 */       HostnameVerifier allHostsValid = new HostnameVerifier() {
/*     */         public boolean verify(String hostname, SSLSession session) {
/*  88 */           return true;
/*     */         }
/*  90 */       };
/*  91 */       SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslContext, allHostsValid);
/*     */       
/*     */ 
/*     */ 
/*  95 */       Registry socketFactoryRegistry = RegistryBuilder.create().register("http", PlainConnectionSocketFactory.getSocketFactory()).register("https", sslSocketFactory).build();
/*  96 */       return new BasicHttpClientConnectionManager(socketFactoryRegistry);
/*     */     }
/*     */     catch (KeyManagementException e) {
/*  99 */       e.printStackTrace();
/* 100 */       System.out.println("初始化https连接出错");
/*     */     } catch (NoSuchAlgorithmException e) {
/* 102 */       e.printStackTrace();
/* 103 */       System.out.println("初始化https连接出错");
/*     */     }
/*     */     
/* 106 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */   public BasicHttpClientConnectionManager buildBasicHttpsClientConnectionManager(String sslVersion)
/*     */   {
/*     */     try
/*     */     {
/* 114 */       TrustManager[] trustAllCerts = { new X509TrustManager()
/*     */       {
/*     */         public X509Certificate[] getAcceptedIssuers() {
/* 117 */           return null;
/*     */         }
/*     */         
/*     */ 
/*     */ 
/*     */         public void checkClientTrusted(X509Certificate[] certs, String authType) {}
/*     */         
/*     */ 
/*     */         public void checkServerTrusted(X509Certificate[] certs, String authType) {}
/* 126 */       } };
/* 127 */       SSLContext sslContext = SSLContext.getInstance("TLS");
/* 128 */       sslContext.init(null, trustAllCerts, new SecureRandom());
/*     */       
/* 130 */       HostnameVerifier allHostsValid = new HostnameVerifier() {
/*     */         public boolean verify(String hostname, SSLSession session) {
/* 132 */           return true;
/*     */         }
/* 134 */       };
/* 135 */       SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslContext, new String[] { sslVersion }, null, allHostsValid);
/*     */       
/*     */ 
/*     */ 
/* 139 */       Registry socketFactoryRegistry = RegistryBuilder.create().register("http", PlainConnectionSocketFactory.getSocketFactory()).register("https", sslSocketFactory).build();
/* 140 */       return new BasicHttpClientConnectionManager(socketFactoryRegistry);
/*     */     }
/*     */     catch (KeyManagementException e) {
/* 143 */       e.printStackTrace();
/* 144 */       System.out.println("初始化https连接出错");
/*     */     } catch (NoSuchAlgorithmException e) {
/* 146 */       e.printStackTrace();
/* 147 */       System.out.println("初始化https连接出错");
/*     */     }
/*     */     
/* 150 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */   public PoolingHttpClientConnectionManager buildPoolingHttpsClientConnectionManager(int maxTotal, int defaultMaxPerRoute)
/*     */   {
/*     */     try
/*     */     {
/* 158 */       TrustManager[] trustAllCerts = { new X509TrustManager()
/*     */       {
/*     */         public X509Certificate[] getAcceptedIssuers() {
/* 161 */           return null;
/*     */         }
/*     */         
/*     */ 
/*     */ 
/*     */         public void checkClientTrusted(X509Certificate[] certs, String authType) {}
/*     */         
/*     */ 
/*     */         public void checkServerTrusted(X509Certificate[] certs, String authType) {}
/* 170 */       } };
/* 171 */       SSLContext sslContext = SSLContext.getInstance("TLS");
/* 172 */       sslContext.init(null, trustAllCerts, new SecureRandom());
/*     */       
/* 174 */       HostnameVerifier allHostsValid = new HostnameVerifier() {
/*     */         public boolean verify(String hostname, SSLSession session) {
/* 176 */           return true;
/*     */         }
/* 178 */       };
/* 179 */       SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslContext, allHostsValid);
/*     */       
/*     */ 
/*     */ 
/* 183 */       Registry socketFactoryRegistry = RegistryBuilder.create().register("http", PlainConnectionSocketFactory.getSocketFactory()).register("https", sslSocketFactory).build();
/* 184 */       PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
/* 185 */       connectionManager.setMaxTotal(maxTotal);
/* 186 */       connectionManager.setDefaultMaxPerRoute(defaultMaxPerRoute);
/* 187 */       return connectionManager;
/*     */     } catch (KeyManagementException e) {
/* 189 */       e.printStackTrace();
/* 190 */       System.out.println("初始化https连接出错");
/*     */     } catch (NoSuchAlgorithmException e) {
/* 192 */       e.printStackTrace();
/* 193 */       System.out.println("初始化https连接出错");
/*     */     }
/*     */     
/* 196 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public PoolingHttpClientConnectionManager buildPoolingHttpClientConnectionManager(int maxTotal, int defaultMaxPerRoute)
/*     */   {
/* 203 */     PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
/* 204 */     connectionManager.setMaxTotal(maxTotal);
/* 205 */     connectionManager.setDefaultMaxPerRoute(defaultMaxPerRoute);
/* 206 */     return connectionManager;
/*     */   }
/*     */   
/*     */ 
/*     */   public PoolingHttpClientConnectionManager buildP12PoolingHttpClientConnectionManager(int maxTotal, int defaultMaxPerRoute, java.io.File p12File, String pwd)
/*     */   {
/*     */     try
/*     */     {
/* 214 */       KeyStore ks = KeyStore.getInstance("PKCS12");
/* 215 */       FileInputStream fis = new FileInputStream(p12File);
/* 216 */       ks.load(fis, pwd.toCharArray());
/* 217 */       KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
/* 218 */       kmf.init(ks, pwd.toCharArray());
/* 219 */       javax.net.ssl.KeyManager[] kms = kmf.getKeyManagers();
/*     */       
/*     */ 
/* 222 */       TrustManager[] trustAllCerts = { new X509TrustManager()
/*     */       {
/*     */         public X509Certificate[] getAcceptedIssuers() {
/* 225 */           return null;
/*     */         }
/*     */         
/*     */ 
/*     */ 
/*     */         public void checkClientTrusted(X509Certificate[] certs, String authType) {}
/*     */         
/*     */ 
/*     */         public void checkServerTrusted(X509Certificate[] certs, String authType) {}
/* 234 */       } };
/* 235 */       SSLContext sslContext = SSLContext.getInstance("TLS");
/* 236 */       sslContext.init(kms, trustAllCerts, new SecureRandom());
/*     */       
/* 238 */       HostnameVerifier allHostsValid = new HostnameVerifier() {
/*     */         public boolean verify(String hostname, SSLSession session) {
/* 240 */           return true;
/*     */         }
/* 242 */       };
/* 243 */       SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslContext, allHostsValid);
/*     */       
/*     */ 
/*     */ 
/* 247 */       Registry socketFactoryRegistry = RegistryBuilder.create().register("http", PlainConnectionSocketFactory.getSocketFactory()).register("https", sslSocketFactory).build();
/* 248 */       PoolingHttpClientConnectionManager connMgr = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
/* 249 */       connMgr.setMaxTotal(maxTotal);
/* 250 */       connMgr.setDefaultMaxPerRoute(defaultMaxPerRoute);
/*     */       
/* 252 */       return connMgr;
/*     */     } catch (KeyStoreException e) {
/* 254 */       e.printStackTrace();
/* 255 */       System.out.println("初始化P12连接出错");
/*     */     } catch (CertificateException e) {
/* 257 */       e.printStackTrace();
/* 258 */       System.out.println("初始化P12连接出错");
/*     */     } catch (NoSuchAlgorithmException e) {
/* 260 */       e.printStackTrace();
/* 261 */       System.out.println("初始化P12连接出错");
/*     */     } catch (FileNotFoundException e) {
/* 263 */       e.printStackTrace();
/* 264 */       System.out.println("初始化P12连接出错");
/*     */     } catch (IOException e) {
/* 266 */       e.printStackTrace();
/* 267 */       System.out.println("初始化P12连接出错");
/*     */     } catch (UnrecoverableKeyException e) {
/* 269 */       e.printStackTrace();
/* 270 */       System.out.println("初始化P12连接出错");
/*     */     } catch (KeyManagementException e) {
/* 272 */       e.printStackTrace();
/* 273 */       System.out.println("初始化P12连接出错");
/*     */     }
/*     */     
/* 276 */     return null;
/*     */   }
/*     */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryCapture/lotteryCaptureRepair.jar!/javautils/http/HttpConfigBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */