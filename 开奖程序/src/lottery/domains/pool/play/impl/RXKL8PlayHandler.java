/*    */ package lottery.domains.pool.play.impl;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import lottery.domains.pool.play.ITicketPlayHandler;
/*    */ import lottery.domains.pool.play.WinResult;
/*    */ import lottery.domains.pool.util.TicketPlayUtils;
/*    */ 
/*    */ public class RXKL8PlayHandler
/*    */   implements ITicketPlayHandler
/*    */ {
/*    */   private String playId;
/*    */   private int bdwNum;
/*    */   private int[] offsets;
/*    */   
/*    */   public RXKL8PlayHandler(String playId, int bdwNum, int[] offsets)
/*    */   {
/* 17 */     this.playId = playId;
/* 18 */     this.bdwNum = bdwNum;
/* 19 */     this.offsets = offsets;
/*    */   }
/*    */   
/*    */ 
/*    */   public String[] getBetNums(String betNums)
/*    */   {
/* 25 */     return betNums.trim().split(",");
/*    */   }
/*    */   
/*    */   public String[] getOpenNums(String openNums)
/*    */   {
/* 30 */     return TicketPlayUtils.getOpenNums(openNums, this.offsets);
/*    */   }
/*    */   
/*    */   public WinResult calculateWinNum(int userBetsId, String index, String openNums) {
/* 34 */     WinResult result = new WinResult();
/* 35 */     String[] nums = getBetNums(index);
/* 36 */     String[] openNum = getOpenNums(openNums);
/* 37 */     if ((nums == null) || (openNum == null) || (openNum.length != this.offsets.length)) {
/* 38 */       return result;
/*    */     }
/* 40 */     int matchNum = bdwMatchNum(nums, openNum);
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
/*    */ 
/* 53 */     if (matchNum < this.bdwNum) {
/* 54 */       return result;
/*    */     }
/* 56 */     result.setPlayId(this.playId);
/* 57 */     result.setWinNum(bdwNum(matchNum, this.bdwNum));
/* 58 */     return result;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   private static int bdwMatchNum(String[] betNums, String[] openNums)
/*    */   {
/* 69 */     int matchNum = 0;
/* 70 */     Arrays.sort(openNums);
/* 71 */     for (String num : betNums) {
/* 72 */       if (Arrays.binarySearch(openNums, num) >= 0) {
/* 73 */         matchNum++;
/*    */       }
/*    */     }
/* 76 */     return matchNum;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   private static int bdwNum(int betNum, int bdwNum)
/*    */   {
/* 89 */     int upCount = 1;
/* 90 */     int downCount = 1;
/* 91 */     for (int i = 0; i < bdwNum; i++) {
/* 92 */       upCount *= (betNum - i);
/*    */     }
/* 94 */     for (int a = 1; a <= bdwNum; a++) {
/* 95 */       downCount *= a;
/*    */     }
/* 97 */     return upCount / downCount;
/*    */   }
/*    */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/pool/play/impl/RXKL8PlayHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */