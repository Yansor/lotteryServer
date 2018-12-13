/*    */ package lottery.domains.pool.play;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract interface ITicketPlayHandler
/*    */ {
/* 36 */   public static final String[] OFFSETS = { "万", "千", "百", "十", "个" };
/*    */   
/*    */ 
/* 39 */   public static final int[] OFFSETS_WUXIN = { 0, 1, 2, 3, 4 };
/*    */   
/* 41 */   public static final int[] OFFSETS_HOUSI = { 1, 2, 3, 4 };
/*    */   
/* 43 */   public static final int[] OFFSETS_QIANSAN = { 0, 1, 2 };
/*    */   
/* 45 */   public static final int[] OFFSETS_ZHONGSAN = { 1, 2, 3 };
/*    */   
/* 47 */   public static final int[] OFFSETS_HOUSAN = { 2, 3, 4 };
/*    */   
/* 49 */   public static final int[] OFFSETS_QIANER = { 0, 1 };
/*    */   
/* 51 */   public static final int[] OFFSETS_ERSAN = { 1, 2 };
/*    */   
/* 53 */   public static final int[] OFFSETS_HOUER = { 3, 4 };
/*    */   
/*    */ 
/* 56 */   public static final int[] OFFSETS_QIANYI = { 0 };
/*    */   
/* 58 */   public static final int[] OFFSETS_QIANSI = { 0, 1, 2, 3 };
/*    */   
/* 60 */   public static final int[] OFFSETS_QIANWU = { 0, 1, 2, 3, 4 };
/*    */   
/* 62 */   public static final int[] OFFSETS_HOUWU = { 5, 6, 7, 8, 9 };
/*    */   
/* 64 */   public static final int[] OFFSETS_PK10DW = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
/*    */   
/* 66 */   public static final int[] ERXING_ZHHZ = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1 };
/* 67 */   public static final int[] ERXING_ZXHZ = { 1, 1, 2, 2, 3, 3, 4, 4, 5, 4, 4, 3, 3, 2, 2, 1, 1 };
/* 68 */   public static final int[] ERXING_MULTI = { 1, 3, 6, 10 };
/*    */   
/* 70 */   public static final int[] SANXING_ZHHZ = { 1, 3, 6, 10, 15, 21, 28, 36, 45, 55, 63, 69, 73, 75, 75, 73, 69, 63, 55, 45, 36, 28, 21, 15, 10, 6, 3, 1 };
/*    */   
/* 72 */   public static final int[] SANXING_ZXHZ = { 1, 2, 2, 4, 5, 6, 8, 10, 11, 13, 14, 14, 15, 15, 14, 14, 13, 11, 10, 8, 6, 5, 4, 2, 2, 1 };
/*    */   
/* 74 */   public static final int[] SANXING_MULTI = { 1, 4, 10 };
/* 75 */   public static final int[] SIXING_MULTI = { 1, 5 };
/*    */   
/* 77 */   public static final int[] OFFSETS_QIANERSHI = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19 };
/*    */   
/* 79 */   public static final String[] RX_PLAY_IDS = { "cqssc_wxzhixfs" };
/*    */   
/*    */ 
/* 82 */   public static final int[] OFFSET_11X5ZHONG = { 2 };
/*    */   
/*    */ 
/* 85 */   public static final String[] NUMS_SSC = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
/*    */   
/* 87 */   public static final String[] NUMS_115 = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11" };
/*    */   
/* 89 */   public static final String[] NUMS_FC3D = NUMS_SSC;
/*    */   
/* 91 */   public static final String[] NUMS_P5P3 = NUMS_SSC;
/*    */   
/* 93 */   public static final String[] NUMS_PK10 = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10" };
/*    */   
/*    */   public abstract String[] getBetNums(String paramString);
/*    */   
/*    */   public abstract String[] getOpenNums(String paramString);
/*    */   
/*    */   public abstract WinResult calculateWinNum(int paramInt, String paramString1, String paramString2);
/*    */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/pool/play/ITicketPlayHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */