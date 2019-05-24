package lottery.domains.content.dao.impl;

import java.util.List;
import javautils.jdbc.PageList;
import javautils.jdbc.hibernate.HibernateSuperDao;
import lottery.domains.content.dao.UserBlacklistDao;
import lottery.domains.content.entity.UserBlacklist;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserBlacklistDaoImpl
  implements UserBlacklistDao
{
  private final String tab = UserBlacklist.class.getSimpleName();
  @Autowired
  private HibernateSuperDao<UserBlacklist> superDao;
  
  public PageList find(List<Criterion> criterions, List<Order> orders, int start, int limit)
  {
    return this.superDao.findPageList(UserBlacklist.class, criterions, orders, start, limit);
  }
  
  public boolean add(UserBlacklist entity)
  {
    return this.superDao.save(entity);
  }
  
  public boolean delete(int id)
  {
    String hql = "delete from " + this.tab + " where id = ?0";
    Object[] values = { Integer.valueOf(id) };
    return this.superDao.delete(hql, values);
  }
  
  public int getByIp(String ip)
  {
    String hql = "select count(id) from " + this.tab + " where ip = ?0";
    Object[] values = { ip };
    Object result = this.superDao.unique(hql, values);
    return result == null ? 0 : Integer.parseInt(result.toString());
  }
}
