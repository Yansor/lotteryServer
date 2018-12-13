/*     */ package javautils.redis;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.SortedMap;
/*     */ import java.util.TreeMap;
/*     */ import javautils.redis.pool.JedisPool;
/*     */ import redis.clients.jedis.Tuple;
/*     */ import redis.clients.jedis.exceptions.JedisException;
/*     */ import redis.clients.util.Hashing;
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
/*     */ public class JedisShardedTemplate
/*     */ {
/*  29 */   private final Hashing algo = Hashing.MURMUR_HASH;
/*  30 */   private TreeMap<Long, JedisTemplate> nodes = new TreeMap();
/*  31 */   private JedisTemplate singleTemplate = null;
/*     */   
/*     */   public JedisShardedTemplate(JedisPool... jedisPools) {
/*  34 */     if (jedisPools.length == 1) {
/*  35 */       this.singleTemplate = new JedisTemplate(jedisPools[0]);
/*     */     } else {
/*  37 */       initNodes(jedisPools);
/*     */     }
/*     */   }
/*     */   
/*     */   public JedisShardedTemplate(List<JedisPool> jedisPools) {
/*  42 */     this((JedisPool[])jedisPools.toArray(new JedisPool[jedisPools.size()]));
/*     */   }
/*     */   
/*     */   private void initNodes(JedisPool... jedisPools) {
/*  46 */     for (int i = 0; i != jedisPools.length; i++)
/*     */     {
/*  48 */       for (int n = 0; n < 128; n++) {
/*  49 */         JedisPool jedisPool = jedisPools[i];
/*  50 */         this.nodes.put(Long.valueOf(this.algo.hash("SHARD-" + i + "-NODE-" + n)), new JedisTemplate(jedisPool));
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public JedisTemplate getShard(String key)
/*     */   {
/*  59 */     if (this.singleTemplate != null) {
/*  60 */       return this.singleTemplate;
/*     */     }
/*     */     
/*  63 */     SortedMap<Long, JedisTemplate> tail = this.nodes.tailMap(Long.valueOf(this.algo.hash(key)));
/*  64 */     if (tail.isEmpty())
/*     */     {
/*  66 */       return (JedisTemplate)this.nodes.get(this.nodes.firstKey());
/*     */     }
/*  68 */     return (JedisTemplate)tail.get(tail.firstKey());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public <T> T execute(String key, JedisTemplate.JedisAction<T> jedisAction)
/*     */     throws JedisException
/*     */   {
/*  77 */     JedisTemplate jedisTemplate = getShard(key);
/*  78 */     return (T)jedisTemplate.execute(jedisAction);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void execute(String key, JedisTemplate.JedisActionNoResult jedisAction)
/*     */     throws JedisException
/*     */   {
/*  87 */     JedisTemplate jedisTemplate = getShard(key);
/*  88 */     jedisTemplate.execute(jedisAction);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public List<Object> execute(String key, JedisTemplate.PipelineAction pipelineAction)
/*     */     throws JedisException
/*     */   {
/*  97 */     JedisTemplate jedisTemplate = getShard(key);
/*  98 */     return jedisTemplate.execute(pipelineAction);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void execute(String key, JedisTemplate.PipelineActionNoResult pipelineAction)
/*     */     throws JedisException
/*     */   {
/* 107 */     JedisTemplate jedisTemplate = getShard(key);
/* 108 */     jedisTemplate.execute(pipelineAction);
/*     */   }
/*     */   
/*     */ 
/*     */   public Boolean del(String key)
/*     */   {
/* 114 */     JedisTemplate jedisTemplate = getShard(key);
/* 115 */     return jedisTemplate.del(new String[] { key });
/*     */   }
/*     */   
/*     */   public Boolean del(String shardingKey, String key) {
/* 119 */     JedisTemplate jedisTemplate = getShard(shardingKey);
/* 120 */     return jedisTemplate.del(new String[] { key });
/*     */   }
/*     */   
/*     */ 
/*     */   public String get(String key)
/*     */   {
/* 126 */     JedisTemplate jedisTemplate = getShard(key);
/* 127 */     return jedisTemplate.get(key);
/*     */   }
/*     */   
/*     */   public String get(String shardingKey, String key) {
/* 131 */     JedisTemplate jedisTemplate = getShard(shardingKey);
/* 132 */     return jedisTemplate.get(key);
/*     */   }
/*     */   
/*     */   public Long getAsLong(String key) {
/* 136 */     JedisTemplate jedisTemplate = getShard(key);
/* 137 */     return jedisTemplate.getAsLong(key);
/*     */   }
/*     */   
/*     */   public Long getAsLong(String shardingKey, String key) {
/* 141 */     JedisTemplate jedisTemplate = getShard(shardingKey);
/* 142 */     return jedisTemplate.getAsLong(key);
/*     */   }
/*     */   
/*     */   public Integer getAsInt(String key) {
/* 146 */     JedisTemplate jedisTemplate = getShard(key);
/* 147 */     return jedisTemplate.getAsInt(key);
/*     */   }
/*     */   
/*     */   public Integer getAsInt(String shardingKey, String key) {
/* 151 */     JedisTemplate jedisTemplate = getShard(shardingKey);
/* 152 */     return jedisTemplate.getAsInt(key);
/*     */   }
/*     */   
/*     */   public void set(String key, String value) {
/* 156 */     JedisTemplate jedisTemplate = getShard(key);
/* 157 */     jedisTemplate.set(key, value);
/*     */   }
/*     */   
/*     */   public void set(String shardingKey, String key, String value) {
/* 161 */     JedisTemplate jedisTemplate = getShard(shardingKey);
/* 162 */     jedisTemplate.set(key, value);
/*     */   }
/*     */   
/*     */   public void setex(String key, String value, int seconds) {
/* 166 */     JedisTemplate jedisTemplate = getShard(key);
/* 167 */     jedisTemplate.setex(key, value, seconds);
/*     */   }
/*     */   
/*     */   public void setex(String shardingKey, String key, String value, int seconds) {
/* 171 */     JedisTemplate jedisTemplate = getShard(shardingKey);
/* 172 */     jedisTemplate.setex(key, value, seconds);
/*     */   }
/*     */   
/*     */   public Boolean setnx(String key, String value) {
/* 176 */     JedisTemplate jedisTemplate = getShard(key);
/* 177 */     return jedisTemplate.setnx(key, value);
/*     */   }
/*     */   
/*     */   public Boolean setnx(String shardingKey, String key, String value) {
/* 181 */     JedisTemplate jedisTemplate = getShard(shardingKey);
/* 182 */     return jedisTemplate.setnx(key, value);
/*     */   }
/*     */   
/*     */   public Boolean setnxex(String key, String value, int seconds) {
/* 186 */     JedisTemplate jedisTemplate = getShard(key);
/* 187 */     return jedisTemplate.setnxex(key, value, seconds);
/*     */   }
/*     */   
/*     */   public Boolean setnxex(String shardingKey, String key, String value, int seconds) {
/* 191 */     JedisTemplate jedisTemplate = getShard(shardingKey);
/* 192 */     return jedisTemplate.setnxex(key, value, seconds);
/*     */   }
/*     */   
/*     */   public String getSet(String key, String value) {
/* 196 */     JedisTemplate jedisTemplate = getShard(key);
/* 197 */     return jedisTemplate.getSet(key, value);
/*     */   }
/*     */   
/*     */   public String getSet(String shardingKey, String key, String value) {
/* 201 */     JedisTemplate jedisTemplate = getShard(shardingKey);
/* 202 */     return jedisTemplate.getSet(key, value);
/*     */   }
/*     */   
/*     */   public Long incr(String key) {
/* 206 */     JedisTemplate jedisTemplate = getShard(key);
/* 207 */     return jedisTemplate.incr(key);
/*     */   }
/*     */   
/*     */   public Long incr(String shardingKey, String key) {
/* 211 */     JedisTemplate jedisTemplate = getShard(shardingKey);
/* 212 */     return jedisTemplate.incr(key);
/*     */   }
/*     */   
/*     */   public Long incrBy(String key, Long increment) {
/* 216 */     JedisTemplate jedisTemplate = getShard(key);
/* 217 */     return jedisTemplate.incrBy(key, increment.longValue());
/*     */   }
/*     */   
/*     */   public Long incrBy(String shardingKey, String key, Long increment) {
/* 221 */     JedisTemplate jedisTemplate = getShard(shardingKey);
/* 222 */     return jedisTemplate.incrBy(key, increment.longValue());
/*     */   }
/*     */   
/*     */   public Double incrByFloat(String key, double increment) {
/* 226 */     JedisTemplate jedisTemplate = getShard(key);
/* 227 */     return jedisTemplate.incrByFloat(key, increment);
/*     */   }
/*     */   
/*     */   public Double incrByFloat(String shardingKey, String key, double increment) {
/* 231 */     JedisTemplate jedisTemplate = getShard(shardingKey);
/* 232 */     return jedisTemplate.incrByFloat(key, increment);
/*     */   }
/*     */   
/*     */   public Long decr(String key) {
/* 236 */     JedisTemplate jedisTemplate = getShard(key);
/* 237 */     return jedisTemplate.decr(key);
/*     */   }
/*     */   
/*     */   public Long decr(String shardingKey, String key) {
/* 241 */     JedisTemplate jedisTemplate = getShard(shardingKey);
/* 242 */     return jedisTemplate.decr(key);
/*     */   }
/*     */   
/*     */   public Long decrBy(String key, Long decrement) {
/* 246 */     JedisTemplate jedisTemplate = getShard(key);
/* 247 */     return jedisTemplate.decrBy(key, decrement.longValue());
/*     */   }
/*     */   
/*     */   public Long decrBy(String shardingKey, String key, Long decrement) {
/* 251 */     JedisTemplate jedisTemplate = getShard(shardingKey);
/* 252 */     return jedisTemplate.decrBy(key, decrement.longValue());
/*     */   }
/*     */   
/*     */ 
/*     */   public String hget(String key, String field)
/*     */   {
/* 258 */     JedisTemplate jedisTemplate = getShard(key);
/* 259 */     return jedisTemplate.hget(key, field);
/*     */   }
/*     */   
/*     */   public String hget(String shardingKey, String key, String field) {
/* 263 */     JedisTemplate jedisTemplate = getShard(shardingKey);
/* 264 */     return jedisTemplate.hget(key, field);
/*     */   }
/*     */   
/*     */   public List<String> hmget(String key, String field) {
/* 268 */     JedisTemplate jedisTemplate = getShard(key);
/* 269 */     return jedisTemplate.hmget(key, new String[] { field });
/*     */   }
/*     */   
/*     */   public List<String> hmget(String key, String[] fields) {
/* 273 */     JedisTemplate jedisTemplate = getShard(key);
/* 274 */     return jedisTemplate.hmget(key, fields);
/*     */   }
/*     */   
/*     */   public List<String> hmget(String shardingKey, String key, String field) {
/* 278 */     JedisTemplate jedisTemplate = getShard(shardingKey);
/* 279 */     return jedisTemplate.hmget(key, new String[] { field });
/*     */   }
/*     */   
/*     */   public List<String> hmget(String shardingKey, String key, String[] fields) {
/* 283 */     JedisTemplate jedisTemplate = getShard(shardingKey);
/* 284 */     return jedisTemplate.hmget(key, fields);
/*     */   }
/*     */   
/*     */   public Map<String, String> hgetAll(String key) {
/* 288 */     JedisTemplate jedisTemplate = getShard(key);
/* 289 */     return jedisTemplate.hgetAll(key);
/*     */   }
/*     */   
/*     */   public Map<String, String> hgetAll(String shardingKey, String key) {
/* 293 */     JedisTemplate jedisTemplate = getShard(shardingKey);
/* 294 */     return jedisTemplate.hgetAll(key);
/*     */   }
/*     */   
/*     */   public void hset(String key, String field, String value) {
/* 298 */     JedisTemplate jedisTemplate = getShard(key);
/* 299 */     jedisTemplate.hset(key, field, value);
/*     */   }
/*     */   
/*     */   public void hset(String shardingKey, String key, String field, String value) {
/* 303 */     JedisTemplate jedisTemplate = getShard(shardingKey);
/* 304 */     jedisTemplate.hset(key, field, value);
/*     */   }
/*     */   
/*     */   public void hmset(String key, Map<String, String> map) {
/* 308 */     JedisTemplate jedisTemplate = getShard(key);
/* 309 */     jedisTemplate.hmset(key, map);
/*     */   }
/*     */   
/*     */   public void hmset(String shardingKey, String key, Map<String, String> map) {
/* 313 */     JedisTemplate jedisTemplate = getShard(shardingKey);
/* 314 */     jedisTemplate.hmset(key, map);
/*     */   }
/*     */   
/*     */   public Boolean hsetnx(String key, String fieldName, String value) {
/* 318 */     JedisTemplate jedisTemplate = getShard(key);
/* 319 */     return jedisTemplate.hsetnx(key, fieldName, value);
/*     */   }
/*     */   
/*     */   public Boolean hsetnx(String shardingKey, String key, String fieldName, String value) {
/* 323 */     JedisTemplate jedisTemplate = getShard(shardingKey);
/* 324 */     return jedisTemplate.hsetnx(key, fieldName, value);
/*     */   }
/*     */   
/*     */   public Long hincrBy(String key, String fieldName, long increment) {
/* 328 */     JedisTemplate jedisTemplate = getShard(key);
/* 329 */     return jedisTemplate.hincrBy(key, fieldName, increment);
/*     */   }
/*     */   
/*     */   public Long hincrBy(String shardingKey, String key, String fieldName, long increment) {
/* 333 */     JedisTemplate jedisTemplate = getShard(shardingKey);
/* 334 */     return jedisTemplate.hincrBy(key, fieldName, increment);
/*     */   }
/*     */   
/*     */   public Double hincrByFloat(String key, String fieldName, double increment) {
/* 338 */     JedisTemplate jedisTemplate = getShard(key);
/* 339 */     return jedisTemplate.hincrByFloat(key, fieldName, increment);
/*     */   }
/*     */   
/*     */   public Double hincrByFloat(String shardingKey, String key, String fieldName, double increment)
/*     */   {
/* 344 */     JedisTemplate jedisTemplate = getShard(shardingKey);
/* 345 */     return jedisTemplate.hincrByFloat(key, fieldName, increment);
/*     */   }
/*     */   
/*     */   public Long hdel(String key, String fieldsName) {
/* 349 */     JedisTemplate jedisTemplate = getShard(key);
/* 350 */     return jedisTemplate.hdel(key, new String[] { fieldsName });
/*     */   }
/*     */   
/*     */   public Long hdel(String key, String[] fieldsNames) {
/* 354 */     JedisTemplate jedisTemplate = getShard(key);
/* 355 */     return jedisTemplate.hdel(key, fieldsNames);
/*     */   }
/*     */   
/*     */   public Long hdel(String shardingKey, String key, String fieldsName) {
/* 359 */     JedisTemplate jedisTemplate = getShard(shardingKey);
/* 360 */     return jedisTemplate.hdel(key, new String[] { fieldsName });
/*     */   }
/*     */   
/*     */   public Long hdel(String shardingKey, String key, String[] fieldsNames) {
/* 364 */     JedisTemplate jedisTemplate = getShard(shardingKey);
/* 365 */     return jedisTemplate.hdel(key, fieldsNames);
/*     */   }
/*     */   
/*     */   public Boolean hexists(String key, String fieldName) {
/* 369 */     JedisTemplate jedisTemplate = getShard(key);
/* 370 */     return jedisTemplate.hexists(key, fieldName);
/*     */   }
/*     */   
/*     */   public Boolean hexists(String shardingKey, String key, String fieldName) {
/* 374 */     JedisTemplate jedisTemplate = getShard(shardingKey);
/* 375 */     return jedisTemplate.hexists(key, fieldName);
/*     */   }
/*     */   
/*     */   public Set<String> hkeys(String key) {
/* 379 */     JedisTemplate jedisTemplate = getShard(key);
/* 380 */     return jedisTemplate.hkeys(key);
/*     */   }
/*     */   
/*     */   public Set<String> hkeys(String shardingKey, String key) {
/* 384 */     JedisTemplate jedisTemplate = getShard(shardingKey);
/* 385 */     return jedisTemplate.hkeys(key);
/*     */   }
/*     */   
/*     */   public Long hlen(String key) {
/* 389 */     JedisTemplate jedisTemplate = getShard(key);
/* 390 */     return jedisTemplate.hlen(key);
/*     */   }
/*     */   
/*     */   public Long hlen(String shardingKey, String key) {
/* 394 */     JedisTemplate jedisTemplate = getShard(shardingKey);
/* 395 */     return jedisTemplate.hlen(key);
/*     */   }
/*     */   
/*     */ 
/*     */   public Long lpush(String key, String value)
/*     */   {
/* 401 */     JedisTemplate jedisTemplate = getShard(key);
/* 402 */     return jedisTemplate.lpush(key, new String[] { value });
/*     */   }
/*     */   
/*     */   public Long lpush(String key, String[] values) {
/* 406 */     JedisTemplate jedisTemplate = getShard(key);
/* 407 */     return jedisTemplate.lpush(key, values);
/*     */   }
/*     */   
/*     */   public Long lpush(String shardingKey, String key, String value) {
/* 411 */     JedisTemplate jedisTemplate = getShard(shardingKey);
/* 412 */     return jedisTemplate.lpush(key, new String[] { value });
/*     */   }
/*     */   
/*     */   public Long lpush(String shardingKey, String key, String[] values) {
/* 416 */     JedisTemplate jedisTemplate = getShard(shardingKey);
/* 417 */     return jedisTemplate.lpush(key, values);
/*     */   }
/*     */   
/*     */   public String rpop(String key) {
/* 421 */     JedisTemplate jedisTemplate = getShard(key);
/* 422 */     return jedisTemplate.rpop(key);
/*     */   }
/*     */   
/*     */   public String rpop(String shardingKey, String key) {
/* 426 */     JedisTemplate jedisTemplate = getShard(shardingKey);
/* 427 */     return jedisTemplate.rpop(key);
/*     */   }
/*     */   
/*     */   public String brpop(String key) {
/* 431 */     JedisTemplate jedisTemplate = getShard(key);
/* 432 */     return jedisTemplate.brpop(key);
/*     */   }
/*     */   
/*     */   public String brpop(String shardingKey, String key) {
/* 436 */     JedisTemplate jedisTemplate = getShard(shardingKey);
/* 437 */     return jedisTemplate.brpop(key);
/*     */   }
/*     */   
/*     */   public String brpop(int timeout, String key) {
/* 441 */     JedisTemplate jedisTemplate = getShard(key);
/* 442 */     return jedisTemplate.brpop(timeout, key);
/*     */   }
/*     */   
/*     */   public String brpop(String shardingKey, int timeout, String key) {
/* 446 */     JedisTemplate jedisTemplate = getShard(shardingKey);
/* 447 */     return jedisTemplate.brpop(timeout, key);
/*     */   }
/*     */   
/*     */   public Long llen(String key) {
/* 451 */     JedisTemplate jedisTemplate = getShard(key);
/* 452 */     return jedisTemplate.llen(key);
/*     */   }
/*     */   
/*     */   public Long llen(String shardingKey, String key) {
/* 456 */     JedisTemplate jedisTemplate = getShard(shardingKey);
/* 457 */     return jedisTemplate.llen(key);
/*     */   }
/*     */   
/*     */   public String lindex(String key, long index) {
/* 461 */     JedisTemplate jedisTemplate = getShard(key);
/* 462 */     return jedisTemplate.lindex(key, index);
/*     */   }
/*     */   
/*     */   public String lindex(String shardingKey, String key, long index) {
/* 466 */     JedisTemplate jedisTemplate = getShard(shardingKey);
/* 467 */     return jedisTemplate.lindex(key, index);
/*     */   }
/*     */   
/*     */   public List<String> lrange(String key, int start, int end) {
/* 471 */     JedisTemplate jedisTemplate = getShard(key);
/* 472 */     return jedisTemplate.lrange(key, start, end);
/*     */   }
/*     */   
/*     */   public List<String> lrange(String shardingKey, String key, int start, int end) {
/* 476 */     JedisTemplate jedisTemplate = getShard(shardingKey);
/* 477 */     return jedisTemplate.lrange(key, start, end);
/*     */   }
/*     */   
/*     */   public void ltrim(String key, int start, int end) {
/* 481 */     JedisTemplate jedisTemplate = getShard(key);
/* 482 */     jedisTemplate.ltrim(key, start, end);
/*     */   }
/*     */   
/*     */   public void ltrim(String shardingKey, String key, int start, int end) {
/* 486 */     JedisTemplate jedisTemplate = getShard(shardingKey);
/* 487 */     jedisTemplate.ltrim(key, start, end);
/*     */   }
/*     */   
/*     */   public void ltrimFromLeft(String key, int size) {
/* 491 */     JedisTemplate jedisTemplate = getShard(key);
/* 492 */     jedisTemplate.ltrimFromLeft(key, size);
/*     */   }
/*     */   
/*     */   public void ltrimFromLeft(String shardingKey, String key, int size) {
/* 496 */     JedisTemplate jedisTemplate = getShard(shardingKey);
/* 497 */     jedisTemplate.ltrimFromLeft(key, size);
/*     */   }
/*     */   
/*     */   public Boolean lremFirst(String key, String value) {
/* 501 */     JedisTemplate jedisTemplate = getShard(key);
/* 502 */     return jedisTemplate.lremFirst(key, value);
/*     */   }
/*     */   
/*     */   public Boolean lremFirst(String shardingKey, String key, String value) {
/* 506 */     JedisTemplate jedisTemplate = getShard(shardingKey);
/* 507 */     return jedisTemplate.lremFirst(key, value);
/*     */   }
/*     */   
/*     */   public Boolean lremAll(String key, String value) {
/* 511 */     JedisTemplate jedisTemplate = getShard(key);
/* 512 */     return jedisTemplate.lremAll(key, value);
/*     */   }
/*     */   
/*     */   public Boolean lremAll(String shardingKey, String key, String value) {
/* 516 */     JedisTemplate jedisTemplate = getShard(shardingKey);
/* 517 */     return jedisTemplate.lremAll(key, value);
/*     */   }
/*     */   
/*     */   public Boolean sadd(String key, String member)
/*     */   {
/* 522 */     JedisTemplate jedisTemplate = getShard(key);
/* 523 */     return jedisTemplate.sadd(key, member);
/*     */   }
/*     */   
/*     */   public Boolean sadd(String shardingKey, String key, String member) {
/* 527 */     JedisTemplate jedisTemplate = getShard(shardingKey);
/* 528 */     return jedisTemplate.sadd(key, member);
/*     */   }
/*     */   
/*     */   public Set<String> smembers(String key) {
/* 532 */     JedisTemplate jedisTemplate = getShard(key);
/* 533 */     return jedisTemplate.smembers(key);
/*     */   }
/*     */   
/*     */   public Set<String> smembers(String shardingKey, String key) {
/* 537 */     JedisTemplate jedisTemplate = getShard(shardingKey);
/* 538 */     return jedisTemplate.smembers(key);
/*     */   }
/*     */   
/*     */ 
/*     */   public Boolean zadd(String key, double score, String member)
/*     */   {
/* 544 */     JedisTemplate jedisTemplate = getShard(key);
/* 545 */     return jedisTemplate.zadd(key, score, member);
/*     */   }
/*     */   
/*     */   public Boolean zadd(String shardingKey, String key, double score, String member) {
/* 549 */     JedisTemplate jedisTemplate = getShard(shardingKey);
/* 550 */     return jedisTemplate.zadd(key, score, member);
/*     */   }
/*     */   
/*     */   public Double zscore(String key, String member) {
/* 554 */     JedisTemplate jedisTemplate = getShard(key);
/* 555 */     return jedisTemplate.zscore(key, member);
/*     */   }
/*     */   
/*     */   public Double zscore(String shardingKey, String key, String member) {
/* 559 */     JedisTemplate jedisTemplate = getShard(shardingKey);
/* 560 */     return jedisTemplate.zscore(key, member);
/*     */   }
/*     */   
/*     */   public Long zrank(String key, String member) {
/* 564 */     JedisTemplate jedisTemplate = getShard(key);
/* 565 */     return jedisTemplate.zrank(key, member);
/*     */   }
/*     */   
/*     */   public Long zrank(String shardingKey, String key, String member) {
/* 569 */     JedisTemplate jedisTemplate = getShard(shardingKey);
/* 570 */     return jedisTemplate.zrank(key, member);
/*     */   }
/*     */   
/*     */   public Long zrevrank(String key, String member) {
/* 574 */     JedisTemplate jedisTemplate = getShard(key);
/* 575 */     return jedisTemplate.zrevrank(key, member);
/*     */   }
/*     */   
/*     */   public Long zrevrank(String shardingKey, String key, String member) {
/* 579 */     JedisTemplate jedisTemplate = getShard(shardingKey);
/* 580 */     return jedisTemplate.zrevrank(key, member);
/*     */   }
/*     */   
/*     */   public Long zcount(String key, double start, double end) {
/* 584 */     JedisTemplate jedisTemplate = getShard(key);
/* 585 */     return jedisTemplate.zcount(key, start, end);
/*     */   }
/*     */   
/*     */   public Long zcount(String shardingKey, String key, double start, double end) {
/* 589 */     JedisTemplate jedisTemplate = getShard(shardingKey);
/* 590 */     return jedisTemplate.zcount(key, start, end);
/*     */   }
/*     */   
/*     */   public Set<String> zrange(String key, int start, int end) {
/* 594 */     JedisTemplate jedisTemplate = getShard(key);
/* 595 */     return jedisTemplate.zrange(key, start, end);
/*     */   }
/*     */   
/*     */   public Set<String> zrange(String shardingKey, String key, int start, int end) {
/* 599 */     JedisTemplate jedisTemplate = getShard(shardingKey);
/* 600 */     return jedisTemplate.zrange(key, start, end);
/*     */   }
/*     */   
/*     */   public Set<Tuple> zrangeWithScores(String key, int start, int end) {
/* 604 */     JedisTemplate jedisTemplate = getShard(key);
/* 605 */     return jedisTemplate.zrangeWithScores(key, start, end);
/*     */   }
/*     */   
/*     */   public Set<Tuple> zrangeWithScores(String shardingKey, String key, int start, int end) {
/* 609 */     JedisTemplate jedisTemplate = getShard(shardingKey);
/* 610 */     return jedisTemplate.zrangeWithScores(key, start, end);
/*     */   }
/*     */   
/*     */   public Set<String> zrevrange(String key, int start, int end) {
/* 614 */     JedisTemplate jedisTemplate = getShard(key);
/* 615 */     return jedisTemplate.zrevrange(key, start, end);
/*     */   }
/*     */   
/*     */   public Set<String> zrevrange(String shardingKey, String key, int start, int end) {
/* 619 */     JedisTemplate jedisTemplate = getShard(shardingKey);
/* 620 */     return jedisTemplate.zrevrange(key, start, end);
/*     */   }
/*     */   
/*     */   public Set<Tuple> zrevrangeWithScores(String key, int start, int end) {
/* 624 */     JedisTemplate jedisTemplate = getShard(key);
/* 625 */     return jedisTemplate.zrevrangeWithScores(key, start, end);
/*     */   }
/*     */   
/*     */   public Set<Tuple> zrevrangeWithScores(String shardingKey, String key, int start, int end) {
/* 629 */     JedisTemplate jedisTemplate = getShard(shardingKey);
/* 630 */     return jedisTemplate.zrevrangeWithScores(key, start, end);
/*     */   }
/*     */   
/*     */   public Set<String> zrangeByScore(String key, double min, double max) {
/* 634 */     JedisTemplate jedisTemplate = getShard(key);
/* 635 */     return jedisTemplate.zrangeByScore(key, min, max);
/*     */   }
/*     */   
/*     */   public Set<String> zrangeByScore(String shardingKey, String key, double min, double max) {
/* 639 */     JedisTemplate jedisTemplate = getShard(shardingKey);
/* 640 */     return jedisTemplate.zrangeByScore(key, min, max);
/*     */   }
/*     */   
/*     */   public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max) {
/* 644 */     JedisTemplate jedisTemplate = getShard(key);
/* 645 */     return jedisTemplate.zrangeByScoreWithScores(key, min, max);
/*     */   }
/*     */   
/*     */   public Set<Tuple> zrangeByScoreWithScores(String shardingKey, String key, double min, double max)
/*     */   {
/* 650 */     JedisTemplate jedisTemplate = getShard(shardingKey);
/* 651 */     return jedisTemplate.zrangeByScoreWithScores(key, min, max);
/*     */   }
/*     */   
/*     */   public Set<String> zrevrangeByScore(String key, double max, double min) {
/* 655 */     JedisTemplate jedisTemplate = getShard(key);
/* 656 */     return jedisTemplate.zrevrangeByScore(key, max, min);
/*     */   }
/*     */   
/*     */   public Set<String> zrevrangeByScore(String shardingKey, String key, double max, double min) {
/* 660 */     JedisTemplate jedisTemplate = getShard(shardingKey);
/* 661 */     return jedisTemplate.zrevrangeByScore(key, max, min);
/*     */   }
/*     */   
/*     */   public Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min) {
/* 665 */     JedisTemplate jedisTemplate = getShard(key);
/* 666 */     return jedisTemplate.zrevrangeByScoreWithScores(key, max, min);
/*     */   }
/*     */   
/*     */   public Set<Tuple> zrevrangeByScoreWithScores(String shardingKey, String key, double max, double min)
/*     */   {
/* 671 */     JedisTemplate jedisTemplate = getShard(shardingKey);
/* 672 */     return jedisTemplate.zrevrangeByScoreWithScores(key, max, min);
/*     */   }
/*     */   
/*     */   public Boolean zrem(String key, String member) {
/* 676 */     JedisTemplate jedisTemplate = getShard(key);
/* 677 */     return jedisTemplate.zrem(key, member);
/*     */   }
/*     */   
/*     */   public Boolean zrem(String shardingKey, String key, String member) {
/* 681 */     JedisTemplate jedisTemplate = getShard(shardingKey);
/* 682 */     return jedisTemplate.zrem(key, member);
/*     */   }
/*     */   
/*     */   public Long zremByScore(String key, double min, double max) {
/* 686 */     JedisTemplate jedisTemplate = getShard(key);
/* 687 */     return jedisTemplate.zremByScore(key, min, max);
/*     */   }
/*     */   
/*     */   public Long zremByScore(String shardingKey, String key, double min, double max) {
/* 691 */     JedisTemplate jedisTemplate = getShard(shardingKey);
/* 692 */     return jedisTemplate.zremByScore(key, min, max);
/*     */   }
/*     */   
/*     */   public Long zremByRank(String key, long start, long end) {
/* 696 */     JedisTemplate jedisTemplate = getShard(key);
/* 697 */     return jedisTemplate.zremByRank(key, start, end);
/*     */   }
/*     */   
/*     */   public Long zremByRank(String shardingKey, String key, long start, long end) {
/* 701 */     JedisTemplate jedisTemplate = getShard(shardingKey);
/* 702 */     return jedisTemplate.zremByRank(key, start, end);
/*     */   }
/*     */   
/*     */   public Long zcard(String key) {
/* 706 */     JedisTemplate jedisTemplate = getShard(key);
/* 707 */     return jedisTemplate.zcard(key);
/*     */   }
/*     */   
/*     */   public Long zcard(String shardingKey, String key) {
/* 711 */     JedisTemplate jedisTemplate = getShard(shardingKey);
/* 712 */     return jedisTemplate.zcard(key);
/*     */   }
/*     */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/javautils/redis/JedisShardedTemplate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */