/*    */ package lottery.domains.content.dao.impl;
/*    */ 
/*    */ import java.util.List;
/*    */ import javautils.jdbc.PageList;
/*    */ import javautils.jdbc.hibernate.HibernateSuperDao;
/*    */ import lottery.domains.content.dao.LotteryOpenTimeDao;
/*    */ import lottery.domains.content.entity.LotteryOpenTime;
/*    */ import org.hibernate.criterion.Criterion;
/*    */ import org.hibernate.criterion.Order;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Repository;
/*    */ 
/*    */ @Repository
/*    */ public class LotteryOpenTimeDaoImpl
/*    */   implements LotteryOpenTimeDao
/*    */ {
/* 17 */   private final String tab = LotteryOpenTime.class.getSimpleName();
/*    */   
/*    */   @Autowired
/*    */   private HibernateSuperDao<LotteryOpenTime> superDao;
/*    */   
/*    */   public List<LotteryOpenTime> listAll()
/*    */   {
/* 24 */     String hql = "from " + this.tab + " order by expect asc";
/* 25 */     return this.superDao.list(hql);
/*    */   }
/*    */   
/*    */   public PageList find(List<Criterion> criterions, List<Order> orders, int start, int limit)
/*    */   {
/* 30 */     return this.superDao.findPageList(LotteryOpenTime.class, criterions, orders, start, limit);
/*    */   }
/*    */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/content/dao/impl/LotteryOpenTimeDaoImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */