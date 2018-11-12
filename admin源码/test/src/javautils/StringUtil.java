package javautils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import javautils.regex.RegexUtil;
import javautils.math.MathUtil;
import java.util.Random;

public class StringUtil
{
    public static boolean isNotNull(final String s) {
        return s != null && s.trim().length() != 0;
    }
    
    public static boolean isInteger(final String s) {
        try {
            Integer.parseInt(s);
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }
    
    public static boolean isDouble(final String s) {
        try {
            Double.parseDouble(s);
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }
    
    public static String getRandUserName() {
        final String chars = "abcdefghijklmnopqrstuvwxyz";
        final Random random = new Random();
        final int rand = random.nextInt(3) + 2;
        String s = "";
        for (int j = 0; j < rand; ++j) {
            s = String.valueOf(s) + String.valueOf(chars.charAt((int)(Math.random() * 26.0)));
        }
        final int a = (int)(Math.random() * 9000.0) + 1000;
        return String.valueOf(s) + a;
    }
    
    public static String getRandomString(final int getLength) {
        int StringLength = 16;
        if (getLength > 0) {
            StringLength = getLength;
        }
        final int[] number = { 48, 49, 50, 51, 52, 53, 54, 55, 56, 57 };
        final int[] lAlphabet = { 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90 };
        final int[] tAlphabet = { 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122 };
        final Random rd = new Random();
        int nowNum = 0;
        final StringBuffer nowString = new StringBuffer();
        for (int j = 0; j < StringLength; ++j) {
            nowNum = rd.nextInt(3);
            switch (nowNum) {
                case 0: {
                    nowString.append((char)number[rd.nextInt(number.length)]);
                    break;
                }
                case 1: {
                    nowString.append((char)lAlphabet[rd.nextInt(lAlphabet.length)]);
                    break;
                }
                case 2: {
                    nowString.append((char)tAlphabet[rd.nextInt(tAlphabet.length)]);
                    break;
                }
                default: {
                    nowString.append((char)lAlphabet[rd.nextInt(lAlphabet.length)]);
                    break;
                }
            }
        }
        return nowString.toString();
    }
    
    public static boolean isFloat(final String s) {
        try {
            Float.parseFloat(s);
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }
    
    public static boolean isBoolean(final String s) {
        try {
            Boolean.parseBoolean(s);
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }
    
    public static boolean isShort(final String s) {
        try {
            Short.parseShort(s);
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }
    
    public static boolean isLong(final String s) {
        try {
            Long.parseLong(s);
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }
    
    public static Object transObject(final String s, final Class<?> clazz) {
        if (clazz != null) {
            if (clazz == Integer.class) {
                if (isInteger(s)) {
                    return Integer.parseInt(s);
                }
                return 0;
            }
            else if (clazz == Double.class) {
                if (isDouble(s)) {
                    return Double.parseDouble(s);
                }
                return 0;
            }
            else if (clazz == Float.class) {
                if (isFloat(s)) {
                    return Float.parseFloat(s);
                }
                return 0;
            }
            else if (clazz == Boolean.class) {
                if (isBoolean(s)) {
                    return Boolean.parseBoolean(s);
                }
                return true;
            }
            else if (clazz == Short.class) {
                if (isShort(s)) {
                    return Short.parseShort(s);
                }
                return 0;
            }
            else if (clazz == Long.class) {
                if (isLong(s)) {
                    return Long.parseLong(s);
                }
                return 0;
            }
        }
        return s;
    }
    
    public static String transArrayToString(final Object[] array) {
        final StringBuffer sb = new StringBuffer();
        for (int i = 0, j = array.length; i < j; ++i) {
            sb.append(array[i].toString());
            if (i < j - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }
    
    public static String transArrayToString(final Object[] array, final String p) {
        String tempStr = new String();
        for (final Object value : array) {
            tempStr = String.valueOf(tempStr) + p + value.toString() + p + ",";
        }
        tempStr = tempStr.substring(0, tempStr.length() - 2);
        return tempStr;
    }
    
    public static int[] transStringToIntArray(final String string, final String regex) {
        if (isNotNull(string)) {
            final String[] sArray = string.split(regex);
            final int[] intArray = new int[sArray.length];
            for (int i = 0; i < sArray.length; ++i) {
                if (isIntegerString(sArray[i])) {
                    intArray[i] = Integer.parseInt(sArray[i]);
                }
            }
            return intArray;
        }
        return null;
    }
    
    public static String transDataLong(final long b) {
        final StringBuffer sb = new StringBuffer();
        final long KB = 1024L;
        final long MB = KB * 1024L;
        final long GB = MB * 1024L;
        final long TB = GB * 1024L;
        if (b >= TB) {
            sb.append(String.valueOf(MathUtil.doubleFormat(b / (double)TB, 2)) + "TB");
        }
        else if (b >= GB) {
            sb.append(String.valueOf(MathUtil.doubleFormat(b / (double)GB, 2)) + "GB");
        }
        else if (b >= MB) {
            sb.append(String.valueOf(MathUtil.doubleFormat(b / (double)MB, 2)) + "MB");
        }
        else if (b >= KB) {
            sb.append(String.valueOf(MathUtil.doubleFormat(b / (double)KB, 2)) + "KB");
        }
        else {
            sb.append(String.valueOf(b) + "B");
        }
        return sb.toString();
    }
    
    public static boolean isIntegerString(final String str) {
        boolean flag = false;
        if (RegexUtil.isMatcher(str, "^-?\\d+$")) {
            flag = true;
        }
        return flag;
    }
    
    public static boolean isDoubleString(final String str) {
        boolean flag = false;
        if (RegexUtil.isMatcher(str, "^(-?\\d+)(\\.\\d+)?$")) {
            flag = true;
        }
        return flag;
    }
    
    public static boolean isFloatString(final String str) {
        boolean flag = false;
        if (RegexUtil.isMatcher(str, "^(-?\\d+)(\\.\\d+)?$")) {
            flag = true;
        }
        return flag;
    }
    
    public static boolean isDateString(final String str) {
        try {
            if (str.length() != 10) {
                return false;
            }
            new SimpleDateFormat("yyyy-MM-dd").parse(str);
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }
    
    public static String markWithSymbol(final Object obj, final String symbol) {
        return String.valueOf(symbol) + obj.toString() + symbol;
    }
    
    public static Object[] split(final String s) {
        final char[] carr = s.toCharArray();
        final Object[] arr = new Object[carr.length];
        for (int i = 0; i < carr.length; ++i) {
            arr[i] = carr[i];
        }
        return arr;
    }
    
    public static String substring(final String s, final String start, final String end, final boolean isInSub) {
        int idx = s.indexOf(start);
        int edx = s.indexOf(end);
        idx = ((idx == -1) ? 0 : (idx + (isInSub ? 0 : start.length())));
        edx = ((edx == -1) ? s.length() : (edx + (isInSub ? end.length() : 0)));
        return s.substring(idx, edx);
    }
    
    public static String doubleFormat(final double d) {
        final DecimalFormat decimalformat = new DecimalFormat("#0.00");
        return decimalformat.format(d);
    }
}
