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
/*    */ public class ZXDTPlayHandler
/*    */   implements ITicketPlayHandler
/*    */ {
/*    */   private String playId;
/*    */   private int[] offsets;
/*    */   
/*    */   public ZXDTPlayHandler(String playId, int[] offsets)
/*    */   {
/* 20 */     this.playId = playId;
/* 21 */     this.offsets = offsets;
/*    */   }
/*    */   
/*    */   public String[] getBetNums(String betNums)
/*    */   {
/* 26 */     String[] nums = betNums.trim().split(",");
/* 27 */     String[] res = new String[nums.length];
/* 28 */     for (int i = 0; i < nums.length; i++) {
/* 29 */       if ((nums[i] != null) && (nums[i].trim().length() > 0)) {
/* 30 */         char[] bnums = nums[i].toCharArray();
/* 31 */         Arrays.sort(bnums);
/* 32 */         StringBuilder sb = new StringBuilder();
/* 33 */         for (char bnum : bnums) {
/* 34 */           sb.append(bnum).append(" ");
/*    */         }
/* 36 */         res[i] = sb.toString().trim();
/*    */       }
/*    */     }
/* 39 */     return res;
/*    */   }
/*    */   
/*    */   public String[] getOpenNums(String openNums)
/*    */   {
/* 44 */     return TicketPlayUtils.getSortedOpenNums(openNums, this.offsets);
/*    */   }
/*    */   
/*    */ 
/*    */   public WinResult calculateWinNum(int userBetsId, String index, String openNums)
/*    */   {
/* 50 */     WinResult result = new WinResult();
/* 51 */     String[] openNum = getOpenNums(openNums);
/* 52 */     String[] nums = getBetNums(index);
/* 53 */     if ((nums == null) || (nums.length != 2) || (openNum == null) || (openNum.length != this.offsets.length)) {
/* 54 */       return result;
/*    */     }
/* 56 */     String[] dNums = nums[0].trim().split(" ");
/* 57 */     String[] tNums = nums[1].trim().split(" ");
/* 58 */     int dhit = 0;
/* 59 */     String[] arrayOfString1 = dNums;int i = arrayOfString1.length; for (int str1 = 0; str1 < i; str1++) {
    String dnum = arrayOfString1[str1];
/* 60 */       if (Arrays.binarySearch(openNum, dnum) >= 0) {
/* 61 */         dhit++;
/*    */       }
/*    */     }
/* 64 */     int thit = 0;
/* 65 */     String[] arrayOfString2 = tNums;int str1 = arrayOfString2.length; for (int dnum = 0; dnum < str1; dnum++) { String tnum = arrayOfString2[dnum];
/* 66 */       if (Arrays.binarySearch(openNum, tnum) >= 0) {
/* 67 */         thit++;
/*    */       }
/*    */     }
/* 70 */     result.setPlayId(this.playId);
/* 71 */     result.setWinNum(dhit * thit);
/* 72 */     return result;
/*    */   }
/*    */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/pool/play/impl/ZXDTPlayHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */