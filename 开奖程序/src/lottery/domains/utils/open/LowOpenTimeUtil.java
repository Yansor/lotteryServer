/*     */ package lottery.domains.utils.open;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javautils.date.DateUtil;
/*     */ import lottery.domains.content.entity.Lottery;
/*     */ import lottery.domains.content.entity.LotteryOpenTime;
/*     */ import lottery.domains.pool.DataFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Component;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Component
/*     */ public class LowOpenTimeUtil
/*     */   implements OpenTimeUtil
/*     */ {
/*     */   @Autowired
/*     */   private DataFactory df;
/*     */   
/*     */   public OpenTime getCurrOpenTime(int lotteryId, String currTime)
/*     */   {
/*  25 */     Lottery lottery = this.df.getLottery(lotteryId);
/*  26 */     if (lottery != null) {
/*  27 */       List<LotteryOpenTime> list = this.df.listLotteryOpenTime(lottery.getShortName());
/*  28 */       for (LotteryOpenTime tmpBean : list) {
/*  29 */         String startTime = tmpBean.getStartTime();
/*  30 */         String stopTime = tmpBean.getStopTime();
/*  31 */         if ((currTime.compareTo(startTime) >= 0) && (currTime.compareTo(stopTime) < 0)) {
/*  32 */           OpenTime bean = new OpenTime();
/*  33 */           bean.setExpect(tmpBean.getExpect());
/*  34 */           bean.setStartTime(tmpBean.getStartTime());
/*  35 */           bean.setStopTime(tmpBean.getStopTime());
/*  36 */           bean.setOpenTime(tmpBean.getOpenTime());
/*  37 */           return bean;
/*     */         }
/*     */       }
/*     */     }
/*  41 */     return null;
/*     */   }
/*     */   
/*     */   public OpenTime getLastOpenTime(int lotteryId, String currTime)
/*     */   {
/*  46 */     Lottery lottery = this.df.getLottery(lotteryId);
/*  47 */     if (lottery != null) {
/*  48 */       List<LotteryOpenTime> list = this.df.listLotteryOpenTime(lottery.getShortName());
/*  49 */       int i = 0; for (int j = list.size(); i < j; i++) {
/*  50 */         LotteryOpenTime tmpBean = (LotteryOpenTime)list.get(i);
/*  51 */         String startTime = tmpBean.getStartTime();
/*  52 */         String stopTime = tmpBean.getStopTime();
/*  53 */         if ((currTime.compareTo(startTime) >= 0) && (currTime.compareTo(stopTime) < 0)) {
/*  54 */           if (i == 0) {
/*  55 */             return null;
/*     */           }
/*  57 */           tmpBean = (LotteryOpenTime)list.get(i - 1);
/*  58 */           OpenTime bean = new OpenTime();
/*  59 */           bean.setExpect(tmpBean.getExpect());
/*  60 */           bean.setStartTime(tmpBean.getStartTime());
/*  61 */           bean.setStopTime(tmpBean.getStopTime());
/*  62 */           bean.setOpenTime(tmpBean.getOpenTime());
/*  63 */           return bean;
/*     */         }
/*     */       }
/*     */     }
/*  67 */     return null;
/*     */   }
/*     */   
/*     */   public List<OpenTime> getOpenTimeList(int lotteryId, int count)
/*     */   {
/*  72 */     List<OpenTime> list = new ArrayList();
/*  73 */     Lottery lottery = this.df.getLottery(lotteryId);
/*  74 */     String currTime; if (lottery != null) {
/*  75 */       currTime = DateUtil.getCurrentTime();
/*  76 */       List<LotteryOpenTime> opList = this.df.listLotteryOpenTime(lottery.getShortName());
/*  77 */       for (LotteryOpenTime tmpBean : opList) {
/*  78 */         String stopTime = tmpBean.getStopTime();
/*  79 */         if (currTime.compareTo(stopTime) < 0) {
/*  80 */           OpenTime bean = new OpenTime();
/*  81 */           bean.setExpect(tmpBean.getExpect());
/*  82 */           bean.setStartTime(tmpBean.getStartTime());
/*  83 */           bean.setStopTime(tmpBean.getStopTime());
/*  84 */           bean.setOpenTime(tmpBean.getOpenTime());
/*  85 */           list.add(bean);
/*  86 */           if (list.size() == count) break;
/*     */         }
/*     */       }
/*     */     }
/*  90 */     return list;
/*     */   }
/*     */   
/*     */   public List<OpenTime> getOpenDateList(int lotteryId, String date)
/*     */   {
/*  95 */     List<OpenTime> list = new ArrayList();
/*  96 */     Lottery lottery = this.df.getLottery(lotteryId);
/*  97 */     if (lottery != null) {
/*  98 */       List<LotteryOpenTime> opList = this.df.listLotteryOpenTime(lottery.getShortName());
/*  99 */       for (LotteryOpenTime tmpBean : opList) {
/* 100 */         String openTime = tmpBean.getOpenTime();
/* 101 */         if (openTime.indexOf(date) != -1) {
/* 102 */           OpenTime bean = new OpenTime();
/* 103 */           bean.setExpect(tmpBean.getExpect());
/* 104 */           bean.setStartTime(tmpBean.getStartTime());
/* 105 */           bean.setStopTime(tmpBean.getStopTime());
/* 106 */           bean.setOpenTime(tmpBean.getOpenTime());
/* 107 */           list.add(bean);
/*     */         }
/*     */       }
/*     */     }
/* 111 */     return list;
/*     */   }
/*     */   
/*     */   public OpenTime getOpenTime(int lotteryId, String expect)
/*     */   {
/* 116 */     Lottery lottery = this.df.getLottery(lotteryId);
/* 117 */     if (lottery != null) {
/* 118 */       List<LotteryOpenTime> opList = this.df.listLotteryOpenTime(lottery.getShortName());
/* 119 */       for (LotteryOpenTime tmpBean : opList) {
/* 120 */         String thisExpect = tmpBean.getExpect();
/* 121 */         if (thisExpect.equals(expect)) {
/* 122 */           OpenTime bean = new OpenTime();
/* 123 */           bean.setExpect(tmpBean.getExpect());
/* 124 */           bean.setStartTime(tmpBean.getStartTime());
/* 125 */           bean.setStopTime(tmpBean.getStopTime());
/* 126 */           bean.setOpenTime(tmpBean.getOpenTime());
/* 127 */           return bean;
/*     */         }
/*     */       }
/*     */     }
/* 131 */     return null;
/*     */   }
/*     */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/utils/open/LowOpenTimeUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */