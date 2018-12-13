/*    */ package com.fsy.javautils.redis.pool;
/*    */ 
/*    */ import org.apache.commons.pool2.impl.GenericObjectPool;
/*    */ import redis.clients.jedis.HostAndPort;
/*    */ import redis.clients.jedis.Jedis;
/*    */ import redis.clients.jedis.JedisPoolConfig;
/*    */ import redis.clients.util.Pool;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class JedisPool
/*    */   extends Pool<Jedis>
/*    */ {
/*    */   protected HostAndPort address;
/*    */   protected ConnectionInfo connectionInfo;
/*    */   
/*    */   public static JedisPoolConfig createPoolConfig(int maxPoolSize)
/*    */   {
/* 30 */     JedisPoolConfig config = new JedisPoolConfig();
/* 31 */     config.setMaxTotal(maxPoolSize);
/* 32 */     config.setMaxIdle(maxPoolSize);
/*    */     
/* 34 */     config.setTimeBetweenEvictionRunsMillis(600000L);
/*    */     
/* 36 */     return config;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   protected void initInternalPool(HostAndPort address, ConnectionInfo connectionInfo, JedisPoolConfig config)
/*    */   {
/* 43 */     this.address = address;
/* 44 */     this.connectionInfo = connectionInfo;
/*    */     
/* 46 */     JedisFactory factory = new JedisFactory(address.getHost(), address.getPort(), connectionInfo.getTimeout(), connectionInfo.getPassword(), connectionInfo.getDatabase());
/*    */     
/* 48 */     this.internalPool = new GenericObjectPool(factory, config);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void returnBrokenResource(Jedis resource)
/*    */   {
/* 56 */     if (resource != null) {
/* 57 */       returnBrokenResourceObject(resource);
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void returnResource(Jedis resource)
/*    */   {
/* 66 */     if (resource != null) {
/* 67 */       resource.resetState();
/* 68 */       returnResourceObject(resource);
/*    */     }
/*    */   }
/*    */   
/*    */   public HostAndPort getAddress() {
/* 73 */     return this.address;
/*    */   }
/*    */   
/*    */   public ConnectionInfo getConnectionInfo() {
/* 77 */     return this.connectionInfo;
/*    */   }
/*    */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryCapture/lotteryCaptureRepair.jar!/javautils/redis/pool/JedisPool.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */