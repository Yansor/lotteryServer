package lottery.domains.content.payment.RX.utils;

import org.apache.commons.lang.time.StopWatch;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class Base64
{
    private static final char[] CHARS;
    private static final int[] INV;
    
    static {
        CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();
        Arrays.fill(INV = new int[256], -1);
        for (int i = 0, iS = Base64.CHARS.length; i < iS; ++i) {
            Base64.INV[Base64.CHARS[i]] = i;
        }
        Base64.INV[61] = 0;
    }
    
    public static char[] getAlphabet() {
        return Base64.CHARS.clone();
    }
    
    public static char[] encodeToChar(final String s) {
        try {
            return encodeToChar(s.getBytes("UTF-8"), false);
        }
        catch (UnsupportedEncodingException ignore) {
            return null;
        }
    }
    
    public static char[] encodeToChar(final byte[] arr) {
        return encodeToChar(arr, false);
    }
    
    public static char[] encodeToChar(final byte[] arr, final boolean lineSeparator) {
        final int len = (arr != null) ? arr.length : 0;
        if (len == 0) {
            return new char[0];
        }
        final int evenlen = len / 3 * 3;
        final int cnt = (len - 1) / 3 + 1 << 2;
        final int destLen = cnt + (lineSeparator ? ((cnt - 1) / 76 << 1) : 0);
        final char[] dest = new char[destLen];
        int s = 0;
        int d = 0;
        int cc = 0;
        while (s < evenlen) {
            final int i = (arr[s++] & 0xFF) << 16 | (arr[s++] & 0xFF) << 8 | (arr[s++] & 0xFF);
            dest[d++] = Base64.CHARS[i >>> 18 & 0x3F];
            dest[d++] = Base64.CHARS[i >>> 12 & 0x3F];
            dest[d++] = Base64.CHARS[i >>> 6 & 0x3F];
            dest[d++] = Base64.CHARS[i & 0x3F];
            if (lineSeparator && ++cc == 19 && d < destLen - 2) {
                dest[d++] = '\r';
                dest[d++] = '\n';
                cc = 0;
            }
        }
        final int left = len - evenlen;
        if (left > 0) {
            final int j = (arr[evenlen] & 0xFF) << 10 | ((left == 2) ? ((arr[len - 1] & 0xFF) << 2) : 0);
            dest[destLen - 4] = Base64.CHARS[j >> 12];
            dest[destLen - 3] = Base64.CHARS[j >>> 6 & 0x3F];
            dest[destLen - 2] = ((left == 2) ? Base64.CHARS[j & 0x3F] : '=');
            dest[destLen - 1] = '=';
        }
        return dest;
    }
    
    public byte[] decode(final char[] arr) {
        final int length = arr.length;
        if (length == 0) {
            return new byte[0];
        }
        int sndx = 0;
        final int endx = length - 1;
        final int pad = (arr[endx] == '=') ? ((arr[endx - 1] == '=') ? 2 : 1) : 0;
        final int cnt = endx - sndx + 1;
        final int sepCnt = (length > 76) ? (((arr[76] == '\r') ? (cnt / 78) : 0) << 1) : 0;
        final int len = ((cnt - sepCnt) * 6 >> 3) - pad;
        final byte[] dest = new byte[len];
        int d = 0;
        int cc = 0;
        final int eLen = len / 3 * 3;
        while (d < eLen) {
            final int i = Base64.INV[arr[sndx++]] << 18 | Base64.INV[arr[sndx++]] << 12 | Base64.INV[arr[sndx++]] << 6 | Base64.INV[arr[sndx++]];
            dest[d++] = (byte)(i >> 16);
            dest[d++] = (byte)(i >> 8);
            dest[d++] = (byte)i;
            if (sepCnt > 0 && ++cc == 19) {
                sndx += 2;
                cc = 0;
            }
        }
        if (d < len) {
            int j = 0;
            for (int k = 0; sndx <= endx - pad; j |= Base64.INV[arr[sndx++]] << 18 - k * 6, ++k) {}
            for (int r = 16; d < len; dest[d++] = (byte)(j >> r), r -= 8) {}
        }
        return dest;
    }
    
    public static byte[] encodeToByte(final String s) {
        try {
            return encodeToByte(s.getBytes("UTF-8"), false);
        }
        catch (UnsupportedEncodingException ignore) {
            return null;
        }
    }
    
    public static byte[] encodeToByte(final byte[] arr) {
        return encodeToByte(arr, false);
    }
    
    public static byte[] encodeToByte(final byte[] arr, final boolean lineSep) {
        final int len = (arr != null) ? arr.length : 0;
        if (len == 0) {
            return new byte[0];
        }
        final int evenlen = len / 3 * 3;
        final int cnt = (len - 1) / 3 + 1 << 2;
        final int destlen = cnt + (lineSep ? ((cnt - 1) / 76 << 1) : 0);
        final byte[] dest = new byte[destlen];
        int s = 0;
        int d = 0;
        int cc = 0;
        while (s < evenlen) {
            final int i = (arr[s++] & 0xFF) << 16 | (arr[s++] & 0xFF) << 8 | (arr[s++] & 0xFF);
            dest[d++] = (byte)Base64.CHARS[i >>> 18 & 0x3F];
            dest[d++] = (byte)Base64.CHARS[i >>> 12 & 0x3F];
            dest[d++] = (byte)Base64.CHARS[i >>> 6 & 0x3F];
            dest[d++] = (byte)Base64.CHARS[i & 0x3F];
            if (lineSep && ++cc == 19 && d < destlen - 2) {
                dest[d++] = 13;
                dest[d++] = 10;
                cc = 0;
            }
        }
        final int left = len - evenlen;
        if (left > 0) {
            final int j = (arr[evenlen] & 0xFF) << 10 | ((left == 2) ? ((arr[len - 1] & 0xFF) << 2) : 0);
            dest[destlen - 4] = (byte)Base64.CHARS[j >> 12];
            dest[destlen - 3] = (byte)Base64.CHARS[j >>> 6 & 0x3F];
            dest[destlen - 2] = (byte)((left == 2) ? ((byte)Base64.CHARS[j & 0x3F]) : 61);
            dest[destlen - 1] = 61;
        }
        return dest;
    }
    
    public static byte[] decode(final byte[] arr) {
        final int length = arr.length;
        if (length == 0) {
            return new byte[0];
        }
        int sndx = 0;
        final int endx = length - 1;
        final int pad = (arr[endx] == 61) ? ((arr[endx - 1] == 61) ? 2 : 1) : 0;
        final int cnt = endx - sndx + 1;
        final int sepCnt = (length > 76) ? (((arr[76] == 13) ? (cnt / 78) : 0) << 1) : 0;
        final int len = ((cnt - sepCnt) * 6 >> 3) - pad;
        final byte[] dest = new byte[len];
        int d = 0;
        int cc = 0;
        final int eLen = len / 3 * 3;
        while (d < eLen) {
            final int i = Base64.INV[arr[sndx++]] << 18 | Base64.INV[arr[sndx++]] << 12 | Base64.INV[arr[sndx++]] << 6 | Base64.INV[arr[sndx++]];
            dest[d++] = (byte)(i >> 16);
            dest[d++] = (byte)(i >> 8);
            dest[d++] = (byte)i;
            if (sepCnt > 0 && ++cc == 19) {
                sndx += 2;
                cc = 0;
            }
        }
        if (d < len) {
            int j = 0;
            for (int k = 0; sndx <= endx - pad; j |= Base64.INV[arr[sndx++]] << 18 - k * 6, ++k) {}
            for (int r = 16; d < len; dest[d++] = (byte)(j >> r), r -= 8) {}
        }
        return dest;
    }
    
    public static String encodeToString(final String s) {
        try {
            return new String(encodeToChar(s.getBytes("UTF-8"), false));
        }
        catch (UnsupportedEncodingException ignore) {
            return null;
        }
    }
    
    public static String decodeToString(final String s) {
        try {
            return new String(decode(s), "UTF-8");
        }
        catch (UnsupportedEncodingException ignore) {
            return null;
        }
    }
    
    public static String encodeToString(final byte[] arr) {
        return new String(encodeToChar(arr, false));
    }
    
    public static String encodeToString(final byte[] arr, final boolean lineSep) {
        return new String(encodeToChar(arr, lineSep));
    }
    
    public static byte[] decode(final String s) {
        final int length = s.length();
        if (length == 0) {
            return new byte[0];
        }
        int sndx = 0;
        final int endx = length - 1;
        final int pad = (s.charAt(endx) == '=') ? ((s.charAt(endx - 1) == '=') ? 2 : 1) : 0;
        final int cnt = endx - sndx + 1;
        final int sepCnt = (length > 76) ? (((s.charAt(76) == '\r') ? (cnt / 78) : 0) << 1) : 0;
        final int len = ((cnt - sepCnt) * 6 >> 3) - pad;
        final byte[] dest = new byte[len];
        int d = 0;
        int cc = 0;
        final int eLen = len / 3 * 3;
        while (d < eLen) {
            final int i = Base64.INV[s.charAt(sndx++)] << 18 | Base64.INV[s.charAt(sndx++)] << 12 | Base64.INV[s.charAt(sndx++)] << 6 | Base64.INV[s.charAt(sndx++)];
            dest[d++] = (byte)(i >> 16);
            dest[d++] = (byte)(i >> 8);
            dest[d++] = (byte)i;
            if (sepCnt > 0 && ++cc == 19) {
                sndx += 2;
                cc = 0;
            }
        }
        if (d < len) {
            int j = 0;
            for (int k = 0; sndx <= endx - pad; j |= Base64.INV[s.charAt(sndx++)] << 18 - k * 6, ++k) {}
            for (int r = 16; d < len; dest[d++] = (byte)(j >> r), r -= 8) {}
        }
        return dest;
    }
    
    public static void main(final String[] args) {
        final StopWatch sw = new StopWatch();
        sw.start();
        final String str = "��@#��%����&*��������+brown fox jumps ��ã�����˭��Hello����dogThe quick brown fox jumps over the lazy dog";
        for (int i = 0; i < 100000; ++i) {
            encodeToString(str);
        }
        sw.stop();
        System.out.println(String.valueOf(sw.getTime()) + "����");
    }
}
