package lottery.domains.content.dao.impl;

import java.util.Map;
import javautils.StringUtil;
import java.util.HashMap;
import javautils.jdbc.PageList;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.entity.ActivityFirstRechargeBill;
import javautils.jdbc.hibernate.HibernateSuperDao;
import org.springframework.stereotype.Repository;
import lottery.domains.content.dao.ActivityFirstRechargeBillDao;

@Repository
public class ActivityFirstRechargeBillDaoImpl implements ActivityFirstRechargeBillDao
{
    private final String tab;
    @Autowired
    private HibernateSuperDao<ActivityFirstRechargeBill> superDao;
    
    public ActivityFirstRechargeBillDaoImpl() {
        this.tab = ActivityFirstRechargeBill.class.getSimpleName();
    }
    
    @Override
    public PageList find(final List<Criterion> criterions, final List<Order> orders, final int start, final int limit) {
        final String propertyName = "id";
        return this.superDao.findPageList(ActivityFirstRechargeBill.class, propertyName, criterions, orders, start, limit);
    }
    
    @Override
    public double sumAmount(final Integer userId, final String sDate, final String eDate, final String ip) {
        String hql = "select sum(amount) from " + this.tab + " where 1=1";
        final Map<String, Object> params = new HashMap<String, Object>();
        if (userId != null) {
            hql = String.valueOf(hql) + " and userId = :userId";
            params.put("userId", userId);
        }
        if (StringUtil.isNotNull(sDate)) {
            hql = String.valueOf(hql) + " and date >= :sDate";
            params.put("sDate", sDate);
        }
        if (StringUtil.isNotNull(eDate)) {
            hql = String.valueOf(hql) + " and date < :eDate";
            params.put("eDate", eDate);
        }
        if (StringUtil.isNotNull(ip)) {
            hql = String.valueOf(hql) + " and ip = :ip";
            params.put("ip", ip);
        }
        final Object result = this.superDao.uniqueWithParams(hql, params);
        if (result == null) {
            return 0.0;
        }
        return (double)result;
    }
    
    @Override
    public ActivityFirstRechargeBill getByDateAndIp(final String date, final String ip) {
        final String hql = "from " + this.tab + " where date = ?0 and ip = ?1";
        final Object[] values = { date, ip };
        return (ActivityFirstRechargeBill)this.superDao.unique(hql, values);
    }
    
    @Override
    public ActivityFirstRechargeBill getByUserIdAndDate(final int userId, final String date) {
        final String hql = "from " + this.tab + " where userId = ?0 and date = ?1";
        final Object[] values = { userId, date };
        return (ActivityFirstRechargeBill)this.superDao.unique(hql, values);
    }
    
    @Override
    public boolean add(final ActivityFirstRechargeBill bill) {
        return this.superDao.save(bill);
    }
}
