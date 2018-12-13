/*     */ package javautils.array;
/*     */ 
/*     */ import java.util.List;
/*     */ 
/*     */ public class ArrayUtils {
/*     */   public static boolean hasSame(String... vals) {
/*   7 */     java.util.Set<String> set = new java.util.HashSet();
/*   8 */     set.addAll(java.util.Arrays.asList(vals));
/*     */     
/*  10 */     return vals.length != set.size();
/*     */   }
/*     */   
/*     */   public static int Combination(int n, int m) {
/*  14 */     if ((m < 0) || (n < 0)) {
/*  15 */       return 0;
/*     */     }
/*  17 */     if ((m == 0) || (n == 0)) {
/*  18 */       return 1;
/*     */     }
/*  20 */     if (m > n) {
/*  21 */       return 0;
/*     */     }
/*  23 */     if (m > n / 2.0D) {
/*  24 */       m = n - m;
/*     */     }
/*  26 */     double result = 0.0D;
/*  27 */     for (int i = n; i >= n - m + 1; i--) {
/*  28 */       result += Math.log(i);
/*     */     }
/*  30 */     for (int i = m; i >= 1; i--) {
/*  31 */       result -= Math.log(i);
/*     */     }
/*  33 */     result = Math.exp(result);
/*  34 */     return (int)Math.round(result);
/*     */   }
/*     */   
/*     */   public static List<Object[]> CombinationValue(List<Object[]> source, int m, int x) {
/*  38 */     int n = source.size();
/*  39 */     List<Object[]> list = new java.util.ArrayList();
/*  40 */     int start = 0;
/*  41 */     while (m > 0) {
/*  42 */       if (m == 1) {
/*  43 */         list.add(source.get(start + x));
/*  44 */         break;
/*     */       }
/*  46 */       for (int i = 0; i <= n - m; i++) {
/*  47 */         int cnm = Combination(n - 1 - i, m - 1);
/*  48 */         if (x <= cnm - 1) {
/*  49 */           list.add(source.get(start + i));
/*  50 */           start += i + 1;
/*  51 */           n -= i + 1;
/*  52 */           m--;
/*  53 */           break;
/*     */         }
/*  55 */         x -= cnm;
/*     */       }
/*     */     }
/*     */     
/*  59 */     return list;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String transInIds(int[] ids)
/*     */   {
/*  68 */     StringBuffer sb = new StringBuffer();
/*  69 */     int i = 0; for (int j = ids.length; i < j; i++) {
/*  70 */       sb.append(ids[i]);
/*  71 */       if (i < j - 1) {
/*  72 */         sb.append(",");
/*     */       }
/*     */     }
/*  75 */     return sb.toString();
/*     */   }
/*     */   
/*     */   public static String transInIds(Integer[] ids) {
/*  79 */     StringBuffer sb = new StringBuffer();
/*  80 */     int i = 0; for (int j = ids.length; i < j; i++) {
/*  81 */       sb.append(ids[i].intValue());
/*  82 */       if (i < j - 1) {
/*  83 */         sb.append(",");
/*     */       }
/*     */     }
/*  86 */     return sb.toString();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String transInIds(List<Integer> ids)
/*     */   {
/*  95 */     StringBuffer sb = new StringBuffer();
/*  96 */     int i = 0; for (int j = ids.size(); i < j; i++) {
/*  97 */       sb.append(((Integer)ids.get(i)).intValue());
/*  98 */       if (i < j - 1) {
/*  99 */         sb.append(",");
/*     */       }
/*     */     }
/* 102 */     return sb.toString();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String transInsertIds(int[] ids)
/*     */   {
/* 111 */     StringBuffer sb = new StringBuffer();
/* 112 */     int i = 0; for (int j = ids.length; i < j; i++) {
/* 113 */       sb.append("[" + ids[i] + "]");
/* 114 */       if (i < j - 1) {
/* 115 */         sb.append(",");
/*     */       }
/*     */     }
/* 118 */     return sb.toString();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static int[] transGetIds(String ids)
/*     */   {
/* 127 */     String[] tmp = ids.replaceAll("\\[|\\]", "").split(",");
/* 128 */     int[] arr = new int[tmp.length];
/* 129 */     for (int i = 0; i < arr.length; i++) {
/* 130 */       arr[i] = Integer.parseInt(tmp[i]);
/*     */     }
/* 132 */     return arr;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String toString(List<Integer> list)
/*     */   {
/* 141 */     StringBuffer sb = new StringBuffer();
/* 142 */     int i = 0; for (int j = list.size(); i < j; i++) {
/* 143 */       sb.append(list.get(i));
/* 144 */       if (i < j - 1) {
/* 145 */         sb.append(",");
/*     */       }
/*     */     }
/* 148 */     return sb.toString();
/*     */   }
/*     */   
/*     */   public static String toString(Object[] objs) {
/* 152 */     StringBuffer sb = new StringBuffer();
/* 153 */     for (Object obj : objs) {
/* 154 */       sb.append(obj.toString());
/*     */     }
/* 156 */     return sb.toString();
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
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void quickSortDouble(Double[] a, int low, int high)
/*     */   {
/* 176 */     if (low >= high) {
/* 177 */       return;
/*     */     }
/* 179 */     int low0 = low;
/* 180 */     int high0 = high;
/* 181 */     boolean forward = false;
/* 182 */     while (low0 != high0) {
/* 183 */       if (a[low0].doubleValue() > a[high0].doubleValue()) {
/* 184 */         double tmp = a[low0].doubleValue();
/* 185 */         a[low0] = a[high0];
/* 186 */         a[high0] = Double.valueOf(tmp);
/* 187 */         forward = !forward;
/*     */       }
/* 189 */       if (forward) {
/* 190 */         low0++;
/*     */       }
/*     */       else {
/* 193 */         high0--;
/*     */       }
/*     */     }
/* 196 */     low0--;
/* 197 */     high0++;
/* 198 */     quickSortDouble(a, low, low0);
/* 199 */     quickSortDouble(a, high0, high);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void quickSortDouble(Double[] a)
/*     */   {
/* 208 */     quickSortDouble(a, 0, a.length - 1);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void quickSortString(String[] a, int low, int high)
/*     */   {
/* 229 */     if (low >= high) {
/* 230 */       return;
/*     */     }
/* 232 */     int low0 = low;
/* 233 */     int high0 = high;
/* 234 */     boolean forward = false;
/* 235 */     while (low0 != high0) {
/* 236 */       if (a[low0].compareTo(a[high0]) > 0) {
/* 237 */         String tmp = a[low0];
/* 238 */         a[low0] = a[high0];
/* 239 */         a[high0] = tmp;
/* 240 */         forward = !forward;
/*     */       }
/* 242 */       if (forward) {
/* 243 */         low0++;
/*     */       }
/*     */       else {
/* 246 */         high0--;
/*     */       }
/*     */     }
/* 249 */     low0--;
/* 250 */     high0++;
/* 251 */     quickSortString(a, low, low0);
/* 252 */     quickSortString(a, high0, high);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void quickSortString(String[] a)
/*     */   {
/* 261 */     quickSortString(a, 0, a.length - 1);
/*     */   }
/*     */   
/*     */   public static void main(String[] args) {}
/*     */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/javautils/array/ArrayUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */