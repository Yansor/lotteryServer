package lottery.domains.content.dao.impl;

import java.util.Map;
import org.apache.commons.lang.StringUtils;
import java.util.Collection;
import org.apache.commons.collections.CollectionUtils;
import java.util.HashMap;
import javautils.jdbc.PageList;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.entity.UserGameDividendBill;
import javautils.jdbc.hibernate.HibernateSuperDao;
import org.springframework.stereotype.Repository;
import lottery.domains.content.dao.UserGameDividendBillDao;

@Repository
public class UserGameDividendBillDaoImpl implements UserGameDividendBillDao
{
    private final String tab;
    @Autowired
    private HibernateSuperDao<UserGameDividendBill> superDao;
    
    public UserGameDividendBillDaoImpl() {
        this.tab = UserGameDividendBill.class.getSimpleName();
    }
    
    @Override
    public PageList search(final List<Criterion> criterions, final List<Order> orders, final int start, final int limit) {
        return this.superDao.findPageList(UserGameDividendBill.class, "id", criterions, orders, start, limit);
    }
    
    @Override
    public double sumUserAmount(final List<Integer> userIds, final String sTime, final String eTime, final Double minUserAmount, final Double maxUserAmount, final Integer status) {
        String hql = "select sum(userAmount) from " + this.tab + " where 1=1";
        final Map<String, Object> params = new HashMap<String, Object>();
        if (CollectionUtils.isNotEmpty((Collection)userIds)) {
            hql = String.valueOf(hql) + " and userId in :userId";
            params.put("userId", userIds);
        }
        if (StringUtils.isNotEmpty(sTime)) {
            hql = String.valueOf(hql) + " and indicateStartDate >= :sTime";
            params.put("sTime", sTime);
        }
        if (StringUtils.isNotEmpty(eTime)) {
            hql = String.valueOf(hql) + " and indicateEndDate <= :eTime";
            params.put("eTime", eTime);
        }
        if (minUserAmount != null) {
            hql = String.valueOf(hql) + " and userAmount >= :minUserAmount";
            params.put("minUserAmount", minUserAmount);
        }
        if (maxUserAmount != null) {
            hql = String.valueOf(hql) + " and userAmount <= :maxUserAmount";
            params.put("maxUserAmount", maxUserAmount);
        }
        if (status != null) {
            hql = String.valueOf(hql) + " and status = :status";
            params.put("status", status);
        }
        final Object result = this.superDao.uniqueWithParams(hql, params);
        if (result == null) {
            return 0.0;
        }
        return (double)result;
    }
    
    @Override
    public List<UserGameDividendBill> findByCriteria(final List<Criterion> criterions, final List<Order> orders) {
        return this.superDao.findByCriteria(UserGameDividendBill.class, criterions, orders);
    }
    
    @Override
    public UserGameDividendBill getById(final int id) {
        final String hql = "from " + this.tab + " where id = ?0";
        final Object[] values = { id };
        return (UserGameDividendBill)this.superDao.unique(hql, values);
    }
    
    @Override
    public boolean add(final UserGameDividendBill dividendBill) {
        return this.superDao.save(dividendBill);
    }
    
    @Override
    public boolean update(final int id, final int status, final double userAmount, final String remarks) {
        final String hql = "update " + this.tab + " set status = ?1, userAmount = ?2, remarks = ?3 where id = ?0";
        final Object[] values = { id, status, userAmount, remarks };
        return this.superDao.update(hql, values);
    }
    
    @Override
    public boolean del(final int id) {
        final String hql = "delete from " + this.tab + " where id = ?0";
        final Object[] values = { id };
        return this.superDao.delete(hql, values);
    }
}
