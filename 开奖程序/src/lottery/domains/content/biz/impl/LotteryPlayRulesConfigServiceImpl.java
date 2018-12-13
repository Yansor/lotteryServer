/*    */ package lottery.domains.content.biz.impl;
/*    */ 
/*    */ import java.util.List;
/*    */ import lottery.domains.content.biz.LotteryPlayRulesConfigService;
/*    */ import lottery.domains.content.dao.LotteryPlayRulesConfigDao;
/*    */ import lottery.domains.content.entity.LotteryPlayRulesConfig;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Service;
/*    */ import org.springframework.transaction.annotation.Transactional;
/*    */ 
/*    */ @Service
/*    */ public class LotteryPlayRulesConfigServiceImpl
/*    */   implements LotteryPlayRulesConfigService
/*    */ {
/*    */   @Autowired
/*    */   private LotteryPlayRulesConfigDao lotteryPlayRulesConfigDao;
/*    */   
/*    */   @Transactional(readOnly=true)
/*    */   public List<LotteryPlayRulesConfig> listAll()
/*    */   {
/* 21 */     return this.lotteryPlayRulesConfigDao.listAll();
/*    */   }
/*    */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/content/biz/impl/LotteryPlayRulesConfigServiceImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */