/*     */ package lottery.domains.open.jobs;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import lottery.domains.content.biz.LotteryOpenCodeService;
/*     */ import lottery.domains.content.biz.UserBetsService;
/*     */ import lottery.domains.content.biz.UserBetsSettleService;
/*     */ import lottery.domains.content.dao.UserBetsDao;
/*     */ import lottery.domains.content.entity.Lottery;
/*     */ import lottery.domains.content.entity.LotteryOpenCode;
/*     */ import lottery.domains.content.entity.UserBets;
/*     */ import lottery.domains.pool.DataFactory;
/*     */ import lottery.domains.utils.open.LotteryOpenUtil;
/*     */ import lottery.domains.utils.open.OpenTime;
/*     */ import org.apache.commons.collections.CollectionUtils;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.hibernate.criterion.Criterion;
/*     */ import org.hibernate.criterion.Restrictions;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Component;
/*     */ 
/*     */ @Component
/*     */ public class LotteryOpenJob
/*     */ {
/*  33 */   private static final Logger log = LoggerFactory.getLogger(LotteryOpenJob.class);
/*  34 */   private static boolean isRuning = false;
/*     */   @Autowired
/*     */   protected UserBetsDao userBetsDao;
/*     */   @Autowired
/*     */   protected LotteryOpenCodeService lotteryOpenCodeService;
/*     */   @Autowired
/*     */   protected UserBetsSettleService userBetsSettleService;
/*     */   @Autowired
/*     */   private UserBetsService uBetsService;
/*     */   @Autowired
/*     */   protected DataFactory dataFactory;
/*     */   @Autowired
/*     */   protected LotteryOpenUtil lotteryOpenUtil;
/*     */   @Autowired
/*     */   private MailJob mailJob;
/*     */   
/*     */   @org.springframework.scheduling.annotation.Scheduled(cron="0/5 * * * * *")
/*     */   public void openUserBets()
/*     */   {
/*  53 */     synchronized (LotteryOpenJob.class) {
/*  54 */       if (isRuning == true) {
/*  55 */         return;
/*     */       }
/*  57 */       isRuning = true;
/*     */     }
/*     */     try
/*     */     {
/*  61 */       long start = System.currentTimeMillis();
/*     */       
/*     */ 
/*  64 */       int total = open();
/*  65 */       long spend = System.currentTimeMillis() - start;
/*     */       
/*  67 */       if (total > 0) {
/*  68 */         log.debug("开奖完成,共计开奖" + total + "条注单,耗时" + spend);
/*     */       }
/*     */     } catch (Exception e) {
/*  71 */       log.error("开奖异常", e);
/*     */     }
/*     */     finally {
/*  74 */       isRuning = false;
/*     */     }
/*     */   }
/*     */   
/*     */   private int open() {
/*  79 */     int total = 0;
/*  80 */     List<Lottery> lotteries = this.dataFactory.listLottery();
/*  81 */     for (Lottery lottery : lotteries) {
/*  82 */       if ((lottery.getStatus() == 0) && (lottery.getId() != 117)) {
/*  83 */         int lotteryTotal = openByLottery(lottery);
/*  84 */         total += lotteryTotal;
/*     */       }
/*     */     }
/*     */     
/*  88 */     return total;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private int openByLottery(Lottery lottery)
/*     */   {
/*  96 */     int total = 0;
/*     */     try
/*     */     {
/*  99 */       long userBetsStart = System.currentTimeMillis();
/*     */       
/* 101 */       List<UserBets> userBetses = getUserBetsNotOpen(lottery.getId());
/* 102 */       if (CollectionUtils.isEmpty(userBetses)) {
/* 103 */         return 0;
/*     */       }
/*     */       
/* 106 */       long userBetsSpent = System.currentTimeMillis() - userBetsStart;
/* 107 */       if (userBetsSpent > 10000L) {
/* 108 */         String warningMsg = String.format("获取%s未开奖注单耗时告警；获取未开奖注单耗时较多；注单%s条,耗时达到%s", new Object[] { lottery.getShowName(), Integer.valueOf(userBetses.size()), Long.valueOf(userBetsSpent) });
/* 109 */         log.warn(warningMsg);
/* 110 */         this.mailJob.addWarning(warningMsg);
/*     */       }
/*     */       
/*     */ 
/* 114 */       long groupStart = System.currentTimeMillis();
/*     */       
/* 116 */       LinkedHashMap<LotteryOpenCode, List<UserBets>> groupByOpenCodes = groupUserBetsByOpenCode(lottery, userBetses);
/* 117 */       if (groupByOpenCodes.isEmpty()) {
/* 118 */         return 0;
/*     */       }
/*     */       
/* 121 */       long groupSpent = System.currentTimeMillis() - groupStart;
/* 122 */       if (groupSpent > 10000L) {
/* 123 */         String warningMsg = String.format("获取%s未开奖注单分组时耗时告警；注单%s条,耗时达到%s", new Object[] { lottery.getShowName(), Integer.valueOf(userBetses.size()), Long.valueOf(groupSpent) });
/* 124 */         log.warn(warningMsg);
/* 125 */         this.mailJob.addWarning(warningMsg);
/*     */       }
/*     */       
/*     */ 
/* 129 */      LinkedHashMap<String, List<UserBets>> groupByChase = groupByChase(userBetses);
/*     */       
/*     */ 
/* 132 */       HashMap openCodeMap = new HashMap();
/* 133 */       for (LotteryOpenCode lotteryOpenCode : groupByOpenCodes.keySet()) {
/* 134 */         openCodeMap.put(lotteryOpenCode.getExpect(), lotteryOpenCode);
/*     */       }
/*     */       
/* 137 */       for (Entry<LotteryOpenCode, List<UserBets>> groupByOpenCode : groupByOpenCodes.entrySet()) {
/* 138 */         LotteryOpenCode openCode = (LotteryOpenCode)groupByOpenCode.getKey();
/* 139 */         List<UserBets> expectUserBets = (List)groupByOpenCode.getValue();
/*     */         
/*     */ 
/* 142 */         if (("txffc".equalsIgnoreCase(openCode.getLottery())) && (openCode.getOpenStatus().intValue() == 2)) {
/* 143 */           this.uBetsService.cancelByTXFFCInvalid(openCode, (List)groupByOpenCode.getValue());
/*     */ 
/*     */ 
/*     */ 
/*     */         }
/* 148 */         else if (("txlhd".equalsIgnoreCase(openCode.getLottery())) && (openCode.getOpenStatus().intValue() == 2)) {
/* 149 */           this.uBetsService.cancelByTXLHDInvalid(openCode, (List)groupByOpenCode.getValue());
/*     */ 
/*     */ 
/*     */ 
/*     */         }
/* 154 */         else if ((openCode.getOpenStatus().intValue() == 0) || (openCode.getOpenStatus().intValue() == 1))
/*     */         {
/*     */ 
/*     */ 
/*     */ 
/* 159 */           List<UserBets> filterChaseBets = new LinkedList();
/* 160 */           for (UserBets expectUserBet : expectUserBets)
/*     */           {
/* 162 */             if ((openCode.getOpenStatus().intValue() != 1) || (
/* 163 */               (expectUserBet.getType() == 1) && 
/*     */               
/*     */ 
/* 166 */               (expectUserBet.getChaseStop() != null) && 
/*     */               
/*     */ 
/* 169 */               (expectUserBet.getChaseStop().intValue() == 1)))
/*     */             {
/*     */ 
/*     */ 
/*     */ 
/* 174 */               if ((expectUserBet.getType() == 1) && (expectUserBet.getChaseStop() != null) && (expectUserBet.getChaseStop().intValue() == 1))
/*     */               {
/* 176 */                 boolean open = true;
/*     */                 
/* 178 */                 List<UserBets> expectChaseBets = (List)groupByChase.get(expectUserBet.getChaseBillno());
/* 179 */                 if (expectChaseBets != null) {
/* 180 */                   for (UserBets expectChaseBet : expectChaseBets) {
/* 181 */                     if ((expectChaseBet.getId() != expectUserBet.getId()) && 
/* 182 */                       (expectChaseBet.getExpect().compareTo(expectUserBet.getExpect()) <= -1))
/*     */                     {
/* 184 */                       LotteryOpenCode lotteryOpenCode = (LotteryOpenCode)openCodeMap.get(expectChaseBet.getExpect());
/* 185 */                       if (lotteryOpenCode == null) {
/* 186 */                         open = false;
/* 187 */                         break;
/*     */                       }
/*     */                     }
/*     */                   }
/*     */                 }
/*     */                 
/* 193 */                 if ((open == true) && (expectUserBet.getLocked() == 0)) {
/* 194 */                   filterChaseBets.add(expectUserBet);
/*     */                 }
/*     */                 
/*     */               }
/* 198 */               else if (expectUserBet.getLocked() == 0) {
/* 199 */                 filterChaseBets.add(expectUserBet);
/*     */               }
/*     */             }
/*     */           }
/*     */           
/* 204 */           if (filterChaseBets.size() > 0)
/*     */           {
/*     */ 
/*     */ 
/*     */ 
/* 209 */             log.debug("开始结算{}第{}期共计{}条注单", new Object[] { lottery.getShowName(), openCode.getExpect(), Integer.valueOf(filterChaseBets.size()) });
/* 210 */             long start = System.currentTimeMillis();
/*     */             
/* 212 */             this.userBetsSettleService.settleUserBets(filterChaseBets, openCode, lottery);
/* 213 */             total += filterChaseBets.size();
/*     */             
/* 215 */             long spent = System.currentTimeMillis() - start;
/* 216 */             log.debug("完成结算{}第{}期共计{}条注单,耗时{}", new Object[] { lottery.getShowName(), openCode.getExpect(), Integer.valueOf(filterChaseBets.size()), Long.valueOf(spent) });
/*     */             
/* 218 */             if (spent >= 30000L) {
/* 219 */               String warningMsg = String.format("结算耗时告警；结算%s第%s时注单%s条,耗时达到%s", new Object[] { lottery.getShowName(), openCode.getExpect(), Integer.valueOf(((List)groupByOpenCode.getValue()).size()), Long.valueOf(spent) });
/* 220 */               log.warn(warningMsg);
/* 221 */               this.mailJob.addWarning(warningMsg);
/*     */             }
/*     */           } } } } catch (Exception e) { LinkedHashMap<String, List<UserBets>> groupByChase;
/*     */       Map<String, LotteryOpenCode> openCodeMap;
/* 225 */       log.error("开奖异常:" + lottery.getShowName(), e);
/*     */     }
/* 227 */     return total;
/*     */   }
/*     */   
/*     */   private LinkedHashMap<String, List<UserBets>> groupByChase(List<UserBets> allUserBets) {
/* 231 */     LinkedHashMap<String, List<UserBets>> groupByChase = new LinkedHashMap();
/* 232 */     for (UserBets userBets : allUserBets) {
/* 233 */       if ((userBets.getType() == 1) && (userBets.getChaseStop().intValue() == 1)) {
/* 234 */         if (!groupByChase.containsKey(userBets.getChaseBillno())) {
/* 235 */           groupByChase.put(userBets.getChaseBillno(), new LinkedList());
/*     */         }
/*     */         
/* 238 */         ((List)groupByChase.get(userBets.getChaseBillno())).add(userBets);
/*     */       }
/*     */     }
/*     */     
/* 242 */     return groupByChase;
/*     */   }
/*     */   
/*     */ 
/*     */   private LinkedHashMap<LotteryOpenCode, List<UserBets>> groupUserBetsByOpenCode(Lottery lottery, List<UserBets> userBetses)
/*     */   {
/* 248 */     LinkedHashMap<String, List<UserBets>> groupByExpect = new LinkedHashMap();
/* 249 */     for (UserBets userBet : userBetses) {
/* 250 */       String key = userBet.getExpect();
/*     */       
/* 252 */       if (!groupByExpect.containsKey(key)) {
/* 253 */         groupByExpect.put(key, new LinkedList());
/*     */       }
/*     */       
/* 256 */       ((List)groupByExpect.get(key)).add(userBet);
/*     */     }
/*     */     String key;
/* 259 */     Object groupByCode = new LinkedHashMap();
/* 260 */     LinkedHashMap<String, LotteryOpenCode> expectCodes = new LinkedHashMap();
/* 261 */     for (Entry<String, List<UserBets>> stringListEntry : groupByExpect.entrySet()) {
/* 262 */       String expect = (String)stringListEntry.getKey();
/*     */       
/*     */ 
/* 265 */       if (!expectCodes.containsKey(expect))
/*     */       {
/* 267 */         LotteryOpenCode lotteryOpenCode = this.lotteryOpenCodeService.getByExcept(lottery.getShortName(), expect);
/* 268 */         if ((lotteryOpenCode == null) || (StringUtils.isEmpty(lotteryOpenCode.getCode()))) {
/* 269 */           expectCodes.put(expect, new LotteryOpenCode());
/*     */         }
/*     */         else {
/* 272 */           expectCodes.put(expect, lotteryOpenCode);
/*     */         }
/*     */       }
/*     */       
/* 276 */       LotteryOpenCode openCode = (LotteryOpenCode)expectCodes.get(expect);
/* 277 */       if ((openCode != null) && (!StringUtils.isEmpty(openCode.getCode())))
/*     */       {
/*     */ 
/*     */ 
/* 281 */         if (!((LinkedHashMap)groupByCode).containsKey(openCode)) {
/* 282 */           ((LinkedHashMap)groupByCode).put(openCode, new LinkedList());
/*     */         }
/*     */         
/* 285 */         ((List)((LinkedHashMap)groupByCode).get(openCode)).addAll((Collection)stringListEntry.getValue());
/*     */       }
/*     */     }
/* 288 */     return (LinkedHashMap<LotteryOpenCode, List<UserBets>>)groupByCode;
/*     */   }
/*     */   
/*     */   private List<UserBets> getUserBetsNotOpen(int lotteryId)
/*     */   {
/* 293 */     List<Criterion> criterions = new ArrayList();
/* 294 */     criterions.add(Restrictions.eq("status", Integer.valueOf(0)));
/* 295 */     criterions.add(Restrictions.eq("lotteryId", Integer.valueOf(lotteryId)));
/* 296 */     criterions.add(Restrictions.gt("id", Integer.valueOf(0)));
/*     */     OpenTime currOpenTime = null;
/*     */     try
/*     */     {
/* 300 */       currOpenTime = this.lotteryOpenUtil.getCurrOpenTime(lotteryId);
/* 301 */       if (currOpenTime == null) {
/* 302 */         log.error("开奖获取当前期数时为空,彩票ID：" + lotteryId);
/* 303 */         return null;
/*     */       }
/*     */     } catch (Exception e) {
/* 306 */       log.error("开奖获取当前期数时异常,彩票ID：" + lotteryId, e);
/* 307 */       return null;
/*     */     }
///*     */     OpenTime currOpenTime;
/* 310 */     criterions.add(Restrictions.lt("expect", currOpenTime.getExpect()));
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 317 */     List<UserBets> betsList = this.userBetsDao.list(criterions, null);
/*     */     
/* 319 */     return betsList;
/*     */   }
/*     */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/open/jobs/LotteryOpenJob.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */