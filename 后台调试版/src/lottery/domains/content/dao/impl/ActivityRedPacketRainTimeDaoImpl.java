package lottery.domains.content.dao.impl;

import javautils.jdbc.hibernate.HibernateSuperDao;
import lottery.domains.content.dao.ActivityRedPacketRainTimeDao;
import lottery.domains.content.entity.ActivityRedPacketRainTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ActivityRedPacketRainTimeDaoImpl
  implements ActivityRedPacketRainTimeDao
{
  private final String tab = ActivityRedPacketRainTime.class.getSimpleName();
  @Autowired
  private HibernateSuperDao<ActivityRedPacketRainTime> superDao;
  
  public boolean add(ActivityRedPacketRainTime time)
  {
    return this.superDao.save(time);
  }
  
  public ActivityRedPacketRainTime getByDateAndHour(String date, String hour)
  {
    String hql = "from " + this.tab + " where date = ?0 and hour = ?1";
    Object[] values = { date, hour };
    return (ActivityRedPacketRainTime)this.superDao.unique(hql, values);
  }
}
