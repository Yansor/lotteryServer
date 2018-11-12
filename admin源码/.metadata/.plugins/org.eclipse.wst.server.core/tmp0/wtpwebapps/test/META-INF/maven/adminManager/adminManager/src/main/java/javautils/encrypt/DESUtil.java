package javautils.encrypt;

import javax.crypto.Cipher;
import org.apache.commons.codec.binary.Base64;
import java.security.spec.KeySpec;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.SecretKeyFactory;
import java.security.Key;

public class DESUtil
{
    private DESUtil() {
    }
    
    public static DESUtil getInstance() {
        return new DESUtil();
    }
    
    private Key getKey(final String strKey) {
        try {
            final SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            final DESKeySpec keySpec = new DESKeySpec(strKey.getBytes());
            keyFactory.generateSecret(keySpec);
            return keyFactory.generateSecret(keySpec);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public String encryptStr(final String strMing, final String keyStr) {
        try {
            final byte[] byteMing = strMing.getBytes();
            final byte[] byteMi = this.encryptByte(byteMing, this.getKey(keyStr));
            return new String(Base64.encodeBase64(byteMi));
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public String decryptStr(final String strMi, final String keyStr) {
        try {
            final byte[] byteMi = Base64.decodeBase64(strMi.getBytes());
            final byte[] byteMing = this.decryptByte(byteMi, this.getKey(keyStr));
            final String strMing = new String(byteMing);
            return strMing;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    private byte[] encryptByte(final byte[] byteS, final Key key) {
        try {
            final Cipher cipher = Cipher.getInstance("DES");
            cipher.init(1, key);
            return cipher.doFinal(byteS);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    private byte[] decryptByte(final byte[] byteD, final Key key) {
        try {
            final Cipher cipher = Cipher.getInstance("DES");
            cipher.init(2, key);
            return cipher.doFinal(byteD);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static void main(final String[] args) {
        try {
            final String str = "2564456666aasdfasdf";
            final String key = "asd$65699))*i-23";
            String encrypt = getInstance().encryptStr(str, key);
            System.out.println(encrypt);
            encrypt = URLEncodeUtil.encode(encrypt, "UTF-8");
            System.out.println(encrypt);
            encrypt = URLEncodeUtil.decode(encrypt, "UTF-8");
            System.out.println(encrypt);
            final String decrypt = getInstance().decryptStr(encrypt, key);
            System.out.println(decrypt);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
