package lottery.domains.content.biz.impl;

import javautils.date.Moment;
import lottery.domains.content.entity.User;
import lottery.domains.content.entity.ActivityFirstRechargeBill;
import lottery.domains.content.vo.activity.ActivityFirstRechargeBillVO;
import org.hibernate.criterion.Restrictions;
import javautils.StringUtil;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.ArrayList;
import javautils.jdbc.PageList;
import java.util.Iterator;
import java.util.List;
import lottery.domains.content.entity.activity.ActivityFirstRechargeConfigVO;
import lottery.domains.content.entity.activity.ActivityFirstRechargeConfigRule;
import lottery.domains.content.biz.UserSysMessageService;
import lottery.domains.pool.LotteryDataFactory;
import lottery.domains.content.dao.UserDao;
import lottery.domains.content.biz.UserBillService;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.dao.ActivityFirstRechargeBillDao;
import org.springframework.stereotype.Service;
import lottery.domains.content.biz.ActivityFirstRechargeBillService;

@Service
public class ActivityFirstRechargeBillServiceImpl implements ActivityFirstRechargeBillService
{
    @Autowired
    private ActivityFirstRechargeBillDao billDao;
    @Autowired
    private UserBillService uBillService;
    @Autowired
    private UserDao uDao;
    @Autowired
    private LotteryDataFactory dataFactory;
    @Autowired
    private UserSysMessageService uSysMessageService;
    
    private double chooseAmount(final double rechargeAmount) {
        final ActivityFirstRechargeConfigVO config = this.dataFactory.getActivityFirstRechargeConfig();
        if (config == null || config.getStatus() != 1) {
            return 0.0;
        }
        final List<ActivityFirstRechargeConfigRule> rules = config.getRuleVOs();
        for (final ActivityFirstRechargeConfigRule rule : rules) {
            if (rechargeAmount >= rule.getMinRecharge() && (rule.getMaxRecharge() <= -1.0 || rechargeAmount <= rule.getMaxRecharge())) {
                return rule.getAmount();
            }
        }
        return 0.0;
    }
    
    @Override
    public PageList find(final String username, final String sDate, final String eDate, final String ip, final int start, final int limit) {
        final List<Criterion> criterions = new ArrayList<Criterion>();
        final List<Order> orders = new ArrayList<Order>();
        if (StringUtil.isNotNull(username)) {
            final User user = this.uDao.getByUsername(username);
            if (user == null) {
                return new PageList();
            }
            criterions.add((Criterion)Restrictions.eq("userId", (Object)user.getId()));
        }
        if (StringUtil.isNotNull(sDate)) {
            criterions.add((Criterion)Restrictions.ge("date", (Object)sDate));
        }
        if (StringUtil.isNotNull(eDate)) {
            criterions.add((Criterion)Restrictions.lt("date", (Object)eDate));
        }
        if (StringUtil.isNotNull(ip)) {
            criterions.add((Criterion)Restrictions.eq("ip", (Object)ip));
        }
        orders.add(Order.desc("time"));
        orders.add(Order.desc("id"));
        final List<ActivityFirstRechargeBillVO> list = new ArrayList<ActivityFirstRechargeBillVO>();
        final PageList pList = this.billDao.find(criterions, orders, start, limit);
        for (final Object tmpBean : pList.getList()) {
            final ActivityFirstRechargeBillVO tmpVO = new ActivityFirstRechargeBillVO((ActivityFirstRechargeBill)tmpBean, this.dataFactory);
            list.add(tmpVO);
        }
        pList.setList(list);
        return pList;
    }
    
    @Override
    public double sumAmount(final String username, final String sDate, final String eDate, final String ip) {
        Integer userId = null;
        if (StringUtil.isNotNull(username)) {
            final User user = this.uDao.getByUsername(username);
            if (user != null) {
                userId = user.getId();
            }
        }
        return this.billDao.sumAmount(userId, sDate, eDate, ip);
    }
    
    @Override
    public double tryCollect(final int userId, final double rechargeAmount, final String ip) {
        final ActivityFirstRechargeConfigVO config = this.dataFactory.getActivityFirstRechargeConfig();
        if (config == null || config.getStatus() != 1) {
            return 0.0;
        }
        final String date = new Moment().toSimpleDate();
        final ActivityFirstRechargeBill ipBill = this.billDao.getByDateAndIp(date, ip);
        if (ipBill != null) {
            return 0.0;
        }
        final ActivityFirstRechargeBill userBill = this.billDao.getByUserIdAndDate(userId, date);
        if (userBill != null) {
            return 0.0;
        }
        final double amount = this.chooseAmount(rechargeAmount);
        if (amount <= 0.0) {
            return 0.0;
        }
        final ActivityFirstRechargeBill firstRechargeBill = new ActivityFirstRechargeBill();
        firstRechargeBill.setUserId(userId);
        firstRechargeBill.setDate(date);
        firstRechargeBill.setTime(new Moment().toSimpleTime());
        firstRechargeBill.setRecharge(rechargeAmount);
        firstRechargeBill.setAmount(amount);
        firstRechargeBill.setIp(ip);
        final boolean added = this.billDao.add(firstRechargeBill);
        if (added) {
            final User user = this.uDao.getById(userId);
            final boolean addedBill = this.uBillService.addActivityBill(user, 2, amount, firstRechargeBill.getId(), "首充活动");
            if (addedBill) {
                this.uDao.updateLotteryMoney(userId, amount);
                this.uSysMessageService.addFirstRecharge(userId, rechargeAmount, amount);
            }
            return amount;
        }
        return 0.0;
    }
}
