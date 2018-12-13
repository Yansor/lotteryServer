/*     */ package lottery.domains.open.jobs;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import javautils.date.Moment;
/*     */ import lottery.domains.content.dao.LotteryOpenCodeDao;
/*     */ import lottery.domains.content.entity.Lottery;
/*     */ import lottery.domains.content.entity.LotteryOpenCode;
/*     */ import lottery.domains.utils.open.LotteryOpenUtil;
/*     */ import lottery.domains.utils.open.OpenTime;
/*     */ import org.apache.commons.collections.CollectionUtils;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.scheduling.annotation.Scheduled;
/*     */ import org.springframework.stereotype.Component;
/*     */ 
/*     */ 
/*     */ @Component
/*     */ public class SelfLotteryBeforeOpenCodeJob
/*     */   extends AbstractSelfLotteryOpenCodeJob
/*     */ {
/*  26 */   private static final Logger log = LoggerFactory.getLogger(SelfLotteryBeforeOpenCodeJob.class);
/*  27 */   private static boolean isRuning = false;
/*     */   
/*     */   private static final int CHECK_DAYS = 1;
/*     */   @Autowired
/*     */   private LotteryOpenCodeDao openCodeDao;
/*     */   
/*     */   @Scheduled(cron="0 0/10 * * * ?")
/*     */   public void generate()
/*     */   {
/*  36 */     synchronized (SelfLotteryBeforeOpenCodeJob.class) {
/*  37 */       if (isRuning == true) {
/*  38 */         return;
/*     */       }
/*  40 */       isRuning = true;
/*     */     }
/*     */     
/*     */     try
/*     */     {
/*  45 */       List<Lottery> selfLotteries = getSelfLotteries();
/*  46 */       generate(selfLotteries);
/*     */     } catch (Exception e) {
/*  48 */       log.error("自主彩生成开奖号码异常", e);
/*     */     } finally {
/*  50 */       isRuning = false;
/*     */     }
/*     */   }
/*     */   
/*     */   private void generate(List<Lottery> selfLotteries) {
/*  55 */     if (CollectionUtils.isEmpty(selfLotteries)) {
/*  56 */       return;
/*     */     }
/*     */     
/*  59 */     for (Lottery selfLottery : selfLotteries) {
/*  60 */       generateBefore(selfLottery);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private void generateBefore(Lottery selfLottery)
/*     */   {
/*  68 */     OpenTime lastOpenTime = this.lotteryOpenUtil.getLastOpenTime(selfLottery.getId());
/*  69 */     if (lastOpenTime == null) {
/*  70 */       return;
/*     */     }
/*     */     
/*  73 */     String lastExpect = lastOpenTime.getExpect();
/*     */     
/*     */ 
/*  76 */     HashSet<String> beforeExpects = new HashSet();
/*     */     OpenTime openTime;
/*  78 */     if (selfLottery.getType() == 4)
/*     */     {
/*  80 */        openTime = this.lotteryOpenUtil.substractOneExpect(selfLottery.getId(), lastExpect);
/*  81 */       if (openTime != null) {
/*  82 */         beforeExpects.add(openTime.getExpect());
/*     */       }
/*     */     }
/*     */     else {
/*     */       Iterator localIterator;
/*  87 */       for (int i = 0; i < 2; i++) {
/*  88 */         Moment moment = new Moment().add(-i, "days");
/*     */         
/*  90 */         List<OpenTime> openTimeList = this.lotteryOpenUtil.getOpenDateList(selfLottery.getId(), moment.toSimpleDate());
/*  91 */         for (localIterator = openTimeList.iterator(); localIterator.hasNext();) { openTime = (OpenTime)localIterator.next();
/*     */           
/*  93 */           if (openTime.getExpect().compareTo(lastExpect) < 0)
/*     */           {
/*  95 */             beforeExpects.add(openTime.getExpect());
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 102 */     Object[] obj = beforeExpects.toArray();
/* 103 */     Arrays.sort(obj);
/* 104 */     String minExpect = obj[0].toString();
/*     */     
/*     */ 
/* 107 */     List<LotteryOpenCode> beforeOpenCodes = this.openCodeDao.listAfter(selfLottery.getShortName(), minExpect);
/*     */     
/* 109 */     Object savedExpects = new HashSet();
/* 110 */     for (LotteryOpenCode beforeOpenCode : beforeOpenCodes) {
/* 111 */       ((HashSet)savedExpects).add(beforeOpenCode.getExpect());
/*     */     }
/*     */     
/*     */ 
/* 115 */     beforeExpects.removeAll((Collection)savedExpects);
/*     */     
/* 117 */     for (String beforeExpect : beforeExpects) {
/* 118 */       long start = System.currentTimeMillis();
/*     */       
/* 120 */       LotteryOpenCode openCode = generateOpenCode(selfLottery, beforeExpect, null);
/* 121 */       if (openCode != null) {
/* 122 */         long spend = System.currentTimeMillis() - start;
/* 123 */         log.debug("补开{}第{}期开奖号码{},耗时{}", new Object[] { selfLottery.getShowName(), beforeExpect, openCode.getCode(), Long.valueOf(spend) });
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/open/jobs/SelfLotteryBeforeOpenCodeJob.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */