/*     */ package com.fsy.lottery.utils;
/*     */ 
/*     */ import java.io.InputStream;
/*     */ import java.text.DecimalFormat;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Random;
/*     */ import com.fsy.lottery.utils.math.MathUtil;
/*     */ import com.fsy.lottery.utils.regex.RegexUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StringUtil
/*     */ {
/*     */   public static boolean isNotNull(String s)
/*     */   {
/*  19 */     if (s == null) return false;
/*  20 */     if (s.trim().length() == 0) return false;
/*  21 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static boolean isInteger(String s)
/*     */   {
/*     */     try
/*     */     {
/*  31 */       Integer.parseInt(s);
/*     */     } catch (Exception e) {
/*  33 */       return false;
/*     */     }
/*  35 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static boolean isDouble(String s)
/*     */   {
/*     */     try
/*     */     {
/*  45 */       Double.parseDouble(s);
/*     */     } catch (Exception e) {
/*  47 */       return false;
/*     */     }
/*  49 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static boolean isFloat(String s)
/*     */   {
/*     */     try
/*     */     {
/*  59 */       Float.parseFloat(s);
/*     */     } catch (Exception e) {
/*  61 */       return false;
/*     */     }
/*  63 */     return true;
/*     */   }
/*     */   
/*     */   public static String fromInputStream(InputStream inputStream) {
/*     */     try {
/*  68 */       StringBuffer sb = new StringBuffer();
/*  69 */       byte[] bytes = new byte['Ѐ'];
/*     */       int len;
/*  71 */       while ((len = inputStream.read(bytes)) != -1) {
/*  72 */         sb.append(new String(bytes, 0, len));
/*     */       }
/*  74 */       inputStream.close();
/*  75 */       return sb.toString();
/*     */     } catch (Exception localException) {}
/*  77 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String createString(int length)
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     char[] ch = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1' };
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*  92 */     Random random = new Random();
/*  93 */     if (length > 0) {
/*  94 */       int index = 0;
/*  95 */       char[] temp = new char[length];
/*  96 */       int num = random.nextInt();
/*  97 */       for (int i = 0; i < length % 5; i++) {
/*  98 */         temp[(index++)] = ch[(num & 0x3F)];
/*  99 */         num >>= 6;
/*     */       }
/* 101 */       for (int i = 0; i < length / 5; i++) {
/* 102 */         num = random.nextInt();
/* 103 */         for (int j = 0; j < 5; j++) {
/* 104 */           temp[(index++)] = ch[(num & 0x3F)];
/* 105 */           num >>= 6;
/*     */         }
/*     */       }
/* 108 */       sb.append(new String(temp, 0, length));
/*     */     }
/* 110 */     return sb.toString();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String createString(Object[] config)
/*     */   {
/* 120 */     StringBuffer sb = new StringBuffer();
/* 121 */     for (Object key : config) {
/* 122 */       if ((key instanceof Integer)) {
/* 123 */         sb.append(createString(((Integer)key).intValue()));
/*     */       }
/* 125 */       if ((key instanceof String)) {
/* 126 */         sb.append(key);
/*     */       }
/*     */     }
/* 129 */     return sb.toString();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String createNumberString(int length)
/*     */   {
/* 138 */     StringBuffer sb = new StringBuffer();
/* 139 */     char[] ch = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '9', '8', '7', '6', '5', '4', '3', '2', '1', '0', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '9', '8', '7', '6', '5', '4', '3', '2', '1', '0', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '9', '8', '7', '6', '5', '4', '3', '2', '1', '0', '0', '1', '2', '3' };
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 144 */     Random random = new Random();
/* 145 */     if (length > 0) {
/* 146 */       int index = 0;
/* 147 */       char[] temp = new char[length];
/* 148 */       int num = random.nextInt();
/* 149 */       for (int i = 0; i < length % 5; i++) {
/* 150 */         temp[(index++)] = ch[(num & 0x3F)];
/* 151 */         num >>= 6;
/*     */       }
/* 153 */       for (int i = 0; i < length / 5; i++) {
/* 154 */         num = random.nextInt();
/* 155 */         for (int j = 0; j < 5; j++) {
/* 156 */           temp[(index++)] = ch[(num & 0x3F)];
/* 157 */           num >>= 6;
/*     */         }
/*     */       }
/* 160 */       sb.append(new String(temp, 0, length));
/*     */     }
/* 162 */     return sb.toString();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String transDataLong(long b)
/*     */   {
/* 171 */     StringBuffer sb = new StringBuffer();
/* 172 */     long KB = 1024L;
/* 173 */     long MB = KB * 1024L;
/* 174 */     long GB = MB * 1024L;
/* 175 */     long TB = GB * 1024L;
/* 176 */     if (b >= TB) {
/* 177 */       sb.append(MathUtil.doubleFormat(b / TB, 2) + "TB");
/* 178 */     } else if (b >= GB) {
/* 179 */       sb.append(MathUtil.doubleFormat(b / GB, 2) + "GB");
/* 180 */     } else if (b >= MB) {
/* 181 */       sb.append(MathUtil.doubleFormat(b / MB, 2) + "MB");
/* 182 */     } else if (b >= KB) {
/* 183 */       sb.append(MathUtil.doubleFormat(b / KB, 2) + "KB");
/*     */     } else {
/* 185 */       sb.append(b + "B");
/*     */     }
/* 187 */     return sb.toString();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static boolean isIntegerString(String str)
/*     */   {
/* 197 */     boolean flag = false;
/* 198 */     if (RegexUtil.isMatcher(str, "^-?\\d+$")) {
/* 199 */       flag = true;
/*     */     }
/* 201 */     return flag;
/*     */   }
/*     */   
/*     */   public static boolean isDoubleString(String str) {
/* 205 */     boolean flag = false;
/* 206 */     if (RegexUtil.isMatcher(str, "^(-?\\d+)(\\.\\d+)?$")) {
/* 207 */       flag = true;
/*     */     }
/* 209 */     return flag;
/*     */   }
/*     */   
/*     */   public static boolean isFloatString(String str) {
/* 213 */     boolean flag = false;
/* 214 */     if (RegexUtil.isMatcher(str, "^(-?\\d+)(\\.\\d+)?$")) {
/* 215 */       flag = true;
/*     */     }
/* 217 */     return flag;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static boolean isDateString(String str)
/*     */   {
/*     */     try
/*     */     {
/* 227 */       if (str.length() != 10) {
/* 228 */         return false;
/*     */       }
/* 230 */       new SimpleDateFormat("yyyy-MM-dd").parse(str);
/*     */     } catch (Exception e) {
/* 232 */       return false;
/*     */     }
/* 234 */     return true;
/*     */   }
/*     */   
/*     */   public static String markWithSymbol(Object obj, String symbol) {
/* 238 */     return symbol + obj.toString() + symbol;
/*     */   }
/*     */   
/*     */   public static Object[] split(String s)
/*     */   {
/* 243 */     char[] carr = s.toCharArray();
/* 244 */     Object[] arr = new Object[carr.length];
/* 245 */     for (int i = 0; i < carr.length; i++) {
/* 246 */       arr[i] = Character.valueOf(carr[i]);
/*     */     }
/* 248 */     return arr;
/*     */   }
/*     */   
/*     */   public static String substring(String s, String start, String end, boolean isInSub) {
/* 252 */     int idx = s.indexOf(start);
/* 253 */     int edx = s.indexOf(end);
/* 254 */     idx = idx == -1 ? 0 : idx + (isInSub ? 0 : start.length());
/* 255 */     edx = edx == -1 ? s.length() : edx + (isInSub ? end.length() : 0);
/* 256 */     return s.substring(idx, edx);
/*     */   }
/*     */   
/*     */   public static String doubleFormat(double d) {
/* 260 */     DecimalFormat decimalformat = new DecimalFormat("#0.00");
/* 261 */     return decimalformat.format(d);
/*     */   }
/*     */   
/*     */   public static void main(String[] args) {}
/*     */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryCapture/lotteryCaptureRepair.jar!/lottery/utils/StringUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */