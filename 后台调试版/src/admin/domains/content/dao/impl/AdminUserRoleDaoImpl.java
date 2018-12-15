package admin.domains.content.dao.impl;

import admin.domains.content.dao.AdminUserRoleDao;
import admin.domains.content.entity.AdminUserRole;
import java.util.List;
import javautils.jdbc.hibernate.HibernateSuperDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AdminUserRoleDaoImpl
  implements AdminUserRoleDao
{
  private final String tab = AdminUserRole.class.getSimpleName();
  @Autowired
  private HibernateSuperDao<AdminUserRole> superDao;
  
  public List<AdminUserRole> listAll()
  {
    String hql = "from " + this.tab + " order by sort";
    return this.superDao.list(hql);
  }
  
  public boolean update(AdminUserRole entity)
  {
    return this.superDao.update(entity);
  }
  
  public boolean save(AdminUserRole entity)
  {
    return this.superDao.save(entity);
  }
  
  public AdminUserRole getByName(String name)
  {
    String hql = "from " + this.tab + " where name = ?0";
    Object[] values = { name };
    return (AdminUserRole)this.superDao.unique(hql, values);
  }
  
  public AdminUserRole getById(int id)
  {
    String hql = "from " + this.tab + " where id = ?0";
    Object[] values = { Integer.valueOf(id) };
    return (AdminUserRole)this.superDao.unique(hql, values);
  }
  
  public List<AdminUserRole> getByUpid(int upid)
  {
    String hql = "from " + this.tab + " where upid = ?0";
    Object[] values = { Integer.valueOf(upid) };
    return this.superDao.list(hql, values);
  }
}
