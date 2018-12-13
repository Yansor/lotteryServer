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
/*    */ public class DWDPK10PlayHandler
/*    */   implements ITicketPlayHandler
/*    */ {
/*    */   private String playId;
/*    */   private int[] offsets;
/*    */   
/*    */   public DWDPK10PlayHandler(String playId, int[] offsets)
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
/*    */     
/* 41 */     int winNum = 0;
/* 42 */     for (int i = 0; i < this.offsets.length; i++) {
/* 43 */       String dwNum = nums[i];
/*    */       
/* 45 */       if (!"-".equals(dwNum))
/*    */       {
/*    */ 
/*    */ 
/* 49 */         if (dwNum.indexOf(openNum[i]) >= 0)
/* 50 */           winNum++;
/*    */       }
/*    */     }
/* 53 */     result.setPlayId(this.playId);
/* 54 */     result.setWinNum(winNum);
/* 55 */     return result;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static void main(String[] args)
/*    */   {
/* 66 */     String betNum = "01 02 03 04 05 06 07 08 09 10,01 02 03 04 05 06 07 08 09 10,01 02 03 04 05 06 07 08 09 10,01 02 03 04 05 06 07 08 09 10,-";
/* 67 */     String openCode = "06,04,09,07,02,01,03,10,05,08";
/*    */     
/* 69 */     DWDPK10PlayHandler handler = new DWDPK10PlayHandler("bjpk10_hwdingweidan", ITicketPlayHandler.OFFSETS_HOUWU);
/* 70 */     WinResult winResult = handler.calculateWinNum(1, betNum, openCode);
/* 71 */     System.out.println(winResult.getWinNum());
/*    */   }
/*    */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/pool/play/impl/DWDPK10PlayHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */