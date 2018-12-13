/*     */ package com.fsy.lottery.utils.date;
/*     */ 
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.GregorianCalendar;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WeekUtil
/*     */ {
/*     */   public static int getWeekOfYear(Date date)
/*     */   {
/*  14 */     Calendar c = new GregorianCalendar();
/*  15 */     c.setFirstDayOfWeek(2);
/*  16 */     c.setMinimalDaysInFirstWeek(7);
/*  17 */     c.setTime(date);
/*  18 */     return c.get(3);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static int getMaxWeekNumOfYear(int year)
/*     */   {
/*  27 */     Calendar c = new GregorianCalendar();
/*  28 */     c.set(year, 11, 31, 23, 59, 59);
/*  29 */     return getWeekOfYear(c.getTime());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Date getFirstDayOfWeek(int year, int week)
/*     */   {
/*  39 */     Calendar c = new GregorianCalendar();
/*  40 */     c.set(1, year);
/*  41 */     c.set(2, 0);
/*  42 */     c.set(5, 1);
/*     */     
/*  44 */     Calendar cal = (GregorianCalendar)c.clone();
/*  45 */     cal.add(5, week * 7);
/*  46 */     return getFirstDayOfWeek(cal.getTime(), 2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Date getLastDayOfWeek(int year, int week)
/*     */   {
/*  56 */     Calendar c = new GregorianCalendar();
/*  57 */     c.set(1, year);
/*  58 */     c.set(2, 0);
/*  59 */     c.set(5, 1);
/*     */     
/*  61 */     Calendar cal = (GregorianCalendar)c.clone();
/*  62 */     cal.add(5, week * 7);
/*     */     
/*  64 */     return getLastDayOfWeek(cal.getTime(), 2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Date getFirstDayOfWeek(Date date, int firstDayOfWeek)
/*     */   {
/*  73 */     Calendar c = new GregorianCalendar();
/*  74 */     c.setFirstDayOfWeek(firstDayOfWeek);
/*  75 */     c.setTime(date);
/*  76 */     c.set(7, c.getFirstDayOfWeek());
/*  77 */     return c.getTime();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Date getLastDayOfWeek(Date date, int firstDayOfWeek)
/*     */   {
/*  86 */     Calendar c = new GregorianCalendar();
/*  87 */     c.setFirstDayOfWeek(firstDayOfWeek);
/*  88 */     c.setTime(date);
/*  89 */     c.set(7, c.getFirstDayOfWeek() + 6);
/*  90 */     return c.getTime();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String getFirstDayOfWeek(String time)
/*     */   {
/*  99 */     long date = DateUtil.formatTime(time, "yyyy-MM-dd");
/* 100 */     Calendar c = new GregorianCalendar();
/* 101 */     c.setFirstDayOfWeek(1);
/* 102 */     c.setTimeInMillis(date);
/* 103 */     c.set(7, c.getFirstDayOfWeek());
/* 104 */     String firstDayOfWeek = DateUtil.formatTime(c.getTime(), "yyyy-MM-dd");
/* 105 */     return firstDayOfWeek;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String getLastDayOfWeek(String time)
/*     */   {
/* 114 */     long date = DateUtil.formatTime(time, "yyyy-MM-dd");
/* 115 */     Calendar c = new GregorianCalendar();
/* 116 */     c.setFirstDayOfWeek(1);
/* 117 */     c.setTimeInMillis(date);
/* 118 */     c.set(7, c.getFirstDayOfWeek() + 6);
/* 119 */     String lastDayOfWeek = DateUtil.formatTime(c.getTime(), "yyyy-MM-dd");
/* 120 */     return lastDayOfWeek;
/*     */   }
/*     */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryCapture/lotteryCaptureRepair.jar!/lottery/utils/date/WeekUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */