package javautils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Random;
import javautils.math.MathUtil;
import javautils.regex.RegexUtil;

public class StringUtil
{
  public static boolean isNotNull(String s)
  {
    if (s == null) {
      return false;
    }
    if (s.trim().length() == 0) {
      return false;
    }
    return true;
  }
  
  public static boolean isInteger(String s)
  {
    try
    {
      Integer.parseInt(s);
    }
    catch (Exception e)
    {
      return false;
    }
    return true;
  }
  
  public static boolean isDouble(String s)
  {
    try
    {
      Double.parseDouble(s);
    }
    catch (Exception e)
    {
      return false;
    }
    return true;
  }
  
  public static String getRandUserName()
  {
    String chars = "abcdefghijklmnopqrstuvwxyz";
    Random random = new Random();
    int rand = random.nextInt(3) + 2;
    String s = "";
    for (int j = 0; j < rand; j++) {
      s = s + String.valueOf(chars.charAt((int)(Math.random() * 26.0D)));
    }
    int a = (int)(Math.random() * 9000.0D) + 1000;
    return s + a;
  }
  
  public static String getRandomString(int getLength)
  {
    int StringLength = 16;
    if (getLength > 0) {
      StringLength = getLength;
    }
    int[] number = { 48, 49, 50, 51, 52, 53, 54, 55, 56, 57 };
    int[] lAlphabet = { 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 
      86, 87, 88, 89, 90 };
    int[] tAlphabet = { 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 
      114, 115, 116, 117, 118, 119, 120, 121, 122 };
    Random rd = new Random();
    int nowNum = 0;
    StringBuffer nowString = new StringBuffer();
    for (int j = 0; j < StringLength; j++)
    {
      nowNum = rd.nextInt(3);
      switch (nowNum)
      {
      case 0: 
        nowString.append((char)number[rd.nextInt(number.length)]);
        break;
      case 1: 
        nowString.append((char)lAlphabet[rd.nextInt(lAlphabet.length)]);
        break;
      case 2: 
        nowString.append((char)tAlphabet[rd.nextInt(tAlphabet.length)]);
        break;
      default: 
        nowString.append((char)lAlphabet[rd.nextInt(lAlphabet.length)]);
      }
    }
    return nowString.toString();
  }
  
  public static boolean isFloat(String s)
  {
    try
    {
      Float.parseFloat(s);
    }
    catch (Exception e)
    {
      return false;
    }
    return true;
  }
  
  public static boolean isBoolean(String s)
  {
    try
    {
      Boolean.parseBoolean(s);
    }
    catch (Exception e)
    {
      return false;
    }
    return true;
  }
  
  public static boolean isShort(String s)
  {
    try
    {
      Short.parseShort(s);
    }
    catch (Exception e)
    {
      return false;
    }
    return true;
  }
  
  public static boolean isLong(String s)
  {
    try
    {
      Long.parseLong(s);
    }
    catch (Exception e)
    {
      return false;
    }
    return true;
  }
  
  public static Object transObject(String s, Class<?> clazz)
  {
    if (clazz != null)
    {
      if (clazz == Integer.class)
      {
        if (isInteger(s)) {
          return Integer.valueOf(Integer.parseInt(s));
        }
        return Integer.valueOf(0);
      }
      if (clazz == Double.class)
      {
        if (isDouble(s)) {
          return Double.valueOf(Double.parseDouble(s));
        }
        return Integer.valueOf(0);
      }
      if (clazz == Float.class)
      {
        if (isFloat(s)) {
          return Float.valueOf(Float.parseFloat(s));
        }
        return Integer.valueOf(0);
      }
      if (clazz == Boolean.class)
      {
        if (isBoolean(s)) {
          return Boolean.valueOf(Boolean.parseBoolean(s));
        }
        return Boolean.valueOf(true);
      }
      if (clazz == Short.class)
      {
        if (isShort(s)) {
          return Short.valueOf(Short.parseShort(s));
        }
        return Integer.valueOf(0);
      }
      if (clazz == Long.class)
      {
        if (isLong(s)) {
          return Long.valueOf(Long.parseLong(s));
        }
        return Integer.valueOf(0);
      }
    }
    return s;
  }
  
  public static String transArrayToString(Object[] array)
  {
    StringBuffer sb = new StringBuffer();
    int i = 0;
    for (int j = array.length; i < j; i++)
    {
      sb.append(array[i].toString());
      if (i < j - 1) {
        sb.append(",");
      }
    }
    return sb.toString();
  }
  
  public static String transArrayToString(Object[] array, String p)
  {
    String tempStr = new String();
    Object[] arrayOfObject = array;int j = array.length;
    for (int i = 0; i < j; i++)
    {
      Object value = arrayOfObject[i];
      tempStr = tempStr + p + value.toString() + p + ",";
    }
    tempStr = tempStr.substring(0, tempStr.length() - 2);
    return tempStr;
  }
  
  public static int[] transStringToIntArray(String string, String regex)
  {
    if (isNotNull(string))
    {
      String[] sArray = string.split(regex);
      int[] intArray = new int[sArray.length];
      for (int i = 0; i < sArray.length; i++) {
        if (isIntegerString(sArray[i])) {
          intArray[i] = Integer.parseInt(sArray[i]);
        }
      }
      return intArray;
    }
    return null;
  }
  
  public static String transDataLong(long b)
  {
    StringBuffer sb = new StringBuffer();
    long KB = 1024L;
    long MB = KB * 1024L;
    long GB = MB * 1024L;
    long TB = GB * 1024L;
    if (b >= TB) {
      sb.append(MathUtil.doubleFormat(b / TB, 2) + "TB");
    } else if (b >= GB) {
      sb.append(MathUtil.doubleFormat(b / GB, 2) + "GB");
    } else if (b >= MB) {
      sb.append(MathUtil.doubleFormat(b / MB, 2) + "MB");
    } else if (b >= KB) {
      sb.append(MathUtil.doubleFormat(b / KB, 2) + "KB");
    } else {
      sb.append(b + "B");
    }
    return sb.toString();
  }
  
  public static boolean isIntegerString(String str)
  {
    boolean flag = false;
    if (RegexUtil.isMatcher(str, "^-?\\d+$")) {
      flag = true;
    }
    return flag;
  }
  
  public static boolean isDoubleString(String str)
  {
    boolean flag = false;
    if (RegexUtil.isMatcher(str, "^(-?\\d+)(\\.\\d+)?$")) {
      flag = true;
    }
    return flag;
  }
  
  public static boolean isFloatString(String str)
  {
    boolean flag = false;
    if (RegexUtil.isMatcher(str, "^(-?\\d+)(\\.\\d+)?$")) {
      flag = true;
    }
    return flag;
  }
  
  public static boolean isDateString(String str)
  {
    try
    {
      if (str.length() != 10) {
        return false;
      }
      new SimpleDateFormat("yyyy-MM-dd").parse(str);
    }
    catch (Exception e)
    {
      return false;
    }
    return true;
  }
  
  public static String markWithSymbol(Object obj, String symbol)
  {
    return symbol + obj.toString() + symbol;
  }
  
  public static Object[] split(String s)
  {
    char[] carr = s.toCharArray();
    Object[] arr = new Object[carr.length];
    for (int i = 0; i < carr.length; i++) {
      arr[i] = Character.valueOf(carr[i]);
    }
    return arr;
  }
  
  public static String substring(String s, String start, String end, boolean isInSub)
  {
    int idx = s.indexOf(start);
    int edx = s.indexOf(end);
    idx = idx == -1 ? 0 : idx + (isInSub ? 0 : start.length());
    edx = edx == -1 ? s.length() : edx + (isInSub ? end.length() : 0);
    return s.substring(idx, edx);
  }
  
  public static String doubleFormat(double d)
  {
    DecimalFormat decimalformat = new DecimalFormat("#0.00");
    return decimalformat.format(d);
  }
}
