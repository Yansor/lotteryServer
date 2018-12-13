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
/*    */ @Table(name="db_server_sync", catalog="ecai")
/*    */ public class DbServerSync
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private String key;
/*    */   private String lastModTime;
/*    */   private String desc;
/*    */   
/*    */   public DbServerSync() {}
/*    */   
/*    */   public DbServerSync(String lastModTime, String desc)
/*    */   {
/* 34 */     this.lastModTime = lastModTime;
/* 35 */     this.desc = desc;
/*    */   }
/*    */   
/*    */   @Id
/*    */   @GeneratedValue(strategy=GenerationType.IDENTITY)
/*    */   @Column(name="`key`", unique=true, nullable=false, length=128)
/*    */   public String getKey()
/*    */   {
/* 43 */     return this.key;
/*    */   }
/*    */   
/*    */   public void setKey(String key) {
/* 47 */     this.key = key;
/*    */   }
/*    */   
/*    */   @Column(name="last_mod_time", length=19)
/*    */   public String getLastModTime() {
/* 52 */     return this.lastModTime;
/*    */   }
/*    */   
/*    */   public void setLastModTime(String lastModTime) {
/* 56 */     this.lastModTime = lastModTime;
/*    */   }
/*    */   
/*    */   @Column(name="`desc`")
/*    */   public String getDesc() {
/* 61 */     return this.desc;
/*    */   }
/*    */   
/*    */   public void setDesc(String desc) {
/* 65 */     this.desc = desc;
/*    */   }
/*    */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/content/entity/DbServerSync.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */