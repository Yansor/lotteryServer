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
/*     */ @Table(name="user_lottery_report", catalog="ecai", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"user_id", "time"})})
/*     */ public class UserLotteryReport
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private int id;
/*     */   private int userId;
/*     */   private double transIn;
/*     */   private double transOut;
/*     */   private double spend;
/*     */   private double prize;
/*     */   private double spendReturn;
/*     */   private double proxyReturn;
/*     */   private double cancelOrder;
/*     */   private double activity;
/*     */   private double dividend;
/*     */   private double billingOrder;
/*     */   private double packet;
/*     */   private String time;
/*     */   private double rechargeFee;
/*     */   
/*     */   public UserLotteryReport() {}
/*     */   
/*     */   public UserLotteryReport(int userId, double transIn, double transOut, double spend, double prize, double spendReturn, double proxyReturn, double cancelOrder, double activity, double dividend, double billingOrder, String time)
/*     */   {
/*  50 */     this.userId = userId;
/*  51 */     this.transIn = transIn;
/*  52 */     this.transOut = transOut;
/*  53 */     this.spend = spend;
/*  54 */     this.prize = prize;
/*  55 */     this.spendReturn = spendReturn;
/*  56 */     this.proxyReturn = proxyReturn;
/*  57 */     this.cancelOrder = cancelOrder;
/*  58 */     this.activity = activity;
/*  59 */     this.dividend = dividend;
/*  60 */     this.billingOrder = billingOrder;
/*  61 */     this.time = time;
/*     */   }
/*     */   
/*     */   @Id
/*     */   @GeneratedValue(strategy=GenerationType.IDENTITY)
/*     */   @Column(name="id", unique=true, nullable=false)
/*     */   public int getId()
/*     */   {
/*  69 */     return this.id;
/*     */   }
/*     */   
/*     */   public void setId(int id) {
/*  73 */     this.id = id;
/*     */   }
/*     */   
/*     */   @Column(name="user_id", nullable=false)
/*     */   public int getUserId() {
/*  78 */     return this.userId;
/*     */   }
/*     */   
/*     */   public void setUserId(int userId) {
/*  82 */     this.userId = userId;
/*     */   }
/*     */   
/*     */   @Column(name="trans_in", nullable=false, precision=16, scale=5)
/*     */   public double getTransIn() {
/*  87 */     return this.transIn;
/*     */   }
/*     */   
/*     */   public void setTransIn(double transIn) {
/*  91 */     this.transIn = transIn;
/*     */   }
/*     */   
/*     */   @Column(name="trans_out", nullable=false, precision=16, scale=5)
/*     */   public double getTransOut() {
/*  96 */     return this.transOut;
/*     */   }
/*     */   
/*     */   public void setTransOut(double transOut) {
/* 100 */     this.transOut = transOut;
/*     */   }
/*     */   
/*     */   @Column(name="spend", nullable=false, precision=16, scale=5)
/*     */   public double getSpend() {
/* 105 */     return this.spend;
/*     */   }
/*     */   
/*     */   public void setSpend(double spend) {
/* 109 */     this.spend = spend;
/*     */   }
/*     */   
/*     */   @Column(name="prize", nullable=false, precision=16, scale=5)
/*     */   public double getPrize() {
/* 114 */     return this.prize;
/*     */   }
/*     */   
/*     */   public void setPrize(double prize) {
/* 118 */     this.prize = prize;
/*     */   }
/*     */   
/*     */   @Column(name="spend_return", nullable=false, precision=16, scale=5)
/*     */   public double getSpendReturn() {
/* 123 */     return this.spendReturn;
/*     */   }
/*     */   
/*     */   public void setSpendReturn(double spendReturn) {
/* 127 */     this.spendReturn = spendReturn;
/*     */   }
/*     */   
/*     */   @Column(name="proxy_return", nullable=false, precision=16, scale=5)
/*     */   public double getProxyReturn() {
/* 132 */     return this.proxyReturn;
/*     */   }
/*     */   
/*     */   public void setProxyReturn(double proxyReturn) {
/* 136 */     this.proxyReturn = proxyReturn;
/*     */   }
/*     */   
/*     */   @Column(name="cancel_order", nullable=false, precision=16, scale=5)
/*     */   public double getCancelOrder() {
/* 141 */     return this.cancelOrder;
/*     */   }
/*     */   
/*     */   public void setCancelOrder(double cancelOrder) {
/* 145 */     this.cancelOrder = cancelOrder;
/*     */   }
/*     */   
/*     */   @Column(name="activity", nullable=false, precision=16, scale=5)
/*     */   public double getActivity() {
/* 150 */     return this.activity;
/*     */   }
/*     */   
/*     */   public void setActivity(double activity) {
/* 154 */     this.activity = activity;
/*     */   }
/*     */   
/*     */   @Column(name="dividend", nullable=false, precision=16, scale=5)
/*     */   public double getDividend() {
/* 159 */     return this.dividend;
/*     */   }
/*     */   
/*     */   public void setDividend(double dividend) {
/* 163 */     this.dividend = dividend;
/*     */   }
/*     */   
/*     */   @Column(name="billing_order", nullable=false, precision=16, scale=5)
/*     */   public double getBillingOrder() {
/* 168 */     return this.billingOrder;
/*     */   }
/*     */   
/*     */   public void setBillingOrder(double billingOrder) {
/* 172 */     this.billingOrder = billingOrder;
/*     */   }
/*     */   
/*     */   @Column(name="time", nullable=false, length=10)
/*     */   public String getTime() {
/* 177 */     return this.time;
/*     */   }
/*     */   
/*     */   public void setTime(String time) {
/* 181 */     this.time = time;
/*     */   }
/*     */   
/*     */   @Column(name="packet", nullable=false, precision=16, scale=5)
/*     */   public double getPacket() {
/* 186 */     return this.packet;
/*     */   }
/*     */   
/*     */   public void setPacket(double packet) {
/* 190 */     this.packet = packet;
/*     */   }
/*     */   
/*     */   @Column(name="recharge_fee", nullable=false, precision=16, scale=5)
/*     */   public double getRechargeFee() {
/* 195 */     return this.rechargeFee;
/*     */   }
/*     */   
/*     */   public void setRechargeFee(double rechargeFee) {
/* 199 */     this.rechargeFee = rechargeFee;
/*     */   }
/*     */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/content/entity/UserLotteryReport.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */