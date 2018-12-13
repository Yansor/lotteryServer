/*     */ package lottery.domains.open.jobs;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import javautils.date.Moment;
/*     */ import lottery.domains.content.biz.LotteryOpenCodeService;
/*     */ import lottery.domains.content.biz.UserBetsSettleService;
/*     */ import lottery.domains.content.dao.UserBetsDao;
/*     */ import lottery.domains.content.entity.Lottery;
/*     */ import lottery.domains.content.entity.LotteryOpenCode;
/*     */ import lottery.domains.content.entity.UserBets;
/*     */ import lottery.domains.pool.DataFactory;
/*     */ import lottery.domains.utils.open.LotteryOpenUtil;
/*     */ import org.apache.commons.lang.StringUtils;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ @Component
/*     */ public class JSMMCLotteryOpenJob
/*     */ {
/*  35 */   private static final Logger log = LoggerFactory.getLogger(JSMMCLotteryOpenJob.class);
/*  36 */   private static boolean isRuning = false;
/*     */   @Autowired
/*     */   protected UserBetsDao userBetsDao;
/*     */   @Autowired
/*     */   protected LotteryOpenCodeService lotteryOpenCodeService;
/*     */   @Autowired
/*     */   protected UserBetsSettleService userBetsSettleService;
/*     */   @Autowired
/*     */   protected DataFactory dataFactory;
/*     */   @Autowired
/*     */   protected LotteryOpenUtil lotteryOpenUtil;
/*     */   
/*     */   @Scheduled(cron="0/3 * * * * *")
/*     */   public void openUserBets() {
/*  50 */     synchronized (JSMMCLotteryOpenJob.class) {
/*  51 */       if (isRuning == true) {
/*  52 */         return;
/*     */       }
/*  54 */       isRuning = true;
/*     */     }
/*     */     try
/*     */     {
/*  58 */       long start = System.currentTimeMillis();
/*     */       
/*     */ 
/*  61 */       int total = open();
/*  62 */       long spend = System.currentTimeMillis() - start;
/*     */       
/*  64 */       if (total > 0) {
/*  65 */         log.debug("急速秒秒彩开奖完成,共计开奖" + total + "条注单,耗时" + spend);
/*     */       }
/*     */     } catch (Exception e) {
/*  68 */       log.error("急速秒秒彩开奖异常", e);
/*     */     }
/*     */     finally {
/*  71 */       isRuning = false;
/*     */     }
/*     */   }
/*     */   
/*     */   private int open() {
/*  76 */     Lottery lottery = this.dataFactory.getLottery(117);
/*  77 */     if ((lottery == null) || (lottery.getStatus() != 0)) {
/*  78 */       return 0;
/*     */     }
/*  80 */     int total = openByLottery(lottery);
/*  81 */     return total;
/*     */   }
/*     */   
/*     */   private int openByLottery(Lottery lottery) {
/*  85 */     int total = 0;
/*     */     try
/*     */     {
/*  88 */       List<UserBets> userBetses = getUserBetsNotOpen(lottery.getId());
/*     */       
/*     */ 
/*  91 */       Map<LotteryOpenCode, List<UserBets>> groupByOpenCodes = groupUserBetsByOpenCode(lottery, userBetses);
/*  92 */       if (groupByOpenCodes.isEmpty()) {
/*  93 */         return 0;
/*     */       }
/*     */       
/*  96 */       for (Entry<LotteryOpenCode, List<UserBets>> groupByOpenCode : groupByOpenCodes.entrySet()) {
/*  97 */         LotteryOpenCode openCode = (LotteryOpenCode)groupByOpenCode.getKey();
/*  98 */         this.userBetsSettleService.settleUserBets((List)groupByOpenCode.getValue(), openCode, lottery);
/*  99 */         total += ((List)groupByOpenCode.getValue()).size();
/*     */       }
/*     */     } catch (Exception e) {
/* 102 */       log.error("急速秒秒彩开奖异常:" + lottery.getShowName(), e);
/*     */     }
/* 104 */     return total;
/*     */   }
/*     */   
/*     */ 
/*     */   private Map<LotteryOpenCode, List<UserBets>> groupUserBetsByOpenCode(Lottery lottery, List<UserBets> userBetses)
/*     */   {
/* 110 */     Map<String, List<UserBets>> groupByExpect = new HashMap();
/* 111 */     for (UserBets userBet : userBetses) {
/* 112 */       String key = userBet.getUserId() + "_" + userBet.getExpect();
/*     */       
/* 114 */       if (!groupByExpect.containsKey(key)) {
/* 115 */         groupByExpect.put(key, new ArrayList());
/*     */       }
/*     */       
/* 118 */       ((List)groupByExpect.get(key)).add(userBet);
/*     */     }
/*     */     String key;
/* 121 */     Object groupByCode = new HashMap();
/* 122 */     Map<String, LotteryOpenCode> expectCodes = new HashMap();
/*     */     
/* 124 */     for (Entry<String, List<UserBets>> entry : groupByExpect.entrySet())
/* 125 */       if (!expectCodes.containsKey(entry.getKey())) {
/* 126 */         String[] split = ((String)entry.getKey()).split("_");
/* 127 */         int userId = Integer.valueOf(split[0]).intValue();
/* 128 */         String expect = String.valueOf(split[1]);
/*     */         
/* 130 */         LotteryOpenCode lotteryOpenCode = this.lotteryOpenCodeService.getByExceptAndUserId(lottery.getShortName(), userId, expect);
/* 131 */         if (lotteryOpenCode != null)
/*     */         {
/*     */ 
/*     */ 
/* 135 */           expectCodes.put(entry.getKey(), lotteryOpenCode);
/*     */         }
/*     */       }
/*     */       else {
/* 139 */         LotteryOpenCode openCode = (LotteryOpenCode)expectCodes.get(entry.getKey());
/* 140 */         if ((openCode != null) && (!StringUtils.isEmpty(openCode.getCode())))
/*     */         {
/*     */ 
/*     */ 
/* 144 */           if (!((Map)groupByCode).containsKey(openCode)) {
/* 145 */             ((Map)groupByCode).put(openCode, new ArrayList());
/*     */           }
/*     */           
/* 148 */           ((List)((Map)groupByCode).get(openCode)).addAll((Collection)entry.getValue());
/*     */         }
/*     */       }
/* 151 */     return (Map<LotteryOpenCode, List<UserBets>>)groupByCode;
/*     */   }
/*     */   
/*     */   private List<UserBets> getUserBetsNotOpen(int lotteryId)
/*     */   {
/* 156 */     List<Criterion> criterions = new ArrayList();
/* 157 */     criterions.add(Restrictions.eq("status", Integer.valueOf(0)));
/* 158 */     criterions.add(Restrictions.eq("lotteryId", Integer.valueOf(lotteryId)));
/* 159 */     criterions.add(Restrictions.gt("id", Integer.valueOf(0)));
/*     */     
/*     */ 
/* 162 */     String start = new Moment().add(-2, "hours").toSimpleTime();
/* 163 */     String end = new Moment().add(-2, "seconds").toSimpleTime();
/* 164 */     criterions.add(Restrictions.ge("time", start));
/* 165 */     criterions.add(Restrictions.le("time", end));
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 172 */     List<UserBets> betsList = this.userBetsDao.list(criterions, null);
/*     */     
/* 174 */     return betsList;
/*     */   }
/*     */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/open/jobs/JSMMCLotteryOpenJob.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */