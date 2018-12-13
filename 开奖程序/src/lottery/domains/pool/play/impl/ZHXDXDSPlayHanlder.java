/*     */ package lottery.domains.pool.play.impl;
/*     */ 
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
/*     */ 
/*     */ 
/*     */ public class ZHXDXDSPlayHanlder
/*     */   implements ITicketPlayHandler
/*     */ {
/*     */   private String playId;
/*     */   private int[] offsets;
/*  20 */   String[] OFFSETS_SSC_DA = { "5", "6", "7", "8", "9" };
/*     */   
/*  22 */   String[] OFFSETS_SSC_XIAO = { "0", "1", "2", "3", "4" };
/*     */   
/*  24 */   String[] OFFSETS_SSC_DAN = { "1", "3", "5", "7", "9" };
/*     */   
/*  26 */   String[] OFFSETS_SSC_SHUANG = { "0", "2", "4", "6", "8" };
/*     */   
/*     */   public ZHXDXDSPlayHanlder(String playId, int[] offsets) {
/*  29 */     this.playId = playId;
/*  30 */     this.offsets = offsets;
/*     */   }
/*     */   
/*     */   public String[] getBetNums(String betNums)
/*     */   {
/*  35 */     return betNums.trim().split(",");
/*     */   }
/*     */   
/*     */   public String[] getOpenNums(String openNums)
/*     */   {
/*  40 */     return TicketPlayUtils.getOpenNums(openNums, this.offsets);
/*     */   }
/*     */   
/*     */   public WinResult calculateWinNum(int userBetsId, String index, String openNums)
/*     */   {
/*  45 */     WinResult result = new WinResult();
/*  46 */     String[] nums = getBetNums(index);
/*  47 */     String[] openNum = getOpenNums(openNums);
/*  48 */     if ((nums == null) || (openNums == null)) {
/*  49 */       return result;
/*     */     }
/*  51 */     if ((openNum == null) || (openNum.length != this.offsets.length)) {
/*  52 */       return result;
/*     */     }
/*  54 */     int index0 = 0;
/*  55 */     int index1 = 0;
/*     */     
/*  57 */     char[] bet0 = nums[0].toCharArray();
/*  58 */     for (int j = 0; j < bet0.length; j++) {
/*  59 */       String[] bj0 = matchingBetNum(String.valueOf(bet0[j]));
/*  60 */       if (bj0 == null) {
/*  61 */         return result;
/*     */       }
/*  63 */       if (Arrays.binarySearch(bj0, openNum[0]) >= 0) {
/*  64 */         index0++;
/*     */       }
/*     */     }
/*     */     
/*  68 */     char[] bet1 = nums[1].toCharArray();
/*  69 */     for (int n = 0; n < bet1.length; n++) {
/*  70 */       String[] bj1 = matchingBetNum(String.valueOf(bet1[n]));
/*  71 */       if (bj1 == null) {
/*  72 */         return result;
/*     */       }
/*  74 */       if (Arrays.binarySearch(bj1, openNum[1]) >= 0) {
/*  75 */         index1++;
/*     */       }
/*     */     }
/*     */     
/*  79 */     int winNum = 0;
/*  80 */     if ((index0 > 0) && (index1 > 0)) {
/*  81 */       winNum = index0 * index1;
/*     */     }
/*  83 */     result.setPlayId(this.playId);
/*  84 */     result.setWinNum(winNum);
/*  85 */     return result;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private String[] matchingBetNum(String betNum)
/*     */   {
/*  94 */     switch (betNum) {
/*     */     case "大": 
/*  96 */       return this.OFFSETS_SSC_DA;
/*     */     case "小": 
/*  98 */       return this.OFFSETS_SSC_XIAO;
/*     */     case "单": 
/* 100 */       return this.OFFSETS_SSC_DAN;
/*     */     case "双": 
/* 102 */       return this.OFFSETS_SSC_SHUANG;
/*     */     }
/* 104 */     return null;
/*     */   }
/*     */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/pool/play/impl/ZHXDXDSPlayHanlder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */