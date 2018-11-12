package lottery.domains.content.biz.impl;

import java.util.Iterator;
import java.util.List;
import lottery.domains.content.entity.UserGameWaterBill;
import lottery.domains.content.vo.user.UserGameWaterBillVO;
import org.hibernate.criterion.Order;
import javautils.StringUtil;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Criterion;
import java.util.ArrayList;
import javautils.jdbc.PageList;
import lottery.domains.pool.LotteryDataFactory;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.dao.UserGameWaterBillDao;
import org.springframework.stereotype.Service;
import lottery.domains.content.biz.UserGameWaterBillService;

@Service
public class UserGameWaterBillServiceImpl implements UserGameWaterBillService
{
    @Autowired
    private UserGameWaterBillDao uGameWaterBillDao;
    @Autowired
    private LotteryDataFactory dataFactory;
    
    @Override
    public PageList search(final Integer userId, final String sTime, final String eTime, final Double minUserAmount, final Double maxUserAmount, final Integer type, final Integer status, int start, int limit) {
        start = ((start < 0) ? 0 : start);
        limit = ((limit < 0) ? 0 : limit);
        limit = ((limit > 20) ? 20 : limit);
        final List<Criterion> criterions = new ArrayList<Criterion>();
        if (userId != null) {
            criterions.add((Criterion)Restrictions.eq("userId", (Object)userId));
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
        if (type != null) {
            criterions.add((Criterion)Restrictions.eq("type", (Object)type));
        }
        if (status != null) {
            criterions.add((Criterion)Restrictions.eq("status", (Object)type));
        }
        final List<Order> orders = new ArrayList<Order>();
        orders.add(Order.desc("id"));
        final PageList pList = this.uGameWaterBillDao.search(criterions, orders, start, limit);
        final List<UserGameWaterBillVO> convertList = new ArrayList<UserGameWaterBillVO>();
        if (pList != null && pList.getList() != null) {
            for (final Object tmpBean : pList.getList()) {
                convertList.add(new UserGameWaterBillVO((UserGameWaterBill)tmpBean, this.dataFactory));
            }
        }
        pList.setList(convertList);
        return pList;
    }
    
    @Override
    public boolean add(final UserGameWaterBill bill) {
        return this.uGameWaterBillDao.add(bill);
    }
}
