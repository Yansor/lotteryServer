package lottery.domains.content.dao.impl;

import javautils.jdbc.PageList;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.entity.UserBlacklist;
import javautils.jdbc.hibernate.HibernateSuperDao;
import org.springframework.stereotype.Repository;
import lottery.domains.content.dao.UserBlacklistDao;

@Repository
public class UserBlacklistDaoImpl implements UserBlacklistDao
{
    private final String tab;
    @Autowired
    private HibernateSuperDao<UserBlacklist> superDao;
    
    public UserBlacklistDaoImpl() {
        this.tab = UserBlacklist.class.getSimpleName();
    }
    
    @Override
    public PageList find(final List<Criterion> criterions, final List<Order> orders, final int start, final int limit) {
        return this.superDao.findPageList(UserBlacklist.class, criterions, orders, start, limit);
    }
    
    @Override
    public boolean add(final UserBlacklist entity) {
        return this.superDao.save(entity);
    }
    
    @Override
    public boolean delete(final int id) {
        final String hql = "delete from " + this.tab + " where id = ?0";
        final Object[] values = { id };
        return this.superDao.delete(hql, values);
    }
    
    @Override
    public int getByIp(final String ip) {
        final String hql = "select count(id) from " + this.tab + " where ip = ?0";
        final Object[] values = { ip };
        final Object result = this.superDao.unique(hql, values);
        return (result == null) ? 0 : Integer.parseInt(result.toString());
    }
}
