package lottery.domains.content.biz.impl;

import java.util.Iterator;
import org.hibernate.criterion.Disjunction;
import java.util.List;
import lottery.domains.content.entity.ActivityBindBill;
import lottery.domains.content.vo.activity.ActivityBindBillVO;
import org.hibernate.criterion.MatchMode;
import lottery.domains.content.entity.User;
import org.hibernate.criterion.Restrictions;
import javautils.StringUtil;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.ArrayList;
import javautils.jdbc.PageList;
import lottery.domains.pool.LotteryDataFactory;
import lottery.domains.content.dao.UserRechargeDao;
import lottery.domains.content.dao.UserBetsDao;
import lottery.domains.content.dao.ActivityBindBillDao;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.dao.UserDao;
import org.springframework.stereotype.Service;
import lottery.domains.content.biz.ActivityBindService;

@Service
public class ActivityBindServiceImpl implements ActivityBindService
{
    @Autowired
    private UserDao uDao;
    @Autowired
    private ActivityBindBillDao aBindBillDao;
    @Autowired
    private UserBetsDao uBetsDao;
    @Autowired
    private UserRechargeDao uRechargeDao;
    @Autowired
    private LotteryDataFactory lotteryDataFactory;
    
    @Override
    public PageList search(final String username, final String upperUser, final String date, final String keyword, final Integer status, final int start, final int limit) {
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
        if (StringUtil.isNotNull(upperUser)) {
            final User user = this.uDao.getByUsername(upperUser);
            if (user != null) {
                final List<User> lowers = this.uDao.getUserLower(user.getId());
                if (lowers.size() > 0) {
                    final Integer[] ids = new Integer[lowers.size()];
                    for (int i = 0; i < lowers.size(); ++i) {
                        ids[i] = lowers.get(i).getId();
                    }
                    criterions.add(Restrictions.in("userId", (Object[])ids));
                }
                else {
                    isSearch = false;
                }
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
            disjunction.add((Criterion)Restrictions.eq("bindName", (Object)keyword));
            disjunction.add((Criterion)Restrictions.eq("bindCard", (Object)keyword));
            criterions.add((Criterion)disjunction);
        }
        if (status != null) {
            criterions.add((Criterion)Restrictions.eq("status", (Object)status));
        }
        orders.add(Order.desc("id"));
        if (isSearch) {
            final List<ActivityBindBillVO> list = new ArrayList<ActivityBindBillVO>();
            final PageList pList = this.aBindBillDao.find(criterions, orders, start, limit);
            for (final Object tmpBean : pList.getList()) {
                final ActivityBindBill bBean = (ActivityBindBill)tmpBean;
                final User uBean = this.uDao.getById(bBean.getUserId());
                final ActivityBindBillVO vBean = new ActivityBindBillVO(bBean, uBean, this.lotteryDataFactory);
                final boolean isRecharge = this.uRechargeDao.isRecharge(bBean.getUserId());
                vBean.setRecharge(isRecharge);
                final boolean isCost = this.uBetsDao.isCost(bBean.getUserId());
                vBean.setCost(isCost);
                list.add(vBean);
            }
            pList.setList(list);
            return pList;
        }
        return null;
    }
    
    @Override
    public boolean refuse(final int id) {
        final ActivityBindBill entity = this.aBindBillDao.getById(id);
        if (entity != null && entity.getStatus() == 0) {
            entity.setStatus(-1);
            return this.aBindBillDao.update(entity);
        }
        return false;
    }
    
    @Override
    public boolean check(final int id) {
        final ActivityBindBill entity = this.aBindBillDao.getById(id);
        if (entity != null) {
            final List<ActivityBindBill> list = this.aBindBillDao.get(entity.getIp(), entity.getBindName(), entity.getBindCard());
            if (list != null && list.size() > 1) {
                return true;
            }
        }
        return false;
    }
}
