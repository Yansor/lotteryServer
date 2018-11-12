package javautils.redis;

import redis.clients.jedis.Tuple;
import java.util.Map;
import java.util.Set;
import redis.clients.jedis.exceptions.JedisDataException;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.jedis.Pipeline;
import java.util.List;
import redis.clients.jedis.exceptions.JedisException;
import redis.clients.jedis.Jedis;
import org.slf4j.LoggerFactory;
import javautils.redis.pool.JedisPool;
import org.slf4j.Logger;

public class JedisTemplate
{
    private static Logger logger;
    private JedisPool jedisPool;
    
    static {
        JedisTemplate.logger = LoggerFactory.getLogger((Class)JedisTemplate.class);
    }
    
    public JedisTemplate(final JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }
    
    public <T> T execute(final JedisAction<T> jedisAction) throws JedisException {
        Jedis jedis = null;
        boolean broken = false;
        try {
            jedis = (Jedis)this.jedisPool.getResource();
            return jedisAction.action(jedis);
        }
        catch (JedisException e) {
            broken = this.handleJedisException(e);
            throw e;
        }
        finally {
            this.closeResource(jedis, broken);
        }
    }
    
    public void execute(final JedisActionNoResult jedisAction) throws JedisException {
        Jedis jedis = null;
        boolean broken = false;
        try {
            jedis = (Jedis)this.jedisPool.getResource();
            jedisAction.action(jedis);
        }
        catch (JedisException e) {
            broken = this.handleJedisException(e);
            throw e;
        }
        finally {
            this.closeResource(jedis, broken);
        }
        this.closeResource(jedis, broken);
    }
    
    public List<Object> execute(final PipelineAction pipelineAction) throws JedisException {
        Jedis jedis = null;
        boolean broken = false;
        try {
            jedis = (Jedis)this.jedisPool.getResource();
            final Pipeline pipeline = jedis.pipelined();
            pipelineAction.action(pipeline);
            return (List<Object>)pipeline.syncAndReturnAll();
        }
        catch (JedisException e) {
            broken = this.handleJedisException(e);
            throw e;
        }
        finally {
            this.closeResource(jedis, broken);
        }
    }
    
    public void execute(final PipelineActionNoResult pipelineAction) throws JedisException {
        Jedis jedis = null;
        boolean broken = false;
        try {
            jedis = (Jedis)this.jedisPool.getResource();
            final Pipeline pipeline = jedis.pipelined();
            pipelineAction.action(pipeline);
            pipeline.sync();
        }
        catch (JedisException e) {
            broken = this.handleJedisException(e);
            throw e;
        }
        finally {
            this.closeResource(jedis, broken);
        }
        this.closeResource(jedis, broken);
    }
    
    public JedisPool getJedisPool() {
        return this.jedisPool;
    }
    
    protected boolean handleJedisException(final JedisException jedisException) {
        if (jedisException instanceof JedisConnectionException) {
            JedisTemplate.logger.error("Redis connection " + this.jedisPool.getAddress() + " lost.", (Throwable)jedisException);
        }
        else if (jedisException instanceof JedisDataException) {
            if (jedisException.getMessage() == null || jedisException.getMessage().indexOf("READONLY") == -1) {
                return false;
            }
            JedisTemplate.logger.error("Redis connection " + this.jedisPool.getAddress() + " are read-only slave.", (Throwable)jedisException);
        }
        else {
            JedisTemplate.logger.error("Jedis exception happen.", (Throwable)jedisException);
        }
        return true;
    }
    
    protected void closeResource(final Jedis jedis, final boolean conectionBroken) {
        try {
            if (conectionBroken) {
                this.jedisPool.returnBrokenResource(jedis);
            }
            else {
                this.jedisPool.returnResource(jedis);
            }
        }
        catch (Exception e) {
            JedisTemplate.logger.error("return back jedis failed, will fore close the jedis.", (Throwable)e);
            JedisUtils.destroyJedis(jedis);
        }
    }
    
    public Boolean del(final String... keys) {
        return this.execute((JedisAction<Boolean>)new JedisAction<Boolean>() {
            @Override
            public Boolean action(final Jedis jedis) {
                return jedis.del(keys) == keys.length;
            }
        });
    }
    
    public void flushDB() {
        this.execute(new JedisActionNoResult() {
            @Override
            public void action(final Jedis jedis) {
                jedis.flushDB();
            }
        });
    }
    
    public String get(final String key) {
        return this.execute((JedisAction<String>)new JedisAction<String>() {
            @Override
            public String action(final Jedis jedis) {
                return jedis.get(key);
            }
        });
    }
    
    public Set<String> keys(final String key) {
        return this.execute((JedisAction<Set<String>>)new JedisAction<Set<String>>() {
            @Override
            public Set<String> action(final Jedis jedis) {
                return (Set<String>)jedis.keys(key);
            }
        });
    }
    
    public Long getAsLong(final String key) {
        final String result = this.get(key);
        return (result != null) ? Long.valueOf(result) : null;
    }
    
    public Integer getAsInt(final String key) {
        final String result = this.get(key);
        return (result != null) ? Integer.valueOf(result) : null;
    }
    
    public Double getAsDouble(final String key) {
        final String result = this.get(key);
        return (result != null) ? Double.valueOf(result) : null;
    }
    
    public List<String> mget(final String... keys) {
        return this.execute((JedisAction<List<String>>)new JedisAction<List<String>>() {
            @Override
            public List<String> action(final Jedis jedis) {
                return (List<String>)jedis.mget(keys);
            }
        });
    }
    
    public void set(final String key, final String value) {
        this.execute(new JedisActionNoResult() {
            @Override
            public void action(final Jedis jedis) {
                jedis.set(key, value);
            }
        });
    }
    
    public void expire(final String key, final int seconds) {
        this.execute(new JedisActionNoResult() {
            @Override
            public void action(final Jedis jedis) {
                jedis.expire(key, seconds);
            }
        });
    }
    
    public void setex(final String key, final String value, final int seconds) {
        this.execute(new JedisActionNoResult() {
            @Override
            public void action(final Jedis jedis) {
                jedis.setex(key, seconds, value);
            }
        });
    }
    
    public Boolean setnx(final String key, final String value) {
        return this.execute((JedisAction<Boolean>)new JedisAction<Boolean>() {
            @Override
            public Boolean action(final Jedis jedis) {
                return jedis.setnx(key, value) == 1L;
            }
        });
    }
    
    public Boolean setnxex(final String key, final String value, final int seconds) {
        return this.execute((JedisAction<Boolean>)new JedisAction<Boolean>() {
            @Override
            public Boolean action(final Jedis jedis) {
                final String result = jedis.set(key, value, "NX", "EX", seconds);
                return JedisUtils.isStatusOk(result);
            }
        });
    }
    
    public String getSet(final String key, final String value) {
        return this.execute((JedisAction<String>)new JedisAction<String>() {
            @Override
            public String action(final Jedis jedis) {
                return jedis.getSet(key, value);
            }
        });
    }
    
    public Long incr(final String key) {
        return this.execute((JedisAction<Long>)new JedisAction<Long>() {
            @Override
            public Long action(final Jedis jedis) {
                return jedis.incr(key);
            }
        });
    }
    
    public Long incrBy(final String key, final long increment) {
        return this.execute((JedisAction<Long>)new JedisAction<Long>() {
            @Override
            public Long action(final Jedis jedis) {
                return jedis.incrBy(key, increment);
            }
        });
    }
    
    public Double incrByFloat(final String key, final double increment) {
        return this.execute((JedisAction<Double>)new JedisAction<Double>() {
            @Override
            public Double action(final Jedis jedis) {
                return jedis.incrByFloat(key, increment);
            }
        });
    }
    
    public Long decr(final String key) {
        return this.execute((JedisAction<Long>)new JedisAction<Long>() {
            @Override
            public Long action(final Jedis jedis) {
                return jedis.decr(key);
            }
        });
    }
    
    public Long decrBy(final String key, final long decrement) {
        return this.execute((JedisAction<Long>)new JedisAction<Long>() {
            @Override
            public Long action(final Jedis jedis) {
                return jedis.decrBy(key, decrement);
            }
        });
    }
    
    public String hget(final String key, final String fieldName) {
        return this.execute((JedisAction<String>)new JedisAction<String>() {
            @Override
            public String action(final Jedis jedis) {
                return jedis.hget(key, fieldName);
            }
        });
    }
    
    public List<String> hmget(final String key, final String... fieldsNames) {
        return this.execute((JedisAction<List<String>>)new JedisAction<List<String>>() {
            @Override
            public List<String> action(final Jedis jedis) {
                return (List<String>)jedis.hmget(key, fieldsNames);
            }
        });
    }
    
    public Map<String, String> hgetAll(final String key) {
        return this.execute((JedisAction<Map<String, String>>)new JedisAction<Map<String, String>>() {
            @Override
            public Map<String, String> action(final Jedis jedis) {
                return (Map<String, String>)jedis.hgetAll(key);
            }
        });
    }
    
    public void hset(final String key, final String fieldName, final String value) {
        this.execute(new JedisActionNoResult() {
            @Override
            public void action(final Jedis jedis) {
                jedis.hset(key, fieldName, value);
            }
        });
    }
    
    public void hmset(final String key, final Map<String, String> map) {
        this.execute(new JedisActionNoResult() {
            @Override
            public void action(final Jedis jedis) {
                jedis.hmset(key, map);
            }
        });
    }
    
    public Boolean hsetnx(final String key, final String fieldName, final String value) {
        return this.execute((JedisAction<Boolean>)new JedisAction<Boolean>() {
            @Override
            public Boolean action(final Jedis jedis) {
                return jedis.hsetnx(key, fieldName, value) == 1L;
            }
        });
    }
    
    public Long hincrBy(final String key, final String fieldName, final long increment) {
        return this.execute((JedisAction<Long>)new JedisAction<Long>() {
            @Override
            public Long action(final Jedis jedis) {
                return jedis.hincrBy(key, fieldName, increment);
            }
        });
    }
    
    public Double hincrByFloat(final String key, final String fieldName, final double increment) {
        return this.execute((JedisAction<Double>)new JedisAction<Double>() {
            @Override
            public Double action(final Jedis jedis) {
                return jedis.hincrByFloat(key, fieldName, increment);
            }
        });
    }
    
    public Long hdel(final String key, final String... fieldsNames) {
        return this.execute((JedisAction<Long>)new JedisAction<Long>() {
            @Override
            public Long action(final Jedis jedis) {
                return jedis.hdel(key, fieldsNames);
            }
        });
    }
    
    public Boolean hexists(final String key, final String fieldName) {
        return this.execute((JedisAction<Boolean>)new JedisAction<Boolean>() {
            @Override
            public Boolean action(final Jedis jedis) {
                return jedis.hexists(key, fieldName);
            }
        });
    }
    
    public Set<String> hkeys(final String key) {
        return this.execute((JedisAction<Set<String>>)new JedisAction<Set<String>>() {
            @Override
            public Set<String> action(final Jedis jedis) {
                return (Set<String>)jedis.hkeys(key);
            }
        });
    }
    
    public Long hlen(final String key) {
        return this.execute((JedisAction<Long>)new JedisAction<Long>() {
            @Override
            public Long action(final Jedis jedis) {
                return jedis.hlen(key);
            }
        });
    }
    
    public Long lpush(final String key, final String... values) {
        return this.execute((JedisAction<Long>)new JedisAction<Long>() {
            @Override
            public Long action(final Jedis jedis) {
                return jedis.lpush(key, values);
            }
        });
    }
    
    public Long rpush(final String key, final String... values) {
        return this.execute((JedisAction<Long>)new JedisAction<Long>() {
            @Override
            public Long action(final Jedis jedis) {
                return jedis.rpush(key, values);
            }
        });
    }
    
    public String rpop(final String key) {
        return this.execute((JedisAction<String>)new JedisAction<String>() {
            @Override
            public String action(final Jedis jedis) {
                return jedis.rpop(key);
            }
        });
    }
    
    public String brpop(final String key) {
        return this.execute((JedisAction<String>)new JedisAction<String>() {
            @Override
            public String action(final Jedis jedis) {
                final List<String> nameValuePair = (List<String>)jedis.brpop(key);
                if (nameValuePair != null) {
                    return nameValuePair.get(1);
                }
                return null;
            }
        });
    }
    
    public String brpop(final int timeout, final String key) {
        return this.execute((JedisAction<String>)new JedisAction<String>() {
            @Override
            public String action(final Jedis jedis) {
                final List<String> nameValuePair = (List<String>)jedis.brpop(timeout, key);
                if (nameValuePair != null) {
                    return nameValuePair.get(1);
                }
                return null;
            }
        });
    }
    
    public String rpoplpush(final String sourceKey, final String destinationKey) {
        return this.execute((JedisAction<String>)new JedisAction<String>() {
            @Override
            public String action(final Jedis jedis) {
                return jedis.rpoplpush(sourceKey, destinationKey);
            }
        });
    }
    
    public String brpoplpush(final String source, final String destination, final int timeout) {
        return this.execute((JedisAction<String>)new JedisAction<String>() {
            @Override
            public String action(final Jedis jedis) {
                return jedis.brpoplpush(source, destination, timeout);
            }
        });
    }
    
    public Long llen(final String key) {
        return this.execute((JedisAction<Long>)new JedisAction<Long>() {
            @Override
            public Long action(final Jedis jedis) {
                return jedis.llen(key);
            }
        });
    }
    
    public String lindex(final String key, final long index) {
        return this.execute((JedisAction<String>)new JedisAction<String>() {
            @Override
            public String action(final Jedis jedis) {
                return jedis.lindex(key, index);
            }
        });
    }
    
    public List<String> lrange(final String key, final int start, final int end) {
        return this.execute((JedisAction<List<String>>)new JedisAction<List<String>>() {
            @Override
            public List<String> action(final Jedis jedis) {
                return (List<String>)jedis.lrange(key, (long)start, (long)end);
            }
        });
    }
    
    public void ltrim(final String key, final int start, final int end) {
        this.execute(new JedisActionNoResult() {
            @Override
            public void action(final Jedis jedis) {
                jedis.ltrim(key, (long)start, (long)end);
            }
        });
    }
    
    public void ltrimFromLeft(final String key, final int size) {
        this.execute(new JedisActionNoResult() {
            @Override
            public void action(final Jedis jedis) {
                jedis.ltrim(key, 0L, (long)(size - 1));
            }
        });
    }
    
    public Boolean lremFirst(final String key, final String value) {
        return this.execute((JedisAction<Boolean>)new JedisAction<Boolean>() {
            @Override
            public Boolean action(final Jedis jedis) {
                final Long count = jedis.lrem(key, 1L, value);
                if (count == 1L) {
                    return true;
                }
                return false;
            }
        });
    }
    
    public Boolean lremAll(final String key, final String value) {
        return this.execute((JedisAction<Boolean>)new JedisAction<Boolean>() {
            @Override
            public Boolean action(final Jedis jedis) {
                final Long count = jedis.lrem(key, 0L, value);
                if (count > 0L) {
                    return true;
                }
                return false;
            }
        });
    }
    
    public Boolean sadd(final String key, final String member) {
        return this.execute((JedisAction<Boolean>)new JedisAction<Boolean>() {
            @Override
            public Boolean action(final Jedis jedis) {
                return jedis.sadd(key, new String[] { member }) == 1L;
            }
        });
    }
    
    public Set<String> smembers(final String key) {
        return this.execute((JedisAction<Set<String>>)new JedisAction<Set<String>>() {
            @Override
            public Set<String> action(final Jedis jedis) {
                return (Set<String>)jedis.smembers(key);
            }
        });
    }
    
    public Boolean zadd(final String key, final double score, final String member) {
        return this.execute((JedisAction<Boolean>)new JedisAction<Boolean>() {
            @Override
            public Boolean action(final Jedis jedis) {
                return jedis.zadd(key, score, member) == 1L;
            }
        });
    }
    
    public Double zscore(final String key, final String member) {
        return this.execute((JedisAction<Double>)new JedisAction<Double>() {
            @Override
            public Double action(final Jedis jedis) {
                return jedis.zscore(key, member);
            }
        });
    }
    
    public Long zrank(final String key, final String member) {
        return this.execute((JedisAction<Long>)new JedisAction<Long>() {
            @Override
            public Long action(final Jedis jedis) {
                return jedis.zrank(key, member);
            }
        });
    }
    
    public Long zrevrank(final String key, final String member) {
        return this.execute((JedisAction<Long>)new JedisAction<Long>() {
            @Override
            public Long action(final Jedis jedis) {
                return jedis.zrevrank(key, member);
            }
        });
    }
    
    public Long zcount(final String key, final double min, final double max) {
        return this.execute((JedisAction<Long>)new JedisAction<Long>() {
            @Override
            public Long action(final Jedis jedis) {
                return jedis.zcount(key, min, max);
            }
        });
    }
    
    public Set<String> zrange(final String key, final int start, final int end) {
        return this.execute((JedisAction<Set<String>>)new JedisAction<Set<String>>() {
            @Override
            public Set<String> action(final Jedis jedis) {
                return (Set<String>)jedis.zrange(key, (long)start, (long)end);
            }
        });
    }
    
    public Set<Tuple> zrangeWithScores(final String key, final int start, final int end) {
        return this.execute((JedisAction<Set<Tuple>>)new JedisAction<Set<Tuple>>() {
            @Override
            public Set<Tuple> action(final Jedis jedis) {
                return (Set<Tuple>)jedis.zrangeWithScores(key, (long)start, (long)end);
            }
        });
    }
    
    public Set<String> zrevrange(final String key, final int start, final int end) {
        return this.execute((JedisAction<Set<String>>)new JedisAction<Set<String>>() {
            @Override
            public Set<String> action(final Jedis jedis) {
                return (Set<String>)jedis.zrevrange(key, (long)start, (long)end);
            }
        });
    }
    
    public Set<Tuple> zrevrangeWithScores(final String key, final int start, final int end) {
        return this.execute((JedisAction<Set<Tuple>>)new JedisAction<Set<Tuple>>() {
            @Override
            public Set<Tuple> action(final Jedis jedis) {
                return (Set<Tuple>)jedis.zrevrangeWithScores(key, (long)start, (long)end);
            }
        });
    }
    
    public Set<String> zrangeByScore(final String key, final double min, final double max) {
        return this.execute((JedisAction<Set<String>>)new JedisAction<Set<String>>() {
            @Override
            public Set<String> action(final Jedis jedis) {
                return (Set<String>)jedis.zrangeByScore(key, min, max);
            }
        });
    }
    
    public Set<Tuple> zrangeByScoreWithScores(final String key, final double min, final double max) {
        return this.execute((JedisAction<Set<Tuple>>)new JedisAction<Set<Tuple>>() {
            @Override
            public Set<Tuple> action(final Jedis jedis) {
                return (Set<Tuple>)jedis.zrangeByScoreWithScores(key, min, max);
            }
        });
    }
    
    public Set<String> zrevrangeByScore(final String key, final double max, final double min) {
        return this.execute((JedisAction<Set<String>>)new JedisAction<Set<String>>() {
            @Override
            public Set<String> action(final Jedis jedis) {
                return (Set<String>)jedis.zrevrangeByScore(key, max, min);
            }
        });
    }
    
    public Set<Tuple> zrevrangeByScoreWithScores(final String key, final double max, final double min) {
        return this.execute((JedisAction<Set<Tuple>>)new JedisAction<Set<Tuple>>() {
            @Override
            public Set<Tuple> action(final Jedis jedis) {
                return (Set<Tuple>)jedis.zrevrangeByScoreWithScores(key, max, min);
            }
        });
    }
    
    public Boolean zrem(final String key, final String member) {
        return this.execute((JedisAction<Boolean>)new JedisAction<Boolean>() {
            @Override
            public Boolean action(final Jedis jedis) {
                return jedis.zrem(key, new String[] { member }) == 1L;
            }
        });
    }
    
    public Long zremByScore(final String key, final double start, final double end) {
        return this.execute((JedisAction<Long>)new JedisAction<Long>() {
            @Override
            public Long action(final Jedis jedis) {
                return jedis.zremrangeByScore(key, start, end);
            }
        });
    }
    
    public Long zremByRank(final String key, final long start, final long end) {
        return this.execute((JedisAction<Long>)new JedisAction<Long>() {
            @Override
            public Long action(final Jedis jedis) {
                return jedis.zremrangeByRank(key, start, end);
            }
        });
    }
    
    public Long zcard(final String key) {
        return this.execute((JedisAction<Long>)new JedisAction<Long>() {
            @Override
            public Long action(final Jedis jedis) {
                return jedis.zcard(key);
            }
        });
    }
    
    public interface JedisAction<T>
    {
        T action(final Jedis p0);
    }
    
    public interface JedisActionNoResult
    {
        void action(final Jedis p0);
    }
    
    public interface PipelineAction
    {
        List<Object> action(final Pipeline p0);
    }
    
    public interface PipelineActionNoResult
    {
        void action(final Pipeline p0);
    }
}
