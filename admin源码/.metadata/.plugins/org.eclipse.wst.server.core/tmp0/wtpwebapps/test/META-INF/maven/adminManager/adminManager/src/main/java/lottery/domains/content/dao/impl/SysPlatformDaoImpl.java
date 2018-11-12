package lottery.domains.content.dao.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.entity.SysPlatform;
import javautils.jdbc.hibernate.HibernateSuperDao;
import org.springframework.stereotype.Repository;
import lottery.domains.content.dao.SysPlatformDao;

@Repository
public class SysPlatformDaoImpl implements SysPlatformDao
{
    private final String tab;
    @Autowired
    private HibernateSuperDao<SysPlatform> superDao;
    
    public SysPlatformDaoImpl() {
        this.tab = SysPlatform.class.getSimpleName();
    }
    
    @Override
    public List<SysPlatform> listAll() {
        final String hql = "from " + this.tab;
        return this.superDao.list(hql);
    }
    
    @Override
    public boolean updateStatus(final int id, final int status) {
        final String hql = "update " + this.tab + " set status = ?1 where id = ?0";
        final Object[] values = { id, status };
        return this.superDao.update(hql, values);
    }
}
