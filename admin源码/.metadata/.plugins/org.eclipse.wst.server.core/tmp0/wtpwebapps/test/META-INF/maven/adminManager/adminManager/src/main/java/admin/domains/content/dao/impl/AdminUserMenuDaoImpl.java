package admin.domains.content.dao.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import admin.domains.content.entity.AdminUserMenu;
import javautils.jdbc.hibernate.HibernateSuperDao;
import org.springframework.stereotype.Repository;
import admin.domains.content.dao.AdminUserMenuDao;

@Repository
public class AdminUserMenuDaoImpl implements AdminUserMenuDao
{
    private final String tab;
    @Autowired
    private HibernateSuperDao<AdminUserMenu> superDao;
    
    public AdminUserMenuDaoImpl() {
        this.tab = AdminUserMenu.class.getSimpleName();
    }
    
    @Override
    public List<AdminUserMenu> listAll() {
        final String hql = "from " + this.tab + " order by sort";
        return this.superDao.list(hql);
    }
    
    @Override
    public boolean update(final AdminUserMenu entity) {
        return this.superDao.update(entity);
    }
    
    @Override
    public AdminUserMenu getById(final int id) {
        final String hql = "from " + this.tab + " where id = ?0";
        final Object[] values = { id };
        return (AdminUserMenu)this.superDao.unique(hql, values);
    }
    
    @Override
    public boolean modsort(final int id, final int sort) {
        final String hql = "update " + this.tab + " set  sort= sort+ ?1 where id = ?0";
        final Object[] values = { id, sort };
        return this.superDao.update(hql, values);
    }
    
    @Override
    public boolean updateSort(final int id, final int sort) {
        final String hql = "update " + this.tab + " set  sort= ?1 where id = ?0";
        final Object[] values = { id, sort };
        return this.superDao.update(hql, values);
    }
    
    @Override
    public List<AdminUserMenu> getBySortUp(final int upid, final int sort) {
        final String hql = "from " + this.tab + " where upid = ?0 and sort < ?1 order by sort desc";
        final Object[] values = { upid, sort };
        return this.superDao.list(hql, values);
    }
    
    @Override
    public List<AdminUserMenu> getBySortDown(final int upid, final int sort) {
        final String hql = "from " + this.tab + " where upid = ?0 and sort > ?1 order by sort asc";
        final Object[] values = { upid, sort };
        return this.superDao.list(hql, values);
    }
    
    @Override
    public int getMaxSort(final int upid) {
        final String hql = "select max(sort) from " + this.tab + " where upid =?0";
        final Object[] values = { upid };
        final Object result = this.superDao.unique(hql, values);
        return (result != null) ? ((Number)result).intValue() : 0;
    }
}
