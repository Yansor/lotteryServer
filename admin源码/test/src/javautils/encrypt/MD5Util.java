package javautils.encrypt;

import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;

public class MD5Util
{
    private static final String KEY_MD5 = "MD5";
    private static final String[] strDigits;
    
    static {
        strDigits = new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
    }
    
    private static String byteToArrayString(final byte bByte) {
        int iRet = bByte;
        if (iRet < 0) {
            iRet += 256;
        }
        final int iD1 = iRet / 16;
        final int iD2 = iRet % 16;
        return String.valueOf(MD5Util.strDigits[iD1]) + MD5Util.strDigits[iD2];
    }
    
    private static String byteToString(final byte[] bByte) {
        final StringBuffer sBuffer = new StringBuffer();
        for (int i = 0; i < bByte.length; ++i) {
            sBuffer.append(byteToArrayString(bByte[i]));
        }
        return sBuffer.toString();
    }
    
    public static String getMD5Code(final String strObj) {
        try {
            final MessageDigest md = MessageDigest.getInstance("MD5");
            return byteToString(md.digest(strObj.getBytes()));
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static void main(final String[] args) {
        final String md5Code = getMD5Code("123456");
        System.out.println(md5Code.toUpperCase());
    }
}
