package lottery.domains.content.biz;

import javautils.jdbc.PageList;
import lottery.domains.content.entity.LotteryOpenTime;

public abstract interface LotteryOpenTimeService
{
  public abstract PageList search(String paramString1, String paramString2, int paramInt1, int paramInt2);
  
  public abstract boolean modify(int paramInt, String paramString1, String paramString2);
  
  public abstract boolean modifyRefExpect(String paramString, int paramInt);
  
  public abstract boolean modify(String paramString, int paramInt);
  
  public abstract LotteryOpenTime getByLottery(String paramString);
  
  public abstract boolean update(LotteryOpenTime paramLotteryOpenTime);
}
