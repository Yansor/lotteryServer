/*    */ package lottery.domains.pool.play.impl;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import java.util.TreeSet;
/*    */ import lottery.domains.pool.play.ITicketPlayHandler;
/*    */ import lottery.domains.pool.play.WinResult;
/*    */ import lottery.domains.pool.util.TicketPlayUtils;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SSCRXZXZ3PlayHanlder
/*    */   implements ITicketPlayHandler
/*    */ {
/*    */   private String playId;
/*    */   
/*    */   public SSCRXZXZ3PlayHanlder(String playId)
/*    */   {
/* 18 */     this.playId = playId;
/*    */   }
/*    */   
/*    */   public String[] getBetNums(String bet)
/*    */   {
/* 23 */     String[] res = new String[2];
/* 24 */     String[] xz = bet.substring(1, 10).split(",");
/* 25 */     StringBuffer placeIndex = new StringBuffer();
/* 26 */     for (int i = 0; i < xz.length; i++) {
/* 27 */       if (xz[i].equals("√")) {
/* 28 */         placeIndex.append(String.valueOf(i)).append(",");
/*    */       }
/*    */     }
/* 31 */     String placeVal = placeIndex.toString().trim();
/* 32 */     if (!placeVal.equals("")) {
/* 33 */       placeVal = placeVal.substring(0, placeVal.length() - 1);
/*    */     }
/*    */     
/* 36 */     String betValue = bet.substring(11, bet.length()).trim();
/* 37 */     if ((placeVal.equals("")) || (betValue.equals(""))) {
/* 38 */       return null;
/*    */     }
/* 40 */     res[0] = placeVal;
/* 41 */     res[1] = betValue;
/* 42 */     return res;
/*    */   }
/*    */   
/*    */   public String[] getOpenNums(String openNums)
/*    */   {
/* 47 */     return TicketPlayUtils.getOpenNums(openNums, OFFSETS_WUXIN);
/*    */   }
/*    */   
/*    */   public WinResult calculateWinNum(int userBetsId, String index, String openNums)
/*    */   {
/* 52 */     WinResult result = new WinResult();
/* 53 */     String[] data = getBetNums(index);
/* 54 */     if ((data == null) || (data.length != 2)) {
/* 55 */       return result;
/*    */     }
/* 57 */     int winNum = 0;
/* 58 */     String[] offsets = data[0].trim().split(",");
/* 59 */     String[] betNum = data[1].trim().split(",");
/* 60 */     Arrays.sort(betNum);
/* 61 */     String[] nums = getOpenNums(openNums);
/*    */     
/* 63 */     for (int i = 0; i < offsets.length; i++) {
/* 64 */       for (int j = i + 1; j < offsets.length; j++) {
/* 65 */         for (int k = j + 1; k < offsets.length; k++) {
/* 66 */           TreeSet<String> set = new TreeSet();
/* 67 */           set.add(nums[Integer.parseInt(offsets[i])]);
/* 68 */           set.add(nums[Integer.parseInt(offsets[j])]);
/* 69 */           set.add(nums[Integer.parseInt(offsets[k])]);
/* 70 */           if ((set.size() == 2) && 
/* 71 */             (Arrays.binarySearch(betNum, set.pollFirst()) >= 0) && 
/* 72 */             (Arrays.binarySearch(betNum, set.pollFirst()) >= 0)) {
/* 73 */             winNum++;
/*    */           }
/*    */         }
/*    */       }
/*    */     }
/*    */     
/* 79 */     result.setPlayId(this.playId);
/* 80 */     result.setWinNum(winNum);
/* 81 */     return result;
/*    */   }
/*    */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/pool/play/impl/SSCRXZXZ3PlayHanlder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */