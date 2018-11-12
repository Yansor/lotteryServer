package lottery.domains.content.dao.impl;

import javautils.jdbc.PageList;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.List;
import lottery.domains.content.entity.SysNotice;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.entity.UserBalanceSnapshot;
import javautils.jdbc.hibernate.HibernateSuperDao;
import org.springframework.stereotype.Repository;
import lottery.domains.content.dao.UserBalanceSnapshotDao;

@Repository
public class UserBalanceSnapshotDaoImpl implements UserBalanceSnapshotDao
{
    private final String tab;
    @Autowired
    private HibernateSuperDao<UserBalanceSnapshot> superDao;
    
    public UserBalanceSnapshotDaoImpl() {
        this.tab = SysNotice.class.getSimpleName();
    }
    
    @Override
    public PageList find(final List<Criterion> criterions, final List<Order> orders, final int start, final int limit) {
        return this.superDao.findPageList(UserBalanceSnapshot.class, criterions, orders, start, limit);
    }
    
    @Override
    public boolean add(final UserBalanceSnapshot entity) {
        return this.superDao.save(entity);
    }
}
