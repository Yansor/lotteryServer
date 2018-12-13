/*     */ package lottery.domains.open.jobs;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.BlockingQueue;
/*     */ import java.util.concurrent.LinkedBlockingDeque;
/*     */ import javautils.date.Moment;
/*     */ import javautils.email.SpringMailUtil;
/*     */ import lottery.domains.content.entity.Lottery;
/*     */ import lottery.domains.content.entity.LotteryPlayRules;
/*     */ import lottery.domains.content.entity.LotteryPlayRulesGroup;
/*     */ import lottery.domains.content.entity.UserBets;
/*     */ import lottery.domains.content.vo.config.MailConfig;
/*     */ import lottery.domains.content.vo.user.UserVO;
/*     */ import lottery.domains.pool.DataFactory;
/*     */ import org.apache.commons.collections.CollectionUtils;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Component;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Component
/*     */ public class MailJob
/*     */ {
/*  31 */   private static final Logger log = LoggerFactory.getLogger(MailJob.class);
/*     */   
/*  33 */   private BlockingQueue<String> logQueue = new LinkedBlockingDeque();
/*  34 */   private BlockingQueue<String> warningQueue = new LinkedBlockingDeque();
/*     */   
/*     */   @Autowired
/*     */   private DataFactory dataFactory;
/*     */   
/*  39 */   private static boolean isRunning = false;
/*  40 */   private static boolean isRunningWarning = false;
/*  41 */   private static Object warningLock = new Object();
/*     */   
/*     */   /* Error */
/*     */   @org.springframework.scheduling.annotation.Scheduled(cron="0/10 * * * * *")
/*     */   public void run()
/*     */   {
/*     */     // Byte code:
/*     */     //   0: ldc 6
/*     */     //   2: dup
/*     */     //   3: astore_1
/*     */     //   4: monitorenter
/*     */     //   5: getstatic 7	lottery/domains/open/jobs/MailJob:isRunning	Z
/*     */     //   8: iconst_1
/*     */     //   9: if_icmpne +6 -> 15
/*     */     //   12: aload_1
/*     */     //   13: monitorexit
/*     */     //   14: return
/*     */     //   15: iconst_1
/*     */     //   16: putstatic 7	lottery/domains/open/jobs/MailJob:isRunning	Z
/*     */     //   19: aload_1
/*     */     //   20: monitorexit
/*     */     //   21: goto +8 -> 29
/*     */     //   24: astore_2
/*     */     //   25: aload_1
/*     */     //   26: monitorexit
/*     */     //   27: aload_2
/*     */     //   28: athrow
/*     */     //   29: aload_0
/*     */     //   30: invokespecial 8	lottery/domains/open/jobs/MailJob:send	()V
/*     */     //   33: iconst_0
/*     */     //   34: putstatic 7	lottery/domains/open/jobs/MailJob:isRunning	Z
/*     */     //   37: goto +10 -> 47
/*     */     //   40: astore_3
/*     */     //   41: iconst_0
/*     */     //   42: putstatic 7	lottery/domains/open/jobs/MailJob:isRunning	Z
/*     */     //   45: aload_3
/*     */     //   46: athrow
/*     */     //   47: return
/*     */     // Line number table:
/*     */     //   Java source line #45	-> byte code offset #0
/*     */     //   Java source line #46	-> byte code offset #5
/*     */     //   Java source line #48	-> byte code offset #12
/*     */     //   Java source line #50	-> byte code offset #15
/*     */     //   Java source line #51	-> byte code offset #19
/*     */     //   Java source line #53	-> byte code offset #29
/*     */     //   Java source line #55	-> byte code offset #33
/*     */     //   Java source line #56	-> byte code offset #37
/*     */     //   Java source line #55	-> byte code offset #40
/*     */     //   Java source line #57	-> byte code offset #47
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	48	0	this	MailJob
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
/*     */   /* Error */
/*     */   @org.springframework.scheduling.annotation.Scheduled(cron="0/8 * * * * *")
/*     */   public void runWarning()
/*     */   {
/*     */     // Byte code:
/*     */     //   0: getstatic 9	lottery/domains/open/jobs/MailJob:warningLock	Ljava/lang/Object;
/*     */     //   3: dup
/*     */     //   4: astore_1
/*     */     //   5: monitorenter
/*     */     //   6: getstatic 10	lottery/domains/open/jobs/MailJob:isRunningWarning	Z
/*     */     //   9: iconst_1
/*     */     //   10: if_icmpne +6 -> 16
/*     */     //   13: aload_1
/*     */     //   14: monitorexit
/*     */     //   15: return
/*     */     //   16: iconst_1
/*     */     //   17: putstatic 10	lottery/domains/open/jobs/MailJob:isRunningWarning	Z
/*     */     //   20: aload_1
/*     */     //   21: monitorexit
/*     */     //   22: goto +8 -> 30
/*     */     //   25: astore_2
/*     */     //   26: aload_1
/*     */     //   27: monitorexit
/*     */     //   28: aload_2
/*     */     //   29: athrow
/*     */     //   30: aload_0
/*     */     //   31: invokespecial 11	lottery/domains/open/jobs/MailJob:sendWarning	()V
/*     */     //   34: iconst_0
/*     */     //   35: putstatic 10	lottery/domains/open/jobs/MailJob:isRunningWarning	Z
/*     */     //   38: goto +10 -> 48
/*     */     //   41: astore_3
/*     */     //   42: iconst_0
/*     */     //   43: putstatic 10	lottery/domains/open/jobs/MailJob:isRunningWarning	Z
/*     */     //   46: aload_3
/*     */     //   47: athrow
/*     */     //   48: return
/*     */     // Line number table:
/*     */     //   Java source line #60	-> byte code offset #0
/*     */     //   Java source line #61	-> byte code offset #6
/*     */     //   Java source line #63	-> byte code offset #13
/*     */     //   Java source line #65	-> byte code offset #16
/*     */     //   Java source line #66	-> byte code offset #20
/*     */     //   Java source line #68	-> byte code offset #30
/*     */     //   Java source line #70	-> byte code offset #34
/*     */     //   Java source line #71	-> byte code offset #38
/*     */     //   Java source line #70	-> byte code offset #41
/*     */     //   Java source line #72	-> byte code offset #48
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	49	0	this	MailJob
/*     */     //   4	23	1	Ljava/lang/Object;	Object
/*     */     //   25	4	2	localObject1	Object
/*     */     //   41	6	3	localObject2	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   6	15	25	finally
/*     */     //   16	22	25	finally
/*     */     //   25	28	25	finally
/*     */     //   30	34	41	finally
/*     */   }
/*     */   
/*     */   private void send()
/*     */   {
/*  75 */     if ((this.logQueue != null) && (this.logQueue.size() > 0)) {
/*     */       try {
/*  77 */         List<String> msgs = new LinkedList();
/*  78 */         this.logQueue.drainTo(msgs, 5);
/*  79 */         if (CollectionUtils.isNotEmpty(msgs)) {
/*  80 */           send(msgs, null);
/*     */         }
/*     */       } catch (Exception e) {
/*  83 */         log.error("发送邮件错误", e);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void sendWarning() {
/*  89 */     if ((this.warningQueue != null) && (this.warningQueue.size() > 0)) {
/*     */       try {
/*  91 */         List<String> msgs = new LinkedList();
/*  92 */         this.warningQueue.drainTo(msgs, 5);
/*  93 */         if (CollectionUtils.isNotEmpty(msgs)) {
/*  94 */           send(msgs, "nickathome2020@gmail.com");
/*     */         }
/*     */       } catch (Exception e) {
/*  97 */         log.error("发送邮件错误", e);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void add(String message) {
/* 103 */     this.logQueue.offer(message);
/*     */   }
/*     */   
/*     */   public void addWarning(String message) {
/* 107 */     this.warningQueue.offer(message);
/*     */   }
/*     */   
/*     */   public void sendOpen(UserBets userBets) {
/*     */     try {
/* 112 */       UserVO user = this.dataFactory.getUser(userBets.getUserId());
/* 113 */       String username = user == null ? "未知" : user.getUsername();
/* 114 */       Lottery lottery = this.dataFactory.getLottery(userBets.getLotteryId());
/* 115 */       String lotteryName = lottery.getShowName();
/* 116 */       int amount = Double.valueOf(userBets.getMoney()).intValue();
/* 117 */       int prizeAmount = Double.valueOf(userBets.getPrizeMoney().doubleValue()).intValue();
/* 118 */       String expect = userBets.getExpect();
/*     */       
/* 120 */       LotteryPlayRules rule = this.dataFactory.getLotteryPlayRules(lottery.getId(), userBets.getRuleId());
/* 121 */       if (rule == null) { return;
/*     */       }
/* 123 */       LotteryPlayRulesGroup group = this.dataFactory.getLotteryPlayRulesGroup(lottery.getId(), rule.getGroupId());
/* 124 */       if (group == null) { return;
/*     */       }
/* 126 */       String method = group.getName() + rule.getName();
/* 127 */       String model = getModel(userBets.getModel());
/* 128 */       int multiple = userBets.getMultiple();
/* 129 */       int nums = userBets.getNums();
/* 130 */       String time = userBets.getTime();
/* 131 */       String openTime = new Moment().toSimpleTime();
/* 132 */       String billNo = userBets.getBillno();
/*     */       
/* 134 */       Object[] values = { username, Integer.valueOf(amount), Integer.valueOf(prizeAmount), lotteryName, expect, method, model, Integer.valueOf(multiple), Integer.valueOf(nums), time, openTime, billNo };
/* 135 */       String message = String.format("用户彩票中奖提醒；用户名：%s；投注金额：%s；中奖金额：%s；彩种：%s；期号：%s；玩法：%s；模式：%s；倍数：%s；注数：%s；投注时间：%s；开奖时间：%s；注单号：%s；", values);
/* 136 */       add(message);
/*     */     } catch (Exception e) {
/* 138 */       log.error("发送邮件发生错误", e == null ? "" : e.getMessage());
/*     */     }
/*     */   }
/*     */   
/*     */   public void sendExpectExceedPrize(UserBets userBets, double beforePrize, double afterPrize) {
/*     */     try {
/* 144 */       UserVO user = this.dataFactory.getUser(userBets.getUserId());
/* 145 */       String username = user == null ? "未知" : user.getUsername();
/* 146 */       Lottery lottery = this.dataFactory.getLottery(userBets.getLotteryId());
/* 147 */       String lotteryName = lottery.getShowName();
/* 148 */       int amount = Double.valueOf(userBets.getMoney()).intValue();
/* 149 */       int beforePrizeAmount = Double.valueOf(beforePrize).intValue();
/* 150 */       int afterPrizeAmount = Double.valueOf(afterPrize).intValue();
/* 151 */       String expect = userBets.getExpect();
/*     */       
/* 153 */       LotteryPlayRules rule = this.dataFactory.getLotteryPlayRules(lottery.getId(), userBets.getRuleId());
/* 154 */       if (rule == null) { return;
/*     */       }
/* 156 */       LotteryPlayRulesGroup group = this.dataFactory.getLotteryPlayRulesGroup(lottery.getId(), rule.getGroupId());
/* 157 */       if (group == null) { return;
/*     */       }
/* 159 */       String method = group.getName() + rule.getName();
/* 160 */       String model = getModel(userBets.getModel());
/* 161 */       int multiple = userBets.getMultiple();
/* 162 */       int nums = userBets.getNums();
/* 163 */       String time = userBets.getTime();
/* 164 */       String openTime = new Moment().toSimpleTime();
/* 165 */       String billNo = userBets.getBillno();
/*     */       
/* 167 */       Object[] values = { username, Integer.valueOf(amount), Integer.valueOf(beforePrizeAmount), Integer.valueOf(afterPrizeAmount), lotteryName, expect, method, model, Integer.valueOf(multiple), Integer.valueOf(nums), time, openTime, billNo };
/* 168 */       String message = String.format("用户彩票奖金超出清除提醒；用户名：%s；投注金额：%s；原中奖金额：%s；清除后中奖金额：%s；彩种：%s；期号：%s；玩法：%s；模式：%s；倍数：%s；注数：%s；投注时间：%s；开奖时间：%s；注单号：%s；", values);
/* 169 */       add(message);
/*     */     } catch (Exception e) {
/* 171 */       log.error("发送邮件发生错误", e == null ? "" : e.getMessage());
/*     */     }
/*     */   }
/*     */   
/*     */   public static String getModel(String model) {
/* 176 */     if ("yuan".equals(model)) return "2元";
/* 177 */     if ("jiao".equals(model)) return "2角";
/* 178 */     if ("fen".equals(model)) return "2分";
/* 179 */     if ("li".equals(model)) return "2厘";
/* 180 */     if ("1yuan".equals(model)) return "1元";
/* 181 */     if ("1jiao".equals(model)) return "1角";
/* 182 */     if ("1fen".equals(model)) return "1分";
/* 183 */     if ("1li".equals(model)) return "1厘";
/* 184 */     return "未知";
/*     */   }
/*     */   
/*     */   private void send(List<String> msgs, String email) {
/*     */     List<String> receiveMails;
/* 189 */     if (StringUtils.isEmpty(email)) {
/* 190 */       receiveMails = this.dataFactory.getMailConfig().getReceiveMails();
/* 191 */       if (!CollectionUtils.isEmpty(receiveMails)) {}
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/* 196 */       receiveMails = new ArrayList();
/* 197 */       receiveMails.add(email);
/*     */     }
/*     */     try
/*     */     {
/* 201 */       MailConfig mailConfig = this.dataFactory.getMailConfig();
/* 202 */       SpringMailUtil mailUtil = new SpringMailUtil(mailConfig.getUsername(), mailConfig.getPersonal(), mailConfig.getPassword(), mailConfig.getHost());
/* 203 */       for (Iterator localIterator1 = msgs.iterator(); localIterator1.hasNext();) { String msg = (String)localIterator1.next();
/* 204 */         for (String receiveMail : receiveMails) {
/* 205 */           mailUtil.send(receiveMail, "提醒", msg);
/* 206 */           Thread.sleep(1000L);
/*     */         }
/*     */       } } catch (InterruptedException e) { SpringMailUtil mailUtil;
/*     */       Iterator localIterator1;
/*     */       String msg;
/* 211 */       log.error("发送邮件错误");
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/open/jobs/MailJob.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */