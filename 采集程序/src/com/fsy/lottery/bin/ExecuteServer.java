/*    */ package com.fsy.lottery.bin;
/*    */ 
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.scheduling.annotation.Scheduled;
/*    */ import org.springframework.stereotype.Component;
/*    */ 
/*    */ @Component
/*    */ public class ExecuteServer
/*    */ {
/* 12 */   private static final Logger mLogger = LoggerFactory.getLogger(ExecuteServer.class);
/*    */   
/*    */   private static final String CRON = "0 0 0/1 * * *";
/*    */   
/*    */   @Autowired
/*    */   Catalina mCatalina;
/*    */   
/*    */   @Scheduled(cron="0 0 0/1 * * *")
/*    */   public void execute()
/*    */   {
/* 22 */     mLogger.info("重启====>>>>>>>>>");
/* 23 */     this.mCatalina.executeOpenCai();
/*    */   }
/*    */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryCapture/lotteryCaptureRepair.jar!/lottery/bin/ExecuteServer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */