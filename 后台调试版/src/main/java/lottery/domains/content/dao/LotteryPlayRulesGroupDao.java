package lottery.domains.content.dao;

import java.util.List;
import lottery.domains.content.entity.LotteryPlayRulesGroup;

public abstract interface LotteryPlayRulesGroupDao
{
  public abstract List<LotteryPlayRulesGroup> listAll();
  
  public abstract List<LotteryPlayRulesGroup> listByType(int paramInt);
  
  public abstract LotteryPlayRulesGroup getById(int paramInt);
  
  public abstract boolean update(LotteryPlayRulesGroup paramLotteryPlayRulesGroup);
  
  public abstract boolean updateStatus(int paramInt1, int paramInt2);
}
