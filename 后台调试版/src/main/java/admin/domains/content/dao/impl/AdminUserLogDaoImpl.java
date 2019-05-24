package admin.domains.content.dao.impl;

import admin.domains.content.dao.AdminUserLogDao;
import admin.domains.content.entity.AdminUserLog;
import java.util.ArrayList;
import java.util.List;
import javautils.jdbc.PageList;
import javautils.jdbc.hibernate.HibernateSuperDao;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AdminUserLogDaoImpl
  implements AdminUserLogDao
{
  @Autowired
  private HibernateSuperDao<AdminUserLog> superDao;
  
  public boolean save(List<AdminUserLog> list)
  {
    String sql = "insert into `admin_user_log` (`user_id`, `ip`, `address`, `action`, `time`, `user_agent`) values (?, ?, ?, ?, ?, ?)";
    List<Object[]> params = new ArrayList();
    for (AdminUserLog tmpBean : list) {
      try
      {
        Object[] param = { Integer.valueOf(tmpBean.getUserId()), tmpBean.getIp(), tmpBean.getAddress(), tmpBean.getAction(), tmpBean.getTime(), tmpBean.getUserAgent() };
        params.add(param);
      }
      catch (Exception localException) {}
    }
    return this.superDao.doWork(sql, params);
  }
  
  public PageList find(List<Criterion> criterions, List<Order> orders, int start, int limit)
  {
    String propertyName = "id";
    return this.superDao.findPageList(AdminUserLog.class, propertyName, criterions, orders, start, limit);
  }
}
