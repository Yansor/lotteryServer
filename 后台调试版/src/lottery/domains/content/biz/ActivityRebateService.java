package lottery.domains.content.biz;

import lottery.domains.content.entity.ActivityRebate;

public abstract interface ActivityRebateService
{
  public abstract boolean updateStatus(int paramInt1, int paramInt2);
  
  public abstract boolean edit(int paramInt, String paramString1, String paramString2, String paramString3);
  
  public abstract ActivityRebate getByType(int paramInt);
  
  public abstract ActivityRebate getById(int paramInt);
}
