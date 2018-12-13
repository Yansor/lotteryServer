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
/*    */ public class LHPlayHanlder
/*    */   implements ITicketPlayHandler
/*    */ {
/*    */   private String playId;
/*    */   private int[] offsets;
/*    */   private String codeSeparator;
/*    */   private static final String HE = "和";
/*    */   private static final String LONG = "龙";
/*    */   private static final String HU = "虎";
/*    */   
/*    */   public LHPlayHanlder(String playId, int offset1, int offset2, String codeSeparator)
/*    */   {
/* 29 */     this.playId = playId;
/* 30 */     this.offsets = new int[] { offset1, offset2 };
/* 31 */     this.codeSeparator = codeSeparator;
/*    */   }
/*    */   
/*    */   public String[] getBetNums(String betNums)
/*    */   {
/* 36 */     return betNums.trim().split(this.codeSeparator);
/*    */   }
/*    */   
/*    */   public String[] getOpenNums(String openNums)
/*    */   {
/* 41 */     return TicketPlayUtils.getOpenNums(openNums, this.offsets);
/*    */   }
/*    */   
/*    */   public WinResult calculateWinNum(int userBetsId, String index, String openNums)
/*    */   {
/* 46 */     WinResult result = new WinResult();
/* 47 */     String[] nums = getBetNums(index);
/* 48 */     String[] openNum = getOpenNums(openNums);
/* 49 */     if ((nums == null) || (openNums == null)) {
/* 50 */       return result;
/*    */     }
/*    */     
/* 53 */     if (ArrayUtils.hasSame(nums)) {
/* 54 */       return result;
/*    */     }
/*    */     
/* 57 */     for (String num : nums) {
/* 58 */       if (num.length() > 1) {
/* 59 */         return result;
/*    */       }
/*    */     }
/*    */     
/* 63 */     String transferResult = transferOpenNum(openNum);
/*    */     
/* 65 */     if (index.indexOf(transferResult) >= 0) {
/* 66 */       result.setWinNum(1);
/*    */     }
/*    */     
/* 69 */     result.setPlayId(this.playId);
/* 70 */     return result;
/*    */   }
/*    */   
/*    */   private String transferOpenNum(String[] openNum) {
/* 74 */     Integer openNum1 = Integer.valueOf(openNum[0]);
/* 75 */     Integer openNum2 = Integer.valueOf(openNum[1]);
/*    */     
/* 77 */     if (openNum1 == openNum2) {
/* 78 */       return "和";
/*    */     }
/* 80 */     if (openNum1.intValue() > openNum2.intValue()) {
/* 81 */       return "龙";
/*    */     }
/*    */     
/* 84 */     return "虎";
/*    */   }
/*    */   
/*    */   public static void main(String[] args) {
/* 88 */     String betNum = "龙 虎 和";
/* 89 */     String openNum = "07,06,02,03,09,08,10,01,04,07";
/*    */     
/* 91 */     LHPlayHanlder hanlder = new LHPlayHanlder("01vs10", 0, 9, " ");
/* 92 */     WinResult winResult = hanlder.calculateWinNum(1, betNum, openNum);
/* 93 */     System.out.println(winResult.getWinNum());
/*    */   }
/*    */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/pool/play/impl/LHPlayHanlder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */