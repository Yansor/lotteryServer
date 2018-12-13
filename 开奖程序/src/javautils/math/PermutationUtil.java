/*    */ package javautils.math;
/*    */ 
/*    */ import java.io.PrintStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
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
/*    */ public class PermutationUtil
/*    */ {
/*    */   public static void main(String[] args)
/*    */     throws Exception
/*    */   {}
/*    */   
/*    */   public static List<Object[]> permutation(Object[] objs)
/*    */   {
/* 25 */     if (objs.length > 5) {
/* 26 */       System.out.println("PermutationUtil.permutation排列元素不要超过5个，会有性能瓶颈");
/*    */     }
/*    */     
/* 29 */     return doPermutation(objs, 0, objs.length - 1);
/*    */   }
/*    */   
/*    */   private static List<Object[]> doPermutation(Object[] objs, int first, int end) {
/* 33 */     if ((objs == null) || (objs.length <= 0)) {
/* 34 */       return new ArrayList();
/*    */     }
/*    */     
/* 37 */     List<Object[]> result = new ArrayList();
/*    */     
/* 39 */     if (first == end) {
/* 40 */       Object[] permuration = new Object[objs.length];
/* 41 */       int count = 0;
/* 42 */       for (int j = 0; j <= end; j++) {
/* 43 */         permuration[(count++)] = objs[j];
/*    */       }
/* 45 */       result.add(permuration);
/*    */     }
/*    */     
/* 48 */     for (int i = first; i <= end; i++) {
/* 49 */       swap(objs, i, first);
/* 50 */       List<Object[]> subResult = doPermutation(objs, first + 1, end);
/* 51 */       result.addAll(subResult);
/* 52 */       swap(objs, i, first);
/*    */     }
/*    */     
/* 55 */     return result;
/*    */   }
/*    */   
/*    */   private static void swap(Object[] objs, int i, int first)
/*    */   {
/* 60 */     Object tmp = objs[first];
/* 61 */     objs[first] = objs[i];
/* 62 */     objs[i] = tmp;
/*    */   }
/*    */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/javautils/math/PermutationUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */