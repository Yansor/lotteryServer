/*     */ package lottery.domains.pool.play.impl;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import lottery.domains.pool.play.ITicketPlayHandler;
/*     */ import lottery.domains.pool.play.WinResult;
/*     */ import lottery.domains.pool.util.TicketPlayUtils;
/*     */ 
/*     */ public class ZXHZPlayHanlder
/*     */   implements ITicketPlayHandler
/*     */ {
/*     */   private int[] offsets;
/*     */   private String playId0;
/*     */   private String playId1;
/*     */   
/*     */   public ZXHZPlayHanlder(String playId0, String playId1, int[] offsets)
/*     */   {
/*  19 */     this.playId0 = playId0;
/*  20 */     this.playId1 = playId1;
/*  21 */     this.offsets = offsets;
/*     */   }
/*     */   
/*     */   public String[] getBetNums(String betNums)
/*     */   {
/*  26 */     return betNums.trim().split(",");
/*     */   }
/*     */   
/*     */   public String[] getOpenNums(String openNums)
/*     */   {
/*  31 */     String[] open = TicketPlayUtils.getOpenNums(openNums, this.offsets);
/*  32 */     Arrays.sort(open);
/*  33 */     return open;
/*     */   }
/*     */   
/*     */   public WinResult calculateWinNum(int userBetsId, String index, String openNums)
/*     */   {
/*  38 */     WinResult result = new WinResult();
/*  39 */     String[] openNum = getOpenNums(openNums);
/*  40 */     if ((this.playId0.equals(this.playId1)) && ((this.playId0.equalsIgnoreCase("1_exzuxhzh")) || (this.playId0.equals("1_exzuxhzq")))) {
/*  41 */       result.setPlayId(this.playId0);
/*  42 */       int winNum = ssczuxhz2x(index, openNum).intValue();
/*  43 */       result.setWinNum(winNum);
/*     */     } else {
/*  45 */       int winNum = ssczuxhz3x(index, openNum).intValue();
/*  46 */       String[] fixedOpenNum = TicketPlayUtils.getFixedNums(openNum);
/*  47 */       if (openNum.length == fixedOpenNum.length) {
/*  48 */         result.setPlayId(this.playId1);
/*  49 */         result.setGroupType(2);
/*     */       } else {
/*  51 */         result.setPlayId(this.playId0);
/*  52 */         result.setGroupType(1);
/*     */       }
/*     */       
/*  55 */       if (1 == winNum) {
/*  56 */         result.setWinNum(1);
/*  57 */       } else if (2 == winNum) {
/*  58 */         result.setWinNum(1);
/*     */       }
/*     */     }
/*  61 */     return result;
/*     */   }
/*     */   
/*     */   private Integer ssczuxhz2x(String betCode, String[] codes) {
/*  65 */     Integer result = Integer.valueOf(0);
/*  66 */     String sum = String.valueOf(sum(codes, true));
/*  67 */     String[] betCodes = betCode.split(",");
/*     */     
/*     */ 
/*  70 */     int dbc = Collections.frequency(Arrays.asList(codes), codes[0]);
/*  71 */     if (dbc == 2) {
/*  72 */       return result;
/*     */     }
/*     */     
/*  75 */     for (String bc : betCodes) {
/*  76 */       if (Integer.valueOf(bc) == Integer.valueOf(sum)) {
/*  77 */         result = Integer.valueOf(1);
/*  78 */         break;
/*     */       }
/*     */     }
/*  81 */     return result;
/*     */   }
/*     */   
/*     */   private Integer ssczuxhz3x(String betCode, String[] codes) {
/*  85 */     Integer result = Integer.valueOf(0);
/*  86 */     String sum = String.valueOf(sum(codes, true));
/*  87 */     String[] betCodes = betCode.split(",");
/*     */     
/*  89 */     int dbc = Collections.frequency(Arrays.asList(codes), codes[0]);
/*  90 */     if (dbc == 3) {
/*  91 */       return result;
/*     */     }
/*  93 */     for (String bc : betCodes) {
/*  94 */       if (Integer.valueOf(bc) == Integer.valueOf(sum)) {
/*  95 */         boolean isone = false;
/*  96 */         for (String c : codes) {
/*  97 */           dbc = Collections.frequency(Arrays.asList(codes), c);
/*  98 */           if (dbc > 1) {
/*  99 */             isone = true;
/* 100 */             break;
/*     */           }
/*     */         }
/*     */         
/*     */ 
/* 105 */         if (isone) {
/* 106 */           result = Integer.valueOf(1);
/* 107 */           break;
/*     */         }
/* 109 */         result = Integer.valueOf(2);
/* 110 */         break;
/*     */       }
/*     */     }
/*     */     
/* 114 */     return result;
/*     */   }
/*     */   
/*     */   private int sum(String[] codes, boolean leopard)
/*     */   {
/* 119 */     if ((leopard) && (Collections.frequency(Arrays.asList(codes), codes[0]) == codes.length)) {
/* 120 */       return -1;
/*     */     }
/* 122 */     int result = 0;
/* 123 */     for (int i = 0; i < codes.length; i++) {
/* 124 */       result += Integer.parseInt(codes[i]);
/*     */     }
/* 126 */     return result;
/*     */   }
/*     */   
/*     */   public static void main(String[] args) {
/* 130 */     ZXHZPlayHanlder test = new ZXHZPlayHanlder("1_sxzuxzsq", "1_sxzuxzlq", ITicketPlayHandler.OFFSETS_QIANSAN);
/* 131 */     WinResult r1 = test.calculateWinNum(11, "1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26", "9,8,1");
/* 132 */     WinResult r2 = test.calculateWinNum(11, "2,3", "1,1,0");
/* 133 */     System.out.println(r1.getPlayId() + "     " + r1.getWinNum());
/* 134 */     System.out.println(r2.getPlayId() + "     " + r2.getWinNum());
/*     */     
/* 136 */     ZXHZPlayHanlder test2 = new ZXHZPlayHanlder("1_exzuxhzq", "1_exzuxhzq", ITicketPlayHandler.OFFSETS_QIANSAN);
/* 137 */     WinResult r3 = test2.calculateWinNum(11, "2,3", "1,2");
/* 138 */     System.out.println(r3.getPlayId() + "     " + r3.getWinNum());
/*     */   }
/*     */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/pool/play/impl/ZXHZPlayHanlder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */