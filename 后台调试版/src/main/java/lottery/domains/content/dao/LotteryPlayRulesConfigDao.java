package lottery.domains.content.dao;

import java.util.List;
import lottery.domains.content.entity.LotteryPlayRulesConfig;

public abstract interface LotteryPlayRulesConfigDao
{
  public abstract List<LotteryPlayRulesConfig> listAll();
  
  public abstract List<LotteryPlayRulesConfig> listByLottery(int paramInt);
  
  public abstract List<LotteryPlayRulesConfig> listByLotteryAndRule(int paramInt, List<Integer> paramList);
  
  public abstract LotteryPlayRulesConfig get(int paramInt1, int paramInt2);
  
  public abstract boolean save(LotteryPlayRulesConfig paramLotteryPlayRulesConfig);
  
  public abstract boolean update(LotteryPlayRulesConfig paramLotteryPlayRulesConfig);
  
  public abstract boolean updateStatus(int paramInt1, int paramInt2, int paramInt3);
  
  public abstract boolean updateStatus(int paramInt1, int paramInt2);
  
  public abstract boolean update(int paramInt, String paramString1, String paramString2);
}
