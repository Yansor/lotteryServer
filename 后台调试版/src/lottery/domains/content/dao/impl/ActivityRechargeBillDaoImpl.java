package lottery.domains.content.dao.impl;

import java.util.List;
import javautils.jdbc.PageList;
import javautils.jdbc.hibernate.HibernateSuperDao;
import lottery.domains.content.dao.ActivityRechargeBillDao;
import lottery.domains.content.entity.ActivityRechargeBill;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ActivityRechargeBillDaoImpl
  implements ActivityRechargeBillDao
{
  private final String tab = ActivityRechargeBill.class.getSimpleName();
  @Autowired
  private HibernateSuperDao<ActivityRechargeBill> superDao;
  
  public ActivityRechargeBill getById(int id)
  {
    String hql = "from " + this.tab + " where id = ?0";
    Object[] values = { Integer.valueOf(id) };
    return (ActivityRechargeBill)this.superDao.unique(hql, values);
  }
  
  public int getWaitTodo()
  {
    String hql = "select count(id) from " + this.tab + " where status = 0";
    Object result = this.superDao.unique(hql);
    return result != null ? ((Number)result).intValue() : 0;
  }
  
  public boolean update(ActivityRechargeBill entity)
  {
    return this.superDao.update(entity);
  }
  
  public boolean add(ActivityRechargeBill entity)
  {
    return this.superDao.save(entity);
  }
  
  public boolean hasDateRecord(int userId, String date)
  {
    String hql = "from " + this.tab + " where userId = ?0 and payTime like ?1";
    Object[] values = { Integer.valueOf(userId), "%" + date + "%" };
    List<ActivityRechargeBill> list = this.superDao.list(hql, values);
    if ((list != null) && (list.size() > 0)) {
      return true;
    }
    return false;
  }
  
  public List<ActivityRechargeBill> get(String ip, String date)
  {
    String hql = "from " + this.tab + " where ip = ?0 and payTime like ?1";
    Object[] values = { ip, "%" + date + "%" };
    return this.superDao.list(hql, values);
  }
  
  public PageList find(List<Criterion> criterions, List<Order> orders, int start, int limit)
  {
    return this.superDao.findPageList(ActivityRechargeBill.class, criterions, orders, start, limit);
  }
  
  public double total(String sTime, String eTime)
  {
    String hql = "select sum(money) from " + this.tab + " where status = 1 and time >= ?0 and time < ?1";
    Object[] values = { sTime, eTime };
    Object result = this.superDao.unique(hql, values);
    return result != null ? ((Number)result).doubleValue() : 0.0D;
  }
}
