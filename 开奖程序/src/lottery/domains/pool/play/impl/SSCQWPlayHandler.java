/*    */ package lottery.domains.pool.play.impl;
/*    */ 
/*    */ import lottery.domains.pool.play.ITicketPlayHandler;
/*    */ import lottery.domains.pool.play.WinResult;
/*    */ import lottery.domains.pool.util.TicketPlayUtils;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SSCQWPlayHandler
/*    */   implements ITicketPlayHandler
/*    */ {
/*    */   private String playId;
/*    */   private int[] offsets;
/*    */   private int rxNum;
/*    */   
/*    */   public SSCQWPlayHandler(String playId, int[] offsets, int rxNum)
/*    */   {
/* 19 */     this.playId = playId;
/* 20 */     this.offsets = offsets;
/* 21 */     this.rxNum = rxNum;
/*    */   }
/*    */   
/*    */   public String[] getBetNums(String betNums) {
/* 25 */     String[] bets = betNums.trim().split(",");
/* 26 */     return TicketPlayUtils.getFixedNums(bets);
/*    */   }
/*    */   
/*    */   public String[] getOpenNums(String openNums)
/*    */   {
/* 31 */     return TicketPlayUtils.getOpenNums(openNums, this.offsets);
/*    */   }
/*    */   
/*    */   public WinResult calculateWinNum(int userBetsId, String index, String openNums)
/*    */   {
/* 36 */     WinResult result = new WinResult();
/* 37 */     String[] openNum = getOpenNums(openNums);
/* 38 */     String[] nums = getBetNums(index);
/* 39 */     if ((nums == null) || (openNum == null) || (openNum.length != this.offsets.length)) {
/* 40 */       return result;
/*    */     }
/* 42 */     int winNum = 0;
/* 43 */     for (int i = 0; i < nums.length; i++) {
/* 44 */       int count = 0;
/* 45 */       for (int j = 0; j < openNum.length; j++) {
/* 46 */         if (nums[i].equals(openNum[j])) {
/* 47 */           count++;
/*    */         }
/*    */       }
/* 50 */       if (count >= this.rxNum) {
/* 51 */         winNum++;
/* 52 */         count = 0;
/*    */       }
/*    */     }
/*    */     
/* 56 */     result.setPlayId(this.playId);
/* 57 */     result.setWinNum(winNum);
/* 58 */     return result;
/*    */   }
/*    */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/pool/play/impl/SSCQWPlayHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */