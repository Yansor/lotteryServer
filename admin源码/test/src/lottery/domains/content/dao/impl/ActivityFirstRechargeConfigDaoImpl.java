package lottery.domains.content.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.entity.ActivityFirstRechargeConfig;
import javautils.jdbc.hibernate.HibernateSuperDao;
import org.springframework.stereotype.Repository;
import lottery.domains.content.dao.ActivityFirstRechargeConfigDao;

@Repository
public class ActivityFirstRechargeConfigDaoImpl implements ActivityFirstRechargeConfigDao
{
    private final String tab;
    @Autowired
    private HibernateSuperDao<ActivityFirstRechargeConfig> superDao;
    
    public ActivityFirstRechargeConfigDaoImpl() {
        this.tab = ActivityFirstRechargeConfig.class.getSimpleName();
    }
    
    @Override
    public ActivityFirstRechargeConfig getConfig() {
        final String hql = "from " + this.tab;
        return this.superDao.list(hql, 0, 1).get(0);
    }
    
    @Override
    public boolean updateConfig(final int id, final String rules) {
        final String hql = "update " + this.tab + " set rules = ?0 where id = ?1";
        final Object[] values = { rules, id };
        return this.superDao.update(hql, values);
    }
    
    @Override
    public boolean updateStatus(final int id, final int status) {
        final String hql = "update " + this.tab + " set status = ?0 where id = ?1";
        final Object[] values = { status, id };
        return this.superDao.update(hql, values);
    }
}
