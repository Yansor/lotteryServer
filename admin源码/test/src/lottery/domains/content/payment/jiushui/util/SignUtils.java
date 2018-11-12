package lottery.domains.content.payment.jiushui.util;

import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.KeySpec;
import java.security.KeyFactory;
import java.security.spec.PKCS8EncodedKeySpec;

public class SignUtils
{
    public static final String SIGN_ALGORITHMS = "SHA1WithRSA";
    
    public static String Signaturer(final String content, final String privateKey) {
        try {
            final PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64Utils.decode(privateKey));
            final KeyFactory keyf = KeyFactory.getInstance("RSA");
            final PrivateKey priKey = keyf.generatePrivate(priPKCS8);
            final Signature signature = Signature.getInstance("SHA1WithRSA");
            signature.initSign(priKey);
            signature.update(content.getBytes("UTF-8"));
            final byte[] signed = signature.sign();
            return Base64Utils.encode(signed);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static boolean validataSign(final String content, final String sign, final String publicKey) {
        try {
            final KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            final byte[] encodedKey = Base64Utils.decode(publicKey);
            final PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
            final Signature signature = Signature.getInstance("SHA1WithRSA");
            signature.initVerify(pubKey);
            signature.update(content.getBytes("UTF-8"));
            final boolean bverify = signature.verify(Base64Utils.decode(sign));
            return bverify;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
