/*    */ package lottery.domains.utils.open;
/*    */ 
/*    */ import java.util.Date;
/*    */ import javautils.date.DateUtil;
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
/*    */   public static OpenTime trans(OpenTime calcOpenTime, String refDate, int refExpect, int times)
/*    */   {
/* 27 */     String calcExpect = calcOpenTime.getExpect();
/*    */     
/*    */ 
/* 30 */     Date currentDate = DateUtil.stringToDate(calcExpect.substring(0, 8), "yyyyMMdd");
/*    */     
/* 32 */     int currentTimes = Integer.valueOf(calcExpect.substring(9)).intValue();
/*    */     
/*    */ 
/* 35 */     Date refDateDate = DateUtil.stringToDate(refDate, "yyyy-MM-dd");
/*    */     
/*    */ 
/* 38 */     int disDays = DateUtil.calcDays(currentDate, refDateDate);
/*    */     
/*    */ 
/* 41 */     int disTimes = disDays * times + currentTimes;
/*    */     
/*    */ 
/* 44 */     int finalExpect = refExpect + disTimes;
/*    */     
/* 46 */     calcOpenTime.setExpect(String.valueOf(finalExpect));
/*    */     
/* 48 */     return calcOpenTime;
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
/* 62 */     int disTimes = realExpect - refExpect;
/*    */     
/*    */ 
/* 65 */     int disDays = disTimes / times;
/*    */     
/*    */ 
/* 68 */     int remainTimes = disTimes % times;
/*    */     
/*    */ 
/* 71 */     String currentDate = DateUtil.calcNewDay(refDate, disDays);
/*    */     
/* 73 */     return currentDate.replace("-", "") + "-" + String.format("%03d", new Object[] { Integer.valueOf(remainTimes) });
/*    */   }
/*    */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/utils/open/OpenTimeTransUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */