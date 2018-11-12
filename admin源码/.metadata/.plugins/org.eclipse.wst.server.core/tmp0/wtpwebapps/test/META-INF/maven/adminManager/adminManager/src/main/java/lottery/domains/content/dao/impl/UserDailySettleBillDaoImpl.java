package lottery.domains.content.dao.impl;

import javautils.jdbc.PageList;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.entity.UserDailySettleBill;
import javautils.jdbc.hibernate.HibernateSuperDao;
import org.springframework.stereotype.Repository;
import lottery.domains.content.dao.UserDailySettleBillDao;

@Repository
public class UserDailySettleBillDaoImpl implements UserDailySettleBillDao
{
    private final String tab;
    @Autowired
    private HibernateSuperDao<UserDailySettleBill> superDao;
    
    public UserDailySettleBillDaoImpl() {
        this.tab = UserDailySettleBill.class.getSimpleName();
    }
    
    @Override
    public PageList search(final List<Criterion> criterions, final List<Order> orders, final int start, final int limit) {
        final String propertyName = "id";
        return this.superDao.findPageList(UserDailySettleBill.class, propertyName, criterions, orders, start, limit);
    }
    
    @Override
    public List<UserDailySettleBill> findByCriteria(final List<Criterion> criterions, final List<Order> orders) {
        return this.superDao.findByCriteria(UserDailySettleBill.class, criterions, orders);
    }
    
    @Override
    public boolean add(final UserDailySettleBill settleBill) {
        return this.superDao.save(settleBill);
    }
    
    @Override
    public UserDailySettleBill getById(final int id) {
        final String hql = "from " + this.tab + " where id = ?0";
        final Object[] values = { id };
        return (UserDailySettleBill)this.superDao.unique(hql, values);
    }
    
    @Override
    public boolean update(final UserDailySettleBill settleBill) {
        return this.superDao.update(settleBill);
    }
}
