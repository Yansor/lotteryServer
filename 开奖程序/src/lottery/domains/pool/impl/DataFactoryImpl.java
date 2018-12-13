/*     */ package lottery.domains.pool.impl;
/*     */ 
/*     */ import java.util.Iterator;
import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import lottery.domains.content.entity.DbServerSync;
/*     */ import lottery.domains.content.entity.Lottery;
/*     */ import lottery.domains.content.entity.LotteryOpenTime;
/*     */ import lottery.domains.content.entity.LotteryPlayRules;
/*     */ import lottery.domains.content.entity.LotteryPlayRulesConfig;
/*     */ import lottery.domains.content.entity.LotteryPlayRulesGroup;
/*     */ import lottery.domains.content.entity.LotteryPlayRulesGroupConfig;
/*     */ import lottery.domains.content.entity.User;
/*     */ import lottery.domains.content.global.DbServerSyncEnum;
/*     */ import lottery.domains.content.vo.config.CodeConfig;
/*     */ import lottery.domains.content.vo.config.LotteryConfig;
/*     */ import lottery.domains.content.vo.config.MailConfig;
/*     */ import lottery.domains.content.vo.config.PlanConfig;
/*     */ import lottery.domains.content.vo.user.UserVO;
/*     */ import org.slf4j.Logger;
/*     */ import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.text.html.HTMLDocument;

/*     */
/*     */ @org.springframework.stereotype.Component
/*     */ public class DataFactoryImpl implements lottery.domains.pool.DataFactory, org.springframework.beans.factory.InitializingBean
/*     */ {
/*  26 */   private static final Logger log = org.slf4j.LoggerFactory.getLogger(DataFactoryImpl.class);
/*     */   
/*     */   public void afterPropertiesSet() throws Exception
/*     */   {
/*  30 */     init();
/*     */   }
/*     */   
/*     */   public void init()
/*     */   {
/*  35 */     log.info("init datafactory....start, current time " + new javautils.date.Moment().toSimpleTime());
/*  36 */     initSysConfig();
/*  37 */     initUser();
/*     */     
/*  39 */     initLottery();
/*  40 */     initLotteryOpenTime();
/*  41 */     initLotteryPlayRules();
/*  42 */     initLotteryOpenCode();
/*     */     
/*  44 */     log.info("init datafactory....done");
/*     */   }
/*     */   
/*  47 */   private Map<String, DbServerSync> DbServerSyncMap = new java.util.HashMap();
/*     */   
/*  49 */   private static volatile boolean isRunningSyncInit = false;
/*  50 */   private static Object syncInitLock = new Object();
/*     */   @Autowired
/*     */   private lottery.domains.content.dao.DbServerSyncDao dbServerSyncDao;
/*     */   @Autowired
/*     */   private lottery.domains.content.dao.SysConfigDao sysConfigDao;
/*     */   
/*     */   /* Error */
/*     */   @org.springframework.scheduling.annotation.Scheduled(cron="0,10,20,30,40,50 * * * * *")
/*     */   public void syncInitJob()
/*     */   {
/*     */     // Byte code:
/*     */     //   0: getstatic 47	lottery/domains/pool/impl/DataFactoryImpl:syncInitLock	Ljava/lang/Object;
/*     */     //   3: dup
/*     */     //   4: astore_1
/*     */     //   5: monitorenter
/*     */     //   6: getstatic 48	lottery/domains/pool/impl/DataFactoryImpl:isRunningSyncInit	Z
/*     */     //   9: iconst_1
/*     */     //   10: if_icmpne +6 -> 16
/*     */     //   13: aload_1
/*     */     //   14: monitorexit
/*     */     //   15: return
/*     */     //   16: iconst_1
/*     */     //   17: putstatic 48	lottery/domains/pool/impl/DataFactoryImpl:isRunningSyncInit	Z
/*     */     //   20: aload_1
/*     */     //   21: monitorexit
/*     */     //   22: goto +8 -> 30
/*     */     //   25: astore_2
/*     */     //   26: aload_1
/*     */     //   27: monitorexit
/*     */     //   28: aload_2
/*     */     //   29: athrow
/*     */     //   30: aload_0
/*     */     //   31: invokespecial 49	lottery/domains/pool/impl/DataFactoryImpl:SyncInit	()V
/*     */     //   34: iconst_0
/*     */     //   35: putstatic 48	lottery/domains/pool/impl/DataFactoryImpl:isRunningSyncInit	Z
/*     */     //   38: goto +10 -> 48
/*     */     //   41: astore_3
/*     */     //   42: iconst_0
/*     */     //   43: putstatic 48	lottery/domains/pool/impl/DataFactoryImpl:isRunningSyncInit	Z
/*     */     //   46: aload_3
/*     */     //   47: athrow
/*     */     //   48: return
/*     */     // Line number table:
/*     */     //   Java source line #57	-> byte code offset #0
/*     */     //   Java source line #58	-> byte code offset #6
/*     */     //   Java source line #60	-> byte code offset #13
/*     */     //   Java source line #62	-> byte code offset #16
/*     */     //   Java source line #63	-> byte code offset #20
/*     */     //   Java source line #66	-> byte code offset #30
/*     */     //   Java source line #68	-> byte code offset #34
/*     */     //   Java source line #69	-> byte code offset #38
/*     */     //   Java source line #68	-> byte code offset #41
/*     */     //   Java source line #70	-> byte code offset #48
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	49	0	this	DataFactoryImpl
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
/*     */   private void SyncInit()
/*     */   {
/*     */     try
/*     */     {
/*  74 */       List<DbServerSync> list = this.dbServerSyncDao.listAll();
/*  75 */       for (DbServerSync serverBean : list) {
/*  76 */         String key = serverBean.getKey();
/*  77 */         if (!this.DbServerSyncMap.containsKey(key)) {
/*  78 */           this.DbServerSyncMap.put(key, serverBean);
/*     */         }
/*  80 */         DbServerSync thisBean = (DbServerSync)this.DbServerSyncMap.get(key);
/*     */         
/*  82 */         if ((serverBean.getLastModTime() != null) && 
/*  83 */           (!serverBean.getLastModTime().equals(thisBean.getLastModTime())))
/*     */         {
/*  85 */           log.debug("有新的同步数据：" + key);
/*  86 */           if (DbServerSyncEnum.LOTTERY.name().equals(key)) {
/*  87 */             initLottery();
/*     */           }
/*  89 */           if (DbServerSyncEnum.LOTTERY_OPEN_TIME.name().equals(key)) {
/*  90 */             initLotteryOpenTime();
/*     */           }
/*  92 */           if (DbServerSyncEnum.LOTTERY_PLAY_RULES.name().equals(key)) {
/*  93 */             initLotteryPlayRules();
/*     */           }
/*  95 */           if (DbServerSyncEnum.SYS_CONFIG.name().equals(key)) {
/*  96 */             initSysConfig();
/*     */           }
/*  98 */           this.DbServerSyncMap.put(key, serverBean);
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 103 */       log.error("同步失败！", e);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 112 */   private CodeConfig codeConfig = new CodeConfig();
/* 113 */   private LotteryConfig lotteryConfig = new LotteryConfig();
/* 114 */   private PlanConfig planConfig = new PlanConfig();
/* 115 */   private MailConfig mailConfig = new MailConfig();
/* 116 */   private lottery.domains.content.vo.config.SelfLotteryConfig selfLotteryConfig = new lottery.domains.content.vo.config.SelfLotteryConfig();
/*     */   @Autowired
/*     */   private lottery.domains.content.dao.LotteryDao lotteryDao;
/*     */   
/*     */   public void initSysConfig() {
/* 121 */     try { List<lottery.domains.content.entity.SysConfig> list = this.sysConfigDao.listAll();
/* 122 */       for (lottery.domains.content.entity.SysConfig bean : list) {
/* 123 */         String group = bean.getGroup();
/* 124 */         String key = bean.getKey();
/* 125 */         String value = bean.getValue();
///* 126 */         int i;
lottery.domains.content.vo.user.SysCodeRangeVO range; if ("CODE".equals(group)) {
/* 127 */           if ("SYS_MIN_POINT".equals(key)) {
/* 128 */             this.codeConfig.setSysMinLp(Double.valueOf(value).doubleValue());
/*     */           }
/* 130 */           if ("SYS_MIN_CODE".equals(key)) {
/* 131 */             this.codeConfig.setSysMinCode(Integer.parseInt(value));
/*     */           }
/* 133 */           if ("SYS_CODE".equals(key)) {
/* 134 */             this.codeConfig.setSysCode(Integer.parseInt(value));
/*     */           }
/* 136 */           if ("SYS_LOCATE_POINT".equals(key)) {
/* 137 */             this.codeConfig.setSysLp(Double.parseDouble(value));
/*     */           }
/* 139 */           if ("SYS_NOT_LOCATE_POINT".equals(key)) {
/* 140 */             this.codeConfig.setSysNlp(Double.parseDouble(value));
/*     */           }
/* 142 */           if (("SYS_RANGE".equals(key)) && 
/* 143 */             (org.apache.commons.lang.StringUtils.isNotEmpty(value))) {
/* 144 */             String[] ranges = value.split("\\|");
/* 145 */             List<lottery.domains.content.vo.user.SysCodeRangeVO> rlist = new java.util.ArrayList();
/* 146 */             for (int i = 0; i < ranges.length; i++) {
/* 147 */               String[] values = ranges[i].split("~");
/* 148 */               range = new lottery.domains.content.vo.user.SysCodeRangeVO();
/* 149 */               range.setMinPoint(Double.parseDouble(values[0]));
/* 150 */               range.setMaxPoint(Double.parseDouble(values[1]));
/* 151 */               rlist.add(range);
/*     */             }
/* 153 */             this.codeConfig.setSysCodeRange(rlist);
/*     */           }
/*     */         }
/*     */         
/* 157 */         if ("LOTTERY".equals(group)) {
/* 158 */           if ("BET_UNIT_MONEY".equals(key)) {
/* 159 */             this.lotteryConfig.setbUnitMoney(Integer.parseInt(value));
/*     */           }
/* 161 */           if ("FEN_MODEL_DOWN_CODE".equals(key)) {
/* 162 */             this.lotteryConfig.setFenModelDownCode(Integer.parseInt(value));
/*     */           }
/* 164 */           if ("LI_MODEL_DOWN_CODE".equals(key)) {
/* 165 */             this.lotteryConfig.setLiModelDownCode(Integer.parseInt(value));
/*     */           }
/* 167 */           if ("AUTO_HIT_RANKING".equals(key)) {
/* 168 */             this.lotteryConfig.setAutoHitRanking(Integer.parseInt(value) == 1);
/*     */           }
/* 170 */           if ("HIT_RANKING_SIZE".equals(key)) {
/* 171 */             int hitRankingSize = Integer.parseInt(value);
/* 172 */             if (hitRankingSize < 0) {
/* 173 */               hitRankingSize = 10;
/*     */             }
/* 175 */             if (hitRankingSize > 10) {
/* 176 */               hitRankingSize = 10;
/*     */             }
/* 178 */             this.lotteryConfig.setHitRankingSize(hitRankingSize); } }
///*     */         Object object;
/*     */         String[] arr;
/* 181 */         if ("PLAN".equals(group)) {
/* 182 */           if ("MIN_MONEY".equals(key)) {
/* 183 */             double minMoney = Double.parseDouble(value);
/* 184 */             this.planConfig.setMinMoney(minMoney);
/*     */           }
/* 186 */           if ("TITLE".equals(key)) {
/* 187 */             net.sf.json.JSONArray jsonArray = net.sf.json.JSONArray.fromObject(value);
/* 188 */             List<String> title = new java.util.ArrayList();
/* 189 */             for (Iterator i = jsonArray.iterator(); i.hasNext();) { Object object = i.next();
/* 190 */               title.add((String)object);
/*     */             }
/* 192 */             this.planConfig.setTitle(title);
/*     */           }
/* 194 */           if ("RATE".equals(key)) {
/* 195 */             List<Integer> rate = new java.util.ArrayList();
/* 196 */             String[] temp = value.split(",");
/* 197 */             String [] i = temp;int size  = i.length; for (int index = 0; index < size; index++) { String v = i[index];
/* 198 */               rate.add(Integer.valueOf(Integer.parseInt(v)));
/*     */             }
/* 200 */             this.planConfig.setRate(rate);
/*     */           }
/* 202 */           if ("LEVEL".equals(key)) {
/* 203 */             List<Integer> level = new java.util.ArrayList();
/* 204 */             arr = value.split(",");
/* 205 */            String []  i = arr;
int object = i.length; for (int index = 0; index < object; index++) { String v = i[index];
/* 206 */               level.add(Integer.valueOf(Integer.parseInt(v)));
/*     */             }

/* 208 */             this.planConfig.setLevel(level);
/*     */           }
/*     */         }
/* 211 */         if ("MAIL".equals(group)) {
/* 212 */           if ("PASSWORD".equals(key)) {
/* 213 */             this.mailConfig.setPassword(value.trim());
/*     */           }
/* 215 */           if ("PERSONAL".equals(key)) {
/* 216 */             this.mailConfig.setPersonal(value.trim());
/*     */           }
/* 218 */           if ("SMTP_HOST".equals(key)) {
/* 219 */             this.mailConfig.setHost(value.trim());
/*     */           }
/* 221 */           if ("USERNAME".equals(key)) {
/* 222 */             this.mailConfig.setUsername(value.trim());
/*     */           }
/* 224 */           if ("BET".equals(key)) {
/* 225 */             this.mailConfig.setBet(Integer.valueOf(value).intValue());
/*     */           }
/* 227 */           if ("OPEN".equals(key)) {
/* 228 */             this.mailConfig.setOpen(Integer.valueOf(value).intValue());
/*     */           }
/* 230 */           if ("RECHARGE".equals(key)) {
/* 231 */             this.mailConfig.setRecharge(Integer.valueOf(value).intValue());
/*     */           }
/* 233 */           if ("SYS_RECEIVE_MAILS".equals(key)) {
/* 234 */             this.mailConfig.getReceiveMails().clear();
/* 235 */             if (org.apache.commons.lang.StringUtils.isNotEmpty(value)) {
/* 236 */               String[] mails = value.split(",");
/* 237 */               arr = mails;int i = arr.length; for (int object = 0; object < i; object++) { String mail = arr[object];
/* 238 */                 this.mailConfig.addReceiveMail(mail.trim());
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/* 243 */         if ("SELF_LOTTERY".equals(group)) {
/* 244 */           if ("CONTROL".equals(key)) {
/* 245 */             this.selfLotteryConfig.setControl(Integer.valueOf(value).intValue() == 1);
/*     */           }
/* 247 */           if ("PROBABILITY".equals(key)) {
/* 248 */             this.selfLotteryConfig.setProbability(Double.valueOf(value).doubleValue());
/*     */           }
/*     */         }
/*     */       }
/* 252 */       log.info("初始化系统配置表完成！");
/*     */     } catch (Exception e) {
/* 254 */       log.error("初始化系统配置表失败！", e);
/*     */     }
/*     */   }
/*     */   
/*     */   public CodeConfig getCodeConfig()
/*     */   {
/* 260 */     return this.codeConfig;
/*     */   }
/*     */   
/*     */   public LotteryConfig getLotteryConfig()
/*     */   {
/* 265 */     return this.lotteryConfig;
/*     */   }
/*     */   
/*     */   public PlanConfig getPlanConfig()
/*     */   {
/* 270 */     return this.planConfig;
/*     */   }
/*     */   
/*     */   public MailConfig getMailConfig()
/*     */   {
/* 275 */     return this.mailConfig;
/*     */   }
/*     */   
/*     */   public lottery.domains.content.vo.config.SelfLotteryConfig getSelfLotteryConfig()
/*     */   {
/* 280 */     return this.selfLotteryConfig;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 289 */   private Map<Integer, Lottery> lotteryMap = new LinkedHashMap();
/*     */   @Autowired
/*     */   private lottery.domains.content.dao.LotteryOpenTimeDao lotteryOpenTimeDao;
/*     */   
/*     */   public void initLottery() {
/* 294 */     try { List<Lottery> list = this.lotteryDao.listAll();
/* 295 */       Map<Integer, Lottery> tmpMap = new LinkedHashMap();
/* 296 */       for (Lottery lottery : list) {
/* 297 */         tmpMap.put(Integer.valueOf(lottery.getId()), lottery);
/*     */       }
/* 299 */       this.lotteryMap = tmpMap;
/* 300 */       log.debug("初始化彩票信息完成！");
/*     */     } catch (Exception e) {
/* 302 */       log.error("初始化彩票信息失败！");
/*     */     }
/*     */   }
/*     */   
/*     */   public Lottery getLottery(int id)
/*     */   {
/* 308 */     if (this.lotteryMap.containsKey(Integer.valueOf(id))) {
/* 309 */       return (Lottery)this.lotteryMap.get(Integer.valueOf(id));
/*     */     }
/* 311 */     return null;
/*     */   }
/*     */   
/*     */   public Lottery getLottery(String shortName)
/*     */   {
/* 316 */     Object[] keys = this.lotteryMap.keySet().toArray();
/* 317 */     for (Object o : keys) {
/* 318 */       Lottery lottery = (Lottery)this.lotteryMap.get(o);
/* 319 */       if (lottery.getShortName().equals(shortName)) {
/* 320 */         return lottery;
/*     */       }
/*     */     }
/* 323 */     return null;
/*     */   }
/*     */   
/*     */   public List<Lottery> listLottery()
/*     */   {
/* 328 */     List<Lottery> list = new java.util.LinkedList();
/* 329 */     Object[] keys = this.lotteryMap.keySet().toArray();
/* 330 */     for (Object o : keys) {
/* 331 */       list.add(this.lotteryMap.get(o));
/*     */     }
/* 333 */     return list;
/*     */   }
/*     */   
/*     */   public List<Lottery> listLottery(int type)
/*     */   {
/* 338 */     List<Lottery> list = new java.util.LinkedList();
/* 339 */     Object[] keys = this.lotteryMap.keySet().toArray();
/* 340 */     for (Object o : keys) {
/* 341 */       Lottery lottery = (Lottery)this.lotteryMap.get(o);
/* 342 */       if (lottery.getType() == type) {
/* 343 */         list.add(lottery);
/*     */       }
/*     */     }
/* 346 */     return list;
/*     */   }
/*     */   
/*     */   public List<Lottery> listSelfLottery()
/*     */   {
/* 351 */     List<Lottery> list = new java.util.LinkedList();
/* 352 */     Object[] keys = this.lotteryMap.keySet().toArray();
/* 353 */     for (Object o : keys) {
/* 354 */       Lottery lottery = (Lottery)this.lotteryMap.get(o);
/* 355 */       if (lottery.getSelf() == 1) {
/* 356 */         list.add(lottery);
/*     */       }
/*     */     }
/* 359 */     return list;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 368 */   private List<LotteryOpenTime> lotteryOpenTimeList = new java.util.LinkedList();
/*     */   @Autowired
/*     */   private lottery.domains.content.biz.LotteryPlayRulesService playRulesService;
/*     */   
/*     */   public void initLotteryOpenTime() {
/* 373 */     try { List<LotteryOpenTime> list = this.lotteryOpenTimeDao.listAll();
/* 374 */       if (this.lotteryOpenTimeList != null) {
/* 375 */         this.lotteryOpenTimeList.clear();
/*     */       }
/* 377 */       this.lotteryOpenTimeList.addAll(list);
/* 378 */       log.debug("初始化彩票开奖时间信息完成！");
/*     */     } catch (Exception e) {
/* 380 */       log.error("初始化彩票开奖时间信息失败！");
/*     */     }
/*     */   }
/*     */   
/*     */   public List<LotteryOpenTime> listLotteryOpenTime(String lottery)
/*     */   {
/* 386 */     List<LotteryOpenTime> list = new java.util.LinkedList();
/* 387 */     for (LotteryOpenTime tmpBean : this.lotteryOpenTimeList) {
/* 388 */       if (tmpBean.getLottery().equals(lottery)) {
/* 389 */         list.add(tmpBean);
/*     */       }
/*     */     }
/* 392 */     return list;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   @Autowired
/*     */   private lottery.domains.content.biz.LotteryPlayRulesConfigService playRulesConfigService;
/*     */   
/*     */ 
/*     */   @Autowired
/*     */   private lottery.domains.content.biz.LotteryPlayRulesGroupService playRulesGroupService;
/*     */   
/*     */ 
/*     */   @Autowired
/*     */   private lottery.domains.content.biz.LotteryPlayRulesGroupConfigService playRulesGroupConfigService;
/*     */   
/* 408 */   private Map<Integer, Map<Integer, LotteryPlayRules>> playRulesMap = new LinkedHashMap();
/* 409 */   private Map<Integer, Map<Integer, LotteryPlayRulesGroup>> playRulesGroupMap = new LinkedHashMap();
/*     */   @Autowired
/*     */   private lottery.domains.content.biz.LotteryOpenCodeService lotteryOpenCodeService;
/*     */   @Autowired
/*     */   private lottery.domains.content.dao.UserDao uDao;
/*     */   
/* 415 */   public void initLotteryPlayRules() { try { List<Lottery> lotteries = this.lotteryDao.listAll();
/* 416 */       if (org.apache.commons.collections.CollectionUtils.isEmpty(lotteries)) {
/* 417 */         return;
/*     */       }
/*     */       
/* 420 */       List<LotteryPlayRules> rules = this.playRulesService.listAll();
/* 421 */       List<LotteryPlayRulesConfig> rulesConfigs = this.playRulesConfigService.listAll();
/* 422 */       List<LotteryPlayRulesGroup> groups = this.playRulesGroupService.listAll();
/* 423 */       List<LotteryPlayRulesGroupConfig> groupConfigs = this.playRulesGroupConfigService.listAll();
/*     */       
/*     */ 
/* 426 */       Map<Integer, Map<Integer, LotteryPlayRules>> playRulesMapTmp = groupPlayRulesData(lotteries, rules, rulesConfigs);
/*     */       
/* 428 */       Map<Integer, Map<Integer, LotteryPlayRulesGroup>> playRulesGroupMapTmp = groupPlayRulesGroupData(lotteries, groups, groupConfigs);
/*     */       
/*     */ 
/* 431 */       this.playRulesMap = playRulesMapTmp;
/* 432 */       this.playRulesGroupMap = playRulesGroupMapTmp;
/* 433 */       log.debug("初始化彩票玩法规则完成！");
/*     */     } catch (Exception e) {
/* 435 */       log.error("初始化彩票玩法规则失败！");
/*     */     }
/*     */   }
/*     */   
/*     */   public LotteryPlayRules getLotteryPlayRules(int lotteryId, int ruleId)
/*     */   {
/* 441 */     Map<Integer, LotteryPlayRules> lotteryRules = (Map)this.playRulesMap.get(Integer.valueOf(lotteryId));
/* 442 */     if (lotteryRules == null) { return null;
/*     */     }
/* 444 */     return (LotteryPlayRules)lotteryRules.get(Integer.valueOf(ruleId));
/*     */   }
/*     */   
/*     */   public LotteryPlayRules getLotteryPlayRules(int lotteryId, String code)
/*     */   {
/* 449 */     Map<Integer, LotteryPlayRules> lotteryRules = (Map)this.playRulesMap.get(Integer.valueOf(lotteryId));
/* 450 */     if (lotteryRules == null) { return null;
/*     */     }
/* 452 */     java.util.Collection<LotteryPlayRules> rules = lotteryRules.values();
/* 453 */     for (LotteryPlayRules rule : rules) {
/* 454 */       if (code.equalsIgnoreCase(rule.getCode())) {
/* 455 */         return rule;
/*     */       }
/*     */     }
/*     */     
/* 459 */     return null;
/*     */   }
/*     */   
/*     */   public LotteryPlayRulesGroup getLotteryPlayRulesGroup(int lotteryId, int groupId)
/*     */   {
/* 464 */     Map<Integer, LotteryPlayRulesGroup> lotteryGroups = (Map)this.playRulesGroupMap.get(Integer.valueOf(lotteryId));
/* 465 */     if (lotteryGroups == null) { return null;
/*     */     }
/* 467 */     return (LotteryPlayRulesGroup)lotteryGroups.get(Integer.valueOf(groupId));
/*     */   }
/*     */   
/*     */ 
/*     */   private Map<Integer, Map<Integer, LotteryPlayRules>> groupPlayRulesData(List<Lottery> lotteries, List<LotteryPlayRules> rules, List<LotteryPlayRulesConfig> rulesConfigs)
/*     */   {
/* 473 */     Map<Integer, Map<Integer, LotteryPlayRules>> playRulesMapTmp = new LinkedHashMap();
/*     */     
/* 475 */     for (Lottery lottery : lotteries)
/*     */     {
/* 477 */       Map<Integer, LotteryPlayRules> lotteryRules = new LinkedHashMap();
/*     */       
/* 479 */       for (LotteryPlayRules rule : rules) {
/* 480 */         if (rule.getTypeId() == lottery.getType())
/*     */         {
/*     */ 
/*     */ 
/* 484 */           LotteryPlayRules tmpRule = new LotteryPlayRules(rule);
/*     */           
/* 486 */           for (LotteryPlayRulesConfig config : rulesConfigs) {
/* 487 */             if ((config.getRuleId() == rule.getId()) && (config.getLotteryId() == lottery.getId())) {
/* 488 */               tmpRule.setMinNum(config.getMinNum());
/* 489 */               tmpRule.setMaxNum(config.getMaxNum());
/* 490 */               tmpRule.setStatus(config.getStatus());
/* 491 */               tmpRule.setPrize(config.getPrize());
/*     */             }
/*     */           }
/*     */           
/* 495 */           lotteryRules.put(Integer.valueOf(rule.getId()), tmpRule);
/*     */         }
/*     */       }
/* 498 */       playRulesMapTmp.put(Integer.valueOf(lottery.getId()), lotteryRules);
/*     */     }
/*     */     
/* 501 */     return playRulesMapTmp;
/*     */   }
/*     */   
/*     */ 
/*     */   private Map<Integer, Map<Integer, LotteryPlayRulesGroup>> groupPlayRulesGroupData(List<Lottery> lotteries, List<LotteryPlayRulesGroup> groups, List<LotteryPlayRulesGroupConfig> groupsConfigs)
/*     */   {
/* 507 */     Map<Integer, Map<Integer, LotteryPlayRulesGroup>> playRulesGroupMapTmp = new LinkedHashMap();
/*     */     
/* 509 */     for (Lottery lottery : lotteries)
/*     */     {
/* 511 */       Map<Integer, LotteryPlayRulesGroup> lotteryGroups = new LinkedHashMap();
/*     */       
/* 513 */       for (LotteryPlayRulesGroup group : groups) {
/* 514 */         if (group.getTypeId() == lottery.getType())
/*     */         {
/*     */ 
/*     */ 
/* 518 */           LotteryPlayRulesGroup tmpGroup = new LotteryPlayRulesGroup(group);
/*     */           
/* 520 */           for (LotteryPlayRulesGroupConfig config : groupsConfigs) {
/* 521 */             if ((config.getGroupId() == group.getId()) && (config.getLotteryId() == lottery.getId())) {
/* 522 */               tmpGroup.setStatus(config.getStatus());
/*     */             }
/*     */           }
/*     */           
/* 526 */           lotteryGroups.put(Integer.valueOf(group.getId()), tmpGroup);
/*     */         }
/*     */       }
/* 529 */       playRulesGroupMapTmp.put(Integer.valueOf(lottery.getId()), lotteryGroups);
/*     */     }
/*     */     
/* 532 */     return playRulesGroupMapTmp;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void initLotteryOpenCode()
/*     */   {
/* 541 */     this.lotteryOpenCodeService.initLotteryOpenCode();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 550 */   private Map<Integer, UserVO> userMap = new LinkedHashMap();
/*     */   
/*     */   public void initUser()
/*     */   {
/*     */     try {
/* 555 */       List<User> list = this.uDao.listAll();
/* 556 */       Map<Integer, UserVO> tmpMap = new LinkedHashMap();
/* 557 */       for (User user : list) {
/* 558 */         tmpMap.put(Integer.valueOf(user.getId()), new UserVO(user.getId(), user.getUsername()));
/*     */       }
/* 560 */       this.userMap = tmpMap;
/* 561 */       log.info("初始化用户基础信息完成！");
/*     */     } catch (Exception e) {
/* 563 */       log.error("初始化用户基础信息失败！");
/*     */     }
/*     */   }
/*     */   
/*     */   public UserVO getUser(int id)
/*     */   {
/* 569 */     if (this.userMap.containsKey(Integer.valueOf(id))) {
/* 570 */       return (UserVO)this.userMap.get(Integer.valueOf(id));
/*     */     }
/* 572 */     User user = this.uDao.getById(id);
/* 573 */     if (user != null) {
/* 574 */       this.userMap.put(Integer.valueOf(user.getId()), new UserVO(user.getId(), user.getUsername()));
/* 575 */       return (UserVO)this.userMap.get(Integer.valueOf(id));
/*     */     }
/* 577 */     return null;
/*     */   }
/*     */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/pool/impl/DataFactoryImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */