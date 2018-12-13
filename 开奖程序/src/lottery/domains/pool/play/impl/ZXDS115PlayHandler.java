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
/*    */ public class ZXDS115PlayHandler
/*    */   implements ITicketPlayHandler
/*    */ {
/*    */   private String playId;
/*    */   private int[] offsets;
/*    */   
/*    */   public ZXDS115PlayHandler(String playId, int[] offsets)
/*    */   {
/* 20 */     this.playId = playId;
/* 21 */     this.offsets = offsets;
/*    */   }
/*    */   
/*    */   public String[] getBetNums(String betNums) {
/* 25 */     String[] nums = betNums.trim().split(";");
/* 26 */     String[] res = new String[nums.length];
/* 27 */     for (int i = 0; i < nums.length; i++) {
/* 28 */       if ((nums[i] != null) && (nums[i].trim().length() > 0)) {
/* 29 */         String[] bnums = nums[i].split(" ");
/* 30 */         Arrays.sort(bnums);
/* 31 */         StringBuilder sb = new StringBuilder();
/* 32 */         for (String bnum : bnums) {
/* 33 */           sb.append(bnum).append(" ");
/*    */         }
/* 35 */         res[i] = sb.toString().trim();
/*    */       }
/*    */     }
/* 38 */     return res;
/*    */   }
/*    */   
/*    */   public String[] getOpenNums(String openNums)
/*    */   {
/* 43 */     String[] open = TicketPlayUtils.getOpenNums(openNums, this.offsets);
/* 44 */     Arrays.sort(open);
/* 45 */     return open;
/*    */   }
/*    */   
/*    */   public WinResult calculateWinNum(int userBetsId, String index, String openNums)
/*    */   {
/* 50 */     WinResult result = new WinResult();
/* 51 */     String[] nums = getBetNums(index);
/* 52 */     String[] openNum = getOpenNums(openNums);
/* 53 */     if ((nums == null) || (openNums == null) || (openNum == null)) {
/* 54 */       return result;
/*    */     }
/* 56 */     StringBuilder sb = new StringBuilder();
/* 57 */     for (String nm : openNum) {
/* 58 */       sb.append(nm.trim()).append(" ");
/*    */     }
/* 60 */     int winNum = 0;
/* 61 */     for (int i = 0; i < nums.length; i++) {
/* 62 */       if (nums[i].equals(sb.toString().trim()))
/*    */       {
/* 64 */         winNum++;
/*    */       }
/*    */     }
/* 67 */     result.setPlayId(this.playId);
/* 68 */     result.setWinNum(winNum);
/* 69 */     return result;
/*    */   }
/*    */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/pool/play/impl/ZXDS115PlayHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */