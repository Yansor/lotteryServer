package lottery.domains.content.payment.RX.utils;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.HttpEntity;
import java.util.Iterator;
import org.apache.http.impl.client.CloseableHttpClient;
import java.io.IOException;
import org.apache.http.util.EntityUtils;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.client.config.RequestConfig;
import java.util.List;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.NameValuePair;
import java.util.ArrayList;
import org.apache.http.client.methods.HttpPost;
import java.util.Map;

public class HttpClientUtil
{
    public static String post(final String url, final Map<String, String> params) {
        CloseableHttpClient httpclient = null;
        try {
            final HttpPost httpRequest = new HttpPost(url);
            final List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            for (final String key : params.keySet()) {
                nameValuePairs.add((NameValuePair)new BasicNameValuePair(key, (String)params.get(key)));
            }
            HttpEntity httpentity = null;
            httpentity = (HttpEntity)new UrlEncodedFormEntity((List)nameValuePairs, "UTF-8");
            final RequestConfig config = RequestConfig.custom().setSocketTimeout(50000).setConnectTimeout(50000).build();
            httpRequest.setConfig(config);
            httpRequest.setEntity(httpentity);
            httpclient = HttpClients.createDefault();
            final HttpResponse httpResponse = (HttpResponse)httpclient.execute((HttpUriRequest)httpRequest);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                return EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
            }
        }
        catch (Exception e2) {
            return null;
        }
        finally {
            try {
                httpclient.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            httpclient.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static String httpsPostJSON(final String url, final JSONObject json) throws Exception {
        final HttpPost httpRequest = new HttpPost(url);
        httpRequest.addHeader("X-tenantId", "single");
        final StringEntity se = new StringEntity(json.toString(), "UTF-8");
        se.setContentType("application/json");
        httpRequest.setEntity((HttpEntity)se);
        final CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            final HttpResponse httpResponse = (HttpResponse)httpclient.execute((HttpUriRequest)httpRequest);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                return EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
            }
        }
        catch (Exception e2) {
            return null;
        }
        finally {
            try {
                httpclient.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            httpclient.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static String postJSON(final String url, final JSONObject json) throws Exception {
        final HttpPost httpRequest = new HttpPost(url);
        httpRequest.addHeader("X-tenantId", "single");
        final StringEntity se = new StringEntity(json.toString(), "UTF-8");
        se.setContentType("application/json");
        httpRequest.setEntity((HttpEntity)se);
        final CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            final HttpResponse httpResponse = (HttpResponse)httpclient.execute((HttpUriRequest)httpRequest);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                return EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        finally {
            try {
                httpclient.close();
            }
            catch (IOException e2) {
                e2.printStackTrace();
            }
        }
        try {
            httpclient.close();
        }
        catch (IOException e2) {
            e2.printStackTrace();
        }
        return null;
    }
    
    public static String get(final String url, final Map<String, String> params) throws Exception {
        final List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        for (final String key : params.keySet()) {
            nameValuePairs.add((NameValuePair)new BasicNameValuePair(key, (String)params.get(key)));
        }
        final String param = URLEncodedUtils.format((List)nameValuePairs, "UTF-8");
        final HttpGet httpRequest = new HttpGet(String.valueOf(url) + "?" + param);
        System.out.println(String.valueOf(url) + "?" + param);
        final CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            final HttpResponse httpResponse = (HttpResponse)httpclient.execute((HttpUriRequest)httpRequest);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                return EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
            }
        }
        catch (Exception e2) {
            return null;
        }
        finally {
            try {
                httpclient.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            httpclient.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static String omsPostJSON(final String url, final JSONObject json) throws Exception {
        final HttpPost httpRequest = new HttpPost(url);
        final StringEntity se = new StringEntity(json.toString(), "UTF-8");
        se.setContentType("application/json");
        httpRequest.setEntity((HttpEntity)se);
        httpRequest.setHeader("Content-Type", "application/json");
        httpRequest.setHeader("Accept", "application/json");
        httpRequest.setHeader("X-tenantId", "single");
        final CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            final HttpResponse httpResponse = (HttpResponse)httpclient.execute((HttpUriRequest)httpRequest);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                return EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
            }
        }
        catch (Exception e2) {
            return null;
        }
        finally {
            try {
                httpclient.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            httpclient.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static String postForm(final String url, final JSONObject json) throws Exception {
        final HttpPost httpRequest = new HttpPost(url);
        final StringEntity se = new StringEntity(json.toString(), "UTF-8");
        se.setContentType("application/x-www-form-urlencoded");
        httpRequest.setEntity((HttpEntity)se);
        final CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            final HttpResponse httpResponse = (HttpResponse)httpclient.execute((HttpUriRequest)httpRequest);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                return EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
            }
        }
        catch (Exception e2) {
            return null;
        }
        finally {
            try {
                httpclient.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            httpclient.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static String sendHttpPostRequest(final String reqURL, final String data) {
        final CloseableHttpClient httpclient = HttpClients.createDefault();
        String respStr = "";
        try {
            final HttpPost httppost = new HttpPost(reqURL);
            final StringEntity strEntity = new StringEntity(data, "UTF-8");
            strEntity.setContentType("application/x-www-form-urlencoded");
            httppost.setEntity((HttpEntity)strEntity);
            final RequestConfig config = RequestConfig.custom().setSocketTimeout(10000).setConnectTimeout(10000).build();
            httppost.setConfig(config);
            final HttpResponse response = (HttpResponse)httpclient.execute((HttpUriRequest)httppost);
            final int status = response.getStatusLine().getStatusCode();
            if (status == 200) {
                final HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    respStr = EntityUtils.toString(resEntity);
                    EntityUtils.consume(resEntity);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        finally {
            try {
                httpclient.close();
            }
            catch (IOException e2) {
                e2.printStackTrace();
            }
        }
        try {
            httpclient.close();
        }
        catch (IOException e2) {
            e2.printStackTrace();
        }
        return respStr;
    }
}
