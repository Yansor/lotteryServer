package lottery.domains.content.dao.impl;

import javautils.date.Moment;
import javautils.array.ArrayUtils;
import javautils.jdbc.PageList;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.entity.UserDividend;
import javautils.jdbc.hibernate.HibernateSuperDao;
import org.springframework.stereotype.Repository;
import lottery.domains.content.dao.UserDividendDao;

@Repository
public class UserDividendDaoImpl implements UserDividendDao
{
    private final String tab;
    @Autowired
    private HibernateSuperDao<UserDividend> superDao;
    
    public UserDividendDaoImpl() {
        this.tab = UserDividend.class.getSimpleName();
    }
    
    @Override
    public PageList search(final List<Criterion> criterions, final List<Order> orders, final int start, final int limit) {
        final String propertyName = "id";
        return this.superDao.findPageList(UserDividend.class, propertyName, criterions, orders, start, limit);
    }
    
    @Override
    public List<UserDividend> findByUserIds(final List<Integer> userIds) {
        final String hql = "from " + this.tab + " where userId in( " + ArrayUtils.transInIds(userIds) + ")";
        return this.superDao.list(hql);
    }
    
    @Override
    public UserDividend getByUserId(final int userId) {
        final String hql = "from " + this.tab + " where userId = ?0";
        final Object[] values = { userId };
        return (UserDividend)this.superDao.unique(hql, values);
    }
    
    @Override
    public UserDividend getById(final int id) {
        final String hql = "from " + this.tab + " where id = ?0";
        final Object[] values = { id };
        return (UserDividend)this.superDao.unique(hql, values);
    }
    
    @Override
    public void add(final UserDividend entity) {
        this.superDao.save(entity);
    }
    
    @Override
    public void updateStatus(final int id, final int status) {
        final String hql = "update " + this.tab + " set status = ?1, agreeTime = ?2 where id = ?0";
        final Object[] values = { id, status, new Moment().toSimpleTime() };
        this.superDao.update(hql, values);
    }
    
    @Override
    public boolean updateSomeFields(final int id, final String scaleLevel, final String lossLevel, final String salesLevel, final int minValidUser, final double minScale, final double maxScale, final String userLevel) {
        final String hql = "update " + this.tab + " set scaleLevel = ?0, lossLevel = ?1, salesLevel = ?2, minValidUser = ?3, minScale = ?4, maxScale = ?5, agreeTime = ?6, status = ?7, userLevel = ?8 where id = ?9";
        final Object[] values = { scaleLevel, lossLevel, salesLevel, minValidUser, minScale, maxScale, "", 2, userLevel, id };
        return this.superDao.update(hql, values);
    }
    
    @Override
    public boolean updateSomeFields(final int id, final String scaleLevel, final String lossLevel, final String salesLevel, final int minValidUser, final int fixed, final double minScale, final double maxScale, final int status) {
        final String agreeTime = new Moment().toSimpleTime();
        final String hql = "update " + this.tab + " set scaleLevel = ?0, lossLevel = ?1, salesLevel = ?2, minValidUser = ?3, fixed = ?4, minScale = ?5, maxScale = ?6, status = ?7, agreeTime = ?8 where id = ?9";
        final Object[] values = { scaleLevel, lossLevel, salesLevel, minValidUser, fixed, minScale, maxScale, status, agreeTime, id };
        return this.superDao.update(hql, values);
    }
    
    @Override
    public void deleteByUser(final int userId) {
        final String hql = "delete from " + this.tab + " where userId = ?0";
        final Object[] values = { userId };
        this.superDao.delete(hql, values);
    }
    
    @Override
    public void deleteByTeam(final int upUserId) {
        final String hql = "delete from " + this.tab + " where userId in(select id from User where id = ?0 or upids like ?1)";
        final Object[] values = { upUserId, "%[" + upUserId + "]%" };
        this.superDao.delete(hql, values);
    }
    
    @Override
    public void deleteLowers(final int upUserId) {
        final String hql = "delete from " + this.tab + " where userId in(select id from User where upids like ?0)";
        final Object[] values = { "%[" + upUserId + "]%" };
        this.superDao.delete(hql, values);
    }
}
