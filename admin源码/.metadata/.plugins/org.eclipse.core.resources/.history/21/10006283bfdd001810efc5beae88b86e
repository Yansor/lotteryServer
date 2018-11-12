package lottery.domains.content.payment.zs.utils;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;

public class MD5Encrypt
{
    private static final String ENCODE = "UTF-8";
    
    public static String getMessageDigest(final String strSrc) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = null;
        String strDes = null;
        final String ALGO_MD5 = "MD5";
        final byte[] bt = strSrc.getBytes("UTF-8");
        md = MessageDigest.getInstance("MD5");
        md.update(bt);
        strDes = StringUtil.byte2hex(md.digest());
        return strDes;
    }
    
    public static String bytes2Hex(final byte[] bts) {
        String des = "";
        String tmp = null;
        for (int i = 0; i < bts.length; ++i) {
            tmp = Integer.toHexString(bts[i] & 0xFF);
            if (tmp.length() == 1) {
                des = String.valueOf(des) + "0";
            }
            des = String.valueOf(des) + tmp;
        }
        return des;
    }
}
