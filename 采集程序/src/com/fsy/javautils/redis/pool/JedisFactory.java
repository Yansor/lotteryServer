/*    */ package com.fsy.javautils.redis.pool;
/*    */ 
/*    */ import org.apache.commons.pool2.PooledObject;
/*    */ import org.apache.commons.pool2.PooledObjectFactory;
/*    */ import org.apache.commons.pool2.impl.DefaultPooledObject;
/*    */ import redis.clients.jedis.BinaryJedis;
/*    */ import redis.clients.jedis.Jedis;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class JedisFactory
/*    */   implements PooledObjectFactory<Jedis>
/*    */ {
/*    */   private final String host;
/*    */   private final int port;
/*    */   private final int timeout;
/*    */   private final String password;
/*    */   private final int database;
/*    */   private final String clientName;
/*    */   
/*    */   public JedisFactory(String host, int port, int timeout, String password, int database)
/*    */   {
/* 26 */     this(host, port, timeout, password, database, null);
/*    */   }
/*    */   
/*    */ 
/*    */   public JedisFactory(String host, int port, int timeout, String password, int database, String clientName)
/*    */   {
/* 32 */     this.host = host;
/* 33 */     this.port = port;
/* 34 */     this.timeout = timeout;
/* 35 */     this.password = password;
/* 36 */     this.database = database;
/* 37 */     this.clientName = clientName;
/*    */   }
/*    */   
/*    */   public void activateObject(PooledObject<Jedis> pooledJedis) throws Exception
/*    */   {
/* 42 */     BinaryJedis jedis = (BinaryJedis)pooledJedis.getObject();
/* 43 */     if (jedis.getDB().longValue() != this.database) {
/* 44 */       jedis.select(this.database);
/*    */     }
/*    */   }
/*    */   
/*    */   public void destroyObject(PooledObject<Jedis> pooledJedis)
/*    */     throws Exception
/*    */   {
/* 51 */     BinaryJedis jedis = (BinaryJedis)pooledJedis.getObject();
/* 52 */     if (jedis.isConnected()) {
/*    */       try {
/*    */         try {
/* 55 */           jedis.quit();
/*    */         }
/*    */         catch (Exception localException) {}
/* 58 */         jedis.disconnect();
/*    */       }
/*    */       catch (Exception localException1) {}
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */   public PooledObject<Jedis> makeObject()
/*    */     throws Exception
/*    */   {
/* 68 */     Jedis jedis = new Jedis(this.host, this.port, this.timeout);
/*    */     
/* 70 */     jedis.connect();
/* 71 */     if (null != this.password) {
/* 72 */       jedis.auth(this.password);
/*    */     }
/* 74 */     if (this.database != 0) {
/* 75 */       jedis.select(this.database);
/*    */     }
/* 77 */     if (this.clientName != null) {
/* 78 */       jedis.clientSetname(this.clientName);
/*    */     }
/*    */     
/* 81 */     return new DefaultPooledObject(jedis);
/*    */   }
/*    */   
/*    */ 
/*    */   public void passivateObject(PooledObject<Jedis> pooledJedis)
/*    */     throws Exception
/*    */   {}
/*    */   
/*    */   public boolean validateObject(PooledObject<Jedis> pooledJedis)
/*    */   {
/* 91 */     BinaryJedis jedis = (BinaryJedis)pooledJedis.getObject();
/*    */     try {
/* 93 */       return (jedis.isConnected()) && (jedis.ping().equals("PONG"));
/*    */     } catch (Exception e) {}
/* 95 */     return false;
/*    */   }
/*    */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryCapture/lotteryCaptureRepair.jar!/javautils/redis/pool/JedisFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */