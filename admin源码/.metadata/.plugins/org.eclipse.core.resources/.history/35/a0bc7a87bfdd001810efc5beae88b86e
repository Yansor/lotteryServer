package lottery.domains.content.dao.impl;

import javautils.jdbc.PageList;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.entity.UserWithdrawLog;
import javautils.jdbc.hibernate.HibernateSuperDao;
import org.springframework.stereotype.Repository;
import lottery.domains.content.dao.UserWithdrawLogDao;

@Repository
public class UserWithdrawLogDaoImpl implements UserWithdrawLogDao
{
    private final String tab;
    @Autowired
    private HibernateSuperDao<UserWithdrawLog> superDao;
    
    public UserWithdrawLogDaoImpl() {
        this.tab = UserWithdrawLogDao.class.getSimpleName();
    }
    
    @Override
    public boolean add(final UserWithdrawLog entity) {
        return this.superDao.save(entity);
    }
    
    @Override
    public List<UserWithdrawLog> getByUserId(final int userId, final int tpye) {
        final String hql = "from " + this.tab + " where userId = ?0";
        final Object[] values = { userId };
        return this.superDao.list(hql, values);
    }
    
    @Override
    public PageList find(final List<Criterion> criterions, final List<Order> orders, final int start, final int limit) {
        final String propertyName = "id";
        return this.superDao.findPageList(UserWithdrawLog.class, propertyName, criterions, orders, start, limit);
    }
}
