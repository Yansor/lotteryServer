package lottery.domains.content.dao;

import java.util.List;
import lottery.domains.content.entity.UserWithdrawLimit;

public abstract interface UserWithdrawLimitDao
{
  public abstract UserWithdrawLimit getByUserId(int paramInt);
  
  public abstract List<UserWithdrawLimit> getUserWithdrawLimits(int paramInt, String paramString);
  
  public abstract boolean add(UserWithdrawLimit paramUserWithdrawLimit);
  
  public abstract boolean delByUserId(int paramInt);
}
