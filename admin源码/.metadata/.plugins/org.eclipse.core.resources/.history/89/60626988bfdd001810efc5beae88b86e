package lottery.domains.content.dao.impl;

import javautils.array.ArrayUtils;
import javautils.jdbc.PageList;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.entity.UserDailySettle;
import javautils.jdbc.hibernate.HibernateSuperDao;
import org.springframework.stereotype.Repository;
import lottery.domains.content.dao.UserDailySettleDao;

@Repository
public class UserDailySettleDaoImpl implements UserDailySettleDao
{
    private final String tab;
    @Autowired
    private HibernateSuperDao<UserDailySettle> superDao;
    
    public UserDailySettleDaoImpl() {
        this.tab = UserDailySettle.class.getSimpleName();
    }
    
    @Override
    public PageList search(final List<Criterion> criterions, final List<Order> orders, final int start, final int limit) {
        final String propertyName = "id";
        return this.superDao.findPageList(UserDailySettle.class, propertyName, criterions, orders, start, limit);
    }
    
    @Override
    public List<UserDailySettle> list(final List<Criterion> criterions, final List<Order> orders) {
        return this.superDao.findByCriteria(UserDailySettle.class, criterions, orders);
    }
    
    @Override
    public List<UserDailySettle> findByUserIds(final List<Integer> userIds) {
        final String hql = "from " + this.tab + " where userId in( " + ArrayUtils.transInIds(userIds) + ")";
        return this.superDao.list(hql);
    }
    
    @Override
    public UserDailySettle getByUserId(final int userId) {
        final String hql = "from " + this.tab + " where userId = ?0";
        final Object[] values = { userId };
        return (UserDailySettle)this.superDao.unique(hql, values);
    }
    
    @Override
    public UserDailySettle getById(final int id) {
        final String hql = "from " + this.tab + " where id = ?0";
        final Object[] values = { id };
        return (UserDailySettle)this.superDao.unique(hql, values);
    }
    
    @Override
    public void add(final UserDailySettle entity) {
        this.superDao.save(entity);
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
    
    @Override
    public boolean updateStatus(final int id, final int status) {
        final String hql = "update " + this.tab + " set status = ?1 where id = ?0";
        final Object[] values = { id, status };
        return this.superDao.update(hql, values);
    }
    
    @Override
    public boolean updateSomeFields(final int id, final String scaleLevel, final String lossLevel, final String salesLevel, final int minValidUser, final String userLevel) {
        final String hql = "update " + this.tab + " set scaleLevel = ?0,lossLevel=?1,salesLevel=?2, minValidUser = ?3, userLevel = ?4, minScale=?5,maxScale=?6 where id = ?7";
        final String[] scaleArr = scaleLevel.split(",");
        final Object[] values = { scaleLevel, lossLevel, salesLevel, minValidUser, userLevel, Double.valueOf(scaleArr[0]), Double.valueOf(scaleArr[scaleArr.length - 1]), id };
        return this.superDao.update(hql, values);
    }
    
    @Override
    public boolean updateSomeFields(final int id, final double scale, final int minValidUser, final int status, final int fixed, final double minScale, final double maxScale) {
        final String hql = "update " + this.tab + " set scale = ?0, minValidUser = ?1, status = ?2, fixed = ?3, minScale = ?4, maxScale = ?5 where id = ?6";
        final Object[] values = { scale, minValidUser, status, fixed, minScale, maxScale, id };
        return this.superDao.update(hql, values);
    }
    
    @Override
    public boolean updateTotalAmount(final int userId, final double amount) {
        final String hql = "update " + this.tab + " set totalAmount = totalAmount + ?1 where userId = ?0";
        final Object[] values = { userId, amount };
        return this.superDao.update(hql, values);
    }
}
