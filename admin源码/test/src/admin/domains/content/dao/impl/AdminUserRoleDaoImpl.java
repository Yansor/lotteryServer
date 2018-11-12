package admin.domains.content.dao.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import admin.domains.content.entity.AdminUserRole;
import javautils.jdbc.hibernate.HibernateSuperDao;
import org.springframework.stereotype.Repository;
import admin.domains.content.dao.AdminUserRoleDao;

@Repository
public class AdminUserRoleDaoImpl implements AdminUserRoleDao
{
    private final String tab;
    @Autowired
    private HibernateSuperDao<AdminUserRole> superDao;
    
    public AdminUserRoleDaoImpl() {
        this.tab = AdminUserRole.class.getSimpleName();
    }
    
    @Override
    public List<AdminUserRole> listAll() {
        final String hql = "from " + this.tab + " order by sort";
        return this.superDao.list(hql);
    }
    
    @Override
    public boolean update(final AdminUserRole entity) {
        return this.superDao.update(entity);
    }
    
    @Override
    public boolean save(final AdminUserRole entity) {
        return this.superDao.save(entity);
    }
    
    @Override
    public AdminUserRole getByName(final String name) {
        final String hql = "from " + this.tab + " where name = ?0";
        final Object[] values = { name };
        return (AdminUserRole)this.superDao.unique(hql, values);
    }
    
    @Override
    public AdminUserRole getById(final int id) {
        final String hql = "from " + this.tab + " where id = ?0";
        final Object[] values = { id };
        return (AdminUserRole)this.superDao.unique(hql, values);
    }
    
    @Override
    public List<AdminUserRole> getByUpid(final int upid) {
        final String hql = "from " + this.tab + " where upid = ?0";
        final Object[] values = { upid };
        return this.superDao.list(hql, values);
    }
}
