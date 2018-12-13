/*     */ package lottery.domains.content.entity;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import javautils.encrypt.UserBetsEncrypt;
/*     */ import javax.persistence.Column;
/*     */ import javax.persistence.Entity;
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
/*     */ @Entity
/*     */ @Table(name="user_bets_original", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"billno", "user_id"})})
/*     */ public class UserBetsOriginal
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
/*     */   private String identification;
/*     */   private String certification;
/*     */   private int compressed;
/*     */   
/*     */   public UserBetsOriginal clone()
/*     */   {
/*     */     try
/*     */     {
/*  56 */       return (UserBetsOriginal)super.clone();
/*     */     } catch (Exception localException) {}
/*  58 */     return null;
/*     */   }
/*     */   
/*     */   public UserBetsOriginal(UserBets userBets) {
/*  62 */     this.id = userBets.getId();
/*  63 */     this.billno = userBets.getBillno();
/*  64 */     this.userId = userBets.getUserId();
/*  65 */     this.type = userBets.getType();
/*  66 */     this.lotteryId = userBets.getLotteryId();
/*  67 */     this.expect = userBets.getExpect();
/*  68 */     this.ruleId = userBets.getRuleId();
/*  69 */     this.codes = userBets.getCodes();
/*  70 */     this.nums = userBets.getNums();
/*  71 */     this.model = userBets.getModel();
/*  72 */     this.multiple = userBets.getMultiple();
/*  73 */     this.code = userBets.getCode();
/*  74 */     this.point = userBets.getPoint();
/*  75 */     this.money = userBets.getMoney();
/*  76 */     this.time = userBets.getTime();
/*  77 */     this.stopTime = userBets.getStopTime();
/*  78 */     this.openTime = userBets.getOpenTime();
/*  79 */     this.status = userBets.getStatus();
/*  80 */     this.openCode = userBets.getOpenCode();
/*  81 */     this.prizeMoney = userBets.getPrizeMoney();
/*  82 */     this.prizeTime = userBets.getPrizeTime();
/*  83 */     this.chaseBillno = userBets.getChaseBillno();
/*  84 */     this.chaseStop = userBets.getChaseStop();
/*  85 */     this.planBillno = userBets.getPlanBillno();
/*  86 */     this.rewardMoney = userBets.getRewardMoney();
/*  87 */     String cer = UserBetsEncrypt.getRandomCertification();
/*  88 */     this.identification = UserBetsEncrypt.getIdentification(this, cer);
/*  89 */     this.certification = UserBetsEncrypt.encryptCertification(cer);
/*  90 */     this.compressed = userBets.getCompressed();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public UserBetsOriginal() {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public UserBetsOriginal(String billno, int userId, int type, int lotteryId, String expect, int ruleId, String codes, int nums, String model, int multiple, int code, double point, double money, String time, String stopTime, String openTime, int status)
/*     */   {
/* 105 */     this.billno = billno;
/* 106 */     this.userId = userId;
/* 107 */     this.type = type;
/* 108 */     this.lotteryId = lotteryId;
/* 109 */     this.expect = expect;
/* 110 */     this.ruleId = ruleId;
/* 111 */     this.codes = codes;
/* 112 */     this.nums = nums;
/* 113 */     this.model = model;
/* 114 */     this.multiple = multiple;
/* 115 */     this.code = code;
/* 116 */     this.point = point;
/* 117 */     this.money = money;
/* 118 */     this.time = time;
/* 119 */     this.stopTime = stopTime;
/* 120 */     this.openTime = openTime;
/* 121 */     this.status = status;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public UserBetsOriginal(String billno, int userId, int type, int lotteryId, String expect, int ruleId, String codes, int nums, String model, int multiple, int code, double point, double money, String time, String stopTime, String openTime, int status, String openCode, Double prizeMoney, String prizeTime, String chaseBillno, Integer chaseStop, String planBillno, Double rewardMoney)
/*     */   {
/* 132 */     this.billno = billno;
/* 133 */     this.userId = userId;
/* 134 */     this.type = type;
/* 135 */     this.lotteryId = lotteryId;
/* 136 */     this.expect = expect;
/* 137 */     this.ruleId = ruleId;
/* 138 */     this.codes = codes;
/* 139 */     this.nums = nums;
/* 140 */     this.model = model;
/* 141 */     this.multiple = multiple;
/* 142 */     this.code = code;
/* 143 */     this.point = point;
/* 144 */     this.money = money;
/* 145 */     this.time = time;
/* 146 */     this.stopTime = stopTime;
/* 147 */     this.openTime = openTime;
/* 148 */     this.status = status;
/* 149 */     this.openCode = openCode;
/* 150 */     this.prizeMoney = prizeMoney;
/* 151 */     this.prizeTime = prizeTime;
/* 152 */     this.chaseBillno = chaseBillno;
/* 153 */     this.chaseStop = chaseStop;
/* 154 */     this.planBillno = planBillno;
/* 155 */     this.rewardMoney = rewardMoney;
/*     */   }
/*     */   
/*     */   @Id
/*     */   @Column(name="id", unique=true, nullable=false)
/*     */   public int getId()
/*     */   {
/* 162 */     return this.id;
/*     */   }
/*     */   
/*     */   public void setId(int id) {
/* 166 */     this.id = id;
/*     */   }
/*     */   
/*     */   @Column(name="billno", nullable=false, length=32, updatable=false)
/*     */   public String getBillno() {
/* 171 */     return this.billno;
/*     */   }
/*     */   
/*     */   public void setBillno(String billno) {
/* 175 */     this.billno = billno;
/*     */   }
/*     */   
/*     */   @Column(name="user_id", nullable=false, updatable=false)
/*     */   public int getUserId() {
/* 180 */     return this.userId;
/*     */   }
/*     */   
/*     */   public void setUserId(int userId) {
/* 184 */     this.userId = userId;
/*     */   }
/*     */   
/*     */   @Column(name="type", nullable=false, updatable=false)
/*     */   public int getType() {
/* 189 */     return this.type;
/*     */   }
/*     */   
/*     */   public void setType(int type) {
/* 193 */     this.type = type;
/*     */   }
/*     */   
/*     */   @Column(name="lottery_id", nullable=false, updatable=false)
/*     */   public int getLotteryId() {
/* 198 */     return this.lotteryId;
/*     */   }
/*     */   
/*     */   public void setLotteryId(int lotteryId) {
/* 202 */     this.lotteryId = lotteryId;
/*     */   }
/*     */   
/*     */   @Column(name="expect", nullable=false, length=32, updatable=false)
/*     */   public String getExpect() {
/* 207 */     return this.expect;
/*     */   }
/*     */   
/*     */   public void setExpect(String expect) {
/* 211 */     this.expect = expect;
/*     */   }
/*     */   
/*     */   @Column(name="rule_id", nullable=false, updatable=false)
/*     */   public int getRuleId() {
/* 216 */     return this.ruleId;
/*     */   }
/*     */   
/*     */   public void setRuleId(int ruleId) {
/* 220 */     this.ruleId = ruleId;
/*     */   }
/*     */   
/*     */   @Column(name="codes", nullable=false, length=16777215, updatable=false)
/*     */   public String getCodes() {
/* 225 */     return this.codes;
/*     */   }
/*     */   
/*     */   public void setCodes(String codes) {
/* 229 */     this.codes = codes;
/*     */   }
/*     */   
/*     */   @Column(name="nums", nullable=false, updatable=false)
/*     */   public int getNums() {
/* 234 */     return this.nums;
/*     */   }
/*     */   
/*     */   public void setNums(int nums) {
/* 238 */     this.nums = nums;
/*     */   }
/*     */   
/*     */   @Column(name="model", nullable=false, length=16, updatable=false)
/*     */   public String getModel() {
/* 243 */     return this.model;
/*     */   }
/*     */   
/*     */   public void setModel(String model) {
/* 247 */     this.model = model;
/*     */   }
/*     */   
/*     */   @Column(name="multiple", nullable=false, updatable=false)
/*     */   public int getMultiple() {
/* 252 */     return this.multiple;
/*     */   }
/*     */   
/*     */   public void setMultiple(int multiple) {
/* 256 */     this.multiple = multiple;
/*     */   }
/*     */   
/*     */   @Column(name="code", nullable=false, updatable=false)
/*     */   public int getCode() {
/* 261 */     return this.code;
/*     */   }
/*     */   
/*     */   public void setCode(int code) {
/* 265 */     this.code = code;
/*     */   }
/*     */   
/*     */   @Column(name="point", nullable=false, precision=11, scale=1, updatable=false)
/*     */   public double getPoint() {
/* 270 */     return this.point;
/*     */   }
/*     */   
/*     */   public void setPoint(double point) {
/* 274 */     this.point = point;
/*     */   }
/*     */   
/*     */   @Column(name="money", nullable=false, precision=16, scale=5, updatable=false)
/*     */   public double getMoney() {
/* 279 */     return this.money;
/*     */   }
/*     */   
/*     */   public void setMoney(double money) {
/* 283 */     this.money = money;
/*     */   }
/*     */   
/*     */   @Column(name="time", nullable=false, length=19, updatable=false)
/*     */   public String getTime() {
/* 288 */     return this.time;
/*     */   }
/*     */   
/*     */   public void setTime(String time) {
/* 292 */     this.time = time;
/*     */   }
/*     */   
/*     */   @Column(name="stop_time", nullable=false, length=19, updatable=false)
/*     */   public String getStopTime() {
/* 297 */     return this.stopTime;
/*     */   }
/*     */   
/*     */   public void setStopTime(String stopTime) {
/* 301 */     this.stopTime = stopTime;
/*     */   }
/*     */   
/*     */   @Column(name="open_time", nullable=false, length=19, updatable=false)
/*     */   public String getOpenTime() {
/* 306 */     return this.openTime;
/*     */   }
/*     */   
/*     */   public void setOpenTime(String openTime) {
/* 310 */     this.openTime = openTime;
/*     */   }
/*     */   
/*     */   @Column(name="status", nullable=false, updatable=false)
/*     */   public int getStatus() {
/* 315 */     return this.status;
/*     */   }
/*     */   
/*     */   public void setStatus(int status) {
/* 319 */     this.status = status;
/*     */   }
/*     */   
/*     */   @Column(name="open_code", length=128, updatable=false)
/*     */   public String getOpenCode() {
/* 324 */     return this.openCode;
/*     */   }
/*     */   
/*     */   public void setOpenCode(String openCode) {
/* 328 */     this.openCode = openCode;
/*     */   }
/*     */   
/*     */   @Column(name="prize_money", precision=16, scale=5, updatable=false)
/*     */   public Double getPrizeMoney() {
/* 333 */     return this.prizeMoney;
/*     */   }
/*     */   
/*     */   public void setPrizeMoney(Double prizeMoney) {
/* 337 */     this.prizeMoney = prizeMoney;
/*     */   }
/*     */   
/*     */   @Column(name="prize_time", length=19, updatable=false)
/*     */   public String getPrizeTime() {
/* 342 */     return this.prizeTime;
/*     */   }
/*     */   
/*     */   public void setPrizeTime(String prizeTime) {
/* 346 */     this.prizeTime = prizeTime;
/*     */   }
/*     */   
/*     */   @Column(name="chase_billno", length=64, updatable=false)
/*     */   public String getChaseBillno() {
/* 351 */     return this.chaseBillno;
/*     */   }
/*     */   
/*     */   public void setChaseBillno(String chaseBillno) {
/* 355 */     this.chaseBillno = chaseBillno;
/*     */   }
/*     */   
/*     */   @Column(name="chase_stop", updatable=false)
/*     */   public Integer getChaseStop() {
/* 360 */     return this.chaseStop;
/*     */   }
/*     */   
/*     */   public void setChaseStop(Integer chaseStop) {
/* 364 */     this.chaseStop = chaseStop;
/*     */   }
/*     */   
/*     */   @Column(name="plan_billno", length=64, updatable=false)
/*     */   public String getPlanBillno() {
/* 369 */     return this.planBillno;
/*     */   }
/*     */   
/*     */   public void setPlanBillno(String planBillno) {
/* 373 */     this.planBillno = planBillno;
/*     */   }
/*     */   
/*     */   @Column(name="reward_money", precision=16, scale=5, updatable=false)
/*     */   public Double getRewardMoney() {
/* 378 */     return this.rewardMoney;
/*     */   }
/*     */   
/*     */   public void setRewardMoney(Double rewardMoney) {
/* 382 */     this.rewardMoney = rewardMoney;
/*     */   }
/*     */   
/*     */   @Column(name="identification", length=128, nullable=false)
/*     */   public String getIdentification() {
/* 387 */     return this.identification;
/*     */   }
/*     */   
/*     */   public void setIdentification(String identification) {
/* 391 */     this.identification = identification;
/*     */   }
/*     */   
/*     */   @Column(name="certification", length=128, nullable=false)
/*     */   public String getCertification() {
/* 396 */     return this.certification;
/*     */   }
/*     */   
/*     */   public void setCertification(String certification) {
/* 400 */     this.certification = certification;
/*     */   }
/*     */   
/*     */   @Column(name="compressed")
/*     */   public int getCompressed() {
/* 405 */     return this.compressed;
/*     */   }
/*     */   
/*     */   public void setCompressed(int compressed) {
/* 409 */     this.compressed = compressed;
/*     */   }
/*     */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/content/entity/UserBetsOriginal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */