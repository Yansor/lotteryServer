package lottery.domains.content.payment.mkt;

import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;
import java.io.UnsupportedEncodingException;

public class MD5Encoder
{
    public static final String DEFAULT_CHARSET = "UTF-8";
    private static final char[] hexadecimal;
    
    static {
        hexadecimal = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
    }
    
    private MD5Encoder() {
    }
    
    public static String encode(final String s) {
        return encode(s, "UTF-8");
    }
    
    public static String encode(final String s, final String charset) {
        if (s == null) {
            return null;
        }
        byte[] strTemp = null;
        try {
            strTemp = s.getBytes(charset);
        }
        catch (UnsupportedEncodingException e) {
            System.out.println(e);
            return null;
        }
        MessageDigest mdTemp = null;
        try {
            mdTemp = MessageDigest.getInstance("MD5");
        }
        catch (NoSuchAlgorithmException e2) {
            System.out.println(e2);
            return null;
        }
        mdTemp.update(strTemp);
        final byte[] binaryData = mdTemp.digest();
        if (binaryData.length != 16) {
            return null;
        }
        final char[] buffer = new char[32];
        for (int i = 0; i < 16; ++i) {
            final int low = binaryData[i] & 0xF;
            final int high = (binaryData[i] & 0xF0) >> 4;
            buffer[i * 2] = MD5Encoder.hexadecimal[high];
            buffer[i * 2 + 1] = MD5Encoder.hexadecimal[low];
        }
        return new String(buffer);
    }
}
