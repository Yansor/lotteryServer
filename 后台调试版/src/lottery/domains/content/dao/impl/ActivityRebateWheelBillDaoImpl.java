package lottery.domains.content.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javautils.StringUtil;
import javautils.jdbc.PageList;
import javautils.jdbc.hibernate.HibernateSuperDao;
import lottery.domains.content.dao.ActivityRebateWheelBillDao;
import lottery.domains.content.entity.ActivityRebateWheelBill;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ActivityRebateWheelBillDaoImpl
  implements ActivityRebateWheelBillDao
{
  private final String tab = ActivityRebateWheelBill.class.getSimpleName();
  @Autowired
  private HibernateSuperDao<ActivityRebateWheelBill> superDao;
  
  public PageList find(List<Criterion> criterions, List<Order> orders, int start, int limit)
  {
    String propertyName = "id";
    return this.superDao.findPageList(ActivityRebateWheelBill.class, propertyName, criterions, orders, start, limit);
  }
  
  public double sumAmount(Integer userId, String minTime, String maxTime, String ip)
  {
    String hql = "select sum(amount) from " + this.tab + " where 1=1";
    
    Map<String, Object> params = new HashMap();
    if (userId != null)
    {
      hql = hql + " and userId = :userId";
      params.put("userId", userId);
    }
    if (StringUtil.isNotNull(minTime))
    {
      hql = hql + " and time >= :minTime";
      params.put("minTime", minTime);
    }
    if (StringUtil.isNotNull(maxTime))
    {
      hql = hql + " and time <= :maxTime";
      params.put("maxTime", maxTime);
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
}
