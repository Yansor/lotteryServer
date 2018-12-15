package lottery.domains.content.dao;

import lottery.domains.content.entity.UserPlanInfo;

public abstract interface UserPlanInfoDao
{
  public abstract boolean add(UserPlanInfo paramUserPlanInfo);
  
  public abstract UserPlanInfo get(int paramInt);
  
  public abstract boolean update(UserPlanInfo paramUserPlanInfo);
  
  public abstract boolean update(int paramInt1, int paramInt2, int paramInt3, double paramDouble1, double paramDouble2);
  
  public abstract boolean updateLevel(int paramInt1, int paramInt2);
}
