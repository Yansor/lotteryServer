package lottery.domains.content.payment.utils;

import java.util.HashMap;
import org.apache.commons.lang.StringUtils;
import java.util.TreeMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Map;

public class UrlParamUtils
{
    private static final String EMPTY = "";
    private static final String EQUALS = "=";
    private static final String DEFAULT_SEPARATOR = "&";
    
    public static String toUrlParam(final Map<String, String> params) {
        return toUrlParam(params, "&", true);
    }
    
    public static String toUrlParam(final Map<String, String> params, final String separator) {
        return toUrlParam(params, separator, true);
    }
    
    public static String toUrlParam(final Map<String, String> params, final String separator, final boolean ignoreEmpty) {
        if (params == null || params.isEmpty()) {
            return "";
        }
        final StringBuffer url = new StringBuffer();
        final Iterator<Entry<String, String>> it = params.entrySet().iterator();
        while (it.hasNext()) {
            final Entry<String, String> entry = it.next();
            final String value = entry.getValue();
            final boolean valueIsEmpty = isEmpty(value);
            if (ignoreEmpty && valueIsEmpty) {
                continue;
            }
            url.append(entry.getKey()).append("=").append(value);
            if (!it.hasNext()) {
                continue;
            }
            url.append(separator);
        }
        return url.toString();
    }
    
    public String buildSignStr(final Map<String, Object> params) {
        final StringBuilder sb = new StringBuilder();
        final Map<String, Object> sortParams = new TreeMap<String, Object>(params);
        for (final Entry<String, Object> entry : sortParams.entrySet()) {
            if (sb.length() != 0) {
                sb.append("&");
            }
            sb.append(entry.getKey()).append("=").append(entry.getValue());
        }
        return sb.toString();
    }
    
    public static String toUrlParamWithoutEmpty(final Map<String, String> params, final String separator, final boolean bool) {
        if (params == null || params.isEmpty()) {
            return "";
        }
        Map<String, String> sortParams = null;
        final StringBuffer url = new StringBuffer();
        if (bool) {
            sortParams = new TreeMap<String, String>(params);
        }
        else {
            sortParams = params;
        }
        final Iterator<Entry<String, String>> it = sortParams.entrySet().iterator();
        while (it.hasNext()) {
            final Entry<String, String> entry = it.next();
            final String value = entry.getValue();
            if (StringUtils.isEmpty(value)) {
                continue;
            }
            url.append(entry.getKey()).append("=").append(value);
            if (!it.hasNext()) {
                continue;
            }
            url.append(separator);
        }
        String urlStr = url.toString();
        if (urlStr.endsWith(separator)) {
            urlStr = urlStr.substring(0, urlStr.length() - separator.length());
        }
        return urlStr;
    }
    
    public static Map<String, String> fromUrlParam(final String url) {
        final Map<String, String> paramsMap = new HashMap<String, String>();
        final String[] params = url.split("&");
        String[] array;
        for (int length = (array = params).length, i = 0; i < length; ++i) {
            final String param = array[i];
            final String[] values = param.split("=");
            if (values != null) {
                final String key = (values.length > 0) ? values[0] : null;
                final String value = (values.length > 1) ? values[1] : null;
                if (key != null) {
                    paramsMap.put(key, value);
                }
            }
        }
        return paramsMap;
    }
    
    private static boolean isEmpty(final String value) {
        return value == null || "".equals(value);
    }
}
