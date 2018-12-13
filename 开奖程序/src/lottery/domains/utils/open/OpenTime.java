/*    */ package lottery.domains.utils.open;
/*    */ 
/*    */ 
/*    */ public class OpenTime
/*    */ {
/*    */   private String expect;
/*    */   
/*    */   private String startTime;
/*    */   private String stopTime;
/*    */   private String openTime;
/*    */   
/*    */   public OpenTime() {}
/*    */   
/*    */   public OpenTime(String expect, String startTime, String stopTime, String openTime)
/*    */   {
/* 16 */     this.expect = expect;
/* 17 */     this.startTime = startTime;
/* 18 */     this.stopTime = stopTime;
/* 19 */     this.openTime = openTime;
/*    */   }
/*    */   
/*    */   public String getExpect() {
/* 23 */     return this.expect;
/*    */   }
/*    */   
/*    */   public void setExpect(String expect) {
/* 27 */     this.expect = expect;
/*    */   }
/*    */   
/*    */   public String getStartTime() {
/* 31 */     return this.startTime;
/*    */   }
/*    */   
/*    */   public void setStartTime(String startTime) {
/* 35 */     this.startTime = startTime;
/*    */   }
/*    */   
/*    */   public String getStopTime() {
/* 39 */     return this.stopTime;
/*    */   }
/*    */   
/*    */   public void setStopTime(String stopTime) {
/* 43 */     this.stopTime = stopTime;
/*    */   }
/*    */   
/*    */   public String getOpenTime() {
/* 47 */     return this.openTime;
/*    */   }
/*    */   
/*    */   public void setOpenTime(String openTime) {
/* 51 */     this.openTime = openTime;
/*    */   }
/*    */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/utils/open/OpenTime.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */