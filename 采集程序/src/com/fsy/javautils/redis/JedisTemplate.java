/*     */ package com.fsy.javautils.redis;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import com.fsy.javautils.redis.pool.JedisPool;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import redis.clients.jedis.Jedis;
/*     */ import redis.clients.jedis.Pipeline;
/*     */ import redis.clients.jedis.Tuple;
/*     */ import redis.clients.jedis.exceptions.JedisConnectionException;
/*     */ import redis.clients.jedis.exceptions.JedisDataException;
/*     */ import redis.clients.jedis.exceptions.JedisException;
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
/*     */ 
/*     */ public class JedisTemplate
/*     */ {
/*  31 */   private static Logger logger = LoggerFactory.getLogger(JedisTemplate.class);
/*     */   private JedisPool jedisPool;
/*     */   
/*     */   public JedisTemplate(JedisPool jedisPool)
/*     */   {
/*  36 */     this.jedisPool = jedisPool;
/*     */   }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public <T> T execute(JedisAction<T> jedisAction)
/*     */     throws JedisException
/*     */   {
/*  71 */     Jedis jedis = null;
/*  72 */     boolean broken = false;
/*     */     try {
/*  74 */       jedis = (Jedis)this.jedisPool.getResource();
/*  75 */       return (T)jedisAction.action(jedis);
/*     */     } catch (JedisException e) {
/*  77 */       broken = handleJedisException(e);
/*  78 */       throw e;
/*     */     } finally {
/*  80 */       closeResource(jedis, broken);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void execute(JedisActionNoResult jedisAction)
/*     */     throws JedisException
/*     */   {
/*  88 */     Jedis jedis = null;
/*  89 */     boolean broken = false;
/*     */     try {
/*  91 */       jedis = (Jedis)this.jedisPool.getResource();
/*  92 */       jedisAction.action(jedis);
/*     */     } catch (JedisException e) {
/*  94 */       broken = handleJedisException(e);
/*  95 */       throw e;
/*     */     } finally {
/*  97 */       closeResource(jedis, broken);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public List<Object> execute(PipelineAction pipelineAction)
/*     */     throws JedisException
/*     */   {
/* 105 */     Jedis jedis = null;
/* 106 */     boolean broken = false;
/*     */     try {
/* 108 */       jedis = (Jedis)this.jedisPool.getResource();
/* 109 */       Pipeline pipeline = jedis.pipelined();
/* 110 */       pipelineAction.action(pipeline);
/* 111 */       return pipeline.syncAndReturnAll();
/*     */     } catch (JedisException e) {
/* 113 */       broken = handleJedisException(e);
/* 114 */       throw e;
/*     */     } finally {
/* 116 */       closeResource(jedis, broken);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void execute(PipelineActionNoResult pipelineAction)
/*     */     throws JedisException
/*     */   {
/* 124 */     Jedis jedis = null;
/* 125 */     boolean broken = false;
/*     */     try {
/* 127 */       jedis = (Jedis)this.jedisPool.getResource();
/* 128 */       Pipeline pipeline = jedis.pipelined();
/* 129 */       pipelineAction.action(pipeline);
/* 130 */       pipeline.sync();
/*     */     } catch (JedisException e) {
/* 132 */       broken = handleJedisException(e);
/* 133 */       throw e;
/*     */     } finally {
/* 135 */       closeResource(jedis, broken);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public JedisPool getJedisPool()
/*     */   {
/* 143 */     return this.jedisPool;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected boolean handleJedisException(JedisException jedisException)
/*     */   {
/* 150 */     if ((jedisException instanceof JedisConnectionException)) {
/* 151 */       logger.error("Redis connection " + this.jedisPool.getAddress() + " lost.", jedisException);
/* 152 */     } else if ((jedisException instanceof JedisDataException)) {
/* 153 */       if ((jedisException.getMessage() != null) && (jedisException.getMessage().indexOf("READONLY") != -1)) {
/* 154 */         logger.error("Redis connection " + this.jedisPool.getAddress() + " are read-only slave.", jedisException);
/*     */       }
/*     */       else {
/* 157 */         return false;
/*     */       }
/*     */     } else {
/* 160 */       logger.error("Jedis exception happen.", jedisException);
/*     */     }
/* 162 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */   protected void closeResource(Jedis jedis, boolean conectionBroken)
/*     */   {
/*     */     try
/*     */     {
/* 170 */       if (conectionBroken) {
/* 171 */         this.jedisPool.returnBrokenResource(jedis);
/*     */       } else {
/* 173 */         this.jedisPool.returnResource(jedis);
/*     */       }
/*     */     } catch (Exception e) {
/* 176 */       logger.error("return back jedis failed, will fore close the jedis.", e);
/* 177 */       JedisUtils.destroyJedis(jedis);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Boolean del(final String... keys)
/*     */   {
/* 191 */     return (Boolean)execute(new JedisAction()
/*     */     {
/*     */       public Boolean action(Jedis jedis)
/*     */       {
/* 195 */         return Boolean.valueOf(jedis.del(keys).longValue() == keys.length);
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */   public void flushDB() {
/* 201 */     execute(new JedisActionNoResult()
/*     */     {
/*     */       public void action(Jedis jedis)
/*     */       {
/* 205 */         jedis.flushDB();
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String get(final String key)
/*     */   {
/* 218 */     return (String)execute(new JedisAction()
/*     */     {
/*     */       public String action(Jedis jedis)
/*     */       {
/* 222 */         return jedis.get(key);
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */   public Set<String> keys(final String key) {
/* 228 */     return (Set)execute(new JedisAction()
/*     */     {
/*     */       public Set<String> action(Jedis jedis)
/*     */       {
/* 232 */         return jedis.keys(key);
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Long getAsLong(String key)
/*     */   {
/* 241 */     String result = get(key);
/* 242 */     return result != null ? Long.valueOf(result) : null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Integer getAsInt(String key)
/*     */   {
/* 249 */     String result = get(key);
/* 250 */     return result != null ? Integer.valueOf(result) : null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Double getAsDouble(String key)
/*     */   {
/* 257 */     String result = get(key);
/* 258 */     return result != null ? Double.valueOf(result) : null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public List<String> mget(final String... keys)
/*     */   {
/* 267 */     return (List)execute(new JedisAction()
/*     */     {
/*     */       public List<String> action(Jedis jedis)
/*     */       {
/* 271 */         return jedis.mget(keys);
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void set(final String key, final String value)
/*     */   {
/* 281 */     execute(new JedisActionNoResult()
/*     */     {
/*     */       public void action(Jedis jedis)
/*     */       {
/* 285 */         jedis.set(key, value);
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */   public void expire(final String key, final int seconds) {
/* 291 */     execute(new JedisActionNoResult()
/*     */     {
/*     */       public void action(Jedis jedis)
/*     */       {
/* 295 */         jedis.expire(key, seconds);
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setex(final String key, final String value, final int seconds)
/*     */   {
/* 306 */     execute(new JedisActionNoResult()
/*     */     {
/*     */       public void action(Jedis jedis)
/*     */       {
/* 310 */         jedis.setex(key, seconds, value);
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Boolean setnx(final String key, final String value)
/*     */   {
/* 323 */     return (Boolean)execute(new JedisAction()
/*     */     {
/*     */       public Boolean action(Jedis jedis)
/*     */       {
/* 327 */         return Boolean.valueOf(jedis.setnx(key, value).longValue() == 1L);
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Boolean setnxex(final String key, final String value, final int seconds)
/*     */   {
/* 338 */     return (Boolean)execute(new JedisAction()
/*     */     {
/*     */       public Boolean action(Jedis jedis)
/*     */       {
/* 342 */         String result = jedis.set(key, value, "NX", "EX", seconds);
/* 343 */         return Boolean.valueOf(JedisUtils.isStatusOk(result));
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getSet(final String key, final String value)
/*     */   {
/* 354 */     return (String)execute(new JedisAction()
/*     */     {
/*     */       public String action(Jedis jedis)
/*     */       {
/* 358 */         return jedis.getSet(key, value);
/*     */       }
/*     */     });
/*     */   }
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
/*     */   public Long incr(final String key)
/*     */   {
/* 377 */     return (Long)execute(new JedisAction()
/*     */     {
/*     */       public Long action(Jedis jedis) {
/* 380 */         return jedis.incr(key);
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */   public Long incrBy(final String key, final long increment) {
/* 386 */     return (Long)execute(new JedisAction()
/*     */     {
/*     */       public Long action(Jedis jedis) {
/* 389 */         return jedis.incrBy(key, increment);
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */   public Double incrByFloat(final String key, final double increment) {
/* 395 */     return (Double)execute(new JedisAction()
/*     */     {
/*     */       public Double action(Jedis jedis) {
/* 398 */         return jedis.incrByFloat(key, increment);
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Long decr(final String key)
/*     */   {
/* 409 */     return (Long)execute(new JedisAction()
/*     */     {
/*     */       public Long action(Jedis jedis) {
/* 412 */         return jedis.decr(key);
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */   public Long decrBy(final String key, final long decrement) {
/* 418 */     return (Long)execute(new JedisAction()
/*     */     {
/*     */       public Long action(Jedis jedis) {
/* 421 */         return jedis.decrBy(key, decrement);
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String hget(final String key, final String fieldName)
/*     */   {
/* 434 */     return (String)execute(new JedisAction()
/*     */     {
/*     */       public String action(Jedis jedis) {
/* 437 */         return jedis.hget(key, fieldName);
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */   public List<String> hmget(final String key, final String... fieldsNames) {
/* 443 */     return (List)execute(new JedisAction()
/*     */     {
/*     */       public List<String> action(Jedis jedis) {
/* 446 */         return jedis.hmget(key, fieldsNames);
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */   public Map<String, String> hgetAll(final String key) {
/* 452 */     return (Map)execute(new JedisAction()
/*     */     {
/*     */       public Map<String, String> action(Jedis jedis) {
/* 455 */         return jedis.hgetAll(key);
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */   public void hset(final String key, final String fieldName, final String value) {
/* 461 */     execute(new JedisActionNoResult()
/*     */     {
/*     */       public void action(Jedis jedis)
/*     */       {
/* 465 */         jedis.hset(key, fieldName, value);
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */   public void hmset(final String key, final Map<String, String> map) {
/* 471 */     execute(new JedisActionNoResult()
/*     */     {
/*     */       public void action(Jedis jedis)
/*     */       {
/* 475 */         jedis.hmset(key, map);
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */   public Boolean hsetnx(final String key, final String fieldName, final String value)
/*     */   {
/* 482 */     return (Boolean)execute(new JedisAction()
/*     */     {
/*     */       public Boolean action(Jedis jedis)
/*     */       {
/* 486 */         return Boolean.valueOf(jedis.hsetnx(key, fieldName, value).longValue() == 1L);
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */   public Long hincrBy(final String key, final String fieldName, final long increment) {
/* 492 */     return (Long)execute(new JedisAction()
/*     */     {
/*     */       public Long action(Jedis jedis) {
/* 495 */         return jedis.hincrBy(key, fieldName, increment);
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */   public Double hincrByFloat(final String key, final String fieldName, final double increment) {
/* 501 */     return (Double)execute(new JedisAction()
/*     */     {
/*     */       public Double action(Jedis jedis) {
/* 504 */         return jedis.hincrByFloat(key, fieldName, increment);
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */   public Long hdel(final String key, final String... fieldsNames) {
/* 510 */     return (Long)execute(new JedisAction()
/*     */     {
/*     */       public Long action(Jedis jedis) {
/* 513 */         return jedis.hdel(key, fieldsNames);
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */   public Boolean hexists(final String key, final String fieldName) {
/* 519 */     return (Boolean)execute(new JedisAction()
/*     */     {
/*     */       public Boolean action(Jedis jedis) {
/* 522 */         return jedis.hexists(key, fieldName);
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */   public Set<String> hkeys(final String key) {
/* 528 */     return (Set)execute(new JedisAction()
/*     */     {
/*     */       public Set<String> action(Jedis jedis) {
/* 531 */         return jedis.hkeys(key);
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */   public List<String> hvals(final String key) {
/* 537 */     return (List)execute(new JedisAction()
/*     */     {
/*     */       public List<String> action(Jedis jedis) {
/* 540 */         return jedis.hvals(key);
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */   public Long hlen(final String key) {
/* 546 */     return (Long)execute(new JedisAction()
/*     */     {
/*     */       public Long action(Jedis jedis) {
/* 549 */         return jedis.hlen(key);
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */ 
/*     */   public Long lpush(final String key, final String... values)
/*     */   {
/* 557 */     return (Long)execute(new JedisAction()
/*     */     {
/*     */       public Long action(Jedis jedis) {
/* 560 */         return jedis.lpush(key, values);
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */   public Long rpush(final String key, final String... values) {
/* 566 */     return (Long)execute(new JedisAction()
/*     */     {
/*     */       public Long action(Jedis jedis) {
/* 569 */         return jedis.rpush(key, values);
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */   public String rpop(final String key) {
/* 575 */     return (String)execute(new JedisAction()
/*     */     {
/*     */       public String action(Jedis jedis)
/*     */       {
/* 579 */         return jedis.rpop(key);
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */   public String brpop(final String key) {
/* 585 */     return (String)execute(new JedisAction()
/*     */     {
/*     */       public String action(Jedis jedis)
/*     */       {
/* 589 */         List<String> nameValuePair = jedis.brpop(key);
/* 590 */         if (nameValuePair != null) {
/* 591 */           return (String)nameValuePair.get(1);
/*     */         }
/* 593 */         return null;
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */   public String brpop(final int timeout, final String key)
/*     */   {
/* 600 */     return (String)execute(new JedisAction()
/*     */     {
/*     */       public String action(Jedis jedis)
/*     */       {
/* 604 */         List<String> nameValuePair = jedis.brpop(timeout, key);
/* 605 */         if (nameValuePair != null) {
/* 606 */           return (String)nameValuePair.get(1);
/*     */         }
/* 608 */         return null;
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String rpoplpush(final String sourceKey, final String destinationKey)
/*     */   {
/* 618 */     return (String)execute(new JedisAction()
/*     */     {
/*     */       public String action(Jedis jedis)
/*     */       {
/* 622 */         return jedis.rpoplpush(sourceKey, destinationKey);
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String brpoplpush(final String source, final String destination, final int timeout)
/*     */   {
/* 631 */     return (String)execute(new JedisAction()
/*     */     {
/*     */       public String action(Jedis jedis)
/*     */       {
/* 635 */         return jedis.brpoplpush(source, destination, timeout);
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */   public Long llen(final String key) {
/* 641 */     return (Long)execute(new JedisAction()
/*     */     {
/*     */       public Long action(Jedis jedis)
/*     */       {
/* 645 */         return jedis.llen(key);
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */   public String lindex(final String key, final long index) {
/* 651 */     return (String)execute(new JedisAction()
/*     */     {
/*     */       public String action(Jedis jedis)
/*     */       {
/* 655 */         return jedis.lindex(key, index);
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */   public List<String> lrange(final String key, final int start, final int end) {
/* 661 */     return (List)execute(new JedisAction()
/*     */     {
/*     */       public List<String> action(Jedis jedis)
/*     */       {
/* 665 */         return jedis.lrange(key, start, end);
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */   public void ltrim(final String key, final int start, final int end) {
/* 671 */     execute(new JedisActionNoResult()
/*     */     {
/*     */       public void action(Jedis jedis) {
/* 674 */         jedis.ltrim(key, start, end);
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */   public void ltrimFromLeft(final String key, final int size) {
/* 680 */     execute(new JedisActionNoResult()
/*     */     {
/*     */       public void action(Jedis jedis) {
/* 683 */         jedis.ltrim(key, 0L, size - 1);
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */   public Boolean lremFirst(final String key, final String value) {
/* 689 */     return (Boolean)execute(new JedisAction()
/*     */     {
/*     */       public Boolean action(Jedis jedis) {
/* 692 */         Long count = jedis.lrem(key, 1L, value);
/* 693 */         return Boolean.valueOf(count.longValue() == 1L);
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */   public Boolean lremAll(final String key, final String value) {
/* 699 */     return (Boolean)execute(new JedisAction()
/*     */     {
/*     */       public Boolean action(Jedis jedis) {
/* 702 */         Long count = jedis.lrem(key, 0L, value);
/* 703 */         return Boolean.valueOf(count.longValue() > 0L);
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */   public Boolean sadd(final String key, final String member)
/*     */   {
/* 710 */     return (Boolean)execute(new JedisAction()
/*     */     {
/*     */       public Boolean action(Jedis jedis)
/*     */       {
/* 714 */         return Boolean.valueOf(jedis.sadd(key, new String[] { member }).longValue() == 1L);
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */   public Set<String> smembers(final String key) {
/* 720 */     return (Set)execute(new JedisAction()
/*     */     {
/*     */       public Set<String> action(Jedis jedis)
/*     */       {
/* 724 */         return jedis.smembers(key);
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Boolean zadd(final String key, final double score, String member)
/*     */   {
/* 734 */     return (Boolean)execute(new JedisAction()
/*     */     {
/*     */       public Boolean action(Jedis jedis)
/*     */       {
///* 738 */         return Boolean.valueOf(jedis.zadd(key, score, this.val$member).longValue() == 1L);
    return Boolean.valueOf(jedis.zadd(key, score, member).longValue() == 1L);
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */   public Double zscore(final String key, final String member) {
/* 744 */     return (Double)execute(new JedisAction()
/*     */     {
/*     */       public Double action(Jedis jedis)
/*     */       {
/* 748 */         return jedis.zscore(key, member);
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */   public Long zrank(final String key, final String member) {
/* 754 */     return (Long)execute(new JedisAction()
/*     */     {
/*     */       public Long action(Jedis jedis)
/*     */       {
/* 758 */         return jedis.zrank(key, member);
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */   public Long zrevrank(final String key, final String member) {
/* 764 */     return (Long)execute(new JedisAction()
/*     */     {
/*     */       public Long action(Jedis jedis)
/*     */       {
/* 768 */         return jedis.zrevrank(key, member);
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */   public Long zcount(final String key, final double min, double max) {
/* 774 */     return (Long)execute(new JedisAction()
/*     */     {
/*     */       public Long action(Jedis jedis)
/*     */       {
/* 778 */         return jedis.zcount(key, min, max);
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */   public Set<String> zrange(final String key, final int start, final int end) {
/* 784 */     return (Set)execute(new JedisAction()
/*     */     {
/*     */       public Set<String> action(Jedis jedis)
/*     */       {
/* 788 */         return jedis.zrange(key, start, end);
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */   public Set<Tuple> zrangeWithScores(final String key, final int start, final int end) {
/* 794 */     return (Set)execute(new JedisAction()
/*     */     {
/*     */       public Set<Tuple> action(Jedis jedis)
/*     */       {
/* 798 */         return jedis.zrangeWithScores(key, start, end);
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */   public Set<String> zrevrange(final String key, final int start, final int end) {
/* 804 */     return (Set)execute(new JedisAction()
/*     */     {
/*     */       public Set<String> action(Jedis jedis)
/*     */       {
/* 808 */         return jedis.zrevrange(key, start, end);
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */   public Set<Tuple> zrevrangeWithScores(final String key, final int start, final int end) {
/* 814 */     return (Set)execute(new JedisAction()
/*     */     {
/*     */       public Set<Tuple> action(Jedis jedis)
/*     */       {
/* 818 */         return jedis.zrevrangeWithScores(key, start, end);
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */   public Set<String> zrangeByScore(final String key, final double min, double max) {
/* 824 */     return (Set)execute(new JedisAction()
/*     */     {
/*     */       public Set<String> action(Jedis jedis)
/*     */       {
/* 828 */         return jedis.zrangeByScore(key, min, max);
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */   public Set<Tuple> zrangeByScoreWithScores(final String key, final double min, double max) {
/* 834 */     return (Set)execute(new JedisAction()
/*     */     {
/*     */       public Set<Tuple> action(Jedis jedis)
/*     */       {
/* 838 */         return jedis.zrangeByScoreWithScores(key, min, max);
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */   public Set<String> zrevrangeByScore(final String key, final double max, double min) {
/* 844 */     return (Set)execute(new JedisAction()
/*     */     {
/*     */       public Set<String> action(Jedis jedis)
/*     */       {
/* 848 */         return jedis.zrevrangeByScore(key, max, min);
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */   public Set<Tuple> zrevrangeByScoreWithScores(final String key, final double max, double min) {
/* 854 */     return (Set)execute(new JedisAction()
/*     */     {
/*     */       public Set<Tuple> action(Jedis jedis)
/*     */       {
/* 858 */         return jedis.zrevrangeByScoreWithScores(key, max, min);
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */   public Boolean zrem(final String key, final String member) {
/* 864 */     return (Boolean)execute(new JedisAction()
/*     */     {
/*     */       public Boolean action(Jedis jedis)
/*     */       {
/* 868 */         return Boolean.valueOf(jedis.zrem(key, new String[] { member }).longValue() == 1L);
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */   public Long zremByScore(final String key, final double start, double end) {
/* 874 */     return (Long)execute(new JedisAction()
/*     */     {
/*     */       public Long action(Jedis jedis)
/*     */       {
    return jedis.zremrangeByScore(key, start, end);
///* 878 */         return jedis.zremrangeByScore(key, start, this.val$end);
/*     */       }

/*     */     });
/*     */   }
/*     */   
/*     */   public Long zremByRank(final String key, final long start, long end) {
/* 884 */     return (Long)execute(new JedisAction()
/*     */     {
/*     */       public Long action(Jedis jedis)
/*     */       {
///* 888 */         return jedis.zremrangeByRank(key, start, this.val$end);
    return jedis.zremrangeByRank(key, start, end);
/*     */       }

/*     */     });
/*     */   }
/*     */   
/*     */   public Long zcard(final String key) {
/* 894 */     return (Long)execute(new JedisAction()
/*     */     {
/*     */       public Long action(Jedis jedis)
/*     */       {
/* 898 */         return jedis.zcard(key);
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */   public static abstract interface PipelineActionNoResult
/*     */   {
/*     */     public abstract void action(Pipeline paramPipeline);
/*     */   }
/*     */   
/*     */   public static abstract interface PipelineAction
/*     */   {
/*     */     public abstract List<Object> action(Pipeline paramPipeline);
/*     */   }
/*     */   
/*     */   public static abstract interface JedisActionNoResult
/*     */   {
/*     */     public abstract void action(Jedis paramJedis);
/*     */   }
/*     */   
/*     */   public static abstract interface JedisAction<T>
/*     */   {
/*     */     public abstract T action(Jedis paramJedis);
/*     */   }
/*     */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryCapture/lotteryCaptureRepair.jar!/javautils/redis/JedisTemplate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */