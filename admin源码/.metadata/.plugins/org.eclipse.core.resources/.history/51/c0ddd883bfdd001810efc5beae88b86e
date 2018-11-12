package lottery.domains.content.payment.mkt;

import org.apache.commons.lang.StringUtils;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class URLUtils
{
    public static String encode(final String str, final String charset) {
        try {
            return URLEncoder.encode(str, charset);
        }
        catch (Exception e) {
            System.out.println(e);
            return str;
        }
    }
    
    public static String decode(final String str, final String charset) {
        try {
            return URLDecoder.decode(str, charset);
        }
        catch (Exception e) {
            System.out.println(e);
            return str;
        }
    }
    
    public static void appendParam(final StringBuilder sb, final String name, final String val) {
        appendParam(sb, name, val, true);
    }
    
    public static void appendParam(final StringBuilder sb, final String name, final String val, final String charset) {
        appendParam(sb, name, val, true, charset);
    }
    
    public static void appendParam(final StringBuilder sb, final String name, final String val, final boolean and) {
        appendParam(sb, name, val, and, null);
    }
    
    public static void appendParam(final StringBuilder sb, final String name, String val, final boolean and, final String charset) {
        if (and) {
            sb.append("&");
        }
        else {
            sb.append("?");
        }
        sb.append(name);
        sb.append("=");
        if (val == null) {
            val = "";
        }
        if (StringUtils.isEmpty(charset)) {
            sb.append(val);
        }
        else {
            sb.append(encode(val, charset));
        }
    }
}
