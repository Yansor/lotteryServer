package javautils.http;

import java.security.UnrecoverableKeyException;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.security.cert.CertificateException;
import java.security.KeyStoreException;

import javax.net.ssl.KeyManagerFactory;

import java.io.InputStream;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.io.File;

import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.config.Registry;

import java.security.NoSuchAlgorithmException;
import java.security.KeyManagementException;

import org.apache.http.config.Lookup;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;

import javax.net.ssl.SSLSession;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManager;

import java.security.SecureRandom;

import javax.net.ssl.SSLContext;

import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;
import javax.net.ssl.TrustManager;

import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.protocol.HttpContext;
import org.apache.http.HttpResponse;
import org.apache.http.conn.ConnectionKeepAliveStrategy;

public class HttpConfigBuilder
{
    private static final HttpConfigBuilder INSTANCE;
    
    static {
        INSTANCE = new HttpConfigBuilder();
    }
    
    private HttpConfigBuilder() {
    }
    
    public static HttpConfigBuilder getInstance() {
        return HttpConfigBuilder.INSTANCE;
    }
    
    public ConnectionKeepAliveStrategy buildKeepAliveStrategy(final long duration) {
        return (ConnectionKeepAliveStrategy)new ConnectionKeepAliveStrategy() {
            public long getKeepAliveDuration(final HttpResponse response, final HttpContext context) {
                return duration;
            }
        };
    }
    
    public HttpRequestRetryHandler buildRetryHandler(final int retryCount) {
        return (HttpRequestRetryHandler)new DefaultHttpRequestRetryHandler(retryCount, false);
    }
    
    public RequestConfig buildRequestConfig(final int timeout) {
        return RequestConfig.custom().setConnectionRequestTimeout(timeout).setConnectTimeout(timeout).setSocketTimeout(timeout).build();
    }
    
    public BasicHttpClientConnectionManager buildBasicHttpClientConnectionManager() {
        final BasicHttpClientConnectionManager connectionManager = new BasicHttpClientConnectionManager();
        return connectionManager;
    }
    
    public BasicHttpClientConnectionManager buildBasicHttpsClientConnectionManager() {
        try {
            final TrustManager[] trustAllCerts = { new X509TrustManager() {
                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                    
                    @Override
                    public void checkClientTrusted(final X509Certificate[] certs, final String authType) {
                    }
                    
                    @Override
                    public void checkServerTrusted(final X509Certificate[] certs, final String authType) {
                    }
                } };
            final SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts, new SecureRandom());
            final HostnameVerifier allHostsValid = new HostnameVerifier() {
                @Override
                public boolean verify(final String hostname, final SSLSession session) {
                    return true;
                }
            };
            final SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslContext, allHostsValid);
            final Registry<Object> socketFactoryRegistry = RegistryBuilder.create().register("http", PlainConnectionSocketFactory.getSocketFactory()).register("https", sslSocketFactory).build();
            final BasicHttpClientConnectionManager connMgr = new BasicHttpClientConnectionManager((Lookup)socketFactoryRegistry);
            return connMgr;
        }
        catch (KeyManagementException e) {
            e.printStackTrace();
            System.out.println("初始化https连接出错");
        }
        catch (NoSuchAlgorithmException e2) {
            e2.printStackTrace();
            System.out.println("初始化https连接出错");
        }
        return null;
    }
    
    public BasicHttpClientConnectionManager buildBasicHttpsClientConnectionManager(final String sslVersion) {
        try {
            final TrustManager[] trustAllCerts = { new X509TrustManager() {
                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                    
                    @Override
                    public void checkClientTrusted(final X509Certificate[] certs, final String authType) {
                    }
                    
                    @Override
                    public void checkServerTrusted(final X509Certificate[] certs, final String authType) {
                    }
                } };
            final SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts, new SecureRandom());
            final HostnameVerifier allHostsValid = new HostnameVerifier() {
                @Override
                public boolean verify(final String hostname, final SSLSession session) {
                    return true;
                }
            };
            final SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslContext, new String[] { sslVersion }, (String[])null, allHostsValid);
            final Registry<Object> socketFactoryRegistry = RegistryBuilder.create().register("http", (Object)PlainConnectionSocketFactory.getSocketFactory()).register("https", (Object)sslSocketFactory).build();
            final BasicHttpClientConnectionManager connMgr = new BasicHttpClientConnectionManager((Lookup)socketFactoryRegistry);
            return connMgr;
        }
        catch (KeyManagementException e) {
            e.printStackTrace();
            System.out.println("初始化https连接出错");
        }
        catch (NoSuchAlgorithmException e2) {
            e2.printStackTrace();
            System.out.println("初始化https连接出错");
        }
        return null;
    }
    
    public PoolingHttpClientConnectionManager buildPoolingHttpsClientConnectionManager(final int maxTotal, final int defaultMaxPerRoute) {
        try {
            final TrustManager[] trustAllCerts = { new X509TrustManager() {
                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                    
                    @Override
                    public void checkClientTrusted(final X509Certificate[] certs, final String authType) {
                    }
                    
                    @Override
                    public void checkServerTrusted(final X509Certificate[] certs, final String authType) {
                    }
                } };
            final SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts, new SecureRandom());
            final HostnameVerifier allHostsValid = new HostnameVerifier() {
                @Override
                public boolean verify(final String hostname, final SSLSession session) {
                    return true;
                }
            };
            final SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslContext, allHostsValid);
            final Registry<Object> socketFactoryRegistry = RegistryBuilder.create().register("http", (Object)PlainConnectionSocketFactory.getSocketFactory()).register("https", (Object)sslSocketFactory).build();
            final PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager((Registry)socketFactoryRegistry);
            connectionManager.setMaxTotal(maxTotal);
            connectionManager.setDefaultMaxPerRoute(defaultMaxPerRoute);
            return connectionManager;
        }
        catch (KeyManagementException e) {
            e.printStackTrace();
            System.out.println("初始化https连接出错");
        }
        catch (NoSuchAlgorithmException e2) {
            e2.printStackTrace();
            System.out.println("初始化https连接出错");
        }
        return null;
    }
    
    public PoolingHttpClientConnectionManager buildPoolingHttpClientConnectionManager(final int maxTotal, final int defaultMaxPerRoute) {
        final PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(maxTotal);
        connectionManager.setDefaultMaxPerRoute(defaultMaxPerRoute);
        return connectionManager;
    }
    
    public PoolingHttpClientConnectionManager buildP12PoolingHttpClientConnectionManager(final int maxTotal, final int defaultMaxPerRoute, final File p12File, final String pwd) {
        try {
            final KeyStore ks = KeyStore.getInstance("PKCS12");
            final FileInputStream fis = new FileInputStream(p12File);
            ks.load(fis, pwd.toCharArray());
            final KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            kmf.init(ks, pwd.toCharArray());
            final KeyManager[] kms = kmf.getKeyManagers();
            final TrustManager[] trustAllCerts = { new X509TrustManager() {
                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                    
                    @Override
                    public void checkClientTrusted(final X509Certificate[] certs, final String authType) {
                    }
                    
                    @Override
                    public void checkServerTrusted(final X509Certificate[] certs, final String authType) {
                    }
                } };
            final SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(kms, trustAllCerts, new SecureRandom());
            final HostnameVerifier allHostsValid = new HostnameVerifier() {
                @Override
                public boolean verify(final String hostname, final SSLSession session) {
                    return true;
                }
            };
            final SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslContext, allHostsValid);
            final Registry<Object> socketFactoryRegistry = RegistryBuilder.create().register("http", (Object)PlainConnectionSocketFactory.getSocketFactory()).register("https", (Object)sslSocketFactory).build();
            final PoolingHttpClientConnectionManager connMgr = new PoolingHttpClientConnectionManager((Registry)socketFactoryRegistry);
            connMgr.setMaxTotal(maxTotal);
            connMgr.setDefaultMaxPerRoute(defaultMaxPerRoute);
            return connMgr;
        }
        catch (KeyStoreException e) {
            e.printStackTrace();
            System.out.println("初始化P12连接出错");
        }
        catch (CertificateException e2) {
            e2.printStackTrace();
            System.out.println("初始化P12连接出错");
        }
        catch (NoSuchAlgorithmException e3) {
            e3.printStackTrace();
            System.out.println("初始化P12连接出错");
        }
        catch (FileNotFoundException e4) {
            e4.printStackTrace();
            System.out.println("初始化P12连接出错");
        }
        catch (IOException e5) {
            e5.printStackTrace();
            System.out.println("初始化P12连接出错");
        }
        catch (UnrecoverableKeyException e6) {
            e6.printStackTrace();
            System.out.println("初始化P12连接出错");
        }
        catch (KeyManagementException e7) {
            e7.printStackTrace();
            System.out.println("初始化P12连接出错");
        }
        return null;
    }
}
