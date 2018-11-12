package lottery.domains.content.dao;

import lottery.domains.content.entity.LotteryPlayRulesGroup;
import java.util.List;

public interface LotteryPlayRulesGroupDao
{
    List<LotteryPlayRulesGroup> listAll();
    
    List<LotteryPlayRulesGroup> listByType(final int p0);
    
    LotteryPlayRulesGroup getById(final int p0);
    
    boolean update(final LotteryPlayRulesGroup p0);
    
    boolean updateStatus(final int p0, final int p1);
}
