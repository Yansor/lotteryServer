/*     */ package lottery.domains.content.biz.impl;
/*     */ 
/*     */ import javautils.date.Moment;
/*     */ import lottery.domains.content.biz.UserBillService;
/*     */ import lottery.domains.content.biz.UserLotteryDetailsReportService;
/*     */ import lottery.domains.content.biz.UserLotteryReportService;
/*     */ import lottery.domains.content.dao.UserBillDao;
/*     */ import lottery.domains.content.dao.UserDao;
/*     */ import lottery.domains.content.entity.User;
/*     */ import lottery.domains.content.entity.UserBets;
/*     */ import lottery.domains.content.entity.UserBill;
/*     */ import org.bson.types.ObjectId;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Service;
/*     */ 
/*     */ @Service
/*     */ public class UserBillServiceImpl implements UserBillService
/*     */ {
/*  21 */   private static final Logger log = LoggerFactory.getLogger(UserBillServiceImpl.class);
/*     */   
/*     */   @Autowired
/*     */   private UserBillDao uBillDao;
/*     */   
/*     */   @Autowired
/*     */   private UserDao userDao;
/*     */   
/*     */   @Autowired
/*     */   private UserLotteryReportService uLotteryReportService;
/*     */   
/*     */   @Autowired
/*     */   private UserLotteryDetailsReportService uLotteryDetailsReportService;
/*     */   
/*     */ 
/*     */   private String billno()
/*     */   {
/*  38 */     return ObjectId.get().toString();
/*     */   }
/*     */   
/*     */   public boolean addCancelOrderBill(UserBets userBets, String remarks)
/*     */   {
/*  43 */     boolean flag = false;
/*     */     try {
/*  45 */       User newestUser = this.userDao.getById(userBets.getUserId());
/*     */       
/*  47 */       String billno = billno();
/*  48 */       int userId = newestUser.getId();
/*  49 */       int account = 2;
/*  50 */       int type = 10;
/*  51 */       double money = userBets.getMoney();
/*  52 */       double beforeMoney = newestUser.getLotteryMoney();
/*  53 */       if (beforeMoney < 0.0D) {
/*  54 */         beforeMoney = 0.0D;
/*     */       }
/*  56 */       double afterMoney = newestUser.getLotteryMoney() + money;
/*  57 */       if (afterMoney < 0.0D) {
/*  58 */         afterMoney = 0.0D;
/*     */       }
/*  60 */       Integer refType = Integer.valueOf(type);
/*  61 */       String refId = String.valueOf(userBets.getId());
/*  62 */       Moment thisTime = new Moment();
/*  63 */       UserBill tmpBill = new UserBill(billno, userId, account, type, money, beforeMoney, afterMoney, refType, refId, thisTime.toSimpleTime(), remarks);
/*  64 */       flag = this.uBillDao.add(tmpBill);
/*     */ 
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*     */ 
/*  70 */       log.error("写入彩票撤单失败！", e);
/*  71 */       throw e;
/*     */     }
/*  73 */     return flag;
/*     */   }
/*     */   
/*     */   public boolean addUserWinBill(UserBets userBets, double amount, String remarks)
/*     */   {
/*  78 */     boolean flag = false;
/*     */     try {
/*  80 */       User newestUser = this.userDao.getById(userBets.getUserId());
/*     */       
/*  82 */       String billno = billno();
/*  83 */       int userId = newestUser.getId();
/*  84 */       int type = 7;
/*  85 */       int refType = type;
/*  86 */       int account = 2;
/*  87 */       String refId = String.valueOf(userBets.getId());
/*  88 */       double money = amount;
/*  89 */       double beforeMoney = newestUser.getLotteryMoney();
/*  90 */       if (beforeMoney < 0.0D) {
/*  91 */         beforeMoney = 0.0D;
/*     */       }
/*  93 */       double afterMoney = newestUser.getLotteryMoney() + money;
/*  94 */       if (afterMoney < 0.0D) {
/*  95 */         afterMoney = 0.0D;
/*     */       }
/*  97 */       Moment thisTime = new Moment();
/*  98 */       UserBill tmpBill = new UserBill(billno, userId, account, type, money, beforeMoney, afterMoney, Integer.valueOf(refType), refId, thisTime.toSimpleTime(), remarks);
/*  99 */       flag = this.uBillDao.add(tmpBill);
/* 100 */       if (flag)
/*     */       {
/* 102 */         this.uLotteryReportService.update(userId, type, money, thisTime.toSimpleDate());
/* 103 */         this.uLotteryDetailsReportService.update(userId, userBets.getLotteryId(), userBets.getRuleId(), type, amount, thisTime.toSimpleDate());
/*     */       }
/*     */     } catch (Exception e) {
/* 106 */       log.error("写入账单失败！", e);
/* 107 */       throw e;
/*     */     }
/* 109 */     return flag;
/*     */   }
/*     */   
/*     */   public boolean addProxyReturnBill(UserBets userBets, int upId, double amount, String remarks)
/*     */   {
/* 114 */     boolean flag = false;
/*     */     try {
/* 116 */       User newestUser = this.userDao.getById(upId);
/*     */       
/* 118 */       String billno = billno();
/* 119 */       int userId = newestUser.getId();
/* 120 */       int type = 9;
/* 121 */       int refType = type;
/* 122 */       int account = 2;
/* 123 */       String refId = String.valueOf(userBets.getId());
/* 124 */       double money = amount;
/* 125 */       double beforeMoney = newestUser.getLotteryMoney();
/* 126 */       if (beforeMoney < 0.0D) {
/* 127 */         beforeMoney = 0.0D;
/*     */       }
/* 129 */       double afterMoney = newestUser.getLotteryMoney() + money;
/* 130 */       if (afterMoney < 0.0D) {
/* 131 */         afterMoney = 0.0D;
/*     */       }
/* 133 */       Moment thisTime = new Moment();
/* 134 */       UserBill tmpBill = new UserBill(billno, userId, account, type, money, beforeMoney, afterMoney, Integer.valueOf(refType), refId, thisTime.toSimpleTime(), remarks);
/* 135 */       flag = this.uBillDao.add(tmpBill);
/* 136 */       if (flag)
/*     */       {
/* 138 */         this.uLotteryReportService.update(userId, type, money, thisTime.toSimpleDate());
/* 139 */         this.uLotteryDetailsReportService.update(userId, userBets.getLotteryId(), userBets.getRuleId(), type, amount, thisTime.toSimpleDate());
/*     */       }
/*     */     } catch (Exception e) {
/* 142 */       log.error("写入账单失败！", e);
/* 143 */       throw e;
/*     */     }
/* 145 */     return flag;
/*     */   }
/*     */   
/*     */   public boolean addSpendReturnBill(UserBets userBets, double amount, String remarks)
/*     */   {
/* 150 */     boolean flag = false;
/*     */     try {
/* 152 */       User newestUser = this.userDao.getById(userBets.getUserId());
/* 153 */       String billno = billno();
/* 154 */       int userId = newestUser.getId();
/* 155 */       int type = 8;
/* 156 */       int refType = type;
/* 157 */       int account = 2;
/* 158 */       String refId = String.valueOf(userBets.getId());
/* 159 */       double money = amount;
/* 160 */       double beforeMoney = newestUser.getLotteryMoney();
/* 161 */       if (beforeMoney < 0.0D) {
/* 162 */         beforeMoney = 0.0D;
/*     */       }
/* 164 */       double afterMoney = newestUser.getLotteryMoney() + money;
/* 165 */       if (afterMoney < 0.0D) {
/* 166 */         afterMoney = 0.0D;
/*     */       }
/* 168 */       Moment thisTime = new Moment();
/* 169 */       UserBill tmpBill = new UserBill(billno, userId, account, type, money, beforeMoney, afterMoney, Integer.valueOf(refType), refId, thisTime.toSimpleTime(), remarks);
/* 170 */       flag = this.uBillDao.add(tmpBill);
/* 171 */       if (flag) {
/* 172 */         this.uLotteryReportService.update(userId, type, money, thisTime.toSimpleDate());
/* 173 */         this.uLotteryDetailsReportService.update(userId, userBets.getLotteryId(), userBets.getRuleId(), type, amount, thisTime.toSimpleDate());
/*     */       }
/*     */     } catch (Exception e) {
/* 176 */       log.error("写入账单失败！", e);
/* 177 */       throw e;
/*     */     }
/* 179 */     return flag;
/*     */   }
/*     */   
/*     */   public boolean addSpendBill(UserBets bBean)
/*     */   {
/* 184 */     boolean flag = false;
/*     */     try {
/* 186 */       User newestUser = this.userDao.getById(bBean.getUserId());
/*     */       
/* 188 */       String billno = billno();
/* 189 */       int userId = newestUser.getId();
/* 190 */       int account = 2;
/* 191 */       int type = 6;
/* 192 */       double money = bBean.getMoney();
/*     */       
/* 194 */       double beforeMoney = newestUser.getLotteryMoney() + money;
/* 195 */       double afterMoney = newestUser.getLotteryMoney();
/*     */       
/* 197 */       if (beforeMoney <= 0.0D) {
/* 198 */         beforeMoney = 0.0D;
/*     */       }
/* 200 */       if (afterMoney <= 0.0D) {
/* 201 */         afterMoney = 0.0D;
/*     */       }
/*     */       
/* 204 */       Integer refType = Integer.valueOf(type);
/* 205 */       String refId = String.valueOf(bBean.getId());
/* 206 */       Moment thisTime = new Moment();
/* 207 */       String remarks = "用户投注：" + bBean.getExpect();
/* 208 */       UserBill tmpBill = new UserBill(billno, userId, account, type, money, beforeMoney, afterMoney, refType, refId, thisTime.toSimpleTime(), remarks);
/* 209 */       flag = this.uBillDao.add(tmpBill);
/* 210 */       if (flag) {
/* 211 */         this.uLotteryDetailsReportService.update(userId, bBean.getLotteryId(), bBean.getRuleId(), type, money, thisTime.toSimpleDate());
/*     */       }
/*     */     } catch (Exception e) {
/* 214 */       log.error("写入彩票消费失败！", e);
/* 215 */       throw e;
/*     */     }
/* 217 */     return flag;
/*     */   }
/*     */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/content/biz/impl/UserBillServiceImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */