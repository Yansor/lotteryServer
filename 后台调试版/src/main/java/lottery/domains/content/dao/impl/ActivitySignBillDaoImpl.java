package lottery.domains.content.dao.impl;

import java.util.List;
import javautils.jdbc.PageList;
import javautils.jdbc.hibernate.HibernateSuperDao;
import lottery.domains.content.dao.ActivitySignBillDao;
import lottery.domains.content.entity.ActivitySignBill;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ActivitySignBillDaoImpl
  implements ActivitySignBillDao
{
  private final String tab = ActivitySignBill.class.getSimpleName();
  @Autowired
  private HibernateSuperDao<ActivitySignBill> superDao;
  
  public PageList find(List<Criterion> criterions, List<Order> orders, int start, int limit)
  {
    return this.superDao.findPageList(ActivitySignBill.class, criterions, orders, start, limit);
  }
  
  public double total(String sTime, String eTime)
  {
    String hql = "select sum(money) from " + this.tab + " where time >= ?0 and time < ?1";
    Object[] values = { sTime, eTime };
    Object result = this.superDao.unique(hql, values);
    return result != null ? ((Number)result).doubleValue() : 0.0D;
  }
}
