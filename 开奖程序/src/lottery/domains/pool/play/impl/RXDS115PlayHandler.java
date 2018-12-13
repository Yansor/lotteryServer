/*    */ package lottery.domains.pool.play.impl;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import java.util.Collection;
/*    */ import lottery.domains.pool.play.ITicketPlayHandler;
/*    */ import lottery.domains.pool.play.WinResult;
/*    */ import lottery.domains.pool.util.TicketPlayUtils;
/*    */ import org.apache.commons.collections.CollectionUtils;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RXDS115PlayHandler
/*    */   implements ITicketPlayHandler
/*    */ {
/*    */   private String playId;
/*    */   private int[] offsets;
/*    */   private int dsNum;
/*    */   
/*    */   public RXDS115PlayHandler(String playId, int dsNum, int[] offsets)
/*    */   {
/* 24 */     this.playId = playId;
/* 25 */     this.dsNum = dsNum;
/* 26 */     this.offsets = offsets;
/*    */   }
/*    */   
/*    */   public String[] getBetNums(String betNums)
/*    */   {
/* 31 */     String[] nums = betNums.trim().split(";");
/* 32 */     String[] res = new String[nums.length];
/* 33 */     for (int i = 0; i < nums.length; i++) {
/* 34 */       if ((nums[i] != null) && (nums[i].trim().length() > 0)) {
/* 35 */         String[] bnums = nums[i].split(" ");
/* 36 */         Arrays.sort(bnums);
/* 37 */         StringBuilder sb = new StringBuilder();
/* 38 */         for (String bnum : bnums) {
/* 39 */           sb.append(bnum).append(" ");
/*    */         }
/* 41 */         res[i] = sb.toString().trim();
/*    */       }
/*    */     }
/* 44 */     return res;
/*    */   }
/*    */   
/*    */   public String[] getOpenNums(String openNums)
/*    */   {
/* 49 */     String[] open = TicketPlayUtils.getOpenNums(openNums, this.offsets);
/* 50 */     Arrays.sort(open);
/* 51 */     return open;
/*    */   }
/*    */   
/*    */   public WinResult calculateWinNum(int userBetsId, String index, String openNums)
/*    */   {
/* 56 */     WinResult result = new WinResult();
/* 57 */     String[] nums = getBetNums(index);
/* 58 */     String[] openNum = getOpenNums(openNums);
/* 59 */     if ((nums == null) || (openNums == null) || (openNum == null)) {
/* 60 */       return result;
/*    */     }
/* 62 */     int winNum = 0;
/* 63 */     if (this.dsNum > 5) {
/* 64 */       for (int i = 0; i < nums.length; i++) {
/* 65 */         Collection<?> inter = CollectionUtils.intersection(Arrays.asList(nums[i].split(" ")), 
/* 66 */           Arrays.asList(openNum));
/* 67 */         if (inter.size() == 5) {
/* 68 */           winNum++;
/*    */         }
/*    */       }
/*    */     } else {
/* 72 */       for (int i = 0; i < nums.length; i++) {
/* 73 */         Collection<?> inter = CollectionUtils.intersection(Arrays.asList(nums[i].split(" ")), 
/* 74 */           Arrays.asList(openNum));
/* 75 */         if (inter.size() == this.dsNum) {
/* 76 */           winNum++;
/*    */         }
/*    */       }
/*    */     }
/* 80 */     result.setPlayId(this.playId);
/* 81 */     result.setWinNum(winNum);
/* 82 */     return result;
/*    */   }
/*    */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/pool/play/impl/RXDS115PlayHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */