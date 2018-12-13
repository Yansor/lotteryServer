/*    */ package lottery.domains.pool.play.impl;
/*    */ 
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
/*    */ import lottery.domains.pool.play.ITicketPlayHandler;
/*    */ import lottery.domains.pool.play.WinResult;
/*    */ import lottery.domains.pool.util.TicketPlayUtils;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ZHXFSKillPlayHandler
/*    */   implements ITicketPlayHandler
/*    */ {
/*    */   private String playId;
/*    */   private int[] offsets;
/*    */   
/*    */   public ZHXFSKillPlayHandler(String playId, int[] offsets)
/*    */   {
/* 21 */     this.playId = playId;
/* 22 */     this.offsets = offsets;
/*    */   }
/*    */   
/*    */   public String[] getBetNums(String betNums)
/*    */   {
/* 27 */     String[] nums = betNums.trim().split(",");
/* 28 */     String[] res = new String[this.offsets.length];
/* 29 */     int idx = 0;
/* 30 */     for (int i = 0; i < nums.length; i++) {
/* 31 */       if (!"-".equals(nums[i].trim())) {
/* 32 */         res[(idx++)] = nums[i].replaceAll(" ", "").trim();
/*    */       }
/*    */     }
/* 35 */     return res;
/*    */   }
/*    */   
/*    */   public String[] getOpenNums(String openNums)
/*    */   {
/* 40 */     return TicketPlayUtils.getOpenNums(openNums, this.offsets);
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
/*    */   public WinResult calculateWinNum(int userBetsId, String index, String openNums)
/*    */   {
/* 62 */     WinResult result = new WinResult();
/* 63 */     String[] openNum = getOpenNums(openNums);
/* 64 */     String[] nums = getBetNums(index);
/* 65 */     if ((nums == null) || (openNum == null) || (openNum.length != this.offsets.length)) {
/* 66 */       return result;
/*    */     }
/* 68 */     int winNum = 1;
/* 69 */     for (int i = 0; i < this.offsets.length; i++) {
/* 70 */       if (nums[i].indexOf(openNum[i]) < 0) {
/* 71 */         winNum = 0;
/* 72 */         break;
/*    */       }
/*    */     }
/*    */     
/*    */ 
/* 77 */     Set<String> kill = new HashSet();
/* 78 */     for (int i = 0; i < this.offsets.length; i++) {
/* 79 */       kill.add(openNum[i]);
/*    */     }
/* 81 */     if (kill.size() == 1) {
/* 82 */       winNum = 0;
/*    */     }
/*    */     
/* 85 */     result.setPlayId(this.playId);
/* 86 */     result.setWinNum(winNum);
/* 87 */     return result;
/*    */   }
/*    */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/pool/play/impl/ZHXFSKillPlayHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */