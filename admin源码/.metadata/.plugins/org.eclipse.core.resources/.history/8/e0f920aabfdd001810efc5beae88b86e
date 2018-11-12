package lottery.domains.content.biz.impl;

import java.util.HashMap;
import javautils.date.DateUtil;
import java.util.Map;
import java.util.Iterator;
import lottery.domains.content.entity.User;
import java.util.List;
import lottery.domains.content.entity.ActivityGrabBill;
import lottery.domains.content.vo.activity.ActivityGrabBillVO;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import javautils.StringUtil;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.ArrayList;
import javautils.jdbc.PageList;
import lottery.domains.pool.LotteryDataFactory;
import lottery.domains.content.dao.ActivityGrabBillDao;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.dao.UserDao;
import org.springframework.stereotype.Service;
import lottery.domains.content.biz.ActivityGrabService;

@Service
public class ActivityGrabServiceImpl implements ActivityGrabService
{
    @Autowired
    private UserDao userDao;
    @Autowired
    private ActivityGrabBillDao grabBillDao;
    @Autowired
    private LotteryDataFactory lotteryDataFactory;
    private static final String ALL_AMOUNT = "allAmount";
    private static final String TODAY_AMOUNT = "todayAmount";
    
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
            final PageList pList = this.grabBillDao.find(criterions, orders, start, limit);
            final List<ActivityGrabBillVO> list = new ArrayList<ActivityGrabBillVO>();
            for (final Object o : pList.getList()) {
                list.add(new ActivityGrabBillVO((ActivityGrabBill)o, this.lotteryDataFactory));
            }
            pList.setList(list);
            return pList;
        }
        return null;
    }
    
    @Override
    public Map<String, Double> getOutTotalInfo() {
        final Double allAmount = this.grabBillDao.getOutAmount(null);
        final Double todayAmount = this.grabBillDao.getOutAmount(DateUtil.getCurrentTime());
        final Map<String, Double> totalInfo = new HashMap<String, Double>();
        totalInfo.put("allAmount", allAmount);
        totalInfo.put("todayAmount", todayAmount);
        return totalInfo;
    }
}
