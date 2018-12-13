/*     */ package com.fsy.lottery.domains.content.biz.impl;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.TreeMap;
/*     */ import java.util.TreeSet;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import com.fsy.javautils.redis.JedisTemplate;
/*     */ import com.fsy.javautils.redis.JedisTemplate.JedisActionNoResult;
/*     */ import com.fsy.lottery.domains.capture.utils.open.LotteryOpenUtil;
/*     */ import com.fsy.lottery.domains.content.biz.LotteryOpenCodeService;
/*     */ import com.fsy.lottery.domains.content.dao.LotteryDao;
/*     */ import com.fsy.lottery.domains.content.dao.LotteryOpenCodeDao;
/*     */ import com.fsy.lottery.domains.content.entity.Lottery;
/*     */ import com.fsy.lottery.domains.content.entity.LotteryOpenCode;
/*     */ import org.apache.commons.collections.CollectionUtils;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Service;
/*     */ import redis.clients.jedis.Jedis;
/*     */ import redis.clients.jedis.Pipeline;
/*     */ 
/*     */ 
/*     */ 
/*     */ @Service
/*     */ public class LotteryOpenCodeServiceImpl
/*     */   implements LotteryOpenCodeService
/*     */ {
/*  32 */   private static final Logger log = LoggerFactory.getLogger(LotteryOpenCodeServiceImpl.class);
/*     */   
/*     */   private static final String OPEN_CODE_KEY = "OPEN_CODE:%s";
/*     */   
/*     */   private static final int OPEN_CODE_MOST_EXPECT = 50;
/*     */   @Autowired
/*     */   private LotteryOpenCodeDao lotteryOpenCodeDao;
/*     */   @Autowired
/*     */   private LotteryDao lotteryDao;
/*     */   @Autowired
/*     */   private JedisTemplate jedisTemplate;
/*     */   @Autowired
/*     */   private LotteryOpenUtil lotteryOpenUtil;
/*  45 */   private static ConcurrentHashMap<String, Boolean> CODE_CAPTURE_CACHE = new ConcurrentHashMap();
/*     */   
/*     */ 
/*     */ 
/*     */   public void initLotteryOpenCode()
/*     */   {
/*  51 */     List<Lottery> lotteries = this.lotteryDao.listAll();
/*  52 */     for (Lottery lottery : lotteries) {
/*  53 */       if (lottery.getId() != 117)
/*     */       {
/*  55 */         initLotteryOpenCodeByLottery(lottery); }
/*     */     }
/*     */   }
/*     */   
/*     */   private void initLotteryOpenCodeByLottery(Lottery lottery) {
/*  60 */     List<LotteryOpenCode> openCodes = this.lotteryOpenCodeDao.getLatest(lottery.getShortName(), 50);
/*     */     
/*  62 */     final String key = String.format("OPEN_CODE:%s", new Object[] { lottery.getShortName() });
/*     */     
/*     */ 
/*  65 */     final TreeMap<String, String> values = new TreeMap();
/*  66 */     for (LotteryOpenCode openCode : openCodes) {
/*  67 */       values.put(openCode.getExpect(), openCode.getCode());
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  74 */     this.jedisTemplate.execute(new JedisTemplate.JedisActionNoResult()
/*     */     {
/*     */       public void action(Jedis jedis) {
/*  77 */         Pipeline pipelined = jedis.pipelined();
/*     */         
/*     */ 
/*  80 */         pipelined.del(key);
/*     */         
/*     */ 
/*  83 */         for (String expect : values.keySet()) {
/*  84 */           pipelined.hset(key, expect, (String)values.get(expect));
/*     */         }
/*     */         
/*     */ 
/*  88 */         pipelined.sync();
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */   public boolean hasCaptured(String lotteryName, String expect)
/*     */   {
/*  95 */     LotteryOpenCode openCode = this.lotteryOpenCodeDao.get(lotteryName, expect);
/*  96 */     return openCode != null;
/*     */   }
/*     */   
/*     */   public synchronized boolean add(LotteryOpenCode entity, boolean txffcAssistLast)
/*     */   {
/* 101 */     String cacheKey = entity.getLottery() + "_" + entity.getExpect();
/* 102 */     if (CODE_CAPTURE_CACHE.containsKey(cacheKey)) {
/* 103 */       return false;
/*     */     }
/*     */     
/* 106 */     boolean hasCaptured = hasCaptured(entity.getLottery(), entity.getExpect());
/* 107 */     if (hasCaptured) {
/* 108 */       CODE_CAPTURE_CACHE.put(cacheKey, Boolean.valueOf(true));
/* 109 */       return false;
/*     */     }
/*     */     
/* 112 */     if ((txffcAssistLast) && ("txffc".equals(entity.getLottery()))) {
/* 113 */       String lastExpect = this.lotteryOpenUtil.subtractExpect(entity.getLottery(), entity.getExpect());
/* 114 */       LotteryOpenCode lastExpectCode = this.lotteryOpenCodeDao.get(entity.getLottery(), lastExpect);
/* 115 */       if (lastExpectCode == null) {
/* 116 */         return false;
/*     */       }
/*     */       
/* 119 */       if (entity.getCode().equals(lastExpectCode.getCode())) {
/* 120 */         entity.setOpenStatus(Integer.valueOf(2));
/*     */       }
/*     */     }
/*     */     
/* 124 */     boolean added = this.lotteryOpenCodeDao.add(entity);
/* 125 */     if (!added) {
/* 126 */       return false;
/*     */     }
/*     */     
/* 129 */     addedToRedis(entity);
/* 130 */     CODE_CAPTURE_CACHE.put(cacheKey, Boolean.valueOf(true));
/*     */     
/* 132 */     return added;
/*     */   }
/*     */   
/*     */   private void addedToRedis(LotteryOpenCode entity)
/*     */   {
/* 137 */     String key = String.format("OPEN_CODE:%s", new Object[] { entity.getLottery() });
/*     */     
/*     */ 
/* 140 */     Set<String> hkeys = this.jedisTemplate.hkeys(key);
/* 141 */     if (CollectionUtils.isEmpty(hkeys))
/*     */     {
/* 143 */       this.jedisTemplate.hset(key, entity.getExpect(), entity.getCode());
/*     */     }
/*     */     else {
/* 146 */       TreeSet<String> sortHKeys = new TreeSet(hkeys);
/*     */       
/*     */ 
/* 149 */       String[] expects = (String[])sortHKeys.toArray(new String[0]);
/* 150 */       String firstExpect = expects[0];
/* 151 */       if (entity.getExpect().compareTo(firstExpect) > 0) {
/* 152 */         this.jedisTemplate.hset(key, entity.getExpect(), entity.getCode());
/* 153 */         sortHKeys.add(entity.getExpect());
/*     */       }
/*     */       
/* 156 */       if ((CollectionUtils.isNotEmpty(sortHKeys)) && (sortHKeys.size() > 50)) {
/* 157 */         int exceedSize = sortHKeys.size() - 50;
/*     */         
/* 159 */         Iterator<String> iterator = sortHKeys.iterator();
/* 160 */         int count = 0;
/* 161 */         List<String> delFields = new ArrayList();
/* 162 */         while ((iterator.hasNext()) && 
/* 163 */           (count < exceedSize))
/*     */         {
/*     */ 
/*     */ 
/* 167 */           delFields.add(iterator.next());
/* 168 */           iterator.remove();
/* 169 */           count++;
/*     */         }
/*     */         
/*     */ 
/* 173 */         this.jedisTemplate.hdel(key, (String[])delFields.toArray(new String[0]));
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public LotteryOpenCode get(String lottery, String expect)
/*     */   {
/* 182 */     return this.lotteryOpenCodeDao.get(lottery, expect);
/*     */   }
/*     */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryCapture/lotteryCaptureRepair.jar!/lottery/domains/content/biz/impl/LotteryOpenCodeServiceImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */