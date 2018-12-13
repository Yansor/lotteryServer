/*    */ package lottery.domains.content.dao.impl;
/*    */ 
/*    */ import java.util.List;
/*    */ import javautils.jdbc.hibernate.HibernateSuperDao;
/*    */ import lottery.domains.content.dao.UserLotteryReportDao;
/*    */ import lottery.domains.content.entity.UserLotteryReport;
/*    */ import org.hibernate.criterion.Criterion;
/*    */ import org.hibernate.criterion.Order;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Repository;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Repository
/*    */ public class UserLotteryReportDaoImpl
/*    */   implements UserLotteryReportDao
/*    */ {
/* 22 */   private final String tab = UserLotteryReport.class.getSimpleName();
/*    */   
/*    */   @Autowired
/*    */   private HibernateSuperDao<UserLotteryReport> superDao;
/*    */   
/*    */   public UserLotteryReport get(int userId, String time)
/*    */   {
/* 29 */     String hql = "from " + this.tab + " where userId = ?0 and time = ?1";
/* 30 */     Object[] values = { Integer.valueOf(userId), time };
/* 31 */     return (UserLotteryReport)this.superDao.unique(hql, values);
/*    */   }
/*    */   
/*    */   public boolean update(UserLotteryReport entity) {
/* 35 */     String hql = "update " + this.tab + " set transIn = transIn + ?1, transOut = transOut + ?2, spend = spend + ?3, prize = prize + ?4, spendReturn = spendReturn + ?5, proxyReturn = proxyReturn + ?6, cancelOrder = cancelOrder + ?7, activity = activity + ?8, billingOrder = billingOrder + ?9, packet = packet + ?10 where id = ?0";
/* 36 */     Object[] values = { Integer.valueOf(entity.getId()), Double.valueOf(entity.getTransIn()), Double.valueOf(entity.getTransOut()), Double.valueOf(entity.getSpend()), Double.valueOf(entity.getPrize()), Double.valueOf(entity.getSpendReturn()), Double.valueOf(entity.getProxyReturn()), Double.valueOf(entity.getCancelOrder()), Double.valueOf(entity.getActivity()), Double.valueOf(entity.getBillingOrder()), Double.valueOf(entity.getPacket()) };
/* 37 */     return this.superDao.update(hql, values);
/*    */   }
/*    */   
/*    */   public boolean add(UserLotteryReport entity)
/*    */   {
/* 42 */     return this.superDao.save(entity);
/*    */   }
/*    */   
/*    */ 
/*    */   public List<UserLotteryReport> find(List<Criterion> criterions, List<Order> orders)
/*    */   {
/* 48 */     return this.superDao.findByCriteria(UserLotteryReport.class, criterions, orders);
/*    */   }
/*    */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/content/dao/impl/UserLotteryReportDaoImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */