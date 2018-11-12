package javautils.http;

import java.io.InputStream;
import org.apache.http.entity.InputStreamEntity;
import java.io.ByteArrayInputStream;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import java.util.ArrayList;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.CloseableHttpResponse;
import java.util.Iterator;
import java.io.IOException;
import java.net.URLDecoder;
import org.apache.http.util.EntityUtils;
import org.apache.http.client.methods.HttpUriRequest;
import java.util.Map.Entry;
import org.apache.http.client.methods.HttpGet;
import java.util.Map;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class HttpClientUtil
{
    private static Logger logger;
    
    static {
        HttpClientUtil.logger = LoggerFactory.getLogger((Class)HttpClientUtil.class);
    }
    
    private static CloseableHttpClient getHttpClient(final int timeout) {
        final HttpClientBuilder clientBuilder = HttpClientBuilder.create();
        final ConnectionKeepAliveStrategy keepAliveStrategy = HttpConfigBuilder.getInstance().buildKeepAliveStrategy(60000L);
        clientBuilder.setKeepAliveStrategy(keepAliveStrategy);
        final RequestConfig requestConfig = HttpConfigBuilder.getInstance().buildRequestConfig(timeout);
        clientBuilder.setDefaultRequestConfig(requestConfig);
        final CloseableHttpClient httpClient = clientBuilder.build();
        return httpClient;
    }
    
    private static CloseableHttpClient getHttpsClient(final int timeout, final String sslVersion) throws NoSuchAlgorithmException, KeyManagementException {
        final HttpClientBuilder clientBuilder = HttpClientBuilder.create();
        final ConnectionKeepAliveStrategy keepAliveStrategy = HttpConfigBuilder.getInstance().buildKeepAliveStrategy(60000L);
        clientBuilder.setKeepAliveStrategy(keepAliveStrategy);
        final RequestConfig requestConfig = HttpConfigBuilder.getInstance().buildRequestConfig(timeout);
        clientBuilder.setDefaultRequestConfig(requestConfig);
        BasicHttpClientConnectionManager connMgr;
        if (sslVersion == null) {
            connMgr = HttpConfigBuilder.getInstance().buildBasicHttpsClientConnectionManager();
        }
        else {
            connMgr = HttpConfigBuilder.getInstance().buildBasicHttpsClientConnectionManager(sslVersion);
        }
        clientBuilder.setConnectionManager((HttpClientConnectionManager)connMgr);
        final CloseableHttpClient httpClient = clientBuilder.build();
        return httpClient;
    }
    
    public static String get(final String url, final Map<String, String> httpHeader, final int timeout) throws Exception {
        CloseableHttpClient httpClient;
        if (url.startsWith("https://")) {
            httpClient = getHttpsClient(timeout, null);
        }
        else {
            httpClient = getHttpClient(timeout);
        }
        return get(httpClient, url, httpHeader);
    }
    
    public static String get(final CloseableHttpClient httpClient, String url, final Map<String, String> httpHeader) throws IOException {
        String strResult = null;
        Label_0247: {
            try {
                final HttpGet request = new HttpGet(url);
                if (httpHeader != null && httpHeader.size() > 0) {
                    for (final Entry<String, String> entry : httpHeader.entrySet()) {
                        final String key = entry.getKey();
                        final String value = entry.getValue();
                        request.addHeader(key, value);
                    }
                }
                final CloseableHttpResponse response = httpClient.execute((HttpUriRequest)request);
                if (response.getStatusLine().getStatusCode() == 200) {
                    try {
                        strResult = EntityUtils.toString(response.getEntity(), "UTF-8");
                        url = URLDecoder.decode(url, "UTF-8");
                    }
                    catch (Exception e2) {
                        HttpClientUtil.logger.error("get请求提交失败:" + url);
                        break Label_0247;
                    }
                    finally {
                        response.close();
                    }
                    response.close();
                }
            }
            catch (IOException e) {
                HttpClientUtil.logger.error("get请求提交失败:" + url, (Throwable)e);
                return strResult;
            }
            finally {
                httpClient.close();
            }
        }
        httpClient.close();
        return strResult;
    }
    
    public static String post(final String url, final Map<String, String> params, final Map<String, String> headers, final int timeout) throws Exception {
        CloseableHttpClient httpClient;
        if (url.startsWith("https://")) {
            httpClient = getHttpsClient(timeout, null);
        }
        else {
            httpClient = getHttpClient(timeout);
        }
        return post(httpClient, url, params, headers);
    }
    
    public static String postAsStream(final String url, final Map<String, String> params, final Map<String, String> headers, final int timeout) throws Exception {
        CloseableHttpClient httpClient;
        if (url.startsWith("https://")) {
            httpClient = getHttpsClient(timeout, null);
        }
        else {
            httpClient = getHttpClient(timeout);
        }
        return postAsStream(httpClient, url, params, headers);
    }
    
    public static String postAsStream(final String url, final String content, final Map<String, String> headers, final int timeout) throws Exception {
        CloseableHttpClient httpClient;
        if (url.startsWith("https://")) {
            httpClient = getHttpsClient(timeout, null);
        }
        else {
            httpClient = getHttpClient(timeout);
        }
        return postAsStream(httpClient, url, content, headers);
    }
    
    public static String postSSL(final String url, final Map<String, String> params, final Map<String, String> headers, final int timeout, final String sslVersion) throws Exception {
        CloseableHttpClient httpClient;
        if (url.startsWith("https://")) {
            httpClient = getHttpsClient(timeout, sslVersion);
        }
        else {
            httpClient = getHttpClient(timeout);
        }
        return post(httpClient, url, params, headers);
    }
    
    public static String post(final CloseableHttpClient httpClient, String url, final Map<String, String> params, final Map<String, String> headers) throws IOException {
        String result = null;
        Label_0430: {
            try {
                final HttpPost request = new HttpPost(url);
                if (headers != null && headers.size() > 0) {
                    for (final Entry<String, String> entry : headers.entrySet()) {
                        final String key = entry.getKey();
                        final String value = entry.getValue();
                        request.addHeader(key, value);
                    }
                }
                if (params != null && params.size() > 0) {
                    final List list = new ArrayList();
                    for (final Entry elem : params.entrySet()) {
                        list.add(new BasicNameValuePair((String)elem.getKey(), (String)elem.getValue()));
                    }
                    if (list.size() > 0) {
                        final UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, "utf-8");
                        request.setEntity((HttpEntity)entity);
                    }
                }
                final CloseableHttpResponse response = httpClient.execute((HttpUriRequest)request);
                url = URLDecoder.decode(url, "UTF-8");
                if (response.getStatusLine().getStatusCode() == 200) {
                    try {
                        result = EntityUtils.toString(response.getEntity(), "UTF-8");
                    }
                    catch (Exception e) {
                        HttpClientUtil.logger.error("post请求提交失败:" + url, (Throwable)e);
                        break Label_0430;
                    }
                    finally {
                        response.close();
                    }
                    response.close();
                }
                else {
                    result = String.valueOf(response.getStatusLine().getStatusCode()) + "-" + response.getStatusLine().getReasonPhrase();
                }
            }
            catch (Exception e2) {
                HttpClientUtil.logger.error("post请求提交失败:" + url, (Throwable)e2);
                return result;
            }
            finally {
                httpClient.close();
            }
        }
        httpClient.close();
        return result;
    }
    
    public static String postAsStream(final CloseableHttpClient httpClient, String url, final Map<String, String> params, final Map<String, String> headers) throws IOException {
        String result = null;
        Label_0355: {
            try {
                final HttpPost request = new HttpPost(url);
                if (headers != null && headers.size() > 0) {
                    for (final Entry<String, String> entry : headers.entrySet()) {
                        final String key = entry.getKey();
                        final String value = entry.getValue();
                        request.addHeader(key, value);
                    }
                }
                if (params != null && params.size() > 0) {
                    final String paramUrl = ToUrlParamUtils.toUrlParam(params);
                    final InputStream is = new ByteArrayInputStream(paramUrl.getBytes());
                    final InputStreamEntity inputEntry = new InputStreamEntity(is);
                    request.setEntity((HttpEntity)inputEntry);
                }
                final CloseableHttpResponse response = httpClient.execute((HttpUriRequest)request);
                url = URLDecoder.decode(url, "UTF-8");
                if (response.getStatusLine().getStatusCode() == 200) {
                    try {
                        result = EntityUtils.toString(response.getEntity(), "UTF-8");
                    }
                    catch (Exception e) {
                        HttpClientUtil.logger.error("post请求提交失败:" + url, (Throwable)e);
                        break Label_0355;
                    }
                    finally {
                        response.close();
                    }
                    response.close();
                }
                else {
                    result = String.valueOf(response.getStatusLine().getStatusCode()) + "-" + response.getStatusLine().getReasonPhrase();
                }
            }
            catch (Exception e2) {
                HttpClientUtil.logger.error("post请求提交失败:" + url, (Throwable)e2);
                return result;
            }
            finally {
                httpClient.close();
            }
        }
        httpClient.close();
        return result;
    }
    
    public static String postAsStream(final CloseableHttpClient httpClient, String url, final String content, final Map<String, String> headers) throws IOException {
        String result = null;
        Label_0335: {
            try {
                final HttpPost request = new HttpPost(url);
                if (headers != null && headers.size() > 0) {
                    for (final Entry<String, String> entry : headers.entrySet()) {
                        final String key = entry.getKey();
                        final String value = entry.getValue();
                        request.addHeader(key, value);
                    }
                }
                final InputStream is = new ByteArrayInputStream(content.getBytes());
                final InputStreamEntity inputEntry = new InputStreamEntity(is);
                request.setEntity((HttpEntity)inputEntry);
                final CloseableHttpResponse response = httpClient.execute((HttpUriRequest)request);
                url = URLDecoder.decode(url, "UTF-8");
                if (response.getStatusLine().getStatusCode() == 200) {
                    try {
                        result = EntityUtils.toString(response.getEntity(), "UTF-8");
                    }
                    catch (Exception e) {
                        HttpClientUtil.logger.error("post请求提交失败:" + url, (Throwable)e);
                        break Label_0335;
                    }
                    finally {
                        response.close();
                    }
                    response.close();
                }
                else {
                    result = String.valueOf(response.getStatusLine().getStatusCode()) + "-" + response.getStatusLine().getReasonPhrase();
                }
            }
            catch (Exception e2) {
                HttpClientUtil.logger.error("post请求提交失败:" + url, (Throwable)e2);
                return result;
            }
            finally {
                httpClient.close();
            }
        }
        httpClient.close();
        return result;
    }
}
