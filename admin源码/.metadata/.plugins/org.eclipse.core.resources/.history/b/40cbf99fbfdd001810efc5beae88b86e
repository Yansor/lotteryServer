package lottery.domains.content.payment.zs.utils;

import java.io.InputStream;
import java.util.Iterator;
import java.io.IOException;
import org.apache.commons.httpclient.HttpException;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.HttpClient;
import java.util.Map.Entry;
import java.util.Map;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class HttpUtilKeyVal
{
    private static Logger log;
    
    static {
        HttpUtilKeyVal.log = LoggerFactory.getLogger((Class)HttpUtilKeyVal.class);
    }
    
    private HttpUtilKeyVal() {
    }
    
    public static HttpUtilKeyVal getInstance() {
        return new HttpUtilKeyVal();
    }
    
    public static String doPost(final String url, final Map<String, String> params) {
        final StringBuffer sb = new StringBuffer();
        if (params != null) {
            boolean isFirst = false;
            for (final Entry<String, String> e : params.entrySet()) {
                if (isFirst) {
                    sb.append("&");
                }
                sb.append(e.getKey());
                sb.append("=");
                sb.append(e.getValue());
                if (!isFirst) {
                    isFirst = true;
                }
            }
        }
        final String reciveStr = null;
        PostMethod postMethod = null;
        try {
            final HttpClient httpClient = new HttpClient();
            postMethod = new PostMethod(url);
            httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(80000);
            httpClient.getHttpConnectionManager().getParams().setSoTimeout(80000);
            postMethod.setRequestHeader("Connection", "close");
            postMethod.addRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
            postMethod.getParams().setContentCharset("utf-8");
            postMethod.getParams().setParameter("http.protocol.content-charset", (Object)"UTF-8");
            NameValuePair[] dataList = null;
            if (params != null) {
                dataList = new NameValuePair[params.keySet().size()];
                int i = 0;
                for (final Entry<String, String> e2 : params.entrySet()) {
                    dataList[i] = new NameValuePair((String)e2.getKey(), (String)e2.getValue());
                    ++i;
                }
                postMethod.setRequestBody(dataList);
            }
            else {
                postMethod.setRequestBody("");
            }
            httpClient.executeMethod((HttpMethod)postMethod);
            final InputStream resStream = postMethod.getResponseBodyAsStream();
            final BufferedReader br = new BufferedReader(new InputStreamReader(resStream));
            String retStr = "";
            String tempbf;
            while ((tempbf = br.readLine()) != null) {
                retStr = String.valueOf(retStr) + tempbf;
            }
            return retStr;
        }
        catch (HttpException e3) {
            e3.printStackTrace();
        }
        catch (IOException e4) {
            e4.printStackTrace();
        }
        finally {
            if (postMethod != null) {
                postMethod.releaseConnection();
            }
        }
        return reciveStr;
    }
}
