/*     */ package lottery.domains.open.jobs;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Random;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.CountDownLatch;
/*     */ import java.util.concurrent.ExecutorService;
/*     */ import java.util.concurrent.Executors;
/*     */ import java.util.concurrent.atomic.AtomicReference;
/*     */ import javautils.array.ArrayUtils;
/*     */ import javautils.date.DateUtil;
/*     */ import javautils.date.Moment;
/*     */ import javautils.map.MapUtil;
/*     */ import javautils.math.MathUtil;
/*     */ import javautils.random.RandomUtil;
/*     */ import javautils.redis.JedisTemplate;
/*     */ import lottery.domains.content.biz.LotteryOpenCodeService;
/*     */ import lottery.domains.content.biz.UserBetsSettleService;
/*     */ import lottery.domains.content.dao.UserBetsDao;
/*     */ import lottery.domains.content.entity.Lottery;
/*     */ import lottery.domains.content.entity.LotteryOpenCode;
/*     */ import lottery.domains.content.entity.UserBets;
/*     */ import lottery.domains.content.vo.config.SelfLotteryConfig;
/*     */ import lottery.domains.open.utils.ssc.LotteryCodeUtils;
/*     */ import lottery.domains.pool.DataFactory;
/*     */ import lottery.domains.utils.open.LotteryOpenUtil;
/*     */ import org.apache.commons.collections.CollectionUtils;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.InitializingBean;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ 
/*     */ public abstract class AbstractSelfLotteryOpenCodeJob
/*     */   implements InitializingBean
/*     */ {
/*  46 */   private static final Logger log = LoggerFactory.getLogger("probability");
/*     */   
/*     */   private static final String ADMIN_OPEN_CODE_KEY = "ADMIN_OPEN_CODE:%s";
/*     */   private static final double DEFAULT_PROBABILITY = 0.15D;
/*  50 */   private static List<String> ALL_SSC_CODES = new ArrayList();
/*  51 */   private static List<String> ALL_11X5_CODES = new ArrayList();
/*  52 */   private static List<String> ALL_K3_CODES = new ArrayList();
/*  53 */   private static List<String> ALL_3D_CODES = new ArrayList();
/*  54 */   private static List<String> ALL_PK10_CODES = new ArrayList();
/*     */   
/*  56 */   private static Map<Integer, Integer> THREAD_COUNTS = new HashMap();
/*     */   @Autowired
/*     */   protected DataFactory dataFactory;
/*     */   @Autowired
/*     */   protected LotteryOpenUtil lotteryOpenUtil;
/*     */   @Autowired
/*     */   protected LotteryOpenCodeService lotteryOpenCodeService;
/*     */   @Autowired
/*     */   protected UserBetsSettleService userBetsSettleService;
/*     */   @Autowired
/*     */   protected UserBetsDao userBetsDao;
/*     */   @Autowired
/*     */   protected LotteryCodeUtils lotteryCodeUtils;
/*     */   @Autowired
/*     */   protected MailJob mailJob;
/*     */   @Autowired
/*     */   private JedisTemplate jedisTemplate;
/*     */   
/*     */   public void afterPropertiesSet()
/*     */     throws Exception
/*     */   {
/*  77 */     ALL_SSC_CODES = new ArrayList(this.lotteryCodeUtils.getSSCCodes());
/*  78 */     ALL_11X5_CODES = new ArrayList(this.lotteryCodeUtils.get11X5Codes());
/*  79 */     ALL_K3_CODES = new ArrayList(this.lotteryCodeUtils.getK3Codes());
/*  80 */     ALL_3D_CODES = new ArrayList(this.lotteryCodeUtils.get3DCodes());
/*  81 */     ALL_PK10_CODES = new ArrayList(this.lotteryCodeUtils.getpk10Codes());
/*     */     
/*     */ 
/*  84 */     THREAD_COUNTS.put(Integer.valueOf(1), Integer.valueOf(10));
/*  85 */     THREAD_COUNTS.put(Integer.valueOf(2), Integer.valueOf(10));
/*  86 */     THREAD_COUNTS.put(Integer.valueOf(3), Integer.valueOf(1));
/*  87 */     THREAD_COUNTS.put(Integer.valueOf(4), Integer.valueOf(1));
/*  88 */     THREAD_COUNTS.put(Integer.valueOf(6), Integer.valueOf(1));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected LotteryOpenCode generateOpenCode(Lottery lottery, String expect, Integer userId)
/*     */   {
/*  95 */     LotteryOpenCode openCode = generate(lottery, this.dataFactory.getSelfLotteryConfig().isControl(), this.dataFactory
/*  96 */       .getSelfLotteryConfig().getProbability(), expect, userId);
/*  97 */     return openCode;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private LotteryOpenCode generate(Lottery lottery, boolean control, double probability, String expect, Integer userId)
/*     */   {
/* 104 */     String key = String.format("ADMIN_OPEN_CODE:%s", new Object[] { lottery.getShortName() });
/* 105 */     boolean iskey = this.jedisTemplate.hexists(key, expect).booleanValue();
/* 106 */     if (iskey) {
/* 107 */       String code = this.jedisTemplate.hget(key, expect);
/* 108 */       log.debug("{}第{}期，获取指定开奖号码为{}", new Object[] { lottery.getShowName(), expect, code });
/* 109 */       long del = this.jedisTemplate.hdel(key, new String[] { expect }).longValue();
/* 110 */       log.debug("{}第{}期，删除redis开奖号码，结果为{}", new Object[] { lottery.getShowName(), expect, Long.valueOf(del) });
/* 111 */       return save(lottery, expect, code, userId); }
/*     */     String code;
///*     */     String code;
/* 114 */     if (!control)
/*     */     {
/* 116 */       code = randomGenerate(lottery);
/*     */     }
/*     */     else
/*     */     {
/* 120 */       code = probabilityGenerate(lottery, probability, expect, userId);
/*     */     }
/*     */     
/* 123 */     if (StringUtils.isNotEmpty(code)) {
/* 124 */       return save(lottery, expect, code, userId);
/*     */     }
/* 126 */     return null;
/*     */   }
/*     */   
/*     */   private String randomGenerate(Lottery lottery) {
/* 130 */     if (lottery.getType() == 1) {
/* 131 */       return this.lotteryCodeUtils.randomSSC();
/*     */     }
/* 133 */     if (lottery.getType() == 2) {
/* 134 */       return this.lotteryCodeUtils.random11X5();
/*     */     }
/* 136 */     if (lottery.getType() == 3) {
/* 137 */       return this.lotteryCodeUtils.randomK3();
/*     */     }
/* 139 */     if (lottery.getType() == 4) {
/* 140 */       return this.lotteryCodeUtils.random3D();
/*     */     }
/* 142 */     if (lottery.getType() == 6) {
/* 143 */       return this.lotteryCodeUtils.randompk10();
/*     */     }
/*     */     
/* 146 */     return null;
/*     */   }
/*     */   
/*     */   private List<String> getAllCodes(Lottery lottery) {
/* 150 */     if (lottery.getType() == 1) {
/* 151 */       return ALL_SSC_CODES;
/*     */     }
/* 153 */     if (lottery.getType() == 2) {
/* 154 */       return ALL_11X5_CODES;
/*     */     }
/* 156 */     if (lottery.getType() == 3) {
/* 157 */       return ALL_K3_CODES;
/*     */     }
/* 159 */     if (lottery.getType() == 4) {
/* 160 */       return ALL_3D_CODES;
/*     */     }
/* 162 */     if (lottery.getType() == 6) {
/* 163 */       return ALL_PK10_CODES;
/*     */     }
/*     */     
/* 166 */     return null;
/*     */   }
/*     */   
/*     */   private String probabilityGenerate(Lottery lottery, double probability, String expect, Integer userId) {
/* 170 */     List<UserBets> userBets = getUserBetsByExpect(lottery, expect, userId);
/*     */     
/* 172 */     if (CollectionUtils.isEmpty(userBets)) {
/* 173 */       return randomGenerate(lottery);
/*     */     }
/*     */     
/* 176 */     double maxPrize = getMaxPrize(lottery.getId(), probability, userBets);
/*     */     
/* 178 */     return open(lottery, userBets, maxPrize);
/*     */   }
/*     */   
/*     */   private List<UserBets> getUserBetsByExpect(Lottery lottery, String expect, Integer userId) {
/* 182 */     String hql = "select b  from UserBets  b, User  u  where   u.id = b.userId   and    b.lotteryId = ?0 and  b.status = ?1  and b.expect = ?2 and b.id > 0 and u.upid !=?3 and u.utype=?4";
/* 183 */     Object[] values = { Integer.valueOf(lottery.getId()), Integer.valueOf(0), expect, Integer.valueOf(0), Integer.valueOf(0) };
/* 184 */     if (userId != null) {
/* 185 */       hql = hql + "  and b.userId =  " + userId;
/*     */     }
/* 187 */     return this.userBetsDao.getNoDemoUserBetsByExpect(hql, values);
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
/*     */   private double getMaxPrize(int lotteryId, double probability, List<UserBets> userBets)
/*     */   {
/* 218 */     HashSet<Integer> userIds = new HashSet();
/* 219 */     for (UserBets userBet : userBets) {
/* 220 */       if (!userIds.contains(Integer.valueOf(userBet.getUserId()))) {
/* 221 */         userIds.add(Integer.valueOf(userBet.getUserId()));
/*     */       }
/*     */     }
/*     */     
/* 225 */     Object userBetsList = sumUserProfitGroupByUserId(lotteryId, new ArrayList(userIds));
/* 226 */     if ((CollectionUtils.isEmpty((Collection)userBetsList)) || (userIds.size() != ((List)userBetsList).size())) {
/* 227 */       return 0.0D;
/*     */     }
/*     */     
/* 230 */     double maxPercent = 1.0D - probability;
/*     */     
/* 232 */     if ((maxPercent <= 0.0D) || (maxPercent >= 1.0D)) {
/* 233 */       maxPercent = 0.15D;
/*     */     }
/*     */     
/* 236 */     Map<Integer, Double> userMaxPrizes = new HashMap();
/* 237 */     for (Object o : (List)userBetsList) {
/* 238 */       Object[] values = (Object[])o;
/*     */       
/* 240 */       int userId = Integer.valueOf(values[0].toString()).intValue();
/* 241 */       double money = Double.valueOf(values[1].toString()).doubleValue();
/* 242 */       double prizeMoney = Double.valueOf(values[2].toString()).doubleValue();
/*     */       
/* 244 */       if (prizeMoney >= money) {
/* 245 */         userMaxPrizes.put(Integer.valueOf(userId), Double.valueOf(0.0D));
/*     */       }
/*     */       else {
/* 248 */         double maxPrize = MathUtil.multiply(money, maxPercent);
/* 249 */         maxPrize -= prizeMoney;
/* 250 */         maxPrize = maxPrize <= 0.0D ? 0.0D : maxPrize;
/*     */         
/* 252 */         userMaxPrizes.put(Integer.valueOf(userId), Double.valueOf(maxPrize));
/*     */       }
/*     */     }
/*     */     
/* 256 */     double maxPrize = 0.0D;
/*     */     
/* 258 */     if (!userMaxPrizes.isEmpty()) {
/* 259 */       Map<Integer, Double> sortedPrizeAsc = MapUtil.sortByValueAsc(userMaxPrizes);
/* 260 */       Integer userId = (Integer)sortedPrizeAsc.keySet().iterator().next();
/* 261 */       return ((Double)userMaxPrizes.get(userId)).doubleValue();
/*     */     }
/*     */     
/* 264 */     return maxPrize;
/*     */   }
/*     */   
/*     */   protected List<Lottery> getSelfLotteries() {
/* 268 */     List<Lottery> lotteries = this.dataFactory.listSelfLottery();
/* 269 */     if (CollectionUtils.isEmpty(lotteries)) {
/* 270 */       return null;
/*     */     }
/*     */     
/* 273 */     List<Lottery> selfLotteries = new ArrayList();
/* 274 */     for (Lottery lottery : lotteries) {
/* 275 */       if (lottery.getStatus() == 0)
/*     */       {
/*     */ 
/* 278 */         if (lottery.getId() != 117) {
/* 279 */           selfLotteries.add(lottery);
/*     */         }
/*     */       }
/*     */     }
/* 283 */     return selfLotteries;
/*     */   }
/*     */   
/*     */   private LotteryOpenCode save(Lottery lottery, String expect, String code, Integer userId) {
/* 287 */     LotteryOpenCode bean = new LotteryOpenCode();
/* 288 */     bean.setUserId(userId);
/* 289 */     bean.setCode(code);
/* 290 */     bean.setOpenStatus(Integer.valueOf(0));
/* 291 */     bean.setExpect(expect);
/* 292 */     bean.setLottery(lottery.getShortName());
/* 293 */     bean.setTime(new Moment().toSimpleTime());
/* 294 */     bean.setInterfaceTime(new Moment().toSimpleTime());
/* 295 */     this.lotteryOpenCodeService.add(bean);
/* 296 */     return bean;
/*     */   }
/*     */   
/*     */   private List<?> sumUserProfitGroupByUserId(int lotteryId, List<Integer> userIds) {
/* 300 */     String currentDate = DateUtil.getCurrentDate();
/* 301 */     String startTime = currentDate + " 00:00:00";
/* 302 */     String endTime = currentDate + " 23:59:59";
/*     */     
/* 304 */     return this.userBetsDao.sumUserProfitGroupByUserId(lotteryId, startTime, endTime, userIds);
/*     */   }
/*     */   
/*     */   private String open(final Lottery lottery, final List<UserBets> userBets, final double maxPrize) {
/* 308 */     long start = System.currentTimeMillis();
/*     */     
/*     */ 
/* 311 */     int threadCount = ((Integer)THREAD_COUNTS.get(Integer.valueOf(lottery.getType()))).intValue();
/* 312 */     final List<String> allCodes = getAllCodes(lottery);
/*     */     
/* 314 */     int eachCount = allCodes.size() / threadCount;
/*     */     
/* 316 */     ExecutorService service = Executors.newFixedThreadPool(threadCount);
/* 317 */     final CountDownLatch latch = new CountDownLatch(threadCount);
/*     */     
/* 319 */     List<Integer> indexes = RandomUtil.randomUniqueNumbers(0, allCodes.size() - 1, allCodes.size());
/*     */     
/* 321 */     final AtomicReference<String> randomCode = new AtomicReference();
/* 322 */     Object codeLock = new Object();
/*     */     
/* 324 */     final ConcurrentHashMap<String, Double> prizeCodes = new ConcurrentHashMap();
/*     */     try
/*     */     {
/* 327 */       for (int i = 0; i < threadCount; i++) {
/* 328 */         int startSize = i * eachCount;
/* 329 */         int endSize = startSize + eachCount;
/* 330 */         final List<Integer> threadIndexes = indexes.subList(startSize, endSize);
/*     */         
/* 332 */         Runnable runnable = new Runnable()
/*     */         {
/*     */           public void run() {
/*     */             try {
/* 336 */               Iterator<Integer> iterator = threadIndexes.iterator();
/* 337 */               while ((iterator.hasNext()) && 
/* 338 */                 (randomCode.get() == null))
/*     */               {
/*     */ 
/*     */ 
/* 342 */                 String code = (String)allCodes.get(((Integer)iterator.next()).intValue());
/*     */                 
/* 344 */                 double totalPrize = 0.0D;
/* 345 */                 for (UserBets userBet : userBets)
/*     */                 {
/* 347 */                   Object[] objects = AbstractSelfLotteryOpenCodeJob.this.userBetsSettleService.testUsersBets(userBet, code, lottery, false);
/* 348 */                   if (objects != null) {
/* 349 */                     double prize = ((Double)objects[1]).doubleValue();
/* 350 */                     totalPrize += prize;
/*     */                   }
/*     */                 }
/*     */                 
/* 354 */                 if (totalPrize <= maxPrize) {
/* 355 */                   synchronized (prizeCodes) {
/* 356 */                     if (randomCode.get() == null) {
/* 357 */                       randomCode.set(code);
/* 358 */                       break;
/*     */                     }
/*     */                     
/*     */                   }
/*     */                 } else {
    System.out.println("AbstractSelfLotteryOpenCodeJob.363line修改过");
///* 363 */                   latch.put(code, Double.valueOf(totalPrize));
/*     */                 }
/*     */               }
/*     */             } catch (Exception e) {
/* 367 */               AbstractSelfLotteryOpenCodeJob.log.error("杀率计算开奖号码时异常", e);
/*     */             } finally {
/* 369 */               latch.countDown();
/*     */             }
/*     */           }
/* 372 */         };
/* 373 */         service.submit(runnable);
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 377 */       log.error("杀率计算开奖号码时异常", e);
/*     */     }
/*     */     try
/*     */     {
/* 381 */       latch.await();
/*     */     } catch (InterruptedException e) {
/* 383 */       log.error("杀率计算开奖号码时阑珊异常", e);
/*     */     } finally {
/* 385 */       service.shutdown();
/*     */     }
/*     */     
/* 388 */     String code = null;
/* 389 */     if (randomCode.get() != null) {
/* 390 */       code = (String)randomCode.get();
/*     */ 
/*     */     }
/* 393 */     else if (!prizeCodes.isEmpty()) {
/* 394 */       code = tryRandomGenerate(lottery, userBets, prizeCodes);
/*     */     }
/*     */     
/*     */ 
/* 398 */     if (StringUtils.isEmpty(code)) {
/* 399 */       code = randomGenerate(lottery);
/*     */     }
/*     */     
/* 402 */     long end = System.currentTimeMillis();
/* 403 */     long spend = end - start;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 411 */     prizeCodes.clear();
/* 412 */     indexes.clear();
/*     */     
/* 414 */     return code;
/*     */   }
/*     */   
/*     */   private String tryRandomGenerate(Lottery lottery, List<UserBets> userBets, ConcurrentHashMap<String, Double> prizeCodes) {
/* 418 */     int firstUserId = ((UserBets)userBets.get(0)).getUserId();
/* 419 */     boolean isOnlyOnePerson = true;
/* 420 */     for (UserBets userBet : userBets) {
/* 421 */       if (userBet.getUserId() != firstUserId) {
/* 422 */         isOnlyOnePerson = false;
/* 423 */         break;
/*     */       }
/*     */     }
/*     */     
/* 427 */     if (isOnlyOnePerson) {
/* 428 */       Object uniqueValues = new HashSet(prizeCodes.values());
/* 429 */       Double[] prizes = (Double[])((HashSet)uniqueValues).toArray(new Double[0]);
/* 430 */       ArrayUtils.quickSortDouble(prizes);
/* 431 */       double minPrize = prizes[0].doubleValue();
/*     */       
/* 433 */       LinkedList<String> allMinCodes = new LinkedList();
/* 434 */       Iterator<Entry<String, Double>> iterator = prizeCodes.entrySet().iterator();
/* 435 */       while (iterator.hasNext()) {
/* 436 */         Entry<String, Double> next = (Entry)iterator.next();
/* 437 */         String randomMinCode = (String)next.getKey();
/* 438 */         double prize = ((Double)next.getValue()).doubleValue();
/* 439 */         if (prize == minPrize) {
/* 440 */           allMinCodes.add(randomMinCode);
/*     */         }
/*     */       }
/*     */       
/* 444 */       Random random = new Random();
/* 445 */       int randomIndex = random.nextInt(allMinCodes.size());
/* 446 */       return (String)allMinCodes.get(randomIndex);
/*     */     }
/*     */     
/* 449 */     return randomGenerate(lottery);
/*     */   }
/*     */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/open/jobs/AbstractSelfLotteryOpenCodeJob.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */