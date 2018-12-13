/*    */ package lottery.domains.content.biz.impl;
/*    */ 
/*    */ import java.util.List;
/*    */ import lottery.domains.content.biz.LotteryPlayRulesGroupService;
/*    */ import lottery.domains.content.dao.LotteryPlayRulesGroupDao;
/*    */ import lottery.domains.content.entity.LotteryPlayRulesGroup;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Service;
/*    */ import org.springframework.transaction.annotation.Transactional;
/*    */ 
/*    */ @Service
/*    */ public class LotteryPlayRulesGroupServiceImpl
/*    */   implements LotteryPlayRulesGroupService
/*    */ {
/*    */   @Autowired
/*    */   private LotteryPlayRulesGroupDao lotteryPlayRulesGroupDao;
/*    */   
/*    */   @Transactional(readOnly=true)
/*    */   public List<LotteryPlayRulesGroup> listAll()
/*    */   {
/* 21 */     return this.lotteryPlayRulesGroupDao.listAll();
/*    */   }
/*    */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/content/biz/impl/LotteryPlayRulesGroupServiceImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */