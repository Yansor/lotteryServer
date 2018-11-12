package lottery.domains.content.dao.impl;

import javautils.jdbc.PageList;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.entity.UserSecurity;
import javautils.jdbc.hibernate.HibernateSuperDao;
import org.springframework.stereotype.Repository;
import lottery.domains.content.dao.UserSecurityDao;

@Repository
public class UserSecurityDaoImpl implements UserSecurityDao
{
    private final String tab;
    @Autowired
    private HibernateSuperDao<UserSecurity> superDao;
    
    public UserSecurityDaoImpl() {
        this.tab = UserSecurity.class.getSimpleName();
    }
    
    @Override
    public UserSecurity getById(final int id) {
        final String hql = "from " + this.tab + " where id = ?0";
        final Object[] values = { id };
        return (UserSecurity)this.superDao.unique(hql, values);
    }
    
    @Override
    public List<UserSecurity> getByUserId(final int userId) {
        final String hql = "from " + this.tab + " where userId = ?0";
        final Object[] values = { userId };
        return this.superDao.list(hql, values);
    }
    
    @Override
    public boolean delete(final int userId) {
        final String hql = "delete from " + this.tab + " where userId = ?0";
        final Object[] values = { userId };
        return this.superDao.delete(hql, values);
    }
    
    @Override
    public PageList find(final List<Criterion> criterions, final List<Order> orders, final int start, final int limit) {
        return this.superDao.findPageList(UserSecurity.class, criterions, orders, start, limit);
    }
    
    @Override
    public boolean updateValue(final int id, final String md5Value) {
        final String hql = "update " + this.tab + " set value = ?1 where id = ?0";
        final Object[] values = { id, md5Value };
        return this.superDao.update(hql, values);
    }
}
