package lottery.domains.content.dao.impl;

import java.util.List;
import javautils.jdbc.PageList;
import javautils.jdbc.hibernate.HibernateSuperDao;
import lottery.domains.content.dao.UserBetsLimitDao;
import lottery.domains.content.entity.UserBetsLimit;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserBetsLimitDaoImpl
  implements UserBetsLimitDao
{
  private final String tab = UserBetsLimit.class.getSimpleName();
  @Autowired
  private HibernateSuperDao<UserBetsLimit> superDao;
  
  public PageList find(List<Criterion> criterions, List<Order> orders, int start, int limit)
  {
    return this.superDao.findPageList(UserBetsLimit.class, criterions, orders, start, limit);
  }
  
  public boolean save(UserBetsLimit entity)
  {
    return this.superDao.save(entity);
  }
  
  public UserBetsLimit getByUserId(int userId)
  {
    String hql = "from " + this.tab + " where userId = ?0";
    Object[] values = { Integer.valueOf(userId) };
    List<UserBetsLimit> list = this.superDao.list(hql, values);
    if ((list != null) && (list.size() > 0)) {
      return (UserBetsLimit)list.get(0);
    }
    return null;
  }
  
  public boolean update(UserBetsLimit entity)
  {
    return this.superDao.update(entity);
  }
  
  public boolean delete(int id)
  {
    String hql = "delete from " + this.tab + " where id = ?0";
    Object[] values = { Integer.valueOf(id) };
    return this.superDao.delete(hql, values);
  }
  
  public UserBetsLimit getById(int id)
  {
    String hql = "from " + this.tab + " where id = ?0";
    Object[] values = { Integer.valueOf(id) };
    List<UserBetsLimit> list = this.superDao.list(hql, values);
    if ((list != null) && (list.size() > 0)) {
      return (UserBetsLimit)list.get(0);
    }
    return null;
  }
}
