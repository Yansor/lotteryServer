/*    */ package com.fsy.javautils.math;
/*    */ 
/*    */ import java.math.BigDecimal;
/*    */ 
/*    */ public class MathUtil
/*    */ {
/*    */   public static double decimalFormat(BigDecimal bd, int point) {
/*  8 */     return bd.setScale(point, 5).doubleValue();
/*    */   }
/*    */   
/*    */   public static double doubleFormat(double d, int point) {
/*    */     try {
/* 13 */       BigDecimal bd = new BigDecimal(d);
/* 14 */       return bd.setScale(point, 5).doubleValue();
/*    */     } catch (Exception e) {
/* 16 */       e.printStackTrace();
/*    */     }
/* 18 */     return d;
/*    */   }
/*    */   
/*    */   public static float floatFormat(float f, int point) {
/*    */     try {
/* 23 */       BigDecimal bd = new BigDecimal(f);
/* 24 */       return bd.setScale(point, 5).floatValue();
/*    */     } catch (Exception e) {
/* 26 */       e.printStackTrace();
/*    */     }
/* 28 */     return f;
/*    */   }
/*    */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryCapture/lotteryCaptureRepair.jar!/javautils/math/MathUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */