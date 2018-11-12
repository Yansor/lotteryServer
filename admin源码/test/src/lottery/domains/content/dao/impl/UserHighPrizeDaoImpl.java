package lottery.domains.content.dao.impl;

import java.util.Map;
import java.util.HashMap;
import javautils.jdbc.PageList;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.List;
import lottery.domains.content.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.entity.UserHighPrize;
import javautils.jdbc.hibernate.HibernateSuperDao;
import org.springframework.stereotype.Repository;
import lottery.domains.content.dao.UserHighPrizeDao;

@Repository
public class UserHighPrizeDaoImpl implements UserHighPrizeDao
{
    private final String tab;
    private final String utab;
    @Autowired
    private HibernateSuperDao<UserHighPrize> superDao;
    
    public UserHighPrizeDaoImpl() {
        this.tab = UserHighPrize.class.getSimpleName();
        this.utab = User.class.getSimpleName();
    }
    
    @Override
    public PageList find(final List<Criterion> criterions, final List<Order> orders, final int start, final int limit) {
        final String propertyName = "id";
        return this.superDao.findPageList(UserHighPrize.class, propertyName, criterions, orders, start, limit);
    }
    
    @Override
    public UserHighPrize getById(final int id) {
        final String hql = "from " + this.tab + " where id = ?0";
        final Object[] values = { id };
        return (UserHighPrize)this.superDao.unique(hql, values);
    }
    
    @Override
    public boolean add(final UserHighPrize entity) {
        return this.superDao.save(entity);
    }
    
    @Override
    public boolean updateStatus(final int id, final int status) {
        final String hql = "update " + this.tab + " set status = ?1 where id = ?0";
        final Object[] values = { id, status };
        return this.superDao.update(hql, values);
    }
    
    @Override
    public boolean updateStatusAndConfirmUsername(final int id, final int status, final String confirmUsername) {
        final String hql = "update " + this.tab + " set status = ?1,confirmUsername=?2 where id = ?0";
        final Object[] values = { id, status, confirmUsername };
        return this.superDao.update(hql, values);
    }
    
    @Override
    public int getUnProcessCount() {
        final String hql = "select count(b.id) from " + this.tab + " b  ," + this.utab + " u  where u.id = b.userId  and u.upid !=0 and b.status = 0";
        final Object result = this.superDao.unique(hql);
        return (result != null) ? ((Number)result).intValue() : 0;
    }
    
    @Override
    public PageList find(final String sql, final int start, final int limit) {
        final String hsql = "select b.* from user_high_prize b, user u where b.user_id = u.id  ";
        final PageList pageList = this.superDao.findPageEntityList(String.valueOf(hsql) + sql, UserHighPrize.class, new HashMap<String, Object>(), start, limit);
        return pageList;
    }
}
