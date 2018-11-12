package lottery.domains.content.payment.RX.utils;

import java.security.PublicKey;
import java.security.PrivateKey;
import java.security.Signature;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.security.InvalidKeyException;
import javax.crypto.NoSuchPaddingException;
import java.io.ByteArrayOutputStream;
import java.security.Key;
import javax.crypto.Cipher;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.KeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.util.HashMap;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.KeyPairGenerator;
import java.util.Map;

public class RSAEncrypt
{
    private static final int MAX_ENCRYPT_BLOCK = 117;
    private static final int MAX_DECRYPT_BLOCK = 256;
    
    public static Map<String, Object> initKey() throws Exception {
        final KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        final KeyPair keyPair = keyPairGenerator.generateKeyPair();
        final RSAPublicKey publicKey = (RSAPublicKey)keyPair.getPublic();
        final RSAPrivateKey privateKey = (RSAPrivateKey)keyPair.getPrivate();
        final Map keyMap = new HashMap();
        keyMap.put("RSAPublicKey", publicKey);
        keyMap.put("RSAPrivateKey", privateKey);
        return (Map<String, Object>)keyMap;
    }
    
    public static RSAPublicKey loadPublicKeyByStr(final String publicKeyStr) throws Exception {
        try {
            final byte[] buffer = Base64Utils.decode(publicKeyStr);
            final KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            final X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            return (RSAPublicKey)keyFactory.generatePublic(keySpec);
        }
        catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法");
        }
        catch (InvalidKeySpecException e2) {
            throw new Exception("公钥非法");
        }
        catch (NullPointerException e3) {
            throw new Exception("公钥数据为空");
        }
    }
    
    public static RSAPrivateKey loadPrivateKeyByStr(final String privateKeyStr) throws Exception {
        try {
            final byte[] buffer = Base64Utils.decode(privateKeyStr);
            final PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
            final KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return (RSAPrivateKey)keyFactory.generatePrivate(keySpec);
        }
        catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法");
        }
        catch (InvalidKeySpecException e2) {
            throw new Exception("私钥非法");
        }
        catch (NullPointerException e3) {
            throw new Exception("私钥数据为空");
        }
    }
    
    public static byte[] encrypt(final RSAPublicKey publicKey, final byte[] plainTextData) throws Exception {
        if (publicKey == null) {
            throw new Exception("加密公钥为空, 请设置");
        }
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(1, publicKey);
            final int inputLen = plainTextData.length;
            final ByteArrayOutputStream out = new ByteArrayOutputStream();
            for (int offSet = 0, i = 0; inputLen - offSet > 0; offSet = ++i * 117) {
                byte[] cache;
                if (inputLen - offSet > 117) {
                    cache = cipher.doFinal(plainTextData, offSet, 117);
                }
                else {
                    cache = cipher.doFinal(plainTextData, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
            }
            final byte[] encryptedData = out.toByteArray();
            out.close();
            return encryptedData;
        }
        catch (NoSuchAlgorithmException e2) {
            throw new Exception("无此加密算法");
        }
        catch (NoSuchPaddingException e) {
            e.printStackTrace();
            return null;
        }
        catch (InvalidKeyException e3) {
            throw new Exception("加密公钥非法,请检查");
        }
        catch (IllegalBlockSizeException e4) {
            throw new Exception("明文长度非法");
        }
        catch (BadPaddingException e5) {
            throw new Exception("明文数据已损坏");
        }
    }
    
    public static byte[] encrypt(final RSAPrivateKey rsaPrivateKey, final byte[] plainTextData) throws Exception {
        try {
            final PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(rsaPrivateKey.getEncoded());
            final KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            final PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
            final Signature signature = Signature.getInstance("MD5withRSA");
            signature.initSign(privateKey);
            signature.update(plainTextData);
            final byte[] result = signature.sign();
            return result;
        }
        catch (Exception ex) {
            return null;
        }
    }
    
    public static byte[] decrypt(final RSAPrivateKey privateKey, final byte[] cipherData) throws Exception {
        if (privateKey == null) {
            throw new Exception("解密私钥为空, 请设置");
        }
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(2, privateKey);
            final int inputLen = cipherData.length;
            final ByteArrayOutputStream out = new ByteArrayOutputStream();
            for (int offSet = 0, i = 0; inputLen - offSet > 0; offSet = ++i * 256) {
                byte[] cache;
                if (inputLen - offSet > 256) {
                    cache = cipher.doFinal(cipherData, offSet, 256);
                }
                else {
                    cache = cipher.doFinal(cipherData, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
            }
            final byte[] decryptedData = out.toByteArray();
            out.close();
            return decryptedData;
        }
        catch (NoSuchAlgorithmException e2) {
            throw new Exception("无此解密算法");
        }
        catch (NoSuchPaddingException e) {
            e.printStackTrace();
            return null;
        }
        catch (InvalidKeyException e3) {
            throw new Exception("解密私钥非法,请检查");
        }
        catch (IllegalBlockSizeException e4) {
            throw new Exception("密文长度非法");
        }
        catch (BadPaddingException e5) {
            throw new Exception("密文数据已损坏");
        }
    }
    
    public static byte[] decrypt(final RSAPublicKey publicKey, final byte[] cipherData) throws Exception {
        if (publicKey == null) {
            throw new Exception("解密公钥为空, 请设置");
        }
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(2, publicKey);
            final int inputLen = cipherData.length;
            final ByteArrayOutputStream out = new ByteArrayOutputStream();
            for (int offSet = 0, i = 0; inputLen - offSet > 0; offSet = ++i * 256) {
                byte[] cache;
                if (inputLen - offSet > 256) {
                    cache = cipher.doFinal(cipherData, offSet, 256);
                }
                else {
                    cache = cipher.doFinal(cipherData, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
            }
            final byte[] decryptedData = out.toByteArray();
            out.close();
            return decryptedData;
        }
        catch (NoSuchAlgorithmException e2) {
            throw new Exception("无此解密算法");
        }
        catch (NoSuchPaddingException e) {
            e.printStackTrace();
            return null;
        }
        catch (InvalidKeyException e3) {
            throw new Exception("解密公钥非法,请检查");
        }
        catch (IllegalBlockSizeException e4) {
            throw new Exception("密文长度非法");
        }
        catch (BadPaddingException e5) {
            throw new Exception("密文数据已损坏");
        }
    }
    
    public static boolean publicsign(final String src, final byte[] sign, final RSAPublicKey rsaPublicKey) throws Exception {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            Signature signature = Signature.getInstance("MD5withRSA");
            final X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(rsaPublicKey.getEncoded());
            keyFactory = KeyFactory.getInstance("RSA");
            final PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
            signature = Signature.getInstance("MD5withRSA");
            signature.initVerify(publicKey);
            signature.update(src.getBytes());
            final boolean bool = signature.verify(sign);
            return bool;
        }
        catch (Exception ex) {
            return false;
        }
    }
}
