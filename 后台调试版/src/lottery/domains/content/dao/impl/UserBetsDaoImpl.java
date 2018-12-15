package lottery.domains.content.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javautils.StringUtil;
import javautils.array.ArrayUtils;
import javautils.jdbc.PageList;
import javautils.jdbc.hibernate.HibernateSuperDao;
import lottery.domains.content.dao.UserBetsDao;
import lottery.domains.content.entity.HistoryUserBets;
import lottery.domains.content.entity.HistoryUserBetsNoCode;
import lottery.domains.content.entity.User;
import lottery.domains.content.entity.UserBets;
import lottery.domains.content.entity.UserBetsNoCode;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UserBetsDaoImpl
  implements UserBetsDao
{
  private final String tab = UserBets.class.getSimpleName();
  private final String utab = User.class.getSimpleName();
  private final String historyTab = HistoryUserBets.class.getSimpleName();
  private final String noCodeTab = UserBetsNoCode.class.getSimpleName();
  private final String historyNoCodeTab = HistoryUserBetsNoCode.class.getSimpleName();
  @Autowired
  private HibernateSuperDao<UserBets> superDao;
  @Autowired
  private HibernateSuperDao<UserBetsNoCode> noCodeSuperDao;
  @Autowired
  private HibernateSuperDao<HistoryUserBets> historySuperDao;
  @Autowired
  private HibernateSuperDao<HistoryUserBetsNoCode> historyNoCodeSuperDao;
  
  public boolean updateStatus(int id, int status, String codes, String openCode, double prizeMoney, String prizeTime)
  {
    String hql = "update " + this.tab + " set status = ?1, codes = ?2, openCode = ?3, prizeMoney = ?4, prizeTime = ?5, locked = 0 where id = ?0";
    Object[] values = { Integer.valueOf(id), Integer.valueOf(status), codes, openCode, Double.valueOf(prizeMoney), prizeTime };
    return this.superDao.update(hql, values);
  }
  
  public boolean updateLocked(int id, int locked)
  {
    String hql = "update " + this.tab + " set locked = ?1 where id = ?0";
    Object[] values = { Integer.valueOf(id), Integer.valueOf(locked) };
    return this.superDao.update(hql, values);
  }
  
  public UserBets getById(int id)
  {
    String hql = "from " + this.tab + " where id = ?0";
    Object[] values = { Integer.valueOf(id) };
    return (UserBets)this.superDao.unique(hql, values);
  }
  
  public HistoryUserBets getHistoryById(int id)
  {
    String hql = "from " + this.historyTab + " where id = ?0";
    Object[] values = { Integer.valueOf(id) };
    return (HistoryUserBets)this.historySuperDao.unique(hql, values);
  }
  
  public List<UserBets> getByBillno(String billno, boolean withCodes)
  {
    String targetTab = withCodes ? this.tab : this.noCodeTab;
    String hql = "from " + targetTab + " where billno = ?0";
    Object[] values = { billno };
    if (withCodes) {
      return this.superDao.list(hql, values);
    }
    List<UserBetsNoCode> noCodeList = this.noCodeSuperDao.list(hql, values);
    List<UserBets> list = new ArrayList();
    for (UserBetsNoCode tmpBean : noCodeList) {
      list.add(tmpBean.formatBean());
    }
    return list;
  }
  
  public List<HistoryUserBets> getHistoryByBillno(String billno, boolean withCodes)
  {
    String targetTab = withCodes ? this.historyTab : this.historyNoCodeTab;
    String hql = "from " + targetTab + " where billno = ?0";
    Object[] values = { billno };
    if (withCodes) {
      return this.historySuperDao.list(hql, values);
    }
    List<HistoryUserBetsNoCode> noCodeList = this.historyNoCodeSuperDao.list(hql, values);
    List<HistoryUserBets> list = new ArrayList();
    for (HistoryUserBetsNoCode tmpBean : noCodeList) {
      list.add(tmpBean.formatBean());
    }
    return list;
  }
  
  public UserBets getBybillno(int userId, String billno)
  {
    String hql = "from " + this.tab + " where userId = ?0 and billno = ?1";
    Object[] values = { Integer.valueOf(userId), billno };
    return (UserBets)this.superDao.unique(hql, values);
  }
  
  public boolean cancel(int id)
  {
    String hql = "update " + this.tab + " set status = -1 where id = ?0 and status = 0";
    Object[] values = { Integer.valueOf(id) };
    return this.superDao.update(hql, values);
  }
  
  public boolean delete(int userId)
  {
    String hql = "delete from " + this.tab + " where userId = ?0";
    Object[] values = { Integer.valueOf(userId) };
    return this.superDao.delete(hql, values);
  }
  
  public boolean isCost(int userId)
  {
    String hql = "select count(id) from " + this.tab + " where userId = ?0 and status > 0";
    Object[] values = { Integer.valueOf(userId) };
    Object result = this.superDao.unique(hql, values);
    return ((Number)result).intValue() > 0;
  }
  
  public List<UserBets> getSuspiciousOrder(int userId, int multiple, boolean withCodes)
  {
    String targetTab = withCodes ? this.tab : this.noCodeTab;
    String hql = "from " + targetTab + " where userId = ?0 and status > 0 and prizeMoney >= money * ?1";
    Object[] values = { Integer.valueOf(userId), Double.valueOf(multiple * 1.0D) };
    if (withCodes) {
      return this.superDao.list(hql, values);
    }
    List<UserBetsNoCode> noCodeList = this.noCodeSuperDao.list(hql, values);
    List<UserBets> list = new ArrayList();
    for (UserBetsNoCode tmpBean : noCodeList) {
      list.add(tmpBean.formatBean());
    }
    return list;
  }
  
  public List<UserBets> getByFollowBillno(String followBillno, boolean withCodes)
  {
    String targetTab = withCodes ? this.tab : this.noCodeTab;
    String hql = "from " + targetTab + " where type = 0 and status > 0 and planBillno = ?0";
    Object[] values = { followBillno };
    if (withCodes) {
      return this.superDao.list(hql, values);
    }
    List<UserBetsNoCode> noCodeList = this.noCodeSuperDao.list(hql, values);
    List<UserBets> list = new ArrayList();
    for (UserBetsNoCode tmpBean : noCodeList) {
      list.add(tmpBean.formatBean());
    }
    return list;
  }
  
  @Transactional(readOnly=true)
  public PageList find(String sql, int start, int limit, boolean withCodes)
  {
    String hsql = "select b.* from user_bets b, user u where b.user_id = u.id  ";
    if (withCodes)
    {
      PageList pageList = this.superDao.findPageEntityList(hsql + sql, UserBets.class, new HashMap(), start, limit);
      return pageList;
    }
    PageList pageList = this.noCodeSuperDao.findPageEntityList(hsql + sql, UserBetsNoCode.class, new HashMap(), start, limit);
    List<UserBets> list = new ArrayList();
    for (Object o : pageList.getList())
    {
      UserBetsNoCode entity = (UserBetsNoCode)o;
      list.add(entity.formatBean());
    }
    pageList.setList(list);
    return pageList;
  }
  
  public List<UserBets> find(List<Criterion> criterions, List<Order> orders, boolean withCodes)
  {
    if (withCodes) {
      return this.superDao.findByCriteria(UserBets.class, criterions, orders);
    }
    List<UserBetsNoCode> noCodeList = this.noCodeSuperDao.findByCriteria(UserBetsNoCode.class, criterions, orders);
    List<UserBets> list = new ArrayList();
    for (UserBetsNoCode tmpBean : noCodeList) {
      list.add(tmpBean.formatBean());
    }
    return list;
  }
  
  public long getTotalBetsMoney(String sTime, String eTime)
  {
    String hql = "select sum(b.money) from " + this.tab + "   b  ," + this.utab + "   u   where  u.id = b.userId   and  b.status >= 0 and b.time >= ?0 and b.time < ?1 and u.upid !=?2";
    Object[] values = { sTime, eTime, Integer.valueOf(0) };
    Object result = this.superDao.unique(hql, values);
    if (result != null) {
      return ((Number)result).longValue();
    }
    return 0L;
  }
  
  public int getTotalOrderCount(String sTime, String eTime)
  {
    String hql = "select count(b.id) from  " + this.tab + "   b  ," + this.utab + "   u   where  u.id = b.userId and b.status >= 0 and b.time >= ?0 and b.time < ?1 and u.upid !=?2";
    Object[] values = { sTime, eTime, Integer.valueOf(0) };
    Object result = this.superDao.unique(hql, values);
    if (result != null) {
      return ((Number)result).intValue();
    }
    return 0;
  }
  
  public List<?> getDayUserBets(int[] lids, String sTime, String eTime)
  {
    String hql = "select substring(b.time, 1, 10), count(b.id) from " + this.tab + "   b  ," + this.utab + "   u   where  u.id = b.userId  and  b.status >= 0 and b.time >= ?0 and b.time < ?1 and u.upid !=?2";
    if ((lids != null) && (lids.length > 0))
    {
      String ids = ArrayUtils.transInIds(lids);
      hql = hql + " and b.lotteryId in (" + ids + ")";
    }
    hql = hql + " group by substring(b.time, 1, 10)";
    Object[] values = { sTime, eTime, Integer.valueOf(0) };
    return this.superDao.listObject(hql, values);
  }
  
  public List<?> getDayBetsMoney(int[] lids, String sTime, String eTime)
  {
    String hql = "select substring(b.time, 1, 10), sum(b.money) from " + this.tab + "   b  ," + this.utab + "   u   where  u.id = b.userId  and b.status >= 0 and b.time >= ?0 and b.time < ?1  and u.upid !=?2";
    if ((lids != null) && (lids.length > 0))
    {
      String ids = ArrayUtils.transInIds(lids);
      hql = hql + " and b.lotteryId in (" + ids + ")";
    }
    hql = hql + " group by substring(b.time, 1, 10)";
    Object[] values = { sTime, eTime, Integer.valueOf(0) };
    return this.superDao.listObject(hql, values);
  }
  
  public List<?> getDayPrizeMoney(int[] lids, String sTime, String eTime)
  {
    String hql = "select substring(b.time, 1, 10), sum(b.prizeMoney) from " + this.tab + "   b  ," + this.utab + "   u   where  u.id = b.userId  and  b.status = 2 and b.time >= ?0 and b.time < ?1 and u.upid !=?2";
    if ((lids != null) && (lids.length > 0))
    {
      String ids = ArrayUtils.transInIds(lids);
      hql = hql + " and b.lotteryId in (" + ids + ")";
    }
    hql = hql + " group by substring(b.time, 1, 10)";
    Object[] values = { sTime, eTime, Integer.valueOf(0) };
    return this.superDao.listObject(hql, values);
  }
  
  public List<?> getLotteryHot(int[] lids, String sTime, String eTime)
  {
    String hql = "select lotteryId, count(id) from " + this.tab + " where time >= ?0 and time < ?1";
    if ((lids != null) && (lids.length > 0))
    {
      String ids = ArrayUtils.transInIds(lids);
      hql = hql + " and lotteryId in (" + ids + ")";
    }
    hql = hql + " group by lotteryId";
    Object[] values = { sTime, eTime };
    return this.superDao.listObject(hql, values);
  }
  
  public List<?> report(List<Integer> lids, Integer ruleId, String sTime, String eTime)
  {
    String hql = "select substring(b.prizeTime, 1, 10), sum(b.money), sum(((13.0-(b.point*20+b.code-1700)/20))*b.money)/100, sum(b.prizeMoney) from " + this.tab + "    b  ," + this.utab + "   u   where  u.id = b.userId where b.status > 0 and u.upid !=?2";
    if ((lids != null) && (lids.size() > 0)) {
      hql = hql + " and b.lotteryId in (" + ArrayUtils.transInIds(lids) + ")";
    }
    if (ruleId != null) {
      hql = hql + " and b.ruleId = '" + ruleId + "'";
    }
    hql = hql + " and b.prizeTime >= ?0 and b.prizeTime < ?1 group by substring(b.prizeTime, 1, 10)";
    Object[] values = { sTime, eTime, Integer.valueOf(0) };
    return this.superDao.listObject(hql, values);
  }
  
  public int countUserOnline(String time)
  {
    String hql = "select count(*) as ucount from (select id from  user_bets where time > '" + time + "'  GROUP BY user_id ) as ubet;";
    Object result = this.superDao.uniqueWithSqlCount(hql);
    return result != null ? ((Number)result).intValue() : 0;
  }
  
  public double[] getTotalMoney(String keyword, Integer userId, Integer utype, Integer type, Integer lotteryId, String expect, Integer ruleId, String minTime, String maxTime, String minPrizeTime, String maxPrizeTime, Double minMoney, Double maxMoney, Integer minMultiple, Integer maxMultiple, Double minPrizeMoney, Double maxPrizeMoney, Integer status, Integer locked, String ip)
  {
    String hql = "select sum(b.money), sum(b.prizeMoney) from " + this.tab + " b , " + this.utab + "  u  where b.userId = u.id ";
    
    Map<String, Object> params = new HashMap();
    if (StringUtil.isNotNull(keyword)) {
      if (StringUtil.isInteger(keyword))
      {
        hql = hql + " and (b.id = :id  or b.billno like :billno or b.chaseBillno like :chaseBillno)";
        params.put("id", Integer.valueOf(keyword));
        params.put("billno", "%" + keyword + "%");
        params.put("chaseBillno", "%" + keyword + "%");
      }
      else
      {
        hql = hql + " and (b.billno like :billno or b.chaseBillno like :chaseBillno)";
        params.put("billno", "%" + keyword + "%");
        params.put("chaseBillno", "%" + keyword + "%");
      }
    }
    if (userId != null)
    {
      hql = hql + " and b.userId = :userId";
      params.put("userId", userId);
    }
    if (type != null)
    {
      hql = hql + " and b.type = :type";
      params.put("type", type);
    }
    if (utype != null)
    {
      hql = hql + " and u.type = :utype";
      params.put("utype", utype);
    }
    else
    {
      hql = hql + " and u.upid != 0";
    }
    if (lotteryId != null)
    {
      hql = hql + " and b.lotteryId = :lotteryId";
      params.put("lotteryId", lotteryId);
    }
    if (StringUtil.isNotNull(expect))
    {
      hql = hql + " and b.expect like :expect";
      params.put("expect", expect);
    }
    if (ruleId != null)
    {
      hql = hql + " and b.ruleId = :ruleId";
      params.put("ruleId", ruleId);
    }
    if (StringUtil.isNotNull(minTime))
    {
      hql = hql + " and b.time > :minTime";
      params.put("minTime", minTime);
    }
    if (StringUtil.isNotNull(maxTime))
    {
      hql = hql + " and b. time < :maxTime";
      params.put("maxTime", maxTime);
    }
    if (StringUtil.isNotNull(minPrizeTime))
    {
      hql = hql + " and b.prizeTime > :minPrizeTime";
      params.put("minPrizeTime", minPrizeTime);
    }
    if (StringUtil.isNotNull(maxPrizeTime))
    {
      hql = hql + " and b.prizeTime < :maxPrizeTime";
      params.put("maxPrizeTime", maxPrizeTime);
    }
    if (minMoney != null)
    {
      hql = hql + " and b.money >= :minMoney";
      params.put("minMoney", minMoney);
    }
    if (maxMoney != null)
    {
      hql = hql + " and b.money <= :maxMoney";
      params.put("maxMoney", maxMoney);
    }
    if (minMultiple != null)
    {
      hql = hql + " and b.multiple >= :minMultiple";
      params.put("minMultiple", minMultiple);
    }
    if (maxMultiple != null)
    {
      hql = hql + " and b.multiple <= :maxMultiple";
      params.put("maxMultiple", maxMultiple);
    }
    if (minPrizeMoney != null)
    {
      hql = hql + " and b.prizeMoney >= :minPrizeMoney";
      params.put("minPrizeMoney", minPrizeMoney);
    }
    if (maxPrizeMoney != null)
    {
      hql = hql + " and b.prizeMoney <= :maxPrizeMoney";
      params.put("maxPrizeMoney", maxPrizeMoney);
    }
    if (status != null)
    {
      hql = hql + " and b.status = :status";
      params.put("status", status);
    }
    if (locked != null)
    {
      hql = hql + " and b.locked = :locked";
      params.put("locked", locked);
    }
    if (StringUtils.isNotEmpty(ip))
    {
      hql = hql + " and b.ip = :ip";
      params.put("ip", ip);
    }
    Object result = this.superDao.uniqueWithParams(hql, params);
    if (result == null) {
      return new double[] { 0.0D, 0.0D };
    }
    Object[] results = (Object[])result;
    double totalMoney = results[0] == null ? 0.0D : ((Double)results[0]).doubleValue();
    double totalPrizeMoney = results[1] == null ? 0.0D : ((Double)results[1]).doubleValue();
    return new double[] { totalMoney, totalPrizeMoney };
  }
  
  public double[] getHistoryTotalMoney(String keyword, Integer userId, Integer utype, Integer type, Integer lotteryId, String expect, Integer ruleId, String minTime, String maxTime, String minPrizeTime, String maxPrizeTime, Double minMoney, Double maxMoney, Integer minMultiple, Integer maxMultiple, Double minPrizeMoney, Double maxPrizeMoney, Integer status, Integer locked)
  {
    String hql = "select sum(b.money), sum(b.prizeMoney) from " + this.historyTab + " b , " + this.utab + "  u  where b.userId = u.id ";
    
    Map<String, Object> params = new HashMap();
    if (StringUtil.isNotNull(keyword)) {
      if (StringUtil.isInteger(keyword))
      {
        hql = hql + " and (b.id = :id  or b.billno like :billno or b.chaseBillno like :chaseBillno)";
        params.put("id", Integer.valueOf(keyword));
        params.put("billno", "%" + keyword + "%");
        params.put("chaseBillno", "%" + keyword + "%");
      }
      else
      {
        hql = hql + " and (b.billno like :billno or b.chaseBillno like :chaseBillno)";
        params.put("billno", "%" + keyword + "%");
        params.put("chaseBillno", "%" + keyword + "%");
      }
    }
    if (userId != null)
    {
      hql = hql + " and b.userId = :userId";
      params.put("userId", userId);
    }
    if (type != null)
    {
      hql = hql + " and b.type = :type";
      params.put("type", type);
    }
    if (utype != null)
    {
      hql = hql + " and u.type = :utype";
      params.put("utype", utype);
    }
    else
    {
      hql = hql + " and u.upid !=0";
    }
    if (lotteryId != null)
    {
      hql = hql + " and b.lotteryId = :lotteryId";
      params.put("lotteryId", lotteryId);
    }
    if (StringUtil.isNotNull(expect))
    {
      hql = hql + " and b.expect like :expect";
      params.put("expect", expect);
    }
    if (ruleId != null)
    {
      hql = hql + " and b.ruleId = :ruleId";
      params.put("ruleId", ruleId);
    }
    if (StringUtil.isNotNull(minTime))
    {
      hql = hql + " and b.time > :minTime";
      params.put("minTime", minTime);
    }
    if (StringUtil.isNotNull(maxTime))
    {
      hql = hql + " and b.time < :maxTime";
      params.put("maxTime", maxTime);
    }
    if (StringUtil.isNotNull(minPrizeTime))
    {
      hql = hql + " and b.prizeTime > :minPrizeTime";
      params.put("minPrizeTime", minPrizeTime);
    }
    if (StringUtil.isNotNull(maxPrizeTime))
    {
      hql = hql + " and b.prizeTime < :maxPrizeTime";
      params.put("maxPrizeTime", maxPrizeTime);
    }
    if (minMoney != null)
    {
      hql = hql + " and b.money >= :minMoney";
      params.put("minMoney", minMoney);
    }
    if (maxMoney != null)
    {
      hql = hql + " and b.money <= :maxMoney";
      params.put("maxMoney", maxMoney);
    }
    if (minMultiple != null)
    {
      hql = hql + " and b.multiple >= :minMultiple";
      params.put("minMultiple", minMultiple);
    }
    if (maxMultiple != null)
    {
      hql = hql + " and b.multiple <= :maxMultiple";
      params.put("maxMultiple", maxMultiple);
    }
    if (minPrizeMoney != null)
    {
      hql = hql + " and b.prizeMoney >= :minPrizeMoney";
      params.put("minPrizeMoney", minPrizeMoney);
    }
    if (maxPrizeMoney != null)
    {
      hql = hql + " and b.prizeMoney <= :maxPrizeMoney";
      params.put("maxPrizeMoney", maxPrizeMoney);
    }
    if (status != null)
    {
      hql = hql + " and b.status = :status";
      params.put("status", status);
    }
    if (locked != null)
    {
      hql = hql + " and b.locked = :locked";
      params.put("locked", locked);
    }
    Object result = this.historySuperDao.uniqueWithParams(hql, params);
    if (result == null) {
      return new double[] { 0.0D, 0.0D };
    }
    Object[] results = (Object[])result;
    double totalMoney = results[0] == null ? 0.0D : ((Double)results[0]).doubleValue();
    double totalPrizeMoney = results[1] == null ? 0.0D : ((Double)results[1]).doubleValue();
    return new double[] { totalMoney, totalPrizeMoney };
  }
  
  public PageList findHistory(List<Criterion> criterions, List<Order> orders, int start, int limit, boolean withCodes)
  {
    String propertyName = "id";
    if (withCodes) {
      return this.historySuperDao.findPageList(HistoryUserBets.class, propertyName, criterions, orders, start, limit);
    }
    PageList pageList = this.historyNoCodeSuperDao.findPageList(HistoryUserBetsNoCode.class, propertyName, criterions, orders, start, limit);
    List<HistoryUserBets> list = new ArrayList();
    for (Object o : pageList.getList())
    {
      HistoryUserBetsNoCode entity = (HistoryUserBetsNoCode)o;
      list.add(entity.formatBean());
    }
    pageList.setList(list);
    return pageList;
  }
  
  public double getBillingOrder(int userId, String startTime, String endTime)
  {
    String hql = "select sum(money) from " + this.tab + " where userId = ?0 and prizeTime >= ?1 and prizeTime < ?2 and status > 0 and id > 0";
    Object[] values = { Integer.valueOf(userId), startTime, endTime };
    Object result = this.superDao.unique(hql, values);
    return result != null ? ((Number)result).doubleValue() : 0.0D;
  }
}
