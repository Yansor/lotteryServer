package lottery.domains.content.biz.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javautils.StringUtil;
import javautils.date.DateUtil;
import javautils.jdbc.PageList;
import javautils.redis.JedisTemplate;
import lottery.domains.content.biz.UserBetsService;
import lottery.domains.content.biz.UserBillService;
import lottery.domains.content.dao.UserBetsDao;
import lottery.domains.content.dao.UserDao;
import lottery.domains.content.entity.HistoryUserBets;
import lottery.domains.content.entity.User;
import lottery.domains.content.entity.UserBets;
import lottery.domains.content.vo.user.HistoryUserBetsVO;
import lottery.domains.content.vo.user.UserBetsVO;
import lottery.domains.pool.LotteryDataFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.exceptions.JedisException;

@Service
public class UserBetsServiceImpl
  implements UserBetsService
{
  private static final Logger log = LoggerFactory.getLogger(UserBetsServiceImpl.class);
  public static final String USER_BETS_UNOPEN_RECENT_KEY = "USER:BETS:UNOPEN:RECENT:%s";
  public static final String USER_BETS_OPENED_RECENT_KEY = "USER:BETS:OPENED:RECENT:%s";
  @Autowired
  private UserDao uDao;
  @Autowired
  private UserBetsDao uBetsDao;
  @Autowired
  private UserBillService uBillService;
  @Autowired
  private LotteryDataFactory lotteryDataFactory;
  @Autowired
  private JedisTemplate jedisTemplate;
  
  public PageList search(String keyword, String username, Integer uype, Integer type, Integer lotteryId, String expect, Integer ruleId, String minTime, String maxTime, String minPrizeTime, String maxPrizeTime, Double minMoney, Double maxMoney, Integer minMultiple, Integer maxMultiple, Double minPrizeMoney, Double maxPrizeMoney, Integer status, Integer locked, String ip, int start, int limit)
  {
    StringBuilder querySql = new StringBuilder();
    boolean isSearch = true;
    if (StringUtil.isNotNull(username))
    {
      User user = this.uDao.getByUsername(username);
      if (user != null) {
        querySql.append(" and b.user_id = ").append(user.getId());
      } else {
        isSearch = false;
      }
    }
    if (StringUtil.isNotNull(keyword)) {
      if (StringUtil.isInteger(keyword)) {
        querySql.append(" and b.id = " + Integer.parseInt(keyword));
      } else {
        querySql.append(" and b.billno =").append("'" + keyword + "'");
      }
    }
    if (lotteryId != null) {
      querySql.append(" and  b.lottery_id = ").append(lotteryId.intValue());
    }
    if (StringUtil.isNotNull(expect)) {
      querySql.append(" and  b.expect = ").append(expect);
    }
    if (ruleId != null) {
      querySql.append(" and b.rule_id = ").append(ruleId);
    }
    if (type != null) {
      querySql.append(" and b.type = ").append(type);
    }
    if (StringUtil.isNotNull(minTime)) {
      querySql.append(" and b.time > ").append("'" + minTime + "'");
    }
    if (StringUtil.isNotNull(maxTime)) {
      querySql.append(" and b.time < ").append("'" + maxTime + "'");
    }
    if (StringUtil.isNotNull(minPrizeTime)) {
      querySql.append(" and b.prize_time > ").append("'" + minPrizeTime + "'");
    }
    if (StringUtil.isNotNull(maxPrizeTime)) {
      querySql.append(" and b.prize_time < ").append("'" + maxPrizeTime + "'");
    }
    if (minMoney != null) {
      querySql.append(" and b.money >= ").append(minMoney.doubleValue());
    }
    if (maxMoney != null) {
      querySql.append(" and b.money <= ").append(maxMoney.doubleValue());
    }
    if (minMultiple != null) {
      querySql.append(" and b.multiple >= ").append(minMultiple.intValue());
    }
    if (maxMultiple != null) {
      querySql.append(" and b.multiple <= ").append(maxMultiple.intValue());
    }
    if (minPrizeMoney != null) {
      querySql.append(" and b.prize_money >= ").append(minPrizeMoney.doubleValue());
    }
    if (maxPrizeMoney != null) {
      querySql.append(" and b.prize_money <= ").append(maxPrizeMoney.doubleValue());
    }
    if (status != null) {
      querySql.append(" and b.status = ").append(status.intValue());
    }
    if (locked != null) {
      querySql.append(" and b.locked = ").append(locked.intValue());
    }
    if (StringUtil.isNotNull(ip)) {
      querySql.append("  and b.ip = ").append(ip);
    }
    querySql.append(" and b.id > ").append(0);
    if (uype != null) {
      querySql.append("  and u.type = ").append(uype);
    } else {
      querySql.append("  and u.upid != ").append(0);
    }
    querySql.append("  ORDER BY b.time DESC ");
    if (isSearch)
    {
      List<UserBetsVO> list = new ArrayList();
      PageList pList = this.uBetsDao.find(querySql.toString(), start, limit, false);
      for (Object tmpBean : pList.getList())
      {
        UserBetsVO tmpVO = new UserBetsVO((UserBets)tmpBean, this.lotteryDataFactory, false);
        list.add(tmpVO);
      }
      pList.setList(list);
      return pList;
    }
    return null;
  }
  
  public PageList searchHistory(String keyword, String username, Integer uypeu, Integer type, Integer lotteryId, String expect, Integer ruleId, String minTime, String maxTime, String minPrizeTime, String maxPrizeTime, Double minMoney, Double maxMoney, Integer minMultiple, Integer maxMultiple, Double minPrizeMoney, Double maxPrizeMoney, Integer status, Integer locked, int start, int limit)
  {
    List<Criterion> criterions = new ArrayList();
    List<Order> orders = new ArrayList();
    boolean isSearch = true;
    if (StringUtil.isNotNull(username))
    {
      User user = this.uDao.getByUsername(username);
      if (user != null) {
        criterions.add(Restrictions.eq("userId", Integer.valueOf(user.getId())));
      } else {
        isSearch = false;
      }
    }
    if (StringUtil.isNotNull(keyword))
    {
      Disjunction disjunction = Restrictions.or(new Criterion[0]);
      if (StringUtil.isInteger(keyword)) {
        disjunction.add(Restrictions.eq("id", Integer.valueOf(Integer.parseInt(keyword))));
      }
      disjunction.add(Restrictions.eq("billno", keyword));
      criterions.add(disjunction);
    }
    if (type != null) {
      criterions.add(Restrictions.eq("type", Integer.valueOf(type.intValue())));
    }
    if (lotteryId != null) {
      criterions.add(Restrictions.eq("lotteryId", Integer.valueOf(lotteryId.intValue())));
    }
    if (StringUtil.isNotNull(expect)) {
      criterions.add(Restrictions.eq("expect", expect));
    }
    if (ruleId != null) {
      criterions.add(Restrictions.eq("ruleId", ruleId));
    }
    if (StringUtil.isNotNull(minTime)) {
      criterions.add(Restrictions.gt("time", minTime));
    }
    if (StringUtil.isNotNull(maxTime)) {
      criterions.add(Restrictions.lt("time", maxTime));
    }
    if (StringUtil.isNotNull(minPrizeTime)) {
      criterions.add(Restrictions.gt("prizeTime", minPrizeTime));
    }
    if (StringUtil.isNotNull(maxPrizeTime)) {
      criterions.add(Restrictions.lt("prizeTime", maxPrizeTime));
    }
    if (minMoney != null) {
      criterions.add(Restrictions.ge("money", Double.valueOf(minMoney.doubleValue())));
    }
    if (maxMoney != null) {
      criterions.add(Restrictions.le("money", Double.valueOf(maxMoney.doubleValue())));
    }
    if (minMultiple != null) {
      criterions.add(Restrictions.ge("multiple", Integer.valueOf(minMultiple.intValue())));
    }
    if (maxMultiple != null) {
      criterions.add(Restrictions.le("multiple", Integer.valueOf(maxMultiple.intValue())));
    }
    if (minPrizeMoney != null) {
      criterions.add(Restrictions.ge("prizeMoney", Double.valueOf(minPrizeMoney.doubleValue())));
    }
    if (maxPrizeMoney != null) {
      criterions.add(Restrictions.le("prizeMoney", Double.valueOf(maxPrizeMoney.doubleValue())));
    }
    if (status != null) {
      criterions.add(Restrictions.eq("status", Integer.valueOf(status.intValue())));
    }
    if (locked != null) {
      criterions.add(Restrictions.eq("locked", Integer.valueOf(locked.intValue())));
    }
    criterions.add(Restrictions.gt("id", Integer.valueOf(0)));
    orders.add(Order.desc("time"));
    orders.add(Order.desc("id"));
    if (isSearch)
    {
      List<HistoryUserBetsVO> list = new ArrayList();
      PageList pList = this.uBetsDao.findHistory(criterions, orders, start, limit, false);
      for (Object tmpBean : pList.getList())
      {
        HistoryUserBetsVO tmpVO = new HistoryUserBetsVO((HistoryUserBets)tmpBean, this.lotteryDataFactory, false);
        list.add(tmpVO);
      }
      pList.setList(list);
      return pList;
    }
    return null;
  }
  
  public List<UserBets> notOpened(int lotteryId, Integer ruleId, String expect, String match)
  {
    List<Criterion> criterions = new ArrayList();
    criterions.add(Restrictions.eq("lotteryId", Integer.valueOf(lotteryId)));
    if (ruleId != null) {
      criterions.add(Restrictions.eq("ruleId", ruleId));
    }
    if (StringUtil.isNotNull(expect))
    {
      if ("eq".equals(match)) {
        criterions.add(Restrictions.eq("expect", expect));
      }
      if ("le".equals(match)) {
        criterions.add(Restrictions.le("expect", expect));
      }
      if ("ge".equals(match)) {
        criterions.add(Restrictions.ge("expect", expect));
      }
    }
    criterions.add(Restrictions.eq("status", Integer.valueOf(0)));
    List<Order> orders = new ArrayList();
    return this.uBetsDao.find(criterions, orders, false);
  }
  
  public UserBetsVO getById(int id)
  {
    UserBets entity = this.uBetsDao.getById(id);
    if (entity != null)
    {
      UserBetsVO bean = new UserBetsVO(entity, this.lotteryDataFactory, true);
      return bean;
    }
    return null;
  }
  
  public HistoryUserBetsVO getHistoryById(int id)
  {
    HistoryUserBets entity = this.uBetsDao.getHistoryById(id);
    if (entity != null)
    {
      HistoryUserBetsVO bean = new HistoryUserBetsVO(entity, this.lotteryDataFactory, true);
      return bean;
    }
    return null;
  }
  
  public boolean cancel(int id)
  {
    UserBets bBean = this.uBetsDao.getById(id);
    if (bBean != null) {
      return doCancelOrder(bBean);
    }
    return false;
  }
  
  public boolean cancel(int lotteryId, Integer ruleId, String expect, String match)
  {
    List<UserBets> list = notOpened(lotteryId, ruleId, expect, match);
    for (UserBets bBean : list) {
      doCancelOrder(bBean);
    }
    return true;
  }
  
  private synchronized boolean doCancelOrder(UserBets bBean)
  {
    if (bBean.getStatus() == 0)
    {
      boolean cFlag = this.uBetsDao.cancel(bBean.getId());
      if (cFlag)
      {
        User uBean = this.uDao.getById(bBean.getUserId());
        if (uBean != null)
        {
          boolean uFlag = this.uDao.updateLotteryMoney(uBean.getId(), bBean.getMoney(), -bBean.getMoney());
          if (uFlag)
          {
            cacheUserBetsId(bBean);
            this.uBillService.addCancelOrderBill(bBean, uBean);
          }
        }
      }
      return cFlag;
    }
    return false;
  }
  
  private void cacheUserBetsId(UserBets userBets)
  {
    String unOpenCacheKey = String.format("USER:BETS:UNOPEN:RECENT:%s", new Object[] { Integer.valueOf(userBets.getUserId()) });
    String openedCacheKey = String.format("USER:BETS:OPENED:RECENT:%s", new Object[] { Integer.valueOf(userBets.getUserId()) });
    int userBetsId = userBets.getId();
    try
    {

//      this.jedisTemplate.execute(new UserBetsServiceImpl.1(this, unOpenCacheKey, userBetsId, openedCacheKey));
    }
    catch (JedisException e)
    {
      log.error("执行Redis缓存注单ID时出错", e);
    }
  }
  
  public List<UserBetsVO> getSuspiciousOrder(int userId, int multiple)
  {
    List<UserBetsVO> formatList = new ArrayList();
    List<UserBets> list = this.uBetsDao.getSuspiciousOrder(userId, multiple, false);
    for (UserBets tmpBean : list) {
      formatList.add(new UserBetsVO(tmpBean, this.lotteryDataFactory, false));
    }
    return formatList;
  }
  
  public int countUserOnline(Date time)
  {
    String stime = DateUtil.calcDateByTime(DateUtil.dateToString(time), 64936);
    return this.uBetsDao.countUserOnline(stime);
  }
  
  public double[] getTotalMoney(String keyword, String username, Integer uype, Integer type, Integer lotteryId, String expect, Integer ruleId, String minTime, String maxTime, String minPrizeTime, String maxPrizeTime, Double minMoney, Double maxMoney, Integer minMultiple, Integer maxMultiple, Double minPrizeMoney, Double maxPrizeMoney, Integer status, Integer locked, String ip)
  {
    Integer userId = null;
    if (StringUtil.isNotNull(username))
    {
      User user = this.uDao.getByUsername(username);
      if (user != null) {
        userId = Integer.valueOf(user.getId());
      }
    }
    return this.uBetsDao.getTotalMoney(keyword, userId, uype, type, lotteryId, expect, ruleId, minTime, maxTime, 
      minPrizeTime, maxPrizeTime, minMoney, maxMoney, minMultiple, maxMultiple, minPrizeMoney, maxPrizeMoney, 
      status, locked, ip);
  }
  
  public double[] getHistoryTotalMoney(String keyword, String username, Integer utype, Integer type, Integer lotteryId, String expect, Integer ruleId, String minTime, String maxTime, String minPrizeTime, String maxPrizeTime, Double minMoney, Double maxMoney, Integer minMultiple, Integer maxMultiple, Double minPrizeMoney, Double maxPrizeMoney, Integer status, Integer locked)
  {
    Integer userId = null;
    if (StringUtil.isNotNull(username))
    {
      User user = this.uDao.getByUsername(username);
      if (user != null) {
        userId = Integer.valueOf(user.getId());
      }
    }
    return this.uBetsDao.getHistoryTotalMoney(keyword, userId, utype, type, lotteryId, expect, ruleId, minTime, maxTime, 
      minPrizeTime, maxPrizeTime, minMoney, maxMoney, minMultiple, maxMultiple, minPrizeMoney, maxPrizeMoney, 
      status, locked);
  }
  
  @Transactional(readOnly=true)
  public double getBillingOrder(int userId, String startTime, String endTime)
  {
    return this.uBetsDao.getBillingOrder(userId, startTime, endTime);
  }
  
  public UserBets getBetsById(int id)
  {
    return this.uBetsDao.getById(id);
  }
}
