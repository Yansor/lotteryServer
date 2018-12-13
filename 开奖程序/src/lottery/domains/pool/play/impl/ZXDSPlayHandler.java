/*     */ package lottery.domains.pool.play.impl;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.util.Arrays;
/*     */ import java.util.Timer;
/*     */ import java.util.TimerTask;
/*     */ import java.util.TreeSet;
/*     */ import java.util.concurrent.ConcurrentHashMap;
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
/*     */ public class ZXDSPlayHandler
/*     */   implements ITicketPlayHandler
/*     */ {
/*     */   private int[] offsets;
/*     */   private String playId0;
/*     */   private String playId1;
/*     */   private String playId;
/*     */   
/*     */   public ZXDSPlayHandler(String playId, String playId0, String playId1, int[] offsets)
/*     */   {
/*  29 */     this.playId = playId;
/*  30 */     this.playId0 = playId0;
/*  31 */     this.playId1 = playId1;
/*  32 */     this.offsets = offsets;
/*  33 */     schedulerClearCache();
/*     */   }
/*     */   
/*     */   public String[] getBetNums(String betNums)
/*     */   {
/*  38 */     String[] nums = betNums.trim().split(" ");
/*  39 */     TreeSet<String> res = new TreeSet();
/*  40 */     for (int i = 0; i < nums.length; i++) {
/*  41 */       if ((nums[i] != null) && (nums[i].trim().length() > 0)) {
/*  42 */         res.add(TicketPlayUtils.getSortedNums(nums[i]));
/*     */       }
/*     */     }
/*  45 */     return (String[])res.toArray(new String[0]);
/*     */   }
/*     */   
/*     */   public String[] getOpenNums(String openNums)
/*     */   {
/*  50 */     String[] open = TicketPlayUtils.getOpenNums(openNums, this.offsets);
/*  51 */     Arrays.sort(open);
/*  52 */     return open;
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
/*  79 */   private ConcurrentHashMap<Integer, String[]> numCache = new ConcurrentHashMap();
/*     */   
/*  81 */   public void schedulerClearCache() { TimerTask task = new TimerTask()
/*     */     {
/*     */       public void run() {
/*  84 */         synchronized (ZXDSPlayHandler.this.numCache) {
/*  85 */           ZXDSPlayHandler.this.numCache.clear();
/*     */         }
/*     */       }
/*  88 */     };
/*  89 */     Timer timer = new Timer();
/*  90 */     timer.schedule(task, 1000L, 1800000L);
/*     */   }
/*     */   
/*     */   public WinResult calculateWinNum(int userBetsId, String index, String openNums)
/*     */   {
/*  95 */     WinResult result = new WinResult();
/*     */     
/*  97 */     String[] nums = null;
/*  98 */     boolean hit = false;
/*     */     try {
/* 100 */       if (this.numCache.containsKey(Integer.valueOf(userBetsId))) {
/* 101 */         synchronized (this.numCache) {
/* 102 */           nums = (String[])this.numCache.get(Integer.valueOf(userBetsId));
/* 103 */           hit = true;
/*     */         }
/*     */         
/*     */       } else {
/* 107 */         nums = getBetNums(index);
/*     */       }
/*     */     } catch (Exception e) {
/* 110 */       e.printStackTrace();
/* 111 */       this.numCache.remove(Integer.valueOf(userBetsId));
/*     */     }
/*     */     
/* 114 */     if (nums == null) {
/* 115 */       nums = getBetNums(index);
/* 116 */       hit = false;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 121 */     String[] openNum = getOpenNums(openNums);
/* 122 */     if ((nums == null) || (openNums == null)) {
/* 123 */       return result;
/*     */     }
/* 125 */     String[] fixedOpenNum = TicketPlayUtils.getFixedNums(openNum);
/* 126 */     if (fixedOpenNum.length == 1) {
/* 127 */       return result;
/*     */     }
/* 129 */     StringBuilder sb = new StringBuilder();
/* 130 */     for (String nm : openNum) {
/* 131 */       sb.append(nm.trim());
/*     */     }
/* 133 */     int winNum = 0;
/* 134 */     if (!hit) {
/* 135 */       Arrays.sort(nums);
/* 136 */       synchronized (this.numCache) {
/* 137 */         this.numCache.put(Integer.valueOf(userBetsId), nums);
/*     */       }
/*     */     }
/* 140 */     if (Arrays.binarySearch(nums, sb.toString()) >= 0) {
/* 141 */       winNum = 1;
/*     */     }
/* 143 */     if (openNum.length == fixedOpenNum.length) {
/* 144 */       result.setPlayId(this.playId1);
/* 145 */       result.setGroupType(2);
/*     */     } else {
/* 147 */       result.setPlayId(this.playId0);
/* 148 */       result.setGroupType(1);
/*     */     }
/* 150 */     result.setWinNum(winNum);
/* 151 */     return result;
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
/*     */   public static void main(String[] args)
/*     */   {
/* 177 */     String betNum = "046 047 049 067 069 079 146 147 149 167 169 179 246 247 249 267 269 279 346 347 349 367 369 379 446 447 449 456 457 459 466 467 468 469 477 478 479 489 499 567 569 579 667 669 677 678 679 689 699 779 789 799";
/* 178 */     ZXDSPlayHandler zxdsPlayHandler = new ZXDSPlayHandler("hg1d5fc_sxhhzxh", "hg1d5fc_sxzuxzsh", "hg1d5fc_sxzuxzlh", ITicketPlayHandler.OFFSETS_HOUSAN);
/*     */     
/* 180 */     WinResult winResult = zxdsPlayHandler.calculateWinNum(1, betNum, "0,4,4,4,7");
/*     */     
/* 182 */     System.out.println(winResult.getWinNum());
/* 183 */     System.out.println(winResult.getPlayId());
/*     */   }
/*     */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/pool/play/impl/ZXDSPlayHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */