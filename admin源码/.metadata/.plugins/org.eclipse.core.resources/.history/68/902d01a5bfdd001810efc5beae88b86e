package lottery.domains.content.dao.impl;

import java.util.Map;
import javautils.StringUtil;
import java.util.HashMap;
import javautils.jdbc.PageList;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.entity.ActivityRedPacketRainBill;
import javautils.jdbc.hibernate.HibernateSuperDao;
import org.springframework.stereotype.Repository;
import lottery.domains.content.dao.ActivityRedPacketRainBillDao;

@Repository
public class ActivityRedPacketRainBillDaoImpl implements ActivityRedPacketRainBillDao
{
    private final String tab;
    @Autowired
    private HibernateSuperDao<ActivityRedPacketRainBill> superDao;
    
    public ActivityRedPacketRainBillDaoImpl() {
        this.tab = ActivityRedPacketRainBill.class.getSimpleName();
    }
    
    @Override
    public PageList find(final List<Criterion> criterions, final List<Order> orders, final int start, final int limit) {
        final String propertyName = "id";
        return this.superDao.findPageList(ActivityRedPacketRainBill.class, propertyName, criterions, orders, start, limit);
    }
    
    @Override
    public double sumAmount(final Integer userId, final String minTime, final String maxTime, final String ip) {
        String hql = "select sum(amount) from " + this.tab + " where 1=1";
        final Map<String, Object> params = new HashMap<String, Object>();
        if (userId != null) {
            hql = String.valueOf(hql) + " and userId = :userId";
            params.put("userId", userId);
        }
        if (StringUtil.isNotNull(minTime)) {
            hql = String.valueOf(hql) + " and time > :minTime";
            params.put("minTime", minTime);
        }
        if (StringUtil.isNotNull(maxTime)) {
            hql = String.valueOf(hql) + " and time < :maxTime";
            params.put("maxTime", maxTime);
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
}
