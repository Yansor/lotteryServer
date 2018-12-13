/*    */ package com.fsy.javautils.array;
/*    */ 
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ArrayUtils
/*    */ {
/*    */   public static String transInIds(int[] ids)
/*    */   {
/* 13 */     StringBuffer sb = new StringBuffer();
/* 14 */     int i = 0; for (int j = ids.length; i < j; i++) {
/* 15 */       sb.append(ids[i]);
/* 16 */       if (i < j - 1) {
/* 17 */         sb.append(",");
/*    */       }
/*    */     }
/* 20 */     return sb.toString();
/*    */   }
/*    */   
/*    */   public static String transInIds(Integer[] ids) {
/* 24 */     StringBuffer sb = new StringBuffer();
/* 25 */     int i = 0; for (int j = ids.length; i < j; i++) {
/* 26 */       sb.append(ids[i].intValue());
/* 27 */       if (i < j - 1) {
/* 28 */         sb.append(",");
/*    */       }
/*    */     }
/* 31 */     return sb.toString();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static String transInIds(List<Integer> ids)
/*    */   {
/* 40 */     StringBuffer sb = new StringBuffer();
/* 41 */     int i = 0; for (int j = ids.size(); i < j; i++) {
/* 42 */       sb.append(((Integer)ids.get(i)).intValue());
/* 43 */       if (i < j - 1) {
/* 44 */         sb.append(",");
/*    */       }
/*    */     }
/* 47 */     return sb.toString();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static String transInsertIds(int[] ids)
/*    */   {
/* 56 */     StringBuffer sb = new StringBuffer();
/* 57 */     int i = 0; for (int j = ids.length; i < j; i++) {
/* 58 */       sb.append("[" + ids[i] + "]");
/* 59 */       if (i < j - 1) {
/* 60 */         sb.append(",");
/*    */       }
/*    */     }
/* 63 */     return sb.toString();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static int[] transGetIds(String ids)
/*    */   {
/* 72 */     String[] tmp = ids.replaceAll("\\[|\\]", "").split(",");
/* 73 */     int[] arr = new int[tmp.length];
/* 74 */     for (int i = 0; i < arr.length; i++) {
/* 75 */       arr[i] = Integer.parseInt(tmp[i]);
/*    */     }
/* 77 */     return arr;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static String toString(List<Integer> list)
/*    */   {
/* 86 */     StringBuffer sb = new StringBuffer();
/* 87 */     int i = 0; for (int j = list.size(); i < j; i++) {
/* 88 */       sb.append(list.get(i));
/* 89 */       if (i < j - 1) {
/* 90 */         sb.append(",");
/*    */       }
/*    */     }
/* 93 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryCapture/lotteryCaptureRepair.jar!/javautils/array/ArrayUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */