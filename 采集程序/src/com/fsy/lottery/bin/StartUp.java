/*    */ package com.fsy.lottery.bin;
/*    */ 
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ import org.springframework.context.ApplicationContext;
/*    */ import org.springframework.context.support.ClassPathXmlApplicationContext;
/*    */ 
/*    */ public class StartUp
/*    */ {
/* 10 */   private static final Logger logger = LoggerFactory.getLogger(StartUp.class);
/*    */   private static ApplicationContext applicationContext;
/*    */   
/*    */   public static void main(String[] args) {
/* 14 */     logger.debug("start up main method.");
/* 15 */     String[] configLocations = { "classpath:com/fsy/config/spring/spring-config.xml" };
/* 16 */     applicationContext = new ClassPathXmlApplicationContext(configLocations);
/*    */     
/* 18 */     ExecuteServer mExecuteServer = (ExecuteServer)applicationContext.getBean("executeServer");
/* 19 */     mExecuteServer.execute();
/*    */   }
/*    */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryCapture/lotteryCaptureRepair.jar!/lottery/bin/StartUp.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */