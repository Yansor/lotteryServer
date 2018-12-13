/*     */ package lottery.domains.content.biz.impl;
/*     */ 
/*     */ import java.math.BigDecimal;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.BlockingQueue;
/*     */ import java.util.concurrent.LinkedBlockingDeque;
/*     */ import javautils.date.Moment;
/*     */ import lottery.domains.content.biz.UserBetsHitRankingService;
/*     */ import lottery.domains.content.dao.DbServerSyncDao;
/*     */ import lottery.domains.content.dao.UserBetsHitRankingDao;
/*     */ import lottery.domains.content.dao.UserDao;
/*     */ import lottery.domains.content.entity.Lottery;
/*     */ import lottery.domains.content.entity.User;
/*     */ import lottery.domains.content.entity.UserBets;
/*     */ import lottery.domains.content.entity.UserBetsHitRanking;
/*     */ import lottery.domains.content.global.DbServerSyncEnum;
/*     */ import lottery.domains.content.vo.config.LotteryConfig;
/*     */ import lottery.domains.pool.DataFactory;
/*     */ import org.apache.commons.collections.CollectionUtils;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Service;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Service
/*     */ public class UserBetsHitRankingServiceImpl
/*     */   implements UserBetsHitRankingService
/*     */ {
/*  35 */   private static final Logger log = LoggerFactory.getLogger(UserBetsHitRankingServiceImpl.class);
/*     */   
/*  37 */   private BlockingQueue<UserBets> userBetsQueue = new LinkedBlockingDeque();
/*     */   
/*     */   @Autowired
/*     */   private UserBetsHitRankingDao rankingDao;
/*     */   
/*     */   @Autowired
/*     */   private DataFactory dataFactory;
/*     */   
/*     */   @Autowired
/*     */   private UserDao uDao;
/*     */   
/*     */   @Autowired
/*     */   private DbServerSyncDao dbServerSyncDao;
/*     */   
/*  51 */   private static boolean isRunning = false;
/*     */   
/*     */   /* Error */
/*     */   @org.springframework.scheduling.annotation.Scheduled(cron="0 0/5 * * * *")
/*     */   public void run()
/*     */   {
/*     */     // Byte code:
/*     */     //   0: ldc 5
/*     */     //   2: dup
/*     */     //   3: astore_1
/*     */     //   4: monitorenter
/*     */     //   5: getstatic 6	lottery/domains/content/biz/impl/UserBetsHitRankingServiceImpl:isRunning	Z
/*     */     //   8: iconst_1
/*     */     //   9: if_icmpne +6 -> 15
/*     */     //   12: aload_1
/*     */     //   13: monitorexit
/*     */     //   14: return
/*     */     //   15: iconst_1
/*     */     //   16: putstatic 6	lottery/domains/content/biz/impl/UserBetsHitRankingServiceImpl:isRunning	Z
/*     */     //   19: aload_1
/*     */     //   20: monitorexit
/*     */     //   21: goto +8 -> 29
/*     */     //   24: astore_2
/*     */     //   25: aload_1
/*     */     //   26: monitorexit
/*     */     //   27: aload_2
/*     */     //   28: athrow
/*     */     //   29: aload_0
/*     */     //   30: invokespecial 7	lottery/domains/content/biz/impl/UserBetsHitRankingServiceImpl:add	()V
/*     */     //   33: iconst_0
/*     */     //   34: putstatic 6	lottery/domains/content/biz/impl/UserBetsHitRankingServiceImpl:isRunning	Z
/*     */     //   37: goto +10 -> 47
/*     */     //   40: astore_3
/*     */     //   41: iconst_0
/*     */     //   42: putstatic 6	lottery/domains/content/biz/impl/UserBetsHitRankingServiceImpl:isRunning	Z
/*     */     //   45: aload_3
/*     */     //   46: athrow
/*     */     //   47: return
/*     */     // Line number table:
/*     */     //   Java source line #56	-> byte code offset #0
/*     */     //   Java source line #57	-> byte code offset #5
/*     */     //   Java source line #59	-> byte code offset #12
/*     */     //   Java source line #61	-> byte code offset #15
/*     */     //   Java source line #62	-> byte code offset #19
/*     */     //   Java source line #64	-> byte code offset #29
/*     */     //   Java source line #66	-> byte code offset #33
/*     */     //   Java source line #67	-> byte code offset #37
/*     */     //   Java source line #66	-> byte code offset #40
/*     */     //   Java source line #68	-> byte code offset #47
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	48	0	this	UserBetsHitRankingServiceImpl
/*     */     //   3	23	1	Ljava/lang/Object;	Object
/*     */     //   24	4	2	localObject1	Object
/*     */     //   40	6	3	localObject2	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   5	14	24	finally
/*     */     //   15	21	24	finally
/*     */     //   24	27	24	finally
/*     */     //   29	33	40	finally
/*     */   }
/*     */   
/*     */   private void add()
/*     */   {
/*  71 */     if ((this.userBetsQueue != null) && (this.userBetsQueue.size() > 0)) {
/*     */       try {
/*  73 */         List<UserBets> adds = new LinkedList();
/*  74 */         this.userBetsQueue.drainTo(adds, 50);
/*  75 */         if (CollectionUtils.isNotEmpty(adds)) {
/*  76 */           add(adds);
/*     */         }
/*     */       } catch (Exception e) {
/*  79 */         log.error("添加中奖排行榜错误", e);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void add(List<UserBets> adds) {
/*  85 */     if (!this.dataFactory.getLotteryConfig().isAutoHitRanking()) {
/*  86 */       return;
/*     */     }
/*  88 */     int hitRankingSize = this.dataFactory.getLotteryConfig().getHitRankingSize();
/*     */     
/*     */ 
/*  91 */     Collections.sort(adds, new Comparator<UserBets>()
/*     */     {
/*     */       public int compare(UserBets o1, UserBets o2) {
/*  94 */         return o2.getPrizeTime().compareTo(o1.getPrizeTime());
/*     */       }
/*     */       
/*  97 */     });
/*  98 */     String today = new Moment().toSimpleDate() + " 00:00:00";
/*  99 */     String tomorrow = new Moment().add(1, "days").toSimpleDate() + " 00:00:00";
/*     */     
/*     */ 
/* 102 */     List<Integer> todayIds = this.rankingDao.getIds(2, today, tomorrow);
/*     */     
/*     */ 
/* 105 */     int currentSize = todayIds.size();
/* 106 */     UserBetsHitRanking ranking; if (currentSize < hitRankingSize) {
/* 107 */       for (int i = 0; i < adds.size(); i++) {
/* 108 */         if (currentSize >= hitRankingSize) {
/*     */           break;
/*     */         }
/*     */         
/* 112 */         UserBets userBets = (UserBets)adds.get(i);
/* 113 */         ranking = convert(userBets);
/* 114 */         this.rankingDao.add(ranking);
/* 115 */         currentSize++;
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 120 */       List<Integer> idsByTimeDesc = this.rankingDao.getIdsByTimeDesc(hitRankingSize, 2);
/* 121 */       if (CollectionUtils.isNotEmpty(idsByTimeDesc)) {
/* 122 */         this.rankingDao.delNotInIds(idsByTimeDesc, 2);
/*     */       }
/*     */       
/* 125 */       this.dbServerSyncDao.update(DbServerSyncEnum.HIT_RANKING);
/*     */       
/* 127 */       log.info("中奖排行榜更新完成");
/*     */     }
/*     */     else
/*     */     {
/* 131 */       UserBetsHitRanking minRanking = this.rankingDao.getMinRanking(2, today, tomorrow);
/* 132 */       int addedCount = 0;
/* 133 */       for (UserBets add : adds) {
/* 134 */         boolean allowAdd = false;
/* 135 */         if (minRanking == null) {
/* 136 */           allowAdd = true;
/*     */         }
/*     */         else {
/* 139 */           BigDecimal prize = BigDecimal.valueOf(add.getPrizeMoney().doubleValue());
/* 140 */           int prizeMoney = prize.intValue();
/* 141 */           if (prizeMoney > minRanking.getPrizeMoney()) {
/* 142 */             allowAdd = true;
/*     */           }
/*     */         }
/*     */         
/* 146 */         if (allowAdd) {
/* 147 */            ranking = convert(add);
/* 148 */           this.rankingDao.add(ranking);
/* 149 */           addedCount++;
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 154 */       if (addedCount > 0) {
/* 155 */         List<Integer> totalIds = this.rankingDao.getTotalIds(hitRankingSize, 2, today, tomorrow);
/* 156 */         if (CollectionUtils.isNotEmpty(totalIds)) {
/* 157 */           this.rankingDao.delNotInIds(totalIds, 2, today, tomorrow);
/*     */         }
/*     */         
/*     */ 
/* 161 */         List<Integer> idsByTimeDesc = this.rankingDao.getIdsByTimeDesc(hitRankingSize, 2);
/* 162 */         if (CollectionUtils.isNotEmpty(idsByTimeDesc)) {
/* 163 */           this.rankingDao.delNotInIds(idsByTimeDesc, 2);
/*     */         }
/*     */         
/* 166 */         this.dbServerSyncDao.update(DbServerSyncEnum.HIT_RANKING);
/*     */         
/* 168 */         log.info("中奖排行榜更新完成");
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private UserBetsHitRanking convert(UserBets userBets)
/*     */   {
/* 175 */     Lottery lottery = this.dataFactory.getLottery(userBets.getLotteryId());
/* 176 */     if (lottery == null) {
/* 177 */       return null;
/*     */     }
/*     */     
/* 180 */     User user = this.uDao.getById(userBets.getUserId());
/* 181 */     if (user == null) {
/* 182 */       return null;
/*     */     }
/*     */     
/* 185 */     UserBetsHitRanking ranking = new UserBetsHitRanking();
/* 186 */     ranking.setName(lottery.getShowName());
/* 187 */     ranking.setUsername(user.getUsername());
/* 188 */     BigDecimal prize = BigDecimal.valueOf(userBets.getPrizeMoney().doubleValue());
/* 189 */     ranking.setPrizeMoney(prize.intValue());
/* 190 */     ranking.setTime(userBets.getPrizeTime());
/* 191 */     ranking.setPlatform(2);
/* 192 */     ranking.setCode(lottery.getShortName());
/* 193 */     ranking.setType(lottery.getType() + "");
/*     */     
/* 195 */     return ranking;
/*     */   }
/*     */   
/*     */   public void addIfNecessary(UserBets userBets)
/*     */   {
/* 200 */     if ((userBets.getPrizeMoney() != null) && (userBets.getPrizeMoney().doubleValue() >= 50000.0D)) {
/* 201 */       this.userBetsQueue.offer(userBets);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/content/biz/impl/UserBetsHitRankingServiceImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */