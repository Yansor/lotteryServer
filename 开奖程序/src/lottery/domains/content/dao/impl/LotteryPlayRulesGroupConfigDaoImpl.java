/*    */ package lottery.domains.content.dao.impl;
/*    */ 
/*    */ import java.util.List;
/*    */ import javautils.jdbc.hibernate.HibernateSuperDao;
/*    */ import lottery.domains.content.dao.LotteryPlayRulesGroupConfigDao;
/*    */ import lottery.domains.content.entity.LotteryPlayRulesGroupConfig;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Repository;
/*    */ 
/*    */ @Repository
/*    */ public class LotteryPlayRulesGroupConfigDaoImpl
/*    */   implements LotteryPlayRulesGroupConfigDao
/*    */ {
/* 14 */   private final String tab = LotteryPlayRulesGroupConfig.class.getSimpleName();
/*    */   
/*    */   @Autowired
/*    */   private HibernateSuperDao<LotteryPlayRulesGroupConfig> superDao;
/*    */   
/*    */   public List<LotteryPlayRulesGroupConfig> listAll()
/*    */   {
/* 21 */     String hql = "from " + this.tab + " order by id";
/* 22 */     return this.superDao.list(hql);
/*    */   }
/*    */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/content/dao/impl/LotteryPlayRulesGroupConfigDaoImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */