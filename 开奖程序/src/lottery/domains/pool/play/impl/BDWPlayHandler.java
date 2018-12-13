/*     */ package lottery.domains.pool.play.impl;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.util.Arrays;
/*     */ import lottery.domains.pool.play.ITicketPlayHandler;
/*     */ import lottery.domains.pool.play.WinResult;
/*     */ import lottery.domains.pool.util.TicketPlayUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BDWPlayHandler
/*     */   implements ITicketPlayHandler
/*     */ {
/*     */   private String playId;
/*     */   private int bdwNum;
/*     */   private int[] offsets;
/*     */   
/*     */   public BDWPlayHandler(String playId, int bdwNum, int[] offsets)
/*     */   {
/*  23 */     this.playId = playId;
/*  24 */     this.bdwNum = bdwNum;
/*  25 */     this.offsets = offsets;
/*     */   }
/*     */   
/*     */ 
/*     */   public String[] getBetNums(String betNums)
/*     */   {
/*  31 */     return betNums.trim().split(",");
/*     */   }
/*     */   
/*     */   public String[] getOpenNums(String openNums)
/*     */   {
/*  36 */     return TicketPlayUtils.getOpenNums(openNums, this.offsets);
/*     */   }
/*     */   
/*     */   public WinResult calculateWinNum(int userBetsId, String index, String openNums) {
/*  40 */     WinResult result = new WinResult();
/*  41 */     String[] nums = getBetNums(index);
/*  42 */     String[] openNum = getOpenNums(openNums);
/*  43 */     if ((nums == null) || (openNum == null) || (openNum.length != this.offsets.length)) {
/*  44 */       return result;
/*     */     }
/*  46 */     int matchNum = bdwMatchNum(nums, openNum);
/*  47 */     if (this.bdwNum > 5) {
/*  48 */       if (matchNum < 5) {
/*  49 */         return result;
/*     */       }
/*  51 */       result.setPlayId(this.playId);
/*  52 */       int wnum = bdwNum(nums.length - matchNum, this.bdwNum - 5);
/*  53 */       result.setWinNum(wnum);
/*  54 */       if (wnum > 0)
/*     */       {
/*  56 */         result.setWinCode(openNum[0]);
/*     */       }
/*  58 */       return result;
/*     */     }
/*  60 */     if (matchNum < this.bdwNum) {
/*  61 */       return result;
/*     */     }
/*  63 */     result.setPlayId(this.playId);
/*  64 */     int wnum = bdwNum(matchNum, this.bdwNum);
/*  65 */     result.setWinNum(wnum);
/*  66 */     if (wnum > 0)
/*     */     {
/*  68 */       result.setWinCode(openNum[0]);
/*     */     }
/*  70 */     return result;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static int bdwMatchNum(String[] betNums, String[] openNums)
/*     */   {
/*  82 */     int matchNum = 0;
/*  83 */     Arrays.sort(openNums);
/*  84 */     for (String num : betNums) {
/*  85 */       if (Arrays.binarySearch(openNums, num) >= 0) {
/*  86 */         matchNum++;
/*     */       }
/*     */     }
/*  89 */     return matchNum;
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
/*     */   private static int bdwNum(int betNum, int bdwNum)
/*     */   {
/* 102 */     int upCount = 1;
/* 103 */     int downCount = 1;
/* 104 */     for (int i = 0; i < bdwNum; i++) {
/* 105 */       upCount *= (betNum - i);
/*     */     }
/* 107 */     for (int a = 1; a <= bdwNum; a++) {
/* 108 */       downCount *= a;
/*     */     }
/* 110 */     return upCount / downCount;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/* 118 */     BDWPlayHandler cqssc_bdwwx3m = new BDWPlayHandler("1_bdwwx3m", 3, ITicketPlayHandler.OFFSETS_WUXIN);
/* 119 */     WinResult winResult = cqssc_bdwwx3m.calculateWinNum(1, "0,1,2,3,4,5,6,7", "7,3,2,0,1");
/* 120 */     System.out.println(winResult.getWinNum());
/*     */   }
/*     */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/pool/play/impl/BDWPlayHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */