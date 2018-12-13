/*    */ package lottery.domains.content.dao.impl;
/*    */ 
/*    */ import java.util.List;
/*    */ import javautils.date.Moment;
/*    */ import javautils.jdbc.hibernate.HibernateSuperDao;
/*    */ import lottery.domains.content.dao.DbServerSyncDao;
/*    */ import lottery.domains.content.entity.DbServerSync;
/*    */ import lottery.domains.content.global.DbServerSyncEnum;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Repository;
/*    */ 
/*    */ @Repository
/*    */ public class DbServerSyncDaoImpl
/*    */   implements DbServerSyncDao
/*    */ {
/* 16 */   private final String tab = DbServerSync.class.getSimpleName();
/*    */   
/*    */   @Autowired
/*    */   private HibernateSuperDao<DbServerSync> superDao;
/*    */   
/*    */   public List<DbServerSync> listAll()
/*    */   {
/* 23 */     String hql = "from " + this.tab;
/* 24 */     return this.superDao.list(hql);
/*    */   }
/*    */   
/*    */   public boolean update(DbServerSyncEnum type)
/*    */   {
/* 29 */     String key = type.name();
/* 30 */     String time = new Moment().toSimpleTime();
/* 31 */     String hql = "update " + this.tab + " set lastModTime = ?1 where key = ?0";
/* 32 */     Object[] values = { key, time };
/* 33 */     return this.superDao.update(hql, values);
/*    */   }
/*    */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/content/dao/impl/DbServerSyncDaoImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */