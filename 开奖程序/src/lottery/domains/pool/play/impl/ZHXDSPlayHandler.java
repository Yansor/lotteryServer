/*     */ package lottery.domains.pool.play.impl;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.util.Arrays;
/*     */ import java.util.Timer;
/*     */ import java.util.TimerTask;
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
/*     */ public class ZHXDSPlayHandler
/*     */   implements ITicketPlayHandler
/*     */ {
/*     */   private String playId;
/*     */   private int[] offsets;
/*     */   
/*     */   public ZHXDSPlayHandler(String playId, int[] offsets)
/*     */   {
/*  25 */     this.playId = playId;
/*  26 */     this.offsets = offsets;
/*  27 */     schedulerClearCache();
/*     */   }
/*     */   
/*     */ 
/*     */   public String[] getBetNums(String betNums)
/*     */   {
/*  33 */     return betNums.trim().split(" ");
/*     */   }
/*     */   
/*     */   public String[] getOpenNums(String openNums)
/*     */   {
/*  38 */     return TicketPlayUtils.getOpenNums(openNums, this.offsets);
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
/*  61 */   private ConcurrentHashMap<Integer, String[]> numCache = new ConcurrentHashMap();
/*     */   
/*  63 */   public void schedulerClearCache() { TimerTask task = new TimerTask()
/*     */     {
/*     */       public void run() {
/*  66 */         synchronized (ZHXDSPlayHandler.this.numCache) {
/*  67 */           ZHXDSPlayHandler.this.numCache.clear();
/*     */         }
/*     */       }
/*  70 */     };
/*  71 */     Timer timer = new Timer();
/*  72 */     timer.schedule(task, 1000L, 1800000L);
/*     */   }
/*     */   
/*     */   public WinResult calculateWinNum(int userBetsId, String index, String openNums)
/*     */   {
/*  77 */     WinResult result = new WinResult();
/*     */     
/*  79 */     String[] nums = null;
/*  80 */     boolean hit = false;
/*     */     try {
/*  82 */       if (this.numCache.containsKey(Integer.valueOf(userBetsId))) {
/*  83 */         synchronized (this.numCache) {
/*  84 */           nums = (String[])this.numCache.get(Integer.valueOf(userBetsId));
/*  85 */           hit = true;
/*     */         }
/*     */         
/*     */       } else {
/*  89 */         nums = getBetNums(index);
/*     */       }
/*     */     } catch (Exception e) {
/*  92 */       e.printStackTrace();
/*  93 */       this.numCache.remove(Integer.valueOf(userBetsId));
/*     */     }
/*     */     
/*  96 */     if (nums == null) {
/*  97 */       nums = getBetNums(index);
/*  98 */       hit = false;
/*     */     }
/*     */     
/*     */ 
/* 102 */     String[] openNum = getOpenNums(openNums);
/* 103 */     if ((nums == null) || (openNum == null) || (openNum.length != this.offsets.length)) {
/* 104 */       return result;
/*     */     }
/* 106 */     StringBuilder openRes = new StringBuilder();
/* 107 */     for (String num : openNum) {
/* 108 */       openRes.append(num);
/*     */     }
/* 110 */     int winNum = 0;
/*     */     
/* 112 */     if (!hit) {
/* 113 */       Arrays.sort(nums);
/* 114 */       synchronized (this.numCache) {
/* 115 */         this.numCache.put(Integer.valueOf(userBetsId), nums);
/*     */       }
/*     */     }
/*     */     
/* 119 */     if (Arrays.binarySearch(nums, openRes.toString()) >= 0) {
/* 120 */       winNum = 1;
/*     */     }
/* 122 */     result.setPlayId(this.playId);
/* 123 */     result.setWinNum(winNum);
/* 124 */     return result;
/*     */   }
/*     */   
/*     */   public static void main(String[] args) {
/* 128 */     ZHXDSPlayHandler handler = new ZHXDSPlayHandler("1_exzhixdsh", ITicketPlayHandler.OFFSETS_HOUER);
/*     */     
/* 130 */     WinResult winResult = handler.calculateWinNum(5980325, "00 02 03 05 06 07 08 09 11 15 16 20 21 22 23 26 27 31 34 35 36 39 40 41 42 43 44 45 46 48 58 61 63 64 70 71 73 74 75 81 84 85 86 87 90 92 93 94 96 97", "8,4,4,6,3");
/* 131 */     System.out.println(winResult.getWinNum());
/*     */   }
/*     */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/pool/play/impl/ZHXDSPlayHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */