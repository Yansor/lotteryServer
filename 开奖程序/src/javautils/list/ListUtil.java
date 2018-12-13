/*     */ package javautils.list;
/*     */ 
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import javautils.StringUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ListUtil
/*     */ {
/*     */   public static HashSet<String> transToHashSet(String[] strs)
/*     */   {
/*  16 */     if ((strs == null) || (strs.length <= 0)) {
/*  17 */       return new HashSet();
/*     */     }
/*     */     
/*  20 */     HashSet<String> results = new HashSet();
/*  21 */     for (int i = 0; i < strs.length; i++) {
/*  22 */       results.add(strs[i]);
/*     */     }
/*  24 */     return results;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static LinkedList removeAll(LinkedList src, LinkedList list)
/*     */   {
/*  31 */     if ((src == null) || (src.isEmpty()) || (list == null) || (list.isEmpty())) {
/*  32 */       return src;
/*     */     }
/*     */     
/*  35 */     HashSet oth = new HashSet(list);
/*  36 */     return removeAll(src, oth);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static LinkedList removeAll(LinkedList src, HashSet oth)
/*     */   {
/*  43 */     if ((src == null) || (src.isEmpty()) || (oth == null) || (oth.isEmpty())) {
/*  44 */       return src;
/*     */     }
/*     */     
/*  47 */     Iterator iter = src.iterator();
/*  48 */     while (iter.hasNext()) {
/*  49 */       if (oth.contains(iter.next())) {
/*  50 */         iter.remove();
/*     */       }
/*     */     }
/*  53 */     return src;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static LinkedList<String> retainAll(LinkedList<String> list1, LinkedList<String> list2)
/*     */   {
/*  60 */     if ((list1 == null) || (list1.isEmpty())) {
/*  61 */       return new LinkedList();
/*     */     }
/*  63 */     if ((list2 == null) || (list2.isEmpty())) {
/*  64 */       return new LinkedList();
/*     */     }
/*     */     
/*  67 */     HashSet<String> retain = new HashSet();
/*     */     
/*  69 */     Iterator<String> iter1 = list1.iterator();
/*  70 */     Iterator<String> iter2 = list2.iterator();
/*  71 */     HashSet<String> oth1 = new HashSet(list1);
/*  72 */     HashSet<String> oth2 = new HashSet(list2);
/*     */     
/*     */ 
/*  75 */     while (iter1.hasNext()) {
/*  76 */       String val = (String)iter1.next();
/*  77 */       if (oth2.contains(val)) {
/*  78 */         retain.add(val);
/*     */       }
/*     */     }
/*     */     
/*  82 */     while (iter2.hasNext()) {
/*  83 */       String val = (String)iter2.next();
/*  84 */       if (oth1.contains(val)) {
/*  85 */         retain.add(val);
/*     */       }
/*     */     }
/*     */     
/*  89 */     return new LinkedList(retain);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static int[] transObjectToInt(Object[] o)
/*     */   {
/*  98 */     int[] t = new int[o.length];
/*  99 */     for (int i = 0; i < o.length; i++) {
/* 100 */       if ((o[i] instanceof Integer)) {
/* 101 */         t[i] = ((Integer)o[i]).intValue();
/*     */       }
/* 103 */       if ((o[i] instanceof String)) {
/* 104 */         String s = (String)o[i];
/* 105 */         if (StringUtil.isIntegerString(s)) {
/* 106 */           t[i] = Integer.parseInt(s);
/*     */         }
/*     */       }
/*     */     }
/* 110 */     return t;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String transListToString(List<?> list)
/*     */   {
/* 119 */     StringBuffer sb = new StringBuffer();
/* 120 */     for (Object obj : list) {
/* 121 */       sb.append(String.valueOf(obj) + ", ");
/*     */     }
/* 123 */     if (list.size() > 0) {
/* 124 */       return sb.substring(0, sb.length() - 2);
/*     */     }
/* 126 */     return sb.toString();
/*     */   }
/*     */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/javautils/list/ListUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */