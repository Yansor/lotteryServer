/*    */ package lottery.domains.pool.play.impl;
/*    */ 
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
/*    */ 
/*    */ public class ZX6PlayHanlder
/*    */   implements ITicketPlayHandler
/*    */ {
/*    */   private String playId;
/*    */   private int[] offsets;
/*    */   
/*    */   public ZX6PlayHanlder(String playId, int[] offsets)
/*    */   {
/* 22 */     this.playId = playId;
/* 23 */     this.offsets = offsets;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public String[] getBetNums(String betNums)
/*    */   {
/* 33 */     return betNums.trim().split(",");
/*    */   }
/*    */   
/*    */   public String[] getOpenNums(String openNums)
/*    */   {
/* 38 */     return TicketPlayUtils.getOpenNums(openNums, this.offsets);
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
/*    */   public WinResult calculateWinNum(int userBetsId, String index, String openNums)
/*    */   {
/* 59 */     WinResult result = new WinResult();
/* 60 */     String nums = getIndexOpenNum(getOpenNums(openNums));
/* 61 */     if ((nums == null) || (nums.length() != 2)) {
/* 62 */       return result;
/*    */     }
/* 64 */     int winNum = 1;
/* 65 */     for (int i = 0; i < nums.length(); i++) {
/* 66 */       if (!index.contains(String.valueOf(nums.charAt(i)))) {
/* 67 */         winNum = 0;
/* 68 */         break;
/*    */       }
/*    */     }
/* 71 */     result.setPlayId(this.playId);
/* 72 */     result.setWinNum(winNum);
/* 73 */     return result;
/*    */   }
/*    */   
/*    */   private String getIndexOpenNum(String[] openNums) {
/* 77 */     Map<String, Integer> nums = new HashMap();
/* 78 */     for (String num : openNums) {
/* 79 */       if (nums.get(num) == null) {
/* 80 */         nums.put(num, Integer.valueOf(1));
/*    */       } else {
/* 82 */         nums.put(num, Integer.valueOf(((Integer)nums.get(num)).intValue() + 1));
/*    */       }
/*    */     }
/* 85 */     Object it = nums.keySet().iterator();
/* 86 */     String c = null;
/* 87 */     StringBuilder resNum = new StringBuilder();
/* 88 */     while (((Iterator)it).hasNext()) {
/* 89 */       c = (String)((Iterator)it).next();
/* 90 */       if (((Integer)nums.get(c)).intValue() == 2) {
/* 91 */         resNum.append(c);
/*    */       }
/*    */     }
/* 94 */     if (resNum.length() != 2) {
/* 95 */       return null;
/*    */     }
/* 97 */     return resNum.toString();
/*    */   }
/*    */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/pool/play/impl/ZX6PlayHanlder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */