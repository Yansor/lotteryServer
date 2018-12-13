/*     */ package lottery.domains.content.biz.impl;
/*     */ 
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.BlockingQueue;
/*     */ import java.util.concurrent.LinkedBlockingDeque;
/*     */ import javautils.array.ArrayUtils;
/*     */ import javautils.math.MathUtil;
/*     */ import lottery.domains.content.biz.UserBetsPointService;
/*     */ import lottery.domains.content.biz.UserBillService;
/*     */ import lottery.domains.content.dao.UserDao;
/*     */ import lottery.domains.content.entity.Lottery;
/*     */ import lottery.domains.content.entity.LotteryPlayRules;
/*     */ import lottery.domains.content.entity.User;
/*     */ import lottery.domains.content.entity.UserBets;
/*     */ import lottery.domains.content.vo.config.CodeConfig;
/*     */ import lottery.domains.open.jobs.MailJob;
/*     */ import lottery.domains.pool.DataFactory;
/*     */ import lottery.domains.utils.user.UserCodePointUtil;
/*     */ import org.apache.commons.collections.CollectionUtils;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Service;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Service
/*     */ public class UserBetsPointServiceImpl
/*     */   implements UserBetsPointService
/*     */ {
/*  34 */   private static final Logger log = LoggerFactory.getLogger(UserBetsPointServiceImpl.class);
/*  35 */   private BlockingQueue<UserBets> userBetsQueue = new LinkedBlockingDeque();
/*  36 */   private static boolean isRunning = false;
/*     */   
/*     */   @Autowired
/*     */   private MailJob mailJob;
/*     */   
/*     */   @Autowired
/*     */   private UserDao uDao;
/*     */   
/*     */   @Autowired
/*     */   private UserBillService uBillService;
/*     */   
/*     */   @Autowired
/*     */   private UserCodePointUtil uCodePointUtil;
/*     */   
/*     */   @Autowired
/*     */   private DataFactory dataFactory;
/*     */   
/*     */   public void add(UserBets userBets)
/*     */   {
/*  55 */     userBets.setCodes(null);
/*  56 */     this.userBetsQueue.offer(userBets);
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   @org.springframework.scheduling.annotation.Scheduled(cron="0/3 * * * * *")
/*     */   public void run()
/*     */   {
/*     */     // Byte code:
/*     */     //   0: ldc 7
/*     */     //   2: dup
/*     */     //   3: astore_1
/*     */     //   4: monitorenter
/*     */     //   5: getstatic 8	lottery/domains/content/biz/impl/UserBetsPointServiceImpl:isRunning	Z
/*     */     //   8: iconst_1
/*     */     //   9: if_icmpne +6 -> 15
/*     */     //   12: aload_1
/*     */     //   13: monitorexit
/*     */     //   14: return
/*     */     //   15: iconst_1
/*     */     //   16: putstatic 8	lottery/domains/content/biz/impl/UserBetsPointServiceImpl:isRunning	Z
/*     */     //   19: aload_1
/*     */     //   20: monitorexit
/*     */     //   21: goto +8 -> 29
/*     */     //   24: astore_2
/*     */     //   25: aload_1
/*     */     //   26: monitorexit
/*     */     //   27: aload_2
/*     */     //   28: athrow
/*     */     //   29: aload_0
/*     */     //   30: invokespecial 9	lottery/domains/content/biz/impl/UserBetsPointServiceImpl:calculateOrder	()V
/*     */     //   33: iconst_0
/*     */     //   34: putstatic 8	lottery/domains/content/biz/impl/UserBetsPointServiceImpl:isRunning	Z
/*     */     //   37: goto +10 -> 47
/*     */     //   40: astore_3
/*     */     //   41: iconst_0
/*     */     //   42: putstatic 8	lottery/domains/content/biz/impl/UserBetsPointServiceImpl:isRunning	Z
/*     */     //   45: aload_3
/*     */     //   46: athrow
/*     */     //   47: return
/*     */     // Line number table:
/*     */     //   Java source line #61	-> byte code offset #0
/*     */     //   Java source line #62	-> byte code offset #5
/*     */     //   Java source line #64	-> byte code offset #12
/*     */     //   Java source line #66	-> byte code offset #15
/*     */     //   Java source line #67	-> byte code offset #19
/*     */     //   Java source line #70	-> byte code offset #29
/*     */     //   Java source line #72	-> byte code offset #33
/*     */     //   Java source line #73	-> byte code offset #37
/*     */     //   Java source line #72	-> byte code offset #40
/*     */     //   Java source line #74	-> byte code offset #47
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	48	0	this	UserBetsPointServiceImpl
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
/*     */   private void calculateOrder()
/*     */   {
/*  77 */     if ((this.userBetsQueue != null) && (this.userBetsQueue.size() > 0)) {
/*     */       try {
/*  79 */         List<UserBets> userBetsList = new LinkedList();
/*  80 */         this.userBetsQueue.drainTo(userBetsList, 3000);
/*  81 */         if (CollectionUtils.isNotEmpty(userBetsList)) {
/*  82 */           calculateOrders(userBetsList);
/*     */         }
/*     */       } catch (Exception e) {
/*  85 */         log.error("添加用户大额中奖失败", e);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void calculateOrders(List<UserBets> userBetsList)
/*     */   {
/*  92 */     log.debug("正在进行注单返点，共计{}条注单", Integer.valueOf(userBetsList.size()));
/*  93 */     long start = System.currentTimeMillis();
/*     */     
/*  95 */     for (UserBets userBets : userBetsList) {
/*  96 */       calculatePointMoney(userBets);
/*     */     }
/*     */     
/*  99 */     long spent = System.currentTimeMillis() - start;
/* 100 */     log.debug("完成注单返点，共计{}条注单,耗时{}", Integer.valueOf(userBetsList.size()), Long.valueOf(spent));
/*     */     
/* 102 */     if (spent >= 60000L) {
/* 103 */       String warningMsg = String.format("返点耗时告警；对%s条注单进行返点时耗时达到%s", new Object[] { Integer.valueOf(userBetsList.size()), Long.valueOf(spent) });
/* 104 */       log.warn(warningMsg);
/* 105 */       this.mailJob.addWarning(warningMsg);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void calculatePointMoney(UserBets userBets)
/*     */   {
/* 115 */     calculateUpPoint(userBets);
/*     */     
/*     */ 
/* 118 */     calculateUserPoint(userBets);
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
/*     */ 
/*     */   private void calculateUpPoint(UserBets userBets)
/*     */   {
/* 153 */     if (userBets.getCode() == this.dataFactory.getCodeConfig().getSysCode()) {
/* 154 */       return;
/*     */     }
/*     */     
/* 157 */     User user = this.uDao.getById(userBets.getUserId());
/* 158 */     if ((user == null) || (user.getUpid() == 0) || (user.getUpid() == 72) || (StringUtils.isEmpty(user.getUpids())))
/*     */     {
/* 160 */       return;
/*     */     }
/* 162 */     if (user.getCode() == this.dataFactory.getCodeConfig().getSysCode()) {
/* 163 */       return;
/*     */     }
/*     */     
/* 166 */     Lottery lottery = this.dataFactory.getLottery(userBets.getLotteryId());
/* 167 */     LotteryPlayRules rules = this.dataFactory.getLotteryPlayRules(lottery.getId(), userBets.getRuleId());
/* 168 */     boolean isLocate = rules.getIsLocate() != 0;
/*     */     
/* 170 */     int[] upIds = ArrayUtils.transGetIds(user.getUpids());
/* 171 */     double currentPoint = 0.0D;
/*     */     double totalPoint;
///* 173 */     double totalPoint;
if (isLocate) {
/* 174 */       totalPoint = MathUtil.subtract(this.dataFactory.getCodeConfig().getSysLp(), user.getLocatePoint());
/*     */     }
/*     */     else {
/* 177 */       totalPoint = MathUtil.subtract(this.dataFactory.getCodeConfig().getSysNlp(), user.getNotLocatePoint());
/*     */     }
/* 179 */     for (int upId : upIds)
/*     */     {
/* 181 */       if (upId != 72)
/*     */       {
/*     */ 
/* 184 */         if (currentPoint >= totalPoint) {
/* 185 */           return;
/*     */         }
/*     */         
/* 188 */         User upUser = this.uDao.getById(upId);
/*     */         
/* 190 */         if ((upUser != null) && (upUser.getType() != 3))
/*     */         {
/* 192 */           double upPoint = isLocate ? upUser.getLocatePoint() : upUser.getNotLocatePoint();
/* 193 */           double point = MathUtil.subtract(upPoint, isLocate ? user.getLocatePoint() : user.getNotLocatePoint());
/* 194 */           point = MathUtil.subtract(point, currentPoint);
/* 195 */           if (point > 0.0D)
/*     */           {
/*     */ 
/* 198 */             double upPointMoney = calculateMoneyByPoint(userBets.getMoney(), point);
/*     */             
/* 200 */             if ((upPointMoney > 0.0D) && (upPointMoney > 1.0E-6D))
/*     */             {
/* 202 */               currentPoint = MathUtil.add(currentPoint, point);
/*     */               
/* 204 */               if ((upUser.getAStatus() == 0) || (upUser.getAStatus() == -1))
/*     */               {
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
/* 222 */                 if (upPointMoney <= 0.0D) {
/* 223 */                   return;
/*     */                 }
/*     */                 
/*     */ 
/* 227 */                 boolean succeed = this.uBillService.addProxyReturnBill(userBets, upId, upPointMoney, "上级返点");
/*     */                 
/*     */ 
/* 230 */                 if (succeed) {
/* 231 */                   this.uDao.updateLotteryMoney(upId, upPointMoney);
/*     */                 }
/*     */                 
/* 234 */                 if (upUser.getCode() == this.dataFactory.getCodeConfig().getSysCode())
/* 235 */                   return;
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void calculateUserPoint(UserBets userBets) {
/* 245 */     double point = userBets.getPoint();
/* 246 */     if (point <= 0.0D) {
/* 247 */       return;
/*     */     }
/*     */     
/* 250 */     Lottery lottery = this.dataFactory.getLottery(userBets.getLotteryId());
/* 251 */     if (lottery != null) {
/* 252 */       LotteryPlayRules lotteryPlayRules = this.dataFactory.getLotteryPlayRules(lottery.getId(), userBets.getRuleId());
/*     */       
/* 254 */       if ((lotteryPlayRules != null) && (lotteryPlayRules.getFixed() == 0)) {
/* 255 */         double pointMoney = calculateMoneyByPoint(userBets.getMoney(), point);
/*     */         
/* 257 */         if ((pointMoney > 0.0D) && (pointMoney >= 1.0E-5D)) {
/* 258 */           boolean succeed = this.uBillService.addSpendReturnBill(userBets, pointMoney, "投注返点");
/* 259 */           if (succeed)
/*     */           {
/* 261 */             this.uDao.updateLotteryMoney(userBets.getUserId(), pointMoney);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private double calculateMoneyByPoint(double money, double point)
/*     */   {
/* 272 */     double percent = MathUtil.divide(point, 100.0D, 6);
/* 273 */     double _money = MathUtil.multiply(money, percent);
/* 274 */     return _money;
/*     */   }
/*     */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/content/biz/impl/UserBetsPointServiceImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */