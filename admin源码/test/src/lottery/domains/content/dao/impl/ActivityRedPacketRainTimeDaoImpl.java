package lottery.domains.content.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.entity.ActivityRedPacketRainTime;
import javautils.jdbc.hibernate.HibernateSuperDao;
import org.springframework.stereotype.Repository;
import lottery.domains.content.dao.ActivityRedPacketRainTimeDao;

@Repository
public class ActivityRedPacketRainTimeDaoImpl implements ActivityRedPacketRainTimeDao
{
    private final String tab;
    @Autowired
    private HibernateSuperDao<ActivityRedPacketRainTime> superDao;
    
    public ActivityRedPacketRainTimeDaoImpl() {
        this.tab = ActivityRedPacketRainTime.class.getSimpleName();
    }
    
    @Override
    public boolean add(final ActivityRedPacketRainTime time) {
        return this.superDao.save(time);
    }
    
    @Override
    public ActivityRedPacketRainTime getByDateAndHour(final String date, final String hour) {
        final String hql = "from " + this.tab + " where date = ?0 and hour = ?1";
        final Object[] values = { date, hour };
        return (ActivityRedPacketRainTime)this.superDao.unique(hql, values);
    }
}
