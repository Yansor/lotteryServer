package lottery.domains.content.dao.impl;

import javautils.jdbc.hibernate.HibernateSuperDao;
import lottery.domains.content.dao.UserRegistLinkDao;
import lottery.domains.content.entity.UserRegistLink;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserRegistLinkDaoImpl
  implements UserRegistLinkDao
{
  private final String tab = UserRegistLink.class.getSimpleName();
  @Autowired
  private HibernateSuperDao<UserRegistLink> superDao;
  
  public boolean delete(int userId)
  {
    String hql = "delete from " + this.tab + " where userId = ?0";
    Object[] values = { Integer.valueOf(userId) };
    return this.superDao.delete(hql, values);
  }
}
