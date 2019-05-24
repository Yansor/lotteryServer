package lottery.domains.content.biz;

import lottery.domains.content.entity.ActivityFirstRechargeConfig;

public abstract interface ActivityFirstRechargeConfigService
{
  public abstract ActivityFirstRechargeConfig getConfig();
  
  public abstract boolean updateConfig(int paramInt, String paramString);
  
  public abstract boolean updateStatus(int paramInt1, int paramInt2);
}
