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
/*     */ @Entity
/*     */ @Table(name="user_bets", catalog="ecai", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"billno", "user_id"})})
/*     */ public class UserBets
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private int id;
/*     */   private String billno;
/*     */   private int userId;
/*     */   private int type;
/*     */   private int lotteryId;
/*     */   private String expect;
/*     */   private int ruleId;
/*     */   private String codes;
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
/*     */   private int compressed;
/*     */   private int locked;
/*     */   
/*     */   public UserBets clone()
/*     */   {
/*     */     try
/*     */     {
/*  54 */       return (UserBets)super.clone();
/*     */     } catch (Exception localException) {}
/*  56 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public UserBets() {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public UserBets(String billno, int userId, int type, int lotteryId, String expect, int ruleId, String codes, int nums, String model, int multiple, int code, double point, double money, String time, String stopTime, String openTime, int status, int locked)
/*     */   {
/*  71 */     this.billno = billno;
/*  72 */     this.userId = userId;
/*  73 */     this.type = type;
/*  74 */     this.lotteryId = lotteryId;
/*  75 */     this.expect = expect;
/*  76 */     this.ruleId = ruleId;
/*  77 */     this.codes = codes;
/*  78 */     this.nums = nums;
/*  79 */     this.model = model;
/*  80 */     this.multiple = multiple;
/*  81 */     this.code = code;
/*  82 */     this.point = point;
/*  83 */     this.money = money;
/*  84 */     this.time = time;
/*  85 */     this.stopTime = stopTime;
/*  86 */     this.openTime = openTime;
/*  87 */     this.status = status;
/*  88 */     this.locked = locked;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public UserBets(String billno, int userId, int type, int lotteryId, String expect, int ruleId, String codes, int nums, String model, int multiple, int code, double point, double money, String time, String stopTime, String openTime, int status, String openCode, Double prizeMoney, String prizeTime, String chaseBillno, Integer chaseStop, String planBillno, Double rewardMoney, int locked)
/*     */   {
/*  99 */     this.billno = billno;
/* 100 */     this.userId = userId;
/* 101 */     this.type = type;
/* 102 */     this.lotteryId = lotteryId;
/* 103 */     this.expect = expect;
/* 104 */     this.ruleId = ruleId;
/* 105 */     this.codes = codes;
/* 106 */     this.nums = nums;
/* 107 */     this.model = model;
/* 108 */     this.multiple = multiple;
/* 109 */     this.code = code;
/* 110 */     this.point = point;
/* 111 */     this.money = money;
/* 112 */     this.time = time;
/* 113 */     this.stopTime = stopTime;
/* 114 */     this.openTime = openTime;
/* 115 */     this.status = status;
/* 116 */     this.openCode = openCode;
/* 117 */     this.prizeMoney = prizeMoney;
/* 118 */     this.prizeTime = prizeTime;
/* 119 */     this.chaseBillno = chaseBillno;
/* 120 */     this.chaseStop = chaseStop;
/* 121 */     this.planBillno = planBillno;
/* 122 */     this.rewardMoney = rewardMoney;
/* 123 */     this.locked = locked;
/*     */   }
/*     */   
/*     */   @Id
/*     */   @GeneratedValue(strategy=GenerationType.IDENTITY)
/*     */   @Column(name="id", unique=true, nullable=false)
/*     */   public int getId()
/*     */   {
/* 131 */     return this.id;
/*     */   }
/*     */   
/*     */   public void setId(int id) {
/* 135 */     this.id = id;
/*     */   }
/*     */   
/*     */   @Column(name="billno", nullable=false, length=32)
/*     */   public String getBillno() {
/* 140 */     return this.billno;
/*     */   }
/*     */   
/*     */   public void setBillno(String billno) {
/* 144 */     this.billno = billno;
/*     */   }
/*     */   
/*     */   @Column(name="user_id", nullable=false)
/*     */   public int getUserId() {
/* 149 */     return this.userId;
/*     */   }
/*     */   
/*     */   public void setUserId(int userId) {
/* 153 */     this.userId = userId;
/*     */   }
/*     */   
/*     */   @Column(name="type", nullable=false)
/*     */   public int getType() {
/* 158 */     return this.type;
/*     */   }
/*     */   
/*     */   public void setType(int type) {
/* 162 */     this.type = type;
/*     */   }
/*     */   
/*     */   @Column(name="lottery_id", nullable=false)
/*     */   public int getLotteryId() {
/* 167 */     return this.lotteryId;
/*     */   }
/*     */   
/*     */   public void setLotteryId(int lotteryId) {
/* 171 */     this.lotteryId = lotteryId;
/*     */   }
/*     */   
/*     */   @Column(name="expect", nullable=false, length=32)
/*     */   public String getExpect() {
/* 176 */     return this.expect;
/*     */   }
/*     */   
/*     */   public void setExpect(String expect) {
/* 180 */     this.expect = expect;
/*     */   }
/*     */   
/*     */   @Column(name="rule_id", nullable=false)
/*     */   public int getRuleId() {
/* 185 */     return this.ruleId;
/*     */   }
/*     */   
/*     */   public void setRuleId(int ruleId) {
/* 189 */     this.ruleId = ruleId;
/*     */   }
/*     */   
/*     */   @Column(name="codes", nullable=false, length=16777215)
/*     */   public String getCodes() {
/* 194 */     return this.codes;
/*     */   }
/*     */   
/*     */   public void setCodes(String codes) {
/* 198 */     this.codes = codes;
/*     */   }
/*     */   
/*     */   @Column(name="nums", nullable=false)
/*     */   public int getNums() {
/* 203 */     return this.nums;
/*     */   }
/*     */   
/*     */   public void setNums(int nums) {
/* 207 */     this.nums = nums;
/*     */   }
/*     */   
/*     */   @Column(name="model", nullable=false, length=16)
/*     */   public String getModel() {
/* 212 */     return this.model;
/*     */   }
/*     */   
/*     */   public void setModel(String model) {
/* 216 */     this.model = model;
/*     */   }
/*     */   
/*     */   @Column(name="multiple", nullable=false)
/*     */   public int getMultiple() {
/* 221 */     return this.multiple;
/*     */   }
/*     */   
/*     */   public void setMultiple(int multiple) {
/* 225 */     this.multiple = multiple;
/*     */   }
/*     */   
/*     */   @Column(name="code", nullable=false)
/*     */   public int getCode() {
/* 230 */     return this.code;
/*     */   }
/*     */   
/*     */   public void setCode(int code) {
/* 234 */     this.code = code;
/*     */   }
/*     */   
/*     */   @Column(name="point", nullable=false, precision=11, scale=1)
/*     */   public double getPoint() {
/* 239 */     return this.point;
/*     */   }
/*     */   
/*     */   public void setPoint(double point) {
/* 243 */     this.point = point;
/*     */   }
/*     */   
/*     */   @Column(name="money", nullable=false, precision=16, scale=5)
/*     */   public double getMoney() {
/* 248 */     return this.money;
/*     */   }
/*     */   
/*     */   public void setMoney(double money) {
/* 252 */     this.money = money;
/*     */   }
/*     */   
/*     */   @Column(name="time", nullable=false, length=19)
/*     */   public String getTime() {
/* 257 */     return this.time;
/*     */   }
/*     */   
/*     */   public void setTime(String time) {
/* 261 */     this.time = time;
/*     */   }
/*     */   
/*     */   @Column(name="stop_time", nullable=false, length=19)
/*     */   public String getStopTime() {
/* 266 */     return this.stopTime;
/*     */   }
/*     */   
/*     */   public void setStopTime(String stopTime) {
/* 270 */     this.stopTime = stopTime;
/*     */   }
/*     */   
/*     */   @Column(name="open_time", nullable=false, length=19)
/*     */   public String getOpenTime() {
/* 275 */     return this.openTime;
/*     */   }
/*     */   
/*     */   public void setOpenTime(String openTime) {
/* 279 */     this.openTime = openTime;
/*     */   }
/*     */   
/*     */   @Column(name="status", nullable=false)
/*     */   public int getStatus() {
/* 284 */     return this.status;
/*     */   }
/*     */   
/*     */   public void setStatus(int status) {
/* 288 */     this.status = status;
/*     */   }
/*     */   
/*     */   @Column(name="open_code", length=128)
/*     */   public String getOpenCode() {
/* 293 */     return this.openCode;
/*     */   }
/*     */   
/*     */   public void setOpenCode(String openCode) {
/* 297 */     this.openCode = openCode;
/*     */   }
/*     */   
/*     */   @Column(name="prize_money", precision=16, scale=5)
/*     */   public Double getPrizeMoney() {
/* 302 */     return this.prizeMoney;
/*     */   }
/*     */   
/*     */   public void setPrizeMoney(Double prizeMoney) {
/* 306 */     this.prizeMoney = prizeMoney;
/*     */   }
/*     */   
/*     */   @Column(name="prize_time", length=19)
/*     */   public String getPrizeTime() {
/* 311 */     return this.prizeTime;
/*     */   }
/*     */   
/*     */   public void setPrizeTime(String prizeTime) {
/* 315 */     this.prizeTime = prizeTime;
/*     */   }
/*     */   
/*     */   @Column(name="chase_billno", length=64)
/*     */   public String getChaseBillno() {
/* 320 */     return this.chaseBillno;
/*     */   }
/*     */   
/*     */   public void setChaseBillno(String chaseBillno) {
/* 324 */     this.chaseBillno = chaseBillno;
/*     */   }
/*     */   
/*     */   @Column(name="chase_stop")
/*     */   public Integer getChaseStop() {
/* 329 */     return this.chaseStop;
/*     */   }
/*     */   
/*     */   public void setChaseStop(Integer chaseStop) {
/* 333 */     this.chaseStop = chaseStop;
/*     */   }
/*     */   
/*     */   @Column(name="plan_billno", length=64)
/*     */   public String getPlanBillno() {
/* 338 */     return this.planBillno;
/*     */   }
/*     */   
/*     */   public void setPlanBillno(String planBillno) {
/* 342 */     this.planBillno = planBillno;
/*     */   }
/*     */   
/*     */   @Column(name="reward_money", precision=16, scale=5)
/*     */   public Double getRewardMoney() {
/* 347 */     return this.rewardMoney;
/*     */   }
/*     */   
/*     */   public void setRewardMoney(Double rewardMoney) {
/* 351 */     this.rewardMoney = rewardMoney;
/*     */   }
/*     */   
/*     */   @Column(name="compressed")
/*     */   public int getCompressed() {
/* 356 */     return this.compressed;
/*     */   }
/*     */   
/*     */   public void setCompressed(int compressed) {
/* 360 */     this.compressed = compressed;
/*     */   }
/*     */   
/*     */   @Column(name="locked", nullable=false)
/*     */   public int getLocked() {
/* 365 */     return this.locked;
/*     */   }
/*     */   
/*     */   public void setLocked(int locked) {
/* 369 */     this.locked = locked;
/*     */   }
/*     */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/content/entity/UserBets.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */