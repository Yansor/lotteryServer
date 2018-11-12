package lottery.domains.content.dao.impl;

import java.util.Map;
import org.apache.commons.lang.StringUtils;
import javautils.StringUtil;
import java.util.HashMap;
import javautils.jdbc.PageList;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.entity.GameBets;
import javautils.jdbc.hibernate.HibernateSuperDao;
import org.springframework.stereotype.Repository;
import lottery.domains.content.dao.GameBetsDao;

@Repository
public class GameBetsDaoImpl implements GameBetsDao
{
    private final String tab;
    @Autowired
    private HibernateSuperDao<GameBets> superDao;
    
    public GameBetsDaoImpl() {
        this.tab = GameBets.class.getSimpleName();
    }
    
    @Override
    public GameBets getById(final int id) {
        final String hql = "from " + this.tab + " where id = ?0";
        final Object[] values = { id };
        return (GameBets)this.superDao.unique(hql, values);
    }
    
    @Override
    public GameBets get(final int userId, final int platformId, final String betsId, final String gameCode) {
        final String hql = "from " + this.tab + " where userId = ?0 and platformId=?1 and betsId=?2 and gameCode=?3";
        final Object[] values = { userId, platformId, betsId, gameCode };
        return (GameBets)this.superDao.unique(hql, values);
    }
    
    @Override
    public boolean save(final GameBets gameBets) {
        return this.superDao.save(gameBets);
    }
    
    @Override
    public boolean update(final GameBets gameBets) {
        return this.superDao.update(gameBets);
    }
    
    @Override
    public PageList find(final List<Criterion> criterions, final List<Order> orders, final int start, final int limit) {
        final String propertyName = "id";
        return this.superDao.findPageList(GameBets.class, propertyName, criterions, orders, start, limit);
    }
    
    @Override
    public double[] getTotalMoney(final String keyword, final Integer userId, final Integer platformId, final String minTime, final String maxTime, final Double minMoney, final Double maxMoney, final Double minPrizeMoney, final Double maxPrizeMoney, final String gameCode, final String gameType, final String gameName) {
        String hql = "select sum(money+progressiveMoney), sum(prizeMoney+progressivePrize) from " + this.tab + " where 1=1";
        final Map<String, Object> params = new HashMap<String, Object>();
        if (StringUtil.isNotNull(keyword)) {
            if (StringUtil.isInteger(keyword)) {
                hql = String.valueOf(hql) + " and (id = :id or betsId like :betsId)";
                params.put("id", Integer.valueOf(keyword));
                params.put("betsId", "%" + keyword + "%");
            }
            else {
                hql = String.valueOf(hql) + " and (betsId like :betsId)";
                params.put("betsId", "%" + keyword + "%");
            }
        }
        if (userId != null) {
            hql = String.valueOf(hql) + " and userId = :userId";
            params.put("userId", userId);
        }
        if (platformId != null) {
            hql = String.valueOf(hql) + " and platformId = :platformId";
            params.put("platformId", platformId);
        }
        if (StringUtil.isNotNull(minTime)) {
            hql = String.valueOf(hql) + " and time > :minTime";
            params.put("minTime", minTime);
        }
        if (StringUtil.isNotNull(maxTime)) {
            hql = String.valueOf(hql) + " and time < :maxTime";
            params.put("maxTime", maxTime);
        }
        if (minMoney != null) {
            hql = String.valueOf(hql) + " and money >= :minMoney";
            params.put("minMoney", minMoney);
        }
        if (maxMoney != null) {
            hql = String.valueOf(hql) + " and money <= :maxMoney";
            params.put("maxMoney", maxMoney);
        }
        if (minPrizeMoney != null) {
            hql = String.valueOf(hql) + " and prizeMoney >= :minPrizeMoney";
            params.put("minPrizeMoney", minPrizeMoney);
        }
        if (maxPrizeMoney != null) {
            hql = String.valueOf(hql) + " and prizeMoney <= :maxPrizeMoney";
            params.put("maxPrizeMoney", maxPrizeMoney);
        }
        if (StringUtils.isNotEmpty(gameCode)) {
            hql = String.valueOf(hql) + " and gameCode like :gameCode";
            params.put("gameCode", "%" + gameCode + "%");
        }
        if (StringUtils.isNotEmpty(gameType)) {
            hql = String.valueOf(hql) + " and gameType like :gameType";
            params.put("gameType", "%" + gameType + "%");
        }
        if (StringUtils.isNotEmpty(gameName)) {
            hql = String.valueOf(hql) + " and gameName like :gameName";
            params.put("gameName", "%" + gameName + "%");
        }
        final Object result = this.superDao.uniqueWithParams(hql, params);
        if (result == null) {
            return new double[] { 0.0, 0.0 };
        }
        final Object[] results = (Object[])result;
        final double totalMoney = (double)((results[0] == null) ? 0.0 : results[0]);
        final double totalPrizeMoney = (double)((results[1] == null) ? 0.0 : results[1]);
        return new double[] { totalMoney, totalPrizeMoney };
    }
    
    @Override
    public double getBillingOrder(final int userId, final String startTime, final String endTime) {
        final String hql = "select sum(money) from " + this.tab + " where userId = ?0 and time >= ?1 and time < ?2";
        final Object[] values = { userId, startTime, endTime };
        final Object result = this.superDao.unique(hql, values);
        return (result != null) ? ((Number)result).doubleValue() : 0.0;
    }
}
