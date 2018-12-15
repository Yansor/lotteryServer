package admin.domains.content.dao.impl;

import admin.domains.content.dao.AdminUserActionDao;
import admin.domains.content.entity.AdminUserAction;
import java.util.List;
import javautils.jdbc.hibernate.HibernateSuperDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AdminUserActionDaoImpl
  implements AdminUserActionDao
{
  private final String tab = AdminUserAction.class.getSimpleName();
  @Autowired
  private HibernateSuperDao<AdminUserAction> superDao;
  
  public List<AdminUserAction> listAll()
  {
    String hql = "from " + this.tab + " order by sort";
    return this.superDao.list(hql);
  }
  
  public boolean update(AdminUserAction entity)
  {
    return this.superDao.update(entity);
  }
  
  public boolean save(AdminUserAction entity)
  {
    return this.superDao.save(entity);
  }
  
  public AdminUserAction getById(int id)
  {
    String hql = "from " + this.tab + " where id = ?0";
    Object[] values = { Integer.valueOf(id) };
    return (AdminUserAction)this.superDao.unique(hql, values);
  }
}
