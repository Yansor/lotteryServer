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
/*     */ @Entity
/*     */ @Table(name="lottery", catalog="ecai")
/*     */ public class Lottery
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private int id;
/*     */   private String showName;
/*     */   private String shortName;
/*     */   private int type;
/*     */   private int times;
/*     */   private int sort;
/*     */   private int status;
/*     */   private int self;
/*     */   private int dantiaoMaxPrize;
/*     */   private int expectMaxPrize;
/*     */   private int expectTrans;
/*     */   
/*     */   public Lottery() {}
/*     */   
/*     */   public Lottery(String showName, int type, int times, int sort, int status, int self, int dantiaoMaxPrize, int expectMaxPrize)
/*     */   {
/*  43 */     this.showName = showName;
/*  44 */     this.type = type;
/*  45 */     this.times = times;
/*  46 */     this.sort = sort;
/*  47 */     this.status = status;
/*  48 */     this.self = self;
/*  49 */     this.dantiaoMaxPrize = dantiaoMaxPrize;
/*  50 */     this.expectMaxPrize = expectMaxPrize;
/*     */   }
/*     */   
/*     */ 
/*     */   public Lottery(String showName, String shortName, int type, int times, int sort, int status, int self, int dantiaoMaxPrize, int expectMaxPrize)
/*     */   {
/*  56 */     this.showName = showName;
/*  57 */     this.shortName = shortName;
/*  58 */     this.type = type;
/*  59 */     this.times = times;
/*  60 */     this.sort = sort;
/*  61 */     this.status = status;
/*  62 */     this.self = self;
/*  63 */     this.dantiaoMaxPrize = dantiaoMaxPrize;
/*  64 */     this.expectMaxPrize = expectMaxPrize;
/*     */   }
/*     */   
/*     */   @Id
/*     */   @GeneratedValue(strategy=GenerationType.IDENTITY)
/*     */   @Column(name="id", unique=true, nullable=false)
/*     */   public int getId()
/*     */   {
/*  72 */     return this.id;
/*     */   }
/*     */   
/*     */   public void setId(int id) {
/*  76 */     this.id = id;
/*     */   }
/*     */   
/*     */   @Column(name="show_name", nullable=false, length=32)
/*     */   public String getShowName() {
/*  81 */     return this.showName;
/*     */   }
/*     */   
/*     */   public void setShowName(String showName) {
/*  85 */     this.showName = showName;
/*     */   }
/*     */   
/*     */   @Column(name="short_name", length=32, nullable=false)
/*     */   public String getShortName() {
/*  90 */     return this.shortName;
/*     */   }
/*     */   
/*     */   public void setShortName(String shortName) {
/*  94 */     this.shortName = shortName;
/*     */   }
/*     */   
/*     */   @Column(name="type", nullable=false)
/*     */   public int getType() {
/*  99 */     return this.type;
/*     */   }
/*     */   
/*     */   public void setType(int type) {
/* 103 */     this.type = type;
/*     */   }
/*     */   
/*     */   @Column(name="times", nullable=false)
/*     */   public int getTimes() {
/* 108 */     return this.times;
/*     */   }
/*     */   
/*     */   public void setTimes(int times) {
/* 112 */     this.times = times;
/*     */   }
/*     */   
/*     */   @Column(name="`sort`", nullable=false)
/*     */   public int getSort() {
/* 117 */     return this.sort;
/*     */   }
/*     */   
/*     */   public void setSort(int sort) {
/* 121 */     this.sort = sort;
/*     */   }
/*     */   
/*     */   @Column(name="status", nullable=false)
/*     */   public int getStatus() {
/* 126 */     return this.status;
/*     */   }
/*     */   
/*     */   public void setStatus(int status) {
/* 130 */     this.status = status;
/*     */   }
/*     */   
/*     */   @Column(name="self", nullable=false)
/*     */   public int getSelf() {
/* 135 */     return this.self;
/*     */   }
/*     */   
/*     */   public void setSelf(int self) {
/* 139 */     this.self = self;
/*     */   }
/*     */   
/*     */   @Column(name="dantiao_max_prize", nullable=false)
/*     */   public int getDantiaoMaxPrize() {
/* 144 */     return this.dantiaoMaxPrize;
/*     */   }
/*     */   
/*     */   public void setDantiaoMaxPrize(int dantiaoMaxPrize) {
/* 148 */     this.dantiaoMaxPrize = dantiaoMaxPrize;
/*     */   }
/*     */   
/*     */   @Column(name="expect_max_prize", nullable=false)
/*     */   public int getExpectMaxPrize() {
/* 153 */     return this.expectMaxPrize;
/*     */   }
/*     */   
/*     */   public void setExpectMaxPrize(int expectMaxPrize) {
/* 157 */     this.expectMaxPrize = expectMaxPrize;
/*     */   }
/*     */   
/*     */   @Column(name="expect_trans", nullable=false)
/*     */   public int getExpectTrans() {
/* 162 */     return this.expectTrans;
/*     */   }
/*     */   
/*     */   public void setExpectTrans(int expectTrans) {
/* 166 */     this.expectTrans = expectTrans;
/*     */   }
/*     */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/content/entity/Lottery.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */