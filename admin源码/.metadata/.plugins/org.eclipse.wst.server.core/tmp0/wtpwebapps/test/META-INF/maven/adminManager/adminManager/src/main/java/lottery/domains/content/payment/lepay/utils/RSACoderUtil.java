package lottery.domains.content.payment.lepay.utils;

import java.net.URLDecoder;
import java.security.KeyPair;
import java.util.HashMap;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.KeyPairGenerator;
import java.util.Map;
import java.security.PublicKey;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;
import java.security.Key;
import javax.crypto.Cipher;
import java.security.spec.KeySpec;
import java.security.KeyFactory;
import java.security.spec.PKCS8EncodedKeySpec;
import java.net.URLEncoder;

public class RSACoderUtil extends CoderUtil
{
    public static final String KEY_ALGORTHM = "RSA";
    public static final String SIGNATURE_ALGORITHM = "SHA1withRSA";
    public static final int KEY_SIZE = 1024;
    public static final String PUBLIC_KEY = "RSAPublicKey";
    public static final String PRIVATE_KEY = "RSAPrivateKey";
    private static final int MAX_ENCRYPT_BLOCK = 117;
    private static final int MAX_DECRYPT_BLOCK = 128;
    
    public static String getParamsWithDecodeByPublicKey(final String paramsString, final String charset, final String fcsPublicKey) throws Exception {
        final byte[] paramByteArr = encryptByPublicKey(paramsString.getBytes(charset), fcsPublicKey);
        return URLEncoder.encode(CoderUtil.encryptBASE64(paramByteArr), charset);
    }
    
    public static String getParamsWithDecodeByPrivateKey(final String paramsString, final String charset, final String privateKey) throws Exception {
        final byte[] shaParams = DigestCoder.encodeWithSHA(paramsString.getBytes(charset));
        final String signParams = sign(shaParams, privateKey);
        return URLEncoder.encode(signParams, charset);
    }
    
    public static byte[] encryptByPrivateKey(final byte[] data, final String key) throws Exception {
        final byte[] keyBytes = CoderUtil.decryptBASE64(key);
        final PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
        final KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        final Key privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        final Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(1, privateKey);
        byte[] dataReturn = null;
        for (int i = 0; i < data.length; i += 117) {
            final byte[] doFinal = cipher.doFinal(subarray(data, i, i + 117));
            dataReturn = addAll(dataReturn, doFinal);
        }
        return dataReturn;
    }
    
    public static byte[] decryptByPrivateKey(final byte[] data, final String key) throws Exception {
        final byte[] keyBytes = CoderUtil.decryptBASE64(key);
        final PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
        final KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        final Key privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        final Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(2, privateKey);
        byte[] dataReturn = null;
        for (int i = 0; i < data.length; i += 128) {
            final byte[] doFinal = cipher.doFinal(subarray(data, i, i + 128));
            dataReturn = addAll(dataReturn, doFinal);
        }
        return dataReturn;
    }
    
    public static byte[] encryptByPublicKey(final byte[] data, final String key) throws Exception {
        final byte[] keyBytes = CoderUtil.decryptBASE64(key);
        final X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
        final KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        final Key publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
        final Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(1, publicKey);
        byte[] dataReturn = null;
        for (int i = 0; i < data.length; i += 117) {
            final byte[] doFinal = cipher.doFinal(subarray(data, i, i + 117));
            dataReturn = addAll(dataReturn, doFinal);
        }
        return dataReturn;
    }
    
    public static byte[] decryptByPublicKey(final byte[] data, final String key) throws Exception {
        final byte[] keyBytes = CoderUtil.decryptBASE64(key);
        final X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
        final KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        final Key publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
        final Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(2, publicKey);
        byte[] dataReturn = null;
        for (int i = 0; i < data.length; i += 128) {
            final byte[] doFinal = cipher.doFinal(subarray(data, i, i + 128));
            dataReturn = addAll(dataReturn, doFinal);
        }
        return dataReturn;
    }
    
    public static String sign(final byte[] data, final String privateKey) throws Exception {
        final byte[] keyBytes = CoderUtil.decryptBASE64(privateKey);
        final PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
        final KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        final PrivateKey privateKey2 = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        final Signature signature = Signature.getInstance("SHA1withRSA");
        signature.initSign(privateKey2);
        signature.update(data);
        return CoderUtil.encryptBASE64(signature.sign());
    }
    
    public static boolean verify(final byte[] data, final String publicKey, final String sign) throws Exception {
        final byte[] keyBytes = CoderUtil.decryptBASE64(publicKey);
        final X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
        final KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        final PublicKey publicKey2 = keyFactory.generatePublic(x509EncodedKeySpec);
        final Signature signature = Signature.getInstance("SHA1withRSA");
        signature.initVerify(publicKey2);
        signature.update(data);
        return signature.verify(CoderUtil.decryptBASE64(sign));
    }
    
    public static byte[] subarray(final byte[] array, int startIndexInclusive, int endIndexExclusive) {
        if (array == null) {
            return null;
        }
        if (startIndexInclusive < 0) {
            startIndexInclusive = 0;
        }
        if (endIndexExclusive > array.length) {
            endIndexExclusive = array.length;
        }
        final int newSize = endIndexExclusive - startIndexInclusive;
        if (newSize <= 0) {
            return new byte[0];
        }
        final byte[] subarray = new byte[newSize];
        System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
        return subarray;
    }
    
    public static Map<String, Object> initKey() throws Exception {
        final KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(1024);
        final KeyPair keyPair = keyPairGenerator.generateKeyPair();
        final RSAPublicKey publicKey = (RSAPublicKey)keyPair.getPublic();
        final RSAPrivateKey privateKey = (RSAPrivateKey)keyPair.getPrivate();
        final Map keyMap = new HashMap();
        keyMap.put("RSAPublicKey", publicKey);
        keyMap.put("RSAPrivateKey", privateKey);
        return (Map<String, Object>)keyMap;
    }
    
    public static byte[] addAll(final byte[] array1, final byte[] array2) {
        if (array1 == null) {
            return clone(array2);
        }
        if (array2 == null) {
            return clone(array1);
        }
        final byte[] joinedArray = new byte[array1.length + array2.length];
        System.arraycopy(array1, 0, joinedArray, 0, array1.length);
        System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
        return joinedArray;
    }
    
    public static byte[] clone(final byte[] array) {
        if (array == null) {
            return null;
        }
        return array.clone();
    }
    
    public static String rsaDecrypt(final String encodeData, final String private_key) throws Exception {
        final String URLDecodeData = URLDecoder.decode(encodeData, "UTF-8");
        final byte[] data = CoderUtil.decryptBASE64(URLDecodeData);
        final byte[] keyBytes = CoderUtil.decryptBASE64(private_key);
        final PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
        final KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        final Key privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        final Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(2, privateKey);
        byte[] dataReturn = null;
        for (int i = 0; i < data.length; i += 128) {
            final byte[] doFinal = cipher.doFinal(subarray(data, i, i + 128));
            dataReturn = addAll(dataReturn, doFinal);
        }
        return new String(dataReturn, "UTF-8");
    }
    
    public static boolean verifySign2(final String paramsStr, final String sign, final String fcs_public_key) throws Exception {
        final byte[] data = paramsStr.getBytes("UTF-8");
        final byte[] signData = CoderUtil.decryptBASE64(URLDecoder.decode(sign, "UTF-8"));
        final byte[] keyBytes = CoderUtil.decryptBASE64(fcs_public_key);
        final X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
        final KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        final PublicKey publicKey2 = keyFactory.generatePublic(x509EncodedKeySpec);
        final Signature signature = Signature.getInstance("SHA1withRSA");
        signature.initVerify(publicKey2);
        signature.update(data);
        return signature.verify(signData);
    }
}
