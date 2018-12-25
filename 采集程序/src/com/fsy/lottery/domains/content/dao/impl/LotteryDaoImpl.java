 package com.fsy.lottery.domains.content.dao.impl;

 import java.util.List;
 import com.fsy.javautils.jdbc.hibernate.HibernateSuperDao;
 import com.fsy.lottery.domains.content.dao.LotteryDao;
 import com.fsy.lottery.domains.content.entity.Lottery;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Repository;

 @Repository
 public class LotteryDaoImpl
   implements LotteryDao
 {
/* 14 */   private final String tab = Lottery.class.getSimpleName();

   @Autowired
   private HibernateSuperDao<Lottery> superDao;

   public List<Lottery> listAll()
   {
/* 21 */     String hql = "from " + this.tab + " order by sort";
/* 22 */     return this.superDao.list(hql);
   }
 }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryCapture/lotteryCaptureRepair.jar!/lottery/domains/content/dao/impl/LotteryDaoImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */