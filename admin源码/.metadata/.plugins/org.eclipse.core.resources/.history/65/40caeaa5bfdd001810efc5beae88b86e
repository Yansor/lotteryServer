package lottery.domains.content.dao.impl;

import javautils.jdbc.PageList;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.entity.UserMessage;
import javautils.jdbc.hibernate.HibernateSuperDao;
import org.springframework.stereotype.Repository;
import lottery.domains.content.dao.UserMessageDao;

@Repository
public class UserMessageDaoImpl implements UserMessageDao
{
    private final String tab;
    @Autowired
    private HibernateSuperDao<UserMessage> superDao;
    
    public UserMessageDaoImpl() {
        this.tab = UserMessage.class.getSimpleName();
    }
    
    @Override
    public UserMessage getById(final int id) {
        final String hql = "from " + this.tab + " where id = ?0";
        final Object[] values = { id };
        return (UserMessage)this.superDao.unique(hql, values);
    }
    
    @Override
    public boolean delete(final int id) {
        final String hql = "update " + this.tab + " set toStatus = -1, fromStatus = -1 where id = ?0";
        final Object[] values = { id };
        return this.superDao.update(hql, values);
    }
    
    @Override
    public PageList search(final List<Criterion> criterions, final List<Order> orders, final int start, final int limit) {
        final String propertyName = "id";
        return this.superDao.findPageList(UserMessage.class, propertyName, criterions, orders, start, limit);
    }
    
    @Override
    public boolean save(final UserMessage userMessage) {
        return this.superDao.save(userMessage);
    }
    
    @Override
    public void update(final UserMessage message) {
        this.superDao.update(message);
    }
}
