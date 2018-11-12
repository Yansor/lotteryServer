package lottery.domains.content.biz.impl;

import java.util.Iterator;
import lottery.domains.content.entity.UserGameDividendBill;
import lottery.domains.content.vo.user.UserGameDividendBillVO;
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
import lottery.domains.content.biz.UserBillService;
import lottery.domains.content.dao.UserDao;
import lottery.domains.pool.LotteryDataFactory;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.dao.UserGameDividendBillDao;
import org.springframework.stereotype.Service;
import lottery.domains.content.biz.UserGameDividendBillService;

@Service
public class UserGameDividendBillServiceImpl implements UserGameDividendBillService
{
    @Autowired
    private UserGameDividendBillDao uGameDividendBillDao;
    @Autowired
    private LotteryDataFactory dataFactory;
    @Autowired
    private UserDao uDao;
    @Autowired
    private UserBillService uBillService;
    @Autowired
    private UserSysMessageService uSysMessageService;
    
    @Override
    public PageList search(final List<Integer> userIds, final String sTime, final String eTime, final Double minUserAmount, final Double maxUserAmount, final Integer status, int start, int limit) {
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
        final List<Order> orders = new ArrayList<Order>();
        orders.add(Order.desc("id"));
        final PageList pList = this.uGameDividendBillDao.search(criterions, orders, start, limit);
        final List<UserGameDividendBillVO> convertList = new ArrayList<UserGameDividendBillVO>();
        if (pList != null && pList.getList() != null) {
            for (final Object tmpBean : pList.getList()) {
                convertList.add(new UserGameDividendBillVO((UserGameDividendBill)tmpBean, this.dataFactory));
            }
        }
        pList.setList(convertList);
        return pList;
    }
    
    @Override
    public double sumUserAmount(final List<Integer> userIds, final String sTime, final String eTime, final Double minUserAmount, final Double maxUserAmount, final Integer status) {
        return this.uGameDividendBillDao.sumUserAmount(userIds, sTime, eTime, minUserAmount, maxUserAmount, status);
    }
    
    @Override
    public List<UserGameDividendBill> findByCriteria(final List<Criterion> criterions, final List<Order> orders) {
        return this.uGameDividendBillDao.findByCriteria(criterions, orders);
    }
    
    @Override
    public UserGameDividendBill getById(final int id) {
        return this.uGameDividendBillDao.getById(id);
    }
    
    @Override
    public boolean agree(final int id, final double userAmount, final String remarks) {
        final UserGameDividendBill dividendBill = this.getById(id);
        if (dividendBill == null || dividendBill.getUserAmount() <= 0.0) {
            return false;
        }
        final boolean updated = this.uGameDividendBillDao.update(dividendBill.getId(), 3, userAmount, remarks);
        if (updated && userAmount > 0.0) {
            this.uSysMessageService.addGameDividendBill(dividendBill.getUserId(), dividendBill.getIndicateStartDate(), dividendBill.getIndicateEndDate());
        }
        return updated;
    }
    
    @Override
    public boolean deny(final int id, final double userAmount, final String remarks) {
        return this.uGameDividendBillDao.update(id, 4, userAmount, remarks);
    }
    
    @Override
    public boolean del(final int id) {
        return this.uGameDividendBillDao.del(id);
    }
}
