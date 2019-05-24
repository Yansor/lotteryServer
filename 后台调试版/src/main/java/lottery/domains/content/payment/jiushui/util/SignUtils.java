package lottery.domains.content.payment.jiushui.util;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class SignUtils
{
  public static final String SIGN_ALGORITHMS = "SHA1WithRSA";
  
  public static String Signaturer(String content, String privateKey)
  {
    try
    {
      PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64Utils.decode(privateKey));
      KeyFactory keyf = KeyFactory.getInstance("RSA");
      PrivateKey priKey = keyf.generatePrivate(priPKCS8);
      Signature signature = Signature.getInstance("SHA1WithRSA");
      signature.initSign(priKey);
      signature.update(content.getBytes("UTF-8"));
      
      byte[] signed = signature.sign();
      return Base64Utils.encode(signed);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return null;
  }
  
  public static boolean validataSign(String content, String sign, String publicKey)
  {
    try
    {
      KeyFactory keyFactory = KeyFactory.getInstance("RSA");
      byte[] encodedKey = Base64Utils.decode(publicKey);
      PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
      Signature signature = 
        Signature.getInstance("SHA1WithRSA");
      signature.initVerify(pubKey);
      signature.update(content.getBytes("UTF-8"));
      return signature.verify(Base64Utils.decode(sign));
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return false;
  }
}
