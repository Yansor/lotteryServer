package lottery.domains.content.payment.tgf.utils;

import java.io.PrintStream;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.cert.X509Certificate;
import org.apache.commons.lang.StringUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class SignUtil
{
  private static PrivateKey privateKey = null;
  private static X509Certificate cert = null;
  private static String signType = "MD5";
  private static final String CHAR_SET = "UTF-8";
  private static String key = "d39190fc1629c2b73c5b503f18bf10a2";
  
  static
  {
    if ("RSA".equals(signType))
    {
      try
      {
        initRAS(Config.PFX_FILE, Config.CERT_FILE, Config.PASSWD);
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
      System.out.println("=================rsa初始化成功");
    }
    else if ("MD5".equals(signType))
    {
      try
      {
        initMD5();
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }
  }
  
  public static void initMD5()
    throws Exception
  {
    key = "";
  }
  
  /* Error */
  public static void initRAS(String pfxFilePath, String certFilePath, String pfxPwd)
    throws Exception
  {
    // Byte code:
    //   0: aload_0
    //   1: invokestatic 89	org/apache/commons/lang/StringUtils:isBlank	(Ljava/lang/String;)Z
    //   4: ifeq +13 -> 17
    //   7: new 55	java/lang/Exception
    //   10: dup
    //   11: ldc 95
    //   13: invokespecial 97	java/lang/Exception:<init>	(Ljava/lang/String;)V
    //   16: athrow
    //   17: aload_1
    //   18: invokestatic 89	org/apache/commons/lang/StringUtils:isBlank	(Ljava/lang/String;)Z
    //   21: ifeq +13 -> 34
    //   24: new 55	java/lang/Exception
    //   27: dup
    //   28: ldc 99
    //   30: invokespecial 97	java/lang/Exception:<init>	(Ljava/lang/String;)V
    //   33: athrow
    //   34: aload_2
    //   35: invokestatic 89	org/apache/commons/lang/StringUtils:isBlank	(Ljava/lang/String;)Z
    //   38: ifeq +13 -> 51
    //   41: new 55	java/lang/Exception
    //   44: dup
    //   45: ldc 101
    //   47: invokespecial 97	java/lang/Exception:<init>	(Ljava/lang/String;)V
    //   50: athrow
    //   51: getstatic 19	lottery/domains/content/payment/tgf/utils/SignUtil:privateKey	Ljava/security/PrivateKey;
    //   54: ifnull +9 -> 63
    //   57: getstatic 21	lottery/domains/content/payment/tgf/utils/SignUtil:cert	Ljava/security/cert/X509Certificate;
    //   60: ifnonnull +248 -> 308
    //   63: aconst_null
    //   64: astore_3
    //   65: ldc 103
    //   67: invokestatic 105	java/security/KeyStore:getInstance	(Ljava/lang/String;)Ljava/security/KeyStore;
    //   70: astore 4
    //   72: new 111	java/io/FileInputStream
    //   75: dup
    //   76: aload_0
    //   77: invokespecial 113	java/io/FileInputStream:<init>	(Ljava/lang/String;)V
    //   80: astore_3
    //   81: aload_3
    //   82: ifnonnull +13 -> 95
    //   85: new 55	java/lang/Exception
    //   88: dup
    //   89: ldc 114
    //   91: invokespecial 97	java/lang/Exception:<init>	(Ljava/lang/String;)V
    //   94: athrow
    //   95: aload_2
    //   96: astore 5
    //   98: aload 4
    //   100: aload_3
    //   101: aload 5
    //   103: invokevirtual 116	java/lang/String:toCharArray	()[C
    //   106: invokevirtual 120	java/security/KeyStore:load	(Ljava/io/InputStream;[C)V
    //   109: ldc 87
    //   111: astore 6
    //   113: aload 4
    //   115: invokevirtual 124	java/security/KeyStore:aliases	()Ljava/util/Enumeration;
    //   118: astore 7
    //   120: goto +15 -> 135
    //   123: aload 7
    //   125: invokeinterface 128 1 0
    //   130: checkcast 34	java/lang/String
    //   133: astore 6
    //   135: aload 7
    //   137: invokeinterface 134 1 0
    //   142: ifne -19 -> 123
    //   145: aload 4
    //   147: aload 6
    //   149: aload 5
    //   151: invokevirtual 116	java/lang/String:toCharArray	()[C
    //   154: invokevirtual 138	java/security/KeyStore:getKey	(Ljava/lang/String;[C)Ljava/security/Key;
    //   157: checkcast 142	java/security/PrivateKey
    //   160: putstatic 19	lottery/domains/content/payment/tgf/utils/SignUtil:privateKey	Ljava/security/PrivateKey;
    //   163: getstatic 59	java/lang/System:out	Ljava/io/PrintStream;
    //   166: new 144	java/lang/StringBuilder
    //   169: dup
    //   170: ldc -110
    //   172: invokespecial 148	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   175: getstatic 19	lottery/domains/content/payment/tgf/utils/SignUtil:privateKey	Ljava/security/PrivateKey;
    //   178: invokevirtual 149	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   181: invokevirtual 153	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   184: invokevirtual 67	java/io/PrintStream:println	(Ljava/lang/String;)V
    //   187: ldc -99
    //   189: invokestatic 159	java/security/cert/CertificateFactory:getInstance	(Ljava/lang/String;)Ljava/security/cert/CertificateFactory;
    //   192: astore 8
    //   194: new 111	java/io/FileInputStream
    //   197: dup
    //   198: aload_1
    //   199: invokespecial 113	java/io/FileInputStream:<init>	(Ljava/lang/String;)V
    //   202: astore_3
    //   203: aload_3
    //   204: ifnonnull +13 -> 217
    //   207: new 55	java/lang/Exception
    //   210: dup
    //   211: ldc 114
    //   213: invokespecial 97	java/lang/Exception:<init>	(Ljava/lang/String;)V
    //   216: athrow
    //   217: aload 8
    //   219: aload_3
    //   220: invokevirtual 164	java/security/cert/CertificateFactory:generateCertificate	(Ljava/io/InputStream;)Ljava/security/cert/Certificate;
    //   223: checkcast 168	java/security/cert/X509Certificate
    //   226: putstatic 21	lottery/domains/content/payment/tgf/utils/SignUtil:cert	Ljava/security/cert/X509Certificate;
    //   229: getstatic 59	java/lang/System:out	Ljava/io/PrintStream;
    //   232: new 144	java/lang/StringBuilder
    //   235: dup
    //   236: ldc -86
    //   238: invokespecial 148	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   241: getstatic 21	lottery/domains/content/payment/tgf/utils/SignUtil:cert	Ljava/security/cert/X509Certificate;
    //   244: invokevirtual 149	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   247: invokevirtual 153	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   250: invokevirtual 67	java/io/PrintStream:println	(Ljava/lang/String;)V
    //   253: aload_3
    //   254: invokevirtual 172	java/io/InputStream:close	()V
    //   257: goto +43 -> 300
    //   260: astore 4
    //   262: new 177	java/lang/RuntimeException
    //   265: dup
    //   266: new 144	java/lang/StringBuilder
    //   269: dup
    //   270: ldc -77
    //   272: invokespecial 148	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   275: aload 4
    //   277: invokevirtual 149	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   280: invokevirtual 153	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   283: invokespecial 181	java/lang/RuntimeException:<init>	(Ljava/lang/String;)V
    //   286: athrow
    //   287: astore 9
    //   289: aload_3
    //   290: ifnull +7 -> 297
    //   293: aload_3
    //   294: invokevirtual 172	java/io/InputStream:close	()V
    //   297: aload 9
    //   299: athrow
    //   300: aload_3
    //   301: ifnull +7 -> 308
    //   304: aload_3
    //   305: invokevirtual 172	java/io/InputStream:close	()V
    //   308: return
    // Line number table:
    //   Java source line #69	-> byte code offset #0
    //   Java source line #70	-> byte code offset #7
    //   Java source line #72	-> byte code offset #17
    //   Java source line #73	-> byte code offset #24
    //   Java source line #75	-> byte code offset #34
    //   Java source line #76	-> byte code offset #41
    //   Java source line #78	-> byte code offset #51
    //   Java source line #79	-> byte code offset #63
    //   Java source line #81	-> byte code offset #65
    //   Java source line #82	-> byte code offset #72
    //   Java source line #83	-> byte code offset #81
    //   Java source line #84	-> byte code offset #85
    //   Java source line #86	-> byte code offset #95
    //   Java source line #87	-> byte code offset #98
    //   Java source line #88	-> byte code offset #109
    //   Java source line #89	-> byte code offset #113
    //   Java source line #90	-> byte code offset #120
    //   Java source line #91	-> byte code offset #123
    //   Java source line #90	-> byte code offset #135
    //   Java source line #93	-> byte code offset #145
    //   Java source line #94	-> byte code offset #163
    //   Java source line #95	-> byte code offset #187
    //   Java source line #96	-> byte code offset #194
    //   Java source line #97	-> byte code offset #203
    //   Java source line #98	-> byte code offset #207
    //   Java source line #100	-> byte code offset #217
    //   Java source line #101	-> byte code offset #229
    //   Java source line #102	-> byte code offset #253
    //   Java source line #103	-> byte code offset #257
    //   Java source line #104	-> byte code offset #262
    //   Java source line #105	-> byte code offset #287
    //   Java source line #106	-> byte code offset #289
    //   Java source line #107	-> byte code offset #293
    //   Java source line #109	-> byte code offset #297
    //   Java source line #106	-> byte code offset #300
    //   Java source line #107	-> byte code offset #304
    //   Java source line #111	-> byte code offset #308
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	309	0	pfxFilePath	String
    //   0	309	1	certFilePath	String
    //   0	309	2	pfxPwd	String
    //   64	241	3	is	java.io.InputStream
    //   70	76	4	ks	java.security.KeyStore
    //   260	16	4	e	Exception
    //   96	54	5	pwd	String
    //   111	37	6	alias	String
    //   118	18	7	e	java.util.Enumeration<String>
    //   192	26	8	cf	java.security.cert.CertificateFactory
    //   287	11	9	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   65	257	260	java/lang/Exception
    //   65	287	287	finally
  }
  
  public static String signData(String sourceData)
    throws Exception
  {
    String signStrintg = "";
    if ("RSA".equals(signType))
    {
      if (privateKey == null)
      {
        System.out.println("====================");
        throw new Exception("签名尚未初始化！");
      }
      if (StringUtils.isBlank(sourceData)) {
        throw new Exception("签名数据为空！");
      }
      Signature sign = Signature.getInstance("MD5withRSA");
      sign.initSign(privateKey);
      sign.update(sourceData.getBytes("utf-8"));
      byte[] signBytes = sign.sign();
      BASE64Encoder encoder = new BASE64Encoder();
      signStrintg = encoder.encode(signBytes);
    }
    else if ("MD5".equals(signType))
    {
      signStrintg = signByMD5(sourceData, key);
    }
    signStrintg.replaceAll("\r", "").replaceAll("\n", "");
    return signStrintg;
  }
  
  public static String signByMD5(String sourceData, String key)
    throws Exception
  {
    String data = sourceData + key;
    MessageDigest md5 = MessageDigest.getInstance("MD5");
    byte[] sign = md5.digest(data.getBytes("UTF-8"));
    
    return Bytes2HexString(sign).toUpperCase();
  }
  
  public static boolean verifyData(String signData, String srcData)
    throws Exception
  {
    if ("RSA".equals(signType))
    {
      if (cert == null) {
        throw new Exception("签名尚未初始化！");
      }
      if (StringUtils.isBlank(signData)) {
        throw new Exception("系统校验时：签名数据为空！");
      }
      if (StringUtils.isBlank(srcData)) {
        throw new Exception("系统校验时：原数据为空！");
      }
      BASE64Decoder decoder = new BASE64Decoder();
      byte[] b = decoder.decodeBuffer(signData);
      Signature sign = Signature.getInstance("MD5withRSA");
      sign.initVerify(cert);
      sign.update(srcData.getBytes("utf-8"));
      return sign.verify(b);
    }
    if ("MD5".equals(signType))
    {
      System.out.println("md5验证签名开始=============");
      System.out.println("签名数据" + signData + "原数据" + srcData);
      if (signData.equalsIgnoreCase(signByMD5(srcData, key)))
      {
        System.out.println("md5验证签名成功=============");
        return true;
      }
      System.out.println("md5验证签名失败=============");
      return false;
    }
    return false;
  }
  
  public static String Bytes2HexString(byte[] b)
  {
    StringBuffer ret = new StringBuffer(b.length);
    String hex = "";
    for (int i = 0; i < b.length; i++)
    {
      hex = Integer.toHexString(b[i] & 0xFF);
      if (hex.length() == 1) {
        hex = '0' + hex;
      }
      ret.append(hex.toUpperCase());
    }
    return ret.toString();
  }
}
