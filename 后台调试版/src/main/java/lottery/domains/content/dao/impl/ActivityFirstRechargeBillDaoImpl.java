package lottery.domains.content.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javautils.StringUtil;
import javautils.jdbc.PageList;
import javautils.jdbc.hibernate.HibernateSuperDao;
import lottery.domains.content.dao.ActivityFirstRechargeBillDao;
import lottery.domains.content.entity.ActivityFirstRechargeBill;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ActivityFirstRechargeBillDaoImpl
  implements ActivityFirstRechargeBillDao
{
  private final String tab = ActivityFirstRechargeBill.class.getSimpleName();
  @Autowired
  private HibernateSuperDao<ActivityFirstRechargeBill> superDao;
  
  public PageList find(List<Criterion> criterions, List<Order> orders, int start, int limit)
  {
    String propertyName = "id";
    return this.superDao.findPageList(ActivityFirstRechargeBill.class, propertyName, criterions, orders, start, limit);
  }
  
  public double sumAmount(Integer userId, String sDate, String eDate, String ip)
  {
    String hql = "select sum(amount) from " + this.tab + " where 1=1";
    
    Map<String, Object> params = new HashMap();
    if (userId != null)
    {
      hql = hql + " and userId = :userId";
      params.put("userId", userId);
    }
    if (StringUtil.isNotNull(sDate))
    {
      hql = hql + " and date >= :sDate";
      params.put("sDate", sDate);
    }
    if (StringUtil.isNotNull(eDate))
    {
      hql = hql + " and date < :eDate";
      params.put("eDate", eDate);
    }
    if (StringUtil.isNotNull(ip))
    {
      hql = hql + " and ip = :ip";
      params.put("ip", ip);
    }
    Object result = this.superDao.uniqueWithParams(hql, params);
    if (result == null) {
      return 0.0D;
    }
    return ((Double)result).doubleValue();
  }
  
  public ActivityFirstRechargeBill getByDateAndIp(String date, String ip)
  {
    String hql = "from " + this.tab + " where date = ?0 and ip = ?1";
    Object[] values = { date, ip };
    return (ActivityFirstRechargeBill)this.superDao.unique(hql, values);
  }
  
  public ActivityFirstRechargeBill getByUserIdAndDate(int userId, String date)
  {
    String hql = "from " + this.tab + " where userId = ?0 and date = ?1";
    Object[] values = { Integer.valueOf(userId), date };
    return (ActivityFirstRechargeBill)this.superDao.unique(hql, values);
  }
  
  public boolean add(ActivityFirstRechargeBill bill)
  {
    return this.superDao.save(bill);
  }
}
