package admin.domains.content.dao.impl;

import admin.domains.content.dao.AdminUserDao;
import admin.domains.content.entity.AdminUser;
import java.util.List;
import javautils.jdbc.hibernate.HibernateSuperDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AdminUserDaoImpl
  implements AdminUserDao
{
  private final String tab = AdminUser.class.getSimpleName();
  @Autowired
  private HibernateSuperDao<AdminUser> superDao;
  
  public List<AdminUser> listAll()
  {
    String hql = "from " + this.tab;
    return this.superDao.list(hql);
  }
  
  public AdminUser getById(int id)
  {
    String hql = "from " + this.tab + " where id = ?0";
    Object[] values = { Integer.valueOf(id) };
    return (AdminUser)this.superDao.unique(hql, values);
  }
  
  public AdminUser getByUsername(String username)
  {
    String hql = "from " + this.tab + " where username = ?0";
    Object[] values = { username };
    return (AdminUser)this.superDao.unique(hql, values);
  }
  
  public boolean add(AdminUser entity)
  {
    return this.superDao.save(entity);
  }
  
  public boolean update(AdminUser entity)
  {
    return this.superDao.update(entity);
  }
}
