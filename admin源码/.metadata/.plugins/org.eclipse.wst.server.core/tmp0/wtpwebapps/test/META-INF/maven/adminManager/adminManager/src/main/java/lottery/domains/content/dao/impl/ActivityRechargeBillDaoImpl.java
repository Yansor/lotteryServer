package lottery.domains.content.dao.impl;

import javautils.jdbc.PageList;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.entity.ActivityRechargeBill;
import javautils.jdbc.hibernate.HibernateSuperDao;
import org.springframework.stereotype.Repository;
import lottery.domains.content.dao.ActivityRechargeBillDao;

@Repository
public class ActivityRechargeBillDaoImpl implements ActivityRechargeBillDao
{
    private final String tab;
    @Autowired
    private HibernateSuperDao<ActivityRechargeBill> superDao;
    
    public ActivityRechargeBillDaoImpl() {
        this.tab = ActivityRechargeBill.class.getSimpleName();
    }
    
    @Override
    public ActivityRechargeBill getById(final int id) {
        final String hql = "from " + this.tab + " where id = ?0";
        final Object[] values = { id };
        return (ActivityRechargeBill)this.superDao.unique(hql, values);
    }
    
    @Override
    public int getWaitTodo() {
        final String hql = "select count(id) from " + this.tab + " where status = 0";
        final Object result = this.superDao.unique(hql);
        return (result != null) ? ((Number)result).intValue() : 0;
    }
    
    @Override
    public boolean update(final ActivityRechargeBill entity) {
        return this.superDao.update(entity);
    }
    
    @Override
    public boolean add(final ActivityRechargeBill entity) {
        return this.superDao.save(entity);
    }
    
    @Override
    public boolean hasDateRecord(final int userId, final String date) {
        final String hql = "from " + this.tab + " where userId = ?0 and payTime like ?1";
        final Object[] values = { userId, "%" + date + "%" };
        final List<ActivityRechargeBill> list = this.superDao.list(hql, values);
        return list != null && list.size() > 0;
    }
    
    @Override
    public List<ActivityRechargeBill> get(final String ip, final String date) {
        final String hql = "from " + this.tab + " where ip = ?0 and payTime like ?1";
        final Object[] values = { ip, "%" + date + "%" };
        return this.superDao.list(hql, values);
    }
    
    @Override
    public PageList find(final List<Criterion> criterions, final List<Order> orders, final int start, final int limit) {
        return this.superDao.findPageList(ActivityRechargeBill.class, criterions, orders, start, limit);
    }
    
    @Override
    public double total(final String sTime, final String eTime) {
        final String hql = "select sum(money) from " + this.tab + " where status = 1 and time >= ?0 and time < ?1";
        final Object[] values = { sTime, eTime };
        final Object result = this.superDao.unique(hql, values);
        return (result != null) ? ((Number)result).doubleValue() : 0.0;
    }
}
