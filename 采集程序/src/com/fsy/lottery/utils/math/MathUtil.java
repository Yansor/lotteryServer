/*    */ package com.fsy.lottery.utils.math;
/*    */ 
/*    */ import java.math.BigDecimal;
/*    */ 
/*    */ public class MathUtil
/*    */ {
/*    */   public static double doubleFormat(double d, int point) {
/*    */     try {
/*  9 */       BigDecimal bd = new BigDecimal(d);
/* 10 */       return bd.setScale(point, 5).doubleValue();
/*    */     } catch (Exception e) {
/* 12 */       e.printStackTrace();
/*    */     }
/* 14 */     return d;
/*    */   }
/*    */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryCapture/lotteryCaptureRepair.jar!/lottery/utils/math/MathUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */