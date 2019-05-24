package lottery.domains.content.dao.impl;

import java.util.List;
import javautils.jdbc.hibernate.HibernateSuperDao;
import lottery.domains.content.dao.SysPlatformDao;
import lottery.domains.content.entity.SysPlatform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SysPlatformDaoImpl
  implements SysPlatformDao
{
  private final String tab = SysPlatform.class.getSimpleName();
  @Autowired
  private HibernateSuperDao<SysPlatform> superDao;
  
  public List<SysPlatform> listAll()
  {
    String hql = "from " + this.tab;
    return this.superDao.list(hql);
  }
  
  public boolean updateStatus(int id, int status)
  {
    String hql = "update " + this.tab + " set status = ?1 where id = ?0";
    Object[] values = { Integer.valueOf(id), Integer.valueOf(status) };
    return this.superDao.update(hql, values);
  }
}
