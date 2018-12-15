package lottery.domains.content.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javautils.StringUtil;
import javautils.jdbc.PageList;
import javautils.jdbc.hibernate.HibernateSuperDao;
import lottery.domains.content.dao.ActivityGrabBillDao;
import lottery.domains.content.entity.ActivityGrabBill;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ActivityGrabBillDaoImpl
  implements ActivityGrabBillDao
{
  private final String tab = ActivityGrabBill.class.getSimpleName();
  @Autowired
  private HibernateSuperDao<ActivityGrabBill> superDao;
  
  public boolean add(ActivityGrabBill entity)
  {
    return this.superDao.save(entity);
  }
  
  public ActivityGrabBill get(int userId)
  {
    String hql = "from " + this.tab + " where userId = ?0 and TO_DAYS(time) = TO_DAYS(current_timestamp())";
    Object[] values = { Integer.valueOf(userId) };
    List<ActivityGrabBill> list = this.superDao.list(hql, values);
    if ((list != null) && (list.size() > 0)) {
      return (ActivityGrabBill)list.get(0);
    }
    return null;
  }
  
  public Map<String, Integer> getByPackageGroup()
  {
    String sql = "select  package, count(*) from activity_grab_bill  where TO_DAYS(time) = TO_DAYS(current_timestamp()) GROUP BY package";
    List<Object[]> result = (List<Object[]>)this.superDao.findWithSql(sql);
    Map<String, Integer> data = new HashMap();
    for (Object[] objects : result) {
      data.put(objects[0].toString(), Integer.valueOf(Integer.parseInt(objects[1].toString())));
    }
    return data;
  }
  
  public PageList find(List<Criterion> criterions, List<Order> orders, int start, int limit)
  {
    return this.superDao.findPageList(ActivityGrabBill.class, criterions, orders, start, limit);
  }
  
  public Double getOutAmount(String date)
  {
    String hql = "select SUM(packageMoney) from " + this.tab;
    if (StringUtil.isNotNull(date)) {
      hql = hql + " where TO_DAYS('" + date + "') = TO_DAYS(time)";
    }
    Object result = this.superDao.unique(hql);
    return result != null ? Double.valueOf(Double.parseDouble(result.toString())) : null;
  }
  
  public double getGrabTotal(String sTime, String eTime)
  {
    String hql = "select sum(packageMoney) from " + this.tab + " where time >= ?0 and time < ?1";
    Object[] values = { sTime, eTime };
    Object result = this.superDao.unique(hql, values);
    return result != null ? ((Number)result).doubleValue() : 0.0D;
  }
}
