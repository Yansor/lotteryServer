 package com.fsy.lottery.domains.capture.utils.open;

 import java.util.List;
 import com.fsy.lottery.domains.content.entity.LotteryOpenTime;
 import com.fsy.lottery.domains.content.pool.LotteryDataFactory;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Component;






 @Component
 public class LowOpenTimeUtil
   implements OpenTimeUtil
 {
   @Autowired
   private LotteryDataFactory df;

   public String getCurrExpect(String lotteryName, String currTime)
   {
/* 23 */     List<LotteryOpenTime> list = this.df.listLotteryOpenTime(lotteryName);
/* 24 */     int i = 0; for (int j = list.size(); i < j; i++) {
/* 25 */       LotteryOpenTime tmpBean = (LotteryOpenTime)list.get(i);
/* 26 */       String startTime = tmpBean.getStartTime();
/* 27 */       String stopTime = tmpBean.getStopTime();
/* 28 */       if ((currTime.compareTo(startTime) >= 0) && (currTime.compareTo(stopTime) < 0)) {
/* 29 */         if (i == 0) {
/* 30 */           return null;
         }
/* 32 */         tmpBean = (LotteryOpenTime)list.get(i - 1);
/* 33 */         return tmpBean.getExpect();
       }
     }
/* 36 */     return null;
   }

   public String getNextExpect(String lotteryName, String lastExpect)
   {
/* 41 */     List<LotteryOpenTime> list = this.df.listLotteryOpenTime(lotteryName);
/* 42 */     int i = 0; for (int j = list.size(); i < j; i++) {
/* 43 */       LotteryOpenTime tmpBean = (LotteryOpenTime)list.get(i);
/* 44 */       String expect = tmpBean.getExpect();
/* 45 */       if (lastExpect.compareTo(expect) < 0) {
/* 46 */         return expect;
       }
     }
/* 49 */     return null;
   }
 }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryCapture/lotteryCaptureRepair.jar!/lottery/domains/capture/utils/open/LowOpenTimeUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */