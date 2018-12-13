/*    */ package lottery.domains.content.vo.config;
/*    */ 
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PlanConfig
/*    */ {
/*    */   private double minMoney;
/*    */   private List<String> title;
/*    */   private List<Integer> rate;
/*    */   private List<Integer> level;
/*    */   
/*    */   public double getMinMoney()
/*    */   {
/* 17 */     return this.minMoney;
/*    */   }
/*    */   
/*    */   public void setMinMoney(double minMoney) {
/* 21 */     this.minMoney = minMoney;
/*    */   }
/*    */   
/*    */   public List<String> getTitle() {
/* 25 */     return this.title;
/*    */   }
/*    */   
/*    */   public void setTitle(List<String> title) {
/* 29 */     this.title = title;
/*    */   }
/*    */   
/*    */   public List<Integer> getRate() {
/* 33 */     return this.rate;
/*    */   }
/*    */   
/*    */   public void setRate(List<Integer> rate) {
/* 37 */     this.rate = rate;
/*    */   }
/*    */   
/*    */   public List<Integer> getLevel() {
/* 41 */     return this.level;
/*    */   }
/*    */   
/*    */   public void setLevel(List<Integer> level) {
/* 45 */     this.level = level;
/*    */   }
/*    */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/content/vo/config/PlanConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */