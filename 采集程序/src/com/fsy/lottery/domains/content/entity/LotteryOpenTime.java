/*     */ package com.fsy.lottery.domains.content.entity;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ @Entity
/*     */ @Table(name="lottery_open_time", catalog="ecai")
/*     */ public class LotteryOpenTime
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private Integer id;
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
/*  44 */     this.lottery = lottery;
/*  45 */     this.expect = expect;
/*  46 */     this.startTime = startTime;
/*  47 */     this.stopTime = stopTime;
/*  48 */     this.openTime = openTime;
/*  49 */     this.isTodayExpect = isTodayExpect;
/*     */   }
/*     */   
/*     */   @Id
/*     */   @GeneratedValue(strategy=GenerationType.IDENTITY)
/*     */   @Column(name="id", unique=true, nullable=false)
/*     */   public Integer getId()
/*     */   {
/*  57 */     return this.id;
/*     */   }
/*     */   
/*     */   public void setId(Integer id) {
/*  61 */     this.id = id;
/*     */   }
/*     */   
/*     */   @Column(name="lottery", nullable=false, length=32)
/*     */   public String getLottery() {
/*  66 */     return this.lottery;
/*     */   }
/*     */   
/*     */   public void setLottery(String lottery) {
/*  70 */     this.lottery = lottery;
/*     */   }
/*     */   
/*     */   @Column(name="expect", nullable=false, length=32)
/*     */   public String getExpect() {
/*  75 */     return this.expect;
/*     */   }
/*     */   
/*     */   public void setExpect(String expect) {
/*  79 */     this.expect = expect;
/*     */   }
/*     */   
/*     */   @Column(name="start_time", nullable=false, length=19)
/*     */   public String getStartTime() {
/*  84 */     return this.startTime;
/*     */   }
/*     */   
/*     */   public void setStartTime(String startTime) {
/*  88 */     this.startTime = startTime;
/*     */   }
/*     */   
/*     */   @Column(name="stop_time", nullable=false, length=19)
/*     */   public String getStopTime() {
/*  93 */     return this.stopTime;
/*     */   }
/*     */   
/*     */   public void setStopTime(String stopTime) {
/*  97 */     this.stopTime = stopTime;
/*     */   }
/*     */   
/*     */   @Column(name="open_time", nullable=false, length=19)
/*     */   public String getOpenTime() {
/* 102 */     return this.openTime;
/*     */   }
/*     */   
/*     */   public void setOpenTime(String openTime) {
/* 106 */     this.openTime = openTime;
/*     */   }
/*     */   
/*     */   @Column(name="is_today_expect", nullable=false)
/*     */   public Boolean getIsTodayExpect() {
/* 111 */     return this.isTodayExpect;
/*     */   }
/*     */   
/*     */   public void setIsTodayExpect(Boolean isTodayExpect) {
/* 115 */     this.isTodayExpect = isTodayExpect;
/*     */   }
/*     */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryCapture/lotteryCaptureRepair.jar!/lottery/domains/content/entity/LotteryOpenTime.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */