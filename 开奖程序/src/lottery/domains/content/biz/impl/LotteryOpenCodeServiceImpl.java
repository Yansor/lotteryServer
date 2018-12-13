/*     */ package lottery.domains.content.biz.impl;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.TreeMap;
/*     */ import java.util.TreeSet;
/*     */ import javautils.date.Moment;
/*     */ import javautils.redis.JedisTemplate;
/*     */ import lottery.domains.content.dao.LotteryDao;
/*     */ import lottery.domains.content.dao.LotteryOpenCodeDao;
/*     */ import lottery.domains.content.entity.Lottery;
/*     */ import lottery.domains.content.entity.LotteryOpenCode;
/*     */ import lottery.domains.pool.DataFactory;
/*     */ import org.apache.commons.collections.CollectionUtils;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import redis.clients.jedis.Jedis;
/*     */ import redis.clients.jedis.Pipeline;
/*     */ 
/*     */ @org.springframework.stereotype.Service
/*     */ public class LotteryOpenCodeServiceImpl implements lottery.domains.content.biz.LotteryOpenCodeService
/*     */ {
/*  26 */   private static final Logger log = LoggerFactory.getLogger(LotteryOpenCodeServiceImpl.class);
/*     */   
/*     */   private static final String OPEN_CODE_KEY = "OPEN_CODE:%s";
/*     */   
/*     */   private static final int OPEN_CODE_MOST_EXPECT = 50;
/*     */   
/*     */   @Autowired
/*     */   private LotteryOpenCodeDao lotteryOpenCodeDao;
/*     */   
/*     */   @Autowired
/*     */   private LotteryDao lotteryDao;
/*     */   
/*     */   @Autowired
/*     */   private JedisTemplate jedisTemplate;
/*     */   @Autowired
/*     */   private lottery.domains.open.jobs.MailJob mailJob;
/*     */   @Autowired
/*     */   private DataFactory dataFactory;
/*     */   
/*     */   public boolean updateOpened(LotteryOpenCode openCode)
/*     */   {
/*  47 */     String updateTime = new Moment().toSimpleTime();
/*  48 */     boolean updated = this.lotteryOpenCodeDao.updateOpened(openCode.getId().intValue(), updateTime);
/*  49 */     return updated;
/*     */   }
/*     */   
/*     */   public boolean updateCancelled(LotteryOpenCode openCode)
/*     */   {
/*  54 */     String updateTime = new Moment().toSimpleTime();
/*  55 */     boolean updated = this.lotteryOpenCodeDao.updateCancelled(openCode.getId().intValue(), updateTime);
/*  56 */     return updated;
/*     */   }
/*     */   
/*     */   public List<LotteryOpenCode> getBeforeNotOpen(String lottery, int count)
/*     */   {
/*  61 */     return this.lotteryOpenCodeDao.getBeforeNotOpen(lottery, count);
/*     */   }
/*     */   
/*     */   public LotteryOpenCode getByExcept(String lottery, String except)
/*     */   {
/*  66 */     return this.lotteryOpenCodeDao.getByExcept(lottery, except);
/*     */   }
/*     */   
/*     */   public LotteryOpenCode getByExceptAndUserId(String lottery, int userId, String except)
/*     */   {
/*  71 */     return this.lotteryOpenCodeDao.getByExceptAndUserId(lottery, userId, except);
/*     */   }
/*     */   
/*     */   public boolean add(LotteryOpenCode openCode)
/*     */   {
/*  76 */     if (!"jsmmc".equals(openCode.getLottery())) {
/*  77 */       boolean hasCaptured = hasCaptured(openCode.getLottery(), openCode.getExpect());
/*  78 */       if (hasCaptured) {
/*  79 */         return false;
/*     */       }
/*     */     }
/*     */     
/*  83 */     boolean added = this.lotteryOpenCodeDao.add(openCode);
/*  84 */     if (!added) {
/*  85 */       return false;
/*     */     }
/*     */     
/*  88 */     addedToRedis(openCode);
/*     */     
/*  90 */     return added;
/*     */   }
/*     */   
/*     */   public boolean hasCaptured(String lotteryName, String expect)
/*     */   {
/*  95 */     String key = String.format("OPEN_CODE:%s", new Object[] { lotteryName });
/*     */     
/*  97 */     return this.jedisTemplate.hexists(key, expect).booleanValue();
/*     */   }
/*     */   
/*     */   private void addedToRedis(LotteryOpenCode entity)
/*     */   {
/* 102 */     String key = String.format("OPEN_CODE:%s", new Object[] { entity.getLottery() });
/*     */     
/* 104 */     if ("jsmmc".equals(entity.getLottery()))
/*     */     {
/* 106 */       String field = entity.getExpect() + ":" + entity.getUserId();
/* 107 */       this.jedisTemplate.hset(key, field, entity.getCode());
/*     */     }
/*     */     else
/*     */     {
/* 111 */       Set<String> hkeys = this.jedisTemplate.hkeys(key);
/* 112 */       if (CollectionUtils.isEmpty(hkeys))
/*     */       {
/* 114 */         this.jedisTemplate.hset(key, entity.getExpect(), entity.getCode());
/*     */       }
/*     */       else {
/* 117 */         TreeSet<String> sortHKeys = new TreeSet(hkeys);
/*     */         
/*     */ 
/* 120 */         String[] expects = (String[])sortHKeys.toArray(new String[0]);
/* 121 */         String firstExpect = expects[0];
/* 122 */         if (entity.getExpect().compareTo(firstExpect) > 0) {
/* 123 */           this.jedisTemplate.hset(key, entity.getExpect(), entity.getCode());
/* 124 */           sortHKeys.add(entity.getExpect());
/*     */         }
/*     */         
/* 127 */         if ((CollectionUtils.isNotEmpty(sortHKeys)) && (sortHKeys.size() > 50)) {
/* 128 */           int exceedSize = sortHKeys.size() - 50;
/*     */           
/* 130 */           Iterator<String> iterator = sortHKeys.iterator();
/* 131 */           int count = 0;
/* 132 */           List<String> delFields = new ArrayList();
/* 133 */           while ((iterator.hasNext()) && 
/* 134 */             (count < exceedSize))
/*     */           {
/*     */ 
/*     */ 
/* 138 */             delFields.add(iterator.next());
/* 139 */             iterator.remove();
/* 140 */             count++;
/*     */           }
/*     */           
/*     */ 
/* 144 */           this.jedisTemplate.hdel(key, (String[])delFields.toArray(new String[0]));
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void initLotteryOpenCode()
/*     */   {
/* 153 */     List<Lottery> lotteries = this.lotteryDao.listAll();
/* 154 */     for (Lottery lottery : lotteries) {
/* 155 */       if (lottery.getId() != 117)
/*     */       {
/* 157 */         initLotteryOpenCodeByLottery(lottery);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void initLotteryOpenCodeByLottery(Lottery lottery) {
/* 163 */     List<LotteryOpenCode> openCodes = this.lotteryOpenCodeDao.getLatest(lottery.getShortName(), 50);
/* 164 */     if (CollectionUtils.isEmpty(openCodes)) {
/* 165 */       return;
/*     */     }
/*     */     
/* 168 */     final String key = String.format("OPEN_CODE:%s", new Object[] { lottery.getShortName() });
/*     */     
/*     */ 
/* 171 */     final TreeMap<String, String> values = new TreeMap();
/* 172 */     for (LotteryOpenCode openCode : openCodes) {
/* 173 */       values.put(openCode.getExpect(), openCode.getCode());
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 180 */     this.jedisTemplate.execute(new javautils.redis.JedisTemplate.JedisActionNoResult()
/*     */     {
/*     */       public void action(Jedis jedis) {
/* 183 */         Pipeline pipelined = jedis.pipelined();
/*     */         
/*     */ 
/* 186 */         pipelined.del(key);
/*     */         
/*     */ 
/* 189 */         for (String expect : values.keySet()) {
/* 190 */           pipelined.hset(key, expect, (String)values.get(expect));
/*     */         }
/*     */         
/*     */ 
/* 194 */         pipelined.sync();
/*     */       }
/*     */     });
/*     */   }
/*     */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/content/biz/impl/LotteryOpenCodeServiceImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */