package lottery.domains.content.biz.impl;

import lottery.domains.content.vo.user.UserVO;
import javautils.math.MathUtil;
import java.util.Arrays;
import lottery.domains.content.entity.User;
import java.util.Iterator;
import lottery.domains.content.entity.UserDailySettleBill;
import lottery.domains.content.vo.user.UserDailySettleBillVO;
import org.hibernate.criterion.Order;
import javautils.StringUtil;
import org.hibernate.criterion.Restrictions;
import java.util.Collection;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.criterion.Criterion;
import java.util.ArrayList;
import javautils.jdbc.PageList;
import java.util.List;
import lottery.domains.pool.LotteryDataFactory;
import lottery.domains.content.dao.UserDao;
import lottery.domains.content.dao.UserDailySettleDao;
import lottery.domains.content.dao.UserDailySettleBillDao;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.biz.UserBillService;
import org.springframework.stereotype.Service;
import lottery.domains.content.biz.UserDailySettleBillService;

@Service
public class UserDailySettleBillServiceImpl implements UserDailySettleBillService
{
    @Autowired
    private UserBillService uBillService;
    @Autowired
    private UserDailySettleBillDao dailySettleBillDao;
    @Autowired
    private UserDailySettleDao uDailySettleDao;
    @Autowired
    private UserDao uDao;
    @Autowired
    private LotteryDataFactory dataFactory;
    
    @Override
    public PageList search(final List<Integer> userIds, final String sTime, final String eTime, final Double minUserAmount, final Double maxUserAmount, final Integer status, int start, int limit) {
        start = ((start < 0) ? 0 : start);
        limit = ((limit < 0) ? 0 : limit);
        limit = ((limit > 20) ? 20 : limit);
        final List<Criterion> criterions = new ArrayList<Criterion>();
        if (CollectionUtils.isNotEmpty((Collection)userIds)) {
            criterions.add(Restrictions.in("userId", (Collection)userIds));
        }
        if (StringUtil.isNotNull(sTime)) {
            criterions.add((Criterion)Restrictions.ge("indicateDate", (Object)sTime));
        }
        if (StringUtil.isNotNull(eTime)) {
            criterions.add((Criterion)Restrictions.lt("indicateDate", (Object)eTime));
        }
        if (minUserAmount != null) {
            criterions.add((Criterion)Restrictions.ge("userAmount", (Object)minUserAmount));
        }
        if (maxUserAmount != null) {
            criterions.add((Criterion)Restrictions.le("userAmount", (Object)maxUserAmount));
        }
        if (status != null) {
            criterions.add((Criterion)Restrictions.eq("status", (Object)status));
        }
        final List<Order> orders = new ArrayList<Order>();
        orders.add(Order.desc("id"));
        final PageList pList = this.dailySettleBillDao.search(criterions, orders, start, limit);
        final List<UserDailySettleBillVO> convertList = new ArrayList<UserDailySettleBillVO>();
        if (pList != null && pList.getList() != null) {
            for (final Object tmpBean : pList.getList()) {
                convertList.add(new UserDailySettleBillVO((UserDailySettleBill)tmpBean, this.dataFactory));
            }
        }
        pList.setList(convertList);
        return pList;
    }
    
    @Override
    public List<UserDailySettleBill> findByCriteria(final List<Criterion> criterions, final List<Order> orders) {
        return this.dailySettleBillDao.findByCriteria(criterions, orders);
    }
    
    @Override
    public List<UserDailySettleBill> getDirectLowerBills(final int userId, final String indicateDate, final Integer[] status, final Integer issueType) {
        final List<User> userLowers = this.uDao.getUserDirectLower(userId);
        if (CollectionUtils.isEmpty((Collection)userLowers)) {
            return new ArrayList<UserDailySettleBill>();
        }
        final List<Integer> userIds = new ArrayList<Integer>();
        final List<Criterion> criterions = new ArrayList<Criterion>();
        if (CollectionUtils.isNotEmpty((Collection)userLowers)) {
            for (final User userLower : userLowers) {
                userIds.add(userLower.getId());
            }
            criterions.add(Restrictions.in("userId", (Collection)userIds));
        }
        if (status != null && status.length > 0) {
            criterions.add(Restrictions.in("status", (Collection)Arrays.asList(status)));
        }
        if (issueType != null) {
            criterions.add((Criterion)Restrictions.eq("issueType", (Object)issueType));
        }
        if (StringUtil.isNotNull(indicateDate)) {
            criterions.add((Criterion)Restrictions.eq("indicateDate", (Object)indicateDate));
        }
        return this.dailySettleBillDao.findByCriteria(criterions, null);
    }
    
    @Override
    public boolean add(final UserDailySettleBill settleBill) {
        final boolean added = this.dailySettleBillDao.add(settleBill);
        if (!added) {
            return added;
        }
        this.uDailySettleDao.updateTotalAmount(settleBill.getUserId(), settleBill.getUserAmount());
        return added;
    }
    
    @Override
    public boolean update(final UserDailySettleBill settleBill) {
        return this.dailySettleBillDao.update(settleBill);
    }
    
    @Override
    public synchronized UserDailySettleBill issue(final int id) {
        final UserDailySettleBill upperBill = this.dailySettleBillDao.getById(id);
        if (upperBill == null || upperBill.getStatus() != 3) {
            return upperBill;
        }
        final Integer[] status = { 2, 3 };
        final List<UserDailySettleBill> lowerBills = this.getDirectLowerBills(upperBill.getUserId(), upperBill.getIndicateDate(), status, 2);
        if (CollectionUtils.isEmpty((Collection)lowerBills)) {
            upperBill.setRemarks("已发放");
            upperBill.setStatus(1);
            this.update(upperBill);
            return upperBill;
        }
        double upperBillMoney = 0.0;
        if (upperBill.getIssueType() == 2) {
            upperBillMoney = upperBill.getAvailableAmount();
            upperBill.setAvailableAmount(0.0);
        }
        double upperThisTimePaid = 0.0;
        for (final UserDailySettleBill lowerBill : lowerBills) {
            final double lowerAmount = lowerBill.getCalAmount();
            double lowerRemainReceived = MathUtil.subtract(lowerAmount, lowerBill.getTotalReceived());
            double billGive = 0.0;
            if (upperBillMoney > 0.0 && lowerRemainReceived > 0.0) {
                billGive = ((lowerAmount >= upperBillMoney) ? upperBillMoney : lowerAmount);
                upperBillMoney = MathUtil.subtract(upperBillMoney, billGive);
                lowerRemainReceived = MathUtil.subtract(lowerRemainReceived, billGive);
            }
            double totalMoneyGive = 0.0;
            final User upperUser = this.uDao.getById(upperBill.getUserId());
            if (lowerRemainReceived > 0.0 && upperUser.getTotalMoney() > 0.0) {
                totalMoneyGive = ((lowerRemainReceived >= upperUser.getTotalMoney()) ? upperUser.getTotalMoney() : lowerRemainReceived);
                lowerRemainReceived = MathUtil.subtract(lowerRemainReceived, totalMoneyGive);
            }
            double lotteryMoneyGive = 0.0;
            if (lowerRemainReceived > 0.0 && upperUser.getLotteryMoney() > 0.0) {
                lotteryMoneyGive = ((lowerRemainReceived >= upperUser.getLotteryMoney()) ? upperUser.getLotteryMoney() : lowerRemainReceived);
                lowerRemainReceived = MathUtil.subtract(lowerRemainReceived, lotteryMoneyGive);
            }
            final double totalGive = MathUtil.add(MathUtil.add(billGive, totalMoneyGive), lotteryMoneyGive);
            if (totalGive > 0.0) {
                if (totalMoneyGive > 0.0) {
                    final UserVO subUser = this.dataFactory.getUser(lowerBill.getUserId());
                    this.uDao.updateTotalMoney(upperUser.getId(), -totalMoneyGive);
                    this.uBillService.addDailySettleBill(upperUser, 1, -totalMoneyGive, "系统自动扣发" + totalMoneyGive + "日结金额到" + subUser.getUsername(), false);
                }
                if (lotteryMoneyGive > 0.0) {
                    final UserVO subUser = this.dataFactory.getUser(lowerBill.getUserId());
                    this.uDao.updateLotteryMoney(upperUser.getId(), -lotteryMoneyGive);
                    this.uBillService.addDailySettleBill(upperUser, 2, -lotteryMoneyGive, "系统自动扣发" + lotteryMoneyGive + "日结金额到" + subUser.getUsername(), false);
                }
                upperThisTimePaid = MathUtil.add(upperThisTimePaid, totalGive);
                lowerBill.setTotalReceived(MathUtil.add(lowerBill.getTotalReceived(), totalGive));
                if (lowerBill.getStatus() == 2) {
                    final User lowerUser = this.uDao.getById(lowerBill.getUserId());
                    final boolean addedBill = this.uBillService.addDailySettleBill(lowerUser, 2, totalGive, "系统自动从上级账户中扣发日结金额", true);
                    if (addedBill) {
                        if (lowerRemainReceived <= 0.0) {
                            lowerBill.setRemarks("已发放");
                            lowerBill.setStatus(1);
                        }
                        this.uDao.updateLotteryMoney(lowerBill.getUserId(), totalGive);
                    }
                }
                else {
                    lowerBill.setAvailableAmount(totalGive);
                }
                this.dailySettleBillDao.update(lowerBill);
            }
        }
        upperBill.setLowerPaidAmount(MathUtil.add(upperBill.getLowerPaidAmount(), upperThisTimePaid));
        if (upperBillMoney > 0.0) {
            final User upperUser2 = this.uDao.getById(upperBill.getUserId());
            if (upperUser2 != null) {
                final boolean addedBill2 = this.uBillService.addDailySettleBill(upperUser2, 2, upperBillMoney, "系统自动从上级账户中扣发日结金额", true);
                if (addedBill2) {
                    upperBill.setRemarks("已发放");
                    upperBill.setStatus(1);
                    this.dailySettleBillDao.update(upperBill);
                    this.uDao.updateLotteryMoney(upperBill.getUserId(), upperBillMoney);
                }
            }
        }
        else {
            final double notYetPay = MathUtil.subtract(upperBill.getLowerTotalAmount(), upperBill.getLowerPaidAmount());
            if (notYetPay > 0.0) {
                this.dailySettleBillDao.update(upperBill);
            }
            else {
                upperBill.setRemarks("已发放");
                upperBill.setStatus(1);
                this.dailySettleBillDao.update(upperBill);
            }
        }
        return upperBill;
    }
}
