package lottery.domains.content.dao.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.entity.LotteryPlayRulesGroup;
import javautils.jdbc.hibernate.HibernateSuperDao;
import org.springframework.stereotype.Repository;
import lottery.domains.content.dao.LotteryPlayRulesGroupDao;

@Repository
public class LotteryPlayRulesGroupDaoImpl implements LotteryPlayRulesGroupDao
{
    private final String tab;
    @Autowired
    private HibernateSuperDao<LotteryPlayRulesGroup> superDao;
    
    public LotteryPlayRulesGroupDaoImpl() {
        this.tab = LotteryPlayRulesGroup.class.getSimpleName();
    }
    
    @Override
    public List<LotteryPlayRulesGroup> listAll() {
        final String hql = "from " + this.tab + " order by typeId,sort";
        return this.superDao.list(hql);
    }
    
    @Override
    public List<LotteryPlayRulesGroup> listByType(final int typeId) {
        final String hql = "from " + this.tab + " where typeId = ?0 order by typeId,sort";
        final Object[] values = { typeId };
        return this.superDao.list(hql, values);
    }
    
    @Override
    public LotteryPlayRulesGroup getById(final int id) {
        final String hql = "from " + this.tab + " where id = ?0";
        final Object[] values = { id };
        return (LotteryPlayRulesGroup)this.superDao.unique(hql, values);
    }
    
    @Override
    public boolean update(final LotteryPlayRulesGroup entity) {
        return this.superDao.update(entity);
    }
    
    @Override
    public boolean updateStatus(final int id, final int status) {
        final String hql = "update from " + this.tab + " set status = ?1 where id = ?0";
        final Object[] values = { id, status };
        return this.superDao.update(hql, values);
    }
}
