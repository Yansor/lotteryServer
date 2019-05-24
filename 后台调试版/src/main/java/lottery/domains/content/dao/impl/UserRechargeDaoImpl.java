package lottery.domains.content.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javautils.StringUtil;
import javautils.array.ArrayUtils;
import javautils.jdbc.PageList;
import javautils.jdbc.hibernate.HibernateSuperDao;
import lottery.domains.content.dao.UserRechargeDao;
import lottery.domains.content.entity.HistoryUserRecharge;
import lottery.domains.content.entity.User;
import lottery.domains.content.entity.UserRecharge;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserRechargeDaoImpl
  implements UserRechargeDao
{
  private final String user = User.class.getSimpleName();
  private final String tab = UserRecharge.class.getSimpleName();
  private final String historyTab = HistoryUserRecharge.class.getSimpleName();
  @Autowired
  private HibernateSuperDao<UserRecharge> superDao;
  @Autowired
  private HibernateSuperDao<HistoryUserRecharge> historySuperDao;
  
  public boolean add(UserRecharge entity)
  {
    return this.superDao.save(entity);
  }
  
  public boolean update(UserRecharge entity)
  {
    return this.superDao.update(entity);
  }
  
  public boolean updateSuccess(int id, double beforeMoney, double afterMoney, double recMoney, String payTime, String payBillno)
  {
    String hql = "update " + this.tab + " set beforeMoney = ?0,afterMoney = ?1,recMoney=?2,status = 1, payTime=?3, payBillno=?4 where id = ?5";
    Object[] values = { Double.valueOf(beforeMoney), Double.valueOf(afterMoney), Double.valueOf(recMoney), payTime, payBillno, Integer.valueOf(id) };
    return this.superDao.update(hql, values);
  }
  
  public UserRecharge getById(int id)
  {
    String hql = "from " + this.tab + " where id = ?0";
    Object[] values = { Integer.valueOf(id) };
    return (UserRecharge)this.superDao.unique(hql, values);
  }
  
  public HistoryUserRecharge getHistoryById(int id)
  {
    String hql = "from " + this.historyTab + " where id = ?0";
    Object[] values = { Integer.valueOf(id) };
    return (HistoryUserRecharge)this.historySuperDao.unique(hql, values);
  }
  
  public UserRecharge getByBillno(String billno)
  {
    String hql = "from " + this.tab + " where billno = ?0";
    Object[] values = { billno };
    return (UserRecharge)this.superDao.unique(hql, values);
  }
  
  public boolean isRecharge(int userId)
  {
    String hql = "select count(id) from " + this.tab + " where userId = ?0 and status = 1";
    Object[] values = { Integer.valueOf(userId) };
    Object result = this.superDao.unique(hql, values);
    return ((Number)result).intValue() > 0;
  }
  
  public List<UserRecharge> getLatest(int userId, int status, int count)
  {
    String hql = "from " + this.tab + " where userId = ?0 and status = ?1 order by id desc";
    Object[] values = { Integer.valueOf(userId), Integer.valueOf(status) };
    return this.superDao.list(hql, values, 0, count);
  }
  
  public List<UserRecharge> list(List<Criterion> criterions, List<Order> orders)
  {
    return this.superDao.findByCriteria(UserRecharge.class, criterions, orders);
  }
  
  public PageList findHistory(List<Criterion> criterions, List<Order> orders, int start, int limit)
  {
    String propertyName = "id";
    return this.historySuperDao.findPageList(HistoryUserRecharge.class, propertyName, criterions, orders, start, limit);
  }
  
  public List<?> getDayRecharge(String sTime, String eTime)
  {
    String hql = "select substring(payTime, 1, 10), sum(recMoney) from " + this.tab + " where status = 1 and payTime >= ?0 and payTime < ?1 group by substring(payTime, 1, 10)";
    Object[] values = { sTime, eTime };
    return this.superDao.listObject(hql, values);
  }
  
  public List<?> getDayRecharge2(String sTime, String eTime, Integer type, Integer subtype)
  {
    String hql = "select substring(b.payTime, 1, 10), count(b.id), sum(b.recMoney), sum(b.receiveFeeMoney) from " + this.tab + " as b , " + this.user + "  as u   where u.id = b.userId  and u.upid != 0 and  b.status = 1 and b.payTime >= ?0 and b.payTime < ?1";
    Object[] values = null;
    if ((type != null) && (subtype != null))
    {
      hql = hql + " and b.type=?2 and b.subtype=?3";
      values = new Object[] { sTime, eTime, type, subtype };
    }
    else if ((type != null) && (subtype == null))
    {
      hql = hql + " and b.type=?2";
      values = new Object[] { sTime, eTime, type };
    }
    else if ((type == null) && (subtype != null))
    {
      hql = hql + " and b.subtype=?2";
      values = new Object[] { sTime, eTime, subtype };
    }
    else
    {
      values = new Object[] { sTime, eTime };
    }
    hql = hql + " group by substring(b.payTime, 1, 10)";
    return this.superDao.listObject(hql, values);
  }
  
  public double getTotalFee(String sTime, String eTime)
  {
    String hql = "select sum(receiveFeeMoney) from " + this.tab + " where status = 1 and payTime >= ?0 and payTime < ?1";
    Object[] values = { sTime, eTime };
    Object result = this.superDao.unique(hql, values);
    return result != null ? ((Number)result).doubleValue() : 0.0D;
  }
  
  public double getThirdTotalRecharge(String sTime, String eTime)
  {
    String hql = "select sum(recMoney) from " + this.tab + " where status = 1 and payTime >= ?0 and payTime < ?1 and channelId is not null and channelId > 0";
    
    Object[] values = { sTime, eTime };
    Object result = this.superDao.unique(hql, values);
    return result != null ? ((Number)result).doubleValue() : 0.0D;
  }
  
  public double getTotalRecharge(String sTime, String eTime, int[] type, int[] subtype, Integer payBankId)
  {
    String hql = "select sum(recMoney) from " + this.tab + " where status = 1 and payTime >= ?0 and payTime < ?1";
    if ((type != null) && (type.length > 0)) {
      hql = hql + " and type in (" + ArrayUtils.transInIds(type) + ")";
    }
    if ((subtype != null) && (subtype.length > 0)) {
      hql = hql + " and subtype in (" + ArrayUtils.transInIds(subtype) + ")";
    }
    if (payBankId != null) {
      hql = hql + " and payBankId = " + payBankId;
    }
    Object[] values = { sTime, eTime };
    Object result = this.superDao.unique(hql, values);
    return result != null ? ((Number)result).doubleValue() : 0.0D;
  }
  
  public Object[] getTotalRechargeData(String sTime, String eTime, Integer type, Integer subtype)
  {
    String hql = "select count(b.id), sum(b.recMoney), sum(b.receiveFeeMoney) from " + this.tab + " as b , " + this.user + " as u  where b.userId = u.id and u.upid != 0 and b.status = 1 and b.payTime >= :sTime and b.payTime < :eTime";
    Map<String, Object> params = new HashMap();
    params.put("sTime", sTime);
    params.put("eTime", eTime);
    if (type != null)
    {
      hql = hql + " and b.type = :type";
      params.put("type", type);
    }
    if (subtype != null)
    {
      hql = hql + " and b.subtype in :subtype";
      params.put("subtype", subtype);
    }
    Object result = this.superDao.uniqueWithParams(hql, params);
    if (result == null) {
      return new Object[] { Integer.valueOf(0), Double.valueOf(0.0D), Double.valueOf(0.0D) };
    }
    Object[] results = (Object[])result;
    
    Object result1 = results[0] == null ? Integer.valueOf(0) : results[0];
    Object result2 = results[1] == null ? Double.valueOf(0.0D) : results[1];
    Object result3 = results[2] == null ? Double.valueOf(0.0D) : results[2];
    
    return new Object[] { result1, result2, result3 };
  }
  
  public double getTotalRecharge(String billno, Integer userId, String minTime, String maxTime, String minPayTime, String maxPayTime, Double minMoney, Double maxMoney, Integer status, Integer channelId)
  {
    String hql = "select sum(recMoney) from " + this.tab + " where 1=1";
    
    Map<String, Object> params = new HashMap();
    if (StringUtil.isNotNull(billno))
    {
      hql = hql + " and billno like :billno";
      params.put("billno", "%" + billno + "%");
    }
    if (userId != null)
    {
      hql = hql + " and userId = :userId";
      params.put("userId", userId);
    }
    if (StringUtil.isNotNull(minTime))
    {
      hql = hql + " and time > :minTime";
      params.put("minTime", minTime);
    }
    if (StringUtil.isNotNull(maxTime))
    {
      hql = hql + " and time < :maxTime";
      params.put("maxTime", maxTime);
    }
    if (StringUtil.isNotNull(minPayTime))
    {
      hql = hql + " and payTime > :minPayTime";
      params.put("minPayTime", minPayTime);
    }
    if (StringUtil.isNotNull(maxPayTime))
    {
      hql = hql + " and payTime < :maxPayTime";
      params.put("maxPayTime", maxPayTime);
    }
    if (minMoney != null)
    {
      hql = hql + " and money >= :minMoney";
      params.put("minMoney", minMoney);
    }
    if (maxMoney != null)
    {
      hql = hql + " and money <= :maxMoney";
      params.put("maxMoney", maxMoney);
    }
    if (status != null)
    {
      hql = hql + " and status = :status";
      params.put("status", status);
    }
    if (channelId != null)
    {
      hql = hql + " and channelId = :channelId";
      params.put("channelId", channelId);
    }
    Object result = this.superDao.uniqueWithParams(hql, params);
    return result != null ? ((Number)result).doubleValue() : 0.0D;
  }
  
  public double getHistoryTotalRecharge(String billno, Integer userId, String minTime, String maxTime, String minPayTime, String maxPayTime, Double minMoney, Double maxMoney, Integer status, Integer channelId)
  {
    String hql = "select sum(recMoney) from " + this.historyTab + " where 1=1";
    
    Map<String, Object> params = new HashMap();
    if (StringUtil.isNotNull(billno))
    {
      hql = hql + " and billno like :billno";
      params.put("billno", "%" + billno + "%");
    }
    if (userId != null)
    {
      hql = hql + " and userId = :userId";
      params.put("userId", userId);
    }
    if (StringUtil.isNotNull(minTime))
    {
      hql = hql + " and time > :minTime";
      params.put("minTime", minTime);
    }
    if (StringUtil.isNotNull(maxTime))
    {
      hql = hql + " and time < :maxTime";
      params.put("maxTime", maxTime);
    }
    if (StringUtil.isNotNull(minPayTime))
    {
      hql = hql + " and payTime > :minPayTime";
      params.put("minPayTime", minPayTime);
    }
    if (StringUtil.isNotNull(maxPayTime))
    {
      hql = hql + " and payTime < :maxPayTime";
      params.put("maxPayTime", maxPayTime);
    }
    if (minMoney != null)
    {
      hql = hql + " and money >= :minMoney";
      params.put("minMoney", minMoney);
    }
    if (maxMoney != null)
    {
      hql = hql + " and money <= :maxMoney";
      params.put("maxMoney", maxMoney);
    }
    if (status != null)
    {
      hql = hql + " and status = :status";
      params.put("status", status);
    }
    if (channelId != null)
    {
      hql = hql + " and channelId = :channelId";
      params.put("channelId", channelId);
    }
    Object result = this.superDao.uniqueWithParams(hql, params);
    return result != null ? ((Number)result).doubleValue() : 0.0D;
  }
  
  public int getRechargeCount(int status, int type, int subType)
  {
    String hql = "select count(id) from " + this.tab + " where status = ?0 and type = ?1 and subtype = ?2";
    Object[] values = { Integer.valueOf(status), Integer.valueOf(type), Integer.valueOf(subType) };
    Object result = this.superDao.unique(hql, values);
    return result != null ? ((Number)result).intValue() : 0;
  }
  
  public PageList find(String sql, int start, int limit)
  {
    String hsql = "select b.* from user_recharge b, user u where b.user_id = u.id  ";
    PageList pageList = this.superDao.findPageEntityList(hsql + sql, UserRecharge.class, new HashMap(), start, limit);
    return pageList;
  }
}
