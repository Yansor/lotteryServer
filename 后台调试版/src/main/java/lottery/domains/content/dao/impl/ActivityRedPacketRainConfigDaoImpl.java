package lottery.domains.content.dao.impl;

import java.util.List;
import javautils.jdbc.hibernate.HibernateSuperDao;
import lottery.domains.content.dao.ActivityRedPacketRainConfigDao;
import lottery.domains.content.entity.ActivityRedPacketRainConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ActivityRedPacketRainConfigDaoImpl
  implements ActivityRedPacketRainConfigDao
{
  private final String tab = ActivityRedPacketRainConfig.class.getSimpleName();
  @Autowired
  private HibernateSuperDao<ActivityRedPacketRainConfig> superDao;
  
  public ActivityRedPacketRainConfig getConfig()
  {
    String hql = "from " + this.tab;
    List<ActivityRedPacketRainConfig> list = this.superDao.list(hql, 0, 1);
    return (list == null) || (list.size() <= 0) ? null : (ActivityRedPacketRainConfig)list.get(0);
  }
  
  public boolean updateConfig(int id, String rules, String hours, int durationMinutes)
  {
    String hql = "update " + this.tab + " set rules = ?0, hours = ?1, durationMinutes = ?2 where id = ?3";
    Object[] values = { rules, hours, Integer.valueOf(durationMinutes), Integer.valueOf(id) };
    return this.superDao.update(hql, values);
  }
  
  public boolean updateStatus(int id, int status)
  {
    String hql = "update " + this.tab + " set status = ?0 where id = ?1";
    Object[] values = { Integer.valueOf(status), Integer.valueOf(id) };
    return this.superDao.update(hql, values);
  }
}
