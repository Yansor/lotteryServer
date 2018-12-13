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
/*    */ public class ZHHZPlayHandler
/*    */   implements ITicketPlayHandler
/*    */ {
/*    */   private String playId;
/*    */   private int[] offsets;
/*    */   
/*    */   public ZHHZPlayHandler(String playId, int[] offsets)
/*    */   {
/* 19 */     this.playId = playId;
/* 20 */     this.offsets = offsets;
/*    */   }
/*    */   
/*    */   public String[] getBetNums(String betNums) {
/* 24 */     return betNums.trim().split(",");
/*    */   }
/*    */   
/*    */   public String[] getOpenNums(String openNums)
/*    */   {
/* 29 */     return TicketPlayUtils.getOpenNums(openNums, this.offsets);
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
/*    */ 
/*    */ 
/*    */   public WinResult calculateWinNum(int userBetsId, String index, String openNums)
/*    */   {
/* 56 */     WinResult result = new WinResult();
/* 57 */     String[] nums = getBetNums(index);
/* 58 */     if ((nums == null) || (openNums == null)) {
/* 59 */       return result;
/*    */     }
/* 61 */     int winNum = 0;
/* 62 */     int sum = TicketPlayUtils.getOpenNumSum(openNums, this.offsets);
/* 63 */     for (int i = 0; i < nums.length; i++) {
/* 64 */       if (Integer.parseInt(nums[i]) == sum)
/*    */       {
/* 66 */         result.setWinCode(nums[i]);
/* 67 */         winNum = 1;
/* 68 */         break;
/*    */       }
/*    */     }
/* 71 */     result.setPlayId(this.playId);
/* 72 */     result.setWinNum(winNum);
/* 73 */     return result;
/*    */   }
/*    */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/pool/play/impl/ZHHZPlayHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */