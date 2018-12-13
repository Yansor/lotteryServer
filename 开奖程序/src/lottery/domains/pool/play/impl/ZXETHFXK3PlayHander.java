/*    */ package lottery.domains.pool.play.impl;
/*    */ 
/*    */ import lottery.domains.pool.play.ITicketPlayHandler;
/*    */ import lottery.domains.pool.play.WinResult;
/*    */ import lottery.domains.pool.util.TicketPlayUtils;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ZXETHFXK3PlayHander
/*    */   implements ITicketPlayHandler
/*    */ {
/*    */   private int[] offsets;
/*    */   private String playId;
/*    */   
/*    */   public ZXETHFXK3PlayHander(String playId, int[] offsets)
/*    */   {
/* 18 */     this.playId = playId;
/* 19 */     this.offsets = offsets;
/*    */   }
/*    */   
/*    */   public String[] getBetNums(String betNums)
/*    */   {
/* 24 */     return betNums.split(",");
/*    */   }
/*    */   
/*    */   public String[] getOpenNums(String openNums)
/*    */   {
/* 29 */     return TicketPlayUtils.getOpenNums(openNums, this.offsets);
/*    */   }
/*    */   
/*    */   public WinResult calculateWinNum(int userBetsId, String index, String openNums)
/*    */   {
/* 34 */     WinResult result = new WinResult();
/* 35 */     String[] nums = getBetNums(index);
/* 36 */     String[] openNum = getOpenNums(openNums);
/* 37 */     if ((nums == null) || (openNum == null)) {
/* 38 */       return result;
/*    */     }
/* 40 */     int winNum = 0;
/* 41 */     String openDual = TicketPlayUtils.getOpenDualNum(openNum);
/* 42 */     if ((openDual != null) && (openDual.length() > 0)) {
/* 43 */       for (int i = 0; i < nums.length; i++) {
/* 44 */         if (openDual.equals(nums[i])) {
/* 45 */           winNum = 1;
/*    */         }
/*    */       }
/*    */     }
/* 49 */     result.setPlayId(this.playId);
/* 50 */     result.setWinNum(winNum);
/* 51 */     return result;
/*    */   }
/*    */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/pool/play/impl/ZXETHFXK3PlayHander.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */