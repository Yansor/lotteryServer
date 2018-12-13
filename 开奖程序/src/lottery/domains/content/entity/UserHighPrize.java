/*     */ package lottery.domains.content.entity;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import javax.persistence.Column;
/*     */ import javax.persistence.Entity;
/*     */ import javax.persistence.GeneratedValue;
/*     */ import javax.persistence.Id;
/*     */ 
/*     */ @Entity
/*     */ @javax.persistence.Table(name="user_high_prize", catalog="ecai")
/*     */ public class UserHighPrize implements Serializable, Cloneable
/*     */ {
/*     */   private int id;
/*     */   private int userId;
/*     */   private int platform;
/*     */   private String name;
/*     */   private String nameId;
/*     */   private String subName;
/*     */   private String refId;
/*     */   private double money;
/*     */   private double prizeMoney;
/*     */   private double times;
/*     */   private String time;
/*     */   private int status;
/*     */   private String confirmUsername;
/*     */   
/*     */   public UserHighPrize clone()
/*     */   {
/*     */     try
/*     */     {
/*  31 */       return (UserHighPrize)super.clone();
/*     */     } catch (Exception localException) {}
/*  33 */     return null;
/*     */   }
/*     */   
/*     */   @Id
/*     */   @GeneratedValue(strategy=javax.persistence.GenerationType.IDENTITY)
/*     */   @Column(name="id", unique=true, nullable=false)
/*     */   public int getId() {
/*  40 */     return this.id;
/*     */   }
/*     */   
/*     */   public void setId(int id) {
/*  44 */     this.id = id;
/*     */   }
/*     */   
/*     */   @Column(name="user_id", nullable=false)
/*     */   public int getUserId() {
/*  49 */     return this.userId;
/*     */   }
/*     */   
/*     */   public void setUserId(int userId) {
/*  53 */     this.userId = userId;
/*     */   }
/*     */   
/*     */   @Column(name="platform", nullable=false)
/*     */   public int getPlatform() {
/*  58 */     return this.platform;
/*     */   }
/*     */   
/*     */   public void setPlatform(int platform) {
/*  62 */     this.platform = platform;
/*     */   }
/*     */   
/*     */   @Column(name="name", nullable=false, length=512)
/*     */   public String getName() {
/*  67 */     return this.name;
/*     */   }
/*     */   
/*     */   public void setName(String name) {
/*  71 */     this.name = name;
/*     */   }
/*     */   
/*     */   @Column(name="name_id", nullable=false, length=256)
/*     */   public String getNameId() {
/*  76 */     return this.nameId;
/*     */   }
/*     */   
/*     */   public void setNameId(String nameId) {
/*  80 */     this.nameId = nameId;
/*     */   }
/*     */   
/*     */   @Column(name="sub_name", nullable=false, length=512)
/*     */   public String getSubName() {
/*  85 */     return this.subName;
/*     */   }
/*     */   
/*     */   public void setSubName(String subName) {
/*  89 */     this.subName = subName;
/*     */   }
/*     */   
/*     */   @Column(name="ref_id", nullable=false, length=512)
/*     */   public String getRefId() {
/*  94 */     return this.refId;
/*     */   }
/*     */   
/*     */   public void setRefId(String refId) {
/*  98 */     this.refId = refId;
/*     */   }
/*     */   
/*     */   @Column(name="money", nullable=false, precision=16, scale=5)
/*     */   public double getMoney() {
/* 103 */     return this.money;
/*     */   }
/*     */   
/*     */   public void setMoney(double money) {
/* 107 */     this.money = money;
/*     */   }
/*     */   
/*     */   @Column(name="prize_money", nullable=false, precision=16, scale=5)
/*     */   public double getPrizeMoney() {
/* 112 */     return this.prizeMoney;
/*     */   }
/*     */   
/*     */   public void setPrizeMoney(double prizeMoney) {
/* 116 */     this.prizeMoney = prizeMoney;
/*     */   }
/*     */   
/*     */   @Column(name="times", nullable=false, precision=16, scale=5)
/*     */   public double getTimes() {
/* 121 */     return this.times;
/*     */   }
/*     */   
/*     */   public void setTimes(double times) {
/* 125 */     this.times = times;
/*     */   }
/*     */   
/*     */   @Column(name="time", nullable=false, length=19)
/*     */   public String getTime() {
/* 130 */     return this.time;
/*     */   }
/*     */   
/*     */   public void setTime(String time) {
/* 134 */     this.time = time;
/*     */   }
/*     */   
/*     */   @Column(name="status", nullable=false)
/*     */   public int getStatus() {
/* 139 */     return this.status;
/*     */   }
/*     */   
/*     */   public void setStatus(int status) {
/* 143 */     this.status = status;
/*     */   }
/*     */   
/*     */   @Column(name="confirm_username", length=50)
/*     */   public String getConfirmUsername() {
/* 148 */     return this.confirmUsername;
/*     */   }
/*     */   
/*     */   public void setConfirmUsername(String confirmUsername) {
/* 152 */     this.confirmUsername = confirmUsername;
/*     */   }
/*     */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/content/entity/UserHighPrize.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */