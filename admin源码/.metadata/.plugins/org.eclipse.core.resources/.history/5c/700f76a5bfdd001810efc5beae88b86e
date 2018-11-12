package lottery.domains.content.dao.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.entity.ActivityRedPacketRainConfig;
import javautils.jdbc.hibernate.HibernateSuperDao;
import org.springframework.stereotype.Repository;
import lottery.domains.content.dao.ActivityRedPacketRainConfigDao;

@Repository
public class ActivityRedPacketRainConfigDaoImpl implements ActivityRedPacketRainConfigDao
{
    private final String tab;
    @Autowired
    private HibernateSuperDao<ActivityRedPacketRainConfig> superDao;
    
    public ActivityRedPacketRainConfigDaoImpl() {
        this.tab = ActivityRedPacketRainConfig.class.getSimpleName();
    }
    
    @Override
    public ActivityRedPacketRainConfig getConfig() {
        final String hql = "from " + this.tab;
        final List<ActivityRedPacketRainConfig> list = this.superDao.list(hql, 0, 1);
        return (list == null || list.size() <= 0) ? null : list.get(0);
    }
    
    @Override
    public boolean updateConfig(final int id, final String rules, final String hours, final int durationMinutes) {
        final String hql = "update " + this.tab + " set rules = ?0, hours = ?1, durationMinutes = ?2 where id = ?3";
        final Object[] values = { rules, hours, durationMinutes, id };
        return this.superDao.update(hql, values);
    }
    
    @Override
    public boolean updateStatus(final int id, final int status) {
        final String hql = "update " + this.tab + " set status = ?0 where id = ?1";
        final Object[] values = { status, id };
        return this.superDao.update(hql, values);
    }
}
