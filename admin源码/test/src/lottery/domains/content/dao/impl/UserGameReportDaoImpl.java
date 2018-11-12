package lottery.domains.content.dao.impl;

import lottery.domains.content.vo.bill.HistoryUserGameReportVO;
import java.util.Map;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ArrayList;
import lottery.domains.content.vo.bill.UserGameReportVO;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.List;
import lottery.domains.content.entity.HistoryUserGameReport;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.entity.UserGameReport;
import javautils.jdbc.hibernate.HibernateSuperDao;
import org.springframework.stereotype.Repository;
import lottery.domains.content.dao.UserGameReportDao;

@Repository
public class UserGameReportDaoImpl implements UserGameReportDao
{
    private final String tab;
    @Autowired
    private HibernateSuperDao<UserGameReport> superDao;
    @Autowired
    private HibernateSuperDao<HistoryUserGameReport> historySuperDao;
    private final String hsInstance = "ecai";
    private final String hsbackupInstance = "ecaibackup";
    
    public UserGameReportDaoImpl() {
        this.tab = UserGameReport.class.getSimpleName();
    }
    
    @Override
    public boolean save(final UserGameReport entity) {
        return this.superDao.save(entity);
    }
    
    @Override
    public UserGameReport get(final int userId, final int platformId, final String time) {
        final String hql = "from " + this.tab + " where userId = ?0 and platformId=?1 and time = ?2";
        final Object[] values = { userId, platformId, time };
        return (UserGameReport)this.superDao.unique(hql, values);
    }
    
    @Override
    public boolean update(final UserGameReport entity) {
        final String hql = "update " + this.tab + " set transIn = transIn + ?1, transOut = transOut + ?2, waterReturn = waterReturn + ?3, proxyReturn = proxyReturn + ?4, activity = activity + ?5, billingOrder = billingOrder + ?6, prize = prize + ?7  where id = ?0";
        final Object[] values = { entity.getId(), entity.getTransIn(), entity.getTransOut(), entity.getWaterReturn(), entity.getProxyReturn(), entity.getActivity(), entity.getBillingOrder(), entity.getPrize() };
        return this.superDao.update(hql, values);
    }
    
    @Override
    public List<UserGameReport> list(final int userId, final String sTime, final String eTime) {
        final String hql = "from " + this.tab + " where userId = ?0 and time >= ?1 and time < ?2";
        final Object[] values = { userId, sTime, eTime };
        return this.superDao.list(hql, values);
    }
    
    @Override
    public List<UserGameReport> find(final List<Criterion> criterions, final List<Order> orders) {
        return this.superDao.findByCriteria(UserGameReport.class, criterions, orders);
    }
    
    @Override
    public List<HistoryUserGameReport> findHistory(final List<Criterion> criterions, final List<Order> orders) {
        return this.historySuperDao.findByCriteria(HistoryUserGameReport.class, criterions, orders);
    }
    
    @Override
    public List<UserGameReportVO> reportByUser(final String sTime, final String eTime) {
        final String hql = "select userId,sum(transIn),sum(transOut),sum(waterReturn),sum(proxyReturn),sum(activity),sum(billingOrder),sum(prize),time from " + this.tab + " where time >= ?0 and time < ?1 and user_id <> 72 group by userId,time";
        final Object[] values = { sTime, eTime };
        final List<Object[]> result = (List<Object[]>)this.superDao.listObject(hql, values);
        final List<UserGameReportVO> reports = new ArrayList<UserGameReportVO>(result.size());
        for (final Object[] objects : result) {
            final UserGameReportVO reportVO = new UserGameReportVO();
            reportVO.setUserId(Integer.valueOf(objects[0].toString()));
            reportVO.setTransIn(Double.valueOf(objects[1].toString()));
            reportVO.setTransOut(Double.valueOf(objects[2].toString()));
            reportVO.setWaterReturn(Double.valueOf(objects[3].toString()));
            reportVO.setProxyReturn(Double.valueOf(objects[4].toString()));
            reportVO.setActivity(Double.valueOf(objects[5].toString()));
            reportVO.setBillingOrder(Double.valueOf(objects[6].toString()));
            reportVO.setPrize(Double.valueOf(objects[7].toString()));
            reportVO.setTime(objects[8].toString());
            reports.add(reportVO);
        }
        return reports;
    }
    
    @Override
    public UserGameReportVO sumLowersAndSelf(final int userId, final String sTime, final String eTime) {
        final String sql = "select sum(ugr.trans_in) transIn,sum(ugr.trans_out) transOut,sum(ugr.prize) prize,sum(ugr.water_return) waterReturn,sum(ugr.proxy_return) proxyReturn,sum(ugr.activity) activity,sum(ugr.billing_order) billingOrder from user_game_report ugr left join user u on ugr.user_id = u.id where ugr.time >= :sTime and ugr.time < :eTime and (u.upids like :upid or ugr.user_id = :userId)";
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("sTime", sTime);
        params.put("eTime", eTime);
        params.put("upid", "%[" + userId + "]%");
        params.put("userId", userId);
        final Object result = this.superDao.uniqueSqlWithParams(sql, params);
        if (result == null) {
            return null;
        }
        final Object[] results = (Object[])result;
        final double transIn = (results[0] == null) ? 0.0 : ((BigDecimal)results[0]).doubleValue();
        final double transOut = (results[1] == null) ? 0.0 : ((BigDecimal)results[1]).doubleValue();
        final double prize = (results[2] == null) ? 0.0 : ((BigDecimal)results[2]).doubleValue();
        final double waterReturn = (results[3] == null) ? 0.0 : ((BigDecimal)results[3]).doubleValue();
        final double proxyReturn = (results[4] == null) ? 0.0 : ((BigDecimal)results[4]).doubleValue();
        final double activity = (results[5] == null) ? 0.0 : ((BigDecimal)results[5]).doubleValue();
        final double billingOrder = (results[6] == null) ? 0.0 : ((BigDecimal)results[6]).doubleValue();
        final UserGameReportVO report = new UserGameReportVO();
        report.setTransIn(transIn);
        report.setTransOut(transOut);
        report.setPrize(prize);
        report.setWaterReturn(waterReturn);
        report.setProxyReturn(proxyReturn);
        report.setActivity(activity);
        report.setBillingOrder(billingOrder);
        return report;
    }
    
    @Override
    public HistoryUserGameReportVO historySumLowersAndSelf(final int userId, final String sTime, final String eTime) {
        final String sql = "select sum(ugr.trans_in) transIn,sum(ugr.trans_out) transOut,sum(ugr.prize) prize,sum(ugr.water_return) waterReturn,sum(ugr.proxy_return) proxyReturn,sum(ugr.activity) activity,sum(ugr.billing_order) billingOrder from ecaibackup.user_game_report ugr left join ecai.user u on ugr.user_id = u.id where ugr.time >= :sTime and ugr.time < :eTime and (u.upids like :upid or ugr.user_id = :userId)";
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("sTime", sTime);
        params.put("eTime", eTime);
        params.put("upid", "%[" + userId + "]%");
        params.put("userId", userId);
        final Object result = this.historySuperDao.uniqueSqlWithParams(sql, params);
        if (result == null) {
            return null;
        }
        final Object[] results = (Object[])result;
        final double transIn = (results[0] == null) ? 0.0 : ((BigDecimal)results[0]).doubleValue();
        final double transOut = (results[1] == null) ? 0.0 : ((BigDecimal)results[1]).doubleValue();
        final double prize = (results[2] == null) ? 0.0 : ((BigDecimal)results[2]).doubleValue();
        final double waterReturn = (results[3] == null) ? 0.0 : ((BigDecimal)results[3]).doubleValue();
        final double proxyReturn = (results[4] == null) ? 0.0 : ((BigDecimal)results[4]).doubleValue();
        final double activity = (results[5] == null) ? 0.0 : ((BigDecimal)results[5]).doubleValue();
        final double billingOrder = (results[6] == null) ? 0.0 : ((BigDecimal)results[6]).doubleValue();
        final HistoryUserGameReportVO report = new HistoryUserGameReportVO();
        report.setTransIn(transIn);
        report.setTransOut(transOut);
        report.setPrize(prize);
        report.setWaterReturn(waterReturn);
        report.setProxyReturn(proxyReturn);
        report.setActivity(activity);
        report.setBillingOrder(billingOrder);
        return report;
    }
    
    @Override
    public UserGameReportVO sum(final int userId, final String sTime, final String eTime) {
        final String sql = "select sum(ugr.trans_in) transIn,sum(ugr.trans_out) transOut,sum(ugr.prize) prize,sum(ugr.spend_return) spendReturn,sum(ugr.proxy_return) proxyReturn,sum(ugr.activity) activity,sum(ugr.billing_order) billingOrder from user_game_report ugr left join user u on ugr.user_id = u.id where ugr.time >= :sTime and ugr.time < :eTime and ugr.user_id = :userId";
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("sTime", sTime);
        params.put("eTime", eTime);
        params.put("userId", userId);
        final Object result = this.superDao.uniqueSqlWithParams(sql, params);
        if (result == null) {
            return null;
        }
        final Object[] results = (Object[])result;
        final double transIn = (results[0] == null) ? 0.0 : ((BigDecimal)results[0]).doubleValue();
        final double transOut = (results[1] == null) ? 0.0 : ((BigDecimal)results[1]).doubleValue();
        final double prize = (results[2] == null) ? 0.0 : ((BigDecimal)results[2]).doubleValue();
        final double waterReturn = (results[3] == null) ? 0.0 : ((BigDecimal)results[3]).doubleValue();
        final double proxyReturn = (results[4] == null) ? 0.0 : ((BigDecimal)results[4]).doubleValue();
        final double activity = (results[5] == null) ? 0.0 : ((BigDecimal)results[5]).doubleValue();
        final double billingOrder = (results[6] == null) ? 0.0 : ((BigDecimal)results[6]).doubleValue();
        final UserGameReportVO report = new UserGameReportVO();
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
