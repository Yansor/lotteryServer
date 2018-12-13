/*     */ package lottery.domains.content.biz.impl;
/*     */ 
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.BlockingQueue;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.LinkedBlockingDeque;
/*     */ import javautils.date.Moment;
/*     */ import javautils.redis.JedisTemplate;
/*     */ import lottery.domains.content.biz.LotteryOpenCodeService;
/*     */ import lottery.domains.content.biz.UserBetsService;
/*     */ import lottery.domains.content.biz.UserBillService;
/*     */ import lottery.domains.content.dao.UserBetsDao;
/*     */ import lottery.domains.content.dao.UserDao;
/*     */ import lottery.domains.content.entity.LotteryOpenCode;
/*     */ import lottery.domains.content.entity.UserBets;
/*     */ import lottery.domains.content.vo.bets.UserBetsAutoCancelAdapter;
/*     */ import lottery.domains.content.vo.bets.UserBetsCancelAdapter;
/*     */ import lottery.domains.open.jobs.MailJob;
/*     */ import org.apache.commons.collections.CollectionUtils;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import redis.clients.jedis.Pipeline;
/*     */ import redis.clients.jedis.exceptions.JedisException;
/*     */ 
/*     */ @org.springframework.stereotype.Service
/*     */ public class UserBetsServiceImpl implements UserBetsService
/*     */ {
/*     */   public static final String USER_BETS_UNOPEN_RECENT_KEY = "USER:BETS:UNOPEN:RECENT:%s";
/*     */   public static final String USER_BETS_OPENED_RECENT_KEY = "USER:BETS:OPENED:RECENT:%s";
/*  35 */   private static final Logger log = LoggerFactory.getLogger(UserBetsServiceImpl.class);
/*  36 */   private BlockingQueue<UserBetsCancelAdapter> cancelChaseQueue = new LinkedBlockingDeque();
/*     */   
/*  38 */   private ConcurrentHashMap<String, Boolean> cancelChaseMap = new ConcurrentHashMap();
/*  39 */   private ConcurrentHashMap<String, Boolean> cancelChaseHisMap = new ConcurrentHashMap();
/*     */   
/*  41 */   private BlockingQueue<UserBetsAutoCancelAdapter> cancelTXFFXInvalidQueue = new LinkedBlockingDeque();
/*  42 */   private Set<String> cancelTXFFXInvalidSet = Collections.synchronizedSet(new HashSet());
/*     */   
/*  44 */   private BlockingQueue<UserBetsAutoCancelAdapter> cancelTXLHDInvalidQueue = new LinkedBlockingDeque();
/*  45 */   private Set<String> cancelTXLHDInvalidSet = Collections.synchronizedSet(new HashSet());
/*     */   
/*  47 */   private static boolean isRunning = false;
/*     */   @Autowired
/*     */   private UserDao uDao;
/*     */   @Autowired
/*     */   private UserBetsDao uBetsDao;
/*     */   @Autowired
/*     */   private LotteryOpenCodeService lotteryOpenCodeService;
/*     */   @Autowired
/*     */   private MailJob mailJob;
/*     */   @Autowired
/*     */   private UserBillService uBillService;
/*     */   @Autowired
/*     */   private JedisTemplate jedisTemplate;
/*     */   
/*     */   /* Error */
/*     */   @org.springframework.scheduling.annotation.Scheduled(cron="0/3 * * * * *")
/*     */   public void run()
/*     */   {
/*     */     // Byte code:
/*     */     //   0: ldc 16
/*     */     //   2: dup
/*     */     //   3: astore_1
/*     */     //   4: monitorenter
/*     */     //   5: getstatic 17	lottery/domains/content/biz/impl/UserBetsServiceImpl:isRunning	Z
/*     */     //   8: iconst_1
/*     */     //   9: if_icmpne +6 -> 15
/*     */     //   12: aload_1
/*     */     //   13: monitorexit
/*     */     //   14: return
/*     */     //   15: iconst_1
/*     */     //   16: putstatic 17	lottery/domains/content/biz/impl/UserBetsServiceImpl:isRunning	Z
/*     */     //   19: aload_1
/*     */     //   20: monitorexit
/*     */     //   21: goto +8 -> 29
/*     */     //   24: astore_2
/*     */     //   25: aload_1
/*     */     //   26: monitorexit
/*     */     //   27: aload_2
/*     */     //   28: athrow
/*     */     //   29: aload_0
/*     */     //   30: invokespecial 18	lottery/domains/content/biz/impl/UserBetsServiceImpl:cancelChase	()V
/*     */     //   33: aload_0
/*     */     //   34: invokespecial 19	lottery/domains/content/biz/impl/UserBetsServiceImpl:cancelTXFFC	()V
/*     */     //   37: aload_0
/*     */     //   38: invokespecial 20	lottery/domains/content/biz/impl/UserBetsServiceImpl:cancelTXLHD	()V
/*     */     //   41: iconst_0
/*     */     //   42: putstatic 17	lottery/domains/content/biz/impl/UserBetsServiceImpl:isRunning	Z
/*     */     //   45: goto +10 -> 55
/*     */     //   48: astore_3
/*     */     //   49: iconst_0
/*     */     //   50: putstatic 17	lottery/domains/content/biz/impl/UserBetsServiceImpl:isRunning	Z
/*     */     //   53: aload_3
/*     */     //   54: athrow
/*     */     //   55: return
/*     */     // Line number table:
/*     */     //   Java source line #75	-> byte code offset #0
/*     */     //   Java source line #76	-> byte code offset #5
/*     */     //   Java source line #78	-> byte code offset #12
/*     */     //   Java source line #80	-> byte code offset #15
/*     */     //   Java source line #81	-> byte code offset #19
/*     */     //   Java source line #83	-> byte code offset #29
/*     */     //   Java source line #84	-> byte code offset #33
/*     */     //   Java source line #85	-> byte code offset #37
/*     */     //   Java source line #87	-> byte code offset #41
/*     */     //   Java source line #88	-> byte code offset #45
/*     */     //   Java source line #87	-> byte code offset #48
/*     */     //   Java source line #89	-> byte code offset #55
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	56	0	this	UserBetsServiceImpl
/*     */     //   3	23	1	Ljava/lang/Object;	Object
/*     */     //   24	4	2	localObject1	Object
/*     */     //   48	6	3	localObject2	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   5	14	24	finally
/*     */     //   15	21	24	finally
/*     */     //   24	27	24	finally
/*     */     //   29	41	48	finally
/*     */   }
/*     */   
/*     */   private void cancelChase()
/*     */   {
/*  92 */     if ((this.cancelChaseQueue != null) && (this.cancelChaseQueue.size() > 0)) {
/*     */       try {
/*  94 */         List<UserBetsCancelAdapter> cancels = new LinkedList();
/*  95 */         this.cancelChaseQueue.drainTo(cancels, 200);
/*  96 */         if (CollectionUtils.isNotEmpty(cancels)) {
/*  97 */           cancelChase(cancels);
/*     */         }
/*     */       } catch (Exception e) {
/* 100 */         log.error("撤销用户追单失败", e);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void cancelChase(List<UserBetsCancelAdapter> cancels) {
/* 106 */     log.info("正在中奖撤销追号订单，共计{}条追号单需要撤销", Integer.valueOf(cancels.size()));
/*     */     
/* 108 */     long start = System.currentTimeMillis();
/*     */     
/* 110 */     int canceledCount = 0;
/* 111 */     for (UserBetsCancelAdapter cancel : cancels) {
/* 112 */       List<UserBets> list = this.uBetsDao.getByChaseBillno(cancel.getChaseBillno(), cancel.getUserId(), cancel.getWinExpect());
/* 113 */       for (UserBets bBean : list) {
/* 114 */         if (bBean.getStatus() == 0) {
/* 115 */           doCancelOrderByChase(bBean);
/* 116 */           canceledCount++;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 121 */     long spent = System.currentTimeMillis() - start;
/* 122 */     log.info("完成中奖撤销追号订单，共计{}条追号单{}条注单,耗时{}", new Object[] { Integer.valueOf(cancels.size()), Integer.valueOf(canceledCount), Long.valueOf(spent) });
/*     */     
/* 124 */     if (spent >= 30000L) {
/* 125 */       String warningMsg = String.format("中奖撤销追号订单耗时告警；撤销%s条追号单%s条注单时耗时达到%s", new Object[] { Integer.valueOf(cancels.size()), Integer.valueOf(canceledCount), Long.valueOf(spent) });
/* 126 */       log.warn(warningMsg);
/* 127 */       this.mailJob.addWarning(warningMsg);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean cancelChase(String chaseBillno, int userId, String winExpect)
/*     */   {
/* 133 */     if ((!this.cancelChaseMap.containsKey(chaseBillno)) && (!this.cancelChaseHisMap.contains(chaseBillno))) {
/* 134 */       this.cancelChaseMap.put(chaseBillno, Boolean.valueOf(true));
/* 135 */       this.cancelChaseQueue.offer(new UserBetsCancelAdapter(chaseBillno, userId, winExpect));
/*     */     }
/* 137 */     return true;
/*     */   }
/*     */   
/*     */   public boolean isCancelChase(String chaseBillno)
/*     */   {
/* 142 */     return (this.cancelChaseMap.containsKey(chaseBillno)) || (this.cancelChaseHisMap.contains(chaseBillno));
/*     */   }
/*     */   
/*     */   private boolean doCancelOrderByChase(UserBets bBean) {
/*     */     try {
/* 147 */       if ((bBean.getType() == 1) && (bBean.getStatus() == 0) && (bBean.getChaseStop().intValue() == 1)) {
/* 148 */         boolean canceled = this.uBetsDao.cancel(bBean.getId());
/* 149 */         if (canceled) {
/* 150 */           boolean added = this.uBillService.addCancelOrderBill(bBean, "中奖后停止追号返款");
/* 151 */           if (added) {
/* 152 */             cacheUserBetsId(bBean);
/* 153 */             return this.uDao.updateLotteryMoney(bBean.getUserId(), bBean.getMoney(), -bBean.getMoney());
/*     */           }
/*     */         }
/*     */       }
/* 157 */       return false;
/*     */     } catch (Exception e) {
/* 159 */       log.error("中奖停止追号出错,注单号：" + bBean.getId() + ",追单号：" + bBean.getChaseBillno(), e);
/*     */     }
/*     */     
/* 162 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   private void cancelTXFFC()
/*     */   {
/* 168 */     if ((this.cancelTXFFXInvalidQueue != null) && (this.cancelTXFFXInvalidQueue.size() > 0)) {
/*     */       try {
/* 170 */         List<UserBetsAutoCancelAdapter> cancels = new LinkedList();
/* 171 */         this.cancelTXFFXInvalidQueue.drainTo(cancels, 200);
/* 172 */         if (CollectionUtils.isNotEmpty(cancels)) {
/* 173 */           cancelTXFFC(cancels);
/*     */         }
/*     */       } catch (Exception e) {
/* 176 */         log.error("撤销腾讯分分彩失败", e);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void cancelTXFFC(List<UserBetsAutoCancelAdapter> cancels) {
/* 182 */     long start = System.currentTimeMillis();
/*     */     
/* 184 */     int canceledCount = 0;
/* 185 */     for (UserBetsAutoCancelAdapter cancel : cancels) {
/* 186 */       List<UserBets> list = cancel.getUserBetsList();
/*     */       
/* 188 */       log.info("正在撤销腾讯分分彩无效开奖号码，期号{}", cancel.getLotteryOpenCode().getExpect());
/*     */       
/*     */ 
/* 191 */       for (UserBets bBean : list) {
/* 192 */         if (bBean.getStatus() == 0) {
/* 193 */           doCancelOrderByTXFFC(bBean, cancel.getLotteryOpenCode().getCode());
/* 194 */           canceledCount++;
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 199 */       this.lotteryOpenCodeService.updateCancelled(cancel.getLotteryOpenCode());
/*     */       
/* 201 */       this.cancelTXFFXInvalidSet.remove(cancel.getLotteryOpenCode().getExpect());
/*     */     }
/*     */     
/* 204 */     long spent = System.currentTimeMillis() - start;
/* 205 */     log.info("完成撤销腾讯分分彩无效开奖号码，共计{}期{}条注单,耗时{}", new Object[] { Integer.valueOf(cancels.size()), Integer.valueOf(canceledCount), Long.valueOf(spent) });
/*     */     
/* 207 */     if (spent >= 30000L) {
/* 208 */       String warningMsg = String.format("撤销腾讯分分彩无效开奖号码耗时告警；撤销%s期%s条注单时耗时达到%s", new Object[] { Integer.valueOf(cancels.size()), Integer.valueOf(canceledCount), Long.valueOf(spent) });
/* 209 */       log.warn(warningMsg);
/* 210 */       this.mailJob.addWarning(warningMsg);
/*     */     }
/*     */   }
/*     */   
/*     */   public void cancelByTXFFCInvalid(LotteryOpenCode lotteryOpenCode, List<UserBets> userBetsList)
/*     */   {
/* 216 */     if (this.cancelTXFFXInvalidSet.contains(lotteryOpenCode.getExpect())) {
/* 217 */       return;
/*     */     }
/* 219 */     if (!"txffc".equalsIgnoreCase(lotteryOpenCode.getLottery())) {
/* 220 */       return;
/*     */     }
/*     */     
/* 223 */     this.cancelTXFFXInvalidSet.add(lotteryOpenCode.getExpect());
/* 224 */     this.cancelTXFFXInvalidQueue.offer(new UserBetsAutoCancelAdapter(lotteryOpenCode, userBetsList));
/*     */   }
/*     */   
/*     */   private boolean doCancelOrderByTXFFC(UserBets bBean, String openCode) {
/*     */     try {
/* 229 */       Moment thisTime = new Moment();
/* 230 */       Moment stopTime = new Moment().fromTime(bBean.getStopTime());
/* 231 */       if ((bBean.getStatus() == 0) && (thisTime.ge(stopTime))) {
/* 232 */         boolean canceled = this.uBetsDao.cancelAndSetOpenCode(bBean.getId(), openCode);
/* 233 */         if (canceled) {
/* 234 */           boolean added = this.uBillService.addCancelOrderBill(bBean, "腾讯分分彩重复开奖号码撤单");
/* 235 */           if (added) {
/* 236 */             cacheUserBetsId(bBean);
/* 237 */             return this.uDao.updateLotteryMoney(bBean.getUserId(), bBean.getMoney(), -bBean.getMoney());
/*     */           }
/*     */         }
/*     */       }
/* 241 */       return false;
/*     */     } catch (Exception e) {
/* 243 */       log.error("腾讯分分彩无效开奖号码撤单出错,注单号：" + bBean.getId(), e);
/*     */     }
/*     */     
/* 246 */     return false;
/*     */   }
/*     */   
/*     */   private void cancelTXLHD()
/*     */   {
/* 251 */     if ((this.cancelTXLHDInvalidQueue != null) && (this.cancelTXLHDInvalidQueue.size() > 0)) {
/*     */       try {
/* 253 */         List<UserBetsAutoCancelAdapter> cancels = new LinkedList();
/* 254 */         this.cancelTXLHDInvalidQueue.drainTo(cancels, 200);
/* 255 */         if (CollectionUtils.isNotEmpty(cancels)) {
/* 256 */           cancelTXLHD(cancels);
/*     */         }
/*     */       } catch (Exception e) {
/* 259 */         log.error("撤销腾讯龙虎斗失败", e);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void cancelTXLHD(List<UserBetsAutoCancelAdapter> cancels) {
/* 265 */     long start = System.currentTimeMillis();
/*     */     
/* 267 */     int canceledCount = 0;
/* 268 */     for (UserBetsAutoCancelAdapter cancel : cancels) {
/* 269 */       List<UserBets> list = cancel.getUserBetsList();
/*     */       
/* 271 */       log.info("正在撤销腾讯龙虎斗无效开奖号码，期号{}", cancel.getLotteryOpenCode().getExpect());
/*     */       
/*     */ 
/* 274 */       for (UserBets bBean : list) {
/* 275 */         if (bBean.getStatus() == 0) {
/* 276 */           doCancelOrderByTXLHD(bBean, cancel.getLotteryOpenCode().getCode());
/* 277 */           canceledCount++;
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 282 */       this.lotteryOpenCodeService.updateCancelled(cancel.getLotteryOpenCode());
/*     */       
/* 284 */       this.cancelTXLHDInvalidSet.remove(cancel.getLotteryOpenCode().getExpect());
/*     */     }
/*     */     
/* 287 */     long spent = System.currentTimeMillis() - start;
/* 288 */     log.info("完成撤销腾讯龙虎斗无效开奖号码，共计{}期{}条注单,耗时{}", new Object[] { Integer.valueOf(cancels.size()), Integer.valueOf(canceledCount), Long.valueOf(spent) });
/*     */     
/* 290 */     if (spent >= 30000L) {
/* 291 */       String warningMsg = String.format("撤销腾讯龙虎斗无效开奖号码耗时告警；撤销%s期%s条注单时耗时达到%s", new Object[] { Integer.valueOf(cancels.size()), Integer.valueOf(canceledCount), Long.valueOf(spent) });
/* 292 */       log.warn(warningMsg);
/* 293 */       this.mailJob.addWarning(warningMsg);
/*     */     }
/*     */   }
/*     */   
/*     */   public void cancelByTXLHDInvalid(LotteryOpenCode lotteryOpenCode, List<UserBets> userBetsList)
/*     */   {
/* 299 */     if (this.cancelTXLHDInvalidSet.contains(lotteryOpenCode.getExpect())) {
/* 300 */       return;
/*     */     }
/* 302 */     if (!"txlhd".equalsIgnoreCase(lotteryOpenCode.getLottery())) {
/* 303 */       return;
/*     */     }
/*     */     
/* 306 */     this.cancelTXLHDInvalidSet.add(lotteryOpenCode.getExpect());
/* 307 */     this.cancelTXLHDInvalidQueue.offer(new UserBetsAutoCancelAdapter(lotteryOpenCode, userBetsList));
/*     */   }
/*     */   
/*     */   private boolean doCancelOrderByTXLHD(UserBets bBean, String openCode) {
/*     */     try {
/* 312 */       Moment thisTime = new Moment();
/* 313 */       Moment stopTime = new Moment().fromTime(bBean.getStopTime());
/* 314 */       if ((bBean.getStatus() == 0) && (thisTime.ge(stopTime))) {
/* 315 */         boolean canceled = this.uBetsDao.cancelAndSetOpenCode(bBean.getId(), openCode);
/* 316 */         if (canceled) {
/* 317 */           boolean added = this.uBillService.addCancelOrderBill(bBean, "腾讯龙虎斗重复开奖号码撤单");
/* 318 */           if (added) {
/* 319 */             cacheUserBetsId(bBean);
/* 320 */             return this.uDao.updateLotteryMoney(bBean.getUserId(), bBean.getMoney(), -bBean.getMoney());
/*     */           }
/*     */         }
/*     */       }
/* 324 */       return false;
/*     */     } catch (Exception e) {
/* 326 */       log.error("腾讯龙虎斗无效开奖号码撤单出错,注单号：" + bBean.getId(), e);
/*     */     }
/*     */     
/* 329 */     return false;
/*     */   }
/*     */   
/*     */   @org.springframework.scheduling.annotation.Scheduled(cron="0 30 5 * * *")
/*     */   public void clear() {
/* 334 */     this.cancelChaseHisMap.clear();
/* 335 */     this.cancelChaseHisMap.putAll(this.cancelChaseMap);
/* 336 */     this.cancelChaseMap.clear();
/*     */   }
/*     */   
/*     */   private void cacheUserBetsId(UserBets userBets)
/*     */   {
/* 341 */     final String unOpenCacheKey = String.format("USER:BETS:UNOPEN:RECENT:%s", new Object[] { Integer.valueOf(userBets.getUserId()) });
/* 342 */     final String openedCacheKey = String.format("USER:BETS:OPENED:RECENT:%s", new Object[] { Integer.valueOf(userBets.getUserId()) });
/* 343 */     final String userBetsId = userBets.getId() + "";
/*     */     try
/*     */     {
/* 346 */       this.jedisTemplate.execute(new javautils.redis.JedisTemplate.PipelineActionNoResult()
/*     */       {
/*     */         public void action(Pipeline pipeline) {
/* 349 */           pipeline.lrem(unOpenCacheKey, 1L, userBetsId);
/* 350 */           pipeline.lpush(openedCacheKey, new String[] { userBetsId });
/* 351 */           pipeline.ltrim(openedCacheKey, 0L, 10L);
/* 352 */           pipeline.expire(openedCacheKey, 43200);
/* 353 */           pipeline.sync();
/*     */         }
/*     */       });
/*     */     } catch (JedisException e) {
/* 357 */       log.error("执行Redis缓存注单ID时出错", e);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/content/biz/impl/UserBetsServiceImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */