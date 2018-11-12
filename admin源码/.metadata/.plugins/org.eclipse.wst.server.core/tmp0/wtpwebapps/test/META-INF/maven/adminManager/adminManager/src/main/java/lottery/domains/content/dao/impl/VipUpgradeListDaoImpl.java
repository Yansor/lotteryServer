package lottery.domains.content.dao.impl;

import javautils.jdbc.PageList;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.entity.VipUpgradeList;
import javautils.jdbc.hibernate.HibernateSuperDao;
import org.springframework.stereotype.Repository;
import lottery.domains.content.dao.VipUpgradeListDao;

@Repository
public class VipUpgradeListDaoImpl implements VipUpgradeListDao
{
    private final String tab;
    @Autowired
    private HibernateSuperDao<VipUpgradeList> superDao;
    
    public VipUpgradeListDaoImpl() {
        this.tab = VipUpgradeList.class.getSimpleName();
    }
    
    @Override
    public boolean add(final VipUpgradeList entity) {
        return this.superDao.save(entity);
    }
    
    @Override
    public boolean hasRecord(final int userId, final String month) {
        final String hql = "from " + this.tab + " where userId = ?0 and month = ?1";
        final Object[] values = { userId, month };
        final List<VipUpgradeList> list = this.superDao.list(hql, values);
        return list != null && list.size() > 0;
    }
    
    @Override
    public PageList find(final List<Criterion> criterions, final List<Order> orders, final int start, final int limit) {
        return this.superDao.findPageList(VipUpgradeList.class, criterions, orders, start, limit);
    }
}
