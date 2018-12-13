/*    */ package lottery.domains.pool.play.impl;
/*    */ 
/*    */ import java.io.PrintStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Arrays;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import lottery.domains.pool.play.ITicketPlayHandler;
/*    */ import lottery.domains.pool.play.MultipleResult;
/*    */ import lottery.domains.pool.play.WinResult;
/*    */ import lottery.domains.pool.util.TicketPlayUtils;
/*    */ 
/*    */ public class HZMultiplePrizePlayHandler implements ITicketPlayHandler
/*    */ {
/*    */   private String playId;
/*    */   private int[] offsets;
/*    */   private int[] codes;
/*    */   
/*    */   public HZMultiplePrizePlayHandler(String playId, int[] offsets, int codeFrom, int codeTo)
/*    */   {
/* 21 */     this.playId = playId;
/* 22 */     this.offsets = offsets;
/*    */     
/* 24 */     int index = 0;
/* 25 */     this.codes = new int[codeTo - codeFrom + 1];
/* 26 */     for (int i = codeFrom; i <= codeTo; i++) {
/* 27 */       this.codes[(index++)] = i;
/*    */     }
/*    */   }
/*    */   
/*    */   public HZMultiplePrizePlayHandler(String playId, int[] offsets, int[] codes) {
/* 32 */     this.playId = playId;
/* 33 */     this.offsets = offsets;
/* 34 */     this.codes = codes;
/* 35 */     Arrays.sort(this.codes);
/*    */   }
/*    */   
/*    */   public String[] getBetNums(String betNums)
/*    */   {
/* 40 */     return betNums.trim().split(",");
/*    */   }
/*    */   
/*    */   public String[] getOpenNums(String openNums)
/*    */   {
/* 45 */     return TicketPlayUtils.getOpenNums(openNums, this.offsets);
/*    */   }
/*    */   
/*    */   public WinResult calculateWinNum(int userBetsId, String betCodes, String openNums)
/*    */   {
/* 50 */     WinResult result = new WinResult();
/* 51 */     String[] nums = getBetNums(betCodes);
/* 52 */     if ((nums == null) || (openNums == null)) {
/* 53 */       return result;
/*    */     }
/*    */     
/* 56 */     int winNum = 0;
/*    */     
/* 58 */     int sum = TicketPlayUtils.getOpenNumSum(openNums, this.offsets);
/*    */     
/* 60 */     Map<String, MultipleResult> codeResults = new HashMap();
/*    */     
/* 62 */     java.util.HashSet<String> betCodeSet = javautils.list.ListUtil.transToHashSet(nums);
/*    */     
/* 64 */     for (String betCode : betCodeSet) {
/* 65 */       int code = Integer.parseInt(betCode);
/* 66 */       if (code == sum) {
/* 67 */         winNum = 1;
///*    */         MultipleResult codeResult;
/*    */         MultipleResult codeResult;
/* 70 */         if (codeResults.containsKey(betCode)) {
/* 71 */           codeResult = (MultipleResult)codeResults.get(betCode);
/*    */         }
/*    */         else {
/* 74 */           int codeIndex = Arrays.binarySearch(this.codes, code);
/* 75 */           codeResult = new MultipleResult(betCode, codeIndex);
/* 76 */           codeResults.put(betCode, codeResult);
/*    */         }
/* 78 */         codeResult.increseNum();
/*    */       }
/*    */     }
/* 81 */     result.setPlayId(this.playId);
/* 82 */     result.setWinNum(winNum);
/* 83 */     result.setMultipleResults(new ArrayList(codeResults.values()));
/* 84 */     return result;
/*    */   }
/*    */   
/*    */ 
/*    */   public static void main(String[] args)
/*    */   {
/* 90 */     String betNum = "6,7,8,9,10,11,12,13,14,15,16,17";
/* 91 */     String openCode = "02,03,01,05,09,07,08,10,04,06";
/*    */     
/* 93 */     HZMultiplePrizePlayHandler hd = new HZMultiplePrizePlayHandler("6_pk10_hzqshz", ITicketPlayHandler.OFFSETS_QIANSAN, 6, 27);
/* 94 */     WinResult winResult = hd.calculateWinNum(1, betNum, openCode);
/* 95 */     System.out.println(winResult.getWinNum());
/* 96 */     for (MultipleResult multipleResult : winResult.getMultipleResults()) {
/* 97 */       System.out.println(multipleResult.getCode() + "-" + multipleResult.getOddsIndex() + "-" + multipleResult.getNums());
/*    */     }
/*    */   }
/*    */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/pool/play/impl/HZMultiplePrizePlayHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */