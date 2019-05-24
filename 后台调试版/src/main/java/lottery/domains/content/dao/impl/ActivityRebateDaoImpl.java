package lottery.domains.content.dao.impl;

import javautils.jdbc.hibernate.HibernateSuperDao;
import lottery.domains.content.dao.ActivityRebateDao;
import lottery.domains.content.entity.ActivityRebate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ActivityRebateDaoImpl
  implements ActivityRebateDao
{
  private final String tab = ActivityRebate.class.getSimpleName();
  @Autowired
  private HibernateSuperDao<ActivityRebate> superDao;
  
  public boolean add(ActivityRebate entity)
  {
    return this.superDao.save(entity);
  }
  
  public ActivityRebate getById(int id)
  {
    String hql = "from " + this.tab + " where id = ?0";
    Object[] values = { Integer.valueOf(id) };
    return (ActivityRebate)this.superDao.unique(hql, values);
  }
  
  public ActivityRebate getByType(int type)
  {
    String hql = "from " + this.tab + " where type = ?0";
    Object[] values = { Integer.valueOf(type) };
    return (ActivityRebate)this.superDao.unique(hql, values);
  }
  
  public boolean update(ActivityRebate entity)
  {
    return this.superDao.update(entity);
  }
}
