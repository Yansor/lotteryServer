package lottery.domains.content.dao.impl;

import java.util.Map;
import javautils.StringUtil;
import java.util.HashMap;
import javautils.jdbc.PageList;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.List;
import lottery.domains.content.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.entity.UserBetsOriginal;
import javautils.jdbc.hibernate.HibernateSuperDao;
import org.springframework.stereotype.Repository;
import lottery.domains.content.dao.UserBetsOriginalDao;

@Repository
public class UserBetsOriginalDaoImpl implements UserBetsOriginalDao
{
    private final String tab;
    private final String utab;
    @Autowired
    private HibernateSuperDao<UserBetsOriginal> superDao;
    
    public UserBetsOriginalDaoImpl() {
        this.tab = UserBetsOriginal.class.getSimpleName();
        this.utab = User.class.getSimpleName();
    }
    
    @Override
    public UserBetsOriginal getById(final int id) {
        final String hql = "from " + this.tab + " where id = ?0";
        final Object[] values = { id };
        return (UserBetsOriginal)this.superDao.unique(hql, values);
    }
    
    @Override
    public PageList find(final List<Criterion> criterions, final List<Order> orders, final int start, final int limit) {
        final String propertyName = "id";
        return this.superDao.findPageList(UserBetsOriginal.class, propertyName, criterions, orders, start, limit);
    }
    
    @Override
    public double[] getTotalMoney(final String keyword, final Integer userId, final Integer utype, final Integer type, final Integer lotteryId, final String expect, final Integer ruleId, final String minTime, final String maxTime, final String minPrizeTime, final String maxPrizeTime, final Double minMoney, final Double maxMoney, final Integer minMultiple, final Integer maxMultiple, final Double minPrizeMoney, final Double maxPrizeMoney, final Integer status) {
        String hql = "select sum(b.money), sum(b.prizeMoney) from " + this.tab + " b , " + this.utab + "  u  where b.userId = u.id ";
        final Map<String, Object> params = new HashMap<String, Object>();
        if (StringUtil.isNotNull(keyword)) {
            if (StringUtil.isInteger(keyword)) {
                hql = String.valueOf(hql) + " and (b.id = :id  or b.billno like :billno or b.chaseBillno like :chaseBillno)";
                params.put("b.id", Integer.valueOf(keyword));
                params.put("b.billno", "%" + keyword + "%");
                params.put("b.chaseBillno", "%" + keyword + "%");
            }
            else {
                hql = String.valueOf(hql) + " and (b.billno like :billno or b.chaseBillno like :chaseBillno)";
                params.put("billno", "%" + keyword + "%");
                params.put("chaseBillno", "%" + keyword + "%");
            }
        }
        if (userId != null) {
            hql = String.valueOf(hql) + " and b.userId = :userId";
            params.put("b.userId", userId);
        }
        if (type != null) {
            hql = String.valueOf(hql) + " and b.type = :type";
            params.put("type", type);
        }
        if (utype != null) {
            hql = String.valueOf(hql) + "  and u.type =" + utype;
        }
        else {
            hql = String.valueOf(hql) + "  and u.upid != 0";
        }
        if (lotteryId != null) {
            hql = String.valueOf(hql) + " and b.lotteryId = :lotteryId";
            params.put("lotteryId", lotteryId);
        }
        if (StringUtil.isNotNull(expect)) {
            hql = String.valueOf(hql) + " and b.expect like :expect";
            params.put("expect", expect);
        }
        if (ruleId != null) {
            hql = String.valueOf(hql) + " and b.ruleId = :ruleId";
            params.put("ruleId", ruleId);
        }
        if (StringUtil.isNotNull(minTime)) {
            hql = String.valueOf(hql) + " and b.time > :minTime";
            params.put("minTime", minTime);
        }
        if (StringUtil.isNotNull(maxTime)) {
            hql = String.valueOf(hql) + " and b. time < :maxTime";
            params.put("maxTime", maxTime);
        }
        if (StringUtil.isNotNull(minPrizeTime)) {
            hql = String.valueOf(hql) + " and b.prizeTime > :minPrizeTime";
            params.put("minPrizeTime", minPrizeTime);
        }
        if (StringUtil.isNotNull(maxPrizeTime)) {
            hql = String.valueOf(hql) + " and b.prizeTime < :maxPrizeTime";
            params.put("maxPrizeTime", maxPrizeTime);
        }
        if (minMoney != null) {
            hql = String.valueOf(hql) + " and b.money >= :minMoney";
            params.put("minMoney", minMoney);
        }
        if (maxMoney != null) {
            hql = String.valueOf(hql) + " and b.money <= :maxMoney";
            params.put("maxMoney", maxMoney);
        }
        if (minMultiple != null) {
            hql = String.valueOf(hql) + " and b.multiple >= :minMultiple";
            params.put("minMultiple", minMultiple);
        }
        if (maxMultiple != null) {
            hql = String.valueOf(hql) + " and b.multiple <= :maxMultiple";
            params.put("maxMultiple", maxMultiple);
        }
        if (minPrizeMoney != null) {
            hql = String.valueOf(hql) + " and b.prizeMoney >= :minPrizeMoney";
            params.put("minPrizeMoney", minPrizeMoney);
        }
        if (maxPrizeMoney != null) {
            hql = String.valueOf(hql) + " and b.prizeMoney <= :maxPrizeMoney";
            params.put("maxPrizeMoney", maxPrizeMoney);
        }
        if (status != null) {
            hql = String.valueOf(hql) + " and b.status = :status";
            params.put("status", status);
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
    public PageList find(final String sql, final int start, final int limit) {
        final String hsql = "select b.* from user_bets_original b, user u where b.user_id = u.id  ";
        final PageList pageList = this.superDao.findPageEntityList(String.valueOf(hsql) + sql, UserBetsOriginal.class, new HashMap<String, Object>(), start, limit);
        return pageList;
    }
}
