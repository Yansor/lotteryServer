package lottery.domains.content.biz.impl;

import java.util.Iterator;
import org.hibernate.criterion.Disjunction;
import lottery.domains.content.entity.User;
import java.util.List;
import lottery.domains.content.entity.UserActionLog;
import lottery.domains.content.vo.user.UserActionLogVO;
import org.hibernate.criterion.MatchMode;
import java.util.StringTokenizer;
import org.hibernate.criterion.Restrictions;
import javautils.StringUtil;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.ArrayList;
import javautils.jdbc.PageList;
import lottery.domains.pool.LotteryDataFactory;
import lottery.domains.content.dao.UserActionLogDao;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.dao.UserDao;
import org.springframework.stereotype.Service;
import lottery.domains.content.biz.UserActionLogService;

@Service
public class UserActionLogServiceImpl implements UserActionLogService
{
    @Autowired
    private UserDao uDao;
    @Autowired
    private UserActionLogDao uActionLogDao;
    @Autowired
    private LotteryDataFactory lotteryDataFactory;
    
    @Override
    public PageList search(final String username, final String ip, final String keyword, final String date, final int start, final int limit) {
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
        if (StringUtil.isNotNull(ip)) {
            criterions.add((Criterion)Restrictions.eq("ip", (Object)ip));
        }
        if (StringUtil.isNotNull(keyword)) {
            final StringTokenizer token = new StringTokenizer(keyword);
            final Disjunction disjunction = Restrictions.or(new Criterion[0]);
            while (token.hasMoreElements()) {
                final String value = (String)token.nextElement();
                disjunction.add((Criterion)Restrictions.like("action", value, MatchMode.ANYWHERE));
            }
            criterions.add((Criterion)disjunction);
        }
        if (StringUtil.isNotNull(date)) {
            criterions.add((Criterion)Restrictions.like("time", date, MatchMode.ANYWHERE));
        }
        orders.add(Order.desc("id"));
        if (isSearch) {
            final List<UserActionLogVO> list = new ArrayList<UserActionLogVO>();
            final PageList pList = this.uActionLogDao.find(criterions, orders, start, limit);
            for (final Object tmpBean : pList.getList()) {
                list.add(new UserActionLogVO((UserActionLog)tmpBean, this.lotteryDataFactory));
            }
            pList.setList(list);
            return pList;
        }
        return null;
    }
}
