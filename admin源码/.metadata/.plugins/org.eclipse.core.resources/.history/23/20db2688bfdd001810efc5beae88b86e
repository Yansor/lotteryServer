package lottery.domains.content.dao.impl;

import javautils.array.ArrayUtils;
import lottery.domains.content.vo.bill.UserBetsReportVO;
import lottery.domains.content.entity.LotteryPlayRulesGroup;
import lottery.domains.content.entity.LotteryPlayRules;
import lottery.domains.content.vo.bill.HistoryUserLotteryDetailsReportVO;
import java.util.Iterator;
import java.util.Map;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import org.apache.commons.collections.CollectionUtils;
import java.util.HashMap;
import lottery.domains.content.vo.bill.UserLotteryDetailsReportVO;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.List;
import lottery.domains.content.entity.HistoryUserLotteryDetailsReport;
import lottery.domains.pool.LotteryDataFactory;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.entity.UserLotteryDetailsReport;
import javautils.jdbc.hibernate.HibernateSuperDao;
import org.springframework.stereotype.Repository;
import lottery.domains.content.dao.UserLotteryDetailsReportDao;

@Repository
public class UserLotteryDetailsReportDaoImpl implements UserLotteryDetailsReportDao
{
    private final String tab;
    @Autowired
    private HibernateSuperDao<UserLotteryDetailsReport> superDao;
    @Autowired
    private LotteryDataFactory dataFactory;
    @Autowired
    private HibernateSuperDao<HistoryUserLotteryDetailsReport> historySuperDao;
    
    public UserLotteryDetailsReportDaoImpl() {
        this.tab = UserLotteryDetailsReport.class.getSimpleName();
    }
    
    @Override
    public boolean add(final UserLotteryDetailsReport entity) {
        return this.superDao.save(entity);
    }
    
    @Override
    public UserLotteryDetailsReport get(final int userId, final int lotteryId, final int ruleId, final String time) {
        final String hql = "from " + this.tab + " where userId = ?0 and lotteryId = ?1 and ruleId = ?2 and time = ?3";
        final Object[] values = { userId, lotteryId, ruleId, time };
        return (UserLotteryDetailsReport)this.superDao.unique(hql, values);
    }
    
    @Override
    public boolean update(final UserLotteryDetailsReport entity) {
        final String hql = "update " + this.tab + " set spend = spend + ?1, prize = prize + ?2, spendReturn = spendReturn + ?3, proxyReturn = proxyReturn + ?4, cancelOrder = cancelOrder + ?5, billingOrder = billingOrder + ?6 where id = ?0";
        final Object[] values = { entity.getId(), entity.getSpend(), entity.getPrize(), entity.getSpendReturn(), entity.getProxyReturn(), entity.getCancelOrder(), entity.getBillingOrder() };
        return this.superDao.update(hql, values);
    }
    
    @Override
    public List<UserLotteryDetailsReport> find(final List<Criterion> criterions, final List<Order> orders) {
        return this.superDao.findByCriteria(UserLotteryDetailsReport.class, criterions, orders);
    }
    
    @Override
    public List<UserLotteryDetailsReportVO> sumLowersAndSelfByLottery(final int userId, final String sTime, final String eTime) {
        final String sql = "select uldr.lottery_id,l.show_name, sum(uldr.prize) prize,sum(uldr.spend_return) spendReturn,sum(uldr.proxy_return) proxyReturn,sum(uldr.billing_order) billingOrder from user_lottery_details_report uldr left join user u on uldr.user_id = u.id left join lottery l on uldr.lottery_id = l.id where uldr.time >= :sTime and uldr.time < :eTime and (u.upids like :upid or uldr.user_id = :userId) group by uldr.lottery_id,l.show_name";
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("sTime", sTime);
        params.put("eTime", eTime);
        params.put("upid", "%[" + userId + "]%");
        params.put("userId", userId);
        final List<?> arrs = this.superDao.listBySql(sql, params);
        if (CollectionUtils.isEmpty((Collection)arrs)) {
            return new ArrayList<UserLotteryDetailsReportVO>();
        }
        final List<UserLotteryDetailsReportVO> reports = new ArrayList<UserLotteryDetailsReportVO>(arrs.size());
        for (final Object arr : arrs) {
            final Object[] objects = (Object[])arr;
            final int lotteryId = (int)((objects[0] == null) ? 0 : objects[0]);
            final String lotteryName = objects[1].toString();
            final double prize = (objects[2] == null) ? 0.0 : ((BigDecimal)objects[2]).doubleValue();
            final double spendReturn = (objects[3] == null) ? 0.0 : ((BigDecimal)objects[3]).doubleValue();
            final double proxyReturn = (objects[4] == null) ? 0.0 : ((BigDecimal)objects[4]).doubleValue();
            final double billingOrder = (objects[5] == null) ? 0.0 : ((BigDecimal)objects[5]).doubleValue();
            final UserLotteryDetailsReportVO report = new UserLotteryDetailsReportVO();
            report.setName(lotteryName);
            report.setKey(new StringBuilder(String.valueOf(lotteryId)).toString());
            report.setPrize(prize);
            report.setSpendReturn(spendReturn);
            report.setProxyReturn(proxyReturn);
            report.setBillingOrder(billingOrder);
            reports.add(report);
        }
        return reports;
    }
    
    @Override
    public List<HistoryUserLotteryDetailsReportVO> historySumLowersAndSelfByLottery(final int userId, final String sTime, final String eTime) {
        final String sql = "select uldr.lottery_id,l.show_name, sum(uldr.prize) prize,sum(uldr.spend_return) spendReturn,sum(uldr.proxy_return) proxyReturn,sum(uldr.billing_order) billingOrder from user_lottery_details_report uldr left join user u on uldr.user_id = u.id left join lottery l on uldr.lottery_id = l.id where uldr.time >= :sTime and uldr.time < :eTime and (u.upids like :upid or uldr.user_id = :userId) group by uldr.lottery_id,l.show_name";
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("sTime", sTime);
        params.put("eTime", eTime);
        params.put("upid", "%[" + userId + "]%");
        params.put("userId", userId);
        final List<?> arrs = this.historySuperDao.listBySql(sql, params);
        if (CollectionUtils.isEmpty((Collection)arrs)) {
            return new ArrayList<HistoryUserLotteryDetailsReportVO>();
        }
        final List<HistoryUserLotteryDetailsReportVO> reports = new ArrayList<HistoryUserLotteryDetailsReportVO>(arrs.size());
        for (final Object arr : arrs) {
            final Object[] objects = (Object[])arr;
            final int lotteryId = (int)((objects[0] == null) ? 0 : objects[0]);
            final String lotteryName = objects[1].toString();
            final double prize = (objects[2] == null) ? 0.0 : ((BigDecimal)objects[2]).doubleValue();
            final double spendReturn = (objects[3] == null) ? 0.0 : ((BigDecimal)objects[3]).doubleValue();
            final double proxyReturn = (objects[4] == null) ? 0.0 : ((BigDecimal)objects[4]).doubleValue();
            final double billingOrder = (objects[5] == null) ? 0.0 : ((BigDecimal)objects[5]).doubleValue();
            final HistoryUserLotteryDetailsReportVO report = new HistoryUserLotteryDetailsReportVO();
            report.setName(lotteryName);
            report.setKey(new StringBuilder(String.valueOf(lotteryId)).toString());
            report.setPrize(prize);
            report.setSpendReturn(spendReturn);
            report.setProxyReturn(proxyReturn);
            report.setBillingOrder(billingOrder);
            reports.add(report);
        }
        return reports;
    }
    
    @Override
    public List<UserLotteryDetailsReportVO> sumLowersAndSelfByRule(final int userId, final int lotteryId, final String sTime, final String eTime) {
        final String sql = "select uldr.rule_id, sum(uldr.prize) prize,sum(uldr.spend_return) spendReturn,sum(uldr.proxy_return) proxyReturn,sum(uldr.billing_order) billingOrder from user_lottery_details_report uldr left join user u on uldr.user_id = u.id where uldr.time >= :sTime and uldr.time < :eTime and (u.upids like :upid or uldr.user_id = :userId) and uldr.lottery_id = :lotteryId group by uldr.rule_id";
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("sTime", sTime);
        params.put("eTime", eTime);
        params.put("upid", "%[" + userId + "]%");
        params.put("userId", userId);
        params.put("lotteryId", lotteryId);
        final List<?> arrs = this.superDao.listBySql(sql, params);
        if (CollectionUtils.isEmpty((Collection)arrs)) {
            return new ArrayList<UserLotteryDetailsReportVO>();
        }
        final List<UserLotteryDetailsReportVO> reports = new ArrayList<UserLotteryDetailsReportVO>(arrs.size());
        for (final Object arr : arrs) {
            int index = 0;
            final Object[] objects = (Object[])arr;
            final int ruleId = Integer.valueOf(objects[index].toString());
            ++index;
            final double prize = (objects[index] == null) ? 0.0 : ((BigDecimal)objects[index]).doubleValue();
            ++index;
            final double spendReturn = (objects[index] == null) ? 0.0 : ((BigDecimal)objects[index]).doubleValue();
            ++index;
            final double proxyReturn = (objects[index] == null) ? 0.0 : ((BigDecimal)objects[index]).doubleValue();
            ++index;
            final double billingOrder = (objects[index] == null) ? 0.0 : ((BigDecimal)objects[index]).doubleValue();
            final UserLotteryDetailsReportVO report = new UserLotteryDetailsReportVO();
            final LotteryPlayRules rule = this.dataFactory.getLotteryPlayRules(ruleId);
            if (rule == null) {
                continue;
            }
            final LotteryPlayRulesGroup group = this.dataFactory.getLotteryPlayRulesGroup(rule.getGroupId());
            if (group == null) {
                continue;
            }
            report.setName(String.valueOf(group.getName()) + "_" + rule.getName());
            report.setKey(new StringBuilder(String.valueOf(ruleId)).toString());
            report.setPrize(prize);
            report.setSpendReturn(spendReturn);
            report.setProxyReturn(proxyReturn);
            report.setBillingOrder(billingOrder);
            reports.add(report);
        }
        return reports;
    }
    
    @Override
    public List<HistoryUserLotteryDetailsReportVO> historySumLowersAndSelfByRule(final int userId, final int lotteryId, final String sTime, final String eTime) {
        final String sql = "select uldr.rule_id, sum(uldr.prize) prize,sum(uldr.spend_return) spendReturn,sum(uldr.proxy_return) proxyReturn,sum(uldr.billing_order) billingOrder from ecaibackup.user_lottery_details_report uldr left join ecai.user u on uldr.user_id = u.id where uldr.time >= :sTime and uldr.time < :eTime and (u.upids like :upid or uldr.user_id = :userId) and uldr.lottery_id = :lotteryId group by uldr.rule_id";
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("sTime", sTime);
        params.put("eTime", eTime);
        params.put("upid", "%[" + userId + "]%");
        params.put("userId", userId);
        params.put("lotteryId", lotteryId);
        final List<?> arrs = this.historySuperDao.listBySql(sql, params);
        if (CollectionUtils.isEmpty((Collection)arrs)) {
            return new ArrayList<HistoryUserLotteryDetailsReportVO>();
        }
        final List<HistoryUserLotteryDetailsReportVO> reports = new ArrayList<HistoryUserLotteryDetailsReportVO>(arrs.size());
        for (final Object arr : arrs) {
            int index = 0;
            final Object[] objects = (Object[])arr;
            final int ruleId = Integer.valueOf(objects[index].toString());
            ++index;
            final double prize = (objects[index] == null) ? 0.0 : ((BigDecimal)objects[index]).doubleValue();
            ++index;
            final double spendReturn = (objects[index] == null) ? 0.0 : ((BigDecimal)objects[index]).doubleValue();
            ++index;
            final double proxyReturn = (objects[index] == null) ? 0.0 : ((BigDecimal)objects[index]).doubleValue();
            ++index;
            final double billingOrder = (objects[index] == null) ? 0.0 : ((BigDecimal)objects[index]).doubleValue();
            final HistoryUserLotteryDetailsReportVO report = new HistoryUserLotteryDetailsReportVO();
            final LotteryPlayRules rule = this.dataFactory.getLotteryPlayRules(ruleId);
            if (rule == null) {
                continue;
            }
            final LotteryPlayRulesGroup group = this.dataFactory.getLotteryPlayRulesGroup(rule.getGroupId());
            if (group == null) {
                continue;
            }
            report.setName(String.valueOf(group.getName()) + "_" + rule.getName());
            report.setKey(new StringBuilder(String.valueOf(ruleId)).toString());
            report.setPrize(prize);
            report.setSpendReturn(spendReturn);
            report.setProxyReturn(proxyReturn);
            report.setBillingOrder(billingOrder);
            reports.add(report);
        }
        return reports;
    }
    
    @Override
    public List<UserLotteryDetailsReportVO> sumSelfByLottery(final int userId, final String sTime, final String eTime) {
        final String sql = "select uldr.lottery_id,l.show_name, sum(uldr.prize) prize,sum(uldr.spend_return) spendReturn,sum(uldr.proxy_return) proxyReturn,sum(uldr.billing_order) billingOrder from user_lottery_details_report uldr left join user u on uldr.user_id = u.id left join lottery l on uldr.lottery_id = l.id where uldr.time >= :sTime and uldr.time < :eTime and uldr.user_id = :userId group by uldr.lottery_id,l.show_name";
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("sTime", sTime);
        params.put("eTime", eTime);
        params.put("userId", userId);
        final List<?> arrs = this.superDao.listBySql(sql, params);
        if (CollectionUtils.isEmpty((Collection)arrs)) {
            return new ArrayList<UserLotteryDetailsReportVO>();
        }
        final List<UserLotteryDetailsReportVO> reports = new ArrayList<UserLotteryDetailsReportVO>(arrs.size());
        for (final Object arr : arrs) {
            final Object[] objects = (Object[])arr;
            final int lotteryId = (int)((objects[0] == null) ? 0 : objects[0]);
            final String lotteryName = objects[1].toString();
            final double prize = (objects[2] == null) ? 0.0 : ((BigDecimal)objects[2]).doubleValue();
            final double spendReturn = (objects[3] == null) ? 0.0 : ((BigDecimal)objects[3]).doubleValue();
            final double proxyReturn = (objects[4] == null) ? 0.0 : ((BigDecimal)objects[4]).doubleValue();
            final double billingOrder = (objects[5] == null) ? 0.0 : ((BigDecimal)objects[5]).doubleValue();
            final UserLotteryDetailsReportVO report = new UserLotteryDetailsReportVO();
            report.setName(lotteryName);
            report.setKey(new StringBuilder(String.valueOf(lotteryId)).toString());
            report.setPrize(prize);
            report.setSpendReturn(spendReturn);
            report.setProxyReturn(proxyReturn);
            report.setBillingOrder(billingOrder);
            reports.add(report);
        }
        return reports;
    }
    
    @Override
    public List<HistoryUserLotteryDetailsReportVO> historySumSelfByLottery(final int userId, final String sTime, final String eTime) {
        final String sql = "select uldr.lottery_id,l.show_name, sum(uldr.prize) prize,sum(uldr.spend_return) spendReturn,sum(uldr.proxy_return) proxyReturn,sum(uldr.billing_order) billingOrder from ecaibackup.user_lottery_details_report uldr left join ecai.user u on uldr.user_id = u.id left join lottery l on uldr.lottery_id = l.id where uldr.time >= :sTime and uldr.time < :eTime and uldr.user_id = :userId group by uldr.lottery_id,l.show_name";
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("sTime", sTime);
        params.put("eTime", eTime);
        params.put("userId", userId);
        final List<?> arrs = this.historySuperDao.listBySql(sql, params);
        if (CollectionUtils.isEmpty((Collection)arrs)) {
            return new ArrayList<HistoryUserLotteryDetailsReportVO>();
        }
        final List<HistoryUserLotteryDetailsReportVO> reports = new ArrayList<HistoryUserLotteryDetailsReportVO>(arrs.size());
        for (final Object arr : arrs) {
            final Object[] objects = (Object[])arr;
            final int lotteryId = (int)((objects[0] == null) ? 0 : objects[0]);
            final String lotteryName = objects[1].toString();
            final double prize = (objects[2] == null) ? 0.0 : ((BigDecimal)objects[2]).doubleValue();
            final double spendReturn = (objects[3] == null) ? 0.0 : ((BigDecimal)objects[3]).doubleValue();
            final double proxyReturn = (objects[4] == null) ? 0.0 : ((BigDecimal)objects[4]).doubleValue();
            final double billingOrder = (objects[5] == null) ? 0.0 : ((BigDecimal)objects[5]).doubleValue();
            final HistoryUserLotteryDetailsReportVO report = new HistoryUserLotteryDetailsReportVO();
            report.setName(lotteryName);
            report.setKey(new StringBuilder(String.valueOf(lotteryId)).toString());
            report.setPrize(prize);
            report.setSpendReturn(spendReturn);
            report.setProxyReturn(proxyReturn);
            report.setBillingOrder(billingOrder);
            reports.add(report);
        }
        return reports;
    }
    
    @Override
    public List<UserLotteryDetailsReportVO> sumSelfByRule(final int userId, final int lotteryId, final String sTime, final String eTime) {
        final String sql = "select uldr.rule_id, sum(uldr.prize) prize,sum(uldr.spend_return) spendReturn,sum(uldr.proxy_return) proxyReturn,sum(uldr.billing_order) billingOrder from user_lottery_details_report uldr left join user u on uldr.user_id = u.id where uldr.time >= :sTime and uldr.time < :eTime and uldr.user_id = :userId and uldr.lottery_id = :lotteryId group by uldr.rule_id";
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("sTime", sTime);
        params.put("eTime", eTime);
        params.put("userId", userId);
        params.put("lotteryId", lotteryId);
        final List<?> arrs = this.superDao.listBySql(sql, params);
        if (CollectionUtils.isEmpty((Collection)arrs)) {
            return new ArrayList<UserLotteryDetailsReportVO>();
        }
        final List<UserLotteryDetailsReportVO> reports = new ArrayList<UserLotteryDetailsReportVO>(arrs.size());
        for (final Object arr : arrs) {
            int index = 0;
            final Object[] objects = (Object[])arr;
            final int ruleId = Integer.valueOf(objects[index].toString());
            ++index;
            final double prize = (objects[index] == null) ? 0.0 : ((BigDecimal)objects[index]).doubleValue();
            ++index;
            final double spendReturn = (objects[index] == null) ? 0.0 : ((BigDecimal)objects[index]).doubleValue();
            ++index;
            final double proxyReturn = (objects[index] == null) ? 0.0 : ((BigDecimal)objects[index]).doubleValue();
            ++index;
            final double billingOrder = (objects[index] == null) ? 0.0 : ((BigDecimal)objects[index]).doubleValue();
            final UserLotteryDetailsReportVO report = new UserLotteryDetailsReportVO();
            final LotteryPlayRules rule = this.dataFactory.getLotteryPlayRules(ruleId);
            if (rule == null) {
                continue;
            }
            final LotteryPlayRulesGroup group = this.dataFactory.getLotteryPlayRulesGroup(rule.getGroupId());
            if (group == null) {
                continue;
            }
            report.setName(String.valueOf(group.getName()) + "_" + rule.getName());
            report.setKey(new StringBuilder(String.valueOf(ruleId)).toString());
            report.setPrize(prize);
            report.setSpendReturn(spendReturn);
            report.setProxyReturn(proxyReturn);
            report.setBillingOrder(billingOrder);
            reports.add(report);
        }
        return reports;
    }
    
    @Override
    public List<HistoryUserLotteryDetailsReportVO> historySumSelfByRule(final int userId, final int lotteryId, final String sTime, final String eTime) {
        final String sql = "select uldr.rule_id, sum(uldr.prize) prize,sum(uldr.spend_return) spendReturn,sum(uldr.proxy_return) proxyReturn,sum(uldr.billing_order) billingOrder from ecaibackup.user_lottery_details_report uldr left join ecai.user u on uldr.user_id = u.id where uldr.time >= :sTime and uldr.time < :eTime and uldr.user_id = :userId and uldr.lottery_id = :lotteryId group by uldr.rule_id";
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("sTime", sTime);
        params.put("eTime", eTime);
        params.put("userId", userId);
        params.put("lotteryId", lotteryId);
        final List<?> arrs = this.historySuperDao.listBySql(sql, params);
        if (CollectionUtils.isEmpty((Collection)arrs)) {
            return new ArrayList<HistoryUserLotteryDetailsReportVO>();
        }
        final List<HistoryUserLotteryDetailsReportVO> reports = new ArrayList<HistoryUserLotteryDetailsReportVO>(arrs.size());
        for (final Object arr : arrs) {
            int index = 0;
            final Object[] objects = (Object[])arr;
            final int ruleId = Integer.valueOf(objects[index].toString());
            ++index;
            final double prize = (objects[index] == null) ? 0.0 : ((BigDecimal)objects[index]).doubleValue();
            ++index;
            final double spendReturn = (objects[index] == null) ? 0.0 : ((BigDecimal)objects[index]).doubleValue();
            ++index;
            final double proxyReturn = (objects[index] == null) ? 0.0 : ((BigDecimal)objects[index]).doubleValue();
            ++index;
            final double billingOrder = (objects[index] == null) ? 0.0 : ((BigDecimal)objects[index]).doubleValue();
            final HistoryUserLotteryDetailsReportVO report = new HistoryUserLotteryDetailsReportVO();
            final LotteryPlayRules rule = this.dataFactory.getLotteryPlayRules(ruleId);
            if (rule == null) {
                continue;
            }
            final LotteryPlayRulesGroup group = this.dataFactory.getLotteryPlayRulesGroup(rule.getGroupId());
            if (group == null) {
                continue;
            }
            report.setName(String.valueOf(group.getName()) + "_" + rule.getName());
            report.setKey(new StringBuilder(String.valueOf(ruleId)).toString());
            report.setPrize(prize);
            report.setSpendReturn(spendReturn);
            report.setProxyReturn(proxyReturn);
            report.setBillingOrder(billingOrder);
            reports.add(report);
        }
        return reports;
    }
    
    @Override
    public List<UserBetsReportVO> sumUserBets(final List<Integer> lotteryIds, final Integer ruleId, final String sTime, final String eTime) {
        final Map<String, Object> params = new HashMap<String, Object>();
        String sql = "select uldr.`time`, sum(uldr.prize) prize,sum(uldr.spend_return+uldr.proxy_return) returnMoney,sum(uldr.billing_order) billingOrder from user_lottery_details_report uldr ,user u where uldr.user_id = u.id and u.upid !=0  and  uldr.`time` >= :sTime and uldr.`time` < :eTime";
        if (ruleId != null) {
            sql = String.valueOf(sql) + " and uldr.rule_id = :ruleId";
            params.put("ruleId", ruleId);
        }
        if (CollectionUtils.isNotEmpty((Collection)lotteryIds)) {
            sql = String.valueOf(sql) + " and uldr.lottery_id in (" + ArrayUtils.toString(lotteryIds) + ")";
        }
        sql = String.valueOf(sql) + " group by uldr.`time`";
        params.put("sTime", sTime);
        params.put("eTime", eTime);
        final List<?> arrs = this.superDao.listBySql(sql, params);
        if (CollectionUtils.isEmpty((Collection)arrs)) {
            return new ArrayList<UserBetsReportVO>();
        }
        final List<UserBetsReportVO> reports = new ArrayList<UserBetsReportVO>(arrs.size());
        for (final Object arr : arrs) {
            final Object[] objects = (Object[])arr;
            final String time = objects[0].toString();
            final double prize = (objects[1] == null) ? 0.0 : ((BigDecimal)objects[1]).doubleValue();
            final double returnMoney = (objects[2] == null) ? 0.0 : ((BigDecimal)objects[2]).doubleValue();
            final double billingOrder = (objects[3] == null) ? 0.0 : ((BigDecimal)objects[3]).doubleValue();
            final UserBetsReportVO report = new UserBetsReportVO();
            report.setField(time);
            report.setMoney(billingOrder);
            report.setPrizeMoney(prize);
            report.setReturnMoney(returnMoney);
            reports.add(report);
        }
        return reports;
    }
}
