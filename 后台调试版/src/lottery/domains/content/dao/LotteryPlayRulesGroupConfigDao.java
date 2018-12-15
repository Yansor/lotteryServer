package lottery.domains.content.dao;

import java.util.List;
import lottery.domains.content.entity.LotteryPlayRulesGroupConfig;

public abstract interface LotteryPlayRulesGroupConfigDao
{
  public abstract List<LotteryPlayRulesGroupConfig> listAll();
  
  public abstract List<LotteryPlayRulesGroupConfig> listByLottery(int paramInt);
  
  public abstract LotteryPlayRulesGroupConfig get(int paramInt1, int paramInt2);
  
  public abstract boolean save(LotteryPlayRulesGroupConfig paramLotteryPlayRulesGroupConfig);
  
  public abstract boolean update(LotteryPlayRulesGroupConfig paramLotteryPlayRulesGroupConfig);
  
  public abstract boolean updateStatus(int paramInt1, int paramInt2, int paramInt3);
  
  public abstract boolean updateStatus(int paramInt1, int paramInt2);
}
