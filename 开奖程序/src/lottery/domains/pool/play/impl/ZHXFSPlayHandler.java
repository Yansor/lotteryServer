/*    */ package lottery.domains.pool.play.impl;
/*    */ 
/*    */ import lottery.domains.pool.play.ITicketPlayHandler;
/*    */ import lottery.domains.pool.play.WinResult;
/*    */ import lottery.domains.pool.util.TicketPlayUtils;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ZHXFSPlayHandler
/*    */   implements ITicketPlayHandler
/*    */ {
/*    */   private String playId;
/*    */   private int[] offsets;
/*    */   
/*    */   public ZHXFSPlayHandler(String playId, int[] offsets)
/*    */   {
/* 18 */     this.playId = playId;
/* 19 */     this.offsets = offsets;
/*    */   }
/*    */   
/*    */   public String[] getBetNums(String betNums)
/*    */   {
/* 24 */     String[] nums = betNums.trim().split(",");
/* 25 */     String[] res = new String[this.offsets.length];
/* 26 */     int idx = 0;
/* 27 */     for (int i = 0; i < nums.length; i++) {
/* 28 */       if (!"-".equals(nums[i].trim())) {
/* 29 */         res[(idx++)] = nums[i].replaceAll(" ", "").trim();
/*    */       }
/*    */     }
/* 32 */     return res;
/*    */   }
/*    */   
/*    */   public String[] getOpenNums(String openNums)
/*    */   {
/* 37 */     return TicketPlayUtils.getOpenNums(openNums, this.offsets);
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
/*    */   public WinResult calculateWinNum(int userBetsId, String index, String openNums)
/*    */   {
/* 59 */     WinResult result = new WinResult();
/* 60 */     String[] openNum = getOpenNums(openNums);
/* 61 */     String[] nums = getBetNums(index);
/* 62 */     if ((nums == null) || (openNum == null) || (openNum.length != this.offsets.length)) {
/* 63 */       return result;
/*    */     }
/* 65 */     int winNum = 1;
/* 66 */     for (int i = 0; i < this.offsets.length; i++) {
/* 67 */       if (nums[i].indexOf(openNum[i]) < 0) {
/* 68 */         winNum = 0;
/* 69 */         break;
/*    */       }
/*    */     }
/* 72 */     result.setPlayId(this.playId);
/* 73 */     result.setWinNum(winNum);
/* 74 */     return result;
/*    */   }
/*    */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/pool/play/impl/ZHXFSPlayHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */