package lottery.domains.content.dao.impl;

import javautils.jdbc.PageList;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.entity.ActivityBindBill;
import javautils.jdbc.hibernate.HibernateSuperDao;
import org.springframework.stereotype.Repository;
import lottery.domains.content.dao.ActivityBindBillDao;

@Repository
public class ActivityBindBillDaoImpl implements ActivityBindBillDao
{
    private final String tab;
    @Autowired
    private HibernateSuperDao<ActivityBindBill> superDao;
    
    public ActivityBindBillDaoImpl() {
        this.tab = ActivityBindBill.class.getSimpleName();
    }
    
    @Override
    public ActivityBindBill getById(final int id) {
        final String hql = "from " + this.tab + " where id = ?0";
        final Object[] values = { id };
        return (ActivityBindBill)this.superDao.unique(hql, values);
    }
    
    @Override
    public int getWaitTodo() {
        final String hql = "select count(id) from " + this.tab + " where status = 0";
        final Object result = this.superDao.unique(hql);
        return (result != null) ? ((Number)result).intValue() : 0;
    }
    
    @Override
    public List<ActivityBindBill> get(final String ip, final String bindName, final String bindCard) {
        final String hql = "from " + this.tab + " where ip = ?0 or bindName = ?1 or bindCard = ?2";
        final Object[] values = { ip, bindName, bindCard };
        return this.superDao.list(hql, values);
    }
    
    @Override
    public boolean update(final ActivityBindBill entity) {
        return this.superDao.update(entity);
    }
    
    @Override
    public PageList find(final List<Criterion> criterions, final List<Order> orders, final int start, final int limit) {
        return this.superDao.findPageList(ActivityBindBill.class, criterions, orders, start, limit);
    }
    
    @Override
    public double total(final String sTime, final String eTime) {
        final String hql = "select sum(money) from " + this.tab + " where status = 1 and time >= ?0 and time < ?1";
        final Object[] values = { sTime, eTime };
        final Object result = this.superDao.unique(hql, values);
        return (result != null) ? ((Number)result).doubleValue() : 0.0;
    }
}
