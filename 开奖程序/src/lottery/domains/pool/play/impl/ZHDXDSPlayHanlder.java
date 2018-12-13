/*     */ package lottery.domains.pool.play.impl;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.util.HashSet;
/*     */ import lottery.domains.pool.play.ITicketPlayHandler;
/*     */ import lottery.domains.pool.play.WinResult;
/*     */ import lottery.domains.pool.util.TicketPlayUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ZHDXDSPlayHanlder
/*     */   implements ITicketPlayHandler
/*     */ {
/*     */   private String playId;
/*     */   private int[] offsets;
/*     */   private int daGreaterEqualsThan;
/*     */   private int xiaoLessEqualsThan;
/*     */   private int maxHits;
/*     */   
/*     */   public ZHDXDSPlayHanlder(String playId, int[] offsets, int daGreaterEqualsThan, int xiaoLessEqualsThan, int maxHits)
/*     */   {
/*  30 */     this.playId = playId;
/*  31 */     this.offsets = offsets;
/*  32 */     this.daGreaterEqualsThan = daGreaterEqualsThan;
/*  33 */     this.xiaoLessEqualsThan = xiaoLessEqualsThan;
/*  34 */     this.maxHits = maxHits;
/*     */   }
/*     */   
/*     */   public String[] getBetNums(String betNums)
/*     */   {
/*  39 */     return betNums.trim().split(",");
/*     */   }
/*     */   
/*     */   public String[] getOpenNums(String openNums)
/*     */   {
/*  44 */     return TicketPlayUtils.getOpenNums(openNums, this.offsets);
/*     */   }
/*     */   
/*     */   public WinResult calculateWinNum(int userBetsId, String betCodes, String openCodes)
/*     */   {
/*  49 */     WinResult result = new WinResult();
/*  50 */     String[] betCodesArr = getBetNums(betCodes);
/*  51 */     String[] openCodesArr = getOpenNums(openCodes);
/*  52 */     if ((betCodesArr == null) || (openCodesArr == null) || (openCodesArr.length != this.offsets.length)) {
/*  53 */       return result;
/*     */     }
/*     */     
/*     */ 
/*  57 */     int openCodesSum = 0;
/*  58 */     String[] arrayOfString1 = openCodesArr;int i = arrayOfString1.length; for (int str1 = 0; str1 < i; str1++) {String openCode = arrayOfString1[str1];
/*  59 */       openCodesSum += Integer.valueOf(openCode).intValue();
/*     */     }
/*     */     
/*  62 */     Object hits = new HashSet();
/*  63 */     String[] arrayOfString2 = betCodesArr;int str1 = arrayOfString2.length; for (int openCode = 0; openCode < str1; openCode++) { String betCode = arrayOfString2[openCode];
/*  64 */       if ((this.maxHits > 0) && (((HashSet)hits).size() >= this.maxHits)) {
/*     */         break;
/*     */       }
/*  67 */       switch (betCode)
/*     */       {
/*     */       case "总和大": 
/*  70 */         if (openCodesSum >= this.daGreaterEqualsThan) {
/*  71 */           ((HashSet)hits).add(betCode);
/*     */         }
/*     */         
/*     */         break;
/*     */       case "总和小": 
/*  76 */         if (openCodesSum <= this.xiaoLessEqualsThan) {
/*  77 */           ((HashSet)hits).add(betCode);
/*     */         }
/*     */         
/*     */         break;
/*     */       case "总和单": 
/*  82 */         if (openCodesSum % 2 == 1) {
/*  83 */           ((HashSet)hits).add(betCode);
/*     */         }
/*     */         
/*     */         break;
/*     */       case "总和双": 
/*  88 */         if (openCodesSum % 2 == 0) {
/*  89 */           ((HashSet)hits).add(betCode);
/*     */         }
/*     */         
/*     */         break;
/*     */       }
/*     */       
/*     */     }
/*     */     
/*  97 */     result.setPlayId(this.playId);
/*  98 */     result.setWinNum(((HashSet)hits).size());
/*  99 */     return result;
/*     */   }
/*     */   
/*     */   public static void main(String[] args) {
/* 103 */     String betCodes = "总和大,总和小,总和单,总和双";
/* 104 */     String openCodes = "8,8,8,3,4";
/*     */     
/* 106 */     ZHDXDSPlayHanlder hanlder = new ZHDXDSPlayHanlder("wxdxds", ITicketPlayHandler.OFFSETS_WUXIN, 23, 22, 2);
/* 107 */     WinResult winResult = hanlder.calculateWinNum(1, betCodes, openCodes);
/* 108 */     System.out.println(winResult + "---");
/* 109 */     System.out.println(winResult.getWinNum());
/*     */   }
/*     */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/pool/play/impl/ZHDXDSPlayHanlder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */