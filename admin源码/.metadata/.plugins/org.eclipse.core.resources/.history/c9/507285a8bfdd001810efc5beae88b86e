package lottery.domains.content.biz.impl;

import java.util.Iterator;
import lottery.domains.content.entity.User;
import java.util.List;
import lottery.domains.content.entity.ActivityRechargeLoopBill;
import lottery.domains.content.vo.activity.ActivityRechargeLoopBillVO;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import javautils.StringUtil;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.ArrayList;
import javautils.jdbc.PageList;
import lottery.domains.pool.LotteryDataFactory;
import lottery.domains.content.dao.ActivityRechargeLoopBillDao;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.dao.UserDao;
import org.springframework.stereotype.Service;
import lottery.domains.content.biz.ActivityRechargeLoopService;

@Service
public class ActivityRechargeLoopServiceImpl implements ActivityRechargeLoopService
{
    @Autowired
    private UserDao uDao;
    @Autowired
    private ActivityRechargeLoopBillDao aRechargeLoopBillDao;
    @Autowired
    private LotteryDataFactory lotteryDataFactory;
    
    @Override
    public PageList search(final String username, final String date, final Integer step, final int start, final int limit) {
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
        if (step != null) {
            criterions.add((Criterion)Restrictions.eq("step", (Object)step));
        }
        orders.add(Order.desc("id"));
        if (isSearch) {
            final List<ActivityRechargeLoopBillVO> list = new ArrayList<ActivityRechargeLoopBillVO>();
            final PageList pList = this.aRechargeLoopBillDao.find(criterions, orders, start, limit);
            for (final Object tmpBean : pList.getList()) {
                list.add(new ActivityRechargeLoopBillVO((ActivityRechargeLoopBill)tmpBean, this.lotteryDataFactory));
            }
            pList.setList(list);
            return pList;
        }
        return null;
    }
}
