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
/*     */ 
/*     */ @Entity
/*     */ @Table(name="lottery_open_code", catalog="ecai", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"lottery", "expect", "user_id"})})
/*     */ public class LotteryOpenCode
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private Integer id;
/*     */   private Integer userId;
/*     */   private String lottery;
/*     */   private String expect;
/*     */   private String code;
/*     */   private String time;
/*     */   private String interfaceTime;
/*     */   private Integer openStatus;
/*     */   private String openTime;
/*     */   private String remarks;
/*     */   
/*     */   public LotteryOpenCode() {}
/*     */   
/*     */   public LotteryOpenCode(String lottery, String expect, String code, String time, Integer openStatus)
/*     */   {
/*  48 */     this.lottery = lottery;
/*  49 */     this.expect = expect;
/*  50 */     this.code = code;
/*  51 */     this.time = time;
/*  52 */     this.openStatus = openStatus;
/*     */   }
/*     */   
/*     */ 
/*     */   public LotteryOpenCode(String lottery, String expect, String code, String time, Integer openStatus, String openTime, String remarks)
/*     */   {
/*  58 */     this.lottery = lottery;
/*  59 */     this.expect = expect;
/*  60 */     this.code = code;
/*  61 */     this.time = time;
/*  62 */     this.openStatus = openStatus;
/*  63 */     this.openTime = openTime;
/*  64 */     this.remarks = remarks;
/*     */   }
/*     */   
/*     */   @Id
/*     */   @GeneratedValue(strategy=GenerationType.IDENTITY)
/*     */   @Column(name="id", unique=true, nullable=false)
/*     */   public Integer getId()
/*     */   {
/*  72 */     return this.id;
/*     */   }
/*     */   
/*     */   public void setId(Integer id) {
/*  76 */     this.id = id;
/*     */   }
/*     */   
/*     */   @Column(name="user_id")
/*     */   public Integer getUserId() {
/*  81 */     return this.userId;
/*     */   }
/*     */   
/*     */   public void setUserId(Integer userId) {
/*  85 */     this.userId = userId;
/*     */   }
/*     */   
/*     */   @Column(name="lottery", nullable=false, length=32)
/*     */   public String getLottery() {
/*  90 */     return this.lottery;
/*     */   }
/*     */   
/*     */   public void setLottery(String lottery) {
/*  94 */     this.lottery = lottery;
/*     */   }
/*     */   
/*     */   @Column(name="expect", nullable=false, length=32)
/*     */   public String getExpect() {
/*  99 */     return this.expect;
/*     */   }
/*     */   
/*     */   public void setExpect(String expect) {
/* 103 */     this.expect = expect;
/*     */   }
/*     */   
/*     */   @Column(name="code", nullable=false, length=128)
/*     */   public String getCode() {
/* 108 */     return this.code;
/*     */   }
/*     */   
/*     */   public void setCode(String code) {
/* 112 */     this.code = code;
/*     */   }
/*     */   
/*     */   @Column(name="time", nullable=false, length=19)
/*     */   public String getTime() {
/* 117 */     return this.time;
/*     */   }
/*     */   
/*     */   public void setTime(String time) {
/* 121 */     this.time = time;
/*     */   }
/*     */   
/*     */   @Column(name="interface_time", nullable=false, length=19)
/*     */   public String getInterfaceTime() {
/* 126 */     return this.interfaceTime;
/*     */   }
/*     */   
/*     */   public void setInterfaceTime(String interfaceTime) {
/* 130 */     this.interfaceTime = interfaceTime;
/*     */   }
/*     */   
/*     */   @Column(name="open_status", nullable=false)
/*     */   public Integer getOpenStatus() {
/* 135 */     return this.openStatus;
/*     */   }
/*     */   
/*     */   public void setOpenStatus(Integer openStatus) {
/* 139 */     this.openStatus = openStatus;
/*     */   }
/*     */   
/*     */   @Column(name="open_time")
/*     */   public String getOpenTime() {
/* 144 */     return this.openTime;
/*     */   }
/*     */   
/*     */   public void setOpenTime(String openTime) {
/* 148 */     this.openTime = openTime;
/*     */   }
/*     */   
/*     */   @Column(name="remarks", length=128)
/*     */   public String getRemarks() {
/* 153 */     return this.remarks;
/*     */   }
/*     */   
/*     */   public void setRemarks(String remarks) {
/* 157 */     this.remarks = remarks;
/*     */   }
/*     */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryCapture/lotteryCaptureRepair.jar!/lottery/domains/content/entity/LotteryOpenCode.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */