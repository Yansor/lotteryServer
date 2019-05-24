package lottery.domains.content.dao;

import lottery.domains.content.entity.ActivityRedPacketRainTime;

public abstract interface ActivityRedPacketRainTimeDao
{
  public abstract boolean add(ActivityRedPacketRainTime paramActivityRedPacketRainTime);
  
  public abstract ActivityRedPacketRainTime getByDateAndHour(String paramString1, String paramString2);
}
