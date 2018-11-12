package lottery.domains.content.dao.impl;

import javautils.array.ArrayUtils;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.entity.UserSysMessage;
import javautils.jdbc.hibernate.HibernateSuperDao;
import org.springframework.stereotype.Repository;
import lottery.domains.content.dao.UserSysMessageDao;

@Repository
public class UserSysMessageDaoImpl implements UserSysMessageDao
{
    private final String tab;
    @Autowired
    private HibernateSuperDao<UserSysMessage> superDao;
    
    public UserSysMessageDaoImpl() {
        this.tab = UserSysMessage.class.getSimpleName();
    }
    
    @Override
    public boolean add(final UserSysMessage entity) {
        return this.superDao.save(entity);
    }
    
    @Override
    public List<UserSysMessage> listUnread(final int userId) {
        final String hql = "from " + this.tab + " where userId = ?0 and status = 0";
        final Object[] values = { userId };
        return this.superDao.list(hql, values);
    }
    
    @Override
    public boolean updateUnread(final int userId, final int[] ids) {
        if (ids.length > 0) {
            final String hql = "update " + this.tab + " set status = 1 where userId = ?0 and id in (" + ArrayUtils.transInIds(ids) + ")";
            final Object[] values = { userId };
            return this.superDao.update(hql, values);
        }
        return false;
    }
}
