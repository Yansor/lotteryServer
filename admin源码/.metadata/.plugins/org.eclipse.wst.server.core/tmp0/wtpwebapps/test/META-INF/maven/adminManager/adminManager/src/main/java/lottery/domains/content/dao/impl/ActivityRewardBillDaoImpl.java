package lottery.domains.content.dao.impl;

import javautils.jdbc.PageList;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.entity.ActivityRewardBill;
import javautils.jdbc.hibernate.HibernateSuperDao;
import org.springframework.stereotype.Repository;
import lottery.domains.content.dao.ActivityRewardBillDao;

@Repository
public class ActivityRewardBillDaoImpl implements ActivityRewardBillDao
{
    private final String tab;
    @Autowired
    private HibernateSuperDao<ActivityRewardBill> superDao;
    
    public ActivityRewardBillDaoImpl() {
        this.tab = ActivityRewardBill.class.getSimpleName();
    }
    
    @Override
    public boolean add(final ActivityRewardBill entity) {
        return this.superDao.save(entity);
    }
    
    @Override
    public ActivityRewardBill getById(final int id) {
        final String hql = "from " + this.tab + " where id = ?0";
        final Object[] values = { id };
        return (ActivityRewardBill)this.superDao.unique(hql, values);
    }
    
    @Override
    public List<ActivityRewardBill> getUntreated(final String date) {
        final String hql = "from " + this.tab + " where date = ?0 and status = 0";
        final Object[] values = { date };
        return this.superDao.list(hql, values);
    }
    
    @Override
    public List<ActivityRewardBill> getLatest(final int toUser, final int status, final int count) {
        final String hql = "from " + this.tab + " where toUser = ?0 and status = 1";
        final Object[] values = { toUser };
        return this.superDao.list(hql, values, 0, count);
    }
    
    @Override
    public boolean hasRecord(final int toUser, final int fromUser, final int type, final String date) {
        final String hql = "from " + this.tab + " where toUser = ?0 and fromUser = ?1 and type = ?2 and date = ?3";
        final Object[] values = { toUser, fromUser, type, date };
        final List<ActivityRewardBill> list = this.superDao.list(hql, values);
        return list != null && list.size() > 0;
    }
    
    @Override
    public boolean update(final ActivityRewardBill entity) {
        return this.superDao.update(entity);
    }
    
    @Override
    public boolean delete(final int id) {
        final String hql = "delete from " + this.tab + " where id = ?0";
        final Object[] values = { id };
        return this.superDao.delete(hql, values);
    }
    
    @Override
    public PageList find(final List<Criterion> criterions, final List<Order> orders, final int start, final int limit) {
        return this.superDao.findPageList(ActivityRewardBill.class, criterions, orders, start, limit);
    }
    
    @Override
    public double total(final String sTime, final String eTime, final int type) {
        final String hql = "select sum(money) from " + this.tab + " where status = 1 and time >= ?0 and time < ?1 and type = ?2";
        final Object[] values = { sTime, eTime, type };
        final Object result = this.superDao.unique(hql, values);
        return (result != null) ? ((Number)result).doubleValue() : 0.0;
    }
}
