package lottery.domains.content.dao.impl;

import javautils.jdbc.PageList;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.entity.UserTransfers;
import javautils.jdbc.hibernate.HibernateSuperDao;
import org.springframework.stereotype.Repository;
import lottery.domains.content.dao.UserTransfersDao;

@Repository
public class UserTransfersDaoImpl implements UserTransfersDao
{
    private final String tab;
    @Autowired
    private HibernateSuperDao<UserTransfers> superDao;
    
    public UserTransfersDaoImpl() {
        this.tab = UserTransfers.class.getSimpleName();
    }
    
    @Override
    public boolean add(final UserTransfers entity) {
        return this.superDao.save(entity);
    }
    
    @Override
    public PageList search(final List<Criterion> criterions, final List<Order> orders, final int start, final int limit) {
        return this.superDao.findPageList(UserTransfers.class, criterions, orders, start, limit);
    }
    
    @Override
    public double getTotalTransfers(final String sTime, final String eTime, final int type) {
        final String hql = "select sum(money) from " + this.tab + " where time >= ?0 and time < ?1 and type = ?2";
        final Object[] values = { sTime, eTime, type };
        final Object result = this.superDao.unique(hql, values);
        return (result != null) ? ((Number)result).doubleValue() : 0.0;
    }
}
