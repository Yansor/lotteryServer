/*    */ package lottery.domains.content.vo.bets;
/*    */ 
/*    */ import lottery.domains.content.entity.User;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class UserBetsSettleBill
/*    */ {
/*    */   private User user;
/*    */   private Integer account;
/*    */   private Double amount;
/*    */   private Integer refType;
/*    */   private Integer refId;
/*    */   private String remarks;
/*    */   
/*    */   public User getUser()
/*    */   {
/* 18 */     return this.user;
/*    */   }
/*    */   
/*    */   public void setUser(User user) {
/* 22 */     this.user = user;
/*    */   }
/*    */   
/*    */   public Integer getAccount() {
/* 26 */     return this.account;
/*    */   }
/*    */   
/*    */   public void setAccount(Integer account) {
/* 30 */     this.account = account;
/*    */   }
/*    */   
/*    */   public Double getAmount() {
/* 34 */     return this.amount;
/*    */   }
/*    */   
/*    */   public void setAmount(Double amount) {
/* 38 */     this.amount = amount;
/*    */   }
/*    */   
/*    */   public Integer getRefType() {
/* 42 */     return this.refType;
/*    */   }
/*    */   
/*    */   public void setRefType(Integer refType) {
/* 46 */     this.refType = refType;
/*    */   }
/*    */   
/*    */   public Integer getRefId() {
/* 50 */     return this.refId;
/*    */   }
/*    */   
/*    */   public void setRefId(Integer refId) {
/* 54 */     this.refId = refId;
/*    */   }
/*    */   
/*    */   public String getRemarks() {
/* 58 */     return this.remarks;
/*    */   }
/*    */   
/*    */   public void setRemarks(String remarks) {
/* 62 */     this.remarks = remarks;
/*    */   }
/*    */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/content/vo/bets/UserBetsSettleBill.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */