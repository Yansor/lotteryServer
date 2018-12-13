/*    */ package lottery.domains.content.dao.impl;
/*    */ 
/*    */ import java.util.List;
/*    */ import javautils.jdbc.hibernate.HibernateSuperDao;
/*    */ import lottery.domains.content.dao.UserDao;
/*    */ import lottery.domains.content.entity.User;
/*    */ import org.hibernate.criterion.Criterion;
/*    */ import org.hibernate.criterion.Order;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Repository;
/*    */ 
/*    */ 
/*    */ @Repository
/*    */ public class UserDaoImpl
/*    */   implements UserDao
/*    */ {
/* 17 */   public static final String tab = User.class.getSimpleName();
/*    */   
/*    */   @Autowired
/*    */   private HibernateSuperDao<User> superDao;
/*    */   
/*    */   public List<User> list(List<Criterion> criterions, List<Order> orders)
/*    */   {
/* 24 */     return this.superDao.findByCriteria(User.class, criterions, orders);
/*    */   }
/*    */   
/*    */   public List<User> listAll()
/*    */   {
/* 29 */     String hql = "from " + tab;
/* 30 */     return this.superDao.list(hql);
/*    */   }
/*    */   
/*    */   public boolean updateLotteryMoney(int id, double lotteryAmount, double freezeAmount)
/*    */   {
/* 35 */     String hql = "update " + tab + " set lotteryMoney = lotteryMoney + ?1, freezeMoney = freezeMoney + ?2 where id = ?0";
/* 36 */     if (lotteryAmount < 0.0D) {
/* 37 */       hql = hql + " and lotteryMoney >= " + -lotteryAmount;
/*    */     }
/* 39 */     Object[] values = { Integer.valueOf(id), Double.valueOf(lotteryAmount), Double.valueOf(freezeAmount) };
/* 40 */     return this.superDao.update(hql, values);
/*    */   }
/*    */   
/*    */   public boolean updateLotteryMoney(int id, double lotteryAmount)
/*    */   {
/* 45 */     String hql = "update " + tab + " set lotteryMoney = lotteryMoney + ?1 where id = ?0";
/* 46 */     if (lotteryAmount < 0.0D) {
/* 47 */       hql = hql + " and lotteryMoney >= " + -lotteryAmount;
/*    */     }
/* 49 */     Object[] values = { Integer.valueOf(id), Double.valueOf(lotteryAmount) };
/* 50 */     return this.superDao.update(hql, values);
/*    */   }
/*    */   
/*    */   public boolean updateFreezeMoney(int id, double freezeAmount)
/*    */   {
/* 55 */     String hql = "update " + tab + " set freezeMoney = freezeMoney + ?1 where id = ?0";
/* 56 */     if (freezeAmount < 0.0D) {
/* 57 */       hql = hql + " and freezeMoney >= " + -freezeAmount;
/*    */     }
/* 59 */     Object[] values = { Integer.valueOf(id), Double.valueOf(freezeAmount) };
/* 60 */     return this.superDao.update(hql, values);
/*    */   }
/*    */   
/*    */   public User getById(int id)
/*    */   {
/* 65 */     String hql = "from " + tab + " where id = ?0";
/* 66 */     Object[] values = { Integer.valueOf(id) };
/* 67 */     return (User)this.superDao.unique(hql, values);
/*    */   }
/*    */   
/*    */   public int clearUserNegateFreezeMoney()
/*    */   {
/* 72 */     String hql = "update " + tab + " set freezeMoney = 0 where freezeMoney < 0";
/* 73 */     return this.superDao.updateByHql(hql);
/*    */   }
/*    */   
/*    */   public List<User> getUserLower(int id)
/*    */   {
/* 78 */     String hql = "from " + tab + " where upids like ?0";
/* 79 */     Object[] values = { "%[" + id + "]%" };
/* 80 */     return this.superDao.list(hql, values);
/*    */   }
/*    */   
/*    */   public List<User> getUserDirectLower(int id)
/*    */   {
/* 85 */     String hql = "from " + tab + " where upid = ?0";
/* 86 */     Object[] values = { Integer.valueOf(id) };
/* 87 */     return this.superDao.list(hql, values);
/*    */   }
/*    */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/content/dao/impl/UserDaoImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */