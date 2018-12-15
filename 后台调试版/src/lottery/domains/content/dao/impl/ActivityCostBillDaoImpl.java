package lottery.domains.content.dao.impl;

import java.util.List;
import javautils.jdbc.PageList;
import javautils.jdbc.hibernate.HibernateSuperDao;
import lottery.domains.content.dao.ActivityCostBillDao;
import lottery.domains.content.entity.ActivityCostBill;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ActivityCostBillDaoImpl
  implements ActivityCostBillDao
{
  private final String tab = ActivityCostBill.class.getSimpleName();
  @Autowired
  private HibernateSuperDao<ActivityCostBill> superDao;
  
  public PageList find(List<Criterion> criterions, List<Order> orders, int start, int limit)
  {
    return this.superDao.findPageList(ActivityCostBill.class, criterions, orders, start, limit);
  }
  
  public double total(String sTime, String eTime)
  {
    String hql = "select sum(money) from " + this.tab + " where time >= ?0 and time < ?1";
    Object[] values = { sTime, eTime };
    Object result = this.superDao.unique(hql, values);
    return result != null ? ((Number)result).doubleValue() : 0.0D;
  }
}
