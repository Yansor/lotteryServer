package lottery.domains.content.payment.lepay.utils;

import java.security.MessageDigest;
import org.apache.commons.codec.binary.Hex;

public class DigestCoder
{
    public static final String KEY_SHA = "SHA";
    public static final String KEY_MD5 = "MD5";
    public static final String KEY_SHA256 = "SHA-256";
    public static final String KEY_SHA384 = "SHA-384";
    public static final String KEY_SHA512 = "SHA-512";
    
    public static byte[] encodeMD5(final byte[] data) throws Exception {
        return encode(data, "MD5");
    }
    
    public static String encodeMD5Hex(final byte[] data) throws Exception {
        return Hex.encodeHexString(encodeMD5(data));
    }
    
    public static byte[] encodeWithSHA(final byte[] data) throws Exception {
        return encode(data, "SHA");
    }
    
    public static String encodeSHAHex(final byte[] data) throws Exception {
        return Hex.encodeHexString(encodeWithSHA(data));
    }
    
    public static byte[] encodeWithSHA512(final byte[] data) throws Exception {
        return encode(data, "SHA-512");
    }
    
    public static String encodeSHA512Hex(final byte[] data) throws Exception {
        return Hex.encodeHexString(encodeWithSHA512(data));
    }
    
    public static byte[] encode(final byte[] data, final String algorithm) throws Exception {
        final MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
        messageDigest.update(data);
        return messageDigest.digest();
    }
    
    public static String encodeHex(final byte[] data, final String algorithm) throws Exception {
        return Hex.encodeHexString(encode(data, algorithm));
    }
}
