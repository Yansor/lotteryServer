package lottery.domains.content.dao.impl;

import javautils.date.Moment;
import java.util.List;
import lottery.domains.content.global.DbServerSyncEnum;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.entity.DbServerSync;
import javautils.jdbc.hibernate.HibernateSuperDao;
import org.springframework.stereotype.Repository;
import lottery.domains.content.dao.DbServerSyncDao;

@Repository
public class DbServerSyncDaoImpl implements DbServerSyncDao
{
    private final String tab;
    @Autowired
    private HibernateSuperDao<DbServerSync> superDao;
    
    public DbServerSyncDaoImpl() {
        this.tab = DbServerSync.class.getSimpleName();
    }
    
    public static void main(final String[] args) {
        System.out.println(DbServerSyncEnum.LOTTERY.name());
    }
    
    @Override
    public List<DbServerSync> listAll() {
        final String hql = "from " + this.tab;
        return this.superDao.list(hql);
    }
    
    @Override
    public boolean update(final DbServerSyncEnum type) {
        final String key = type.name();
        final String time = new Moment().toSimpleTime();
        final String hql = "update " + this.tab + " set lastModTime = ?1 where key = ?0";
        final Object[] values = { key, time };
        return this.superDao.update(hql, values);
    }
    
    @Override
    public boolean update(final String key, final String lastModTime) {
        final String hql = "update " + this.tab + " set lastModTime = ?1 where key = ?0";
        final Object[] values = { key, lastModTime };
        return this.superDao.update(hql, values);
    }
    
    @Override
    public DbServerSync getByKey(final String key) {
        final String hql = "from " + this.tab + " where key=?0";
        final Object[] values = { key };
        return (DbServerSync)this.superDao.unique(hql, values);
    }
    
    @Override
    public boolean save(final DbServerSync dbServerSync) {
        return this.superDao.save(dbServerSync);
    }
}
