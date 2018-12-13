/*     */ package lottery.domains.content.dao.impl;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javautils.array.ArrayUtils;
/*     */ import javautils.jdbc.PageList;
/*     */ import javautils.jdbc.hibernate.HibernateSuperDao;
/*     */ import lottery.domains.content.dao.UserBetsDao;
/*     */ import lottery.domains.content.entity.UserBets;
/*     */ import lottery.domains.content.entity.UserBetsNoCode;
/*     */ import org.apache.commons.collections.CollectionUtils;
/*     */ import org.hibernate.criterion.Criterion;
/*     */ import org.hibernate.criterion.Order;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Repository;
/*     */ 
/*     */ @Repository
/*     */ public class UserBetsDaoImpl
/*     */   implements UserBetsDao
/*     */ {
/*  21 */   private final String tab = UserBets.class.getSimpleName();
/*  22 */   private final String noCodetab = UserBetsNoCode.class.getSimpleName();
/*     */   
/*     */   @Autowired
/*     */   private HibernateSuperDao<UserBets> superDao;
/*     */   
/*     */   @Autowired
/*     */   private HibernateSuperDao<UserBetsNoCode> noCodeSuperDao;
/*     */   
/*     */   public boolean add(UserBets userBets)
/*     */   {
/*  32 */     return this.superDao.save(userBets);
/*     */   }
/*     */   
/*     */   public UserBets getById(int id)
/*     */   {
/*  37 */     String hql = "from " + this.tab + " where id = ?0";
/*  38 */     Object[] values = { Integer.valueOf(id) };
/*  39 */     return (UserBets)this.superDao.unique(hql, values);
/*     */   }
/*     */   
/*     */   public UserBets getById(int id, int userId)
/*     */   {
/*  44 */     String hql = "from " + this.tab + " where id = ?0 and userId = ?1";
/*  45 */     Object[] values = { Integer.valueOf(id), Integer.valueOf(userId) };
/*  46 */     return (UserBets)this.superDao.unique(hql, values);
/*     */   }
/*     */   
/*     */   public List<UserBets> getByChaseBillno(String chaseBillno, int userId, String winExpect)
/*     */   {
/*  51 */     String hql = "from " + this.noCodetab + " where chaseBillno = ?0 and userId = ?1 and status=0 and type = 1 and expect > ?2 and id>0";
/*  52 */     Object[] values = { chaseBillno, Integer.valueOf(userId), winExpect };
/*     */     
/*     */ 
/*  55 */     List<UserBetsNoCode> noCodeList = this.noCodeSuperDao.list(hql, values);
/*  56 */     List<UserBets> list = new ArrayList();
/*  57 */     for (UserBetsNoCode tmpBean : noCodeList) {
/*  58 */       list.add(tmpBean.formatBean());
/*     */     }
/*  60 */     return list;
/*     */   }
/*     */   
/*     */   public List<UserBets> getByFollowBillno(String followBillno)
/*     */   {
/*  65 */     String hql = "from " + this.tab + " where type = 0 and status > 0 and planBillno = ?0";
/*  66 */     Object[] values = { followBillno };
/*  67 */     return this.superDao.list(hql, values);
/*     */   }
/*     */   
/*     */   public boolean updateToPlan(int id, int type, String planBillno)
/*     */   {
/*  72 */     String hql = "update " + this.tab + " set type = ?1, planBillno = ?2 where id = ?0";
/*  73 */     Object[] values = { Integer.valueOf(id), Integer.valueOf(type), planBillno };
/*  74 */     return this.superDao.update(hql, values);
/*     */   }
/*     */   
/*     */   public List<UserBets> list(List<Criterion> criterions, List<Order> orders)
/*     */   {
/*  79 */     return this.superDao.findByCriteria(UserBets.class, criterions, orders);
/*     */   }
/*     */   
/*     */ 
/*     */   public PageList search(List<Criterion> criterions, List<Order> orders, int start, int limit)
/*     */   {
/*  85 */     String propertyName = "id";
/*  86 */     return this.superDao.findPageList(UserBets.class, propertyName, criterions, orders, start, limit);
/*     */   }
/*     */   
/*     */   public boolean updateStatus(int id, int status, String openCode, double prizeMoney, String prizeTime)
/*     */   {
/*  91 */     String hql = "update " + this.tab + " set status = ?1, openCode = ?2, prizeMoney = ?3, prizeTime = ?4 where id = ?0";
/*  92 */     Object[] values = { Integer.valueOf(id), Integer.valueOf(status), openCode, Double.valueOf(prizeMoney), prizeTime };
/*  93 */     return this.superDao.update(hql, values);
/*     */   }
/*     */   
/*     */   public boolean cancel(int id)
/*     */   {
/*  98 */     String hql = "update " + this.tab + " set status = -1 where id = ?0 and status = 0";
/*  99 */     Object[] values = { Integer.valueOf(id) };
/* 100 */     return this.superDao.update(hql, values);
/*     */   }
/*     */   
/*     */   public boolean cancelAndSetOpenCode(int id, String openCode)
/*     */   {
/* 105 */     String hql = "update " + this.tab + " set status = -1, openCode = ?1 where id = ?0 and status = 0";
/* 106 */     Object[] values = { Integer.valueOf(id), openCode };
/* 107 */     return this.superDao.update(hql, values);
/*     */   }
/*     */   
/*     */   public List<?> sumUserProfitGroupByUserId(int lotteryId, String startTime, String endTime, List<Integer> userIds)
/*     */   {
/* 112 */     String hql = "select userId, sum(money), sum(prizeMoney) from " + this.tab + " where status in (1,2) and time >= ?0 and time <= ?1 and lotteryId = ?2 and id > 0";
/* 113 */     if (CollectionUtils.isNotEmpty(userIds)) {
/* 114 */       hql = hql + " and userId in (" + ArrayUtils.toString(userIds) + ")";
/*     */     }
/* 116 */     hql = hql + " group by userId";
/* 117 */     Object[] values = { startTime, endTime, Integer.valueOf(lotteryId) };
/* 118 */     return this.superDao.listObject(hql, values);
/*     */   }
/*     */   
/*     */   public double[] sumProfit(int lotteryId, String startTime, String endTime)
/*     */   {
/* 123 */     String hql = "select sum(money), sum(prizeMoney) from " + this.tab + " where status in (1,2) and time >= ?0 and time <= ?1 and lotteryId = ?2";
/* 124 */     Object[] values = { startTime, endTime, Integer.valueOf(lotteryId) };
/* 125 */     Object result = this.superDao.unique(hql, values);
/* 126 */     if (result == null) {
/* 127 */       return new double[] { 0.0D, 0.0D };
/*     */     }
/*     */     
/* 130 */     Object[] results = (Object[])result;
/* 131 */     double money = results[0] == null ? 0.0D : ((Double)results[0]).doubleValue();
/* 132 */     double prizeMoney = results[1] == null ? 0.0D : ((Double)results[1]).doubleValue();
/* 133 */     return new double[] { money, prizeMoney };
/*     */   }
/*     */   
/*     */   public List<UserBets> getLatest(int userId, int lotteryId, int count)
/*     */   {
/* 138 */     String hql = "from " + this.tab + " where userId=?0 and lotteryId=?1 and status in (1,2) and id > 0 order by expect desc";
/* 139 */     Object[] values = { Integer.valueOf(userId), Integer.valueOf(lotteryId) };
/* 140 */     List<UserBets> list = this.superDao.list(hql, values, 0, count);
/* 141 */     return list;
/*     */   }
/*     */   
/*     */   public List<UserBets> getByExpect(int lotteryId, String expect)
/*     */   {
/* 146 */     String hql = "from " + this.tab + " where lotteryId=?0 and expect=?1 and status in (1,2) and id > 0";
/* 147 */     Object[] values = { Integer.valueOf(lotteryId), expect };
/* 148 */     List<UserBets> list = this.superDao.list(hql, values);
/* 149 */     return list;
/*     */   }
/*     */   
/*     */   public List<UserBets> getByUserIdAndExpect(int userId, int lotteryId, String expect)
/*     */   {
/* 154 */     String hql = "from " + this.tab + " where userId = ?0 and lotteryId=?1 and expect=?2 and status in (1,2) and id > 0";
/* 155 */     Object[] values = { Integer.valueOf(userId), Integer.valueOf(lotteryId), expect };
/* 156 */     List<UserBets> list = this.superDao.list(hql, values);
/* 157 */     return list;
/*     */   }
/*     */   
/*     */   public List<UserBets> getNoDemoUserBetsByExpect(String hsql, Object[] values)
/*     */   {
/* 162 */     List<UserBets> list = this.superDao.list(hsql, values);
/* 163 */     return list;
/*     */   }
/*     */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/content/dao/impl/UserBetsDaoImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */