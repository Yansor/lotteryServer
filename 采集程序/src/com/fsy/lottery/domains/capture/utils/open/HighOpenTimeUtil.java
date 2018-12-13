/*     */ package com.fsy.lottery.domains.capture.utils.open;
/*     */ 
/*     */ import java.util.List;
/*     */ import com.fsy.javautils.date.DateUtil;
/*     */ import com.fsy.lottery.domains.content.dao.LotteryCrawlerStatusDao;
/*     */ import com.fsy.lottery.domains.content.entity.LotteryCrawlerStatus;
/*     */ import com.fsy.lottery.domains.content.entity.LotteryOpenTime;
/*     */ import com.fsy.lottery.domains.content.pool.LotteryDataFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Component;
/*     */ 
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
/*     */   private LotteryDataFactory df;
/*     */   @Autowired
/*     */   private LotteryCrawlerStatusDao lotteryCrawlerStatusDao;
/*     */   
/*     */   public String getExpect(String lotteryName, String currTime)
/*     */   {
/*  28 */     List<LotteryOpenTime> list = this.df.listLotteryOpenTime(lotteryName);
/*  29 */     String currDate = currTime.substring(0, 10);
/*  30 */     String nextDate = DateUtil.calcNextDay(currDate);
/*  31 */     String lastDate = DateUtil.calcLastDay(currDate);
/*  32 */     int i = 0; for (int j = list.size(); i < j; i++) {
/*  33 */       LotteryOpenTime tmpBean = (LotteryOpenTime)list.get(i);
/*  34 */       String startDate = currDate;
/*  35 */       String stopDate = currDate;
/*  36 */       String openDate = currDate;
/*  37 */       String expectDate = currDate;
/*  38 */       String startTime = tmpBean.getStartTime();
/*  39 */       String stopTime = tmpBean.getStopTime();
/*  40 */       String openTime = tmpBean.getOpenTime();
/*  41 */       String expect = tmpBean.getExpect();
/*  42 */       if (i == 0) {
/*  43 */         if (startTime.compareTo(stopTime) > 0) {
/*  44 */           startDate = lastDate;
/*     */         }
/*  46 */       } else if (i == j - 1) {
/*  47 */         if (startTime.compareTo(stopTime) > 0) {
/*  48 */           stopDate = nextDate;
/*     */         }
/*  50 */         if (startTime.compareTo(openTime) > 0) {
/*  51 */           openDate = nextDate;
/*     */         }
/*  53 */         if (currTime.compareTo(stopDate + " " + stopTime) >= 0) {
/*  54 */           tmpBean = (LotteryOpenTime)list.get(0);
/*  55 */           startDate = nextDate;
/*  56 */           stopDate = nextDate;
/*  57 */           openDate = nextDate;
/*  58 */           expectDate = nextDate;
/*  59 */           startTime = tmpBean.getStartTime();
/*  60 */           stopTime = tmpBean.getStopTime();
/*  61 */           openTime = tmpBean.getOpenTime();
/*  62 */           expect = tmpBean.getExpect();
/*  63 */           if (startTime.compareTo(stopTime) > 0) {
/*  64 */             startDate = currDate;
/*     */           }
/*     */         }
/*     */       } else {
/*  68 */         if (startTime.compareTo(stopTime) > 0) {
/*  69 */           stopDate = nextDate;
/*     */         }
/*  71 */         if (startTime.compareTo(openTime) > 0) {
/*  72 */           openDate = nextDate;
/*     */         }
/*     */       }
/*  75 */       if (!tmpBean.getIsTodayExpect().booleanValue()) {
/*  76 */         expectDate = lastDate;
/*     */       }
/*  78 */       startTime = startDate + " " + startTime;
/*  79 */       stopTime = stopDate + " " + stopTime;
/*  80 */       openTime = openDate + " " + openTime;
/*  81 */       expect = expectDate.replace("-", "") + "-" + expect;
/*  82 */       if ((currTime.compareTo(startTime) >= 0) && (currTime.compareTo(stopTime) < 0)) {
/*  83 */         return expect;
/*     */       }
/*     */     }
/*  86 */     return null;
/*     */   }
/*     */   
/*     */   public String getCurrExpect(String lotteryName, String currTime)
/*     */   {
/*  91 */     LotteryCrawlerStatus lotteryCrawlerStatus = this.lotteryCrawlerStatusDao.get(lotteryName);
/*     */     
/*  93 */     String tmpExpect = getExpect(lotteryName, currTime);
/*  94 */     String tmpDate = tmpExpect.substring(0, 8);
/*  95 */     String currDate = DateUtil.formatTime(tmpDate, "yyyyMMdd", "yyyy-MM-dd");
/*  96 */     String currExpect = tmpExpect.substring(9);
/*  97 */     String lastDate = currDate;
/*  98 */     int lastExpect = Integer.parseInt(currExpect);
/*  99 */     int times = lotteryCrawlerStatus.getTimes().intValue();
/*     */     
/* 101 */     if (lastExpect == 1) {
/* 102 */       lastDate = DateUtil.calcLastDay(currDate);
/* 103 */       lastExpect = times;
/*     */     } else {
/* 105 */       lastExpect--;
/*     */     }
/*     */     
/* 108 */     int formatCount = 3;
/* 109 */     if ("fgffc".equals(lotteryName)) {
/* 110 */       formatCount = 4;
/*     */     }
/*     */     
/* 113 */     String expect = lastDate.replaceAll("-", "") + "-" + String.format(new StringBuilder().append("%0").append(formatCount).append("d").toString(), new Object[] { Integer.valueOf(lastExpect) });
/* 114 */     return expect;
/*     */   }
/*     */   
/*     */   public String getNextExpect(String lotteryName, String lastExpect)
/*     */   {
/* 119 */     return null;
/*     */   }
/*     */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryCapture/lotteryCaptureRepair.jar!/lottery/domains/capture/utils/open/HighOpenTimeUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */