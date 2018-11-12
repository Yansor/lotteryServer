package lottery.domains.content.dao.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.entity.LotteryPlayRulesGroupConfig;
import javautils.jdbc.hibernate.HibernateSuperDao;
import org.springframework.stereotype.Repository;
import lottery.domains.content.dao.LotteryPlayRulesGroupConfigDao;

@Repository
public class LotteryPlayRulesGroupConfigDaoImpl implements LotteryPlayRulesGroupConfigDao
{
    private final String tab;
    @Autowired
    private HibernateSuperDao<LotteryPlayRulesGroupConfig> superDao;
    
    public LotteryPlayRulesGroupConfigDaoImpl() {
        this.tab = LotteryPlayRulesGroupConfig.class.getSimpleName();
    }
    
    @Override
    public List<LotteryPlayRulesGroupConfig> listAll() {
        final String hql = "from " + this.tab + " order by id";
        return this.superDao.list(hql);
    }
    
    @Override
    public List<LotteryPlayRulesGroupConfig> listByLottery(final int lotteryId) {
        final String hql = "from " + this.tab + " where lotteryId = ?0 order by id";
        final Object[] values = { lotteryId };
        return this.superDao.list(hql, values);
    }
    
    @Override
    public LotteryPlayRulesGroupConfig get(final int lotteryId, final int groupId) {
        final String hql = "from " + this.tab + " where lotteryId = ?0 and groupId = ?1";
        final Object[] values = { lotteryId, groupId };
        return (LotteryPlayRulesGroupConfig)this.superDao.unique(hql, values);
    }
    
    @Override
    public boolean save(final LotteryPlayRulesGroupConfig entity) {
        return this.superDao.save(entity);
    }
    
    @Override
    public boolean update(final LotteryPlayRulesGroupConfig entity) {
        return this.superDao.update(entity);
    }
    
    @Override
    public boolean updateStatus(final int lotteryId, final int groupId, final int status) {
        final String hql = "update from " + this.tab + " set status = ?0 where lotteryId = ?1 and groupId = ?2";
        final Object[] values = { status, lotteryId, groupId };
        return this.superDao.update(hql, values);
    }
    
    @Override
    public boolean updateStatus(final int groupId, final int status) {
        final String hql = "update from " + this.tab + " set status = ?0 where groupId = ?1";
        final Object[] values = { status, groupId };
        return this.superDao.update(hql, values);
    }
}
