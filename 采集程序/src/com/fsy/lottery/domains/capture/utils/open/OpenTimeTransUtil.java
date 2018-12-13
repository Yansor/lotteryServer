/*    */ package com.fsy.lottery.domains.capture.utils.open;
/*    */ 
/*    */ import java.util.Date;
/*    */ import com.fsy.javautils.date.DateUtil;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class OpenTimeTransUtil
/*    */ {
/*    */   public static String trans(String expect, String refDate, int refExpect, int times)
/*    */   {
/* 28 */     String calcExpect = expect;
/*    */     
/*    */ 
/* 31 */     Date currentDate = DateUtil.stringToDate(calcExpect.substring(0, 8), "yyyyMMdd");
/*    */     
/* 33 */     int currentTimes = Integer.valueOf(calcExpect.substring(9)).intValue();
/*    */     
/*    */ 
/* 36 */     Date refDateDate = DateUtil.stringToDate(refDate, "yyyy-MM-dd");
/*    */     
/*    */ 
/* 39 */     int disDays = DateUtil.calcDays(currentDate, refDateDate);
/*    */     
/*    */ 
/* 42 */     int disTimes = disDays * times + currentTimes;
/*    */     
/*    */ 
/* 45 */     int finalExpect = refExpect + disTimes;
/*    */     
/* 47 */     return String.valueOf(finalExpect);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static String trans(int realExpect, String refDate, int refExpect, int times)
/*    */   {
/* 61 */     int disTimes = realExpect - refExpect;
/*    */     
/*    */ 
/* 64 */     int disDays = disTimes / times;
/*    */     
/*    */ 
/* 67 */     int remainTimes = disTimes % times;
/*    */     
/*    */ 
/* 70 */     String currentDate = DateUtil.calcNewDay(refDate, disDays);
/*    */     
/* 72 */     return currentDate.replace("-", "") + "-" + String.format("%03d", new Object[] { Integer.valueOf(remainTimes) });
/*    */   }
/*    */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryCapture/lotteryCaptureRepair.jar!/lottery/domains/capture/utils/open/OpenTimeTransUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */