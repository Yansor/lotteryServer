/*    */ package lottery.domains.pool.play.impl;
/*    */ 
/*    */ import lottery.domains.pool.play.ITicketPlayHandler;
/*    */ import lottery.domains.pool.play.WinResult;
/*    */ import lottery.domains.pool.util.TicketPlayUtils;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class QW11X5DDSPlayHandler
/*    */   implements ITicketPlayHandler
/*    */ {
/*    */   private String playId;
/*    */   private int[] offsets;
/*    */   
/*    */   public QW11X5DDSPlayHandler(String playId, int[] offsets)
/*    */   {
/* 17 */     this.playId = playId;
/* 18 */     this.offsets = offsets;
/*    */   }
/*    */   
/*    */   public String[] getBetNums(String betNums) {
/* 22 */     return betNums.split("\\|");
/*    */   }
/*    */   
/*    */   public String[] getOpenNums(String openNums)
/*    */   {
/* 27 */     return TicketPlayUtils.getOpenNums(openNums, this.offsets);
/*    */   }
/*    */   
/*    */   public WinResult calculateWinNum(int userBetsId, String index, String openNums)
/*    */   {
/* 32 */     WinResult result = new WinResult();
/* 33 */     String[] openCodes = getOpenNums(openNums);
/* 34 */     String[] betNums = getBetNums(index);
/* 35 */     if ((betNums == null) || (openCodes == null) || (openCodes.length != this.offsets.length)) {
/* 36 */       return result;
/*    */     }
/* 38 */     int dan = 0;
/* 39 */     int shuang = 0;
/* 40 */     for (int i = 0; i < openCodes.length; i++) {
/* 41 */       Integer num = Integer.valueOf(Integer.parseInt(openCodes[i]));
/* 42 */       if (num.intValue() % 2 == 0) {
/* 43 */         shuang++;
/*    */       } else {
/* 45 */         dan++;
/*    */       }
/*    */     }
/* 48 */     int winNum = 0;
/* 49 */     StringBuffer prizeStr = new StringBuffer();
/* 50 */     prizeStr.append(dan).append("单").append(shuang).append("双");
/* 51 */     for (int i = 0; i < betNums.length; i++) {
/* 52 */       if (betNums[i].equals(prizeStr.toString().trim())) {
/* 53 */         winNum++;
/*    */       }
/*    */     }
/* 56 */     result.setPlayId(this.playId);
/* 57 */     result.setWinCode(prizeStr.toString().trim());
/* 58 */     result.setWinNum(winNum);
/* 59 */     return result;
/*    */   }
/*    */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/pool/play/impl/QW11X5DDSPlayHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */