/*    */ package lottery.domains.content.dao.impl;
/*    */ 
/*    */ import java.util.List;
/*    */ import javautils.jdbc.hibernate.HibernateSuperDao;
/*    */ import lottery.domains.content.dao.SysConfigDao;
/*    */ import lottery.domains.content.entity.SysConfig;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Repository;
/*    */ 
/*    */ @Repository
/*    */ public class SysConfigDaoImpl
/*    */   implements SysConfigDao
/*    */ {
/* 14 */   private final String tab = SysConfig.class.getSimpleName();
/*    */   
/*    */   @Autowired
/*    */   private HibernateSuperDao<SysConfig> superDao;
/*    */   
/*    */   public List<SysConfig> listAll()
/*    */   {
/* 21 */     String hql = "from " + this.tab;
/* 22 */     return this.superDao.list(hql);
/*    */   }
/*    */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/content/dao/impl/SysConfigDaoImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */