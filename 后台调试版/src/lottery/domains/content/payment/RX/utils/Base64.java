package lottery.domains.content.payment.RX.utils;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import org.apache.commons.lang.time.StopWatch;

public class Base64
{
  private static final char[] CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"
    .toCharArray();
  private static final int[] INV = new int['Ā'];
  
  static
  {
    Arrays.fill(INV, -1);
    
    int i = 0;
    for (int iS = CHARS.length; i < iS; i++) {
      INV[CHARS[i]] = i;
    }
    INV[61] = 0;
  }
  
  public static char[] getAlphabet()
  {
    return (char[])CHARS.clone();
  }
  
  public static char[] encodeToChar(String s)
  {
    try
    {
      return encodeToChar(s.getBytes("UTF-8"), false);
    }
    catch (UnsupportedEncodingException ignore) {}
    return null;
  }
  
  public static char[] encodeToChar(byte[] arr)
  {
    return encodeToChar(arr, false);
  }
  
  public static char[] encodeToChar(byte[] arr, boolean lineSeparator)
  {
    int len = arr != null ? arr.length : 0;
    if (len == 0) {
      return new char[0];
    }
    int evenlen = len / 3 * 3;
    
    int cnt = (len - 1) / 3 + 1 << 2;
    
    int destLen = cnt + (lineSeparator ? (cnt - 1) / 76 << 1 : 0);
    
    char[] dest = new char[destLen];
    
    int s = 0;int d = 0;
    for (int cc = 0; s < evenlen;)
    {
      int i = (arr[(s++)] & 0xFF) << 16 | (arr[(s++)] & 0xFF) << 8 | 
      
        arr[(s++)] & 0xFF;
      
      dest[(d++)] = CHARS[(i >>> 18 & 0x3F)];
      
      dest[(d++)] = CHARS[(i >>> 12 & 0x3F)];
      
      dest[(d++)] = CHARS[(i >>> 6 & 0x3F)];
      
      dest[(d++)] = CHARS[(i & 0x3F)];
      if (lineSeparator)
      {
        cc++;
        if ((cc == 19) && (d < destLen - 2))
        {
          dest[(d++)] = '\r';
          
          dest[(d++)] = '\n';
          
          cc = 0;
        }
      }
    }
    int left = len - evenlen;
    if (left > 0)
    {
      int i = (arr[evenlen] & 0xFF) << 10 | 
      
        (left == 2 ? (arr[(len - 1)] & 0xFF) << 2 : 0);
      
      dest[(destLen - 4)] = CHARS[(i >> 12)];
      
      dest[(destLen - 3)] = CHARS[(i >>> 6 & 0x3F)];
      
      dest[(destLen - 2)] = (left == 2 ? CHARS[(i & 0x3F)] : '=');
      
      dest[(destLen - 1)] = '=';
    }
    return dest;
  }
  
  public byte[] decode(char[] arr)
  {
    int length = arr.length;
    if (length == 0) {
      return new byte[0];
    }
    int sndx = 0;int endx = length - 1;
    
    int pad = arr[endx] == '=' ? 1 : arr[(endx - 1)] == '=' ? 2 : 0;
    
    int cnt = endx - sndx + 1;
    
    int sepCnt = length > 76 ? (arr[76] == '\r' ? cnt / 78 : 0) << 1 : 0;
    
    int len = ((cnt - sepCnt) * 6 >> 3) - pad;
    
    byte[] dest = new byte[len];
    
    int d = 0;
    
    int cc = 0;
    for (int eLen = len / 3 * 3; d < eLen;)
    {
      int i = INV[arr[(sndx++)]] << 18 | INV[arr[(sndx++)]] << 12 | 
      
        INV[arr[(sndx++)]] << 6 | INV[arr[(sndx++)]];
      
      dest[(d++)] = ((byte)(i >> 16));
      
      dest[(d++)] = ((byte)(i >> 8));
      
      dest[(d++)] = ((byte)i);
      if (sepCnt > 0)
      {
        cc++;
        if (cc == 19)
        {
          sndx += 2;
          
          cc = 0;
        }
      }
    }
    if (d < len)
    {
      int i = 0;
      for (int j = 0; sndx <= endx - pad; j++) {
        i |= INV[arr[(sndx++)]] << 18 - j * 6;
      }
      for (int r = 16; d < len; r -= 8) {
        dest[(d++)] = ((byte)(i >> r));
      }
    }
    return dest;
  }
  
  public static byte[] encodeToByte(String s)
  {
    try
    {
      return encodeToByte(s.getBytes("UTF-8"), false);
    }
    catch (UnsupportedEncodingException ignore) {}
    return null;
  }
  
  public static byte[] encodeToByte(byte[] arr)
  {
    return encodeToByte(arr, false);
  }
  
  public static byte[] encodeToByte(byte[] arr, boolean lineSep)
  {
    int len = arr != null ? arr.length : 0;
    if (len == 0) {
      return new byte[0];
    }
    int evenlen = len / 3 * 3;
    
    int cnt = (len - 1) / 3 + 1 << 2;
    
    int destlen = cnt + (lineSep ? (cnt - 1) / 76 << 1 : 0);
    
    byte[] dest = new byte[destlen];
    
    int s = 0;int d = 0;
    for (int cc = 0; s < evenlen;)
    {
      int i = (arr[(s++)] & 0xFF) << 16 | (arr[(s++)] & 0xFF) << 8 | 
      
        arr[(s++)] & 0xFF;
      
      dest[(d++)] = ((byte)CHARS[(i >>> 18 & 0x3F)]);
      
      dest[(d++)] = ((byte)CHARS[(i >>> 12 & 0x3F)]);
      
      dest[(d++)] = ((byte)CHARS[(i >>> 6 & 0x3F)]);
      
      dest[(d++)] = ((byte)CHARS[(i & 0x3F)]);
      if (lineSep)
      {
        cc++;
        if ((cc == 19) && (d < destlen - 2))
        {
          dest[(d++)] = 13;
          
          dest[(d++)] = 10;
          
          cc = 0;
        }
      }
    }
    int left = len - evenlen;
    if (left > 0)
    {
      int i = (arr[evenlen] & 0xFF) << 10 | 
      
        (left == 2 ? (arr[(len - 1)] & 0xFF) << 2 : 0);
      
      dest[(destlen - 4)] = ((byte)CHARS[(i >> 12)]);
      
      dest[(destlen - 3)] = ((byte)CHARS[(i >>> 6 & 0x3F)]);
      
      dest[(destlen - 2)] = (left == 2 ? (byte)CHARS[(i & 0x3F)] : 61);
      
      dest[(destlen - 1)] = 61;
    }
    return dest;
  }
  
  public static byte[] decode(byte[] arr)
  {
    int length = arr.length;
    if (length == 0) {
      return new byte[0];
    }
    int sndx = 0;int endx = length - 1;
    
    int pad = arr[endx] == 61 ? 1 : arr[(endx - 1)] == 61 ? 2 : 0;
    
    int cnt = endx - sndx + 1;
    
    int sepCnt = length > 76 ? (arr[76] == 13 ? cnt / 78 : 0) << 1 : 0;
    
    int len = ((cnt - sepCnt) * 6 >> 3) - pad;
    
    byte[] dest = new byte[len];
    
    int d = 0;
    
    int cc = 0;
    for (int eLen = len / 3 * 3; d < eLen;)
    {
      int i = INV[arr[(sndx++)]] << 18 | INV[arr[(sndx++)]] << 12 | 
      
        INV[arr[(sndx++)]] << 6 | INV[arr[(sndx++)]];
      
      dest[(d++)] = ((byte)(i >> 16));
      
      dest[(d++)] = ((byte)(i >> 8));
      
      dest[(d++)] = ((byte)i);
      if (sepCnt > 0)
      {
        cc++;
        if (cc == 19)
        {
          sndx += 2;
          
          cc = 0;
        }
      }
    }
    if (d < len)
    {
      int i = 0;
      for (int j = 0; sndx <= endx - pad; j++) {
        i |= INV[arr[(sndx++)]] << 18 - j * 6;
      }
      for (int r = 16; d < len; r -= 8) {
        dest[(d++)] = ((byte)(i >> r));
      }
    }
    return dest;
  }
  
  public static String encodeToString(String s)
  {
    try
    {
      return new String(encodeToChar(s.getBytes("UTF-8"), false));
    }
    catch (UnsupportedEncodingException ignore) {}
    return null;
  }
  
  public static String decodeToString(String s)
  {
    try
    {
      return new String(decode(s), "UTF-8");
    }
    catch (UnsupportedEncodingException ignore) {}
    return null;
  }
  
  public static String encodeToString(byte[] arr)
  {
    return new String(encodeToChar(arr, false));
  }
  
  public static String encodeToString(byte[] arr, boolean lineSep)
  {
    return new String(encodeToChar(arr, lineSep));
  }
  
  public static byte[] decode(String s)
  {
    int length = s.length();
    if (length == 0) {
      return new byte[0];
    }
    int sndx = 0;int endx = length - 1;
    
    int pad = s.charAt(endx) == '=' ? 1 : s.charAt(endx - 1) == '=' ? 2 : 
    
      0;
    
    int cnt = endx - sndx + 1;
    
    int sepCnt = length > 76 ? (s.charAt(76) == '\r' ? cnt / 78 : 0) << 1 : 
    
      0;
    
    int len = ((cnt - sepCnt) * 6 >> 3) - pad;
    
    byte[] dest = new byte[len];
    
    int d = 0;
    
    int cc = 0;
    for (int eLen = len / 3 * 3; d < eLen;)
    {
      int i = INV[s.charAt(sndx++)] << 18 | INV[s.charAt(sndx++)] << 12 | 
      
        INV[s.charAt(sndx++)] << 6 | INV[s.charAt(sndx++)];
      
      dest[(d++)] = ((byte)(i >> 16));
      
      dest[(d++)] = ((byte)(i >> 8));
      
      dest[(d++)] = ((byte)i);
      if (sepCnt > 0)
      {
        cc++;
        if (cc == 19)
        {
          sndx += 2;
          
          cc = 0;
        }
      }
    }
    if (d < len)
    {
      int i = 0;
      for (int j = 0; sndx <= endx - pad; j++) {
        i |= INV[s.charAt(sndx++)] << 18 - j * 6;
      }
      for (int r = 16; d < len; r -= 8) {
        dest[(d++)] = ((byte)(i >> r));
      }
    }
    return dest;
  }
  
  public static void main(String[] args)
  {
    StopWatch sw = new StopWatch();
    sw.start();
    
    String str = "��@#��%����&*��������+brown fox jumps ��ã�����˭��Hello����dogThe quick brown fox jumps over the lazy dog";
    for (int i = 0; i < 100000; i++) {
      encodeToString(str);
    }
    sw.stop();
    
    System.out.println(sw.getTime() + "����");
  }
}
