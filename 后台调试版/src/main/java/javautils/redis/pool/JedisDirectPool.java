package javautils.redis.pool;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisPoolConfig;

public class JedisDirectPool
  extends JedisPool
{
  public JedisDirectPool(HostAndPort address, JedisPoolConfig config)
  {
    initInternalPool(address, new ConnectionInfo(), config);
  }
  
  public JedisDirectPool(HostAndPort address, ConnectionInfo connectionInfo, JedisPoolConfig config)
  {
    initInternalPool(address, connectionInfo, config);
  }
}
