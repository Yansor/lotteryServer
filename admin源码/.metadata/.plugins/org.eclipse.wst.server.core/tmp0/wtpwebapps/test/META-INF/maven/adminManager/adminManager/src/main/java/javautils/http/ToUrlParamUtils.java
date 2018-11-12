package javautils.http;

import org.apache.commons.lang.StringUtils;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Map;

public class ToUrlParamUtils
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
    
    public static String toUrlParamWithoutEmpty(final Map<String, String> params, final String separator) {
        if (params == null || params.isEmpty()) {
            return "";
        }
        final StringBuffer url = new StringBuffer();
        final Iterator<Entry<String, String>> it = params.entrySet().iterator();
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
    
    private static boolean isEmpty(final String value) {
        return value == null || "".equals(value);
    }
}
