/*     */ package com.fsy.javautils.redis.pool;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import redis.clients.jedis.HostAndPort;
/*     */ import redis.clients.jedis.JedisPoolConfig;
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
/*     */ public class JedisPoolBuilder
/*     */ {
/*     */   public static final String DIRECT_POOL_PREFIX = "direct:";
/*  26 */   private static Logger logger = LoggerFactory.getLogger(JedisPoolBuilder.class);
/*     */   
/*     */   private String[] sentinelHosts;
/*  29 */   private int sentinelPort = 26379;
/*     */   
/*     */   private String masterName;
/*     */   
/*     */   private String[] shardedMasterNames;
/*  34 */   private int poolSize = -1;
/*     */   
/*  36 */   private int database = 0;
/*  37 */   private String password = ConnectionInfo.DEFAULT_PASSWORD;
/*  38 */   private int timeout = 2000;
/*     */   
/*     */   public JedisPoolBuilder setHosts(String[] hosts) {
/*  41 */     this.sentinelHosts = hosts;
/*  42 */     return this;
/*     */   }
/*     */   
/*     */   public JedisPoolBuilder setHosts(String hosts) {
/*  46 */     if (hosts != null) {
/*  47 */       this.sentinelHosts = hosts.split(",");
/*     */     }
/*  49 */     return this;
/*     */   }
/*     */   
/*     */   public JedisPoolBuilder setPort(int port) {
/*  53 */     this.sentinelPort = port;
/*  54 */     return this;
/*     */   }
/*     */   
/*     */   public JedisPoolBuilder setMasterName(String masterName) {
/*  58 */     this.masterName = masterName;
/*  59 */     return this;
/*     */   }
/*     */   
/*     */   public JedisPoolBuilder setShardedMasterNames(String[] shardedMasterNames) {
/*  63 */     this.shardedMasterNames = shardedMasterNames;
/*  64 */     return this;
/*     */   }
/*     */   
/*     */   public JedisPoolBuilder setShardedMasterNames(String shardedMasterNames) {
/*  68 */     if (shardedMasterNames != null) {
/*  69 */       this.shardedMasterNames = shardedMasterNames.split(",");
/*     */     }
/*  71 */     return this;
/*     */   }
/*     */   
/*     */   public JedisPoolBuilder setDirectHostAndPort(String host, String port) {
/*  75 */     this.masterName = (host + ":" + port);
/*  76 */     return this;
/*     */   }
/*     */   
/*     */   public JedisPoolBuilder setPoolSize(int poolSize) {
/*  80 */     this.poolSize = poolSize;
/*  81 */     return this;
/*     */   }
/*     */   
/*     */   public JedisPoolBuilder setDatabase(int database) {
/*  85 */     this.database = database;
/*  86 */     return this;
/*     */   }
/*     */   
/*     */   public JedisPoolBuilder setPassword(String password) {
/*  90 */     this.password = password;
/*  91 */     return this;
/*     */   }
/*     */   
/*     */   public JedisPoolBuilder setTimeout(int timeout) {
/*  95 */     this.timeout = timeout;
/*  96 */     return this;
/*     */   }
/*     */   
/*     */   public JedisPool buildPool()
/*     */   {
/* 101 */     if ((this.masterName == null) || ("".equals(this.masterName))) {
/* 102 */       throw new IllegalArgumentException("masterName is null or empty");
/*     */     }
/*     */     
/* 105 */     if (this.poolSize < 1) {
/* 106 */       throw new IllegalArgumentException("poolSize is less then one");
/*     */     }
/*     */     
/* 109 */     JedisPoolConfig config = JedisPool.createPoolConfig(this.poolSize);
/* 110 */     ConnectionInfo connectionInfo = new ConnectionInfo(this.database, this.password, this.timeout);
/*     */     
/* 112 */     if (isDirect(this.masterName)) {
/* 113 */       return buildDirectPool(this.masterName, connectionInfo, config);
/*     */     }
/* 115 */     if ((this.sentinelHosts == null) || (this.sentinelHosts.length == 0)) {
/* 116 */       throw new IllegalArgumentException("sentinelHosts is null or empty");
/*     */     }
/* 118 */     return buildSentinelPool(this.masterName, connectionInfo, config);
/*     */   }
/*     */   
/*     */ 
/*     */   public List<JedisPool> buildShardedPools()
/*     */   {
/* 124 */     if ((this.shardedMasterNames == null) || (this.shardedMasterNames.length == 0) || ("".equals(this.shardedMasterNames[0]))) {
/* 125 */       throw new IllegalArgumentException("shardedMasterNames is null or empty");
/*     */     }
/*     */     
/* 128 */     if (this.poolSize < 1) {
/* 129 */       throw new IllegalArgumentException("poolSize is less then one");
/*     */     }
/*     */     
/* 132 */     JedisPoolConfig config = JedisPool.createPoolConfig(this.poolSize);
/* 133 */     ConnectionInfo connectionInfo = new ConnectionInfo(this.database, this.password, this.timeout);
/*     */     
/* 135 */     List<JedisPool> jedisPools = new ArrayList();
/*     */     
/* 137 */     if (isDirect(this.shardedMasterNames[0])) {
/* 138 */       for (String theMasterName : this.shardedMasterNames) {
/* 139 */         jedisPools.add(buildDirectPool(theMasterName, connectionInfo, config));
/*     */       }
/*     */     }
/*     */     else {
/* 143 */       if ((this.sentinelHosts == null) || (this.sentinelHosts.length == 0)) {
/* 144 */         throw new IllegalArgumentException("sentinelHosts is null or empty");
/*     */       }
/*     */       
/* 147 */       for (String theMasterName : this.shardedMasterNames) {
/* 148 */         jedisPools.add(buildSentinelPool(theMasterName, connectionInfo, config));
/*     */       }
/*     */     }
/* 151 */     return jedisPools;
/*     */   }
/*     */   
/*     */   private JedisPool buildDirectPool(String directMasterName, ConnectionInfo connectionInfo, JedisPoolConfig config) {
/* 155 */     String hostPortStr = directMasterName.substring(directMasterName.indexOf(":") + 1, directMasterName.length());
/* 156 */     String[] hostPort = hostPortStr.split(":");
/*     */     
/* 158 */     logger.info("Building JedisDirectPool, on redis server " + hostPort[0] + " ,sentinelPort is " + hostPort[1]);
/*     */     
/* 160 */     HostAndPort masterAddress = new HostAndPort(hostPort[0], Integer.parseInt(hostPort[1]));
/* 161 */     return new JedisDirectPool(masterAddress, config);
/*     */   }
/*     */   
/*     */   private JedisPool buildSentinelPool(String sentinelMasterName, ConnectionInfo connectionInfo, JedisPoolConfig config) {
/* 165 */     logger.info("Building JedisSentinelPool, on sentinel sentinelHosts:" + Arrays.toString(this.sentinelHosts) + " ,sentinelPort is " + this.sentinelPort + " ,masterName is " + sentinelMasterName);
/*     */     
/*     */ 
/* 168 */     HostAndPort[] sentinelAddress = new HostAndPort[this.sentinelHosts.length];
/* 169 */     for (int i = 0; i < this.sentinelHosts.length; i++) {
/* 170 */       sentinelAddress[i] = new HostAndPort(this.sentinelHosts[i], this.sentinelPort);
/*     */     }
/*     */     
/* 173 */     return new JedisSentinelPool(sentinelAddress, sentinelMasterName, connectionInfo, config);
/*     */   }
/*     */   
/*     */   private static boolean isDirect(String masterName) {
/* 177 */     return masterName.startsWith("direct:");
/*     */   }
/*     */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryCapture/lotteryCaptureRepair.jar!/javautils/redis/pool/JedisPoolBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */