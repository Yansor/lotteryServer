/*    */ package lottery.domains.content.dao.impl;
/*    */ 
/*    */ import java.util.List;
/*    */ import javautils.jdbc.hibernate.HibernateSuperDao;
/*    */ import lottery.domains.content.dao.LotteryOpenCodeDao;
/*    */ import lottery.domains.content.entity.LotteryOpenCode;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Repository;
/*    */ 
/*    */ @Repository
/*    */ public class LotteryOpenCodeDaoImpl implements LotteryOpenCodeDao
/*    */ {
/* 13 */   private final String tab = LotteryOpenCode.class.getSimpleName();
/*    */   
/*    */   @Autowired
/*    */   private HibernateSuperDao<LotteryOpenCode> superDao;
/*    */   
/*    */   public boolean updateOpened(int id, String updateTime)
/*    */   {
/* 20 */     String hql = "update " + this.tab + " set openStatus = 1, openTime = ?0 where  id=?1";
/* 21 */     Object[] values = { updateTime, Integer.valueOf(id) };
/* 22 */     return this.superDao.update(hql, values);
/*    */   }
/*    */   
/*    */   public boolean updateCancelled(int id, String updateTime)
/*    */   {
/* 27 */     String hql = "update " + this.tab + " set openStatus = 3, openTime = ?0 where  id=?1";
/* 28 */     Object[] values = { updateTime, Integer.valueOf(id) };
/* 29 */     return this.superDao.update(hql, values);
/*    */   }
/*    */   
/*    */   public List<LotteryOpenCode> getLatest(String lottery, int count)
/*    */   {
/* 34 */     String hql = "from " + this.tab + " where lottery=?0 order by expect desc";
/* 35 */     Object[] values = { lottery };
/* 36 */     List<LotteryOpenCode> list = this.superDao.list(hql, values, 0, count);
/* 37 */     return list;
/*    */   }
/*    */   
/*    */   public List<LotteryOpenCode> listAfter(String lottery, String expect)
/*    */   {
/* 42 */     String hql = "from " + this.tab + " where lottery=?0 and expect>=?1";
/* 43 */     Object[] values = { lottery, expect };
/* 44 */     List<LotteryOpenCode> list = this.superDao.list(hql, values);
/* 45 */     return list;
/*    */   }
/*    */   
/*    */   public List<LotteryOpenCode> getBeforeNotOpen(String lottery, int count)
/*    */   {
/* 50 */     String hql = "from " + this.tab + " where openStatus = 0 and lottery=?0 order by expect desc";
/* 51 */     Object[] values = { lottery };
/* 52 */     List<LotteryOpenCode> list = this.superDao.list(hql, values, 0, count);
/* 53 */     return list;
/*    */   }
/*    */   
/*    */   public LotteryOpenCode getByExcept(String lottery, String except)
/*    */   {
/* 58 */     String hql = "from " + this.tab + " where lottery=?0 and  expect = ?1 ";
/* 59 */     Object[] values = { lottery, except };
/* 60 */     List<LotteryOpenCode> list = this.superDao.list(hql, values, 0, 1);
/* 61 */     if ((list != null) && (list.size() > 0)) {
/* 62 */       return (LotteryOpenCode)list.get(0);
/*    */     }
/* 64 */     return null;
/*    */   }
/*    */   
/*    */   public LotteryOpenCode getByExceptAndUserId(String lottery, int userId, String except)
/*    */   {
/* 69 */     String hql = "from " + this.tab + " where lottery=?0 and  expect = ?1 and userId = ?2";
/* 70 */     Object[] values = { lottery, except, Integer.valueOf(userId) };
/* 71 */     List<LotteryOpenCode> list = this.superDao.list(hql, values, 0, 1);
/* 72 */     if ((list != null) && (list.size() > 0)) {
/* 73 */       return (LotteryOpenCode)list.get(0);
/*    */     }
/* 75 */     return null;
/*    */   }
/*    */   
/*    */   public boolean add(LotteryOpenCode openCode)
/*    */   {
/* 80 */     return this.superDao.save(openCode);
/*    */   }
/*    */   
/*    */   public boolean update(LotteryOpenCode openCode)
/*    */   {
/* 85 */     return this.superDao.update(openCode);
/*    */   }
/*    */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/content/dao/impl/LotteryOpenCodeDaoImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */