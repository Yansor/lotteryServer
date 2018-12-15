package javautils.encrypt;

import java.io.PrintStream;
import java.security.Key;
import java.security.SecureRandom;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

public class AESUtil
{
  public static final String ALGORITHM = "AES";
  public static final String TRANSFORMATION = "AES/ECB/PKCS5Padding";
  
  public static Key generateKey(int keysize)
    throws Exception
  {
    KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
    keyGenerator.init(keysize, new SecureRandom());
    Key key = keyGenerator.generateKey();
    return key;
  }
  
  public static Key generateKey()
    throws Exception
  {
    return generateKey(128);
  }
  
  public static Key generateKey(int keysize, byte[] seed)
    throws Exception
  {
    KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
    keyGenerator.init(keysize, new SecureRandom(seed));
    Key key = keyGenerator.generateKey();
    return key;
  }
  
  public static Key generateKey(int keysize, String password)
    throws Exception
  {
    return generateKey(keysize, password.getBytes());
  }
  
  public static Key generateKey(String password)
    throws Exception
  {
    return generateKey(128, password);
  }
  
  public static byte[] encrypt(byte[] content, byte[] key)
    throws Exception
  {
    Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
    cipher.init(1, new SecretKeySpec(key, "AES"));
    byte[] output = cipher.doFinal(content);
    return output;
  }
  
  public static byte[] encrypt(byte[] content, String password)
    throws Exception
  {
    Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
    cipher.init(1, generateKey(password));
    byte[] output = cipher.doFinal(content);
    return output;
  }
  
  public static byte[] decrypt(byte[] content, byte[] key)
    throws Exception
  {
    Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
    cipher.init(2, new SecretKeySpec(key, "AES"));
    byte[] output = cipher.doFinal(content);
    return output;
  }
  
  public static byte[] decrypt(byte[] content, String password)
    throws Exception
  {
    Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
    cipher.init(2, generateKey(password));
    byte[] output = cipher.doFinal(content);
    return output;
  }
  
  public static void main(String[] args)
    throws Exception
  {
    System.out.println(Arrays.toString(encrypt("当我们把密钥定为大于128时（即192或256）时".getBytes(), "012345")));
    System.out.println(new String(decrypt(encrypt("当我们把密钥定为大于128时（即192或256）时".getBytes(), "012345"), "012345")));
    
    System.out.println(Arrays.toString(encrypt("当我们把密钥定为大于128时（即192或256）时".getBytes(), "01234567890123450123456789012345".getBytes())));
    System.out.println(new String(decrypt(encrypt("当我们把密钥定为大于128时（即192或256）时".getBytes(), "01234567890123450123456789012345".getBytes()), "01234567890123450123456789012345".getBytes())));
  }
}
