package lottery.domains.content.biz.impl;

import org.apache.commons.lang.StringUtils;
import admin.web.WebJSONObject;
import javautils.math.MathUtil;
import lottery.domains.content.vo.user.UserVO;
import java.util.Set;
import javautils.array.ArrayUtils;
import lottery.domains.content.entity.User;
import java.util.HashSet;
import java.util.Iterator;
import lottery.domains.content.entity.UserDividendBill;
import lottery.domains.content.vo.user.UserDividendBillVO;
import org.hibernate.criterion.Order;
import javautils.StringUtil;
import org.hibernate.criterion.Restrictions;
import java.util.Collection;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.criterion.Criterion;
import java.util.ArrayList;
import javautils.jdbc.PageList;
import java.util.List;
import lottery.domains.content.biz.UserSysMessageService;
import lottery.web.content.utils.UserCodePointUtil;
import lottery.domains.content.biz.UserBillService;
import lottery.domains.content.dao.UserDao;
import lottery.domains.pool.LotteryDataFactory;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.dao.UserDividendBillDao;
import org.springframework.stereotype.Service;
import lottery.domains.content.biz.UserDividendBillService;

@Service
public class UserDividendBillServiceImpl implements UserDividendBillService
{
    @Autowired
    private UserDividendBillDao uDividendBillDao;
    @Autowired
    private LotteryDataFactory dataFactory;
    @Autowired
    private UserDao uDao;
    @Autowired
    private UserBillService uBillService;
    @Autowired
    private UserCodePointUtil uCodePointUtil;
    @Autowired
    private UserSysMessageService uSysMessageService;
    
    @Override
    public PageList search(final List<Integer> userIds, final String sTime, final String eTime, final Double minUserAmount, final Double maxUserAmount, final Integer status, final Integer issueType, int start, int limit) {
        start = ((start < 0) ? 0 : start);
        limit = ((limit < 0) ? 0 : limit);
        limit = ((limit > 20) ? 20 : limit);
        final List<Criterion> criterions = new ArrayList<Criterion>();
        if (CollectionUtils.isNotEmpty((Collection)userIds)) {
            criterions.add(Restrictions.in("userId", (Collection)userIds));
        }
        if (StringUtil.isNotNull(sTime)) {
            criterions.add((Criterion)Restrictions.ge("indicateStartDate", (Object)sTime));
        }
        if (StringUtil.isNotNull(eTime)) {
            criterions.add((Criterion)Restrictions.le("indicateEndDate", (Object)eTime));
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
        if (issueType != null) {
            criterions.add((Criterion)Restrictions.eq("issueType", (Object)issueType));
        }
        final List<Order> orders = new ArrayList<Order>();
        orders.add(Order.desc("id"));
        final PageList pList = this.uDividendBillDao.search(criterions, orders, start, limit);
        final List<UserDividendBillVO> voList = new ArrayList<UserDividendBillVO>();
        if (pList != null && pList.getList() != null) {
            for (final Object tmpBean : pList.getList()) {
                voList.add(new UserDividendBillVO((UserDividendBill)tmpBean, this.dataFactory));
            }
        }
        pList.setList(voList);
        return pList;
    }
    
    @Override
    public PageList searchPlatformLoss(final List<Integer> userIds, final String sTime, final String eTime, final Double minUserAmount, final Double maxUserAmount, int start, int limit) {
        start = ((start < 0) ? 0 : start);
        limit = ((limit < 0) ? 0 : limit);
        limit = ((limit > 20) ? 20 : limit);
        final List<Criterion> criterions = new ArrayList<Criterion>();
        if (CollectionUtils.isNotEmpty((Collection)userIds)) {
            criterions.add(Restrictions.in("userId", (Collection)userIds));
        }
        if (StringUtil.isNotNull(sTime)) {
            criterions.add((Criterion)Restrictions.ge("indicateStartDate", (Object)sTime));
        }
        if (StringUtil.isNotNull(eTime)) {
            criterions.add((Criterion)Restrictions.le("indicateEndDate", (Object)eTime));
        }
        if (minUserAmount != null) {
            criterions.add((Criterion)Restrictions.ge("userAmount", (Object)minUserAmount));
        }
        if (maxUserAmount != null) {
            criterions.add((Criterion)Restrictions.le("userAmount", (Object)maxUserAmount));
        }
        criterions.add((Criterion)Restrictions.eq("issueType", (Object)1));
        criterions.add((Criterion)Restrictions.lt("totalLoss", (Object)0.0));
        final List<Integer> status = new ArrayList<Integer>();
        status.add(1);
        status.add(2);
        status.add(3);
        status.add(6);
        criterions.add(Restrictions.in("status", (Collection)status));
        final List<Order> orders = new ArrayList<Order>();
        orders.add(Order.desc("id"));
        final PageList pList = this.uDividendBillDao.search(criterions, orders, start, limit);
        List<UserDividendBillVO> voList = new ArrayList<UserDividendBillVO>();
        if (pList != null && pList.getList() != null) {
            for (final Object tmpBean : pList.getList()) {
                voList.add(new UserDividendBillVO((UserDividendBill)tmpBean, this.dataFactory));
            }
        }
        voList = this.convertUserLevels(voList);
        pList.setList(voList);
        return pList;
    }
    
    private List<UserDividendBillVO> convertUserLevels(final List<UserDividendBillVO> voList) {
        final Set<Integer> userIds = new HashSet<Integer>();
        for (final UserDividendBillVO userDividendBillVO : voList) {
            userIds.add(userDividendBillVO.getBean().getUserId());
        }
        final List<Criterion> criterions = new ArrayList<Criterion>();
        criterions.add(Restrictions.in("id", (Collection)userIds));
        final List<User> users = this.uDao.list(criterions, null);
        for (final UserDividendBillVO userDividendBillVO2 : voList) {
            for (final User user : users) {
                if (userDividendBillVO2.getBean().getUserId() == user.getId()) {
                    final int[] upIds = ArrayUtils.transGetIds(user.getUpids());
                    int[] array;
                    for (int length = (array = upIds).length, i = 0; i < length; ++i) {
                        final int upId = array[i];
                        final UserVO upUser = this.dataFactory.getUser(upId);
                        if (upUser != null) {
                            userDividendBillVO2.getUserLevels().add(upUser.getUsername());
                        }
                    }
                }
            }
        }
        return voList;
    }
    
    @Override
    public double[] sumUserAmount(final List<Integer> userIds, final String sTime, final String eTime, final Double minUserAmount, final Double maxUserAmount) {
        return this.uDividendBillDao.sumUserAmount(userIds, sTime, eTime, minUserAmount, maxUserAmount);
    }
    
    @Override
    public List<UserDividendBill> findByCriteria(final List<Criterion> criterions, final List<Order> orders) {
        return this.uDividendBillDao.findByCriteria(criterions, orders);
    }
    
    @Override
    public boolean updateAllExpire() {
        return this.uDividendBillDao.updateAllExpire();
    }
    
    @Override
    public List<UserDividendBill> getLowerBills(final int userId, final String sTime, final String eTime) {
        final List<User> userLowers = this.uDao.getUserLower(userId);
        if (CollectionUtils.isEmpty((Collection)userLowers)) {
            return new ArrayList<UserDividendBill>();
        }
        final List<Integer> userIds = new ArrayList<Integer>();
        final List<Criterion> criterions = new ArrayList<Criterion>();
        if (CollectionUtils.isNotEmpty((Collection)userLowers)) {
            for (final User userLower : userLowers) {
                userIds.add(userLower.getId());
            }
            criterions.add(Restrictions.in("userId", (Collection)userIds));
        }
        if (CollectionUtils.isEmpty((Collection)userIds)) {
            return new ArrayList<UserDividendBill>();
        }
        if (StringUtil.isNotNull(sTime)) {
            criterions.add((Criterion)Restrictions.ge("indicateStartDate", (Object)sTime));
        }
        if (StringUtil.isNotNull(eTime)) {
            criterions.add((Criterion)Restrictions.le("indicateEndDate", (Object)eTime));
        }
        return this.uDividendBillDao.findByCriteria(criterions, null);
    }
    
    @Override
    public List<UserDividendBill> getDirectLowerBills(final int userId, final String sTime, final String eTime) {
        final List<User> userLowers = this.uDao.getUserDirectLower(userId);
        if (CollectionUtils.isEmpty((Collection)userLowers)) {
            return new ArrayList<UserDividendBill>();
        }
        final List<Integer> userIds = new ArrayList<Integer>();
        final List<Criterion> criterions = new ArrayList<Criterion>();
        if (CollectionUtils.isNotEmpty((Collection)userLowers)) {
            for (final User userLower : userLowers) {
                userIds.add(userLower.getId());
            }
            criterions.add(Restrictions.in("userId", (Collection)userIds));
        }
        if (CollectionUtils.isEmpty((Collection)userIds)) {
            return new ArrayList<UserDividendBill>();
        }
        if (StringUtil.isNotNull(sTime)) {
            criterions.add((Criterion)Restrictions.ge("indicateStartDate", (Object)sTime));
        }
        if (StringUtil.isNotNull(eTime)) {
            criterions.add((Criterion)Restrictions.le("indicateEndDate", (Object)eTime));
        }
        return this.uDividendBillDao.findByCriteria(criterions, null);
    }
    
    @Override
    public UserDividendBill getById(final int id) {
        return this.uDividendBillDao.getById(id);
    }
    
    @Override
    public UserDividendBill getBill(final int userId, final String sTime, final String eTime) {
        final List<Criterion> criterions = new ArrayList<Criterion>();
        criterions.add((Criterion)Restrictions.eq("userId", (Object)userId));
        if (StringUtil.isNotNull(sTime)) {
            criterions.add((Criterion)Restrictions.ge("indicateStartDate", (Object)sTime));
        }
        if (StringUtil.isNotNull(eTime)) {
            criterions.add((Criterion)Restrictions.le("indicateEndDate", (Object)eTime));
        }
        final List<Order> orders = new ArrayList<Order>();
        orders.add(Order.desc("id"));
        final PageList pList = this.uDividendBillDao.search(criterions, orders, 0, 1);
        if (pList != null && CollectionUtils.isNotEmpty((Collection)pList.getList())) {
            return (UserDividendBill)pList.getList().get(0);
        }
        return null;
    }
    
    @Override
    public boolean add(final UserDividendBill dividendBill) {
        return this.uDividendBillDao.add(dividendBill);
    }
    
    @Override
    public boolean addAvailableMoney(final int id, final double money) {
        return this.uDividendBillDao.addAvailableMoney(id, money);
    }
    
    @Override
    public synchronized void issueInsufficient(final int id) {
        final UserDividendBill dividendBill = this.getById(id);
        if (dividendBill.getStatus() != 6) {
            return;
        }
        if (dividendBill.getLowerTotalAmount() <= 0.0) {
            return;
        }
        double upperBillMoney = 0.0;
        final double upperLowerTotalAmount = dividendBill.getLowerTotalAmount();
        final double upperLowerPaidAmount = dividendBill.getLowerPaidAmount();
        if (dividendBill.getIssueType() != 1) {
            upperBillMoney = dividendBill.getAvailableAmount();
        }
        double upperStillNotPay = MathUtil.subtract(upperLowerTotalAmount, upperLowerPaidAmount);
        if (upperStillNotPay <= 0.0) {
            if (upperBillMoney > 0.0) {
                this.uDividendBillDao.updateStatus(dividendBill.getId(), 3);
            }
            else {
                this.uDividendBillDao.updateStatus(dividendBill.getId(), 1);
            }
            return;
        }
        double upperThisTimePaid = 0.0;
        final List<UserDividendBill> directLowerBills = this.getDirectLowerBills(dividendBill.getUserId(), dividendBill.getIndicateStartDate(), dividendBill.getIndicateEndDate());
        for (final UserDividendBill directLowerBill : directLowerBills) {
            if (directLowerBill.getStatus() != 3 && directLowerBill.getStatus() != 6 && directLowerBill.getStatus() != 7) {
                continue;
            }
            double lowerCalAmount = directLowerBill.getUserAmount();
            if (directLowerBill.getStatus() == 6) {
                lowerCalAmount = MathUtil.add(lowerCalAmount, directLowerBill.getCalAmount());
            }
            if (lowerCalAmount <= 0.0) {
                continue;
            }
            final double lowerReceived = MathUtil.add(directLowerBill.getAvailableAmount(), directLowerBill.getTotalReceived());
            double lowerRemainReceived = MathUtil.subtract(lowerCalAmount, lowerReceived);
            if (lowerRemainReceived <= 0.1) {
                continue;
            }
            double billGive = 0.0;
            if (upperBillMoney > 0.0 && lowerRemainReceived > 0.0) {
                billGive = ((lowerRemainReceived >= upperBillMoney) ? upperBillMoney : lowerRemainReceived);
                upperBillMoney = MathUtil.subtract(upperBillMoney, billGive);
                lowerRemainReceived = MathUtil.subtract(lowerRemainReceived, billGive);
                upperStillNotPay = MathUtil.subtract(upperStillNotPay, billGive);
            }
            double totalMoneyGive = 0.0;
            final User upUser = this.uDao.getById(dividendBill.getUserId());
            if (lowerRemainReceived > 0.0 && upperStillNotPay > 0.0 && upUser.getTotalMoney() > 0.0) {
                totalMoneyGive = ((lowerRemainReceived >= upUser.getTotalMoney()) ? upUser.getTotalMoney() : lowerRemainReceived);
                lowerRemainReceived = MathUtil.subtract(lowerRemainReceived, totalMoneyGive);
                upperStillNotPay = MathUtil.subtract(upperStillNotPay, totalMoneyGive);
            }
            double lotteryMoneyGive = 0.0;
            if (lowerRemainReceived > 0.0 && upperStillNotPay > 0.0 && upUser.getLotteryMoney() > 0.0) {
                lotteryMoneyGive = ((lowerRemainReceived >= upUser.getLotteryMoney()) ? upUser.getLotteryMoney() : lowerRemainReceived);
                lowerRemainReceived = MathUtil.subtract(lowerRemainReceived, lotteryMoneyGive);
                upperStillNotPay = MathUtil.subtract(upperStillNotPay, lotteryMoneyGive);
            }
            final double totalGive = MathUtil.add(MathUtil.add(billGive, totalMoneyGive), lotteryMoneyGive);
            if (totalGive <= 0.0) {
                break;
            }
            if (totalMoneyGive > 0.0) {
                final UserVO subUser = this.dataFactory.getUser(directLowerBill.getUserId());
                this.uDao.updateTotalMoney(upUser.getId(), -totalMoneyGive);
                this.uBillService.addDividendBill(upUser, 1, -totalMoneyGive, "系统自动扣发" + totalMoneyGive + "分红金额到" + subUser.getUsername(), false);
            }
            if (lotteryMoneyGive > 0.0) {
                final UserVO subUser = this.dataFactory.getUser(directLowerBill.getUserId());
                this.uDao.updateLotteryMoney(upUser.getId(), -lotteryMoneyGive);
                this.uBillService.addDividendBill(upUser, 2, -lotteryMoneyGive, "系统自动扣发" + lotteryMoneyGive + "分红金额到" + subUser.getUsername(), false);
            }
            upperThisTimePaid = MathUtil.add(upperThisTimePaid, totalGive);
            this.uDividendBillDao.addAvailableMoney(directLowerBill.getId(), totalGive);
            if (directLowerBill.getStatus() != 6) {
                continue;
            }
            this.uDividendBillDao.addTotalReceived(directLowerBill.getId(), totalGive);
        }
        if (upperThisTimePaid > 0.0) {
            this.uDividendBillDao.addLowerPaidAmount(dividendBill.getId(), upperThisTimePaid);
        }
        if (dividendBill.getIssueType() == 1 && upperStillNotPay <= 0.1) {
            this.uDividendBillDao.update(dividendBill.getId(), 1, "");
        }
        else if (dividendBill.getIssueType() == 2) {
            final double upperRemains = MathUtil.subtract(dividendBill.getAvailableAmount(), upperThisTimePaid);
            if (upperRemains > 0.0) {
                this.uDividendBillDao.update(dividendBill.getId(), 3, upperRemains, "");
            }
            else {
                this.uDividendBillDao.setAvailableMoney(dividendBill.getId(), 0.0);
            }
        }
    }
    
    @Override
    public boolean agree(final WebJSONObject json, final int id, final String remarks) {
        final UserDividendBill dividendBill = this.getById(id);
        if (dividendBill.getStatus() != 2) {
            json.set(2, "2-3004");
            return false;
        }
        if (dividendBill.getIssueType() == 2) {
            final User user = this.uDao.getById(dividendBill.getUserId());
            final UserDividendBill bill = this.getBill(user.getUpid(), dividendBill.getIndicateStartDate(), dividendBill.getIndicateEndDate());
            if (bill != null && bill.getStatus() == 2) {
                final UserVO upper = this.dataFactory.getUser(user.getUpid());
                json.setWithParams(2, "2-3003", (upper == null) ? "" : upper.getUsername());
                return false;
            }
        }
        boolean updated = false;
        if (dividendBill.getLowerTotalAmount() > 0.0) {
            final double[] result = this.agreeProcess(id);
            double stillNotPay = result[0];
            final double availableAmount = result[1];
            final double upperNotYetPay = result[2];
            stillNotPay -= 0.1;
            if (stillNotPay > 0.0) {
                String _remarks = remarks;
                if (StringUtils.isEmpty(_remarks)) {
                    _remarks = "余额不足，请充值";
                }
                updated = this.uDividendBillDao.update(id, 6, _remarks);
            }
            else if (availableAmount <= 0.0) {
                if (upperNotYetPay > 0.0) {
                    updated = this.uDividendBillDao.update(id, 7, remarks);
                }
                else {
                    updated = this.uDividendBillDao.update(id, 1, remarks);
                }
            }
            else if (availableAmount > 0.0) {
                updated = this.uDividendBillDao.update(id, 3, remarks);
            }
        }
        else {
            if (dividendBill.getIssueType() == 1) {
                this.uDividendBillDao.addAvailableMoney(id, dividendBill.getCalAmount());
            }
            updated = this.uDividendBillDao.update(id, 3, remarks);
        }
        if (updated) {
            this.uSysMessageService.addDividendBill(dividendBill.getUserId(), dividendBill.getIndicateStartDate(), dividendBill.getIndicateEndDate());
        }
        return updated;
    }
    
    private double[] agreeProcess(final int id) {
        final UserDividendBill dividendBill = this.getById(id);
        if (dividendBill.getStatus() != 2 && dividendBill.getStatus() != 3 && dividendBill.getStatus() != 6 && dividendBill.getStatus() != 7) {
            return new double[] { 0.0, 0.0 };
        }
        if (dividendBill.getLowerTotalAmount() <= 0.0) {
            if (dividendBill.getIssueType() == 1) {
                this.uDividendBillDao.addAvailableMoney(id, dividendBill.getCalAmount());
            }
            return new double[] { 0.0, dividendBill.getCalAmount() };
        }
        final double upperLowerTotalAmount = dividendBill.getLowerTotalAmount();
        final double upperLowerPaidAmount = dividendBill.getLowerPaidAmount();
        double upperBillMoney;
        if (dividendBill.getIssueType() == 1) {
            upperBillMoney = dividendBill.getCalAmount();
        }
        else {
            upperBillMoney = dividendBill.getAvailableAmount();
        }
        double upperStillNotPay = MathUtil.subtract(upperLowerTotalAmount, upperLowerPaidAmount);
        if (upperStillNotPay <= 0.0) {
            if (dividendBill.getIssueType() == 1) {
                this.uDividendBillDao.addAvailableMoney(id, upperBillMoney);
            }
            return new double[] { 0.0, upperBillMoney };
        }
        double upperThisTimePaid = 0.0;
        final List<UserDividendBill> directLowerBills = this.getDirectLowerBills(dividendBill.getUserId(), dividendBill.getIndicateStartDate(), dividendBill.getIndicateEndDate());
        double accountPayout = 0.0;
        for (final UserDividendBill directLowerBill : directLowerBills) {
            if (directLowerBill.getStatus() != 2 && directLowerBill.getStatus() != 3 && directLowerBill.getStatus() != 6 && directLowerBill.getStatus() != 7) {
                continue;
            }
            final double lowerCalAmount = directLowerBill.getCalAmount();
            if (lowerCalAmount <= 0.0) {
                continue;
            }
            final double lowerReceived = MathUtil.add(directLowerBill.getAvailableAmount(), directLowerBill.getTotalReceived());
            double lowerRemainReceived = MathUtil.subtract(lowerCalAmount, lowerReceived);
            if (lowerRemainReceived <= 0.1) {
                continue;
            }
            double billGive = 0.0;
            if (upperBillMoney > 0.0 && lowerRemainReceived > 0.0) {
                billGive = ((lowerRemainReceived >= upperBillMoney) ? upperBillMoney : lowerRemainReceived);
                upperBillMoney = MathUtil.subtract(upperBillMoney, billGive);
                lowerRemainReceived = MathUtil.subtract(lowerRemainReceived, billGive);
                upperStillNotPay = MathUtil.subtract(upperStillNotPay, billGive);
            }
            double totalMoneyGive = 0.0;
            final User upUser = this.uDao.getById(dividendBill.getUserId());
            if (lowerRemainReceived > 0.0 && upperStillNotPay > 0.0 && upUser.getTotalMoney() > 0.0) {
                totalMoneyGive = ((lowerRemainReceived >= upUser.getTotalMoney()) ? upUser.getTotalMoney() : lowerRemainReceived);
                lowerRemainReceived = MathUtil.subtract(lowerRemainReceived, totalMoneyGive);
                upperStillNotPay = MathUtil.subtract(upperStillNotPay, totalMoneyGive);
            }
            double lotteryMoneyGive = 0.0;
            if (lowerRemainReceived > 0.0 && upperStillNotPay > 0.0 && upUser.getLotteryMoney() > 0.0) {
                lotteryMoneyGive = ((lowerRemainReceived >= upUser.getLotteryMoney()) ? upUser.getLotteryMoney() : lowerRemainReceived);
                lowerRemainReceived = MathUtil.subtract(lowerRemainReceived, lotteryMoneyGive);
                upperStillNotPay = MathUtil.subtract(upperStillNotPay, lotteryMoneyGive);
            }
            final double totalGive = MathUtil.add(MathUtil.add(billGive, totalMoneyGive), lotteryMoneyGive);
            if (totalGive <= 0.0) {
                break;
            }
            if (totalMoneyGive > 0.0) {
                final UserVO subUser = this.dataFactory.getUser(directLowerBill.getUserId());
                this.uDao.updateTotalMoney(upUser.getId(), -totalMoneyGive);
                this.uBillService.addDividendBill(upUser, 1, -totalMoneyGive, "系统自动扣发" + totalMoneyGive + "分红金额到" + subUser.getUsername(), false);
            }
            if (lotteryMoneyGive > 0.0) {
                final UserVO subUser = this.dataFactory.getUser(directLowerBill.getUserId());
                this.uDao.updateLotteryMoney(upUser.getId(), -lotteryMoneyGive);
                this.uBillService.addDividendBill(upUser, 2, -lotteryMoneyGive, "系统自动扣发" + lotteryMoneyGive + "分红金额到" + subUser.getUsername(), false);
            }
            upperThisTimePaid = MathUtil.add(upperThisTimePaid, totalGive);
            this.uDividendBillDao.addAvailableMoney(directLowerBill.getId(), totalGive);
            accountPayout = MathUtil.add(totalMoneyGive, lotteryMoneyGive);
        }
        final User user = this.uDao.getById(dividendBill.getUserId());
        final UserDividendBill upperBill = this.uDividendBillDao.getByUserId(user.getUpid(), dividendBill.getIndicateStartDate(), dividendBill.getIndicateEndDate());
        double upperNotYetPay = 0.0;
        if (upperBill != null && upperBill.getStatus() == 6 && accountPayout > 0.0) {
            final double meReceived = MathUtil.add(dividendBill.getAvailableAmount(), dividendBill.getTotalReceived());
            final double meRemainReceived = MathUtil.subtract(dividendBill.getCalAmount(), meReceived);
            if (meRemainReceived > 0.0) {
                final double addUserAmount = (accountPayout > meRemainReceived) ? meRemainReceived : accountPayout;
                this.uDividendBillDao.addUserAmount(dividendBill.getId(), addUserAmount);
                upperNotYetPay = addUserAmount;
            }
        }
        if (upperThisTimePaid > 0.0) {
            this.uDividendBillDao.addLowerPaidAmount(dividendBill.getId(), upperThisTimePaid);
        }
        if (dividendBill.getIssueType() == 1 && dividendBill.getStatus() == 2 && upperBillMoney > 0.0 && dividendBill.getCalAmount() > 0.0 && upperStillNotPay <= 0.1 && dividendBill.getAvailableAmount() <= 0.0) {
            this.uDividendBillDao.setAvailableMoney(dividendBill.getId(), upperBillMoney);
        }
        else if (dividendBill.getIssueType() == 2) {
            final double upperRemains = MathUtil.subtract(dividendBill.getAvailableAmount(), upperThisTimePaid);
            if (upperRemains > 0.0) {
                this.uDividendBillDao.addAvailableMoney(dividendBill.getId(), -upperThisTimePaid);
            }
            else {
                this.uDividendBillDao.setAvailableMoney(dividendBill.getId(), 0.0);
            }
        }
        return new double[] { upperStillNotPay, upperBillMoney, upperNotYetPay };
    }
    
    @Override
    public boolean deny(final WebJSONObject json, final int id, final String remarks) {
        final UserDividendBill dividendBill = this.getById(id);
        if (dividendBill.getStatus() != 2) {
            json.set(2, "2-3004");
            return false;
        }
        final User user = this.uDao.getById(dividendBill.getUserId());
        if (user == null) {
            json.set(2, "2-32");
            return false;
        }
        final boolean isZhuGuan = this.uCodePointUtil.isLevel1Proxy(user);
        if (isZhuGuan) {
            return this.uDividendBillDao.update(id, 4, remarks);
        }
        json.set(2, "2-3005");
        return false;
    }
    
    @Override
    public boolean del(final WebJSONObject json, final int id) {
        return this.uDividendBillDao.del(id);
    }
    
    @Override
    public boolean reset(final WebJSONObject json, final int id, final String remarks) {
        final UserDividendBill userDividendBill = this.getById(id);
        if (userDividendBill == null) {
            json.set(2, "2-3001");
            return false;
        }
        if (userDividendBill.getStatus() != 6) {
            json.set(2, "2-3002");
            return false;
        }
        final double stillNotPay = MathUtil.subtract(userDividendBill.getLowerTotalAmount(), userDividendBill.getLowerPaidAmount());
        if (stillNotPay <= 0.0) {
            json.set(2, "2-3002");
            return false;
        }
        this.uDividendBillDao.updateStatus(id, 1, remarks);
        final List<UserDividendBill> lowerBills = this.getLowerBills(userDividendBill.getUserId(), userDividendBill.getIndicateStartDate(), userDividendBill.getIndicateEndDate());
        if (CollectionUtils.isNotEmpty((Collection)lowerBills)) {
            for (final UserDividendBill lowerBill : lowerBills) {
                if (lowerBill.getStatus() == 3 || lowerBill.getStatus() == 7) {
                    this.uDividendBillDao.updateStatus(lowerBill.getId(), 1, remarks);
                }
            }
        }
        return true;
    }
    
    @Override
    public double queryPeriodCollect(final int userId, final String sTime, final String eTime) {
        final List<Criterion> criterions = new ArrayList<Criterion>();
        criterions.add((Criterion)Restrictions.ge("collectTime", (Object)sTime));
        criterions.add((Criterion)Restrictions.lt("collectTime", (Object)eTime));
        criterions.add((Criterion)Restrictions.eq("userId", (Object)userId));
        final List<UserDividendBill> lists = this.uDividendBillDao.findByCriteria(criterions, null);
        double result = 0.0;
        if (lists == null || lists.isEmpty()) {
            return result;
        }
        for (final UserDividendBill bill : lists) {
            result += bill.getUserAmount();
        }
        return result;
    }
}
