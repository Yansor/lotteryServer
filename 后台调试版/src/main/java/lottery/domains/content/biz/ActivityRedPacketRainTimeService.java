package lottery.domains.content.biz;

import lottery.domains.content.entity.ActivityRedPacketRainTime;

public abstract interface ActivityRedPacketRainTimeService
{
  public abstract boolean add(ActivityRedPacketRainTime paramActivityRedPacketRainTime);
  
  public abstract ActivityRedPacketRainTime getByDateAndHour(String paramString1, String paramString2);
  
  public abstract boolean initTimes(int paramInt);
}
