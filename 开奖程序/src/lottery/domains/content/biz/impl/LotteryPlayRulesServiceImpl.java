/*    */ package lottery.domains.content.biz.impl;
/*    */ 
/*    */ import java.util.List;
/*    */ import lottery.domains.content.biz.LotteryPlayRulesService;
/*    */ import lottery.domains.content.dao.LotteryPlayRulesDao;
/*    */ import lottery.domains.content.entity.LotteryPlayRules;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Service;
/*    */ import org.springframework.transaction.annotation.Transactional;
/*    */ 
/*    */ @Service
/*    */ public class LotteryPlayRulesServiceImpl
/*    */   implements LotteryPlayRulesService
/*    */ {
/*    */   @Autowired
/*    */   private LotteryPlayRulesDao lotteryPlayRulesDao;
/*    */   
/*    */   @Transactional(readOnly=true)
/*    */   public List<LotteryPlayRules> listAll()
/*    */   {
/* 21 */     return this.lotteryPlayRulesDao.listAll();
/*    */   }
/*    */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/content/biz/impl/LotteryPlayRulesServiceImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */