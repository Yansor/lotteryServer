package javautils.redis.pool;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.HostAndPort;

public class JedisDirectPool extends JedisPool
{
    public JedisDirectPool(final HostAndPort address, final JedisPoolConfig config) {
        this.initInternalPool(address, new ConnectionInfo(), config);
    }
    
    public JedisDirectPool(final HostAndPort address, final ConnectionInfo connectionInfo, final JedisPoolConfig config) {
        this.initInternalPool(address, connectionInfo, config);
    }
}
