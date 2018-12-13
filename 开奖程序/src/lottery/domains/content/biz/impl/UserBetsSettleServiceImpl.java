/*     */ package lottery.domains.content.biz.impl;
/*     */ 
/*     */ import com.alibaba.fastjson.JSON;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import javautils.StringUtil;
/*     */ import javautils.date.DateUtil;
/*     */ import javautils.date.Moment;
/*     */ import javautils.math.MathUtil;
/*     */ import javautils.redis.JedisTemplate;
/*     */ import javautils.redis.JedisTemplate.PipelineActionNoResult;
/*     */ import lottery.domains.content.biz.LotteryOpenCodeService;
/*     */ import lottery.domains.content.biz.UserBetsHitRankingService;
/*     */ import lottery.domains.content.biz.UserBetsPointService;
/*     */ import lottery.domains.content.biz.UserBetsService;
/*     */ import lottery.domains.content.biz.UserBetsSettleService;
/*     */ import lottery.domains.content.biz.UserBillService;
/*     */ import lottery.domains.content.biz.UserHighPrizeService;
/*     */ import lottery.domains.content.biz.UserLotteryDetailsReportService;
/*     */ import lottery.domains.content.biz.UserLotteryReportService;
/*     */ import lottery.domains.content.dao.UserBetsDao;
/*     */ import lottery.domains.content.dao.UserDao;
/*     */ import lottery.domains.content.entity.Lottery;
/*     */ import lottery.domains.content.entity.LotteryOpenCode;
/*     */ import lottery.domains.content.entity.LotteryPlayRules;
/*     */ import lottery.domains.content.entity.LotteryPlayRulesGroup;
/*     */ import lottery.domains.content.entity.UserBets;
/*     */ import lottery.domains.content.vo.config.LotteryConfig;
/*     */ import lottery.domains.content.vo.config.MailConfig;
/*     */ import lottery.domains.content.vo.user.UserVO;
/*     */ import lottery.domains.open.jobs.MailJob;
/*     */ import lottery.domains.pool.DataFactory;
/*     */ import lottery.domains.pool.play.*;
import lottery.domains.pool.play.HConstants.playPrize;
/*     */
/*     */
/*     */
/*     */
/*     */ import lottery.domains.pool.play.impl.SSCRX3HHZXPlayHanlder;
/*     */ import lottery.domains.utils.prize.PrizeUtils;
/*     */ import org.apache.commons.collections.CollectionUtils;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.lang.math.NumberUtils;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.scheduling.annotation.Scheduled;
/*     */ import org.springframework.stereotype.Service;
/*     */ import redis.clients.jedis.Pipeline;
/*     */ import redis.clients.jedis.exceptions.JedisException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Service
/*     */ public class UserBetsSettleServiceImpl
/*     */   implements UserBetsSettleService
/*     */ {
/*  62 */   private static final Logger log = LoggerFactory.getLogger(UserBetsSettleServiceImpl.class);
/*     */   
/*     */   private static final String USER_CURRENT_PRIZE_KEY = "USER:CURRENT_PRIZE:";
/*     */   
/*     */   private static final String LOTTERY_CURRENT_PRIZE_KEY = "LOTTERY:CURRENT_PRIZE:";
/*     */   
/*     */   private static final String USER_BETS_NOTICE_KEY = "USER:BETS_NOTICE";
/*     */   
/*     */   public static final String USER_BETS_UNOPEN_RECENT_KEY = "USER:BETS:UNOPEN:RECENT:%s";
/*     */   
/*     */   public static final String USER_BETS_OPENED_RECENT_KEY = "USER:BETS:OPENED:RECENT:%s";
/*     */   
/*     */   private static final int USER_BETS_NOTICE_EXPIRE_HOURS = 20;
/*     */   
/*     */   @Autowired
/*     */   private UserBetsDao userBetsDao;
/*     */   
/*     */   @Autowired
/*     */   private UserDao userDao;
/*     */   
/*     */   @Autowired
/*     */   private UserBillService userBillService;
/*     */   
/*     */   @Autowired
/*     */   private UserBetsService userBetsService;
/*     */   
/*     */   @Autowired
/*     */   private UserLotteryReportService userLotteryReportService;
/*     */   @Autowired
/*     */   private UserLotteryDetailsReportService userLotteryDetailsReportService;
/*     */   @Autowired
/*     */   private DataFactory dataFactory;
/*     */   @Autowired
/*     */   private LotteryOpenCodeService lotteryOpenCodeService;
/*     */   @Autowired
/*     */   private UserBetsHitRankingService userBetsHitRankingService;
/*     */   @Autowired
/*     */   private UserHighPrizeService highPrizeService;
/*     */   @Autowired
/*     */   private UserBetsPointService userBetsPointService;
/*     */   @Autowired
/*     */   private MailJob mailJob;
/*     */   @Autowired
/*     */   private JedisTemplate jedisTemplate;
/* 106 */   private static ConcurrentHashMap<String, Double> expectDantiaoPrize = new ConcurrentHashMap();
/* 107 */   private static ConcurrentHashMap<String, Double> expectDantiaoUnCachePrize = new ConcurrentHashMap();
/* 108 */   private static ConcurrentHashMap<String, Double> userExpectPrize = new ConcurrentHashMap();
/*     */   
/*     */   @Scheduled(cron="0 0 6 * * *")
/*     */   public void clearCache()
/*     */   {
/* 113 */     expectDantiaoPrize.clear();
/* 114 */     userExpectPrize.clear();
/* 115 */     expectDantiaoUnCachePrize.clear();
/*     */   }
/*     */   
/*     */   public void settleUserBets(List<UserBets> userBetsList, LotteryOpenCode openCode, Lottery lottery)
/*     */   {
/*     */     try
/*     */     {
/* 122 */       for (UserBets userBets : userBetsList) {
/* 123 */         if (userBets.getTime().compareTo(openCode.getTime()) < 0)
/*     */         {
/*     */ 
/*     */           try
/*     */           {
/*     */ 
/*     */ 
/* 130 */             if ((userBets.getType() == 1) && 
/* 131 */               (userBets.getChaseStop().intValue() == 1)) {
/* 132 */               boolean cancelChase = this.userBetsService.isCancelChase(userBets.getChaseBillno());
/* 133 */               if (!cancelChase) {
/* 134 */                 calculateUsersBets(userBets, openCode.getCode(), lottery);
/*     */               }
/*     */             }
/*     */             else {
/* 138 */               calculateUsersBets(userBets, openCode.getCode(), lottery);
/*     */             }
/*     */           } catch (Exception e) {
/* 141 */             log.error("开奖发生错误", e);
/*     */           }
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 147 */       this.lotteryOpenCodeService.updateOpened(openCode);
/*     */     } catch (Exception e) {
/* 149 */       throw e;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private void calculateUsersBets(UserBets userBets, String openCode, Lottery lottery)
/*     */   {
/* 157 */     long start = System.currentTimeMillis();
/*     */     
/*     */ 
/* 160 */     Object[] results = testUsersBets(userBets, openCode, lottery, true);
/*     */     
/* 162 */     long spend = System.currentTimeMillis() - start;
/* 163 */     if (spend >= 500L) {
/* 164 */       String warningMsg = String.format("开奖耗时告警；计算注单耗时较多；计算注单%s时耗时达到%s", new Object[] { Integer.valueOf(userBets.getId()), Long.valueOf(spend) });
/* 165 */       log.warn(warningMsg);
/* 166 */       this.mailJob.addWarning(warningMsg);
/*     */     }
/*     */     
/* 169 */     if ((results == null) || (results.length <= 0)) {
/* 170 */       return;
/*     */     }
/*     */     
/* 173 */     int winNum = ((Integer)results[0]).intValue();
/* 174 */     double prize = ((Double)results[1]).doubleValue();
/*     */     
/*     */ 
/* 177 */     start = System.currentTimeMillis();
/*     */     
/*     */ 
/* 180 */     calculateResult(lottery, userBets, openCode, prize, winNum);
/*     */     
/* 182 */     spend = System.currentTimeMillis() - start;
/* 183 */     if (spend >= 1000L) {
/* 184 */       String warningMsg = String.format("开奖耗时告警；处理注单耗时较多；处理注单%s时耗时达到%s", new Object[] { Integer.valueOf(userBets.getId()), Long.valueOf(spend) });
/* 185 */       log.warn(warningMsg);
/* 186 */       this.mailJob.addWarning(warningMsg);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public double getWinMoneyByCode(WinResult result, UserBets userBets, int bUnitMoney, String[] prizeCodes)
/*     */   {
/* 193 */     LotteryPlayRules rules = this.dataFactory.getLotteryPlayRules(userBets.getLotteryId(), userBets.getRuleId());
/* 194 */     String tempPrize = disposeDsPlayPrize(rules, prizeCodes, result.getWinCode());
/* 195 */     double prize = 0.0D;
/* 196 */     if (!tempPrize.equals(""))
/*     */     {
/* 198 */       prize = MathUtil.multiply(
/* 199 */         MathUtil.multiply(PrizeUtils.getWinMoneyByCode(rules.getFixed(), userBets
/* 200 */         .getModel(), bUnitMoney, userBets
/* 201 */         .getCode(), Double.parseDouble(tempPrize)), result
/* 202 */         .getWinNum()), userBets
/* 203 */         .getMultiple());
/*     */     }
/* 205 */     return prize;
/*     */   }
/*     */   
/*     */   public LotteryPlayRules[] getZhWinLev(int winNum, Lottery lottery)
/*     */   {
/* 210 */     LotteryPlayRules zuheRules1 = this.dataFactory.getLotteryPlayRules(lottery.getId(), "dw");
/* 211 */     if (zuheRules1 == null) {
/* 212 */       return null;
/*     */     }
/* 214 */     if (winNum == 1) {
/* 215 */       LotteryPlayRules[] res = { zuheRules1 };
/* 216 */       return res;
/*     */     }
/* 218 */     if (winNum == 2) {
/* 219 */       LotteryPlayRules zuheRules2 = this.dataFactory.getLotteryPlayRules(lottery.getId(), "exzhixdsq");
/*     */       
/* 221 */       LotteryPlayRules[] res = { zuheRules1, zuheRules2 };
/* 222 */       return res;
/*     */     }
/* 224 */     if (winNum == 3) {
/* 225 */       LotteryPlayRules zuheRules2 = this.dataFactory.getLotteryPlayRules(lottery.getId(), "exzhixdsq");
/*     */       
/* 227 */       LotteryPlayRules zuheRules3 = this.dataFactory.getLotteryPlayRules(lottery.getId(), "sxzhixdsq");
/*     */       
/* 229 */       LotteryPlayRules[] res = { zuheRules1, zuheRules2, zuheRules3 };
/* 230 */       return res;
/*     */     }
/* 232 */     if (winNum == 4) {
/* 233 */       LotteryPlayRules zuheRules2 = this.dataFactory.getLotteryPlayRules(lottery.getId(), "exzhixdsq");
/*     */       
/* 235 */       LotteryPlayRules zuheRules3 = this.dataFactory.getLotteryPlayRules(lottery.getId(), "sxzhixdsq");
/*     */       
/* 237 */       LotteryPlayRules zuheRules4 = this.dataFactory.getLotteryPlayRules(lottery.getId(), "sixzhixzhq");
/*     */       
/* 239 */       LotteryPlayRules[] res = { zuheRules1, zuheRules2, zuheRules3, zuheRules4 };
/* 240 */       return res;
/*     */     }
/* 242 */     if (winNum == 5) {
/* 243 */       LotteryPlayRules zuheRules2 = this.dataFactory.getLotteryPlayRules(lottery.getId(), "exzhixdsq");
/*     */       
/* 245 */       LotteryPlayRules zuheRules3 = this.dataFactory.getLotteryPlayRules(lottery.getId(), "sxzhixdsq");
/*     */       
/* 247 */       LotteryPlayRules zuheRules4 = this.dataFactory.getLotteryPlayRules(lottery.getId(), "sixzhixzhq");
/*     */       
/* 249 */       LotteryPlayRules zuheRules5 = this.dataFactory.getLotteryPlayRules(lottery.getId(), "wxzhixzh");
/*     */       
/* 251 */       LotteryPlayRules[] res = { zuheRules1, zuheRules2, zuheRules3, zuheRules4, zuheRules5 };
/*     */       
/* 253 */       return res;
/*     */     }
/* 255 */     return null;
/*     */   }
/*     */   
/*     */   public String disposeDsPlayPrize(LotteryPlayRules rule, String[] codeArr, String winCode) {
/* 259 */     String[] pr = rule.getPrize().split(",");
/* 260 */     String res = "";
/* 261 */     for (int i = 0; i < pr.length; i++) {
/* 262 */       String[] valuearr = codeArr[i].split(",");
/* 263 */       for (int j = 0; j < valuearr.length; j++) {
/* 264 */         if (valuearr[j].trim().equals(winCode.trim())) {
/* 265 */           res = pr[i];
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 270 */     return res;
/*     */   }
/*     */   
/*     */   public void calculateResult(Lottery lottery, UserBets userBets, String openCode, double prize, int winNum) {
/* 274 */     userBets.setOpenCode(openCode);
/* 275 */     String prizeTime = new Moment().toSimpleTime();
/* 276 */     userBets.setPrizeTime(prizeTime);
/*     */     
/* 278 */     boolean succeed = false;
/* 279 */     if ((winNum > 0) && (prize > 0.0D)) {
/* 280 */       userBets.setPrizeMoney(Double.valueOf(prize));
/* 281 */       userBets.setStatus(2);
/*     */       
/*     */ 
/* 284 */       succeed = this.userBillService.addUserWinBill(userBets, prize, "派奖");
/*     */       
/* 286 */       if (succeed)
/*     */       {
/* 288 */         this.userBetsDao.updateStatus(userBets.getId(), 2, openCode, prize, prizeTime);
/*     */         
/*     */ 
/* 291 */         this.userDao.updateLotteryMoney(userBets.getUserId(), prize, -userBets.getMoney());
/*     */       }
/*     */     } else {
/* 294 */       userBets.setPrizeMoney(Double.valueOf(0.0D));
/*     */       
/* 296 */       if (winNum > 0) {
/* 297 */         userBets.setStatus(2);
/* 298 */         this.userBetsDao.updateStatus(userBets.getId(), 2, openCode, 0.0D, prizeTime);
/*     */       }
/*     */       else {
/* 301 */         userBets.setStatus(1);
/* 302 */         this.userBetsDao.updateStatus(userBets.getId(), 1, openCode, 0.0D, prizeTime);
/*     */       }
/*     */       
/*     */ 
/* 306 */       this.userDao.updateFreezeMoney(userBets.getUserId(), -userBets.getMoney());
/* 307 */       succeed = true;
/*     */     }
/*     */     
/* 310 */     if (!succeed) {
/* 311 */       String warningMsg = "结算订单出错；ID：" + userBets.getId() + "，新增赢账单时错误";
/* 312 */       log.error(warningMsg);
/* 313 */       this.mailJob.addWarning(warningMsg);
/* 314 */       return;
/*     */     }
/*     */     
/*     */ 
/* 318 */     String time = DateUtil.formatTime(userBets.getTime(), "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd");
/* 319 */     this.userLotteryReportService.update(userBets.getUserId(), 22, userBets.getMoney(), time);
/* 320 */     this.userLotteryDetailsReportService.update(userBets.getUserId(), userBets.getLotteryId(), userBets.getRuleId(), 22, userBets.getMoney(), time);
/*     */     
/*     */ 
/* 323 */     this.userBetsPointService.add(userBets);
/*     */     
/*     */ 
/* 326 */     if ((winNum > 0) && 
/* 327 */       (userBets.getType() == 1) && 
/* 328 */       (userBets.getChaseStop().intValue() == 1)) {
/* 329 */       this.userBetsService.cancelChase(userBets.getChaseBillno(), userBets.getUserId(), userBets.getExpect());
/*     */     }
/*     */     
/*     */ 
/*     */     try
/*     */     {
/* 335 */       if (prize >= this.dataFactory.getMailConfig().getOpen()) {
/* 336 */         userBets.setPrizeMoney(Double.valueOf(prize));
/* 337 */         this.mailJob.sendOpen(userBets);
/*     */       }
/*     */       
/*     */ 
/* 341 */       if ((lottery.getSelf() == 1) && 
/* 342 */         (prize >= 200.0D)) {
/* 343 */         UserVO user = this.dataFactory.getUser(userBets.getUserId());
/* 344 */         String username = user == null ? "未知" : user.getUsername();
/* 345 */         this.mailJob.addWarning("用户" + username + "在自主彩" + lottery.getShowName() + "第" + userBets.getExpect() + "中奖" + prize + "元");
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 350 */       log.error("发送邮件告警错误", e);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     try
/*     */     {
/* 358 */       if (this.dataFactory.getLotteryConfig().isAutoHitRanking()) {
/* 359 */         this.userBetsHitRankingService.addIfNecessary(userBets);
/*     */       }
/*     */       
/*     */ 
/* 363 */       notifyUser(userBets);
/*     */       
/*     */ 
/* 366 */       cacheUserBetsId(userBets);
/*     */       
/*     */ 
/* 369 */       this.highPrizeService.addIfNecessary(userBets);
/*     */     } catch (Exception e) {
/* 371 */       log.error("添加中奖通知信息时异常", e);
/*     */     }
/*     */   }
/*     */   
/*     */   private void cacheUserBetsId(UserBets userBets)
/*     */   {
/* 377 */     final String unOpenCacheKey = String.format("USER:BETS:UNOPEN:RECENT:%s", new Object[] { Integer.valueOf(userBets.getUserId()) });
/* 378 */     final String openedCacheKey = String.format("USER:BETS:OPENED:RECENT:%s", new Object[] { Integer.valueOf(userBets.getUserId()) });
/* 379 */     final String userBetsId = userBets.getId() + "";
/*     */     try
/*     */     {
/* 382 */       this.jedisTemplate.execute(new JedisTemplate.PipelineActionNoResult()
/*     */       {
/*     */         public void action(Pipeline pipeline) {
/* 385 */           pipeline.lrem(unOpenCacheKey, 1L, userBetsId);
/* 386 */           pipeline.lpush(openedCacheKey, new String[] { userBetsId });
/* 387 */           pipeline.ltrim(openedCacheKey, 0L, 10L);
/* 388 */           pipeline.expire(openedCacheKey, 43200);
/* 389 */           pipeline.sync();
/*     */         }
/*     */       });
/*     */     } catch (JedisException e) {
/* 393 */       log.error("执行Redis缓存注单ID时出错", e);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private void notifyUser(UserBets userBets)
/*     */   {
/* 401 */     long expireAt = new Moment().add(20, "hours").toDate().getTime();
/*     */     
/* 403 */     Lottery lottery = this.dataFactory.getLottery(userBets.getLotteryId());
/* 404 */     String field = userBets.getUserId() + ":" + userBets.getId() + ":" + expireAt;
/* 405 */     Map<String, Object> value = new HashMap();
/*     */     
/* 407 */     value.put("lotteryId", Integer.valueOf(lottery.getId()));
/* 408 */     value.put("lottery", lottery.getShowName());
/* 409 */     value.put("expect", userBets.getExpect());
/* 410 */     value.put("id", Integer.valueOf(userBets.getId()));
/* 411 */     value.put("status", Integer.valueOf(userBets.getStatus()));
/* 412 */     value.put("money", Double.valueOf(userBets.getMoney()));
/* 413 */     value.put("prizeMoney", userBets.getPrizeMoney());
/*     */     
/* 415 */     String mname = "";
/* 416 */     LotteryPlayRules playRules = this.dataFactory.getLotteryPlayRules(lottery.getId(), userBets.getRuleId());
/* 417 */     LotteryPlayRulesGroup group = this.dataFactory.getLotteryPlayRulesGroup(lottery.getId(), playRules.getGroupId());
/* 418 */     if (playRules != null) {
/* 419 */       mname = "[" + group.getName() + "_" + playRules.getName() + "]";
/*     */     }
/*     */     
/* 422 */     value.put("mname", mname);
/* 423 */     value.put("type", Integer.valueOf(2));
/*     */     
/* 425 */     this.jedisTemplate.hset("USER:BETS_NOTICE", field, JSON.toJSONString(value));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Object[] testUsersBets(UserBets userBets, String openCode, Lottery lottery, boolean cache)
/*     */   {
/* 432 */     LotteryConfig config = this.dataFactory.getLotteryConfig();
/*     */     
/* 434 */     String codes = userBets.getCodes();
/*     */     
/*     */ 
/* 437 */     LotteryPlayRules userBetsPlayRules = this.dataFactory.getLotteryPlayRules(userBets.getLotteryId(), userBets.getRuleId());
/* 438 */     if ((lottery.getType() == 1) && (userBetsPlayRules.getCode().equals("rx3hhzx"))) {
/* 439 */       return rx3hhzxTestUsersBets(userBets, openCode, lottery, cache);
/*     */     }
/*     */     
/*     */ 
/* 443 */     ITicketPlayHandler handler = TicketPlayHandlerContext.getHandler(lottery.getType(), userBetsPlayRules.getCode());
/* 444 */     if (handler == null) {
/* 445 */       log.error("注单{}未找到对应的处理类", Integer.valueOf(userBets.getId()));
/* 446 */       return null;
/*     */     }
/*     */     
/*     */ 
/* 450 */     WinResult result = handler.calculateWinNum(userBets.getId(), codes, openCode);
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
/* 492 */     int winNum = 0;
/* 493 */     double prize = 0.0D;
/* 494 */     if ((result != null) && ((result.getWinNum() > 0) || (CollectionUtils.isNotEmpty(result.getMultipleResults())))) { LotteryPlayRules ruleDefault;
/* 495 */       double[] prizeArrDouble; switch (result.getPlayId()) {
/*     */       case "1_wxzhixzh": case "1_sixzhixzhh": case "1_sixzhixzhq": 
/* 497 */         LotteryPlayRules[] levNum = getZhWinLev(result.getWinNum(), lottery);
/* 498 */         double zhprize = 0.0D;
/* 499 */         for (int i = 0; i < levNum.length; i++) {
/* 500 */           zhprize += PrizeUtils.getBetWinMoney(levNum[i], userBets.getModel(), config
/* 501 */             .getbUnitMoney(), userBets.getCode());
/*     */         }
/*     */         
/* 504 */         prize = MathUtil.multiply(zhprize, userBets.getMultiple());
/*     */         
/* 506 */         winNum = result.getWinNum();
/* 507 */         break;
/*     */       case "1_sxzuxzsh": case "1_sxzuxzlh": case "1_sxzuxzsz": 
/*     */       case "1_sxzuxzlz": case "1_sxzuxzsq": case "1_sxzuxzlq": 
/*     */       case "4_sanxzs": case "4_sanxzl": case "1_sxzuxhzh": 
/*     */       case "1_sxzuxhzz": case "1_sxzuxhzq": 
/* 512 */         String playId = result.getPlayId().substring(result.getPlayId().indexOf("_") + 1, result
/* 513 */           .getPlayId().length());
/* 514 */         LotteryPlayRules rulezx = this.dataFactory.getLotteryPlayRules(lottery.getId(), playId);
/* 515 */         double prizehh = PrizeUtils.getMoney(rulezx, userBets.getModel(), config.getbUnitMoney(), userBets.getCode());
/*     */         
/* 517 */         prize = MathUtil.multiply(MathUtil.multiply(prizehh, result.getWinNum()), userBets
/* 518 */           .getMultiple());
/*     */         
/* 520 */         winNum = result.getWinNum();
/* 521 */         break;
/*     */       case "1_longhuhewq": case "1_longhuhewb": case "1_longhuhews": 
/*     */       case "1_longhuhewg": case "1_longhuheqb": case "1_longhuheqs": 
/*     */       case "1_longhuheqg": case "1_longhuhebs": 
/*     */       case "1_longhuhebg": case "1_longhuhesg": 
/*     */       case "7_lhd_longhuhewq": case "7_lhd_longhuhewb": 
/*     */       case "7_lhd_longhuhews": case "7_lhd_longhuhewg": 
/*     */       case "7_lhd_longhuheqb": case "7_lhd_longhuheqs": 
/*     */       case "7_lhd_longhuheqg": case "7_lhd_longhuhebs": 
/*     */       case "7_lhd_longhuhebg": case "7_lhd_longhuhesg": 
/* 531 */         winNum = result.getWinNum();
/* 532 */         LotteryPlayRules rule = this.dataFactory.getLotteryPlayRules(lottery.getId(), userBets.getRuleId());
/* 533 */         String calPrize = rule.getPrize().split(",")[result.getPrizeIndex()];
/*     */         
/* 535 */         LotteryPlayRules calRule = new LotteryPlayRules();
/* 536 */         calRule.setFixed(rule.getFixed());
/* 537 */         calRule.setPrize(calPrize);
/*     */         
/* 539 */         double prizelh = PrizeUtils.getBetWinMoney(calRule, userBets.getModel(), config
/* 540 */           .getbUnitMoney(), userBets.getCode());
/*     */         
/* 542 */         prize = MathUtil.multiply(MathUtil.multiply(prizelh, result.getWinNum()), userBets
/* 543 */           .getMultiple());
/* 544 */         break;
/*     */       case "2_dds": 
/* 546 */         winNum = result.getWinNum();
/* 547 */         prize = getWinMoneyByCode(result, userBets, config.getbUnitMoney(), HConstants.playPrize.dds11x5);
/*     */         
/* 549 */         break;
/*     */       case "2_czw": 
/* 551 */         winNum = result.getWinNum();
/* 552 */         prize = getWinMoneyByCode(result, userBets, config.getbUnitMoney(), HConstants.playPrize.czw11x5);
/*     */         
/* 554 */         break;
/*     */       case "3_hezhi": 
/* 556 */         winNum = result.getWinNum();
/* 557 */         prize = getWinMoneyByCode(result, userBets, config.getbUnitMoney(), HConstants.playPrize.k3hezhi);
/*     */         
/* 559 */         break;
/*     */       case "5_hezhidx": 
/* 561 */         winNum = result.getWinNum();
/* 562 */         prize = getWinMoneyByCode(result, userBets, config.getbUnitMoney(), HConstants.playPrize.kl8hezhidx);
/*     */         
/* 564 */         break;
/*     */       case "5_jopan": 
/* 566 */         winNum = result.getWinNum();
/* 567 */         prize = getWinMoneyByCode(result, userBets, config.getbUnitMoney(), HConstants.playPrize.kl8jopan);
/*     */         
/* 569 */         break;
/*     */       case "5_sxpan": 
/* 571 */         winNum = result.getWinNum();
/* 572 */         prize = getWinMoneyByCode(result, userBets, config.getbUnitMoney(), HConstants.playPrize.kl8sxpan);
/*     */         
/* 574 */         break;
/*     */       case "5_hezhiwx": 
/* 576 */         winNum = result.getWinNum();
/* 577 */         prize = getWinMoneyByCode(result, userBets, config.getbUnitMoney(), HConstants.playPrize.kl8wx);
/*     */         
/* 579 */         break;
/*     */       case "6_pk10_dxdsgyhz": 
/*     */       case "6_pk10_hzgyhz": 
/*     */       case "6_pk10_hzqshz": 
/* 583 */         if (CollectionUtils.isNotEmpty(result.getMultipleResults())) {
/* 584 */           winNum = result.getWinNum();
/*     */           
/* 586 */           ruleDefault = this.dataFactory.getLotteryPlayRules(lottery.getId(), userBets.getRuleId());
/* 587 */           String[] prizeArr = ruleDefault.getPrize().split(",");
/* 588 */           prizeArrDouble = new double[prizeArr.length];
/* 589 */           for (int i = 0; i < prizeArr.length; i++) {
/* 590 */             prizeArrDouble[i] = Double.valueOf(prizeArr[i]).doubleValue();
/*     */           }
/*     */           
/* 593 */           List<MultipleResult> multipleResults = result.getMultipleResults();
/*     */           
/* 595 */           for (MultipleResult multipleResult : multipleResults)
/* 596 */             if (multipleResult.getNums() > 0)
/*     */             {
/* 598 */               double odds = prizeArrDouble[multipleResult.getOddsIndex()];
/*     */               
/*     */ 
/* 601 */               double multiplePrize = PrizeUtils.getPrize(ruleDefault.getFixed(), userBets.getModel(), config.getbUnitMoney(), userBets.getCode(), odds);
/*     */               
/*     */ 
/* 604 */               multiplePrize = MathUtil.multiply(MathUtil.multiply(multiplePrize, multipleResult.getNums()), userBets.getMultiple());
/*     */               
/* 606 */               prize += multiplePrize;
/*     */             }
/*     */         }
/* 609 */         break;
/*     */       default:
//    LotteryPlayRules ruleDefault;
///*     */         double[] prizeArrDouble;
/* 612 */         if (CollectionUtils.isNotEmpty(result.getMultipleResults())) {
/* 613 */           winNum = result.getWinNum();
/*     */           
/* 615 */           ruleDefault = this.dataFactory.getLotteryPlayRules(lottery.getId(), userBets.getRuleId());
/* 616 */           String[] prizeArr = ruleDefault.getPrize().split(",");
/* 617 */           prizeArrDouble = new double[prizeArr.length];
/* 618 */           for (int i = 0; i < prizeArr.length; i++) {
/* 619 */             prizeArrDouble[i] = Double.valueOf(prizeArr[i]).doubleValue();
/*     */           }
/*     */           
/* 622 */           List<MultipleResult> multipleResults = result.getMultipleResults();
/*     */           
/* 624 */           for (MultipleResult multipleResult : multipleResults) {
/* 625 */             if (multipleResult.getNums() > 0)
/*     */             {
/* 627 */               double odds = prizeArrDouble[multipleResult.getOddsIndex()];
/*     */               
/*     */ 
/* 630 */               double multiplePrize = PrizeUtils.getPrize(ruleDefault.getFixed(), userBets.getModel(), config.getbUnitMoney(), userBets.getCode(), odds);
/*     */               
/*     */ 
/* 633 */               multiplePrize = MathUtil.multiply(MathUtil.multiply(multiplePrize, multipleResult.getNums()), userBets.getMultiple());
/*     */               
/* 635 */               prize += multiplePrize;
/*     */             }
/*     */           }
/*     */         }
/*     */         else {
///* 640 */           LotteryPlayRules
                            ruleDefault = this.dataFactory.getLotteryPlayRules(lottery.getId(), userBets.getRuleId());
/* 641 */           double prizedefailt = PrizeUtils.getBetWinMoney(ruleDefault, userBets.getModel(), config.getbUnitMoney(), userBets.getCode());
/*     */           
/* 643 */           prize = MathUtil.multiply(MathUtil.multiply(prizedefailt, result.getWinNum()), userBets.getMultiple());
/*     */           
/* 645 */           winNum = result.getWinNum();
/*     */         }
/*     */         break;
/*     */       }
/*     */       
/*     */     }
/* 651 */     if ((winNum > 0) && (prize > 0.0D))
/*     */     {
/* 653 */       prize = assertDantiao(lottery, userBets, prize, cache);
/*     */       
/*     */ 
/* 656 */       prize = assertExpectPrize(lottery, userBets, prize, cache);
/*     */     }
/*     */     
/* 659 */     return new Object[] { Integer.valueOf(winNum), Double.valueOf(prize) };
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Object[] rx3hhzxTestUsersBets(UserBets userBets, String openCode, Lottery lottery, boolean cache)
/*     */   {
/* 666 */     LotteryConfig config = this.dataFactory.getLotteryConfig();
/*     */     
/* 668 */     String codes = userBets.getCodes();
/*     */     
/* 670 */     SSCRX3HHZXPlayHanlder hanlder = new SSCRX3HHZXPlayHanlder();
/* 671 */     List<WinResult> results = hanlder.calculateWinNum(userBets.getId(), codes, openCode);
/* 672 */     if ((results == null) || (results.size() <= 0)) {
/* 673 */       return new Object[] { Integer.valueOf(0), Double.valueOf(0.0D) };
/*     */     }
/*     */     
/* 676 */     double totalPrize = 0.0D;
/* 677 */     for (WinResult winResult : results) {
/* 678 */       LotteryPlayRules rule = this.dataFactory.getLotteryPlayRules(lottery.getId(), winResult.getPlayId());
/* 679 */       double rulePrize = PrizeUtils.getBetWinMoney(rule, userBets.getModel(), config.getbUnitMoney(), userBets.getCode());
/*     */       
/*     */ 
/* 682 */       totalPrize += MathUtil.multiply(rulePrize, winResult.getWinNum());
/*     */     }
/*     */     
/* 685 */     totalPrize = MathUtil.multiply(totalPrize, userBets.getMultiple());
/*     */     
/*     */ 
/* 688 */     totalPrize = assertDantiao(lottery, userBets, totalPrize, cache);
/*     */     
/*     */ 
/* 691 */     totalPrize = assertExpectPrize(lottery, userBets, totalPrize, cache);
/*     */     
/* 693 */     return new Object[] { Integer.valueOf(1), Double.valueOf(totalPrize) };
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private double assertDantiao(Lottery lottery, UserBets userBets, double prize, boolean cacheDantiao)
/*     */   {
/* 704 */     double finalPrize = prize;
/* 705 */     if (lottery.getDantiaoMaxPrize() > 0) {
/* 706 */       LotteryPlayRules playRules = this.dataFactory.getLotteryPlayRules(lottery.getId(), userBets.getRuleId());
/* 707 */       if (StringUtils.isNotEmpty(playRules.getDantiao())) {
/* 708 */         int dantiaoNum = getDantiaoNum(userBets, playRules);
/*     */         
/* 710 */         if ((dantiaoNum > 0) && (userBets.getNums() <= dantiaoNum))
/*     */         {
/*     */ 
/* 713 */           String expectKey = userBets.getLotteryId() + "_" + userBets.getExpect() + "_" + userBets.getUserId();
/*     */           
/* 715 */           Double beforeTotalPrize = cacheDantiao ? (Double)expectDantiaoPrize.get(expectKey) : (Double)expectDantiaoUnCachePrize.get(expectKey);
/*     */           
/* 717 */           if (beforeTotalPrize == null) { beforeTotalPrize = Double.valueOf(0.0D);
/*     */           }
/* 719 */           if (beforeTotalPrize.doubleValue() >= lottery.getDantiaoMaxPrize()) {
/* 720 */             finalPrize = 0.0D;
/*     */           }
/*     */           else {
/* 723 */             double expectPrize = beforeTotalPrize.doubleValue() + prize;
/*     */             
/* 725 */             if (expectPrize > lottery.getDantiaoMaxPrize()) {
/* 726 */               finalPrize = MathUtil.subtract(lottery.getDantiaoMaxPrize(), beforeTotalPrize.doubleValue());
/*     */               
/* 728 */               if (finalPrize < 0.0D) {
/* 729 */                 finalPrize = 0.0D;
/*     */               }
/*     */             }
/* 732 */             if (cacheDantiao) {
/* 733 */               expectDantiaoPrize.put(expectKey, Double.valueOf(beforeTotalPrize.doubleValue() + finalPrize));
/*     */             }
/*     */             else {
/* 736 */               expectDantiaoUnCachePrize.put(expectKey, Double.valueOf(beforeTotalPrize.doubleValue() + finalPrize));
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 743 */     if ((cacheDantiao) && (prize != finalPrize)) {
/* 744 */       String warningMsg = lottery.getShowName() + "第" + userBets.getExpect() + ",用户注单" + userBets.getBillno() + "派奖时属于单挑模式，原奖金：" + prize + ",清除后奖金：" + finalPrize;
/* 745 */       log.warn(warningMsg);
/* 746 */       this.mailJob.addWarning(warningMsg);
/*     */     }
/*     */     
/* 749 */     return finalPrize;
/*     */   }
/*     */   
/*     */   private int getDantiaoNum(UserBets userBets, LotteryPlayRules playRules) {
/* 753 */     if (playRules.getTypeId() != 1) {
/* 754 */       return Integer.valueOf(playRules.getDantiao()).intValue();
/*     */     }
/*     */     
/* 757 */     int totalPosition = getSSCBetTotalPosition(userBets);
///*     */     int minPosition;
///*     */     int minPosition;
/* 760 */     int minPosition; switch (playRules.getCode()) {
/*     */     case "rx2fs": 
/*     */     case "rx2ds": 
/*     */     case "rx2zx": 
/* 764 */       minPosition = 2;
/* 765 */       break;
/*     */     case "rx3hhzx": 
/*     */     case "rx3fs": 
/*     */     case "rx3ds": 
/*     */     case "rx3z3": 
/*     */     case "rx3z6": 
/* 771 */       minPosition = 3;
/* 772 */       break;
/*     */     case "rx4fs": 
/*     */     case "rx4ds": 
/* 775 */       minPosition = 4;
/* 776 */       break;
/*     */     
/*     */     case "longhuhewq": 
/*     */     case "longhuhewb": 
/*     */     case "longhuhews": 
/*     */     case "longhuhewg": 
/*     */     case "longhuheqb": 
/*     */     case "longhuheqs": 
/*     */     case "longhuheqg": 
/*     */     case "longhuhebs": 
/*     */     case "longhuhebg": 
/*     */     case "longhuhesg": 
/* 788 */       if (userBets.getCodes().contains("和")) {
/* 789 */         return Integer.valueOf(playRules.getDantiao()).intValue();
/*     */       }
/*     */       
/* 792 */       return 0;
/*     */     
/*     */ 
/*     */     case "sscniuniu": 
/* 796 */       if (userBets.getCodes().contains("五条")) {
/* 797 */         return Integer.valueOf(playRules.getDantiao().split(",")[0]).intValue();
/*     */       }
/* 799 */       if (userBets.getCodes().contains("炸弹")) {
/* 800 */         return Integer.valueOf(playRules.getDantiao().split(",")[1]).intValue();
/*     */       }
/* 802 */       if (userBets.getCodes().contains("葫芦")) {
/* 803 */         return Integer.valueOf(playRules.getDantiao().split(",")[2]).intValue();
/*     */       }
/*     */       
/* 806 */       return 0;
/*     */     
/*     */     default: 
/* 809 */       return Integer.valueOf(playRules.getDantiao()).intValue();
/*     */     }
///*     */     int minPosition;
/* 812 */     if (minPosition > totalPosition) {
/* 813 */       log.error("玩法{}配置错误,单挑规则配置错误[{}]", playRules.getName(), playRules.getDantiao());
/* 814 */       return 0;
/*     */     }
/*     */     
/* 817 */     String[] dantiaoNums = playRules.getDantiao().split(",");
/* 818 */     return Integer.valueOf(dantiaoNums[(totalPosition - minPosition)]).intValue();
/*     */   }
/*     */   
/*     */   private int getSSCBetTotalPosition(UserBets userBets) {
/* 822 */     LotteryPlayRules playRules = this.dataFactory.getLotteryPlayRules(userBets.getLotteryId(), userBets.getRuleId());
/* 823 */     int totalPosition = 0;
/*     */     
/*     */ 
/* 826 */     switch (playRules.getCode()) {
/*     */     case "rx2fs": 
/*     */     case "rx3fs": 
/*     */     case "rx4fs": 
/* 830 */       String[] codes = userBets.getCodes().split(",");
/* 831 */       for (String code : codes) {
/* 832 */         if (NumberUtils.isDigits(code)) {
/* 833 */           totalPosition++;
/*     */         }
/*     */       }
/* 836 */       return totalPosition;
/*     */     case "rx3hhzx": 
/*     */     case "rx2ds": 
/*     */     case "rx2zx": 
/*     */     case "rx3ds": 
/*     */     case "rx3z3": 
/*     */     case "rx3z6": 
/*     */     case "rx4ds": 
/* 844 */       String format = StringUtil.substring(userBets.getCodes(), "[", "]", false);
/* 845 */       return StringUtils.countMatches(format, "√");
/*     */     }
/* 847 */     return 0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private double assertExpectPrize(Lottery lottery, UserBets userBets, double prize, boolean cachePrize)
/*     */   {
/* 856 */     if ((!cachePrize) || (lottery.getExpectMaxPrize() <= 0)) {
/* 857 */       return prize;
/*     */     }
/*     */     
/* 860 */     String prizeKey = userBets.getLotteryId() + "_" + userBets.getExpect() + "_" + userBets.getUserId();
/* 861 */     Double beforeTotalPrize = (Double)userExpectPrize.get(prizeKey);
/* 862 */     if (beforeTotalPrize == null) { beforeTotalPrize = Double.valueOf(0.0D);
/*     */     }
/* 864 */     double expectTotalPrize = beforeTotalPrize.doubleValue() + prize;
/*     */     
/* 866 */     double finalPrize = prize;
/*     */     
/* 868 */     if (expectTotalPrize > lottery.getExpectMaxPrize()) {
/* 869 */       finalPrize = MathUtil.subtract(lottery.getExpectMaxPrize(), beforeTotalPrize.doubleValue());
/*     */       
/* 871 */       if (finalPrize < 0.0D) {
/* 872 */         finalPrize = 0.0D;
/*     */       }
/*     */     }
/* 875 */     if (prize != finalPrize) {
/* 876 */       this.mailJob.sendExpectExceedPrize(userBets, prize, finalPrize);
/*     */     }
/*     */     
/* 879 */     userExpectPrize.put(prizeKey, Double.valueOf(beforeTotalPrize.doubleValue() + finalPrize));
/*     */     
/* 881 */     return finalPrize;
/*     */   }
/*     */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/content/biz/impl/UserBetsSettleServiceImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */