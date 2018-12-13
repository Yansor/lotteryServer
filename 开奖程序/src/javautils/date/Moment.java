/*     */ package javautils.date;
/*     */ 
/*     */ import java.text.DateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.GregorianCalendar;
/*     */ 
/*     */ public class Moment
/*     */ {
/*     */   public GregorianCalendar gc;
/*     */   
/*     */   public static void main(String[] args)
/*     */   {
/*  13 */     Moment moment = new Moment();
/*  14 */     System.out.println(moment.difference(new Moment().fromDate("2015-01-18"), "hour"));
/*     */   }
/*     */   
/*     */   static {
/*  18 */     java.util.TimeZone china = java.util.TimeZone.getTimeZone("Asia/Shanghai");
/*  19 */     java.util.TimeZone.setDefault(china);
/*     */   }
/*     */   
/*     */ 
/*     */   public Moment()
/*     */   {
/*  25 */     this.gc = new GregorianCalendar();
/*  26 */     this.gc.setFirstDayOfWeek(2);
/*     */   }
/*     */   
/*     */   public Moment(GregorianCalendar gc) {
/*  30 */     this.gc = gc;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Moment fromDate(String date)
/*     */   {
/*     */     try
/*     */     {
/*  40 */       DateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
/*  41 */       Date d = format.parse(date);
/*  42 */       this.gc.setTime(d);
/*     */     } catch (Exception localException) {}
/*  44 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Moment fromDate(Date date)
/*     */   {
/*  53 */     this.gc.setTime(date);
/*  54 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Moment fromTime(String time)
/*     */   {
/*     */     try
/*     */     {
/*  64 */       DateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
/*  65 */       Date d = format.parse(time);
/*  66 */       this.gc.setTime(d);
/*     */     } catch (Exception localException) {}
/*  68 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Moment fromTime(String time, String formatStr)
/*     */   {
/*     */     try
/*     */     {
/*  79 */       DateFormat format = new java.text.SimpleDateFormat(formatStr);
/*  80 */       Date d = format.parse(time);
/*  81 */       this.gc.setTime(d);
/*     */     } catch (Exception localException) {}
/*  83 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toSimpleTime()
/*     */   {
/*  91 */     return year() + "-" + months() + "-" + days() + " " + hours() + ":" + minutes() + ":" + seconds();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toFullTime()
/*     */   {
/*  99 */     return toSimpleTime() + "." + milliseconds();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toSimpleDate()
/*     */   {
/* 107 */     return year() + "-" + months() + "-" + days();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Date toDate()
/*     */   {
/* 115 */     return this.gc.getTime();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String format(String format)
/*     */   {
/* 130 */     return new java.text.SimpleDateFormat(format).format(toDate());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int year()
/*     */   {
/* 140 */     return this.gc.get(1);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Moment year(int year)
/*     */   {
/* 149 */     this.gc.set(1, year);
/* 150 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int month()
/*     */   {
/* 159 */     return this.gc.get(2) + 1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String months()
/*     */   {
/* 168 */     return String.format("%02d", new Object[] { Integer.valueOf(month()) });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Moment month(int month)
/*     */   {
/* 178 */     if ((month > 0) && (month <= 12)) {
/* 179 */       this.gc.set(2, month - 1);
/*     */     }
/* 181 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Moment month(String month)
/*     */   {
/* 191 */     month(Integer.parseInt(month));
/* 192 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int day()
/*     */   {
/* 201 */     return this.gc.get(5);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String days()
/*     */   {
/* 210 */     return String.format("%02d", new Object[] { Integer.valueOf(day()) });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Moment day(int day)
/*     */   {
/* 220 */     if ((day > 0) && (day <= 31)) {
/* 221 */       this.gc.set(5, day);
/*     */     }
/* 223 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Moment day(String day)
/*     */   {
/* 233 */     day(Integer.parseInt(day));
/* 234 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int hour()
/*     */   {
/* 243 */     return this.gc.get(11);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String hours()
/*     */   {
/* 252 */     return String.format("%02d", new Object[] { Integer.valueOf(hour()) });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Moment hour(int hour)
/*     */   {
/* 262 */     if ((hour >= 0) && (hour < 24)) {
/* 263 */       this.gc.set(11, hour);
/*     */     }
/* 265 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Moment hour(String hour)
/*     */   {
/* 275 */     hour(Integer.parseInt(hour));
/* 276 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int minute()
/*     */   {
/* 285 */     return this.gc.get(12);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String minutes()
/*     */   {
/* 294 */     return String.format("%02d", new Object[] { Integer.valueOf(minute()) });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Moment minute(int minute)
/*     */   {
/* 304 */     if ((minute >= 0) && (minute < 60)) {
/* 305 */       this.gc.set(12, minute);
/*     */     }
/* 307 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Moment minute(String minute)
/*     */   {
/* 317 */     minute(Integer.parseInt(minute));
/* 318 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int second()
/*     */   {
/* 327 */     return this.gc.get(13);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String seconds()
/*     */   {
/* 336 */     return String.format("%02d", new Object[] { Integer.valueOf(second()) });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Moment second(int second)
/*     */   {
/* 346 */     if ((second >= 0) && (second < 60)) {
/* 347 */       this.gc.set(13, second);
/*     */     }
/* 349 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Moment second(String second)
/*     */   {
/* 359 */     second(Integer.parseInt(second));
/* 360 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int millisecond()
/*     */   {
/* 369 */     return this.gc.get(14);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String milliseconds()
/*     */   {
/* 378 */     return String.format("%03d", new Object[] { Integer.valueOf(millisecond()) });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Moment millisecond(int millisecond)
/*     */   {
/* 388 */     if ((millisecond >= 0) && (millisecond < 1000)) {
/* 389 */       this.gc.set(14, millisecond);
/*     */     }
/* 391 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Moment millisecond(String millisecond)
/*     */   {
/* 401 */     millisecond(Integer.parseInt(millisecond));
/* 402 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int weekOfMonth()
/*     */   {
/* 411 */     return this.gc.get(4);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int weekOfYear()
/*     */   {
/* 420 */     return this.gc.get(3);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int dayOfWeek()
/*     */   {
/* 429 */     int field = this.gc.get(7);
/* 430 */     switch (field) {
/*     */     case 1: 
/* 432 */       return 7;
/*     */     case 2: 
/* 434 */       return 1;
/*     */     case 3: 
/* 436 */       return 2;
/*     */     case 4: 
/* 438 */       return 3;
/*     */     case 5: 
/* 440 */       return 4;
/*     */     case 6: 
/* 442 */       return 5;
/*     */     case 7: 
/* 444 */       return 6;
/*     */     }
/*     */     
/*     */     
/* 448 */     return this.gc.getFirstDayOfWeek();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Moment dayOfWeek(int week)
/*     */   {
/* 457 */     switch (week) {
/*     */     case 7: 
/* 459 */       this.gc.set(7, 1);
/* 460 */       break;
/*     */     case 1: 
/* 462 */       this.gc.set(7, 2);
/* 463 */       break;
/*     */     case 2: 
/* 465 */       this.gc.set(7, 3);
/* 466 */       break;
/*     */     case 3: 
/* 468 */       this.gc.set(7, 4);
/* 469 */       break;
/*     */     case 4: 
/* 471 */       this.gc.set(7, 5);
/* 472 */       break;
/*     */     case 5: 
/* 474 */       this.gc.set(7, 6);
/* 475 */       break;
/*     */     case 6: 
/* 477 */       this.gc.set(7, 7);
/* 478 */       break;
/*     */     }
/*     */     
/*     */     
/* 482 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int dayOfYear()
/*     */   {
/* 491 */     return this.gc.get(6);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Moment dayOfYear(int day)
/*     */   {
/* 501 */     this.gc.set(6, day);
/* 502 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int quarter()
/*     */   {
/* 511 */     int month = month();
/* 512 */     switch (month) {
/*     */     case 1: 
/*     */     case 2: 
/*     */     case 3: 
/* 516 */       return 1;
/*     */     case 4: 
/*     */     case 5: 
/*     */     case 6: 
/* 520 */       return 2;
/*     */     case 7: 
/*     */     case 8: 
/*     */     case 9: 
/* 524 */       return 3;
/*     */     case 10: 
/*     */     case 11: 
/*     */     case 12: 
/* 528 */       return 4;
/*     */     }
/* 530 */     return 0;
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
/* 541 */     switch (key) {
/*     */     case "year": 
/* 543 */       return year();
/*     */     case "month": 
/* 545 */       return month();
/*     */     case "day": 
/*     */     case "date": 
/* 548 */       return day();
/*     */     case "hour": 
/* 550 */       return hour();
/*     */     case "minute": 
/* 552 */       return minute();
/*     */     case "second": 
/* 554 */       return second();
/*     */     case "millisecond": 
/* 556 */       return millisecond();
/*     */     }
/* 558 */     return 0;
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
/* 570 */     switch (key) {
/*     */     case "year": 
/* 572 */       year(value);
/* 573 */       break;
/*     */     case "month": 
/* 575 */       month(value);
/* 576 */       break;
/*     */     case "day": 
/*     */     case "date": 
/* 579 */       day(value);
/* 580 */       break;
/*     */     case "hour": 
/* 582 */       hour(value);
/* 583 */       break;
/*     */     case "minute": 
/* 585 */       minute(value);
/* 586 */       break;
/*     */     case "second": 
/* 588 */       second(value);
/* 589 */       break;
/*     */     case "millisecond": 
/* 591 */       millisecond(value);
/* 592 */       break;
/*     */     }
/*     */     
/*     */     
/* 596 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public long millis()
/*     */   {
/* 605 */     return this.gc.getTimeInMillis();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Moment max(Moment... moment)
/*     */   {
/* 615 */     Moment max = null;
/* 616 */     if ((moment != null) && (moment.length > 0)) {
/* 617 */       for (Moment tmp : moment) {
/* 618 */         if (max == null) {
/* 619 */           max = tmp;
/*     */         }
/* 621 */         if (max.millis() < tmp.millis()) {
/* 622 */           max = tmp;
/*     */         }
/*     */       }
/*     */     }
/* 626 */     return max;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Moment min(Moment... moment)
/*     */   {
/* 636 */     Moment min = null;
/* 637 */     if ((moment != null) && (moment.length > 0)) {
/* 638 */       for (Moment tmp : moment) {
/* 639 */         if (min == null) {
/* 640 */           min = tmp;
/*     */         }
/* 642 */         if (min.millis() > tmp.millis()) {
/* 643 */           min = tmp;
/*     */         }
/*     */       }
/*     */     }
/* 647 */     return min;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Moment add(int amount, String key)
/*     */   {
/* 657 */     switch (key) {
/*     */     case "years": 
/*     */     case "y": 
/* 660 */       this.gc.add(1, amount);
/* 661 */       break;
/*     */     case "quarters": 
/*     */     case "Q": 
/* 664 */       this.gc.add(2, amount * 3);
/* 665 */       break;
/*     */     case "months": 
/*     */     case "M": 
/* 668 */       this.gc.add(2, amount);
/* 669 */       break;
/*     */     case "weeks": 
/*     */     case "w": 
/* 672 */       this.gc.add(3, amount);
/* 673 */       break;
/*     */     case "days": 
/*     */     case "d": 
/* 676 */       this.gc.add(5, amount);
/* 677 */       break;
/*     */     case "hours": 
/*     */     case "h": 
/* 680 */       this.gc.add(11, amount);
/* 681 */       break;
/*     */     case "minutes": 
/*     */     case "m": 
/* 684 */       this.gc.add(12, amount);
/* 685 */       break;
/*     */     case "seconds": 
/*     */     case "s": 
/* 688 */       this.gc.add(13, amount);
/* 689 */       break;
/*     */     case "milliseconds": 
/*     */     case "ms": 
/* 692 */       this.gc.add(14, amount);
/* 693 */       break;
/*     */     }
/*     */     
/*     */     
/* 697 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Moment subtract(int amount, String key)
/*     */   {
/* 707 */     return add(-amount, key);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Moment startOf(String key)
/*     */   {
/* 717 */     Moment moment = new Moment((GregorianCalendar)this.gc.clone());
/* 718 */     switch (key) {
/*     */     case "year": 
/* 720 */       moment.dayOfYear(1).hour(0).minute(0).second(0).millisecond(0);
/* 721 */       break;
/*     */     case "month": 
/* 723 */       moment.day(1).hour(0).minute(0).second(0).millisecond(0);
/* 724 */       break;
/*     */     case "week": 
/* 726 */       moment.dayOfWeek(1).hour(0).minute(0).second(0).millisecond(0);
/* 727 */       break;
/*     */     case "day": 
/* 729 */       moment.hour(0).minute(0).second(0).millisecond(0);
/* 730 */       break;
/*     */     case "hour": 
/* 732 */       moment.minute(0).second(0).millisecond(0);
/*     */     case "minute": 
/* 734 */       moment.second(0).millisecond(0);
/* 735 */       break;
/*     */     case "second": 
/* 737 */       moment.millisecond(0);
/* 738 */       break;
/*     */     }
/*     */     
/*     */     
/* 742 */     return moment;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Moment endOf(String key)
/*     */   {
/* 752 */     Moment moment = new Moment();
/* 753 */     switch (key) {
/*     */     case "year": 
/* 755 */       return moment.startOf(key).add(1, "years").subtract(1, "days").hour(23).minute(59).second(59).millisecond(999);
/*     */     case "month": 
/* 757 */       return moment.startOf(key).add(1, "months").subtract(1, "days").hour(23).minute(59).second(59).millisecond(999);
/*     */     case "week": 
/* 759 */       return moment.startOf(key).add(1, "weeks").subtract(1, "days").hour(23).minute(59).second(59).millisecond(999);
/*     */     case "day": 
/* 761 */       return moment.startOf(key).hour(23).minute(59).second(59).millisecond(999);
/*     */     case "hour": 
/* 763 */       return moment.startOf(key).minute(59).second(59).millisecond(999);
/*     */     case "minute": 
/* 765 */       return moment.startOf(key).second(59).millisecond(999);
/*     */     case "second": 
/* 767 */       return moment.startOf(key).millisecond(999);
/*     */     }
/*     */     
/*     */     
/* 771 */     return moment;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int difference(Moment moment, String key)
/*     */   {
/* 781 */     switch (key) {
/*     */     case "year": 
/* 783 */       return year() - moment.year();
/*     */     case "month": 
/* 785 */       return difference(moment, "year") * 12 + (month() - moment.month());
/*     */     case "day": 
/* 787 */       return (int)((millis() - moment.millis()) / 86400000L);
/*     */     case "hour": 
/* 789 */       return (int)((millis() - moment.millis()) / 3600000L);
/*     */     case "minute": 
/* 791 */       return (int)((millis() - moment.millis()) / 60000L);
/*     */     case "second": 
/* 793 */       return (int)((millis() - moment.millis()) / 1000L);
/*     */     }
/*     */     
/*     */     
/* 797 */     return 0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean gt(Moment moment)
/*     */   {
/* 806 */     return millis() > moment.millis();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean lt(Moment moment)
/*     */   {
/* 815 */     return millis() < moment.millis();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean ge(Moment moment)
/*     */   {
/* 824 */     return millis() >= moment.millis();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean le(Moment moment)
/*     */   {
/* 833 */     return millis() <= moment.millis();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean between(Moment sMoment, Moment eMoment)
/*     */   {
/* 843 */     return (ge(sMoment)) && (le(eMoment));
/*     */   }
/*     */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/javautils/date/Moment.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */