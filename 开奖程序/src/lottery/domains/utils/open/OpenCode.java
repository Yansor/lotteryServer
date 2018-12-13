/*    */ package lottery.domains.utils.open;
/*    */ 
/*    */ import lottery.domains.content.entity.LotteryOpenCode;
/*    */ 
/*    */ public class OpenCode
/*    */ {
/*    */   private String code;
/*    */   private String expect;
/*    */   
/*    */   public OpenCode(LotteryOpenCode bean) {
/* 11 */     this.code = bean.getCode();
/* 12 */     this.expect = bean.getExpect();
/*    */   }
/*    */   
/*    */   public String getCode() {
/* 16 */     return this.code;
/*    */   }
/*    */   
/*    */   public void setCode(String code) {
/* 20 */     this.code = code;
/*    */   }
/*    */   
/*    */   public String getExpect() {
/* 24 */     return this.expect;
/*    */   }
/*    */   
/*    */   public void setExpect(String expect) {
/* 28 */     this.expect = expect;
/*    */   }
/*    */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/utils/open/OpenCode.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */