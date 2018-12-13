///*    */ package javautils.lzma;
///*    */
///*    */ import SevenZip.Compression.LZMA.Decoder;
///*    */ import java.io.ByteArrayInputStream;
///*    */ import java.io.ByteArrayOutputStream;
///*    */ import java.io.IOException;
///*    */ import java.io.InputStream;
///*    */
///*    */ public class LZMAJsUtil
///*    */ {
///*    */   public static byte[] hex2byte(String s)
///*    */   {
///* 13 */     int len = s.length();
///* 14 */     byte[] data = new byte[len / 2];
///* 15 */     for (int i = 0; i < len; i += 2)
///*    */     {
///* 17 */       data[(i / 2)] = ((byte)((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16)));
///*    */     }
///* 19 */     return data;
///*    */   }
///*    */
///*    */
///*    */
///*    */
///*    */
///*    */
///*    */
///*    */
///*    */
///*    */
///*    */
///*    */
///*    */
///*    */
///*    */
///*    */
///*    */
///*    */
///*    */   public static String decompress(String hexString)
///*    */   {
///*    */     try
///*    */     {
///* 43 */       byte[] data = hex2byte(hexString);
///* 44 */       ByteArrayInputStream input = new ByteArrayInputStream(data);
///* 45 */       ByteArrayOutputStream output = new ByteArrayOutputStream();
///* 46 */       decode(input, output);
///* 47 */       byte[] result = output.toByteArray();
///* 48 */       if ((result != null) && (result.length > 0)) {
///* 49 */         return new String(result);
///*    */       }
///*    */     } catch (Exception e) {
///* 52 */       e.printStackTrace();
///*    */     }
///* 54 */     return null;
///*    */   }
///*    */
///*    */   public static void decode(InputStream input, java.io.OutputStream output) throws IOException
///*    */   {
///* 59 */     byte[] properties = new byte[5];
///* 60 */     for (int i = 0; i < properties.length; i++) {
///* 61 */       int r = input.read();
///* 62 */       if (r == -1)
///* 63 */         throw new IOException("truncated input");
///* 64 */       properties[i] = ((byte)r);
///*    */     }
///* 66 */     Decoder decoder = new Decoder();
///* 67 */     if (!decoder.SetDecoderProperties(properties))
///* 68 */       throw new IOException("corrupted input");
///* 69 */     long expectedLength = -1L;
///* 70 */     for (int i = 0; i < 64; i += 8) {
///* 71 */       int r = input.read();
///* 72 */       if (r == -1)
///* 73 */         throw new IOException("truncated input");
///* 74 */       expectedLength |= r << i;
///*    */     }
///* 76 */     decoder.Code(input, output, expectedLength);
///*    */   }
///*    */
///*    */   public static void main(String[] args) {}
///*    */ }
//
//
///* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/javautils/lzma/LZMAJsUtil.class
// * Java compiler version: 8 (52.0)
// * JD-Core Version:       0.7.1
// */