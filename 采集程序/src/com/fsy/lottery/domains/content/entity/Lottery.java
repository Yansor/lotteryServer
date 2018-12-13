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
/*     */   
/*     */   public Lottery() {}
/*     */   
/*     */   public Lottery(String showName, int type, int times, int sort, int status, int self)
/*     */   {
/*  40 */     this.showName = showName;
/*  41 */     this.type = type;
/*  42 */     this.times = times;
/*  43 */     this.sort = sort;
/*  44 */     this.status = status;
/*  45 */     this.self = self;
/*     */   }
/*     */   
/*     */ 
/*     */   public Lottery(String showName, String shortName, int type, int times, int sort, int status, int self)
/*     */   {
/*  51 */     this.showName = showName;
/*  52 */     this.shortName = shortName;
/*  53 */     this.type = type;
/*  54 */     this.times = times;
/*  55 */     this.sort = sort;
/*  56 */     this.status = status;
/*  57 */     this.self = self;
/*     */   }
/*     */   
/*     */   @Id
/*     */   @GeneratedValue(strategy=GenerationType.IDENTITY)
/*     */   @Column(name="id", unique=true, nullable=false)
/*     */   public int getId()
/*     */   {
/*  65 */     return this.id;
/*     */   }
/*     */   
/*     */   public void setId(int id) {
/*  69 */     this.id = id;
/*     */   }
/*     */   
/*     */   @Column(name="show_name", nullable=false, length=32)
/*     */   public String getShowName() {
/*  74 */     return this.showName;
/*     */   }
/*     */   
/*     */   public void setShowName(String showName) {
/*  78 */     this.showName = showName;
/*     */   }
/*     */   
/*     */   @Column(name="short_name", length=32, nullable=false)
/*     */   public String getShortName() {
/*  83 */     return this.shortName;
/*     */   }
/*     */   
/*     */   public void setShortName(String shortName) {
/*  87 */     this.shortName = shortName;
/*     */   }
/*     */   
/*     */   @Column(name="type", nullable=false)
/*     */   public int getType() {
/*  92 */     return this.type;
/*     */   }
/*     */   
/*     */   public void setType(int type) {
/*  96 */     this.type = type;
/*     */   }
/*     */   
/*     */   @Column(name="times", nullable=false)
/*     */   public int getTimes() {
/* 101 */     return this.times;
/*     */   }
/*     */   
/*     */   public void setTimes(int times) {
/* 105 */     this.times = times;
/*     */   }
/*     */   
/*     */   @Column(name="`sort`", nullable=false)
/*     */   public int getSort() {
/* 110 */     return this.sort;
/*     */   }
/*     */   
/*     */   public void setSort(int sort) {
/* 114 */     this.sort = sort;
/*     */   }
/*     */   
/*     */   @Column(name="status", nullable=false)
/*     */   public int getStatus() {
/* 119 */     return this.status;
/*     */   }
/*     */   
/*     */   public void setStatus(int status) {
/* 123 */     this.status = status;
/*     */   }
/*     */   
/*     */   @Column(name="self", nullable=false)
/*     */   public int getSelf() {
/* 128 */     return this.self;
/*     */   }
/*     */   
/*     */   public void setSelf(int self) {
/* 132 */     this.self = self;
/*     */   }
/*     */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryCapture/lotteryCaptureRepair.jar!/lottery/domains/content/entity/Lottery.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */