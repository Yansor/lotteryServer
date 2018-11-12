package lottery.domains.content.biz.impl;

import java.util.Iterator;
import lottery.domains.content.vo.user.UserVO;
import java.util.List;
import lottery.domains.content.entity.ActivityRebateWheelBill;
import lottery.domains.content.vo.activity.ActivityRebateWheelBillVO;
import org.hibernate.criterion.Restrictions;
import javautils.StringUtil;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.ArrayList;
import javautils.jdbc.PageList;
import lottery.domains.pool.LotteryDataFactory;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.dao.ActivityRebateWheelBillDao;
import org.springframework.stereotype.Service;
import lottery.domains.content.biz.ActivityRebateWheelBillService;

@Service
public class ActivityRebateWheelBillServiceImpl implements ActivityRebateWheelBillService
{
    @Autowired
    private ActivityRebateWheelBillDao billDao;
    @Autowired
    private LotteryDataFactory dataFactory;
    
    @Override
    public PageList find(final String username, final String minTime, final String maxTime, final String ip, final int start, final int limit) {
        final List<Criterion> criterions = new ArrayList<Criterion>();
        final List<Order> orders = new ArrayList<Order>();
        if (StringUtil.isNotNull(username)) {
            final UserVO user = this.dataFactory.getUser(username);
            if (user != null) {
                criterions.add((Criterion)Restrictions.eq("userId", (Object)user.getId()));
            }
        }
        if (StringUtil.isNotNull(minTime)) {
            criterions.add((Criterion)Restrictions.ge("time", (Object)minTime));
        }
        if (StringUtil.isNotNull(maxTime)) {
            criterions.add((Criterion)Restrictions.lt("time", (Object)maxTime));
        }
        if (StringUtil.isNotNull(ip)) {
            criterions.add((Criterion)Restrictions.eq("ip", (Object)ip));
        }
        orders.add(Order.desc("id"));
        final List<ActivityRebateWheelBillVO> list = new ArrayList<ActivityRebateWheelBillVO>();
        final PageList pList = this.billDao.find(criterions, orders, start, limit);
        for (final Object tmpBean : pList.getList()) {
            final ActivityRebateWheelBillVO tmpVO = new ActivityRebateWheelBillVO((ActivityRebateWheelBill)tmpBean, this.dataFactory);
            list.add(tmpVO);
        }
        pList.setList(list);
        return pList;
    }
    
    @Override
    public double sumAmount(final String username, final String minTime, final String maxTime, final String ip) {
        Integer userId = null;
        if (StringUtil.isNotNull(username)) {
            final UserVO user = this.dataFactory.getUser(username);
            if (user != null) {
                userId = user.getId();
            }
        }
        return this.billDao.sumAmount(userId, minTime, maxTime, ip);
    }
}
