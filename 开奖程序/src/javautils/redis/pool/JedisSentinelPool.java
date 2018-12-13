/*     */ package javautils.redis.pool;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.CountDownLatch;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ import javautils.redis.JedisTemplate;
/*     */ import javautils.redis.JedisTemplate.JedisAction;
/*     */ import javautils.redis.JedisUtils;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import redis.clients.jedis.HostAndPort;
/*     */ import redis.clients.jedis.Jedis;
/*     */ import redis.clients.jedis.JedisPoolConfig;
/*     */ import redis.clients.jedis.JedisPubSub;
/*     */ import redis.clients.jedis.exceptions.JedisConnectionException;
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
/*     */ 
/*     */ public final class JedisSentinelPool
/*     */   extends JedisPool
/*     */ {
/*     */   private static final String NO_ADDRESS_YET = "I dont know because no sentinel up";
/*  35 */   private static Logger logger = LoggerFactory.getLogger(JedisSentinelPool.class);
/*     */   
/*  37 */   private List<JedisPool> sentinelPools = new ArrayList();
/*     */   
/*     */   private MasterSwitchListener masterSwitchListener;
/*     */   private String masterName;
/*     */   private JedisPoolConfig masterPoolConfig;
/*     */   private ConnectionInfo masterConnectionInfo;
/*  43 */   private CountDownLatch poolInit = new CountDownLatch(1);
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
/*     */ 
/*     */   public JedisSentinelPool(HostAndPort[] sentinelAddresses, String masterName, ConnectionInfo masterConnectionInfo, JedisPoolConfig masterPoolConfig)
/*     */   {
/*  59 */     assertArgument((sentinelAddresses == null) || (sentinelAddresses.length == 0), "seintinelInfos is not set");
/*     */     
/*  61 */     for (HostAndPort sentinelInfo : sentinelAddresses) {
/*  62 */       JedisPool sentinelPool = new JedisDirectPool(sentinelInfo, new JedisPoolConfig());
/*  63 */       this.sentinelPools.add(sentinelPool);
/*     */     }
/*     */     
/*     */ 
/*  67 */     assertArgument(masterConnectionInfo == null, "masterConnectionInfo is not set");
/*  68 */     this.masterConnectionInfo = masterConnectionInfo;
/*     */     
/*     */ 
/*  71 */     assertArgument((masterName == null) || (masterName.isEmpty()), "masterName is not set");
/*  72 */     this.masterName = masterName;
/*     */     
/*     */ 
/*  75 */     assertArgument(masterPoolConfig == null, "masterPoolConfig is not set");
/*  76 */     this.masterPoolConfig = masterPoolConfig;
/*     */     
/*     */ 
/*  79 */     this.masterSwitchListener = new MasterSwitchListener();
/*  80 */     this.masterSwitchListener.start();
/*     */     try
/*     */     {
/*  83 */       if (!this.poolInit.await(5L, TimeUnit.SECONDS)) {
/*  84 */         logger.warn("the sentiel pool can't not init in 5 seconds");
/*     */       }
/*     */     } catch (InterruptedException e) {
/*  87 */       Thread.currentThread().interrupt();
/*     */     }
/*     */   }
/*     */   
/*     */   public JedisSentinelPool(HostAndPort[] sentinelAddresses, String masterName, JedisPoolConfig masterPoolConfig) {
/*  92 */     this(sentinelAddresses, masterName, new ConnectionInfo(), masterPoolConfig);
/*     */   }
/*     */   
/*     */ 
/*     */   public void destroy()
/*     */   {
/*  98 */     this.masterSwitchListener.shutdown();
/*     */     
/*     */ 
/* 101 */     for (JedisPool sentinel : this.sentinelPools) {
/* 102 */       sentinel.destroy();
/*     */     }
/*     */     
/*     */ 
/* 106 */     destroyInternelPool();
/*     */     
/*     */     try
/*     */     {
/* 110 */       logger.info("Waiting for MasterSwitchListener thread finish");
/* 111 */       this.masterSwitchListener.join();
/* 112 */       logger.info("MasterSwitchListener thread finished");
/*     */     } catch (InterruptedException e) {
/* 114 */       Thread.currentThread().interrupt();
/*     */     }
/*     */   }
/*     */   
/*     */   protected void destroyInternelPool() {
/* 119 */     closeInternalPool();
/*     */     
/* 121 */     this.address = null;
/* 122 */     this.connectionInfo = null;
/* 123 */     this.internalPool = null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private static void assertArgument(boolean expression, String message)
/*     */   {
/* 130 */     if (expression) {
/* 131 */       throw new IllegalArgumentException(message);
/*     */     }
/*     */   }
/*     */   
/*     */   public MasterSwitchListener getMasterSwitchListener()
/*     */   {
/* 137 */     return this.masterSwitchListener;
/*     */   }
/*     */   
/*     */ 
/*     */   public class MasterSwitchListener
/*     */     extends Thread
/*     */   {
/*     */     public static final String THREAD_NAME_PREFIX = "MasterSwitchListener-";
/*     */     
/*     */     private JedisPubSub subscriber;
/*     */     private JedisPool sentinelPool;
/*     */     private Jedis sentinelJedis;
/* 149 */     private AtomicBoolean running = new AtomicBoolean(true);
/*     */     private HostAndPort previousMasterAddress;
/*     */     
/*     */     public MasterSwitchListener() {
/* 153 */       super();
/*     */     }
/*     */     
/*     */     /* Error */
/*     */     public void shutdown()
/*     */     {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: getfield 15	javautils/redis/pool/JedisSentinelPool$MasterSwitchListener:running	Ljava/util/concurrent/atomic/AtomicBoolean;
/*     */       //   4: iconst_0
/*     */       //   5: invokevirtual 16	java/util/concurrent/atomic/AtomicBoolean:getAndSet	(Z)Z
/*     */       //   8: pop
/*     */       //   9: aload_0
/*     */       //   10: invokevirtual 17	javautils/redis/pool/JedisSentinelPool$MasterSwitchListener:interrupt	()V
/*     */       //   13: aload_0
/*     */       //   14: getfield 18	javautils/redis/pool/JedisSentinelPool$MasterSwitchListener:subscriber	Lredis/clients/jedis/JedisPubSub;
/*     */       //   17: ifnull +10 -> 27
/*     */       //   20: aload_0
/*     */       //   21: getfield 18	javautils/redis/pool/JedisSentinelPool$MasterSwitchListener:subscriber	Lredis/clients/jedis/JedisPubSub;
/*     */       //   24: invokevirtual 19	redis/clients/jedis/JedisPubSub:unsubscribe	()V
/*     */       //   27: aload_0
/*     */       //   28: getfield 20	javautils/redis/pool/JedisSentinelPool$MasterSwitchListener:sentinelJedis	Lredis/clients/jedis/Jedis;
/*     */       //   31: invokestatic 21	javautils/redis/JedisUtils:destroyJedis	(Lredis/clients/jedis/Jedis;)V
/*     */       //   34: goto +13 -> 47
/*     */       //   37: astore_1
/*     */       //   38: aload_0
/*     */       //   39: getfield 20	javautils/redis/pool/JedisSentinelPool$MasterSwitchListener:sentinelJedis	Lredis/clients/jedis/Jedis;
/*     */       //   42: invokestatic 21	javautils/redis/JedisUtils:destroyJedis	(Lredis/clients/jedis/Jedis;)V
/*     */       //   45: aload_1
/*     */       //   46: athrow
/*     */       //   47: return
/*     */       // Line number table:
/*     */       //   Java source line #159	-> byte code offset #0
/*     */       //   Java source line #160	-> byte code offset #9
/*     */       //   Java source line #164	-> byte code offset #13
/*     */       //   Java source line #165	-> byte code offset #20
/*     */       //   Java source line #168	-> byte code offset #27
/*     */       //   Java source line #169	-> byte code offset #34
/*     */       //   Java source line #168	-> byte code offset #37
/*     */       //   Java source line #170	-> byte code offset #47
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	signature
/*     */       //   0	48	0	this	MasterSwitchListener
/*     */       //   37	9	1	localObject	Object
/*     */       // Exception table:
/*     */       //   from	to	target	type
/*     */       //   13	27	37	finally
/*     */     }
/*     */     
/*     */     public void run()
/*     */     {
/* 174 */       while (this.running.get()) {
/*     */         try {
/* 176 */           this.sentinelPool = pickupSentinel();
/*     */           
/* 178 */           if (this.sentinelPool != null)
/*     */           {
/* 180 */             HostAndPort masterAddress = queryMasterAddress();
/*     */             
/* 182 */             if ((JedisSentinelPool.this.internalPool != null) && (isAddressChange(masterAddress))) {
/* 183 */               JedisSentinelPool.logger.info("The internalPool {} had changed, destroy it now.", this.previousMasterAddress);
/* 184 */               JedisSentinelPool.this.destroyInternelPool();
/*     */             }
/*     */             
/* 187 */             if (JedisSentinelPool.this.internalPool == null) {
/* 188 */               JedisSentinelPool.logger.info("The internalPool {} is not init or the address had changed, init it now.", masterAddress);
/*     */               
/* 190 */               JedisSentinelPool.this.initInternalPool(masterAddress, JedisSentinelPool.this.masterConnectionInfo, JedisSentinelPool.this.masterPoolConfig);
/* 191 */               JedisSentinelPool.this.poolInit.countDown();
/*     */             }
/*     */             
/* 194 */             this.previousMasterAddress = masterAddress;
/*     */             
/* 196 */             this.sentinelJedis = ((Jedis)this.sentinelPool.getResource());
/* 197 */             this.subscriber = new MasterSwitchSubscriber();
/* 198 */             this.sentinelJedis.subscribe(this.subscriber, new String[] { "+switch-master", "+redirect-to-master" });
/*     */           } else {
/* 200 */             JedisSentinelPool.logger.info("All sentinels down, sleep 2 seconds and try to connect again.");
/*     */             
/*     */ 
/* 203 */             if (JedisSentinelPool.this.internalPool == null) {
/* 204 */               HostAndPort masterAddress = new HostAndPort("I dont know because no sentinel up", 6379);
/* 205 */               JedisSentinelPool.this.initInternalPool(masterAddress, JedisSentinelPool.this.masterConnectionInfo, JedisSentinelPool.this.masterPoolConfig);
/* 206 */               this.previousMasterAddress = masterAddress;
/*     */             }
/* 208 */             sleep(2000);
/*     */           }
/*     */         }
/*     */         catch (JedisConnectionException e) {
/* 212 */           if (this.sentinelJedis != null) {
/* 213 */             this.sentinelPool.returnBrokenResource(this.sentinelJedis);
/*     */           }
/*     */           
/* 216 */           if (this.running.get()) {
/* 217 */             JedisSentinelPool.logger.error("Lost connection with Sentinel " + this.sentinelPool.getAddress() + ", sleep 1 seconds and try to connect other one. ");
/*     */             
/* 219 */             sleep(1000);
/*     */           }
/*     */         } catch (Exception e) {
/* 222 */           JedisSentinelPool.logger.error(e.getMessage(), e);
/* 223 */           sleep(1000);
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */     public HostAndPort getCurrentMasterAddress() {
/* 229 */       return this.previousMasterAddress;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */     private JedisPool pickupSentinel()
/*     */     {
/* 236 */       for (JedisPool pool : JedisSentinelPool.this.sentinelPools) {
/* 237 */         if (JedisUtils.ping(pool)) {
/* 238 */           return pool;
/*     */         }
/*     */       }
/*     */       
/* 242 */       return null;
/*     */     }
/*     */     
/*     */     private boolean isAddressChange(HostAndPort currentMasterAddress) {
/* 246 */       if (this.previousMasterAddress == null) {
/* 247 */         return true;
/*     */       }
/*     */       
/* 250 */       return !this.previousMasterAddress.equals(currentMasterAddress);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */     private HostAndPort queryMasterAddress()
/*     */     {
/* 257 */       JedisTemplate sentinelTemplate = new JedisTemplate(this.sentinelPool);
/* 258 */       List<String> address = (List)sentinelTemplate.execute(new JedisTemplate.JedisAction()
/*     */       {
/*     */         public List<String> action(Jedis jedis) {
/* 261 */           return jedis.sentinelGetMasterAddrByName(JedisSentinelPool.this.masterName);
/*     */         }
/*     */       });
/*     */       
/* 265 */       if ((address == null) || (address.isEmpty())) {
/* 266 */         throw new IllegalArgumentException("Master name " + JedisSentinelPool.this.masterName + " is not in sentinel.conf");
/*     */       }
/*     */       
/* 269 */       return new HostAndPort((String)address.get(0), Integer.valueOf((String)address.get(1)).intValue());
/*     */     }
/*     */     
/*     */     private void sleep(int millseconds) {
/*     */       try {
/* 274 */         Thread.sleep(millseconds);
/*     */       } catch (InterruptedException e1) {
/* 276 */         Thread.currentThread().interrupt();
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */     private class MasterSwitchSubscriber
/*     */       extends JedisPubSub
/*     */     {
/*     */       private MasterSwitchSubscriber() {}
/*     */       
/*     */       public void onMessage(String channel, String message)
/*     */       {
/* 288 */         JedisSentinelPool.logger.info("Sentinel " + MasterSwitchListener.this.sentinelPool.getAddress() + " published: " + message);
/* 289 */         String[] switchMasterMsg = message.split(" ");
/*     */         
/* 291 */         if (JedisSentinelPool.this.masterName.equals(switchMasterMsg[0]))
/*     */         {
/*     */ 
/* 294 */           HostAndPort masterAddress = new HostAndPort(switchMasterMsg[3], Integer.parseInt(switchMasterMsg[4]));
/* 295 */           JedisSentinelPool.logger.info("Switch master to " + masterAddress);
/* 296 */           JedisSentinelPool.this.destroyInternelPool();
/* 297 */           JedisSentinelPool.this.initInternalPool(masterAddress, JedisSentinelPool.this.masterConnectionInfo, JedisSentinelPool.this.masterPoolConfig);
/* 298 */           MasterSwitchListener.this.previousMasterAddress = masterAddress;
/*     */         }
/*     */       }
/*     */       
/*     */       public void onPMessage(String pattern, String channel, String message) {}
/*     */       
/*     */       public void onSubscribe(String channel, int subscribedChannels) {}
/*     */       
/*     */       public void onUnsubscribe(String channel, int subscribedChannels) {}
/*     */       
/*     */       public void onPUnsubscribe(String pattern, int subscribedChannels) {}
/*     */       
/*     */       public void onPSubscribe(String pattern, int subscribedChannels) {}
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/javautils/redis/pool/JedisSentinelPool.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */