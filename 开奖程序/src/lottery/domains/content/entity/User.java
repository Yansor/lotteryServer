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
/*     */ @Entity
/*     */ @Table(name="user", catalog="ecai", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"username"})})
/*     */ public class User
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private int id;
/*     */   private String username;
/*     */   private String password;
/*     */   private String imgPassword;
/*     */   private String nickname;
/*     */   private double totalMoney;
/*     */   private double lotteryMoney;
/*     */   private double baccaratMoney;
/*     */   private double freezeMoney;
/*     */   private double dividendMoney;
/*     */   private int type;
/*     */   private int upid;
/*     */   private String upids;
/*     */   private int code;
/*     */   private double locatePoint;
/*     */   private double notLocatePoint;
/*     */   private int codeType;
/*     */   private double extraPoint;
/*     */   private String withdrawName;
/*     */   private String withdrawPassword;
/*     */   private String registTime;
/*     */   private String loginTime;
/*     */   private String lockTime;
/*     */   private int AStatus;
/*     */   private int BStatus;
/*     */   private String message;
/*     */   private String sessionId;
/*     */   private int onlineStatus;
/*     */   private int allowEqualCode;
/*     */   private int allowTransfers;
/*     */   private int loginValidate;
/*     */   private int bindStatus;
/*     */   private int vipLevel;
/*     */   private double integral;
/*     */   private String secretKey;
/*     */   private int isBindGoogle;
/*     */   private int relatedUpid;
/*     */   private double relatedPoint;
/*     */   private Integer utype;
/*     */   
/*     */   public User() {}
/*     */   
/*     */   public User(String username, String password, String nickname, double totalMoney, double lotteryMoney, double baccaratMoney, double freezeMoney, double dividendMoney, int type, int upid, int code, double locatePoint, double notLocatePoint, int codeType, double extraPoint, String registTime, int AStatus, int BStatus, int onlineStatus, int allowEqualCode, int allowTransfers, int loginValidate, int bindStatus, int vipLevel, double integral)
/*     */   {
/*  73 */     this.username = username;
/*  74 */     this.password = password;
/*  75 */     this.nickname = nickname;
/*  76 */     this.totalMoney = totalMoney;
/*  77 */     this.lotteryMoney = lotteryMoney;
/*  78 */     this.baccaratMoney = baccaratMoney;
/*  79 */     this.freezeMoney = freezeMoney;
/*  80 */     this.dividendMoney = dividendMoney;
/*  81 */     this.type = type;
/*  82 */     this.upid = upid;
/*  83 */     this.code = code;
/*  84 */     this.locatePoint = locatePoint;
/*  85 */     this.notLocatePoint = notLocatePoint;
/*  86 */     this.codeType = codeType;
/*  87 */     this.extraPoint = extraPoint;
/*  88 */     this.registTime = registTime;
/*  89 */     this.AStatus = AStatus;
/*  90 */     this.BStatus = BStatus;
/*  91 */     this.onlineStatus = onlineStatus;
/*  92 */     this.allowEqualCode = allowEqualCode;
/*  93 */     this.allowTransfers = allowTransfers;
/*  94 */     this.loginValidate = loginValidate;
/*  95 */     this.bindStatus = bindStatus;
/*  96 */     this.vipLevel = vipLevel;
/*  97 */     this.integral = integral;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public User(String username, String password, String imgPassword, String nickname, double totalMoney, double lotteryMoney, double baccaratMoney, double freezeMoney, double dividendMoney, int type, int upid, String upids, int code, double locatePoint, double notLocatePoint, int codeType, double extraPoint, String withdrawName, String withdrawPassword, String registTime, String loginTime, String lockTime, int AStatus, int BStatus, String message, String sessionId, int onlineStatus, int allowEqualCode, int allowTransfers, int loginValidate, int bindStatus, int vipLevel, double integral)
/*     */   {
/* 107 */     this.username = username;
/* 108 */     this.password = password;
/* 109 */     this.imgPassword = imgPassword;
/* 110 */     this.nickname = nickname;
/* 111 */     this.totalMoney = totalMoney;
/* 112 */     this.lotteryMoney = lotteryMoney;
/* 113 */     this.baccaratMoney = baccaratMoney;
/* 114 */     this.freezeMoney = freezeMoney;
/* 115 */     this.dividendMoney = dividendMoney;
/* 116 */     this.type = type;
/* 117 */     this.upid = upid;
/* 118 */     this.upids = upids;
/* 119 */     this.code = code;
/* 120 */     this.locatePoint = locatePoint;
/* 121 */     this.notLocatePoint = notLocatePoint;
/* 122 */     this.codeType = codeType;
/* 123 */     this.extraPoint = extraPoint;
/* 124 */     this.withdrawName = withdrawName;
/* 125 */     this.withdrawPassword = withdrawPassword;
/* 126 */     this.registTime = registTime;
/* 127 */     this.loginTime = loginTime;
/* 128 */     this.lockTime = lockTime;
/* 129 */     this.AStatus = AStatus;
/* 130 */     this.BStatus = BStatus;
/* 131 */     this.message = message;
/* 132 */     this.sessionId = sessionId;
/* 133 */     this.onlineStatus = onlineStatus;
/* 134 */     this.allowEqualCode = allowEqualCode;
/* 135 */     this.allowTransfers = allowTransfers;
/* 136 */     this.loginValidate = loginValidate;
/* 137 */     this.bindStatus = bindStatus;
/* 138 */     this.vipLevel = vipLevel;
/* 139 */     this.integral = integral;
/*     */   }
/*     */   
/*     */   @Id
/*     */   @GeneratedValue(strategy=GenerationType.IDENTITY)
/*     */   @Column(name="id", unique=true, nullable=false)
/*     */   public int getId()
/*     */   {
/* 147 */     return this.id;
/*     */   }
/*     */   
/*     */   public void setId(int id) {
/* 151 */     this.id = id;
/*     */   }
/*     */   
/*     */   @Column(name="username", unique=true, nullable=false, length=20)
/*     */   public String getUsername() {
/* 156 */     return this.username;
/*     */   }
/*     */   
/*     */   public void setUsername(String username) {
/* 160 */     this.username = username;
/*     */   }
/*     */   
/*     */   @Column(name="password", nullable=false, length=32)
/*     */   public String getPassword() {
/* 165 */     return this.password;
/*     */   }
/*     */   
/*     */   public void setPassword(String password) {
/* 169 */     this.password = password;
/*     */   }
/*     */   
/*     */   @Column(name="img_password", length=32)
/*     */   public String getImgPassword() {
/* 174 */     return this.imgPassword;
/*     */   }
/*     */   
/*     */   public void setImgPassword(String imgPassword) {
/* 178 */     this.imgPassword = imgPassword;
/*     */   }
/*     */   
/*     */   @Column(name="nickname", nullable=false, length=20)
/*     */   public String getNickname() {
/* 183 */     return this.nickname;
/*     */   }
/*     */   
/*     */   public void setNickname(String nickname) {
/* 187 */     this.nickname = nickname;
/*     */   }
/*     */   
/*     */   @Column(name="total_money", nullable=false, precision=16, scale=5)
/*     */   public double getTotalMoney() {
/* 192 */     return this.totalMoney;
/*     */   }
/*     */   
/*     */   public void setTotalMoney(double totalMoney) {
/* 196 */     this.totalMoney = totalMoney;
/*     */   }
/*     */   
/*     */   @Column(name="lottery_money", nullable=false, precision=16, scale=5)
/*     */   public double getLotteryMoney() {
/* 201 */     return this.lotteryMoney;
/*     */   }
/*     */   
/*     */   public void setLotteryMoney(double lotteryMoney) {
/* 205 */     this.lotteryMoney = lotteryMoney;
/*     */   }
/*     */   
/*     */   @Column(name="baccarat_money", nullable=false, precision=16, scale=5)
/*     */   public double getBaccaratMoney() {
/* 210 */     return this.baccaratMoney;
/*     */   }
/*     */   
/*     */   public void setBaccaratMoney(double baccaratMoney) {
/* 214 */     this.baccaratMoney = baccaratMoney;
/*     */   }
/*     */   
/*     */   @Column(name="freeze_money", nullable=false, precision=16, scale=5)
/*     */   public double getFreezeMoney() {
/* 219 */     return this.freezeMoney;
/*     */   }
/*     */   
/*     */   public void setFreezeMoney(double freezeMoney) {
/* 223 */     this.freezeMoney = freezeMoney;
/*     */   }
/*     */   
/*     */   @Column(name="dividend_money", nullable=false, precision=16, scale=5)
/*     */   public double getDividendMoney() {
/* 228 */     return this.dividendMoney;
/*     */   }
/*     */   
/*     */   public void setDividendMoney(double dividendMoney) {
/* 232 */     this.dividendMoney = dividendMoney;
/*     */   }
/*     */   
/*     */   @Column(name="type", nullable=false)
/*     */   public int getType() {
/* 237 */     return this.type;
/*     */   }
/*     */   
/*     */   public void setType(int type) {
/* 241 */     this.type = type;
/*     */   }
/*     */   
/*     */   @Column(name="upid", nullable=false)
/*     */   public int getUpid() {
/* 246 */     return this.upid;
/*     */   }
/*     */   
/*     */   public void setUpid(int upid) {
/* 250 */     this.upid = upid;
/*     */   }
/*     */   
/*     */   @Column(name="upids")
/*     */   public String getUpids() {
/* 255 */     return this.upids;
/*     */   }
/*     */   
/*     */   public void setUpids(String upids) {
/* 259 */     this.upids = upids;
/*     */   }
/*     */   
/*     */   @Column(name="code", nullable=false)
/*     */   public int getCode() {
/* 264 */     return this.code;
/*     */   }
/*     */   
/*     */   public void setCode(int code) {
/* 268 */     this.code = code;
/*     */   }
/*     */   
/*     */   @Column(name="locate_point", nullable=false, precision=11, scale=2)
/*     */   public double getLocatePoint() {
/* 273 */     return this.locatePoint;
/*     */   }
/*     */   
/*     */   public void setLocatePoint(double locatePoint) {
/* 277 */     this.locatePoint = locatePoint;
/*     */   }
/*     */   
/*     */   @Column(name="not_locate_point", nullable=false, precision=11, scale=2)
/*     */   public double getNotLocatePoint() {
/* 282 */     return this.notLocatePoint;
/*     */   }
/*     */   
/*     */   public void setNotLocatePoint(double notLocatePoint) {
/* 286 */     this.notLocatePoint = notLocatePoint;
/*     */   }
/*     */   
/*     */   @Column(name="code_type", nullable=false)
/*     */   public int getCodeType() {
/* 291 */     return this.codeType;
/*     */   }
/*     */   
/*     */   public void setCodeType(int codeType) {
/* 295 */     this.codeType = codeType;
/*     */   }
/*     */   
/*     */   @Column(name="extra_point", nullable=false, precision=11, scale=3)
/*     */   public double getExtraPoint() {
/* 300 */     return this.extraPoint;
/*     */   }
/*     */   
/*     */   public void setExtraPoint(double extraPoint) {
/* 304 */     this.extraPoint = extraPoint;
/*     */   }
/*     */   
/*     */   @Column(name="withdraw_name", length=32)
/*     */   public String getWithdrawName() {
/* 309 */     return this.withdrawName;
/*     */   }
/*     */   
/*     */   public void setWithdrawName(String withdrawName) {
/* 313 */     this.withdrawName = withdrawName;
/*     */   }
/*     */   
/*     */   @Column(name="withdraw_password", length=32)
/*     */   public String getWithdrawPassword() {
/* 318 */     return this.withdrawPassword;
/*     */   }
/*     */   
/*     */   public void setWithdrawPassword(String withdrawPassword) {
/* 322 */     this.withdrawPassword = withdrawPassword;
/*     */   }
/*     */   
/*     */   @Column(name="regist_time", nullable=false, length=19)
/*     */   public String getRegistTime() {
/* 327 */     return this.registTime;
/*     */   }
/*     */   
/*     */   public void setRegistTime(String registTime) {
/* 331 */     this.registTime = registTime;
/*     */   }
/*     */   
/*     */   @Column(name="login_time", length=19)
/*     */   public String getLoginTime() {
/* 336 */     return this.loginTime;
/*     */   }
/*     */   
/*     */   public void setLoginTime(String loginTime) {
/* 340 */     this.loginTime = loginTime;
/*     */   }
/*     */   
/*     */   @Column(name="lock_time", length=19)
/*     */   public String getLockTime() {
/* 345 */     return this.lockTime;
/*     */   }
/*     */   
/*     */   public void setLockTime(String lockTime) {
/* 349 */     this.lockTime = lockTime;
/*     */   }
/*     */   
/*     */   @Column(name="a_status", nullable=false)
/*     */   public int getAStatus() {
/* 354 */     return this.AStatus;
/*     */   }
/*     */   
/*     */   public void setAStatus(int AStatus) {
/* 358 */     this.AStatus = AStatus;
/*     */   }
/*     */   
/*     */   @Column(name="b_status", nullable=false)
/*     */   public int getBStatus() {
/* 363 */     return this.BStatus;
/*     */   }
/*     */   
/*     */   public void setBStatus(int BStatus) {
/* 367 */     this.BStatus = BStatus;
/*     */   }
/*     */   
/*     */   @Column(name="message")
/*     */   public String getMessage() {
/* 372 */     return this.message;
/*     */   }
/*     */   
/*     */   public void setMessage(String message) {
/* 376 */     this.message = message;
/*     */   }
/*     */   
/*     */   @Column(name="session_id", length=128)
/*     */   public String getSessionId() {
/* 381 */     return this.sessionId;
/*     */   }
/*     */   
/*     */   public void setSessionId(String sessionId) {
/* 385 */     this.sessionId = sessionId;
/*     */   }
/*     */   
/*     */   @Column(name="online_status", nullable=false)
/*     */   public int getOnlineStatus() {
/* 390 */     return this.onlineStatus;
/*     */   }
/*     */   
/*     */   public void setOnlineStatus(int onlineStatus) {
/* 394 */     this.onlineStatus = onlineStatus;
/*     */   }
/*     */   
/*     */   @Column(name="allow_equal_code", nullable=false)
/*     */   public int getAllowEqualCode() {
/* 399 */     return this.allowEqualCode;
/*     */   }
/*     */   
/*     */   public void setAllowEqualCode(int allowEqualCode) {
/* 403 */     this.allowEqualCode = allowEqualCode;
/*     */   }
/*     */   
/*     */   @Column(name="allow_transfers", nullable=false)
/*     */   public int getAllowTransfers() {
/* 408 */     return this.allowTransfers;
/*     */   }
/*     */   
/*     */   public void setAllowTransfers(int allowTransfers) {
/* 412 */     this.allowTransfers = allowTransfers;
/*     */   }
/*     */   
/*     */   @Column(name="login_validate", nullable=false)
/*     */   public int getLoginValidate() {
/* 417 */     return this.loginValidate;
/*     */   }
/*     */   
/*     */   public void setLoginValidate(int loginValidate) {
/* 421 */     this.loginValidate = loginValidate;
/*     */   }
/*     */   
/*     */   @Column(name="bind_status", nullable=false)
/*     */   public int getBindStatus() {
/* 426 */     return this.bindStatus;
/*     */   }
/*     */   
/*     */   public void setBindStatus(int bindStatus) {
/* 430 */     this.bindStatus = bindStatus;
/*     */   }
/*     */   
/*     */   @Column(name="vip_level", nullable=false)
/*     */   public int getVipLevel() {
/* 435 */     return this.vipLevel;
/*     */   }
/*     */   
/*     */   public void setVipLevel(int vipLevel) {
/* 439 */     this.vipLevel = vipLevel;
/*     */   }
/*     */   
/*     */   @Column(name="integral", nullable=false, precision=16, scale=5)
/*     */   public double getIntegral() {
/* 444 */     return this.integral;
/*     */   }
/*     */   
/*     */   public void setIntegral(double integral) {
/* 448 */     this.integral = integral;
/*     */   }
/*     */   
/*     */   @Column(name="secret_key")
/*     */   public String getSecretKey() {
/* 453 */     return this.secretKey;
/*     */   }
/*     */   
/*     */   public void setSecretKey(String secretKey) {
/* 457 */     this.secretKey = secretKey;
/*     */   }
/*     */   
/*     */   @Column(name="is_bind_google", nullable=false)
/*     */   public int getIsBindGoogle() {
/* 462 */     return this.isBindGoogle;
/*     */   }
/*     */   
/*     */   public void setIsBindGoogle(int isBindGoogle) {
/* 466 */     this.isBindGoogle = isBindGoogle;
/*     */   }
/*     */   
/*     */   @Column(name="related_upid")
/*     */   public int getRelatedUpid() {
/* 471 */     return this.relatedUpid;
/*     */   }
/*     */   
/*     */   public void setRelatedUpid(int relatedUpid) {
/* 475 */     this.relatedUpid = relatedUpid;
/*     */   }
/*     */   
/*     */   @Column(name="related_point", nullable=false, precision=3, scale=2)
/*     */   public double getRelatedPoint() {
/* 480 */     return this.relatedPoint;
/*     */   }
/*     */   
/*     */   public void setRelatedPoint(double relatedPoint) {
/* 484 */     this.relatedPoint = relatedPoint;
/*     */   }
/*     */   
/*     */   @Column(name="utype")
/*     */   public Integer getUtype() {
/* 489 */     return this.utype;
/*     */   }
/*     */   
/*     */   public void setUtype(Integer utype) {
/* 493 */     this.utype = utype;
/*     */   }
/*     */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/content/entity/User.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */