/*    */ package lottery.domains.pool.play.impl;
/*    */ 
/*    */ import lottery.domains.pool.play.ITicketPlayHandler;
/*    */ import lottery.domains.pool.play.WinResult;
/*    */ import lottery.domains.pool.util.TicketPlayUtils;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SSCRXZXFSPlayHandler
/*    */   implements ITicketPlayHandler
/*    */ {
/*    */   private String playId;
/*    */   private int rxNum;
/*    */   
/*    */   public SSCRXZXFSPlayHandler(String playId, int rxNum)
/*    */   {
/* 18 */     this.playId = playId;
/* 19 */     this.rxNum = rxNum;
/*    */   }
/*    */   
/*    */   public String[] getBetNums(String bet)
/*    */   {
/* 24 */     String[] res = new String[2];
/* 25 */     String[] xz = bet.substring(1, 10).split(",");
/* 26 */     StringBuffer placeIndex = new StringBuffer();
/* 27 */     for (int i = 0; i < xz.length; i++) {
/* 28 */       if (xz[i].equals("√")) {
/* 29 */         placeIndex.append(String.valueOf(i)).append(",");
/*    */       }
/*    */     }
/* 32 */     String placeVal = placeIndex.toString().trim();
/* 33 */     if (!placeVal.equals("")) {
/* 34 */       placeVal = placeVal.substring(0, placeVal.length() - 1);
/*    */     }
/*    */     
/* 37 */     String betValue = bet.substring(11, bet.length()).trim();
/* 38 */     if ((placeVal.equals("")) || (betValue.equals(""))) {
/* 39 */       return null;
/*    */     }
/* 41 */     res[0] = placeVal;
/* 42 */     res[1] = betValue;
/* 43 */     return res;
/*    */   }
/*    */   
/*    */   public String[] getOpenNums(String openNums)
/*    */   {
/* 48 */     return TicketPlayUtils.getOpenNums(openNums, OFFSETS_WUXIN);
/*    */   }
/*    */   
/*    */   public WinResult calculateWinNum(int userBetsId, String index, String openNums)
/*    */   {
/* 53 */     WinResult result = new WinResult();
/* 54 */     String[] data = getBetNums(index);
/* 55 */     if ((data == null) || (data.length != 2)) {
/* 56 */       return result;
/*    */     }
/* 58 */     String nums = data[1].trim();
/* 59 */     String[] offsets = data[0].trim().split(",");
/*    */     
/* 61 */     int[] offset = new int[offsets.length];
/* 62 */     for (int i = 0; i < offsets.length; i++) {
/* 63 */       offset[i] = Integer.parseInt(offsets[i]);
/*    */     }
/* 65 */     String[] openNum = getOpenNums(openNums);
/* 66 */     int winNum = 0;
/* 67 */     switch (this.rxNum) {
/*    */     case 1: 
/*    */       break;
/*    */     case 2: 
/* 71 */       for (int i = 0; i < offset.length; i++) {
/* 72 */         for (int j = i + 1; j < offset.length; j++) {
/* 73 */           if ((nums.contains(openNum[offset[i]])) && (nums.contains(openNum[offset[j]])) && 
/* 74 */             (!openNum[offset[i]].equals(openNum[offset[j]]))) {
/* 75 */             winNum++;
/*    */           }
/*    */         }
/*    */       }
/* 79 */       break;
/*    */     case 3: 
/*    */       break;
/*    */     case 4: 
/*    */       break;
/*    */     }
/*    */     
/*    */     
/* 87 */     result.setPlayId(this.playId);
/* 88 */     result.setWinNum(winNum);
/* 89 */     return result;
/*    */   }
/*    */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/pool/play/impl/SSCRXZXFSPlayHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */