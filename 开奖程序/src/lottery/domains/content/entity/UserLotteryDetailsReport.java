/*     */ package lottery.domains.content.entity;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import javax.persistence.Column;
/*     */ import javax.persistence.Entity;
/*     */ import javax.persistence.GeneratedValue;
/*     */ import javax.persistence.GenerationType;
/*     */ import javax.persistence.Id;
/*     */ import javax.persistence.Table;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Entity
/*     */ @Table(name="user_lottery_details_report", catalog="ecai", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"user_id", "lottery_id", "rule_id", "time"})})
/*     */ public class UserLotteryDetailsReport
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private int id;
/*     */   private int userId;
/*     */   private int lotteryId;
/*     */   private int ruleId;
/*     */   private double spend;
/*     */   private double prize;
/*     */   private double spendReturn;
/*     */   private double proxyReturn;
/*     */   private double cancelOrder;
/*     */   private double billingOrder;
/*     */   private String time;
/*     */   
/*     */   public UserLotteryDetailsReport() {}
/*     */   
/*     */   public UserLotteryDetailsReport(int userId, int lotteryId, int ruleId, double spend, double prize, double spendReturn, double proxyReturn, double cancelOrder, double billingOrder, String time)
/*     */   {
/*  46 */     this.userId = userId;
/*  47 */     this.lotteryId = lotteryId;
/*  48 */     this.ruleId = ruleId;
/*  49 */     this.spend = spend;
/*  50 */     this.prize = prize;
/*  51 */     this.spendReturn = spendReturn;
/*  52 */     this.proxyReturn = proxyReturn;
/*  53 */     this.cancelOrder = cancelOrder;
/*  54 */     this.billingOrder = billingOrder;
/*  55 */     this.time = time;
/*     */   }
/*     */   
/*     */   @Id
/*     */   @GeneratedValue(strategy=GenerationType.IDENTITY)
/*     */   @Column(name="id", unique=true, nullable=false)
/*     */   public int getId()
/*     */   {
/*  63 */     return this.id;
/*     */   }
/*     */   
/*     */   public void setId(int id) {
/*  67 */     this.id = id;
/*     */   }
/*     */   
/*     */   @Column(name="user_id", nullable=false)
/*     */   public int getUserId() {
/*  72 */     return this.userId;
/*     */   }
/*     */   
/*     */   public void setUserId(int userId) {
/*  76 */     this.userId = userId;
/*     */   }
/*     */   
/*     */   @Column(name="lottery_id", nullable=false)
/*     */   public int getLotteryId() {
/*  81 */     return this.lotteryId;
/*     */   }
/*     */   
/*     */   public void setLotteryId(int lotteryId) {
/*  85 */     this.lotteryId = lotteryId;
/*     */   }
/*     */   
/*     */   @Column(name="rule_id", nullable=false)
/*     */   public int getRuleId() {
/*  90 */     return this.ruleId;
/*     */   }
/*     */   
/*     */   public void setRuleId(int ruleId) {
/*  94 */     this.ruleId = ruleId;
/*     */   }
/*     */   
/*     */   @Column(name="spend", nullable=false, precision=16, scale=5)
/*     */   public double getSpend() {
/*  99 */     return this.spend;
/*     */   }
/*     */   
/*     */   public void setSpend(double spend) {
/* 103 */     this.spend = spend;
/*     */   }
/*     */   
/*     */   @Column(name="prize", nullable=false, precision=16, scale=5)
/*     */   public double getPrize() {
/* 108 */     return this.prize;
/*     */   }
/*     */   
/*     */   public void setPrize(double prize) {
/* 112 */     this.prize = prize;
/*     */   }
/*     */   
/*     */   @Column(name="spend_return", nullable=false, precision=16, scale=5)
/*     */   public double getSpendReturn() {
/* 117 */     return this.spendReturn;
/*     */   }
/*     */   
/*     */   public void setSpendReturn(double spendReturn) {
/* 121 */     this.spendReturn = spendReturn;
/*     */   }
/*     */   
/*     */   @Column(name="proxy_return", nullable=false, precision=16, scale=5)
/*     */   public double getProxyReturn() {
/* 126 */     return this.proxyReturn;
/*     */   }
/*     */   
/*     */   public void setProxyReturn(double proxyReturn) {
/* 130 */     this.proxyReturn = proxyReturn;
/*     */   }
/*     */   
/*     */   @Column(name="cancel_order", nullable=false, precision=16, scale=5)
/*     */   public double getCancelOrder() {
/* 135 */     return this.cancelOrder;
/*     */   }
/*     */   
/*     */   public void setCancelOrder(double cancelOrder) {
/* 139 */     this.cancelOrder = cancelOrder;
/*     */   }
/*     */   
/*     */   @Column(name="billing_order", nullable=false, precision=16, scale=5)
/*     */   public double getBillingOrder() {
/* 144 */     return this.billingOrder;
/*     */   }
/*     */   
/*     */   public void setBillingOrder(double billingOrder) {
/* 148 */     this.billingOrder = billingOrder;
/*     */   }
/*     */   
/*     */   @Column(name="time", nullable=false, length=10)
/*     */   public String getTime() {
/* 153 */     return this.time;
/*     */   }
/*     */   
/*     */   public void setTime(String time) {
/* 157 */     this.time = time;
/*     */   }
/*     */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/content/entity/UserLotteryDetailsReport.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */