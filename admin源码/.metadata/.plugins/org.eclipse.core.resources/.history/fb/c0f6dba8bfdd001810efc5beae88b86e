package lottery.domains.content.biz.impl;

import java.util.Set;
import java.util.HashSet;
import lottery.domains.content.entity.ActivityRebate;
import lottery.domains.content.entity.UserLotteryReport;
import lottery.domains.content.entity.activity.RebateRulesReward;
import net.sf.json.JSONArray;
import javautils.date.Moment;
import java.util.Iterator;
import lottery.domains.content.entity.User;
import java.util.List;
import lottery.domains.content.entity.ActivityRewardBill;
import lottery.domains.content.vo.activity.ActivityRewardBillVO;
import org.hibernate.criterion.Restrictions;
import javautils.StringUtil;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.ArrayList;
import javautils.jdbc.PageList;
import lottery.domains.pool.LotteryDataFactory;
import lottery.domains.content.biz.UserSysMessageService;
import lottery.domains.content.biz.UserBillService;
import lottery.domains.content.dao.UserLotteryReportDao;
import lottery.domains.content.dao.ActivityRewardBillDao;
import lottery.domains.content.dao.ActivityRebateDao;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.dao.UserDao;
import org.springframework.stereotype.Service;
import lottery.domains.content.biz.ActivityRewardService;

@Service
public class ActivityRewardServiceImpl implements ActivityRewardService
{
    @Autowired
    private UserDao uDao;
    @Autowired
    private ActivityRebateDao aRebateDao;
    @Autowired
    private ActivityRewardBillDao aRewardBillDao;
    @Autowired
    private UserLotteryReportDao uLotteryReportDao;
    @Autowired
    private UserBillService uBillService;
    @Autowired
    private UserSysMessageService uSysMessageService;
    @Autowired
    private LotteryDataFactory lotteryDataFactory;
    
    @Override
    public PageList search(final String username, final String date, final Integer type, final Integer status, final int start, final int limit) {
        final List<Criterion> criterions = new ArrayList<Criterion>();
        final List<Order> orders = new ArrayList<Order>();
        boolean isSearch = true;
        if (StringUtil.isNotNull(username)) {
            final User user = this.uDao.getByUsername(username);
            if (user != null) {
                criterions.add((Criterion)Restrictions.eq("toUser", (Object)user.getId()));
            }
            else {
                isSearch = false;
            }
        }
        if (StringUtil.isNotNull(date)) {
            criterions.add((Criterion)Restrictions.eq("date", (Object)date));
        }
        if (type != null) {
            criterions.add((Criterion)Restrictions.eq("type", (Object)type));
        }
        if (status != null) {
            criterions.add((Criterion)Restrictions.eq("status", (Object)status));
        }
        orders.add(Order.desc("id"));
        if (isSearch) {
            final List<ActivityRewardBillVO> list = new ArrayList<ActivityRewardBillVO>();
            final PageList pList = this.aRewardBillDao.find(criterions, orders, start, limit);
            for (final Object tmpBean : pList.getList()) {
                list.add(new ActivityRewardBillVO((ActivityRewardBill)tmpBean, this.lotteryDataFactory));
            }
            pList.setList(list);
            return pList;
        }
        return null;
    }
    
    @Override
    public boolean add(final int toUser, final int fromUser, final int type, final double totalMoney, final double money, final String date) {
        final String time = new Moment().toSimpleTime();
        final int status = 0;
        final ActivityRewardBill entity = new ActivityRewardBill(toUser, fromUser, type, totalMoney, money, date, time, status);
        return this.aRewardBillDao.add(entity);
    }
    
    @Override
    public List<ActivityRewardBillVO> getLatest(final int toUser, final int status, final int count) {
        final List<ActivityRewardBillVO> formatList = new ArrayList<ActivityRewardBillVO>();
        final List<ActivityRewardBill> list = this.aRewardBillDao.getLatest(toUser, status, count);
        for (final ActivityRewardBill tmpBean : list) {
            formatList.add(new ActivityRewardBillVO(tmpBean, this.lotteryDataFactory));
        }
        return formatList;
    }
    
    @Override
    public boolean calculate(final int type, final String date) {
        final ActivityRebate activity = this.aRebateDao.getByType(type);
        if (activity.getStatus() == 0) {
            final List<RebateRulesReward> rewards = (List<RebateRulesReward>)JSONArray.toCollection(JSONArray.fromObject((Object)activity.getRules()), (Class)RebateRulesReward.class);
            final String sTime = new Moment().fromDate(date).toSimpleDate();
            final String eTime = new Moment().fromDate(date).add(1, "days").toSimpleDate();
            final List<UserLotteryReport> list = this.doReport(sTime, eTime);
            for (final UserLotteryReport tmpBean : list) {
                if (tmpBean.getUserId() == 72) {
                    continue;
                }
                if (type == 1) {
                    final double totalMoney = tmpBean.getBillingOrder();
                    if (totalMoney > 0.0) {
                        final RebateRulesReward rBean = this.doMatch(rewards, totalMoney);
                        final int bType = 1;
                        this.addHandelBill(tmpBean.getUserId(), rBean, bType, totalMoney, date);
                    }
                }
                if (type != 2) {
                    continue;
                }
                final double totalMoney = tmpBean.getBillingOrder() - tmpBean.getPrize() - tmpBean.getSpendReturn();
                if (totalMoney <= 0.0) {
                    continue;
                }
                final RebateRulesReward rBean = this.doMatch(rewards, totalMoney);
                final int bType = 2;
                this.addHandelBill(tmpBean.getUserId(), rBean, bType, totalMoney, date);
            }
            return true;
        }
        return false;
    }
    
    List<UserLotteryReport> doReport(final String sTime, final String eTime) {
        final List<Criterion> criterions = new ArrayList<Criterion>();
        final List<Order> orders = new ArrayList<Order>();
        if (StringUtil.isNotNull(sTime)) {
            criterions.add((Criterion)Restrictions.ge("time", (Object)sTime));
        }
        if (StringUtil.isNotNull(eTime)) {
            criterions.add((Criterion)Restrictions.lt("time", (Object)eTime));
        }
        return this.uLotteryReportDao.find(criterions, orders);
    }
    
    RebateRulesReward doMatch(final List<RebateRulesReward> rewards, final double totalMoney) {
        RebateRulesReward reward = new RebateRulesReward();
        for (final RebateRulesReward tmpBean : rewards) {
            final double money = tmpBean.getMoney();
            if (totalMoney >= money && money > reward.getMoney()) {
                reward = tmpBean;
            }
        }
        return reward;
    }
    
    boolean addHandelBill(final int fromUser, final RebateRulesReward rBean, final int type, final double totalMoney, final String date) {
        if (rBean.getMoney() <= 0.0) {
            return false;
        }
        final User thisUser = this.uDao.getById(fromUser);
        if (thisUser == null) {
            return false;
        }
        if (rBean.getRewardUp1() == 0.0) {
            return false;
        }
        if (thisUser.getUpid() == 0 || thisUser.getUpid() == 72) {
            return false;
        }
        final User up1 = this.uDao.getById(thisUser.getUpid());
        if (up1 == null) {
            return false;
        }
        try {
            final int toUesr = up1.getId();
            final double money = rBean.getRewardUp1();
            final boolean hasRecord = this.aRewardBillDao.hasRecord(toUesr, fromUser, type, date);
            if (!hasRecord) {
                this.add(toUesr, fromUser, type, totalMoney, money, date);
            }
        }
        catch (Exception ex) {}
        if (rBean.getRewardUp2() == 0.0) {
            return false;
        }
        if (up1.getUpid() == 0) {
            return false;
        }
        final User up2 = this.uDao.getById(up1.getUpid());
        if (up2 == null) {
            return false;
        }
        try {
            final int toUesr2 = up2.getId();
            final double money2 = rBean.getRewardUp2();
            final boolean hasRecord2 = this.aRewardBillDao.hasRecord(toUesr2, fromUser, type, date);
            if (!hasRecord2) {
                this.add(toUesr2, fromUser, type, totalMoney, money2, date);
            }
        }
        catch (Exception ex2) {}
        return true;
    }
    
    @Override
    public boolean agreeAll(final String date) {
        final List<ActivityRewardBill> list = this.aRewardBillDao.getUntreated(date);
        final Set<Integer> uSet = new HashSet<Integer>();
        for (final ActivityRewardBill tmpBean : list) {
            try {
                if (tmpBean.getStatus() != 0) {
                    continue;
                }
                final String thisTime = new Moment().toSimpleTime();
                tmpBean.setStatus(1);
                tmpBean.setTime(thisTime);
                final boolean aFlag = this.aRewardBillDao.update(tmpBean);
                if (!aFlag) {
                    continue;
                }
                final User uBean = this.uDao.getById(tmpBean.getToUser());
                final boolean uFlag = this.uDao.updateLotteryMoney(uBean.getId(), tmpBean.getMoney());
                if (!uFlag) {
                    continue;
                }
                uSet.add(uBean.getId());
                String remarks = "彩票账户，";
                if (tmpBean.getType() == 1) {
                    remarks = String.valueOf(remarks) + "发放消费佣金。";
                }
                if (tmpBean.getType() == 2) {
                    remarks = String.valueOf(remarks) + "发放盈亏佣金。";
                }
                final int refType = 1;
                this.uBillService.addActivityBill(uBean, 2, tmpBean.getMoney(), refType, remarks);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        final String formatDate = new Moment().fromDate(date).format("yyyy年MM月dd日");
        for (final Integer id : uSet) {
            this.uSysMessageService.addRewardMessage(id, formatDate);
        }
        return true;
    }
}
