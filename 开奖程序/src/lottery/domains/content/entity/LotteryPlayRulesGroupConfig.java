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
/*    */ @Table(name="lottery_play_rules_group_config", catalog="ecai", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"group_id", "lottery_id"})})
/*    */ public class LotteryPlayRulesGroupConfig
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private int id;
/*    */   private int groupId;
/*    */   private int lotteryId;
/*    */   private int status;
/*    */   
/*    */   @Id
/*    */   @GeneratedValue(strategy=GenerationType.IDENTITY)
/*    */   @Column(name="id", unique=true, nullable=false)
/*    */   public int getId()
/*    */   {
/* 27 */     return this.id;
/*    */   }
/*    */   
/*    */   public void setId(int id) {
/* 31 */     this.id = id;
/*    */   }
/*    */   
/*    */   @Column(name="group_id", nullable=false)
/*    */   public int getGroupId() {
/* 36 */     return this.groupId;
/*    */   }
/*    */   
/*    */   public void setGroupId(int groupId) {
/* 40 */     this.groupId = groupId;
/*    */   }
/*    */   
/*    */   @Column(name="lottery_id", nullable=false)
/*    */   public int getLotteryId() {
/* 45 */     return this.lotteryId;
/*    */   }
/*    */   
/*    */   public void setLotteryId(int lotteryId) {
/* 49 */     this.lotteryId = lotteryId;
/*    */   }
/*    */   
/*    */   @Column(name="status")
/*    */   public int getStatus() {
/* 54 */     return this.status;
/*    */   }
/*    */   
/*    */   public void setStatus(int status) {
/* 58 */     this.status = status;
/*    */   }
/*    */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/content/entity/LotteryPlayRulesGroupConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */