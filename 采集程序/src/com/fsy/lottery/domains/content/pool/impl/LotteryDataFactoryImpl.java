/*     */ package com.fsy.lottery.domains.content.pool.impl;
/*     */ 
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import com.fsy.lottery.domains.content.biz.LotteryOpenCodeService;
/*     */ import com.fsy.lottery.domains.content.dao.LotteryDao;
/*     */ import com.fsy.lottery.domains.content.dao.LotteryOpenTimeDao;
/*     */ import com.fsy.lottery.domains.content.entity.Lottery;
/*     */ import com.fsy.lottery.domains.content.entity.LotteryOpenTime;
/*     */ import com.fsy.lottery.domains.content.pool.LotteryDataFactory;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.InitializingBean;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Component;
/*     */ 
/*     */ @Component
/*     */ public class LotteryDataFactoryImpl implements LotteryDataFactory, InitializingBean
/*     */ {
/*  23 */   private static final Logger logger = LoggerFactory.getLogger(LotteryDataFactoryImpl.class);
/*     */   @Autowired
/*     */   private LotteryOpenTimeDao lotteryOpenTimeDao;
/*     */   
/*  27 */   public void init() { logger.info("init LotteryDataFactory....start");
/*  28 */     initLottery();
/*  29 */     initLotteryOpenTime();
/*  30 */     initLotteryOpenCode();
/*  31 */     logger.info("init LotteryDataFactory....done");
/*     */   }
/*     */   
/*     */   public void afterPropertiesSet() throws Exception
/*     */   {
/*  36 */     init();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Autowired
/*     */   private LotteryOpenCodeService lotteryOpenCodeService;
/*     */   
/*     */ 
/*     */ 
/*  48 */   private List<LotteryOpenTime> lotteryOpenTimeList = new LinkedList();
/*     */   @Autowired
/*     */   private LotteryDao lotteryDao;
/*     */   
/*     */   public void initLotteryOpenTime() {
/*  53 */     try { List<LotteryOpenTime> list = this.lotteryOpenTimeDao.listAll();
/*  54 */       if (this.lotteryOpenTimeList != null) {
/*  55 */         this.lotteryOpenTimeList.clear();
/*     */       }
/*  57 */       this.lotteryOpenTimeList.addAll(list);
/*  58 */       logger.info("初始化彩票开奖时间信息完成！");
/*     */     } catch (Exception e) {
/*  60 */       logger.error("初始化彩票开奖时间信息失败！");
/*     */     }
/*     */   }
/*     */   
/*     */   public List<LotteryOpenTime> listLotteryOpenTime(String lottery)
/*     */   {
/*  66 */     List<LotteryOpenTime> list = new LinkedList();
/*  67 */     for (LotteryOpenTime tmpBean : this.lotteryOpenTimeList) {
/*  68 */       if (tmpBean.getLottery().equals(lottery)) {
/*  69 */         list.add(tmpBean);
/*     */       }
/*     */     }
/*  72 */     return list;
/*     */   }
/*     */   
/*     */   public void initLotteryOpenCode()
/*     */   {
/*  77 */     this.lotteryOpenCodeService.initLotteryOpenCode();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  83 */   private Map<Integer, Lottery> lotteryMap = new LinkedHashMap();
/*     */   
/*     */   public void initLottery()
/*     */   {
/*     */     try {
/*  88 */       List<Lottery> list = this.lotteryDao.listAll();
/*  89 */       Map<Integer, Lottery> tmpMap = new LinkedHashMap();
/*  90 */       for (Lottery lottery : list) {
/*  91 */         tmpMap.put(Integer.valueOf(lottery.getId()), lottery);
/*     */       }
/*  93 */       this.lotteryMap = tmpMap;
/*  94 */       logger.info("初始化彩票信息完成！");
/*     */     } catch (Exception e) {
/*  96 */       logger.error("初始化彩票信息失败！");
/*     */     }
/*     */   }
/*     */   
/*     */   public Lottery getLottery(int id)
/*     */   {
/* 102 */     if (this.lotteryMap.containsKey(Integer.valueOf(id))) {
/* 103 */       return (Lottery)this.lotteryMap.get(Integer.valueOf(id));
/*     */     }
/* 105 */     return null;
/*     */   }
/*     */   
/*     */   public Lottery getLottery(String shortName)
/*     */   {
/* 110 */     Object[] keys = this.lotteryMap.keySet().toArray();
/* 111 */     for (Object o : keys) {
/* 112 */       Lottery lottery = (Lottery)this.lotteryMap.get(o);
/* 113 */       if (lottery.getShortName().equals(shortName)) {
/* 114 */         return lottery;
/*     */       }
/*     */     }
/* 117 */     return null;
/*     */   }
/*     */   
/*     */   public List<Lottery> listLottery()
/*     */   {
/* 122 */     List<Lottery> list = new LinkedList();
/* 123 */     Object[] keys = this.lotteryMap.keySet().toArray();
/* 124 */     for (Object o : keys) {
/* 125 */       list.add(this.lotteryMap.get(o));
/*     */     }
/* 127 */     return list;
/*     */   }
/*     */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryCapture/lotteryCaptureRepair.jar!/lottery/domains/content/pool/impl/LotteryDataFactoryImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */