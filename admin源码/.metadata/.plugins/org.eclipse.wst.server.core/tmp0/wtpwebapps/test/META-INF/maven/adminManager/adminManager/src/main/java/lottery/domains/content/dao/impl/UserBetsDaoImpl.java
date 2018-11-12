package lottery.domains.content.dao.impl;

import org.apache.commons.lang.StringUtils;
import javautils.StringUtil;
import javautils.array.ArrayUtils;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import org.springframework.transaction.annotation.Transactional;
import java.util.Map;
import java.util.HashMap;
import javautils.jdbc.PageList;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import lottery.domains.content.entity.User;
import lottery.domains.content.entity.HistoryUserBetsNoCode;
import lottery.domains.content.entity.HistoryUserBets;
import lottery.domains.content.entity.UserBetsNoCode;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.entity.UserBets;
import javautils.jdbc.hibernate.HibernateSuperDao;
import org.springframework.stereotype.Repository;
import lottery.domains.content.dao.UserBetsDao;

@Repository
public class UserBetsDaoImpl implements UserBetsDao
{
    private final String tab;
    private final String utab;
    private final String historyTab;
    private final String noCodeTab;
    private final String historyNoCodeTab;
    @Autowired
    private HibernateSuperDao<UserBets> superDao;
    @Autowired
    private HibernateSuperDao<UserBetsNoCode> noCodeSuperDao;
    @Autowired
    private HibernateSuperDao<HistoryUserBets> historySuperDao;
    @Autowired
    private HibernateSuperDao<HistoryUserBetsNoCode> historyNoCodeSuperDao;
    
    public UserBetsDaoImpl() {
        this.tab = UserBets.class.getSimpleName();
        this.utab = User.class.getSimpleName();
        this.historyTab = HistoryUserBets.class.getSimpleName();
        this.noCodeTab = UserBetsNoCode.class.getSimpleName();
        this.historyNoCodeTab = HistoryUserBetsNoCode.class.getSimpleName();
    }
    
    @Override
    public boolean updateStatus(final int id, final int status, final String codes, final String openCode, final double prizeMoney, final String prizeTime) {
        final String hql = "update " + this.tab + " set status = ?1, codes = ?2, openCode = ?3, prizeMoney = ?4, prizeTime = ?5, locked = 0 where id = ?0";
        final Object[] values = { id, status, codes, openCode, prizeMoney, prizeTime };
        return this.superDao.update(hql, values);
    }
    
    @Override
    public boolean updateLocked(final int id, final int locked) {
        final String hql = "update " + this.tab + " set locked = ?1 where id = ?0";
        final Object[] values = { id, locked };
        return this.superDao.update(hql, values);
    }
    
    @Override
    public UserBets getById(final int id) {
        final String hql = "from " + this.tab + " where id = ?0";
        final Object[] values = { id };
        return (UserBets)this.superDao.unique(hql, values);
    }
    
    @Override
    public HistoryUserBets getHistoryById(final int id) {
        final String hql = "from " + this.historyTab + " where id = ?0";
        final Object[] values = { id };
        return (HistoryUserBets)this.historySuperDao.unique(hql, values);
    }
    
    @Override
    public List<UserBets> getByBillno(final String billno, final boolean withCodes) {
        final String targetTab = withCodes ? this.tab : this.noCodeTab;
        final String hql = "from " + targetTab + " where billno = ?0";
        final Object[] values = { billno };
        if (withCodes) {
            return this.superDao.list(hql, values);
        }
        final List<UserBetsNoCode> noCodeList = this.noCodeSuperDao.list(hql, values);
        final List<UserBets> list = new ArrayList<UserBets>();
        for (final UserBetsNoCode tmpBean : noCodeList) {
            list.add(tmpBean.formatBean());
        }
        return list;
    }
    
    @Override
    public List<HistoryUserBets> getHistoryByBillno(final String billno, final boolean withCodes) {
        final String targetTab = withCodes ? this.historyTab : this.historyNoCodeTab;
        final String hql = "from " + targetTab + " where billno = ?0";
        final Object[] values = { billno };
        if (withCodes) {
            return this.historySuperDao.list(hql, values);
        }
        final List<HistoryUserBetsNoCode> noCodeList = this.historyNoCodeSuperDao.list(hql, values);
        final List<HistoryUserBets> list = new ArrayList<HistoryUserBets>();
        for (final HistoryUserBetsNoCode tmpBean : noCodeList) {
            list.add(tmpBean.formatBean());
        }
        return list;
    }
    
    @Override
    public UserBets getBybillno(final int userId, final String billno) {
        final String hql = "from " + this.tab + " where userId = ?0 and billno = ?1";
        final Object[] values = { userId, billno };
        return (UserBets)this.superDao.unique(hql, values);
    }
    
    @Override
    public boolean cancel(final int id) {
        final String hql = "update " + this.tab + " set status = -1 where id = ?0 and status = 0";
        final Object[] values = { id };
        return this.superDao.update(hql, values);
    }
    
    @Override
    public boolean delete(final int userId) {
        final String hql = "delete from " + this.tab + " where userId = ?0";
        final Object[] values = { userId };
        return this.superDao.delete(hql, values);
    }
    
    @Override
    public boolean isCost(final int userId) {
        final String hql = "select count(id) from " + this.tab + " where userId = ?0 and status > 0";
        final Object[] values = { userId };
        final Object result = this.superDao.unique(hql, values);
        return result != null && ((Number)result).intValue() > 0;
    }
    
    @Override
    public List<UserBets> getSuspiciousOrder(final int userId, final int multiple, final boolean withCodes) {
        final String targetTab = withCodes ? this.tab : this.noCodeTab;
        final String hql = "from " + targetTab + " where userId = ?0 and status > 0 and prizeMoney >= money * ?1";
        final Object[] values = { userId, multiple * 1.0 };
        if (withCodes) {
            return this.superDao.list(hql, values);
        }
        final List<UserBetsNoCode> noCodeList = this.noCodeSuperDao.list(hql, values);
        final List<UserBets> list = new ArrayList<UserBets>();
        for (final UserBetsNoCode tmpBean : noCodeList) {
            list.add(tmpBean.formatBean());
        }
        return list;
    }
    
    @Override
    public List<UserBets> getByFollowBillno(final String followBillno, final boolean withCodes) {
        final String targetTab = withCodes ? this.tab : this.noCodeTab;
        final String hql = "from " + targetTab + " where type = 0 and status > 0 and planBillno = ?0";
        final Object[] values = { followBillno };
        if (withCodes) {
            return this.superDao.list(hql, values);
        }
        final List<UserBetsNoCode> noCodeList = this.noCodeSuperDao.list(hql, values);
        final List<UserBets> list = new ArrayList<UserBets>();
        for (final UserBetsNoCode tmpBean : noCodeList) {
            list.add(tmpBean.formatBean());
        }
        return list;
    }
    
    @Transactional(readOnly = true)
    @Override
    public PageList find(final String sql, final int start, final int limit, final boolean withCodes) {
        final String hsql = "select b.* from user_bets b, user u where b.user_id = u.id  ";
        if (withCodes) {
            final PageList pageList = this.superDao.findPageEntityList(String.valueOf(hsql) + sql, UserBets.class, new HashMap<String, Object>(), start, limit);
            return pageList;
        }
        final PageList pageList = this.noCodeSuperDao.findPageEntityList(String.valueOf(hsql) + sql, UserBetsNoCode.class, new HashMap<String, Object>(), start, limit);
        final List<UserBets> list = new ArrayList<UserBets>();
        for (final Object o : pageList.getList()) {
            final UserBetsNoCode entity = (UserBetsNoCode)o;
            list.add(entity.formatBean());
        }
        pageList.setList(list);
        return pageList;
    }
    
    @Override
    public List<UserBets> find(final List<Criterion> criterions, final List<Order> orders, final boolean withCodes) {
        if (withCodes) {
            return this.superDao.findByCriteria(UserBets.class, criterions, orders);
        }
        final List<UserBetsNoCode> noCodeList = this.noCodeSuperDao.findByCriteria(UserBetsNoCode.class, criterions, orders);
        final List<UserBets> list = new ArrayList<UserBets>();
        for (final UserBetsNoCode tmpBean : noCodeList) {
            list.add(tmpBean.formatBean());
        }
        return list;
    }
    
    @Override
    public long getTotalBetsMoney(final String sTime, final String eTime) {
        final String hql = "select sum(b.money) from " + this.tab + "   b  ," + this.utab + "   u   where  u.id = b.userId   and  b.status >= 0 and b.time >= ?0 and b.time < ?1 and u.upid !=?2";
        final Object[] values = { sTime, eTime, 0 };
        final Object result = this.superDao.unique(hql, values);
        if (result != null) {
            return ((Number)result).longValue();
        }
        return 0L;
    }
    
    @Override
    public int getTotalOrderCount(final String sTime, final String eTime) {
        final String hql = "select count(b.id) from  " + this.tab + "   b  ," + this.utab + "   u   where  u.id = b.userId and b.status >= 0 and b.time >= ?0 and b.time < ?1 and u.upid !=?2";
        final Object[] values = { sTime, eTime, 0 };
        final Object result = this.superDao.unique(hql, values);
        if (result != null) {
            return ((Number)result).intValue();
        }
        return 0;
    }
    
    @Override
    public List<?> getDayUserBets(final int[] lids, final String sTime, final String eTime) {
        String hql = "select substring(b.time, 1, 10), count(b.id) from " + this.tab + "   b  ," + this.utab + "   u   where  u.id = b.userId  and  b.status >= 0 and b.time >= ?0 and b.time < ?1 and u.upid !=?2";
        if (lids != null && lids.length > 0) {
            final String ids = ArrayUtils.transInIds(lids);
            hql = String.valueOf(hql) + " and b.lotteryId in (" + ids + ")";
        }
        hql = String.valueOf(hql) + " group by substring(b.time, 1, 10)";
        final Object[] values = { sTime, eTime, 0 };
        return this.superDao.listObject(hql, values);
    }
    
    @Override
    public List<?> getDayBetsMoney(final int[] lids, final String sTime, final String eTime) {
        String hql = "select substring(b.time, 1, 10), sum(b.money) from " + this.tab + "   b  ," + this.utab + "   u   where  u.id = b.userId  and b.status >= 0 and b.time >= ?0 and b.time < ?1  and u.upid !=?2";
        if (lids != null && lids.length > 0) {
            final String ids = ArrayUtils.transInIds(lids);
            hql = String.valueOf(hql) + " and b.lotteryId in (" + ids + ")";
        }
        hql = String.valueOf(hql) + " group by substring(b.time, 1, 10)";
        final Object[] values = { sTime, eTime, 0 };
        return this.superDao.listObject(hql, values);
    }
    
    @Override
    public List<?> getDayPrizeMoney(final int[] lids, final String sTime, final String eTime) {
        String hql = "select substring(b.time, 1, 10), sum(b.prizeMoney) from " + this.tab + "   b  ," + this.utab + "   u   where  u.id = b.userId  and  b.status = 2 and b.time >= ?0 and b.time < ?1 and u.upid !=?2";
        if (lids != null && lids.length > 0) {
            final String ids = ArrayUtils.transInIds(lids);
            hql = String.valueOf(hql) + " and b.lotteryId in (" + ids + ")";
        }
        hql = String.valueOf(hql) + " group by substring(b.time, 1, 10)";
        final Object[] values = { sTime, eTime, 0 };
        return this.superDao.listObject(hql, values);
    }
    
    @Override
    public List<?> getLotteryHot(final int[] lids, final String sTime, final String eTime) {
        String hql = "select lotteryId, count(id) from " + this.tab + " where time >= ?0 and time < ?1";
        if (lids != null && lids.length > 0) {
            final String ids = ArrayUtils.transInIds(lids);
            hql = String.valueOf(hql) + " and lotteryId in (" + ids + ")";
        }
        hql = String.valueOf(hql) + " group by lotteryId";
        final Object[] values = { sTime, eTime };
        return this.superDao.listObject(hql, values);
    }
    
    @Override
    public List<?> report(final List<Integer> lids, final Integer ruleId, final String sTime, final String eTime) {
        String hql = "select substring(b.prizeTime, 1, 10), sum(b.money), sum(((13.0-(b.point*20+b.code-1700)/20))*b.money)/100, sum(b.prizeMoney) from " + this.tab + "    b  ," + this.utab + "   u   where  u.id = b.userId where b.status > 0 and u.upid !=?2";
        if (lids != null && lids.size() > 0) {
            hql = String.valueOf(hql) + " and b.lotteryId in (" + ArrayUtils.transInIds(lids) + ")";
        }
        if (ruleId != null) {
            hql = String.valueOf(hql) + " and b.ruleId = '" + ruleId + "'";
        }
        hql = String.valueOf(hql) + " and b.prizeTime >= ?0 and b.prizeTime < ?1 group by substring(b.prizeTime, 1, 10)";
        final Object[] values = { sTime, eTime, 0 };
        return this.superDao.listObject(hql, values);
    }
    
    @Override
    public int countUserOnline(final String time) {
        final String hql = "select count(*) as ucount from (select id from  user_bets where time > '" + time + "'  GROUP BY user_id ) as ubet;";
        final Object result = this.superDao.uniqueWithSqlCount(hql);
        return (result != null) ? ((Number)result).intValue() : 0;
    }
    
    @Override
    public double[] getTotalMoney(final String keyword, final Integer userId, final Integer utype, final Integer type, final Integer lotteryId, final String expect, final Integer ruleId, final String minTime, final String maxTime, final String minPrizeTime, final String maxPrizeTime, final Double minMoney, final Double maxMoney, final Integer minMultiple, final Integer maxMultiple, final Double minPrizeMoney, final Double maxPrizeMoney, final Integer status, final Integer locked, final String ip) {
        String hql = "select sum(b.money), sum(b.prizeMoney) from " + this.tab + " b , " + this.utab + "  u  where b.userId = u.id ";
        final Map<String, Object> params = new HashMap<String, Object>();
        if (StringUtil.isNotNull(keyword)) {
            if (StringUtil.isInteger(keyword)) {
                hql = String.valueOf(hql) + " and (b.id = :id  or b.billno like :billno or b.chaseBillno like :chaseBillno)";
                params.put("id", Integer.valueOf(keyword));
                params.put("billno", "%" + keyword + "%");
                params.put("chaseBillno", "%" + keyword + "%");
            }
            else {
                hql = String.valueOf(hql) + " and (b.billno like :billno or b.chaseBillno like :chaseBillno)";
                params.put("billno", "%" + keyword + "%");
                params.put("chaseBillno", "%" + keyword + "%");
            }
        }
        if (userId != null) {
            hql = String.valueOf(hql) + " and b.userId = :userId";
            params.put("userId", userId);
        }
        if (type != null) {
            hql = String.valueOf(hql) + " and b.type = :type";
            params.put("type", type);
        }
        if (utype != null) {
            hql = String.valueOf(hql) + " and u.type = :utype";
            params.put("utype", utype);
        }
        else {
            hql = String.valueOf(hql) + " and u.upid != 0";
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
        if (locked != null) {
            hql = String.valueOf(hql) + " and b.locked = :locked";
            params.put("locked", locked);
        }
        if (StringUtils.isNotEmpty(ip)) {
            hql = String.valueOf(hql) + " and b.ip = :ip";
            params.put("ip", ip);
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
    public double[] getHistoryTotalMoney(final String keyword, final Integer userId, final Integer utype, final Integer type, final Integer lotteryId, final String expect, final Integer ruleId, final String minTime, final String maxTime, final String minPrizeTime, final String maxPrizeTime, final Double minMoney, final Double maxMoney, final Integer minMultiple, final Integer maxMultiple, final Double minPrizeMoney, final Double maxPrizeMoney, final Integer status, final Integer locked) {
        String hql = "select sum(b.money), sum(b.prizeMoney) from " + this.historyTab + " b , " + this.utab + "  u  where b.userId = u.id ";
        final Map<String, Object> params = new HashMap<String, Object>();
        if (StringUtil.isNotNull(keyword)) {
            if (StringUtil.isInteger(keyword)) {
                hql = String.valueOf(hql) + " and (b.id = :id  or b.billno like :billno or b.chaseBillno like :chaseBillno)";
                params.put("id", Integer.valueOf(keyword));
                params.put("billno", "%" + keyword + "%");
                params.put("chaseBillno", "%" + keyword + "%");
            }
            else {
                hql = String.valueOf(hql) + " and (b.billno like :billno or b.chaseBillno like :chaseBillno)";
                params.put("billno", "%" + keyword + "%");
                params.put("chaseBillno", "%" + keyword + "%");
            }
        }
        if (userId != null) {
            hql = String.valueOf(hql) + " and b.userId = :userId";
            params.put("userId", userId);
        }
        if (type != null) {
            hql = String.valueOf(hql) + " and b.type = :type";
            params.put("type", type);
        }
        if (utype != null) {
            hql = String.valueOf(hql) + " and u.type = :utype";
            params.put("utype", utype);
        }
        else {
            hql = String.valueOf(hql) + " and u.upid !=0";
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
            hql = String.valueOf(hql) + " and b.time < :maxTime";
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
        if (locked != null) {
            hql = String.valueOf(hql) + " and b.locked = :locked";
            params.put("locked", locked);
        }
        final Object result = this.historySuperDao.uniqueWithParams(hql, params);
        if (result == null) {
            return new double[] { 0.0, 0.0 };
        }
        final Object[] results = (Object[])result;
        final double totalMoney = (double)((results[0] == null) ? 0.0 : results[0]);
        final double totalPrizeMoney = (double)((results[1] == null) ? 0.0 : results[1]);
        return new double[] { totalMoney, totalPrizeMoney };
    }
    
    @Override
    public PageList findHistory(final List<Criterion> criterions, final List<Order> orders, final int start, final int limit, final boolean withCodes) {
        final String propertyName = "id";
        if (withCodes) {
            return this.historySuperDao.findPageList(HistoryUserBets.class, propertyName, criterions, orders, start, limit);
        }
        final PageList pageList = this.historyNoCodeSuperDao.findPageList(HistoryUserBetsNoCode.class, propertyName, criterions, orders, start, limit);
        final List<HistoryUserBets> list = new ArrayList<HistoryUserBets>();
        for (final Object o : pageList.getList()) {
            final HistoryUserBetsNoCode entity = (HistoryUserBetsNoCode)o;
            list.add(entity.formatBean());
        }
        pageList.setList(list);
        return pageList;
    }
    
    @Override
    public double getBillingOrder(final int userId, final String startTime, final String endTime) {
        final String hql = "select sum(money) from " + this.tab + " where userId = ?0 and prizeTime >= ?1 and prizeTime < ?2 and status > 0 and id > 0";
        final Object[] values = { userId, startTime, endTime };
        final Object result = this.superDao.unique(hql, values);
        return (result != null) ? ((Number)result).doubleValue() : 0.0;
    }
}
