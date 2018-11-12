package lottery.domains.content.dao.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.entity.LotteryCrawlerStatus;
import javautils.jdbc.hibernate.HibernateSuperDao;
import org.springframework.stereotype.Repository;
import lottery.domains.content.dao.LotteryCrawlerStatusDao;

@Repository
public class LotteryCrawlerStatusDaoImpl implements LotteryCrawlerStatusDao
{
    private final String tab;
    @Autowired
    private HibernateSuperDao<LotteryCrawlerStatus> superDao;
    
    public LotteryCrawlerStatusDaoImpl() {
        this.tab = LotteryCrawlerStatus.class.getSimpleName();
    }
    
    @Override
    public List<LotteryCrawlerStatus> listAll() {
        final String hql = "from " + this.tab;
        return this.superDao.list(hql);
    }
    
    @Override
    public LotteryCrawlerStatus get(final String shortName) {
        final String hql = "from " + this.tab + " where shortName = ?0";
        final Object[] values = { shortName };
        return (LotteryCrawlerStatus)this.superDao.unique(hql, values);
    }
    
    @Override
    public boolean update(final LotteryCrawlerStatus entity) {
        return this.superDao.update(entity);
    }
}
