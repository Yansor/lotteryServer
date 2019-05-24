package lottery.domains.content.payment.RX.utils;

import java.io.ByteArrayOutputStream;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class RSAEncrypt
{
  private static final int MAX_ENCRYPT_BLOCK = 117;
  private static final int MAX_DECRYPT_BLOCK = 256;
  
  public static Map<String, Object> initKey()
    throws Exception
  {
    KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
    keyPairGenerator.initialize(2048);
    KeyPair keyPair = keyPairGenerator.generateKeyPair();
    RSAPublicKey publicKey = (RSAPublicKey)keyPair.getPublic();
    RSAPrivateKey privateKey = (RSAPrivateKey)keyPair.getPrivate();
    Map keyMap = new HashMap();
    keyMap.put("RSAPublicKey", publicKey);
    keyMap.put("RSAPrivateKey", privateKey);
    return keyMap;
  }
  
  public static RSAPublicKey loadPublicKeyByStr(String publicKeyStr)
    throws Exception
  {
    try
    {
      byte[] buffer = Base64Utils.decode(publicKeyStr);
      KeyFactory keyFactory = KeyFactory.getInstance("RSA");
      X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
      return (RSAPublicKey)keyFactory.generatePublic(keySpec);
    }
    catch (NoSuchAlgorithmException e)
    {
      throw new Exception("无此算法");
    }
    catch (InvalidKeySpecException e)
    {
      throw new Exception("公钥非法");
    }
    catch (NullPointerException e)
    {
      throw new Exception("公钥数据为空");
    }
  }
  
  public static RSAPrivateKey loadPrivateKeyByStr(String privateKeyStr)
    throws Exception
  {
    try
    {
      byte[] buffer = Base64Utils.decode(privateKeyStr);
      PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
      KeyFactory keyFactory = KeyFactory.getInstance("RSA");
      return (RSAPrivateKey)keyFactory.generatePrivate(keySpec);
    }
    catch (NoSuchAlgorithmException e)
    {
      throw new Exception("无此算法");
    }
    catch (InvalidKeySpecException e)
    {
      throw new Exception("私钥非法");
    }
    catch (NullPointerException e)
    {
      throw new Exception("私钥数据为空");
    }
  }
  
  public static byte[] encrypt(RSAPublicKey publicKey, byte[] plainTextData)
    throws Exception
  {
    if (publicKey == null) {
      throw new Exception("加密公钥为空, 请设置");
    }
    Cipher cipher = null;
    try
    {
      cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
      
      cipher.init(1, publicKey);
      
      int inputLen = plainTextData.length;
      ByteArrayOutputStream out = new ByteArrayOutputStream();
      int offSet = 0;
      
      int i = 0;
      while (inputLen - offSet > 0)
      {
        byte[] cache;
        if (inputLen - offSet > 117) {
          cache = cipher.doFinal(plainTextData, offSet, 117);
        } else {
          cache = cipher.doFinal(plainTextData, offSet, inputLen - offSet);
        }
        out.write(cache, 0, cache.length);
        i++;
        offSet = i * 117;
      }
      byte[] encryptedData = out.toByteArray();
      out.close();
      return encryptedData;
    }
    catch (NoSuchAlgorithmException e)
    {
      throw new Exception("无此加密算法");
    }
    catch (NoSuchPaddingException e)
    {
      e.printStackTrace();
      return null;
    }
    catch (InvalidKeyException e)
    {
      throw new Exception("加密公钥非法,请检查");
    }
    catch (IllegalBlockSizeException e)
    {
      throw new Exception("明文长度非法");
    }
    catch (BadPaddingException e)
    {
      throw new Exception("明文数据已损坏");
    }
  }
  
  public static byte[] encrypt(RSAPrivateKey rsaPrivateKey, byte[] plainTextData)
    throws Exception
  {
    try
    {
      PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(rsaPrivateKey.getEncoded());
      KeyFactory keyFactory = KeyFactory.getInstance("RSA");
      PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
      Signature signature = Signature.getInstance("MD5withRSA");
      
      signature.initSign(privateKey);
      
      signature.update(plainTextData);
      
      return signature.sign();
    }
    catch (Exception localException) {}
    return null;
  }
  
  public static byte[] decrypt(RSAPrivateKey privateKey, byte[] cipherData)
    throws Exception
  {
    if (privateKey == null) {
      throw new Exception("解密私钥为空, 请设置");
    }
    Cipher cipher = null;
    try
    {
      cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
      
      cipher.init(2, privateKey);
      
      int inputLen = cipherData.length;
      ByteArrayOutputStream out = new ByteArrayOutputStream();
      int offSet = 0;
      
      int i = 0;
      while (inputLen - offSet > 0)
      {
        byte[] cache;
        if (inputLen - offSet > 256) {
          cache = cipher.doFinal(cipherData, offSet, 
            256);
        } else {
          cache = cipher.doFinal(cipherData, offSet, inputLen - 
            offSet);
        }
        out.write(cache, 0, cache.length);
        i++;
        offSet = i * 256;
      }
      byte[] decryptedData = out.toByteArray();
      out.close();
      return decryptedData;
    }
    catch (NoSuchAlgorithmException e)
    {
      throw new Exception("无此解密算法");
    }
    catch (NoSuchPaddingException e)
    {
      e.printStackTrace();
      return null;
    }
    catch (InvalidKeyException e)
    {
      throw new Exception("解密私钥非法,请检查");
    }
    catch (IllegalBlockSizeException e)
    {
      throw new Exception("密文长度非法");
    }
    catch (BadPaddingException e)
    {
      throw new Exception("密文数据已损坏");
    }
  }
  
  public static byte[] decrypt(RSAPublicKey publicKey, byte[] cipherData)
    throws Exception
  {
    if (publicKey == null) {
      throw new Exception("解密公钥为空, 请设置");
    }
    Cipher cipher = null;
    try
    {
      cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
      
      cipher.init(2, publicKey);
      
      int inputLen = cipherData.length;
      ByteArrayOutputStream out = new ByteArrayOutputStream();
      int offSet = 0;
      
      int i = 0;
      while (inputLen - offSet > 0)
      {
        byte[] cache;
        if (inputLen - offSet > 256) {
          cache = cipher.doFinal(cipherData, offSet, 
            256);
        } else {
          cache = cipher.doFinal(cipherData, offSet, inputLen - 
            offSet);
        }
        out.write(cache, 0, cache.length);
        i++;
        offSet = i * 256;
      }
      byte[] decryptedData = out.toByteArray();
      out.close();
      return decryptedData;
    }
    catch (NoSuchAlgorithmException e)
    {
      throw new Exception("无此解密算法");
    }
    catch (NoSuchPaddingException e)
    {
      e.printStackTrace();
      return null;
    }
    catch (InvalidKeyException e)
    {
      throw new Exception("解密公钥非法,请检查");
    }
    catch (IllegalBlockSizeException e)
    {
      throw new Exception("密文长度非法");
    }
    catch (BadPaddingException e)
    {
      throw new Exception("密文数据已损坏");
    }
  }
  
  public static boolean publicsign(String src, byte[] sign, RSAPublicKey rsaPublicKey)
    throws Exception
  {
    try
    {
      KeyFactory keyFactory = KeyFactory.getInstance("RSA");
      Signature signature = Signature.getInstance("MD5withRSA");
      
      X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(rsaPublicKey.getEncoded());
      keyFactory = KeyFactory.getInstance("RSA");
      PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
      signature = Signature.getInstance("MD5withRSA");
      
      signature.initVerify(publicKey);
      
      signature.update(src.getBytes());
      
      return signature.verify(sign);
    }
    catch (Exception localException) {}
    return false;
  }
}
