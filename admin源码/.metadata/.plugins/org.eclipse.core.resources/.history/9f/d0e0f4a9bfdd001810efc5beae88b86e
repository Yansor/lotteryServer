package lottery.domains.content.biz.impl;

import lottery.domains.content.vo.payment.PaymentChannelVO;
import lottery.domains.content.entity.User;
import javautils.StringUtil;
import javautils.jdbc.PageList;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import lottery.domains.content.entity.HistoryUserRecharge;
import lottery.domains.content.vo.user.HistoryUserRechargeVO;
import lottery.domains.content.entity.UserRecharge;
import lottery.domains.content.vo.user.UserRechargeVO;
import org.apache.commons.lang.RandomStringUtils;
import javautils.date.Moment;
import lottery.domains.content.dao.PaymentChannelDao;
import lottery.domains.pool.LotteryDataFactory;
import lottery.domains.content.biz.ActivityFirstRechargeBillService;
import lottery.domains.content.biz.UserLotteryReportService;
import lottery.domains.content.biz.UserWithdrawLimitService;
import lottery.domains.content.biz.UserSysMessageService;
import lottery.domains.content.biz.UserBillService;
import lottery.domains.content.dao.UserRechargeDao;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.dao.UserDao;
import org.springframework.stereotype.Service;
import lottery.domains.content.biz.UserRechargeService;

@Service
public class UserRechargeServiceImpl implements UserRechargeService
{
    @Autowired
    private UserDao uDao;
    @Autowired
    private UserRechargeDao uRechargeDao;
    @Autowired
    private UserBillService uBillService;
    @Autowired
    private UserSysMessageService uSysMessageService;
    @Autowired
    private UserWithdrawLimitService uWithdrawLimitService;
    @Autowired
    private UserLotteryReportService uLotteryReportService;
    @Autowired
    private ActivityFirstRechargeBillService activityFirstRechargeBillService;
    @Autowired
    private LotteryDataFactory lotteryDataFactory;
    @Autowired
    private PaymentChannelDao paymentChannelDao;
    @Autowired
    private UserDao userDao;
    
    private String billno() {
        return String.valueOf(new Moment().format("yyMMddHHmmss")) + RandomStringUtils.random(8, true, true);
    }
    
    @Override
    public UserRechargeVO getById(final int id) {
        final UserRecharge bean = this.uRechargeDao.getById(id);
        if (bean != null) {
            return new UserRechargeVO(bean, this.lotteryDataFactory);
        }
        return null;
    }
    
    @Override
    public HistoryUserRechargeVO getHistoryById(final int id) {
        final HistoryUserRecharge bean = this.uRechargeDao.getHistoryById(id);
        if (bean != null) {
            return new HistoryUserRechargeVO(bean, this.lotteryDataFactory);
        }
        return null;
    }
    
    @Override
    public List<UserRechargeVO> getLatest(final int userId, final int status, final int count) {
        final List<UserRechargeVO> formatList = new ArrayList<UserRechargeVO>();
        final List<UserRecharge> list = this.uRechargeDao.getLatest(userId, status, count);
        for (final UserRecharge tmpBean : list) {
            formatList.add(new UserRechargeVO(tmpBean, this.lotteryDataFactory));
        }
        return formatList;
    }
    
    @Override
    public List<UserRecharge> listByPayTimeAndStatus(final String sDate, final String eDate, final int status) {
        final List<Criterion> criterions = new ArrayList<Criterion>();
        final List<Order> orders = new ArrayList<Order>();
        orders.add(Order.desc("time"));
        orders.add(Order.desc("id"));
        criterions.add((Criterion)Restrictions.eq("status", (Object)status));
        criterions.add((Criterion)Restrictions.ge("payTime", (Object)sDate));
        criterions.add((Criterion)Restrictions.lt("payTime", (Object)eDate));
        final List<UserRecharge> rechargeList = this.uRechargeDao.list(criterions, orders);
        return rechargeList;
    }
    
    @Override
    public PageList search(final Integer type, final String billno, final String username, final String minTime, final String maxTime, final String minPayTime, final String maxPayTime, final Double minMoney, final Double maxMoney, final Integer status, final Integer channelId, final int start, final int limit) {
        final StringBuilder queryStr = new StringBuilder();
        boolean isSearch = true;
        if (StringUtil.isNotNull(username)) {
            final User user = this.uDao.getByUsername(username);
            if (user != null) {
                queryStr.append(" and b.user_id  = ").append(user.getId());
            }
            else {
                isSearch = false;
            }
        }
        if (StringUtil.isNotNull(billno)) {
            queryStr.append(" and b.billno  = ").append("'" + billno + "'");
        }
        if (StringUtil.isNotNull(minTime)) {
            queryStr.append(" and b.time  > ").append("'" + minTime + "'");
        }
        if (StringUtil.isNotNull(maxTime)) {
            queryStr.append(" and b.time  < ").append("'" + maxTime + "'");
        }
        if (StringUtil.isNotNull(minPayTime)) {
            queryStr.append(" and b.pay_time  > ").append("'" + minPayTime + "'");
        }
        if (StringUtil.isNotNull(maxPayTime)) {
            queryStr.append(" and b.pay_time  < ").append("'" + maxPayTime + "'");
        }
        if (minMoney != null) {
            queryStr.append(" and b.money  >= ").append((double)minMoney);
        }
        if (maxMoney != null) {
            queryStr.append(" and b.money  <= ").append((double)maxMoney);
        }
        if (status != null) {
            queryStr.append(" and b.status  = ").append((int)status);
        }
        if (channelId != null) {
            queryStr.append(" and b.channel_id  = ").append(channelId);
        }
        if (type != null) {
            queryStr.append(" and  u.type  = ").append(type);
        }
        else {
            queryStr.append(" and u.upid  != ").append(0);
        }
        queryStr.append("  ORDER BY b.time,b.id DESC ");
        if (isSearch) {
            final List<UserRechargeVO> list = new ArrayList<UserRechargeVO>();
            final PageList pList = this.uRechargeDao.find(queryStr.toString(), start, limit);
            for (final Object tmpBean : pList.getList()) {
                list.add(new UserRechargeVO((UserRecharge)tmpBean, this.lotteryDataFactory));
            }
            pList.setList(list);
            return pList;
        }
        return null;
    }
    
    @Override
    public PageList searchHistory(final String billno, final String username, final String minTime, final String maxTime, final String minPayTime, final String maxPayTime, final Double minMoney, final Double maxMoney, final Integer status, final Integer channelId, final int start, final int limit) {
        final List<Criterion> criterions = new ArrayList<Criterion>();
        final List<Order> orders = new ArrayList<Order>();
        orders.add(Order.desc("time"));
        orders.add(Order.desc("id"));
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
        if (StringUtil.isNotNull(billno)) {
            criterions.add((Criterion)Restrictions.eq("billno", (Object)billno));
        }
        if (StringUtil.isNotNull(minTime)) {
            criterions.add((Criterion)Restrictions.gt("time", (Object)minTime));
        }
        if (StringUtil.isNotNull(maxTime)) {
            criterions.add((Criterion)Restrictions.lt("time", (Object)maxTime));
        }
        if (StringUtil.isNotNull(minPayTime)) {
            criterions.add((Criterion)Restrictions.gt("payTime", (Object)minPayTime));
        }
        if (StringUtil.isNotNull(maxPayTime)) {
            criterions.add((Criterion)Restrictions.lt("payTime", (Object)maxPayTime));
        }
        if (minMoney != null) {
            criterions.add((Criterion)Restrictions.ge("money", (Object)minMoney));
        }
        if (maxMoney != null) {
            criterions.add((Criterion)Restrictions.le("money", (Object)maxMoney));
        }
        if (status != null) {
            criterions.add((Criterion)Restrictions.eq("status", (Object)status));
        }
        if (channelId != null) {
            criterions.add((Criterion)Restrictions.eq("channelId", (Object)channelId));
        }
        if (isSearch) {
            final List<HistoryUserRechargeVO> list = new ArrayList<HistoryUserRechargeVO>();
            final PageList pList = this.uRechargeDao.findHistory(criterions, orders, start, limit);
            for (final Object tmpBean : pList.getList()) {
                list.add(new HistoryUserRechargeVO((HistoryUserRecharge)tmpBean, this.lotteryDataFactory));
            }
            pList.setList(list);
            return pList;
        }
        return null;
    }
    
    @Override
    public boolean addSystemRecharge(final String username, final int subtype, final int account, final double amount, final int isLimit, final String remarks) {
        final User uBean = this.uDao.getByUsername(username);
        if (uBean != null) {
            if (subtype == 1 && amount > 0.0) {
                final boolean uFlag = this.uDao.updateLotteryMoney(uBean.getId(), amount);
                if (uFlag) {
                    final String billno = this.billno();
                    final double money = amount;
                    final double beforeMoney = uBean.getLotteryMoney();
                    final double afterMoney = beforeMoney + money;
                    final double recMoney = money;
                    final double feeMoney = 0.0;
                    final Moment moment = new Moment();
                    final String time = moment.toSimpleTime();
                    final int status = 1;
                    final int type = 3;
                    String infos = "系统补充值未到账。";
                    if (StringUtil.isNotNull(remarks)) {
                        infos = String.valueOf(infos) + "备注：" + remarks;
                    }
                    final UserRecharge cBean = new UserRecharge(billno, uBean.getId(), money, beforeMoney, afterMoney, recMoney, feeMoney, time, status, type, subtype);
                    cBean.setChannelId(-1);
                    cBean.setPayTime(time);
                    cBean.setInfos(infos);
                    cBean.setRemarks(remarks);
                    final boolean cFlag = this.uRechargeDao.add(cBean);
                    if (cFlag) {
                        this.uBillService.addRechargeBill(cBean, uBean, infos);
                        final double percent = this.lotteryDataFactory.getWithdrawConfig().getSystemConsumptionPercent();
                        this.uWithdrawLimitService.add(uBean.getId(), amount, time, type, subtype, percent);
                        this.uSysMessageService.addSysRecharge(uBean.getId(), amount, remarks);
                    }
                    return cFlag;
                }
            }
            if (subtype == 2 && amount > 0.0) {
                boolean uFlag = false;
                if (account == 2) {
                    uFlag = this.uDao.updateLotteryMoney(uBean.getId(), amount);
                }
                if (uFlag) {
                    String infos2 = "系统活动补贴。";
                    if (StringUtil.isNotNull(remarks)) {
                        infos2 = String.valueOf(infos2) + "备注：" + remarks;
                    }
                    final int refType = 0;
                    this.uBillService.addActivityBill(uBean, account, amount, refType, infos2);
                    if (isLimit == 1) {
                        final String time2 = new Moment().toSimpleTime();
                        final int type2 = 3;
                        final double percent2 = this.lotteryDataFactory.getWithdrawConfig().getSystemConsumptionPercent();
                        this.uWithdrawLimitService.add(uBean.getId(), amount, time2, type2, subtype, percent2);
                    }
                }
                return uFlag;
            }
            if (subtype == 3 && amount > 0.0) {
                boolean uFlag = false;
                if (account == 1) {
                    uFlag = this.uDao.updateTotalMoney(uBean.getId(), amount);
                }
                if (account == 2) {
                    uFlag = this.uDao.updateLotteryMoney(uBean.getId(), amount);
                }
                if (account == 3) {
                    uFlag = this.uDao.updateBaccaratMoney(uBean.getId(), amount);
                }
                if (uFlag) {
                    String infos2 = "管理员增加资金。";
                    if (StringUtil.isNotNull(remarks)) {
                        infos2 = String.valueOf(infos2) + "备注：" + remarks;
                    }
                    this.uBillService.addAdminAddBill(uBean, account, amount, infos2);
                }
                return uFlag;
            }
            if (subtype == 4 && amount > 0.0) {
                boolean uFlag = false;
                if (account == 1) {
                    uFlag = this.uDao.updateTotalMoney(uBean.getId(), -amount);
                }
                if (account == 2) {
                    uFlag = this.uDao.updateLotteryMoney(uBean.getId(), -amount);
                }
                if (account == 3) {
                    uFlag = this.uDao.updateBaccaratMoney(uBean.getId(), -amount);
                }
                if (uFlag) {
                    String infos2 = "管理员减少资金。";
                    if (StringUtil.isNotNull(remarks)) {
                        infos2 = String.valueOf(infos2) + "备注：" + remarks;
                    }
                    this.uBillService.addAdminMinusBill(uBean, account, amount, infos2);
                }
                return uFlag;
            }
        }
        return false;
    }
    
    @Override
    public boolean patchOrder(final String billno, final String payBillno, final String remarks) {
        final UserRecharge cBean = this.uRechargeDao.getByBillno(billno);
        if (cBean != null && cBean.getStatus() == 0) {
            final User uBean = this.uDao.getById(cBean.getUserId());
            if (uBean != null) {
                final Moment moment = new Moment();
                final String payTime = moment.toSimpleTime();
                final double money = cBean.getRecMoney();
                String infos = "充值漏单补单，" + money;
                final PaymentChannelVO channel = this.lotteryDataFactory.getPaymentChannelVO(cBean.getChannelId());
                if (channel == null) {
                    return false;
                }
                if (channel.getAddMoneyType() == 2) {
                    infos = "在线充值";
                }
                cBean.setBeforeMoney(uBean.getLotteryMoney());
                cBean.setAfterMoney(uBean.getLotteryMoney() + money);
                cBean.setStatus(1);
                cBean.setPayBillno(payBillno);
                cBean.setPayTime(payTime);
                cBean.setInfos(infos);
                cBean.setRemarks(remarks);
                final boolean result = this.uRechargeDao.update(cBean);
                if (result) {
                    final boolean flag = this.uDao.updateLotteryMoney(uBean.getId(), money);
                    if (flag) {
                        final String _remarks = "在线充值";
                        this.uBillService.addRechargeBill(cBean, uBean, _remarks);
                        this.uWithdrawLimitService.add(uBean.getId(), money, payTime, channel.getType(), channel.getSubType(), channel.getConsumptionPercent());
                        this.uSysMessageService.addOnlineRecharge(uBean.getId(), money);
                        if (uBean.getUpid() != 0) {
                            this.activityFirstRechargeBillService.tryCollect(cBean.getUserId(), money, cBean.getApplyIp());
                        }
                        if (channel.getAddMoneyType() == 2) {
                            this.paymentChannelDao.addUsedCredits(channel.getId(), money);
                        }
                    }
                }
                return result;
            }
        }
        return false;
    }
    
    @Override
    public boolean cancelOrder(final String billno) {
        final UserRecharge bean = this.uRechargeDao.getByBillno(billno);
        if (bean != null && bean.getStatus() == 0) {
            bean.setStatus(-1);
            return this.uRechargeDao.update(bean);
        }
        return false;
    }
    
    @Override
    public double getTotalRecharge(final Integer type, final String billno, final String username, final String minTime, final String maxTime, final String minPayTime, final String maxPayTime, final Double minMoney, final Double maxMoney, final Integer status, final Integer channelId) {
        Integer userId = null;
        if (StringUtil.isNotNull(username)) {
            final User user = this.uDao.getByUsername(username);
            if (user != null) {
                userId = user.getId();
            }
        }
        return this.uRechargeDao.getTotalRecharge(billno, userId, minTime, maxTime, minPayTime, maxPayTime, minMoney, maxMoney, status, channelId);
    }
    
    @Override
    public double getHistoryTotalRecharge(final String billno, final String username, final String minTime, final String maxTime, final String minPayTime, final String maxPayTime, final Double minMoney, final Double maxMoney, final Integer status, final Integer channelId) {
        Integer userId = null;
        if (StringUtil.isNotNull(username)) {
            final User user = this.uDao.getByUsername(username);
            if (user != null) {
                userId = user.getId();
            }
        }
        return this.uRechargeDao.getHistoryTotalRecharge(billno, userId, minTime, maxTime, minPayTime, maxPayTime, minMoney, maxMoney, status, channelId);
    }
}
