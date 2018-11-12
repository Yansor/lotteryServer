package lottery.domains.content.dao.impl;

import javautils.array.ArrayUtils;
import java.util.Collection;
import org.apache.commons.collections.CollectionUtils;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.entity.LotteryPlayRulesConfig;
import javautils.jdbc.hibernate.HibernateSuperDao;
import org.springframework.stereotype.Repository;
import lottery.domains.content.dao.LotteryPlayRulesConfigDao;

@Repository
public class LotteryPlayRulesConfigDaoImpl implements LotteryPlayRulesConfigDao
{
    private final String tab;
    @Autowired
    private HibernateSuperDao<LotteryPlayRulesConfig> superDao;
    
    public LotteryPlayRulesConfigDaoImpl() {
        this.tab = LotteryPlayRulesConfig.class.getSimpleName();
    }
    
    @Override
    public List<LotteryPlayRulesConfig> listAll() {
        final String hql = "from " + this.tab + " order by id";
        return this.superDao.list(hql);
    }
    
    @Override
    public List<LotteryPlayRulesConfig> listByLottery(final int lotteryId) {
        final String hql = "from " + this.tab + " where lotteryId = ?0 order by id";
        final Object[] values = { lotteryId };
        return this.superDao.list(hql, values);
    }
    
    @Override
    public List<LotteryPlayRulesConfig> listByLotteryAndRule(final int lotteryId, final List<Integer> ruleIds) {
        if (CollectionUtils.isEmpty((Collection)ruleIds)) {
            final String hql = "from " + this.tab + " where lotteryId = ?0 order by id";
            final Object[] values = { lotteryId };
            return this.superDao.list(hql, values);
        }
        final String hql = "from " + this.tab + " where lotteryId = ?0 and ruleId in (" + ArrayUtils.transInIds(ruleIds) + ") order by id";
        final Object[] values = { lotteryId };
        return this.superDao.list(hql, values);
    }
    
    @Override
    public LotteryPlayRulesConfig get(final int lotteryId, final int ruleId) {
        final String hql = "from " + this.tab + " where lotteryId = ?0 and ruleId = ?1";
        final Object[] values = { lotteryId, ruleId };
        return (LotteryPlayRulesConfig)this.superDao.unique(hql, values);
    }
    
    @Override
    public boolean save(final LotteryPlayRulesConfig entity) {
        return this.superDao.save(entity);
    }
    
    @Override
    public boolean update(final LotteryPlayRulesConfig entity) {
        return this.superDao.update(entity);
    }
    
    @Override
    public boolean updateStatus(final int lotteryId, final int ruleId, final int status) {
        final String hql = "update from " + this.tab + " set status = ?0 where lotteryId = ?1 and ruleId = ?2";
        final Object[] values = { status, lotteryId, ruleId };
        return this.superDao.update(hql, values);
    }
    
    @Override
    public boolean updateStatus(final int ruleId, final int status) {
        final String hql = "update from " + this.tab + " set status = ?0 where ruleId = ?1";
        final Object[] values = { status, ruleId };
        return this.superDao.update(hql, values);
    }
    
    @Override
    public boolean update(final int id, final String minNum, final String maxNum) {
        final String hql = "update from " + this.tab + " set minNum = ?0, maxNum = ?1 where id = ?2";
        final Object[] values = { minNum, maxNum, id };
        return this.superDao.update(hql, values);
    }
}
