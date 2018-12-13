package lottery.domains.utils.open;

import java.util.List;

public abstract interface OpenTimeUtil
{
  public abstract OpenTime getCurrOpenTime(int paramInt, String paramString);
  
  public abstract OpenTime getLastOpenTime(int paramInt, String paramString);
  
  public abstract List<OpenTime> getOpenTimeList(int paramInt1, int paramInt2);
  
  public abstract List<OpenTime> getOpenDateList(int paramInt, String paramString);
  
  public abstract OpenTime getOpenTime(int paramInt, String paramString);
}


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/utils/open/OpenTimeUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */