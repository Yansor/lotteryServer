package admin.domains.content.dao.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import admin.domains.content.entity.AdminUser;
import javautils.jdbc.hibernate.HibernateSuperDao;
import org.springframework.stereotype.Repository;
import admin.domains.content.dao.AdminUserDao;

@Repository
public class AdminUserDaoImpl implements AdminUserDao
{
    private final String tab;
    @Autowired
    private HibernateSuperDao<AdminUser> superDao;
    
    public AdminUserDaoImpl() {
        this.tab = AdminUser.class.getSimpleName();
    }
    
    @Override
    public List<AdminUser> listAll() {
        final String hql = "from " + this.tab;
        return this.superDao.list(hql);
    }
    
    @Override
    public AdminUser getById(final int id) {
        final String hql = "from " + this.tab + " where id = ?0";
        final Object[] values = { id };
        return (AdminUser)this.superDao.unique(hql, values);
    }
    
    @Override
    public AdminUser getByUsername(final String username) {
        final String hql = "from " + this.tab + " where username = ?0";
        final Object[] values = { username };
        return (AdminUser)this.superDao.unique(hql, values);
    }
    
    @Override
    public boolean add(final AdminUser entity) {
        return this.superDao.save(entity);
    }
    
    @Override
    public boolean update(final AdminUser entity) {
        return this.superDao.update(entity);
    }
}
