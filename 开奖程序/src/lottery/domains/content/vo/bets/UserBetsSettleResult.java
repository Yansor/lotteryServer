/*     */ package lottery.domains.content.vo.bets;
/*     */ 
/*     */ import java.util.List;
/*     */ import lottery.domains.content.entity.User;
/*     */ import lottery.domains.content.entity.UserBets;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class UserBetsSettleResult
/*     */ {
/*     */   private User user;
/*     */   private Integer userBetsStatus;
/*     */   private boolean newSettle;
/*     */   private String settleTime;
/*     */   private String openCode;
/*     */   private Double prize;
/*     */   private UserBets userBets;
/*     */   private Double lotteryMoney;
/*     */   private Double freezeMoney;
/*     */   private List<UserBetsSettleBill> bills;
/*     */   private List<UserBetsSettleUpPoint> upPoints;
/*     */   
/*     */   public User getUser()
/*     */   {
/*  26 */     return this.user;
/*     */   }
/*     */   
/*     */   public void setUser(User user) {
/*  30 */     this.user = user;
/*     */   }
/*     */   
/*     */   public Integer getUserBetsStatus() {
/*  34 */     return this.userBetsStatus;
/*     */   }
/*     */   
/*     */   public void setUserBetsStatus(Integer userBetsStatus) {
/*  38 */     this.userBetsStatus = userBetsStatus;
/*     */   }
/*     */   
/*     */   public boolean isNewSettle() {
/*  42 */     return this.newSettle;
/*     */   }
/*     */   
/*     */   public void setNewSettle(boolean newSettle) {
/*  46 */     this.newSettle = newSettle;
/*     */   }
/*     */   
/*     */   public String getSettleTime() {
/*  50 */     return this.settleTime;
/*     */   }
/*     */   
/*     */   public void setSettleTime(String settleTime) {
/*  54 */     this.settleTime = settleTime;
/*     */   }
/*     */   
/*     */   public String getOpenCode() {
/*  58 */     return this.openCode;
/*     */   }
/*     */   
/*     */   public void setOpenCode(String openCode) {
/*  62 */     this.openCode = openCode;
/*     */   }
/*     */   
/*     */   public Double getPrize() {
/*  66 */     return this.prize;
/*     */   }
/*     */   
/*     */   public void setPrize(Double prize) {
/*  70 */     this.prize = prize;
/*     */   }
/*     */   
/*     */   public UserBets getUserBets() {
/*  74 */     return this.userBets;
/*     */   }
/*     */   
/*     */   public void setUserBets(UserBets userBets) {
/*  78 */     this.userBets = userBets;
/*     */   }
/*     */   
/*     */   public Double getLotteryMoney() {
/*  82 */     return this.lotteryMoney;
/*     */   }
/*     */   
/*     */   public void setLotteryMoney(Double lotteryMoney) {
/*  86 */     this.lotteryMoney = lotteryMoney;
/*     */   }
/*     */   
/*     */   public Double getFreezeMoney() {
/*  90 */     return this.freezeMoney;
/*     */   }
/*     */   
/*     */   public void setFreezeMoney(Double freezeMoney) {
/*  94 */     this.freezeMoney = freezeMoney;
/*     */   }
/*     */   
/*     */   public List<UserBetsSettleBill> getBills() {
/*  98 */     return this.bills;
/*     */   }
/*     */   
/*     */   public void setBills(List<UserBetsSettleBill> bills) {
/* 102 */     this.bills = bills;
/*     */   }
/*     */   
/*     */   public List<UserBetsSettleUpPoint> getUpPoints() {
/* 106 */     return this.upPoints;
/*     */   }
/*     */   
/*     */   public void setUpPoints(List<UserBetsSettleUpPoint> upPoints) {
/* 110 */     this.upPoints = upPoints;
/*     */   }
/*     */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/content/vo/bets/UserBetsSettleResult.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */