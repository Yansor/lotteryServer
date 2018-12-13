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
/*     */ @Table(name="lottery_crawler_status", catalog="ecai")
/*     */ public class LotteryCrawlerStatus
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private Integer id;
/*     */   private String showName;
/*     */   private String shortName;
/*     */   private Integer times;
/*     */   private String lastUpdate;
/*     */   private String lastExpect;
/*     */   
/*     */   public LotteryCrawlerStatus() {}
/*     */   
/*     */   public LotteryCrawlerStatus(String showName, String shortName, Integer times, String lastUpdate, String lastExpect)
/*     */   {
/*  43 */     this.showName = showName;
/*  44 */     this.shortName = shortName;
/*  45 */     this.times = times;
/*  46 */     this.lastUpdate = lastUpdate;
/*  47 */     this.lastExpect = lastExpect;
/*     */   }
/*     */   
/*     */   @Id
/*     */   @GeneratedValue(strategy=GenerationType.IDENTITY)
/*     */   @Column(name="id", unique=true, nullable=false)
/*     */   public Integer getId()
/*     */   {
/*  55 */     return this.id;
/*     */   }
/*     */   
/*     */   public void setId(Integer id) {
/*  59 */     this.id = id;
/*     */   }
/*     */   
/*     */   @Column(name="show_name", nullable=false, length=128)
/*     */   public String getShowName() {
/*  64 */     return this.showName;
/*     */   }
/*     */   
/*     */   public void setShowName(String showName) {
/*  68 */     this.showName = showName;
/*     */   }
/*     */   
/*     */   @Column(name="short_name", nullable=false, length=32)
/*     */   public String getShortName() {
/*  73 */     return this.shortName;
/*     */   }
/*     */   
/*     */   public void setShortName(String shortName) {
/*  77 */     this.shortName = shortName;
/*     */   }
/*     */   
/*     */   @Column(name="times", nullable=false)
/*     */   public Integer getTimes() {
/*  82 */     return this.times;
/*     */   }
/*     */   
/*     */   public void setTimes(Integer times) {
/*  86 */     this.times = times;
/*     */   }
/*     */   
/*     */   @Column(name="last_update", nullable=false, length=19)
/*     */   public String getLastUpdate() {
/*  91 */     return this.lastUpdate;
/*     */   }
/*     */   
/*     */   public void setLastUpdate(String lastUpdate) {
/*  95 */     this.lastUpdate = lastUpdate;
/*     */   }
/*     */   
/*     */   @Column(name="last_expect", nullable=false, length=32)
/*     */   public String getLastExpect() {
/* 100 */     return this.lastExpect;
/*     */   }
/*     */   
/*     */   public void setLastExpect(String lastExpect) {
/* 104 */     this.lastExpect = lastExpect;
/*     */   }
/*     */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryCapture/lotteryCaptureRepair.jar!/lottery/domains/content/entity/LotteryCrawlerStatus.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */