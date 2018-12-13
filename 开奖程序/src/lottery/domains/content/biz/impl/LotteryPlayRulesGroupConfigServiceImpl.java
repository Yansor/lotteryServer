/*    */ package lottery.domains.content.biz.impl;
/*    */ 
/*    */ import java.util.List;
/*    */ import lottery.domains.content.biz.LotteryPlayRulesGroupConfigService;
/*    */ import lottery.domains.content.dao.LotteryPlayRulesGroupConfigDao;
/*    */ import lottery.domains.content.entity.LotteryPlayRulesGroupConfig;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Service;
/*    */ import org.springframework.transaction.annotation.Transactional;
/*    */ 
/*    */ @Service
/*    */ public class LotteryPlayRulesGroupConfigServiceImpl
/*    */   implements LotteryPlayRulesGroupConfigService
/*    */ {
/*    */   @Autowired
/*    */   private LotteryPlayRulesGroupConfigDao lotteryPlayRulesGroupConfigDao;
/*    */   
/*    */   @Transactional(readOnly=true)
/*    */   public List<LotteryPlayRulesGroupConfig> listAll()
/*    */   {
/* 21 */     return this.lotteryPlayRulesGroupConfigDao.listAll();
/*    */   }
/*    */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/content/biz/impl/LotteryPlayRulesGroupConfigServiceImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */