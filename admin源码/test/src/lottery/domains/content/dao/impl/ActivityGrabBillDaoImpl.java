package lottery.domains.content.dao.impl;

import javautils.StringUtil;
import javautils.jdbc.PageList;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.Iterator;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.entity.ActivityGrabBill;
import javautils.jdbc.hibernate.HibernateSuperDao;
import org.springframework.stereotype.Repository;
import lottery.domains.content.dao.ActivityGrabBillDao;

@Repository
public class ActivityGrabBillDaoImpl implements ActivityGrabBillDao
{
    private final String tab;
    @Autowired
    private HibernateSuperDao<ActivityGrabBill> superDao;
    
    public ActivityGrabBillDaoImpl() {
        this.tab = ActivityGrabBill.class.getSimpleName();
    }
    
    @Override
    public boolean add(final ActivityGrabBill entity) {
        return this.superDao.save(entity);
    }
    
    @Override
    public ActivityGrabBill get(final int userId) {
        final String hql = "from " + this.tab + " where userId = ?0 and TO_DAYS(time) = TO_DAYS(current_timestamp())";
        final Object[] values = { userId };
        final List<ActivityGrabBill> list = this.superDao.list(hql, values);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }
    
    @Override
    public Map<String, Integer> getByPackageGroup() {
        final String sql = "select  package, count(*) from activity_grab_bill  where TO_DAYS(time) = TO_DAYS(current_timestamp()) GROUP BY package";
        final List<Object[]> result = (List<Object[]>)this.superDao.findWithSql(sql);
        final Map<String, Integer> data = new HashMap<String, Integer>();
        for (final Object[] objects : result) {
            data.put(objects[0].toString(), Integer.parseInt(objects[1].toString()));
        }
        return data;
    }
    
    @Override
    public PageList find(final List<Criterion> criterions, final List<Order> orders, final int start, final int limit) {
        return this.superDao.findPageList(ActivityGrabBill.class, criterions, orders, start, limit);
    }
    
    @Override
    public Double getOutAmount(final String date) {
        String hql = "select SUM(packageMoney) from " + this.tab;
        if (StringUtil.isNotNull(date)) {
            hql = String.valueOf(hql) + " where TO_DAYS('" + date + "') = TO_DAYS(time)";
        }
        final Object result = this.superDao.unique(hql);
        return (result != null) ? Double.valueOf(Double.parseDouble(result.toString())) : null;
    }
    
    @Override
    public double getGrabTotal(final String sTime, final String eTime) {
        final String hql = "select sum(packageMoney) from " + this.tab + " where time >= ?0 and time < ?1";
        final Object[] values = { sTime, eTime };
        final Object result = this.superDao.unique(hql, values);
        return (result != null) ? ((Number)result).doubleValue() : 0.0;
    }
}
