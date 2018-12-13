/*    */ package lottery.domains.content.entity;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import javax.persistence.Column;
/*    */ import javax.persistence.Entity;
/*    */ import javax.persistence.GeneratedValue;
/*    */ import javax.persistence.GenerationType;
/*    */ import javax.persistence.Id;
/*    */ import javax.persistence.Table;
/*    */ 
/*    */ @Entity
/*    */ @Table(name="lottery_play_rules_group", catalog="ecai")
/*    */ public class LotteryPlayRulesGroup
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private int id;
/*    */   private int typeId;
/*    */   private String name;
/*    */   private String code;
/*    */   private int status;
/*    */   private int sort;
/*    */   
/*    */   public LotteryPlayRulesGroup() {}
/*    */   
/*    */   public LotteryPlayRulesGroup(LotteryPlayRulesGroup group)
/*    */   {
/* 28 */     this.id = group.getId();
/* 29 */     this.typeId = group.typeId;
/* 30 */     this.name = group.getName();
/* 31 */     this.code = group.getCode();
/* 32 */     this.status = group.getStatus();
/* 33 */     this.sort = group.getSort();
/*    */   }
/*    */   
/*    */   @Id
/*    */   @GeneratedValue(strategy=GenerationType.IDENTITY)
/*    */   @Column(name="id", unique=true, nullable=false)
/*    */   public int getId() {
/* 40 */     return this.id;
/*    */   }
/*    */   
/*    */   public void setId(int id) {
/* 44 */     this.id = id;
/*    */   }
/*    */   
/*    */   @Column(name="type_id", nullable=false)
/*    */   public int getTypeId() {
/* 49 */     return this.typeId;
/*    */   }
/*    */   
/*    */   public void setTypeId(int typeId) {
/* 53 */     this.typeId = typeId;
/*    */   }
/*    */   
/*    */   @Column(name="name", nullable=false, length=128)
/*    */   public String getName() {
/* 58 */     return this.name;
/*    */   }
/*    */   
/*    */   public void setName(String name) {
/* 62 */     this.name = name;
/*    */   }
/*    */   
/*    */   @Column(name="code", nullable=false, length=128)
/*    */   public String getCode() {
/* 67 */     return this.code;
/*    */   }
/*    */   
/*    */   public void setCode(String code) {
/* 71 */     this.code = code;
/*    */   }
/*    */   
/*    */   @Column(name="status")
/*    */   public int getStatus() {
/* 76 */     return this.status;
/*    */   }
/*    */   
/*    */   public void setStatus(int status) {
/* 80 */     this.status = status;
/*    */   }
/*    */   
/*    */   @Column(name="sort")
/*    */   public int getSort() {
/* 85 */     return this.sort;
/*    */   }
/*    */   
/*    */   public void setSort(int sort) {
/* 89 */     this.sort = sort;
/*    */   }
/*    */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/content/entity/LotteryPlayRulesGroup.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */