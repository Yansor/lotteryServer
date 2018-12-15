package lottery.domains.content.dao;

import lottery.domains.content.entity.ActivityRedPacketRainConfig;

public abstract interface ActivityRedPacketRainConfigDao
{
  public abstract ActivityRedPacketRainConfig getConfig();
  
  public abstract boolean updateConfig(int paramInt1, String paramString1, String paramString2, int paramInt2);
  
  public abstract boolean updateStatus(int paramInt1, int paramInt2);
}
