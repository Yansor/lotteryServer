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
/*    */ public abstract interface HConstants
/*    */ {
/*    */   public static abstract interface playPrize
/*    */   {
/* 59 */     public static final String[] dds11x5 = { "5单0双", "4单1双", "3单2双", "2单3双", "1单4双", "0单5双" };
/* 60 */     public static final String[] sscniuniu = { "牛大", "牛小", "牛单", "牛双", "无牛", "牛牛", "牛1", "牛2", "牛3", "牛4", "牛5", "牛6", "牛7", "牛8", "牛9", "五条", "炸弹", "葫芦", "顺子", "三条", "两对", "单对", "散号" };
/*    */     
/* 62 */     public static final String[] czw11x5 = { "03,09", "04,08", "05,07", "06" };
/*    */     
/* 64 */     public static final String[] k3hezhi = { "3,18", "4,17", "5,16", "6,15", "7,14", "8,13", "9,12", "10,11" };
/*    */     
/* 66 */     public static final String[] kl8hezhidx = { "大", "和", "小" };
/* 67 */     public static final String[] kl8jopan = { "奇", "和", "偶" };
/* 68 */     public static final String[] kl8sxpan = { "上", "中", "下" };
/* 69 */     public static final String[] kl8wx = { "金", "木", "水", "火", "土" };
/*    */   }
/*    */   
/*    */   public static abstract interface TicketSeriesType
/*    */   {
/*    */     public static final String SSC_DA = "大";
/*    */     public static final String SSC_ZH_DA = "总和大";
/*    */     public static final String SSC_XIAO = "小";
/*    */     public static final String SSC_ZH_XIAO = "总和小";
/*    */     public static final String SSC_DAN = "单";
/*    */     public static final String SSC_ZH_DAN = "总和单";
/*    */     public static final String SSC_SHUANG = "双";
/*    */     public static final String SSC_ZH_SHUANG = "总和双";
/*    */     public static final String KL8_HZDAN = "单";
/*    */     public static final String KL8_HZSHUANG = "双";
/*    */     public static final int HZX_SRART = 210;
/*    */     public static final int HZX_END = 809;
/*    */     public static final int HZH = 810;
/*    */     public static final int HZD_SRART = 811;
/*    */     public static final int HZD_END = 1410;
/*    */     public static final String KL8_HZXIAO = "小";
/*    */     public static final String KL8_HZHE = "和";
/*    */     public static final String KL8_HZDA = "大";
/*    */     public static final String KL8_HZJI = "奇";
/*    */     public static final String KL8_HZOU = "偶";
/*    */     public static final String KL8_HZSHANG = "上";
/*    */     public static final String KL8_HZZHONG = "中";
/*    */     public static final String KL8_HZXIA = "下";
/*    */     public static final int KL8_WX_JIN_START = 210;
/*    */     public static final int KL8_WX_JIN_END = 695;
/*    */     public static final int KL8_WX_MU_START = 696;
/*    */     public static final int KL8_WX_MU_END = 763;
/*    */     public static final int KL8_WX_SHUI_START = 764;
/*    */     public static final int KL8_WX_SHUI_END = 855;
/*    */     public static final int KL8_WX_HUO_START = 856;
/*    */     public static final int KL8_WX_HUO_END = 923;
/*    */     public static final int KL8_WX_TU_START = 924;
/*    */     public static final int KL8_WX_TU_END = 1410;
/*    */     public static final String KL8_JIN = "金";
/*    */     public static final String KL8_MU = "木";
/*    */     public static final String KL8_SHUI = "水";
/*    */     public static final String KL8_HUO = "火";
/*    */     public static final String KL8_TU = "土";
/*    */   }
/*    */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/pool/play/HConstants.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */