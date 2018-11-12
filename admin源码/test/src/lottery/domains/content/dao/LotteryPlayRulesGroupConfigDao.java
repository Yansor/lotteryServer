package lottery.domains.content.dao;

import lottery.domains.content.entity.LotteryPlayRulesGroupConfig;
import java.util.List;

public interface LotteryPlayRulesGroupConfigDao
{
    List<LotteryPlayRulesGroupConfig> listAll();
    
    List<LotteryPlayRulesGroupConfig> listByLottery(final int p0);
    
    LotteryPlayRulesGroupConfig get(final int p0, final int p1);
    
    boolean save(final LotteryPlayRulesGroupConfig p0);
    
    boolean update(final LotteryPlayRulesGroupConfig p0);
    
    boolean updateStatus(final int p0, final int p1, final int p2);
    
    boolean updateStatus(final int p0, final int p1);
}
