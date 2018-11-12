package lottery.domains.content.dao.impl;

import javautils.jdbc.PageList;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.Collection;
import org.apache.commons.collections.CollectionUtils;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.entity.UserCard;
import javautils.jdbc.hibernate.HibernateSuperDao;
import org.springframework.stereotype.Repository;
import lottery.domains.content.dao.UserCardDao;

@Repository
public class UserCardDaoImpl implements UserCardDao
{
    private final String tab;
    @Autowired
    private HibernateSuperDao<UserCard> superDao;
    
    public UserCardDaoImpl() {
        this.tab = UserCard.class.getSimpleName();
    }
    
    @Override
    public boolean add(final UserCard entity) {
        return this.superDao.save(entity);
    }
    
    @Override
    public UserCard getById(final int id) {
        final String hql = "from " + this.tab + " where id = ?0";
        final Object[] values = { id };
        return (UserCard)this.superDao.unique(hql, values);
    }
    
    @Override
    public List<UserCard> getByUserId(final int id) {
        final String hql = "from " + this.tab + " where userId = ?0";
        final Object[] values = { id };
        return this.superDao.list(hql, values);
    }
    
    @Override
    public UserCard getByCardId(final String cardId) {
        final String hql = "from " + this.tab + " where cardId = ?0";
        final Object[] values = { cardId };
        return (UserCard)this.superDao.unique(hql, values);
    }
    
    @Override
    public UserCard getByUserAndCardId(final int userId, final String cardId) {
        final String hql = "from " + this.tab + " where userId = ?0 and cardId = ?1";
        final Object[] values = { userId, cardId };
        final List<UserCard> list = this.superDao.list(hql, values);
        return CollectionUtils.isEmpty((Collection)list) ? null : list.get(0);
    }
    
    @Override
    public boolean update(final UserCard entity) {
        return this.superDao.update(entity);
    }
    
    @Override
    public boolean updateCardName(final int userId, final String cardName) {
        final String hql = "update " + this.tab + " set cardName = ?1 where userId = ?0";
        final Object[] values = { userId, cardName };
        return this.superDao.update(hql, values);
    }
    
    @Override
    public boolean delete(final int userId) {
        final String hql = "delete from " + this.tab + " where userId = ?0";
        final Object[] values = { userId };
        return this.superDao.delete(hql, values);
    }
    
    @Override
    public PageList find(final List<Criterion> criterions, final List<Order> orders, final int start, final int limit) {
        return this.superDao.findPageList(UserCard.class, criterions, orders, start, limit);
    }
}
