package lottery.domains.content.payment.lepay.utils;

import java.security.NoSuchAlgorithmException;
import java.io.UnsupportedEncodingException;
import org.apache.commons.codec.binary.Hex;
import java.security.MessageDigest;

public class MD5Encoder
{
    public static String md5Encode(final String content) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        final byte[] bytes = content.getBytes("utf-8");
        final MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.update(bytes);
        return Hex.encodeHexString(messageDigest.digest());
    }
}
