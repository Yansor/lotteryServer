package lottery.domains.pool;

import java.util.List;
import lottery.domains.content.entity.GameType;
import lottery.domains.content.entity.Lottery;
import lottery.domains.content.entity.LotteryOpenTime;
import lottery.domains.content.entity.LotteryPlayRules;
import lottery.domains.content.entity.LotteryPlayRulesGroup;
import lottery.domains.content.entity.LotteryType;
import lottery.domains.content.entity.PaymentBank;
import lottery.domains.content.entity.PaymentChannel;
import lottery.domains.content.entity.SysPlatform;
import lottery.domains.content.entity.UserGameAccount;
import lottery.domains.content.entity.activity.ActivityFirstRechargeConfigVO;
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
import lottery.domains.content.vo.config.RechargeConfig;
import lottery.domains.content.vo.config.VipConfig;
import lottery.domains.content.vo.config.WithdrawConfig;
import lottery.domains.content.vo.payment.PaymentCardVO;
import lottery.domains.content.vo.payment.PaymentChannelSimpleVO;
import lottery.domains.content.vo.payment.PaymentChannelVO;
import lottery.domains.content.vo.user.UserVO;

public abstract interface LotteryDataFactory
{
  public abstract void init();
  
  public abstract void initSysConfig();
  
  public abstract void initCDNConfig();
  
  public abstract CodeConfig getCodeConfig();
  
  public abstract LotteryConfig getLotteryConfig();
  
  public abstract DividendConfig getDividendConfig();
  
  public abstract DividendConfigRule determineZhaoShangDividendRule(double paramDouble);
  
  public abstract DividendConfigRule determineCJZhaoShangDividendRule(double paramDouble);
  
  public abstract GameDividendConfig getGameDividendConfig();
  
  public abstract GameDividendConfigRule determineGameDividendRule(double paramDouble);
  
  public abstract DailySettleConfig getDailySettleConfig();
  
  public abstract WithdrawConfig getWithdrawConfig();
  
  public abstract RechargeConfig getRechargeConfig();
  
  public abstract VipConfig getVipConfig();
  
  public abstract PlanConfig getPlanConfig();
  
  public abstract MailConfig getMailConfig();
  
  public abstract AdminGoogleConfig getAdminGoogleConfig();
  
  public abstract AdminGlobalConfig getAdminGlobalConfig();
  
  public abstract void initSysPlatform();
  
  public abstract List<SysPlatform> listSysPlatform();
  
  public abstract SysPlatform getSysPlatform(int paramInt);
  
  public abstract SysPlatform getSysPlatform(String paramString);
  
  public abstract void initUser();
  
  public abstract UserVO getUser(int paramInt);
  
  public abstract UserVO getUser(String paramString);
  
  public abstract void initLotteryType();
  
  public abstract LotteryType getLotteryType(int paramInt);
  
  public abstract List<LotteryType> listLotteryType();
  
  public abstract void initLottery();
  
  public abstract Lottery getLottery(int paramInt);
  
  public abstract Lottery getLottery(String paramString);
  
  public abstract List<Lottery> listLottery();
  
  public abstract List<Lottery> listLottery(int paramInt);
  
  public abstract void initLotteryOpenTime();
  
  public abstract List<LotteryOpenTime> listLotteryOpenTime(String paramString);
  
  public abstract void initLotteryPlayRules();
  
  public abstract LotteryPlayRules getLotteryPlayRules(int paramInt);
  
  public abstract LotteryPlayRulesGroup getLotteryPlayRulesGroup(int paramInt);
  
  public abstract void initPaymentBank();
  
  public abstract List<PaymentBank> listPaymentBank();
  
  public abstract ActivityFirstRechargeConfigVO getActivityFirstRechargeConfig();
  
  public abstract PaymentBank getPaymentBank(int paramInt);
  
  public abstract void initPaymentCard();
  
  public abstract List<PaymentCardVO> listPaymentCard();
  
  public abstract PaymentCardVO getPaymentCard(int paramInt);
  
  public abstract void initPaymentChannel();
  
  public abstract List<PaymentChannelVO> listPaymentChannelVOs();
  
  public abstract List<PaymentChannelSimpleVO> listPaymentChannelVOsSimple();
  
  public abstract PaymentChannelVO getPaymentChannelVO(int paramInt);
  
  public abstract PaymentChannelVO getPaymentChannelVO(String paramString, Integer paramInteger);
  
  public abstract PaymentChannel getPaymentChannelFullProperty(int paramInt);
  
  public abstract PaymentChannel getPaymentChannelFullProperty(String paramString, Integer paramInteger);
  
  public abstract GameType getGameType(int paramInt);
  
  public abstract UserGameAccount getGameAccount(String paramString, int paramInt);
  
  public abstract void initActivityRedPacketRainTimes();
}
