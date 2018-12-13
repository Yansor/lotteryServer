/*    */ package lottery.domains.content.dao.impl;
/*    */ 
/*    */ import java.util.List;
/*    */ import javautils.jdbc.hibernate.HibernateSuperDao;
/*    */ import lottery.domains.content.dao.LotteryDao;
/*    */ import lottery.domains.content.entity.Lottery;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Repository;
/*    */ 
/*    */ @Repository
/*    */ public class LotteryDaoImpl
/*    */   implements LotteryDao
/*    */ {
/* 14 */   private final String tab = Lottery.class.getSimpleName();
/*    */   
/*    */   @Autowired
/*    */   private HibernateSuperDao<Lottery> superDao;
/*    */   
/*    */   public List<Lottery> listAll()
/*    */   {
/* 21 */     String hql = "from " + this.tab + " order by sort, type";
/* 22 */     return this.superDao.list(hql);
/*    */   }
/*    */   
/*    */   public boolean updateStatus(int id, int status)
/*    */   {
/* 27 */     String hql = "update " + this.tab + " set status = ?1 where id = ?0";
/* 28 */     Object[] values = { Integer.valueOf(id), Integer.valueOf(status) };
/* 29 */     return this.superDao.update(hql, values);
/*    */   }
/*    */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/content/dao/impl/LotteryDaoImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */