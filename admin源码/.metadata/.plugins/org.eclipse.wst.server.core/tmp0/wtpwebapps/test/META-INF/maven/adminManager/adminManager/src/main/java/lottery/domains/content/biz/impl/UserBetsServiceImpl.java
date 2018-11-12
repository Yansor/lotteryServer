package lottery.domains.content.biz.impl;

import org.springframework.transaction.annotation.Transactional;
import javautils.date.DateUtil;
import java.util.Date;
import redis.clients.jedis.exceptions.JedisException;
import redis.clients.jedis.Pipeline;
import javautils.redis.JedisTemplate.PipelineActionNoResult;
import org.hibernate.criterion.Disjunction;
import lottery.domains.content.entity.HistoryUserBets;
import lottery.domains.content.vo.user.HistoryUserBetsVO;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.Iterator;
import lottery.domains.content.entity.User;
import java.util.List;
import lottery.domains.content.entity.UserBets;
import lottery.domains.content.vo.user.UserBetsVO;
import java.util.ArrayList;
import javautils.StringUtil;
import javautils.jdbc.PageList;
import org.slf4j.LoggerFactory;
import javautils.redis.JedisTemplate;
import lottery.domains.pool.LotteryDataFactory;
import lottery.domains.content.biz.UserBillService;
import lottery.domains.content.dao.UserBetsDao;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.dao.UserDao;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import lottery.domains.content.biz.UserBetsService;

@Service
public class UserBetsServiceImpl implements UserBetsService
{
    private static final Logger log;
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
    
    static {
        log = LoggerFactory.getLogger((Class)UserBetsServiceImpl.class);
    }
    
    @Override
    public PageList search(final String keyword, final String username, final Integer uype, final Integer type, final Integer lotteryId, final String expect, final Integer ruleId, final String minTime, final String maxTime, final String minPrizeTime, final String maxPrizeTime, final Double minMoney, final Double maxMoney, final Integer minMultiple, final Integer maxMultiple, final Double minPrizeMoney, final Double maxPrizeMoney, final Integer status, final Integer locked, final String ip, final int start, final int limit) {
        final StringBuilder querySql = new StringBuilder();
        boolean isSearch = true;
        if (StringUtil.isNotNull(username)) {
            final User user = this.uDao.getByUsername(username);
            if (user != null) {
                querySql.append(" and b.user_id = ").append(user.getId());
            }
            else {
                isSearch = false;
            }
        }
        if (StringUtil.isNotNull(keyword)) {
            if (StringUtil.isInteger(keyword)) {
                querySql.append(" and b.id = " + Integer.parseInt(keyword));
            }
            else {
                querySql.append(" and b.billno =").append("'" + keyword + "'");
            }
        }
        if (lotteryId != null) {
            querySql.append(" and  b.lottery_id = ").append((int)lotteryId);
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
            querySql.append(" and b.money >= ").append((double)minMoney);
        }
        if (maxMoney != null) {
            querySql.append(" and b.money <= ").append((double)maxMoney);
        }
        if (minMultiple != null) {
            querySql.append(" and b.multiple >= ").append((int)minMultiple);
        }
        if (maxMultiple != null) {
            querySql.append(" and b.multiple <= ").append((int)maxMultiple);
        }
        if (minPrizeMoney != null) {
            querySql.append(" and b.prize_money >= ").append((double)minPrizeMoney);
        }
        if (maxPrizeMoney != null) {
            querySql.append(" and b.prize_money <= ").append((double)maxPrizeMoney);
        }
        if (status != null) {
            querySql.append(" and b.status = ").append((int)status);
        }
        if (locked != null) {
            querySql.append(" and b.locked = ").append((int)locked);
        }
        if (StringUtil.isNotNull(ip)) {
            querySql.append("  and b.ip = ").append(ip);
        }
        querySql.append(" and b.id > ").append(0);
        if (uype != null) {
            querySql.append("  and u.type = ").append(uype);
        }
        else {
            querySql.append("  and u.upid != ").append(0);
        }
        querySql.append("  ORDER BY b.time DESC ");
        if (isSearch) {
            final List<UserBetsVO> list = new ArrayList<UserBetsVO>();
            final PageList pList = this.uBetsDao.find(querySql.toString(), start, limit, false);
            for (final Object tmpBean : pList.getList()) {
                final UserBetsVO tmpVO = new UserBetsVO((UserBets)tmpBean, this.lotteryDataFactory, false);
                list.add(tmpVO);
            }
            pList.setList(list);
            return pList;
        }
        return null;
    }
    
    @Override
    public PageList searchHistory(final String keyword, final String username, final Integer uypeu, final Integer type, final Integer lotteryId, final String expect, final Integer ruleId, final String minTime, final String maxTime, final String minPrizeTime, final String maxPrizeTime, final Double minMoney, final Double maxMoney, final Integer minMultiple, final Integer maxMultiple, final Double minPrizeMoney, final Double maxPrizeMoney, final Integer status, final Integer locked, final int start, final int limit) {
        final List<Criterion> criterions = new ArrayList<Criterion>();
        final List<Order> orders = new ArrayList<Order>();
        boolean isSearch = true;
        if (StringUtil.isNotNull(username)) {
            final User user = this.uDao.getByUsername(username);
            if (user != null) {
                criterions.add((Criterion)Restrictions.eq("userId", (Object)user.getId()));
            }
            else {
                isSearch = false;
            }
        }
        if (StringUtil.isNotNull(keyword)) {
            final Disjunction disjunction = Restrictions.or(new Criterion[0]);
            if (StringUtil.isInteger(keyword)) {
                disjunction.add((Criterion)Restrictions.eq("id", (Object)Integer.parseInt(keyword)));
            }
            disjunction.add((Criterion)Restrictions.eq("billno", (Object)keyword));
            criterions.add((Criterion)disjunction);
        }
        if (type != null) {
            criterions.add((Criterion)Restrictions.eq("type", (Object)type));
        }
        if (lotteryId != null) {
            criterions.add((Criterion)Restrictions.eq("lotteryId", (Object)lotteryId));
        }
        if (StringUtil.isNotNull(expect)) {
            criterions.add((Criterion)Restrictions.eq("expect", (Object)expect));
        }
        if (ruleId != null) {
            criterions.add((Criterion)Restrictions.eq("ruleId", (Object)ruleId));
        }
        if (StringUtil.isNotNull(minTime)) {
            criterions.add((Criterion)Restrictions.gt("time", (Object)minTime));
        }
        if (StringUtil.isNotNull(maxTime)) {
            criterions.add((Criterion)Restrictions.lt("time", (Object)maxTime));
        }
        if (StringUtil.isNotNull(minPrizeTime)) {
            criterions.add((Criterion)Restrictions.gt("prizeTime", (Object)minPrizeTime));
        }
        if (StringUtil.isNotNull(maxPrizeTime)) {
            criterions.add((Criterion)Restrictions.lt("prizeTime", (Object)maxPrizeTime));
        }
        if (minMoney != null) {
            criterions.add((Criterion)Restrictions.ge("money", (Object)minMoney));
        }
        if (maxMoney != null) {
            criterions.add((Criterion)Restrictions.le("money", (Object)maxMoney));
        }
        if (minMultiple != null) {
            criterions.add((Criterion)Restrictions.ge("multiple", (Object)minMultiple));
        }
        if (maxMultiple != null) {
            criterions.add((Criterion)Restrictions.le("multiple", (Object)maxMultiple));
        }
        if (minPrizeMoney != null) {
            criterions.add((Criterion)Restrictions.ge("prizeMoney", (Object)minPrizeMoney));
        }
        if (maxPrizeMoney != null) {
            criterions.add((Criterion)Restrictions.le("prizeMoney", (Object)maxPrizeMoney));
        }
        if (status != null) {
            criterions.add((Criterion)Restrictions.eq("status", (Object)status));
        }
        if (locked != null) {
            criterions.add((Criterion)Restrictions.eq("locked", (Object)locked));
        }
        criterions.add((Criterion)Restrictions.gt("id", (Object)0));
        orders.add(Order.desc("time"));
        orders.add(Order.desc("id"));
        if (isSearch) {
            final List<HistoryUserBetsVO> list = new ArrayList<HistoryUserBetsVO>();
            final PageList pList = this.uBetsDao.findHistory(criterions, orders, start, limit, false);
            for (final Object tmpBean : pList.getList()) {
                final HistoryUserBetsVO tmpVO = new HistoryUserBetsVO((HistoryUserBets)tmpBean, this.lotteryDataFactory, false);
                list.add(tmpVO);
            }
            pList.setList(list);
            return pList;
        }
        return null;
    }
    
    @Override
    public List<UserBets> notOpened(final int lotteryId, final Integer ruleId, final String expect, final String match) {
        final List<Criterion> criterions = new ArrayList<Criterion>();
        criterions.add((Criterion)Restrictions.eq("lotteryId", (Object)lotteryId));
        if (ruleId != null) {
            criterions.add((Criterion)Restrictions.eq("ruleId", (Object)ruleId));
        }
        if (StringUtil.isNotNull(expect)) {
            if ("eq".equals(match)) {
                criterions.add((Criterion)Restrictions.eq("expect", (Object)expect));
            }
            if ("le".equals(match)) {
                criterions.add((Criterion)Restrictions.le("expect", (Object)expect));
            }
            if ("ge".equals(match)) {
                criterions.add((Criterion)Restrictions.ge("expect", (Object)expect));
            }
        }
        criterions.add((Criterion)Restrictions.eq("status", (Object)0));
        final List<Order> orders = new ArrayList<Order>();
        return this.uBetsDao.find(criterions, orders, false);
    }
    
    @Override
    public UserBetsVO getById(final int id) {
        final UserBets entity = this.uBetsDao.getById(id);
        if (entity != null) {
            final UserBetsVO bean = new UserBetsVO(entity, this.lotteryDataFactory, true);
            return bean;
        }
        return null;
    }
    
    @Override
    public HistoryUserBetsVO getHistoryById(final int id) {
        final HistoryUserBets entity = this.uBetsDao.getHistoryById(id);
        if (entity != null) {
            final HistoryUserBetsVO bean = new HistoryUserBetsVO(entity, this.lotteryDataFactory, true);
            return bean;
        }
        return null;
    }
    
    @Override
    public boolean cancel(final int id) {
        final UserBets bBean = this.uBetsDao.getById(id);
        return bBean != null && this.doCancelOrder(bBean);
    }
    
    @Override
    public boolean cancel(final int lotteryId, final Integer ruleId, final String expect, final String match) {
        final List<UserBets> list = this.notOpened(lotteryId, ruleId, expect, match);
        for (final UserBets bBean : list) {
            this.doCancelOrder(bBean);
        }
        return true;
    }
    
    private synchronized boolean doCancelOrder(final UserBets bBean) {
        if (bBean.getStatus() == 0) {
            final boolean cFlag = this.uBetsDao.cancel(bBean.getId());
            if (cFlag) {
                final User uBean = this.uDao.getById(bBean.getUserId());
                if (uBean != null) {
                    final boolean uFlag = this.uDao.updateLotteryMoney(uBean.getId(), bBean.getMoney(), -bBean.getMoney());
                    if (uFlag) {
                        this.cacheUserBetsId(bBean);
                        this.uBillService.addCancelOrderBill(bBean, uBean);
                    }
                }
            }
            return cFlag;
        }
        return false;
    }
    
    private void cacheUserBetsId(final UserBets userBets) {
        final String unOpenCacheKey = String.format("USER:BETS:UNOPEN:RECENT:%s", userBets.getUserId());
        final String openedCacheKey = String.format("USER:BETS:OPENED:RECENT:%s", userBets.getUserId());
        final String userBetsId = new StringBuilder(String.valueOf(userBets.getId())).toString();
        try {
            this.jedisTemplate.execute(new PipelineActionNoResult() {
                @Override
                public void action(final Pipeline pipeline) {
                    pipeline.lrem(unOpenCacheKey, 1L, userBetsId);
                    pipeline.lpush(openedCacheKey, new String[] { userBetsId });
                    pipeline.ltrim(openedCacheKey, 0L, 10L);
                    pipeline.expire(openedCacheKey, 43200);
                    pipeline.sync();
                }
            });
        }
        catch (JedisException e) {
            UserBetsServiceImpl.log.error("执行Redis缓存注单ID时出错", (Throwable)e);
        }
    }
    
    @Override
    public List<UserBetsVO> getSuspiciousOrder(final int userId, final int multiple) {
        final List<UserBetsVO> formatList = new ArrayList<UserBetsVO>();
        final List<UserBets> list = this.uBetsDao.getSuspiciousOrder(userId, multiple, false);
        for (final UserBets tmpBean : list) {
            formatList.add(new UserBetsVO(tmpBean, this.lotteryDataFactory, false));
        }
        return formatList;
    }
    
    @Override
    public int countUserOnline(final Date time) {
        final String stime = DateUtil.calcDateByTime(DateUtil.dateToString(time), -600);
        return this.uBetsDao.countUserOnline(stime);
    }
    
    @Override
    public double[] getTotalMoney(final String keyword, final String username, final Integer uype, final Integer type, final Integer lotteryId, final String expect, final Integer ruleId, final String minTime, final String maxTime, final String minPrizeTime, final String maxPrizeTime, final Double minMoney, final Double maxMoney, final Integer minMultiple, final Integer maxMultiple, final Double minPrizeMoney, final Double maxPrizeMoney, final Integer status, final Integer locked, final String ip) {
        Integer userId = null;
        if (StringUtil.isNotNull(username)) {
            final User user = this.uDao.getByUsername(username);
            if (user != null) {
                userId = user.getId();
            }
        }
        return this.uBetsDao.getTotalMoney(keyword, userId, uype, type, lotteryId, expect, ruleId, minTime, maxTime, minPrizeTime, maxPrizeTime, minMoney, maxMoney, minMultiple, maxMultiple, minPrizeMoney, maxPrizeMoney, status, locked, ip);
    }
    
    @Override
    public double[] getHistoryTotalMoney(final String keyword, final String username, final Integer utype, final Integer type, final Integer lotteryId, final String expect, final Integer ruleId, final String minTime, final String maxTime, final String minPrizeTime, final String maxPrizeTime, final Double minMoney, final Double maxMoney, final Integer minMultiple, final Integer maxMultiple, final Double minPrizeMoney, final Double maxPrizeMoney, final Integer status, final Integer locked) {
        Integer userId = null;
        if (StringUtil.isNotNull(username)) {
            final User user = this.uDao.getByUsername(username);
            if (user != null) {
                userId = user.getId();
            }
        }
        return this.uBetsDao.getHistoryTotalMoney(keyword, userId, utype, type, lotteryId, expect, ruleId, minTime, maxTime, minPrizeTime, maxPrizeTime, minMoney, maxMoney, minMultiple, maxMultiple, minPrizeMoney, maxPrizeMoney, status, locked);
    }
    
    @Transactional(readOnly = true)
    @Override
    public double getBillingOrder(final int userId, final String startTime, final String endTime) {
        return this.uBetsDao.getBillingOrder(userId, startTime, endTime);
    }
    
    @Override
    public UserBets getBetsById(final int id) {
        return this.uBetsDao.getById(id);
    }
}
