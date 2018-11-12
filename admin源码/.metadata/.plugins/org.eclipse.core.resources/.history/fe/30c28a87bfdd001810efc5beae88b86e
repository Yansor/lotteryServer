package lottery.domains.content.dao.impl;

import javautils.jdbc.PageList;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.entity.ActivitySignBill;
import javautils.jdbc.hibernate.HibernateSuperDao;
import org.springframework.stereotype.Repository;
import lottery.domains.content.dao.ActivitySignBillDao;

@Repository
public class ActivitySignBillDaoImpl implements ActivitySignBillDao
{
    private final String tab;
    @Autowired
    private HibernateSuperDao<ActivitySignBill> superDao;
    
    public ActivitySignBillDaoImpl() {
        this.tab = ActivitySignBill.class.getSimpleName();
    }
    
    @Override
    public PageList find(final List<Criterion> criterions, final List<Order> orders, final int start, final int limit) {
        return this.superDao.findPageList(ActivitySignBill.class, criterions, orders, start, limit);
    }
    
    @Override
    public double total(final String sTime, final String eTime) {
        final String hql = "select sum(money) from " + this.tab + " where time >= ?0 and time < ?1";
        final Object[] values = { sTime, eTime };
        final Object result = this.superDao.unique(hql, values);
        return (result != null) ? ((Number)result).doubleValue() : 0.0;
    }
}
