package lottery.domains.content.biz.impl;

import lottery.domains.content.vo.user.UserLoginSameIpLog;
import org.apache.commons.lang.StringUtils;
import lottery.domains.content.entity.HistoryUserLoginLog;
import lottery.domains.content.vo.user.HistoryUserLoginLogVO;
import org.hibernate.criterion.MatchMode;
import java.util.Iterator;
import lottery.domains.content.entity.User;
import java.util.List;
import lottery.domains.content.entity.UserLoginLog;
import lottery.domains.content.vo.user.UserLoginLogVO;
import org.hibernate.criterion.Restrictions;
import javautils.StringUtil;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.ArrayList;
import javautils.jdbc.PageList;
import lottery.domains.pool.LotteryDataFactory;
import lottery.domains.content.dao.UserLoginLogDao;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.dao.UserDao;
import org.springframework.stereotype.Service;
import lottery.domains.content.biz.UserLoginLogService;

@Service
public class UserLoginLogServiceImpl implements UserLoginLogService
{
    @Autowired
    private UserDao uDao;
    @Autowired
    private UserLoginLogDao uLoginLogDao;
    @Autowired
    private LotteryDataFactory lotteryDataFactory;
    
    @Override
    public PageList search(final String username, final String ip, final String date, final String loginLine, final int start, final int limit) {
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
        if (StringUtil.isNotNull(date)) {
            criterions.add((Criterion)Restrictions.ge("time", (Object)date));
        }
        if (StringUtil.isNotNull(loginLine)) {
            criterions.add((Criterion)Restrictions.eq("loginLine", (Object)loginLine));
        }
        orders.add(Order.desc("id"));
        if (isSearch) {
            final List<UserLoginLogVO> list = new ArrayList<UserLoginLogVO>();
            final PageList pList = this.uLoginLogDao.find(criterions, orders, start, limit);
            for (final Object tmpBean : pList.getList()) {
                list.add(new UserLoginLogVO((UserLoginLog)tmpBean, this.lotteryDataFactory));
            }
            pList.setList(list);
            return pList;
        }
        return null;
    }
    
    @Override
    public PageList searchHistory(final String username, final String ip, final String date, final String loginLine, final int start, final int limit) {
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
        if (StringUtil.isNotNull(date)) {
            criterions.add((Criterion)Restrictions.like("time", date, MatchMode.ANYWHERE));
        }
        if (StringUtil.isNotNull(loginLine)) {
            criterions.add((Criterion)Restrictions.like("loginLine", (Object)loginLine));
        }
        orders.add(Order.desc("id"));
        if (isSearch) {
            final List<HistoryUserLoginLogVO> list = new ArrayList<HistoryUserLoginLogVO>();
            final PageList pList = this.uLoginLogDao.findHistory(criterions, orders, start, limit);
            for (final Object tmpBean : pList.getList()) {
                list.add(new HistoryUserLoginLogVO((HistoryUserLoginLog)tmpBean, this.lotteryDataFactory));
            }
            pList.setList(list);
            return pList;
        }
        return null;
    }
    
    @Override
    public PageList searchSameIp(final String username, final String ip, final int start, final int limit) {
        if (StringUtils.isEmpty(username) && StringUtils.isEmpty(ip)) {
            return null;
        }
        Integer userId = null;
        if (StringUtils.isNotEmpty(username)) {
            final User user = this.uDao.getByUsername(username);
            if (user == null) {
                return null;
            }
            userId = user.getId();
        }
        final PageList pageList = this.uLoginLogDao.searchSameIp(userId, ip, start, limit);
        final List<?> list = pageList.getList();
        final List<UserLoginSameIpLog> voList = new ArrayList<UserLoginSameIpLog>(list.size());
        for (final Object o : list) {
            final Object[] objs = (Object[])o;
            final String _ip = (objs[0] != null) ? objs[0].toString() : "";
            final String _address = (objs[1] != null) ? objs[1].toString() : "";
            final String _users = (objs[2] != null) ? objs[2].toString() : "";
            final UserLoginSameIpLog vo = new UserLoginSameIpLog(_ip, _address, _users);
            voList.add(vo);
        }
        pageList.setList(voList);
        return pageList;
    }
}
