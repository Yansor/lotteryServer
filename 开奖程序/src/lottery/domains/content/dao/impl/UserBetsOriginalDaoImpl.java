/*    */ package lottery.domains.content.dao.impl;
/*    */ 
/*    */ import javautils.jdbc.hibernate.HibernateSuperDao;
/*    */ import lottery.domains.content.dao.UserBetsOriginalDao;
/*    */ import lottery.domains.content.entity.UserBetsOriginal;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Repository;
/*    */ 
/*    */ 
/*    */ @Repository
/*    */ public class UserBetsOriginalDaoImpl
/*    */   implements UserBetsOriginalDao
/*    */ {
/* 14 */   private final String tab = UserBetsOriginal.class.getSimpleName();
/*    */   
/*    */   @Autowired
/*    */   private HibernateSuperDao<UserBetsOriginal> superDao;
/*    */   
/*    */   public boolean add(UserBetsOriginal original)
/*    */   {
/* 21 */     return this.superDao.save(original);
/*    */   }
/*    */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/content/dao/impl/UserBetsOriginalDaoImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */