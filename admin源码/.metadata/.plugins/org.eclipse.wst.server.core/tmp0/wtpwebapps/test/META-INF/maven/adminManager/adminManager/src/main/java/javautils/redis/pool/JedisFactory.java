package javautils.redis.pool;

import org.apache.commons.pool2.impl.DefaultPooledObject;
import redis.clients.jedis.BinaryJedis;
import org.apache.commons.pool2.PooledObject;
import redis.clients.jedis.Jedis;
import org.apache.commons.pool2.PooledObjectFactory;

public class JedisFactory implements PooledObjectFactory<Jedis>
{
    private final String host;
    private final int port;
    private final int timeout;
    private final String password;
    private final int database;
    private final String clientName;
    
    public JedisFactory(final String host, final int port, final int timeout, final String password, final int database) {
        this(host, port, timeout, password, database, null);
    }
    
    public JedisFactory(final String host, final int port, final int timeout, final String password, final int database, final String clientName) {
        this.host = host;
        this.port = port;
        this.timeout = timeout;
        this.password = password;
        this.database = database;
        this.clientName = clientName;
    }
    
    public void activateObject(final PooledObject<Jedis> pooledJedis) throws Exception {
        final BinaryJedis jedis = (BinaryJedis)pooledJedis.getObject();
        if (jedis.getDB() != this.database) {
            jedis.select(this.database);
        }
    }
    
    public void destroyObject(final PooledObject<Jedis> pooledJedis) throws Exception {
        final BinaryJedis jedis = (BinaryJedis)pooledJedis.getObject();
        if (jedis.isConnected()) {
            try {
                try {
                    jedis.quit();
                }
                catch (Exception ex) {}
                jedis.disconnect();
            }
            catch (Exception ex2) {}
        }
    }
    
    public PooledObject<Jedis> makeObject() throws Exception {
        final Jedis jedis = new Jedis(this.host, this.port, this.timeout);
        jedis.connect();
        if (this.password != null) {
            jedis.auth(this.password);
        }
        if (this.database != 0) {
            jedis.select(this.database);
        }
        if (this.clientName != null) {
            jedis.clientSetname(this.clientName);
        }
        return (PooledObject<Jedis>)new DefaultPooledObject((Object)jedis);
    }
    
    public void passivateObject(final PooledObject<Jedis> pooledJedis) throws Exception {
    }
    
    public boolean validateObject(final PooledObject<Jedis> pooledJedis) {
        final BinaryJedis jedis = (BinaryJedis)pooledJedis.getObject();
        try {
            return jedis.isConnected() && jedis.ping().equals("PONG");
        }
        catch (Exception e) {
            return false;
        }
    }
}
