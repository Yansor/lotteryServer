 package com.fsy.javautils.redis;

 import com.fsy.javautils.redis.pool.JedisPool;
 import redis.clients.jedis.Jedis;
 import redis.clients.jedis.exceptions.JedisException;








 public class JedisUtils
 {
   private static final String OK_CODE = "OK";
   private static final String OK_MULTI_CODE = "+OK";

   public static boolean isStatusOk(String status)
   {
/* 21 */     return (status != null) && (("OK".equals(status)) || ("+OK".equals(status)));
   }



   public static void destroyJedis(Jedis jedis)
   {
/* 28 */     if ((jedis != null) && (jedis.isConnected())) {
       try {
         try {
/* 31 */           jedis.quit();
         }
         catch (Exception localException) {}
/* 34 */         jedis.disconnect();
       }
       catch (Exception localException1) {}
     }
   }



   public static boolean ping(JedisPool pool)
   {
/* 44 */     JedisTemplate template = new JedisTemplate(pool);
     try {
/* 46 */       String result = (String)template.execute(new JedisTemplate.JedisAction() {
         public String action(Jedis jedis) {
/* 48 */           return jedis.ping();
         }
/* 50 */       });
/* 51 */       return (result != null) && (result.equals("PONG"));
     } catch (JedisException e) {}
/* 53 */     return false;
   }
 }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryCapture/lotteryCaptureRepair.jar!/javautils/redis/JedisUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */