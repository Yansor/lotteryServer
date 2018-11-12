package javautils.http;

import java.util.ArrayList;
import java.util.Arrays;
import org.springframework.http.MediaType;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.client.HttpRequestRetryHandler;
import java.util.List;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.apache.http.client.HttpClient;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.apache.http.conn.HttpClientConnectionManager;
import java.io.File;
import org.apache.commons.lang.StringUtils;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.web.client.RestTemplate;
import java.nio.charset.Charset;

public class RestTemplateBuilder
{
    private static final RestTemplateBuilder INSTANCE;
    private static final int DEFAULT_TIMEOUT = 3000;
    private static final long DEFAULT_KEEPALIVE_DURATION = 60000L;
    private static final int DEFAULT_RETRYCOUNT = 3;
    private static final int DEFAULT_MAX_TOTAL = 100;
    private static final int DEFAULT_MAX_PERROUTE = 50;
    private static final Charset DEFAULT_CHARSET;
    private int timeout;
    private long keepaliveDuration;
    private int retrycount;
    private int maxTotal;
    private int maxPerroute;
    private String sslFile;
    private String sslFilePwd;
    
    static {
        INSTANCE = new RestTemplateBuilder();
        DEFAULT_CHARSET = Charset.forName("UTF-8");
    }
    
    public RestTemplateBuilder() {
        this.timeout = 3000;
        this.keepaliveDuration = 60000L;
        this.retrycount = 3;
        this.maxTotal = 100;
        this.maxPerroute = 50;
    }
    
    public RestTemplate build() {
        final HttpClientBuilder clientBuilder = HttpClientBuilder.create();
        if (this.retrycount > 0) {
            final HttpRequestRetryHandler retryHandler = HttpConfigBuilder.getInstance().buildRetryHandler(this.retrycount);
            clientBuilder.setRetryHandler(retryHandler);
        }
        if (this.keepaliveDuration > 0L) {
            final ConnectionKeepAliveStrategy keepAliveStrategy = HttpConfigBuilder.getInstance().buildKeepAliveStrategy(this.keepaliveDuration);
            clientBuilder.setKeepAliveStrategy(keepAliveStrategy);
        }
        if (this.timeout > 0) {
            final RequestConfig requestConfig = HttpConfigBuilder.getInstance().buildRequestConfig(this.timeout);
            clientBuilder.setDefaultRequestConfig(requestConfig);
        }
        if (this.maxTotal > 0 && this.maxPerroute > 0) {
            PoolingHttpClientConnectionManager connMgr;
            if (StringUtils.isNotEmpty(this.sslFile) && StringUtils.isNotEmpty(this.sslFilePwd)) {
                final String path = RestTemplateBuilder.class.getResource("/").getPath();
                final File file = new File(String.valueOf(path) + this.sslFile);
                connMgr = HttpConfigBuilder.getInstance().buildP12PoolingHttpClientConnectionManager(this.maxTotal, this.maxPerroute, file, this.sslFilePwd);
            }
            else {
                connMgr = HttpConfigBuilder.getInstance().buildPoolingHttpClientConnectionManager(this.maxTotal, this.maxPerroute);
            }
            clientBuilder.setConnectionManager((HttpClientConnectionManager)connMgr);
        }
        final CloseableHttpClient httpClient = clientBuilder.build();
        final HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient((HttpClient)httpClient);
        final RestTemplate restTemplate = new RestTemplate((ClientHttpRequestFactory)requestFactory);
        restTemplate.setMessageConverters((List)this.getMessageConverters());
        return restTemplate;
    }
    
    private List<HttpMessageConverter<?>> getMessageConverters() {
        final StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter(RestTemplateBuilder.DEFAULT_CHARSET);
        final FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
        final MediaType jsonMediaType = MediaType.valueOf("application/json;charset=UTF-8");
        final MediaType textMediaType = MediaType.valueOf("text/html;charset=UTF-8");
        final List<MediaType> supportedMediaTypes = Arrays.asList(jsonMediaType, textMediaType);
        fastJsonHttpMessageConverter.setSupportedMediaTypes((List)supportedMediaTypes);
        final List<HttpMessageConverter<?>> httpMessageConverters = new ArrayList<HttpMessageConverter<?>>();
        httpMessageConverters.add((HttpMessageConverter<?>)stringHttpMessageConverter);
        httpMessageConverters.add((HttpMessageConverter<?>)fastJsonHttpMessageConverter);
        return httpMessageConverters;
    }
    
    public int getTimeout() {
        return this.timeout;
    }
    
    public void setTimeout(final int timeout) {
        this.timeout = timeout;
    }
    
    public long getKeepaliveDuration() {
        return this.keepaliveDuration;
    }
    
    public void setKeepaliveDuration(final int keepaliveDuration) {
        this.keepaliveDuration = keepaliveDuration;
    }
    
    public int getRetrycount() {
        return this.retrycount;
    }
    
    public void setRetrycount(final int retrycount) {
        this.retrycount = retrycount;
    }
    
    public int getMaxTotal() {
        return this.maxTotal;
    }
    
    public void setMaxTotal(final int maxTotal) {
        this.maxTotal = maxTotal;
    }
    
    public int getMaxPerroute() {
        return this.maxPerroute;
    }
    
    public void setMaxPerroute(final int maxPerroute) {
        this.maxPerroute = maxPerroute;
    }
    
    public String getSslFile() {
        return this.sslFile;
    }
    
    public void setSslFile(final String sslFile) {
        this.sslFile = sslFile;
    }
    
    public String getSslFilePwd() {
        return this.sslFilePwd;
    }
    
    public void setSslFilePwd(final String sslFilePwd) {
        this.sslFilePwd = sslFilePwd;
    }
}
