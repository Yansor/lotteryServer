/*    */ package lottery.domains.content.vo.config;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import lottery.domains.content.vo.user.SysCodeRangeVO;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CodeConfig
/*    */ {
/*    */   private int sysCode;
/*    */   private double sysLp;
/*    */   private double sysNlp;
/*    */   private int sysMinCode;
/*    */   private double sysMinLp;
/* 16 */   private List<SysCodeRangeVO> sysCodeRange = new ArrayList();
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getSysCode()
/*    */   {
/* 23 */     return this.sysCode;
/*    */   }
/*    */   
/*    */   public void setSysCode(int sysCode) {
/* 27 */     this.sysCode = sysCode;
/*    */   }
/*    */   
/*    */   public double getSysLp() {
/* 31 */     return this.sysLp;
/*    */   }
/*    */   
/*    */   public void setSysLp(double sysLp) {
/* 35 */     this.sysLp = sysLp;
/*    */   }
/*    */   
/*    */   public double getSysNlp() {
/* 39 */     return this.sysNlp;
/*    */   }
/*    */   
/*    */   public void setSysNlp(double sysNlp) {
/* 43 */     this.sysNlp = sysNlp;
/*    */   }
/*    */   
/*    */   public List<SysCodeRangeVO> getSysCodeRange() {
/* 47 */     return this.sysCodeRange;
/*    */   }
/*    */   
/*    */   public void setSysCodeRange(List<SysCodeRangeVO> sysCodeRange) {
/* 51 */     this.sysCodeRange = sysCodeRange;
/*    */   }
/*    */   
/*    */   public int getSysMinCode() {
/* 55 */     return this.sysMinCode;
/*    */   }
/*    */   
/*    */   public void setSysMinCode(int sysMinCode) {
/* 59 */     this.sysMinCode = sysMinCode;
/*    */   }
/*    */   
/*    */   public double getSysMinLp() {
/* 63 */     return this.sysMinLp;
/*    */   }
/*    */   
/*    */   public void setSysMinLp(double sysMinLp) {
/* 67 */     this.sysMinLp = sysMinLp;
/*    */   }
/*    */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/content/vo/config/CodeConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */