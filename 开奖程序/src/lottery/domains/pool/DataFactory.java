package lottery.domains.pool;

import java.util.List;
import lottery.domains.content.entity.Lottery;
import lottery.domains.content.entity.LotteryOpenTime;
import lottery.domains.content.entity.LotteryPlayRules;
import lottery.domains.content.entity.LotteryPlayRulesGroup;
import lottery.domains.content.vo.config.CodeConfig;
import lottery.domains.content.vo.config.LotteryConfig;
import lottery.domains.content.vo.config.MailConfig;
import lottery.domains.content.vo.config.PlanConfig;
import lottery.domains.content.vo.config.SelfLotteryConfig;
import lottery.domains.content.vo.user.UserVO;

public abstract interface DataFactory
{
  public abstract void init();
  
  public abstract void initSysConfig();
  
  public abstract CodeConfig getCodeConfig();
  
  public abstract LotteryConfig getLotteryConfig();
  
  public abstract PlanConfig getPlanConfig();
  
  public abstract MailConfig getMailConfig();
  
  public abstract SelfLotteryConfig getSelfLotteryConfig();
  
  public abstract void initLottery();
  
  public abstract Lottery getLottery(int paramInt);
  
  public abstract Lottery getLottery(String paramString);
  
  public abstract List<Lottery> listLottery();
  
  public abstract List<Lottery> listLottery(int paramInt);
  
  public abstract List<Lottery> listSelfLottery();
  
  public abstract void initLotteryOpenTime();
  
  public abstract List<LotteryOpenTime> listLotteryOpenTime(String paramString);
  
  public abstract void initLotteryPlayRules();
  
  public abstract LotteryPlayRules getLotteryPlayRules(int paramInt1, int paramInt2);
  
  public abstract LotteryPlayRules getLotteryPlayRules(int paramInt, String paramString);
  
  public abstract LotteryPlayRulesGroup getLotteryPlayRulesGroup(int paramInt1, int paramInt2);
  
  public abstract void initLotteryOpenCode();
  
  public abstract void initUser();
  
  public abstract UserVO getUser(int paramInt);
}


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/pool/DataFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */