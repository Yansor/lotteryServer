/*     */ package javautils.math;
/*     */ 
/*     */ import java.math.BigDecimal;
/*     */ import java.text.DecimalFormat;
/*     */ 
/*     */ public class MathUtil
/*     */ {
/*     */   public static boolean inRound(double value, double min, double max)
/*     */   {
/*  10 */     return (value >= min) && (value <= max);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static double add(double a, double b)
/*     */   {
/*  20 */     BigDecimal b1 = new BigDecimal(Double.toString(a));
/*  21 */     BigDecimal b2 = new BigDecimal(Double.toString(b));
/*  22 */     return b1.add(b2).doubleValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static double subtract(double a, double b)
/*     */   {
/*  32 */     BigDecimal b1 = new BigDecimal(Double.toString(a));
/*  33 */     BigDecimal b2 = new BigDecimal(Double.toString(b));
/*  34 */     return b1.subtract(b2).doubleValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static double multiply(double a, double b)
/*     */   {
/*  44 */     BigDecimal b1 = new BigDecimal(Double.toString(a));
/*  45 */     BigDecimal b2 = new BigDecimal(Double.toString(b));
/*  46 */     return b1.multiply(b2).doubleValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static double divide(double v1, double v2, int scale)
/*     */   {
/*  57 */     BigDecimal b1 = new BigDecimal(Double.toString(v1));
/*  58 */     BigDecimal b2 = new BigDecimal(Double.toString(v2));
/*  59 */     return b1.divide(b2, scale, 5).doubleValue();
/*     */   }
/*     */   
/*     */   public static void main(String[] args) {
/*  63 */     System.out.println(divide(1.2134D, 2312.23456D, 6));
/*  64 */     System.out.println(add(12.9D, 0.101D));
/*     */     
/*  66 */     Double d = new Double(8.747470732E7D);
/*  67 */     DecimalFormat format = new DecimalFormat("##.0000");
/*  68 */     System.out.println(format.format(d));
/*     */   }
/*     */   
/*     */   public static double decimalFormat(BigDecimal bd, int point) {
/*  72 */     return bd.setScale(point, 5).doubleValue();
/*     */   }
/*     */   
/*     */   public static double doubleFormat(double d, int point) {
/*     */     try {
/*  77 */       BigDecimal bd = new BigDecimal(d);
/*  78 */       return bd.setScale(point, 5).doubleValue();
/*     */     } catch (Exception e) {
/*  80 */       e.printStackTrace();
/*     */     }
/*  82 */     return d;
/*     */   }
/*     */   
/*     */   public static double StringFormat(String d, int point) {
/*     */     try {
/*  87 */       BigDecimal bd = new BigDecimal(d);
/*  88 */       return bd.setScale(point, 5).doubleValue();
/*     */     } catch (Exception e) {
/*  90 */       e.printStackTrace();
/*     */     }
/*  92 */     return 0.0D;
/*     */   }
/*     */   
/*     */   public static float floatFormat(float f, int point) {
/*     */     try {
/*  97 */       BigDecimal bd = new BigDecimal(f);
/*  98 */       return bd.setScale(point, 5).floatValue();
/*     */     } catch (Exception e) {
/* 100 */       e.printStackTrace();
/*     */     }
/* 102 */     return f;
/*     */   }
/*     */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/javautils/math/MathUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */