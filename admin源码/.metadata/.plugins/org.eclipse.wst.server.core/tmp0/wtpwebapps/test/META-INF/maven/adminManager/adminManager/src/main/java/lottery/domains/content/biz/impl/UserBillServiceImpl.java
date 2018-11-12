package lottery.domains.content.biz.impl;

import org.hibernate.criterion.Conjunction;
import lottery.domains.content.entity.HistoryUserBill;
import lottery.domains.content.vo.bill.HistoryUserBillVO;
import lottery.domains.content.entity.HistoryUserBets;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import org.springframework.transaction.annotation.Transactional;
import java.util.Iterator;
import java.util.List;
import lottery.domains.content.vo.bill.UserBillVO;
import java.util.ArrayList;
import java.util.Collection;
import org.apache.commons.collections.CollectionUtils;
import javautils.StringUtil;
import javautils.jdbc.PageList;
import lottery.domains.content.entity.UserBets;
import lottery.domains.content.entity.UserTransfers;
import lottery.domains.content.entity.UserWithdraw;
import javautils.math.MathUtil;
import lottery.domains.content.entity.UserBill;
import javautils.date.Moment;
import lottery.domains.content.entity.User;
import lottery.domains.content.entity.UserRecharge;
import org.bson.types.ObjectId;
import org.slf4j.LoggerFactory;
import lottery.domains.pool.LotteryDataFactory;
import lottery.domains.content.biz.UserBaccaratReportService;
import lottery.domains.content.biz.UserLotteryDetailsReportService;
import lottery.domains.content.biz.UserLotteryReportService;
import lottery.domains.content.biz.UserGameReportService;
import lottery.domains.content.biz.UserMainReportService;
import lottery.domains.content.dao.UserWithdrawDao;
import lottery.domains.content.dao.UserRechargeDao;
import lottery.domains.content.dao.UserBetsDao;
import lottery.domains.content.dao.UserBillDao;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.dao.UserDao;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import lottery.domains.content.biz.UserBillService;

@Service
public class UserBillServiceImpl implements UserBillService
{
    private static final Logger logger;
    @Autowired
    private UserDao uDao;
    @Autowired
    private UserBillDao uBillDao;
    @Autowired
    private UserBetsDao uBetsDao;
    @Autowired
    private UserRechargeDao uRechargeDao;
    @Autowired
    private UserWithdrawDao uWithdrawDao;
    @Autowired
    private UserMainReportService uMainReportService;
    @Autowired
    private UserGameReportService uGameReportService;
    @Autowired
    private UserLotteryReportService uLotteryReportService;
    @Autowired
    private UserLotteryDetailsReportService uLotteryDetailsReportService;
    @Autowired
    private UserBaccaratReportService uBaccaratReportService;
    @Autowired
    private LotteryDataFactory lotteryDataFactory;
    
    static {
        logger = LoggerFactory.getLogger((Class)UserBillServiceImpl.class);
    }
    
    private String billno() {
        return ObjectId.get().toString();
    }
    
    @Override
    public boolean addRechargeBill(final UserRecharge cBean, final User uBean, final String remarks) {
        boolean flag = false;
        try {
            final String billno = this.billno();
            final int userId = uBean.getId();
            final int account = 2;
            final int type = 1;
            final double money = cBean.getRecMoney();
            final double beforeMoney = uBean.getLotteryMoney();
            final double afterMoney = beforeMoney + money;
            final Integer refType = type;
            final String refId = cBean.getBillno();
            final Moment thisTime = new Moment();
            final UserBill tmpBill = new UserBill(billno, userId, account, type, money, beforeMoney, afterMoney, refType, refId, thisTime.toSimpleTime(), remarks);
            flag = this.uBillDao.add(tmpBill);
            if (flag) {
                this.uMainReportService.update(userId, type, money, thisTime.toSimpleDate());
                if (this.lotteryDataFactory.getRechargeConfig().getFeePercent() > 0.0) {
                    final double feeAmount = MathUtil.multiply(money, this.lotteryDataFactory.getRechargeConfig().getFeePercent());
                    if (feeAmount > 0.0) {
                        this.uLotteryReportService.updateRechargeFee(uBean.getId(), feeAmount, thisTime.toSimpleDate());
                    }
                }
            }
        }
        catch (Exception e) {
            UserBillServiceImpl.logger.error("写入存款账单失败！", (Throwable)e);
        }
        return flag;
    }
    
    @Override
    public boolean addWithdrawReport(final UserWithdraw wBean) {
        final boolean flag = false;
        try {
            final int userId = wBean.getUserId();
            final int type = 2;
            final double money = wBean.getMoney();
            final Moment thisTime = new Moment();
            this.uMainReportService.update(userId, type, money, thisTime.toSimpleDate());
        }
        catch (Exception e) {
            UserBillServiceImpl.logger.error("写入取款账单失败！", (Throwable)e);
        }
        return flag;
    }
    
    @Override
    public boolean addDrawBackBill(final UserWithdraw wBean, final User uBean, final String remarks) {
        final boolean flag = false;
        try {
            final String billno = this.billno();
            final int userId = wBean.getUserId();
            final int account = 2;
            final int type = 16;
            final double money = wBean.getMoney();
            final double beforeMoney = uBean.getLotteryMoney();
            final double afterMoney = beforeMoney + money;
            final Integer refType = null;
            final String refId = wBean.getBillno();
            final Moment thisTime = new Moment();
            final UserBill tmpBill = new UserBill(billno, userId, account, type, money, beforeMoney, afterMoney, refType, refId, thisTime.toSimpleTime(), remarks);
            return this.uBillDao.add(tmpBill);
        }
        catch (Exception e) {
            UserBillServiceImpl.logger.error("写入取款账单失败！", (Throwable)e);
            return flag;
        }
    }
    
    @Override
    public boolean addTransInBill(final UserTransfers tBean, final User uBean, final int account, final String remarks) {
        boolean flag = false;
        try {
            final String billno = this.billno();
            final int userId = uBean.getId();
            final int type = 3;
            final double money = tBean.getMoney();
            double beforeMoney = 0.0;
            if (account == 1) {
                beforeMoney = uBean.getTotalMoney();
            }
            if (account == 2) {
                beforeMoney = uBean.getLotteryMoney();
            }
            if (account == 3) {
                beforeMoney = uBean.getBaccaratMoney();
            }
            final double afterMoney = beforeMoney + money;
            final Integer refType = null;
            final String refId = tBean.getBillno();
            final Moment thisTime = new Moment();
            final UserBill tmpBill = new UserBill(billno, userId, account, type, money, beforeMoney, afterMoney, refType, refId, thisTime.toSimpleTime(), remarks);
            flag = this.uBillDao.add(tmpBill);
            if (flag) {
                if (account == 1) {
                    this.uMainReportService.update(userId, type, money, thisTime.toSimpleDate());
                }
                if (account == 2) {
                    this.uLotteryReportService.update(userId, type, money, thisTime.toSimpleDate());
                }
                if (account == 3) {
                    this.uBaccaratReportService.update(userId, type, money, thisTime.toSimpleDate());
                }
            }
        }
        catch (Exception e) {
            UserBillServiceImpl.logger.error("写入转入账单失败！", (Throwable)e);
        }
        return flag;
    }
    
    @Override
    public boolean addTransOutBill(final UserTransfers tBean, final User uBean, final int account, final String remarks) {
        boolean flag = false;
        try {
            final String billno = this.billno();
            final int userId = uBean.getId();
            final int type = 4;
            final double money = tBean.getMoney();
            double beforeMoney = 0.0;
            if (account == 1) {
                beforeMoney = uBean.getTotalMoney();
            }
            if (account == 2) {
                beforeMoney = uBean.getLotteryMoney();
            }
            if (account == 3) {
                beforeMoney = uBean.getBaccaratMoney();
            }
            final double afterMoney = beforeMoney - money;
            final Integer refType = null;
            final String refId = tBean.getBillno();
            final Moment thisTime = new Moment();
            final UserBill tmpBill = new UserBill(billno, userId, account, type, money, beforeMoney, afterMoney, refType, refId, thisTime.toSimpleTime(), remarks);
            flag = this.uBillDao.add(tmpBill);
            if (flag) {
                if (account == 1) {
                    this.uMainReportService.update(userId, type, money, thisTime.toSimpleDate());
                }
                if (account == 2) {
                    this.uLotteryReportService.update(userId, type, money, thisTime.toSimpleDate());
                }
                if (account == 3) {
                    this.uBaccaratReportService.update(userId, type, money, thisTime.toSimpleDate());
                }
            }
        }
        catch (Exception e) {
            UserBillServiceImpl.logger.error("写入转出账单失败！", (Throwable)e);
        }
        return flag;
    }
    
    @Override
    public boolean addActivityBill(final User uBean, final int account, final double amount, final int refType, final String remarks) {
        boolean flag = false;
        try {
            final String billno = this.billno();
            final int userId = uBean.getId();
            final int type = 5;
            final double money = amount;
            double beforeMoney = 0.0;
            if (account == 1) {
                beforeMoney = uBean.getTotalMoney();
            }
            if (account == 2) {
                beforeMoney = uBean.getLotteryMoney();
            }
            if (account == 3) {
                beforeMoney = uBean.getBaccaratMoney();
            }
            final double afterMoney = beforeMoney + money;
            final String refId = null;
            final Moment thisTime = new Moment();
            final UserBill tmpBill = new UserBill(billno, userId, account, type, money, beforeMoney, afterMoney, refType, refId, thisTime.toSimpleTime(), remarks);
            flag = this.uBillDao.add(tmpBill);
            if (flag) {
                if (account == 1) {
                    this.uMainReportService.update(userId, type, money, thisTime.toSimpleDate());
                }
                if (account == 2) {
                    this.uLotteryReportService.update(userId, type, money, thisTime.toSimpleDate());
                }
                if (account == 3) {
                    this.uBaccaratReportService.update(userId, type, money, thisTime.toSimpleDate());
                }
            }
        }
        catch (Exception e) {
            UserBillServiceImpl.logger.error("写入活动账单失败！", (Throwable)e);
        }
        return flag;
    }
    
    @Override
    public boolean addAdminAddBill(final User uBean, final int account, final double amount, final String remarks) {
        final boolean flag = false;
        try {
            final String billno = this.billno();
            final int userId = uBean.getId();
            final int type = 13;
            final double money = amount;
            double beforeMoney = 0.0;
            if (account == 1) {
                beforeMoney = uBean.getTotalMoney();
            }
            if (account == 2) {
                beforeMoney = uBean.getLotteryMoney();
            }
            if (account == 3) {
                beforeMoney = uBean.getBaccaratMoney();
            }
            final double afterMoney = beforeMoney + money;
            final Integer refType = null;
            final String refId = null;
            final Moment thisTime = new Moment();
            final UserBill tmpBill = new UserBill(billno, userId, account, type, money, beforeMoney, afterMoney, refType, refId, thisTime.toSimpleTime(), remarks);
            return this.uBillDao.add(tmpBill);
        }
        catch (Exception e) {
            UserBillServiceImpl.logger.error("写入管理员增加资金失败！", (Throwable)e);
            return flag;
        }
    }
    
    @Override
    public boolean addAdminMinusBill(final User uBean, final int account, final double amount, final String remarks) {
        final boolean flag = false;
        try {
            final String billno = this.billno();
            final int userId = uBean.getId();
            final int type = 14;
            final double money = amount;
            double beforeMoney = 0.0;
            if (account == 1) {
                beforeMoney = uBean.getTotalMoney();
            }
            if (account == 2) {
                beforeMoney = uBean.getLotteryMoney();
            }
            if (account == 3) {
                beforeMoney = uBean.getBaccaratMoney();
            }
            final double afterMoney = beforeMoney - money;
            final Integer refType = null;
            final String refId = null;
            final Moment thisTime = new Moment();
            final UserBill tmpBill = new UserBill(billno, userId, account, type, money, beforeMoney, afterMoney, refType, refId, thisTime.toSimpleTime(), remarks);
            return this.uBillDao.add(tmpBill);
        }
        catch (Exception e) {
            UserBillServiceImpl.logger.error("写入管理员减少资金失败！", (Throwable)e);
            return flag;
        }
    }
    
    @Override
    public boolean addSpendBill(final UserBets bBean, final User uBean) {
        boolean flag = false;
        try {
            final String billno = this.billno();
            final int userId = uBean.getId();
            final int account = 2;
            final int type = 6;
            final double money = bBean.getMoney();
            final double beforeMoney = uBean.getLotteryMoney();
            final double afterMoney = beforeMoney - money;
            final Integer refType = null;
            final String refId = String.valueOf(bBean.getId());
            final Moment thisTime = new Moment();
            final String remarks = "用户投注：" + bBean.getExpect();
            final UserBill tmpBill = new UserBill(billno, userId, account, type, money, beforeMoney, afterMoney, refType, refId, thisTime.toSimpleTime(), remarks);
            flag = this.uBillDao.add(tmpBill);
            if (flag) {
                this.uLotteryReportService.update(userId, type, money, thisTime.toSimpleDate());
                this.uLotteryDetailsReportService.update(userId, bBean.getLotteryId(), bBean.getRuleId(), type, money, thisTime.toSimpleDate());
            }
        }
        catch (Exception e) {
            UserBillServiceImpl.logger.error("写入彩票消费失败！", (Throwable)e);
        }
        return flag;
    }
    
    @Override
    public boolean addCancelOrderBill(final UserBets bBean, final User uBean) {
        boolean flag = false;
        try {
            final String billno = this.billno();
            final int userId = uBean.getId();
            final int account = 2;
            final int type = 10;
            final double money = bBean.getMoney();
            final double beforeMoney = uBean.getLotteryMoney();
            final double afterMoney = beforeMoney + money;
            final Integer refType = null;
            final String refId = String.valueOf(bBean.getId());
            final Moment thisTime = new Moment();
            final String remarks = "用户撤单：" + bBean.getExpect();
            final UserBill tmpBill = new UserBill(billno, userId, account, type, money, beforeMoney, afterMoney, refType, refId, thisTime.toSimpleTime(), remarks);
            flag = this.uBillDao.add(tmpBill);
            if (flag) {
                this.uLotteryReportService.update(userId, type, money, thisTime.toSimpleDate());
                this.uLotteryDetailsReportService.update(userId, bBean.getLotteryId(), bBean.getRuleId(), type, money, thisTime.toSimpleDate());
            }
        }
        catch (Exception e) {
            UserBillServiceImpl.logger.error("写入彩票撤单失败！", (Throwable)e);
        }
        return flag;
    }
    
    @Override
    public boolean addDividendBill(final User uBean, final int account, final double amount, final String remarks, final boolean activity) {
        boolean flag = false;
        try {
            final String billno = this.billno();
            final int userId = uBean.getId();
            final int type = 12;
            final double money = amount;
            double beforeMoney = 0.0;
            if (account == 1) {
                beforeMoney = uBean.getTotalMoney();
            }
            if (account == 2) {
                beforeMoney = uBean.getLotteryMoney();
            }
            if (account == 3) {
                beforeMoney = uBean.getBaccaratMoney();
            }
            final double afterMoney = beforeMoney + money;
            final Integer refType = null;
            final String refId = null;
            final Moment thisTime = new Moment();
            final UserBill tmpBill = new UserBill(billno, userId, account, type, Math.abs(money), beforeMoney, afterMoney, refType, refId, thisTime.toSimpleTime(), remarks);
            flag = this.uBillDao.add(tmpBill);
            if (flag && activity) {
                if (account == 1) {
                    this.uMainReportService.update(userId, type, money, thisTime.toSimpleDate());
                }
                if (account == 2) {
                    this.uLotteryReportService.update(userId, type, money, thisTime.toSimpleDate());
                }
                if (account == 3) {
                    this.uBaccaratReportService.update(userId, type, money, thisTime.toSimpleDate());
                }
            }
        }
        catch (Exception e) {
            UserBillServiceImpl.logger.error("写入分红账单失败！", (Throwable)e);
        }
        return flag;
    }
    
    @Override
    public boolean addRewardPayBill(final User uBean, final int account, final double amount, final String remarks) {
        boolean flag = false;
        try {
            final String billno = this.billno();
            final int userId = uBean.getId();
            final int type = 18;
            final double money = amount;
            double beforeMoney = 0.0;
            if (account == 1) {
                beforeMoney = uBean.getTotalMoney();
            }
            if (account == 2) {
                beforeMoney = uBean.getLotteryMoney();
            }
            if (account == 3) {
                beforeMoney = uBean.getBaccaratMoney();
            }
            final double afterMoney = beforeMoney - money;
            final Integer refType = null;
            final String refId = null;
            final Moment thisTime = new Moment();
            final UserBill tmpBill = new UserBill(billno, userId, account, type, money, beforeMoney, afterMoney, refType, refId, thisTime.toSimpleTime(), remarks);
            flag = this.uBillDao.add(tmpBill);
            if (flag) {
                if (account == 1) {
                    this.uMainReportService.update(userId, type, money, thisTime.toSimpleDate());
                }
                if (account == 2) {
                    this.uLotteryReportService.update(userId, type, money, thisTime.toSimpleDate());
                }
                if (account == 3) {
                    this.uBaccaratReportService.update(userId, type, money, thisTime.toSimpleDate());
                }
            }
        }
        catch (Exception e) {
            UserBillServiceImpl.logger.error("写入支付佣金账单失败！", (Throwable)e);
        }
        return flag;
    }
    
    @Override
    public boolean addRewardIncomeBill(final User uBean, final int account, final double amount, final String remarks) {
        boolean flag = false;
        try {
            final String billno = this.billno();
            final int userId = uBean.getId();
            final int type = 19;
            final double money = amount;
            double beforeMoney = 0.0;
            if (account == 1) {
                beforeMoney = uBean.getTotalMoney();
            }
            if (account == 2) {
                beforeMoney = uBean.getLotteryMoney();
            }
            if (account == 3) {
                beforeMoney = uBean.getBaccaratMoney();
            }
            final double afterMoney = beforeMoney + money;
            final Integer refType = null;
            final String refId = null;
            final Moment thisTime = new Moment();
            final UserBill tmpBill = new UserBill(billno, userId, account, type, money, beforeMoney, afterMoney, refType, refId, thisTime.toSimpleTime(), remarks);
            flag = this.uBillDao.add(tmpBill);
            if (flag) {
                if (account == 1) {
                    this.uMainReportService.update(userId, type, money, thisTime.toSimpleDate());
                }
                if (account == 2) {
                    this.uLotteryReportService.update(userId, type, money, thisTime.toSimpleDate());
                }
                if (account == 3) {
                    this.uBaccaratReportService.update(userId, type, money, thisTime.toSimpleDate());
                }
            }
        }
        catch (Exception e) {
            UserBillServiceImpl.logger.error("写入收取佣金账单失败！", (Throwable)e);
        }
        return flag;
    }
    
    @Override
    public boolean addRewardReturnBill(final User uBean, final int account, final double amount, final String remarks) {
        boolean flag = false;
        try {
            final String billno = this.billno();
            final int userId = uBean.getId();
            final int type = 20;
            final double money = amount;
            double beforeMoney = 0.0;
            if (account == 1) {
                beforeMoney = uBean.getTotalMoney();
            }
            if (account == 2) {
                beforeMoney = uBean.getLotteryMoney();
            }
            if (account == 3) {
                beforeMoney = uBean.getBaccaratMoney();
            }
            final double afterMoney = beforeMoney + money;
            final Integer refType = null;
            final String refId = null;
            final Moment thisTime = new Moment();
            final UserBill tmpBill = new UserBill(billno, userId, account, type, money, beforeMoney, afterMoney, refType, refId, thisTime.toSimpleTime(), remarks);
            flag = this.uBillDao.add(tmpBill);
            if (flag) {
                if (account == 1) {
                    this.uMainReportService.update(userId, type, money, thisTime.toSimpleDate());
                }
                if (account == 2) {
                    this.uLotteryReportService.update(userId, type, money, thisTime.toSimpleDate());
                }
                if (account == 3) {
                    this.uBaccaratReportService.update(userId, type, money, thisTime.toSimpleDate());
                }
            }
        }
        catch (Exception e) {
            UserBillServiceImpl.logger.error("写入退还佣金账单失败！", (Throwable)e);
        }
        return flag;
    }
    
    @Override
    public boolean addDailySettleBill(final User uBean, final int account, final double amount, final String remarks, final boolean activity) {
        boolean flag = false;
        try {
            final String billno = this.billno();
            final int userId = uBean.getId();
            final int type = 22;
            final double money = amount;
            double beforeMoney = 0.0;
            if (account == 1) {
                beforeMoney = uBean.getTotalMoney();
            }
            if (account == 2) {
                beforeMoney = uBean.getLotteryMoney();
            }
            if (account == 3) {
                beforeMoney = uBean.getBaccaratMoney();
            }
            final double afterMoney = beforeMoney + money;
            final Integer refType = null;
            final String refId = null;
            final Moment thisTime = new Moment();
            final UserBill tmpBill = new UserBill(billno, userId, account, type, Math.abs(money), beforeMoney, afterMoney, refType, refId, thisTime.toSimpleTime(), remarks);
            flag = this.uBillDao.add(tmpBill);
            if (flag && activity) {
                if (account == 1) {
                    this.uMainReportService.update(userId, 5, Math.abs(money), thisTime.toSimpleDate());
                }
                if (account == 2) {
                    this.uLotteryReportService.update(userId, 5, Math.abs(money), thisTime.toSimpleDate());
                }
                if (account == 3) {
                    this.uBaccaratReportService.update(userId, 5, Math.abs(money), thisTime.toSimpleDate());
                }
            }
        }
        catch (Exception e) {
            UserBillServiceImpl.logger.error("写入退还佣金账单失败！", (Throwable)e);
        }
        return flag;
    }
    
    @Override
    public boolean addGameWaterBill(final User uBean, final int account, final int type, final double amount, final String remarks) {
        boolean flag = false;
        try {
            final String billno = this.billno();
            final int userId = uBean.getId();
            final double money = amount;
            double beforeMoney = 0.0;
            final int _type = (type == 1) ? 11 : 9;
            if (account == 1) {
                beforeMoney = uBean.getTotalMoney();
            }
            if (account == 2) {
                beforeMoney = uBean.getLotteryMoney();
            }
            if (account == 3) {
                beforeMoney = uBean.getBaccaratMoney();
            }
            final double afterMoney = beforeMoney + money;
            final Integer refType = null;
            final String refId = null;
            final Moment thisTime = new Moment();
            final UserBill tmpBill = new UserBill(billno, userId, account, _type, money, beforeMoney, afterMoney, refType, refId, thisTime.toSimpleTime(), remarks);
            flag = this.uBillDao.add(tmpBill);
            if (flag) {
                if (type == 1) {
                    this.uGameReportService.update(userId, 4, 0.0, 0.0, amount, 0.0, thisTime.toSimpleDate());
                }
                else {
                    this.uGameReportService.update(userId, 4, 0.0, 0.0, 0.0, amount, thisTime.toSimpleDate());
                }
            }
        }
        catch (Exception e) {
            UserBillServiceImpl.logger.error("写入退还佣金账单失败！", (Throwable)e);
        }
        return flag;
    }
    
    @Transactional(readOnly = true)
    @Override
    public PageList search(final String keyword, final String username, final Integer utype, final Integer type, final String minTime, final String maxTime, final Double minMoney, final Double maxMoney, final Integer status, final int start, final int limit) {
        boolean isSearch = true;
        User targetUser = null;
        final StringBuilder sqlStr = new StringBuilder();
        if (StringUtil.isNotNull(username)) {
            targetUser = this.uDao.getByUsername(username);
            if (targetUser != null) {
                sqlStr.append("\tand b.user_id = ").append(targetUser.getId());
            }
            else {
                isSearch = false;
            }
        }
        if (StringUtil.isNotNull(keyword)) {
            final List<UserBets> tmpBets = this.uBetsDao.getByBillno(keyword, false);
            final UserBets uBets = CollectionUtils.isNotEmpty((Collection)tmpBets) ? tmpBets.get(0) : null;
            if (uBets != null) {
                sqlStr.append("\tand b.ref_id = ").append("'" + uBets.getId() + "'");
                sqlStr.append("\tand b.account = ").append(2);
                sqlStr.append("\tand b.type  in ").append("(6,7,8,9,10)");
            }
        }
        if (type != null) {
            sqlStr.append(" and b.type  =").append((int)type);
        }
        if (utype != null) {
            sqlStr.append(" and u.type  =").append((int)utype);
        }
        else {
            sqlStr.append("  and u.upid != ").append(0);
        }
        if (StringUtil.isNotNull(minTime)) {
            sqlStr.append(" and b.time  >=").append("'" + minTime + "'");
        }
        if (StringUtil.isNotNull(maxTime)) {
            sqlStr.append(" and b.time  <=").append("'" + maxTime + "'");
        }
        if (minMoney != null) {
            sqlStr.append("  and ABS(b.money) >= ").append((double)minMoney);
        }
        if (maxMoney != null) {
            sqlStr.append("  and ABS(b.money) <= ").append((double)maxMoney);
        }
        if (status != null) {
            sqlStr.append("  and b.status = ").append((int)status);
        }
        sqlStr.append("   ORDER BY  b.time,  b.id desc ");
        if (isSearch) {
            final List<UserBillVO> list = new ArrayList<UserBillVO>();
            final PageList pList = this.uBillDao.findNoDemoUserBill(sqlStr.toString(), start, limit);
            for (final Object tmpBean : pList.getList()) {
                list.add(new UserBillVO((UserBill)tmpBean, this.lotteryDataFactory));
            }
            pList.setList(list);
            return pList;
        }
        return null;
    }
    
    @Transactional(readOnly = true)
    @Override
    public PageList searchHistory(final String keyword, final String username, final Integer type, final String minTime, final String maxTime, final Double minMoney, final Double maxMoney, final Integer status, final int start, final int limit) {
        final List<Criterion> criterions = new ArrayList<Criterion>();
        final List<Order> orders = new ArrayList<Order>();
        boolean isSearch = true;
        User targetUser = null;
        if (StringUtil.isNotNull(username)) {
            targetUser = this.uDao.getByUsername(username);
            if (targetUser != null) {
                criterions.add((Criterion)Restrictions.eq("userId", (Object)targetUser.getId()));
            }
            else {
                isSearch = false;
            }
        }
        if (StringUtil.isNotNull(keyword)) {
            boolean isOrder = false;
            final Conjunction conjunctionBill = Restrictions.conjunction();
            conjunctionBill.add((Criterion)Restrictions.like("billno", keyword, MatchMode.ANYWHERE));
            final Conjunction conjunctionOrder = Restrictions.conjunction();
            final List<HistoryUserBets> tmpBets = this.uBetsDao.getHistoryByBillno(keyword, false);
            if (tmpBets.size() > 0) {
                isOrder = true;
                conjunctionOrder.add((Criterion)Restrictions.eq("account", (Object)2));
                conjunctionOrder.add(Restrictions.in("type", (Object[])new Integer[] { 6, 7, 9, 9, 10 }));
                final List<String> targetIds = new ArrayList<String>();
                for (final HistoryUserBets tmpBean : tmpBets) {
                    targetIds.add(String.valueOf(tmpBean.getId()));
                }
                conjunctionOrder.add(Restrictions.in("refId", (Collection)targetIds));
            }
            if (isOrder) {
                criterions.add((Criterion)conjunctionOrder);
            }
            else {
                criterions.add((Criterion)conjunctionBill);
            }
        }
        if (type != null) {
            criterions.add((Criterion)Restrictions.eq("type", (Object)type));
        }
        if (StringUtil.isNotNull(minTime)) {
            criterions.add((Criterion)Restrictions.ge("time", (Object)minTime));
        }
        if (StringUtil.isNotNull(maxTime)) {
            criterions.add((Criterion)Restrictions.lt("time", (Object)maxTime));
        }
        if (minMoney != null) {
            criterions.add(Restrictions.sqlRestriction("ABS(money) >= " + (double)minMoney));
        }
        if (maxMoney != null) {
            criterions.add(Restrictions.sqlRestriction("ABS(money) <= " + (double)maxMoney));
        }
        if (status != null) {
            criterions.add((Criterion)Restrictions.eq("status", (Object)status));
        }
        orders.add(Order.desc("time"));
        orders.add(Order.desc("id"));
        if (isSearch) {
            final List<HistoryUserBillVO> list = new ArrayList<HistoryUserBillVO>();
            final PageList pList = this.uBillDao.findHistory(criterions, orders, start, limit);
            for (final Object tmpBean2 : pList.getList()) {
                final HistoryUserBillVO tmpVO = new HistoryUserBillVO((HistoryUserBill)tmpBean2, this.lotteryDataFactory);
                list.add(tmpVO);
            }
            pList.setList(list);
            return pList;
        }
        return null;
    }
    
    @Transactional(readOnly = true)
    @Override
    public List<UserBillVO> getLatest(final int userId, final int type, final int count) {
        final List<UserBillVO> formatList = new ArrayList<UserBillVO>();
        final List<UserBill> list = this.uBillDao.getLatest(userId, type, count);
        for (final UserBill tmpBean : list) {
            formatList.add(new UserBillVO(tmpBean, this.lotteryDataFactory));
        }
        return formatList;
    }
}
