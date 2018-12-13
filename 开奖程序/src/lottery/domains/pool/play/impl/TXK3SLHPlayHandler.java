/*    */ package lottery.domains.pool.play.impl;
/*    */ 
/*    */ import lottery.domains.pool.play.ITicketPlayHandler;
/*    */ import lottery.domains.pool.play.WinResult;
/*    */ 
/*    */ public class TXK3SLHPlayHandler
/*    */   implements ITicketPlayHandler
/*    */ {
/*    */   private String playId;
/*    */   private int[] offsets;
/*    */   
/*    */   public TXK3SLHPlayHandler(String playId, int[] offsets)
/*    */   {
/* 14 */     this.playId = playId;
/* 15 */     this.offsets = offsets;
/*    */   }
/*    */   
/*    */   public String[] getBetNums(String betNums) {
/* 19 */     return betNums.split(",");
/*    */   }
/*    */   
/*    */   public String[] getOpenNums(String openNums)
/*    */   {
/* 24 */     return openNums.split(",");
/*    */   }
/*    */   
/*    */   public WinResult calculateWinNum(int userBetsId, String index, String openNums)
/*    */   {
/* 29 */     WinResult result = new WinResult();
/* 30 */     String[] nums = getBetNums(index);
/* 31 */     String[] openNum = getOpenNums(openNums);
/* 32 */     if ((nums == null) || (openNum == null)) {
/* 33 */       return result;
/*    */     }
/* 35 */     int winNum = 0;
/* 36 */     StringBuilder sb = new StringBuilder();
/* 37 */     for (String nm : openNum) {
/* 38 */       sb.append(nm.trim());
/*    */     }
/*    */     
/* 41 */     for (int i = 0; i < nums.length; i++) {
/* 42 */       if (nums[i].equals(sb.toString())) {
/* 43 */         winNum++;
/*    */       }
/*    */     }
/* 46 */     result.setPlayId(this.playId);
/* 47 */     result.setWinNum(winNum);
/* 48 */     return result;
/*    */   }
/*    */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/pool/play/impl/TXK3SLHPlayHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */