package lottery.domains.content.dao.impl;

import javautils.jdbc.PageList;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.entity.UserBankcardUnbindRecord;
import javautils.jdbc.hibernate.HibernateSuperDao;
import org.springframework.stereotype.Repository;
import lottery.domains.content.dao.UserBankcardUnbindDao;

@Repository
public class UserBankcardUnbindDaoImpl implements UserBankcardUnbindDao
{
    public static final String tab;
    @Autowired
    private HibernateSuperDao<UserBankcardUnbindRecord> superDao;
    
    static {
        tab = UserBankcardUnbindRecord.class.getSimpleName();
    }
    
    @Override
    public boolean add(final UserBankcardUnbindRecord entity) {
        return this.superDao.save(entity);
    }
    
    @Override
    public boolean update(final UserBankcardUnbindRecord entity) {
        return this.superDao.update(entity);
    }
    
    @Override
    public boolean delByCardId(final String cardId) {
        final String hql = "delete from " + UserBankcardUnbindDaoImpl.tab + " where cardId = ?0";
        final Object[] values = { cardId };
        return this.superDao.delete(hql, values);
    }
    
    @Override
    public boolean updateByParam(final String userIds, final String cardId, final int unbindNum, final String unbindTime) {
        final String hql = "update " + UserBankcardUnbindDaoImpl.tab + " set userIds =?0, unbindNum = ?1,unbindTime = ?2 where cardId = ?3";
        final Object[] values = { userIds, unbindNum, unbindTime, cardId };
        return this.superDao.update(hql, values);
    }
    
    @Override
    public UserBankcardUnbindRecord getUnbindInfoById(final int id) {
        final String hql = "from " + UserBankcardUnbindDaoImpl.tab + " where id = ?0";
        final Object[] values = { id };
        return (UserBankcardUnbindRecord)this.superDao.unique(hql, values);
    }
    
    @Override
    public UserBankcardUnbindRecord getUnbindInfoBycardId(final String cardId) {
        final String hql = "from " + UserBankcardUnbindDaoImpl.tab + " where cardId = ?0";
        final Object[] values = { cardId };
        return (UserBankcardUnbindRecord)this.superDao.unique(hql, values);
    }
    
    @Override
    public PageList search(final List<Criterion> criterions, final List<Order> orders, final int start, final int limit) {
        return this.superDao.findPageList(UserBankcardUnbindRecord.class, criterions, orders, start, limit);
    }
    
    @Override
    public List<UserBankcardUnbindRecord> listAll() {
        final String hql = "from " + UserBankcardUnbindDaoImpl.tab;
        return this.superDao.list(hql);
    }
}
