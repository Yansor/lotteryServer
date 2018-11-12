package lottery.domains.content.payment.lepay.utils;

import org.apache.commons.codec.binary.Hex;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.net.URLEncoder;
import org.apache.commons.lang.StringUtils;
import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;
import java.io.UnsupportedEncodingException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

public class EncryptionUtil extends DigestUtils
{
    private static String encodingCharset;
    private static final Integer PHONE_ENCRYPT_KEY;
    
    static {
        EncryptionUtil.encodingCharset = "UTF-8";
        PHONE_ENCRYPT_KEY = 175082;
    }
    
    private static String bytesToHex(final byte[] b) {
        final char[] hexDigit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        final StringBuffer buf = new StringBuffer();
        for (int j = 0; j < b.length; ++j) {
            buf.append(hexDigit[b[j] >> 4 & 0xF]);
            buf.append(hexDigit[b[j] & 0xF]);
        }
        return buf.toString();
    }
    
    public static String decryptBASE64pt(final String key) {
        return Base64.encodeBase64URLSafeString(key.getBytes());
    }
    
    public static byte[] decryptBASE64(final String key) throws Exception {
        return Base64.decodeBase64(key.getBytes());
    }
    
    public static String digest(String aValue) {
        aValue = aValue.trim();
        byte[] value;
        try {
            value = aValue.getBytes(EncryptionUtil.encodingCharset);
        }
        catch (UnsupportedEncodingException e2) {
            value = aValue.getBytes();
        }
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA");
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
        return toHex(md.digest(value));
    }
    
    public static String encrypt(final String originalString) {
        return encryptThis(originalString).toLowerCase();
    }
    
    public static String encryptPassword(final String pwd) {
        return StringUtils.lowerCase(encrypt(pwd));
    }
    
    public static void main(final String[] args) {
        try {
            System.out.println(URLEncoder.encode(encryptBASE64("pboleba14".getBytes())));
            System.out.println(decryptBASE64("cGJvbGViYTE0"));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static String encryptBASE64(final byte[] key) throws Exception {
        return new String(Base64.encodeBase64(key));
    }
    
    public static String encryptThis(final String originalString) {
        return DigestUtils.md5Hex(originalString);
    }
    
    public static String getHmac(final String[] args, final String key) {
        if (args == null || args.length == 0) {
            return null;
        }
        final StringBuffer str = new StringBuffer();
        for (int i = 0; i < args.length; ++i) {
            str.append(args[i]);
        }
        return hmacEncrypt(str.toString(), key);
    }
    
    public static String hmacEncrypt(final String aValue, final String aKey) {
        final byte[] k_ipad = new byte[64];
        final byte[] k_opad = new byte[64];
        byte[] keyb;
        byte[] value;
        try {
            keyb = aKey.getBytes(EncryptionUtil.encodingCharset);
            value = aValue.getBytes(EncryptionUtil.encodingCharset);
        }
        catch (UnsupportedEncodingException e) {
            keyb = aKey.getBytes();
            value = aValue.getBytes();
        }
        Arrays.fill(k_ipad, keyb.length, 64, (byte)54);
        Arrays.fill(k_opad, keyb.length, 64, (byte)92);
        for (int i = 0; i < keyb.length; ++i) {
            k_ipad[i] = (byte)(keyb[i] ^ 0x36);
            k_opad[i] = (byte)(keyb[i] ^ 0x5C);
        }
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        }
        catch (NoSuchAlgorithmException e2) {
            return null;
        }
        md.update(k_ipad);
        md.update(value);
        byte[] dg = md.digest();
        md.reset();
        md.update(k_opad);
        md.update(dg, 0, 16);
        dg = md.digest();
        return toHex(dg);
    }
    
    public static String md5Encrypt(final String originalString) {
        return encryptThis(originalString);
    }
    
    public static String toHex(final byte[] input) {
        if (input == null) {
            return null;
        }
        final StringBuffer output = new StringBuffer(input.length * 2);
        for (int i = 0; i < input.length; ++i) {
            final int current = input[i] & 0xFF;
            if (current < 16) {
                output.append("0");
            }
            output.append(Integer.toString(current, 16));
        }
        return output.toString();
    }
    
    public static String generateMD5(final String plainText) {
        try {
            final MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(plainText.getBytes(Charset.forName("UTF8")));
            final byte[] resultByte = messageDigest.digest();
            return new String(Hex.encodeHex(resultByte));
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
