package lottery.domains.content.biz.impl;

import java.util.Iterator;
import org.hibernate.criterion.Disjunction;
import lottery.domains.content.entity.User;
import java.util.List;
import lottery.domains.content.entity.UserBetsPlan;
import lottery.domains.content.vo.user.UserBetsPlanVO;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import javautils.StringUtil;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.ArrayList;
import javautils.jdbc.PageList;
import lottery.domains.pool.LotteryDataFactory;
import lottery.domains.content.dao.UserBetsPlanDao;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.dao.UserDao;
import org.springframework.stereotype.Service;
import lottery.domains.content.biz.UserBetsPlanService;

@Service
public class UserBetsPlanServiceImpl implements UserBetsPlanService
{
    @Autowired
    private UserDao uDao;
    @Autowired
    private UserBetsPlanDao uBetsPlanDao;
    @Autowired
    private LotteryDataFactory lotteryDataFactory;
    
    @Override
    public PageList search(final String keyword, final String username, final Integer lotteryId, final String expect, final Integer ruleId, final String minTime, final String maxTime, final Double minMoney, final Double maxMoney, final Integer minMultiple, final Integer maxMultiple, final Integer status, final int start, final int limit) {
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
        if (StringUtil.isNotNull(keyword)) {
            final Disjunction disjunction = Restrictions.or(new Criterion[0]);
            if (StringUtil.isInteger(keyword)) {
                disjunction.add((Criterion)Restrictions.eq("id", (Object)Integer.parseInt(keyword)));
            }
            disjunction.add((Criterion)Restrictions.like("billno", keyword, MatchMode.ANYWHERE));
            disjunction.add((Criterion)Restrictions.like("orderId", keyword, MatchMode.ANYWHERE));
            criterions.add((Criterion)disjunction);
        }
        if (lotteryId != null) {
            criterions.add((Criterion)Restrictions.eq("lotteryId", (Object)lotteryId));
        }
        if (StringUtil.isNotNull(expect)) {
            criterions.add((Criterion)Restrictions.like("expect", expect, MatchMode.END));
        }
        if (ruleId != null) {
            criterions.add((Criterion)Restrictions.eq("ruleId", (Object)ruleId));
        }
        if (StringUtil.isNotNull(minTime)) {
            criterions.add((Criterion)Restrictions.gt("time", (Object)minTime));
        }
        if (StringUtil.isNotNull(maxTime)) {
            criterions.add((Criterion)Restrictions.lt("time", (Object)maxTime));
        }
        if (minMoney != null) {
            criterions.add((Criterion)Restrictions.ge("money", (Object)minMoney));
        }
        if (maxMoney != null) {
            criterions.add((Criterion)Restrictions.le("money", (Object)maxMoney));
        }
        if (minMultiple != null) {
            criterions.add((Criterion)Restrictions.ge("multiple", (Object)minMultiple));
        }
        if (maxMultiple != null) {
            criterions.add((Criterion)Restrictions.le("multiple", (Object)maxMultiple));
        }
        if (status != null) {
            criterions.add((Criterion)Restrictions.eq("status", (Object)status));
        }
        orders.add(Order.desc("id"));
        if (isSearch) {
            final List<UserBetsPlanVO> list = new ArrayList<UserBetsPlanVO>();
            final PageList pList = this.uBetsPlanDao.search(criterions, orders, start, limit);
            for (final Object tmpBean : pList.getList()) {
                final UserBetsPlanVO tmpVO = new UserBetsPlanVO((UserBetsPlan)tmpBean, this.lotteryDataFactory);
                list.add(tmpVO);
            }
            pList.setList(list);
            return pList;
        }
        return null;
    }
}
