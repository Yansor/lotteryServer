package javautils.redis.pool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisPoolConfig;

public class JedisPoolBuilder
{
  public static final String DIRECT_POOL_PREFIX = "direct:";
  private static Logger logger = LoggerFactory.getLogger(JedisPoolBuilder.class);
  private String[] sentinelHosts;
  private int sentinelPort = 26379;
  private String masterName;
  private String[] shardedMasterNames;
  private int poolSize = -1;
  private int database = 0;
  private String password = ConnectionInfo.DEFAULT_PASSWORD;
  private int timeout = 2000;
  
  public JedisPoolBuilder setHosts(String[] hosts)
  {
    this.sentinelHosts = hosts;
    return this;
  }
  
  public JedisPoolBuilder setHosts(String hosts)
  {
    if (hosts != null) {
      this.sentinelHosts = hosts.split(",");
    }
    return this;
  }
  
  public JedisPoolBuilder setPort(int port)
  {
    this.sentinelPort = port;
    return this;
  }
  
  public JedisPoolBuilder setMasterName(String masterName)
  {
    this.masterName = masterName;
    return this;
  }
  
  public JedisPoolBuilder setShardedMasterNames(String[] shardedMasterNames)
  {
    this.shardedMasterNames = shardedMasterNames;
    return this;
  }
  
  public JedisPoolBuilder setShardedMasterNames(String shardedMasterNames)
  {
    if (shardedMasterNames != null) {
      this.shardedMasterNames = shardedMasterNames.split(",");
    }
    return this;
  }
  
  public JedisPoolBuilder setDirectHostAndPort(String host, String port)
  {
    this.masterName = (host + ":" + port);
    return this;
  }
  
  public JedisPoolBuilder setPoolSize(int poolSize)
  {
    this.poolSize = poolSize;
    return this;
  }
  
  public JedisPoolBuilder setDatabase(int database)
  {
    this.database = database;
    return this;
  }
  
  public JedisPoolBuilder setPassword(String password)
  {
    this.password = password;
    return this;
  }
  
  public JedisPoolBuilder setTimeout(int timeout)
  {
    this.timeout = timeout;
    return this;
  }
  
  public JedisPool buildPool()
  {
    if ((this.masterName == null) || ("".equals(this.masterName))) {
      throw new IllegalArgumentException("masterName is null or empty");
    }
    if (this.poolSize < 1) {
      throw new IllegalArgumentException("poolSize is less then one");
    }
    JedisPoolConfig config = JedisPool.createPoolConfig(this.poolSize);
    ConnectionInfo connectionInfo = new ConnectionInfo(this.database, this.password, this.timeout);
    if (isDirect(this.masterName)) {
      return buildDirectPool(this.masterName, connectionInfo, config);
    }
    if ((this.sentinelHosts == null) || (this.sentinelHosts.length == 0)) {
      throw new IllegalArgumentException("sentinelHosts is null or empty");
    }
    return buildSentinelPool(this.masterName, connectionInfo, config);
  }
  
  public List<JedisPool> buildShardedPools()
  {
    if ((this.shardedMasterNames == null) || (this.shardedMasterNames.length == 0) || ("".equals(this.shardedMasterNames[0]))) {
      throw new IllegalArgumentException("shardedMasterNames is null or empty");
    }
    if (this.poolSize < 1) {
      throw new IllegalArgumentException("poolSize is less then one");
    }
    JedisPoolConfig config = JedisPool.createPoolConfig(this.poolSize);
    ConnectionInfo connectionInfo = new ConnectionInfo(this.database, this.password, this.timeout);
    
    List<JedisPool> jedisPools = new ArrayList();
    String[] arrayOfString;
    int j;
    int i;
    if (isDirect(this.shardedMasterNames[0]))
    {
      j = (arrayOfString = this.shardedMasterNames).length;
      for (i = 0; i < j; i++)
      {
        String theMasterName = arrayOfString[i];
        jedisPools.add(buildDirectPool(theMasterName, connectionInfo, config));
      }
    }
    else
    {
      if ((this.sentinelHosts == null) || (this.sentinelHosts.length == 0)) {
        throw new IllegalArgumentException("sentinelHosts is null or empty");
      }
      j = (arrayOfString = this.shardedMasterNames).length;
      for (i = 0; i < j; i++)
      {
        String theMasterName = arrayOfString[i];
        jedisPools.add(buildSentinelPool(theMasterName, connectionInfo, config));
      }
    }
    return jedisPools;
  }
  
  private JedisPool buildDirectPool(String directMasterName, ConnectionInfo connectionInfo, JedisPoolConfig config)
  {
    String hostPortStr = directMasterName.substring(directMasterName.indexOf(":") + 1, directMasterName.length());
    String[] hostPort = hostPortStr.split(":");
    
    logger.info("Building JedisDirectPool, on redis server " + hostPort[0] + " ,sentinelPort is " + hostPort[1]);
    
    HostAndPort masterAddress = new HostAndPort(hostPort[0], Integer.parseInt(hostPort[1]));
    return new JedisDirectPool(masterAddress, config);
  }
  
  private JedisPool buildSentinelPool(String sentinelMasterName, ConnectionInfo connectionInfo, JedisPoolConfig config)
  {
    logger.info("Building JedisSentinelPool, on sentinel sentinelHosts:" + Arrays.toString(this.sentinelHosts) + 
      " ,sentinelPort is " + this.sentinelPort + " ,masterName is " + sentinelMasterName);
    
    HostAndPort[] sentinelAddress = new HostAndPort[this.sentinelHosts.length];
    for (int i = 0; i < this.sentinelHosts.length; i++) {
      sentinelAddress[i] = new HostAndPort(this.sentinelHosts[i], this.sentinelPort);
    }
    return new JedisSentinelPool(sentinelAddress, sentinelMasterName, connectionInfo, config);
  }
  
  private static boolean isDirect(String masterName)
  {
    return masterName.startsWith("direct:");
  }
}
