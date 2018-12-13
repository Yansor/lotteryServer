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
/*     */ @Table(name="lottery_open_time", catalog="ecai")
/*     */ public class LotteryOpenTime
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private int id;
/*     */   private String lottery;
/*     */   private String expect;
/*     */   private String startTime;
/*     */   private String stopTime;
/*     */   private String openTime;
/*     */   private Boolean isTodayExpect;
/*     */   
/*     */   public LotteryOpenTime() {}
/*     */   
/*     */   public LotteryOpenTime(String lottery, String expect, String startTime, String stopTime, String openTime, Boolean isTodayExpect)
/*     */   {
/*  39 */     this.lottery = lottery;
/*  40 */     this.expect = expect;
/*  41 */     this.startTime = startTime;
/*  42 */     this.stopTime = stopTime;
/*  43 */     this.openTime = openTime;
/*  44 */     this.isTodayExpect = isTodayExpect;
/*     */   }
/*     */   
/*     */   @Id
/*     */   @GeneratedValue(strategy=GenerationType.IDENTITY)
/*     */   @Column(name="id", unique=true, nullable=false)
/*     */   public int getId()
/*     */   {
/*  52 */     return this.id;
/*     */   }
/*     */   
/*     */   public void setId(int id) {
/*  56 */     this.id = id;
/*     */   }
/*     */   
/*     */   @Column(name="lottery", nullable=false, length=32)
/*     */   public String getLottery() {
/*  61 */     return this.lottery;
/*     */   }
/*     */   
/*     */   public void setLottery(String lottery) {
/*  65 */     this.lottery = lottery;
/*     */   }
/*     */   
/*     */   @Column(name="expect", nullable=false, length=32)
/*     */   public String getExpect() {
/*  70 */     return this.expect;
/*     */   }
/*     */   
/*     */   public void setExpect(String expect) {
/*  74 */     this.expect = expect;
/*     */   }
/*     */   
/*     */   @Column(name="start_time", nullable=false, length=19)
/*     */   public String getStartTime() {
/*  79 */     return this.startTime;
/*     */   }
/*     */   
/*     */   public void setStartTime(String startTime) {
/*  83 */     this.startTime = startTime;
/*     */   }
/*     */   
/*     */   @Column(name="stop_time", nullable=false, length=19)
/*     */   public String getStopTime() {
/*  88 */     return this.stopTime;
/*     */   }
/*     */   
/*     */   public void setStopTime(String stopTime) {
/*  92 */     this.stopTime = stopTime;
/*     */   }
/*     */   
/*     */   @Column(name="open_time", nullable=false, length=19)
/*     */   public String getOpenTime() {
/*  97 */     return this.openTime;
/*     */   }
/*     */   
/*     */   public void setOpenTime(String openTime) {
/* 101 */     this.openTime = openTime;
/*     */   }
/*     */   
/*     */   @Column(name="is_today_expect", nullable=false)
/*     */   public Boolean getIsTodayExpect() {
/* 106 */     return this.isTodayExpect;
/*     */   }
/*     */   
/*     */   public void setIsTodayExpect(Boolean isTodayExpect) {
/* 110 */     this.isTodayExpect = isTodayExpect;
/*     */   }
/*     */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/content/entity/LotteryOpenTime.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */