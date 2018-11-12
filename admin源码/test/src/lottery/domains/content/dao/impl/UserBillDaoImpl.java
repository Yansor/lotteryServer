package lottery.domains.content.dao.impl;

import java.util.Map;
import java.util.HashMap;
import javautils.jdbc.PageList;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.List;
import javautils.array.ArrayUtils;
import lottery.domains.content.entity.HistoryUserBill;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.entity.UserBill;
import javautils.jdbc.hibernate.HibernateSuperDao;
import org.springframework.stereotype.Repository;
import lottery.domains.content.dao.UserBillDao;

@Repository
public class UserBillDaoImpl implements UserBillDao
{
    private final String tab;
    @Autowired
    private HibernateSuperDao<UserBill> superDao;
    @Autowired
    private HibernateSuperDao<HistoryUserBill> dao;
    
    public UserBillDaoImpl() {
        this.tab = UserBill.class.getSimpleName();
    }
    
    @Override
    public boolean add(final UserBill entity) {
        return this.superDao.save(entity);
    }
    
    @Override
    public UserBill getById(final int id) {
        final String hql = "from " + this.tab + " where id = ?0";
        final Object[] values = { id };
        return (UserBill)this.superDao.unique(hql, values);
    }
    
    @Override
    public double getTotalMoney(final String sTime, final String eTime, final int type, final int[] refType) {
        String hql = "select sum(money) from " + this.tab + " where time >= ?0 and time < ?1 and type = ?2";
        if (refType != null && refType.length > 0) {
            hql = String.valueOf(hql) + " and refType in (" + ArrayUtils.transInIds(refType) + ")";
        }
        final Object[] values = { sTime, eTime, type };
        final Object result = this.superDao.unique(hql, values);
        return (result != null) ? ((Number)result).doubleValue() : 0.0;
    }
    
    @Override
    public List<UserBill> getLatest(final int userId, final int type, final int count) {
        final String hql = "from " + this.tab + " where userId = ?0 and type = ?1 order by id desc";
        final Object[] values = { userId, type };
        return this.superDao.list(hql, values, 0, count);
    }
    
    @Override
    public List<UserBill> listByDateAndType(final String date, final int type, final int[] refType) {
        String hql = "from " + this.tab + " where time like ?0 and type = ?1";
        if (refType != null && refType.length > 0) {
            hql = String.valueOf(hql) + " and refType in (" + ArrayUtils.transInIds(refType) + ")";
        }
        final Object[] values = { "%" + date + "%", type };
        return this.superDao.list(hql, values);
    }
    
    @Override
    public PageList find(final List<Criterion> criterions, final List<Order> orders, final int start, final int limit) {
        final String propertyName = "id";
        return this.superDao.findPageList(UserBill.class, propertyName, criterions, orders, start, limit);
    }
    
    @Override
    public PageList findHistory(final List<Criterion> criterions, final List<Order> orders, final int start, final int limit) {
        final String propertyName = "id";
        return this.dao.findPageList(HistoryUserBill.class, propertyName, criterions, orders, start, limit);
    }
    
    @Override
    public PageList findNoDemoUserBill(final String sql, final int start, final int limit) {
        final String hsql = "select b.* from user_bill b, user u where b.user_id = u.id  ";
        final PageList pageList = this.superDao.findPageEntityList(String.valueOf(hsql) + sql, UserBill.class, new HashMap<String, Object>(), start, limit);
        return pageList;
    }
}
