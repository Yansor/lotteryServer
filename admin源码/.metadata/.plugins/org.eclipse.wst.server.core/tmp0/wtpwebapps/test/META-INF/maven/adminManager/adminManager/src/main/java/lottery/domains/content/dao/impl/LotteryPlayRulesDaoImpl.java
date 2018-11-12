package lottery.domains.content.dao.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.entity.LotteryPlayRules;
import javautils.jdbc.hibernate.HibernateSuperDao;
import org.springframework.stereotype.Repository;
import lottery.domains.content.dao.LotteryPlayRulesDao;

@Repository
public class LotteryPlayRulesDaoImpl implements LotteryPlayRulesDao
{
    private final String tab;
    @Autowired
    private HibernateSuperDao<LotteryPlayRules> superDao;
    
    public LotteryPlayRulesDaoImpl() {
        this.tab = LotteryPlayRules.class.getSimpleName();
    }
    
    @Override
    public List<LotteryPlayRules> listAll() {
        final String hql = "from " + this.tab + " order by groupId";
        return this.superDao.list(hql);
    }
    
    @Override
    public List<LotteryPlayRules> listByType(final int typeId) {
        final String hql = "from " + this.tab + " where typeId = ?0 order by groupId";
        final Object[] values = { typeId };
        return this.superDao.list(hql, values);
    }
    
    @Override
    public List<LotteryPlayRules> listByTypeAndGroup(final int typeId, final int groupId) {
        final String hql = "from " + this.tab + " where typeId = ?0 and groupId = ?1 order by groupId";
        final Object[] values = { typeId, groupId };
        return this.superDao.list(hql, values);
    }
    
    @Override
    public LotteryPlayRules getById(final int id) {
        final String hql = "from " + this.tab + " where id = ?0";
        final Object[] values = { id };
        return (LotteryPlayRules)this.superDao.unique(hql, values);
    }
    
    @Override
    public boolean update(final LotteryPlayRules entity) {
        return this.superDao.update(entity);
    }
    
    @Override
    public boolean updateStatus(final int id, final int status) {
        final String hql = "update from " + this.tab + " set status = ?0 where id = ?1";
        final Object[] values = { status, id };
        return this.superDao.update(hql, values);
    }
    
    @Override
    public boolean update(final int id, final String minNum, final String maxNum) {
        final String hql = "update from " + this.tab + " set minNum = ?0, maxNum = ?1 where id = ?2";
        final Object[] values = { minNum, maxNum, id };
        return this.superDao.update(hql, values);
    }
}
