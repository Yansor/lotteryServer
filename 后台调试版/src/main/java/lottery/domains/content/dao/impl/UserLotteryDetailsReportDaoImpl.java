package lottery.domains.content.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javautils.array.ArrayUtils;
import javautils.jdbc.hibernate.HibernateSuperDao;
import lottery.domains.content.dao.UserLotteryDetailsReportDao;
import lottery.domains.content.entity.HistoryUserLotteryDetailsReport;
import lottery.domains.content.entity.LotteryPlayRules;
import lottery.domains.content.entity.LotteryPlayRulesGroup;
import lottery.domains.content.entity.UserLotteryDetailsReport;
import lottery.domains.content.vo.bill.HistoryUserLotteryDetailsReportVO;
import lottery.domains.content.vo.bill.UserBetsReportVO;
import lottery.domains.content.vo.bill.UserLotteryDetailsReportVO;
import lottery.domains.pool.LotteryDataFactory;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserLotteryDetailsReportDaoImpl
  implements UserLotteryDetailsReportDao
{
  private final String tab = UserLotteryDetailsReport.class.getSimpleName();
  @Autowired
  private HibernateSuperDao<UserLotteryDetailsReport> superDao;
  @Autowired
  private LotteryDataFactory dataFactory;
  @Autowired
  private HibernateSuperDao<HistoryUserLotteryDetailsReport> historySuperDao;
  
  public boolean add(UserLotteryDetailsReport entity)
  {
    return this.superDao.save(entity);
  }
  
  public UserLotteryDetailsReport get(int userId, int lotteryId, int ruleId, String time)
  {
    String hql = "from " + this.tab + " where userId = ?0 and lotteryId = ?1 and ruleId = ?2 and time = ?3";
    Object[] values = { Integer.valueOf(userId), Integer.valueOf(lotteryId), Integer.valueOf(ruleId), time };
    return (UserLotteryDetailsReport)this.superDao.unique(hql, values);
  }
  
  public boolean update(UserLotteryDetailsReport entity)
  {
    String hql = "update " + this.tab + " set spend = spend + ?1, prize = prize + ?2, spendReturn = spendReturn + ?3, proxyReturn = proxyReturn + ?4, cancelOrder = cancelOrder + ?5, billingOrder = billingOrder + ?6 where id = ?0";
    Object[] values = { Integer.valueOf(entity.getId()), Double.valueOf(entity.getSpend()), Double.valueOf(entity.getPrize()), Double.valueOf(entity.getSpendReturn()), Double.valueOf(entity.getProxyReturn()), Double.valueOf(entity.getCancelOrder()), Double.valueOf(entity.getBillingOrder()) };
    return this.superDao.update(hql, values);
  }
  
  public List<UserLotteryDetailsReport> find(List<Criterion> criterions, List<Order> orders)
  {
    return this.superDao.findByCriteria(UserLotteryDetailsReport.class, criterions, orders);
  }
  
  public List<UserLotteryDetailsReportVO> sumLowersAndSelfByLottery(int userId, String sTime, String eTime)
  {
    String sql = "select uldr.lottery_id,l.show_name, sum(uldr.prize) prize,sum(uldr.spend_return) spendReturn,sum(uldr.proxy_return) proxyReturn,sum(uldr.billing_order) billingOrder from user_lottery_details_report uldr left join user u on uldr.user_id = u.id left join lottery l on uldr.lottery_id = l.id where uldr.time >= :sTime and uldr.time < :eTime and (u.upids like :upid or uldr.user_id = :userId) group by uldr.lottery_id,l.show_name";
    Map<String, Object> params = new HashMap();
    params.put("sTime", sTime);
    params.put("eTime", eTime);
    params.put("upid", "%[" + userId + "]%");
    params.put("userId", Integer.valueOf(userId));
    
    List<?> arrs = this.superDao.listBySql(sql, params);
    if (CollectionUtils.isEmpty(arrs)) {
      return new ArrayList();
    }
    List<UserLotteryDetailsReportVO> reports = new ArrayList(arrs.size());
    for (Object arr : arrs)
    {
      Object[] objects = (Object[])arr;
      int lotteryId = objects[0] == null ? 0 : ((Integer)objects[0]).intValue();
      String lotteryName = objects[1].toString();
      double prize = objects[2] == null ? 0.0D : ((BigDecimal)objects[2]).doubleValue();
      double spendReturn = objects[3] == null ? 0.0D : ((BigDecimal)objects[3]).doubleValue();
      double proxyReturn = objects[4] == null ? 0.0D : ((BigDecimal)objects[4]).doubleValue();
      double billingOrder = objects[5] == null ? 0.0D : ((BigDecimal)objects[5]).doubleValue();
      
      UserLotteryDetailsReportVO report = new UserLotteryDetailsReportVO();
      report.setName(lotteryName);
      report.setKey(lotteryId+"");
      report.setPrize(prize);
      report.setSpendReturn(spendReturn);
      report.setProxyReturn(proxyReturn);
      report.setBillingOrder(billingOrder);
      
      reports.add(report);
    }
    return reports;
  }
  
  public List<HistoryUserLotteryDetailsReportVO> historySumLowersAndSelfByLottery(int userId, String sTime, String eTime)
  {
    String sql = "select uldr.lottery_id,l.show_name, sum(uldr.prize) prize,sum(uldr.spend_return) spendReturn,sum(uldr.proxy_return) proxyReturn,sum(uldr.billing_order) billingOrder from user_lottery_details_report uldr left join user u on uldr.user_id = u.id left join lottery l on uldr.lottery_id = l.id where uldr.time >= :sTime and uldr.time < :eTime and (u.upids like :upid or uldr.user_id = :userId) group by uldr.lottery_id,l.show_name";
    Map<String, Object> params = new HashMap();
    params.put("sTime", sTime);
    params.put("eTime", eTime);
    params.put("upid", "%[" + userId + "]%");
    params.put("userId", Integer.valueOf(userId));
    
    List<?> arrs = this.historySuperDao.listBySql(sql, params);
    if (CollectionUtils.isEmpty(arrs)) {
      return new ArrayList();
    }
    List<HistoryUserLotteryDetailsReportVO> reports = new ArrayList(arrs.size());
    for (Object arr : arrs)
    {
      Object[] objects = (Object[])arr;
      int lotteryId = objects[0] == null ? 0 : ((Integer)objects[0]).intValue();
      String lotteryName = objects[1].toString();
      double prize = objects[2] == null ? 0.0D : ((BigDecimal)objects[2]).doubleValue();
      double spendReturn = objects[3] == null ? 0.0D : ((BigDecimal)objects[3]).doubleValue();
      double proxyReturn = objects[4] == null ? 0.0D : ((BigDecimal)objects[4]).doubleValue();
      double billingOrder = objects[5] == null ? 0.0D : ((BigDecimal)objects[5]).doubleValue();
      
      HistoryUserLotteryDetailsReportVO report = new HistoryUserLotteryDetailsReportVO();
      report.setName(lotteryName);
      report.setKey(lotteryId+"");
      report.setPrize(prize);
      report.setSpendReturn(spendReturn);
      report.setProxyReturn(proxyReturn);
      report.setBillingOrder(billingOrder);
      
      reports.add(report);
    }
    return reports;
  }
  
  public List<UserLotteryDetailsReportVO> sumLowersAndSelfByRule(int userId, int lotteryId, String sTime, String eTime)
  {
    String sql = "select uldr.rule_id, sum(uldr.prize) prize,sum(uldr.spend_return) spendReturn,sum(uldr.proxy_return) proxyReturn,sum(uldr.billing_order) billingOrder from user_lottery_details_report uldr left join user u on uldr.user_id = u.id where uldr.time >= :sTime and uldr.time < :eTime and (u.upids like :upid or uldr.user_id = :userId) and uldr.lottery_id = :lotteryId group by uldr.rule_id";
    Map<String, Object> params = new HashMap();
    params.put("sTime", sTime);
    params.put("eTime", eTime);
    params.put("upid", "%[" + userId + "]%");
    params.put("userId", Integer.valueOf(userId));
    params.put("lotteryId", Integer.valueOf(lotteryId));
    
    List<?> arrs = this.superDao.listBySql(sql, params);
    if (CollectionUtils.isEmpty(arrs)) {
      return new ArrayList();
    }
    List<UserLotteryDetailsReportVO> reports = new ArrayList(arrs.size());
    for (Object arr : arrs)
    {
      int index = 0;
      
      Object[] objects = (Object[])arr;
      int ruleId = Integer.valueOf(objects[index].toString()).intValue();index++;
      double prize = objects[index] == null ? 0.0D : ((BigDecimal)objects[index]).doubleValue();index++;
      double spendReturn = objects[index] == null ? 0.0D : ((BigDecimal)objects[index]).doubleValue();index++;
      double proxyReturn = objects[index] == null ? 0.0D : ((BigDecimal)objects[index]).doubleValue();index++;
      double billingOrder = objects[index] == null ? 0.0D : ((BigDecimal)objects[index]).doubleValue();
      
      UserLotteryDetailsReportVO report = new UserLotteryDetailsReportVO();
      LotteryPlayRules rule = this.dataFactory.getLotteryPlayRules(ruleId);
      if (rule != null)
      {
        LotteryPlayRulesGroup group = this.dataFactory.getLotteryPlayRulesGroup(rule.getGroupId());
        if (group != null)
        {
          report.setName(group.getName() + "_" + rule.getName());
          report.setKey(ruleId+"");
          report.setPrize(prize);
          report.setSpendReturn(spendReturn);
          report.setProxyReturn(proxyReturn);
          report.setBillingOrder(billingOrder);
          
          reports.add(report);
        }
      }
    }
    return reports;
  }
  
  public List<HistoryUserLotteryDetailsReportVO> historySumLowersAndSelfByRule(int userId, int lotteryId, String sTime, String eTime)
  {
    String sql = "select uldr.rule_id, sum(uldr.prize) prize,sum(uldr.spend_return) spendReturn,sum(uldr.proxy_return) proxyReturn,sum(uldr.billing_order) billingOrder from ecaibackup.user_lottery_details_report uldr left join ecai.user u on uldr.user_id = u.id where uldr.time >= :sTime and uldr.time < :eTime and (u.upids like :upid or uldr.user_id = :userId) and uldr.lottery_id = :lotteryId group by uldr.rule_id";
    Map<String, Object> params = new HashMap();
    params.put("sTime", sTime);
    params.put("eTime", eTime);
    params.put("upid", "%[" + userId + "]%");
    params.put("userId", Integer.valueOf(userId));
    params.put("lotteryId", Integer.valueOf(lotteryId));
    
    List<?> arrs = this.historySuperDao.listBySql(sql, params);
    if (CollectionUtils.isEmpty(arrs)) {
      return new ArrayList();
    }
    List<HistoryUserLotteryDetailsReportVO> reports = new ArrayList(arrs.size());
    for (Object arr : arrs)
    {
      int index = 0;
      
      Object[] objects = (Object[])arr;
      int ruleId = Integer.valueOf(objects[index].toString()).intValue();index++;
      double prize = objects[index] == null ? 0.0D : ((BigDecimal)objects[index]).doubleValue();index++;
      double spendReturn = objects[index] == null ? 0.0D : ((BigDecimal)objects[index]).doubleValue();index++;
      double proxyReturn = objects[index] == null ? 0.0D : ((BigDecimal)objects[index]).doubleValue();index++;
      double billingOrder = objects[index] == null ? 0.0D : ((BigDecimal)objects[index]).doubleValue();
      
      HistoryUserLotteryDetailsReportVO report = new HistoryUserLotteryDetailsReportVO();
      LotteryPlayRules rule = this.dataFactory.getLotteryPlayRules(ruleId);
      if (rule != null)
      {
        LotteryPlayRulesGroup group = this.dataFactory.getLotteryPlayRulesGroup(rule.getGroupId());
        if (group != null)
        {
          report.setName(group.getName() + "_" + rule.getName());
          report.setKey(ruleId+"");
          report.setPrize(prize);
          report.setSpendReturn(spendReturn);
          report.setProxyReturn(proxyReturn);
          report.setBillingOrder(billingOrder);
          
          reports.add(report);
        }
      }
    }
    return reports;
  }
  
  public List<UserLotteryDetailsReportVO> sumSelfByLottery(int userId, String sTime, String eTime)
  {
    String sql = "select uldr.lottery_id,l.show_name, sum(uldr.prize) prize,sum(uldr.spend_return) spendReturn,sum(uldr.proxy_return) proxyReturn,sum(uldr.billing_order) billingOrder from user_lottery_details_report uldr left join user u on uldr.user_id = u.id left join lottery l on uldr.lottery_id = l.id where uldr.time >= :sTime and uldr.time < :eTime and uldr.user_id = :userId group by uldr.lottery_id,l.show_name";
    Map<String, Object> params = new HashMap();
    params.put("sTime", sTime);
    params.put("eTime", eTime);
    params.put("userId", Integer.valueOf(userId));
    
    List<?> arrs = this.superDao.listBySql(sql, params);
    if (CollectionUtils.isEmpty(arrs)) {
      return new ArrayList();
    }
    List<UserLotteryDetailsReportVO> reports = new ArrayList(arrs.size());
    for (Object arr : arrs)
    {
      Object[] objects = (Object[])arr;
      int lotteryId = objects[0] == null ? 0 : ((Integer)objects[0]).intValue();
      String lotteryName = objects[1].toString();
      double prize = objects[2] == null ? 0.0D : ((BigDecimal)objects[2]).doubleValue();
      double spendReturn = objects[3] == null ? 0.0D : ((BigDecimal)objects[3]).doubleValue();
      double proxyReturn = objects[4] == null ? 0.0D : ((BigDecimal)objects[4]).doubleValue();
      double billingOrder = objects[5] == null ? 0.0D : ((BigDecimal)objects[5]).doubleValue();
      
      UserLotteryDetailsReportVO report = new UserLotteryDetailsReportVO();
      report.setName(lotteryName);
      report.setKey(lotteryId+"");
      report.setPrize(prize);
      report.setSpendReturn(spendReturn);
      report.setProxyReturn(proxyReturn);
      report.setBillingOrder(billingOrder);
      
      reports.add(report);
    }
    return reports;
  }
  
  public List<HistoryUserLotteryDetailsReportVO> historySumSelfByLottery(int userId, String sTime, String eTime)
  {
    String sql = "select uldr.lottery_id,l.show_name, sum(uldr.prize) prize,sum(uldr.spend_return) spendReturn,sum(uldr.proxy_return) proxyReturn,sum(uldr.billing_order) billingOrder from ecaibackup.user_lottery_details_report uldr left join ecai.user u on uldr.user_id = u.id left join lottery l on uldr.lottery_id = l.id where uldr.time >= :sTime and uldr.time < :eTime and uldr.user_id = :userId group by uldr.lottery_id,l.show_name";
    Map<String, Object> params = new HashMap();
    params.put("sTime", sTime);
    params.put("eTime", eTime);
    params.put("userId", Integer.valueOf(userId));
    
    List<?> arrs = this.historySuperDao.listBySql(sql, params);
    if (CollectionUtils.isEmpty(arrs)) {
      return new ArrayList();
    }
    List<HistoryUserLotteryDetailsReportVO> reports = new ArrayList(arrs.size());
    for (Object arr : arrs)
    {
      Object[] objects = (Object[])arr;
      int lotteryId = objects[0] == null ? 0 : ((Integer)objects[0]).intValue();
      String lotteryName = objects[1].toString();
      double prize = objects[2] == null ? 0.0D : ((BigDecimal)objects[2]).doubleValue();
      double spendReturn = objects[3] == null ? 0.0D : ((BigDecimal)objects[3]).doubleValue();
      double proxyReturn = objects[4] == null ? 0.0D : ((BigDecimal)objects[4]).doubleValue();
      double billingOrder = objects[5] == null ? 0.0D : ((BigDecimal)objects[5]).doubleValue();
      
      HistoryUserLotteryDetailsReportVO report = new HistoryUserLotteryDetailsReportVO();
      report.setName(lotteryName);
      report.setKey(lotteryId+"");
      report.setPrize(prize);
      report.setSpendReturn(spendReturn);
      report.setProxyReturn(proxyReturn);
      report.setBillingOrder(billingOrder);
      
      reports.add(report);
    }
    return reports;
  }
  
  public List<UserLotteryDetailsReportVO> sumSelfByRule(int userId, int lotteryId, String sTime, String eTime)
  {
    String sql = "select uldr.rule_id, sum(uldr.prize) prize,sum(uldr.spend_return) spendReturn,sum(uldr.proxy_return) proxyReturn,sum(uldr.billing_order) billingOrder from user_lottery_details_report uldr left join user u on uldr.user_id = u.id where uldr.time >= :sTime and uldr.time < :eTime and uldr.user_id = :userId and uldr.lottery_id = :lotteryId group by uldr.rule_id";
    Map<String, Object> params = new HashMap();
    params.put("sTime", sTime);
    params.put("eTime", eTime);
    params.put("userId", Integer.valueOf(userId));
    params.put("lotteryId", Integer.valueOf(lotteryId));
    
    List<?> arrs = this.superDao.listBySql(sql, params);
    if (CollectionUtils.isEmpty(arrs)) {
      return new ArrayList();
    }
    List<UserLotteryDetailsReportVO> reports = new ArrayList(arrs.size());
    for (Object arr : arrs)
    {
      int index = 0;
      
      Object[] objects = (Object[])arr;
      int ruleId = Integer.valueOf(objects[index].toString()).intValue();index++;
      double prize = objects[index] == null ? 0.0D : ((BigDecimal)objects[index]).doubleValue();index++;
      double spendReturn = objects[index] == null ? 0.0D : ((BigDecimal)objects[index]).doubleValue();index++;
      double proxyReturn = objects[index] == null ? 0.0D : ((BigDecimal)objects[index]).doubleValue();index++;
      double billingOrder = objects[index] == null ? 0.0D : ((BigDecimal)objects[index]).doubleValue();
      
      UserLotteryDetailsReportVO report = new UserLotteryDetailsReportVO();
      LotteryPlayRules rule = this.dataFactory.getLotteryPlayRules(ruleId);
      if (rule != null)
      {
        LotteryPlayRulesGroup group = this.dataFactory.getLotteryPlayRulesGroup(rule.getGroupId());
        if (group != null)
        {
          report.setName(group.getName() + "_" + rule.getName());
          report.setKey(ruleId+"");
          report.setPrize(prize);
          report.setSpendReturn(spendReturn);
          report.setProxyReturn(proxyReturn);
          report.setBillingOrder(billingOrder);
          
          reports.add(report);
        }
      }
    }
    return reports;
  }
  
  public List<HistoryUserLotteryDetailsReportVO> historySumSelfByRule(int userId, int lotteryId, String sTime, String eTime)
  {
    String sql = "select uldr.rule_id, sum(uldr.prize) prize,sum(uldr.spend_return) spendReturn,sum(uldr.proxy_return) proxyReturn,sum(uldr.billing_order) billingOrder from ecaibackup.user_lottery_details_report uldr left join ecai.user u on uldr.user_id = u.id where uldr.time >= :sTime and uldr.time < :eTime and uldr.user_id = :userId and uldr.lottery_id = :lotteryId group by uldr.rule_id";
    Map<String, Object> params = new HashMap();
    params.put("sTime", sTime);
    params.put("eTime", eTime);
    params.put("userId", Integer.valueOf(userId));
    params.put("lotteryId", Integer.valueOf(lotteryId));
    
    List<?> arrs = this.historySuperDao.listBySql(sql, params);
    if (CollectionUtils.isEmpty(arrs)) {
      return new ArrayList();
    }
    List<HistoryUserLotteryDetailsReportVO> reports = new ArrayList(arrs.size());
    for (Object arr : arrs)
    {
      int index = 0;
      
      Object[] objects = (Object[])arr;
      int ruleId = Integer.valueOf(objects[index].toString()).intValue();index++;
      double prize = objects[index] == null ? 0.0D : ((BigDecimal)objects[index]).doubleValue();index++;
      double spendReturn = objects[index] == null ? 0.0D : ((BigDecimal)objects[index]).doubleValue();index++;
      double proxyReturn = objects[index] == null ? 0.0D : ((BigDecimal)objects[index]).doubleValue();index++;
      double billingOrder = objects[index] == null ? 0.0D : ((BigDecimal)objects[index]).doubleValue();
      
      HistoryUserLotteryDetailsReportVO report = new HistoryUserLotteryDetailsReportVO();
      LotteryPlayRules rule = this.dataFactory.getLotteryPlayRules(ruleId);
      if (rule != null)
      {
        LotteryPlayRulesGroup group = this.dataFactory.getLotteryPlayRulesGroup(rule.getGroupId());
        if (group != null)
        {
          report.setName(group.getName() + "_" + rule.getName());
          report.setKey(ruleId+"");
          report.setPrize(prize);
          report.setSpendReturn(spendReturn);
          report.setProxyReturn(proxyReturn);
          report.setBillingOrder(billingOrder);
          
          reports.add(report);
        }
      }
    }
    return reports;
  }
  
  public List<UserBetsReportVO> sumUserBets(List<Integer> lotteryIds, Integer ruleId, String sTime, String eTime)
  {
    Map<String, Object> params = new HashMap();
    String sql = "select uldr.`time`, sum(uldr.prize) prize,sum(uldr.spend_return+uldr.proxy_return) returnMoney,sum(uldr.billing_order) billingOrder from user_lottery_details_report uldr ,user u where uldr.user_id = u.id and u.upid !=0  and  uldr.`time` >= :sTime and uldr.`time` < :eTime";
    if (ruleId != null)
    {
      sql = sql + " and uldr.rule_id = :ruleId";
      params.put("ruleId", ruleId);
    }
    if (CollectionUtils.isNotEmpty(lotteryIds)) {
      sql = sql + " and uldr.lottery_id in (" + ArrayUtils.toString(lotteryIds) + ")";
    }
    sql = sql + " group by uldr.`time`";
    params.put("sTime", sTime);
    params.put("eTime", eTime);
    
    List<?> arrs = this.superDao.listBySql(sql, params);
    if (CollectionUtils.isEmpty(arrs)) {
      return new ArrayList();
    }
    List<UserBetsReportVO> reports = new ArrayList(arrs.size());
    for (Object arr : arrs)
    {
      Object[] objects = (Object[])arr;
      String time = objects[0].toString();
      double prize = objects[1] == null ? 0.0D : ((BigDecimal)objects[1]).doubleValue();
      double returnMoney = objects[2] == null ? 0.0D : ((BigDecimal)objects[2]).doubleValue();
      double billingOrder = objects[3] == null ? 0.0D : ((BigDecimal)objects[3]).doubleValue();
      
      UserBetsReportVO report = new UserBetsReportVO();
      report.setField(time);
      report.setMoney(billingOrder);
      report.setPrizeMoney(prize);
      report.setReturnMoney(returnMoney);
      
      reports.add(report);
    }
    return reports;
  }
}
