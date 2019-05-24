package lottery.domains.content.dao.impl;

import java.util.List;
import javautils.jdbc.PageList;
import javautils.jdbc.hibernate.HibernateSuperDao;
import lottery.domains.content.dao.ActivitySalaryBillDao;
import lottery.domains.content.entity.ActivitySalaryBill;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ActivitySalaryBillDaoImpl
  implements ActivitySalaryBillDao
{
  private final String tab = ActivitySalaryBill.class.getSimpleName();
  @Autowired
  private HibernateSuperDao<ActivitySalaryBill> superDao;
  
  public PageList find(List<Criterion> criterions, List<Order> orders, int start, int limit)
  {
    return this.superDao.findPageList(ActivitySalaryBill.class, criterions, orders, start, limit);
  }
  
  public double total(String sTime, String eTime, int type)
  {
    String hql = "select sum(money) from " + this.tab + " where time >= ?0 and time < ?1 and type = ?2";
    Object[] values = { sTime, eTime, Integer.valueOf(type) };
    Object result = this.superDao.unique(hql, values);
    return result != null ? ((Number)result).doubleValue() : 0.0D;
  }
}
