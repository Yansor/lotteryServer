/*    */ package lottery.domains.pool.play.impl;
/*    */ 
/*    */ import lottery.domains.pool.play.ITicketPlayHandler;
/*    */ import lottery.domains.pool.play.WinResult;
/*    */ import lottery.domains.pool.util.TicketPlayUtils;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ZHXDSPK10PlayHandler
/*    */   implements ITicketPlayHandler
/*    */ {
/*    */   private String playId;
/*    */   private int[] offsets;
/*    */   
/*    */   public ZHXDSPK10PlayHandler(String playId, int[] offsets)
/*    */   {
/* 20 */     this.playId = playId;
/* 21 */     this.offsets = offsets;
/*    */   }
/*    */   
/*    */   public String[] getBetNums(String betNums) {
/* 25 */     return betNums.trim().split(";");
/*    */   }
/*    */   
/*    */   public String[] getOpenNums(String openNums)
/*    */   {
/* 30 */     return TicketPlayUtils.getOpenNums(openNums, this.offsets);
/*    */   }
/*    */   
/*    */   public WinResult calculateWinNum(int userBetsId, String index, String openNums)
/*    */   {
/* 35 */     WinResult result = new WinResult();
/* 36 */     String[] nums = getBetNums(index);
/* 37 */     String[] openNum = getOpenNums(openNums);
/* 38 */     if ((nums == null) || (openNums == null) || (openNum == null)) {
/* 39 */       return result;
/*    */     }
/* 41 */     StringBuilder sb = new StringBuilder();
/* 42 */     for (String nm : openNum) {
/* 43 */       sb.append(nm.trim()).append(" ");
/*    */     }
/* 45 */     int winNum = 0;
/* 46 */     for (int i = 0; i < nums.length; i++) {
/* 47 */       if (nums[i].equals(sb.toString().trim())) {
/* 48 */         winNum++;
/*    */       }
/*    */     }
/* 51 */     result.setPlayId(this.playId);
/* 52 */     result.setWinNum(winNum);
/* 53 */     return result;
/*    */   }
/*    */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/pool/play/impl/ZHXDSPK10PlayHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */