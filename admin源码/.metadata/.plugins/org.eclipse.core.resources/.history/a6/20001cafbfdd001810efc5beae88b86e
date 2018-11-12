package javautils.http;

import java.io.InputStream;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.NameValuePair;
import java.net.SocketTimeoutException;
import org.apache.commons.httpclient.ConnectTimeoutException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.slf4j.LoggerFactory;
import org.apache.commons.httpclient.HttpClient;
import org.slf4j.Logger;

public class EasyHttpClient
{
    private static final Logger logger;
    private HttpClient httpClient;
    private final int TIMEOUT = 10000;
    private final int SO_TIMEOUT = 10000;
    private int REPEAT_TIMES;
    
    static {
        logger = LoggerFactory.getLogger((Class)EasyHttpClient.class);
    }
    
    public EasyHttpClient() {
        this.httpClient = new HttpClient((HttpConnectionManager)new MultiThreadedHttpConnectionManager());
        this.REPEAT_TIMES = 3;
        this.setTimeOut(10000, 10000);
    }
    
    public void setTimeOut(final int TIMEOUT, final int SO_TIMEOUT) {
        this.httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(TIMEOUT);
        this.httpClient.getHttpConnectionManager().getParams().setSoTimeout(SO_TIMEOUT);
    }
    
    public void setRepeatTimes(final int times) {
        this.REPEAT_TIMES = times;
    }
    
    public String get(final String url) {
        final GetMethod get = new GetMethod(url);
        for (int i = 0; i < this.REPEAT_TIMES; ++i) {
            EasyHttpClient.logger.debug("正在发送请求，当前第" + (i + 1) + "次...");
            try {
                final int state = this.httpClient.executeMethod((HttpMethod)get);
                EasyHttpClient.logger.debug("请求状态" + state);
                if (state == 200) {
                    final String data = fromInputStream(get.getResponseBodyAsStream());
                    EasyHttpClient.logger.debug("成功获取到数据，长度为" + data.length());
                    get.releaseConnection();
                    return data;
                }
            }
            catch (ConnectTimeoutException e2) {
                EasyHttpClient.logger.error("请求超时...Connect Timeout");
            }
            catch (SocketTimeoutException e3) {
                EasyHttpClient.logger.error("请求超时...Socket Timeout");
            }
            catch (Exception e) {
                EasyHttpClient.logger.error("请求出错...", (Throwable)e);
            }
            finally {
                get.releaseConnection();
            }
            get.releaseConnection();
        }
        return null;
    }
    
    public String post(final String url, final NameValuePair[] params) {
        final PostMethod post = new PostMethod(url);
        if (params != null) {
            post.setRequestBody(params);
        }
        for (int i = 0; i < this.REPEAT_TIMES; ++i) {
            EasyHttpClient.logger.debug("正在发送请求，当前第" + (i + 1) + "次...");
            try {
                final int state = this.httpClient.executeMethod((HttpMethod)post);
                EasyHttpClient.logger.debug("请求状态" + state);
                if (state == 200) {
                    final String data = fromInputStream(post.getResponseBodyAsStream());
                    EasyHttpClient.logger.debug("成功获取到数据，长度为" + data.length());
                    post.releaseConnection();
                    return data;
                }
            }
            catch (ConnectTimeoutException e2) {
                EasyHttpClient.logger.error("请求超时...Connect Timeout");
            }
            catch (SocketTimeoutException e3) {
                EasyHttpClient.logger.error("请求超时...Socket Timeout");
            }
            catch (Exception e) {
                EasyHttpClient.logger.error("请求出错...", (Throwable)e);
            }
            finally {
                post.releaseConnection();
            }
            post.releaseConnection();
        }
        return null;
    }
    
    public static String fromInputStream(final InputStream inputStream) {
        try {
            final StringBuffer sb = new StringBuffer();
            final byte[] bytes = new byte[1024];
            int len;
            while ((len = inputStream.read(bytes)) != -1) {
                sb.append(new String(bytes, 0, len));
            }
            inputStream.close();
            return sb.toString();
        }
        catch (Exception ex) {
            return null;
        }
    }
}
