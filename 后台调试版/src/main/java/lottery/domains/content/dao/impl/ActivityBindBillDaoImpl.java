package lottery.domains.content.dao.impl;

import java.util.List;
import javautils.jdbc.PageList;
import javautils.jdbc.hibernate.HibernateSuperDao;
import lottery.domains.content.dao.ActivityBindBillDao;
import lottery.domains.content.entity.ActivityBindBill;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ActivityBindBillDaoImpl
  implements ActivityBindBillDao
{
  private final String tab = ActivityBindBill.class.getSimpleName();
  @Autowired
  private HibernateSuperDao<ActivityBindBill> superDao;
  
  public ActivityBindBill getById(int id)
  {
    String hql = "from " + this.tab + " where id = ?0";
    Object[] values = { Integer.valueOf(id) };
    return (ActivityBindBill)this.superDao.unique(hql, values);
  }
  
  public int getWaitTodo()
  {
    String hql = "select count(id) from " + this.tab + " where status = 0";
    Object result = this.superDao.unique(hql);
    return result != null ? ((Number)result).intValue() : 0;
  }
  
  public List<ActivityBindBill> get(String ip, String bindName, String bindCard)
  {
    String hql = "from " + this.tab + " where ip = ?0 or bindName = ?1 or bindCard = ?2";
    Object[] values = { ip, bindName, bindCard };
    return this.superDao.list(hql, values);
  }
  
  public boolean update(ActivityBindBill entity)
  {
    return this.superDao.update(entity);
  }
  
  public PageList find(List<Criterion> criterions, List<Order> orders, int start, int limit)
  {
    return this.superDao.findPageList(ActivityBindBill.class, criterions, orders, start, limit);
  }
  
  public double total(String sTime, String eTime)
  {
    String hql = "select sum(money) from " + this.tab + " where status = 1 and time >= ?0 and time < ?1";
    Object[] values = { sTime, eTime };
    Object result = this.superDao.unique(hql, values);
    return result != null ? ((Number)result).doubleValue() : 0.0D;
  }
}
