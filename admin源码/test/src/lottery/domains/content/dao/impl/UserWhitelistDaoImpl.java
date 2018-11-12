package lottery.domains.content.dao.impl;

import javautils.jdbc.PageList;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.entity.UserWhitelist;
import javautils.jdbc.hibernate.HibernateSuperDao;
import org.springframework.stereotype.Repository;
import lottery.domains.content.dao.UserWhitelistDao;

@Repository
public class UserWhitelistDaoImpl implements UserWhitelistDao
{
    private final String tab;
    @Autowired
    private HibernateSuperDao<UserWhitelist> superDao;
    
    public UserWhitelistDaoImpl() {
        this.tab = UserWhitelist.class.getSimpleName();
    }
    
    @Override
    public PageList find(final List<Criterion> criterions, final List<Order> orders, final int start, final int limit) {
        return this.superDao.findPageList(UserWhitelist.class, criterions, orders, start, limit);
    }
    
    @Override
    public boolean add(final UserWhitelist entity) {
        return this.superDao.save(entity);
    }
    
    @Override
    public boolean delete(final int id) {
        final String hql = "delete from " + this.tab + " where id = ?0";
        final Object[] values = { id };
        return this.superDao.delete(hql, values);
    }
}
