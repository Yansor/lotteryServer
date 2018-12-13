/*     */ package lottery.domains.pool.play.impl;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
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
/*     */ public class ZXPlayHanlder
/*     */   implements ITicketPlayHandler
/*     */ {
/*     */   private String playId;
/*     */   private int[] offsets;
/*     */   private int moreNumCount;
/*     */   private int moreNumTimes;
/*     */   private int fewerNumCount;
/*     */   private int fewerNumTimes;
/*     */   
/*     */   public ZXPlayHanlder(String playId, int moreNumCount, int moreNumTimes, int fewerNumCount, int fewerNumTimes, int[] offsets)
/*     */   {
/*  31 */     this.playId = playId;
/*  32 */     this.offsets = offsets;
/*  33 */     this.moreNumCount = moreNumCount;
/*  34 */     this.moreNumTimes = moreNumTimes;
/*  35 */     this.fewerNumCount = fewerNumCount;
/*  36 */     this.fewerNumTimes = fewerNumTimes;
/*     */   }
/*     */   
/*     */ 
/*     */   public String[] getBetNums(String betNums)
/*     */   {
/*  42 */     return betNums.trim().split(",");
/*     */   }
/*     */   
/*     */   public String[] getOpenNums(String openNums)
/*     */   {
/*  47 */     return TicketPlayUtils.getOpenNums(openNums, this.offsets);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public WinResult calculateWinNum(int userBetsId, String index, String openNums)
/*     */   {
/*  76 */     WinResult result = new WinResult();
/*  77 */     String[] nums = getIndexOpenNum(openNums);
/*  78 */     if ((nums == null) || (nums.length != 2)) {
/*  79 */       return result;
/*     */     }
/*  81 */     int winNum = 1;
/*  82 */     String[] betNum = getBetNums(index);
/*  83 */     for (int i = 0; i < nums[0].length(); i++) {
/*  84 */       if (!betNum[0].contains(String.valueOf(nums[0].charAt(i)))) {
/*  85 */         winNum = 0;
/*  86 */         break;
/*     */       }
/*     */     }
/*  89 */     for (int i = 0; i < nums[1].length(); i++) {
/*  90 */       if (!betNum[1].contains(String.valueOf(nums[1].charAt(i)))) {
/*  91 */         winNum = 0;
/*  92 */         break;
/*     */       }
/*     */     }
/*  95 */     result.setPlayId(this.playId);
/*  96 */     result.setWinNum(winNum);
/*  97 */     return result;
/*     */   }
/*     */   
/*     */   private String[] getIndexOpenNum(String openNums) {
/* 101 */     String[] openNum = TicketPlayUtils.getOpenNums(openNums, this.offsets);
/* 102 */     String[] res = new String[2];
/* 103 */     Map<String, Integer> nums = new HashMap();
/* 104 */     for (String num : openNum) {
/* 105 */       if (nums.get(num) == null) {
/* 106 */         nums.put(num, Integer.valueOf(1));
/*     */       } else {
/* 108 */         nums.put(num, Integer.valueOf(((Integer)nums.get(num)).intValue() + 1));
/*     */       }
/*     */     }
/* 111 */     Object it = nums.keySet().iterator();
/* 112 */     String c = null;
/* 113 */     StringBuilder moreNum = new StringBuilder();
/* 114 */     StringBuilder fewerNum = new StringBuilder();
/* 115 */     int mCount = 0;
/* 116 */     int fCount = 0;
/* 117 */     while (((Iterator)it).hasNext()) {
/* 118 */       c = (String)((Iterator)it).next();
/* 119 */       if (((Integer)nums.get(c)).intValue() == this.moreNumTimes)
/*     */       {
/* 121 */         moreNum.append(c);
/* 122 */         mCount++;
/* 123 */       } else if (((Integer)nums.get(c)).intValue() == this.fewerNumTimes)
/*     */       {
/* 125 */         fewerNum.append(c);
/* 126 */         fCount++;
/*     */       }
/*     */     }
/* 129 */     if ((mCount != this.moreNumCount) || (fCount != this.fewerNumCount)) {
/* 130 */       return null;
/*     */     }
/* 132 */     res[0] = moreNum.toString().trim();
/* 133 */     res[1] = fewerNum.toString().trim();
/* 134 */     return res;
/*     */   }
/*     */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/pool/play/impl/ZXPlayHanlder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */