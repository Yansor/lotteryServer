/*    */ package lottery.domains.content.dao.impl;
/*    */ 
/*    */ import java.util.List;
/*    */ import javautils.jdbc.hibernate.HibernateSuperDao;
/*    */ import lottery.domains.content.dao.UserBetsRiskDao;
/*    */ import lottery.domains.content.entity.UserBetsRisk;
/*    */ import org.hibernate.criterion.Criterion;
/*    */ import org.hibernate.criterion.Order;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Repository;
/*    */ 
/*    */ @Repository
/*    */ public class UserBetsRiskDaoImpl implements UserBetsRiskDao
/*    */ {
/* 15 */   private final String tab = UserBetsRisk.class.getSimpleName();
/*    */   
/*    */   @Autowired
/*    */   private HibernateSuperDao<UserBetsRisk> superDao;
/*    */   
/*    */   public List<UserBetsRisk> list(List<Criterion> criterions, List<Order> orders)
/*    */   {
/* 22 */     return this.superDao.findByCriteria(UserBetsRisk.class, criterions, orders);
/*    */   }
/*    */   
/*    */   public boolean updateStatus(int id, int fromStatus, int toStatus, String openCode, double prizeMoney, String prizeTime, int winNum)
/*    */   {
/* 27 */     String hql = "update " + this.tab + " set status = ?0, openCode = ?1, prizeMoney = ?2, prizeTime = ?3, winNum = ?4 where id = ?5 and status = ?6";
/* 28 */     Object[] values = { Integer.valueOf(toStatus), openCode, Double.valueOf(prizeMoney), prizeTime, Integer.valueOf(winNum), Integer.valueOf(id), Integer.valueOf(fromStatus) };
/* 29 */     return this.superDao.update(hql, values);
/*    */   }
/*    */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/content/dao/impl/UserBetsRiskDaoImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */