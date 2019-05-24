package lottery.domains.content.dao;

import lottery.domains.content.entity.ActivityFirstRechargeConfig;

public abstract interface ActivityFirstRechargeConfigDao
{
  public abstract ActivityFirstRechargeConfig getConfig();
  
  public abstract boolean updateConfig(int paramInt, String paramString);
  
  public abstract boolean updateStatus(int paramInt1, int paramInt2);
}
