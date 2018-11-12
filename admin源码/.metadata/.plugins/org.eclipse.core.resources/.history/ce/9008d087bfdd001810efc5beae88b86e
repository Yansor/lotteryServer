package lottery.domains.content.dao.impl;

import org.hibernate.criterion.Restrictions;
import java.util.ArrayList;
import javautils.jdbc.PageList;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.entity.LotteryOpenCode;
import javautils.jdbc.hibernate.HibernateSuperDao;
import org.springframework.stereotype.Repository;
import lottery.domains.content.dao.LotteryOpenCodeDao;

@Repository
public class LotteryOpenCodeDaoImpl implements LotteryOpenCodeDao
{
    private final String tab;
    @Autowired
    private HibernateSuperDao<LotteryOpenCode> superDao;
    
    public LotteryOpenCodeDaoImpl() {
        this.tab = LotteryOpenCode.class.getSimpleName();
    }
    
    @Override
    public PageList find(final List<Criterion> criterions, final List<Order> orders, final int start, final int limit) {
        return this.superDao.findPageList(LotteryOpenCode.class, criterions, orders, start, limit);
    }
    
    @Override
    public LotteryOpenCode get(final String lottery, final String expect) {
        final String hql = "from " + this.tab + " where lottery = ?0 and expect = ?1";
        final Object[] values = { lottery, expect };
        final List<LotteryOpenCode> list = this.superDao.list(hql, values);
        if (list == null || list.size() <= 0) {
            return null;
        }
        return list.get(0);
    }
    
    @Override
    public boolean add(final LotteryOpenCode entity) {
        return this.superDao.save(entity);
    }
    
    @Override
    public List<LotteryOpenCode> list(final String lottery, final String[] expects) {
        final List<Criterion> criterions = new ArrayList<Criterion>();
        criterions.add((Criterion)Restrictions.eq("lottery", (Object)lottery));
        criterions.add(Restrictions.in("expect", (Object[])expects));
        final List<Order> orders = new ArrayList<Order>();
        orders.add(Order.asc("expect"));
        return this.superDao.findByCriteria(LotteryOpenCode.class, criterions, orders);
    }
    
    @Override
    public boolean update(final LotteryOpenCode entity) {
        return this.superDao.update(entity);
    }
    
    @Override
    public boolean delete(final LotteryOpenCode entity) {
        final String hql = "delete " + this.tab + " where lottery =?0 and expect=?1";
        final Object[] values = { entity.getLottery(), entity.getExpect() };
        return this.superDao.delete(hql, values);
    }
    
    @Override
    public int countByInterfaceTime(final String lottery, final String startTime, final String endTime) {
        final String hql = "select count(id) from " + this.tab + " where interfaceTime >= ?0 and interfaceTime < ?1 and lottery=?2";
        final Object[] values = { startTime, endTime, lottery };
        final Object result = this.superDao.unique(hql, values);
        return (result != null) ? ((Number)result).intValue() : 0;
    }
    
    @Override
    public LotteryOpenCode getFirstExpectByInterfaceTime(final String lottery, final String startTime, final String endTime) {
        final String hql = "from " + this.tab + " where interfaceTime >= ?0 and interfaceTime < ?1 and lottery=?2 order by expect asc";
        final Object[] values = { startTime, endTime, lottery };
        final List<LotteryOpenCode> list = this.superDao.list(hql, values, 0, 1);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }
    
    @Override
    public List<LotteryOpenCode> getLatest(final String lottery, final int count) {
        final String hql = "from " + this.tab + " where lottery=?0 order by expect desc";
        final Object[] values = { lottery };
        final List<LotteryOpenCode> list = this.superDao.list(hql, values, 0, count);
        return list;
    }
}
