/*    */ package lottery.domains.pool.play.impl;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import java.util.HashMap;
/*    */ import java.util.Iterator;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ import lottery.domains.pool.play.ITicketPlayHandler;
/*    */ import lottery.domains.pool.play.WinResult;
/*    */ import lottery.domains.pool.util.TicketPlayUtils;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ZXZ3PlayHanlder
/*    */   implements ITicketPlayHandler
/*    */ {
/*    */   private String playId;
/*    */   private int[] offsets;
/*    */   
/*    */   public ZXZ3PlayHanlder(String playId, int[] offsets)
/*    */   {
/* 22 */     this.playId = playId;
/* 23 */     this.offsets = offsets;
/*    */   }
/*    */   
/*    */   public String[] getBetNums(String betNums)
/*    */   {
/* 28 */     String[] nums = betNums.trim().split(",");
/* 29 */     return TicketPlayUtils.getFixedNums(nums);
/*    */   }
/*    */   
/*    */   public String[] getOpenNums(String openNums)
/*    */   {
/* 34 */     return TicketPlayUtils.getOpenNums(openNums, this.offsets);
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
/*    */   public WinResult calculateWinNum(int userBetsId, String index, String openNums)
/*    */   {
/* 57 */     WinResult result = new WinResult();
/* 58 */     String[] betNum = getBetNums(index);
/* 59 */     String[] nums = getIndexOpenNum(openNums);
/* 60 */     if ((nums == null) || (nums.length != 2)) {
/* 61 */       return result;
/*    */     }
/* 63 */     int winNum = 1;
/* 64 */     Arrays.sort(betNum);
/* 65 */     for (String num : nums) {
/* 66 */       if (Arrays.binarySearch(betNum, num) < 0) {
/* 67 */         winNum = 0;
/* 68 */         break;
/*    */       }
/*    */     }
/* 71 */     result.setPlayId(this.playId);
/* 72 */     result.setWinNum(winNum);
/* 73 */     return result;
/*    */   }
/*    */   
/*    */   private String[] getIndexOpenNum(String openNums) {
/* 77 */     String[] openNum = TicketPlayUtils.getOpenNums(openNums, this.offsets);
/* 78 */     Map<String, Integer> nums = new HashMap();
/* 79 */     for (String num : openNum) {
/* 80 */       if (nums.get(num) == null) {
/* 81 */         nums.put(num, Integer.valueOf(1));
/*    */       } else {
/* 83 */         nums.put(num, Integer.valueOf(((Integer)nums.get(num)).intValue() + 1));
/*    */       }
/*    */     }
/* 86 */     if (nums.size() != 2) {
/* 87 */       return null;
/*    */     }
/* 89 */     String[] res = new String[2];
/* 90 */     Object it = nums.keySet().iterator();
/* 91 */     int idx = 0;
/* 92 */     while (((Iterator)it).hasNext()) {
/* 93 */       res[(idx++)] = ((String)((Iterator)it).next());
/*    */     }
/* 95 */     return res;
/*    */   }
/*    */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/pool/play/impl/ZXZ3PlayHanlder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */