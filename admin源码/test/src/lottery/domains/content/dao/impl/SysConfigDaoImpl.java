package lottery.domains.content.dao.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.entity.SysConfig;
import javautils.jdbc.hibernate.HibernateSuperDao;
import org.springframework.stereotype.Repository;
import lottery.domains.content.dao.SysConfigDao;

@Repository
public class SysConfigDaoImpl implements SysConfigDao
{
    private final String tab;
    @Autowired
    private HibernateSuperDao<SysConfig> superDao;
    
    public SysConfigDaoImpl() {
        this.tab = SysConfig.class.getSimpleName();
    }
    
    @Override
    public List<SysConfig> listAll() {
        final String hql = "from " + this.tab;
        return this.superDao.list(hql);
    }
    
    @Override
    public SysConfig get(final String group, final String key) {
        final String hql = "from " + this.tab + " where group = ?0 and key = ?1";
        final Object[] values = { group, key };
        return (SysConfig)this.superDao.unique(hql, values);
    }
    
    @Override
    public boolean update(final SysConfig entity) {
        return this.superDao.update(entity);
    }
    
    @Override
    public boolean save(final SysConfig entity) {
        return this.superDao.save(entity);
    }
}
