package lottery.domains.content.dao.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.entity.Lottery;
import javautils.jdbc.hibernate.HibernateSuperDao;
import org.springframework.stereotype.Repository;
import lottery.domains.content.dao.LotteryDao;

@Repository
public class LotteryDaoImpl implements LotteryDao
{
    private final String tab;
    @Autowired
    private HibernateSuperDao<Lottery> superDao;
    
    public LotteryDaoImpl() {
        this.tab = Lottery.class.getSimpleName();
    }
    
    @Override
    public List<Lottery> listAll() {
        final String hql = "from " + this.tab + " order by sort";
        return this.superDao.list(hql);
    }
    
    @Override
    public boolean updateStatus(final int id, final int status) {
        final String hql = "update " + this.tab + " set status = ?1 where id = ?0";
        final Object[] values = { id, status };
        return this.superDao.update(hql, values);
    }
    
    @Override
    public boolean updateTimes(final int id, final int times) {
        final String hql = "update " + this.tab + " set times = ?1 where id = ?0";
        final Object[] values = { id, times };
        return this.superDao.update(hql, values);
    }
    
    @Override
    public Lottery getByName(final String name) {
        final String hql = "from " + this.tab + "  where showName = ?0";
        final Object[] values = { name };
        final List<Lottery> list = this.superDao.list(hql, values);
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }
    
    @Override
    public Lottery getByShortName(final String name) {
        final String hql = "from " + this.tab + "  where shortName = ?0";
        final Object[] values = { name };
        final List<Lottery> list = this.superDao.list(hql, values);
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }
    
    @Override
    public Lottery getById(final int id) {
        final String hql = "from " + this.tab + "  where id = ?0";
        final Object[] values = { id };
        final List<Lottery> list = this.superDao.list(hql, values);
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }
}
