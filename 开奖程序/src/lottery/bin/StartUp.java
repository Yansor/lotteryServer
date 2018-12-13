/*    */ package lottery.bin;
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
/* 15 */     String[] configLocations = { "classpath:config/spring/spring-config.xml" };
/* 16 */     applicationContext = new ClassPathXmlApplicationContext(configLocations);
/*    */   }
/*    */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/bin/StartUp.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */