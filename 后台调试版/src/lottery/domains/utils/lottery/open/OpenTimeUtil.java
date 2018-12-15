package lottery.domains.utils.lottery.open;

import java.util.List;

public abstract interface OpenTimeUtil
{
  public abstract OpenTime getCurrOpenTime(int paramInt, String paramString);
  
  public abstract OpenTime getLastOpenTime(int paramInt, String paramString);
  
  public abstract List<OpenTime> getOpenTimeList(int paramInt1, int paramInt2);
  
  public abstract List<OpenTime> getOpenDateList(int paramInt, String paramString);
  
  public abstract OpenTime getOpenTime(int paramInt, String paramString);
}
