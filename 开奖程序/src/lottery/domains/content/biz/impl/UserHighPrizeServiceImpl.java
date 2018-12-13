/*     */ package lottery.domains.content.biz.impl;
/*     */ 
/*     */ import com.alibaba.fastjson.JSON;
/*     */ import java.io.PrintStream;
/*     */ import java.util.HashMap;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.BlockingQueue;
/*     */ import java.util.concurrent.LinkedBlockingDeque;
/*     */ import javautils.math.MathUtil;
/*     */ import javautils.redis.JedisTemplate;
/*     */ import lottery.domains.content.biz.UserHighPrizeService;
/*     */ import lottery.domains.content.dao.UserDao;
/*     */ import lottery.domains.content.dao.UserHighPrizeDao;
/*     */ import lottery.domains.content.entity.Lottery;
/*     */ import lottery.domains.content.entity.User;
/*     */ import lottery.domains.content.entity.UserBets;
/*     */ import lottery.domains.content.entity.UserHighPrize;
/*     */ import lottery.domains.pool.DataFactory;
/*     */ import org.apache.commons.collections.CollectionUtils;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Service;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Service
/*     */ public class UserHighPrizeServiceImpl
/*     */   implements UserHighPrizeService
/*     */ {
/*  34 */   private static final Logger log = LoggerFactory.getLogger(UserHighPrizeServiceImpl.class);
/*     */   
/*     */   private static final String USER_HIGH_PRIZE_NOTICE_KEY = "USER:HIGH_PRIZE:NOTICE";
/*     */   
/*  38 */   private BlockingQueue<UserBets> userBetsQueue = new LinkedBlockingDeque();
/*     */   
/*     */   @Autowired
/*     */   private UserHighPrizeDao highTimesDao;
/*     */   
/*     */   @Autowired
/*     */   private JedisTemplate jedisTemplate;
/*     */   
/*     */   @Autowired
/*     */   private DataFactory dataFactory;
/*     */   
/*     */   @Autowired
/*     */   private UserDao uDao;
/*     */   
/*  52 */   private static boolean isRunning = false;
/*     */   
/*     */   /* Error */
/*     */   @org.springframework.scheduling.annotation.Scheduled(cron="0/3 * * * * *")
/*     */   public void run()
/*     */   {
/*     */     // Byte code:
/*     */     //   0: ldc 5
/*     */     //   2: dup
/*     */     //   3: astore_1
/*     */     //   4: monitorenter
/*     */     //   5: getstatic 6	lottery/domains/content/biz/impl/UserHighPrizeServiceImpl:isRunning	Z
/*     */     //   8: iconst_1
/*     */     //   9: if_icmpne +6 -> 15
/*     */     //   12: aload_1
/*     */     //   13: monitorexit
/*     */     //   14: return
/*     */     //   15: iconst_1
/*     */     //   16: putstatic 6	lottery/domains/content/biz/impl/UserHighPrizeServiceImpl:isRunning	Z
/*     */     //   19: aload_1
/*     */     //   20: monitorexit
/*     */     //   21: goto +8 -> 29
/*     */     //   24: astore_2
/*     */     //   25: aload_1
/*     */     //   26: monitorexit
/*     */     //   27: aload_2
/*     */     //   28: athrow
/*     */     //   29: aload_0
/*     */     //   30: invokespecial 7	lottery/domains/content/biz/impl/UserHighPrizeServiceImpl:add	()V
/*     */     //   33: iconst_0
/*     */     //   34: putstatic 6	lottery/domains/content/biz/impl/UserHighPrizeServiceImpl:isRunning	Z
/*     */     //   37: goto +10 -> 47
/*     */     //   40: astore_3
/*     */     //   41: iconst_0
/*     */     //   42: putstatic 6	lottery/domains/content/biz/impl/UserHighPrizeServiceImpl:isRunning	Z
/*     */     //   45: aload_3
/*     */     //   46: athrow
/*     */     //   47: return
/*     */     // Line number table:
/*     */     //   Java source line #56	-> byte code offset #0
/*     */     //   Java source line #57	-> byte code offset #5
/*     */     //   Java source line #59	-> byte code offset #12
/*     */     //   Java source line #61	-> byte code offset #15
/*     */     //   Java source line #62	-> byte code offset #19
/*     */     //   Java source line #64	-> byte code offset #29
/*     */     //   Java source line #66	-> byte code offset #33
/*     */     //   Java source line #67	-> byte code offset #37
/*     */     //   Java source line #66	-> byte code offset #40
/*     */     //   Java source line #68	-> byte code offset #47
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	48	0	this	UserHighPrizeServiceImpl
/*     */     //   3	23	1	Ljava/lang/Object;	Object
/*     */     //   24	4	2	localObject1	Object
/*     */     //   40	6	3	localObject2	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   5	14	24	finally
/*     */     //   15	21	24	finally
/*     */     //   24	27	24	finally
/*     */     //   29	33	40	finally
/*     */   }
/*     */   
/*     */   private void add()
/*     */   {
/*  71 */     if ((this.userBetsQueue != null) && (this.userBetsQueue.size() > 0)) {
/*     */       try {
/*  73 */         List<UserBets> adds = new LinkedList();
/*  74 */         this.userBetsQueue.drainTo(adds, 50);
/*  75 */         if (CollectionUtils.isNotEmpty(adds)) {
/*  76 */           add(adds);
/*     */         }
/*     */       } catch (Exception e) {
/*  79 */         log.error("添加用户大额中奖失败", e);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void add(List<UserBets> adds) {
/*  85 */     Map<Integer, User> userMap = new HashMap();
/*  86 */     for (UserBets add : adds) {
/*  87 */       Lottery lottery = this.dataFactory.getLottery(add.getLotteryId());
/*  88 */       if (lottery != null)
/*     */       {
/*     */ 
/*     */ 
/*  92 */         User user = null;
/*  93 */         if (userMap.containsKey(Integer.valueOf(add.getUserId()))) {
/*  94 */           user = (User)userMap.get(Integer.valueOf(add.getUserId()));
/*     */         }
/*  96 */         if (user == null) {
/*  97 */           user = this.uDao.getById(add.getUserId());
/*     */         }
/*  99 */         if (user != null)
/*     */         {
/*     */ 
/*     */ 
/* 103 */           UserHighPrize highTimes = convert(add, lottery, user);
/*     */           
/*     */ 
/* 106 */           this.highTimesDao.add(highTimes);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private UserHighPrize convert(UserBets userBets, Lottery lottery, User user)
/*     */   {
/* 114 */     UserHighPrize highPrize = new UserHighPrize();
/* 115 */     highPrize.setUserId(user.getId());
/* 116 */     highPrize.setPlatform(2);
/* 117 */     highPrize.setName(lottery.getShowName());
/* 118 */     highPrize.setNameId(lottery.getId() + "");
/* 119 */     highPrize.setSubName(userBets.getExpect());
/* 120 */     highPrize.setRefId(userBets.getId() + "");
/* 121 */     highPrize.setMoney(userBets.getMoney());
/* 122 */     highPrize.setPrizeMoney(userBets.getPrizeMoney().doubleValue());
/*     */     
/* 124 */     double times = userBets.getPrizeMoney().doubleValue() / userBets.getMoney();
/* 125 */     times = MathUtil.doubleFormat(times, 3);
/* 126 */     highPrize.setTimes(times);
/* 127 */     highPrize.setTime(userBets.getPrizeTime());
/* 128 */     highPrize.setStatus(0);
/*     */     
/* 130 */     return highPrize;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private void addToRedis(UserHighPrize highPrize, User user)
/*     */   {
/* 137 */     String field = highPrize.getId() + "";
/* 138 */     Map<String, Object> value = new HashMap();
/*     */     
/* 140 */     value.put("platform", Integer.valueOf(highPrize.getPlatform()));
/* 141 */     value.put("username", user.getUsername());
/* 142 */     value.put("name", highPrize.getName());
/* 143 */     value.put("subName", highPrize.getSubName());
/* 144 */     value.put("refId", highPrize.getRefId());
/* 145 */     value.put("money", Double.valueOf(highPrize.getMoney()));
/* 146 */     value.put("prizeMoney", Double.valueOf(highPrize.getPrizeMoney()));
/* 147 */     value.put("times", Double.valueOf(highPrize.getTimes()));
/* 148 */     value.put("type", Integer.valueOf(1));
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
/* 162 */     this.jedisTemplate.hset("USER:HIGH_PRIZE:NOTICE", field, JSON.toJSONString(value));
/*     */   }
/*     */   
/*     */   public void addIfNecessary(UserBets userBets)
/*     */   {
/* 167 */     if (userBets.getPrizeMoney().doubleValue() <= 0.0D) {
/* 168 */       return;
/*     */     }
/*     */     
/* 171 */     if ((userBets.getPrizeMoney().doubleValue() <= 100.0D) && (userBets.getMoney() <= 100.0D)) {
/* 172 */       return;
/*     */     }
/*     */     
/*     */ 
/* 176 */     double times = userBets.getPrizeMoney().doubleValue() / userBets.getMoney();
/* 177 */     if ((userBets.getPrizeMoney().doubleValue() >= 10000.0D) || (times >= 5.0D)) {
/* 178 */       this.userBetsQueue.offer(userBets);
/*     */     }
/*     */   }
/*     */   
/*     */   public static void main(String[] args) {
/* 183 */     String value = "用户%s在%s%s投注￥%s元中奖￥%s元,请在<大额中奖查询>中确认";
/* 184 */     value = String.format(value, new Object[] { "aaa", "aaa", "aaa", "100", "200" });
/* 185 */     System.out.println(value);
/*     */   }
/*     */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/content/biz/impl/UserHighPrizeServiceImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */