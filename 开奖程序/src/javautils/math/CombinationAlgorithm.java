/*     */ package javautils.math;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import javautils.StringUtil;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CombinationAlgorithm
/*     */ {
/*     */   private Object[] src;
/*     */   private int m;
/*     */   private int n;
/*     */   private int objLineIndex;
/*     */   public Object[][] obj;
/*     */   
/*     */   public CombinationAlgorithm(Object[] src, int getNum)
/*     */   {
/*  32 */     this.src = src;
/*  33 */     this.m = src.length;
/*  34 */     this.n = getNum;
/*     */     
/*     */ 
/*  37 */     this.objLineIndex = 0;
/*  38 */     this.obj = new Object[combination(this.m, this.n)][this.n];
/*     */     
/*  40 */     Object[] tmp = new Object[this.n];
/*  41 */     combine(src, 0, 0, this.n, tmp);
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
/*     */   public int combination(int m, int n)
/*     */   {
/*  56 */     if (m < n) {
/*  57 */       return 0;
/*     */     }
/*  59 */     int k = 1;
/*  60 */     int j = 1;
/*     */     
/*  62 */     for (int i = n; i >= 1; i--) {
/*  63 */       k *= m;
/*  64 */       j *= n;
/*  65 */       m--;
/*  66 */       n--;
/*     */     }
/*  68 */     return k / j;
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
/*     */   private void combine(Object[] src, int srcIndex, int i, int n, Object[] tmp)
/*     */   {
/*  82 */     for (int j = srcIndex; j < src.length - (n - 1); j++) {
/*  83 */       tmp[i] = src[j];
/*  84 */       if (n == 1)
/*     */       {
/*  86 */         System.arraycopy(tmp, 0, this.obj[this.objLineIndex], 0, tmp.length);
/*     */         
/*  88 */         this.objLineIndex += 1;
/*     */       } else {
/*  90 */         n--;
/*  91 */         i++;
/*  92 */         combine(src, j + 1, i, n, tmp);
/*  93 */         n++;
/*  94 */         i--;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public Object[][] getResutl()
/*     */   {
/* 101 */     return this.obj;
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
/*     */ 
/*     */ 
/*     */   public static void main(String[] args)
/*     */     throws Exception
/*     */   {
/* 119 */     long start = System.currentTimeMillis();
/*     */     
/* 121 */     for (int i = 0; i < 100000; i++) {
/* 122 */       String[] a = { "1", "2", "3", "4", "5" };
/* 123 */       CombinationAlgorithm ca = new CombinationAlgorithm(a, 3);
/*     */       
/* 125 */       Object[][] arrayOfObject1 = ca.getResutl();
/*     */     }
/* 127 */     long spent = System.currentTimeMillis() - start;
/* 128 */     System.out.println("10万次耗时：" + spent);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 133 */     String[] a = { "1", "2", "3", "4", "5" };
/* 134 */     CombinationAlgorithm ca = new CombinationAlgorithm(a, 3);
/*     */     
/* 136 */     Object[][] c = ca.getResutl();
/* 137 */     for (int i = 0; i < c.length; i++) {
/* 138 */       System.out.println(StringUtil.transArrayToString(c[i]));
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/javautils/math/CombinationAlgorithm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */