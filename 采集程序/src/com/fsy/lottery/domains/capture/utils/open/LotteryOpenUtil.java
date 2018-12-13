/*    */ package com.fsy.lottery.domains.capture.utils.open;
/*    */ 
/*    */ import com.fsy.javautils.date.Moment;
/*    */ import com.fsy.lottery.domains.content.entity.Lottery;
/*    */ import com.fsy.lottery.domains.content.pool.LotteryDataFactory;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Component;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Component
/*    */ public class LotteryOpenUtil
/*    */ {
/*    */   @Autowired
/*    */   private LotteryDataFactory dataFactory;
/*    */   
/*    */   public String subtractExpect(String lotteryShortName, String expect)
/*    */   {
/* 22 */     Lottery lottery = this.dataFactory.getLottery(lotteryShortName);
/* 23 */     if (lottery == null) return null;
/*    */     String subExpect;
///*    */     String subExpect;
/* 26 */     if (expect.indexOf("-") <= -1) {
/* 27 */       Integer integerExpect = Integer.valueOf(expect);
/* 28 */       integerExpect = Integer.valueOf(integerExpect.intValue() - 1);
///*    */       String subExpect;
/* 30 */       if (integerExpect.toString().length() >= expect.length()) {
/* 31 */         subExpect = integerExpect.toString();
/*    */       }
/*    */       else {
/* 34 */         subExpect = String.format("%0" + expect.length() + "d", new Object[] { integerExpect });
/*    */       }
/*    */     }
/*    */     else {
/* 38 */       String[] split = expect.split("-");
/* 39 */       int formatCount = split[1].length();
/* 40 */       String date = split[0];
/* 41 */       String currExpect = split[1];
///*    */       String subExpect;
/* 43 */       if ((currExpect.equals("001")) || (currExpect.equals("0001"))) {
/* 44 */         date = new Moment().fromDate(date).subtract(1, "days").format("yyyyMMdd");
/* 45 */         subExpect = String.format("%0" + formatCount + "d", new Object[] { Integer.valueOf(lottery.getTimes()) });
/*    */       }
/*    */       else {
/* 48 */         Integer integer = Integer.valueOf(currExpect);
/* 49 */         integer = Integer.valueOf(integer.intValue() - 1);
///* 50 */         String subExpect;
if (integer.toString().length() >= formatCount) {
/* 51 */           subExpect = integer.toString();
/*    */         }
/*    */         else {
/* 54 */           subExpect = String.format("%0" + formatCount + "d", new Object[] { integer });
/*    */         }
/*    */       }
/*    */       
/* 58 */       subExpect = date + "-" + subExpect;
/*    */     }
/*    */     
/* 61 */     return subExpect;
/*    */   }
/*    */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryCapture/lotteryCaptureRepair.jar!/lottery/domains/capture/utils/open/LotteryOpenUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */