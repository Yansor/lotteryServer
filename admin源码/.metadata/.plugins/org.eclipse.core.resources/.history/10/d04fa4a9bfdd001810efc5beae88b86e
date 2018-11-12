package lottery.domains.content.biz.impl;

import lottery.domains.content.entity.ActivitySignRecord;
import lottery.domains.content.vo.activity.ActivitySignRecordVO;
import lottery.domains.content.entity.UserLotteryReport;
import javautils.date.Moment;
import java.util.Iterator;
import lottery.domains.content.entity.User;
import java.util.List;
import lottery.domains.content.entity.ActivitySignBill;
import lottery.domains.content.vo.activity.ActivitySignBillVO;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import javautils.StringUtil;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.ArrayList;
import javautils.jdbc.PageList;
import lottery.domains.pool.LotteryDataFactory;
import lottery.domains.content.dao.UserLotteryReportDao;
import lottery.domains.content.dao.ActivitySignRecordDao;
import lottery.domains.content.dao.ActivitySignBillDao;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.dao.UserDao;
import org.springframework.stereotype.Service;
import lottery.domains.content.biz.ActivitySignService;

@Service
public class ActivitySignServiceImpl implements ActivitySignService
{
    @Autowired
    private UserDao userDao;
    @Autowired
    private ActivitySignBillDao activitySignBillDao;
    @Autowired
    private ActivitySignRecordDao activitySignRecordDao;
    @Autowired
    private UserLotteryReportDao uLotteryReportDao;
    @Autowired
    private LotteryDataFactory lotteryDataFactory;
    
    @Override
    public PageList searchBill(final String username, final String date, int start, int limit) {
        if (start < 0) {
            start = 0;
        }
        if (limit < 0) {
            limit = 10;
        }
        if (limit > 100) {
            limit = 100;
        }
        final List<Criterion> criterions = new ArrayList<Criterion>();
        final List<Order> orders = new ArrayList<Order>();
        orders.add(Order.desc("id"));
        boolean isSearch = true;
        if (StringUtil.isNotNull(username)) {
            final User uBean = this.userDao.getByUsername(username);
            if (uBean != null) {
                criterions.add((Criterion)Restrictions.eq("userId", (Object)uBean.getId()));
            }
            else {
                isSearch = false;
            }
        }
        if (StringUtil.isNotNull(date)) {
            criterions.add((Criterion)Restrictions.like("time", date, MatchMode.ANYWHERE));
        }
        if (isSearch) {
            final PageList pList = this.activitySignBillDao.find(criterions, orders, start, limit);
            final List<ActivitySignBillVO> list = new ArrayList<ActivitySignBillVO>();
            for (final Object o : pList.getList()) {
                list.add(new ActivitySignBillVO((ActivitySignBill)o, this.lotteryDataFactory));
            }
            pList.setList(list);
            return pList;
        }
        return null;
    }
    
    double getTotalCost(final String startTime, final String endTime, final int userId) {
        final String sDate = new Moment().fromTime(startTime).toSimpleDate();
        final String eDate = new Moment().fromTime(endTime).add(1, "days").toSimpleDate();
        final int[] ids = { userId };
        final List<UserLotteryReport> lotteryDayList = this.uLotteryReportDao.getDayList(ids, sDate, eDate);
        double totalCost = 0.0;
        for (final UserLotteryReport tmpBean : lotteryDayList) {
            totalCost += tmpBean.getBillingOrder();
        }
        return totalCost;
    }
    
    @Override
    public PageList searchRecord(final String username, int start, int limit) {
        if (start < 0) {
            start = 0;
        }
        if (limit < 0) {
            limit = 10;
        }
        if (limit > 100) {
            limit = 100;
        }
        final List<Criterion> criterions = new ArrayList<Criterion>();
        final List<Order> orders = new ArrayList<Order>();
        orders.add(Order.desc("lastSignTime"));
        boolean isSearch = true;
        if (StringUtil.isNotNull(username)) {
            final User uBean = this.userDao.getByUsername(username);
            if (uBean != null) {
                criterions.add((Criterion)Restrictions.eq("userId", (Object)uBean.getId()));
            }
            else {
                isSearch = false;
            }
        }
        if (isSearch) {
            final PageList pList = this.activitySignRecordDao.find(criterions, orders, start, limit);
            final List<ActivitySignRecordVO> list = new ArrayList<ActivitySignRecordVO>();
            for (final Object o : pList.getList()) {
                final ActivitySignRecord signRecord = (ActivitySignRecord)o;
                list.add(new ActivitySignRecordVO(signRecord, this.lotteryDataFactory));
            }
            pList.setList(list);
            return pList;
        }
        return null;
    }
}
