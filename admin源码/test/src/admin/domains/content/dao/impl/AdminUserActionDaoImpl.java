package admin.domains.content.dao.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import admin.domains.content.entity.AdminUserAction;
import javautils.jdbc.hibernate.HibernateSuperDao;
import org.springframework.stereotype.Repository;
import admin.domains.content.dao.AdminUserActionDao;

@Repository
public class AdminUserActionDaoImpl implements AdminUserActionDao
{
    private final String tab;
    @Autowired
    private HibernateSuperDao<AdminUserAction> superDao;
    
    public AdminUserActionDaoImpl() {
        this.tab = AdminUserAction.class.getSimpleName();
    }
    
    @Override
    public List<AdminUserAction> listAll() {
        final String hql = "from " + this.tab + " order by sort";
        return this.superDao.list(hql);
    }
    
    @Override
    public boolean update(final AdminUserAction entity) {
        return this.superDao.update(entity);
    }
    
    @Override
    public boolean save(final AdminUserAction entity) {
        return this.superDao.save(entity);
    }
    
    @Override
    public AdminUserAction getById(final int id) {
        final String hql = "from " + this.tab + " where id = ?0";
        final Object[] values = { id };
        return (AdminUserAction)this.superDao.unique(hql, values);
    }
}
