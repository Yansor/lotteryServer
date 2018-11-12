package javautils.encrypt;

import java.net.URLDecoder;
import java.net.URLEncoder;

public class URLEncodeUtil
{
    public static String encode(final String str, final String charset) {
        try {
            return URLEncoder.encode(str, charset);
        }
        catch (Exception e) {
            e.printStackTrace();
            return str;
        }
    }
    
    public static String decode(final String str, final String charset) {
        try {
            return URLDecoder.decode(str, charset);
        }
        catch (Exception e) {
            e.printStackTrace();
            return str;
        }
    }
}
