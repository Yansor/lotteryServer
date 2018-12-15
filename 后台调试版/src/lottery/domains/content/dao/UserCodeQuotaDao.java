package lottery.domains.content.dao;

import java.util.List;
import lottery.domains.content.entity.UserCodeQuota;

public abstract interface UserCodeQuotaDao
{
  public abstract boolean add(UserCodeQuota paramUserCodeQuota);
  
  public abstract UserCodeQuota get(int paramInt);
  
  public abstract List<UserCodeQuota> list(int[] paramArrayOfInt);
  
  public abstract boolean update(UserCodeQuota paramUserCodeQuota);
  
  public abstract boolean delete(int paramInt);
}
