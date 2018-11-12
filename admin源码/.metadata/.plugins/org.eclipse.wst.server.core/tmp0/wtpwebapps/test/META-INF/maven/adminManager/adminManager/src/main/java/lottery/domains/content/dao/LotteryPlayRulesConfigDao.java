package lottery.domains.content.dao;

import lottery.domains.content.entity.LotteryPlayRulesConfig;
import java.util.List;

public interface LotteryPlayRulesConfigDao
{
    List<LotteryPlayRulesConfig> listAll();
    
    List<LotteryPlayRulesConfig> listByLottery(final int p0);
    
    List<LotteryPlayRulesConfig> listByLotteryAndRule(final int p0, final List<Integer> p1);
    
    LotteryPlayRulesConfig get(final int p0, final int p1);
    
    boolean save(final LotteryPlayRulesConfig p0);
    
    boolean update(final LotteryPlayRulesConfig p0);
    
    boolean updateStatus(final int p0, final int p1, final int p2);
    
    boolean updateStatus(final int p0, final int p1);
    
    boolean update(final int p0, final String p1, final String p2);
}
