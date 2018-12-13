package lottery.domains.content.biz;

import java.util.List;
import lottery.domains.content.entity.Lottery;
import lottery.domains.content.entity.LotteryOpenCode;
import lottery.domains.content.entity.UserBets;

public abstract interface UserBetsSettleService
{
  public abstract void settleUserBets(List<UserBets> paramList, LotteryOpenCode paramLotteryOpenCode, Lottery paramLottery);
  
  public abstract Object[] testUsersBets(UserBets paramUserBets, String paramString, Lottery paramLottery, boolean paramBoolean);
}


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/content/biz/UserBetsSettleService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */