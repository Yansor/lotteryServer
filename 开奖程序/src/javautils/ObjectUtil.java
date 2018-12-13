/*    */ package javautils;
/*    */ 
/*    */ public class ObjectUtil
/*    */ {
/*    */   public static double toDouble(Object object) {
/*  6 */     if ((object != null) && ((object instanceof Number))) {
/*  7 */       return ((Number)object).doubleValue();
/*    */     }
/*  9 */     return 0.0D;
/*    */   }
/*    */   
/*    */   public static int toInt(Object object) {
/* 13 */     if ((object != null) && ((object instanceof Number))) {
/* 14 */       return ((Number)object).intValue();
/*    */     }
/* 16 */     return 0;
/*    */   }
/*    */   
/*    */   public static long toLong(Object object) {
/* 20 */     if ((object != null) && ((object instanceof Number))) {
/* 21 */       return ((Number)object).longValue();
/*    */     }
/* 23 */     return 0L;
/*    */   }
/*    */   
/*    */   public static float toFloat(Object object) {
/* 27 */     if ((object != null) && ((object instanceof Number))) {
/* 28 */       return ((Number)object).floatValue();
/*    */     }
/* 30 */     return 0.0F;
/*    */   }
/*    */   
/*    */   public static short toShort(Object object) {
/* 34 */     if ((object != null) && ((object instanceof Number))) {
/* 35 */       return ((Number)object).shortValue();
/*    */     }
/* 37 */     return 0;
/*    */   }
/*    */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/javautils/ObjectUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */