package lottery.domains.content.biz;

import java.util.Map;
import lottery.domains.content.entity.UserWithdrawLimit;

public abstract interface UserWithdrawLimitService
{
  public abstract boolean delByUserId(int paramInt);
  
  public abstract boolean add(int paramInt1, double paramDouble1, String paramString, int paramInt2, int paramInt3, double paramDouble2);
  
  public abstract UserWithdrawLimit getByUserId(int paramInt);
  
  public abstract Map<String, Object> getUserWithdrawLimits(int paramInt, String paramString);
}
