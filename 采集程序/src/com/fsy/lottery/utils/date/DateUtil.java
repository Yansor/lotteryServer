/*     */ package com.fsy.lottery.utils.date;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.text.DateFormat;
/*     */ import java.text.ParseException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.GregorianCalendar;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DateUtil
/*     */ {
/*     */   public static String getCurrentTime()
/*     */   {
/*  17 */     GregorianCalendar g = new GregorianCalendar();
/*  18 */     return dateToString(g);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String getCurrentDate()
/*     */   {
/*  26 */     GregorianCalendar g = new GregorianCalendar();
/*  27 */     return dateToStringSim(g);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String getYesterday()
/*     */   {
/*  35 */     GregorianCalendar g = new GregorianCalendar();
/*  36 */     g.set(5, g.get(5) - 1);
/*  37 */     return dateToStringSim(g);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String getTomorrow()
/*     */   {
/*  45 */     GregorianCalendar g = new GregorianCalendar();
/*  46 */     g.set(5, g.get(5) + 1);
/*  47 */     return dateToStringSim(g);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String getTime(long ms)
/*     */   {
/*  56 */     GregorianCalendar g = new GregorianCalendar();
/*  57 */     g.setTimeInMillis(ms);
/*  58 */     return dateToString(g);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static GregorianCalendar getCalendarByTime(String time, String format)
/*     */   {
/*  67 */     GregorianCalendar g = new GregorianCalendar();
/*     */     try {
/*  69 */       g.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time));
/*     */     } catch (ParseException e) {
/*  71 */       g = null;
/*     */     }
/*  73 */     return g;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static String calcDate(GregorianCalendar g, int seconds)
/*     */   {
/*  83 */     if (g == null) {
/*  84 */       g = new GregorianCalendar();
/*     */     }
/*  86 */     g.add(13, seconds);
/*  87 */     return dateToString(g);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String dateToString(Date date)
/*     */   {
/*     */     try
/*     */     {
/*  97 */       DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
/*  98 */       return formatter.format(date);
/*     */     }
/*     */     catch (Exception e) {
/* 101 */       e.printStackTrace();
/*     */     }
/* 103 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Date stringToDate(String date)
/*     */   {
/*     */     try
/*     */     {
/* 113 */       DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
/* 114 */       return formatter.parse(date);
/*     */     }
/*     */     catch (Exception e) {
/* 117 */       e.printStackTrace();
/*     */     }
/* 119 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Date stringToDate(String date, String format)
/*     */   {
/*     */     try
/*     */     {
/* 130 */       DateFormat formatter = new SimpleDateFormat(format);
/* 131 */       return formatter.parse(date);
/*     */     }
/*     */     catch (Exception e) {
/* 134 */       e.printStackTrace();
/*     */     }
/* 136 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static long calcDate(String subDate, String minDate)
/*     */   {
/* 146 */     long lSubDate = getCalendarByTime(subDate, "yyyy-MM-dd HH:mm:ss").getTimeInMillis();
/* 147 */     long lMinDate = getCalendarByTime(minDate, "yyyy-MM-dd HH:mm:ss").getTimeInMillis();
/* 148 */     return lSubDate - lMinDate;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String calcDateByTime(String time, int seconds)
/*     */   {
/* 158 */     GregorianCalendar g = getCalendarByTime(time, "yyyy-MM-dd HH:mm:ss");
/* 159 */     return calcDate(g, seconds);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String calcNewDay(String date, int days)
/*     */   {
/* 169 */     GregorianCalendar g = new GregorianCalendar();
/* 170 */     g.setTimeInMillis(formatTime(date, "yyyy-MM-dd"));
/* 171 */     g.set(5, g.get(5) + days);
/* 172 */     return dateToStringSim(g);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String calcNextDay(String date)
/*     */   {
/* 181 */     GregorianCalendar g = new GregorianCalendar();
/* 182 */     g.setTimeInMillis(formatTime(date, "yyyy-MM-dd"));
/* 183 */     g.set(5, g.get(5) + 1);
/* 184 */     return dateToStringSim(g);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String calcLastDay(String date)
/*     */   {
/* 193 */     GregorianCalendar g = new GregorianCalendar();
/* 194 */     g.setTimeInMillis(formatTime(date, "yyyy-MM-dd"));
/* 195 */     g.set(5, g.get(5) - 1);
/* 196 */     return dateToStringSim(g);
/*     */   }
/*     */   
/*     */   private static Calendar getDateOfMonth(Calendar date, int num, boolean flag) {
/* 200 */     Calendar lastDate = (Calendar)date.clone();
/* 201 */     if (flag) {
/* 202 */       lastDate.add(2, num);
/*     */     } else {
/* 204 */       lastDate.add(2, -num);
/*     */     }
/*     */     
/* 207 */     return lastDate;
/*     */   }
/*     */   
/*     */   private static Calendar getDateOfLastMonth(String dateStr, int num, boolean flag) {
/* 211 */     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
/*     */     try {
/* 213 */       Date date = sdf.parse(dateStr);
/* 214 */       Calendar c = Calendar.getInstance();
/* 215 */       c.setTime(date);
/* 216 */       return getDateOfMonth(c, num, flag);
/*     */     } catch (Exception e) {
/* 218 */       throw new IllegalArgumentException("Invalid date format(yyyyMMdd): " + dateStr);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String getSameDateOfLastMonth(String date, int num, boolean flag)
/*     */   {
/* 231 */     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
/* 232 */     String lastDate = sdf.format(getDateOfLastMonth(date, num, flag).getTime());
/* 233 */     return lastDate;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static String dateToString(GregorianCalendar g)
/*     */   {
/* 242 */     String year = String.valueOf(g.get(1));
/* 243 */     String month = String.format("%02d", new Object[] { Integer.valueOf(g.get(2) + 1) });
/* 244 */     String day = String.format("%02d", new Object[] { Integer.valueOf(g.get(5)) });
/* 245 */     String hours = String.format("%02d", new Object[] { Integer.valueOf(g.get(11)) });
/* 246 */     String minutes = String.format("%02d", new Object[] { Integer.valueOf(g.get(12)) });
/* 247 */     String seconds = String.format("%02d", new Object[] { Integer.valueOf(g.get(13)) });
/* 248 */     return year + "-" + month + "-" + day + " " + hours + ":" + minutes + ":" + seconds;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static long stringToLong(String time, String format)
/*     */   {
/* 258 */     return stringToDate(time, format).getTime();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String dateToStringSim(GregorianCalendar g)
/*     */   {
/* 267 */     String year = String.valueOf(g.get(1));
/* 268 */     String month = String.format("%02d", new Object[] { Integer.valueOf(g.get(2) + 1) });
/* 269 */     String day = String.format("%02d", new Object[] { Integer.valueOf(g.get(5)) });
/* 270 */     return year + "-" + month + "-" + day;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String getCurTimeStr()
/*     */   {
/* 278 */     GregorianCalendar g = new GregorianCalendar();
/* 279 */     String year = String.valueOf(g.get(1));
/* 280 */     String month = String.format("%02d", new Object[] { Integer.valueOf(g.get(2) + 1) });
/* 281 */     String day = String.format("%02d", new Object[] { Integer.valueOf(g.get(5)) });
/* 282 */     String hours = String.format("%02d", new Object[] { Integer.valueOf(g.get(11)) });
/* 283 */     String minutes = String.format("%02d", new Object[] { Integer.valueOf(g.get(12)) });
/* 284 */     String seconds = String.format("%02d", new Object[] { Integer.valueOf(g.get(13)) });
/*     */     
/* 286 */     return year + month + day + hours + minutes + seconds;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String formatTime(String time, String oldFormat, String newFormat)
/*     */   {
/* 298 */     return new SimpleDateFormat(newFormat).format(stringToDate(time, oldFormat));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String formatTime(Date date, String format)
/*     */   {
/* 309 */     return new SimpleDateFormat(format).format(date);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String formatTime(long time, String format)
/*     */   {
/* 319 */     return formatTime(getTime(time), "yyyy-MM-dd HH:mm:ss", format);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static long formatTime(String time, String format)
/*     */   {
/* 329 */     return stringToDate(time, format).getTime();
/*     */   }
/*     */   
/*     */   public static String dateForm(String date, String config) {
/* 333 */     if ("MM/dd/yyyy".equals(config)) {
/* 334 */       String[] dateStrs = date.split("/");
/* 335 */       return dateStrs[2] + "-" + dateStrs[0] + "-" + dateStrs[1];
/*     */     }
/* 337 */     if ("MM-dd-yyyy".equals(config)) {
/* 338 */       String[] dateStrs = date.split("-");
/* 339 */       return dateStrs[2] + "-" + dateStrs[0] + "-" + dateStrs[1];
/*     */     }
/* 341 */     return null;
/*     */   }
/*     */   
/*     */   public static int getYear(String time) {
/* 345 */     return Integer.parseInt(time.substring(0, 4));
/*     */   }
/*     */   
/*     */   public static int getMonth(String time) {
/* 349 */     return Integer.parseInt(time.substring(5, 7));
/*     */   }
/*     */   
/*     */   public static int getDay(String time) {
/* 353 */     return Integer.parseInt(time.substring(8, 10));
/*     */   }
/*     */   
/*     */   public static int getHours(String time) {
/* 357 */     return Integer.parseInt(time.substring(11, 13));
/*     */   }
/*     */   
/*     */   public static int getMinutes(String time) {
/* 361 */     return Integer.parseInt(time.substring(14, 16));
/*     */   }
/*     */   
/*     */   public static int getSeconds(String time) {
/* 365 */     return Integer.parseInt(time.substring(17));
/*     */   }
/*     */   
/*     */   public static int getYear() {
/* 369 */     GregorianCalendar g = new GregorianCalendar();
/* 370 */     return g.get(1);
/*     */   }
/*     */   
/*     */   public static int getMonth() {
/* 374 */     GregorianCalendar g = new GregorianCalendar();
/* 375 */     return g.get(2) + 1;
/*     */   }
/*     */   
/*     */   public static int getDay() {
/* 379 */     GregorianCalendar g = new GregorianCalendar();
/* 380 */     return g.get(5);
/*     */   }
/*     */   
/*     */   public static int getHours() {
/* 384 */     GregorianCalendar g = new GregorianCalendar();
/* 385 */     return g.get(11);
/*     */   }
/*     */   
/*     */   public static int getMinutes() {
/* 389 */     GregorianCalendar g = new GregorianCalendar();
/* 390 */     return g.get(12);
/*     */   }
/*     */   
/*     */   public static int getSeconds() {
/* 394 */     GregorianCalendar g = new GregorianCalendar();
/* 395 */     return g.get(13);
/*     */   }
/*     */   
/*     */   public static void main(String[] args) {
/* 399 */     System.out.println("年:" + getYear());
/* 400 */     System.out.println("月:" + getMonth());
/* 401 */     System.out.println("日:" + getDay());
/* 402 */     System.out.println("时:" + getHours());
/* 403 */     System.out.println("分:" + getMinutes());
/* 404 */     System.out.println("秒:" + getSeconds());
/*     */   }
/*     */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryCapture/lotteryCaptureRepair.jar!/lottery/utils/date/DateUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */