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
/*     */ @Entity
/*     */ @Table(name="user_bets_risk", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"billno", "user_id"})})
/*     */ public class UserBetsRisk
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
/*     */   private String ip;
/*     */   private int winNum;
/*     */   
/*     */   public UserBetsRisk clone()
/*     */   {
/*     */     try
/*     */     {
/*  53 */       return (UserBetsRisk)super.clone();
/*     */     } catch (Exception localException) {}
/*  55 */     return null;
/*     */   }
/*     */   
/*     */   public UserBetsRisk() {}
/*     */   
/*     */   public UserBetsRisk(UserBets userBets)
/*     */   {
/*  62 */     this.billno = userBets.getBillno();
/*  63 */     this.userId = userBets.getUserId();
/*  64 */     this.type = userBets.getType();
/*  65 */     this.lotteryId = userBets.getLotteryId();
/*  66 */     this.expect = userBets.getExpect();
/*  67 */     this.ruleId = userBets.getRuleId();
/*  68 */     this.codes = userBets.getCodes();
/*  69 */     this.nums = userBets.getNums();
/*  70 */     this.model = userBets.getModel();
/*  71 */     this.multiple = userBets.getMultiple();
/*  72 */     this.code = userBets.getCode();
/*  73 */     this.point = userBets.getPoint();
/*  74 */     this.money = userBets.getMoney();
/*  75 */     this.time = userBets.getTime();
/*  76 */     this.stopTime = userBets.getStopTime();
/*  77 */     this.openTime = userBets.getOpenTime();
/*  78 */     this.status = userBets.getStatus();
/*  79 */     this.openCode = userBets.getOpenCode();
/*  80 */     this.prizeMoney = userBets.getPrizeMoney();
/*  81 */     this.prizeTime = userBets.getPrizeTime();
/*  82 */     this.chaseBillno = userBets.getChaseBillno();
/*  83 */     this.chaseStop = userBets.getChaseStop();
/*  84 */     this.planBillno = userBets.getPlanBillno();
/*  85 */     this.rewardMoney = userBets.getRewardMoney();
/*  86 */     this.compressed = userBets.getCompressed();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   @Id
/*     */   @GeneratedValue(strategy=GenerationType.IDENTITY)
/*     */   @Column(name="id", unique=true, nullable=false)
/*     */   public int getId()
/*     */   {
/*  96 */     return this.id;
/*     */   }
/*     */   
/*     */   public void setId(int id) {
/* 100 */     this.id = id;
/*     */   }
/*     */   
/*     */   @Column(name="billno", nullable=false, length=32, updatable=false)
/*     */   public String getBillno() {
/* 105 */     return this.billno;
/*     */   }
/*     */   
/*     */   public void setBillno(String billno) {
/* 109 */     this.billno = billno;
/*     */   }
/*     */   
/*     */   @Column(name="user_id", nullable=false, updatable=false)
/*     */   public int getUserId() {
/* 114 */     return this.userId;
/*     */   }
/*     */   
/*     */   public void setUserId(int userId) {
/* 118 */     this.userId = userId;
/*     */   }
/*     */   
/*     */   @Column(name="type", nullable=false, updatable=false)
/*     */   public int getType() {
/* 123 */     return this.type;
/*     */   }
/*     */   
/*     */   public void setType(int type) {
/* 127 */     this.type = type;
/*     */   }
/*     */   
/*     */   @Column(name="lottery_id", nullable=false, updatable=false)
/*     */   public int getLotteryId() {
/* 132 */     return this.lotteryId;
/*     */   }
/*     */   
/*     */   public void setLotteryId(int lotteryId) {
/* 136 */     this.lotteryId = lotteryId;
/*     */   }
/*     */   
/*     */   @Column(name="expect", nullable=false, length=32, updatable=false)
/*     */   public String getExpect() {
/* 141 */     return this.expect;
/*     */   }
/*     */   
/*     */   public void setExpect(String expect) {
/* 145 */     this.expect = expect;
/*     */   }
/*     */   
/*     */   @Column(name="rule_id", nullable=false, updatable=false)
/*     */   public int getRuleId() {
/* 150 */     return this.ruleId;
/*     */   }
/*     */   
/*     */   public void setRuleId(int ruleId) {
/* 154 */     this.ruleId = ruleId;
/*     */   }
/*     */   
/*     */   @Column(name="codes", nullable=false, length=16777215, updatable=false)
/*     */   public String getCodes() {
/* 159 */     return this.codes;
/*     */   }
/*     */   
/*     */   public void setCodes(String codes) {
/* 163 */     this.codes = codes;
/*     */   }
/*     */   
/*     */   @Column(name="nums", nullable=false, updatable=false)
/*     */   public int getNums() {
/* 168 */     return this.nums;
/*     */   }
/*     */   
/*     */   public void setNums(int nums) {
/* 172 */     this.nums = nums;
/*     */   }
/*     */   
/*     */   @Column(name="model", nullable=false, length=16, updatable=false)
/*     */   public String getModel() {
/* 177 */     return this.model;
/*     */   }
/*     */   
/*     */   public void setModel(String model) {
/* 181 */     this.model = model;
/*     */   }
/*     */   
/*     */   @Column(name="multiple", nullable=false, updatable=false)
/*     */   public int getMultiple() {
/* 186 */     return this.multiple;
/*     */   }
/*     */   
/*     */   public void setMultiple(int multiple) {
/* 190 */     this.multiple = multiple;
/*     */   }
/*     */   
/*     */   @Column(name="code", nullable=false, updatable=false)
/*     */   public int getCode() {
/* 195 */     return this.code;
/*     */   }
/*     */   
/*     */   public void setCode(int code) {
/* 199 */     this.code = code;
/*     */   }
/*     */   
/*     */   @Column(name="point", nullable=false, precision=11, scale=1, updatable=false)
/*     */   public double getPoint() {
/* 204 */     return this.point;
/*     */   }
/*     */   
/*     */   public void setPoint(double point) {
/* 208 */     this.point = point;
/*     */   }
/*     */   
/*     */   @Column(name="money", nullable=false, precision=16, scale=5, updatable=false)
/*     */   public double getMoney() {
/* 213 */     return this.money;
/*     */   }
/*     */   
/*     */   public void setMoney(double money) {
/* 217 */     this.money = money;
/*     */   }
/*     */   
/*     */   @Column(name="time", nullable=false, length=19, updatable=false)
/*     */   public String getTime() {
/* 222 */     return this.time;
/*     */   }
/*     */   
/*     */   public void setTime(String time) {
/* 226 */     this.time = time;
/*     */   }
/*     */   
/*     */   @Column(name="stop_time", nullable=false, length=19, updatable=false)
/*     */   public String getStopTime() {
/* 231 */     return this.stopTime;
/*     */   }
/*     */   
/*     */   public void setStopTime(String stopTime) {
/* 235 */     this.stopTime = stopTime;
/*     */   }
/*     */   
/*     */   @Column(name="open_time", nullable=false, length=19, updatable=false)
/*     */   public String getOpenTime() {
/* 240 */     return this.openTime;
/*     */   }
/*     */   
/*     */   public void setOpenTime(String openTime) {
/* 244 */     this.openTime = openTime;
/*     */   }
/*     */   
/*     */   @Column(name="status", nullable=false)
/*     */   public int getStatus() {
/* 249 */     return this.status;
/*     */   }
/*     */   
/*     */   public void setStatus(int status) {
/* 253 */     this.status = status;
/*     */   }
/*     */   
/*     */   @Column(name="open_code", length=128)
/*     */   public String getOpenCode() {
/* 258 */     return this.openCode;
/*     */   }
/*     */   
/*     */   public void setOpenCode(String openCode) {
/* 262 */     this.openCode = openCode;
/*     */   }
/*     */   
/*     */   @Column(name="prize_money", precision=16, scale=5)
/*     */   public Double getPrizeMoney() {
/* 267 */     return this.prizeMoney;
/*     */   }
/*     */   
/*     */   public void setPrizeMoney(Double prizeMoney) {
/* 271 */     this.prizeMoney = prizeMoney;
/*     */   }
/*     */   
/*     */   @Column(name="prize_time", length=19)
/*     */   public String getPrizeTime() {
/* 276 */     return this.prizeTime;
/*     */   }
/*     */   
/*     */   public void setPrizeTime(String prizeTime) {
/* 280 */     this.prizeTime = prizeTime;
/*     */   }
/*     */   
/*     */   @Column(name="chase_billno", length=64)
/*     */   public String getChaseBillno() {
/* 285 */     return this.chaseBillno;
/*     */   }
/*     */   
/*     */   public void setChaseBillno(String chaseBillno) {
/* 289 */     this.chaseBillno = chaseBillno;
/*     */   }
/*     */   
/*     */   @Column(name="chase_stop")
/*     */   public Integer getChaseStop() {
/* 294 */     return this.chaseStop;
/*     */   }
/*     */   
/*     */   public void setChaseStop(Integer chaseStop) {
/* 298 */     this.chaseStop = chaseStop;
/*     */   }
/*     */   
/*     */   @Column(name="plan_billno", length=64)
/*     */   public String getPlanBillno() {
/* 303 */     return this.planBillno;
/*     */   }
/*     */   
/*     */   public void setPlanBillno(String planBillno) {
/* 307 */     this.planBillno = planBillno;
/*     */   }
/*     */   
/*     */   @Column(name="reward_money", precision=16, scale=5)
/*     */   public Double getRewardMoney() {
/* 312 */     return this.rewardMoney;
/*     */   }
/*     */   
/*     */   public void setRewardMoney(Double rewardMoney) {
/* 316 */     this.rewardMoney = rewardMoney;
/*     */   }
/*     */   
/*     */   @Column(name="compressed")
/*     */   public int getCompressed() {
/* 321 */     return this.compressed;
/*     */   }
/*     */   
/*     */   public void setCompressed(int compressed) {
/* 325 */     this.compressed = compressed;
/*     */   }
/*     */   
/*     */   @Column(name="ip", length=20)
/*     */   public String getIp() {
/* 330 */     return this.ip;
/*     */   }
/*     */   
/*     */   public void setIp(String ip) {
/* 334 */     this.ip = ip;
/*     */   }
/*     */   
/*     */   @Column(name="win_num", length=11)
/*     */   public int getWinNum() {
/* 339 */     return this.winNum;
/*     */   }
/*     */   
/*     */   public void setWinNum(int winNum) {
/* 343 */     this.winNum = winNum;
/*     */   }
/*     */   
/*     */   public UserBets toUserBets() {
/* 347 */     UserBets userBets = new UserBets();
/*     */     
/* 349 */     userBets.setBillno(this.billno);
/* 350 */     userBets.setUserId(this.userId);
/* 351 */     userBets.setType(this.type);
/* 352 */     userBets.setLotteryId(this.lotteryId);
/* 353 */     userBets.setExpect(this.expect);
/* 354 */     userBets.setRuleId(this.ruleId);
/* 355 */     userBets.setCodes(this.codes);
/* 356 */     userBets.setNums(this.nums);
/* 357 */     userBets.setModel(this.model);
/* 358 */     userBets.setMultiple(this.multiple);
/* 359 */     userBets.setCode(this.code);
/* 360 */     userBets.setPoint(this.point);
/* 361 */     userBets.setMoney(this.money);
/* 362 */     userBets.setTime(this.time);
/* 363 */     userBets.setStopTime(this.stopTime);
/* 364 */     userBets.setOpenTime(this.openTime);
/* 365 */     userBets.setStatus(this.status);
/* 366 */     userBets.setOpenCode(this.openCode);
/* 367 */     userBets.setPrizeMoney(this.prizeMoney);
/* 368 */     userBets.setPrizeTime(this.prizeTime);
/* 369 */     userBets.setChaseBillno(this.chaseBillno);
/* 370 */     userBets.setChaseStop(this.chaseStop);
/* 371 */     userBets.setPlanBillno(this.planBillno);
/* 372 */     userBets.setRewardMoney(this.rewardMoney);
/* 373 */     userBets.setCompressed(this.compressed);
/*     */     
/*     */ 
/* 376 */     return userBets;
/*     */   }
/*     */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/content/entity/UserBetsRisk.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */