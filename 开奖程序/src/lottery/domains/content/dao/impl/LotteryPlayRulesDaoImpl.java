/*    */ package lottery.domains.content.dao.impl;
/*    */ 
/*    */ import java.util.List;
/*    */ import javautils.jdbc.hibernate.HibernateSuperDao;
/*    */ import lottery.domains.content.dao.LotteryPlayRulesDao;
/*    */ import lottery.domains.content.entity.LotteryPlayRules;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Repository;
/*    */ 
/*    */ @Repository
/*    */ public class LotteryPlayRulesDaoImpl
/*    */   implements LotteryPlayRulesDao
/*    */ {
/* 14 */   private final String tab = LotteryPlayRules.class.getSimpleName();
/*    */   
/*    */   @Autowired
/*    */   private HibernateSuperDao<LotteryPlayRules> superDao;
/*    */   
/*    */   public List<LotteryPlayRules> listAll()
/*    */   {
/* 21 */     String hql = "from " + this.tab + " order by typeId, groupId";
/* 22 */     return this.superDao.list(hql);
/*    */   }
/*    */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/content/dao/impl/LotteryPlayRulesDaoImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */