package lottery.domains.content.biz.impl;

import java.util.Iterator;
import java.util.List;
import lottery.domains.content.entity.UserBetsSameIpLog;
import lottery.domains.content.vo.user.UserBetsSameIpLogVO;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import javautils.StringUtil;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.ArrayList;
import javautils.jdbc.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.dao.UserBetsSameIpLogDao;
import org.springframework.stereotype.Service;
import lottery.domains.content.biz.UserBetsSameIpLogService;

@Service
public class UserBetsSameIpLogServiceImpl implements UserBetsSameIpLogService
{
    @Autowired
    private UserBetsSameIpLogDao uBetsSameIpLogDao;
    
    @Override
    public PageList search(final String ip, final String username, final String sortColumn, final String sortType, final int start, final int limit) {
        final List<Criterion> criterions = new ArrayList<Criterion>();
        final List<Order> orders = new ArrayList<Order>();
        if (StringUtil.isNotNull(ip)) {
            criterions.add((Criterion)Restrictions.eq("ip", (Object)ip));
        }
        if (StringUtil.isNotNull(username)) {
            criterions.add((Criterion)Restrictions.like("users", "[" + username + "]", MatchMode.ANYWHERE));
        }
        if (StringUtil.isNotNull(sortColumn)) {
            if ("usersCount".equals(sortColumn)) {
                orders.add("DESC".equals(sortType) ? Order.desc("usersCount") : Order.asc("usersCount"));
            }
            else if ("lastTime".equals(sortColumn)) {
                orders.add("DESC".equals(sortType) ? Order.desc("lastTime") : Order.asc("lastTime"));
            }
            else if ("times".equals(sortColumn)) {
                orders.add("DESC".equals(sortType) ? Order.desc("times") : Order.asc("times"));
            }
            else if ("amount".equals(sortColumn)) {
                orders.add("DESC".equals(sortType) ? Order.desc("amount") : Order.asc("amount"));
            }
        }
        else {
            orders.add(Order.desc("lastTime"));
            orders.add(Order.desc("id"));
        }
        final List<UserBetsSameIpLogVO> list = new ArrayList<UserBetsSameIpLogVO>();
        final PageList plist = this.uBetsSameIpLogDao.search(criterions, orders, start, limit);
        for (final Object tmpBean : plist.getList()) {
            list.add(new UserBetsSameIpLogVO((UserBetsSameIpLog)tmpBean));
        }
        plist.setList(list);
        return plist;
    }
}
