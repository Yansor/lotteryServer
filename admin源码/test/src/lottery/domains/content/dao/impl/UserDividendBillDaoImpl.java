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
import lottery.domains.content.entity.UserDividendBill;
import javautils.jdbc.hibernate.HibernateSuperDao;
import org.springframework.stereotype.Repository;
import lottery.domains.content.dao.UserDividendBillDao;

@Repository
public class UserDividendBillDaoImpl implements UserDividendBillDao
{
    private final String tab;
    @Autowired
    private HibernateSuperDao<UserDividendBill> superDao;
    
    public UserDividendBillDaoImpl() {
        this.tab = UserDividendBill.class.getSimpleName();
    }
    
    @Override
    public PageList search(final List<Criterion> criterions, final List<Order> orders, final int start, final int limit) {
        final String propertyName = "id";
        return this.superDao.findPageList(UserDividendBill.class, propertyName, criterions, orders, start, limit);
    }
    
    @Override
    public double[] sumUserAmount(final List<Integer> userIds, final String sTime, final String eTime, final Double minUserAmount, final Double maxUserAmount) {
        String hql = "select sum(case when issueType = 1 and status in (1,2,3,6) and totalLoss < 0 then totalLoss else 0 end), sum(case when issueType = 1 and status in (1,2,3,6) then calAmount else 0 end),sum(case when issueType = 2 and status in (1,2,3,6,7) then calAmount else 0 end) from " + this.tab + " where 1=1";
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
        final Object result = this.superDao.uniqueWithParams(hql, params);
        if (result == null) {
            return new double[] { 0.0, 0.0 };
        }
        final Object[] results = (Object[])result;
        int index = 0;
        final double totalLoss = (double)((results[index] == null) ? 0.0 : results[index]);
        ++index;
        final double platformTotalUserAmount = (double)((results[index] == null) ? 0.0 : results[index]);
        ++index;
        final double upperTotalUserAmount = (double)((results[index] == null) ? 0.0 : results[index]);
        ++index;
        return new double[] { totalLoss, platformTotalUserAmount, upperTotalUserAmount };
    }
    
    @Override
    public List<UserDividendBill> findByCriteria(final List<Criterion> criterions, final List<Order> orders) {
        return this.superDao.findByCriteria(UserDividendBill.class, criterions, orders);
    }
    
    @Override
    public boolean updateAllExpire() {
        final String hql = "update " + this.tab + " set status = 8 where status in (3, 6, 7)";
        final Object[] values = new Object[0];
        return this.superDao.update(hql, values);
    }
    
    @Override
    public UserDividendBill getById(final int id) {
        final String hql = "from " + this.tab + " where id = ?0";
        final Object[] values = { id };
        return (UserDividendBill)this.superDao.unique(hql, values);
    }
    
    @Override
    public UserDividendBill getByUserId(final int userId, final String indicateStartDate, final String indicateEndDate) {
        final String hql = "from " + this.tab + " where userId = ?0 and indicateStartDate = ?1 and indicateEndDate = ?2";
        final Object[] values = { userId, indicateStartDate, indicateEndDate };
        return (UserDividendBill)this.superDao.unique(hql, values);
    }
    
    @Override
    public boolean add(final UserDividendBill dividendBill) {
        return this.superDao.save(dividendBill);
    }
    
    @Override
    public boolean addAvailableMoney(final int id, final double money) {
        final String hql = "update " + this.tab + " set availableAmount = availableAmount+?1 where id = ?0";
        final Object[] values = { id, money };
        return this.superDao.update(hql, values);
    }
    
    @Override
    public boolean addUserAmount(final int id, final double money) {
        final String hql = "update " + this.tab + " set userAmount = userAmount+?1 where id = ?0";
        final Object[] values = { id, money };
        return this.superDao.update(hql, values);
    }
    
    @Override
    public boolean setAvailableMoney(final int id, final double money) {
        final String hql = "update " + this.tab + " set availableAmount = ?1 where id = ?0";
        final Object[] values = { id, money };
        return this.superDao.update(hql, values);
    }
    
    @Override
    public boolean addTotalReceived(final int id, final double money) {
        final String hql = "update " + this.tab + " set totalReceived = totalReceived+?1 where id = ?0";
        final Object[] values = { id, money };
        return this.superDao.update(hql, values);
    }
    
    @Override
    public boolean addLowerPaidAmount(final int id, final double money) {
        final String hql = "update " + this.tab + " set lowerPaidAmount = lowerPaidAmount+?1 where id = ?0";
        final Object[] values = { id, money };
        return this.superDao.update(hql, values);
    }
    
    @Override
    public boolean update(final UserDividendBill dividendBill) {
        return this.superDao.update(dividendBill);
    }
    
    @Override
    public boolean update(final int id, final int status, final String remarks) {
        final String hql = "update " + this.tab + " set status = ?1, remarks = ?2 where id = ?0";
        final Object[] values = { id, status, remarks };
        return this.superDao.update(hql, values);
    }
    
    @Override
    public boolean update(final int id, final int status, final double availableAmount, final String remarks) {
        final String hql = "update " + this.tab + " set status = ?1, availableAmount = ?2, remarks = ?3 where id = ?0";
        final Object[] values = { id, status, availableAmount, remarks };
        return this.superDao.update(hql, values);
    }
    
    @Override
    public boolean del(final int id) {
        final String hql = "delete from " + this.tab + " where id = ?0";
        final Object[] values = { id };
        return this.superDao.delete(hql, values);
    }
    
    @Override
    public boolean updateStatus(final int id, final int status) {
        final String hql = "update " + this.tab + " set status = ?1 where id = ?0";
        final Object[] values = { id, status };
        return this.superDao.update(hql, values);
    }
    
    @Override
    public boolean updateStatus(final int id, final int status, final String remarks) {
        final String hql = "update " + this.tab + " set status = ?1, remarks = ?2 where id = ?0";
        final Object[] values = { id, status, remarks };
        return this.superDao.update(hql, values);
    }
}
