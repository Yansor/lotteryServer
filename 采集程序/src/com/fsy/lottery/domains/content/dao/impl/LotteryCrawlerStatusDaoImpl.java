 package com.fsy.lottery.domains.content.dao.impl;

 import com.fsy.javautils.jdbc.hibernate.HibernateSuperDao;
 import com.fsy.lottery.domains.content.dao.LotteryCrawlerStatusDao;
 import com.fsy.lottery.domains.content.entity.LotteryCrawlerStatus;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Repository;


 @Repository
 public class LotteryCrawlerStatusDaoImpl
   implements LotteryCrawlerStatusDao
 {
/* 14 */   private final String tab = LotteryCrawlerStatus.class.getSimpleName();

   @Autowired
   private HibernateSuperDao<LotteryCrawlerStatus> superDao;

   public LotteryCrawlerStatus get(String shortName)
   {
/* 21 */     String hql = "from " + this.tab + " where shortName = ?0";
/* 22 */     Object[] values = { shortName };
/* 23 */     return (LotteryCrawlerStatus)this.superDao.unique(hql, values);
   }

   public boolean update(String shortName, String lastExpect, String lastUpdate)
   {
/* 28 */     String hql = "update " + this.tab + " set lastExpect = ?1, lastUpdate = ?2 where shortName = ?0";
/* 29 */     Object[] values = { shortName, lastExpect, lastUpdate };
/* 30 */     return this.superDao.update(hql, values);
   }
 }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryCapture/lotteryCaptureRepair.jar!/lottery/domains/content/dao/impl/LotteryCrawlerStatusDaoImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */