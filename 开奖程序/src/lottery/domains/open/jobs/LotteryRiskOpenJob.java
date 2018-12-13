/*     */ package lottery.domains.open.jobs;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import javautils.date.Moment;
/*     */ import lottery.domains.content.biz.LotteryOpenCodeService;
/*     */ import lottery.domains.content.biz.UserBetsSettleService;
/*     */ import lottery.domains.content.biz.UserBillService;
/*     */ import lottery.domains.content.dao.UserBetsDao;
/*     */ import lottery.domains.content.dao.UserBetsOriginalDao;
/*     */ import lottery.domains.content.dao.UserBetsRiskDao;
/*     */ import lottery.domains.content.dao.UserDao;
/*     */ import lottery.domains.content.entity.Lottery;
/*     */ import lottery.domains.content.entity.LotteryOpenCode;
/*     */ import lottery.domains.content.entity.UserBets;
/*     */ import lottery.domains.content.entity.UserBetsOriginal;
/*     */ import lottery.domains.content.entity.UserBetsRisk;
/*     */ import lottery.domains.pool.DataFactory;
/*     */ import lottery.domains.utils.open.LotteryOpenUtil;
/*     */ import lottery.domains.utils.open.OpenTime;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.hibernate.criterion.Restrictions;
/*     */ import org.slf4j.Logger;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ 
/*     */ @org.springframework.stereotype.Component
/*     */ public class LotteryRiskOpenJob
/*     */ {
/*  34 */   private static final Logger log = org.slf4j.LoggerFactory.getLogger(LotteryRiskOpenJob.class);
/*  35 */   private static boolean isRuning = false;
/*     */   
/*     */   @Autowired
/*     */   protected UserBetsDao userBetsDao;
/*     */   @Autowired
/*     */   protected UserBetsRiskDao userBetsRiskDao;
/*     */   @Autowired
/*     */   private UserDao uDao;
/*     */   @Autowired
/*     */   private UserBetsOriginalDao uBetsOriginalDao;
/*     */   @Autowired
/*     */   protected LotteryOpenCodeService lotteryOpenCodeService;
/*     */   @Autowired
/*     */   protected UserBetsSettleService userBetsSettleService;
/*     */   @Autowired
/*     */   private lottery.domains.content.biz.UserBetsService uBetsService;
/*     */   @Autowired
/*     */   private UserBillService uBillService;
/*     */   @Autowired
/*     */   protected DataFactory dataFactory;
/*     */   @Autowired
/*     */   protected LotteryOpenUtil lotteryOpenUtil;
/*     */   
/*     */   public void openUserBetsRisk()
/*     */   {
/*  60 */     synchronized (LotteryRiskOpenJob.class) {
/*  61 */       if (isRuning == true) {
/*  62 */         return;
/*     */       }
/*  64 */       isRuning = true;
/*     */     }
/*     */     try
/*     */     {
/*  68 */       long start = System.currentTimeMillis();
/*     */       
/*     */ 
/*  71 */       int total = open();
/*  72 */       long spend = System.currentTimeMillis() - start;
/*     */       
/*  74 */       if (total > 0) {
/*  75 */         log.debug("开奖完成,共计开奖" + total + "条注单,耗时" + spend);
/*     */       }
/*     */     } catch (Exception e) {
/*  78 */       log.error("开奖异常", e);
/*     */     } finally {
/*  80 */       isRuning = false;
/*     */     }
/*     */   }
/*     */   
/*     */   private int open() {
/*  85 */     int total = 0;
/*  86 */     List<Lottery> lotteries = this.dataFactory.listLottery();
/*  87 */     for (Lottery lottery : lotteries) {
/*  88 */       if (!"jsmmc".equalsIgnoreCase(lottery.getShortName()))
/*     */       {
/*     */ 
/*  91 */         Map<Integer, List<UserBetsRisk>> result = openByLottery(lottery);
/*  92 */         for (Integer uid : result.keySet()) {
/*  93 */           List<UserBetsRisk> ubRisk = (List)result.get(uid);
/*  94 */           double sumMoney = 0.0D;
/*  95 */           double sumPrize = 0.0D;
/*  96 */           for (UserBetsRisk risk : ubRisk) {
/*  97 */             sumMoney += risk.getMoney();
/*  98 */             sumPrize += risk.getPrizeMoney().doubleValue();
/*     */           }
/*     */           
/* 101 */           if (sumPrize > sumMoney) {
/* 102 */             for (UserBetsRisk risk : ubRisk) {
/* 103 */               updateByHit(risk, risk.getOpenCode(), risk.getPrizeMoney().doubleValue(), risk.getWinNum());
/*     */             }
/*     */           } else {
/* 106 */             for (UserBetsRisk risk : ubRisk) {
/* 107 */               if (risk.getPrizeMoney().doubleValue() > 0.0D) {
/* 108 */                 updateByUnHit(risk, risk.getOpenCode(), 2, risk.getPrizeMoney().doubleValue(), risk.getWinNum());
/*     */               } else {
/* 110 */                 updateByUnHit(risk, risk.getOpenCode(), 1, 0.0D, 0);
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     Map<Integer, List<UserBetsRisk>> result;
/* 118 */     return total;
/*     */   }
/*     */   
/*     */   private Map<Integer, List<UserBetsRisk>> openByLottery(Lottery lottery) {
/* 122 */     int total = 0;
/* 123 */     Map<Integer, List<UserBetsRisk>> rsMap = new HashMap();
/*     */     try
/*     */     {
/* 126 */       List<UserBetsRisk> userBetses = getUserBetsMonitoring(lottery.getId());
/* 127 */       if (org.apache.commons.collections.CollectionUtils.isEmpty(userBetses)) {
/* 128 */         return rsMap;
/*     */       }
/*     */       
/*     */ 
/* 132 */       LinkedHashMap<LotteryOpenCode, List<UserBetsRisk>> groupByOpenCodes = groupUserBetsByOpenCode(lottery, userBetses);
/*     */       
/* 134 */       if (groupByOpenCodes.isEmpty()) {
/* 135 */         return rsMap;
/*     */       }
/*     */       
/*     */ 
/* 139 */       Map<String, LotteryOpenCode> openCodeMap = new HashMap();
/* 140 */       for (LotteryOpenCode lotteryOpenCode : groupByOpenCodes.keySet()) {
/* 141 */         openCodeMap.put(lotteryOpenCode.getExpect(), lotteryOpenCode);
/*     */       }
/*     */       
/*     */ 
/* 145 */       for (Entry<LotteryOpenCode, List<UserBetsRisk>> groupByOpenCode : groupByOpenCodes.entrySet()) {
/* 146 */         LotteryOpenCode openCode = (LotteryOpenCode)groupByOpenCode.getKey();
/* 147 */         List<UserBetsRisk> expectUserBets = (List)groupByOpenCode.getValue();
/*     */         
/*     */ 
/* 150 */         if (("txffc".equalsIgnoreCase(openCode.getLottery())) && 
/* 151 */           (openCode.getOpenStatus().intValue() == 2)) {
/* 152 */           updateByCancel(expectUserBets, openCode.getCode());
/*     */ 
/*     */ 
/*     */ 
/*     */         }
/* 157 */         else if (("txlhd".equalsIgnoreCase(openCode.getLottery())) && 
/* 158 */           (openCode.getOpenStatus().intValue() == 2)) {
/* 159 */           updateByCancel(expectUserBets, openCode.getCode());
/*     */ 
/*     */         }
/*     */         else
/*     */         {
/* 164 */           for (UserBetsRisk expectUserBet : expectUserBets) {
/* 165 */             UserBets userBets = expectUserBet.toUserBets();
/*     */             
/* 167 */             Object[] results = this.userBetsSettleService.testUsersBets(userBets, openCode.getCode(), lottery, false);
/*     */             
/*     */ 
/* 170 */             if (results == null) {
/* 171 */               updateByHit(expectUserBet, openCode.getCode(), 0.0D, 0);
/*     */             } else {
/* 173 */               int winNum = ((Integer)results[0]).intValue();
/* 174 */               double prize = ((Double)results[1]).doubleValue();
/* 175 */               expectUserBet.setPrizeMoney(Double.valueOf(prize));
/* 176 */               expectUserBet.setWinNum(winNum);
/* 177 */               expectUserBet.setOpenCode(openCode.getCode());
/*     */               
/*     */ 
/* 180 */               if (rsMap.containsKey(Integer.valueOf(expectUserBet.getUserId()))) {
/* 181 */                 List<UserBetsRisk> urisks = (List)rsMap.get(Integer.valueOf(expectUserBet.getUserId()));
/* 182 */                 urisks.add(expectUserBet);
/* 183 */                 rsMap.put(Integer.valueOf(expectUserBet.getUserId()), urisks);
/*     */               } else {
/* 185 */                 List<UserBetsRisk> urisks = new ArrayList();
/* 186 */                 urisks.add(expectUserBet);
/* 187 */                 rsMap.put(Integer.valueOf(expectUserBet.getUserId()), urisks);
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*     */       LotteryOpenCode openCode;
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 202 */       log.error("开奖异常:" + lottery.getShowName(), e);
/*     */     }
/* 204 */     return rsMap;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void updateByHit(UserBetsRisk userBetsRisk, String lotteryOpenCode, double prizeMoney, int winNum)
/*     */   {
/* 213 */     boolean updatedRiskOrder = updateStatus(userBetsRisk, lotteryOpenCode, 5, prizeMoney, winNum);
/*     */     
/* 215 */     if (updatedRiskOrder) {
/* 216 */       int userId = userBetsRisk.getUserId();
/* 217 */       double money = userBetsRisk.getMoney();
/*     */       
/* 219 */       this.uDao.updateLotteryMoney(userId, money);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private void updateByCancel(List<UserBetsRisk> userBetsRiskList, String lotteryOpenCode)
/*     */   {
/* 227 */     for (UserBetsRisk userBetsRisk : userBetsRiskList)
/*     */     {
/* 229 */       boolean updatedRiskOrder = updateStatus(userBetsRisk, lotteryOpenCode, -1, 0.0D, 0);
/*     */       
/* 231 */       if (updatedRiskOrder) {
/* 232 */         moveToRealData(userBetsRisk);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private void updateByUnHit(UserBetsRisk userBetsRisk, String lotteryOpenCode, int status, double prizeMoney, int winNum)
/*     */   {
/* 242 */     boolean updatedRiskOrder = updateStatus(userBetsRisk, lotteryOpenCode, status, prizeMoney, winNum);
/* 243 */     if (updatedRiskOrder) {
/* 244 */       moveToRealData(userBetsRisk);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private boolean moveToRealData(UserBetsRisk userBetsRisk)
/*     */   {
/* 252 */     UserBets userBets = userBetsRisk.toUserBets();
/* 253 */     userBets.setStatus(0);
/*     */     
/* 255 */     boolean addedUserBets = this.userBetsDao.add(userBets);
/*     */     
/* 257 */     if (addedUserBets)
/*     */     {
/* 259 */       boolean addedBill = this.uBillService.addSpendBill(userBets);
/*     */       
/* 261 */       if (addedBill)
/*     */       {
/* 263 */         return addOriginalUserBets(userBets);
/*     */       }
/*     */     }
/*     */     
/* 267 */     return addedUserBets;
/*     */   }
/*     */   
/*     */   private boolean updateStatus(UserBetsRisk userBetsRisk, String openCode, int status, double prizeMoney, int winNum) {
/* 271 */     int id = userBetsRisk.getId();
/*     */     
/* 273 */     int fromStatus = 3;
/* 274 */     int toStatus = status;
/*     */     
/* 276 */     String prizeTime = new Moment().toSimpleTime();
/*     */     
/* 278 */     return this.userBetsRiskDao.updateStatus(id, fromStatus, toStatus, openCode, prizeMoney, prizeTime, winNum);
/*     */   }
/*     */   
/*     */   private boolean addOriginalUserBets(UserBets userBets) {
/*     */     try {
/* 283 */       UserBetsOriginal original = new UserBetsOriginal(userBets);
/* 284 */       return this.uBetsOriginalDao.add(original);
/*     */     } catch (Exception e) {
/* 286 */       log.error("增加原始注单时发生异常", e);
/*     */     }
/*     */     
/* 289 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private LinkedHashMap<LotteryOpenCode, List<UserBetsRisk>> groupUserBetsByOpenCode(Lottery lottery, List<UserBetsRisk> userBetses)
/*     */   {
/* 296 */     LinkedHashMap<String, List<UserBetsRisk>> groupByExpect = new LinkedHashMap();
/* 297 */     for (UserBetsRisk userBet : userBetses) {
/* 298 */       String key = userBet.getExpect();
/*     */       
/* 300 */       if (!groupByExpect.containsKey(key)) {
/* 301 */         groupByExpect.put(key, new LinkedList());
/*     */       }
/*     */       
/* 304 */       ((List)groupByExpect.get(key)).add(userBet);
/*     */     }
/*     */     String key;
/* 307 */     Object groupByCode = new LinkedHashMap();
/* 308 */     LinkedHashMap<String, LotteryOpenCode> expectCodes = new LinkedHashMap();
/* 309 */     for (Entry<String, List<UserBetsRisk>> stringListEntry : groupByExpect.entrySet()) {
/* 310 */       String expect = (String)stringListEntry.getKey();
/*     */       
/*     */ 
/* 313 */       if (!expectCodes.containsKey(expect))
/*     */       {
/* 315 */         LotteryOpenCode lotteryOpenCode = this.lotteryOpenCodeService.getByExcept(lottery.getShortName(), expect);
/* 316 */         if ((lotteryOpenCode == null) || (StringUtils.isEmpty(lotteryOpenCode.getCode()))) {
/* 317 */           expectCodes.put(expect, new LotteryOpenCode());
/*     */         } else {
/* 319 */           expectCodes.put(expect, lotteryOpenCode);
/*     */         }
/*     */       }
/*     */       
/* 323 */       LotteryOpenCode openCode = (LotteryOpenCode)expectCodes.get(expect);
/* 324 */       if ((openCode != null) && (!StringUtils.isEmpty(openCode.getCode())))
/*     */       {
/*     */ 
/*     */ 
/* 328 */         if (!((LinkedHashMap)groupByCode).containsKey(openCode)) {
/* 329 */           ((LinkedHashMap)groupByCode).put(openCode, new LinkedList());
/*     */         }
/*     */         
/* 332 */         ((List)((LinkedHashMap)groupByCode).get(openCode)).addAll((java.util.Collection)stringListEntry.getValue());
/*     */       }
/*     */     }
/* 335 */     return (LinkedHashMap<LotteryOpenCode, List<UserBetsRisk>>)groupByCode;
/*     */   }
/*     */   
/*     */   private List<UserBetsRisk> getUserBetsMonitoring(int lotteryId) {
/* 339 */     List<org.hibernate.criterion.Criterion> criterions = new ArrayList();
/* 340 */     criterions.add(Restrictions.eq("status", Integer.valueOf(3)));
/* 341 */     criterions.add(Restrictions.eq("lotteryId", Integer.valueOf(lotteryId)));
/* 342 */     criterions.add(Restrictions.gt("id", Integer.valueOf(0)));
/*     */     OpenTime currOpenTime = null;
/*     */     try
/*     */     {
/* 346 */       currOpenTime = this.lotteryOpenUtil.getCurrOpenTime(lotteryId);
/* 347 */       if (currOpenTime == null) {
/* 348 */         log.error("开奖获取当前期数时为空,彩票ID：" + lotteryId);
/* 349 */         return null;
/*     */       }
/*     */     } catch (Exception e) {
/* 352 */       log.error("开奖获取当前期数时异常,彩票ID：" + lotteryId, e);
/* 353 */       return null;
/*     */     }
///*     */     OpenTime currOpenTime = ;
/* 356 */     criterions.add(Restrictions.lt("expect", currOpenTime.getExpect()));
/*     */     
/*     */ 
/* 359 */     List<UserBetsRisk> betsList = this.userBetsRiskDao.list(criterions, null);
/*     */     
/* 361 */     return betsList;
/*     */   }
/*     */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/open/jobs/LotteryRiskOpenJob.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */