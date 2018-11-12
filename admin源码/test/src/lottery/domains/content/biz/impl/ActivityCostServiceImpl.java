package lottery.domains.content.biz.impl;

import java.util.Iterator;
import lottery.domains.content.entity.User;
import java.util.List;
import lottery.domains.content.entity.ActivityCostBill;
import lottery.domains.content.vo.activity.ActivityCostBillVO;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import javautils.StringUtil;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.ArrayList;
import javautils.jdbc.PageList;
import lottery.domains.pool.LotteryDataFactory;
import lottery.domains.content.dao.ActivityCostBillDao;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.dao.UserDao;
import org.springframework.stereotype.Service;
import lottery.domains.content.biz.ActivityCostService;

@Service
public class ActivityCostServiceImpl implements ActivityCostService
{
    @Autowired
    private UserDao userDao;
    @Autowired
    private ActivityCostBillDao activityCostBillDao;
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
            final PageList pList = this.activityCostBillDao.find(criterions, orders, start, limit);
            final List<ActivityCostBillVO> list = new ArrayList<ActivityCostBillVO>();
            for (final Object o : pList.getList()) {
                list.add(new ActivityCostBillVO((ActivityCostBill)o, this.lotteryDataFactory));
            }
            pList.setList(list);
            return pList;
        }
        return null;
    }
}
