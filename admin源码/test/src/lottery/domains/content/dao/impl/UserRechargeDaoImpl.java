package lottery.domains.content.dao.impl;

import javautils.StringUtil;
import java.util.Map;
import java.util.HashMap;
import javautils.array.ArrayUtils;
import javautils.jdbc.PageList;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.List;
import lottery.domains.content.entity.User;
import lottery.domains.content.entity.HistoryUserRecharge;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.entity.UserRecharge;
import javautils.jdbc.hibernate.HibernateSuperDao;
import org.springframework.stereotype.Repository;
import lottery.domains.content.dao.UserRechargeDao;

@Repository
public class UserRechargeDaoImpl implements UserRechargeDao
{
    private final String user;
    private final String tab;
    private final String historyTab;
    @Autowired
    private HibernateSuperDao<UserRecharge> superDao;
    @Autowired
    private HibernateSuperDao<HistoryUserRecharge> historySuperDao;
    
    public UserRechargeDaoImpl() {
        this.user = User.class.getSimpleName();
        this.tab = UserRecharge.class.getSimpleName();
        this.historyTab = HistoryUserRecharge.class.getSimpleName();
    }
    
    @Override
    public boolean add(final UserRecharge entity) {
        return this.superDao.save(entity);
    }
    
    @Override
    public boolean update(final UserRecharge entity) {
        return this.superDao.update(entity);
    }
    
    @Override
    public boolean updateSuccess(final int id, final double beforeMoney, final double afterMoney, final double recMoney, final String payTime, final String payBillno) {
        final String hql = "update " + this.tab + " set beforeMoney = ?0,afterMoney = ?1,recMoney=?2,status = 1, payTime=?3, payBillno=?4 where id = ?5";
        final Object[] values = { beforeMoney, afterMoney, recMoney, payTime, payBillno, id };
        return this.superDao.update(hql, values);
    }
    
    @Override
    public UserRecharge getById(final int id) {
        final String hql = "from " + this.tab + " where id = ?0";
        final Object[] values = { id };
        return (UserRecharge)this.superDao.unique(hql, values);
    }
    
    @Override
    public HistoryUserRecharge getHistoryById(final int id) {
        final String hql = "from " + this.historyTab + " where id = ?0";
        final Object[] values = { id };
        return (HistoryUserRecharge)this.historySuperDao.unique(hql, values);
    }
    
    @Override
    public UserRecharge getByBillno(final String billno) {
        final String hql = "from " + this.tab + " where billno = ?0";
        final Object[] values = { billno };
        return (UserRecharge)this.superDao.unique(hql, values);
    }
    
    @Override
    public boolean isRecharge(final int userId) {
        final String hql = "select count(id) from " + this.tab + " where userId = ?0 and status = 1";
        final Object[] values = { userId };
        final Object result = this.superDao.unique(hql, values);
        return result != null && ((Number)result).intValue() > 0;
    }
    
    @Override
    public List<UserRecharge> getLatest(final int userId, final int status, final int count) {
        final String hql = "from " + this.tab + " where userId = ?0 and status = ?1 order by id desc";
        final Object[] values = { userId, status };
        return this.superDao.list(hql, values, 0, count);
    }
    
    @Override
    public List<UserRecharge> list(final List<Criterion> criterions, final List<Order> orders) {
        return this.superDao.findByCriteria(UserRecharge.class, criterions, orders);
    }
    
    @Override
    public PageList findHistory(final List<Criterion> criterions, final List<Order> orders, final int start, final int limit) {
        final String propertyName = "id";
        return this.historySuperDao.findPageList(HistoryUserRecharge.class, propertyName, criterions, orders, start, limit);
    }
    
    @Override
    public List<?> getDayRecharge(final String sTime, final String eTime) {
        final String hql = "select substring(payTime, 1, 10), sum(recMoney) from " + this.tab + " where status = 1 and payTime >= ?0 and payTime < ?1 group by substring(payTime, 1, 10)";
        final Object[] values = { sTime, eTime };
        return this.superDao.listObject(hql, values);
    }
    
    @Override
    public List<?> getDayRecharge2(final String sTime, final String eTime, final Integer type, final Integer subtype) {
        String hql = "select substring(b.payTime, 1, 10), count(b.id), sum(b.recMoney), sum(b.receiveFeeMoney) from " + this.tab + " as b , " + this.user + "  as u   where u.id = b.userId  and u.upid != 0 and  b.status = 1 and b.payTime >= ?0 and b.payTime < ?1";
        Object[] values = null;
        if (type != null && subtype != null) {
            hql = String.valueOf(hql) + " and b.type=?2 and b.subtype=?3";
            values = new Object[] { sTime, eTime, type, subtype };
        }
        else if (type != null && subtype == null) {
            hql = String.valueOf(hql) + " and b.type=?2";
            values = new Object[] { sTime, eTime, type };
        }
        else if (type == null && subtype != null) {
            hql = String.valueOf(hql) + " and b.subtype=?2";
            values = new Object[] { sTime, eTime, subtype };
        }
        else {
            values = new Object[] { sTime, eTime };
        }
        hql = String.valueOf(hql) + " group by substring(b.payTime, 1, 10)";
        return this.superDao.listObject(hql, values);
    }
    
    @Override
    public double getTotalFee(final String sTime, final String eTime) {
        final String hql = "select sum(receiveFeeMoney) from " + this.tab + " where status = 1 and payTime >= ?0 and payTime < ?1";
        final Object[] values = { sTime, eTime };
        final Object result = this.superDao.unique(hql, values);
        return (result != null) ? ((Number)result).doubleValue() : 0.0;
    }
    
    @Override
    public double getThirdTotalRecharge(final String sTime, final String eTime) {
        final String hql = "select sum(recMoney) from " + this.tab + " where status = 1 and payTime >= ?0 and payTime < ?1 and channelId is not null and channelId > 0";
        final Object[] values = { sTime, eTime };
        final Object result = this.superDao.unique(hql, values);
        return (result != null) ? ((Number)result).doubleValue() : 0.0;
    }
    
    @Override
    public double getTotalRecharge(final String sTime, final String eTime, final int[] type, final int[] subtype, final Integer payBankId) {
        String hql = "select sum(recMoney) from " + this.tab + " where status = 1 and payTime >= ?0 and payTime < ?1";
        if (type != null && type.length > 0) {
            hql = String.valueOf(hql) + " and type in (" + ArrayUtils.transInIds(type) + ")";
        }
        if (subtype != null && subtype.length > 0) {
            hql = String.valueOf(hql) + " and subtype in (" + ArrayUtils.transInIds(subtype) + ")";
        }
        if (payBankId != null) {
            hql = String.valueOf(hql) + " and payBankId = " + payBankId;
        }
        final Object[] values = { sTime, eTime };
        final Object result = this.superDao.unique(hql, values);
        return (result != null) ? ((Number)result).doubleValue() : 0.0;
    }
    
    @Override
    public Object[] getTotalRechargeData(final String sTime, final String eTime, final Integer type, final Integer subtype) {
        String hql = "select count(b.id), sum(b.recMoney), sum(b.receiveFeeMoney) from " + this.tab + " as b , " + this.user + " as u  where b.userId = u.id and u.upid != 0 and b.status = 1 and b.payTime >= :sTime and b.payTime < :eTime";
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("sTime", sTime);
        params.put("eTime", eTime);
        if (type != null) {
            hql = String.valueOf(hql) + " and b.type = :type";
            params.put("type", type);
        }
        if (subtype != null) {
            hql = String.valueOf(hql) + " and b.subtype in :subtype";
            params.put("subtype", subtype);
        }
        final Object result = this.superDao.uniqueWithParams(hql, params);
        if (result == null) {
            return new Object[] { 0, 0.0, 0.0 };
        }
        final Object[] results = (Object[])result;
        final Object result2 = (results[0] == null) ? Integer.valueOf(0) : results[0];
        final Object result3 = (results[1] == null) ? Double.valueOf(0.0) : results[1];
        final Object result4 = (results[2] == null) ? Double.valueOf(0.0) : results[2];
        return new Object[] { result2, result3, result4 };
    }
    
    @Override
    public double getTotalRecharge(final String billno, final Integer userId, final String minTime, final String maxTime, final String minPayTime, final String maxPayTime, final Double minMoney, final Double maxMoney, final Integer status, final Integer channelId) {
        String hql = "select sum(recMoney) from " + this.tab + " where 1=1";
        final Map<String, Object> params = new HashMap<String, Object>();
        if (StringUtil.isNotNull(billno)) {
            hql = String.valueOf(hql) + " and billno like :billno";
            params.put("billno", "%" + billno + "%");
        }
        if (userId != null) {
            hql = String.valueOf(hql) + " and userId = :userId";
            params.put("userId", userId);
        }
        if (StringUtil.isNotNull(minTime)) {
            hql = String.valueOf(hql) + " and time > :minTime";
            params.put("minTime", minTime);
        }
        if (StringUtil.isNotNull(maxTime)) {
            hql = String.valueOf(hql) + " and time < :maxTime";
            params.put("maxTime", maxTime);
        }
        if (StringUtil.isNotNull(minPayTime)) {
            hql = String.valueOf(hql) + " and payTime > :minPayTime";
            params.put("minPayTime", minPayTime);
        }
        if (StringUtil.isNotNull(maxPayTime)) {
            hql = String.valueOf(hql) + " and payTime < :maxPayTime";
            params.put("maxPayTime", maxPayTime);
        }
        if (minMoney != null) {
            hql = String.valueOf(hql) + " and money >= :minMoney";
            params.put("minMoney", minMoney);
        }
        if (maxMoney != null) {
            hql = String.valueOf(hql) + " and money <= :maxMoney";
            params.put("maxMoney", maxMoney);
        }
        if (status != null) {
            hql = String.valueOf(hql) + " and status = :status";
            params.put("status", status);
        }
        if (channelId != null) {
            hql = String.valueOf(hql) + " and channelId = :channelId";
            params.put("channelId", channelId);
        }
        final Object result = this.superDao.uniqueWithParams(hql, params);
        return (result != null) ? ((Number)result).doubleValue() : 0.0;
    }
    
    @Override
    public double getHistoryTotalRecharge(final String billno, final Integer userId, final String minTime, final String maxTime, final String minPayTime, final String maxPayTime, final Double minMoney, final Double maxMoney, final Integer status, final Integer channelId) {
        String hql = "select sum(recMoney) from " + this.historyTab + " where 1=1";
        final Map<String, Object> params = new HashMap<String, Object>();
        if (StringUtil.isNotNull(billno)) {
            hql = String.valueOf(hql) + " and billno like :billno";
            params.put("billno", "%" + billno + "%");
        }
        if (userId != null) {
            hql = String.valueOf(hql) + " and userId = :userId";
            params.put("userId", userId);
        }
        if (StringUtil.isNotNull(minTime)) {
            hql = String.valueOf(hql) + " and time > :minTime";
            params.put("minTime", minTime);
        }
        if (StringUtil.isNotNull(maxTime)) {
            hql = String.valueOf(hql) + " and time < :maxTime";
            params.put("maxTime", maxTime);
        }
        if (StringUtil.isNotNull(minPayTime)) {
            hql = String.valueOf(hql) + " and payTime > :minPayTime";
            params.put("minPayTime", minPayTime);
        }
        if (StringUtil.isNotNull(maxPayTime)) {
            hql = String.valueOf(hql) + " and payTime < :maxPayTime";
            params.put("maxPayTime", maxPayTime);
        }
        if (minMoney != null) {
            hql = String.valueOf(hql) + " and money >= :minMoney";
            params.put("minMoney", minMoney);
        }
        if (maxMoney != null) {
            hql = String.valueOf(hql) + " and money <= :maxMoney";
            params.put("maxMoney", maxMoney);
        }
        if (status != null) {
            hql = String.valueOf(hql) + " and status = :status";
            params.put("status", status);
        }
        if (channelId != null) {
            hql = String.valueOf(hql) + " and channelId = :channelId";
            params.put("channelId", channelId);
        }
        final Object result = this.superDao.uniqueWithParams(hql, params);
        return (result != null) ? ((Number)result).doubleValue() : 0.0;
    }
    
    @Override
    public int getRechargeCount(final int status, final int type, final int subType) {
        final String hql = "select count(id) from " + this.tab + " where status = ?0 and type = ?1 and subtype = ?2";
        final Object[] values = { status, type, subType };
        final Object result = this.superDao.unique(hql, values);
        return (result != null) ? ((Number)result).intValue() : 0;
    }
    
    @Override
    public PageList find(final String sql, final int start, final int limit) {
        final String hsql = "select b.* from user_recharge b, user u where b.user_id = u.id  ";
        final PageList pageList = this.superDao.findPageEntityList(String.valueOf(hsql) + sql, UserRecharge.class, new HashMap<String, Object>(), start, limit);
        return pageList;
    }
}
