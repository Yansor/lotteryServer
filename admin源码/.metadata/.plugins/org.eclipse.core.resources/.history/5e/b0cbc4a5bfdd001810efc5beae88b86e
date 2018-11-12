package lottery.domains.content.dao.impl;

import javautils.jdbc.PageList;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.entity.UserBetsLimit;
import javautils.jdbc.hibernate.HibernateSuperDao;
import org.springframework.stereotype.Repository;
import lottery.domains.content.dao.UserBetsLimitDao;

@Repository
public class UserBetsLimitDaoImpl implements UserBetsLimitDao
{
    private final String tab;
    @Autowired
    private HibernateSuperDao<UserBetsLimit> superDao;
    
    public UserBetsLimitDaoImpl() {
        this.tab = UserBetsLimit.class.getSimpleName();
    }
    
    @Override
    public PageList find(final List<Criterion> criterions, final List<Order> orders, final int start, final int limit) {
        return this.superDao.findPageList(UserBetsLimit.class, criterions, orders, start, limit);
    }
    
    @Override
    public boolean save(final UserBetsLimit entity) {
        return this.superDao.save(entity);
    }
    
    @Override
    public UserBetsLimit getByUserId(final int userId) {
        final String hql = "from " + this.tab + " where userId = ?0";
        final Object[] values = { userId };
        final List<UserBetsLimit> list = this.superDao.list(hql, values);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }
    
    @Override
    public boolean update(final UserBetsLimit entity) {
        return this.superDao.update(entity);
    }
    
    @Override
    public boolean delete(final int id) {
        final String hql = "delete from " + this.tab + " where id = ?0";
        final Object[] values = { id };
        return this.superDao.delete(hql, values);
    }
    
    @Override
    public UserBetsLimit getById(final int id) {
        final String hql = "from " + this.tab + " where id = ?0";
        final Object[] values = { id };
        final List<UserBetsLimit> list = this.superDao.list(hql, values);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }
}
