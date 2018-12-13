/*     */ package lottery.domains.content.entity;
/*     */ 
/*     */ import com.alibaba.fastjson.annotation.JSONField;
/*     */ import java.io.Serializable;
/*     */ import javax.persistence.Column;
/*     */ import javax.persistence.Entity;
/*     */ import javax.persistence.GeneratedValue;
/*     */ import javax.persistence.Id;
/*     */ 
/*     */ @Entity
/*     */ @javax.persistence.Table(name="user_bets_hit_ranking", catalog="ecai")
/*     */ public class UserBetsHitRanking implements Serializable, Comparable<UserBetsHitRanking>
/*     */ {
/*     */   private static final long serialVersionUID = -2265717841611668255L;
/*     */   @JSONField(serialize=false)
/*     */   private int id;
/*     */   private String name;
/*     */   private String username;
/*     */   private int prizeMoney;
/*     */   @JSONField(serialize=false)
/*     */   private String time;
/*     */   private String code;
/*     */   private String type;
/*     */   private int platform;
/*     */   
/*     */   public UserBetsHitRanking() {}
/*     */   
/*     */   public UserBetsHitRanking(int id, String name, String username, int prizeMoney, String time, String code, String type, int platform)
/*     */   {
/*  30 */     this.id = id;
/*  31 */     this.name = name;
/*  32 */     this.username = username;
/*  33 */     this.prizeMoney = prizeMoney;
/*  34 */     this.time = time;
/*  35 */     this.code = code;
/*  36 */     this.type = type;
/*  37 */     this.platform = platform;
/*     */   }
/*     */   
/*     */   @Id
/*     */   @GeneratedValue(strategy=javax.persistence.GenerationType.IDENTITY)
/*     */   @Column(name="id", unique=true, nullable=false)
/*     */   public int getId() {
/*  44 */     return this.id;
/*     */   }
/*     */   
/*     */   public void setId(int id) {
/*  48 */     this.id = id;
/*     */   }
/*     */   
/*     */   @Column(name="name", nullable=false, length=256)
/*     */   public String getName() {
/*  53 */     return this.name;
/*     */   }
/*     */   
/*     */   public void setName(String name) {
/*  57 */     this.name = name;
/*     */   }
/*     */   
/*     */   @Column(name="username", nullable=false, length=256)
/*     */   public String getUsername() {
/*  62 */     return this.username;
/*     */   }
/*     */   
/*     */   public void setUsername(String username) {
/*  66 */     this.username = username;
/*     */   }
/*     */   
/*     */   @Column(name="prize_money", nullable=false)
/*     */   public int getPrizeMoney() {
/*  71 */     return this.prizeMoney;
/*     */   }
/*     */   
/*     */   public void setPrizeMoney(int prizeMoney) {
/*  75 */     this.prizeMoney = prizeMoney;
/*     */   }
/*     */   
/*     */   @Column(name="time", nullable=false, length=20)
/*     */   public String getTime() {
/*  80 */     return this.time;
/*     */   }
/*     */   
/*     */   public void setTime(String time) {
/*  84 */     this.time = time;
/*     */   }
/*     */   
/*     */   @Column(name="code")
/*     */   public String getCode() {
/*  89 */     return this.code;
/*     */   }
/*     */   
/*     */   public void setCode(String code) {
/*  93 */     this.code = code;
/*     */   }
/*     */   
/*     */   @Column(name="type")
/*     */   public String getType() {
/*  98 */     return this.type;
/*     */   }
/*     */   
/*     */   public void setType(String type) {
/* 102 */     this.type = type;
/*     */   }
/*     */   
/*     */   @Column(name="platform", nullable=false)
/*     */   public int getPlatform() {
/* 107 */     return this.platform;
/*     */   }
/*     */   
/*     */   public void setPlatform(int platform) {
/* 111 */     this.platform = platform;
/*     */   }
/*     */   
/*     */   public int compareTo(UserBetsHitRanking o)
/*     */   {
/* 116 */     if (getPrizeMoney() == o.getPrizeMoney()) {
/* 117 */       return 1;
/*     */     }
/*     */     
/* 120 */     return o.getPrizeMoney() > getPrizeMoney() ? 1 : -1;
/*     */   }
/*     */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/content/entity/UserBetsHitRanking.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */