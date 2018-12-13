/*    */ package lottery.domains.content.dao.impl;
/*    */ 
/*    */ import javautils.jdbc.hibernate.HibernateSuperDao;
/*    */ import lottery.domains.content.dao.UserHighPrizeDao;
/*    */ import lottery.domains.content.entity.UserHighPrize;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Repository;
/*    */ 
/*    */ 
/*    */ @Repository
/*    */ public class UserHighPrizeDaoImpl
/*    */   implements UserHighPrizeDao
/*    */ {
/* 14 */   private final String tab = UserHighPrize.class.getSimpleName();
/*    */   
/*    */   @Autowired
/*    */   private HibernateSuperDao<UserHighPrize> superDao;
/*    */   
/*    */ 
/*    */   public boolean add(UserHighPrize entity)
/*    */   {
/* 22 */     return this.superDao.save(entity);
/*    */   }
/*    */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/content/dao/impl/UserHighPrizeDaoImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */