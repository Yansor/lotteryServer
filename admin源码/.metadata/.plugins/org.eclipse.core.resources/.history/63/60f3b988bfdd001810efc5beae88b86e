package lottery.domains.content.dao.impl;

import javautils.jdbc.PageList;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.entity.UserBetsPlan;
import javautils.jdbc.hibernate.HibernateSuperDao;
import org.springframework.stereotype.Repository;
import lottery.domains.content.dao.UserBetsPlanDao;

@Repository
public class UserBetsPlanDaoImpl implements UserBetsPlanDao
{
    private final String tab;
    @Autowired
    private HibernateSuperDao<UserBetsPlan> superDao;
    
    public UserBetsPlanDaoImpl() {
        this.tab = UserBetsPlan.class.getSimpleName();
    }
    
    @Override
    public boolean add(final UserBetsPlan entity) {
        return this.superDao.save(entity);
    }
    
    @Override
    public UserBetsPlan get(final int id) {
        final String hql = "from " + this.tab + " where id = ?0";
        final Object[] values = { id };
        return (UserBetsPlan)this.superDao.unique(hql, values);
    }
    
    @Override
    public UserBetsPlan get(final int id, final int userId) {
        final String hql = "from " + this.tab + " where id = ?0 and userId = ?1";
        final Object[] values = { id, userId };
        return (UserBetsPlan)this.superDao.unique(hql, values);
    }
    
    @Override
    public boolean hasRecord(final int userId, final int lotteryId, final String expect) {
        final String hql = "from " + this.tab + " where userId = ?0 and lotteryId = ?1 and expect = ?2";
        final Object[] values = { userId, lotteryId, expect };
        final List<UserBetsPlan> list = this.superDao.list(hql, values);
        return list != null && list.size() > 0;
    }
    
    @Override
    public boolean updateCount(final int id, final int count) {
        final String hql = "update " + this.tab + " set count = count + ?1 where id = ?0";
        final Object[] values = { id, count };
        return this.superDao.update(hql, values);
    }
    
    @Override
    public boolean updateStatus(final int id, final int status) {
        final String hql = "update " + this.tab + " set status = ?1 where id = ?0 and status = 0";
        final Object[] values = { id, status };
        return this.superDao.update(hql, values);
    }
    
    @Override
    public boolean updateStatus(final int id, final int status, final double prizeMoney) {
        final String hql = "update " + this.tab + " set status = ?1, prizeMoney = ?2 where id = ?0 and status = 0";
        final Object[] values = { id, status, prizeMoney };
        return this.superDao.update(hql, values);
    }
    
    @Override
    public List<UserBetsPlan> listUnsettled() {
        final String hql = "from " + this.tab + " where status = 0";
        return this.superDao.list(hql);
    }
    
    @Override
    public List<UserBetsPlan> list(final int lotteryId, final String expect) {
        final String hql = "from " + this.tab + " where lotteryId = ?0 and expect = ?1";
        final Object[] values = { lotteryId, expect };
        return this.superDao.list(hql, values);
    }
    
    @Override
    public PageList search(final List<Criterion> criterions, final List<Order> orders, final int start, final int limit) {
        final String propertyName = "id";
        return this.superDao.findPageList(UserBetsPlan.class, propertyName, criterions, orders, start, limit);
    }
}
