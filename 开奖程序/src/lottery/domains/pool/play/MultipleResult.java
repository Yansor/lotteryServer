/*    */ package lottery.domains.pool.play;
/*    */ 
/*    */ 
/*    */ public class MultipleResult
/*    */ {
/*    */   private String code;
/*    */   
/*    */   private int oddsIndex;
/*    */   private int nums;
/*    */   
/*    */   public MultipleResult() {}
/*    */   
/*    */   public MultipleResult(String code, int oddsIndex)
/*    */   {
/* 15 */     this.code = code;
/* 16 */     this.oddsIndex = oddsIndex;
/*    */   }
/*    */   
/*    */   public String getCode() {
/* 20 */     return this.code;
/*    */   }
/*    */   
/*    */   public void setCode(String code) {
/* 24 */     this.code = code;
/*    */   }
/*    */   
/*    */   public int getOddsIndex() {
/* 28 */     return this.oddsIndex;
/*    */   }
/*    */   
/*    */   public void setOddsIndex(int oddsIndex) {
/* 32 */     this.oddsIndex = oddsIndex;
/*    */   }
/*    */   
/*    */   public int getNums() {
/* 36 */     return this.nums;
/*    */   }
/*    */   
/*    */   public void setNums(int nums) {
/* 40 */     this.nums = nums;
/*    */   }
/*    */   
/*    */   public void increseNum() {
/* 44 */     this.nums += 1;
/*    */   }
/*    */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/pool/play/MultipleResult.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */