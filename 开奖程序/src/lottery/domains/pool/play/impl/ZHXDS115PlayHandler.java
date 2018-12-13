/*    */ package lottery.domains.pool.play.impl;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import lottery.domains.pool.play.ITicketPlayHandler;
/*    */ import lottery.domains.pool.play.WinResult;
/*    */ import lottery.domains.pool.util.TicketPlayUtils;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ZHXDS115PlayHandler
/*    */   implements ITicketPlayHandler
/*    */ {
/*    */   private String playId;
/*    */   private int[] offsets;
/*    */   
/*    */   public ZHXDS115PlayHandler(String playId, int[] offsets)
/*    */   {
/* 22 */     this.playId = playId;
/* 23 */     this.offsets = offsets;
/*    */   }
/*    */   
/*    */   public String[] getBetNums(String betNums)
/*    */   {
/* 28 */     return betNums.trim().split(";");
/*    */   }
/*    */   
/*    */   public String[] getOpenNums(String openNums)
/*    */   {
/* 33 */     return TicketPlayUtils.getOpenNums(openNums, this.offsets);
/*    */   }
/*    */   
/*    */   public WinResult calculateWinNum(int userBetsId, String index, String openNums)
/*    */   {
/* 38 */     WinResult result = new WinResult();
/* 39 */     String[] nums = getBetNums(index);
/* 40 */     String[] openNum = getOpenNums(openNums);
/* 41 */     if ((nums == null) || (openNum == null) || (openNum.length != this.offsets.length)) {
/* 42 */       return result;
/*    */     }
/* 44 */     StringBuilder openRes = new StringBuilder();
/* 45 */     for (String num : openNum) {
/* 46 */       openRes.append(num).append(" ");
/*    */     }
/* 48 */     int winNum = 0;
/* 49 */     Arrays.sort(nums);
/* 50 */     if (Arrays.binarySearch(nums, openRes.toString().trim()) >= 0) {
/* 51 */       winNum = 1;
/*    */     }
/* 53 */     result.setPlayId(this.playId);
/* 54 */     result.setWinNum(winNum);
/* 55 */     return result;
/*    */   }
/*    */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/pool/play/impl/ZHXDS115PlayHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */