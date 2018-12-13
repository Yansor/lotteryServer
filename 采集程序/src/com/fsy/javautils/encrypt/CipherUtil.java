/*    */ package com.fsy.javautils.encrypt;
/*    */ 
/*    */ import java.security.MessageDigest;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CipherUtil
/*    */ {
/* 11 */   private static final String[] hexDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
/*    */   
/*    */ 
/*    */   public static String generatePassword(String inputString)
/*    */   {
/* 16 */     return encodeByMD5(inputString);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static boolean validatePassword(String password, String inputString)
/*    */   {
/* 29 */     if (password.equals(encodeByMD5(inputString))) {
/* 30 */       return true;
/*    */     }
/* 32 */     return false;
/*    */   }
/*    */   
/*    */ 
/*    */   private static String encodeByMD5(String originString)
/*    */   {
/* 38 */     if (originString != null) {
/*    */       try
/*    */       {
/* 41 */         MessageDigest md = MessageDigest.getInstance("MD5");
/*    */         
/* 43 */         byte[] results = md.digest(originString.getBytes());
/*    */         
/* 45 */         String resultString = byteArrayToHexString(results);
/* 46 */         return resultString.toUpperCase();
/*    */       } catch (Exception ex) {
/* 48 */         ex.printStackTrace();
/*    */       }
/*    */     }
/* 51 */     return null;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   private static String byteArrayToHexString(byte[] b)
/*    */   {
/* 61 */     StringBuffer resultSb = new StringBuffer();
/* 62 */     for (int i = 0; i < b.length; i++) {
/* 63 */       resultSb.append(byteToHexString(b[i]));
/*    */     }
/* 65 */     return resultSb.toString();
/*    */   }
/*    */   
/*    */   private static String byteToHexString(byte b)
/*    */   {
/* 70 */     int n = b;
/* 71 */     if (n < 0)
/* 72 */       n = 256 + n;
/* 73 */     int d1 = n / 16;
/* 74 */     int d2 = n % 16;
/* 75 */     return hexDigits[d1] + hexDigits[d2];
/*    */   }
/*    */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryCapture/lotteryCaptureRepair.jar!/javautils/encrypt/CipherUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */