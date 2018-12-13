/*     */ package javautils;
/*     */ 
/*     */ import java.io.InputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.text.DecimalFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import javautils.date.Moment;
/*     */ import javautils.math.MathUtil;
/*     */ import javautils.regex.RegexUtil;
/*     */ 
/*     */ public class StringUtil
/*     */ {
/*     */   public static boolean isNotNull(String s)
/*     */   {
/*  22 */     if (s == null) return false;
/*  23 */     if (s.trim().length() == 0) return false;
/*  24 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static boolean isInteger(String s)
/*     */   {
/*     */     try
/*     */     {
/*  34 */       Integer.parseInt(s);
/*     */     } catch (Exception e) {
/*  36 */       return false;
/*     */     }
/*  38 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static boolean isDouble(String s)
/*     */   {
/*     */     try
/*     */     {
/*  48 */       Double.parseDouble(s);
/*     */     } catch (Exception e) {
/*  50 */       return false;
/*     */     }
/*  52 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static boolean isFloat(String s)
/*     */   {
/*     */     try
/*     */     {
/*  62 */       Float.parseFloat(s);
/*     */     } catch (Exception e) {
/*  64 */       return false;
/*     */     }
/*  66 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static boolean isBoolean(String s)
/*     */   {
/*     */     try
/*     */     {
/*  76 */       Boolean.parseBoolean(s);
/*     */     } catch (Exception e) {
/*  78 */       return false;
/*     */     }
/*  80 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static boolean isShort(String s)
/*     */   {
/*     */     try
/*     */     {
/*  90 */       Short.parseShort(s);
/*     */     } catch (Exception e) {
/*  92 */       return false;
/*     */     }
/*  94 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static boolean isLong(String s)
/*     */   {
/*     */     try
/*     */     {
/* 104 */       Long.parseLong(s);
/*     */     } catch (Exception e) {
/* 106 */       return false;
/*     */     }
/* 108 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Object transObject(String s, Class<?> clazz)
/*     */   {
/* 118 */     if (clazz != null) {
/* 119 */       if (clazz == Integer.class) {
/* 120 */         if (isInteger(s)) {
/* 121 */           return Integer.valueOf(Integer.parseInt(s));
/*     */         }
/* 123 */         return Integer.valueOf(0);
/*     */       }
/* 125 */       if (clazz == Double.class) {
/* 126 */         if (isDouble(s)) {
/* 127 */           return Double.valueOf(Double.parseDouble(s));
/*     */         }
/* 129 */         return Integer.valueOf(0);
/*     */       }
/* 131 */       if (clazz == Float.class) {
/* 132 */         if (isFloat(s)) {
/* 133 */           return Float.valueOf(Float.parseFloat(s));
/*     */         }
/* 135 */         return Integer.valueOf(0);
/*     */       }
/* 137 */       if (clazz == Boolean.class) {
/* 138 */         if (isBoolean(s)) {
/* 139 */           return Boolean.valueOf(Boolean.parseBoolean(s));
/*     */         }
/* 141 */         return Boolean.valueOf(true);
/*     */       }
/* 143 */       if (clazz == Short.class) {
/* 144 */         if (isShort(s)) {
/* 145 */           return Short.valueOf(Short.parseShort(s));
/*     */         }
/* 147 */         return Integer.valueOf(0);
/*     */       }
/* 149 */       if (clazz == Long.class) {
/* 150 */         if (isLong(s)) {
/* 151 */           return Long.valueOf(Long.parseLong(s));
/*     */         }
/* 153 */         return Integer.valueOf(0);
/*     */       }
/*     */     }
/* 156 */     return s;
/*     */   }
/*     */   
/*     */   public static String transArrayToString(Object[] array) {
/* 160 */     StringBuffer sb = new StringBuffer();
/* 161 */     int i = 0; for (int j = array.length; i < j; i++) {
/* 162 */       sb.append(array[i].toString());
/* 163 */       if (i < j - 1) {
/* 164 */         sb.append(",");
/*     */       }
/*     */     }
/* 167 */     return sb.toString();
/*     */   }
/*     */   
/*     */   public static String transArrayToString(Object[] array, String p) {
/* 171 */     String tempStr = new String();
/* 172 */     for (Object value : array) {
/* 173 */       tempStr = tempStr + p + value.toString() + p + ",";
/*     */     }
/* 175 */     tempStr = tempStr.substring(0, tempStr.length() - 2);
/* 176 */     return tempStr;
/*     */   }
/*     */   
/*     */   public static int[] transStringToIntArray(String string, String regex) {
/* 180 */     if (isNotNull(string)) {
/* 181 */       String[] sArray = string.split(regex);
/* 182 */       int[] intArray = new int[sArray.length];
/* 183 */       for (int i = 0; i < sArray.length; i++) {
/* 184 */         if (isIntegerString(sArray[i])) {
/* 185 */           intArray[i] = Integer.parseInt(sArray[i]);
/*     */         }
/*     */       }
/* 188 */       return intArray;
/*     */     }
/* 190 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String transDataLong(long b)
/*     */   {
/* 199 */     StringBuffer sb = new StringBuffer();
/* 200 */     long KB = 1024L;
/* 201 */     long MB = KB * 1024L;
/* 202 */     long GB = MB * 1024L;
/* 203 */     long TB = GB * 1024L;
/* 204 */     if (b >= TB) {
/* 205 */       sb.append(MathUtil.doubleFormat(b / TB, 2) + "TB");
/* 206 */     } else if (b >= GB) {
/* 207 */       sb.append(MathUtil.doubleFormat(b / GB, 2) + "GB");
/* 208 */     } else if (b >= MB) {
/* 209 */       sb.append(MathUtil.doubleFormat(b / MB, 2) + "MB");
/* 210 */     } else if (b >= KB) {
/* 211 */       sb.append(MathUtil.doubleFormat(b / KB, 2) + "KB");
/*     */     } else {
/* 213 */       sb.append(b + "B");
/*     */     }
/* 215 */     return sb.toString();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static boolean isIntegerString(String str)
/*     */   {
/* 225 */     boolean flag = false;
/* 226 */     if (RegexUtil.isMatcher(str, "^-?\\d+$")) {
/* 227 */       flag = true;
/*     */     }
/* 229 */     return flag;
/*     */   }
/*     */   
/*     */   public static boolean isDoubleString(String str) {
/* 233 */     boolean flag = false;
/* 234 */     if (RegexUtil.isMatcher(str, "^(-?\\d+)(\\.\\d+)?$")) {
/* 235 */       flag = true;
/*     */     }
/* 237 */     return flag;
/*     */   }
/*     */   
/*     */   public static boolean isFloatString(String str) {
/* 241 */     boolean flag = false;
/* 242 */     if (RegexUtil.isMatcher(str, "^(-?\\d+)(\\.\\d+)?$")) {
/* 243 */       flag = true;
/*     */     }
/* 245 */     return flag;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static boolean isDateString(String str)
/*     */   {
/*     */     try
/*     */     {
/* 255 */       if (str.length() != 10) {
/* 256 */         return false;
/*     */       }
/* 258 */       new java.text.SimpleDateFormat("yyyy-MM-dd").parse(str);
/*     */     } catch (Exception e) {
/* 260 */       return false;
/*     */     }
/* 262 */     return true;
/*     */   }
/*     */   
/*     */   public static String markWithSymbol(Object obj, String symbol) {
/* 266 */     return symbol + obj.toString() + symbol;
/*     */   }
/*     */   
/*     */   public static Object[] split(String s)
/*     */   {
/* 271 */     char[] carr = s.toCharArray();
/* 272 */     Object[] arr = new Object[carr.length];
/* 273 */     for (int i = 0; i < carr.length; i++) {
/* 274 */       arr[i] = Character.valueOf(carr[i]);
/*     */     }
/* 276 */     return arr;
/*     */   }
/*     */   
/*     */   public static String[] splitToStr(String s) {
/* 280 */     char[] carr = s.toCharArray();
/* 281 */     String[] arr = new String[carr.length];
/* 282 */     for (int i = 0; i < carr.length; i++) {
/* 283 */       arr[i] = (carr[i] + "");
/*     */     }
/* 285 */     return arr;
/*     */   }
/*     */   
/*     */   public static String[] splitDistinct(String s) {
/* 289 */     String[] strs = splitToStr(s);
/*     */     
/* 291 */     HashSet<String> distinct = new HashSet();
/* 292 */     for (String str : strs) {
/* 293 */       distinct.add(str);
/*     */     }
/*     */     
/* 296 */     return (String[])distinct.toArray(new String[0]);
/*     */   }
/*     */   
/*     */   public static String substring(String s, String start, String end, boolean isInSub) {
/* 300 */     int idx = s.indexOf(start);
/* 301 */     int edx = s.indexOf(end);
/* 302 */     idx = idx == -1 ? 0 : idx + (isInSub ? 0 : start.length());
/* 303 */     edx = edx == -1 ? s.length() : edx + (isInSub ? end.length() : 0);
/* 304 */     return s.substring(idx, edx);
/*     */   }
/*     */   
/*     */   public static String doubleFormat(double d) {
/* 308 */     DecimalFormat decimalformat = new DecimalFormat("#0.00");
/* 309 */     return decimalformat.format(d);
/*     */   }
/*     */   
/*     */   public static String fromInputStream(InputStream inputStream) {
/*     */     try {
/* 314 */       StringBuffer sb = new StringBuffer();
/* 315 */       byte[] bytes = new byte['Ѐ'];
/*     */       int len;
/* 317 */       while ((len = inputStream.read(bytes)) != -1) {
/* 318 */         sb.append(new String(bytes, 0, len));
/*     */       }
/* 320 */       inputStream.close();
/* 321 */       return sb.toString();
/*     */     } catch (Exception localException) {}
/* 323 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static boolean isServiceTime(Moment moment, String times)
/*     */   {
/* 333 */     int thisMins = moment.hour() * 60 + moment.minute();
/* 334 */     String[] timeArr = times.split("~");
/* 335 */     int startMins = Integer.parseInt(timeArr[0].split(":")[0]) * 60 + Integer.parseInt(timeArr[0].split(":")[1]);
/* 336 */     int endMins = Integer.parseInt(timeArr[1].split(":")[0]) * 60 + Integer.parseInt(timeArr[1].split(":")[1]);
/* 337 */     if (startMins < endMins)
/*     */     {
/* 339 */       if (((thisMins > 0) && (thisMins < startMins)) || ((thisMins > endMins) && (thisMins < 1440))) {
/* 340 */         return false;
/*     */       }
/*     */       
/*     */     }
/* 344 */     else if ((thisMins > endMins) && (thisMins < startMins)) {
/* 345 */       return false;
/*     */     }
/*     */     
/* 348 */     return true;
/*     */   }
/*     */   
/*     */   public static boolean isNameChn(String name)
/*     */   {
/* 353 */     boolean result = true;
/* 354 */     int num = 0;
/* 355 */     for (int i = 0; i < name.length(); i++) {
/* 356 */       if (!java.util.regex.Pattern.matches("[一-龥]", name.substring(i, i + 1))) {
/* 357 */         num++;
/*     */       }
/*     */     }
/* 360 */     if (num > 0) {
/* 361 */       result = false;
/*     */     }
/* 363 */     return result;
/*     */   }
/*     */   
/*     */   public static String join(String str, String separator) {
/* 367 */     if (isNotNull(str)) {
/* 368 */       return org.apache.commons.lang.StringUtils.join(split(str), separator);
/*     */     }
/* 370 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String replaceAt(String str, char search, char[] withs, int[] ats)
/*     */   {
/* 382 */     if (isNotNull(str)) {
/* 383 */       char[] chars = str.toCharArray();
/* 384 */       int appearN = 0;
/* 385 */       int withN = 0;
/* 386 */       int atN = 0;
/*     */       
/* 388 */       for (int i = 0; i < chars.length; i++)
/*     */       {
/* 390 */         if ((withN >= withs.length) || (atN >= ats.length)) {
/*     */           break;
/*     */         }
/*     */         
/* 394 */         char aChar = chars[i];
/* 395 */         if (aChar == search)
/*     */         {
/* 397 */           appearN++;
/*     */           
/* 399 */           if (appearN == ats[atN]) {
/* 400 */             chars[i] = withs[(withN++)];
/* 401 */             atN++;
/*     */           }
/*     */         }
/*     */       }
/*     */       
/* 406 */       return new String(chars);
/*     */     }
/* 408 */     return str;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static Map<String, Integer> countChars(String str)
/*     */   {
/* 415 */     char[] chars = str.toCharArray();
/* 416 */     Map<String, Integer> map = new HashMap();
/*     */     
/* 418 */     for (char aChar : chars) {
/* 419 */       String _char = aChar + "";
/* 420 */       Integer count = (Integer)map.get(_char);
/* 421 */       if (count == null) {
/* 422 */         count = Integer.valueOf(0);
/*     */       }
/*     */       
/* 425 */       Integer localInteger1 = count;Integer localInteger2 = count = Integer.valueOf(count.intValue() + 1);
/* 426 */       map.put(_char, count);
/*     */     }
/*     */     
/* 429 */     Object sortedMap = new java.util.LinkedHashMap();
/* 430 */     if ((map != null) && (!map.isEmpty())) {
/* 431 */       Object entryList = new ArrayList(map.entrySet());
/* 432 */       java.util.Collections.sort((List)entryList, new java.util.Comparator<Entry<String, Integer>>()
/*     */       {
/*     */         public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
/* 435 */           return ((Integer)o2.getValue()).compareTo((Integer)o1.getValue());
/*     */         }
/* 437 */       });
/* 438 */       Object iter = ((List)entryList).iterator();
/*     */       
/* 440 */       while (((Iterator)iter).hasNext()) {
/* 441 */         Entry<String, Integer> tmpEntry = (Entry)((Iterator)iter).next();
/* 442 */         ((Map)sortedMap).put(tmpEntry.getKey(), tmpEntry.getValue());
/*     */       }
/*     */     }
/* 445 */     return (Map<String, Integer>)sortedMap;
/*     */   }
/*     */   
/*     */ 
/*     */   public static Map<String, Integer> countChars(String[] str)
/*     */   {
/* 451 */     Map<String, Integer> map = new HashMap();
/*     */     
/* 453 */     for (String aChar : str) {
/* 454 */       String _char = aChar;
/* 455 */       Integer count = (Integer)map.get(_char);
/* 456 */       if (count == null) {
/* 457 */         count = Integer.valueOf(0);
/*     */       }
/*     */       
/* 460 */       Integer localInteger1 = count;Integer localInteger2 = count = Integer.valueOf(count.intValue() + 1);
/* 461 */       map.put(_char, count);
/*     */     }
/*     */     
/* 464 */     return sortByValueMap(map);
/*     */   }
/*     */   
/*     */   private static Map<String, Integer> sortByValueMap(Map<String, Integer> map) {
/* 468 */     Map<String, Integer> sortedMap = new java.util.LinkedHashMap();
/* 469 */     if ((map != null) && (!map.isEmpty())) {
/* 470 */       List<Entry<String, Integer>> entryList = new ArrayList(map.entrySet());
/* 471 */       java.util.Collections.sort(entryList, new java.util.Comparator<Entry<String, Integer>>()
/*     */       {
/*     */         public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
/* 474 */           return ((Integer)o2.getValue()).compareTo((Integer)o1.getValue());
/*     */         }
/* 476 */       });
/* 477 */       Iterator<Entry<String, Integer>> iter = entryList.iterator();
/*     */       
/* 479 */       while (iter.hasNext()) {
/* 480 */         Entry<String, Integer> tmpEntry = (Entry)iter.next();
/* 481 */         sortedMap.put(tmpEntry.getKey(), tmpEntry.getValue());
/*     */       }
/*     */     }
/* 484 */     return sortedMap;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/* 496 */     Map<String, Integer> stringIntegerMap = countChars(new String[] { "2", "1", "2", "1", "1" });
/* 497 */     Collection<Integer> values = stringIntegerMap.values();
/* 498 */     Integer[] integers = (Integer[])values.toArray(new Integer[0]);
/* 499 */     System.out.println(integers[0]);
/* 500 */     System.out.println(integers[1]);
/*     */   }
/*     */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/javautils/StringUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */