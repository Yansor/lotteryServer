/*     */ package lottery.domains.pool.play.impl;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import javautils.StringUtil;
/*     */ import lottery.domains.pool.play.ITicketPlayHandler;
/*     */ import lottery.domains.pool.play.WinResult;
/*     */ import lottery.domains.pool.util.TicketPlayUtils;
/*     */ 
/*     */ public class RXTD115PlayHandler
/*     */   implements ITicketPlayHandler
/*     */ {
/*     */   private String playId;
/*     */   private int winCount;
/*     */   private int betCount;
/*     */   private int[] offsets;
/*     */   
/*     */   public RXTD115PlayHandler(String playId, int winCount, int betCount, int[] offsets)
/*     */   {
/*  23 */     this.playId = playId;
/*  24 */     this.winCount = winCount;
/*  25 */     this.offsets = offsets;
/*  26 */     this.betCount = betCount;
/*     */   }
/*     */   
/*     */   public String[] getBetNums(String betNums) {
/*  30 */     return betNums.trim().split(",");
/*     */   }
/*     */   
/*     */   public String[] getOpenNums(String openNums)
/*     */   {
/*  35 */     return TicketPlayUtils.getOpenNums(openNums, this.offsets);
/*     */   }
/*     */   
/*     */   public WinResult calculateWinNum(int userBetsId, String index, String openNums)
/*     */   {
/*  40 */     WinResult result = new WinResult();
/*  41 */     String[] nums = getBetNums(index);
/*  42 */     String[] openNum = getOpenNums(openNums);
/*  43 */     if ((nums == null) || (openNum == null) || (openNum.length != this.offsets.length)) {
/*  44 */       return result;
/*     */     }
/*  46 */     result.setPlayId(this.playId);
/*  47 */     List<Integer> wins = zuxuantd(index.trim(), openNums, this.winCount, this.betCount);
/*  48 */     result.setWinNum(wins.size());
/*  49 */     return result;
/*     */   }
/*     */   
/*     */   public static void main(String[] args) {
/*  53 */     RXTD115PlayHandler test = new RXTD115PlayHandler("2_rxtd3", 3, 3, ITicketPlayHandler.OFFSETS_WUXIN);
/*  54 */     long s = System.currentTimeMillis();
/*     */     
/*  56 */     WinResult result = test.calculateWinNum(11, "02,07 08 09 10 11", "11,02,08,07,05");
/*  57 */     System.out.println(result.getWinNum());
/*     */     
/*  59 */     long e = System.currentTimeMillis();
/*  60 */     System.out.println(e - s);
/*     */   }
/*     */   
/*     */   private List<Integer> zuxuantd(String betCode, String openCode, int count, int length) {
/*  64 */     List<Integer> result = new ArrayList();
/*  65 */     String[] betCodes = betCode.split(",");
/*  66 */     String[] dCodes = betCodes[0].split(" ");
/*  67 */     String[] tCodes = betCodes[1].split(" ");
/*     */     
/*  69 */     int check = 0;
/*     */     
/*     */ 
/*  72 */     for (int i = 0; i < dCodes.length; i++) {
/*  73 */       if ((StringUtil.isNotNull(dCodes[i].trim())) && (openCode.contains(dCodes[i].trim()))) {
/*  74 */         check++;
/*     */       }
/*     */     }
/*  77 */     if ((check != dCodes.length) && (dCodes.length < 6))
/*  78 */       return result;
/*  79 */     if ((dCodes.length > 5) && (check < 5)) {
/*  80 */       return result;
/*     */     }
/*     */     
/*  83 */     int codeLength = length - dCodes.length;
/*  84 */     Set<String[]> set = doSet11x5(tCodes, codeLength);
/*     */     
/*     */ 
/*  87 */     List<String> clist = new ArrayList();
/*  88 */     for (String[] str : set) {
/*  89 */       String code = "";
/*  90 */       for (int i = 0; i < str.length; i++) {
/*  91 */         code = code + str[i] + " ";
/*     */       }
/*  93 */       code = code + betCodes[0];
/*  94 */       clist.add(code);
/*     */     }
/*     */     
/*  97 */     check = 0;
/*  98 */     for (String str : clist) {
/*  99 */       String[] c = str.split(" ");
/* 100 */       for (int i = 0; i < c.length; i++) {
/* 101 */         if ((StringUtil.isNotNull(c[i].trim())) && (openCode.contains(c[i].trim()))) {
/* 102 */           check++;
/*     */         }
/*     */       }
/* 105 */       if (check >= count) {
/* 106 */         result.add(Integer.valueOf(1));
/*     */       }
/* 108 */       check = 0;
/*     */     }
/* 110 */     return result;
/*     */   }
/*     */   
/*     */   private Set<String[]> doSet11x5(String[] a, int m) {
/* 114 */     Set<String[]> result = new HashSet();
/* 115 */     int n = a.length;
/* 116 */     if (m >= n) {
/* 117 */       result.add(a);
/* 118 */       return result;
/*     */     }
/*     */     
/* 121 */     int[] bs = new int[n];
/* 122 */     for (int i = 0; i < n; i++) {
/* 123 */       bs[i] = 0;
/*     */     }
/*     */     
/* 126 */     for (int i = 0; i < m; i++) {
/* 127 */       bs[i] = 1;
/*     */     }
/* 129 */     boolean flag = true;
/* 130 */     boolean tempFlag = false;
/* 131 */     int pos = 0;
/* 132 */     int sum = 0;
/*     */     do
/*     */     {
/* 135 */       sum = 0;
/* 136 */       pos = 0;
/* 137 */       tempFlag = true;
/* 138 */       result.add(print(bs, a, m));
/*     */       
/* 140 */       for (int i = 0; i < n - 1; i++) {
/* 141 */         if ((bs[i] == 1) && (bs[(i + 1)] == 0)) {
/* 142 */           bs[i] = 0;
/* 143 */           bs[(i + 1)] = 1;
/* 144 */           pos = i;
/* 145 */           break;
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 150 */       for (int i = 0; i < pos; i++) {
/* 151 */         if (bs[i] == 1) {
/* 152 */           sum++;
/*     */         }
/*     */       }
/* 155 */       for (int i = 0; i < pos; i++) {
/* 156 */         if (i < sum) {
/* 157 */           bs[i] = 1;
/*     */         } else {
/* 159 */           bs[i] = 0;
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 164 */       for (int i = n - m; i < n; i++) {
/* 165 */         if (bs[i] == 0) {
/* 166 */           tempFlag = false;
/* 167 */           break;
/*     */         }
/*     */       }
/* 170 */       if (!tempFlag) {
/* 171 */         flag = true;
/*     */       } else {
/* 173 */         flag = false;
/*     */       }
/*     */       
/* 176 */     } while (flag);
/* 177 */     result.add(print(bs, a, m));
/* 178 */     return result;
/*     */   }
/*     */   
/*     */   private String[] print(int[] bs, String[] a, int m) {
/* 182 */     String[] result = new String[m];
/* 183 */     int pos = 0;
/* 184 */     for (int i = 0; i < bs.length; i++) {
/* 185 */       if (bs[i] == 1) {
/* 186 */         result[pos] = a[i];
/* 187 */         pos++;
/*     */       }
/*     */     }
/* 190 */     return result;
/*     */   }
/*     */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/pool/play/impl/RXTD115PlayHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */