/*     */ package lottery.domains.utils.open;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javautils.date.DateUtil;
/*     */ import javautils.date.Moment;
/*     */ import lottery.domains.content.entity.Lottery;
/*     */ import lottery.domains.content.entity.LotteryOpenTime;
/*     */ import lottery.domains.pool.DataFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.beans.factory.annotation.Qualifier;
/*     */ import org.springframework.stereotype.Component;
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
/*     */ @Component
/*     */ public class LotteryOpenUtil
/*     */ {
/*     */   @Autowired
/*     */   private DataFactory df;
/*     */   @Autowired
/*     */   @Qualifier("highOpenTimeUtil")
/*     */   private OpenTimeUtil highOpenTimeUtil;
/*     */   @Autowired
/*     */   @Qualifier("lowOpenTimeUtil")
/*     */   private OpenTimeUtil lowOpenTimeUtil;
/*     */   
/*     */   public OpenTime getOpentime(int lotteryId, String expect)
/*     */   {
/*  37 */     Lottery lottery = this.df.getLottery(lotteryId);
/*  38 */     if (lottery != null) {
/*  39 */       if (lottery.getExpectTrans() == 1) {
/*  40 */         expect = trans(lotteryId, Integer.parseInt(expect));
/*  41 */         OpenTime bean = this.highOpenTimeUtil.getOpenTime(lotteryId, expect);
/*  42 */         return trans(lotteryId, bean);
/*     */       }
/*  44 */       switch (lottery.getType()) {
/*     */       case 1: 
/*     */       case 2: 
/*     */       case 3: 
/*     */       case 7: 
/*  49 */         if ("bjk3".equals(lottery.getShortName())) {
/*  50 */           expect = trans(lotteryId, Integer.parseInt(expect));
/*  51 */           OpenTime bean = this.highOpenTimeUtil.getOpenTime(lotteryId, expect);
/*  52 */           return trans(lotteryId, bean);
/*     */         }
/*  54 */         return this.highOpenTimeUtil.getOpenTime(lotteryId, expect);
/*     */       case 4: 
/*  56 */         return this.lowOpenTimeUtil.getOpenTime(lotteryId, expect);
/*     */       case 5: 
/*     */       case 6: 
/*  59 */         if ("bjpk10".equals(lottery.getShortName())) {
/*  60 */           expect = trans(lotteryId, Integer.parseInt(expect));
/*  61 */           OpenTime bean = this.highOpenTimeUtil.getOpenTime(lotteryId, expect);
/*  62 */           return trans(lotteryId, bean);
/*     */         }
/*  64 */         return this.highOpenTimeUtil.getOpenTime(lotteryId, expect);
/*     */       }
/*     */       
/*     */     }
/*     */     
/*  69 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public List<OpenTime> getOpenTimeList(int lotteryId, int count)
/*     */   {
/*  78 */     Lottery lottery = this.df.getLottery(lotteryId);
/*  79 */     if (lottery != null) {
/*  80 */       if (lottery.getExpectTrans() == 1) {
/*  81 */         List<OpenTime> list = this.highOpenTimeUtil.getOpenTimeList(lotteryId, count);
/*  82 */         return trans(lotteryId, list);
/*     */       }
/*  84 */       switch (lottery.getType()) {
/*     */       case 1: 
/*     */       case 2: 
/*     */       case 3: 
/*     */       case 7: 
/*  89 */         if ("bjk3".equals(lottery.getShortName())) {
/*  90 */           List<OpenTime> list = this.highOpenTimeUtil.getOpenTimeList(lotteryId, count);
/*  91 */           return trans(lotteryId, list);
/*     */         }
/*  93 */         return this.highOpenTimeUtil.getOpenTimeList(lotteryId, count);
/*     */       case 4: 
/*  95 */         return this.lowOpenTimeUtil.getOpenTimeList(lotteryId, count);
/*     */       case 5: 
/*     */       case 6: 
/*  98 */         if ("bjpk10".equals(lottery.getShortName())) {
/*  99 */           List<OpenTime> list = this.highOpenTimeUtil.getOpenTimeList(lotteryId, count);
/* 100 */           return trans(lotteryId, list);
/*     */         }
/* 102 */         return this.highOpenTimeUtil.getOpenTimeList(lotteryId, count);
/*     */       }
/*     */       
/*     */     }
/*     */     
/* 107 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public OpenTime substractOneExpect(int lotteryId, String expect)
/*     */   {
/* 116 */     Lottery lottery = this.df.getLottery(lotteryId);
/* 117 */     if (lottery != null) { String subExpect;
///*     */       String subExpect;
/* 119 */       if (expect.indexOf("-") <= -1) {
/* 120 */         Integer integer = Integer.valueOf(expect);
/* 121 */         integer = Integer.valueOf(integer.intValue() - 1);
/* 122 */         subExpect = integer.toString();
/*     */       }
/*     */       else {
/* 125 */         int formatCount = 3;
/* 126 */         if ((lotteryId == 118) || (lotteryId == 122) || (lotteryId == 123)) {
/* 127 */           formatCount = 4;
/*     */         }
/*     */         
/* 130 */         String date = expect.substring(0, 8);
/* 131 */         String currExpect = expect.substring(9);
///*     */         String subExpect;
/* 133 */         if ((currExpect.equals("001")) || (currExpect.equals("0001"))) {
/* 134 */           date = new Moment().fromDate(date).subtract(1, "days").format("yyyyMMdd");
/* 135 */           subExpect = String.format("%0" + formatCount + "d", new Object[] { Integer.valueOf(1) });
/*     */         }
/*     */         else {
/* 138 */           Integer integer = Integer.valueOf(currExpect);
/* 139 */           integer = Integer.valueOf(integer.intValue() - 1);
///* 140 */           String subExpect;
if (integer.toString().length() >= formatCount) {
/* 141 */             subExpect = integer.toString();
/*     */           }
/*     */           else {
/* 144 */             subExpect = String.format("%0" + formatCount + "d", new Object[] { integer });
/*     */           }
/*     */         }
/*     */         
/* 148 */         subExpect = date.replaceAll("-", "") + "-" + subExpect;
/*     */       }
/*     */       
/* 151 */       OpenTime tmpBean = getOpentime(lotteryId, subExpect);
/* 152 */       return tmpBean;
/*     */     }
/* 154 */     return null;
/*     */   }
/*     */   
/*     */   public List<OpenTime> getOpenDateList(int lotteryId, String date) {
/* 158 */     Lottery lottery = this.df.getLottery(lotteryId);
/* 159 */     if (lottery != null) {
/* 160 */       if (lottery.getExpectTrans() == 1) {
/* 161 */         List<OpenTime> list = this.highOpenTimeUtil.getOpenDateList(lotteryId, date);
/* 162 */         return trans(lotteryId, list);
/*     */       }
/* 164 */       switch (lottery.getType()) {
/*     */       case 1: 
/*     */       case 2: 
/*     */       case 3: 
/*     */       case 7: 
/* 169 */         if ("bjk3".equals(lottery.getShortName())) {
/* 170 */           List<OpenTime> list = this.highOpenTimeUtil.getOpenDateList(lotteryId, date);
/* 171 */           return trans(lotteryId, list);
/*     */         }
/* 173 */         return this.highOpenTimeUtil.getOpenDateList(lotteryId, date);
/*     */       case 4: 
/* 175 */         return this.lowOpenTimeUtil.getOpenDateList(lotteryId, date);
/*     */       case 5: 
/*     */       case 6: 
/* 178 */         if ("bjpk10".equals(lottery.getShortName())) {
/* 179 */           List<OpenTime> list = this.highOpenTimeUtil.getOpenDateList(lotteryId, date);
/* 180 */           return trans(lotteryId, list);
/*     */         }
/* 182 */         return this.highOpenTimeUtil.getOpenDateList(lotteryId, date);
/*     */       }
/*     */       
/*     */     }
/*     */     
/* 187 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public OpenTime getCurrOpenTime(int lotteryId)
/*     */   {
/* 196 */     Lottery lottery = this.df.getLottery(lotteryId);
/* 197 */     if (lottery != null) {
/* 198 */       String currTime = DateUtil.getCurrentTime();
/* 199 */       if (lottery.getExpectTrans() == 1) {
/* 200 */         OpenTime bean = this.highOpenTimeUtil.getCurrOpenTime(lotteryId, currTime);
/* 201 */         return trans(lotteryId, bean);
/*     */       }
/* 203 */       switch (lottery.getType()) {
/*     */       case 1: 
/*     */       case 2: 
/*     */       case 3: 
/*     */       case 7: 
/* 208 */         if ("bjk3".equals(lottery.getShortName())) {
/* 209 */           OpenTime bean = this.highOpenTimeUtil.getCurrOpenTime(lotteryId, currTime);
/* 210 */           return trans(lotteryId, bean);
/*     */         }
/* 212 */         return this.highOpenTimeUtil.getCurrOpenTime(lotteryId, currTime);
/*     */       case 4: 
/* 214 */         return this.lowOpenTimeUtil.getCurrOpenTime(lotteryId, currTime);
/*     */       case 5: 
/*     */       case 6: 
/* 217 */         if ("bjpk10".equals(lottery.getShortName())) {
/* 218 */           OpenTime bean = this.highOpenTimeUtil.getCurrOpenTime(lotteryId, currTime);
/* 219 */           return trans(lotteryId, bean);
/*     */         }
/* 221 */         return this.highOpenTimeUtil.getCurrOpenTime(lotteryId, currTime);
/*     */       }
/*     */       
/*     */     }
/*     */     
/* 226 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public OpenTime getLastOpenTime(int lotteryId)
/*     */   {
/* 235 */     Lottery lottery = this.df.getLottery(lotteryId);
/* 236 */     if (lottery != null) {
/* 237 */       String currTime = DateUtil.getCurrentTime();
/* 238 */       if (lottery.getExpectTrans() == 1) {
/* 239 */         OpenTime bean = this.highOpenTimeUtil.getLastOpenTime(lotteryId, currTime);
/* 240 */         return trans(lotteryId, bean);
/*     */       }
/* 242 */       switch (lottery.getType()) {
/*     */       case 1: 
/*     */       case 2: 
/*     */       case 3: 
/*     */       case 7: 
/* 247 */         if ("bjk3".equals(lottery.getShortName())) {
/* 248 */           OpenTime bean = this.highOpenTimeUtil.getLastOpenTime(lotteryId, currTime);
/* 249 */           return trans(lotteryId, bean);
/*     */         }
/* 251 */         return this.highOpenTimeUtil.getLastOpenTime(lotteryId, currTime);
/*     */       case 4: 
/* 253 */         return this.lowOpenTimeUtil.getLastOpenTime(lotteryId, currTime);
/*     */       case 5: 
/*     */       case 6: 
/* 256 */         if ("bjpk10".equals(lottery.getShortName())) {
/* 257 */           OpenTime bean = this.highOpenTimeUtil.getLastOpenTime(lotteryId, currTime);
/* 258 */           return trans(lotteryId, bean);
/*     */         }
/* 260 */         return this.highOpenTimeUtil.getLastOpenTime(lotteryId, currTime);
/*     */       }
/*     */       
/*     */     }
/*     */     
/* 265 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public OpenTime trans(int lotteryId, OpenTime bean)
/*     */   {
/* 275 */     Lottery lottery = this.df.getLottery(lotteryId);
/* 276 */     String refLotteryName = lottery.getShortName() + "_ref";
/* 277 */     List<LotteryOpenTime> opList = this.df.listLotteryOpenTime(refLotteryName);
/* 278 */     LotteryOpenTime lotteryOpenTime = (LotteryOpenTime)opList.get(0);
/* 279 */     String refDate = lotteryOpenTime.getOpenTime();
/* 280 */     int refExpect = Integer.parseInt(lotteryOpenTime.getExpect());
/* 281 */     int times = lottery.getTimes();
/* 282 */     return OpenTimeTransUtil.trans(bean, refDate, refExpect, times);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String trans(int lotteryId, int expect)
/*     */   {
/* 292 */     Lottery lottery = this.df.getLottery(lotteryId);
/* 293 */     String refLotteryName = lottery.getShortName() + "_ref";
/* 294 */     List<LotteryOpenTime> opList = this.df.listLotteryOpenTime(refLotteryName);
/* 295 */     LotteryOpenTime lotteryOpenTime = (LotteryOpenTime)opList.get(0);
/* 296 */     String refDate = lotteryOpenTime.getOpenTime();
/* 297 */     int refExpect = Integer.parseInt(lotteryOpenTime.getExpect());
/* 298 */     int times = lottery.getTimes();
/* 299 */     return OpenTimeTransUtil.trans(expect, refDate, refExpect, times);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public List<OpenTime> trans(int lotteryId, List<OpenTime> list)
/*     */   {
/* 309 */     List<OpenTime> nList = new ArrayList();
/* 310 */     for (OpenTime bean : list) {
/* 311 */       nList.add(trans(lotteryId, bean));
/*     */     }
/* 313 */     return nList;
/*     */   }
/*     */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/utils/open/LotteryOpenUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */