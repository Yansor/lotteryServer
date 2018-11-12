package lottery.domains.pool.impl;

import javautils.date.Moment;
import javautils.encrypt.PasswordUtil;
import javautils.StringUtil;
import lottery.domains.content.entity.PaymentCard;
import java.util.Collection;
import org.apache.commons.collections.CollectionUtils;
import lottery.domains.content.entity.User;
import lottery.domains.content.vo.config.DividendConfigRule;
import lottery.domains.content.vo.config.GameDividendConfigRule;
import org.apache.commons.lang.math.RandomUtils;
import net.sf.json.JSONArray;
import org.apache.commons.lang.math.NumberUtils;
import lottery.domains.content.vo.user.SysCodeRangeVO;
import org.apache.commons.lang.StringUtils;
import lottery.domains.content.entity.SysConfig;
import lottery.domains.content.global.DbServerSyncEnum;
import org.springframework.scheduling.annotation.Scheduled;
import java.util.Iterator;
import com.alibaba.fastjson.JSON;
import lottery.domains.content.entity.activity.ActivityFirstRechargeConfigRule;
import lottery.domains.content.entity.ActivityFirstRechargeConfig;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.HashMap;
import org.slf4j.LoggerFactory;
import lottery.domains.content.biz.ActivityRedPacketRainTimeService;
import lottery.domains.content.entity.UserGameAccount;
import lottery.domains.content.dao.UserGameAccountDao;
import lottery.domains.content.entity.GameType;
import lottery.domains.content.dao.GameTypeDao;
import lottery.domains.content.vo.payment.PaymentChannelSimpleVO;
import lottery.domains.content.vo.payment.PaymentChannelVO;
import lottery.domains.content.entity.PaymentChannel;
import lottery.domains.content.biz.PaymentChannelService;
import lottery.domains.content.vo.payment.PaymentCardVO;
import lottery.domains.content.biz.PaymentCardService;
import lottery.domains.content.entity.PaymentBank;
import lottery.domains.content.dao.PaymentBankDao;
import lottery.domains.content.entity.LotteryPlayRules;
import lottery.domains.content.entity.LotteryPlayRulesGroup;
import lottery.domains.content.dao.LotteryPlayRulesDao;
import lottery.domains.content.dao.LotteryPlayRulesGroupDao;
import lottery.domains.content.entity.LotteryOpenTime;
import lottery.domains.content.dao.LotteryOpenTimeDao;
import lottery.domains.content.entity.Lottery;
import lottery.domains.content.dao.LotteryDao;
import lottery.domains.content.entity.LotteryType;
import lottery.domains.content.dao.LotteryTypeDao;
import lottery.domains.content.vo.user.UserVO;
import lottery.domains.content.dao.UserDao;
import lottery.domains.content.entity.SysPlatform;
import java.util.List;
import lottery.domains.content.dao.SysPlatformDao;
import lottery.domains.content.dao.DbServerSyncDao;
import lottery.domains.content.entity.DbServerSync;
import java.util.Map;
import javax.servlet.ServletContext;
import lottery.domains.content.vo.config.RegistConfig;
import lottery.domains.content.vo.config.AdminGlobalConfig;
import lottery.domains.content.vo.config.AdminGoogleConfig;
import lottery.domains.content.vo.config.PortalCDNConfig;
import lottery.domains.content.vo.config.AdminCDNConfig;
import lottery.domains.content.vo.config.MailConfig;
import lottery.domains.content.vo.config.PlanConfig;
import lottery.domains.content.vo.config.VipConfig;
import lottery.domains.content.vo.config.RechargeConfig;
import lottery.domains.content.vo.config.WithdrawConfig;
import lottery.domains.content.vo.config.DailySettleConfig;
import lottery.domains.content.vo.config.GameDividendConfig;
import lottery.domains.content.vo.config.DividendConfig;
import lottery.domains.content.vo.config.LotteryConfig;
import lottery.domains.content.vo.config.CodeConfig;
import lottery.domains.content.dao.SysConfigDao;
import org.slf4j.Logger;
import lottery.domains.content.entity.activity.ActivityFirstRechargeConfigVO;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.biz.ActivityFirstRechargeConfigService;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.InitializingBean;
import lottery.domains.pool.LotteryDataFactory;

@Component
public class LotteryDataFactoryImpl implements LotteryDataFactory, InitializingBean
{
    @Autowired
    private ActivityFirstRechargeConfigService firstRechargeConfigService;
    private ActivityFirstRechargeConfigVO firstRechargeConfig;
    private static final Logger logger;
    @Autowired
    private SysConfigDao sysConfigDao;
    private CodeConfig codeConfig;
    private LotteryConfig lotteryConfig;
    private DividendConfig dividendConfig;
    private GameDividendConfig gameDividendConfig;
    private DailySettleConfig dailySettleConfig;
    private WithdrawConfig withdrawConfig;
    private RechargeConfig rechargeConfig;
    private VipConfig vipConfig;
    private PlanConfig planConfig;
    private MailConfig mailConfig;
    private AdminCDNConfig cdnConfig;
    private PortalCDNConfig pcdnConfig;
    private AdminGoogleConfig adminGoogleConfig;
    private AdminGlobalConfig adminGlobalConfig;
    private RegistConfig registConfig;
    @Autowired
    private ServletContext servletContext;
    private Map<String, DbServerSync> DbServerSyncMap;
    private static volatile boolean isRunningSyncInit;
    private static Object syncInitLock;
    @Autowired
    private DbServerSyncDao dbServerSyncDao;
    @Autowired
    private SysPlatformDao sysPlatformDao;
    private List<SysPlatform> sysPlatformList;
    @Autowired
    private UserDao userDao;
    private Map<Integer, UserVO> userMap;
    private Map<String, UserVO> userMapWithUserName;
    @Autowired
    private LotteryTypeDao lotteryTypeDao;
    private Map<Integer, LotteryType> lotteryTypeMap;
    @Autowired
    private LotteryDao lotteryDao;
    private Map<Integer, Lottery> lotteryMap;
    @Autowired
    private LotteryOpenTimeDao lotteryOpenTimeDao;
    private List<LotteryOpenTime> lotteryOpenTimeList;
    private Map<String, List<LotteryOpenTime>> lotteryOpenTimeMap;
    @Autowired
    private LotteryPlayRulesGroupDao lotteryPlayRulesGroupDao;
    @Autowired
    private LotteryPlayRulesDao lotteryPlayRulesDao;
    private List<LotteryPlayRulesGroup> lotteryPlayRulesGroupList;
    private List<LotteryPlayRules> lotteryPlayRulesList;
    @Autowired
    private PaymentBankDao paymentBankDao;
    private List<PaymentBank> paymentBankList;
    @Autowired
    private PaymentCardService paymentCardService;
    private Map<Integer, PaymentCardVO> paymentCardVOs;
    @Autowired
    private PaymentChannelService paymentChannelService;
    private Map<Integer, PaymentChannel> PAYMENT_CHANNEL_FULL_PROPERTY_CACHE;
    private Map<Integer, PaymentChannelVO> PAYMENT_CHANNEL_VO_CACHE;
    private List<PaymentChannelSimpleVO> PAYMENT_CHANNEL_SIMPLE_LIST;
    @Autowired
    private GameTypeDao gameTypeDao;
    private Map<Integer, GameType> GAME_TYPE_CACHE;
    @Autowired
    private UserGameAccountDao uGameAccountDao;
    private Map<String, Map<Integer, UserGameAccount>> GAME_ACCOUNT_NAME_CACHE;
    @Autowired
    private ActivityRedPacketRainTimeService rainTimeService;
    
    static {
        logger = LoggerFactory.getLogger((Class)LotteryDataFactoryImpl.class);
        LotteryDataFactoryImpl.isRunningSyncInit = false;
        LotteryDataFactoryImpl.syncInitLock = new Object();
    }
    
    public LotteryDataFactoryImpl() {
        this.firstRechargeConfig = null;
        this.codeConfig = new CodeConfig();
        this.lotteryConfig = new LotteryConfig();
        this.dividendConfig = new DividendConfig();
        this.gameDividendConfig = new GameDividendConfig();
        this.dailySettleConfig = new DailySettleConfig();
        this.withdrawConfig = new WithdrawConfig();
        this.rechargeConfig = new RechargeConfig();
        this.vipConfig = new VipConfig();
        this.planConfig = new PlanConfig();
        this.mailConfig = new MailConfig();
        this.cdnConfig = new AdminCDNConfig();
        this.pcdnConfig = new PortalCDNConfig();
        this.adminGoogleConfig = new AdminGoogleConfig();
        this.adminGlobalConfig = new AdminGlobalConfig();
        this.registConfig = new RegistConfig();
        this.DbServerSyncMap = new HashMap<String, DbServerSync>();
        this.sysPlatformList = new LinkedList<SysPlatform>();
        this.userMap = new LinkedHashMap<Integer, UserVO>();
        this.userMapWithUserName = new LinkedHashMap<String, UserVO>();
        this.lotteryTypeMap = new LinkedHashMap<Integer, LotteryType>();
        this.lotteryMap = new LinkedHashMap<Integer, Lottery>();
        this.lotteryOpenTimeList = new LinkedList<LotteryOpenTime>();
        this.lotteryOpenTimeMap = new HashMap<String, List<LotteryOpenTime>>();
        this.lotteryPlayRulesGroupList = new LinkedList<LotteryPlayRulesGroup>();
        this.lotteryPlayRulesList = new LinkedList<LotteryPlayRules>();
        this.paymentBankList = new LinkedList<PaymentBank>();
        this.paymentCardVOs = new LinkedHashMap<Integer, PaymentCardVO>();
        this.PAYMENT_CHANNEL_FULL_PROPERTY_CACHE = new HashMap<Integer, PaymentChannel>();
        this.PAYMENT_CHANNEL_VO_CACHE = new HashMap<Integer, PaymentChannelVO>();
        this.PAYMENT_CHANNEL_SIMPLE_LIST = new ArrayList<PaymentChannelSimpleVO>();
        this.GAME_TYPE_CACHE = new HashMap<Integer, GameType>();
        this.GAME_ACCOUNT_NAME_CACHE = new HashMap<String, Map<Integer, UserGameAccount>>();
    }
    
    @Override
    public void init() {
        LotteryDataFactoryImpl.logger.info("init LotteryDataFactory....start");
        this.initSysConfig();
        this.initCDNConfig();
        this.initSysPlatform();
        this.initGame();
        this.initUser();
        this.initLottery();
        this.initLotteryType();
        this.initLotteryOpenTime();
        this.initLotteryPlayRules();
        this.initActivityRedPacketRainTimes();
        this.initPaymentBank();
        this.initPaymentCard();
        this.initPaymentChannel();
        this.initDemoUser();
        this.initActivityFirstRechargeConfig();
        LotteryDataFactoryImpl.logger.info("init LotteryDataFactory....done");
    }
    
    private void initActivityFirstRechargeConfig() {
        final ActivityFirstRechargeConfig config = this.firstRechargeConfigService.getConfig();
        this.firstRechargeConfig = this.covertFirstRechargeConfigVO(config);
    }
    
    private ActivityFirstRechargeConfigVO covertFirstRechargeConfigVO(final ActivityFirstRechargeConfig config) {
        final List<ActivityFirstRechargeConfigRule> rules = (List<ActivityFirstRechargeConfigRule>)JSON.parseArray(config.getRules(), (Class)ActivityFirstRechargeConfigRule.class);
        final ActivityFirstRechargeConfigVO configVO = new ActivityFirstRechargeConfigVO();
        configVO.setId(config.getId());
        configVO.setRules(config.getRules());
        configVO.setStatus(config.getStatus());
        configVO.setRuleVOs(rules);
        double minRecharge = 0.0;
        for (final ActivityFirstRechargeConfigRule rule : rules) {
            if (minRecharge <= 0.0) {
                minRecharge = rule.getMinRecharge();
            }
            else {
                if (rule.getMinRecharge() >= minRecharge) {
                    continue;
                }
                minRecharge = rule.getMinRecharge();
            }
        }
        configVO.setMinRecharge(minRecharge);
        return configVO;
    }
    
    public void afterPropertiesSet() throws Exception {
        this.init();
    }
    
    @Scheduled(cron = "4,14,24,34,44,54 * * * * *")
    public void syncInitJob() {
        synchronized (LotteryDataFactoryImpl.syncInitLock) {
            if (LotteryDataFactoryImpl.isRunningSyncInit) {
                // monitorexit(LotteryDataFactoryImpl.syncInitLock)
                return;
            }
            LotteryDataFactoryImpl.isRunningSyncInit = true;
        }
        // monitorexit(LotteryDataFactoryImpl.syncInitLock)
        try {
            this.SyncInit();
        }
        finally {
            LotteryDataFactoryImpl.isRunningSyncInit = false;
        }
        LotteryDataFactoryImpl.isRunningSyncInit = false;
    }
    
    private void SyncInit() {
        try {
            final List<DbServerSync> list = this.dbServerSyncDao.listAll();
            for (final DbServerSync serverBean : list) {
                final String key = serverBean.getKey();
                if (!this.DbServerSyncMap.containsKey(key)) {
                    this.DbServerSyncMap.put(key, serverBean);
                }
                final DbServerSync thisBean = this.DbServerSyncMap.get(key);
                if (serverBean.getLastModTime() != null && !serverBean.getLastModTime().equals(thisBean.getLastModTime())) {
                    if (DbServerSyncEnum.SYS_CONFIG.name().equals(key)) {
                        LotteryDataFactoryImpl.logger.debug("有新的同步数据：" + key);
                        this.initSysConfig();
                    }
                    else if (DbServerSyncEnum.PAYMENT_CARD.name().equals(key)) {
                        LotteryDataFactoryImpl.logger.debug("有新的同步数据：" + key);
                        this.initPaymentCard();
                    }
                    else if (DbServerSyncEnum.PAYMENT_CHANNEL.name().equals(key)) {
                        LotteryDataFactoryImpl.logger.debug("有新的同步数据：" + key);
                        this.initPaymentChannel();
                    }
                    else if (DbServerSyncEnum.SYS_PLATFORM.name().equals(key)) {
                        this.initSysPlatform();
                    }
                    else if (DbServerSyncEnum.ADMIN_CDN.name().equals(key)) {
                        LotteryDataFactoryImpl.logger.debug("有新的同步数据：" + key);
                        this.initCDNConfig();
                    }
                    this.DbServerSyncMap.put(key, serverBean);
                }
            }
        }
        catch (Exception e) {
            LotteryDataFactoryImpl.logger.error("同步失败！", (Throwable)e);
        }
    }
    
    @Override
    public void initSysConfig() {
        try {
            final List<SysConfig> list = this.sysConfigDao.listAll();
            for (final SysConfig bean : list) {
                final String group = bean.getGroup();
                final String key = bean.getKey();
                final String value = bean.getValue();
                if ("CODE".equals(group)) {
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
                    if ("SYS_RANGE".equals(key) && StringUtils.isNotEmpty(value)) {
                        final String[] ranges = value.split("\\|");
                        final List<SysCodeRangeVO> rlist = new ArrayList<SysCodeRangeVO>();
                        this.codeConfig.setSysCodeRange(rlist);
                    }
                }
                if ("LOTTERY".equals(group)) {
                    if ("NOT_BET_POINT_ACCOUNT".equals(key)) {
                        this.lotteryConfig.setNotBetPointAccount(Integer.valueOf(value));
                    }
                    if ("NOT_BET_POINT".equals(key)) {
                        this.lotteryConfig.setNotBetPoint(Integer.valueOf(value));
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
                    if ("HIT_RANKING_SIZE".equals(key)) {
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
                if ("WITHDRAW".equals(group)) {
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
                if ("RECHARGE".equals(group)) {
                    if ("STATUS".equals(key)) {
                        this.rechargeConfig.setStatus(Integer.parseInt(value));
                    }
                    if ("SERVICE_TIME".equals(key)) {
                        this.rechargeConfig.setServiceTime(value);
                    }
                    if ("FEE_PERCENT".equals(key) && NumberUtils.isNumber(value)) {
                        this.rechargeConfig.setFeePercent(Double.valueOf(value));
                    }
                }
                if ("VIP".equals(group)) {
                    if ("BIRTHDAY_GIFTS".equals(key)) {
                        final String[] arr = value.split(",");
                        final double[] birthdayGifts = new double[arr.length];
                        for (int i = 0; i < arr.length; ++i) {
                            birthdayGifts[i] = Double.parseDouble(arr[i]);
                        }
                        this.vipConfig.setBirthdayGifts(birthdayGifts);
                    }
                    if ("FREE_CHIPS".equals(key)) {
                        final String[] arr = value.split(",");
                        final double[] freeChips = new double[arr.length];
                        for (int i = 0; i < arr.length; ++i) {
                            freeChips[i] = Double.parseDouble(arr[i]);
                        }
                        this.vipConfig.setFreeChips(freeChips);
                    }
                    if ("UPGRADE_GIFTS".equals(key)) {
                        final String[] arr = value.split(",");
                        final double[] upgradeGifts = new double[arr.length];
                        for (int i = 0; i < arr.length; ++i) {
                            upgradeGifts[i] = Double.parseDouble(arr[i]);
                        }
                        this.vipConfig.setUpgradeGifts(upgradeGifts);
                    }
                    if ("WITHDRAW".equals(key)) {
                        final String[] arr = value.split(",");
                        final double[] withdraw = new double[arr.length];
                        for (int i = 0; i < arr.length; ++i) {
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
                if ("PLAN".equals(group)) {
                    if ("MIN_MONEY".equals(key)) {
                        final double minMoney = Double.parseDouble(value);
                        this.planConfig.setMinMoney(minMoney);
                    }
                    if ("TITLE".equals(key)) {
                        final JSONArray jsonArray = JSONArray.fromObject((Object)value);
                        final List<String> title = new ArrayList<String>();
                        for (final Object object : jsonArray) {
                            title.add((String)object);
                        }
                        this.planConfig.setTitle(title);
                    }
                    if ("RATE".equals(key)) {
                        final List<Integer> rate = new ArrayList<Integer>();
                        final String[] arr2 = value.split(",");
                        String[] array;
                        for (int length = (array = arr2).length, j = 0; j < length; ++j) {
                            final String v = array[j];
                            rate.add(Integer.parseInt(v));
                        }
                        this.planConfig.setRate(rate);
                    }
                    if ("LEVEL".equals(key)) {
                        final List<Integer> level = new ArrayList<Integer>();
                        final String[] arr2 = value.split(",");
                        String[] array2;
                        for (int length2 = (array2 = arr2).length, k = 0; k < length2; ++k) {
                            final String v = array2[k];
                            level.add(Integer.parseInt(v));
                        }
                        this.planConfig.setLevel(level);
                    }
                }
                if ("REGIST".equals(group)) {
                    if ("REGIST_CODE".equals(key)) {
                        this.registConfig.setDefaultCode(Integer.valueOf(value));
                    }
                    if ("REGIST_STATUS".equals(key)) {
                        this.registConfig.setEnable(Integer.valueOf(value) == 1);
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
                if ("DIVIDEND".equals(group)) {
                    if ("FIXED_TYPE".equals(key)) {
                        this.dividendConfig.setFixedType(Integer.valueOf(value));
                    }
                    if ("MIN_VALID_USER".equals(key)) {
                        this.dividendConfig.setMinValidUserl(Integer.valueOf(value));
                    }
                    if ("IS_CHECK_LOSS".equals(key)) {
                        this.dividendConfig.setCheckLoss(Integer.valueOf(value) == 1);
                    }
                    if ("LEVELS_LOSS".equals(key)) {
                        final String[] arrs = value.split("~");
                        final double[] values = { Double.valueOf(arrs[0]), Double.valueOf(arrs[1]) };
                        this.dividendConfig.setLevelsLoss(values);
                    }
                    if ("LEVELS_SALES".equals(key)) {
                        final String[] arrs = value.split("~");
                        final double[] values = { Double.valueOf(arrs[0]), Double.valueOf(arrs[1]) };
                        this.dividendConfig.setLevelsSales(values);
                    }
                    if ("LEVELS_SCALE".equals(key)) {
                        final String[] arrs = value.split("~");
                        final double[] values = { Integer.valueOf(arrs[0]), Integer.valueOf(arrs[1]) };
                        this.dividendConfig.setLevelsScale(values);
                    }
                    if ("MAX_SIGN_LEVEL".equals(key)) {
                        this.dividendConfig.setMaxSignLevel(Integer.valueOf(value));
                    }
                    if ("START_LEVEL".equals(key)) {
                        this.dividendConfig.setStartLevel(Integer.valueOf(value));
                    }
                    if ("ZHAO_SHANG_SCALE".equals(key)) {
                        final String[] values2 = value.split("~");
                        this.dividendConfig.setZhaoShangSalesLevels(values2[0]);
                        this.dividendConfig.setZhaoShangLossLevels(values2[1]);
                        this.dividendConfig.setZhaoShangScaleLevels(values2[2]);
                    }
                    if ("CJ_ZHAO_SHANG_SCALE".equals(key)) {
                        final String[] arr = value.split("\\|");
                        String[] array3;
                        for (int length3 = (array3 = arr).length, l = 0; l < length3; ++l) {
                            final String ruleStr = array3[l];
                            final String[] values3 = ruleStr.split("~");
                            this.dividendConfig.addCJZhaoShangScaleConfig(Double.valueOf(values3[0]), Double.valueOf(values3[1]), Double.valueOf(values3[2]));
                        }
                    }
                    if ("ZHAO_SHANG_MIN_VALID_USER".equals(key)) {
                        this.dividendConfig.setZhaoShangMinValidUser(Integer.valueOf(value));
                    }
                    if ("ZHAO_SHANG_BELOW_SCALE".equals(key)) {
                        final String[] values2 = value.split("~");
                        this.dividendConfig.setZhaoShangBelowMinScale(Double.valueOf(values2[0]));
                        this.dividendConfig.setZhaoShangBelowMaxScale(Double.valueOf(values2[1]));
                    }
                    if ("STATUS".equals(key)) {
                        this.dividendConfig.setEnable(Integer.valueOf(value) == 1);
                    }
                }
                if ("DAILY_SETTLE".equals(group)) {
                    if ("MIN_VALID_USER".equals(key)) {
                        this.dailySettleConfig.setMinValidUserl(Integer.valueOf(value));
                    }
                    if ("IS_CHECK_LOSS".equals(key)) {
                        this.dailySettleConfig.setCheckLoss(Integer.valueOf(value) == 1);
                    }
                    if ("LEVELS_LOSS".equals(key)) {
                        final String[] arrs = value.split("~");
                        final double[] values = { Double.valueOf(arrs[0]), Double.valueOf(arrs[1]) };
                        this.dailySettleConfig.setLevelsLoss(values);
                    }
                    if ("LEVELS_SALES".equals(key)) {
                        final String[] arrs = value.split("~");
                        final double[] values = { Double.valueOf(arrs[0]), Double.valueOf(arrs[1]) };
                        this.dailySettleConfig.setLevelsSales(values);
                    }
                    if ("LEVELS_SCALE".equals(key)) {
                        final String[] arrs = value.split("~");
                        final double[] values = { Double.valueOf(arrs[0]), Double.valueOf(arrs[1]) };
                        this.dailySettleConfig.setLevelsScale(values);
                    }
                    if ("MAX_SIGN_LEVEL".equals(key)) {
                        this.dailySettleConfig.setMaxSignLevel(Integer.valueOf(value));
                    }
                    if ("NEIBU_ZHAO_SHANG_SCALE".equals(key)) {
                        this.dailySettleConfig.setNeibuZhaoShangScale(StringUtils.isEmpty(value) ? 0.0 : ((double)Double.valueOf(value)));
                    }
                    if ("NEIBU_ZHAO_SHANG_MIN_VALID_USER".equals(key)) {
                        this.dailySettleConfig.setNeibuZhaoShangMinValidUser(Integer.valueOf(value));
                    }
                    if ("ZHAO_SHANG_SCALE".equals(key)) {
                        this.dailySettleConfig.setZhaoShangScale(StringUtils.isEmpty(value) ? 0.0 : ((double)Double.valueOf(value)));
                    }
                    if ("CJ_ZHAO_SHANG_SCALE".equals(key)) {
                        this.dailySettleConfig.setCjZhaoShangScale(StringUtils.isEmpty(value) ? 0.0 : ((double)Double.valueOf(value)));
                    }
                    if ("ZHAO_SHANG_MIN_VALID_USER".equals(key)) {
                        this.dailySettleConfig.setZhaoShangMinValidUser(Integer.valueOf(value));
                    }
                    if ("STATUS".equals(key)) {
                        this.dailySettleConfig.setEnable(Integer.valueOf(value) == 1);
                    }
                }
                if ("SETTLE".equals(group) && "MIN_BILLING_ORDER".equals(key)) {
                    final Double minBillingOrder = Double.valueOf(value);
                    this.dailySettleConfig.setMinBillingOrder(minBillingOrder);
                    this.dividendConfig.setMinBillingOrder(minBillingOrder);
                }
                if ("GAME_DIVIDEND".equals(group)) {
                    if ("ZHU_GUAN_SCALE".equals(key)) {
                        final String[] arr = value.split("\\|");
                        String[] array4;
                        for (int length4 = (array4 = arr).length, n = 0; n < length4; ++n) {
                            final String ruleStr = array4[n];
                            final String[] values3 = ruleStr.split("~");
                            this.gameDividendConfig.addZhuGuanScaleConfig(Double.valueOf(values3[0]), Double.valueOf(values3[1]), Double.valueOf(values3[2]));
                        }
                    }
                    if ("ZHU_GUAN_MIN_VALID_USER".equals(key)) {
                        this.gameDividendConfig.setZhuGuanMinValidUser(Integer.valueOf(value));
                    }
                    if ("ZHU_GUAN_BELOW_SCALE".equals(key)) {
                        final String[] values2 = value.split("~");
                        this.gameDividendConfig.setZhuGuanBelowMinScale(Double.valueOf(values2[0]));
                        this.gameDividendConfig.setZhuGuanBelowMaxScale(Double.valueOf(values2[1]));
                    }
                    if ("STATUS".equals(key)) {
                        this.gameDividendConfig.setEnable(Integer.valueOf(value) == 1);
                    }
                }
                if ("MAIL".equals(group)) {
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
                        this.mailConfig.setBet(Integer.valueOf(value));
                    }
                    if ("OPEN".equals(key)) {
                        this.mailConfig.setOpen(Integer.valueOf(value));
                    }
                    if ("RECHARGE".equals(key)) {
                        this.mailConfig.setRecharge(Integer.valueOf(value));
                    }
                    if ("SYS_RECEIVE_MAILS".equals(key)) {
                        this.mailConfig.getReceiveMails().clear();
                        if (StringUtils.isNotEmpty(value)) {
                            final String[] mails = value.split(",");
                            String[] array5;
                            for (int length5 = (array5 = mails).length, n2 = 0; n2 < length5; ++n2) {
                                final String mail = array5[n2];
                                this.mailConfig.addReceiveMail(mail.trim());
                            }
                        }
                    }
                }
                if ("ADMIN_GOOGLE".equals(group) && "LOGIN_STATUS".equals(key)) {
                    this.adminGoogleConfig.setLoginStatus("1".equals(value));
                }
                if ("ADMIN_GLOBAL".equals(group) && "LOGO".equals(key)) {
                    this.adminGlobalConfig.setLogo(value);
                }
            }
            LotteryDataFactoryImpl.logger.info("初始化系统配置表完成！");
        }
        catch (Exception e) {
            LotteryDataFactoryImpl.logger.error("初始化系统配置表失败！", (Throwable)e);
        }
    }
    
    @Override
    public void initCDNConfig() {
        try {
            final List<SysConfig> list = this.sysConfigDao.listAll();
            for (final SysConfig bean : list) {
                final String group = bean.getGroup();
                final String key = bean.getKey();
                final String value = bean.getValue();
                if ("ADMIN_CDN".equals(group)) {
                    if ("ADMIN_DOMAIN".equals(key)) {
                        if (StringUtils.isEmpty(value)) {
                            this.cdnConfig.setCdnDomain("/");
                        }
                        else {
                            final String[] cdnDomains = value.split(",");
                            final int randomIndex = RandomUtils.nextInt(cdnDomains.length);
                            final String cdnDomain = cdnDomains[randomIndex];
                            if (!cdnDomain.endsWith("/")) {
                                this.cdnConfig.setCdnDomain(String.valueOf(cdnDomain) + "/");
                            }
                            else {
                                this.cdnConfig.setCdnDomain(cdnDomain);
                            }
                        }
                    }
                    if ("ADMIN_VERSION".equals(key)) {
                        this.cdnConfig.setCdnVersion(value);
                    }
                    if (this.servletContext != null) {
                        this.servletContext.setAttribute("cdnDomain", (Object)this.cdnConfig.getCdnDomain());
                        this.servletContext.setAttribute("cdnVersion", (Object)this.cdnConfig.getCdnVersion());
                    }
                }
                if ("CDN".equals(group)) {
                    if ("DOMAIN".equals(key)) {
                        if (StringUtils.isEmpty(value)) {
                            this.cdnConfig.setCdnDomain("/");
                        }
                        else {
                            final String[] cdnDomains = value.split(",");
                            final int randomIndex = RandomUtils.nextInt(cdnDomains.length);
                            final String cdnDomain = cdnDomains[randomIndex];
                            if (!cdnDomain.endsWith("/")) {
                                this.pcdnConfig.setCdnDomain(String.valueOf(cdnDomain) + "/");
                            }
                            else {
                                this.pcdnConfig.setCdnDomain(cdnDomain);
                            }
                        }
                    }
                    if ("VERSION".equals(key)) {
                        this.cdnConfig.setCdnVersion(value);
                    }
                    if (this.servletContext == null) {
                        continue;
                    }
                    this.servletContext.setAttribute("pcdnDomain", (Object)this.cdnConfig.getCdnDomain());
                    this.servletContext.setAttribute("pcdnVersion", (Object)this.cdnConfig.getCdnVersion());
                }
            }
            LotteryDataFactoryImpl.logger.info("初始化系统配置表完成！");
        }
        catch (Exception e) {
            LotteryDataFactoryImpl.logger.error("初始化系统配置表失败！", (Throwable)e);
        }
    }
    
    @Override
    public CodeConfig getCodeConfig() {
        return this.codeConfig;
    }
    
    @Override
    public LotteryConfig getLotteryConfig() {
        return this.lotteryConfig;
    }
    
    @Override
    public WithdrawConfig getWithdrawConfig() {
        return this.withdrawConfig;
    }
    
    @Override
    public RechargeConfig getRechargeConfig() {
        return this.rechargeConfig;
    }
    
    @Override
    public VipConfig getVipConfig() {
        return this.vipConfig;
    }
    
    @Override
    public PlanConfig getPlanConfig() {
        return this.planConfig;
    }
    
    @Override
    public DividendConfig getDividendConfig() {
        return this.dividendConfig;
    }
    
    @Override
    public GameDividendConfig getGameDividendConfig() {
        return this.gameDividendConfig;
    }
    
    @Override
    public GameDividendConfigRule determineGameDividendRule(final double loss) {
        return this.gameDividendConfig.determineZhuGuanRule(loss);
    }
    
    @Override
    public MailConfig getMailConfig() {
        return this.mailConfig;
    }
    
    @Override
    public DividendConfigRule determineZhaoShangDividendRule(final double dailyBilling) {
        return this.dividendConfig.determineZhaoShangRule(dailyBilling);
    }
    
    @Override
    public DividendConfigRule determineCJZhaoShangDividendRule(final double dailyBilling) {
        return this.dividendConfig.determineCJZhaoShangRule(dailyBilling);
    }
    
    @Override
    public DailySettleConfig getDailySettleConfig() {
        return this.dailySettleConfig;
    }
    
    @Override
    public AdminGoogleConfig getAdminGoogleConfig() {
        return this.adminGoogleConfig;
    }
    
    @Override
    public AdminGlobalConfig getAdminGlobalConfig() {
        return this.adminGlobalConfig;
    }
    
    @Override
    public void initSysPlatform() {
        try {
            final List<SysPlatform> list = this.sysPlatformDao.listAll();
            this.sysPlatformList = list;
            LotteryDataFactoryImpl.logger.info("初始化平台列表完成！");
        }
        catch (Exception e) {
            LotteryDataFactoryImpl.logger.error("初始化平台列表失败！");
        }
    }
    
    @Override
    public List<SysPlatform> listSysPlatform() {
        return this.sysPlatformList;
    }
    
    @Override
    public SysPlatform getSysPlatform(final int id) {
        for (final SysPlatform tmpBean : this.sysPlatformList) {
            if (tmpBean.getId() == id) {
                return tmpBean;
            }
        }
        return null;
    }
    
    @Override
    public SysPlatform getSysPlatform(final String name) {
        for (final SysPlatform tmpBean : this.sysPlatformList) {
            if (tmpBean.getName().equals(name)) {
                return tmpBean;
            }
        }
        return null;
    }
    
    @Override
    public void initUser() {
        try {
            final List<User> list = this.userDao.listAll();
            final Map<Integer, UserVO> tmpMap = new LinkedHashMap<Integer, UserVO>();
            for (final User user : list) {
                tmpMap.put(user.getId(), new UserVO(user.getId(), user.getUsername()));
            }
            this.userMap = tmpMap;
            final Map<String, UserVO> tmpMapWithUserName = new LinkedHashMap<String, UserVO>();
            for (final User user2 : list) {
                tmpMapWithUserName.put(user2.getUsername(), new UserVO(user2.getId(), user2.getUsername()));
            }
            this.userMapWithUserName = tmpMapWithUserName;
            LotteryDataFactoryImpl.logger.info("初始化用户基础信息完成！");
        }
        catch (Exception e) {
            LotteryDataFactoryImpl.logger.error("初始化用户基础信息失败！");
        }
    }
    
    @Override
    public UserVO getUser(final int id) {
        if (this.userMap.containsKey(id)) {
            return this.userMap.get(id);
        }
        final User user = this.userDao.getById(id);
        if (user != null) {
            this.userMap.put(user.getId(), new UserVO(user.getId(), user.getUsername()));
            this.userMapWithUserName.put(user.getUsername(), new UserVO(user.getId(), user.getUsername()));
            return this.userMap.get(id);
        }
        return null;
    }
    
    @Override
    public UserVO getUser(final String username) {
        if (this.userMapWithUserName.containsKey(username)) {
            return this.userMapWithUserName.get(username);
        }
        final User user = this.userDao.getByUsername(username);
        if (user != null) {
            this.userMapWithUserName.put(user.getUsername(), new UserVO(user.getId(), user.getUsername()));
            this.userMap.put(user.getId(), new UserVO(user.getId(), user.getUsername()));
            return this.userMapWithUserName.get(username);
        }
        return null;
    }
    
    @Override
    public void initLotteryType() {
        try {
            final List<LotteryType> list = this.lotteryTypeDao.listAll();
            final Map<Integer, LotteryType> tmpMap = new LinkedHashMap<Integer, LotteryType>();
            for (final LotteryType lotteryType : list) {
                tmpMap.put(lotteryType.getId(), lotteryType);
            }
            if (this.lotteryTypeMap != null) {
                this.lotteryTypeMap.clear();
            }
            this.lotteryTypeMap = tmpMap;
            LotteryDataFactoryImpl.logger.info("初始化彩票类型完成！");
        }
        catch (Exception e) {
            LotteryDataFactoryImpl.logger.error("初始化彩票类型失败！");
        }
    }
    
    @Override
    public LotteryType getLotteryType(final int id) {
        if (this.lotteryTypeMap.containsKey(id)) {
            return this.lotteryTypeMap.get(id);
        }
        return null;
    }
    
    @Override
    public List<LotteryType> listLotteryType() {
        final List<LotteryType> list = new LinkedList<LotteryType>();
        final Object[] keys = this.lotteryTypeMap.keySet().toArray();
        Object[] array;
        for (int length = (array = keys).length, i = 0; i < length; ++i) {
            final Object o = array[i];
            list.add(this.lotteryTypeMap.get(o));
        }
        return list;
    }
    
    @Override
    public void initLottery() {
        try {
            final List<Lottery> list = this.lotteryDao.listAll();
            final Map<Integer, Lottery> tmpMap = new LinkedHashMap<Integer, Lottery>();
            for (final Lottery lottery : list) {
                tmpMap.put(lottery.getId(), lottery);
            }
            if (this.lotteryMap != null) {
                this.lotteryMap.clear();
            }
            this.lotteryMap = tmpMap;
            LotteryDataFactoryImpl.logger.info("初始化彩票信息完成！");
        }
        catch (Exception e) {
            LotteryDataFactoryImpl.logger.error("初始化彩票信息失败！");
        }
    }
    
    @Override
    public Lottery getLottery(final int id) {
        if (this.lotteryMap.containsKey(id)) {
            return this.lotteryMap.get(id);
        }
        return null;
    }
    
    @Override
    public Lottery getLottery(final String shortName) {
        final Object[] keys = this.lotteryMap.keySet().toArray();
        Object[] array;
        for (int length = (array = keys).length, i = 0; i < length; ++i) {
            final Object o = array[i];
            final Lottery lottery = this.lotteryMap.get(o);
            if (lottery.getShortName().equals(shortName)) {
                return lottery;
            }
        }
        return null;
    }
    
    @Override
    public List<Lottery> listLottery() {
        final List<Lottery> list = new LinkedList<Lottery>();
        final Object[] keys = this.lotteryMap.keySet().toArray();
        Object[] array;
        for (int length = (array = keys).length, i = 0; i < length; ++i) {
            final Object o = array[i];
            list.add(this.lotteryMap.get(o));
        }
        return list;
    }
    
    @Override
    public List<Lottery> listLottery(final int type) {
        final List<Lottery> list = new LinkedList<Lottery>();
        final Object[] keys = this.lotteryMap.keySet().toArray();
        Object[] array;
        for (int length = (array = keys).length, i = 0; i < length; ++i) {
            final Object o = array[i];
            final Lottery lottery = this.lotteryMap.get(o);
            if (lottery.getType() == type) {
                list.add(lottery);
            }
        }
        return list;
    }
    
    @Override
    public void initLotteryOpenTime() {
        try {
            final List<LotteryOpenTime> list = this.lotteryOpenTimeDao.listAll();
            final Map<String, List<LotteryOpenTime>> tmpOpenTimeMap = new HashMap<String, List<LotteryOpenTime>>();
            if (CollectionUtils.isNotEmpty((Collection)list)) {
                for (final LotteryOpenTime lotteryOpenTime : list) {
                    if (tmpOpenTimeMap.containsKey(lotteryOpenTime.getLottery())) {
                        tmpOpenTimeMap.get(lotteryOpenTime.getLottery()).add(lotteryOpenTime);
                    }
                    else {
                        final LinkedList<LotteryOpenTime> openTimes = new LinkedList<LotteryOpenTime>();
                        openTimes.add(lotteryOpenTime);
                        tmpOpenTimeMap.put(lotteryOpenTime.getLottery(), openTimes);
                    }
                }
            }
            this.lotteryOpenTimeMap = tmpOpenTimeMap;
            LotteryDataFactoryImpl.logger.info("初始化彩票开奖时间信息完成！");
        }
        catch (Exception e) {
            LotteryDataFactoryImpl.logger.error("初始化彩票开奖时间信息失败！");
        }
    }
    
    @Override
    public List<LotteryOpenTime> listLotteryOpenTime(final String lottery) {
        return this.lotteryOpenTimeMap.get(lottery);
    }
    
    @Override
    public void initLotteryPlayRules() {
        try {
            final List<LotteryPlayRulesGroup> groupList = this.lotteryPlayRulesGroupDao.listAll();
            this.lotteryPlayRulesGroupList = groupList;
            final List<LotteryPlayRules> ruleList = this.lotteryPlayRulesDao.listAll();
            this.lotteryPlayRulesList = ruleList;
            LotteryDataFactoryImpl.logger.info("初始化彩票玩法规则完成！");
        }
        catch (Exception e) {
            LotteryDataFactoryImpl.logger.error("初始化彩票玩法规则失败！");
        }
    }
    
    @Override
    public LotteryPlayRules getLotteryPlayRules(final int id) {
        for (final LotteryPlayRules rule : this.lotteryPlayRulesList) {
            if (id == rule.getId()) {
                return rule;
            }
        }
        return null;
    }
    
    @Override
    public LotteryPlayRulesGroup getLotteryPlayRulesGroup(final int id) {
        for (final LotteryPlayRulesGroup group : this.lotteryPlayRulesGroupList) {
            if (id == group.getId()) {
                return group;
            }
        }
        return null;
    }
    
    @Override
    public void initPaymentBank() {
        try {
            final List<PaymentBank> list = this.paymentBankDao.listAll();
            if (this.paymentBankList != null) {
                this.paymentBankList.clear();
            }
            this.paymentBankList.addAll(list);
            LotteryDataFactoryImpl.logger.info("初始化银行列表完成！");
        }
        catch (Exception e) {
            LotteryDataFactoryImpl.logger.error("初始化银行列表失败！");
        }
    }
    
    @Override
    public List<PaymentBank> listPaymentBank() {
        return this.paymentBankList;
    }
    
    @Override
    public PaymentBank getPaymentBank(final int id) {
        for (final PaymentBank tmpBean : this.paymentBankList) {
            if (tmpBean.getId() == id) {
                return tmpBean;
            }
        }
        return null;
    }
    
    @Override
    public void initPaymentCard() {
        try {
            final List<PaymentCard> list = this.paymentCardService.listAll();
            final Map<Integer, PaymentCardVO> vos = new LinkedHashMap<Integer, PaymentCardVO>();
            if (CollectionUtils.isNotEmpty((Collection)list)) {
                for (final PaymentCard paymentCard : list) {
                    vos.put(paymentCard.getId(), new PaymentCardVO(paymentCard, this));
                }
            }
            this.paymentCardVOs = vos;
            LotteryDataFactoryImpl.logger.info("初始化转账银行卡列表完成！");
        }
        catch (Exception e) {
            LotteryDataFactoryImpl.logger.error("初始化转账银行卡列表失败！");
        }
    }
    
    @Override
    public List<PaymentCardVO> listPaymentCard() {
        return new ArrayList<PaymentCardVO>(this.paymentCardVOs.values());
    }
    
    @Override
    public PaymentCardVO getPaymentCard(final int id) {
        return this.paymentCardVOs.get(id);
    }
    
    @Override
    public void initPaymentChannel() {
        final List<PaymentChannel> paymentChannels = this.paymentChannelService.listAllFullProperties();
        if (CollectionUtils.isEmpty((Collection)paymentChannels)) {
            this.PAYMENT_CHANNEL_FULL_PROPERTY_CACHE.clear();
            this.PAYMENT_CHANNEL_VO_CACHE.clear();
            this.PAYMENT_CHANNEL_SIMPLE_LIST.clear();
            return;
        }
        final Map<Integer, PaymentChannel> tempPaymentChannelsFullProperty = new HashMap<Integer, PaymentChannel>();
        final Map<Integer, PaymentChannelVO> tempPaymentChannelVOs = new HashMap<Integer, PaymentChannelVO>();
        final List<PaymentChannelSimpleVO> tempPaymentChannelSimpleList = new ArrayList<PaymentChannelSimpleVO>();
        for (final PaymentChannel paymentChannel : paymentChannels) {
            tempPaymentChannelsFullProperty.put(paymentChannel.getId(), paymentChannel);
            tempPaymentChannelVOs.put(paymentChannel.getId(), new PaymentChannelVO(paymentChannel));
            tempPaymentChannelSimpleList.add(new PaymentChannelSimpleVO(paymentChannel));
        }
        this.PAYMENT_CHANNEL_FULL_PROPERTY_CACHE = tempPaymentChannelsFullProperty;
        this.PAYMENT_CHANNEL_VO_CACHE = tempPaymentChannelVOs;
        this.PAYMENT_CHANNEL_SIMPLE_LIST = tempPaymentChannelSimpleList;
    }
    
    public void initDemoUser() {
        final int demoCount = this.userDao.getDemoUserCount();
        if (demoCount != this.registConfig.getDemoCount()) {
            for (int i = 0; i < this.registConfig.getDemoCount(); ++i) {
                this.addDemoUser(this.registConfig.getDemoPassword());
            }
        }
        final int fCount = this.userDao.getFictitiousUserCount();
        if (this.registConfig.getFictitiousCount() != fCount) {
            for (int j = 0; j < this.registConfig.getFictitiousCount(); ++j) {
                this.addFictitiousUser(this.registConfig.getFictitiousPassword());
            }
        }
    }
    
    private void addDemoUser(final String parpassword) {
        final String username = StringUtil.getRandomString(8).toLowerCase();
        if (this.userDao.getByUsername(username) != null) {
            this.addDemoUser(parpassword);
        }
        final String password = PasswordUtil.generatePasswordByPlainString(parpassword);
        final String nickname = "试玩用户";
        final double totalMoney = 0.0;
        final double lotteryMoney = 100000.0;
        final double baccaratMoney = 0.0;
        final double freezeMoney = 0.0;
        final double dividendMoney = 0.0;
        final int codeType = 0;
        final double extraPoint = 0.0;
        final String registTime = new Moment().toSimpleTime();
        final int AStatus = 0;
        final int BStatus = 0;
        final int allowEqualCode = 0;
        final int onlineStatus = 0;
        final int allowTransfers = 0;
        final int allowPlatformTransfers = 0;
        final int allowWithdraw = 0;
        final int loginValidate = 0;
        final int bindStatus = 0;
        final int vipLevel = 0;
        final double integral = 0.0;
        final User entity = new User(username, password, nickname, totalMoney, lotteryMoney, baccaratMoney, freezeMoney, dividendMoney, 2, 0, 1800, 0.0, 0.0, codeType, extraPoint, registTime, AStatus, BStatus, onlineStatus, allowEqualCode, allowTransfers, loginValidate, bindStatus, vipLevel, integral, allowPlatformTransfers, allowWithdraw);
        this.userDao.add(entity);
    }
    
    public static void main(final String[] args) {
        final String password = PasswordUtil.generatePasswordByPlainString("fpw7107");
        System.out.println(password);
    }
    
    private void addFictitiousUser(final String parpassword) {
        final String username = StringUtil.getRandUserName();
        if (this.userDao.getByUsername(username) != null) {
            this.addFictitiousUser(parpassword);
        }
        final String password = PasswordUtil.generatePasswordByPlainString(parpassword);
        final String nickname = username;
        final double totalMoney = 0.0;
        final double lotteryMoney = 100000.0;
        final double baccaratMoney = 0.0;
        final double freezeMoney = 0.0;
        final double dividendMoney = 0.0;
        final int codeType = 0;
        final double extraPoint = 0.0;
        final String registTime = new Moment().toSimpleTime();
        final int AStatus = 0;
        final int BStatus = 0;
        final int allowEqualCode = 0;
        final int onlineStatus = 0;
        final int allowTransfers = 0;
        final int allowPlatformTransfers = 0;
        final int allowWithdraw = 0;
        final int loginValidate = 0;
        final int bindStatus = 0;
        final int vipLevel = 0;
        final double integral = 0.0;
        final User entity = new User(username, password, nickname, totalMoney, lotteryMoney, baccaratMoney, freezeMoney, dividendMoney, 4, 0, 1800, 0.0, 0.0, codeType, extraPoint, registTime, AStatus, BStatus, onlineStatus, allowEqualCode, allowTransfers, loginValidate, bindStatus, vipLevel, integral, allowPlatformTransfers, allowWithdraw);
        this.userDao.add(entity);
    }
    
    @Override
    public List<PaymentChannelVO> listPaymentChannelVOs() {
        return new ArrayList<PaymentChannelVO>(this.PAYMENT_CHANNEL_VO_CACHE.values());
    }
    
    @Override
    public List<PaymentChannelSimpleVO> listPaymentChannelVOsSimple() {
        return this.PAYMENT_CHANNEL_SIMPLE_LIST;
    }
    
    @Override
    public PaymentChannelVO getPaymentChannelVO(final int id) {
        return this.PAYMENT_CHANNEL_VO_CACHE.get(id);
    }
    
    @Override
    public PaymentChannelVO getPaymentChannelVO(final String channelCode, final Integer id) {
        for (final PaymentChannelVO channel : this.PAYMENT_CHANNEL_VO_CACHE.values()) {
            if (channelCode.equals(channel.getChannelCode())) {
                if (id == null) {
                    return channel;
                }
                if (id == channel.getId()) {
                    return channel;
                }
                continue;
            }
        }
        return null;
    }
    
    @Override
    public PaymentChannel getPaymentChannelFullProperty(final int id) {
        return this.PAYMENT_CHANNEL_FULL_PROPERTY_CACHE.get(id);
    }
    
    @Override
    public PaymentChannel getPaymentChannelFullProperty(final String channelCode, final Integer id) {
        for (final PaymentChannel channel : this.PAYMENT_CHANNEL_FULL_PROPERTY_CACHE.values()) {
            if (channelCode.equals(channel.getChannelCode())) {
                if (id == null) {
                    return channel;
                }
                if (id == channel.getId()) {
                    return channel;
                }
                continue;
            }
        }
        return null;
    }
    
    @Scheduled(cron = "* 0/3 * * * *")
    public void initGame() {
        this.initGameType();
    }
    
    public void initGameType() {
        final List<GameType> types = this.gameTypeDao.listAll();
        final Map<Integer, GameType> tempTypes = new HashMap<Integer, GameType>();
        for (final GameType type : types) {
            tempTypes.put(type.getId(), type);
        }
        this.GAME_TYPE_CACHE = tempTypes;
    }
    
    @Override
    public GameType getGameType(final int id) {
        return this.GAME_TYPE_CACHE.get(id);
    }
    
    @Override
    public UserGameAccount getGameAccount(final String platformName, final int platformId) {
        if (this.GAME_ACCOUNT_NAME_CACHE.containsKey(platformName)) {
            final Map<Integer, UserGameAccount> platformAccs = this.GAME_ACCOUNT_NAME_CACHE.get(platformName);
            if (platformAccs.containsKey(platformId)) {
                return platformAccs.get(platformId);
            }
        }
        final UserGameAccount gameAccount = this.uGameAccountDao.get(platformName, platformId);
        if (gameAccount == null) {
            return null;
        }
        if (!this.GAME_ACCOUNT_NAME_CACHE.containsKey(platformName)) {
            this.GAME_ACCOUNT_NAME_CACHE.put(platformName, new HashMap<Integer, UserGameAccount>());
        }
        final Map<Integer, UserGameAccount> platformAccounts = this.GAME_ACCOUNT_NAME_CACHE.get(platformName);
        if (!platformAccounts.containsKey(platformId)) {
            platformAccounts.put(platformId, gameAccount);
        }
        return gameAccount;
    }
    
    @Override
    public void initActivityRedPacketRainTimes() {
        try {
            this.rainTimeService.initTimes(2);
            LotteryDataFactoryImpl.logger.info("初始化红包雨时间完成！");
        }
        catch (Exception e) {
            LotteryDataFactoryImpl.logger.error("初始化红包雨时间失败！", (Throwable)e);
        }
    }
    
    @Override
    public ActivityFirstRechargeConfigVO getActivityFirstRechargeConfig() {
        return this.firstRechargeConfig;
    }
}
