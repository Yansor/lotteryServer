/*    */ package lottery.domains.pool.play.impl;
/*    */ 
/*    */ import java.io.PrintStream;
/*    */ import java.util.Arrays;
/*    */ import javautils.array.ArrayUtils;
/*    */ import lottery.domains.pool.play.ITicketPlayHandler;
/*    */ import lottery.domains.pool.play.WinResult;
/*    */ import lottery.domains.pool.util.TicketPlayUtils;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DXDSPK10PlayHanlder
/*    */   implements ITicketPlayHandler
/*    */ {
/*    */   private String playId;
/*    */   private int[] offsets;
/* 18 */   String[] OFFSETS_DA = { "06", "07", "08", "09", "10" };
/* 19 */   String[] OFFSETS_XIAO = { "01", "02", "03", "04", "05" };
/* 20 */   String[] OFFSETS_DAN = { "01", "03", "05", "07", "09" };
/* 21 */   String[] OFFSETS_SHUANG = { "02", "04", "06", "08", "10" };
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public DXDSPK10PlayHanlder(String playId, int offset)
/*    */   {
/* 29 */     this.playId = playId;
/* 30 */     this.offsets = new int[] { offset };
/*    */   }
/*    */   
/*    */   public String[] getBetNums(String betNums)
/*    */   {
/* 35 */     return betNums.trim().split(" ");
/*    */   }
/*    */   
/*    */   public String[] getOpenNums(String openNums)
/*    */   {
/* 40 */     return TicketPlayUtils.getOpenNums(openNums, this.offsets);
/*    */   }
/*    */   
/*    */   public WinResult calculateWinNum(int userBetsId, String index, String openNums)
/*    */   {
/* 45 */     WinResult result = new WinResult();
/* 46 */     String[] nums = getBetNums(index);
/* 47 */     String[] openNum = getOpenNums(openNums);
/* 48 */     if ((nums == null) || (openNums == null)) {
/* 49 */       return result;
/*    */     }
/*    */     
/* 52 */     if (ArrayUtils.hasSame(nums)) {
/* 53 */       return result;
/*    */     }
/*    */     
/* 56 */     String[] arrayOfString1 = nums;
int i = arrayOfString1.length; for (int str1 = 0; str1 < i; str1++) {
    String num = arrayOfString1[str1];
/* 57 */       if (num.length() > 1) {
/* 58 */         return result;
/*    */       }
/*    */     }
/*    */     
/* 62 */     int winNum = 0;
/* 63 */     String[] arrayOfString2 = nums;int str1 = arrayOfString2.length; for (int num = 0; num < str1; num++) { String num1 = arrayOfString2[num];
/* 64 */       String[] offs = matchingBetNum(num1);
/*    */       
/* 66 */       if (Arrays.binarySearch(offs, openNum[0]) >= 0) {
/* 67 */         winNum++;
/*    */       }
/*    */     }
/* 70 */     result.setPlayId(this.playId);
/* 71 */     result.setWinNum(winNum);
/* 72 */     return result;
/*    */   }
/*    */   
/*    */   private String[] matchingBetNum(String betNum) {
/* 76 */     switch (betNum) {
/*    */     case "大": 
/* 78 */       return this.OFFSETS_DA;
/*    */     case "小": 
/* 80 */       return this.OFFSETS_XIAO;
/*    */     case "单": 
/* 82 */       return this.OFFSETS_DAN;
/*    */     case "双": 
/* 84 */       return this.OFFSETS_SHUANG;
/*    */     }
/* 86 */     return null;
/*    */   }
/*    */   
/*    */   public static void main(String[] args)
/*    */   {
/* 91 */     String betNum = "双";
/* 92 */     String openNum = "09,06,05,08,10,02,04,03,01,07";
/*    */     
/* 94 */     DXDSPK10PlayHanlder hanlder = new DXDSPK10PlayHanlder("bjpk10_dw5dxds", 4);
/* 95 */     WinResult winResult = hanlder.calculateWinNum(1, betNum, openNum);
/* 96 */     System.out.println(winResult.getWinNum());
/*    */   }
/*    */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/pool/play/impl/DXDSPK10PlayHanlder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */