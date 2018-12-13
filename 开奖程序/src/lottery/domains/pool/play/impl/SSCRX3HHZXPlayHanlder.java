/*     */ package lottery.domains.pool.play.impl;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
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
/*     */ public class SSCRX3HHZXPlayHanlder
/*     */ {
/*     */   private static final String PLAYID_ZUX3 = "sxzuxzsh";
/*     */   private static final String PLAYID_ZUX6 = "sxzuxzlh";
/*     */   
/*     */   public Object[] getBetNums(String bet)
/*     */   {
/*  26 */     Object[] res = new Object[2];
/*  27 */     String[] xz = bet.substring(1, 10).split(",");
/*  28 */     StringBuffer placeIndex = new StringBuffer();
/*  29 */     for (int i = 0; i < xz.length; i++) {
/*  30 */       if (xz[i].equals("√")) {
/*  31 */         placeIndex.append(String.valueOf(i)).append(",");
/*     */       }
/*     */     }
/*  34 */     String placeVal = placeIndex.toString().trim();
/*  35 */     if (!placeVal.equals("")) {
/*  36 */       placeVal = placeVal.substring(0, placeVal.length() - 1);
/*     */     }
/*     */     
/*  39 */     String betValue = bet.substring(11, bet.length()).trim();
/*     */     
/*  41 */     TreeSet<String> filter = new TreeSet();
/*  42 */     String[] split = betValue.split(" ");
/*  43 */     for (int i = 0; i < split.length; i++) {
/*  44 */       if ((split[i] != null) && (split[i].trim().length() > 0)) {
/*  45 */         filter.add(TicketPlayUtils.getSortedNums(split[i]));
/*     */       }
/*     */     }
/*  48 */     String[] nums = (String[])filter.toArray(new String[0]);
/*     */     
/*  50 */     res[0] = placeVal.trim().split(",");
/*  51 */     res[1] = nums;
/*  52 */     return res;
/*     */   }
/*     */   
/*     */   public String[] getOpenNums(String openNums) {
/*  56 */     return TicketPlayUtils.getOpenNums(openNums, ITicketPlayHandler.OFFSETS_WUXIN);
/*     */   }
/*     */   
/*  59 */   private static ConcurrentHashMap<Integer, Object[]> numCache = new ConcurrentHashMap();
/*     */   
/*  61 */   public static void schedulerClearCache() { TimerTask task = new TimerTask()
/*     */     {
/*     */       public void run() {
/*  64 */         synchronized (SSCRX3HHZXPlayHanlder.numCache) {
/*  65 */           SSCRX3HHZXPlayHanlder.numCache.clear();
/*     */         }
/*     */       }
/*  68 */     };
/*  69 */     Timer timer = new Timer();
/*  70 */     timer.schedule(task, 1000L, 1800000L);
/*     */   }
/*     */   
/*     */   public List<WinResult> calculateWinNum(int userBetsId, String index, String openNums) {
/*  74 */     Map<String, WinResult> results = new HashMap();
/*     */     
/*  76 */     Object[] data = null;
/*  77 */     boolean hit = false;
/*     */     try {
/*  79 */       if (numCache.containsKey(Integer.valueOf(userBetsId))) {
/*  80 */         synchronized (numCache) {
/*  81 */           data = (Object[])numCache.get(Integer.valueOf(userBetsId));
/*  82 */           hit = true;
/*     */         }
/*     */         
/*     */       } else {
/*  86 */         data = getBetNums(index);
/*     */       }
/*     */     } catch (Exception e) {
/*  89 */       e.printStackTrace();
/*  90 */       numCache.remove(Integer.valueOf(userBetsId));
/*     */     }
/*     */     
/*  93 */     if (data == null) {
/*  94 */       data = getBetNums(index);
/*  95 */       hit = false;
/*     */     }
/*     */     
/*  98 */     if (data.length != 2) {
/*  99 */       return null;
/*     */     }
/*     */     
/* 102 */     String[] offsets = (String[])data[0];
/* 103 */     String[] betNums = (String[])data[1];
/* 104 */     if (!hit) {
/* 105 */       Arrays.sort(betNums);
/* 106 */       data[1] = betNums;
/* 107 */       synchronized (numCache) {
/* 108 */         numCache.put(Integer.valueOf(userBetsId), data);
/*     */       }
/*     */     }
/*     */     
/* 112 */     for (int i = 0; i < offsets.length - 1; i++) {
/* 113 */       for (int j = i + 1; j < offsets.length; j++) {
/* 114 */         for (int k = j + 1; k < offsets.length; k++) {
/* 115 */           String[] offsetOpenNums = TicketPlayUtils.getOpenNums(openNums, new int[] { Integer.parseInt(offsets[i]), Integer.parseInt(offsets[j]), Integer.parseInt(offsets[k]) });
/*     */           
/*     */ 
/* 118 */           if ((!offsetOpenNums[0].equals(offsetOpenNums[1])) || (!offsetOpenNums[1].equals(offsetOpenNums[2])))
/*     */           {
/*     */ 
/*     */ 
/* 122 */             Arrays.sort(offsetOpenNums);
/* 123 */             String num = offsetOpenNums[0] + offsetOpenNums[1] + offsetOpenNums[2];
/* 124 */             if (Arrays.binarySearch(betNums, num) >= 0)
/*     */             {
/*     */ 
/* 127 */               String[] fixedOpenNum = TicketPlayUtils.getFixedNums(offsetOpenNums);
/*     */               
/*     */ 
/* 130 */               String playId = fixedOpenNum.length == 2 ? "sxzuxzsh" : "sxzuxzlh";
/* 131 */               int groupType = fixedOpenNum.length == 2 ? 1 : 2;
/* 132 */               WinResult result = (WinResult)results.get(playId);
/* 133 */               if (result == null) {
/* 134 */                 result = new WinResult();
/* 135 */                 result.setPlayId(playId);
/* 136 */                 result.setGroupType(groupType);
/*     */               }
/*     */               
/* 139 */               result.setWinNum(result.getWinNum() + 1);
/* 140 */               results.put(playId, result);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 146 */     return new ArrayList(results.values());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private String getPlayId(char[] chars)
/*     */   {
/* 153 */     if ((chars[0] == chars[1]) || (chars[0] == chars[2]) || (chars[1] == chars[2])) {
/* 154 */       return "sxzuxzsh";
/*     */     }
/*     */     
/* 157 */     return "sxzuxzlh";
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/* 165 */     String betNum = "[√,√,√,√,√]027 028 029 127 128 129 227 228 229 237 238 239 247 248 249 257 258 259 267 268 269 277 278 279 288 289 299";
/* 166 */     String openCode = "8,2,1,2,9";
/*     */     
/* 168 */     long start = System.currentTimeMillis();
/* 169 */     SSCRX3HHZXPlayHanlder hanlder = new SSCRX3HHZXPlayHanlder();
/*     */     
/* 171 */     System.out.println("开奖号码" + openCode);
/*     */     
/* 173 */     List<WinResult> results = hanlder.calculateWinNum(1, betNum, openCode);
/*     */     
/*     */ 
/* 176 */     long spend = System.currentTimeMillis() - start;
/*     */     
/* 178 */     if (results != null) {
/* 179 */       for (WinResult result : results) {
/* 180 */         System.out.println("玩法ID：" + result.getPlayId() + "，中奖注数：" + result.getWinNum());
/*     */       }
/*     */     }
/* 183 */     System.out.println("耗时" + spend);
/*     */   }
/*     */   
/*     */   static {}
/*     */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/pool/play/impl/SSCRX3HHZXPlayHanlder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */