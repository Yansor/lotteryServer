package lottery.domains.content.biz.impl;

import javautils.date.Moment;
import java.util.Iterator;
import org.hibernate.criterion.Disjunction;
import lottery.domains.content.entity.User;
import java.util.List;
import lottery.domains.content.entity.ActivityRechargeBill;
import lottery.domains.content.vo.activity.ActivityRechargeBillVO;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import javautils.StringUtil;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.ArrayList;
import javautils.jdbc.PageList;
import lottery.domains.pool.LotteryDataFactory;
import lottery.domains.content.biz.UserWithdrawLimitService;
import lottery.domains.content.biz.UserSysMessageService;
import lottery.domains.content.biz.UserBillService;
import lottery.domains.content.dao.ActivityRechargeBillDao;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.dao.UserDao;
import org.springframework.stereotype.Service;
import lottery.domains.content.biz.ActivityRechargeService;

@Service
public class ActivityRechargeServiceImpl implements ActivityRechargeService
{
    @Autowired
    private UserDao uDao;
    @Autowired
    private ActivityRechargeBillDao aRechargeBillDao;
    @Autowired
    private UserBillService uBillService;
    @Autowired
    private UserSysMessageService uSysMessageService;
    @Autowired
    private UserWithdrawLimitService uWithdrawLimitService;
    @Autowired
    private LotteryDataFactory lotteryDataFactory;
    
    @Override
    public PageList search(final String username, final String date, final String keyword, final Integer status, final int start, final int limit) {
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
        if (StringUtil.isNotNull(date)) {
            criterions.add((Criterion)Restrictions.like("time", date, MatchMode.ANYWHERE));
        }
        if (StringUtil.isNotNull(keyword)) {
            final Disjunction disjunction = Restrictions.or(new Criterion[0]);
            disjunction.add((Criterion)Restrictions.eq("ip", (Object)keyword));
            criterions.add((Criterion)disjunction);
        }
        if (status != null) {
            criterions.add((Criterion)Restrictions.eq("status", (Object)status));
        }
        orders.add(Order.desc("id"));
        if (isSearch) {
            final List<ActivityRechargeBillVO> list = new ArrayList<ActivityRechargeBillVO>();
            final PageList pList = this.aRechargeBillDao.find(criterions, orders, start, limit);
            for (final Object tmpBean : pList.getList()) {
                list.add(new ActivityRechargeBillVO((ActivityRechargeBill)tmpBean, this.lotteryDataFactory));
            }
            pList.setList(list);
            return pList;
        }
        return null;
    }
    
    @Override
    public boolean agree(final int id) {
        final ActivityRechargeBill entity = this.aRechargeBillDao.getById(id);
        if (entity != null && entity.getStatus() == 0) {
            final String thisTime = new Moment().toSimpleTime();
            entity.setStatus(1);
            entity.setTime(thisTime);
            final boolean aFlag = this.aRechargeBillDao.update(entity);
            if (aFlag) {
                final User uBean = this.uDao.getById(entity.getUserId());
                final boolean uFlag = this.uDao.updateLotteryMoney(uBean.getId(), entity.getMoney());
                if (uFlag) {
                    final int type = 3;
                    final int subType = 2;
                    final double percent = this.lotteryDataFactory.getWithdrawConfig().getSystemConsumptionPercent();
                    this.uWithdrawLimitService.add(uBean.getId(), entity.getMoney(), thisTime, type, subType, percent);
                    final String remarks = "开业大酬宾。";
                    final int refType = 1;
                    this.uBillService.addActivityBill(uBean, 2, entity.getMoney(), refType, remarks);
                    this.uSysMessageService.addActivityRecharge(uBean.getId(), entity.getMoney());
                }
                return uFlag;
            }
        }
        return false;
    }
    
    @Override
    public boolean refuse(final int id) {
        final ActivityRechargeBill entity = this.aRechargeBillDao.getById(id);
        if (entity != null && entity.getStatus() == 0) {
            entity.setStatus(-1);
            return this.aRechargeBillDao.update(entity);
        }
        return false;
    }
    
    @Override
    public boolean check(final int id) {
        final ActivityRechargeBill entity = this.aRechargeBillDao.getById(id);
        if (entity != null) {
            final String date = new Moment().fromTime(entity.getPayTime()).toSimpleDate();
            final List<ActivityRechargeBill> list = this.aRechargeBillDao.get(entity.getIp(), date);
            if (list != null && list.size() > 1) {
                return true;
            }
        }
        return false;
    }
}
