package lottery.domains.content.dao;

import java.util.List;
import lottery.domains.content.entity.LotteryPlayRules;

public abstract interface LotteryPlayRulesDao
{
  public abstract List<LotteryPlayRules> listAll();
  
  public abstract List<LotteryPlayRules> listByType(int paramInt);
  
  public abstract List<LotteryPlayRules> listByTypeAndGroup(int paramInt1, int paramInt2);
  
  public abstract LotteryPlayRules getById(int paramInt);
  
  public abstract boolean update(LotteryPlayRules paramLotteryPlayRules);
  
  public abstract boolean updateStatus(int paramInt1, int paramInt2);
  
  public abstract boolean update(int paramInt, String paramString1, String paramString2);
}
