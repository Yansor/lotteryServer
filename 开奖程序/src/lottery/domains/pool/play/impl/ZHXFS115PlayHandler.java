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
/*    */ public class ZHXFS115PlayHandler
/*    */   implements ITicketPlayHandler
/*    */ {
/*    */   private String playId;
/*    */   private int[] offsets;
/*    */   
/*    */   public ZHXFS115PlayHandler(String playId, int[] offsets)
/*    */   {
/* 20 */     this.playId = playId;
/* 21 */     this.offsets = offsets;
/*    */   }
/*    */   
/*    */   public String[] getBetNums(String betNums)
/*    */   {
/* 26 */     String[] nums = betNums.trim().split(",");
/* 27 */     String[] res = new String[this.offsets.length];
/* 28 */     int idx = 0;
/* 29 */     for (int i = 0; i < nums.length; i++) {
/* 30 */       if (!"-".equals(nums[i].trim())) {
/* 31 */         res[(idx++)] = nums[i].trim();
/*    */       }
/*    */     }
/* 34 */     return res;
/*    */   }
/*    */   
/*    */   public String[] getOpenNums(String openNums)
/*    */   {
/* 39 */     return TicketPlayUtils.getOpenNums(openNums, this.offsets);
/*    */   }
/*    */   
/*    */   public WinResult calculateWinNum(int userBetsId, String index, String openNums)
/*    */   {
/* 44 */     WinResult result = new WinResult();
/* 45 */     String[] openNum = getOpenNums(openNums);
/* 46 */     String[] nums = getBetNums(index);
/* 47 */     if ((nums == null) || (openNum == null) || (openNum.length != this.offsets.length)) {
/* 48 */       return result;
/*    */     }
/* 50 */     int winNum = 1;
/* 51 */     for (int i = 0; i < this.offsets.length; i++) {
/* 52 */       if (nums[i].indexOf(openNum[this.offsets[i]]) < 0) {
/* 53 */         winNum = 0;
/* 54 */         break;
/*    */       }
/*    */     }
/* 57 */     result.setPlayId(this.playId);
/* 58 */     result.setWinNum(winNum);
/* 59 */     return result;
/*    */   }
/*    */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/pool/play/impl/ZHXFS115PlayHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */