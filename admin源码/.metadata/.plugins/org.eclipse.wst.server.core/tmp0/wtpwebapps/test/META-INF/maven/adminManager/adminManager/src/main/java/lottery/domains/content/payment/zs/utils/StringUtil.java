package lottery.domains.content.payment.zs.utils;

import java.util.Set;
import java.util.Iterator;
import java.util.List;
import com.alibaba.fastjson.JSONObject;
import java.util.Random;
import java.io.UnsupportedEncodingException;

public final class StringUtil
{
    public static final String[] LETTERS;
    public static final String[] NUMS;
    public static final String[] LETTERNUMS;
    public static final String[] NUMSLETTER_A_F;
    public static final byte[] b8;
    
    static {
        LETTERS = new String[] { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };
        NUMS = new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
        LETTERNUMS = new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };
        NUMSLETTER_A_F = new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F" };
        b8 = new byte[8];
    }
    
    private StringUtil() {
    }
    
    public static byte[] hex2byte(final String s) throws UnsupportedEncodingException {
        final byte[] src = s.toLowerCase().getBytes("UTF-8");
        final byte[] ret = new byte[src.length / 2];
        for (int i = 0; i < src.length; i += 2) {
            byte hi = src[i];
            byte low = src[i + 1];
            hi = (byte)((hi >= 97 && hi <= 102) ? (10 + (hi - 97)) : (hi - 48));
            low = (byte)((low >= 97 && low <= 102) ? (10 + (low - 97)) : (low - 48));
            ret[i / 2] = (byte)(hi << 4 | low);
        }
        return ret;
    }
    
    public static String byte2hex(final byte[] b) {
        final char[] digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        final char[] out = new char[b.length * 2];
        for (int i = 0; i < b.length; ++i) {
            final byte c = b[i];
            out[i * 2] = digit[c >>> 4 & 0xF];
            out[i * 2 + 1] = digit[c & 0xF];
        }
        return new String(out);
    }
    
    public static String getRandomNumAndLetterAF(final int len) {
        final String s = "";
        s.toCharArray();
        return getRandom(len, StringUtil.NUMSLETTER_A_F);
    }
    
    public static String getRandomLetter(final int len) {
        return getRandom(len, StringUtil.LETTERS);
    }
    
    public static String getRandomNum(final int len) {
        return getRandom(len, StringUtil.NUMS);
    }
    
    public static String getRandomLetterAndNum(final int len) {
        return getRandom(len, StringUtil.LETTERNUMS);
    }
    
    public static String getRandom(final int len, final String[] arr) {
        String s = "";
        if (len <= 0 || arr == null || arr.length < 0) {
            return s;
        }
        final Random ra = new Random();
        final int arrLen = arr.length;
        for (int i = 0; i < len; ++i) {
            s = String.valueOf(s) + arr[ra.nextInt(arrLen)];
        }
        return s;
    }
    
    public static boolean isEmpty(final String str) {
        return str == null || str.isEmpty();
    }
    
    public static String null2String(final String str) {
        return (str == null) ? "" : str.trim();
    }
    
    public static boolean isNotEmpty(final String... field) {
        if (field == null || field.length < 1) {
            return false;
        }
        for (final String f : field) {
            if (isEmpty(f)) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean isNotEmpty(final String[] keys, final JSONObject json) {
        if (keys == null || keys.length < 1 || json == null || json.size() < 1) {
            return false;
        }
        for (final String k : keys) {
            if (isEmpty(json.getString(k))) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean isNotEmpty(final List<String> keys, final JSONObject json) {
        if (keys == null || keys.size() < 1 || json == null || json.size() < 1) {
            return false;
        }
        for (final String k : keys) {
            if (isEmpty(json.getString(k))) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean isNotEmpty(final JSONObject json) {
        if (json == null || json.size() < 1) {
            return false;
        }
        final Set<String> keys = (Set<String>)json.keySet();
        if (keys == null || keys.isEmpty()) {
            return false;
        }
        for (final String key : keys) {
            if (isEmpty(json.getString(key))) {
                return false;
            }
        }
        return true;
    }
    
    public static Long changeY2F(final String amount) {
        final String currency = amount.replaceAll("\\$|\\￥|\\,", "");
        final int index = currency.indexOf(".");
        final int length = currency.length();
        Long amLong = 0L;
        if (index == -1) {
            amLong = Long.valueOf(String.valueOf(currency) + "00");
        }
        else if (length - index >= 3) {
            amLong = Long.valueOf(currency.substring(0, index + 3).replace(".", ""));
        }
        else if (length - index == 2) {
            amLong = Long.valueOf(String.valueOf(currency.substring(0, index + 2).replace(".", "")) + 0);
        }
        else {
            amLong = Long.valueOf(String.valueOf(currency.substring(0, index + 1).replace(".", "")) + "00");
        }
        return amLong;
    }
}
