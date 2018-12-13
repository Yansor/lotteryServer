/*    */ package javautils.spring;
/*    */ 
/*    */ import javautils.StringUtil;
/*    */ import javautils.encrypt.DESUtil;
/*    */ import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
/*    */ 
/*    */ 
/*    */ public class EncryptPropertyPlaceholderConfigurer
/*    */   extends PropertyPlaceholderConfigurer
/*    */ {
/*    */   private static final String KEY = "#$ddw134R$#G#DSFW@#?!@#!@#$CCCREW1";
/* 12 */   private static final DESUtil DES_UTIL = new DESUtil() ;
/*    */   
/*    */   protected String convertProperty(String propertyName, String propertyValue)
/*    */   {
/* 16 */     if ((StringUtil.isNotNull(propertyValue)) && (propertyValue.endsWith("|e"))) {
/* 17 */       String tempValue = propertyValue.substring(0, propertyValue.length() - 2);
/* 18 */       String decryptValue = DES_UTIL.decryptStr(tempValue, "#$ddw134R$#G#DSFW@#?!@#!@#$CCCREW1");
/* 19 */       return decryptValue;
/*    */     }
/* 21 */     return propertyValue;
/*    */   }
/*    */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/javautils/spring/EncryptPropertyPlaceholderConfigurer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */