package lottery.domains.pool.impl;

import com.alibaba.fastjson.JSON;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javautils.StringUtil;
import javautils.date.Moment;
import javautils.encrypt.PasswordUtil;
import javax.servlet.ServletContext;
import lottery.domains.content.biz.ActivityFirstRechargeConfigService;
import lottery.domains.content.biz.ActivityRedPacketRainTimeService;
import lottery.domains.content.biz.PaymentCardService;
import lottery.domains.content.biz.PaymentChannelService;
import lottery.domains.content.dao.DbServerSyncDao;
import lottery.domains.content.dao.GameTypeDao;
import lottery.domains.content.dao.LotteryDao;
import lottery.domains.content.dao.LotteryOpenTimeDao;
import lottery.domains.content.dao.LotteryPlayRulesDao;
import lottery.domains.content.dao.LotteryPlayRulesGroupDao;
import lottery.domains.content.dao.LotteryTypeDao;
import lottery.domains.content.dao.PaymentBankDao;
import lottery.domains.content.dao.SysConfigDao;
import lottery.domains.content.dao.SysPlatformDao;
import lottery.domains.content.dao.UserDao;
import lottery.domains.content.dao.UserGameAccountDao;
import lottery.domains.content.entity.ActivityFirstRechargeConfig;
import lottery.domains.content.entity.DbServerSync;
import lottery.domains.content.entity.GameType;
import lottery.domains.content.entity.Lottery;
import lottery.domains.content.entity.LotteryOpenTime;
import lottery.domains.content.entity.LotteryPlayRules;
import lottery.domains.content.entity.LotteryPlayRulesGroup;
import lottery.domains.content.entity.LotteryType;
import lottery.domains.content.entity.PaymentBank;
import lottery.domains.content.entity.PaymentCard;
import lottery.domains.content.entity.PaymentChannel;
import lottery.domains.content.entity.SysConfig;
import lottery.domains.content.entity.SysPlatform;
import lottery.domains.content.entity.User;
import lottery.domains.content.entity.UserGameAccount;
import lottery.domains.content.entity.activity.ActivityFirstRechargeConfigRule;
import lottery.domains.content.entity.activity.ActivityFirstRechargeConfigVO;
import lottery.domains.content.global.DbServerSyncEnum;
import lottery.domains.content.vo.config.AdminCDNConfig;
import lottery.domains.content.vo.config.AdminGlobalConfig;
import lottery.domains.content.vo.config.AdminGoogleConfig;
import lottery.domains.content.vo.config.CodeConfig;
import lottery.domains.content.vo.config.DailySettleConfig;
import lottery.domains.content.vo.config.DividendConfig;
import lottery.domains.content.vo.config.DividendConfigRule;
import lottery.domains.content.vo.config.GameDividendConfig;
import lottery.domains.content.vo.config.GameDividendConfigRule;
import lottery.domains.content.vo.config.LotteryConfig;
import lottery.domains.content.vo.config.MailConfig;
import lottery.domains.content.vo.config.PlanConfig;
import lottery.domains.content.vo.config.PortalCDNConfig;
import lottery.domains.content.vo.config.RechargeConfig;
import lottery.domains.content.vo.config.RegistConfig;
import lottery.domains.content.vo.config.VipConfig;
import lottery.domains.content.vo.config.WithdrawConfig;
import lottery.domains.content.vo.payment.PaymentCardVO;
import lottery.domains.content.vo.payment.PaymentChannelSimpleVO;
import lottery.domains.content.vo.payment.PaymentChannelVO;
import lottery.domains.content.vo.user.SysCodeRangeVO;
import lottery.domains.content.vo.user.UserVO;
import lottery.domains.pool.LotteryDataFactory;
import net.sf.json.JSONArray;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class LotteryDataFactoryImpl
  implements LotteryDataFactory, InitializingBean
{
  @Autowired
  private ActivityFirstRechargeConfigService firstRechargeConfigService;
  private ActivityFirstRechargeConfigVO firstRechargeConfig = null;
  private static final Logger logger = LoggerFactory.getLogger(LotteryDataFactoryImpl.class);
  @Autowired
  private SysConfigDao sysConfigDao;
  
  public void init()
  {
    logger.info("init LotteryDataFactory....start");
    initSysConfig();
    initCDNConfig();
    initSysPlatform();
    
    initGame();
    
    initUser();
    initLottery();
    initLotteryType();
    initLotteryOpenTime();
    initLotteryPlayRules();
    initActivityRedPacketRainTimes();
    initPaymentBank();
    initPaymentCard();
    initPaymentChannel();
    initDemoUser();
    initActivityFirstRechargeConfig();
    logger.info("init LotteryDataFactory....done");
  }
  
  private void initActivityFirstRechargeConfig()
  {
    ActivityFirstRechargeConfig config = this.firstRechargeConfigService.getConfig();
    this.firstRechargeConfig = covertFirstRechargeConfigVO(config);
  }
  
  private ActivityFirstRechargeConfigVO covertFirstRechargeConfigVO(ActivityFirstRechargeConfig config)
  {
    List<ActivityFirstRechargeConfigRule> rules = JSON.parseArray(config.getRules(), ActivityFirstRechargeConfigRule.class);
    ActivityFirstRechargeConfigVO configVO = new ActivityFirstRechargeConfigVO();
    configVO.setId(config.getId());
    configVO.setRules(config.getRules());
    configVO.setStatus(config.getStatus());
    configVO.setRuleVOs(rules);
    
    double minRecharge = 0.0D;
    for (ActivityFirstRechargeConfigRule rule : rules) {
      if (minRecharge <= 0.0D) {
        minRecharge = rule.getMinRecharge();
      } else if (rule.getMinRecharge() < minRecharge) {
        minRecharge = rule.getMinRecharge();
      }
    }
    configVO.setMinRecharge(minRecharge);
    return configVO;
  }
  
  public void afterPropertiesSet()
    throws Exception
  {
    init();
  }
  
  private CodeConfig codeConfig = new CodeConfig();
  private LotteryConfig lotteryConfig = new LotteryConfig();
  private DividendConfig dividendConfig = new DividendConfig();
  private GameDividendConfig gameDividendConfig = new GameDividendConfig();
  private DailySettleConfig dailySettleConfig = new DailySettleConfig();
  private WithdrawConfig withdrawConfig = new WithdrawConfig();
  private RechargeConfig rechargeConfig = new RechargeConfig();
  private VipConfig vipConfig = new VipConfig();
  private PlanConfig planConfig = new PlanConfig();
  private MailConfig mailConfig = new MailConfig();
  private AdminCDNConfig cdnConfig = new AdminCDNConfig();
  private PortalCDNConfig pcdnConfig = new PortalCDNConfig();
  private AdminGoogleConfig adminGoogleConfig = new AdminGoogleConfig();
  private AdminGlobalConfig adminGlobalConfig = new AdminGlobalConfig();
  private RegistConfig registConfig = new RegistConfig();
  @Autowired
  private ServletContext servletContext;
  private Map<String, DbServerSync> DbServerSyncMap = new HashMap();
  private static volatile boolean isRunningSyncInit = false;
  private static Object syncInitLock = new Object();
  @Autowired
  private DbServerSyncDao dbServerSyncDao;
  @Autowired
  private SysPlatformDao sysPlatformDao;
  
  /* Error */
  @Scheduled(cron="4,14,24,34,44,54 * * * * *")
  public void syncInitJob()
  {
    // Byte code:
    //   0: getstatic 136	lottery/domains/pool/impl/LotteryDataFactoryImpl:syncInitLock	Ljava/lang/Object;
    //   3: dup
    //   4: astore_1
    //   5: monitorenter
    //   6: getstatic 131	lottery/domains/pool/impl/LotteryDataFactoryImpl:isRunningSyncInit	Z
    //   9: ifeq +6 -> 15
    //   12: aload_1
    //   13: monitorexit
    //   14: return
    //   15: iconst_1
    //   16: putstatic 131	lottery/domains/pool/impl/LotteryDataFactoryImpl:isRunningSyncInit	Z
    //   19: aload_1
    //   20: monitorexit
    //   21: goto +6 -> 27
    //   24: aload_1
    //   25: monitorexit
    //   26: athrow
    //   27: aload_0
    //   28: invokespecial 416	lottery/domains/pool/impl/LotteryDataFactoryImpl:SyncInit	()V
    //   31: goto +10 -> 41
    //   34: astore_1
    //   35: iconst_0
    //   36: putstatic 131	lottery/domains/pool/impl/LotteryDataFactoryImpl:isRunningSyncInit	Z
    //   39: aload_1
    //   40: athrow
    //   41: iconst_0
    //   42: putstatic 131	lottery/domains/pool/impl/LotteryDataFactoryImpl:isRunningSyncInit	Z
    //   45: return
    // Line number table:
    //   Java source line #186	-> byte code offset #0
    //   Java source line #187	-> byte code offset #6
    //   Java source line #189	-> byte code offset #12
    //   Java source line #191	-> byte code offset #15
    //   Java source line #186	-> byte code offset #19
    //   Java source line #195	-> byte code offset #27
    //   Java source line #196	-> byte code offset #31
    //   Java source line #197	-> byte code offset #35
    //   Java source line #198	-> byte code offset #39
    //   Java source line #197	-> byte code offset #41
    //   Java source line #199	-> byte code offset #45
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	46	0	this	LotteryDataFactoryImpl
    //   4	36	1	Ljava/lang/Object;	Object
    // Exception table:
    //   from	to	target	type
    //   6	14	24	finally
    //   15	21	24	finally
    //   24	26	24	finally
    //   27	34	34	finally
  }
  
  private void SyncInit()
  {
    try
    {
      List<DbServerSync> list = this.dbServerSyncDao.listAll();
      for (DbServerSync serverBean : list)
      {
        String key = serverBean.getKey();
        if (!this.DbServerSyncMap.containsKey(key)) {
          this.DbServerSyncMap.put(key, serverBean);
        }
        DbServerSync thisBean = (DbServerSync)this.DbServerSyncMap.get(key);
        if ((serverBean.getLastModTime() != null) && 
          (!serverBean.getLastModTime().equals(thisBean.getLastModTime())))
        {
          if (DbServerSyncEnum.SYS_CONFIG.name().equals(key))
          {
            logger.debug("有新的同步数据：" + key);
            initSysConfig();
          }
          else if (DbServerSyncEnum.PAYMENT_CARD.name().equals(key))
          {
            logger.debug("有新的同步数据：" + key);
            initPaymentCard();
          }
          else if (DbServerSyncEnum.PAYMENT_CHANNEL.name().equals(key))
          {
            logger.debug("有新的同步数据：" + key);
            initPaymentChannel();
          }
          else if (DbServerSyncEnum.SYS_PLATFORM.name().equals(key))
          {
            initSysPlatform();
          }
          else if (DbServerSyncEnum.ADMIN_CDN.name().equals(key))
          {
            logger.debug("有新的同步数据：" + key);
            initCDNConfig();
          }
          this.DbServerSyncMap.put(key, serverBean);
        }
      }
    }
    catch (Exception e)
    {
      logger.error("同步失败！", e);
    }
  }
  
  public void initSysConfig()
  {
    try
    {
      List<SysConfig> list = this.sysConfigDao.listAll();
      for (SysConfig bean : list)
      {
        String group = bean.getGroup();
        String key = bean.getKey();
        String value = bean.getValue();
        if ("CODE".equals(group))
        {
          if ("SYS_CODE".equals(key)) {
            this.codeConfig.setSysCode(Integer.parseInt(value));
          }
          if ("SYS_NOT_CREATE_ACCOUNT".equals(key)) {
            this.codeConfig.setNotCreateAccount(Integer.parseInt(value));
          }
          if ("SYS_LOCATE_POINT".equals(key)) {
            this.codeConfig.setSysLp(Double.parseDouble(value));
          }
          if ("SYS_NOT_LOCATE_POINT".equals(key)) {
            this.codeConfig.setSysNlp(Double.parseDouble(value));
          }
          if (("SYS_RANGE".equals(key)) && 
            (StringUtils.isNotEmpty(value)))
          {
            String[] ranges = value.split("\\|");
            List<SysCodeRangeVO> rlist = new ArrayList();
            
            this.codeConfig.setSysCodeRange(rlist);
          }
        }
        if ("LOTTERY".equals(group))
        {
          if ("NOT_BET_POINT_ACCOUNT".equals(key)) {
            this.lotteryConfig.setNotBetPointAccount(Integer.valueOf(value).intValue());
          }
          if ("NOT_BET_POINT".equals(key)) {
            this.lotteryConfig.setNotBetPoint(Integer.valueOf(value).intValue());
          }
          if ("BET_UNIT_MONEY".equals(key)) {
            this.lotteryConfig.setbUnitMoney(Integer.parseInt(value));
          }
          if ("FEN_MODEL_DOWN_CODE".equals(key)) {
            this.lotteryConfig.setFenModelDownCode(Integer.parseInt(value));
          }
          if ("LI_MODEL_DOWN_CODE".equals(key)) {
            this.lotteryConfig.setLiModelDownCode(Integer.parseInt(value));
          }
          if ("AUTO_HIT_RANKING".equals(key)) {
            this.lotteryConfig.setAutoHitRanking(Integer.parseInt(value) == 1);
          }
          if ("HIT_RANKING_SIZE".equals(key))
          {
            int hitRankingSize = Integer.parseInt(value);
            if (hitRankingSize < 0) {
              hitRankingSize = 10;
            }
            if (hitRankingSize > 10) {
              hitRankingSize = 10;
            }
            this.lotteryConfig.setHitRankingSize(hitRankingSize);
          }
        }
        if ("WITHDRAW".equals(group))
        {
          if ("STATUS".equals(key)) {
            this.withdrawConfig.setStatus(Integer.parseInt(value));
          }
          if ("SERVICE_TIME".equals(key)) {
            this.withdrawConfig.setServiceTime(value);
          }
          if ("SERVICE_MSG".equals(key)) {
            this.withdrawConfig.setServiceMsg(value);
          }
          if ("MIN_AMOUNT".equals(key)) {
            this.withdrawConfig.setMinAmount(Double.parseDouble(value));
          }
          if ("MAX_AMOUNT".equals(key)) {
            this.withdrawConfig.setMaxAmount(Double.parseDouble(value));
          }
          if ("MAX_TIMES".equals(key)) {
            this.withdrawConfig.setMaxTimes(Integer.parseInt(value));
          }
          if ("FREE_TIMES".equals(key)) {
            this.withdrawConfig.setFreeTimes(Integer.parseInt(value));
          }
          if ("FEE".equals(key)) {
            this.withdrawConfig.setFee(Double.parseDouble(value));
          }
          if ("MAX_FEE".equals(key)) {
            this.withdrawConfig.setMaxFee(Double.parseDouble(value));
          }
          if ("SYSTEM_CONSUMPTION_PERCENT".equals(key)) {
            this.withdrawConfig.setSystemConsumptionPercent(Double.parseDouble(value));
          }
          if ("TRANSFER_CONSUMPTION_PERCENT".equals(key)) {
            this.withdrawConfig.setTransferConsumptionPercent(Double.parseDouble(value));
          }
          if ("API_PAY_NOTIFY_URL".equals(key)) {
            this.withdrawConfig.setApiPayNotifyUrl(value);
          }
        }
        if ("RECHARGE".equals(group))
        {
          if ("STATUS".equals(key)) {
            this.rechargeConfig.setStatus(Integer.parseInt(value));
          }
          if ("SERVICE_TIME".equals(key)) {
            this.rechargeConfig.setServiceTime(value);
          }
          if (("FEE_PERCENT".equals(key)) && 
            (NumberUtils.isNumber(value))) {
            this.rechargeConfig.setFeePercent(Double.valueOf(value).doubleValue());
          }
        }
        if ("VIP".equals(group))
        {
          if ("BIRTHDAY_GIFTS".equals(key))
          {
            String[] arr = value.split(",");
            double[] birthdayGifts = new double[arr.length];
            for (int i = 0; i < arr.length; i++) {
              birthdayGifts[i] = Double.parseDouble(arr[i]);
            }
            this.vipConfig.setBirthdayGifts(birthdayGifts);
          }
          if ("FREE_CHIPS".equals(key))
          {
            String[] arr = value.split(",");
            double[] freeChips = new double[arr.length];
            for (int i = 0; i < arr.length; i++) {
              freeChips[i] = Double.parseDouble(arr[i]);
            }
            this.vipConfig.setFreeChips(freeChips);
          }
          if ("UPGRADE_GIFTS".equals(key))
          {
            String[] arr = value.split(",");
            double[] upgradeGifts = new double[arr.length];
            for (int i = 0; i < arr.length; i++) {
              upgradeGifts[i] = Double.parseDouble(arr[i]);
            }
            this.vipConfig.setUpgradeGifts(upgradeGifts);
          }
          if ("WITHDRAW".equals(key))
          {
            String[] arr = value.split(",");
            double[] withdraw = new double[arr.length];
            for (int i = 0; i < arr.length; i++) {
              withdraw[i] = Double.parseDouble(arr[i]);
            }
            this.vipConfig.setWithdraw(withdraw);
          }
          if ("EXCHANGE_RATE".equals(key)) {
            this.vipConfig.setExchangeRate(Integer.parseInt(value));
          }
          if ("MAX_EXCHANGE_MULTIPLE".equals(key)) {
            this.vipConfig.setMaxExchangeMultiple(Integer.parseInt(value));
          }
          if ("MAX_EXCHANGE_TIMES".equals(key)) {
            this.vipConfig.setMaxExchangeTimes(Integer.parseInt(value));
          }
        }
        String str1;
        Object localObject1;
        String v;
        if ("PLAN".equals(group))
        {
          if ("MIN_MONEY".equals(key))
          {
            double minMoney = Double.parseDouble(value);
            this.planConfig.setMinMoney(minMoney);
          }
          if ("TITLE".equals(key))
          {
            JSONArray jsonArray = JSONArray.fromObject(value);
            List<String> title = new ArrayList();
            for (Object object : jsonArray) {
              title.add((String)object);
            }
            this.planConfig.setTitle(title);
          }
          String[] arrayOfString1;
          if ("RATE".equals(key))
          {
            List<Integer> rate = new ArrayList();
            String[] arr = value.split(",");
            int str4 = (arrayOfString1 = arr).length;
            for (int  index = 0; index < str4; index++)
            {
               v = arrayOfString1[index];
              rate.add(Integer.valueOf(Integer.parseInt(v)));
            }
            this.planConfig.setRate(rate);
          }
          if ("LEVEL".equals(key))
          {
            List<Integer> level = new ArrayList();
            String[] arr = value.split(",");
            localObject1 = (arrayOfString1 = arr).length;
            for (int index = 0; index < arr.length; index++)
            {
              v = arrayOfString1[index];
              level.add(Integer.valueOf(Integer.parseInt(v)));
            }
            this.planConfig.setLevel(level);
          }
        }
        if ("REGIST".equals(group))
        {
          if ("REGIST_CODE".equals(key)) {
            this.registConfig.setDefaultCode(Integer.valueOf(value).intValue());
          }
          if ("REGIST_STATUS".equals(key)) {
            this.registConfig.setEnable(Integer.valueOf(value).intValue() == 1);
          }
          if ("DEMO_COUNT".equals(key)) {
            this.registConfig.setDemoCount(Integer.parseInt(value));
          }
          if ("DEMO_PASSWORD".equals(key)) {
            this.registConfig.setDemoPassword(value);
          }
          if ("FICTITIOUS_COUNT".equals(key)) {
            this.registConfig.setFictitiousCount(Integer.parseInt(value));
          }
          if ("FICTITIOUS_PASSWORD".equals(key)) {
            this.registConfig.setFictitiousPassword(value);
          }
          if ("DEMO_LOTTERY_MONEY".equals(key)) {
            this.registConfig.setDemoLotteryMoney(Double.parseDouble(value));
          }
        }
        if ("DIVIDEND".equals(group))
        {
          if ("FIXED_TYPE".equals(key)) {
            this.dividendConfig.setFixedType(Integer.valueOf(value).intValue());
          }
          if ("MIN_VALID_USER".equals(key)) {
            this.dividendConfig.setMinValidUserl(Integer.valueOf(value).intValue());
          }
          if ("IS_CHECK_LOSS".equals(key)) {
            this.dividendConfig.setCheckLoss(Integer.valueOf(value).intValue() == 1);
          }
          if ("LEVELS_LOSS".equals(key))
          {
            String[] arrs = value.split("~");
            double[] values = { Double.valueOf(arrs[0]).doubleValue(), Double.valueOf(arrs[1]).doubleValue() };
            this.dividendConfig.setLevelsLoss(values);
          }
          if ("LEVELS_SALES".equals(key))
          {
            String[] arrs = value.split("~");
            double[] values = { Double.valueOf(arrs[0]).doubleValue(), Double.valueOf(arrs[1]).doubleValue() };
            this.dividendConfig.setLevelsSales(values);
          }
          if ("LEVELS_SCALE".equals(key))
          {
            String[] arrs = value.split("~");
            double[] values = { Integer.valueOf(arrs[0]).intValue(), Integer.valueOf(arrs[1]).intValue() };
            this.dividendConfig.setLevelsScale(values);
          }
          if ("MAX_SIGN_LEVEL".equals(key)) {
            this.dividendConfig.setMaxSignLevel(Integer.valueOf(value).intValue());
          }
          if ("START_LEVEL".equals(key)) {
            this.dividendConfig.setStartLevel(Integer.valueOf(value).intValue());
          }
          if ("ZHAO_SHANG_SCALE".equals(key))
          {
            String[] values = value.split("~");
            this.dividendConfig.setZhaoShangSalesLevels(values[0]);
            this.dividendConfig.setZhaoShangLossLevels(values[1]);
            this.dividendConfig.setZhaoShangScaleLevels(values[2]);
          }
          if ("CJ_ZHAO_SHANG_SCALE".equals(key))
          {
            String[] arr = value.split("\\|");
            int length = arr.length;
            for (int index = 0; index < length; index++)
            {
              String ruleStr = arr[index];
              String[] values = ruleStr.split("~");
              this.dividendConfig.addCJZhaoShangScaleConfig(Double.valueOf(values[0]).doubleValue(), 
                Double.valueOf(values[1]).doubleValue(), Double.valueOf(values[2]).doubleValue());
            }
          }
          if ("ZHAO_SHANG_MIN_VALID_USER".equals(key)) {
            this.dividendConfig.setZhaoShangMinValidUser(Integer.valueOf(value).intValue());
          }
          if ("ZHAO_SHANG_BELOW_SCALE".equals(key))
          {
            String[] values = value.split("~");
            this.dividendConfig.setZhaoShangBelowMinScale(Double.valueOf(values[0]).doubleValue());
            this.dividendConfig.setZhaoShangBelowMaxScale(Double.valueOf(values[1]).doubleValue());
          }
          if ("STATUS".equals(key)) {
            this.dividendConfig.setEnable(Integer.valueOf(value).intValue() == 1);
          }
        }
        if ("DAILY_SETTLE".equals(group))
        {
          if ("MIN_VALID_USER".equals(key)) {
            this.dailySettleConfig.setMinValidUserl(Integer.valueOf(value).intValue());
          }
          if ("IS_CHECK_LOSS".equals(key)) {
            this.dailySettleConfig.setCheckLoss(Integer.valueOf(value).intValue() == 1);
          }
          if ("LEVELS_LOSS".equals(key))
          {
            String[] arrs = value.split("~");
            double[] values = { Double.valueOf(arrs[0]).doubleValue(), Double.valueOf(arrs[1]).doubleValue() };
            this.dailySettleConfig.setLevelsLoss(values);
          }
          if ("LEVELS_SALES".equals(key))
          {
            String[] arrs = value.split("~");
            double[] values = { Double.valueOf(arrs[0]).doubleValue(), Double.valueOf(arrs[1]).doubleValue() };
            this.dailySettleConfig.setLevelsSales(values);
          }
          if ("LEVELS_SCALE".equals(key))
          {
            String[] arrs = value.split("~");
            double[] values = { Double.valueOf(arrs[0]).doubleValue(), Double.valueOf(arrs[1]).doubleValue() };
            this.dailySettleConfig.setLevelsScale(values);
          }
          if ("MAX_SIGN_LEVEL".equals(key)) {
            this.dailySettleConfig.setMaxSignLevel(Integer.valueOf(value).intValue());
          }
          if ("NEIBU_ZHAO_SHANG_SCALE".equals(key)) {
            this.dailySettleConfig.setNeibuZhaoShangScale(StringUtils.isEmpty(value) ? 0.0D : Double.valueOf(value).doubleValue());
          }
          if ("NEIBU_ZHAO_SHANG_MIN_VALID_USER".equals(key)) {
            this.dailySettleConfig.setNeibuZhaoShangMinValidUser(Integer.valueOf(value).intValue());
          }
          if ("ZHAO_SHANG_SCALE".equals(key)) {
            this.dailySettleConfig.setZhaoShangScale(StringUtils.isEmpty(value) ? 0.0D : Double.valueOf(value).doubleValue());
          }
          if ("CJ_ZHAO_SHANG_SCALE".equals(key)) {
            this.dailySettleConfig.setCjZhaoShangScale(StringUtils.isEmpty(value) ? 0.0D : Double.valueOf(value).doubleValue());
          }
          if ("ZHAO_SHANG_MIN_VALID_USER".equals(key)) {
            this.dailySettleConfig.setZhaoShangMinValidUser(Integer.valueOf(value).intValue());
          }
          if ("STATUS".equals(key)) {
            this.dailySettleConfig.setEnable(Integer.valueOf(value).intValue() == 1);
          }
        }
        if (("SETTLE".equals(group)) && 
          ("MIN_BILLING_ORDER".equals(key)))
        {
          Double minBillingOrder = Double.valueOf(value);
          this.dailySettleConfig.setMinBillingOrder(minBillingOrder.doubleValue());
          this.dividendConfig.setMinBillingOrder(minBillingOrder.doubleValue());
        }
        if ("GAME_DIVIDEND".equals(group))
        {
          if ("ZHU_GUAN_SCALE".equals(key))
          {
            String[] arr = value.split("\\|");
            int length = arr.length;
            for (int index = 0; index < length; index++)
            {
              String ruleStr = arr[index];
              String[] values = ruleStr.split("~");
              this.gameDividendConfig.addZhuGuanScaleConfig(Double.valueOf(values[0]).doubleValue(), 
                Double.valueOf(values[1]).doubleValue(), Double.valueOf(values[2]).doubleValue());
            }
          }
          if ("ZHU_GUAN_MIN_VALID_USER".equals(key)) {
            this.gameDividendConfig.setZhuGuanMinValidUser(Integer.valueOf(value).intValue());
          }
          if ("ZHU_GUAN_BELOW_SCALE".equals(key))
          {
            String[] values = value.split("~");
            this.gameDividendConfig.setZhuGuanBelowMinScale(Double.valueOf(values[0]).doubleValue());
            this.gameDividendConfig.setZhuGuanBelowMaxScale(Double.valueOf(values[1]).doubleValue());
          }
          if ("STATUS".equals(key)) {
            this.gameDividendConfig.setEnable(Integer.valueOf(value).intValue() == 1);
          }
        }
        if ("MAIL".equals(group))
        {
          if ("PASSWORD".equals(key)) {
            this.mailConfig.setPassword(value.trim());
          }
          if ("PERSONAL".equals(key)) {
            this.mailConfig.setPersonal(value.trim());
          }
          if ("SMTP_HOST".equals(key)) {
            this.mailConfig.setHost(value.trim());
          }
          if ("USERNAME".equals(key)) {
            this.mailConfig.setUsername(value.trim());
          }
          if ("BET".equals(key)) {
            this.mailConfig.setBet(Integer.valueOf(value).intValue());
          }
          if ("OPEN".equals(key)) {
            this.mailConfig.setOpen(Integer.valueOf(value).intValue());
          }
          if ("RECHARGE".equals(key)) {
            this.mailConfig.setRecharge(Integer.valueOf(value).intValue());
          }
          if ("SYS_RECEIVE_MAILS".equals(key))
          {
            this.mailConfig.getReceiveMails().clear();
            if (StringUtils.isNotEmpty(value))
            {
              String[] mails = value.split(",");
              int str3 = mails.length;
              for (int index = 0; index < str3; index++)
              {
                String mail = mails[index];
                this.mailConfig.addReceiveMail(mail.trim());
              }
            }
          }
        }
        if (("ADMIN_GOOGLE".equals(group)) && 
          ("LOGIN_STATUS".equals(key))) {
          this.adminGoogleConfig.setLoginStatus("1".equals(value));
        }
        if (("ADMIN_GLOBAL".equals(group)) && 
          ("LOGO".equals(key))) {
          this.adminGlobalConfig.setLogo(value);
        }
      }
      logger.info("初始化系统配置表完成！");
    }
    catch (Exception e)
    {
      logger.error("初始化系统配置表失败！", e);
    }
  }
  
  public void initCDNConfig()
  {
    try
    {
      List<SysConfig> list = this.sysConfigDao.listAll();
      for (SysConfig bean : list)
      {
        String group = bean.getGroup();
        String key = bean.getKey();
        String value = bean.getValue();
        if ("ADMIN_CDN".equals(group))
        {
          if ("ADMIN_DOMAIN".equals(key)) {
            if (StringUtils.isEmpty(value))
            {
              this.cdnConfig.setCdnDomain("/");
            }
            else
            {
              String[] cdnDomains = value.split(",");
              int randomIndex = RandomUtils.nextInt(cdnDomains.length);
              String cdnDomain = cdnDomains[randomIndex];
              if (!cdnDomain.endsWith("/")) {
                this.cdnConfig.setCdnDomain(cdnDomain + "/");
              } else {
                this.cdnConfig.setCdnDomain(cdnDomain);
              }
            }
          }
          if ("ADMIN_VERSION".equals(key)) {
            this.cdnConfig.setCdnVersion(value);
          }
          if (this.servletContext != null)
          {
            this.servletContext.setAttribute("cdnDomain", this.cdnConfig.getCdnDomain());
            this.servletContext.setAttribute("cdnVersion", this.cdnConfig.getCdnVersion());
          }
        }
        if ("CDN".equals(group))
        {
          if ("DOMAIN".equals(key)) {
            if (StringUtils.isEmpty(value))
            {
              this.cdnConfig.setCdnDomain("/");
            }
            else
            {
              String[] cdnDomains = value.split(",");
              int randomIndex = RandomUtils.nextInt(cdnDomains.length);
              String cdnDomain = cdnDomains[randomIndex];
              if (!cdnDomain.endsWith("/")) {
                this.pcdnConfig.setCdnDomain(cdnDomain + "/");
              } else {
                this.pcdnConfig.setCdnDomain(cdnDomain);
              }
            }
          }
          if ("VERSION".equals(key)) {
            this.cdnConfig.setCdnVersion(value);
          }
          if (this.servletContext != null)
          {
            this.servletContext.setAttribute("pcdnDomain", this.cdnConfig.getCdnDomain());
            this.servletContext.setAttribute("pcdnVersion", this.cdnConfig.getCdnVersion());
          }
        }
      }
      logger.info("初始化系统配置表完成！");
    }
    catch (Exception e)
    {
      logger.error("初始化系统配置表失败！", e);
    }
  }
  
  public CodeConfig getCodeConfig()
  {
    return this.codeConfig;
  }
  
  public LotteryConfig getLotteryConfig()
  {
    return this.lotteryConfig;
  }
  
  public WithdrawConfig getWithdrawConfig()
  {
    return this.withdrawConfig;
  }
  
  public RechargeConfig getRechargeConfig()
  {
    return this.rechargeConfig;
  }
  
  public VipConfig getVipConfig()
  {
    return this.vipConfig;
  }
  
  public PlanConfig getPlanConfig()
  {
    return this.planConfig;
  }
  
  public DividendConfig getDividendConfig()
  {
    return this.dividendConfig;
  }
  
  public GameDividendConfig getGameDividendConfig()
  {
    return this.gameDividendConfig;
  }
  
  public GameDividendConfigRule determineGameDividendRule(double loss)
  {
    return this.gameDividendConfig.determineZhuGuanRule(loss);
  }
  
  public MailConfig getMailConfig()
  {
    return this.mailConfig;
  }
  
  public DividendConfigRule determineZhaoShangDividendRule(double dailyBilling)
  {
    return this.dividendConfig.determineZhaoShangRule(dailyBilling);
  }
  
  public DividendConfigRule determineCJZhaoShangDividendRule(double dailyBilling)
  {
    return this.dividendConfig.determineCJZhaoShangRule(dailyBilling);
  }
  
  public DailySettleConfig getDailySettleConfig()
  {
    return this.dailySettleConfig;
  }
  
  public AdminGoogleConfig getAdminGoogleConfig()
  {
    return this.adminGoogleConfig;
  }
  
  public AdminGlobalConfig getAdminGlobalConfig()
  {
    return this.adminGlobalConfig;
  }
  
  private List<SysPlatform> sysPlatformList = new LinkedList();
  @Autowired
  private UserDao userDao;
  
  public void initSysPlatform()
  {
    try
    {
      List<SysPlatform> list = this.sysPlatformDao.listAll();
      this.sysPlatformList = list;
      logger.info("初始化平台列表完成！");
    }
    catch (Exception e)
    {
      logger.error("初始化平台列表失败！");
    }
  }
  
  public List<SysPlatform> listSysPlatform()
  {
    return this.sysPlatformList;
  }
  
  public SysPlatform getSysPlatform(int id)
  {
    for (SysPlatform tmpBean : this.sysPlatformList) {
      if (tmpBean.getId() == id) {
        return tmpBean;
      }
    }
    return null;
  }
  
  public SysPlatform getSysPlatform(String name)
  {
    for (SysPlatform tmpBean : this.sysPlatformList) {
      if (tmpBean.getName().equals(name)) {
        return tmpBean;
      }
    }
    return null;
  }
  
  private Map<Integer, UserVO> userMap = new LinkedHashMap();
  private Map<String, UserVO> userMapWithUserName = new LinkedHashMap();
  @Autowired
  private LotteryTypeDao lotteryTypeDao;
  
  public void initUser()
  {
    try
    {
      List<User> list = this.userDao.listAll();
      
      Map<Integer, UserVO> tmpMap = new LinkedHashMap();
      for (User user : list) {
        tmpMap.put(Integer.valueOf(user.getId()), new UserVO(user.getId(), user.getUsername()));
      }
      this.userMap = tmpMap;
      
      Map<String, UserVO> tmpMapWithUserName = new LinkedHashMap();
      for (User user : list) {
        tmpMapWithUserName.put(user.getUsername(), new UserVO(user.getId(), user.getUsername()));
      }
      this.userMapWithUserName = tmpMapWithUserName;
      logger.info("初始化用户基础信息完成！");
    }
    catch (Exception e)
    {
      logger.error("初始化用户基础信息失败！");
    }
  }
  
  public UserVO getUser(int id)
  {
    if (this.userMap.containsKey(Integer.valueOf(id))) {
      return (UserVO)this.userMap.get(Integer.valueOf(id));
    }
    User user = this.userDao.getById(id);
    if (user != null)
    {
      this.userMap.put(Integer.valueOf(user.getId()), new UserVO(user.getId(), user.getUsername()));
      this.userMapWithUserName.put(user.getUsername(), new UserVO(user.getId(), user.getUsername()));
      return (UserVO)this.userMap.get(Integer.valueOf(id));
    }
    return null;
  }
  
  public UserVO getUser(String username)
  {
    if (this.userMapWithUserName.containsKey(username)) {
      return (UserVO)this.userMapWithUserName.get(username);
    }
    User user = this.userDao.getByUsername(username);
    if (user != null)
    {
      this.userMapWithUserName.put(user.getUsername(), new UserVO(user.getId(), user.getUsername()));
      this.userMap.put(Integer.valueOf(user.getId()), new UserVO(user.getId(), user.getUsername()));
      return (UserVO)this.userMapWithUserName.get(username);
    }
    return null;
  }
  
  private Map<Integer, LotteryType> lotteryTypeMap = new LinkedHashMap();
  @Autowired
  private LotteryDao lotteryDao;
  
  public void initLotteryType()
  {
    try
    {
      List<LotteryType> list = this.lotteryTypeDao.listAll();
      Map<Integer, LotteryType> tmpMap = new LinkedHashMap();
      for (LotteryType lotteryType : list) {
        tmpMap.put(Integer.valueOf(lotteryType.getId()), lotteryType);
      }
      if (this.lotteryTypeMap != null) {
        this.lotteryTypeMap.clear();
      }
      this.lotteryTypeMap = tmpMap;
      logger.info("初始化彩票类型完成！");
    }
    catch (Exception e)
    {
      logger.error("初始化彩票类型失败！");
    }
  }
  
  public LotteryType getLotteryType(int id)
  {
    if (this.lotteryTypeMap.containsKey(Integer.valueOf(id))) {
      return (LotteryType)this.lotteryTypeMap.get(Integer.valueOf(id));
    }
    return null;
  }
  
  public List<LotteryType> listLotteryType()
  {
    List<LotteryType> list = new LinkedList();
    Object[] keys = this.lotteryTypeMap.keySet().toArray();
    Object[] arrayOfObject1;
    int j = (arrayOfObject1 = keys).length;
    for (int i = 0; i < j; i++)
    {
      Object o = arrayOfObject1[i];
      list.add((LotteryType)this.lotteryTypeMap.get(o));
    }
    return list;
  }
  
  private Map<Integer, Lottery> lotteryMap = new LinkedHashMap();
  @Autowired
  private LotteryOpenTimeDao lotteryOpenTimeDao;
  
  public void initLottery()
  {
    try
    {
      List<Lottery> list = this.lotteryDao.listAll();
      Map<Integer, Lottery> tmpMap = new LinkedHashMap();
      for (Lottery lottery : list) {
        tmpMap.put(Integer.valueOf(lottery.getId()), lottery);
      }
      if (this.lotteryMap != null) {
        this.lotteryMap.clear();
      }
      this.lotteryMap = tmpMap;
      logger.info("初始化彩票信息完成！");
    }
    catch (Exception e)
    {
      logger.error("初始化彩票信息失败！");
    }
  }
  
  public Lottery getLottery(int id)
  {
    if (this.lotteryMap.containsKey(Integer.valueOf(id))) {
      return (Lottery)this.lotteryMap.get(Integer.valueOf(id));
    }
    return null;
  }
  
  public Lottery getLottery(String shortName)
  {
    Object[] keys = this.lotteryMap.keySet().toArray();
    Object[] arrayOfObject1;
    int j = (arrayOfObject1 = keys).length;
    for (int i = 0; i < j; i++)
    {
      Object o = arrayOfObject1[i];
      Lottery lottery = (Lottery)this.lotteryMap.get(o);
      if (lottery.getShortName().equals(shortName)) {
        return lottery;
      }
    }
    return null;
  }
  
  public List<Lottery> listLottery()
  {
    List<Lottery> list = new LinkedList();
    Object[] keys = this.lotteryMap.keySet().toArray();
    Object[] arrayOfObject1;
    int j = (arrayOfObject1 = keys).length;
    for (int i = 0; i < j; i++)
    {
      Object o = arrayOfObject1[i];
      list.add((Lottery)this.lotteryMap.get(o));
    }
    return list;
  }
  
  public List<Lottery> listLottery(int type)
  {
    List<Lottery> list = new LinkedList();
    Object[] keys = this.lotteryMap.keySet().toArray();
    Object[] arrayOfObject1;
    int j = (arrayOfObject1 = keys).length;
    for (int i = 0; i < j; i++)
    {
      Object o = arrayOfObject1[i];
      Lottery lottery = (Lottery)this.lotteryMap.get(o);
      if (lottery.getType() == type) {
        list.add(lottery);
      }
    }
    return list;
  }
  
  private List<LotteryOpenTime> lotteryOpenTimeList = new LinkedList();
  private Map<String, List<LotteryOpenTime>> lotteryOpenTimeMap = new HashMap();
  @Autowired
  private LotteryPlayRulesGroupDao lotteryPlayRulesGroupDao;
  @Autowired
  private LotteryPlayRulesDao lotteryPlayRulesDao;
  
  public void initLotteryOpenTime()
  {
    try
    {
      List<LotteryOpenTime> list = this.lotteryOpenTimeDao.listAll();
      
      Map<String, List<LotteryOpenTime>> tmpOpenTimeMap = new HashMap();
      if (CollectionUtils.isNotEmpty(list)) {
        for (LotteryOpenTime lotteryOpenTime : list) {
          if (tmpOpenTimeMap.containsKey(lotteryOpenTime.getLottery()))
          {
            ((List)tmpOpenTimeMap.get(lotteryOpenTime.getLottery())).add(lotteryOpenTime);
          }
          else
          {
            LinkedList<LotteryOpenTime> openTimes = new LinkedList();
            openTimes.add(lotteryOpenTime);
            tmpOpenTimeMap.put(lotteryOpenTime.getLottery(), openTimes);
          }
        }
      }
      this.lotteryOpenTimeMap = tmpOpenTimeMap;
      logger.info("初始化彩票开奖时间信息完成！");
    }
    catch (Exception e)
    {
      logger.error("初始化彩票开奖时间信息失败！");
    }
  }
  
  public List<LotteryOpenTime> listLotteryOpenTime(String lottery)
  {
    return (List)this.lotteryOpenTimeMap.get(lottery);
  }
  
  private List<LotteryPlayRulesGroup> lotteryPlayRulesGroupList = new LinkedList();
  private List<LotteryPlayRules> lotteryPlayRulesList = new LinkedList();
  @Autowired
  private PaymentBankDao paymentBankDao;
  
  public void initLotteryPlayRules()
  {
    try
    {
      List<LotteryPlayRulesGroup> groupList = this.lotteryPlayRulesGroupDao.listAll();
      this.lotteryPlayRulesGroupList = groupList;
      
      List<LotteryPlayRules> ruleList = this.lotteryPlayRulesDao.listAll();
      this.lotteryPlayRulesList = ruleList;
      logger.info("初始化彩票玩法规则完成！");
    }
    catch (Exception e)
    {
      logger.error("初始化彩票玩法规则失败！");
    }
  }
  
  public LotteryPlayRules getLotteryPlayRules(int id)
  {
    for (LotteryPlayRules rule : this.lotteryPlayRulesList) {
      if (id == rule.getId()) {
        return rule;
      }
    }
    return null;
  }
  
  public LotteryPlayRulesGroup getLotteryPlayRulesGroup(int id)
  {
    for (LotteryPlayRulesGroup group : this.lotteryPlayRulesGroupList) {
      if (id == group.getId()) {
        return group;
      }
    }
    return null;
  }
  
  private List<PaymentBank> paymentBankList = new LinkedList();
  @Autowired
  private PaymentCardService paymentCardService;
  
  public void initPaymentBank()
  {
    try
    {
      List<PaymentBank> list = this.paymentBankDao.listAll();
      if (this.paymentBankList != null) {
        this.paymentBankList.clear();
      }
      this.paymentBankList.addAll(list);
      logger.info("初始化银行列表完成！");
    }
    catch (Exception e)
    {
      logger.error("初始化银行列表失败！");
    }
  }
  
  public List<PaymentBank> listPaymentBank()
  {
    return this.paymentBankList;
  }
  
  public PaymentBank getPaymentBank(int id)
  {
    for (PaymentBank tmpBean : this.paymentBankList) {
      if (tmpBean.getId() == id) {
        return tmpBean;
      }
    }
    return null;
  }
  
  private Map<Integer, PaymentCardVO> paymentCardVOs = new LinkedHashMap();
  @Autowired
  private PaymentChannelService paymentChannelService;
  
  public void initPaymentCard()
  {
    try
    {
      List<PaymentCard> list = this.paymentCardService.listAll();
      Map<Integer, PaymentCardVO> vos = new LinkedHashMap();
      if (CollectionUtils.isNotEmpty(list)) {
        for (PaymentCard paymentCard : list) {
          vos.put(Integer.valueOf(paymentCard.getId()), new PaymentCardVO(paymentCard, this));
        }
      }
      this.paymentCardVOs = vos;
      logger.info("初始化转账银行卡列表完成！");
    }
    catch (Exception e)
    {
      logger.error("初始化转账银行卡列表失败！");
    }
  }
  
  public List<PaymentCardVO> listPaymentCard()
  {
    return new ArrayList(this.paymentCardVOs.values());
  }
  
  public PaymentCardVO getPaymentCard(int id)
  {
    return (PaymentCardVO)this.paymentCardVOs.get(Integer.valueOf(id));
  }
  
  private Map<Integer, PaymentChannel> PAYMENT_CHANNEL_FULL_PROPERTY_CACHE = new HashMap();
  private Map<Integer, PaymentChannelVO> PAYMENT_CHANNEL_VO_CACHE = new HashMap();
  private List<PaymentChannelSimpleVO> PAYMENT_CHANNEL_SIMPLE_LIST = new ArrayList();
  @Autowired
  private GameTypeDao gameTypeDao;
  
  public void initPaymentChannel()
  {
    List<PaymentChannel> paymentChannels = this.paymentChannelService.listAllFullProperties();
    if (CollectionUtils.isEmpty(paymentChannels))
    {
      this.PAYMENT_CHANNEL_FULL_PROPERTY_CACHE.clear();
      this.PAYMENT_CHANNEL_VO_CACHE.clear();
      this.PAYMENT_CHANNEL_SIMPLE_LIST.clear();
      return;
    }
    Map<Integer, PaymentChannel> tempPaymentChannelsFullProperty = new HashMap();
    Map<Integer, PaymentChannelVO> tempPaymentChannelVOs = new HashMap();
    List<PaymentChannelSimpleVO> tempPaymentChannelSimpleList = new ArrayList();
    for (PaymentChannel paymentChannel : paymentChannels)
    {
      tempPaymentChannelsFullProperty.put(Integer.valueOf(paymentChannel.getId()), paymentChannel);
      tempPaymentChannelVOs.put(Integer.valueOf(paymentChannel.getId()), new PaymentChannelVO(paymentChannel));
      tempPaymentChannelSimpleList.add(new PaymentChannelSimpleVO(paymentChannel));
    }
    this.PAYMENT_CHANNEL_FULL_PROPERTY_CACHE = tempPaymentChannelsFullProperty;
    this.PAYMENT_CHANNEL_VO_CACHE = tempPaymentChannelVOs;
    this.PAYMENT_CHANNEL_SIMPLE_LIST = tempPaymentChannelSimpleList;
  }
  
  public void initDemoUser()
  {
    int demoCount = this.userDao.getDemoUserCount();
    if (demoCount != this.registConfig.getDemoCount()) {
      for (int i = 0; i < this.registConfig.getDemoCount(); i++) {
        addDemoUser(this.registConfig.getDemoPassword());
      }
    }
    int fCount = this.userDao.getFictitiousUserCount();
    if (this.registConfig.getFictitiousCount() != fCount) {
      for (int i = 0; i < this.registConfig.getFictitiousCount(); i++) {
        addFictitiousUser(this.registConfig.getFictitiousPassword());
      }
    }
  }
  
  private void addDemoUser(String parpassword)
  {
    String username = StringUtil.getRandomString(8).toLowerCase();
    if (this.userDao.getByUsername(username) != null) {
      addDemoUser(parpassword);
    }
    String password = PasswordUtil.generatePasswordByPlainString(parpassword);
    String nickname = "试玩用户";
    double totalMoney = 0.0D;
    double lotteryMoney = 100000.0D;
    double baccaratMoney = 0.0D;
    double freezeMoney = 0.0D;
    double dividendMoney = 0.0D;
    int codeType = 0;
    double extraPoint = 0.0D;
    String registTime = new Moment().toSimpleTime();
    int AStatus = 0;
    int BStatus = 0;
    int allowEqualCode = 0;
    int onlineStatus = 0;
    int allowTransfers = 0;
    int allowPlatformTransfers = 0;
    int allowWithdraw = 0;
    int loginValidate = 0;
    int bindStatus = 0;
    int vipLevel = 0;
    double integral = 0.0D;
    User entity = new User(username, password, nickname, totalMoney, lotteryMoney, baccaratMoney, freezeMoney, dividendMoney, 
      2, 0, 1800, 0.0D, 0.0D, codeType, extraPoint, registTime, AStatus, BStatus, onlineStatus, 
      allowEqualCode, allowTransfers, loginValidate, bindStatus, vipLevel, integral, allowPlatformTransfers, allowWithdraw);
    this.userDao.add(entity);
  }
  
  public static void main(String[] args)
  {
    String password = PasswordUtil.generatePasswordByPlainString("fpw7107");
    System.out.println(password);
  }
  
  private void addFictitiousUser(String parpassword)
  {
    String username = StringUtil.getRandUserName();
    if (this.userDao.getByUsername(username) != null) {
      addFictitiousUser(parpassword);
    }
    String password = PasswordUtil.generatePasswordByPlainString(parpassword);
    String nickname = username;
    double totalMoney = 0.0D;
    double lotteryMoney = 100000.0D;
    double baccaratMoney = 0.0D;
    double freezeMoney = 0.0D;
    double dividendMoney = 0.0D;
    int codeType = 0;
    double extraPoint = 0.0D;
    String registTime = new Moment().toSimpleTime();
    int AStatus = 0;
    int BStatus = 0;
    int allowEqualCode = 0;
    int onlineStatus = 0;
    int allowTransfers = 0;
    int allowPlatformTransfers = 0;
    int allowWithdraw = 0;
    int loginValidate = 0;
    int bindStatus = 0;
    int vipLevel = 0;
    double integral = 0.0D;
    User entity = new User(username, password, nickname, totalMoney, lotteryMoney, baccaratMoney, freezeMoney, dividendMoney, 
      4, 0, 1800, 0.0D, 0.0D, codeType, extraPoint, registTime, AStatus, BStatus, onlineStatus, 
      allowEqualCode, allowTransfers, loginValidate, bindStatus, vipLevel, integral, allowPlatformTransfers, allowWithdraw);
    this.userDao.add(entity);
  }
  
  public List<PaymentChannelVO> listPaymentChannelVOs()
  {
    return new ArrayList(this.PAYMENT_CHANNEL_VO_CACHE.values());
  }
  
  public List<PaymentChannelSimpleVO> listPaymentChannelVOsSimple()
  {
    return this.PAYMENT_CHANNEL_SIMPLE_LIST;
  }
  
  public PaymentChannelVO getPaymentChannelVO(int id)
  {
    return (PaymentChannelVO)this.PAYMENT_CHANNEL_VO_CACHE.get(Integer.valueOf(id));
  }
  
  public PaymentChannelVO getPaymentChannelVO(String channelCode, Integer id)
  {
    for (PaymentChannelVO channel : this.PAYMENT_CHANNEL_VO_CACHE.values()) {
      if (channelCode.equals(channel.getChannelCode()))
      {
        if (id == null) {
          return channel;
        }
        if (id.intValue() == channel.getId()) {
          return channel;
        }
      }
    }
    return null;
  }
  
  public PaymentChannel getPaymentChannelFullProperty(int id)
  {
    return (PaymentChannel)this.PAYMENT_CHANNEL_FULL_PROPERTY_CACHE.get(Integer.valueOf(id));
  }
  
  public PaymentChannel getPaymentChannelFullProperty(String channelCode, Integer id)
  {
    for (PaymentChannel channel : this.PAYMENT_CHANNEL_FULL_PROPERTY_CACHE.values()) {
      if (channelCode.equals(channel.getChannelCode()))
      {
        if (id == null) {
          return channel;
        }
        if (id.intValue() == channel.getId()) {
          return channel;
        }
      }
    }
    return null;
  }
  
  private Map<Integer, GameType> GAME_TYPE_CACHE = new HashMap();
  @Autowired
  private UserGameAccountDao uGameAccountDao;
  
  @Scheduled(cron="* 0/3 * * * *")
  public void initGame()
  {
    initGameType();
  }
  
  public void initGameType()
  {
    List<GameType> types = this.gameTypeDao.listAll();
    
    Map<Integer, GameType> tempTypes = new HashMap();
    for (GameType type : types) {
      tempTypes.put(Integer.valueOf(type.getId()), type);
    }
    this.GAME_TYPE_CACHE = tempTypes;
  }
  
  public GameType getGameType(int id)
  {
    return (GameType)this.GAME_TYPE_CACHE.get(Integer.valueOf(id));
  }
  
  private Map<String, Map<Integer, UserGameAccount>> GAME_ACCOUNT_NAME_CACHE = new HashMap();
  @Autowired
  private ActivityRedPacketRainTimeService rainTimeService;
  
  public UserGameAccount getGameAccount(String platformName, int platformId)
  {
    if (this.GAME_ACCOUNT_NAME_CACHE.containsKey(platformName))
    {
      Map<Integer, UserGameAccount> platformAccs = (Map)this.GAME_ACCOUNT_NAME_CACHE.get(platformName);
      if (platformAccs.containsKey(Integer.valueOf(platformId))) {
        return (UserGameAccount)platformAccs.get(Integer.valueOf(platformId));
      }
    }
    UserGameAccount gameAccount = this.uGameAccountDao.get(platformName, platformId);
    if (gameAccount == null) {
      return null;
    }
    if (!this.GAME_ACCOUNT_NAME_CACHE.containsKey(platformName)) {
      this.GAME_ACCOUNT_NAME_CACHE.put(platformName, new HashMap());
    }
    Map<Integer, UserGameAccount> platformAccounts = (Map)this.GAME_ACCOUNT_NAME_CACHE.get(platformName);
    if (!platformAccounts.containsKey(Integer.valueOf(platformId))) {
      platformAccounts.put(Integer.valueOf(platformId), gameAccount);
    }
    return gameAccount;
  }
  
  public void initActivityRedPacketRainTimes()
  {
    try
    {
      this.rainTimeService.initTimes(2);
      logger.info("初始化红包雨时间完成！");
    }
    catch (Exception e)
    {
      logger.error("初始化红包雨时间失败！", e);
    }
  }
  
  public ActivityFirstRechargeConfigVO getActivityFirstRechargeConfig()
  {
    return this.firstRechargeConfig;
  }
}
