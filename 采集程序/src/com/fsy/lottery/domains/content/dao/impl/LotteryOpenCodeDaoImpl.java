/*    */ package com.fsy.lottery.domains.content.dao.impl;
/*    */ 
/*    */ import java.util.List;
/*    */ import com.fsy.javautils.jdbc.hibernate.HibernateSuperDao;
/*    */ import com.fsy.lottery.domains.content.dao.LotteryOpenCodeDao;
/*    */ import com.fsy.lottery.domains.content.entity.LotteryOpenCode;
/*    */ import org.apache.commons.collections.CollectionUtils;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Repository;
/*    */ 
/*    */ 
/*    */ 
/*    */ @Repository
/*    */ public class LotteryOpenCodeDaoImpl
/*    */   implements LotteryOpenCodeDao
/*    */ {
/* 17 */   private final String tab = LotteryOpenCode.class.getSimpleName();
/*    */   
/*    */   @Autowired
/*    */   private HibernateSuperDao<LotteryOpenCode> superDao;
/*    */   
/*    */   public LotteryOpenCode get(String lottery, String expect)
/*    */   {
/* 24 */     String hql = "from " + this.tab + " where lottery = ?0 and expect = ?1 and userId is null";
/* 25 */     Object[] values = { lottery, expect };
/* 26 */     List<LotteryOpenCode> list = this.superDao.list(hql, values);
/* 27 */     if (CollectionUtils.isNotEmpty(list)) {
/* 28 */       return (LotteryOpenCode)list.get(0);
/*    */     }
/*    */     
/* 31 */     return null;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean add(LotteryOpenCode entity)
/*    */   {
/* 44 */     return this.superDao.save(entity);
/*    */   }
/*    */   
/*    */   public List<LotteryOpenCode> getLatest(String lotteryName, int count)
/*    */   {
/* 49 */     String hql = "from " + this.tab + " where lottery = ?0 and userId is null order by expect desc";
/* 50 */     Object[] values = { lotteryName };
/* 51 */     return this.superDao.list(hql, values, 0, count);
/*    */   }
/*    */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryCapture/lotteryCaptureRepair.jar!/lottery/domains/content/dao/impl/LotteryOpenCodeDaoImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */