package javautils.redis;

import redis.clients.jedis.exceptions.JedisException;
import javautils.redis.pool.JedisPool;
import redis.clients.jedis.Jedis;

public class JedisUtils
{
    private static final String OK_CODE = "OK";
    private static final String OK_MULTI_CODE = "+OK";
    
    public static boolean isStatusOk(final String status) {
        return status != null && ("OK".equals(status) || "+OK".equals(status));
    }
    
    public static void destroyJedis(final Jedis jedis) {
        if (jedis != null && jedis.isConnected()) {
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
    
    public static boolean ping(final JedisPool pool) {
        final JedisTemplate template = new JedisTemplate(pool);
        try {
            final String result = template.execute((JedisTemplate.JedisAction<String>)new JedisTemplate.JedisAction<String>() {
                @Override
                public String action(final Jedis jedis) {
                    return jedis.ping();
                }
            });
            return result != null && result.equals("PONG");
        }
        catch (JedisException e) {
            return false;
        }
    }
}
