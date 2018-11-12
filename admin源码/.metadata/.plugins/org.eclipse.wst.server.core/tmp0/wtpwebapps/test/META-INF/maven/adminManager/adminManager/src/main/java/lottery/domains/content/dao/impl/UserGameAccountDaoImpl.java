package lottery.domains.content.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.entity.UserGameAccount;
import javautils.jdbc.hibernate.HibernateSuperDao;
import org.springframework.stereotype.Repository;
import lottery.domains.content.dao.UserGameAccountDao;

@Repository
public class UserGameAccountDaoImpl implements UserGameAccountDao
{
    private final String tab;
    @Autowired
    private HibernateSuperDao<UserGameAccount> superDao;
    
    public UserGameAccountDaoImpl() {
        this.tab = UserGameAccount.class.getSimpleName();
    }
    
    @Override
    public UserGameAccount get(final String platformName, final int platformId) {
        final String hql = "from " + this.tab + " where username=?0 and platformId=?1 and model = 1";
        final Object[] values = { platformName, platformId };
        return (UserGameAccount)this.superDao.unique(hql, values);
    }
    
    @Override
    public UserGameAccount get(final int userId, final int platformId) {
        final String hql = "from " + this.tab + " where userId=?0 and platformId=?1 and model = 1";
        final Object[] values = { userId, platformId };
        return (UserGameAccount)this.superDao.unique(hql, values);
    }
    
    @Override
    public UserGameAccount save(final UserGameAccount account) {
        this.superDao.save(account);
        return account;
    }
}
