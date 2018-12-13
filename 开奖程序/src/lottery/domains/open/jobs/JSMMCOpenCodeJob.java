/*     */ package lottery.domains.open.jobs;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javautils.date.Moment;
/*     */ import lottery.domains.content.biz.LotteryOpenCodeService;
/*     */ import lottery.domains.content.dao.UserBetsDao;
/*     */ import lottery.domains.content.entity.Lottery;
/*     */ import lottery.domains.content.entity.LotteryOpenCode;
/*     */ import lottery.domains.content.entity.UserBets;
/*     */ import lottery.domains.pool.DataFactory;
/*     */ import org.apache.commons.collections.CollectionUtils;
/*     */ import org.hibernate.criterion.Criterion;
/*     */ import org.hibernate.criterion.Restrictions;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.scheduling.annotation.Scheduled;
/*     */ import org.springframework.stereotype.Component;
/*     */ 
/*     */ 
/*     */ 
/*     */ @Component
/*     */ public class JSMMCOpenCodeJob
/*     */   extends AbstractSelfLotteryOpenCodeJob
/*     */ {
/*  29 */   private static final Logger log = LoggerFactory.getLogger(JSMMCOpenCodeJob.class);
/*  30 */   private static boolean isRuning = false;
/*     */   @Autowired
/*     */   protected LotteryOpenCodeService lotteryOpenCodeService;
/*     */   
/*     */   @Scheduled(cron="0/5 * * * * *")
/*     */   public void generate()
/*     */   {
/*  37 */     synchronized (JSMMCOpenCodeJob.class) {
/*  38 */       if (isRuning == true) {
/*  39 */         return;
/*     */       }
/*  41 */       isRuning = true;
/*     */     }
/*     */     
/*     */     try
/*     */     {
/*  46 */       doGenerate();
/*     */     } catch (Exception e) {
/*  48 */       log.error("自主彩生成开奖号码异常", e);
/*     */     } finally {
/*  50 */       isRuning = false;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private void doGenerate()
/*     */   {
/*  58 */     Lottery lottery = this.dataFactory.getLottery(117);
/*  59 */     if ((lottery == null) || (lottery.getStatus() != 0)) {
/*  60 */       return;
/*     */     }
/*     */     
/*     */ 
/*  64 */     List<UserBets> userBets = getUserBets();
/*     */     
/*     */ 
/*  67 */     if (CollectionUtils.isEmpty(userBets)) {
/*  68 */       return;
/*     */     }
/*     */     
/*     */ 
/*  72 */     Map<String, List<UserBets>> group = group(userBets);
/*     */     
/*  74 */     for (List<UserBets> userBetsList : group.values()) {
/*  75 */       doGenerateByUserBets(userBetsList);
/*     */     }
/*     */   }
/*     */   
/*     */   private Map<String, List<UserBets>> group(List<UserBets> userBets) {
/*  80 */     Map<String, List<UserBets>> group = new HashMap();
/*  81 */     for (UserBets userBet : userBets) {
/*  82 */       String key = userBet.getUserId() + "_" + userBet.getExpect();
/*     */       
/*  84 */       if (!group.containsKey(key)) {
/*  85 */         group.put(key, new ArrayList());
/*     */       }
/*     */       
/*  88 */       ((List)group.get(key)).add(userBet);
/*     */     }
/*     */     
/*  91 */     return group;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private void doGenerateByUserBets(List<UserBets> userBets)
/*     */   {
/*  99 */     UserBets userBet = (UserBets)userBets.get(0);
/* 100 */     Lottery lottery = this.dataFactory.getLottery(userBet.getLotteryId());
/* 101 */     int userId = userBet.getUserId();
/* 102 */     String expect = userBet.getExpect();
/*     */     
/* 104 */     LotteryOpenCode openCode = this.lotteryOpenCodeService.getByExceptAndUserId(lottery.getShortName(), userId, expect);
/* 105 */     if (openCode != null) {
/* 106 */       return;
/*     */     }
/*     */     
/* 109 */     generateOpenCode(lottery, expect, Integer.valueOf(userId));
/*     */   }
/*     */   
/*     */   private List<UserBets> getUserBets() {
/* 113 */     List<Criterion> criterions = new ArrayList();
/* 114 */     criterions.add(Restrictions.eq("status", Integer.valueOf(0)));
/* 115 */     criterions.add(Restrictions.eq("lotteryId", Integer.valueOf(117)));
/* 116 */     criterions.add(Restrictions.gt("id", Integer.valueOf(0)));
/*     */     
/*     */ 
/* 119 */     String start = new Moment().add(-2, "hours").toSimpleTime();
/* 120 */     String end = new Moment().add(-2, "seconds").toSimpleTime();
/* 121 */     criterions.add(Restrictions.ge("time", start));
/* 122 */     criterions.add(Restrictions.le("time", end));
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 129 */     List<UserBets> betsList = this.userBetsDao.list(criterions, null);
/*     */     
/* 131 */     return betsList;
/*     */   }
/*     */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/open/jobs/JSMMCOpenCodeJob.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */