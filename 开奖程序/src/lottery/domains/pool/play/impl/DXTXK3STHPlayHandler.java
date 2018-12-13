/*    */ package lottery.domains.pool.play.impl;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import lottery.domains.pool.play.ITicketPlayHandler;
/*    */ import lottery.domains.pool.play.WinResult;
/*    */ import lottery.domains.pool.util.TicketPlayUtils;
/*    */ 
/*    */ 
/*    */ public class DXTXK3STHPlayHandler
/*    */   implements ITicketPlayHandler
/*    */ {
/*    */   private String playId;
/*    */   private int[] offsets;
/*    */   private int bthIndexNum;
/*    */   
/*    */   public DXTXK3STHPlayHandler(String playId, int bthIndexNum, int[] offsets)
/*    */   {
/* 18 */     this.playId = playId;
/* 19 */     this.offsets = offsets;
/* 20 */     this.bthIndexNum = bthIndexNum;
/*    */   }
/*    */   
/*    */   public String[] getBetNums(String betNums) {
/* 24 */     return betNums.split(",");
/*    */   }
/*    */   
/*    */   public String[] getOpenNums(String openNums)
/*    */   {
/* 29 */     String[] openNum = openNums.split(",");
/* 30 */     return TicketPlayUtils.getFixedNums(openNum);
/*    */   }
/*    */   
/*    */   public WinResult calculateWinNum(int userBetsId, String index, String openNums)
/*    */   {
/* 35 */     WinResult result = new WinResult();
/* 36 */     String[] nums = getBetNums(index);
/* 37 */     String[] openNum = getOpenNums(openNums);
/* 38 */     if ((nums == null) || (openNum == null) || (openNum.length != this.bthIndexNum)) {
/* 39 */       return result;
/*    */     }
/* 41 */     int winNum = 0;
/* 42 */     Arrays.sort(openNum);
/* 43 */     StringBuilder sb = new StringBuilder();
/* 44 */     for (String nm : openNum) {
/* 45 */       sb.append(nm.trim());
/*    */     }
/*    */     
/* 48 */     for (int i = 0; i < nums.length; i++) {
/* 49 */       if (nums[i].contains(sb.toString())) {
/* 50 */         winNum++;
/*    */       }
/*    */     }
/* 53 */     result.setPlayId(this.playId);
/* 54 */     result.setWinNum(winNum);
/* 55 */     return result;
/*    */   }
/*    */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/pool/play/impl/DXTXK3STHPlayHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */