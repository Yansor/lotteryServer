/*    */ package javautils.date;
/*    */ 
/*    */ import java.io.PrintStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ public class DateRangeUtil
/*    */ {
/*    */   public static void main(String[] args)
/*    */   {
/* 12 */     String[] days = listDate("2014-01-01", "2014-01-15");
/* 13 */     for (String string : days) {
/* 14 */       System.out.println(string);
/*    */     }
/*    */   }
/*    */   
/*    */   public static String[] listDate(String sDate, String eDate) {
/* 19 */     Moment sMoment = new Moment().fromDate(sDate);
/* 20 */     Moment eMoment = new Moment().fromDate(eDate);
/* 21 */     List<String> list = new ArrayList();
/* 22 */     if (sMoment.le(eMoment)) {
/* 23 */       list.add(sMoment.toSimpleDate());
/* 24 */       int days = eMoment.difference(sMoment, "day");
/* 25 */       for (int i = 0; i < days - 1; i++) {
/* 26 */         list.add(sMoment.add(1, "days").toSimpleDate());
/*    */       }
/*    */     }
/* 29 */     String[] array = new String[list.size()];
/* 30 */     for (int i = 0; i < list.size(); i++) {
/* 31 */       array[i] = ((String)list.get(i));
/*    */     }
/* 33 */     return array;
/*    */   }
/*    */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/javautils/date/DateRangeUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */