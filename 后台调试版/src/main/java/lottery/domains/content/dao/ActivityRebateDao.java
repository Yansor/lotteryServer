package lottery.domains.content.dao;

import lottery.domains.content.entity.ActivityRebate;

public abstract interface ActivityRebateDao
{
  public abstract boolean add(ActivityRebate paramActivityRebate);
  
  public abstract ActivityRebate getById(int paramInt);
  
  public abstract ActivityRebate getByType(int paramInt);
  
  public abstract boolean update(ActivityRebate paramActivityRebate);
}
