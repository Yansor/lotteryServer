package lottery.domains.content.dao.impl;

import javautils.jdbc.PageList;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.entity.UserBetsSameIpLog;
import javautils.jdbc.hibernate.HibernateSuperDao;
import org.springframework.stereotype.Repository;
import lottery.domains.content.dao.UserBetsSameIpLogDao;

@Repository
public class UserBetsSameIpLogDaoImpl implements UserBetsSameIpLogDao
{
    private final String tab;
    @Autowired
    private HibernateSuperDao<UserBetsSameIpLog> superDao;
    
    public UserBetsSameIpLogDaoImpl() {
        this.tab = UserBetsSameIpLog.class.getSimpleName();
    }
    
    @Override
    public PageList search(final List<Criterion> criterions, final List<Order> orders, final int start, final int limit) {
        return this.superDao.findPageList(UserBetsSameIpLog.class, criterions, orders, start, limit);
    }
}
