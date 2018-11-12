package lottery.domains.content.biz.impl;

import lottery.domains.content.entity.UserBalanceSnapshot;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import org.hibernate.criterion.Restrictions;
import javautils.StringUtil;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.ArrayList;
import javautils.jdbc.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.dao.UserBalanceSnapshotDao;
import org.springframework.stereotype.Service;
import lottery.domains.content.biz.UserBalanceSnapshotService;

@Service
public class UserBalanceSnapshotServiceImpl implements UserBalanceSnapshotService
{
    @Autowired
    private UserBalanceSnapshotDao uBalanceSnapshotDao;
    
    @Transactional(readOnly = true)
    @Override
    public PageList search(final String sDate, final String eDate, final int start, final int limit) {
        final List<Criterion> criterions = new ArrayList<Criterion>();
        final List<Order> orders = new ArrayList<Order>();
        if (StringUtil.isNotNull(sDate)) {
            criterions.add((Criterion)Restrictions.ge("time", (Object)sDate));
        }
        if (StringUtil.isNotNull(eDate)) {
            criterions.add((Criterion)Restrictions.le("time", (Object)eDate));
        }
        orders.add(Order.desc("time"));
        return this.uBalanceSnapshotDao.find(criterions, orders, start, limit);
    }
    
    @Override
    public boolean add(final UserBalanceSnapshot entity) {
        return this.uBalanceSnapshotDao.add(entity);
    }
}
