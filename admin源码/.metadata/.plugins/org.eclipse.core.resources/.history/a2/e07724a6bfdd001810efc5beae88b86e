package lottery.domains.content.dao.impl;

import javautils.jdbc.PageList;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.entity.LotteryOpenTime;
import javautils.jdbc.hibernate.HibernateSuperDao;
import org.springframework.stereotype.Repository;
import lottery.domains.content.dao.LotteryOpenTimeDao;

@Repository
public class LotteryOpenTimeDaoImpl implements LotteryOpenTimeDao
{
    private final String tab;
    @Autowired
    private HibernateSuperDao<LotteryOpenTime> superDao;
    
    public LotteryOpenTimeDaoImpl() {
        this.tab = LotteryOpenTime.class.getSimpleName();
    }
    
    @Override
    public List<LotteryOpenTime> listAll() {
        final String hql = "from " + this.tab + " order by expect asc";
        return this.superDao.list(hql);
    }
    
    @Override
    public List<LotteryOpenTime> list(final String lottery) {
        final String hql = "from " + this.tab + " where lottery = ?0";
        final Object[] values = { lottery };
        return this.superDao.list(hql, values);
    }
    
    @Override
    public PageList find(final List<Criterion> criterions, final List<Order> orders, final int start, final int limit) {
        return this.superDao.findPageList(LotteryOpenTime.class, criterions, orders, start, limit);
    }
    
    @Override
    public LotteryOpenTime getById(final int id) {
        final String hql = "from " + this.tab + " where id = ?0";
        final Object[] values = { id };
        return (LotteryOpenTime)this.superDao.unique(hql, values);
    }
    
    @Override
    public LotteryOpenTime getByLottery(final String lottery) {
        final String hql = "from " + this.tab + " where lottery = ?0";
        final Object[] values = { lottery };
        return (LotteryOpenTime)this.superDao.unique(hql, values);
    }
    
    @Override
    public boolean update(final LotteryOpenTime entity) {
        return this.superDao.update(entity);
    }
    
    @Override
    public boolean save(final LotteryOpenTime entity) {
        return this.superDao.save(entity);
    }
}
