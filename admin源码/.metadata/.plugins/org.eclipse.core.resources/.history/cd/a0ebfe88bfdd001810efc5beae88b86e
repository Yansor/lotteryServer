package lottery.domains.content.dao;

import lottery.domains.content.entity.LotteryPlayRules;
import java.util.List;

public interface LotteryPlayRulesDao
{
    List<LotteryPlayRules> listAll();
    
    List<LotteryPlayRules> listByType(final int p0);
    
    List<LotteryPlayRules> listByTypeAndGroup(final int p0, final int p1);
    
    LotteryPlayRules getById(final int p0);
    
    boolean update(final LotteryPlayRules p0);
    
    boolean updateStatus(final int p0, final int p1);
    
    boolean update(final int p0, final String p1, final String p2);
}
