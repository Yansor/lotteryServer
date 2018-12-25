 package com.fsy.lottery.domains.capture.sites.pcqq;

 import com.alibaba.fastjson.annotation.JSONField;
 import com.fsy.javautils.date.Moment;


 public class PCQQBean
 {
   @JSONField(deserialize=false)
/* 10 */   private String onlinetime = new Moment()
/* 11 */     .toSimpleTime();
   @JSONField(name="c")
   private int onlinenumber;
   @JSONField(deserialize=false)
   private int onlinechange;

   public String getOnlinetime() {
/* 18 */     return this.onlinetime;
   }

   public void setOnlinetime(String onlinetime) {
/* 22 */     this.onlinetime = onlinetime;
   }

   public int getOnlinenumber() {
/* 26 */     return this.onlinenumber;
   }

   public void setOnlinenumber(int onlinenumber) {
/* 30 */     this.onlinenumber = onlinenumber;
   }

   public int getOnlinechange() {
/* 34 */     return this.onlinechange;
   }

   public void setOnlinechange(int onlinechange) {
/* 38 */     this.onlinechange = onlinechange;
   }
 }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryCapture/lotteryCaptureRepair.jar!/lottery/domains/capture/sites/pcqq/PCQQBean.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */