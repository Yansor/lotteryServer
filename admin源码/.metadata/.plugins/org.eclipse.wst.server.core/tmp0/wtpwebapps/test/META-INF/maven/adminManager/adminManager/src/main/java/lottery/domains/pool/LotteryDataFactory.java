package lottery.domains.pool;

import lottery.domains.content.entity.UserGameAccount;
import lottery.domains.content.entity.GameType;
import lottery.domains.content.entity.PaymentChannel;
import lottery.domains.content.vo.payment.PaymentChannelSimpleVO;
import lottery.domains.content.vo.payment.PaymentChannelVO;
import lottery.domains.content.vo.payment.PaymentCardVO;
import lottery.domains.content.entity.activity.ActivityFirstRechargeConfigVO;
import lottery.domains.content.entity.PaymentBank;
import lottery.domains.content.entity.LotteryPlayRulesGroup;
import lottery.domains.content.entity.LotteryPlayRules;
import lottery.domains.content.entity.LotteryOpenTime;
import lottery.domains.content.entity.Lottery;
import lottery.domains.content.entity.LotteryType;
import lottery.domains.content.vo.user.UserVO;
import lottery.domains.content.entity.SysPlatform;
import java.util.List;
import lottery.domains.content.vo.config.AdminGlobalConfig;
import lottery.domains.content.vo.config.AdminGoogleConfig;
import lottery.domains.content.vo.config.MailConfig;
import lottery.domains.content.vo.config.PlanConfig;
import lottery.domains.content.vo.config.VipConfig;
import lottery.domains.content.vo.config.RechargeConfig;
import lottery.domains.content.vo.config.WithdrawConfig;
import lottery.domains.content.vo.config.DailySettleConfig;
import lottery.domains.content.vo.config.GameDividendConfigRule;
import lottery.domains.content.vo.config.GameDividendConfig;
import lottery.domains.content.vo.config.DividendConfigRule;
import lottery.domains.content.vo.config.DividendConfig;
import lottery.domains.content.vo.config.LotteryConfig;
import lottery.domains.content.vo.config.CodeConfig;

public interface LotteryDataFactory
{
    void init();
    
    void initSysConfig();
    
    void initCDNConfig();
    
    CodeConfig getCodeConfig();
    
    LotteryConfig getLotteryConfig();
    
    DividendConfig getDividendConfig();
    
    DividendConfigRule determineZhaoShangDividendRule(final double p0);
    
    DividendConfigRule determineCJZhaoShangDividendRule(final double p0);
    
    GameDividendConfig getGameDividendConfig();
    
    GameDividendConfigRule determineGameDividendRule(final double p0);
    
    DailySettleConfig getDailySettleConfig();
    
    WithdrawConfig getWithdrawConfig();
    
    RechargeConfig getRechargeConfig();
    
    VipConfig getVipConfig();
    
    PlanConfig getPlanConfig();
    
    MailConfig getMailConfig();
    
    AdminGoogleConfig getAdminGoogleConfig();
    
    AdminGlobalConfig getAdminGlobalConfig();
    
    void initSysPlatform();
    
    List<SysPlatform> listSysPlatform();
    
    SysPlatform getSysPlatform(final int p0);
    
    SysPlatform getSysPlatform(final String p0);
    
    void initUser();
    
    UserVO getUser(final int p0);
    
    UserVO getUser(final String p0);
    
    void initLotteryType();
    
    LotteryType getLotteryType(final int p0);
    
    List<LotteryType> listLotteryType();
    
    void initLottery();
    
    Lottery getLottery(final int p0);
    
    Lottery getLottery(final String p0);
    
    List<Lottery> listLottery();
    
    List<Lottery> listLottery(final int p0);
    
    void initLotteryOpenTime();
    
    List<LotteryOpenTime> listLotteryOpenTime(final String p0);
    
    void initLotteryPlayRules();
    
    LotteryPlayRules getLotteryPlayRules(final int p0);
    
    LotteryPlayRulesGroup getLotteryPlayRulesGroup(final int p0);
    
    void initPaymentBank();
    
    List<PaymentBank> listPaymentBank();
    
    ActivityFirstRechargeConfigVO getActivityFirstRechargeConfig();
    
    PaymentBank getPaymentBank(final int p0);
    
    void initPaymentCard();
    
    List<PaymentCardVO> listPaymentCard();
    
    PaymentCardVO getPaymentCard(final int p0);
    
    void initPaymentChannel();
    
    List<PaymentChannelVO> listPaymentChannelVOs();
    
    List<PaymentChannelSimpleVO> listPaymentChannelVOsSimple();
    
    PaymentChannelVO getPaymentChannelVO(final int p0);
    
    PaymentChannelVO getPaymentChannelVO(final String p0, final Integer p1);
    
    PaymentChannel getPaymentChannelFullProperty(final int p0);
    
    PaymentChannel getPaymentChannelFullProperty(final String p0, final Integer p1);
    
    GameType getGameType(final int p0);
    
    UserGameAccount getGameAccount(final String p0, final int p1);
    
    void initActivityRedPacketRainTimes();
}
