package lottery.domains.content.dao.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.entity.UserWithdrawLimit;
import javautils.jdbc.hibernate.HibernateSuperDao;
import org.springframework.stereotype.Repository;
import lottery.domains.content.dao.UserWithdrawLimitDao;

@Repository
public class UserWithdrawLimitDaoImpl implements UserWithdrawLimitDao
{
    private final String tab;
    @Autowired
    private HibernateSuperDao<UserWithdrawLimit> superDao;
    
    public UserWithdrawLimitDaoImpl() {
        this.tab = UserWithdrawLimit.class.getSimpleName();
    }
    
    @Override
    public UserWithdrawLimit getByUserId(final int userId) {
        final String hql = "from " + this.tab + " where userId = ?0";
        final Object[] values = { userId };
        return (UserWithdrawLimit)this.superDao.unique(hql, values);
    }
    
    @Override
    public boolean add(final UserWithdrawLimit entity) {
        return this.superDao.save(entity);
    }
    
    @Override
    public List<UserWithdrawLimit> getUserWithdrawLimits(final int userId, final String time) {
        final String hql = "from " + this.tab + " where userId = ?0 and rechargeTime <= ?1 order by rechargeTime asc";
        final Object[] values = { userId, time };
        return this.superDao.list(hql, values);
    }
    
    @Override
    public boolean delByUserId(final int userId) {
        final String hql = "delete from " + this.tab + " where userId = ?0";
        final Object[] values = { userId };
        this.superDao.delete(hql, values);
        return true;
    }
}
