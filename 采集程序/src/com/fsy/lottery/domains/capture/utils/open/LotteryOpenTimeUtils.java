 package com.fsy.lottery.domains.capture.utils.open;

 import java.util.List;
 import com.fsy.javautils.date.Moment;
 import com.fsy.lottery.domains.content.entity.LotteryOpenTime;
 import com.fsy.lottery.domains.content.pool.LotteryDataFactory;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Component;



 @Component
 public class LotteryOpenTimeUtils
 {
   @Autowired
   private LotteryDataFactory df;

   public String getCurrExpect(String lotteryName)
   {
/* 20 */     Moment m = new Moment();
/* 21 */     String curTime = m.toCurTime();
/* 22 */     curTime = "00:00:00";
/* 23 */     String toDay = m.toSimpleDate();
/* 24 */     List<LotteryOpenTime> list = this.df.listLotteryOpenTime(lotteryName);
/* 25 */     for (int i = 0; i < list.size(); i++) {
/* 26 */       if (i < list.size() - 1) {
/* 27 */         LotteryOpenTime ot1 = (LotteryOpenTime)list.get(i);
/* 28 */         LotteryOpenTime ot2 = (LotteryOpenTime)list.get(i + 1);
/* 29 */         if ((curTime.compareTo(ot1.getOpenTime()) >= 0) && 
/* 30 */           (curTime.compareTo(ot2.getOpenTime()) < 0))
         {
/* 32 */           String expect = toDay.replace("-", "") + "-" + ot1.getExpect();
/* 33 */           return expect; }
/* 34 */         if ((curTime.compareTo(ot1.getOpenTime()) >= 0) && 
/* 35 */           (ot1.getOpenTime().compareTo(ot2.getOpenTime()) > 0)) {
/* 36 */           String yesterDay = m.subtract(1, "d").toSimpleDate();

/* 38 */           String expect = yesterDay.replace("-", "") + "-" + ot1.getExpect();
/* 39 */           return expect;
         }
       } else {
/* 42 */         LotteryOpenTime lotteryOpenTime = (LotteryOpenTime)list.get(list.size() - 1);
/* 43 */         if (lotteryOpenTime.getOpenTime().compareTo(lotteryOpenTime
/* 44 */           .getStartTime()) < 0) {
/* 45 */           String yesterDay = m.subtract(1, "d").toSimpleDate();

/* 47 */           String expect = yesterDay.replace("-", "") + "-" + lotteryOpenTime.getExpect();
/* 48 */           return expect;
         }

/* 51 */         String expect = toDay.replace("-", "") + "-" + lotteryOpenTime.getExpect();
/* 52 */         return expect;
       }
     }


/* 57 */     return "";
   }
 }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryCapture/lotteryCaptureRepair.jar!/lottery/domains/capture/utils/open/LotteryOpenTimeUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */