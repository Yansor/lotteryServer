/*    */ package lottery.domains.content.biz.impl;
/*    */ 
/*    */ import lottery.domains.content.biz.UserLotteryReportService;
/*    */ import lottery.domains.content.dao.UserLotteryReportDao;
/*    */ import lottery.domains.content.entity.UserLotteryReport;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Service;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Service
/*    */ public class UserLotteryReportServiceImpl
/*    */   implements UserLotteryReportService
/*    */ {
/*    */   @Autowired
/*    */   private UserLotteryReportDao uLotteryReportDao;
/*    */   
/*    */   public boolean update(int userId, int type, double amount, String time)
/*    */   {
/* 21 */     UserLotteryReport entity = new UserLotteryReport();
/* 22 */     switch (type) {
/*    */     case 3: 
/* 24 */       entity.setTransIn(amount);
/* 25 */       break;
/*    */     case 4: 
/* 27 */       entity.setTransOut(amount);
/* 28 */       break;
/*    */     case 6: 
/* 30 */       entity.setSpend(amount);
/* 31 */       break;
/*    */     case 7: 
/* 33 */       entity.setPrize(amount);
/* 34 */       break;
/*    */     case 8: 
/* 36 */       entity.setSpendReturn(amount);
/* 37 */       break;
/*    */     case 9: 
/* 39 */       entity.setProxyReturn(amount);
/* 40 */       break;
/*    */     case 10: 
/* 42 */       entity.setCancelOrder(amount);
/* 43 */       break;
/*    */     case 12: 
/* 45 */       entity.setDividend(amount);
/* 46 */       break;
/*    */     case 5: 
/*    */     case 17: 
/* 49 */       entity.setActivity(amount);
/* 50 */       break;
/*    */     case 21: 
/* 52 */       entity.setPacket(amount);
/* 53 */       break;
/*    */     case 22: 
/* 55 */       entity.setBillingOrder(amount);
/* 56 */       break;
/*    */     case 11: case 13: case 14: case 15: case 16: case 18: case 19: case 20: default: 
/* 58 */       return false;
/*    */     }
/* 60 */     UserLotteryReport bean = this.uLotteryReportDao.get(userId, time);
/* 61 */     if (bean != null) {
/* 62 */       entity.setId(bean.getId());
/* 63 */       return this.uLotteryReportDao.update(entity);
/*    */     }
/* 65 */     entity.setUserId(userId);
/* 66 */     entity.setTime(time);
/* 67 */     return this.uLotteryReportDao.add(entity);
/*    */   }
/*    */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/content/biz/impl/UserLotteryReportServiceImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */