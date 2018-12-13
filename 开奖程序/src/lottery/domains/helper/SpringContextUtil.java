/*     */ package lottery.domains.helper;
/*     */ 
/*     */ import org.springframework.beans.BeansException;
/*     */ import org.springframework.beans.factory.NoSuchBeanDefinitionException;
/*     */ import org.springframework.context.ApplicationContext;
/*     */ import org.springframework.context.ApplicationContextAware;
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
/*     */ @Component
/*     */ public class SpringContextUtil
/*     */   implements ApplicationContextAware
/*     */ {
/*     */   private static ApplicationContext applicationContext;
/*     */   
/*     */   public void setApplicationContext(ApplicationContext applicationContext)
/*     */     throws BeansException
/*     */   {
/*  26 */     applicationContext = applicationContext;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static ApplicationContext getApplicationContext()
/*     */   {
/*  33 */     return applicationContext;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Object getBean(String name)
/*     */     throws BeansException
/*     */   {
/*  44 */     return applicationContext.getBean(name);
/*     */   }
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
/*     */ 
/*     */ 
/*     */   public static Object getBean(String name, Class<?> requiredType)
/*     */     throws BeansException
/*     */   {
/*  61 */     return applicationContext.getBean(name, requiredType);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static boolean containsBean(String name)
/*     */   {
/*  71 */     return applicationContext.containsBean(name);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static boolean isSingleton(String name)
/*     */     throws NoSuchBeanDefinitionException
/*     */   {
/*  84 */     return applicationContext.isSingleton(name);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Class<?> getType(String name)
/*     */     throws NoSuchBeanDefinitionException
/*     */   {
/*  94 */     return applicationContext.getType(name);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String[] getAliases(String name)
/*     */     throws NoSuchBeanDefinitionException
/*     */   {
/* 106 */     return applicationContext.getAliases(name);
/*     */   }
/*     */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/helper/SpringContextUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */