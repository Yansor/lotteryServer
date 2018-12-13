/*    */ package lottery.domains.content.dao.impl;
/*    */ 
/*    */ import java.util.List;
/*    */ import javautils.jdbc.hibernate.HibernateSuperDao;
/*    */ import lottery.domains.content.dao.LotteryPlayRulesConfigDao;
/*    */ import lottery.domains.content.entity.LotteryPlayRulesConfig;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Repository;
/*    */ 
/*    */ @Repository
/*    */ public class LotteryPlayRulesConfigDaoImpl
/*    */   implements LotteryPlayRulesConfigDao
/*    */ {
/* 14 */   private final String tab = LotteryPlayRulesConfig.class.getSimpleName();
/*    */   
/*    */   @Autowired
/*    */   private HibernateSuperDao<LotteryPlayRulesConfig> superDao;
/*    */   
/*    */   public List<LotteryPlayRulesConfig> listAll()
/*    */   {
/* 21 */     String hql = "from " + this.tab;
/* 22 */     return this.superDao.list(hql);
/*    */   }
/*    */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/content/dao/impl/LotteryPlayRulesConfigDaoImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */