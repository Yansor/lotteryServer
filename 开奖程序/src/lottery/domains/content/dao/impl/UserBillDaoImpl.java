/*    */ package lottery.domains.content.dao.impl;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import javautils.jdbc.PageList;
/*    */ import javautils.jdbc.hibernate.HibernateSuperDao;
/*    */ import lottery.domains.content.dao.UserBillDao;
/*    */ import lottery.domains.content.entity.UserBill;
/*    */ import org.hibernate.criterion.Criterion;
/*    */ import org.hibernate.criterion.Order;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Repository;
/*    */ 
/*    */ 
/*    */ 
/*    */ @Repository
/*    */ public class UserBillDaoImpl
/*    */   implements UserBillDao
/*    */ {
/* 20 */   private final String tab = UserBill.class.getSimpleName();
/*    */   
/*    */   @Autowired
/*    */   private HibernateSuperDao<UserBill> superDao;
/*    */   
/*    */   public boolean add(UserBill entity)
/*    */   {
/* 27 */     return this.superDao.save(entity);
/*    */   }
/*    */   
/*    */   public boolean addBills(List<UserBill> userBills)
/*    */   {
/* 32 */     String sql = "insert into `user_bill`(`billno`, `user_id`, `account`, `type`, `money`, `before_money`, `after_money`, `ref_type`, `ref_id`, `time`, `remarks`) values(?,?,?,?,?,?,?,?,?,?,?)";
/*    */     
/*    */ 
/* 35 */     List<Object[]> params = new ArrayList();
/* 36 */     for (UserBill tmp : userBills)
/*    */     {
/* 38 */       Object[] param = { tmp.getBillno(), Integer.valueOf(tmp.getUserId()), Integer.valueOf(tmp.getAccount()), Integer.valueOf(tmp.getType()), Double.valueOf(tmp.getMoney()), Double.valueOf(tmp.getBeforeMoney()), Double.valueOf(tmp.getAfterMoney()), tmp.getRefType(), tmp.getRefId(), tmp.getTime(), tmp.getRemarks() };
/* 39 */       params.add(param);
/*    */     }
/* 41 */     return this.superDao.doWork(sql, params);
/*    */   }
/*    */   
/*    */   public UserBill getById(int id)
/*    */   {
/* 46 */     String hql = "from " + this.tab + " where id = ?0";
/* 47 */     Object[] values = { Integer.valueOf(id) };
/* 48 */     return (UserBill)this.superDao.unique(hql, values);
/*    */   }
/*    */   
/*    */   public PageList search(List<Criterion> criterions, List<Order> orders, int start, int limit)
/*    */   {
/* 53 */     String propertyName = "id";
/* 54 */     return this.superDao.findPageList(UserBill.class, propertyName, criterions, orders, start, limit);
/*    */   }
/*    */   
/*    */   public int clearUserBillNegateBeforeMoney()
/*    */   {
/* 59 */     String hql = "update " + this.tab + " set beforeMoney = 0 where beforeMoney < 0";
/* 60 */     return this.superDao.updateByHql(hql);
/*    */   }
/*    */   
/*    */   public int clearUserBillNegateAfterMoney()
/*    */   {
/* 65 */     String hql = "update " + this.tab + " set afterMoney = 0 where afterMoney < 0";
/* 66 */     return this.superDao.updateByHql(hql);
/*    */   }
/*    */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/content/dao/impl/UserBillDaoImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */