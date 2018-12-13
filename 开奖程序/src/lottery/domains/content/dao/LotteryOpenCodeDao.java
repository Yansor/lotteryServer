package lottery.domains.content.dao;

import java.util.List;
import lottery.domains.content.entity.LotteryOpenCode;

public abstract interface LotteryOpenCodeDao
{
  public abstract boolean updateOpened(int paramInt, String paramString);
  
  public abstract boolean updateCancelled(int paramInt, String paramString);
  
  public abstract List<LotteryOpenCode> getLatest(String paramString, int paramInt);
  
  public abstract List<LotteryOpenCode> listAfter(String paramString1, String paramString2);
  
  public abstract List<LotteryOpenCode> getBeforeNotOpen(String paramString, int paramInt);
  
  public abstract LotteryOpenCode getByExcept(String paramString1, String paramString2);
  
  public abstract LotteryOpenCode getByExceptAndUserId(String paramString1, int paramInt, String paramString2);
  
  public abstract boolean add(LotteryOpenCode paramLotteryOpenCode);
  
  public abstract boolean update(LotteryOpenCode paramLotteryOpenCode);
}


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/content/dao/LotteryOpenCodeDao.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */