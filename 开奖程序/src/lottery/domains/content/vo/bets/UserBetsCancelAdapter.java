/*    */ package lottery.domains.content.vo.bets;
/*    */ 
/*    */ 
/*    */ public class UserBetsCancelAdapter
/*    */ {
/*    */   private String chaseBillno;
/*    */   private int userId;
/*    */   private String winExpect;
/*    */   
/*    */   public UserBetsCancelAdapter(String chaseBillno, int userId, String winExpect)
/*    */   {
/* 12 */     this.chaseBillno = chaseBillno;
/* 13 */     this.userId = userId;
/* 14 */     this.winExpect = winExpect;
/*    */   }
/*    */   
/*    */   public String getChaseBillno() {
/* 18 */     return this.chaseBillno;
/*    */   }
/*    */   
/*    */   public void setChaseBillno(String chaseBillno) {
/* 22 */     this.chaseBillno = chaseBillno;
/*    */   }
/*    */   
/*    */   public int getUserId() {
/* 26 */     return this.userId;
/*    */   }
/*    */   
/*    */   public void setUserId(int userId) {
/* 30 */     this.userId = userId;
/*    */   }
/*    */   
/*    */   public String getWinExpect() {
/* 34 */     return this.winExpect;
/*    */   }
/*    */   
/*    */   public void setWinExpect(String winExpect) {
/* 38 */     this.winExpect = winExpect;
/*    */   }
/*    */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/content/vo/bets/UserBetsCancelAdapter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */