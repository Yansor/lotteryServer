package lottery.domains.content.dao.impl;

import javautils.jdbc.PageList;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.entity.VipFreeChips;
import javautils.jdbc.hibernate.HibernateSuperDao;
import org.springframework.stereotype.Repository;
import lottery.domains.content.dao.VipFreeChipsDao;

@Repository
public class VipFreeChipsDaoImpl implements VipFreeChipsDao
{
    private final String tab;
    @Autowired
    private HibernateSuperDao<VipFreeChips> superDao;
    
    public VipFreeChipsDaoImpl() {
        this.tab = VipFreeChips.class.getSimpleName();
    }
    
    @Override
    public boolean add(final VipFreeChips entity) {
        return this.superDao.save(entity);
    }
    
    @Override
    public VipFreeChips getById(final int id) {
        final String hql = "from " + this.tab + " where id = ?0";
        final Object[] values = { id };
        return (VipFreeChips)this.superDao.unique(hql, values);
    }
    
    @Override
    public List<VipFreeChips> getUntreated() {
        final String hql = "from " + this.tab + " where status = 0";
        return this.superDao.list(hql);
    }
    
    @Override
    public boolean hasRecord(final int userId, final String sTime, final String eTime) {
        final String hql = "from " + this.tab + " where userId = ?0 and startTime = ?1 and endTime = ?2";
        final Object[] values = { userId, sTime, eTime };
        final List<VipFreeChips> list = this.superDao.list(hql, values);
        return list != null && list.size() > 0;
    }
    
    @Override
    public boolean update(final VipFreeChips entity) {
        return this.superDao.update(entity);
    }
    
    @Override
    public PageList find(final List<Criterion> criterions, final List<Order> orders, final int start, final int limit) {
        return this.superDao.findPageList(VipFreeChips.class, criterions, orders, start, limit);
    }
}
