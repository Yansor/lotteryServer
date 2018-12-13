/*     */ package javautils.encrypt;
/*     */
/*     */ import java.security.Key;
/*     */ import javax.crypto.Cipher;
/*     */ import javax.crypto.SecretKeyFactory;
/*     */ import javax.crypto.spec.DESKeySpec;
/*     */ import sun.misc.BASE64Decoder;
/*     */ import sun.misc.BASE64Encoder;
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */ public class DESUtil
/*     */ {
    /*     */   public static DESUtil getInstance()
/*     */   {
/*  21 */     return new DESUtil();
/*     */   }
    /*     */
/*     */
/*     */
/*     */   public Key getKey(String strKey)
/*     */   {
/*  28 */     Key key = null;
/*     */     try {
/*  30 */       SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
/*  31 */       DESKeySpec keySpec = new DESKeySpec(strKey.getBytes());
/*  32 */       keyFactory.generateSecret(keySpec);
/*  33 */       key = keyFactory.generateSecret(keySpec);
/*     */     } catch (Exception e) {
/*  35 */       e.printStackTrace();
/*     */     }
/*  37 */     return key;
/*     */   }
    /*     */
/*     */
/*     */
/*     */   public String encryptStr(String strMing, String keyStr)
/*     */   {
/*  44 */     byte[] byteMi = null;
/*  45 */     byte[] byteMing = null;
/*  46 */     String strMi = "";
/*  47 */     BASE64Encoder base64en = new BASE64Encoder();
/*     */     try {
/*  49 */       byteMing = strMing.getBytes("UTF8");
/*  50 */       byteMi = encryptByte(byteMing, getKey(keyStr));
/*  51 */       strMi = base64en.encode(byteMi);
/*     */     } catch (Exception e) {
/*  53 */       e.printStackTrace();
/*     */     } finally {
/*  55 */       base64en = null;
/*  56 */       byteMing = null;
/*  57 */       byteMi = null;
/*     */     }
/*  59 */     return strMi;
/*     */   }
    /*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */   public String decryptStr(String strMi, String keyStr)
/*     */   {
/*  69 */     byte[] byteMing = null;
/*  70 */     byte[] byteMi = null;
/*  71 */     String strMing = "";
/*  72 */     BASE64Decoder base64De = new BASE64Decoder();
/*     */     try {
/*  74 */       byteMi = base64De.decodeBuffer(strMi);
/*  75 */       byteMing = decryptByte(byteMi, getKey(keyStr));
/*  76 */       strMing = new String(byteMing, "UTF8");
/*     */     } catch (Exception e) {
/*  78 */       e.printStackTrace();
/*     */     } finally {
/*  80 */       base64De = null;
/*  81 */       byteMing = null;
/*  82 */       byteMi = null;
/*     */     }
/*  84 */     return strMing;
/*     */   }
    /*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */   private byte[] encryptByte(byte[] byteS, Key key)
/*     */   {
/*  94 */     byte[] byteFina = null;
/*     */     Cipher cipher;
/*     */     try {
/*  97 */       cipher = Cipher.getInstance("DES");
/*  98 */       cipher.init(1, key);
/*  99 */       byteFina = cipher.doFinal(byteS);
/*     */     } catch (Exception e) {
//    Cipher cipher;
/* 101 */       e.printStackTrace();
/*     */     } finally {
//    Cipher cipher;
/* 103 */       cipher = null;
/*     */     }
/* 105 */     return byteFina;
/*     */   }
    /*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */   private byte[] decryptByte(byte[] byteD, Key key)
/*     */   {
/* 115 */     byte[] byteFina = null;
/*     */     Cipher cipher;
/*     */     try {
/* 118 */       cipher = Cipher.getInstance("DES");
/* 119 */       cipher.init(2, key);
/* 120 */       byteFina = cipher.doFinal(byteD);
/*     */     } catch (Exception e) {
//    Cipher cipher;
/* 122 */       e.printStackTrace();
/*     */     } finally {
//    Cipher cipher;
/* 124 */       cipher = null;
/*     */     }
/* 126 */     return byteFina;
/*     */   }
/*     */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryCapture/lotteryCaptureRepair.jar!/javautils/encrypt/DESUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */