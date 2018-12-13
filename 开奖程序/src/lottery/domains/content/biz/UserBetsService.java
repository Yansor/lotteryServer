package lottery.domains.content.biz;

import java.util.List;
import lottery.domains.content.entity.LotteryOpenCode;
import lottery.domains.content.entity.UserBets;

public abstract interface UserBetsService
{
  public abstract boolean cancelChase(String paramString1, int paramInt, String paramString2);
  
  public abstract boolean isCancelChase(String paramString);
  
  public abstract void cancelByTXFFCInvalid(LotteryOpenCode paramLotteryOpenCode, List<UserBets> paramList);
  
  public abstract void cancelByTXLHDInvalid(LotteryOpenCode paramLotteryOpenCode, List<UserBets> paramList);
}


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/content/biz/UserBetsService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */