package lottery.domains.content.dao.impl;

import java.util.List;
import javautils.jdbc.PageList;
import javautils.jdbc.hibernate.HibernateSuperDao;
import lottery.domains.content.dao.UserWhitelistDao;
import lottery.domains.content.entity.UserWhitelist;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserWhitelistDaoImpl
  implements UserWhitelistDao
{
  private final String tab = UserWhitelist.class.getSimpleName();
  @Autowired
  private HibernateSuperDao<UserWhitelist> superDao;
  
  public PageList find(List<Criterion> criterions, List<Order> orders, int start, int limit)
  {
    return this.superDao.findPageList(UserWhitelist.class, criterions, orders, start, limit);
  }
  
  public boolean add(UserWhitelist entity)
  {
    return this.superDao.save(entity);
  }
  
  public boolean delete(int id)
  {
    String hql = "delete from " + this.tab + " where id = ?0";
    Object[] values = { Integer.valueOf(id) };
    return this.superDao.delete(hql, values);
  }
}
