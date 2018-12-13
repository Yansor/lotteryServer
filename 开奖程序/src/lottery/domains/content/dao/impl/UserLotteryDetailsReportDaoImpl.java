/*     */ package lottery.domains.content.dao.impl;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import javautils.jdbc.hibernate.HibernateSuperDao;
/*     */ import lottery.domains.content.dao.UserLotteryDetailsReportDao;
/*     */ import lottery.domains.content.entity.UserLotteryDetailsReport;
/*     */ import org.apache.commons.collections.CollectionUtils;
/*     */ import org.hibernate.criterion.Restrictions;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Repository;
/*     */ 
/*     */ 
/*     */ 
/*     */ @Repository
/*     */ public class UserLotteryDetailsReportDaoImpl
/*     */   implements UserLotteryDetailsReportDao
/*     */ {
/*  22 */   private final String tab = UserLotteryDetailsReport.class.getSimpleName();
/*     */   
/*     */   @Autowired
/*     */   private HibernateSuperDao<UserLotteryDetailsReport> superDao;
/*     */   
/*     */   public boolean add(UserLotteryDetailsReport entity)
/*     */   {
/*  29 */     return this.superDao.save(entity);
/*     */   }
/*     */   
/*     */   public UserLotteryDetailsReport get(int userId, int lotteryId, int ruleId, String time)
/*     */   {
/*  34 */     String hql = "from " + this.tab + " where userId = ?0 and lotteryId = ?1 and ruleId = ?2 and time = ?3";
/*  35 */     Object[] values = { Integer.valueOf(userId), Integer.valueOf(lotteryId), Integer.valueOf(ruleId), time };
/*  36 */     return (UserLotteryDetailsReport)this.superDao.unique(hql, values);
/*     */   }
/*     */   
/*     */   public boolean update(UserLotteryDetailsReport entity)
/*     */   {
/*  41 */     String hql = "update " + this.tab + " set spend = spend + ?1, prize = prize + ?2, spendReturn = spendReturn + ?3, proxyReturn = proxyReturn + ?4, cancelOrder = cancelOrder + ?5, billingOrder = billingOrder + ?6 where id = ?0";
/*  42 */     Object[] values = { Integer.valueOf(entity.getId()), Double.valueOf(entity.getSpend()), Double.valueOf(entity.getPrize()), Double.valueOf(entity.getSpendReturn()), Double.valueOf(entity.getProxyReturn()), Double.valueOf(entity.getCancelOrder()), Double.valueOf(entity.getBillingOrder()) };
/*  43 */     return this.superDao.update(hql, values);
/*     */   }
/*     */   
/*     */ 
/*     */   public void addDetailsReports(List<UserLotteryDetailsReport> detailsReports)
/*     */   {
/*  49 */     List<UserLotteryDetailsReport> oldDatas = getOldDatas(detailsReports);
/*     */     
/*     */ 
/*  52 */     Object[] compared = compare(detailsReports, oldDatas);
/*     */     
/*     */ 
/*  55 */     List<UserLotteryDetailsReport> adds = (List)compared[0];
/*  56 */     batchAddDetailsReports(adds);
/*     */     
/*     */ 
/*  59 */     List<UserLotteryDetailsReport> updates = (List)compared[1];
/*  60 */     batchUpdateDetailsReports(updates);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private Object[] compare(List<UserLotteryDetailsReport> detailsReports, List<UserLotteryDetailsReport> oldDatas)
/*     */   {
/*  69 */     List<UserLotteryDetailsReport> adds = new ArrayList();
/*  70 */     for (Iterator localIterator1 = detailsReports.iterator(); localIterator1.hasNext();) {
        UserLotteryDetailsReport  detailsReport = (UserLotteryDetailsReport)localIterator1.next();
/*  71 */       boolean exist = false;
/*  72 */       for (UserLotteryDetailsReport oldData : oldDatas) {
/*  73 */         boolean equalsOld = equalsOld(detailsReport, oldData);
/*  74 */         if (equalsOld) {
/*  75 */           exist = true;
/*  76 */           break;
/*     */         }
/*     */       }
/*  79 */       if (!exist) {
/*  80 */         adds.add(detailsReport);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*  85 */     Object updates = new ArrayList();
/*  86 */     for (Iterator detailsReport = oldDatas.iterator(); detailsReport.hasNext();) {
        UserLotteryDetailsReport  oldData = (UserLotteryDetailsReport)detailsReport.next();
/*  87 */       for (UserLotteryDetailsReport temp : detailsReports) {
/*  88 */         boolean equalsOld = equalsOld(temp, oldData);
/*  89 */         if (equalsOld) {
/*  90 */           temp.setId(oldData.getId());
/*  91 */           ((List)updates).add(detailsReport);
/*  92 */           break;
/*     */         }
/*     */       }
/*     */     }
/*     */     UserLotteryDetailsReport oldData;
/*  97 */     return new Object[] { adds, updates };
/*     */   }
/*     */   
/*     */   private boolean equalsOld(UserLotteryDetailsReport detailsReport, UserLotteryDetailsReport oldData) {
/* 101 */     return (detailsReport.getUserId() == oldData.getUserId()) && 
/* 102 */       (detailsReport.getLotteryId() == oldData.getLotteryId()) && 
/* 103 */       (detailsReport.getRuleId() == oldData.getRuleId()) && 
/* 104 */       (detailsReport.getTime().equalsIgnoreCase(oldData.getTime()));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private List<UserLotteryDetailsReport> getOldDatas(List<UserLotteryDetailsReport> detailsReports)
/*     */   {
/* 113 */     Set<Integer> userIds = new HashSet();
/* 114 */     Set<Integer> lotteryIds = new HashSet();
/* 115 */     Set<Integer> ruleIds = new HashSet();
/* 116 */     Set<String> times = new HashSet();
/* 117 */     for (UserLotteryDetailsReport detailsReport : detailsReports) {
/* 118 */       userIds.add(Integer.valueOf(detailsReport.getUserId()));
/* 119 */       lotteryIds.add(Integer.valueOf(detailsReport.getLotteryId()));
/* 120 */       ruleIds.add(Integer.valueOf(detailsReport.getRuleId()));
/* 121 */       times.add(detailsReport.getTime());
/*     */     }
/*     */     
/*     */ 
/* 125 */     Object criterions = new ArrayList();
/* 126 */     ((List)criterions).add(Restrictions.in("userId", userIds));
/* 127 */     ((List)criterions).add(Restrictions.in("lotteryId", lotteryIds));
/* 128 */     ((List)criterions).add(Restrictions.in("ruleId", ruleIds));
/* 129 */     ((List)criterions).add(Restrictions.in("time", times));
/*     */     
/* 131 */     List<UserLotteryDetailsReport> result = this.superDao.findByCriteria(UserLotteryDetailsReport.class, (List)criterions, null);
/*     */     
/* 133 */     return result;
/*     */   }
/*     */   
/*     */   private void batchAddDetailsReports(List<UserLotteryDetailsReport> adds) {
/* 137 */     if (CollectionUtils.isEmpty(adds)) {
/* 138 */       return;
/*     */     }
/*     */     
/* 141 */     String sql = "insert into `user_lottery_details_report`(`user_id`, `lottery_id`, `rule_id`, `spend`, `prize`, `spend_return`, `proxy_return`, `cancel_order`, `billing_order`, `time`) values(?,?,?,?,?,?,?,?,?,?)";
/*     */     
/*     */ 
/* 144 */     List<Object[]> params = new ArrayList();
/* 145 */     for (UserLotteryDetailsReport tmp : adds)
/*     */     {
/*     */ 
/* 148 */       Object[] param = { Integer.valueOf(tmp.getUserId()), Integer.valueOf(tmp.getLotteryId()), Integer.valueOf(tmp.getRuleId()), Double.valueOf(tmp.getSpend()), Double.valueOf(tmp.getPrize()), Double.valueOf(tmp.getSpendReturn()), Double.valueOf(tmp.getProxyReturn()), Double.valueOf(tmp.getCancelOrder()), Double.valueOf(tmp.getBillingOrder()), tmp.getTime() };
/* 149 */       params.add(param);
/*     */     }
/* 151 */     this.superDao.doWork(sql, params);
/*     */   }
/*     */   
/*     */   private void batchUpdateDetailsReports(List<UserLotteryDetailsReport> updates) {
/* 155 */     if (CollectionUtils.isEmpty(updates)) {
/* 156 */       return;
/*     */     }
/*     */     
/* 159 */     String sql = "update `user_lottery_details_report` set `prize` = `prize` + ?, `spend_return` = `spend_return` + ?, `proxy_return` = `proxy_return` + ?, `billing_order` = `billing_order` + ? where `id` = ?";
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 166 */     List<Object[]> params = new ArrayList();
/* 167 */     for (UserLotteryDetailsReport tmp : updates)
/*     */     {
/* 169 */       Object[] param = { Double.valueOf(tmp.getPrize()), Double.valueOf(tmp.getSpendReturn()), Double.valueOf(tmp.getProxyReturn()), Double.valueOf(tmp.getBillingOrder()), Integer.valueOf(tmp.getId()) };
/* 170 */       params.add(param);
/*     */     }
/*     */     
/* 173 */     this.superDao.doWork(sql, params);
/*     */   }
/*     */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/content/dao/impl/UserLotteryDetailsReportDaoImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */