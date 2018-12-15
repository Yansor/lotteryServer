package lottery.domains.content.dao.impl;

import java.util.List;
import javautils.jdbc.PageList;
import javautils.jdbc.hibernate.HibernateSuperDao;
import lottery.domains.content.dao.UserSecurityDao;
import lottery.domains.content.entity.UserSecurity;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserSecurityDaoImpl
  implements UserSecurityDao
{
  private final String tab = UserSecurity.class.getSimpleName();
  @Autowired
  private HibernateSuperDao<UserSecurity> superDao;
  
  public UserSecurity getById(int id)
  {
    String hql = "from " + this.tab + " where id = ?0";
    Object[] values = { Integer.valueOf(id) };
    return (UserSecurity)this.superDao.unique(hql, values);
  }
  
  public List<UserSecurity> getByUserId(int userId)
  {
    String hql = "from " + this.tab + " where userId = ?0";
    Object[] values = { Integer.valueOf(userId) };
    return this.superDao.list(hql, values);
  }
  
  public boolean delete(int userId)
  {
    String hql = "delete from " + this.tab + " where userId = ?0";
    Object[] values = { Integer.valueOf(userId) };
    return this.superDao.delete(hql, values);
  }
  
  public PageList find(List<Criterion> criterions, List<Order> orders, int start, int limit)
  {
    return this.superDao.findPageList(UserSecurity.class, criterions, orders, start, limit);
  }
  
  public boolean updateValue(int id, String md5Value)
  {
    String hql = "update " + this.tab + " set value = ?1 where id = ?0";
    Object[] values = { Integer.valueOf(id), md5Value };
    return this.superDao.update(hql, values);
  }
}
