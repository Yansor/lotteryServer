package lottery.domains.content.dao.impl;

import javautils.jdbc.PageList;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.entity.UserBetsHitRanking;
import javautils.jdbc.hibernate.HibernateSuperDao;
import org.springframework.stereotype.Repository;
import lottery.domains.content.dao.UserBetsHitRankingDao;

@Repository
public class UserBetsHitRankingDaoImpl implements UserBetsHitRankingDao
{
    private final String tab;
    @Autowired
    private HibernateSuperDao<UserBetsHitRanking> superDao;
    
    public UserBetsHitRankingDaoImpl() {
        this.tab = UserBetsHitRanking.class.getSimpleName();
    }
    
    @Override
    public UserBetsHitRanking getById(final int id) {
        final String hql = "from " + this.tab + " where id = ?0";
        final Object[] values = { id };
        return (UserBetsHitRanking)this.superDao.unique(hql, values);
    }
    
    @Override
    public boolean add(final UserBetsHitRanking entity) {
        return this.superDao.save(entity);
    }
    
    @Override
    public boolean update(final UserBetsHitRanking entity) {
        return this.superDao.update(entity);
    }
    
    @Override
    public boolean delete(final int id) {
        final String hql = "delete from " + this.tab + " where id = ?0";
        final Object[] values = { id };
        return this.superDao.delete(hql, values);
    }
    
    @Override
    public PageList find(final List<Criterion> criterions, final List<Order> orders, final int start, final int limit) {
        return this.superDao.findPageList(UserBetsHitRanking.class, criterions, orders, start, limit);
    }
}
