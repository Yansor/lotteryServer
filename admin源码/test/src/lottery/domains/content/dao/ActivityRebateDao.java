package lottery.domains.content.dao;

import lottery.domains.content.entity.ActivityRebate;

public interface ActivityRebateDao
{
    boolean add(final ActivityRebate p0);
    
    ActivityRebate getById(final int p0);
    
    ActivityRebate getByType(final int p0);
    
    boolean update(final ActivityRebate p0);
}
