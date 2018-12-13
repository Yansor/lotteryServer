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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Entity
/*    */ @Table(name="lottery_type", catalog="ecai")
/*    */ public class LotteryType
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private int id;
/*    */   private String name;
/*    */   private int sort;
/*    */   private int status;
/*    */   
/*    */   public LotteryType() {}
/*    */   
/*    */   public LotteryType(String name, int sort, int status)
/*    */   {
/* 35 */     this.name = name;
/* 36 */     this.sort = sort;
/* 37 */     this.status = status;
/*    */   }
/*    */   
/*    */   @Id
/*    */   @GeneratedValue(strategy=GenerationType.IDENTITY)
/*    */   @Column(name="id", unique=true, nullable=false)
/*    */   public int getId()
/*    */   {
/* 45 */     return this.id;
/*    */   }
/*    */   
/*    */   public void setId(int id) {
/* 49 */     this.id = id;
/*    */   }
/*    */   
/*    */   @Column(name="name", nullable=false, length=128)
/*    */   public String getName() {
/* 54 */     return this.name;
/*    */   }
/*    */   
/*    */   public void setName(String name) {
/* 58 */     this.name = name;
/*    */   }
/*    */   
/*    */   @Column(name="`sort`", nullable=false)
/*    */   public int getSort() {
/* 63 */     return this.sort;
/*    */   }
/*    */   
/*    */   public void setSort(int sort) {
/* 67 */     this.sort = sort;
/*    */   }
/*    */   
/*    */   @Column(name="status", nullable=false)
/*    */   public int getStatus() {
/* 72 */     return this.status;
/*    */   }
/*    */   
/*    */   public void setStatus(int status) {
/* 76 */     this.status = status;
/*    */   }
/*    */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/content/entity/LotteryType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */