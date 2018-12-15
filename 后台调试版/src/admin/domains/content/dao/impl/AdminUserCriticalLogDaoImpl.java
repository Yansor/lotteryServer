package admin.domains.content.dao.impl;

import admin.domains.content.dao.AdminUserCriticalLogDao;
import admin.domains.content.entity.AdminUserCriticalLog;
import java.util.ArrayList;
import java.util.List;
import javautils.jdbc.PageList;
import javautils.jdbc.hibernate.HibernateSuperDao;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AdminUserCriticalLogDaoImpl
  implements AdminUserCriticalLogDao
{
  @Autowired
  private HibernateSuperDao<AdminUserCriticalLog> superDao;
  private final String tab = AdminUserCriticalLog.class.getSimpleName();
  
  public boolean save(List<AdminUserCriticalLog> list)
  {
    String sql = "insert into `admin_user_critical_log` (`admin_user_id`,`user_id`,`action_id`, `ip`, `address`, `action`, `time`, `user_agent`) values (?,?, ?, ?, ?, ?, ?, ?)";
    List<Object[]> params = new ArrayList();
    for (AdminUserCriticalLog tmpBean : list) {
      try
      {
        Object[] param = { Integer.valueOf(tmpBean.getAdminUserId()), Integer.valueOf(tmpBean.getUserId()), Integer.valueOf(tmpBean.getActionId()), tmpBean.getIp(), tmpBean.getAddress(), tmpBean.getAction(), tmpBean.getTime(), tmpBean.getUserAgent() };
        params.add(param);
      }
      catch (Exception localException) {}
    }
    return this.superDao.doWork(sql, params);
  }
  
  public PageList find(List<Criterion> criterions, List<Order> orders, int start, int limit)
  {
    String propertyName = "id";
    return this.superDao.findPageList(AdminUserCriticalLog.class, propertyName, criterions, orders, start, limit);
  }
  
  public List<AdminUserCriticalLog> findAction()
  {
    String hql = "from " + this.tab + " where 1 = 1 group by actionId";
    return this.superDao.list(hql);
  }
}
