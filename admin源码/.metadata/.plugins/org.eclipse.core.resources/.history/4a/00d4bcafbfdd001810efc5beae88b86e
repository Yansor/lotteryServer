package javautils.encrypt;

import java.util.Arrays;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.Cipher;
import java.security.SecureRandom;
import javax.crypto.KeyGenerator;
import java.security.Key;

public class AESUtil
{
    public static final String ALGORITHM = "AES";
    public static final String TRANSFORMATION = "AES/ECB/PKCS5Padding";
    
    public static Key generateKey(final int keysize) throws Exception {
        final KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(keysize, new SecureRandom());
        final Key key = keyGenerator.generateKey();
        return key;
    }
    
    public static Key generateKey() throws Exception {
        return generateKey(128);
    }
    
    public static Key generateKey(final int keysize, final byte[] seed) throws Exception {
        final KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(keysize, new SecureRandom(seed));
        final Key key = keyGenerator.generateKey();
        return key;
    }
    
    public static Key generateKey(final int keysize, final String password) throws Exception {
        return generateKey(keysize, password.getBytes());
    }
    
    public static Key generateKey(final String password) throws Exception {
        return generateKey(128, password);
    }
    
    public static byte[] encrypt(final byte[] content, final byte[] key) throws Exception {
        final Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(1, new SecretKeySpec(key, "AES"));
        final byte[] output = cipher.doFinal(content);
        return output;
    }
    
    public static byte[] encrypt(final byte[] content, final String password) throws Exception {
        final Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(1, generateKey(password));
        final byte[] output = cipher.doFinal(content);
        return output;
    }
    
    public static byte[] decrypt(final byte[] content, final byte[] key) throws Exception {
        final Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(2, new SecretKeySpec(key, "AES"));
        final byte[] output = cipher.doFinal(content);
        return output;
    }
    
    public static byte[] decrypt(final byte[] content, final String password) throws Exception {
        final Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(2, generateKey(password));
        final byte[] output = cipher.doFinal(content);
        return output;
    }
    
    public static void main(final String[] args) throws Exception {
        System.out.println(Arrays.toString(encrypt("当我们把密钥定为大于128时（即192或256）时".getBytes(), "012345")));
        System.out.println(new String(decrypt(encrypt("当我们把密钥定为大于128时（即192或256）时".getBytes(), "012345"), "012345")));
        System.out.println(Arrays.toString(encrypt("当我们把密钥定为大于128时（即192或256）时".getBytes(), "01234567890123450123456789012345".getBytes())));
        System.out.println(new String(decrypt(encrypt("当我们把密钥定为大于128时（即192或256）时".getBytes(), "01234567890123450123456789012345".getBytes()), "01234567890123450123456789012345".getBytes())));
    }
}
