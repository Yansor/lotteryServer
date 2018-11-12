package lottery.domains.content.dao.impl;

import javautils.date.Moment;
import java.util.Iterator;
import lottery.domains.content.vo.bill.UserProfitRankingVO;
import lottery.domains.content.vo.bill.HistoryUserLotteryReportVO;
import java.util.Map;
import java.math.BigDecimal;
import java.util.HashMap;
import lottery.domains.content.vo.bill.UserLotteryReportVO;
import java.util.ArrayList;
import javautils.array.ArrayUtils;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.List;
import lottery.domains.content.entity.HistoryUserLotteryReport;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.entity.UserLotteryReport;
import javautils.jdbc.hibernate.HibernateSuperDao;
import org.springframework.stereotype.Repository;
import lottery.domains.content.dao.UserLotteryReportDao;

@Repository
public class UserLotteryReportDaoImpl implements UserLotteryReportDao
{
    private final String tab;
    @Autowired
    private HibernateSuperDao<UserLotteryReport> superDao;
    @Autowired
    private HibernateSuperDao<HistoryUserLotteryReport> historySuperDao;
    private final String hsInstance = "ecai";
    private final String hsbackupInstance = "ecaibackup";
    
    public UserLotteryReportDaoImpl() {
        this.tab = UserLotteryReport.class.getSimpleName();
    }
    
    @Override
    public boolean add(final UserLotteryReport entity) {
        return this.superDao.save(entity);
    }
    
    @Override
    public UserLotteryReport get(final int userId, final String time) {
        final String hql = "from " + this.tab + " where userId = ?0 and time = ?1";
        final Object[] values = { userId, time };
        return (UserLotteryReport)this.superDao.unique(hql, values);
    }
    
    @Override
    public List<UserLotteryReport> list(final int userId, final String sTime, final String eTime) {
        final String hql = "from " + this.tab + " where userId = ?0 and time >= ?1 and time < ?2 order by time asc";
        final Object[] values = { userId, sTime, eTime };
        return this.superDao.list(hql, values);
    }
    
    @Override
    public boolean update(final UserLotteryReport entity) {
        final String hql = "update " + this.tab + " set transIn = transIn + ?1, transOut = transOut + ?2, spend = spend + ?3, prize = prize + ?4, spendReturn = spendReturn + ?5, proxyReturn = proxyReturn + ?6, cancelOrder = cancelOrder + ?7, activity = activity + ?8, billingOrder = billingOrder + ?9,rechargeFee = rechargeFee + ?10 where id = ?0";
        final Object[] values = { entity.getId(), entity.getTransIn(), entity.getTransOut(), entity.getSpend(), entity.getPrize(), entity.getSpendReturn(), entity.getProxyReturn(), entity.getCancelOrder(), entity.getActivity(), entity.getBillingOrder(), entity.getRechargeFee() };
        return this.superDao.update(hql, values);
    }
    
    @Override
    public List<UserLotteryReport> find(final List<Criterion> criterions, final List<Order> orders) {
        return this.superDao.findByCriteria(UserLotteryReport.class, criterions, orders);
    }
    
    @Override
    public List<HistoryUserLotteryReport> findHistory(final List<Criterion> criterions, final List<Order> orders) {
        return this.historySuperDao.findByCriteria(HistoryUserLotteryReport.class, criterions, orders);
    }
    
    @Override
    public List<UserLotteryReport> getDayList(final int[] ids, final String sDate, final String eDate) {
        if (ids.length > 0) {
            final String hql = "from " + this.tab + " where userId in (" + ArrayUtils.transInIds(ids) + ") and time >= ?0 and time < ?1";
            final Object[] values = { sDate, eDate };
            return this.superDao.list(hql, values);
        }
        return new ArrayList<UserLotteryReport>();
    }
    
    @Override
    public UserLotteryReportVO sumLowersAndSelf(final int userId, final String sTime, final String eTime) {
        final String sql = "select sum(ulr.trans_in) transIn,sum(ulr.trans_out) transOut,sum(ulr.prize) prize,sum(ulr.spend_return) spendReturn,sum(ulr.proxy_return) proxyReturn,sum(ulr.activity) activity,sum(ulr.dividend) dividend,sum(ulr.billing_order) billingOrder,sum(ulr.recharge_fee) rechargeFee from user_lottery_report ulr left join user u on ulr.user_id = u.id where ulr.time >= :sTime and ulr.time < :eTime and (u.upids like :upid or ulr.user_id = :userId)";
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
        final double spendReturn = (results[3] == null) ? 0.0 : ((BigDecimal)results[3]).doubleValue();
        final double proxyReturn = (results[4] == null) ? 0.0 : ((BigDecimal)results[4]).doubleValue();
        final double activity = (results[5] == null) ? 0.0 : ((BigDecimal)results[5]).doubleValue();
        final double dividend = (results[6] == null) ? 0.0 : ((BigDecimal)results[6]).doubleValue();
        final double billingOrder = (results[7] == null) ? 0.0 : ((BigDecimal)results[7]).doubleValue();
        final double rechargeFee = (results[8] == null) ? 0.0 : ((BigDecimal)results[8]).doubleValue();
        final UserLotteryReportVO report = new UserLotteryReportVO();
        report.setTransIn(transIn);
        report.setTransOut(transOut);
        report.setPrize(prize);
        report.setSpendReturn(spendReturn);
        report.setProxyReturn(proxyReturn);
        report.setActivity(activity);
        report.setDividend(dividend);
        report.setBillingOrder(billingOrder);
        report.setRechargeFee(rechargeFee);
        return report;
    }
    
    @Override
    public HistoryUserLotteryReportVO historySumLowersAndSelf(final int userId, final String sTime, final String eTime) {
        final String sql = "select sum(ulr.trans_in) transIn,sum(ulr.trans_out) transOut,sum(ulr.prize) prize,sum(ulr.spend_return) spendReturn,sum(ulr.proxy_return) proxyReturn,sum(ulr.activity) activity,sum(ulr.dividend) dividend,sum(ulr.billing_order) billingOrder,sum(ulr.recharge_fee) rechargeFee from ecaibackup.user_lottery_report ulr left join ecai.user u on ulr.user_id = u.id where ulr.time >= :sTime and ulr.time < :eTime and (u.upids like :upid or ulr.user_id = :userId)";
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
        final double spendReturn = (results[3] == null) ? 0.0 : ((BigDecimal)results[3]).doubleValue();
        final double proxyReturn = (results[4] == null) ? 0.0 : ((BigDecimal)results[4]).doubleValue();
        final double activity = (results[5] == null) ? 0.0 : ((BigDecimal)results[5]).doubleValue();
        final double dividend = (results[6] == null) ? 0.0 : ((BigDecimal)results[6]).doubleValue();
        final double billingOrder = (results[7] == null) ? 0.0 : ((BigDecimal)results[7]).doubleValue();
        final double rechargeFee = (results[8] == null) ? 0.0 : ((BigDecimal)results[8]).doubleValue();
        final HistoryUserLotteryReportVO report = new HistoryUserLotteryReportVO();
        report.setTransIn(transIn);
        report.setTransOut(transOut);
        report.setPrize(prize);
        report.setSpendReturn(spendReturn);
        report.setProxyReturn(proxyReturn);
        report.setActivity(activity);
        report.setDividend(dividend);
        report.setBillingOrder(billingOrder);
        report.setRechargeFee(rechargeFee);
        return report;
    }
    
    @Override
    public UserLotteryReportVO sum(final int userId, final String sTime, final String eTime) {
        final String sql = "select sum(ulr.trans_in) transIn,sum(ulr.trans_out) transOut,sum(ulr.prize) prize,sum(ulr.spend_return) spendReturn,sum(ulr.proxy_return) proxyReturn,sum(ulr.activity) activity,sum(ulr.dividend) dividend,sum(ulr.billing_order) billingOrder,sum(ulr.recharge_fee) recharge_fee from user_lottery_report ulr left join user u on ulr.user_id = u.id where ulr.time >= :sTime and ulr.time < :eTime and ulr.user_id = :userId";
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
        final double spendReturn = (results[3] == null) ? 0.0 : ((BigDecimal)results[3]).doubleValue();
        final double proxyReturn = (results[4] == null) ? 0.0 : ((BigDecimal)results[4]).doubleValue();
        final double activity = (results[5] == null) ? 0.0 : ((BigDecimal)results[5]).doubleValue();
        final double dividend = (results[6] == null) ? 0.0 : ((BigDecimal)results[6]).doubleValue();
        final double billingOrder = (results[7] == null) ? 0.0 : ((BigDecimal)results[7]).doubleValue();
        final double rechargeFee = (results[8] == null) ? 0.0 : ((BigDecimal)results[8]).doubleValue();
        final UserLotteryReportVO report = new UserLotteryReportVO();
        report.setTransIn(transIn);
        report.setTransOut(transOut);
        report.setPrize(prize);
        report.setSpendReturn(spendReturn);
        report.setProxyReturn(proxyReturn);
        report.setActivity(activity);
        report.setDividend(dividend);
        report.setBillingOrder(billingOrder);
        report.setRechargeFee(rechargeFee);
        return report;
    }
    
    @Override
    public List<UserProfitRankingVO> listUserProfitRanking(final String sTime, final String eTime, final int start, final int limit) {
        final String sql = "SELECT r.user_id as user_id , (sum(r.prize) + sum(r.spend_return) + sum(r.proxy_return) + sum(r.activity) + sum(r.packet) + sum(r.recharge_fee) - sum(r.billing_order)) profit FROM user_lottery_report r , user u where  r.user_id = u.id  and r.time >= :sTime and r.time < :eTime and r.id > 0 and u.upid != :upid group by r.user_id order by profit desc";
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("sTime", sTime);
        params.put("eTime", eTime);
        params.put("upid", 0);
        final List<?> results = this.superDao.listBySql(sql, params, start, limit);
        if (results == null || results.size() < 0) {
            return null;
        }
        final List<UserProfitRankingVO> rankingVOs = new ArrayList<UserProfitRankingVO>(results.size());
        for (final Object result : results) {
            final Object[] resultArr = (Object[])result;
            final int _userId = (int)resultArr[0];
            final double _profit = (resultArr[1] == null) ? 0.0 : ((BigDecimal)resultArr[1]).doubleValue();
            final UserProfitRankingVO rankingVO = new UserProfitRankingVO(_userId, sTime, eTime, _profit);
            rankingVOs.add(rankingVO);
        }
        return rankingVOs;
    }
    
    @Override
    public List<UserProfitRankingVO> listUserProfitRankingByDate(final int userId, final String sTime, final String eTime, final int start, final int limit) {
        final String sql = "SELECT user_id, time, (sum(prize) + sum(spend_return) + sum(proxy_return) + sum(activity) + sum(packet) + sum(recharge_fee) - sum(billing_order)) profit FROM user_lottery_report where time >= :sTime and time < :eTime and id > 0 and user_id=:userId group by user_id,time order by time desc";
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("sTime", sTime);
        params.put("eTime", eTime);
        params.put("userId", userId);
        final List<?> results = this.superDao.listBySql(sql, params, start, limit);
        if (results == null || results.size() < 0) {
            return null;
        }
        final List<UserProfitRankingVO> rankingVOs = new ArrayList<UserProfitRankingVO>(results.size());
        for (final Object result : results) {
            final Object[] resultArr = (Object[])result;
            final int _userId = (int)resultArr[0];
            final String _sTime = resultArr[1].toString();
            final String _eTime = new Moment().fromDate(_sTime).add(1, "days").toSimpleDate();
            final double _profit = (resultArr[2] == null) ? 0.0 : ((BigDecimal)resultArr[2]).doubleValue();
            final UserProfitRankingVO rankingVO = new UserProfitRankingVO(_userId, _sTime, _eTime, _profit);
            rankingVOs.add(rankingVO);
        }
        return rankingVOs;
    }
}
