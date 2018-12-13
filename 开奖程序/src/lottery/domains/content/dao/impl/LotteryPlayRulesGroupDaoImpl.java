/*    */ package lottery.domains.content.dao.impl;
/*    */ 
/*    */ import java.util.List;
/*    */ import javautils.jdbc.hibernate.HibernateSuperDao;
/*    */ import lottery.domains.content.dao.LotteryPlayRulesGroupDao;
/*    */ import lottery.domains.content.entity.LotteryPlayRulesGroup;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Repository;
/*    */ 
/*    */ @Repository
/*    */ public class LotteryPlayRulesGroupDaoImpl
/*    */   implements LotteryPlayRulesGroupDao
/*    */ {
/* 14 */   private final String tab = LotteryPlayRulesGroup.class.getSimpleName();
/*    */   
/*    */   @Autowired
/*    */   private HibernateSuperDao<LotteryPlayRulesGroup> superDao;
/*    */   
/*    */   public List<LotteryPlayRulesGroup> listAll()
/*    */   {
/* 21 */     String hql = "from " + this.tab + " order by typeId,sort";
/* 22 */     return this.superDao.list(hql);
/*    */   }
/*    */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/content/dao/impl/LotteryPlayRulesGroupDaoImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */