/*     */ package com.fsy.javautils.date;
/*     */ 
/*     */ import java.text.DateFormat;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.GregorianCalendar;
/*     */ 
/*     */ public class Moment
/*     */ {
/*     */   public GregorianCalendar gc;
/*     */   
/*     */   public static void main(String[] args)
/*     */   {
/*  13 */     Moment moment = new Moment();
/*  14 */     System.out.println(moment.difference(new Moment()
/*  15 */       .fromDate("2015-01-18"), "hour"));
/*     */   }
/*     */   
/*     */   static {
/*  19 */     java.util.TimeZone china = java.util.TimeZone.getTimeZone("Asia/Shanghai");
/*  20 */     java.util.TimeZone.setDefault(china);
/*     */   }
/*     */   
/*     */ 
/*     */   public Moment()
/*     */   {
/*  26 */     this.gc = new GregorianCalendar();
/*  27 */     this.gc.setFirstDayOfWeek(2);
/*     */   }
/*     */   
/*     */   public Moment(GregorianCalendar gc) {
/*  31 */     this.gc = gc;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Moment fromDate(String date)
/*     */   {
/*     */     try
/*     */     {
/*  42 */       DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
/*  43 */       java.util.Date d = format.parse(date);
/*  44 */       this.gc.setTime(d);
/*     */     }
/*     */     catch (Exception localException) {}
/*  47 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Moment fromTime(String time)
/*     */   {
/*     */     try
/*     */     {
/*  58 */       DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
/*  59 */       java.util.Date d = format.parse(time);
/*  60 */       this.gc.setTime(d);
/*     */     }
/*     */     catch (Exception localException) {}
/*  63 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Moment fromTime(String time, String formatStr)
/*     */   {
/*     */     try
/*     */     {
/*  75 */       DateFormat format = new SimpleDateFormat(formatStr);
/*  76 */       java.util.Date d = format.parse(time);
/*  77 */       this.gc.setTime(d);
/*     */     }
/*     */     catch (Exception localException) {}
/*  80 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toSimpleTime()
/*     */   {
/*  89 */     return 
/*  90 */       year() + "-" + months() + "-" + days() + " " + hours() + ":" + minutes() + ":" + seconds();
/*     */   }
/*     */   
/*     */   public String toCurTime() {
/*  94 */     return hours() + ":" + minutes() + ":" + seconds();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toFullTime()
/*     */   {
/* 103 */     return toSimpleTime() + "." + milliseconds();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toSimpleDate()
/*     */   {
/* 112 */     return year() + "-" + months() + "-" + days();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public java.util.Date toDate()
/*     */   {
/* 121 */     return this.gc.getTime();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String format(String format)
/*     */   {
/* 131 */     return new SimpleDateFormat(format).format(toDate());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int year()
/*     */   {
/* 141 */     return this.gc.get(1);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Moment year(int year)
/*     */   {
/* 150 */     this.gc.set(1, year);
/* 151 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int month()
/*     */   {
/* 160 */     return this.gc.get(2) + 1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String months()
/*     */   {
/* 169 */     return String.format("%02d", new Object[] { Integer.valueOf(month()) });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Moment month(int month)
/*     */   {
/* 179 */     if ((month > 0) && (month <= 12)) {
/* 180 */       this.gc.set(2, month - 1);
/*     */     }
/* 182 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Moment month(String month)
/*     */   {
/* 192 */     month(Integer.parseInt(month));
/* 193 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int day()
/*     */   {
/* 202 */     return this.gc.get(5);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String days()
/*     */   {
/* 211 */     return String.format("%02d", new Object[] { Integer.valueOf(day()) });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Moment day(int day)
/*     */   {
/* 221 */     if ((day > 0) && (day <= 31)) {
/* 222 */       this.gc.set(5, day);
/*     */     }
/* 224 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Moment day(String day)
/*     */   {
/* 234 */     day(Integer.parseInt(day));
/* 235 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int hour()
/*     */   {
/* 244 */     return this.gc.get(11);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String hours()
/*     */   {
/* 253 */     return String.format("%02d", new Object[] { Integer.valueOf(hour()) });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Moment hour(int hour)
/*     */   {
/* 263 */     if ((hour >= 0) && (hour < 24)) {
/* 264 */       this.gc.set(11, hour);
/*     */     }
/* 266 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Moment hour(String hour)
/*     */   {
/* 276 */     hour(Integer.parseInt(hour));
/* 277 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int minute()
/*     */   {
/* 286 */     return this.gc.get(12);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String minutes()
/*     */   {
/* 295 */     return String.format("%02d", new Object[] { Integer.valueOf(minute()) });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Moment minute(int minute)
/*     */   {
/* 305 */     if ((minute >= 0) && (minute < 60)) {
/* 306 */       this.gc.set(12, minute);
/*     */     }
/* 308 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Moment minute(String minute)
/*     */   {
/* 318 */     minute(Integer.parseInt(minute));
/* 319 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int second()
/*     */   {
/* 328 */     return this.gc.get(13);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String seconds()
/*     */   {
/* 337 */     return String.format("%02d", new Object[] { Integer.valueOf(second()) });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Moment second(int second)
/*     */   {
/* 347 */     if ((second >= 0) && (second < 60)) {
/* 348 */       this.gc.set(13, second);
/*     */     }
/* 350 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Moment second(String second)
/*     */   {
/* 360 */     second(Integer.parseInt(second));
/* 361 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int millisecond()
/*     */   {
/* 370 */     return this.gc.get(14);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String milliseconds()
/*     */   {
/* 379 */     return String.format("%03d", new Object[] { Integer.valueOf(millisecond()) });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Moment millisecond(int millisecond)
/*     */   {
/* 389 */     if ((millisecond >= 0) && (millisecond < 1000)) {
/* 390 */       this.gc.set(14, millisecond);
/*     */     }
/* 392 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Moment millisecond(String millisecond)
/*     */   {
/* 402 */     millisecond(Integer.parseInt(millisecond));
/* 403 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int weekOfMonth()
/*     */   {
/* 412 */     return this.gc.get(4);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int weekOfYear()
/*     */   {
/* 421 */     return this.gc.get(3);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int dayOfWeek()
/*     */   {
/* 430 */     int field = this.gc.get(7);
/* 431 */     switch (field) {
/*     */     case 1: 
/* 433 */       return 7;
/*     */     case 2: 
/* 435 */       return 1;
/*     */     case 3: 
/* 437 */       return 2;
/*     */     case 4: 
/* 439 */       return 3;
/*     */     case 5: 
/* 441 */       return 4;
/*     */     case 6: 
/* 443 */       return 5;
/*     */     case 7: 
/* 445 */       return 6;
/*     */     }
/*     */     
/*     */     
/* 449 */     return this.gc.getFirstDayOfWeek();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Moment dayOfWeek(int week)
/*     */   {
/* 459 */     switch (week) {
/*     */     case 7: 
/* 461 */       this.gc.set(7, 1);
/* 462 */       break;
/*     */     case 1: 
/* 464 */       this.gc.set(7, 2);
/* 465 */       break;
/*     */     case 2: 
/* 467 */       this.gc.set(7, 3);
/* 468 */       break;
/*     */     case 3: 
/* 470 */       this.gc.set(7, 4);
/* 471 */       break;
/*     */     case 4: 
/* 473 */       this.gc.set(7, 5);
/* 474 */       break;
/*     */     case 5: 
/* 476 */       this.gc.set(7, 6);
/* 477 */       break;
/*     */     case 6: 
/* 479 */       this.gc.set(7, 7);
/* 480 */       break;
/*     */     }
/*     */     
/*     */     
/* 484 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int dayOfYear()
/*     */   {
/* 493 */     return this.gc.get(6);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Moment dayOfYear(int day)
/*     */   {
/* 503 */     this.gc.set(6, day);
/* 504 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int quarter()
/*     */   {
/* 513 */     int month = month();
/* 514 */     switch (month) {
/*     */     case 1: 
/*     */     case 2: 
/*     */     case 3: 
/* 518 */       return 1;
/*     */     case 4: 
/*     */     case 5: 
/*     */     case 6: 
/* 522 */       return 2;
/*     */     case 7: 
/*     */     case 8: 
/*     */     case 9: 
/* 526 */       return 3;
/*     */     case 10: 
/*     */     case 11: 
/*     */     case 12: 
/* 530 */       return 4;
/*     */     }
/* 532 */     return 0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int get(String key)
/*     */   {
/* 543 */     switch (key) {
/*     */     case "year": 
/* 545 */       return year();
/*     */     case "month": 
/* 547 */       return month();
/*     */     case "day": 
/*     */     case "date": 
/* 550 */       return day();
/*     */     case "hour": 
/* 552 */       return hour();
/*     */     case "minute": 
/* 554 */       return minute();
/*     */     case "second": 
/* 556 */       return second();
/*     */     case "millisecond": 
/* 558 */       return millisecond();
/*     */     }
/* 560 */     return 0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Moment set(String key, int value)
/*     */   {
/* 572 */     switch (key) {
/*     */     case "year": 
/* 574 */       year(value);
/* 575 */       break;
/*     */     case "month": 
/* 577 */       month(value);
/* 578 */       break;
/*     */     case "day": 
/*     */     case "date": 
/* 581 */       day(value);
/* 582 */       break;
/*     */     case "hour": 
/* 584 */       hour(value);
/* 585 */       break;
/*     */     case "minute": 
/* 587 */       minute(value);
/* 588 */       break;
/*     */     case "second": 
/* 590 */       second(value);
/* 591 */       break;
/*     */     case "millisecond": 
/* 593 */       millisecond(value);
/* 594 */       break;
/*     */     }
/*     */     
/*     */     
/* 598 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public long millis()
/*     */   {
/* 607 */     return this.gc.getTimeInMillis();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Moment max(Moment... moment)
/*     */   {
/* 617 */     Moment max = null;
/* 618 */     if ((moment != null) && (moment.length > 0)) {
/* 619 */       for (Moment tmp : moment) {
/* 620 */         if (max == null) {
/* 621 */           max = tmp;
/*     */         }
/* 623 */         if (max.millis() < tmp.millis()) {
/* 624 */           max = tmp;
/*     */         }
/*     */       }
/*     */     }
/* 628 */     return max;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Moment min(Moment... moment)
/*     */   {
/* 638 */     Moment min = null;
/* 639 */     if ((moment != null) && (moment.length > 0)) {
/* 640 */       for (Moment tmp : moment) {
/* 641 */         if (min == null) {
/* 642 */           min = tmp;
/*     */         }
/* 644 */         if (min.millis() > tmp.millis()) {
/* 645 */           min = tmp;
/*     */         }
/*     */       }
/*     */     }
/* 649 */     return min;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Moment add(int amount, String key)
/*     */   {
/* 660 */     switch (key) {
/*     */     case "years": 
/*     */     case "y": 
/* 663 */       this.gc.add(1, amount);
/* 664 */       break;
/*     */     case "quarters": 
/*     */     case "Q": 
/* 667 */       this.gc.add(2, amount * 3);
/* 668 */       break;
/*     */     case "months": 
/*     */     case "M": 
/* 671 */       this.gc.add(2, amount);
/* 672 */       break;
/*     */     case "weeks": 
/*     */     case "w": 
/* 675 */       this.gc.add(3, amount);
/* 676 */       break;
/*     */     case "days": 
/*     */     case "d": 
/* 679 */       this.gc.add(5, amount);
/* 680 */       break;
/*     */     case "hours": 
/*     */     case "h": 
/* 683 */       this.gc.add(11, amount);
/* 684 */       break;
/*     */     case "minutes": 
/*     */     case "m": 
/* 687 */       this.gc.add(12, amount);
/* 688 */       break;
/*     */     case "seconds": 
/*     */     case "s": 
/* 691 */       this.gc.add(13, amount);
/* 692 */       break;
/*     */     case "milliseconds": 
/*     */     case "ms": 
/* 695 */       this.gc.add(14, amount);
/* 696 */       break;
/*     */     }
/*     */     
/*     */     
/* 700 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Moment subtract(int amount, String key)
/*     */   {
/* 711 */     return add(-amount, key);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Moment startOf(String key)
/*     */   {
/* 721 */     Moment moment = new Moment((GregorianCalendar)this.gc.clone());
/* 722 */     switch (key) {
/*     */     case "year": 
/* 724 */       moment.dayOfYear(1).hour(0).minute(0).second(0).millisecond(0);
/* 725 */       break;
/*     */     case "month": 
/* 727 */       moment.day(1).hour(0).minute(0).second(0).millisecond(0);
/* 728 */       break;
/*     */     case "week": 
/* 730 */       moment.dayOfWeek(1).hour(0).minute(0).second(0).millisecond(0);
/* 731 */       break;
/*     */     case "day": 
/* 733 */       moment.hour(0).minute(0).second(0).millisecond(0);
/* 734 */       break;
/*     */     case "hour": 
/* 736 */       moment.minute(0).second(0).millisecond(0);
/*     */     case "minute": 
/* 738 */       moment.second(0).millisecond(0);
/* 739 */       break;
/*     */     case "second": 
/* 741 */       moment.millisecond(0);
/* 742 */       break;
/*     */     }
/*     */     
/*     */     
/* 746 */     return moment;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Moment endOf(String key)
/*     */   {
/* 756 */     Moment moment = new Moment();
/* 757 */     switch (key) {
/*     */     case "year": 
/* 759 */       return 
/* 760 */         moment.startOf(key).add(1, "years").subtract(1, "days").hour(23).minute(59).second(59).millisecond(999);
/*     */     case "month": 
/* 762 */       return 
/* 763 */         moment.startOf(key).add(1, "months").subtract(1, "days").hour(23).minute(59).second(59).millisecond(999);
/*     */     case "week": 
/* 765 */       return 
/* 766 */         moment.startOf(key).add(1, "weeks").subtract(1, "days").hour(23).minute(59).second(59).millisecond(999);
/*     */     case "day": 
/* 768 */       return 
/* 769 */         moment.startOf(key).hour(23).minute(59).second(59).millisecond(999);
/*     */     case "hour": 
/* 771 */       return moment.startOf(key).minute(59).second(59).millisecond(999);
/*     */     case "minute": 
/* 773 */       return moment.startOf(key).second(59).millisecond(999);
/*     */     case "second": 
/* 775 */       return moment.startOf(key).millisecond(999);
/*     */     }
/*     */     
/*     */     
/* 779 */     return moment;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int difference(Moment moment, String key)
/*     */   {
/* 790 */     switch (key) {
/*     */     case "year": 
/* 792 */       return year() - moment.year();
/*     */     case "month": 
/* 794 */       return 
/* 795 */         difference(moment, "year") * 12 + (month() - moment.month());
/*     */     case "day": 
/* 797 */       return (int)((millis() - moment.millis()) / 86400000L);
/*     */     case "hour": 
/* 799 */       return (int)((millis() - moment.millis()) / 3600000L);
/*     */     case "minute": 
/* 801 */       return (int)((millis() - moment.millis()) / 60000L);
/*     */     case "second": 
/* 803 */       return (int)((millis() - moment.millis()) / 1000L);
/*     */     }
/*     */     
/*     */     
/* 807 */     return 0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean gt(Moment moment)
/*     */   {
/* 817 */     return millis() > moment.millis();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean lt(Moment moment)
/*     */   {
/* 827 */     return millis() < moment.millis();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean ge(Moment moment)
/*     */   {
/* 837 */     return millis() >= moment.millis();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean le(Moment moment)
/*     */   {
/* 847 */     return millis() <= moment.millis();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean between(Moment sMoment, Moment eMoment)
/*     */   {
/* 858 */     return (ge(sMoment)) && (le(eMoment));
/*     */   }
/*     */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryCapture/lotteryCaptureRepair.jar!/javautils/date/Moment.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */