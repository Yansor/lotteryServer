package admin.domains.content.dao.impl;

import admin.domains.content.dao.AdminUserActionLogDao;
import admin.domains.content.entity.AdminUserActionLog;
import java.util.ArrayList;
import java.util.List;
import javautils.jdbc.PageList;
import javautils.jdbc.hibernate.HibernateSuperDao;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AdminUserActionLogDaoImpl
  implements AdminUserActionLogDao
{
  @Autowired
  private HibernateSuperDao<AdminUserActionLog> superDao;
  
  public boolean save(AdminUserActionLog entity)
  {
    return this.superDao.save(entity);
  }
  
  public boolean save(List<AdminUserActionLog> list)
  {
    String sql = "insert into `admin_user_action_log`(`user_id`, `action_id`, `data`, `millisecond`, `error`, `message`, `time`, `user_agent`) values(?,?,?,?,?,?,?,?)";
    List<Object[]> params = new ArrayList();
    for (AdminUserActionLog tmp : list) {
      try
      {
        Object[] param = { Integer.valueOf(tmp.getUserId()), Integer.valueOf(tmp.getActionId()), tmp.getData(), Long.valueOf(tmp.getMillisecond()), Integer.valueOf(tmp.getError()), tmp.getMessage(), tmp.getTime(), tmp.getUserAgent() };
        params.add(param);
      }
      catch (Exception localException) {}
    }
    return this.superDao.doWork(sql, params);
  }
  
  public PageList find(List<Criterion> criterions, List<Order> orders, int start, int limit)
  {
    String propertyName = "id";
    return this.superDao.findPageList(AdminUserActionLog.class, propertyName, criterions, orders, start, limit);
  }
}
