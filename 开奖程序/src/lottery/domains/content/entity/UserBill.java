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
/*     */ @Entity
/*     */ @Table(name="user_bill", catalog="ecai", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"billno"})})
/*     */ public class UserBill
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private int id;
/*     */   private String billno;
/*     */   private int userId;
/*     */   private int account;
/*     */   private int type;
/*     */   private double money;
/*     */   private double beforeMoney;
/*     */   private double afterMoney;
/*     */   private Integer refType;
/*     */   private String refId;
/*     */   private String time;
/*     */   private String remarks;
/*     */   
/*     */   public UserBill() {}
/*     */   
/*     */   public UserBill(String billno, int userId, int account, int type, double money, double beforeMoney, double afterMoney, String time, String remarks)
/*     */   {
/*  45 */     this.billno = billno;
/*  46 */     this.userId = userId;
/*  47 */     this.account = account;
/*  48 */     this.type = type;
/*  49 */     this.money = money;
/*  50 */     this.beforeMoney = beforeMoney;
/*  51 */     this.afterMoney = afterMoney;
/*  52 */     this.time = time;
/*  53 */     this.remarks = remarks;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public UserBill(String billno, int userId, int account, int type, double money, double beforeMoney, double afterMoney, Integer refType, String refId, String time, String remarks)
/*     */   {
/*  60 */     this.billno = billno;
/*  61 */     this.userId = userId;
/*  62 */     this.account = account;
/*  63 */     this.type = type;
/*  64 */     this.money = money;
/*  65 */     this.beforeMoney = beforeMoney;
/*  66 */     this.afterMoney = afterMoney;
/*  67 */     this.refType = refType;
/*  68 */     this.refId = refId;
/*  69 */     this.time = time;
/*  70 */     this.remarks = remarks;
/*     */   }
/*     */   
/*     */   @Id
/*     */   @GeneratedValue(strategy=GenerationType.IDENTITY)
/*     */   @Column(name="id", unique=true, nullable=false)
/*     */   public int getId()
/*     */   {
/*  78 */     return this.id;
/*     */   }
/*     */   
/*     */   public void setId(int id) {
/*  82 */     this.id = id;
/*     */   }
/*     */   
/*     */   @Column(name="billno", unique=true, nullable=false, length=32)
/*     */   public String getBillno() {
/*  87 */     return this.billno;
/*     */   }
/*     */   
/*     */   public void setBillno(String billno) {
/*  91 */     this.billno = billno;
/*     */   }
/*     */   
/*     */   @Column(name="user_id", nullable=false)
/*     */   public int getUserId() {
/*  96 */     return this.userId;
/*     */   }
/*     */   
/*     */   public void setUserId(int userId) {
/* 100 */     this.userId = userId;
/*     */   }
/*     */   
/*     */   @Column(name="account", nullable=false)
/*     */   public int getAccount() {
/* 105 */     return this.account;
/*     */   }
/*     */   
/*     */   public void setAccount(int account) {
/* 109 */     this.account = account;
/*     */   }
/*     */   
/*     */   @Column(name="type", nullable=false)
/*     */   public int getType() {
/* 114 */     return this.type;
/*     */   }
/*     */   
/*     */   public void setType(int type) {
/* 118 */     this.type = type;
/*     */   }
/*     */   
/*     */   @Column(name="money", nullable=false, precision=16, scale=5)
/*     */   public double getMoney() {
/* 123 */     return this.money;
/*     */   }
/*     */   
/*     */   public void setMoney(double money) {
/* 127 */     this.money = money;
/*     */   }
/*     */   
/*     */   @Column(name="before_money", nullable=false, precision=16, scale=5)
/*     */   public double getBeforeMoney() {
/* 132 */     return this.beforeMoney;
/*     */   }
/*     */   
/*     */   public void setBeforeMoney(double beforeMoney) {
/* 136 */     this.beforeMoney = beforeMoney;
/*     */   }
/*     */   
/*     */   @Column(name="after_money", nullable=false, precision=16, scale=5)
/*     */   public double getAfterMoney() {
/* 141 */     return this.afterMoney;
/*     */   }
/*     */   
/*     */   public void setAfterMoney(double afterMoney) {
/* 145 */     this.afterMoney = afterMoney;
/*     */   }
/*     */   
/*     */   @Column(name="ref_type")
/*     */   public Integer getRefType() {
/* 150 */     return this.refType;
/*     */   }
/*     */   
/*     */   public void setRefType(Integer refType) {
/* 154 */     this.refType = refType;
/*     */   }
/*     */   
/*     */   @Column(name="ref_id", length=32)
/*     */   public String getRefId() {
/* 159 */     return this.refId;
/*     */   }
/*     */   
/*     */   public void setRefId(String refId) {
/* 163 */     this.refId = refId;
/*     */   }
/*     */   
/*     */   @Column(name="time", nullable=false, length=19)
/*     */   public String getTime() {
/* 168 */     return this.time;
/*     */   }
/*     */   
/*     */   public void setTime(String time) {
/* 172 */     this.time = time;
/*     */   }
/*     */   
/*     */   @Column(name="remarks", nullable=false, length=256)
/*     */   public String getRemarks() {
/* 177 */     return this.remarks;
/*     */   }
/*     */   
/*     */   public void setRemarks(String remarks) {
/* 181 */     this.remarks = remarks;
/*     */   }
/*     */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/content/entity/UserBill.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */