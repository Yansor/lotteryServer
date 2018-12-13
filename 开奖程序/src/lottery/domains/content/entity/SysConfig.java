/*    */ package lottery.domains.content.entity;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import javax.persistence.Column;
/*    */ import javax.persistence.Entity;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ @Entity
/*    */ @Table(name="sys_config", catalog="ecai")
/*    */ public class SysConfig
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private String group;
/*    */   private String key;
/*    */   private String value;
/*    */   private String description;
/*    */   
/*    */   public SysConfig() {}
/*    */   
/*    */   public SysConfig(String value)
/*    */   {
/* 36 */     this.value = value;
/*    */   }
/*    */   
/*    */   public SysConfig(String value, String description)
/*    */   {
/* 41 */     this.value = value;
/* 42 */     this.description = description;
/*    */   }
/*    */   
/*    */   @Id
/*    */   @Column(name="`group`", nullable=false, length=64)
/*    */   public String getGroup()
/*    */   {
/* 49 */     return this.group;
/*    */   }
/*    */   
/*    */   public void setGroup(String group) {
/* 53 */     this.group = group;
/*    */   }
/*    */   
/*    */   @Id
/*    */   @Column(name="`key`", nullable=false, length=64)
/*    */   public String getKey() {
/* 59 */     return this.key;
/*    */   }
/*    */   
/*    */   public void setKey(String key) {
/* 63 */     this.key = key;
/*    */   }
/*    */   
/*    */   @Column(name="`value`", nullable=false)
/*    */   public String getValue() {
/* 68 */     return this.value;
/*    */   }
/*    */   
/*    */   public void setValue(String value) {
/* 72 */     this.value = value;
/*    */   }
/*    */   
/*    */   @Column(name="description")
/*    */   public String getDescription() {
/* 77 */     return this.description;
/*    */   }
/*    */   
/*    */   public void setDescription(String description) {
/* 81 */     this.description = description;
/*    */   }
/*    */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/content/entity/SysConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */