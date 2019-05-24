package lottery.domains.content.dao.impl;

import java.util.List;
import javautils.jdbc.PageList;
import javautils.jdbc.hibernate.HibernateSuperDao;
import lottery.domains.content.dao.ActivityRewardBillDao;
import lottery.domains.content.entity.ActivityRewardBill;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ActivityRewardBillDaoImpl
  implements ActivityRewardBillDao
{
  private final String tab = ActivityRewardBill.class.getSimpleName();
  @Autowired
  private HibernateSuperDao<ActivityRewardBill> superDao;
  
  public boolean add(ActivityRewardBill entity)
  {
    return this.superDao.save(entity);
  }
  
  public ActivityRewardBill getById(int id)
  {
    String hql = "from " + this.tab + " where id = ?0";
    Object[] values = { Integer.valueOf(id) };
    return (ActivityRewardBill)this.superDao.unique(hql, values);
  }
  
  public List<ActivityRewardBill> getUntreated(String date)
  {
    String hql = "from " + this.tab + " where date = ?0 and status = 0";
    Object[] values = { date };
    return this.superDao.list(hql, values);
  }
  
  public List<ActivityRewardBill> getLatest(int toUser, int status, int count)
  {
    String hql = "from " + this.tab + " where toUser = ?0 and status = 1";
    Object[] values = { Integer.valueOf(toUser) };
    return this.superDao.list(hql, values, 0, count);
  }
  
  public boolean hasRecord(int toUser, int fromUser, int type, String date)
  {
    String hql = "from " + this.tab + " where toUser = ?0 and fromUser = ?1 and type = ?2 and date = ?3";
    Object[] values = { Integer.valueOf(toUser), Integer.valueOf(fromUser), Integer.valueOf(type), date };
    List<ActivityRewardBill> list = this.superDao.list(hql, values);
    if ((list != null) && (list.size() > 0)) {
      return true;
    }
    return false;
  }
  
  public boolean update(ActivityRewardBill entity)
  {
    return this.superDao.update(entity);
  }
  
  public boolean delete(int id)
  {
    String hql = "delete from " + this.tab + " where id = ?0";
    Object[] values = { Integer.valueOf(id) };
    return this.superDao.delete(hql, values);
  }
  
  public PageList find(List<Criterion> criterions, List<Order> orders, int start, int limit)
  {
    return this.superDao.findPageList(ActivityRewardBill.class, criterions, orders, start, limit);
  }
  
  public double total(String sTime, String eTime, int type)
  {
    String hql = "select sum(money) from " + this.tab + " where status = 1 and time >= ?0 and time < ?1 and type = ?2";
    Object[] values = { sTime, eTime, Integer.valueOf(type) };
    Object result = this.superDao.unique(hql, values);
    return result != null ? ((Number)result).doubleValue() : 0.0D;
  }
}
