/*     */ package lottery.domains.pool.util;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashSet;
/*     */ import java.util.LinkedList;
/*     */ import java.util.Set;
/*     */ import java.util.TreeSet;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class TicketPlayUtils
/*     */ {
/*     */   public static String[] getOpenNums(String openNums)
/*     */   {
/*  15 */     String[] nums = null;
/*  16 */     if (openNums.contains(",")) {
/*  17 */       nums = openNums.split(",");
/*  18 */     } else if (openNums.trim().contains(" ")) {
/*  19 */       nums = openNums.split(" ");
/*     */     } else {
/*  21 */       nums = new String[openNums.trim().length()];
/*  22 */       for (int i = 0; i < openNums.trim().length(); i++) {
/*  23 */         nums[i] = String.valueOf(openNums.trim().charAt(i));
/*     */       }
/*     */     }
/*  26 */     if (nums == null) {
/*  27 */       return null;
/*     */     }
/*  29 */     return nums;
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
/*     */   public static String[] getOpenNums(String openNums, int[] offsets)
/*     */   {
/*  42 */     String[] nums = null;
/*  43 */     if (openNums.contains(",")) {
/*  44 */       nums = openNums.split(",");
/*  45 */     } else if (openNums.trim().contains(" ")) {
/*  46 */       nums = openNums.split(" ");
/*     */     } else {
/*  48 */       nums = new String[openNums.trim().length()];
/*  49 */       for (int i = 0; i < openNums.trim().length(); i++) {
/*  50 */         nums[i] = String.valueOf(openNums.trim().charAt(i));
/*     */       }
/*     */     }
/*  53 */     if (nums == null) {
/*  54 */       return null;
/*     */     }
/*  56 */     String[] res = new String[offsets.length];
/*  57 */     for (int i = 0; i < offsets.length; i++) {
/*  58 */       res[i] = nums[offsets[i]].trim();
/*     */     }
/*  60 */     return res;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String[] sortByDescGetOpenNums(String openNums, int[] offsets)
/*     */   {
/*  70 */     String[] nums = null;
/*  71 */     if (openNums.contains(",")) {
/*  72 */       nums = openNums.split(",");
/*  73 */     } else if (openNums.trim().contains(" ")) {
/*  74 */       nums = openNums.split(" ");
/*     */     } else {
/*  76 */       nums = new String[openNums.trim().length()];
/*  77 */       for (int i = 0; i < openNums.trim().length(); i++) {
/*  78 */         nums[i] = String.valueOf(openNums.trim().charAt(i));
/*     */       }
/*     */     }
/*  81 */     if (nums == null) {
/*  82 */       return null;
/*     */     }
/*     */     
/*  85 */     Arrays.sort(nums);
/*     */     
/*  87 */     String[] descNums = new String[nums.length];
/*     */     
/*  89 */     for (int i = nums.length - 1; i >= 0; i--) {
/*  90 */       descNums[(nums.length - i - 1)] = nums[i];
/*     */     }
/*     */     
/*  93 */     String[] res = new String[offsets.length];
/*  94 */     for (int i = 0; i < offsets.length; i++) {
/*  95 */       res[i] = descNums[offsets[i]].trim();
/*     */     }
/*  97 */     return res;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String[] getSortedOpenNums(String openNums, int[] offsets)
/*     */   {
/* 108 */     String[] res = getOpenNums(openNums, offsets);
/* 109 */     Arrays.sort(res);
/* 110 */     return res;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String getSortedNums(String nums)
/*     */   {
/* 121 */     char[] c = nums.toCharArray();
/* 122 */     Arrays.sort(c);
/* 123 */     return new String(c);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static int getOpenNumSum(String openNums, int[] offsets)
/*     */   {
/* 134 */     String[] nums = getOpenNums(openNums, offsets);
/* 135 */     int sum = 0;
/* 136 */     for (String num : nums) {
/* 137 */       sum += Integer.parseInt(num);
/*     */     }
/* 139 */     return sum;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static int getOpenNumSumByStr(String openNums, String[] openCodes)
/*     */   {
/* 150 */     int sum = 0;
/* 151 */     for (String num : openCodes) {
/* 152 */       sum += Integer.parseInt(num);
/*     */     }
/* 154 */     return sum;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String toIndexString(String betNums)
/*     */   {
/* 164 */     StringBuilder sb = new StringBuilder();
/* 165 */     for (int i = 0; i < betNums.length(); i++) {
/* 166 */       sb.append(betNums.charAt(i)).append(" ");
/*     */     }
/* 168 */     return sb.toString().trim();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String[] getFixedNums(String[] nums)
/*     */   {
/* 178 */     if ((nums == null) || (nums.length <= 0)) {
/* 179 */       return null;
/*     */     }
/* 181 */     Set<String> res = new HashSet();
/* 182 */     for (String num : nums) {
/* 183 */       if (StringUtils.isNotEmpty(num)) {
/* 184 */         res.add(num);
/*     */       }
/*     */     }
/*     */     
/* 188 */     Object arr = new LinkedList(res);
/* 189 */     return (String[])((LinkedList)arr).toArray(new String[0]);
/*     */   }
/*     */   
/*     */   public static String[] getFixedAndSortedNums(String[] nums) {
/* 193 */     if ((nums == null) || (nums.length <= 0)) {
/* 194 */       return null;
/*     */     }
/* 196 */     TreeSet<String> res = new TreeSet();
/* 197 */     for (String num : nums) {
/* 198 */       if (StringUtils.isNotEmpty(num)) {
/* 199 */         res.add(num);
/*     */       }
/*     */     }
/* 202 */     return (String[])res.toArray(new String[0]);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String getOpenDualNum(String[] opencodes)
/*     */   {
/* 212 */     Arrays.sort(opencodes);
/* 213 */     for (int i = 0; i < opencodes.length; i++) {
/* 214 */       int we = 0;
/* 215 */       for (int j = 0; j < opencodes.length; j++) {
/* 216 */         if (opencodes[i].equals(opencodes[j])) {
/* 217 */           we++;
/*     */         }
/*     */       }
/* 220 */       if (we >= 2) {
/* 221 */         return opencodes[i];
/*     */       }
/*     */     }
/* 224 */     return null;
/*     */   }
/*     */   
/*     */   public static void main(String[] args)
/*     */   {
/* 229 */     String[] re = { "1", "2", "3", "5", "5", "6", "2" };
/* 230 */     System.out.println(getOpenDualNum(re));
/*     */   }
/*     */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/pool/util/TicketPlayUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */