/*     */ package lottery.domains.utils.open;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javautils.date.DateUtil;
/*     */ import javautils.date.Moment;
/*     */ import lottery.domains.content.entity.Lottery;
/*     */ import lottery.domains.content.entity.LotteryOpenTime;
/*     */ import lottery.domains.pool.DataFactory;
/*     */ import org.apache.commons.collections.CollectionUtils;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Component;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Component
/*     */ public class HighOpenTimeUtil
/*     */   implements OpenTimeUtil
/*     */ {
/*     */   @Autowired
/*     */   private DataFactory df;
/*     */   
/*     */   public OpenTime getCurrOpenTime(int lotteryId, String currTime)
/*     */   {
/*  27 */     Lottery lottery = this.df.getLottery(lotteryId);
/*  28 */     if (lottery == null) {
/*  29 */       return null;
/*     */     }
/*     */     
/*  32 */     if ("tw5fc".equals(lottery.getShortName())) {
/*  33 */       return getCurrOpenTimeForNext(lotteryId, currTime);
/*     */     }
/*     */     
/*  36 */     if (lottery != null) {
/*  37 */       List<LotteryOpenTime> list = this.df.listLotteryOpenTime(lottery.getShortName());
/*     */       
/*  39 */       if (CollectionUtils.isEmpty(list)) {
/*  40 */         return null;
/*     */       }
/*  42 */       String currDate = currTime.substring(0, 10);
/*  43 */       String nextDate = DateUtil.calcNextDay(currDate);
/*  44 */       String lastDate = DateUtil.calcLastDay(currDate);
/*  45 */       int i = 0; for (int j = list.size(); i < j; i++) {
/*  46 */         LotteryOpenTime tmpBean = (LotteryOpenTime)list.get(i);
/*  47 */         String startDate = currDate;
/*  48 */         String stopDate = currDate;
/*  49 */         String openDate = currDate;
/*  50 */         String expectDate = currDate;
/*  51 */         String startTime = tmpBean.getStartTime();
/*  52 */         String stopTime = tmpBean.getStopTime();
/*  53 */         String openTime = tmpBean.getOpenTime();
/*  54 */         String expect = tmpBean.getExpect();
/*  55 */         if (i == 0) {
/*  56 */           if (startTime.compareTo(stopTime) > 0) {
/*  57 */             startDate = lastDate;
/*     */           }
/*  59 */         } else if (i == j - 1) {
/*  60 */           if (startTime.compareTo(stopTime) > 0) {
/*  61 */             stopDate = nextDate;
/*     */           }
/*  63 */           if (startTime.compareTo(openTime) > 0) {
/*  64 */             openDate = nextDate;
/*     */           }
/*  66 */           if (currTime.compareTo(stopDate + " " + stopTime) >= 0) {
/*  67 */             tmpBean = (LotteryOpenTime)list.get(0);
/*  68 */             startDate = nextDate;
/*  69 */             stopDate = nextDate;
/*  70 */             openDate = nextDate;
/*  71 */             expectDate = nextDate;
/*  72 */             startTime = tmpBean.getStartTime();
/*  73 */             stopTime = tmpBean.getStopTime();
/*  74 */             openTime = tmpBean.getOpenTime();
/*  75 */             expect = tmpBean.getExpect();
/*  76 */             if (startTime.compareTo(stopTime) > 0) {
/*  77 */               startDate = currDate;
/*     */             }
/*     */           }
/*     */         } else {
/*  81 */           if (startTime.compareTo(stopTime) > 0) {
/*  82 */             stopDate = nextDate;
/*     */           }
/*  84 */           if (startTime.compareTo(openTime) > 0) {
/*  85 */             openDate = nextDate;
/*     */           }
/*     */         }
/*  88 */         if (!tmpBean.getIsTodayExpect().booleanValue()) {
/*  89 */           if (startTime.compareTo(stopTime) > 0) {
/*  90 */             if ((currTime.substring(11).compareTo(startTime) < 0) || (currTime.substring(11).compareTo("24:00:00") >= 0))
/*     */             {
/*     */ 
/*  93 */               startDate = lastDate;
/*  94 */               stopDate = currDate;
/*  95 */               expectDate = lastDate;
/*     */             }
/*     */           } else {
/*  98 */             expectDate = lastDate;
/*     */           }
/*     */         }
/* 101 */         startTime = startDate + " " + startTime;
/* 102 */         stopTime = stopDate + " " + stopTime;
/* 103 */         openTime = openDate + " " + openTime;
/* 104 */         expect = expectDate.replace("-", "") + "-" + expect;
/*     */         
/* 106 */         if ((currTime.compareTo(startTime) >= 0) && (currTime.compareTo(stopTime) < 0)) {
/* 107 */           OpenTime bean = new OpenTime();
/* 108 */           bean.setExpect(expect);
/* 109 */           bean.setStartTime(startTime);
/* 110 */           bean.setStopTime(stopTime);
/* 111 */           bean.setOpenTime(openTime);
/* 112 */           if ("mlaft".equals(lottery.getShortName())) {
/* 113 */             return getMlaftOpenTime(bean);
/*     */           }
/* 115 */           return bean;
/*     */         }
/*     */       }
/*     */     }
/* 119 */     return null;
/*     */   }
/*     */   
/*     */   private OpenTime getCurrOpenTimeForNext(int lotteryId, String currTime) {
/* 123 */     Lottery lottery = this.df.getLottery(lotteryId);
/* 124 */     if (lottery == null) {
/* 125 */       return null;
/*     */     }
/*     */     
/*     */ 
/* 129 */     List<LotteryOpenTime> list = this.df.listLotteryOpenTime(lottery.getShortName());
/* 130 */     String currDate = currTime.substring(0, 10);
/* 131 */     String currTimeHMS = currTime.substring(11);
/* 132 */     String nextDate = DateUtil.calcNextDay(currDate);
/* 133 */     String lastDate = DateUtil.calcLastDay(currDate);
/*     */     
/*     */ 
/* 136 */     String startTime = null;
/* 137 */     String stopTime = null;
/* 138 */     String openTime = null;
/* 139 */     String expect = null;
/* 140 */     boolean found = false;
/* 141 */     for (LotteryOpenTime lotteryOpenTime : list) {
/* 142 */       startTime = lotteryOpenTime.getStartTime();
/* 143 */       stopTime = lotteryOpenTime.getStopTime();
/* 144 */       openTime = lotteryOpenTime.getOpenTime();
/* 145 */       expect = lotteryOpenTime.getExpect();
/*     */       
/*     */ 
/* 148 */       if (lotteryOpenTime.getStartTime().compareTo(lotteryOpenTime.getStopTime()) > 0)
/*     */       {
/* 150 */         if (currTimeHMS.compareTo(lotteryOpenTime.getStopTime()) < 0) {
/* 151 */           startTime = lastDate + " " + startTime;
/* 152 */           stopTime = currDate + " " + stopTime;
/* 153 */           openTime = currDate + " " + openTime;
/*     */         }
/*     */         else {
/* 156 */           startTime = currDate + " " + startTime;
/* 157 */           stopTime = nextDate + " " + stopTime;
/* 158 */           openTime = nextDate + " " + openTime;
/*     */         }
/*     */       }
/*     */       else {
/* 162 */         startTime = currDate + " " + startTime;
/* 163 */         stopTime = currDate + " " + stopTime;
/* 164 */         openTime = currDate + " " + openTime;
/*     */       }
/*     */       
/*     */ 
/* 168 */       if ((currTime.compareTo(startTime) >= 0) && (currTime.compareTo(stopTime) < 0)) {
/* 169 */         found = true;
/* 170 */         if (lotteryOpenTime.getIsTodayExpect().booleanValue()) {
/* 171 */           expect = currDate.replace("-", "") + "-" + expect; break;
/*     */         }
/*     */         
/*     */ 
/* 175 */         if (lotteryOpenTime.getStartTime().compareTo(lotteryOpenTime.getStopTime()) > 0)
/*     */         {
/* 177 */           if (currTimeHMS.compareTo(lotteryOpenTime.getStopTime()) < 0) {
/* 178 */             expect = currDate.replace("-", "") + "-" + expect; break;
/*     */           }
/*     */           
/* 181 */           expect = nextDate.replace("-", "") + "-" + expect; break;
/*     */         }
/*     */         
/*     */ 
/* 185 */         expect = nextDate.replace("-", "") + "-" + expect;
/*     */         
/*     */ 
/* 188 */         break;
/*     */       }
/*     */     }
/*     */     
/* 192 */     if (!found) {
/* 193 */       return null;
/*     */     }
/*     */     
/* 196 */     OpenTime bean = new OpenTime();
/* 197 */     bean.setExpect(expect);
/* 198 */     bean.setStartTime(startTime);
/* 199 */     bean.setStopTime(stopTime);
/* 200 */     bean.setOpenTime(openTime);
/* 201 */     return bean;
/*     */   }
/*     */   
/*     */   public OpenTime getLastOpenTime(int lotteryId, String currTime)
/*     */   {
/* 206 */     Lottery lottery = this.df.getLottery(lotteryId);
/* 207 */     if (lottery != null)
/*     */     {
/* 209 */       OpenTime currOpenTime = getCurrOpenTime(lotteryId, currTime);
/*     */       
/* 211 */       if (currOpenTime == null) {
/* 212 */         return null;
/*     */       }
/*     */       
/* 215 */       String tmpExpect = currOpenTime.getExpect();
/* 216 */       String tmpDate = tmpExpect.substring(0, 8);
/* 217 */       String currDate = DateUtil.formatTime(tmpDate, "yyyyMMdd", "yyyy-MM-dd");
/* 218 */       String currExpect = tmpExpect.substring(9);
/* 219 */       String lastDate = currDate;
/* 220 */       int lastExpect = Integer.parseInt(currExpect);
/* 221 */       int times = lottery.getTimes();
/*     */       
/* 223 */       if (lastExpect == 1) {
/* 224 */         lastDate = DateUtil.calcLastDay(currDate);
/* 225 */         lastExpect = times;
/*     */       } else {
/* 227 */         lastExpect--;
/*     */       }
/*     */       
/* 230 */       int formatCount = 3;
/* 231 */       if (lottery.getTimes() >= 1000) {
/* 232 */         formatCount = 4;
/*     */       }
/*     */       
/* 235 */       String expect = lastDate.replaceAll("-", "") + "-" + String.format(new StringBuilder().append("%0").append(formatCount).append("d").toString(), new Object[] { Integer.valueOf(lastExpect) });
/*     */       
/*     */ 
/* 238 */       if ("tw5fc".equals(lottery.getShortName())) {
/* 239 */         return getOpenTimeForNext(lotteryId, expect);
/*     */       }
/* 241 */       return getOpenTime(lotteryId, expect);
/*     */     }
/* 243 */     return null;
/*     */   }
/*     */   
/*     */   public List<OpenTime> getOpenTimeList(int lotteryId, int count)
/*     */   {
/* 248 */     List<OpenTime> list = new ArrayList();
/* 249 */     Lottery lottery = this.df.getLottery(lotteryId);
/* 250 */     if (lottery != null) {
/* 251 */       String currTime = DateUtil.getCurrentTime();
/*     */       
/* 253 */       OpenTime currOpenTime = getCurrOpenTime(lotteryId, currTime);
/* 254 */       String tmpExpect = currOpenTime.getExpect();
/* 255 */       String tmpDate = tmpExpect.substring(0, 8);
/* 256 */       String currDate = DateUtil.formatTime(tmpDate, "yyyyMMdd", "yyyy-MM-dd");
/* 257 */       int currExpect = Integer.parseInt(tmpExpect.substring(9));
/* 258 */       for (int i = 0; i < count; i++) {
/* 259 */         int formatCount = 3;
/* 260 */         if (lottery.getTimes() >= 1000) {
/* 261 */           formatCount = 4;
/*     */         }
/*     */         
/* 264 */         String expect = currDate.replaceAll("-", "") + "-" + String.format(new StringBuilder().append("%0").append(formatCount).append("d").toString(), new Object[] { Integer.valueOf(currExpect) });
///*     */         OpenTime tmpBean;
/*     */         OpenTime tmpBean;
/* 267 */         if ("tw5fc".equals(lottery.getShortName())) {
/* 268 */           tmpBean = getOpenTimeForNext(lotteryId, expect);
/*     */         }
/*     */         else {
/* 271 */           tmpBean = getOpenTime(lotteryId, expect);
/*     */         }
/* 273 */         if (tmpBean != null) {
/* 274 */           if ("mlaft".equals(lottery.getShortName())) {
/* 275 */             tmpBean = getMlaftOpenTime(tmpBean);
/*     */           }
/* 277 */           list.add(tmpBean);
/*     */         }
/*     */         
/* 280 */         String nextDate = currDate;
/* 281 */         int nextExpect = currExpect;
/* 282 */         int times = lottery.getTimes();
/*     */         
/* 284 */         if (nextExpect == times) {
/* 285 */           nextDate = DateUtil.calcNextDay(currDate);
/* 286 */           nextExpect = 1;
/*     */         } else {
/* 288 */           nextExpect++;
/*     */         }
/* 290 */         currDate = nextDate;
/* 291 */         currExpect = nextExpect;
/*     */       }
/*     */     }
/* 294 */     return list;
/*     */   }
/*     */   
/*     */   public List<OpenTime> getOpenDateList(int lotteryId, String date)
/*     */   {
/* 299 */     List<OpenTime> list = new ArrayList();
/* 300 */     Lottery lottery = this.df.getLottery(lotteryId);
/* 301 */     if (lottery != null) {
/* 302 */       int times = lottery.getTimes();
/* 303 */       for (int i = 0; i < times; i++)
/*     */       {
/* 305 */         int formatCount = 3;
/* 306 */         if (lottery.getTimes() >= 1000) {
/* 307 */           formatCount = 4;
/*     */         }
/*     */         
/* 310 */         String expect = date.replaceAll("-", "") + "-" + String.format(new StringBuilder().append("%0").append(formatCount).append("d").toString(), new Object[] { Integer.valueOf(i + 1) });
/* 311 */         OpenTime tmpBean = getOpenTime(lotteryId, expect);
/* 312 */         if (tmpBean != null) {
/* 313 */           if ("mlaft".equals(lottery.getShortName())) {
/* 314 */             tmpBean = getMlaftOpenTime(tmpBean);
/*     */           }
/* 316 */           list.add(tmpBean);
/*     */         }
/*     */       }
/*     */     }
/* 320 */     return list;
/*     */   }
/*     */   
/*     */   public OpenTime getOpenTime(int lotteryId, String expect)
/*     */   {
/* 325 */     Lottery lottery = this.df.getLottery(lotteryId);
/* 326 */     if (lottery == null) {
/* 327 */       return null;
/*     */     }
/*     */     
/* 330 */     if ("tw5fc".equals(lottery.getShortName())) {
/* 331 */       return getOpenTimeForNext(lotteryId, expect);
/*     */     }
/*     */     
/* 334 */     if (lottery != null) {
/* 335 */       List<LotteryOpenTime> list = this.df.listLotteryOpenTime(lottery.getShortName());
/* 336 */       String date = expect.substring(0, 8);
/* 337 */       String currDate = DateUtil.formatTime(date, "yyyyMMdd", "yyyy-MM-dd");
/* 338 */       String nextDate = DateUtil.calcNextDay(currDate);
/* 339 */       String lastDate = DateUtil.calcLastDay(currDate);
/* 340 */       String currExpect = expect.substring(9);
/*     */       
/* 342 */       int i = 0; for (int j = list.size(); i < j; i++) {
/* 343 */         LotteryOpenTime tmpBean = (LotteryOpenTime)list.get(i);
/* 344 */         if (tmpBean.getExpect().equals(currExpect))
/*     */         {
/* 346 */           String startDate = currDate;
/* 347 */           String startTime = tmpBean.getStartTime();
/* 348 */           String stopTime = tmpBean.getStopTime();
/* 349 */           if (i == 0)
/*     */           {
/* 351 */             if (startTime.compareTo(stopTime) > 0) {
/* 352 */               startDate = lastDate;
/*     */             }
/*     */           }
/* 355 */           if (!tmpBean.getIsTodayExpect().booleanValue())
/*     */           {
/* 357 */             if ("xjssc".equals(lottery.getShortName())) {
/* 358 */               if (startTime.compareTo(stopTime) > 0) {
/* 359 */                 String currTime = new Moment().format("HH:mm:ss");
/* 360 */                 if ((currTime.compareTo(startTime) < 0) || (currTime.compareTo("24:00:00") >= 0))
/*     */                 {
/* 362 */                   startDate = currDate;
/*     */                 }
/*     */               } else {
/* 365 */                 startDate = nextDate;
/*     */               }
/*     */             }
/*     */             else {
/* 369 */               startDate = nextDate;
/*     */             }
/*     */           }
/* 372 */           startTime = startDate + " " + startTime;
/* 373 */           return getCurrOpenTime(lotteryId, startTime);
/*     */         }
/*     */       }
/*     */     }
/* 377 */     return null;
/*     */   }
/*     */   
/*     */   private OpenTime getOpenTimeForNext(int lotteryId, String expect) {
/* 381 */     Lottery lottery = this.df.getLottery(lotteryId);
/* 382 */     if (lottery == null) {
/* 383 */       return null;
/*     */     }
/*     */     
/* 386 */     List<LotteryOpenTime> list = this.df.listLotteryOpenTime(lottery.getShortName());
/* 387 */     String date = expect.substring(0, 8);
/* 388 */     String currDate = DateUtil.formatTime(date, "yyyyMMdd", "yyyy-MM-dd");
/* 389 */     String lastDate = DateUtil.calcLastDay(currDate);
/* 390 */     String currExpect = expect.substring(9);
/*     */     
/* 392 */     for (LotteryOpenTime lotteryOpenTime : list)
/* 393 */       if (lotteryOpenTime.getExpect().equals(currExpect))
/*     */       {
/*     */         String startTime;
/*     */         
///*     */         String startTime;
/* 398 */         if (lotteryOpenTime.getIsTodayExpect().booleanValue()) {
/* 399 */           startTime = currDate + " " + lotteryOpenTime.getStartTime();
/*     */         }
/*     */         else {
/* 402 */           startTime = lastDate + " " + lotteryOpenTime.getStartTime();
/*     */         }
/* 404 */         return getCurrOpenTimeForNext(lotteryId, startTime);
/*     */       }
/* 406 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private OpenTime getMlaftOpenTime(OpenTime mlaft)
/*     */   {
/* 418 */     String expect = mlaft.getExpect();
/* 419 */     int expectNo = Integer.valueOf(expect.substring(9)).intValue();
/* 420 */     if (expectNo > 131) {
/* 421 */       String yday = DateUtil.getYesterday();
/* 422 */       expect = yday.replaceAll("-", "") + expect.substring(8);
/* 423 */       mlaft.setExpect(expect);
/*     */     }
/* 425 */     return mlaft;
/*     */   }
/*     */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/utils/open/HighOpenTimeUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */