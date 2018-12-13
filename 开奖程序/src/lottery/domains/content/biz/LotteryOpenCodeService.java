package lottery.domains.content.biz;

import java.util.List;
import lottery.domains.content.entity.LotteryOpenCode;

public abstract interface LotteryOpenCodeService
{
  public abstract boolean updateOpened(LotteryOpenCode paramLotteryOpenCode);
  
  public abstract boolean updateCancelled(LotteryOpenCode paramLotteryOpenCode);
  
  public abstract List<LotteryOpenCode> getBeforeNotOpen(String paramString, int paramInt);
  
  public abstract LotteryOpenCode getByExcept(String paramString1, String paramString2);
  
  public abstract LotteryOpenCode getByExceptAndUserId(String paramString1, int paramInt, String paramString2);
  
  public abstract boolean add(LotteryOpenCode paramLotteryOpenCode);
  
  public abstract boolean hasCaptured(String paramString1, String paramString2);
  
  public abstract void initLotteryOpenCode();
}


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/content/biz/LotteryOpenCodeService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */