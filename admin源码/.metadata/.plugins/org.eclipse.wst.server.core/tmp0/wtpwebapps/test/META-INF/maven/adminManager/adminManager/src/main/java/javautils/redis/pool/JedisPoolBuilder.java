package javautils.redis.pool;

import java.util.Arrays;
import redis.clients.jedis.HostAndPort;
import java.util.ArrayList;
import java.util.List;
import redis.clients.jedis.JedisPoolConfig;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class JedisPoolBuilder
{
    public static final String DIRECT_POOL_PREFIX = "direct:";
    private static Logger logger;
    private String[] sentinelHosts;
    private int sentinelPort;
    private String masterName;
    private String[] shardedMasterNames;
    private int poolSize;
    private int database;
    private String password;
    private int timeout;
    
    static {
        JedisPoolBuilder.logger = LoggerFactory.getLogger((Class)JedisPoolBuilder.class);
    }
    
    public JedisPoolBuilder() {
        this.sentinelPort = 26379;
        this.poolSize = -1;
        this.database = 0;
        this.password = ConnectionInfo.DEFAULT_PASSWORD;
        this.timeout = 2000;
    }
    
    public JedisPoolBuilder setHosts(final String[] hosts) {
        this.sentinelHosts = hosts;
        return this;
    }
    
    public JedisPoolBuilder setHosts(final String hosts) {
        if (hosts != null) {
            this.sentinelHosts = hosts.split(",");
        }
        return this;
    }
    
    public JedisPoolBuilder setPort(final int port) {
        this.sentinelPort = port;
        return this;
    }
    
    public JedisPoolBuilder setMasterName(final String masterName) {
        this.masterName = masterName;
        return this;
    }
    
    public JedisPoolBuilder setShardedMasterNames(final String[] shardedMasterNames) {
        this.shardedMasterNames = shardedMasterNames;
        return this;
    }
    
    public JedisPoolBuilder setShardedMasterNames(final String shardedMasterNames) {
        if (shardedMasterNames != null) {
            this.shardedMasterNames = shardedMasterNames.split(",");
        }
        return this;
    }
    
    public JedisPoolBuilder setDirectHostAndPort(final String host, final String port) {
        this.masterName = String.valueOf(host) + ":" + port;
        return this;
    }
    
    public JedisPoolBuilder setPoolSize(final int poolSize) {
        this.poolSize = poolSize;
        return this;
    }
    
    public JedisPoolBuilder setDatabase(final int database) {
        this.database = database;
        return this;
    }
    
    public JedisPoolBuilder setPassword(final String password) {
        this.password = password;
        return this;
    }
    
    public JedisPoolBuilder setTimeout(final int timeout) {
        this.timeout = timeout;
        return this;
    }
    
    public JedisPool buildPool() {
        if (this.masterName == null || "".equals(this.masterName)) {
            throw new IllegalArgumentException("masterName is null or empty");
        }
        if (this.poolSize < 1) {
            throw new IllegalArgumentException("poolSize is less then one");
        }
        final JedisPoolConfig config = JedisPool.createPoolConfig(this.poolSize);
        final ConnectionInfo connectionInfo = new ConnectionInfo(this.database, this.password, this.timeout);
        if (isDirect(this.masterName)) {
            return this.buildDirectPool(this.masterName, connectionInfo, config);
        }
        if (this.sentinelHosts == null || this.sentinelHosts.length == 0) {
            throw new IllegalArgumentException("sentinelHosts is null or empty");
        }
        return this.buildSentinelPool(this.masterName, connectionInfo, config);
    }
    
    public List<JedisPool> buildShardedPools() {
        if (this.shardedMasterNames == null || this.shardedMasterNames.length == 0 || "".equals(this.shardedMasterNames[0])) {
            throw new IllegalArgumentException("shardedMasterNames is null or empty");
        }
        if (this.poolSize < 1) {
            throw new IllegalArgumentException("poolSize is less then one");
        }
        final JedisPoolConfig config = JedisPool.createPoolConfig(this.poolSize);
        final ConnectionInfo connectionInfo = new ConnectionInfo(this.database, this.password, this.timeout);
        final List<JedisPool> jedisPools = new ArrayList<JedisPool>();
        if (isDirect(this.shardedMasterNames[0])) {
            String[] shardedMasterNames;
            for (int length = (shardedMasterNames = this.shardedMasterNames).length, i = 0; i < length; ++i) {
                final String theMasterName = shardedMasterNames[i];
                jedisPools.add(this.buildDirectPool(theMasterName, connectionInfo, config));
            }
        }
        else {
            if (this.sentinelHosts == null || this.sentinelHosts.length == 0) {
                throw new IllegalArgumentException("sentinelHosts is null or empty");
            }
            String[] shardedMasterNames2;
            for (int length2 = (shardedMasterNames2 = this.shardedMasterNames).length, j = 0; j < length2; ++j) {
                final String theMasterName = shardedMasterNames2[j];
                jedisPools.add(this.buildSentinelPool(theMasterName, connectionInfo, config));
            }
        }
        return jedisPools;
    }
    
    private JedisPool buildDirectPool(final String directMasterName, final ConnectionInfo connectionInfo, final JedisPoolConfig config) {
        final String hostPortStr = directMasterName.substring(directMasterName.indexOf(":") + 1, directMasterName.length());
        final String[] hostPort = hostPortStr.split(":");
        JedisPoolBuilder.logger.info("Building JedisDirectPool, on redis server " + hostPort[0] + " ,sentinelPort is " + hostPort[1]);
        final HostAndPort masterAddress = new HostAndPort(hostPort[0], Integer.parseInt(hostPort[1]));
        return new JedisDirectPool(masterAddress, config);
    }
    
    private JedisPool buildSentinelPool(final String sentinelMasterName, final ConnectionInfo connectionInfo, final JedisPoolConfig config) {
        JedisPoolBuilder.logger.info("Building JedisSentinelPool, on sentinel sentinelHosts:" + Arrays.toString(this.sentinelHosts) + " ,sentinelPort is " + this.sentinelPort + " ,masterName is " + sentinelMasterName);
        final HostAndPort[] sentinelAddress = new HostAndPort[this.sentinelHosts.length];
        for (int i = 0; i < this.sentinelHosts.length; ++i) {
            sentinelAddress[i] = new HostAndPort(this.sentinelHosts[i], this.sentinelPort);
        }
        return new JedisSentinelPool(sentinelAddress, sentinelMasterName, connectionInfo, config);
    }
    
    private static boolean isDirect(final String masterName) {
        return masterName.startsWith("direct:");
    }
}
