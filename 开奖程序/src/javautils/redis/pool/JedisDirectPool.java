/*    */ package javautils.redis.pool;
/*    */ 
/*    */ import redis.clients.jedis.HostAndPort;
/*    */ import redis.clients.jedis.JedisPoolConfig;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class JedisDirectPool
/*    */   extends JedisPool
/*    */ {
/*    */   public JedisDirectPool(HostAndPort address, JedisPoolConfig config)
/*    */   {
/* 17 */     initInternalPool(address, new ConnectionInfo(), config);
/*    */   }
/*    */   
/*    */   public JedisDirectPool(HostAndPort address, ConnectionInfo connectionInfo, JedisPoolConfig config) {
/* 21 */     initInternalPool(address, connectionInfo, config);
/*    */   }
/*    */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/javautils/redis/pool/JedisDirectPool.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */