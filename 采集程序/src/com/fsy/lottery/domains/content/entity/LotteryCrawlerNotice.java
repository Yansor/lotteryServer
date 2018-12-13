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
/*     */ @Table(name="lottery_crawler_notice", catalog="ecai")
/*     */ public class LotteryCrawlerNotice
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private Integer id;
/*     */   private String name;
/*     */   private String type;
/*     */   private String host;
/*     */   private Integer port;
/*     */   private String username;
/*     */   private String password;
/*     */   private String method;
/*     */   private String url;
/*     */   private Integer status;
/*     */   
/*     */   public LotteryCrawlerNotice() {}
/*     */   
/*     */   public LotteryCrawlerNotice(String name, String type, String host, Integer port, String method, Integer status)
/*     */   {
/*  47 */     this.name = name;
/*  48 */     this.type = type;
/*  49 */     this.host = host;
/*  50 */     this.port = port;
/*  51 */     this.method = method;
/*  52 */     this.status = status;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public LotteryCrawlerNotice(String name, String type, String host, Integer port, String username, String password, String method, String url, Integer status)
/*     */   {
/*  59 */     this.name = name;
/*  60 */     this.type = type;
/*  61 */     this.host = host;
/*  62 */     this.port = port;
/*  63 */     this.username = username;
/*  64 */     this.password = password;
/*  65 */     this.method = method;
/*  66 */     this.url = url;
/*  67 */     this.status = status;
/*     */   }
/*     */   
/*     */   @Id
/*     */   @GeneratedValue(strategy=GenerationType.IDENTITY)
/*     */   @Column(name="id", unique=true, nullable=false)
/*     */   public Integer getId()
/*     */   {
/*  75 */     return this.id;
/*     */   }
/*     */   
/*     */   public void setId(Integer id) {
/*  79 */     this.id = id;
/*     */   }
/*     */   
/*     */   @Column(name="name", nullable=false)
/*     */   public String getName() {
/*  84 */     return this.name;
/*     */   }
/*     */   
/*     */   public void setName(String name) {
/*  88 */     this.name = name;
/*     */   }
/*     */   
/*     */   @Column(name="type", nullable=false, length=32)
/*     */   public String getType() {
/*  93 */     return this.type;
/*     */   }
/*     */   
/*     */   public void setType(String type) {
/*  97 */     this.type = type;
/*     */   }
/*     */   
/*     */   @Column(name="host", nullable=false, length=256)
/*     */   public String getHost() {
/* 102 */     return this.host;
/*     */   }
/*     */   
/*     */   public void setHost(String host) {
/* 106 */     this.host = host;
/*     */   }
/*     */   
/*     */   @Column(name="port", nullable=false)
/*     */   public Integer getPort() {
/* 111 */     return this.port;
/*     */   }
/*     */   
/*     */   public void setPort(Integer port) {
/* 115 */     this.port = port;
/*     */   }
/*     */   
/*     */   @Column(name="username")
/*     */   public String getUsername() {
/* 120 */     return this.username;
/*     */   }
/*     */   
/*     */   public void setUsername(String username) {
/* 124 */     this.username = username;
/*     */   }
/*     */   
/*     */   @Column(name="password")
/*     */   public String getPassword() {
/* 129 */     return this.password;
/*     */   }
/*     */   
/*     */   public void setPassword(String password) {
/* 133 */     this.password = password;
/*     */   }
/*     */   
/*     */   @Column(name="method", nullable=false, length=32)
/*     */   public String getMethod() {
/* 138 */     return this.method;
/*     */   }
/*     */   
/*     */   public void setMethod(String method) {
/* 142 */     this.method = method;
/*     */   }
/*     */   
/*     */   @Column(name="url")
/*     */   public String getUrl() {
/* 147 */     return this.url;
/*     */   }
/*     */   
/*     */   public void setUrl(String url) {
/* 151 */     this.url = url;
/*     */   }
/*     */   
/*     */   @Column(name="status", nullable=false)
/*     */   public Integer getStatus() {
/* 156 */     return this.status;
/*     */   }
/*     */   
/*     */   public void setStatus(Integer status) {
/* 160 */     this.status = status;
/*     */   }
/*     */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryCapture/lotteryCaptureRepair.jar!/lottery/domains/content/entity/LotteryCrawlerNotice.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */