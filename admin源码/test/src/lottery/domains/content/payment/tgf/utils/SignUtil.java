package lottery.domains.content.payment.tgf.utils;

import java.security.cert.Certificate;
import sun.misc.BASE64Decoder;
import java.security.MessageDigest;
import sun.misc.BASE64Encoder;
import java.security.Signature;
import java.util.Enumeration;
import java.io.InputStream;
import java.security.cert.CertificateFactory;
import java.io.FileInputStream;
import java.security.KeyStore;
import org.apache.commons.lang.StringUtils;
import java.security.cert.X509Certificate;
import java.security.PrivateKey;

public class SignUtil
{
    private static PrivateKey privateKey;
    private static X509Certificate cert;
    private static String signType;
    private static final String CHAR_SET = "UTF-8";
    private static String key;
    
    static {
        SignUtil.privateKey = null;
        SignUtil.cert = null;
        SignUtil.signType = "MD5";
        SignUtil.key = "d39190fc1629c2b73c5b503f18bf10a2";
        if ("RSA".equals(SignUtil.signType)) {
            try {
                initRAS(Config.PFX_FILE, Config.CERT_FILE, Config.PASSWD);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("=================rsa初始化成功");
        }
        else if ("MD5".equals(SignUtil.signType)) {
            try {
                initMD5();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public static void initMD5() throws Exception {
        SignUtil.key = "";
    }
    
    public static void initRAS(final String pfxFilePath, final String certFilePath, final String pfxPwd) throws Exception {
        if (StringUtils.isBlank(pfxFilePath)) {
            throw new Exception("私钥文件路径不能为空！");
        }
        if (StringUtils.isBlank(certFilePath)) {
            throw new Exception("公钥文件路径不能为空！");
        }
        if (StringUtils.isBlank(pfxPwd)) {
            throw new Exception("私钥密码不能为空！");
        }
        if (SignUtil.privateKey == null || SignUtil.cert == null) {
            InputStream is = null;
            try {
                final KeyStore ks = KeyStore.getInstance("PKCS12");
                is = new FileInputStream(pfxFilePath);
                if (is == null) {
                    throw new Exception("证书文件路径不正确！");
                }
                final String pwd = pfxPwd;
                ks.load(is, pwd.toCharArray());
                String alias = "";
                final Enumeration<String> e = ks.aliases();
                while (e.hasMoreElements()) {
                    alias = e.nextElement();
                }
                SignUtil.privateKey = (PrivateKey)ks.getKey(alias, pwd.toCharArray());
                System.out.println("privateKey证书方式：=================================" + SignUtil.privateKey);
                final CertificateFactory cf = CertificateFactory.getInstance("X.509");
                is = new FileInputStream(certFilePath);
                if (is == null) {
                    throw new Exception("证书文件路径不正确！");
                }
                SignUtil.cert = (X509Certificate)cf.generateCertificate(is);
                System.out.println("cert证书方式：=================================" + SignUtil.cert);
                is.close();
            }
            catch (Exception e2) {
                throw new RuntimeException("签名初始化失败！" + e2);
            }
            finally {
                if (is != null) {
                    is.close();
                }
            }
            if (is != null) {
                is.close();
            }
        }
    }
    
    public static String signData(final String sourceData) throws Exception {
        String signStrintg = "";
        if ("RSA".equals(SignUtil.signType)) {
            if (SignUtil.privateKey == null) {
                System.out.println("====================");
                throw new Exception("签名尚未初始化！");
            }
            if (StringUtils.isBlank(sourceData)) {
                throw new Exception("签名数据为空！");
            }
            final Signature sign = Signature.getInstance("MD5withRSA");
            sign.initSign(SignUtil.privateKey);
            sign.update(sourceData.getBytes("utf-8"));
            final byte[] signBytes = sign.sign();
            final BASE64Encoder encoder = new BASE64Encoder();
            signStrintg = encoder.encode(signBytes);
        }
        else if ("MD5".equals(SignUtil.signType)) {
            signStrintg = signByMD5(sourceData, SignUtil.key);
        }
        signStrintg.replaceAll("\r", "").replaceAll("\n", "");
        return signStrintg;
    }
    
    public static String signByMD5(final String sourceData, final String key) throws Exception {
        final String data = String.valueOf(sourceData) + key;
        final MessageDigest md5 = MessageDigest.getInstance("MD5");
        final byte[] sign = md5.digest(data.getBytes("UTF-8"));
        return Bytes2HexString(sign).toUpperCase();
    }
    
    public static boolean verifyData(final String signData, final String srcData) throws Exception {
        if ("RSA".equals(SignUtil.signType)) {
            if (SignUtil.cert == null) {
                throw new Exception("签名尚未初始化！");
            }
            if (StringUtils.isBlank(signData)) {
                throw new Exception("系统校验时：签名数据为空！");
            }
            if (StringUtils.isBlank(srcData)) {
                throw new Exception("系统校验时：原数据为空！");
            }
            final BASE64Decoder decoder = new BASE64Decoder();
            final byte[] b = decoder.decodeBuffer(signData);
            final Signature sign = Signature.getInstance("MD5withRSA");
            sign.initVerify(SignUtil.cert);
            sign.update(srcData.getBytes("utf-8"));
            return sign.verify(b);
        }
        else {
            if (!"MD5".equals(SignUtil.signType)) {
                return false;
            }
            System.out.println("md5验证签名开始=============");
            System.out.println("签名数据" + signData + "原数据" + srcData);
            if (signData.equalsIgnoreCase(signByMD5(srcData, SignUtil.key))) {
                System.out.println("md5验证签名成功=============");
                return true;
            }
            System.out.println("md5验证签名失败=============");
            return false;
        }
    }
    
    public static String Bytes2HexString(final byte[] b) {
        final StringBuffer ret = new StringBuffer(b.length);
        String hex = "";
        for (int i = 0; i < b.length; ++i) {
            hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = String.valueOf('0') + hex;
            }
            ret.append(hex.toUpperCase());
        }
        return ret.toString();
    }
}
