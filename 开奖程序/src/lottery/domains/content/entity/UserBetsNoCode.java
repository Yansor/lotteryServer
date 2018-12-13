/*     */ package lottery.domains.content.entity;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import javax.persistence.Column;
/*     */ import javax.persistence.Entity;
/*     */ import javax.persistence.GeneratedValue;
/*     */ import javax.persistence.GenerationType;
/*     */ import javax.persistence.Id;
/*     */ 
/*     */ @Entity
/*     */ @javax.persistence.Table(name="user_bets", catalog="ecai", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"billno", "user_id"})})
/*     */ public class UserBetsNoCode implements Serializable, Cloneable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private int id;
/*     */   private String billno;
/*     */   private int userId;
/*     */   private int type;
/*     */   private int lotteryId;
/*     */   private String expect;
/*     */   private int ruleId;
/*     */   private int nums;
/*     */   private String model;
/*     */   private int multiple;
/*     */   private int code;
/*     */   private double point;
/*     */   private double money;
/*     */   private String time;
/*     */   private String stopTime;
/*     */   private String openTime;
/*     */   private int status;
/*     */   private String openCode;
/*     */   private Double prizeMoney;
/*     */   private String prizeTime;
/*     */   private String chaseBillno;
/*     */   private Integer chaseStop;
/*     */   private String planBillno;
/*     */   private Double rewardMoney;
/*     */   
/*     */   @Id
/*     */   @GeneratedValue(strategy=GenerationType.IDENTITY)
/*     */   @Column(name="id", unique=true, nullable=false)
/*     */   public int getId()
/*     */   {
/*  45 */     return this.id;
/*     */   }
/*     */   
/*     */   public void setId(int id) {
/*  49 */     this.id = id;
/*     */   }
/*     */   
/*     */   @Column(name="billno", nullable=false, length=32)
/*     */   public String getBillno() {
/*  54 */     return this.billno;
/*     */   }
/*     */   
/*     */   public void setBillno(String billno) {
/*  58 */     this.billno = billno;
/*     */   }
/*     */   
/*     */   @Column(name="user_id", nullable=false)
/*     */   public int getUserId() {
/*  63 */     return this.userId;
/*     */   }
/*     */   
/*     */   public void setUserId(int userId) {
/*  67 */     this.userId = userId;
/*     */   }
/*     */   
/*     */   @Column(name="type", nullable=false)
/*     */   public int getType() {
/*  72 */     return this.type;
/*     */   }
/*     */   
/*     */   public void setType(int type) {
/*  76 */     this.type = type;
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
/*     */   @Column(name="expect", nullable=false, length=32)
/*     */   public String getExpect() {
/*  90 */     return this.expect;
/*     */   }
/*     */   
/*     */   public void setExpect(String expect) {
/*  94 */     this.expect = expect;
/*     */   }
/*     */   
/*     */   @Column(name="rule_id", nullable=false)
/*     */   public int getRuleId() {
/*  99 */     return this.ruleId;
/*     */   }
/*     */   
/*     */   public void setRuleId(int ruleId) {
/* 103 */     this.ruleId = ruleId;
/*     */   }
/*     */   
/*     */   @Column(name="nums", nullable=false)
/*     */   public int getNums() {
/* 108 */     return this.nums;
/*     */   }
/*     */   
/*     */   public void setNums(int nums) {
/* 112 */     this.nums = nums;
/*     */   }
/*     */   
/*     */   @Column(name="model", nullable=false, length=16)
/*     */   public String getModel() {
/* 117 */     return this.model;
/*     */   }
/*     */   
/*     */   public void setModel(String model) {
/* 121 */     this.model = model;
/*     */   }
/*     */   
/*     */   @Column(name="multiple", nullable=false)
/*     */   public int getMultiple() {
/* 126 */     return this.multiple;
/*     */   }
/*     */   
/*     */   public void setMultiple(int multiple) {
/* 130 */     this.multiple = multiple;
/*     */   }
/*     */   
/*     */   @Column(name="code", nullable=false)
/*     */   public int getCode() {
/* 135 */     return this.code;
/*     */   }
/*     */   
/*     */   public void setCode(int code) {
/* 139 */     this.code = code;
/*     */   }
/*     */   
/*     */   @Column(name="point", nullable=false, precision=11, scale=1)
/*     */   public double getPoint() {
/* 144 */     return this.point;
/*     */   }
/*     */   
/*     */   public void setPoint(double point) {
/* 148 */     this.point = point;
/*     */   }
/*     */   
/*     */   @Column(name="money", nullable=false, precision=16, scale=5)
/*     */   public double getMoney() {
/* 153 */     return this.money;
/*     */   }
/*     */   
/*     */   public void setMoney(double money) {
/* 157 */     this.money = money;
/*     */   }
/*     */   
/*     */   @Column(name="time", nullable=false, length=19)
/*     */   public String getTime() {
/* 162 */     return this.time;
/*     */   }
/*     */   
/*     */   public void setTime(String time) {
/* 166 */     this.time = time;
/*     */   }
/*     */   
/*     */   @Column(name="stop_time", nullable=false, length=19)
/*     */   public String getStopTime() {
/* 171 */     return this.stopTime;
/*     */   }
/*     */   
/*     */   public void setStopTime(String stopTime) {
/* 175 */     this.stopTime = stopTime;
/*     */   }
/*     */   
/*     */   @Column(name="open_time", nullable=false, length=19)
/*     */   public String getOpenTime() {
/* 180 */     return this.openTime;
/*     */   }
/*     */   
/*     */   public void setOpenTime(String openTime) {
/* 184 */     this.openTime = openTime;
/*     */   }
/*     */   
/*     */   @Column(name="status", nullable=false)
/*     */   public int getStatus() {
/* 189 */     return this.status;
/*     */   }
/*     */   
/*     */   public void setStatus(int status) {
/* 193 */     this.status = status;
/*     */   }
/*     */   
/*     */   @Column(name="open_code", length=128)
/*     */   public String getOpenCode() {
/* 198 */     return this.openCode;
/*     */   }
/*     */   
/*     */   public void setOpenCode(String openCode) {
/* 202 */     this.openCode = openCode;
/*     */   }
/*     */   
/*     */   @Column(name="prize_money", precision=16, scale=5)
/*     */   public Double getPrizeMoney() {
/* 207 */     return this.prizeMoney;
/*     */   }
/*     */   
/*     */   public void setPrizeMoney(Double prizeMoney) {
/* 211 */     this.prizeMoney = prizeMoney;
/*     */   }
/*     */   
/*     */   @Column(name="prize_time", length=19)
/*     */   public String getPrizeTime() {
/* 216 */     return this.prizeTime;
/*     */   }
/*     */   
/*     */   public void setPrizeTime(String prizeTime) {
/* 220 */     this.prizeTime = prizeTime;
/*     */   }
/*     */   
/*     */   @Column(name="chase_billno", length=64)
/*     */   public String getChaseBillno() {
/* 225 */     return this.chaseBillno;
/*     */   }
/*     */   
/*     */   public void setChaseBillno(String chaseBillno) {
/* 229 */     this.chaseBillno = chaseBillno;
/*     */   }
/*     */   
/*     */   @Column(name="chase_stop")
/*     */   public Integer getChaseStop() {
/* 234 */     return this.chaseStop;
/*     */   }
/*     */   
/*     */   public void setChaseStop(Integer chaseStop) {
/* 238 */     this.chaseStop = chaseStop;
/*     */   }
/*     */   
/*     */   @Column(name="plan_billno", length=64)
/*     */   public String getPlanBillno() {
/* 243 */     return this.planBillno;
/*     */   }
/*     */   
/*     */   public void setPlanBillno(String planBillno) {
/* 247 */     this.planBillno = planBillno;
/*     */   }
/*     */   
/*     */   @Column(name="reward_money", precision=16, scale=5)
/*     */   public Double getRewardMoney() {
/* 252 */     return this.rewardMoney;
/*     */   }
/*     */   
/*     */   public void setRewardMoney(Double rewardMoney) {
/* 256 */     this.rewardMoney = rewardMoney;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public UserBets formatBean()
/*     */   {
/* 263 */     UserBets entity = new UserBets();
/* 264 */     entity.setId(this.id);
/* 265 */     entity.setBillno(this.billno);
/* 266 */     entity.setUserId(this.userId);
/* 267 */     entity.setType(this.type);
/* 268 */     entity.setLotteryId(this.lotteryId);
/* 269 */     entity.setExpect(this.expect);
/* 270 */     entity.setRuleId(this.ruleId);
/* 271 */     entity.setNums(this.nums);
/* 272 */     entity.setModel(this.model);
/* 273 */     entity.setMultiple(this.multiple);
/* 274 */     entity.setCode(this.code);
/* 275 */     entity.setPoint(this.point);
/* 276 */     entity.setMoney(this.money);
/* 277 */     entity.setTime(this.time);
/* 278 */     entity.setStopTime(this.stopTime);
/* 279 */     entity.setOpenTime(this.openTime);
/* 280 */     entity.setStatus(this.status);
/* 281 */     entity.setOpenCode(this.openCode);
/* 282 */     entity.setPrizeMoney(this.prizeMoney);
/* 283 */     entity.setPrizeTime(this.prizeTime);
/* 284 */     entity.setChaseBillno(this.chaseBillno);
/* 285 */     entity.setChaseStop(this.chaseStop);
/* 286 */     entity.setPlanBillno(this.planBillno);
/* 287 */     entity.setRewardMoney(this.rewardMoney);
/* 288 */     return entity;
/*     */   }
/*     */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/content/entity/UserBetsNoCode.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */