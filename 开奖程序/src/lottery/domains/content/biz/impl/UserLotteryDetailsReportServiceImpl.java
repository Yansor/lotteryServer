/*    */ package lottery.domains.content.biz.impl;
/*    */ 
/*    */ import java.util.List;
/*    */ import lottery.domains.content.biz.UserLotteryDetailsReportService;
/*    */ import lottery.domains.content.dao.UserLotteryDetailsReportDao;
/*    */ import lottery.domains.content.entity.UserLotteryDetailsReport;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Service;
/*    */ 
/*    */ 
/*    */ @Service
/*    */ public class UserLotteryDetailsReportServiceImpl
/*    */   implements UserLotteryDetailsReportService
/*    */ {
/*    */   @Autowired
/*    */   private UserLotteryDetailsReportDao uLotteryDetailsReportDao;
/*    */   
/*    */   public void addDetailsReports(List<UserLotteryDetailsReport> detailsReports)
/*    */   {
/* 20 */     this.uLotteryDetailsReportDao.addDetailsReports(detailsReports);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public boolean update(int userId, int lotteryId, int ruleId, int type, double amount, String time)
/*    */   {
/* 27 */     UserLotteryDetailsReport entity = new UserLotteryDetailsReport();
/* 28 */     switch (type) {
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
/*    */     case 22: 
/* 45 */       entity.setBillingOrder(amount);
/* 46 */       break;
/*    */     case 11: case 12: case 13: case 14: case 15: case 16: case 17: case 18: case 19: case 20: case 21: default: 
/* 48 */       return false;
/*    */     }
/* 50 */     UserLotteryDetailsReport bean = this.uLotteryDetailsReportDao.get(userId, lotteryId, ruleId, time);
/* 51 */     if (bean != null) {
/* 52 */       entity.setId(bean.getId());
/* 53 */       return this.uLotteryDetailsReportDao.update(entity);
/*    */     }
/* 55 */     entity.setUserId(userId);
/* 56 */     entity.setLotteryId(lotteryId);
/* 57 */     entity.setRuleId(ruleId);
/* 58 */     entity.setTime(time);
/* 59 */     return this.uLotteryDetailsReportDao.add(entity);
/*    */   }
/*    */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/content/biz/impl/UserLotteryDetailsReportServiceImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */