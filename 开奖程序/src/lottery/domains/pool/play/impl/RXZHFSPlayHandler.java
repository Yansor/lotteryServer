/*    */ package lottery.domains.pool.play.impl;
/*    */ 
/*    */ import lottery.domains.pool.play.ITicketPlayHandler;
/*    */ import lottery.domains.pool.play.WinResult;
/*    */ import lottery.domains.pool.util.TicketPlayUtils;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RXZHFSPlayHandler
/*    */   implements ITicketPlayHandler
/*    */ {
/*    */   private String playId;
/*    */   private int rxNum;
/*    */   
/*    */   public RXZHFSPlayHandler(String playId, int rxNum)
/*    */   {
/* 18 */     this.playId = playId;
/* 19 */     this.rxNum = rxNum;
/*    */   }
/*    */   
/*    */   public String[] getBetNums(String betNums)
/*    */   {
/* 24 */     return betNums.trim().split(",");
/*    */   }
/*    */   
/*    */   public String[] getOpenNums(String openNums)
/*    */   {
/* 29 */     return TicketPlayUtils.getOpenNums(openNums, OFFSETS_WUXIN);
/*    */   }
/*    */   
/*    */   public WinResult calculateWinNum(int userBetsId, String index, String openNums)
/*    */   {
/* 34 */     WinResult result = new WinResult();
/* 35 */     String[] openNum = getOpenNums(openNums);
/* 36 */     String[] nums = index.trim().split(",");
/* 37 */     int winNum = 0;
/* 38 */     switch (this.rxNum) {
/*    */     case 1: 
/*    */       break;
/*    */     case 2: 
/* 42 */       for (int i = 0; i < nums.length; i++) {
/* 43 */         for (int j = i + 1; j < nums.length; j++) {
/* 44 */           if ((nums[i].contains(openNum[i])) && (nums[j].contains(openNum[j]))) {
/* 45 */             winNum++;
/*    */           }
/*    */         }
/*    */       }
/* 49 */       break;
/*    */     case 3: 
/* 51 */       for (int i = 0; i < nums.length; i++) {
/* 52 */         for (int j = i + 1; j < nums.length; j++) {
/* 53 */           for (int k = j + 1; k < nums.length; k++) {
/* 54 */             if ((nums[i].contains(openNum[i])) && (nums[j].contains(openNum[j])) && 
/* 55 */               (nums[k].contains(openNum[k]))) {
/* 56 */               winNum++;
/*    */             }
/*    */           }
/*    */         }
/*    */       }
/* 61 */       break;
/*    */     case 4: 
/* 63 */       for (int i = 0; i < nums.length; i++) {
/* 64 */         for (int j = i + 1; j < nums.length; j++) {
/* 65 */           for (int k = j + 1; k < nums.length; k++) {
/* 66 */             for (int l = k + 1; l < nums.length; l++) {
/* 67 */               if ((nums[i].contains(openNum[i])) && (nums[j].contains(openNum[j])) && 
/* 68 */                 (nums[k].contains(openNum[k])) && (nums[l].contains(openNum[l]))) {
/* 69 */                 winNum++;
/*    */               }
/*    */             }
/*    */           }
/*    */         }
/*    */       }
/* 75 */       break;
/*    */     }
/*    */     
/*    */     
/* 79 */     result.setPlayId(this.playId);
/* 80 */     result.setWinNum(winNum);
/* 81 */     return result;
/*    */   }
/*    */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/pool/play/impl/RXZHFSPlayHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */