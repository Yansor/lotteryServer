package javautils.redis.pool;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.GenericObjectPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.util.Pool;

public abstract class JedisPool extends Pool<Jedis>
{
    protected HostAndPort address;
    protected ConnectionInfo connectionInfo;
    
    public static JedisPoolConfig createPoolConfig(final int maxPoolSize) {
        final JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(maxPoolSize);
        config.setMaxIdle(maxPoolSize);
        config.setTimeBetweenEvictionRunsMillis(600000L);
        return config;
    }
    
    protected void initInternalPool(final HostAndPort address, final ConnectionInfo connectionInfo, final JedisPoolConfig config) {
        this.address = address;
        this.connectionInfo = connectionInfo;
        final JedisFactory factory = new JedisFactory(address.getHost(), address.getPort(), connectionInfo.getTimeout(), connectionInfo.getPassword(), connectionInfo.getDatabase());
        this.internalPool = new GenericObjectPool((PooledObjectFactory)factory, (GenericObjectPoolConfig)config);
    }
    
    public void returnBrokenResource(final Jedis resource) {
        if (resource != null) {
            this.returnBrokenResourceObject(resource);
        }
    }
    
    public void returnResource(final Jedis resource) {
        if (resource != null) {
            resource.resetState();
            this.returnResourceObject(resource);
        }
    }
    
    public HostAndPort getAddress() {
        return this.address;
    }
    
    public ConnectionInfo getConnectionInfo() {
        return this.connectionInfo;
    }
}
