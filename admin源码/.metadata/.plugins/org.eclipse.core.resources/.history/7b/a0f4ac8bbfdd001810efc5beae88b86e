package lottery.domains.content.biz.impl;

import lottery.domains.content.global.DbServerSyncEnum;
import lottery.domains.content.entity.UserBetsHitRanking;
import java.util.List;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.ArrayList;
import javautils.jdbc.PageList;
import lottery.domains.content.dao.DbServerSyncDao;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.dao.UserBetsHitRankingDao;
import org.springframework.stereotype.Service;
import lottery.domains.content.biz.UserBetsHitRankingService;

@Service
public class UserBetsHitRankingServiceImpl implements UserBetsHitRankingService
{
    @Autowired
    private UserBetsHitRankingDao uBetsHitRankingDao;
    @Autowired
    private DbServerSyncDao dbServerSyncDao;
    
    @Override
    public PageList search(final int start, final int limit) {
        final List<Criterion> criterions = new ArrayList<Criterion>();
        final List<Order> orders = new ArrayList<Order>();
        orders.add(Order.desc("prizeMoney"));
        orders.add(Order.desc("time"));
        return this.uBetsHitRankingDao.find(criterions, orders, start, limit);
    }
    
    @Override
    public boolean add(final String name, final String username, final int prizeMoney, final String time, final String code, final String type, final int platform) {
        final UserBetsHitRanking entity = new UserBetsHitRanking(name, username, prizeMoney, time, code, type, platform);
        final boolean added = this.uBetsHitRankingDao.add(entity);
        if (added) {
            this.dbServerSyncDao.update(DbServerSyncEnum.HIT_RANKING);
        }
        return added;
    }
    
    @Override
    public boolean edit(final int id, final String name, final String username, final int prizeMoney, final String time, final String code, final String type, final int platform) {
        final UserBetsHitRanking entity = this.uBetsHitRankingDao.getById(id);
        if (entity != null) {
            entity.setName(name);
            entity.setUsername(username);
            entity.setPrizeMoney(prizeMoney);
            entity.setTime(time);
            entity.setCode(code);
            entity.setType(type);
            entity.setPlatform(platform);
            final boolean updated = this.uBetsHitRankingDao.update(entity);
            if (updated) {
                this.dbServerSyncDao.update(DbServerSyncEnum.HIT_RANKING);
            }
            return updated;
        }
        return false;
    }
    
    @Override
    public boolean delete(final int id) {
        final boolean deleted = this.uBetsHitRankingDao.delete(id);
        if (deleted) {
            this.dbServerSyncDao.update(DbServerSyncEnum.HIT_RANKING);
        }
        return deleted;
    }
    
    @Override
    public UserBetsHitRanking getById(final int id) {
        return this.uBetsHitRankingDao.getById(id);
    }
}
