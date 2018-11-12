package lottery.domains.content.dao.impl;

import javautils.array.ArrayUtils;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.entity.UserCodeQuota;
import javautils.jdbc.hibernate.HibernateSuperDao;
import org.springframework.stereotype.Repository;
import lottery.domains.content.dao.UserCodeQuotaDao;

@Repository
public class UserCodeQuotaDaoImpl implements UserCodeQuotaDao
{
    private final String tab;
    @Autowired
    private HibernateSuperDao<UserCodeQuota> superDao;
    
    public UserCodeQuotaDaoImpl() {
        this.tab = UserCodeQuota.class.getSimpleName();
    }
    
    @Override
    public boolean add(final UserCodeQuota entity) {
        return this.superDao.save(entity);
    }
    
    @Override
    public UserCodeQuota get(final int userId) {
        final String hql = "from " + this.tab + " where userId = ?0";
        final Object[] values = { userId };
        return (UserCodeQuota)this.superDao.unique(hql, values);
    }
    
    @Override
    public List<UserCodeQuota> list(final int[] userIds) {
        List<UserCodeQuota> result = new ArrayList<UserCodeQuota>();
        if (userIds.length > 0) {
            final String hql = "from " + this.tab + " where userId in (" + ArrayUtils.transInIds(userIds) + ")";
            result = this.superDao.list(hql);
        }
        return result;
    }
    
    @Override
    public boolean update(final UserCodeQuota entity) {
        return this.superDao.update(entity);
    }
    
    @Override
    public boolean delete(final int userId) {
        final String hql = "delete from " + this.tab + " where userId = ?0";
        final Object[] values = { userId };
        return this.superDao.delete(hql, values);
    }
}
