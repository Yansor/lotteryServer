/*     */ package lottery.domains.pool.play.impl;
/*     */ 
/*     */ import lottery.domains.pool.play.ITicketPlayHandler;
/*     */ import lottery.domains.pool.play.WinResult;
/*     */ import lottery.domains.pool.util.TicketPlayUtils;
/*     */ import org.apache.commons.lang.ArrayUtils;
/*     */ 
/*     */ 
/*     */ public class QWKL8PlayHandler
/*     */   implements ITicketPlayHandler
/*     */ {
/*     */   private String playId;
/*     */   
/*     */   public QWKL8PlayHandler(String playId)
/*     */   {
/*  16 */     this.playId = playId;
/*     */   }
/*     */   
/*     */   public String[] getBetNums(String betNums) {
/*  20 */     return betNums.split("\\|");
/*     */   }
/*     */   
/*     */   public String[] getOpenNums(String openNums)
/*     */   {
/*  25 */     return openNums.split(",");
/*     */   }
/*     */   
/*     */   public WinResult calculateWinNum(int userBetsId, String index, String openNums)
/*     */   {
/*  30 */     WinResult result = new WinResult();
/*  31 */     String[] betNum = getBetNums(index);
/*  32 */     String[] openNum = getOpenNums(openNums);
/*  33 */     int sum = TicketPlayUtils.getOpenNumSumByStr(openNums, openNum);
/*  34 */     int winNum = 0;
/*  35 */     switch (this.playId) {
/*     */     case "bjkl8_hezhids": 
/*  37 */       String reds = processDS(sum);
/*  38 */       if (ArrayUtils.contains(betNum, reds)) {
/*  39 */         winNum = 1;
/*  40 */         result.setWinCode(reds);
/*     */       }
/*     */       break;
/*     */     case "bjkl8_hezhidx": 
/*  44 */       String rexhd = processDHX(sum);
/*  45 */       if ((!"".equals(rexhd)) && 
/*  46 */         (ArrayUtils.contains(betNum, rexhd))) {
/*  47 */         winNum = 1;
/*  48 */         result.setWinCode(rexhd);
/*     */       }
/*     */       
/*     */       break;
/*     */     case "bjkl8_jopan": 
/*  53 */       String rejoh = processJOH(openNum);
/*  54 */       if (ArrayUtils.contains(betNum, rejoh)) {
/*  55 */         winNum = 1;
/*  56 */         result.setWinCode(rejoh);
/*     */       }
/*     */       break;
/*     */     case "bjkl8_sxpan": 
/*  60 */       String resx = processSX(openNum);
/*  61 */       if (ArrayUtils.contains(betNum, resx)) {
/*  62 */         winNum = 1;
/*  63 */         result.setWinCode(resx);
/*     */       }
/*     */       break;
/*     */     case "bjkl8_hzdxds": 
/*  67 */       String rexhdDX = processDHX(sum);
/*  68 */       String redxdsDS = processDS(sum);
/*  69 */       StringBuffer dxds = new StringBuffer();
/*  70 */       dxds.append(rexhdDX).append(redxdsDS);
/*  71 */       if (ArrayUtils.contains(betNum, dxds.toString())) {
/*  72 */         winNum = 1;
/*  73 */         result.setWinCode(dxds.toString());
/*     */       }
/*     */       break;
/*     */     case "bjkl8_hezhiwx": 
/*  77 */       String resWuXing = processWUXING(sum);
/*  78 */       if ((!"".equals(resWuXing)) && 
/*  79 */         (ArrayUtils.contains(betNum, resWuXing))) {
/*  80 */         winNum = 1;
/*  81 */         result.setWinCode(resWuXing);
/*     */       }
/*     */       
/*     */       break;
/*     */     }
/*     */     
/*     */     
/*  88 */     result.setPlayId(this.playId);
/*  89 */     result.setWinNum(winNum);
/*  90 */     return result;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String processDS(int sum)
/*     */   {
/*  99 */     if (sum % 2 == 0) {
/* 100 */       return "双";
/*     */     }
/* 102 */     return "单";
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String processDHX(int sum)
/*     */   {
/* 112 */     if ((sum >= 210) && (sum <= 809))
/*     */     {
/* 114 */       return "小";
/*     */     }
/* 116 */     if (sum == 810) {
/* 117 */       return "和";
/*     */     }
/* 119 */     if ((sum >= 811) && (sum <= 1410))
/*     */     {
/* 121 */       return "大";
/*     */     }
/* 123 */     return "";
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String processJOH(String[] openNum)
/*     */   {
/* 130 */     int odd = 0;
/* 131 */     int even = 0;
/* 132 */     for (int i = 0; i < openNum.length; i++) {
/* 133 */       if (Integer.parseInt(openNum[i]) % 2 == 0) {
/* 134 */         even++;
/*     */       } else {
/* 136 */         odd++;
/*     */       }
/*     */     }
/* 139 */     if (odd > even)
/* 140 */       return "奇";
/* 141 */     if (even > odd) {
/* 142 */       return "偶";
/*     */     }
/* 144 */     return "和";
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String processSX(String[] openNum)
/*     */   {
/* 152 */     int up = 0;
/* 153 */     int down = 0;
/* 154 */     for (int i = 0; i < openNum.length; i++) {
/* 155 */       if (Integer.parseInt(openNum[i]) >= 40) {
/* 156 */         down++;
/*     */       } else {
/* 158 */         up++;
/*     */       }
/*     */     }
/* 161 */     if (up > down)
/* 162 */       return "上";
/* 163 */     if (down > up) {
/* 164 */       return "下";
/*     */     }
/* 166 */     return "中";
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String processWUXING(int sum)
/*     */   {
/* 176 */     if ((sum >= 210) && (sum <= 695))
/*     */     {
/* 178 */       return "金";
/*     */     }
/* 180 */     if ((sum >= 696) && (sum <= 763))
/*     */     {
/* 182 */       return "木";
/*     */     }
/* 184 */     if ((sum >= 764) && (sum <= 855))
/*     */     {
/* 186 */       return "水";
/*     */     }
/* 188 */     if ((sum >= 856) && (sum <= 923))
/*     */     {
/* 190 */       return "火";
/*     */     }
/* 192 */     if ((sum >= 924) && (sum <= 1410))
/*     */     {
/* 194 */       return "土";
/*     */     }
/* 196 */     return "";
/*     */   }
/*     */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/pool/play/impl/QWKL8PlayHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */