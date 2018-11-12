package lottery.domains.content.payment.lepay.utils;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

import java.util.HashMap;
import java.net.URLEncoder;
import java.net.URLDecoder;
import java.io.StringWriter;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.util.List;
import java.util.Collections;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;

import javax.net.ssl.SSLSession;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;

import java.security.SecureRandom;

import javax.net.ssl.TrustManager;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.io.IOException;
import java.net.URL;
import java.util.Map;

public abstract class WebUtil
{
    private static final String DEFAULT_CHARSET = "UTF-8";
    private static final String METHOD_POST = "POST";
    private static final String METHOD_GET = "GET";
    
    public static String doPost(final String url, final Map<String, String> params, final int connectTimeout, final int readTimeout) throws Exception {
        return doPost(url, params, "UTF-8", connectTimeout, readTimeout);
    }
    
    public static String doPost(final String url, final Map<String, String> params, final String charset, final int connectTimeout, final int readTimeout) throws Exception {
        final String ctype = "application/x-www-form-urlencoded;charset=" + charset;
        final String query = buildQuery(params, charset);
        byte[] content = new byte[0];
        if (query != null) {
            content = query.getBytes(charset);
        }
        return doPost(url, ctype, content, connectTimeout, readTimeout);
    }
    
    public static String doPost(final String url, final String ctype, final byte[] content, final int connectTimeout, final int readTimeout) throws IOException {
        HttpURLConnection conn = null;
        OutputStream out = null;
        String rsp = null;
        try {
            try {
                conn = getConnection(new URL(url), "POST", ctype);
                conn.setConnectTimeout(connectTimeout);
                conn.setReadTimeout(readTimeout);
            }
            catch (IOException e) {
                throw e;
            }
            try {
                out = conn.getOutputStream();
                out.write(content);
                rsp = getResponseAsString(conn);
            }
            catch (IOException e) {
                throw e;
            }
        }
        finally {
            if (out != null) {
                out.close();
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
        if (out != null) {
            out.close();
        }
        if (conn != null) {
            conn.disconnect();
        }
        return rsp;
    }
    
    public static String doGet(final String url, final String charset) throws IOException {
        HttpURLConnection conn = null;
        String rsp = null;
        try {
            final String ctype = "application/x-www-form-urlencoded;charset=" + charset;
            try {
                conn = getConnection(new URL(url), "GET", ctype);
            }
            catch (IOException e) {
                throw e;
            }
            try {
                rsp = getResponseAsString(conn);
            }
            catch (IOException e) {
                throw e;
            }
        }
        finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        if (conn != null) {
            conn.disconnect();
        }
        return rsp;
    }
    
    private static HttpURLConnection getConnection(final URL url, final String method, final String ctype) throws IOException {
        HttpURLConnection conn = null;
        if ("https".equals(url.getProtocol())) {
            SSLContext ctx = null;
            try {
                ctx = SSLContext.getInstance("TLS");
                ctx.init(new KeyManager[0], new TrustManager[] { new DefaultTrustManager() }, new SecureRandom());
            }
            catch (Exception e) {
                throw new IOException(e);
            }
            final HttpsURLConnection connHttps = (HttpsURLConnection)url.openConnection();
            connHttps.setSSLSocketFactory(ctx.getSocketFactory());
            connHttps.setHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(final String hostname, final SSLSession session) {
                    return false;
                }
            });
            conn = connHttps;
        }
        else {
            conn = (HttpURLConnection)url.openConnection();
        }
        conn.setRequestMethod(method);
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setRequestProperty("Accept", "text/xml,text/javascript,text/html");
        conn.setRequestProperty("Accept", "text/xml,text/javascript,text/html,application/json");
        conn.setRequestProperty("User-Agent", "httpclient");
        conn.setRequestProperty("Content-Type", ctype);
        return conn;
    }
    
    public static String buildQuery(final Map<String, String> params, final String charset) throws Exception {
        if (params == null || params.isEmpty()) {
            return null;
        }
        final StringBuilder query = new StringBuilder();
        boolean hasParam = false;
        for (final Entry entry : params.entrySet()) {
            final String name = (String) entry.getKey();
            final String value = (String) entry.getValue();
            if (StringUtil.areNotEmpty(new String[] { name, value })) {
                if (hasParam) {
                    query.append("&");
                }
                else {
                    hasParam = true;
                }
                query.append(name).append("=").append(value);
            }
        }
        return query.toString();
    }
    
    public static String buildAlphabeticalSortedQuery(final Map<String, String> params) throws Exception {
        if (params == null || params.isEmpty()) {
            return null;
        }
        final StringBuilder query = new StringBuilder();
        final List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);
        boolean hasParam = false;
        for (final String name : keys) {
            final String value = params.get(name);
            if (StringUtil.areNotEmpty(new String[] { name, value })) {
                if (hasParam) {
                    query.append("&");
                }
                else {
                    hasParam = true;
                }
                query.append(name).append("=").append(value);
            }
        }
        return query.toString();
    }
    
    public static String getURL(final Map<String, String> paramsMap) {
        String url = null;
        if (paramsMap != null && paramsMap.size() > 0) {
            for (final String key : paramsMap.keySet()) {
                final String value = paramsMap.get(key);
                if (url == null) {
                    url = String.valueOf(key) + "=" + value;
                }
                else {
                    url = String.valueOf(url) + "&" + key + "=" + value;
                }
            }
        }
        return url;
    }
    
    public static String buildRequestStr(final Map<String, String> params) {
        if (params == null || params.isEmpty()) {
            return null;
        }
        final StringBuilder query = new StringBuilder();
        final List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);
        boolean hasParam = false;
        for (final String name : keys) {
            final String value = params.get(name);
            if (StringUtil.areNotEmpty(new String[] { name, value })) {
                if (hasParam) {
                    query.append("&");
                }
                else {
                    hasParam = true;
                }
                query.append(name).append("=").append(value);
            }
        }
        return query.toString();
    }
    
    protected static String getResponseAsString(final HttpURLConnection conn) throws IOException {
        final String charset = getResponseCharset(conn.getContentType());
        final InputStream es = conn.getErrorStream();
        if (es == null) {
            return getStreamAsString(conn.getInputStream(), charset);
        }
        final String msg = getStreamAsString(es, charset);
        if (StringUtil.isEmpty(msg)) {
            throw new IOException(String.valueOf(conn.getResponseCode()) + ":" + conn.getResponseMessage());
        }
        throw new IOException(msg);
    }
    
    private static String getStreamAsString(final InputStream stream, final String charset) throws IOException {
        try {
            final BufferedReader reader = new BufferedReader(new InputStreamReader(stream, charset));
            final StringWriter writer = new StringWriter();
            final char[] chars = new char[256];
            int count = 0;
            while ((count = reader.read(chars)) > 0) {
                writer.write(chars, 0, count);
            }
            return writer.toString();
        }
        finally {
            if (stream != null) {
                stream.close();
            }
        }
    }
    
    private static String getResponseCharset(final String ctype) {
        String charset = "UTF-8";
        if (!StringUtil.isEmpty(ctype)) {
            final String[] params = ctype.split(";");
            final String[] array;
            final int length = (array = params).length;
            int i = 0;
            while (i < length) {
                String param = array[i];
                param = param.trim();
                if (param.startsWith("charset")) {
                    final String[] pair = param.split("=", 2);
                    if (pair.length != 2) {
                        break;
                    }
                    if (StringUtil.isEmpty(pair[1])) {
                        break;
                    }
                    charset = pair[1].trim();
                    break;
                }
                else {
                    ++i;
                }
            }
        }
        return charset;
    }
    
    public static String decode(final String value) {
        return decode(value, "UTF-8");
    }
    
    public static String encode(final String value) {
        return encode(value, "UTF-8");
    }
    
    public static String decode(final String value, final String charset) {
        String result = null;
        if (!StringUtil.isEmpty(value)) {
            try {
                result = URLDecoder.decode(value, charset);
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }
    
    public static String encode(final String value, final String charset) {
        String result = null;
        if (!StringUtil.isEmpty(value)) {
            try {
                result = URLEncoder.encode(value, charset);
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }
    
    public static Map<String, String> splitUrlQuery(final String query) {
        final Map result = new HashMap();
        final String[] pairs = query.split("&");
        if (pairs != null && pairs.length > 0) {
            String[] array;
            for (int length = (array = pairs).length, i = 0; i < length; ++i) {
                final String pair = array[i];
                final String[] param = pair.split("=", 2);
                if (param != null && param.length == 2) {
                    result.put(param[0], param[1]);
                }
            }
        }
        return (Map<String, String>)result;
    }
    
    private static class DefaultTrustManager implements X509TrustManager
    {
        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
        
        @Override
        public void checkClientTrusted(final X509Certificate[] chain, final String authType) throws CertificateException {
        }
        
        @Override
        public void checkServerTrusted(final X509Certificate[] chain, final String authType) throws CertificateException {
        }
    }
}
