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
/*     */ @Entity
/*     */ @Table(name="lottery_play_rules", catalog="ecai", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"type_id", "code"})})
/*     */ public class LotteryPlayRules
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private int id;
/*     */   private int groupId;
/*     */   private int typeId;
/*     */   private String name;
/*     */   private String code;
/*     */   private String minNum;
/*     */   private String maxNum;
/*     */   private String totalNum;
/*     */   private int status;
/*     */   private int fixed;
/*     */   private String prize;
/*     */   private String desc;
/*     */   private String dantiao;
/*     */   private int isLocate;
/*     */   
/*     */   public LotteryPlayRules() {}
/*     */   
/*     */   public LotteryPlayRules(LotteryPlayRules rules)
/*     */   {
/*  37 */     this.id = rules.getId();
/*  38 */     this.groupId = rules.getGroupId();
/*  39 */     this.typeId = rules.getTypeId();
/*  40 */     this.name = rules.getName();
/*  41 */     this.code = rules.getCode();
/*  42 */     this.minNum = rules.getMinNum();
/*  43 */     this.maxNum = rules.getMaxNum();
/*  44 */     this.totalNum = rules.getTotalNum();
/*  45 */     this.status = rules.getStatus();
/*  46 */     this.fixed = rules.getFixed();
/*  47 */     this.prize = rules.getPrize();
/*  48 */     this.desc = rules.getDesc();
/*  49 */     this.dantiao = rules.getDantiao();
/*  50 */     this.isLocate = rules.getIsLocate();
/*     */   }
/*     */   
/*     */   @Id
/*     */   @GeneratedValue(strategy=GenerationType.IDENTITY)
/*     */   @Column(name="id", unique=true, nullable=false)
/*     */   public int getId() {
/*  57 */     return this.id;
/*     */   }
/*     */   
/*     */   public void setId(int id) {
/*  61 */     this.id = id;
/*     */   }
/*     */   
/*     */   @Column(name="group_id", nullable=false)
/*     */   public int getGroupId() {
/*  66 */     return this.groupId;
/*     */   }
/*     */   
/*     */   public void setGroupId(int groupId) {
/*  70 */     this.groupId = groupId;
/*     */   }
/*     */   
/*     */   @Column(name="type_id", nullable=false)
/*     */   public int getTypeId() {
/*  75 */     return this.typeId;
/*     */   }
/*     */   
/*     */   public void setTypeId(int typeId) {
/*  79 */     this.typeId = typeId;
/*     */   }
/*     */   
/*     */   @Column(name="name", nullable=false, length=128)
/*     */   public String getName() {
/*  84 */     return this.name;
/*     */   }
/*     */   
/*     */   public void setName(String name) {
/*  88 */     this.name = name;
/*     */   }
/*     */   
/*     */   @Column(name="code", nullable=false, length=128)
/*     */   public String getCode() {
/*  93 */     return this.code;
/*     */   }
/*     */   
/*     */   public void setCode(String code) {
/*  97 */     this.code = code;
/*     */   }
/*     */   
/*     */   @Column(name="min_num", length=128)
/*     */   public String getMinNum() {
/* 102 */     return this.minNum;
/*     */   }
/*     */   
/*     */   public void setMinNum(String minNum) {
/* 106 */     this.minNum = minNum;
/*     */   }
/*     */   
/*     */   @Column(name="max_num", length=128)
/*     */   public String getMaxNum() {
/* 111 */     return this.maxNum;
/*     */   }
/*     */   
/*     */   public void setMaxNum(String maxNum) {
/* 115 */     this.maxNum = maxNum;
/*     */   }
/*     */   
/*     */   @Column(name="total_num", length=128)
/*     */   public String getTotalNum() {
/* 120 */     return this.totalNum;
/*     */   }
/*     */   
/*     */   public void setTotalNum(String totalNum) {
/* 124 */     this.totalNum = totalNum;
/*     */   }
/*     */   
/*     */   @Column(name="status")
/*     */   public int getStatus() {
/* 129 */     return this.status;
/*     */   }
/*     */   
/*     */   public void setStatus(int status) {
/* 133 */     this.status = status;
/*     */   }
/*     */   
/*     */   @Column(name="fixed", nullable=false)
/*     */   public int getFixed() {
/* 138 */     return this.fixed;
/*     */   }
/*     */   
/*     */   public void setFixed(int fixed) {
/* 142 */     this.fixed = fixed;
/*     */   }
/*     */   
/*     */   @Column(name="prize", nullable=false, length=512)
/*     */   public String getPrize() {
/* 147 */     return this.prize;
/*     */   }
/*     */   
/*     */   public void setPrize(String prize) {
/* 151 */     this.prize = prize;
/*     */   }
/*     */   
/*     */   @Column(name="desc", length=256)
/*     */   public String getDesc() {
/* 156 */     return this.desc;
/*     */   }
/*     */   
/*     */   public void setDesc(String desc) {
/* 160 */     this.desc = desc;
/*     */   }
/*     */   
/*     */   @Column(name="dantiao", length=128)
/*     */   public String getDantiao() {
/* 165 */     return this.dantiao;
/*     */   }
/*     */   
/*     */   public void setDantiao(String dantiao) {
/* 169 */     this.dantiao = dantiao;
/*     */   }
/*     */   
/*     */   @Column(name="is_locate", nullable=false)
/*     */   public int getIsLocate() {
/* 174 */     return this.isLocate;
/*     */   }
/*     */   
/*     */   public void setIsLocate(int isLocate) {
/* 178 */     this.isLocate = isLocate;
/*     */   }
/*     */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/content/entity/LotteryPlayRules.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */