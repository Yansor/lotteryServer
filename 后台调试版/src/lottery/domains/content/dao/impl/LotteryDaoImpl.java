package lottery.domains.content.dao.impl;

import java.util.List;
import javautils.jdbc.hibernate.HibernateSuperDao;
import lottery.domains.content.dao.LotteryDao;
import lottery.domains.content.entity.Lottery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class LotteryDaoImpl
  implements LotteryDao
{
  private final String tab = Lottery.class.getSimpleName();
  @Autowired
  private HibernateSuperDao<Lottery> superDao;
  
  public List<Lottery> listAll()
  {
    String hql = "from " + this.tab + " order by sort";
    return this.superDao.list(hql);
  }
  
  public boolean updateStatus(int id, int status)
  {
    String hql = "update " + this.tab + " set status = ?1 where id = ?0";
    Object[] values = { Integer.valueOf(id), Integer.valueOf(status) };
    return this.superDao.update(hql, values);
  }
  
  public boolean updateTimes(int id, int times)
  {
    String hql = "update " + this.tab + " set times = ?1 where id = ?0";
    Object[] values = { Integer.valueOf(id), Integer.valueOf(times) };
    return this.superDao.update(hql, values);
  }
  
  public Lottery getByName(String name)
  {
    String hql = "from " + this.tab + "  where showName = ?0";
    Object[] values = { name };
    List<Lottery> list = this.superDao.list(hql, values);
    if ((list != null) && (!list.isEmpty())) {
      return (Lottery)list.get(0);
    }
    return null;
  }
  
  public Lottery getByShortName(String name)
  {
    String hql = "from " + this.tab + "  where shortName = ?0";
    Object[] values = { name };
    List<Lottery> list = this.superDao.list(hql, values);
    if ((list != null) && (!list.isEmpty())) {
      return (Lottery)list.get(0);
    }
    return null;
  }
  
  public Lottery getById(int id)
  {
    String hql = "from " + this.tab + "  where id = ?0";
    Object[] values = { Integer.valueOf(id) };
    List<Lottery> list = this.superDao.list(hql, values);
    if ((list != null) && (!list.isEmpty())) {
      return (Lottery)list.get(0);
    }
    return null;
  }
}
