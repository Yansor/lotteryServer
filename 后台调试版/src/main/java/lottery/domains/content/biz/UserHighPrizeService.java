package lottery.domains.content.biz;

import java.util.Map;
import javautils.jdbc.PageList;
import lottery.domains.content.entity.GameBets;
import lottery.domains.content.entity.UserHighPrize;

public abstract interface UserHighPrizeService
{
  public abstract PageList search(Integer paramInteger1, String paramString1, Integer paramInteger2, String paramString2, String paramString3, String paramString4, Double paramDouble1, Double paramDouble2, Double paramDouble3, Double paramDouble4, Double paramDouble5, Double paramDouble6, String paramString5, String paramString6, Integer paramInteger3, String paramString7, int paramInt1, int paramInt2);
  
  public abstract UserHighPrize getById(int paramInt);
  
  public abstract boolean lock(int paramInt, String paramString);
  
  public abstract boolean unlock(int paramInt, String paramString);
  
  public abstract boolean confirm(int paramInt, String paramString);
  
  public abstract void addIfNecessary(GameBets paramGameBets);
  
  public abstract int getUnProcessCount();
  
  public abstract Map<String, String> getAllHighPrizeNotices();
  
  public abstract void delHighPrizeNotice(String paramString);
}
