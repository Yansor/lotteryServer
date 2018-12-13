/*    */ package javautils.encrypt;
/*    */ 
/*    */ import lottery.domains.content.entity.UserBetsOriginal;
/*    */ import org.apache.commons.codec.digest.DigestUtils;
/*    */ import org.apache.commons.lang.RandomStringUtils;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class UserBetsEncrypt
/*    */ {
/* 11 */   private static final DESUtil DES = new DESUtil();
/*    */   private static final String DES_KEY = "#$ddw4FFWfg#GR0(DSFW@#?!@#!@#$C$$3GhyUhb";
/*    */   
/*    */   public static String encryptCertification(String certification) {
/* 15 */     return DES.encryptStr(certification, "#$ddw4FFWfg#GR0(DSFW@#?!@#!@#$C$$3GhyUhb");
/*    */   }
/*    */   
/*    */   public static String getRandomCertification() {
/* 19 */     return RandomStringUtils.random(10, true, true);
/*    */   }
/*    */   
/*    */   public static String getIdentification(UserBetsOriginal original, String certification) {
/* 23 */     StringBuffer sb = new StringBuffer();
/*    */     
/* 25 */     int point = Double.valueOf(original.getPoint()).intValue();
/* 26 */     int money = Double.valueOf(original.getMoney()).intValue();
/*    */     
/* 28 */     sb.append(original.getId())
/* 29 */       .append(original.getBillno())
/* 30 */       .append(original.getUserId())
/* 31 */       .append(original.getType())
/* 32 */       .append(original.getLotteryId())
/* 33 */       .append(original.getExpect())
/* 34 */       .append(original.getRuleId())
/* 35 */       .append(DigestUtils.md5Hex(original.getCodes()))
/* 36 */       .append(original.getNums())
/* 37 */       .append(original.getModel())
/* 38 */       .append(original.getMultiple())
/* 39 */       .append(original.getCode())
/* 40 */       .append(point)
/* 41 */       .append(money)
/* 42 */       .append(original.getTime())
/* 43 */       .append(original.getStopTime())
/* 44 */       .append(original.getOpenTime())
/* 45 */       .append(original.getStatus())
/* 46 */       .append(original.getOpenCode())
/* 47 */       .append(original.getPrizeMoney())
/* 48 */       .append(original.getPrizeTime())
/* 49 */       .append(original.getChaseBillno())
/* 50 */       .append(original.getChaseBillno())
/* 51 */       .append(original.getPlanBillno())
/* 52 */       .append(original.getRewardMoney())
/* 53 */       .append(certification);
/*    */     
/* 55 */     String thisData = sb.toString();
/* 56 */     return DigestUtils.md5Hex(thisData + certification);
/*    */   }
/*    */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/javautils/encrypt/UserBetsEncrypt.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */