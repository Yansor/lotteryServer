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
/*    */ @Table(name="lottery_play_rules_config", catalog="ecai", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"rule_id", "lottery_id"})})
/*    */ public class LotteryPlayRulesConfig
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private int id;
/*    */   private int ruleId;
/*    */   private int lotteryId;
/*    */   private String minNum;
/*    */   private String maxNum;
/*    */   private int status;
/*    */   private String prize;
/*    */   
/*    */   @Id
/*    */   @GeneratedValue(strategy=GenerationType.IDENTITY)
/*    */   @Column(name="id", unique=true, nullable=false)
/*    */   public int getId()
/*    */   {
/* 30 */     return this.id;
/*    */   }
/*    */   
/*    */   public void setId(int id) {
/* 34 */     this.id = id;
/*    */   }
/*    */   
/*    */   @Column(name="rule_id", nullable=false)
/*    */   public int getRuleId() {
/* 39 */     return this.ruleId;
/*    */   }
/*    */   
/*    */   public void setRuleId(int ruleId) {
/* 43 */     this.ruleId = ruleId;
/*    */   }
/*    */   
/*    */   @Column(name="lottery_id", nullable=false)
/*    */   public int getLotteryId() {
/* 48 */     return this.lotteryId;
/*    */   }
/*    */   
/*    */   public void setLotteryId(int lotteryId) {
/* 52 */     this.lotteryId = lotteryId;
/*    */   }
/*    */   
/*    */   @Column(name="min_num", length=128)
/*    */   public String getMinNum() {
/* 57 */     return this.minNum;
/*    */   }
/*    */   
/*    */   public void setMinNum(String minNum) {
/* 61 */     this.minNum = minNum;
/*    */   }
/*    */   
/*    */   @Column(name="max_num", length=128)
/*    */   public String getMaxNum() {
/* 66 */     return this.maxNum;
/*    */   }
/*    */   
/*    */   public void setMaxNum(String maxNum) {
/* 70 */     this.maxNum = maxNum;
/*    */   }
/*    */   
/*    */   @Column(name="status")
/*    */   public int getStatus() {
/* 75 */     return this.status;
/*    */   }
/*    */   
/*    */   public void setStatus(int status) {
/* 79 */     this.status = status;
/*    */   }
/*    */   
/*    */   @Column(name="prize", nullable=false, length=512)
/*    */   public String getPrize() {
/* 84 */     return this.prize;
/*    */   }
/*    */   
/*    */   public void setPrize(String prize) {
/* 88 */     this.prize = prize;
/*    */   }
/*    */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/content/entity/LotteryPlayRulesConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */