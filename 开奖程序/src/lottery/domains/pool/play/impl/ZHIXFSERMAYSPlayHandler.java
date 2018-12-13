/*    */ package lottery.domains.pool.play.impl;
/*    */ 
/*    */ import lottery.domains.pool.play.ITicketPlayHandler;
/*    */ import lottery.domains.pool.play.WinResult;
/*    */ import lottery.domains.pool.util.TicketPlayUtils;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ZHIXFSERMAYSPlayHandler
/*    */   implements ITicketPlayHandler
/*    */ {
/*    */   private String playId;
/*    */   private int[] offsets;
/*    */   
/*    */   public ZHIXFSERMAYSPlayHandler(String playId, int[] offsets)
/*    */   {
/* 17 */     this.playId = playId;
/* 18 */     this.offsets = offsets;
/*    */   }
/*    */   
/*    */   public String[] getBetNums(String betNums)
/*    */   {
/* 23 */     String[] nums = betNums.trim().split(",");
/* 24 */     String[] res = new String[this.offsets.length];
/* 25 */     int idx = 0;
/* 26 */     for (int i = 0; i < nums.length; i++) {
/* 27 */       if (!"-".equals(nums[i].trim())) {
/* 28 */         res[(idx++)] = nums[i];
/*    */       }
/*    */     }
/* 31 */     return res;
/*    */   }
/*    */   
/*    */   public String[] getOpenNums(String openNums)
/*    */   {
/* 36 */     return TicketPlayUtils.getOpenNums(openNums, this.offsets);
/*    */   }
/*    */   
/*    */   public WinResult calculateWinNum(int userBetsId, String index, String openNums)
/*    */   {
/* 41 */     WinResult result = new WinResult();
/* 42 */     String[] openNum = getOpenNums(openNums);
/* 43 */     String[] nums = getBetNums(index);
/* 44 */     if ((nums == null) || (openNum == null) || (openNum.length != this.offsets.length)) {
/* 45 */       return result;
/*    */     }
/* 47 */     int winNum = 1;
/* 48 */     for (int i = 0; i < this.offsets.length; i++) {
/* 49 */       if (nums[i].indexOf(openNum[i].trim()) < 0) {
/* 50 */         winNum = 0;
/* 51 */         break;
/*    */       }
/*    */     }
/* 54 */     result.setPlayId(this.playId);
/* 55 */     result.setWinNum(winNum);
/* 56 */     return result;
/*    */   }
/*    */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/pool/play/impl/ZHIXFSERMAYSPlayHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */