/*    */ package lottery.domains.content.dao.impl;
/*    */ 
/*    */ import java.util.List;
/*    */ import javautils.array.ArrayUtils;
/*    */ import javautils.jdbc.hibernate.HibernateSuperDao;
/*    */ import lottery.domains.content.dao.UserBetsHitRankingDao;
/*    */ import lottery.domains.content.entity.UserBetsHitRanking;
/*    */ import org.apache.commons.collections.CollectionUtils;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Repository;
/*    */ 
/*    */ @Repository
/*    */ public class UserBetsHitRankingDaoImpl
/*    */   implements UserBetsHitRankingDao
/*    */ {
/* 16 */   private final String tab = UserBetsHitRanking.class.getSimpleName();
/*    */   
/*    */   @Autowired
/*    */   private HibernateSuperDao<UserBetsHitRanking> superDao;
/*    */   
/*    */   public long getTotalSize(int platform)
/*    */   {
/* 23 */     String hql = "select count(id) from " + this.tab + " where platform = ?0";
/* 24 */     Object[] values = { Integer.valueOf(platform) };
/* 25 */     return ((Long)this.superDao.unique(hql, values)).longValue();
/*    */   }
/*    */   
/*    */   public UserBetsHitRanking getMinRanking(int platform, String startTime, String endTime)
/*    */   {
/* 30 */     String hql = "from " + this.tab + " where platform = ?0 and time >= ?1 and time < ?2 order by prizeMoney asc,time asc";
/* 31 */     Object[] values = { Integer.valueOf(platform), startTime, endTime };
/* 32 */     List<UserBetsHitRanking> list = this.superDao.list(hql, values, 0, 1);
/* 33 */     return CollectionUtils.isEmpty(list) ? null : (UserBetsHitRanking)list.get(0);
/*    */   }
/*    */   
/*    */   public boolean add(UserBetsHitRanking ranking)
/*    */   {
/* 38 */     return this.superDao.save(ranking);
/*    */   }
/*    */   
/*    */   public List<Integer> getIds(int platform, String startTime, String endTime)
/*    */   {
/* 43 */     String hql = "select id from " + this.tab + " where platform = ?0 and time >= ?1 and time < ?2 order by prizeMoney desc,time desc";
/* 44 */     Object[] values = { Integer.valueOf(platform), startTime, endTime };
/* 45 */     List list = this.superDao.listObject(hql, values);
/* 46 */     return list;
/*    */   }
/*    */   
/*    */   public List<Integer> getTotalIds(int count, int platform, String startTime, String endTime)
/*    */   {
/* 51 */     String hql = "select id from " + this.tab + " where platform = ?0 and time >= ?1 and time < ?2 order by prizeMoney desc,time desc";
/* 52 */     Object[] values = { Integer.valueOf(platform), startTime, endTime };
/* 53 */     List list = this.superDao.listObject(hql, values, 0, count);
/* 54 */     return list;
/*    */   }
/*    */   
/*    */   public List<Integer> getIdsByTimeDesc(int count, int platform)
/*    */   {
/* 59 */     String hql = "select id from " + this.tab + " where platform = ?0 order by time desc";
/* 60 */     Object[] values = { Integer.valueOf(platform) };
/* 61 */     List list = this.superDao.listObject(hql, values, 0, count);
/* 62 */     return list;
/*    */   }
/*    */   
/*    */   public int delNotInIds(List<Integer> ids, int platform)
/*    */   {
/* 67 */     String hql = "delete from " + this.tab + " where id not in (" + ArrayUtils.transInIds(ids) + ") and platform=" + platform;
/* 68 */     return this.superDao.updateByHql(hql);
/*    */   }
/*    */   
/*    */   public boolean delNotInIds(List<Integer> ids, int platform, String startTime, String endTime)
/*    */   {
/* 73 */     String hql = "delete from " + this.tab + " where id not in (" + ArrayUtils.transInIds(ids) + ") and platform=?0 and time >= ?1 and time < ?2";
/* 74 */     Object[] values = { Integer.valueOf(platform), startTime, endTime };
/* 75 */     return this.superDao.delete(hql, values);
/*    */   }
/*    */   
/*    */   public boolean delByTime(int platform, String endTime)
/*    */   {
/* 80 */     String hql = "delete from " + this.tab + " where platform=?0 and time < ?1";
/* 81 */     Object[] values = { Integer.valueOf(platform), endTime };
/* 82 */     return this.superDao.delete(hql, values);
/*    */   }
/*    */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/content/dao/impl/UserBetsHitRankingDaoImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */