package lottery.domains.content.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javautils.jdbc.hibernate.HibernateSuperDao;
import lottery.domains.content.dao.UserGameReportDao;
import lottery.domains.content.entity.HistoryUserGameReport;
import lottery.domains.content.entity.UserGameReport;
import lottery.domains.content.vo.bill.HistoryUserGameReportVO;
import lottery.domains.content.vo.bill.UserGameReportVO;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserGameReportDaoImpl
  implements UserGameReportDao
{
  private final String tab = UserGameReport.class.getSimpleName();
  @Autowired
  private HibernateSuperDao<UserGameReport> superDao;
  @Autowired
  private HibernateSuperDao<HistoryUserGameReport> historySuperDao;
  private final String hsInstance = "ecai";
  private final String hsbackupInstance = "ecaibackup";
  
  public boolean save(UserGameReport entity)
  {
    return this.superDao.save(entity);
  }
  
  public UserGameReport get(int userId, int platformId, String time)
  {
    String hql = "from " + this.tab + " where userId = ?0 and platformId=?1 and time = ?2";
    Object[] values = { Integer.valueOf(userId), Integer.valueOf(platformId), time };
    return (UserGameReport)this.superDao.unique(hql, values);
  }
  
  public boolean update(UserGameReport entity)
  {
    String hql = "update " + this.tab + " set transIn = transIn + ?1, transOut = transOut + ?2, waterReturn = waterReturn + ?3, proxyReturn = proxyReturn + ?4, activity = activity + ?5, billingOrder = billingOrder + ?6, prize = prize + ?7  where id = ?0";
    Object[] values = { Integer.valueOf(entity.getId()), Double.valueOf(entity.getTransIn()), Double.valueOf(entity.getTransOut()), Double.valueOf(entity.getWaterReturn()), Double.valueOf(entity.getProxyReturn()), Double.valueOf(entity.getActivity()), Double.valueOf(entity.getBillingOrder()), Double.valueOf(entity.getPrize()) };
    return this.superDao.update(hql, values);
  }
  
  public List<UserGameReport> list(int userId, String sTime, String eTime)
  {
    String hql = "from " + this.tab + " where userId = ?0 and time >= ?1 and time < ?2";
    Object[] values = { Integer.valueOf(userId), sTime, eTime };
    return this.superDao.list(hql, values);
  }
  
  public List<UserGameReport> find(List<Criterion> criterions, List<Order> orders)
  {
    return this.superDao.findByCriteria(UserGameReport.class, criterions, orders);
  }
  
  public List<HistoryUserGameReport> findHistory(List<Criterion> criterions, List<Order> orders)
  {
    return this.historySuperDao.findByCriteria(HistoryUserGameReport.class, criterions, orders);
  }
  
  public List<UserGameReportVO> reportByUser(String sTime, String eTime)
  {
    String hql = "select userId,sum(transIn),sum(transOut),sum(waterReturn),sum(proxyReturn),sum(activity),sum(billingOrder),sum(prize),time from " + 
      this.tab + " where time >= ?0 and time < ?1 and user_id <> 72 group by userId,time";
    Object[] values = { sTime, eTime };
    List<Object[]> result = (List<Object[]>)this.superDao.listObject(hql, values);
    
    List<UserGameReportVO> reports = new ArrayList(result.size());
    for (Object[] objects : result)
    {
      UserGameReportVO reportVO = new UserGameReportVO();
      reportVO.setUserId(Integer.valueOf(objects[0].toString()).intValue());
      reportVO.setTransIn(Double.valueOf(objects[1].toString()).doubleValue());
      reportVO.setTransOut(Double.valueOf(objects[2].toString()).doubleValue());
      reportVO.setWaterReturn(Double.valueOf(objects[3].toString()).doubleValue());
      reportVO.setProxyReturn(Double.valueOf(objects[4].toString()).doubleValue());
      reportVO.setActivity(Double.valueOf(objects[5].toString()).doubleValue());
      reportVO.setBillingOrder(Double.valueOf(objects[6].toString()).doubleValue());
      reportVO.setPrize(Double.valueOf(objects[7].toString()).doubleValue());
      reportVO.setTime(objects[8].toString());
      reports.add(reportVO);
    }
    return reports;
  }
  
  public UserGameReportVO sumLowersAndSelf(int userId, String sTime, String eTime)
  {
    String sql = "select sum(ugr.trans_in) transIn,sum(ugr.trans_out) transOut,sum(ugr.prize) prize,sum(ugr.water_return) waterReturn,sum(ugr.proxy_return) proxyReturn,sum(ugr.activity) activity,sum(ugr.billing_order) billingOrder from user_game_report ugr left join user u on ugr.user_id = u.id where ugr.time >= :sTime and ugr.time < :eTime and (u.upids like :upid or ugr.user_id = :userId)";
    Map<String, Object> params = new HashMap();
    params.put("sTime", sTime);
    params.put("eTime", eTime);
    params.put("upid", "%[" + userId + "]%");
    params.put("userId", Integer.valueOf(userId));
    
    Object result = this.superDao.uniqueSqlWithParams(sql, params);
    if (result == null) {
      return null;
    }
    Object[] results = (Object[])result;
    double transIn = results[0] == null ? 0.0D : ((BigDecimal)results[0]).doubleValue();
    double transOut = results[1] == null ? 0.0D : ((BigDecimal)results[1]).doubleValue();
    double prize = results[2] == null ? 0.0D : ((BigDecimal)results[2]).doubleValue();
    double waterReturn = results[3] == null ? 0.0D : ((BigDecimal)results[3]).doubleValue();
    double proxyReturn = results[4] == null ? 0.0D : ((BigDecimal)results[4]).doubleValue();
    double activity = results[5] == null ? 0.0D : ((BigDecimal)results[5]).doubleValue();
    double billingOrder = results[6] == null ? 0.0D : ((BigDecimal)results[6]).doubleValue();
    
    UserGameReportVO report = new UserGameReportVO();
    report.setTransIn(transIn);
    report.setTransOut(transOut);
    report.setPrize(prize);
    report.setWaterReturn(waterReturn);
    report.setProxyReturn(proxyReturn);
    report.setActivity(activity);
    report.setBillingOrder(billingOrder);
    return report;
  }
  
  public HistoryUserGameReportVO historySumLowersAndSelf(int userId, String sTime, String eTime)
  {
    String sql = "select sum(ugr.trans_in) transIn,sum(ugr.trans_out) transOut,sum(ugr.prize) prize,sum(ugr.water_return) waterReturn,sum(ugr.proxy_return) proxyReturn,sum(ugr.activity) activity,sum(ugr.billing_order) billingOrder from ecaibackup.user_game_report ugr left join ecai.user u on ugr.user_id = u.id where ugr.time >= :sTime and ugr.time < :eTime and (u.upids like :upid or ugr.user_id = :userId)";
    Map<String, Object> params = new HashMap();
    params.put("sTime", sTime);
    params.put("eTime", eTime);
    params.put("upid", "%[" + userId + "]%");
    params.put("userId", Integer.valueOf(userId));
    
    Object result = this.historySuperDao.uniqueSqlWithParams(sql, params);
    if (result == null) {
      return null;
    }
    Object[] results = (Object[])result;
    double transIn = results[0] == null ? 0.0D : ((BigDecimal)results[0]).doubleValue();
    double transOut = results[1] == null ? 0.0D : ((BigDecimal)results[1]).doubleValue();
    double prize = results[2] == null ? 0.0D : ((BigDecimal)results[2]).doubleValue();
    double waterReturn = results[3] == null ? 0.0D : ((BigDecimal)results[3]).doubleValue();
    double proxyReturn = results[4] == null ? 0.0D : ((BigDecimal)results[4]).doubleValue();
    double activity = results[5] == null ? 0.0D : ((BigDecimal)results[5]).doubleValue();
    double billingOrder = results[6] == null ? 0.0D : ((BigDecimal)results[6]).doubleValue();
    
    HistoryUserGameReportVO report = new HistoryUserGameReportVO();
    report.setTransIn(transIn);
    report.setTransOut(transOut);
    report.setPrize(prize);
    report.setWaterReturn(waterReturn);
    report.setProxyReturn(proxyReturn);
    report.setActivity(activity);
    report.setBillingOrder(billingOrder);
    return report;
  }
  
  public UserGameReportVO sum(int userId, String sTime, String eTime)
  {
    String sql = "select sum(ugr.trans_in) transIn,sum(ugr.trans_out) transOut,sum(ugr.prize) prize,sum(ugr.spend_return) spendReturn,sum(ugr.proxy_return) proxyReturn,sum(ugr.activity) activity,sum(ugr.billing_order) billingOrder from user_game_report ugr left join user u on ugr.user_id = u.id where ugr.time >= :sTime and ugr.time < :eTime and ugr.user_id = :userId";
    Map<String, Object> params = new HashMap();
    params.put("sTime", sTime);
    params.put("eTime", eTime);
    params.put("userId", Integer.valueOf(userId));
    Object result = this.superDao.uniqueSqlWithParams(sql, params);
    if (result == null) {
      return null;
    }
    Object[] results = (Object[])result;
    double transIn = results[0] == null ? 0.0D : ((BigDecimal)results[0]).doubleValue();
    double transOut = results[1] == null ? 0.0D : ((BigDecimal)results[1]).doubleValue();
    double prize = results[2] == null ? 0.0D : ((BigDecimal)results[2]).doubleValue();
    double waterReturn = results[3] == null ? 0.0D : ((BigDecimal)results[3]).doubleValue();
    double proxyReturn = results[4] == null ? 0.0D : ((BigDecimal)results[4]).doubleValue();
    double activity = results[5] == null ? 0.0D : ((BigDecimal)results[5]).doubleValue();
    double billingOrder = results[6] == null ? 0.0D : ((BigDecimal)results[6]).doubleValue();
    
    UserGameReportVO report = new UserGameReportVO();
    report.setTransIn(transIn);
    report.setTransOut(transOut);
    report.setPrize(prize);
    report.setWaterReturn(waterReturn);
    report.setProxyReturn(proxyReturn);
    report.setActivity(activity);
    report.setBillingOrder(billingOrder);
    return report;
  }
}
