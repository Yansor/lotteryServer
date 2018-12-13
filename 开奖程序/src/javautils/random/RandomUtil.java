/*    */ package javautils.random;
/*    */ 
/*    */ import java.io.PrintStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collections;
/*    */ import java.util.HashSet;
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
/*    */ public final class RandomUtil
/*    */ {
/*    */   public static List<Integer> randomUniqueNumbers(int min, int max, int n)
/*    */   {
/* 21 */     List<Integer> list = new ArrayList();
/* 22 */     for (int i = min; i <= max; i++) {
/* 23 */       list.add(new Integer(i));
/*    */     }
/* 25 */     Collections.shuffle(list);
/*    */     
/* 27 */     List<Integer> result = new ArrayList();
/* 28 */     for (int i = 0; i < n; i++) {
/* 29 */       Integer value = (Integer)list.get(i);
/* 30 */       result.add(value);
/*    */     }
/*    */     
/* 33 */     return result;
/*    */   }
/*    */   
/*    */   public static void main(String[] args) {
/* 37 */     int count = 10000;
/*    */     
/* 39 */     List<Integer> result = randomUniqueNumbers(0, 99999, count);
/*    */     
/* 41 */     HashSet<Integer> resultChecker = new HashSet(result);
/*    */     
/* 43 */     System.out.println("是否是正确的结果" + (resultChecker.size() == count));
/*    */   }
/*    */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/javautils/random/RandomUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */