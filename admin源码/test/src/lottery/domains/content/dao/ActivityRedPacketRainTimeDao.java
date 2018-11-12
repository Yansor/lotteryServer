package lottery.domains.content.dao;

import lottery.domains.content.entity.ActivityRedPacketRainTime;

public interface ActivityRedPacketRainTimeDao
{
    boolean add(final ActivityRedPacketRainTime p0);
    
    ActivityRedPacketRainTime getByDateAndHour(final String p0, final String p1);
}
