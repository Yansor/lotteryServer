/*     */ package lottery.domains.pool.play.impl;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import lottery.domains.pool.play.ITicketPlayHandler;
/*     */ import lottery.domains.pool.play.MultipleResult;
/*     */ import lottery.domains.pool.play.WinResult;
/*     */ import lottery.domains.pool.util.TicketPlayUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HZDXDSPlayHanlder
/*     */   implements ITicketPlayHandler
/*     */ {
/*     */   private String playId;
/*     */   private int[] offsets;
/*  20 */   String split = " ";
/*     */   
/*     */ 
/*     */ 
/*     */   int[] offsets_da;
/*     */   
/*     */ 
/*     */   int[] offsets_xiao;
/*     */   
/*     */ 
/*     */ 
/*     */   public HZDXDSPlayHanlder(String playId, int[] offsets_da, int[] offsets_xiao, int[] offsets)
/*     */   {
/*  33 */     this.playId = playId;
/*  34 */     this.offsets_da = offsets_da;
/*  35 */     this.offsets_xiao = offsets_xiao;
/*  36 */     this.offsets = offsets;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public HZDXDSPlayHanlder(String playId, int[] offsets_da, int[] offsets_xiao, int[] offsets, String split)
/*     */   {
/*  49 */     this.playId = playId;
/*  50 */     this.offsets_da = offsets_da;
/*  51 */     this.offsets_xiao = offsets_xiao;
/*  52 */     this.offsets = offsets;
/*  53 */     this.split = split;
/*     */   }
/*     */   
/*     */   public String[] getBetNums(String betNums)
/*     */   {
/*  58 */     return betNums.trim().split(" ");
/*     */   }
/*     */   
/*     */   public String[] getOpenNums(String openNums)
/*     */   {
/*  63 */     return TicketPlayUtils.getOpenNums(openNums, this.offsets);
/*     */   }
/*     */   
/*     */   public WinResult calculateWinNum(int userBetsId, String codes, String openNums)
/*     */   {
/*  68 */     WinResult result = new WinResult();
/*  69 */     String[] nums = getBetNums(codes);
/*  70 */     if ((nums == null) || (openNums == null)) {
/*  71 */       return result;
/*     */     }
/*  73 */     int sum = TicketPlayUtils.getOpenNumSum(openNums, this.offsets);
/*     */     
/*  75 */     MultipleResult da = new MultipleResult("大", 0);
/*  76 */     MultipleResult xiao = new MultipleResult("小", 1);
/*  77 */     MultipleResult dan = new MultipleResult("单", 2);
/*  78 */     MultipleResult shuang = new MultipleResult("双", 3);
/*     */     
/*  80 */     for (String num : nums) {
/*  81 */       if ("大".equals(num)) {
/*  82 */         if (Arrays.binarySearch(this.offsets_da, sum) >= 0) {
/*  83 */           da.increseNum();
/*     */         }
/*     */       }
/*  86 */       else if ("小".equals(num)) {
/*  87 */         if (Arrays.binarySearch(this.offsets_xiao, sum) >= 0) {
/*  88 */           xiao.increseNum();
/*     */         }
/*     */       }
/*  91 */       else if ("单".equals(num)) {
/*  92 */         if (sum % 2 == 1) {
/*  93 */           dan.increseNum();
/*     */         }
/*     */       }
/*  96 */       else if (("双".equals(num)) && 
/*  97 */         (sum % 2 == 0)) {
/*  98 */         shuang.increseNum();
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 103 */     Object results = new ArrayList();
/* 104 */     if (da.getNums() > 0) ((List)results).add(da);
/* 105 */     if (xiao.getNums() > 0) ((List)results).add(xiao);
/* 106 */     if (dan.getNums() > 0) ((List)results).add(dan);
/* 107 */     if (shuang.getNums() > 0) { ((List)results).add(shuang);
/*     */     }
/* 109 */     result.setPlayId(this.playId);
/* 110 */     result.setMultipleResults((List)results);
/*     */     
/* 112 */     return result;
/*     */   }
/*     */   
/*     */   public static void main(String[] args)
/*     */   {
/* 117 */     String betNum = "大";
/* 118 */     String openNum = "10,08,03,01,02,06,07,05,04,09";
/*     */     
/* 120 */     int[] offsets_da = { 12, 13, 14, 15, 16, 17, 18, 19 };
/* 121 */     int[] offsets_xiao = { 3, 4, 5, 6, 7, 8, 9, 10, 11 };
/*     */     
/* 123 */     HZDXDSPlayHanlder hanlder = new HZDXDSPlayHanlder("bjpk10_pk10_dxdsgyhz", offsets_da, offsets_xiao, ITicketPlayHandler.OFFSETS_QIANER);
/* 124 */     WinResult winResult = hanlder.calculateWinNum(1, betNum, openNum);
/* 125 */     for (MultipleResult multipleResult : winResult.getMultipleResults()) {
/* 126 */       System.out.println(multipleResult.getCode() + "-" + multipleResult.getOddsIndex() + "-" + multipleResult.getNums());
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/pool/play/impl/HZDXDSPlayHanlder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */