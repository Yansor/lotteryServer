package lottery.domains.content.dao.impl;

import java.util.List;
import javautils.jdbc.hibernate.HibernateSuperDao;
import lottery.domains.content.dao.SysConfigDao;
import lottery.domains.content.entity.SysConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SysConfigDaoImpl
  implements SysConfigDao
{
  private final String tab = SysConfig.class.getSimpleName();
  @Autowired
  private HibernateSuperDao<SysConfig> superDao;
  
  public List<SysConfig> listAll()
  {
    String hql = "from " + this.tab;
    return this.superDao.list(hql);
  }
  
  public SysConfig get(String group, String key)
  {
    String hql = "from " + this.tab + " where group = ?0 and key = ?1";
    Object[] values = { group, key };
    return (SysConfig)this.superDao.unique(hql, values);
  }
  
  public boolean update(SysConfig entity)
  {
    return this.superDao.update(entity);
  }
  
  public boolean save(SysConfig entity)
  {
    return this.superDao.save(entity);
  }
}
