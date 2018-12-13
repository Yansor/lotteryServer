/*     */ package lottery.domains.pool.play.impl;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
/*     */ import javautils.StringUtil;
/*     */ import lottery.domains.pool.play.ITicketPlayHandler;
/*     */ import lottery.domains.pool.play.MultipleResult;
/*     */ import lottery.domains.pool.play.WinResult;
/*     */ import lottery.domains.pool.util.TicketPlayUtils;
/*     */ 
/*     */ public class SSCNiuNiuPlayHanlder implements ITicketPlayHandler
/*     */ {
/*     */   private String playId;
/*     */   private int[] offsets;
/*     */   private String codeSeparator;
/*  21 */   private static final String[] ODDS_INDEXES = { "牛大", "牛小", "牛单", "牛双", "无牛", "牛牛", "牛1", "牛2", "牛3", "牛4", "牛5", "牛6", "牛7", "牛8", "牛9", "五条", "炸弹", "葫芦", "顺子", "三条", "两对", "单对", "散号" };
/*     */   private static final String NIU_DA = "牛大";
/*     */   private static final String NIU_XIAO = "牛小";
/*     */   private static final String NIU_DAN = "牛单";
/*     */   private static final String NIU_SHUANG = "牛双";
/*     */   private static final String WU_NIU = "无牛";
/*     */   private static final String NIU_NIU = "牛牛";
/*     */   private static final String NIU_1 = "牛1";
/*     */   private static final String NIU_2 = "牛2";
/*     */   private static final String NIU_3 = "牛3";
/*     */   private static final String NIU_4 = "牛4";
/*     */   private static final String NIU_5 = "牛5";
/*     */   private static final String NIU_6 = "牛6";
/*     */   private static final String NIU_7 = "牛7";
/*     */   private static final String NIU_8 = "牛8";
/*     */   private static final String NIU_9 = "牛9";
/*     */   private static final String WU_TIAO = "五条";
/*     */   private static final String ZHA_DAN = "炸弹";
/*     */   private static final String HU_LU = "葫芦";
/*     */   private static final String SHUN_ZI = "顺子";
/*     */   private static final String SAN_TIAO = "三条";
/*     */   private static final String LIANG_DUI = "两对";
/*     */   private static final String DAN_DUI = "单对";
/*     */   private static final String SAN_HAO = "散号";
/*     */   
/*     */   public SSCNiuNiuPlayHanlder(String playId, int[] offsets, String codeSeparator) {
/*  47 */     this.playId = playId;
/*  48 */     if (offsets.length != 5) {
/*  49 */       throw new RuntimeException("牛牛玩法必须只有5位开奖号码");
/*     */     }
/*  51 */     this.offsets = offsets;
/*  52 */     this.codeSeparator = codeSeparator;
/*     */   }
/*     */   
/*     */   public String[] getBetNums(String betNums) {
/*  56 */     return betNums.trim().split(this.codeSeparator);
/*     */   }
/*     */   
/*     */   public String[] getOpenNums(String openNums)
/*     */   {
/*  61 */     return TicketPlayUtils.getOpenNums(openNums, this.offsets);
/*     */   }
/*     */   
/*     */   private Integer[] countNums(String[] openNumsArr) {
/*  65 */     Map<String, Integer> numsRepatMap = StringUtil.countChars(openNumsArr);
/*     */     
/*  67 */     Collection<Integer> values = numsRepatMap.values();
/*  68 */     Integer[] counts = (Integer[])values.toArray(new Integer[0]);
/*  69 */     return counts;
/*     */   }
/*     */   
/*     */   private int[] sortNums(String[] openNumsArr) {
/*  73 */     int[] openNumInt = new int[openNumsArr.length];
/*  74 */     for (int i = 0; i < openNumsArr.length; i++) {
/*  75 */       openNumInt[i] = Integer.valueOf(openNumsArr[i]).intValue();
/*     */     }
/*     */     
/*  78 */     Arrays.sort(openNumInt);
/*  79 */     return openNumInt;
/*     */   }
/*     */   
/*     */   public WinResult calculateWinNum(int userBetsId, String betCodes, String openNums)
/*     */   {
/*  84 */     WinResult result = new WinResult();
/*  85 */     String[] betNums = getBetNums(betCodes);
/*  86 */     String[] openNumsArr = getOpenNums(openNums);
/*  87 */     if ((betNums == null) || (betNums == null)) {
/*  88 */       return result;
/*     */     }
/*  90 */     if ((openNumsArr == null) || (openNumsArr == null)) {
/*  91 */       return result;
/*     */     }
/*     */     
/*  94 */     Integer[] numCounts = countNums(openNumsArr);
/*  95 */     int[] sortedNums = sortNums(openNumsArr);
/*     */     
/*     */ 
/*  98 */     int niuNum = calculateNiuNum(sortedNums);
/*     */     
/* 100 */     boolean isWuTiao = isWuTiao(sortedNums);
/*     */     
/* 102 */     boolean isSiTiao = isSiTiao(numCounts);
/*     */     
/* 104 */     boolean isHulu = isHulu(numCounts);
/*     */     
/* 106 */     boolean isShunZi = isShunZi(sortedNums);
/*     */     
/* 108 */     boolean isSanTiao = isSanTiao(numCounts);
/*     */     
/* 110 */     boolean isLiangDui = isLiangDui(numCounts);
/*     */     
/* 112 */     boolean isDanDui = isDanDui(numCounts);
/*     */     
/* 114 */     boolean isSanHao = isSanHao(sortedNums);
/*     */     
/*     */ 
/* 117 */     Map<String, MultipleResult> codeResults = new HashMap();
/* 118 */     int winNum = 0;
/* 119 */     for (String betNum : betNums)
/*     */     {
/* 121 */       boolean hit = false;
/* 122 */       int prizeIndex = -1;
/*     */       
/* 124 */       switch (betNum) {
/* 125 */       case "牛大":  hit = (niuNum >= 6) || (niuNum == 0);prizeIndex = 0; break;
/* 126 */       case "牛小":  hit = (niuNum >= 1) && (niuNum <= 5);prizeIndex = 1; break;
/* 127 */       case "牛单":  hit = (niuNum > 0) && (niuNum % 2 == 1);prizeIndex = 2; break;
/* 128 */       case "牛双":  hit = (niuNum > -1) && (niuNum % 2 == 0);prizeIndex = 3; break;
/* 129 */       case "无牛":  hit = niuNum == -1;prizeIndex = 4; break;
/* 130 */       case "牛牛":  hit = niuNum == 0;prizeIndex = 5; break;
/* 131 */       case "牛1":  hit = niuNum == 1;prizeIndex = 6; break;
/* 132 */       case "牛2":  hit = niuNum == 2;prizeIndex = 7; break;
/* 133 */       case "牛3":  hit = niuNum == 3;prizeIndex = 8; break;
/* 134 */       case "牛4":  hit = niuNum == 4;prizeIndex = 9; break;
/* 135 */       case "牛5":  hit = niuNum == 5;prizeIndex = 10; break;
/* 136 */       case "牛6":  hit = niuNum == 6;prizeIndex = 11; break;
/* 137 */       case "牛7":  hit = niuNum == 7;prizeIndex = 12; break;
/* 138 */       case "牛8":  hit = niuNum == 8;prizeIndex = 13; break;
/* 139 */       case "牛9":  hit = niuNum == 9;prizeIndex = 14; break;
/* 140 */       case "五条":  hit = isWuTiao;prizeIndex = 15; break;
/* 141 */       case "炸弹":  hit = isSiTiao;prizeIndex = 16; break;
/* 142 */       case "葫芦":  hit = isHulu;prizeIndex = 17; break;
/* 143 */       case "顺子":  hit = isShunZi;prizeIndex = 18; break;
/* 144 */       case "三条":  hit = isSanTiao;prizeIndex = 19; break;
/* 145 */       case "两对":  hit = isLiangDui;prizeIndex = 20; break;
/* 146 */       case "单对":  hit = isDanDui;prizeIndex = 21; break;
/* 147 */       case "散号":  hit = isSanHao;prizeIndex = 22; break;
/* 148 */       default:  hit = false;
/*     */       }
/*     */       
/* 151 */       if (hit) {
/* 152 */         winNum = 1;
///*     */         MultipleResult codeResult;
/* 154 */         MultipleResult codeResult; if (codeResults.containsKey(betNum)) {
/* 155 */           codeResult = (MultipleResult)codeResults.get(betNum);
/*     */         }
/*     */         else {
/* 158 */           codeResult = new MultipleResult(betNum, prizeIndex);
/* 159 */           codeResults.put(betNum, codeResult);
/*     */         }
/* 161 */         codeResult.increseNum();
/*     */       }
/*     */     }
/* 164 */     result.setPlayId(this.playId);
/* 165 */     result.setWinNum(winNum);
/* 166 */     result.setMultipleResults(new ArrayList(codeResults.values()));
/* 167 */     return result;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private int calculateNiuNum(int[] nums)
/*     */   {
/* 178 */     int niuNum = -1;
/* 179 */     for (int i = 0; i < nums.length; i++) {
/* 180 */       for (int j = i + 1; j < nums.length; j++) {
/* 181 */         for (int k = j + 1; k < nums.length; k++)
/*     */         {
/* 183 */           int num1 = nums[i];
/* 184 */           int num2 = nums[j];
/* 185 */           int num3 = nums[k];
/*     */           
/* 187 */           int niuSum = num1 + num2 + num3;
/*     */           
/* 189 */           if (niuSum % 10 == 0)
/*     */           {
/* 191 */             int num4Index = -1;
/* 192 */             int num5Index = -1;
/*     */             
/* 194 */             if ((i != 0) && (j != 0) && (k != 0)) num4Index = 0;
/* 195 */             if ((i != 1) && (j != 1) && (k != 1)) num4Index = 1;
/* 196 */             if ((i != 2) && (j != 2) && (k != 2)) num4Index = 2;
/* 197 */             if ((i != 3) && (j != 3) && (k != 3)) num4Index = 3;
/* 198 */             if ((i != 4) && (j != 4) && (k != 4)) { num4Index = 4;
/*     */             }
/* 200 */             if ((i != 0) && (j != 0) && (k != 0) && (num4Index != 0)) num5Index = 0;
/* 201 */             if ((i != 1) && (j != 1) && (k != 1) && (num4Index != 1)) num5Index = 1;
/* 202 */             if ((i != 2) && (j != 2) && (k != 2) && (num4Index != 2)) num5Index = 2;
/* 203 */             if ((i != 3) && (j != 3) && (k != 3) && (num4Index != 3)) num5Index = 3;
/* 204 */             if ((i != 4) && (j != 4) && (k != 4) && (num4Index != 4)) { num5Index = 4;
/*     */             }
/* 206 */             int num4 = nums[num4Index];
/* 207 */             int num5 = nums[num5Index];
/* 208 */             int remainSum = num4 + num5;
/* 209 */             niuNum = remainSum % 10;
/*     */             
/*     */ 
/* 212 */             j = nums.length;
/* 213 */             i = nums.length;
/* 214 */             break;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 220 */     return niuNum;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private boolean isWuTiao(int[] nums)
/*     */   {
/* 227 */     int num1 = nums[0];
/* 228 */     int num2 = nums[1];
/* 229 */     int num3 = nums[2];
/* 230 */     int num4 = nums[3];
/* 231 */     int num5 = nums[4];
/*     */     
/* 233 */     return (num1 == num2) && (num2 == num3) && (num3 == num4) && (num4 == num5);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private boolean isSiTiao(Integer[] numCounts)
/*     */   {
/* 243 */     if ((numCounts == null) || (numCounts.length != 2)) {
/* 244 */       return false;
/*     */     }
/*     */     
/* 247 */     int repeatNum1 = numCounts[0].intValue();
/* 248 */     int repeatNum2 = numCounts[1].intValue();
/*     */     
/* 250 */     if ((repeatNum1 == 4) && (repeatNum2 == 1)) {
/* 251 */       return true;
/*     */     }
/*     */     
/* 254 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private boolean isHulu(Integer[] numCounts)
/*     */   {
/* 261 */     if ((numCounts == null) || (numCounts.length != 2)) {
/* 262 */       return false;
/*     */     }
/*     */     
/* 265 */     int repeatNum1 = numCounts[0].intValue();
/* 266 */     int repeatNum2 = numCounts[1].intValue();
/*     */     
/* 268 */     if ((repeatNum1 == 3) && (repeatNum2 == 2)) {
/* 269 */       return true;
/*     */     }
/*     */     
/* 272 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private boolean isShunZi(int[] nums)
/*     */   {
/* 282 */     int num1 = nums[0];
/* 283 */     int num2 = nums[1];
/* 284 */     int num3 = nums[2];
/* 285 */     int num4 = nums[3];
/* 286 */     int num5 = nums[4];
/*     */     
/* 288 */     if ((num1 == 0) && (num2 == 1) && (num3 == 2) && (num4 == 3) && (num5 == 4)) {
/* 289 */       return true;
/*     */     }
/* 291 */     if ((num1 == 1) && (num2 == 2) && (num3 == 3) && (num4 == 4) && (num5 == 5)) {
/* 292 */       return true;
/*     */     }
/* 294 */     if ((num1 == 2) && (num2 == 3) && (num3 == 4) && (num4 == 5) && (num5 == 6)) {
/* 295 */       return true;
/*     */     }
/* 297 */     if ((num1 == 3) && (num2 == 4) && (num3 == 5) && (num4 == 6) && (num5 == 7)) {
/* 298 */       return true;
/*     */     }
/* 300 */     if ((num1 == 4) && (num2 == 5) && (num3 == 6) && (num4 == 7) && (num5 == 8)) {
/* 301 */       return true;
/*     */     }
/* 303 */     if ((num1 == 5) && (num2 == 6) && (num3 == 7) && (num4 == 8) && (num5 == 9)) {
/* 304 */       return true;
/*     */     }
/* 306 */     if ((num1 == 0) && (num2 == 6) && (num3 == 7) && (num4 == 8) && (num5 == 9)) {
/* 307 */       return true;
/*     */     }
/* 309 */     if ((num1 == 0) && (num2 == 1) && (num3 == 7) && (num4 == 8) && (num5 == 9)) {
/* 310 */       return true;
/*     */     }
/* 312 */     if ((num1 == 0) && (num2 == 1) && (num3 == 2) && (num4 == 8) && (num5 == 9)) {
/* 313 */       return true;
/*     */     }
/* 315 */     if ((num1 == 0) && (num2 == 1) && (num3 == 2) && (num4 == 3) && (num5 == 9)) {
/* 316 */       return true;
/*     */     }
/*     */     
/* 319 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private boolean isSanTiao(Integer[] numCounts)
/*     */   {
/* 326 */     if ((numCounts == null) || (numCounts.length != 3)) {
/* 327 */       return false;
/*     */     }
/*     */     
/* 330 */     int repeatNum1 = numCounts[0].intValue();
/* 331 */     int repeatNum2 = numCounts[1].intValue();
/* 332 */     int repeatNum3 = numCounts[2].intValue();
/*     */     
/* 334 */     if ((repeatNum1 == 3) && (repeatNum2 == 1) && (repeatNum3 == 1)) {
/* 335 */       return true;
/*     */     }
/*     */     
/* 338 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private boolean isLiangDui(Integer[] numCounts)
/*     */   {
/* 345 */     if ((numCounts == null) || (numCounts.length != 3)) {
/* 346 */       return false;
/*     */     }
/*     */     
/* 349 */     int repeatNum1 = numCounts[0].intValue();
/* 350 */     int repeatNum2 = numCounts[1].intValue();
/* 351 */     int repeatNum3 = numCounts[2].intValue();
/*     */     
/* 353 */     if ((repeatNum1 == 2) && (repeatNum2 == 2) && (repeatNum3 == 1)) {
/* 354 */       return true;
/*     */     }
/*     */     
/* 357 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private boolean isDanDui(Integer[] numCounts)
/*     */   {
/* 364 */     if ((numCounts == null) || (numCounts.length != 4)) {
/* 365 */       return false;
/*     */     }
/*     */     
/* 368 */     int repeatNum1 = numCounts[0].intValue();
/* 369 */     int repeatNum2 = numCounts[1].intValue();
/* 370 */     int repeatNum3 = numCounts[2].intValue();
/* 371 */     int repeatNum4 = numCounts[3].intValue();
/*     */     
/* 373 */     if ((repeatNum1 == 2) && (repeatNum2 == 1) && (repeatNum3 == 1) && (repeatNum4 == 1)) {
/* 374 */       return true;
/*     */     }
/*     */     
/* 377 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private boolean isSanHao(int[] nums)
/*     */   {
/* 384 */     HashSet<Integer> repeatNums = new HashSet();
/* 385 */     for (int num : nums) {
/* 386 */       if (repeatNums.contains(Integer.valueOf(num))) {
/* 387 */         return false;
/*     */       }
/*     */       
/* 390 */       repeatNums.add(Integer.valueOf(num));
/*     */     }
/*     */     
/* 393 */     if (repeatNums.size() == 5) {
/* 394 */       if (isShunZi(nums)) {
/* 395 */         return false;
/*     */       }
/*     */       
/* 398 */       return true;
/*     */     }
/*     */     
/* 401 */     return false;
/*     */   }
/*     */   
/*     */   public static void main(String[] args) {
/* 405 */     String codes = "无牛";
/* 406 */     String openNum = "3,7,2,3,1";
/*     */     
/* 408 */     SSCNiuNiuPlayHanlder sscNiuNiuPlayHanlder = new SSCNiuNiuPlayHanlder("sscniuniu", ITicketPlayHandler.OFFSETS_WUXIN, ",");
/* 409 */     WinResult winResult = sscNiuNiuPlayHanlder.calculateWinNum(1, codes, openNum);
/* 410 */     System.out.println(winResult.getWinNum());
/* 411 */     for (MultipleResult multipleResult : winResult.getMultipleResults()) {
/* 412 */       System.out.println(multipleResult.getCode() + "-" + multipleResult.getOddsIndex() + "-" + multipleResult.getNums());
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/pool/play/impl/SSCNiuNiuPlayHanlder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */