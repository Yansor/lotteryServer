/*     */ package com.fsy.javautils;
/*     */ 
/*     */ import java.io.InputStream;
/*     */ import java.text.DecimalFormat;
/*     */ import java.text.SimpleDateFormat;
/*     */ import com.fsy.javautils.date.Moment;
/*     */ import com.fsy.javautils.math.MathUtil;
/*     */ import com.fsy.javautils.regex.RegexUtil;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   public static boolean isBoolean(String s)
/*     */   {
/*     */     try
/*     */     {
/*  73 */       Boolean.parseBoolean(s);
/*     */     } catch (Exception e) {
/*  75 */       return false;
/*     */     }
/*  77 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static boolean isShort(String s)
/*     */   {
/*     */     try
/*     */     {
/*  87 */       Short.parseShort(s);
/*     */     } catch (Exception e) {
/*  89 */       return false;
/*     */     }
/*  91 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static boolean isLong(String s)
/*     */   {
/*     */     try
/*     */     {
/* 101 */       Long.parseLong(s);
/*     */     } catch (Exception e) {
/* 103 */       return false;
/*     */     }
/* 105 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Object transObject(String s, Class<?> clazz)
/*     */   {
/* 115 */     if (clazz != null) {
/* 116 */       if (clazz == Integer.class) {
/* 117 */         if (isInteger(s)) {
/* 118 */           return Integer.valueOf(Integer.parseInt(s));
/*     */         }
/* 120 */         return Integer.valueOf(0);
/*     */       }
/* 122 */       if (clazz == Double.class) {
/* 123 */         if (isDouble(s)) {
/* 124 */           return Double.valueOf(Double.parseDouble(s));
/*     */         }
/* 126 */         return Integer.valueOf(0);
/*     */       }
/* 128 */       if (clazz == Float.class) {
/* 129 */         if (isFloat(s)) {
/* 130 */           return Float.valueOf(Float.parseFloat(s));
/*     */         }
/* 132 */         return Integer.valueOf(0);
/*     */       }
/* 134 */       if (clazz == Boolean.class) {
/* 135 */         if (isBoolean(s)) {
/* 136 */           return Boolean.valueOf(Boolean.parseBoolean(s));
/*     */         }
/* 138 */         return Boolean.valueOf(true);
/*     */       }
/* 140 */       if (clazz == Short.class) {
/* 141 */         if (isShort(s)) {
/* 142 */           return Short.valueOf(Short.parseShort(s));
/*     */         }
/* 144 */         return Integer.valueOf(0);
/*     */       }
/* 146 */       if (clazz == Long.class) {
/* 147 */         if (isLong(s)) {
/* 148 */           return Long.valueOf(Long.parseLong(s));
/*     */         }
/* 150 */         return Integer.valueOf(0);
/*     */       }
/*     */     }
/* 153 */     return s;
/*     */   }
/*     */   
/*     */   public static String transArrayToString(Object[] array) {
/* 157 */     StringBuffer sb = new StringBuffer();
/* 158 */     int i = 0; for (int j = array.length; i < j; i++) {
/* 159 */       sb.append(array[i].toString());
/* 160 */       if (i < j - 1) {
/* 161 */         sb.append(",");
/*     */       }
/*     */     }
/* 164 */     return sb.toString();
/*     */   }
/*     */   
/*     */   public static String transArrayToString(Object[] array, String p) {
/* 168 */     String tempStr = new String();
/* 169 */     for (Object value : array) {
/* 170 */       tempStr = tempStr + p + value.toString() + p + ",";
/*     */     }
/* 172 */     tempStr = tempStr.substring(0, tempStr.length() - 2);
/* 173 */     return tempStr;
/*     */   }
/*     */   
/*     */   public static int[] transStringToIntArray(String string, String regex) {
/* 177 */     if (isNotNull(string)) {
/* 178 */       String[] sArray = string.split(regex);
/* 179 */       int[] intArray = new int[sArray.length];
/* 180 */       for (int i = 0; i < sArray.length; i++) {
/* 181 */         if (isIntegerString(sArray[i])) {
/* 182 */           intArray[i] = Integer.parseInt(sArray[i]);
/*     */         }
/*     */       }
/* 185 */       return intArray;
/*     */     }
/* 187 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String transDataLong(long b)
/*     */   {
/* 196 */     StringBuffer sb = new StringBuffer();
/* 197 */     long KB = 1024L;
/* 198 */     long MB = KB * 1024L;
/* 199 */     long GB = MB * 1024L;
/* 200 */     long TB = GB * 1024L;
/* 201 */     if (b >= TB) {
/* 202 */       sb.append(MathUtil.doubleFormat(b / TB, 2) + "TB");
/* 203 */     } else if (b >= GB) {
/* 204 */       sb.append(MathUtil.doubleFormat(b / GB, 2) + "GB");
/* 205 */     } else if (b >= MB) {
/* 206 */       sb.append(MathUtil.doubleFormat(b / MB, 2) + "MB");
/* 207 */     } else if (b >= KB) {
/* 208 */       sb.append(MathUtil.doubleFormat(b / KB, 2) + "KB");
/*     */     } else {
/* 210 */       sb.append(b + "B");
/*     */     }
/* 212 */     return sb.toString();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static boolean isIntegerString(String str)
/*     */   {
/* 222 */     boolean flag = false;
/* 223 */     if (RegexUtil.isMatcher(str, "^-?\\d+$")) {
/* 224 */       flag = true;
/*     */     }
/* 226 */     return flag;
/*     */   }
/*     */   
/*     */   public static boolean isDoubleString(String str) {
/* 230 */     boolean flag = false;
/* 231 */     if (RegexUtil.isMatcher(str, "^(-?\\d+)(\\.\\d+)?$")) {
/* 232 */       flag = true;
/*     */     }
/* 234 */     return flag;
/*     */   }
/*     */   
/*     */   public static boolean isFloatString(String str) {
/* 238 */     boolean flag = false;
/* 239 */     if (RegexUtil.isMatcher(str, "^(-?\\d+)(\\.\\d+)?$")) {
/* 240 */       flag = true;
/*     */     }
/* 242 */     return flag;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static boolean isDateString(String str)
/*     */   {
/*     */     try
/*     */     {
/* 252 */       if (str.length() != 10) {
/* 253 */         return false;
/*     */       }
/* 255 */       new SimpleDateFormat("yyyy-MM-dd").parse(str);
/*     */     } catch (Exception e) {
/* 257 */       return false;
/*     */     }
/* 259 */     return true;
/*     */   }
/*     */   
/*     */   public static String markWithSymbol(Object obj, String symbol) {
/* 263 */     return symbol + obj.toString() + symbol;
/*     */   }
/*     */   
/*     */   public static Object[] split(String s)
/*     */   {
/* 268 */     char[] carr = s.toCharArray();
/* 269 */     Object[] arr = new Object[carr.length];
/* 270 */     for (int i = 0; i < carr.length; i++) {
/* 271 */       arr[i] = Character.valueOf(carr[i]);
/*     */     }
/* 273 */     return arr;
/*     */   }
/*     */   
/*     */   public static String substring(String s, String start, String end, boolean isInSub) {
/* 277 */     int idx = s.indexOf(start);
/* 278 */     int edx = s.indexOf(end);
/* 279 */     idx = idx == -1 ? 0 : idx + (isInSub ? 0 : start.length());
/* 280 */     edx = edx == -1 ? s.length() : edx + (isInSub ? end.length() : 0);
/* 281 */     return s.substring(idx, edx);
/*     */   }
/*     */   
/*     */   public static String doubleFormat(double d) {
/* 285 */     DecimalFormat decimalformat = new DecimalFormat("#0.00");
/* 286 */     return decimalformat.format(d);
/*     */   }
/*     */   
/*     */   public static String fromInputStream(InputStream inputStream) {
/*     */     try {
/* 291 */       StringBuffer sb = new StringBuffer();
/* 292 */       byte[] bytes = new byte['Ѐ'];
/*     */       int len;
/* 294 */       while ((len = inputStream.read(bytes)) != -1) {
/* 295 */         sb.append(new String(bytes, 0, len));
/*     */       }
/* 297 */       inputStream.close();
/* 298 */       return sb.toString();
/*     */     } catch (Exception localException) {}
/* 300 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static boolean isServiceTime(Moment moment, String times)
/*     */   {
/* 310 */     int thisMins = moment.hour() * 60 + moment.minute();
/* 311 */     String[] timeArr = times.split("~");
/* 312 */     int startMins = Integer.parseInt(timeArr[0].split(":")[0]) * 60 + Integer.parseInt(timeArr[0].split(":")[1]);
/* 313 */     int endMins = Integer.parseInt(timeArr[1].split(":")[0]) * 60 + Integer.parseInt(timeArr[1].split(":")[1]);
/* 314 */     if (startMins < endMins)
/*     */     {
/* 316 */       if (((thisMins > 0) && (thisMins < startMins)) || ((thisMins > endMins) && (thisMins < 1440))) {
/* 317 */         return false;
/*     */       }
/*     */       
/*     */     }
/* 321 */     else if ((thisMins > endMins) && (thisMins < startMins)) {
/* 322 */       return false;
/*     */     }
/*     */     
/* 325 */     return true;
/*     */   }
/*     */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryCapture/lotteryCaptureRepair.jar!/com.fsy.javautils/StringUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */