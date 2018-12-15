package admin.domains.content.dao.impl;

import admin.domains.content.dao.AdminUserMenuDao;
import admin.domains.content.entity.AdminUserMenu;
import java.util.List;
import javautils.jdbc.hibernate.HibernateSuperDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AdminUserMenuDaoImpl
  implements AdminUserMenuDao
{
  private final String tab = AdminUserMenu.class.getSimpleName();
  @Autowired
  private HibernateSuperDao<AdminUserMenu> superDao;
  
  public List<AdminUserMenu> listAll()
  {
    String hql = "from " + this.tab + " order by sort";
    return this.superDao.list(hql);
  }
  
  public boolean update(AdminUserMenu entity)
  {
    return this.superDao.update(entity);
  }
  
  public AdminUserMenu getById(int id)
  {
    String hql = "from " + this.tab + " where id = ?0";
    Object[] values = { Integer.valueOf(id) };
    return (AdminUserMenu)this.superDao.unique(hql, values);
  }
  
  public boolean modsort(int id, int sort)
  {
    String hql = "update " + this.tab + " set  sort= sort+ ?1 where id = ?0";
    Object[] values = { Integer.valueOf(id), Integer.valueOf(sort) };
    return this.superDao.update(hql, values);
  }
  
  public boolean updateSort(int id, int sort)
  {
    String hql = "update " + this.tab + " set  sort= ?1 where id = ?0";
    Object[] values = { Integer.valueOf(id), Integer.valueOf(sort) };
    return this.superDao.update(hql, values);
  }
  
  public List<AdminUserMenu> getBySortUp(int upid, int sort)
  {
    String hql = "from " + this.tab + " where upid = ?0 and sort < ?1 order by sort desc";
    Object[] values = { Integer.valueOf(upid), Integer.valueOf(sort) };
    return this.superDao.list(hql, values);
  }
  
  public List<AdminUserMenu> getBySortDown(int upid, int sort)
  {
    String hql = "from " + this.tab + " where upid = ?0 and sort > ?1 order by sort asc";
    Object[] values = { Integer.valueOf(upid), Integer.valueOf(sort) };
    return this.superDao.list(hql, values);
  }
  
  public int getMaxSort(int upid)
  {
    String hql = "select max(sort) from " + this.tab + " where upid =?0";
    Object[] values = { Integer.valueOf(upid) };
    Object result = this.superDao.unique(hql, values);
    return result != null ? ((Number)result).intValue() : 0;
  }
}
