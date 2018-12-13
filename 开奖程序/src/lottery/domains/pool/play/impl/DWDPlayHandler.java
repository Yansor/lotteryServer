/*    */ package lottery.domains.pool.play.impl;
/*    */ 
/*    */ import java.io.PrintStream;
/*    */ import lottery.domains.pool.play.ITicketPlayHandler;
/*    */ import lottery.domains.pool.play.WinResult;
/*    */ import lottery.domains.pool.util.TicketPlayUtils;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DWDPlayHandler
/*    */   implements ITicketPlayHandler
/*    */ {
/*    */   private String playId;
/*    */   private int[] offsets;
/*    */   
/*    */   public DWDPlayHandler(String playId, int[] offsets)
/*    */   {
/* 19 */     this.playId = playId;
/* 20 */     this.offsets = offsets;
/*    */   }
/*    */   
/*    */   public String[] getBetNums(String betNums)
/*    */   {
/* 25 */     return betNums.trim().split(",");
/*    */   }
/*    */   
/*    */   public String[] getOpenNums(String openNums)
/*    */   {
/* 30 */     return TicketPlayUtils.getOpenNums(openNums, this.offsets);
/*    */   }
/*    */   
/*    */   public WinResult calculateWinNum(int userBetsId, String index, String openNums) {
/* 34 */     WinResult result = new WinResult();
/* 35 */     String[] openNum = getOpenNums(openNums);
/* 36 */     String[] nums = getBetNums(index);
/* 37 */     if ((nums == null) || (openNum == null) || (openNum.length != this.offsets.length)) {
/* 38 */       return result;
/*    */     }
/* 40 */     int winNum = 0;
/* 41 */     for (int i = 0; i < this.offsets.length; i++) {
/* 42 */       String ssnum = nums[this.offsets[i]];
/* 43 */       if (ssnum.indexOf(openNum[i]) >= 0) {
/* 44 */         winNum++;
/*    */       }
/*    */     }
/* 47 */     result.setPlayId(this.playId);
/* 48 */     result.setWinNum(winNum);
/* 49 */     return result;
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
/*    */   public static void main(String[] args)
/*    */   {
/* 86 */     String betNum = "01 02 03 04 05 06 07 08 09 10,01 02 03 04 05 06 07 08 09 10,01 02 03 04 05 06 07 08 09 10,01 02 03 04 05 06 07 08 09 10,01 02 03 04 05 06 07 08 09 10";
/* 87 */     String openCode = "06,04,09,07,02,01,03,10,05,08";
/*    */     
/* 89 */     DWDPlayHandler handler = new DWDPlayHandler("bjpk10_hwdingweidan", ITicketPlayHandler.OFFSETS_HOUWU);
/* 90 */     WinResult winResult = handler.calculateWinNum(1, betNum, openCode);
/* 91 */     System.out.println(winResult.getWinNum());
/*    */   }
/*    */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/pool/play/impl/DWDPlayHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */