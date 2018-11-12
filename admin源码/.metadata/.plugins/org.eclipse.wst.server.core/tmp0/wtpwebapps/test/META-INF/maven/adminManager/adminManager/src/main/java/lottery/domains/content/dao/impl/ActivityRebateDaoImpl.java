package lottery.domains.content.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.entity.ActivityRebate;
import javautils.jdbc.hibernate.HibernateSuperDao;
import org.springframework.stereotype.Repository;
import lottery.domains.content.dao.ActivityRebateDao;

@Repository
public class ActivityRebateDaoImpl implements ActivityRebateDao
{
    private final String tab;
    @Autowired
    private HibernateSuperDao<ActivityRebate> superDao;
    
    public ActivityRebateDaoImpl() {
        this.tab = ActivityRebate.class.getSimpleName();
    }
    
    @Override
    public boolean add(final ActivityRebate entity) {
        return this.superDao.save(entity);
    }
    
    @Override
    public ActivityRebate getById(final int id) {
        final String hql = "from " + this.tab + " where id = ?0";
        final Object[] values = { id };
        return (ActivityRebate)this.superDao.unique(hql, values);
    }
    
    @Override
    public ActivityRebate getByType(final int type) {
        final String hql = "from " + this.tab + " where type = ?0";
        final Object[] values = { type };
        return (ActivityRebate)this.superDao.unique(hql, values);
    }
    
    @Override
    public boolean update(final ActivityRebate entity) {
        return this.superDao.update(entity);
    }
}
