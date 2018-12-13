/*     */ package javautils.date;
/*     */ 
/*     */ import java.text.DateFormat;
/*     */ import java.text.ParseException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.GregorianCalendar;
/*     */ 
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
/*     */   public static int calcDays(Date date1, Date date2)
/*     */   {
/* 158 */     Calendar date1Calendar = Calendar.getInstance();
/* 159 */     date1Calendar.setTime(date1);
/*     */     
/* 161 */     Calendar date2Calendar = Calendar.getInstance();
/* 162 */     date2Calendar.setTime(date2);
/*     */     
/* 164 */     int day1 = date1Calendar.get(6);
/* 165 */     int day2 = date2Calendar.get(6);
/*     */     
/* 167 */     return day1 - day2;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String calcDateByTime(String time, int seconds)
/*     */   {
/* 177 */     GregorianCalendar g = getCalendarByTime(time, "yyyy-MM-dd HH:mm:ss");
/* 178 */     return calcDate(g, seconds);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String calcNewDay(String date, int days)
/*     */   {
/* 188 */     GregorianCalendar g = new GregorianCalendar();
/* 189 */     g.setTimeInMillis(formatTime(date, "yyyy-MM-dd"));
/* 190 */     g.set(5, g.get(5) + days);
/* 191 */     return dateToStringSim(g);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String calcNextDay(String date)
/*     */   {
/* 200 */     GregorianCalendar g = new GregorianCalendar();
/* 201 */     g.setTimeInMillis(formatTime(date, "yyyy-MM-dd"));
/* 202 */     g.set(5, g.get(5) + 1);
/* 203 */     return dateToStringSim(g);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String calcLastDay(String date)
/*     */   {
/* 212 */     GregorianCalendar g = new GregorianCalendar();
/* 213 */     g.setTimeInMillis(formatTime(date, "yyyy-MM-dd"));
/* 214 */     g.set(5, g.get(5) - 1);
/* 215 */     return dateToStringSim(g);
/*     */   }
/*     */   
/*     */   private static Calendar getDateOfMonth(Calendar date, int num, boolean flag) {
/* 219 */     Calendar lastDate = (Calendar)date.clone();
/* 220 */     if (flag) {
/* 221 */       lastDate.add(2, num);
/*     */     } else {
/* 223 */       lastDate.add(2, -num);
/*     */     }
/*     */     
/* 226 */     return lastDate;
/*     */   }
/*     */   
/*     */   private static Calendar getDateOfLastMonth(String dateStr, int num, boolean flag) {
/* 230 */     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
/*     */     try {
/* 232 */       Date date = sdf.parse(dateStr);
/* 233 */       Calendar c = Calendar.getInstance();
/* 234 */       c.setTime(date);
/* 235 */       return getDateOfMonth(c, num, flag);
/*     */     } catch (Exception e) {
/* 237 */       throw new IllegalArgumentException("Invalid date format(yyyyMMdd): " + dateStr);
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
/* 250 */     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
/* 251 */     String lastDate = sdf.format(getDateOfLastMonth(date, num, flag).getTime());
/* 252 */     return lastDate;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static String dateToString(GregorianCalendar g)
/*     */   {
/* 261 */     String year = String.valueOf(g.get(1));
/* 262 */     String month = String.format("%02d", new Object[] { Integer.valueOf(g.get(2) + 1) });
/* 263 */     String day = String.format("%02d", new Object[] { Integer.valueOf(g.get(5)) });
/* 264 */     String hours = String.format("%02d", new Object[] { Integer.valueOf(g.get(11)) });
/* 265 */     String minutes = String.format("%02d", new Object[] { Integer.valueOf(g.get(12)) });
/* 266 */     String seconds = String.format("%02d", new Object[] { Integer.valueOf(g.get(13)) });
/* 267 */     return year + "-" + month + "-" + day + " " + hours + ":" + minutes + ":" + seconds;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static long stringToLong(String time, String format)
/*     */   {
/* 277 */     return stringToDate(time, format).getTime();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String dateToStringSim(GregorianCalendar g)
/*     */   {
/* 286 */     String year = String.valueOf(g.get(1));
/* 287 */     String month = String.format("%02d", new Object[] { Integer.valueOf(g.get(2) + 1) });
/* 288 */     String day = String.format("%02d", new Object[] { Integer.valueOf(g.get(5)) });
/* 289 */     return year + "-" + month + "-" + day;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String getCurTimeStr()
/*     */   {
/* 297 */     GregorianCalendar g = new GregorianCalendar();
/* 298 */     String year = String.valueOf(g.get(1));
/* 299 */     String month = String.format("%02d", new Object[] { Integer.valueOf(g.get(2) + 1) });
/* 300 */     String day = String.format("%02d", new Object[] { Integer.valueOf(g.get(5)) });
/* 301 */     String hours = String.format("%02d", new Object[] { Integer.valueOf(g.get(11)) });
/* 302 */     String minutes = String.format("%02d", new Object[] { Integer.valueOf(g.get(12)) });
/* 303 */     String seconds = String.format("%02d", new Object[] { Integer.valueOf(g.get(13)) });
/*     */     
/* 305 */     return year + month + day + hours + minutes + seconds;
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
/* 317 */     return new SimpleDateFormat(newFormat).format(stringToDate(time, oldFormat));
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
/* 328 */     return new SimpleDateFormat(format).format(date);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String formatTime(long time, String format)
/*     */   {
/* 338 */     return formatTime(getTime(time), "yyyy-MM-dd HH:mm:ss", format);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static long formatTime(String time, String format)
/*     */   {
/* 348 */     return stringToDate(time, format).getTime();
/*     */   }
/*     */   
/*     */   public static String dateForm(String date, String config) {
/* 352 */     if ("MM/dd/yyyy".equals(config)) {
/* 353 */       String[] dateStrs = date.split("/");
/* 354 */       return dateStrs[2] + "-" + dateStrs[0] + "-" + dateStrs[1];
/*     */     }
/* 356 */     if ("MM-dd-yyyy".equals(config)) {
/* 357 */       String[] dateStrs = date.split("-");
/* 358 */       return dateStrs[2] + "-" + dateStrs[0] + "-" + dateStrs[1];
/*     */     }
/* 360 */     return null;
/*     */   }
/*     */   
/*     */   public static int getYear(String time) {
/* 364 */     return Integer.parseInt(time.substring(0, 4));
/*     */   }
/*     */   
/*     */   public static int getMonth(String time) {
/* 368 */     return Integer.parseInt(time.substring(5, 7));
/*     */   }
/*     */   
/*     */   public static int getDay(String time) {
/* 372 */     return Integer.parseInt(time.substring(8, 10));
/*     */   }
/*     */   
/*     */   public static int getHours(String time) {
/* 376 */     return Integer.parseInt(time.substring(11, 13));
/*     */   }
/*     */   
/*     */   public static int getMinutes(String time) {
/* 380 */     return Integer.parseInt(time.substring(14, 16));
/*     */   }
/*     */   
/*     */   public static int getSeconds(String time) {
/* 384 */     return Integer.parseInt(time.substring(17));
/*     */   }
/*     */   
/*     */   public static int getYear() {
/* 388 */     GregorianCalendar g = new GregorianCalendar();
/* 389 */     return g.get(1);
/*     */   }
/*     */   
/*     */   public static int getMonth() {
/* 393 */     GregorianCalendar g = new GregorianCalendar();
/* 394 */     return g.get(2) + 1;
/*     */   }
/*     */   
/*     */   public static int getDay() {
/* 398 */     GregorianCalendar g = new GregorianCalendar();
/* 399 */     return g.get(5);
/*     */   }
/*     */   
/*     */   public static int getHours() {
/* 403 */     GregorianCalendar g = new GregorianCalendar();
/* 404 */     return g.get(11);
/*     */   }
/*     */   
/*     */   public static int getMinutes() {
/* 408 */     GregorianCalendar g = new GregorianCalendar();
/* 409 */     return g.get(12);
/*     */   }
/*     */   
/*     */   public static int getSeconds() {
/* 413 */     GregorianCalendar g = new GregorianCalendar();
/* 414 */     return g.get(13);
/*     */   }
/*     */   
/*     */   public static void main(String[] args) {}
/*     */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/javautils/date/DateUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */