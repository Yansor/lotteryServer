/*    */ package lottery.domains.pool.play;
/*    */ 
/*    */ import java.util.List;
/*    */ 
/*    */ public class WinResult
/*    */ {
/*    */   private String playId;
/*    */   private int winNum;
/*  9 */   private int groupType = 0;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/* 14 */   private String winCode = "0#";
/*    */   
/*    */   private int prizeIndex;
/*    */   
/*    */   private List<MultipleResult> multipleResults;
/*    */   
/*    */ 
/*    */   public int getGroupType()
/*    */   {
/* 23 */     return this.groupType;
/*    */   }
/*    */   
/*    */   public void setGroupType(int groupType) {
/* 27 */     this.groupType = groupType;
/*    */   }
/*    */   
/*    */   public String getPlayId() {
/* 31 */     return this.playId;
/*    */   }
/*    */   
/*    */   public void setPlayId(String playId) {
/* 35 */     this.playId = playId;
/*    */   }
/*    */   
/*    */   public int getWinNum() {
/* 39 */     return this.winNum;
/*    */   }
/*    */   
/*    */   public void setWinNum(int winNum) {
/* 43 */     this.winNum = winNum;
/*    */   }
/*    */   
/*    */   public String getWinCode() {
/* 47 */     return this.winCode;
/*    */   }
/*    */   
/*    */   public void setWinCode(String winCode) {
/* 51 */     this.winCode = winCode;
/*    */   }
/*    */   
/*    */   public int getPrizeIndex() {
/* 55 */     return this.prizeIndex;
/*    */   }
/*    */   
/*    */   public void setPrizeIndex(int prizeIndex) {
/* 59 */     this.prizeIndex = prizeIndex;
/*    */   }
/*    */   
/*    */   public List<MultipleResult> getMultipleResults() {
/* 63 */     return this.multipleResults;
/*    */   }
/*    */   
/*    */   public void setMultipleResults(List<MultipleResult> multipleResults) {
/* 67 */     this.multipleResults = multipleResults;
/*    */   }
/*    */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/pool/play/WinResult.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */