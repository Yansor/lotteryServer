/*    */ package lottery.domains.open.jobs;
/*    */ 
/*    */ import java.util.List;
/*    */ import lottery.domains.content.biz.LotteryOpenCodeService;
/*    */ import lottery.domains.content.entity.Lottery;
/*    */ import lottery.domains.content.entity.LotteryOpenCode;
/*    */ import lottery.domains.utils.open.LotteryOpenUtil;
/*    */ import lottery.domains.utils.open.OpenTime;
/*    */ import org.apache.commons.collections.CollectionUtils;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ import org.springframework.scheduling.annotation.Scheduled;
/*    */ import org.springframework.stereotype.Component;
/*    */ 
/*    */ 
/*    */ @Component
/*    */ public class SelfLotteryLastOpenCodeJob
/*    */   extends AbstractSelfLotteryOpenCodeJob
/*    */ {
/* 20 */   private static final Logger log = LoggerFactory.getLogger(SelfLotteryLastOpenCodeJob.class);
/*    */   
/* 22 */   private static boolean isRuning = false;
/*    */   
/*    */   @Scheduled(cron="5,35 * * * * *")
/*    */   public void generate() {
/* 26 */     synchronized (SelfLotteryLastOpenCodeJob.class) {
/* 27 */       if (isRuning == true) {
/* 28 */         return;
/*    */       }
/* 30 */       isRuning = true;
/*    */     }
/*    */     try
/*    */     {
/* 34 */       List<Lottery> selfLotteries = getSelfLotteries();
/* 35 */       generate(selfLotteries);
/*    */     } catch (Exception e) {
/* 37 */       log.error("自主彩生成开奖号码异常", e);
/*    */     } finally {
/* 39 */       isRuning = false;
/*    */     }
/*    */   }
/*    */   
/*    */   private void generate(List<Lottery> selfLotteries) {
/* 44 */     if (CollectionUtils.isEmpty(selfLotteries)) {
/* 45 */       return;
/*    */     }
/*    */     
/* 48 */     for (Lottery selfLottery : selfLotteries) {
/* 49 */       generateLast(selfLottery);
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   private void generateLast(Lottery selfLottery)
/*    */   {
/* 57 */     OpenTime lastOpenTime = this.lotteryOpenUtil.getLastOpenTime(selfLottery.getId());
/* 58 */     if (lastOpenTime == null) {
/* 59 */       return;
/*    */     }
/* 61 */     String lastExpect = lastOpenTime.getExpect();
/*    */     
/*    */ 
/* 64 */     LotteryOpenCode lastOpenCode = this.lotteryOpenCodeService.getByExcept(selfLottery.getShortName(), lastExpect);
/*    */     
/* 66 */     if (lastOpenCode != null) {
/* 67 */       return;
/*    */     }
/*    */     
/* 70 */     long start = System.currentTimeMillis();
/*    */     
/* 72 */     LotteryOpenCode openCode = generateOpenCode(selfLottery, lastExpect, null);
/* 73 */     if (openCode != null) {
/* 74 */       long spend = System.currentTimeMillis() - start;
/* 75 */       log.debug("{}第{}期生成开奖号码为{},耗时{}", new Object[] { selfLottery.getShowName(), lastExpect, openCode.getCode(), Long.valueOf(spend) });
/*    */     }
/*    */     else {
/* 78 */       log.error("{}第{}期生成开奖号码为失败", selfLottery.getShowName(), lastExpect);
/*    */     }
/*    */   }
/*    */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/open/jobs/SelfLotteryLastOpenCodeJob.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */