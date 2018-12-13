/*    */ package lottery.domains.content.vo.bets;
/*    */ 
/*    */ import java.util.List;
/*    */ import lottery.domains.content.entity.LotteryOpenCode;
/*    */ import lottery.domains.content.entity.UserBets;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class UserBetsAutoCancelAdapter
/*    */ {
/*    */   private LotteryOpenCode lotteryOpenCode;
/*    */   private List<UserBets> userBetsList;
/*    */   
/*    */   public UserBetsAutoCancelAdapter(LotteryOpenCode lotteryOpenCode, List<UserBets> userBetsList)
/*    */   {
/* 16 */     this.lotteryOpenCode = lotteryOpenCode;
/* 17 */     this.userBetsList = userBetsList;
/*    */   }
/*    */   
/*    */   public LotteryOpenCode getLotteryOpenCode() {
/* 21 */     return this.lotteryOpenCode;
/*    */   }
/*    */   
/*    */   public void setLotteryOpenCode(LotteryOpenCode lotteryOpenCode) {
/* 25 */     this.lotteryOpenCode = lotteryOpenCode;
/*    */   }
/*    */   
/*    */   public List<UserBets> getUserBetsList() {
/* 29 */     return this.userBetsList;
/*    */   }
/*    */   
/*    */   public void setUserBetsList(List<UserBets> userBetsList) {
/* 33 */     this.userBetsList = userBetsList;
/*    */   }
/*    */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/content/vo/bets/UserBetsAutoCancelAdapter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */