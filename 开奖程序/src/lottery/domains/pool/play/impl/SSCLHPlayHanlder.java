/*    */ package lottery.domains.pool.play.impl;
/*    */ 
/*    */ import java.io.PrintStream;
/*    */ import javautils.array.ArrayUtils;
/*    */ import lottery.domains.pool.play.ITicketPlayHandler;
/*    */ import lottery.domains.pool.play.WinResult;
/*    */ import lottery.domains.pool.util.TicketPlayUtils;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SSCLHPlayHanlder
/*    */   implements ITicketPlayHandler
/*    */ {
/*    */   private String playId;
/*    */   private int[] offsets;
/*    */   private static final String HE = "和";
/*    */   private static final String LONG = "龙";
/*    */   private static final String HU = "虎";
/*    */   
/*    */   public SSCLHPlayHanlder(String playId, int offset1, int offset2)
/*    */   {
/* 28 */     this.playId = playId;
/* 29 */     this.offsets = new int[] { offset1, offset2 };
/*    */   }
/*    */   
/*    */   public String[] getBetNums(String betNums)
/*    */   {
/* 34 */     return betNums.trim().split(",");
/*    */   }
/*    */   
/*    */   public String[] getOpenNums(String openNums)
/*    */   {
/* 39 */     return TicketPlayUtils.getOpenNums(openNums, this.offsets);
/*    */   }
/*    */   
/*    */   public WinResult calculateWinNum(int userBetsId, String index, String openNums)
/*    */   {
/* 44 */     WinResult result = new WinResult();
/* 45 */     String[] nums = getBetNums(index);
/* 46 */     String[] openNum = getOpenNums(openNums);
/* 47 */     if ((nums == null) || (openNums == null)) {
/* 48 */       return result;
/*    */     }
/*    */     
/* 51 */     if (ArrayUtils.hasSame(nums)) {
/* 52 */       return result;
/*    */     }
/*    */     
/* 55 */     for (String num : nums) {
/* 56 */       if (num.length() > 1) {
/* 57 */         return result;
/*    */       }
/*    */     }
/*    */     
/* 61 */     String transferResult = transferOpenNum(openNum);
/*    */     
/* 63 */     if (index.indexOf(transferResult) >= 0) {
/* 64 */       result.setWinNum(1);
/* 65 */       if ("和".equals(transferResult)) {
/* 66 */         result.setPrizeIndex(1);
/*    */       }
/*    */       else {
/* 69 */         result.setPrizeIndex(0);
/*    */       }
/*    */     }
/*    */     
/* 73 */     result.setPlayId(this.playId);
/* 74 */     return result;
/*    */   }
/*    */   
/*    */   private String transferOpenNum(String[] openNum) {
/* 78 */     Integer openNum1 = Integer.valueOf(openNum[0]);
/* 79 */     Integer openNum2 = Integer.valueOf(openNum[1]);
/*    */     
/* 81 */     if (openNum1 == openNum2) {
/* 82 */       return "和";
/*    */     }
/* 84 */     if (openNum1.intValue() > openNum2.intValue()) {
/* 85 */       return "龙";
/*    */     }
/*    */     
/* 88 */     return "虎";
/*    */   }
/*    */   
/*    */   public static void main(String[] args) {
/* 92 */     String betNum = "龙,虎,和";
/* 93 */     String openNum = "1,2,3,4,0";
/*    */     
/* 95 */     SSCLHPlayHanlder hanlder = new SSCLHPlayHanlder("longhuhewq", 0, 4);
/* 96 */     WinResult winResult = hanlder.calculateWinNum(1, betNum, openNum);
/* 97 */     System.out.println(winResult.getWinNum());
/* 98 */     System.out.println(winResult.getPrizeIndex());
/*    */   }
/*    */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/pool/play/impl/SSCLHPlayHanlder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */