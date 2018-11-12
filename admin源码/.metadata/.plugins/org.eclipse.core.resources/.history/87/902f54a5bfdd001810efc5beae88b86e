package lottery.domains.content.dao.impl;

import javautils.StringUtil;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.Map;
import java.util.HashMap;
import javautils.jdbc.PageList;
import javautils.array.ArrayUtils;
import java.util.List;
import lottery.domains.content.entity.User;
import lottery.domains.content.entity.HistoryUserWithdraw;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.entity.UserWithdraw;
import javautils.jdbc.hibernate.HibernateSuperDao;
import org.springframework.stereotype.Repository;
import lottery.domains.content.dao.UserWithdrawDao;

@Repository
public class UserWithdrawDaoImpl implements UserWithdrawDao
{
    private final String tab;
    private final String user;
    private final String historyTab;
    @Autowired
    private HibernateSuperDao<UserWithdraw> superDao;
    @Autowired
    private HibernateSuperDao<HistoryUserWithdraw> historySuperDao;
    
    public UserWithdrawDaoImpl() {
        this.tab = UserWithdraw.class.getSimpleName();
        this.user = User.class.getSimpleName();
        this.historyTab = HistoryUserWithdraw.class.getSimpleName();
    }
    
    @Override
    public boolean update(final UserWithdraw entity) {
        return this.superDao.update(entity);
    }
    
    @Override
    public int getWaitTodo() {
        final String hql = "select count(id) from " + this.tab + " where status = 0";
        final Object result = this.superDao.unique(hql);
        return (result != null) ? ((Number)result).intValue() : 0;
    }
    
    @Override
    public UserWithdraw getById(final int id) {
        final String hql = "from " + this.tab + " where id = ?0";
        final Object[] values = { id };
        return (UserWithdraw)this.superDao.unique(hql, values);
    }
    
    @Override
    public HistoryUserWithdraw getHistoryById(final int id) {
        final String hql = "from " + this.historyTab + " where id = ?0";
        final Object[] values = { id };
        return (HistoryUserWithdraw)this.historySuperDao.unique(hql, values);
    }
    
    @Override
    public UserWithdraw getByBillno(final String billno) {
        final String hql = "from " + this.tab + " where billno = ?0";
        final Object[] values = { billno };
        return (UserWithdraw)this.superDao.unique(hql, values);
    }
    
    @Override
    public List<UserWithdraw> listByOperatorTime(final String sDate, final String eDate) {
        final String hql = "from " + this.tab + " where status = 1 and operatorTime >= ?0 and operatorTime < ?1 order by operatorTime desc,id desc";
        final Object[] values = { sDate, eDate };
        return this.superDao.list(hql, values);
    }
    
    @Override
    public List<UserWithdraw> listByRemitStatus(final int[] remitStatuses, final boolean third, final String sTime, final String eTime) {
        String hql = "from " + this.tab + " where remitStatus in (" + ArrayUtils.transInIds(remitStatuses) + ") and operatorTime >= ?0 and operatorTime < ?1";
        if (third) {
            hql = String.valueOf(hql) + " and paymentChannelId is not null and paymentChannelId > 0";
        }
        final Object[] values = { sTime, eTime };
        return this.superDao.list(hql, values);
    }
    
    @Override
    public List<UserWithdraw> getLatest(final int userId, final int status, final int count) {
        final String hql = "from " + this.tab + " where userId = ?0 and status = ?1 order by id desc";
        final Object[] values = { userId, status };
        return this.superDao.list(hql, values, 0, count);
    }
    
    @Override
    public PageList find(final String sql, final int start, final int limit) {
        final String hsql = "select b.* from user_withdraw b, user u where b.user_id = u.id  ";
        return this.superDao.findPageEntityList(String.valueOf(hsql) + sql, UserWithdraw.class, new HashMap<String, Object>(), start, limit);
    }
    
    public PageList find(final List<Criterion> criterions, final List<Order> orders, final int start, final int limit) {
        final String propertyName = "id";
        return this.superDao.findPageList(UserWithdraw.class, propertyName, criterions, orders, start, limit);
    }
    
    @Override
    public PageList findHistory(final List<Criterion> criterions, final List<Order> orders, final int start, final int limit) {
        final String propertyName = "id";
        return this.historySuperDao.findPageList(HistoryUserWithdraw.class, propertyName, criterions, orders, start, limit);
    }
    
    @Override
    public double getTotalWithdraw(final String sTime, final String eTime) {
        final String hql = "select sum(money) from " + this.tab + " where status = 1 and operatorTime >= ?0 and operatorTime < ?1";
        final Object[] values = { sTime, eTime };
        final Object result = this.superDao.unique(hql, values);
        return (result != null) ? ((Number)result).doubleValue() : 0.0;
    }
    
    @Override
    public Object[] getTotalWithdrawData(final String sTime, final String eTime) {
        final String hql = "select count(b.id), sum(b.money) from " + this.tab + " as b  , " + this.user + " as u  where u.id = b.userId and u.upid !=0 and  b.status = 1 and b.operatorTime >= :sTime and b.operatorTime < :eTime";
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("sTime", sTime);
        params.put("eTime", eTime);
        final Object result = this.superDao.uniqueWithParams(hql, params);
        if (result == null) {
            return new Object[] { 0, 0.0 };
        }
        final Object[] results = (Object[])result;
        final Object result2 = (results[0] == null) ? Integer.valueOf(0) : results[0];
        final Object result3 = (results[1] == null) ? Double.valueOf(0.0) : results[1];
        return new Object[] { result2, result3 };
    }
    
    @Override
    public double[] getTotalWithdraw(final String billno, final Integer userId, final String minTime, final String maxTime, final String minOperatorTime, final String maxOperatorTime, final Double minMoney, final Double maxMoney, final String keyword, final Integer status, final Integer checkStatus, final Integer remitStatus, final Integer paymentChannelId) {
        String hql = "select sum(recMoney), sum(feeMoney) from " + this.tab + " where 1=1";
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
        if (StringUtil.isNotNull(minOperatorTime)) {
            hql = String.valueOf(hql) + " and operatorTime > :minOperatorTime";
            params.put("minOperatorTime", minOperatorTime);
        }
        if (StringUtil.isNotNull(maxOperatorTime)) {
            hql = String.valueOf(hql) + " and operatorTime < :maxOperatorTime";
            params.put("maxOperatorTime", maxOperatorTime);
        }
        if (minMoney != null) {
            hql = String.valueOf(hql) + " and money >= :minMoney";
            params.put("minMoney", minMoney);
        }
        if (maxMoney != null) {
            hql = String.valueOf(hql) + " and money <= :maxMoney";
            params.put("maxMoney", maxMoney);
        }
        if (StringUtil.isNotNull(keyword)) {
            hql = String.valueOf(hql) + " and (cardName like :cardName or cardId like :cardId)";
            params.put("cardName", "%" + keyword + "%");
            params.put("cardId", "%" + keyword + "%");
        }
        if (status != null) {
            hql = String.valueOf(hql) + " and status = :status";
            params.put("status", status);
        }
        if (checkStatus != null) {
            hql = String.valueOf(hql) + " and checkStatus = :checkStatus";
            params.put("checkStatus", checkStatus);
        }
        if (remitStatus != null) {
            hql = String.valueOf(hql) + " and remitStatus = :remitStatus";
            params.put("remitStatus", remitStatus);
        }
        if (paymentChannelId != null) {
            hql = String.valueOf(hql) + " and paymentChannelId = :paymentChannelId";
            params.put("paymentChannelId", paymentChannelId);
        }
        final Object result = this.superDao.uniqueWithParams(hql, params);
        if (result == null) {
            return new double[] { 0.0, 0.0 };
        }
        final Object[] results = (Object[])result;
        final double totalRecMoney = (double)((results[0] == null) ? 0.0 : results[0]);
        final double totalFeeMoney = (double)((results[1] == null) ? 0.0 : results[1]);
        return new double[] { totalRecMoney, totalFeeMoney };
    }
    
    @Override
    public double[] getHistoryTotalWithdraw(final String billno, final Integer userId, final String minTime, final String maxTime, final String minOperatorTime, final String maxOperatorTime, final Double minMoney, final Double maxMoney, final String keyword, final Integer status, final Integer checkStatus, final Integer remitStatus, final Integer paymentChannelId) {
        String hql = "select sum(recMoney), sum(feeMoney) from " + this.historyTab + " where 1=1";
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
        if (StringUtil.isNotNull(minOperatorTime)) {
            hql = String.valueOf(hql) + " and operatorTime > :minOperatorTime";
            params.put("minOperatorTime", minOperatorTime);
        }
        if (StringUtil.isNotNull(maxOperatorTime)) {
            hql = String.valueOf(hql) + " and operatorTime < :maxOperatorTime";
            params.put("maxOperatorTime", maxOperatorTime);
        }
        if (minMoney != null) {
            hql = String.valueOf(hql) + " and money >= :minMoney";
            params.put("minMoney", minMoney);
        }
        if (maxMoney != null) {
            hql = String.valueOf(hql) + " and money <= :maxMoney";
            params.put("maxMoney", maxMoney);
        }
        if (StringUtil.isNotNull(keyword)) {
            hql = String.valueOf(hql) + " and (cardName like :cardName or cardId like :cardId)";
            params.put("cardName", "%" + keyword + "%");
            params.put("cardId", "%" + keyword + "%");
        }
        if (status != null) {
            hql = String.valueOf(hql) + " and status = :status";
            params.put("status", status);
        }
        if (checkStatus != null) {
            hql = String.valueOf(hql) + " and checkStatus = :checkStatus";
            params.put("checkStatus", checkStatus);
        }
        if (remitStatus != null) {
            hql = String.valueOf(hql) + " and remitStatus = :remitStatus";
            params.put("remitStatus", remitStatus);
        }
        if (paymentChannelId != null) {
            hql = String.valueOf(hql) + " and paymentChannelId = :paymentChannelId";
            params.put("paymentChannelId", paymentChannelId);
        }
        final Object result = this.superDao.uniqueWithParams(hql, params);
        if (result == null) {
            return new double[] { 0.0, 0.0 };
        }
        final Object[] results = (Object[])result;
        final double totalRecMoney = (double)((results[0] == null) ? 0.0 : results[0]);
        final double totalFeeMoney = (double)((results[1] == null) ? 0.0 : results[1]);
        return new double[] { totalRecMoney, totalFeeMoney };
    }
    
    @Override
    public double getTotalAutoRemit(final String sTime, final String eTime) {
        final String hql = "select sum(money) from " + this.tab + " where status = 1 and remitStatus = 2 and operatorTime >= ?0 and operatorTime < ?1";
        final Object[] values = { sTime, eTime };
        final Object result = this.superDao.unique(hql, values);
        return (result != null) ? ((Number)result).doubleValue() : 0.0;
    }
    
    @Override
    public double getTotalFee(final String sTime, final String eTime) {
        final String hql = "select sum(feeMoney) from " + this.tab + " where status = 1 and operatorTime >= ?0 and operatorTime < ?1";
        final Object[] values = { sTime, eTime };
        final Object result = this.superDao.unique(hql, values);
        return (result != null) ? ((Number)result).doubleValue() : 0.0;
    }
    
    @Override
    public List<?> getDayWithdraw(final String sTime, final String eTime) {
        final String hql = "select substring(operatorTime, 1, 10), sum(money) from " + this.tab + " where status = 1 and operatorTime >= ?0 and operatorTime < ?1 group by substring(operatorTime, 1, 10)";
        final Object[] values = { sTime, eTime };
        return this.superDao.listObject(hql, values);
    }
    
    @Override
    public List<?> getDayWithdraw2(final String sTime, final String eTime) {
        final String hql = "select substring(b.operatorTime, 1, 10), count(b.id), sum(b.money) from " + this.tab + " as b , " + this.user + " as u  where  b.userId = u.id and u.upid != 0 and b.status = 1 and b.operatorTime >= ?0 and b.operatorTime < ?1 group by substring(b.operatorTime, 1, 10)";
        final Object[] values = { sTime, eTime };
        return this.superDao.listObject(hql, values);
    }
    
    @Override
    public boolean lock(final String billno, final String operatorUser, final String operatorTime) {
        final String hql = "update " + this.tab + " set lockStatus = 1, operatorUser = ?1, operatorTime = ?2 where billno = ?0 and lockStatus = 0";
        final Object[] values = { billno, operatorUser, operatorTime };
        return this.superDao.update(hql, values);
    }
    
    @Override
    public boolean unlock(final String billno, final String operatorUser) {
        final String hql = "update " + this.tab + " set lockStatus = 0, operatorUser = null, operatorTime = null where billno = ?0 and operatorUser = ?1";
        final Object[] values = { billno, operatorUser };
        return this.superDao.update(hql, values);
    }
}
