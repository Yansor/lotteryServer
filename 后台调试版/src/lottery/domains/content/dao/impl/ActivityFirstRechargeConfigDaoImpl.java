package lottery.domains.content.dao.impl;

import java.util.List;
import javautils.jdbc.hibernate.HibernateSuperDao;
import lottery.domains.content.dao.ActivityFirstRechargeConfigDao;
import lottery.domains.content.entity.ActivityFirstRechargeConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ActivityFirstRechargeConfigDaoImpl
  implements ActivityFirstRechargeConfigDao
{
  private final String tab = ActivityFirstRechargeConfig.class.getSimpleName();
  @Autowired
  private HibernateSuperDao<ActivityFirstRechargeConfig> superDao;
  
  public ActivityFirstRechargeConfig getConfig()
  {
    String hql = "from " + this.tab;
    return (ActivityFirstRechargeConfig)this.superDao.list(hql, 0, 1).get(0);
  }
  
  public boolean updateConfig(int id, String rules)
  {
    String hql = "update " + this.tab + " set rules = ?0 where id = ?1";
    Object[] values = { rules, Integer.valueOf(id) };
    return this.superDao.update(hql, values);
  }
  
  public boolean updateStatus(int id, int status)
  {
    String hql = "update " + this.tab + " set status = ?0 where id = ?1";
    Object[] values = { Integer.valueOf(status), Integer.valueOf(id) };
    return this.superDao.update(hql, values);
  }
}
