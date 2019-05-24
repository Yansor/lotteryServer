package javautils.encrypt;

import java.io.PrintStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util
{
  private static final String KEY_MD5 = "MD5";
  private static final String[] strDigits = { "0", "1", "2", "3", "4", "5", 
    "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
  
  private static String byteToArrayString(byte bByte)
  {
    int iRet = bByte;
    if (iRet < 0) {
      iRet += 256;
    }
    int iD1 = iRet / 16;
    int iD2 = iRet % 16;
    return strDigits[iD1] + strDigits[iD2];
  }
  
  private static String byteToString(byte[] bByte)
  {
    StringBuffer sBuffer = new StringBuffer();
    for (int i = 0; i < bByte.length; i++) {
      sBuffer.append(byteToArrayString(bByte[i]));
    }
    return sBuffer.toString();
  }
  
  public static String getMD5Code(String strObj)
  {
    try
    {
      MessageDigest md = MessageDigest.getInstance("MD5");
      
      return byteToString(md.digest(strObj.getBytes()));
    }
    catch (NoSuchAlgorithmException e)
    {
      e.printStackTrace();
    }
    return null;
  }
  
  public static void main(String[] args)
  {
    String md5Code = getMD5Code("123456");
    System.out.println(md5Code.toUpperCase());
  }
}
