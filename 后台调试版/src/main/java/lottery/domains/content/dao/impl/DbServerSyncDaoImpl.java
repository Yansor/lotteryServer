package lottery.domains.content.dao.impl;

import java.io.PrintStream;
import java.util.List;
import javautils.date.Moment;
import javautils.jdbc.hibernate.HibernateSuperDao;
import lottery.domains.content.dao.DbServerSyncDao;
import lottery.domains.content.entity.DbServerSync;
import lottery.domains.content.global.DbServerSyncEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DbServerSyncDaoImpl
  implements DbServerSyncDao
{
  private final String tab = DbServerSync.class.getSimpleName();
  @Autowired
  private HibernateSuperDao<DbServerSync> superDao;
  
  public static void main(String[] args)
  {
    System.out.println(DbServerSyncEnum.LOTTERY.name());
  }
  
  public List<DbServerSync> listAll()
  {
    String hql = "from " + this.tab;
    return this.superDao.list(hql);
  }
  
  public boolean update(DbServerSyncEnum type)
  {
    String key = type.name();
    String time = new Moment().toSimpleTime();
    String hql = "update " + this.tab + " set lastModTime = ?1 where key = ?0";
    Object[] values = { key, time };
    return this.superDao.update(hql, values);
  }
  
  public boolean update(String key, String lastModTime)
  {
    String hql = "update " + this.tab + " set lastModTime = ?1 where key = ?0";
    Object[] values = { key, lastModTime };
    return this.superDao.update(hql, values);
  }
  
  public DbServerSync getByKey(String key)
  {
    String hql = "from " + this.tab + " where key=?0";
    Object[] values = { key };
    return (DbServerSync)this.superDao.unique(hql, values);
  }
  
  public boolean save(DbServerSync dbServerSync)
  {
    return this.superDao.save(dbServerSync);
  }
}
