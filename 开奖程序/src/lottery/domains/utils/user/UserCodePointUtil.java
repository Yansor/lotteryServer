/*    */ package lottery.domains.utils.user;
/*    */ 
/*    */ import lottery.domains.content.dao.UserDao;
/*    */ import lottery.domains.content.entity.User;
/*    */ import lottery.domains.content.vo.config.CodeConfig;
/*    */ import lottery.domains.pool.DataFactory;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Component;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Component
/*    */ public class UserCodePointUtil
/*    */ {
/*    */   @Autowired
/*    */   private UserDao uDao;
/*    */   @Autowired
/*    */   private DataFactory dataFactory;
/*    */   
/*    */   public boolean isLevel1Proxy(User uBean)
/*    */   {
/* 26 */     if ((uBean.getType() == 1) && 
/* 27 */       (uBean.getCode() == this.dataFactory.getCodeConfig().getSysCode()) && 
/* 28 */       (uBean.getUpid() == 72)) {
/* 29 */       return true;
/*    */     }
/* 31 */     return false;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public boolean isLevel2Proxy(User uBean)
/*    */   {
/* 38 */     if ((uBean.getType() == 1) && 
/* 39 */       (uBean.getCode() == this.dataFactory.getCodeConfig().getSysCode()) && 
/* 40 */       (uBean.getUpid() != 0) && 
/* 41 */       (uBean.getUpid() != 72)) {
/* 42 */       User upBean = this.uDao.getById(uBean.getUpid());
/* 43 */       if (isLevel1Proxy(upBean)) {
/* 44 */         return true;
/*    */       }
/*    */     }
/* 47 */     return false;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public boolean isLevel3Proxy(User uBean)
/*    */   {
/* 54 */     if ((uBean.getType() == 1) && 
/* 55 */       (uBean.getCode() == this.dataFactory.getCodeConfig().getSysCode()) && 
/* 56 */       (uBean.getUpid() != 0) && 
/* 57 */       (uBean.getUpid() != 72)) {
/* 58 */       User upBean = this.uDao.getById(uBean.getUpid());
/* 59 */       if (isLevel2Proxy(upBean)) {
/* 60 */         return true;
/*    */       }
/*    */     }
/* 63 */     return false;
/*    */   }
/*    */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/utils/user/UserCodePointUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */