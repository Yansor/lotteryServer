/*    */ package lottery.domains.pool.play.impl;
/*    */ 
/*    */ import java.io.PrintStream;
/*    */ import lottery.domains.pool.play.ITicketPlayHandler;
/*    */ import lottery.domains.pool.play.WinResult;
/*    */ import lottery.domains.pool.util.TicketPlayUtils;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ZXZHPlasyHandler
/*    */   implements ITicketPlayHandler
/*    */ {
/*    */   private String playId;
/*    */   private int[] offsets;
/*    */   
/*    */   public ZXZHPlasyHandler(String playId, int[] offsets)
/*    */   {
/* 19 */     this.playId = playId;
/* 20 */     this.offsets = offsets;
/*    */   }
/*    */   
/*    */   public String[] getBetNums(String betNums)
/*    */   {
/* 25 */     String[] nums = betNums.trim().split(",");
/* 26 */     String[] res = new String[this.offsets.length];
/* 27 */     int idx = 0;
/* 28 */     for (int i = 0; i < nums.length; i++) {
/* 29 */       if (!"-".equals(nums[i].trim())) {
/* 30 */         res[(idx++)] = nums[i].replaceAll(" ", "").trim();
/*    */       }
/*    */     }
/* 33 */     return res;
/*    */   }
/*    */   
/*    */   public String[] getOpenNums(String openNums)
/*    */   {
/* 38 */     return TicketPlayUtils.getOpenNums(openNums, this.offsets);
/*    */   }
/*    */   
/*    */   public WinResult calculateWinNum(int userBetsId, String index, String openNums) {
/* 42 */     WinResult result = new WinResult();
/* 43 */     String[] openNum = getOpenNums(openNums);
/* 44 */     String[] nums = getBetNums(index);
/* 45 */     if ((nums == null) || (openNum == null) || (nums.length != this.offsets.length) || (openNum.length != this.offsets.length))
/*    */     {
/* 47 */       return result;
/*    */     }
/* 49 */     int winNum = 0;
/* 50 */     for (int i = nums.length - 1; i >= 0; i--) {
/* 51 */       String ssnum = nums[i];
/* 52 */       if (ssnum.indexOf(openNum[i]) < 0) break;
/* 53 */       winNum++;
/*    */     }
/*    */     
/*    */ 
/*    */ 
/* 58 */     result.setPlayId(this.playId);
/* 59 */     result.setWinNum(winNum);
/* 60 */     return result;
/*    */   }
/*    */   
/*    */   public static void main(String[] args)
/*    */   {
/* 65 */     String betNum = "3,3,3,3,-";
/* 66 */     String openCode = "3,3,2,3,3";
/* 67 */     int[] offset = { 0, 1, 2, 3 };
/*    */     
/* 69 */     ZXZHPlasyHandler hd = new ZXZHPlasyHandler("单式", offset);
/* 70 */     WinResult resulthd = hd.calculateWinNum(1, betNum, openCode);
/*    */     
/* 72 */     String[] openCodeArray = hd.getOpenNums(openCode);
/* 73 */     StringBuffer sbf = new StringBuffer();
/* 74 */     for (int i = 0; i < openCodeArray.length; i++) {
/* 75 */       sbf.append(openCodeArray[i]).append(",");
/*    */     }
/* 77 */     System.out.println("开奖号码为：" + sbf.toString().substring(0, sbf.toString().length() - 1));
/*    */     
/* 79 */     if (resulthd == null) {
/* 80 */       System.out.println("错误-----");
/*    */     } else {
/* 82 */       System.out.println("中奖注数：" + resulthd.getWinNum() + ",玩法ID为：" + resulthd.getPlayId() + ",中奖号码:" + resulthd
/* 83 */         .getWinCode());
/*    */     }
/*    */   }
/*    */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/pool/play/impl/ZXZHPlasyHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */