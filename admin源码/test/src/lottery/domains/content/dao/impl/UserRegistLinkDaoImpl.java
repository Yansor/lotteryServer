package lottery.domains.content.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.entity.UserRegistLink;
import javautils.jdbc.hibernate.HibernateSuperDao;
import org.springframework.stereotype.Repository;
import lottery.domains.content.dao.UserRegistLinkDao;

@Repository
public class UserRegistLinkDaoImpl implements UserRegistLinkDao
{
    private final String tab;
    @Autowired
    private HibernateSuperDao<UserRegistLink> superDao;
    
    public UserRegistLinkDaoImpl() {
        this.tab = UserRegistLink.class.getSimpleName();
    }
    
    @Override
    public boolean delete(final int userId) {
        final String hql = "delete from " + this.tab + " where userId = ?0";
        final Object[] values = { userId };
        return this.superDao.delete(hql, values);
    }
}
