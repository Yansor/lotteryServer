package lottery.domains.content.dao.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.entity.UserInfo;
import javautils.jdbc.hibernate.HibernateSuperDao;
import org.springframework.stereotype.Repository;
import lottery.domains.content.dao.UserInfoDao;

@Repository
public class UserInfoDaoImpl implements UserInfoDao
{
    private final String tab;
    @Autowired
    private HibernateSuperDao<UserInfo> superDao;
    
    public UserInfoDaoImpl() {
        this.tab = UserInfo.class.getSimpleName();
    }
    
    @Override
    public UserInfo get(final int userId) {
        final String hql = "from " + this.tab + " where userId = ?0";
        final Object[] values = { userId };
        return (UserInfo)this.superDao.unique(hql, values);
    }
    
    @Override
    public List<UserInfo> listByBirthday(final String date) {
        final String hql = "from " + this.tab + " where birthday like ?0";
        final Object[] values = { "%" + date };
        return this.superDao.list(hql, values);
    }
    
    @Override
    public boolean add(final UserInfo entity) {
        return this.superDao.save(entity);
    }
    
    @Override
    public boolean update(final UserInfo entity) {
        return this.superDao.update(entity);
    }
}
