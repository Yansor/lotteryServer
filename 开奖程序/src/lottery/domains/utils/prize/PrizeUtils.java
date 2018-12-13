/*     */ package lottery.domains.utils.prize;
/*     */ 
/*     */ import java.math.BigDecimal;
/*     */ import javautils.math.MathUtil;
/*     */ import lottery.domains.content.entity.LotteryPlayRules;
/*     */ 
/*     */ public class PrizeUtils
/*     */ {
/*     */   public static double getModel(String model)
/*     */   {
/*  11 */     if ("yuan".equals(model)) return 1.0D;
/*  12 */     if ("1yuan".equals(model)) return 0.5D;
/*  13 */     if ("jiao".equals(model)) return 0.1D;
/*  14 */     if ("1jiao".equals(model)) return 0.05D;
/*  15 */     if ("fen".equals(model)) return 0.01D;
/*  16 */     if ("1fen".equals(model)) return 0.005D;
/*  17 */     if ("li".equals(model)) return 0.001D;
/*  18 */     if ("1li".equals(model)) return 5.0E-4D;
/*  19 */     return 0.0D;
/*     */   }
/*     */   
/*     */   public static double getMoney(LotteryPlayRules rule, String model, double bUnitMoney, int code) {
/*  23 */     double mMoney = getModel(model);
/*  24 */     double maxPrize = 0.0D;
/*  25 */     String[] ps = rule.getPrize().split(",");
/*  26 */     int i = 0; for (int j = ps.length; i < j; i++) {
/*  27 */       if (rule.getFixed() == 0) {
/*  28 */         double pm = code / Double.parseDouble(ps[i]) * (bUnitMoney / 2.0D) * mMoney;
/*  29 */         if (pm > maxPrize) {
/*  30 */           BigDecimal bd = new BigDecimal(pm);
/*  31 */           maxPrize = MathUtil.decimalFormat(bd, 10);
/*     */         }
/*     */       }
/*  34 */       if (rule.getFixed() == 1) {
/*  35 */         double pm = Double.parseDouble(ps[i]) * (bUnitMoney / 2.0D) * mMoney;
/*  36 */         if (pm > maxPrize) {
/*  37 */           BigDecimal bd = new BigDecimal(pm);
/*  38 */           maxPrize = MathUtil.decimalFormat(bd, 10);
/*     */         }
/*     */       }
/*     */     }
/*  42 */     return maxPrize;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static double getBetWinMoney(LotteryPlayRules re, String model, double bUnitMoney, int code)
/*     */   {
/*  58 */     double mMoney = getModel(model);
/*  59 */     double maxPrize = 0.0D;
/*  60 */     if (re.getFixed() == 0) {
/*  61 */       double pm = code / Double.parseDouble(re.getPrize()) * (bUnitMoney / 2.0D) * mMoney;
/*  62 */       if (pm > maxPrize) {
/*  63 */         BigDecimal bd = new BigDecimal(pm);
/*  64 */         maxPrize = MathUtil.decimalFormat(bd, 10);
/*     */       }
/*     */     }
/*  67 */     else if (re.getFixed() == 1) {
/*  68 */       double pm = Double.parseDouble(re.getPrize()) * (bUnitMoney / 2.0D) * mMoney;
/*  69 */       if (pm > maxPrize) {
/*  70 */         BigDecimal bd = new BigDecimal(pm);
/*  71 */         maxPrize = MathUtil.decimalFormat(bd, 10);
/*     */       }
/*     */     }
/*  74 */     return maxPrize;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static double getWinMoneyByCode(int fixed, String model, double bUnitMoney, int code, double prizeres)
/*     */   {
/*  91 */     double mMoney = getModel(model);
/*  92 */     double maxPrize = 0.0D;
/*  93 */     if (fixed == 0) {
/*  94 */       double pm = code / prizeres * (bUnitMoney / 2.0D) * mMoney;
/*  95 */       if (pm > maxPrize) {
/*  96 */         BigDecimal bd = new BigDecimal(pm);
/*  97 */         maxPrize = MathUtil.decimalFormat(bd, 10);
/*     */       }
/*     */     }
/* 100 */     if (fixed == 1) {
/* 101 */       double pm = prizeres * (bUnitMoney / 2.0D) * mMoney;
/* 102 */       if (pm > maxPrize) {
/* 103 */         BigDecimal bd = new BigDecimal(pm);
/* 104 */         maxPrize = MathUtil.decimalFormat(bd, 10);
/*     */       }
/*     */     }
/* 107 */     return maxPrize;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static double getPrize(int fixed, String model, double bUnitMoney, int code, double odds)
/*     */   {
/* 114 */     double mMoney = getModel(model);
/* 115 */     double maxPrize = 0.0D;
/* 116 */     if (fixed == 0) {
/* 117 */       double pm = code / odds * (bUnitMoney / 2.0D) * mMoney;
/* 118 */       BigDecimal bd = new BigDecimal(pm);
/* 119 */       maxPrize = MathUtil.decimalFormat(bd, 10);
/*     */     }
/* 121 */     else if (fixed == 1) {
/* 122 */       double pm = odds * (bUnitMoney / 2.0D) * mMoney;
/* 123 */       BigDecimal bd = new BigDecimal(pm);
/* 124 */       maxPrize = MathUtil.decimalFormat(bd, 10);
/*     */     }
/*     */     
/* 127 */     return maxPrize;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/* 134 */     System.out.println(getPrize(0, "yuan", 2.0D, 1946, 444.4953D));
/*     */   }
/*     */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/utils/prize/PrizeUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */