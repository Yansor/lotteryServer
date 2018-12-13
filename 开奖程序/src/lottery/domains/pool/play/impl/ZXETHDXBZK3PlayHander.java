/*    */ package lottery.domains.pool.play.impl;
/*    */ 
/*    */ import lottery.domains.pool.play.ITicketPlayHandler;
/*    */ import lottery.domains.pool.play.WinResult;
/*    */ import lottery.domains.pool.util.TicketPlayUtils;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ZXETHDXBZK3PlayHander
/*    */   implements ITicketPlayHandler
/*    */ {
/*    */   private int[] offsets;
/*    */   private String playId;
/*    */   
/*    */   public ZXETHDXBZK3PlayHander(String playId, int[] offsets)
/*    */   {
/* 18 */     this.playId = playId;
/* 19 */     this.offsets = offsets;
/*    */   }
/*    */   
/*    */   public String[] getBetNums(String betNums)
/*    */   {
/* 24 */     return betNums.split(",");
/*    */   }
/*    */   
/*    */   public String[] getOpenNums(String openNums)
/*    */   {
/* 29 */     return TicketPlayUtils.getOpenNums(openNums, this.offsets);
/*    */   }
/*    */   
/*    */   public WinResult calculateWinNum(int userBetsId, String index, String openNums)
/*    */   {
/* 34 */     WinResult result = new WinResult();
/* 35 */     String[] nums = getBetNums(index);
/* 36 */     String[] openNum = getOpenNums(openNums);
/* 37 */     if ((nums == null) || (openNum == null) || (nums.length != 2)) {
/* 38 */       return result;
/*    */     }
/* 40 */     int winNum = 0;
/* 41 */     int odd = 0;
/* 42 */     int dual = 0;
/* 43 */     char[] oddBet = nums[1].toCharArray();
/* 44 */     for (int i = 0; i < oddBet.length; i++) {
/* 45 */       if (openNums.contains(String.valueOf(oddBet[i]).trim())) {
/* 46 */         odd++;
/*    */       }
/*    */     }
/* 49 */     for (int i = 0; i < openNum.length; i++) {
/* 50 */       if (openNum[i].trim().indexOf(nums[0].trim()) > -1) {
/* 51 */         dual++;
/*    */       }
/*    */     }
/* 54 */     if ((dual == 2) && (odd == 1)) {
/* 55 */       winNum = 1;
/*    */     }
/*    */     
/* 58 */     result.setPlayId(this.playId);
/* 59 */     result.setWinNum(winNum);
/* 60 */     return result;
/*    */   }
/*    */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/pool/play/impl/ZXETHDXBZK3PlayHander.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */